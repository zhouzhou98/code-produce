package com.knowledge.server.app.qry;

import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.server.common.AuthUtils;
import com.knowledge.server.domain.datasource.Datasource;
import com.knowledge.server.domain.datasource.query.QueryRunnerI;
import com.knowledge.server.infrastructure.gateway.DatasourceGateway;
import com.knowledge.server.web.response.PageResponse;
import com.knowledge.server.web.response.table.TableFieldVo;
import com.knowledge.server.web.response.table.TableVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DatasourceQry {
    @Autowired
    private DatasourceGateway datasourceGateway;
    public List<Map<String, Object>> getList() {
        UserDto userDto = AuthUtils.getUserDto();
        List<Datasource> list = datasourceGateway.selectListByUserId(userDto.getId());
        return list.stream().map(item -> {
            return item.toJSON();
        }).collect(Collectors.toList());
    }

    public PageResponse<Map<String, Object>> getPage(Integer pageNum, Integer pageSize, Map<String, Object> queryParams) {
        UserDto userDto = AuthUtils.getUserDto();

        queryParams.put("userId", userDto.getId());
        PageResponse<Map<String, Object>> response = datasourceGateway.selectPageResult(pageNum, pageSize, queryParams);
        return response;
    }

    public Boolean test(Long id) {
        Predicate<Datasource> predicate = x -> x == null;
        Datasource datasource = datasourceGateway.selectById(id);
        if (predicate.test(datasource)) {
            throw new BizException(ResultCodeEnum.DATASOURCE_NOT_FOUND);
        }
        UserDto userDto = AuthUtils.getUserDto();
        if (!datasource.getUser().getId().equals(userDto.getId())) {
            throw new BizException(ResultCodeEnum.NO_RIGHT);
        }
        try {
            QueryRunnerI runner = datasource.getQueryRunner();
            runner.getConnection(datasource.getConUrl(), datasource.getUsername(), datasource.getPassword());
            return true;
        } catch (Exception e) {
            log.error("unknown exception {}", e);
            return false;
        }
    }

    public Datasource selectObjectById(Long id) {
        return datasourceGateway.selectById(id);
    }

    public List<TableVo> getTableList(Long id) {
        Predicate<Datasource> predicate = x -> x == null;
        Datasource datasource = datasourceGateway.selectById(id);
        if (predicate.test(datasource)) {
            throw new BizException(ResultCodeEnum.DATASOURCE_NOT_FOUND);
        }
        UserDto userDto = AuthUtils.getUserDto();
        if (!datasource.getUser().getId().equals(userDto.getId())) {
            throw new BizException(ResultCodeEnum.NO_RIGHT);
        }
        List<TableVo> tableList = datasource.getTableList();
        return tableList;
    }

    public TableVo getTable(Long id, String name) {
        Predicate<Object> predicate = x -> x == null;
        Datasource datasource = datasourceGateway.selectById(id);
        if (predicate.test(datasource)) {
            throw new BizException(ResultCodeEnum.DATASOURCE_NOT_FOUND);
        }
        UserDto userDto = AuthUtils.getUserDto();
        if (!datasource.getUser().getId().equals(userDto.getId())) {
            throw new BizException(ResultCodeEnum.NO_RIGHT);
        }
        return datasource.getTableVo(name);
    }

    public List<TableFieldVo> getTableFields(Long id, String name) {
        Predicate<Object> predicate = x -> x == null;
        Datasource datasource = datasourceGateway.selectById(id);
        if (predicate.test(datasource)) {
            throw new BizException(ResultCodeEnum.DATASOURCE_NOT_FOUND);
        }
        UserDto userDto = AuthUtils.getUserDto();
        if (!datasource.getUser().getId().equals(userDto.getId())) {
            throw new BizException(ResultCodeEnum.NO_RIGHT);
        }
        List<TableFieldVo> tableFields = datasource.getTableFields(name);
        return tableFields;
    }
}
