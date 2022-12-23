package com.knowledge.server.infrastructure.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.server.app.mapper.TableModelMapper;
import com.knowledge.server.app.qry.DatasourceQry;
import com.knowledge.server.app.qry.TableFieldQry;
import com.knowledge.server.app.qry.UserQry;
import com.knowledge.server.domain.datasource.Datasource;
import com.knowledge.server.domain.table.Table;
import com.knowledge.server.domain.table.TableField;
import com.knowledge.server.entity.model.TableModel;
import com.knowledge.server.infrastructure.gateway.TableGateway;
import com.knowledge.server.web.response.PageResponse;
import com.knowledge.server.web.response.table.TableFieldVo;
import com.knowledge.server.web.response.table.TableResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class TableGatewayImpl extends BaseGatewayImpl<TableModel, TableModelMapper, Table> implements TableGateway {
    @Autowired
    private UserQry userQry;
    @Autowired
    private DatasourceQry datasourceQry;
    @Autowired
    private TableFieldQry tableFieldQry;
    @Override
    public QueryWrapper<TableModel> doGetWrapper(Map<String, Object> queryParams) {
        Predicate<Object> predicate = x -> x != null;
        if (!predicate.test(queryParams.get("userId"))) {
            throw new BizException(ResultCodeEnum.PARAM_ERROR);
        }
        QueryWrapper<TableModel> wrapper = new QueryWrapper<>();
        wrapper.eq(predicate.test(queryParams.get("userId")) , "user_id", Long.valueOf(queryParams.get("userId").toString()));
        wrapper.eq(predicate.test(queryParams.get("datasourceId")), "datasource_id", predicate.test(queryParams.get("datasourceId")) ? Long.valueOf(queryParams.get("datasourceId").toString()) : null);
        wrapper.like(predicate.test(queryParams.get("tableName")), "table_name", predicate.test(queryParams.get("tableName")) ? queryParams.get("tableName").toString() : null);
        return wrapper;
    }

    @Override
    Table convertToDomainObject(TableModel dataModel) {
        Predicate<TableModel> predicate = x -> x == null;
        if (predicate.test(dataModel)) {
            return null;
        }
        UserDto userDto = userQry.getUserAuthDto(dataModel.getUserId());
        Datasource datasource = datasourceQry.selectObjectById(dataModel.getDatasourceId());
        List<TableField> fieldsList = tableFieldQry.getTableFieldsList(dataModel.getId());
        Table table = Table.builder()
                .user(userDto)
                .datasource(datasource)
                .tableName(dataModel.getTableName())
                .comment(dataModel.getComment())
                .tableFieldList(fieldsList)
                .build();
        table.setId(dataModel.getId());
        table.setCreatedAt(dataModel.getCreatedAt());
        table.setUpdatedAt(dataModel.getUpdatedAt());
        table.setDataVersion(dataModel.getDataVersion());
        return table;
    }

    @Override
    TableModel convertToDataModel(Table domainObject) {
        Predicate<Table> predicate = x -> x == null;
        if (predicate.test(domainObject)) {
            return null;
        }
        TableModel model = TableModel.builder()
                .datasourceId(domainObject.getDatasource().getId())
                .userId(domainObject.getUser().getId())
                .comment(domainObject.getComment())
                .tableName(domainObject.getTableName())
                .build();
        model.setId(domainObject.getId());
        model.setCreatedAt(domainObject.getCreatedAt());
        model.setUpdatedAt(domainObject.getUpdatedAt());
        model.setDataVersion(domainObject.getDataVersion());
        return model;
    }

    @Override
    public PageResponse<TableResp> selectPageResult(Integer pageNum, Integer pageSize, Map<String, Object> queryParams) {
        IPage<TableModel> page = this.getPage(pageNum, pageSize);
        Predicate<Table> predicate = x -> x != null;
        QueryWrapper<TableModel> wrapper = this.getWrapper(queryParams);
        IPage<TableModel> iPage = this.baseMapper.selectPage(page, wrapper);
        List<TableResp> tableRespList = iPage.getRecords().stream().map(item -> {
            return this.convertToDomainObject(item);
        }).filter(item -> {
            return predicate.test(item);
        }).map(item -> {
            TableResp tableResp = new TableResp(item.getId(), item.getTableName(), item.getComment(), item.getDatasource().getId());
            List<TableField> tableFieldsList = tableFieldQry.getTableFieldsList(item.getId());
            List<TableFieldVo> list = tableFieldsList.stream().map(tableField -> {
                return new TableFieldVo(item.getDatasource().getId(), item.getTableName(), tableField.getFieldName(), tableField.getFieldType(), item.getComment(), tableField.getPrimaryPk(), tableField.getFieldDefault());
            }).collect(Collectors.toList());
            tableResp.setFieldVos(list);
            return tableResp;
        }).collect(Collectors.toList());
        return new PageResponse<>(tableRespList, page.getTotal());
    }

    @Override
    public Table selectByDatasourceAndName(Long datasourceId, String tableName, Long userId) {
        LambdaQueryWrapper<TableModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TableModel::getDatasourceId, datasourceId);
        wrapper.eq(TableModel::getTableName, tableName);
        wrapper.eq(TableModel::getUserId, userId);
        TableModel tableModel = this.baseMapper.selectOne(wrapper);
        return this.convertToDomainObject(tableModel);
    }

    @Override
    public List<Table> selectListByName(Long userId, Long datasourceId, List<String> tableNameList) {
        LambdaQueryWrapper<TableModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TableModel::getDatasourceId, datasourceId);
        wrapper.in(TableModel::getTableName, tableNameList);
        Predicate<Table> predicate = x -> x != null;
        return this.baseMapper.selectList(wrapper).stream().map(item -> {
            return this.convertToDomainObject(item);
        }).filter(item -> {
            return predicate.test(item);
        }).collect(Collectors.toList());
    }
}
