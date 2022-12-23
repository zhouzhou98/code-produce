package com.knowledge.server.web.response.table;


import lombok.Getter;
import lombok.Setter;

public class TableFieldResp extends TableFieldVo {
    @Getter
    @Setter
    private Long id;

    public TableFieldResp(Long id, Long datasourceId, String tableName, String fieldName, String fieldType, String fieldComment, Integer primaryPk, String fieldDefault) {
        super(datasourceId, tableName, fieldName, fieldType, fieldComment, primaryPk, fieldDefault);
        this.id = id;
    }
}
