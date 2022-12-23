package com.knowledge.server.web.request.table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class GenerateDataRequest {
    @NotNull(message = "表明不能为空")
    public String tableName;

    @NotNull(message = "模拟条数不能为空")
    public Integer fakerNum;

    @NotNull
    @Min(value = 1, message = "字段不存在")
    public List<GenerateDataField> fieldList;
}
