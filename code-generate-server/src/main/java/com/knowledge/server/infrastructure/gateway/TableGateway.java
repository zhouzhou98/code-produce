package com.knowledge.server.infrastructure.gateway;

import com.knowledge.server.domain.table.Table;
import com.knowledge.server.entity.model.TableModel;
import com.knowledge.server.web.response.PageResponse;
import com.knowledge.server.web.response.table.TableResp;

import java.util.List;
import java.util.Map;

public interface TableGateway extends BaseGateway<Table, TableModel>{
    PageResponse<TableResp> selectPageResult(Integer pageNum, Integer pageSize, Map<String, Object> queryParams);

    Table selectByDatasourceAndName(Long datasourceId, String tableName, Long userId);

    List<Table> selectListByName(Long userId, Long datasourceId, List<String> tableNameList);
}
