package com.knowledge.server.domain.table;

import com.knowledge.common.mybatis.model.BaseObjEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableField extends BaseObjEntity {
    private Long tableId;
    private String fieldName;
    private String fieldType;
    private String fieldComment;
    private Integer sort;
    private String autoFill;
    private Integer primaryPk;
    private String fieldDefault;
    public Map<String, Object> toJSON() {
        Map<String, Object> map = new HashMap<>();
        map.put("tableId", tableId);
        map.put("fieldName", fieldName);
        map.put("fieldType", fieldType);
        map.put("fieldComment", fieldComment);
        map.put("sort", sort);
        map.put("autoFill", autoFill);
        map.put("primaryPk", primaryPk);
        map.put("fieldDefault", fieldDefault);
        return map;
    }
}
