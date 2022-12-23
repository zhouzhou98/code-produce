package com.knowledge.server.web.response.table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TableFieldVo {
    private Long datasourceId;
    private String tableName;
    private String fieldName;
    private String fieldType;
    private String fieldComment;
    private String fieldDefault;
    private Integer primaryPk;
    public TableFieldVo(Long datasourceId, String tableName, String fieldName, String fieldType, String fieldComment, Integer primaryPk, String fieldDefault) {
        this.datasourceId = datasourceId;
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.fieldComment = fieldComment;
        this.primaryPk = primaryPk;
        this.fieldDefault = fieldDefault;
    }
}
