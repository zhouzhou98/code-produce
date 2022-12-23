package com.knowledge.server.web.query;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class QueryParams {
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    public Integer page;

    @NotNull(message = "每页条数不能为空")
    @Range(min = 10, max = 1000, message = "每页条数，取值范围 10-1000")
    public Integer limit;

    public Map<String, Object> queryParams;
}
