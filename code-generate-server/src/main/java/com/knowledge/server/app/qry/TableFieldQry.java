package com.knowledge.server.app.qry;

import com.knowledge.server.domain.table.TableField;
import com.knowledge.server.infrastructure.gateway.TableFieldGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TableFieldQry {
    @Autowired
    private TableFieldGateway tableFieldGateway;
    public List<Map<String, Object>> getFieldsList(Long tableId) {
        List<TableField> fieldList = tableFieldGateway.getFieldsList(tableId);
        return fieldList.stream().map(item -> {
            return item.toJSON();
        }).collect(Collectors.toList());
    }

    public List<TableField> getTableFieldsList(Long tableId) {
        return tableFieldGateway.getFieldsList(tableId);
    }
    public TableField selectByTableIdAndName(Long id, String fieldName) {
        return tableFieldGateway.selectByTableIdAndName(id, fieldName);
    }
}
