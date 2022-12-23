package com.knowledge.server.web.response.table;

import lombok.Data;

import java.util.List;

@Data
public class TableResp {

    private Long id;
    private String tableName;
    private String comment;
    private Long datasourceId;
    public TableResp(Long id, String tableName, String comment, Long datasourceId) {
        this.id = id;
        this.tableName = tableName;
        this.comment = comment;
        this.datasourceId = datasourceId;
    }
    private List<TableFieldVo> fieldVos;
}
