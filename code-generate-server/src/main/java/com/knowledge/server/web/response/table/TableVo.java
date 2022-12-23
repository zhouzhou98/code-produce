package com.knowledge.server.web.response.table;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TableVo {
    private String tableName;
    private String comment;
    private Long datasourceId;
    public TableVo(String tableName, String comment, Long datasourceId) {
        this.tableName = tableName;
        this.comment = comment;
        this.datasourceId = datasourceId;
    }
    private List<TableFieldVo> fieldVos;
}
