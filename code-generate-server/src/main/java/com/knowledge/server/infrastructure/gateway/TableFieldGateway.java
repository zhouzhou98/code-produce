package com.knowledge.server.infrastructure.gateway;

import com.knowledge.server.domain.table.TableField;
import com.knowledge.server.entity.model.TableFieldModel;

import java.util.List;

public interface TableFieldGateway extends BaseGateway<TableField, TableFieldModel> {
    List<TableField> getFieldsList(Long tableId);

    TableField selectByTableIdAndName(Long tableId, String fieldName);

    void deleteTableField(Long tableId);
}
