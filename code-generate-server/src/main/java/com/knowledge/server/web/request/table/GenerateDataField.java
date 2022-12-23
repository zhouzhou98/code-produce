package com.knowledge.server.web.request.table;

import lombok.Data;

@Data
public class GenerateDataField {
    public String fieldName;
    public String fieldType;
    public String fieldComment;
    public Integer primaryPk;
    public String fieldDefault;
    public String fakerType;
    public String fakerParam;

    public String getValueStr(Object value) {
        if (value == null) {
            return "''";
        }
        switch (fieldType) {
            case "datetime":
            case "date":
            case "char":
            case "varchar":
            case "tinytext":
            case "text":
            case "mediumtext":
            case "longtext":
                return String.format("'%s'", value);
            default:
                return String.valueOf(value);
        }
    }
}
