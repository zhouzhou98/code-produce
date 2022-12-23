package com.knowledge.server.web.controller;

import com.knowledge.core.dto.UserDto;
import com.knowledge.core.result.Result;
import com.knowledge.server.app.cmd.TableCmd;
import com.knowledge.server.app.qry.TableFieldQry;
import com.knowledge.server.app.qry.TableQry;
import com.knowledge.server.common.AuthUtils;
import com.knowledge.server.web.query.QueryParams;
import com.knowledge.server.web.response.PageResponse;
import com.knowledge.server.web.response.table.TableResp;
import com.knowledge.server.web.response.table.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/table")
public class TableController {
    @Autowired
    private TableQry tableQry;
    @Autowired
    private TableCmd tableCmd;

    @Autowired
    private TableFieldQry tableFieldQry;
    @PostMapping("/page")
    public Result<PageResponse<TableResp>> page(@RequestBody @Validated QueryParams params) {
        Integer limit = params.limit;
        Integer page = params.page;
        Map<String, Object> queryParams =  params.queryParams != null ? params.queryParams : new HashMap<>();;
        PageResponse<TableResp> response = tableQry.getPage(page, limit, queryParams);
        return Result.success(response);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody List<TableVo> list) {
        UserDto user = AuthUtils.getUserDto();
        tableCmd.batchSave(list, user);
        return Result.success();
    }

    @GetMapping("/get/tableFields/{tableId}")
    public Result<List<Map<String, Object>>> getTableFields(@PathVariable("tableId") Long tableId) {
        List<Map<String, Object>> list = tableFieldQry.getFieldsList(tableId);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{tableId}")
    public Result<String> delete(@PathVariable("tableId") Long tableId) {
        tableCmd.delete(tableId);
        return Result.success();
    }

    @PutMapping("/sync/{datasourceId}")
    public Result<String> sync(@PathVariable("datasourceId") Long datasourceId, @RequestBody List<String> tableNameList) {
        UserDto user = AuthUtils.getUserDto();
        tableCmd.syncTable(user, tableNameList, datasourceId);
        return Result.success();
    }
}
