package com.knowledge.server.web.controller;

import com.knowledge.core.result.Result;
import com.knowledge.server.app.qry.FieldTypeQry;
import com.knowledge.server.domain.fieldtype.FieldType;
import com.knowledge.server.entity.dto.FieldTypeDto;
import com.knowledge.server.web.query.QueryParams;
import com.knowledge.server.web.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/fieldtype")
public class FieldTypeController {
    @Autowired
    private FieldTypeQry fieldTypeQry;
    /**
     * 获取分页数据
     */
    @PostMapping("/page")
    public Result<PageResponse<FieldType>> page(@RequestBody @Validated QueryParams params) {
        try {
            Integer limit = params.limit;
            Integer page = params.page;
            Map<String, Object> queryParams =  params.queryParams != null ? params.queryParams : new HashMap<>();
            PageResponse<FieldType> response = fieldTypeQry.getPage(page, limit, queryParams);
            return Result.success(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/list")
    public Result<List<FieldType>> list() {
        List<FieldType> list = fieldTypeQry.getList();
        return Result.success(list);
    }

    @GetMapping("/cacheList")
    public Result<Map<String, FieldTypeDto>> cacheList() {
        Map<String, FieldTypeDto> map = fieldTypeQry.fieldTypeMap();
        return Result.success(map);
    }
}
