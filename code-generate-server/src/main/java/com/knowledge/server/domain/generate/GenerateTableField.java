package com.knowledge.server.domain.generate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateTableField {
    private Long tableId;
    private String fieldName;
    private String fieldType;
    private String fieldComment;
    private String attrName;
    private String attrType;
    private String jsAttrType;
    private String packageName;
    private Integer sort;
    private String autoFill;
    private Boolean primaryPk;
}
