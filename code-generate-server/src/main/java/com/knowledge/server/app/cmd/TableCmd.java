package com.knowledge.server.app.cmd;

import com.knowledge.common.mybatis.utils.IdUtils;
import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.AutoFillEnum;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.server.app.qry.DatasourceQry;
import com.knowledge.server.app.qry.TableFieldQry;
import com.knowledge.server.common.AuthUtils;
import com.knowledge.server.domain.datasource.Datasource;
import com.knowledge.server.domain.table.Table;
import com.knowledge.server.domain.table.TableField;
import com.knowledge.server.infrastructure.gateway.TableGateway;
import com.knowledge.server.web.response.table.TableFieldVo;
import com.knowledge.server.web.response.table.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class TableCmd {
    @Autowired
    private TableGateway tableGateway;
    @Autowired
    private DatasourceQry datasourceQry;
    @Autowired
    private TableFieldCmd tableFieldCmd;
    @Autowired
    private TableFieldQry tableFieldQry;
    /**
     * 数据批量保存
     */
    @Async("threadPoolTaskExecutor")
    public void batchSave(List<TableVo> list, UserDto user) {
        Predicate<Object> predicate = x -> x != null;
        IdUtils idUtils = new IdUtils();
        list.forEach(item -> {
            Table table = tableGateway.selectByDatasourceAndName(item.getDatasourceId(), item.getTableName(), user.getId());
            if (!predicate.test(table)) {
                Datasource datasource = datasourceQry.selectObjectById(item.getDatasourceId());
                table = Table.builder()
                        .tableName(item.getTableName())
                        .user(user)
                        .comment(item.getComment())
                        .datasource(datasource)
                        .build();
                table.setId(idUtils.nextId());
                tableGateway.saveDomainObj(table);
            }
            List<TableFieldVo> fieldVos = item.getFieldVos();
            int index = 0;
            for (TableFieldVo tableFieldVo : fieldVos) {
                String fieldName = tableFieldVo.getFieldName();
                TableField tableField = tableFieldQry.selectByTableIdAndName(table.getId(), fieldName);
                if (!predicate.test(tableField)) {
                    tableField =TableField.builder()
                            .fieldComment(tableFieldVo.getFieldComment())
                            .fieldName(tableFieldVo.getFieldName())
                            .fieldType(tableFieldVo.getFieldType())
                            .tableId(table.getId())
                            .fieldDefault(tableFieldVo.getFieldDefault())
                            .autoFill(AutoFillEnum.DEFAULT.toString())
                            .primaryPk(tableFieldVo.getPrimaryPk())
                            .sort(index++)
                            .build();
                    tableFieldCmd.saveDomainObj(tableField);
                }
            }
        });
    }

    public void delete(Long tableId) {
        Predicate<Object> predicate = x -> x == null;
        Table table = tableGateway.selectById(tableId);
        UserDto userDto = AuthUtils.getUserDto();
        if (predicate.test(table)) {
            throw new BizException(ResultCodeEnum.TABLE_NOT_EXIST);
        }
        if (!table.getUser().getId().equals(userDto.getId())) {
            throw new BizException(ResultCodeEnum.NO_RIGHT);
        }
        tableFieldCmd.remove(tableId);
        tableGateway.deleteDomainObj(tableId);
    }

    @Async("threadPoolTaskExecutor")
    public void syncTable(UserDto user, List<String> tableNameList, Long datasourceId) {
        Datasource datasource = datasourceQry.selectObjectById(datasourceId);
        List<Table> tables = tableGateway.selectListByName(user.getId(), datasourceId, tableNameList);
        List<TableVo> list = new ArrayList<>();
        for (Table table: tables) {
            this.delete(table.getId());
            TableVo tableVo = datasource.getTableVo(table.getTableName());
            list.add(tableVo);
        }
        this.batchSave(list, user);
    }
}
