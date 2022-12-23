package com.knowledge.server.infrastructure.gateway;
import com.knowledge.server.domain.fieldtype.FieldType;
import com.knowledge.server.entity.model.FieldTypeModel;
import com.knowledge.server.web.response.PageResponse;

import java.util.Map;

public interface FieldTypeGateway extends BaseGateway<FieldType, FieldTypeModel> {
    PageResponse<FieldType> selectPageResult(Integer pageNum, Integer pageSize, Map<String, Object> queryParams);
}
