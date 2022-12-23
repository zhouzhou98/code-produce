package com.knowledge.server.infrastructure.gateway;

import com.knowledge.server.domain.datasource.Datasource;
import com.knowledge.server.entity.model.DatasourceModel;
import com.knowledge.server.web.response.PageResponse;

import java.util.List;
import java.util.Map;

public interface DatasourceGateway extends BaseGateway<Datasource, DatasourceModel>  {
    List<Datasource> selectListByUserId(Long id);

    PageResponse<Map<String, Object>> selectPageResult(Integer pageNum, Integer pageSize, Map<String, Object> queryParams);

    Datasource selectDatasource(Long id, String conName, String conUrl, String dbType, String username, String password);
}
