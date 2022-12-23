package com.knowledge.server.app.cmd;

import com.knowledge.server.domain.table.TableField;
import com.knowledge.server.infrastructure.gateway.TableFieldGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TableFieldCmd {
    @Autowired
    private TableFieldGateway tableFieldGateway;
    public void saveDomainObj(TableField tableField) {
        tableFieldGateway.saveDomainObj(tableField);
    }

    @Async
    public void remove(Long tableId) {
        tableFieldGateway.deleteTableField(tableId);
    }
}
