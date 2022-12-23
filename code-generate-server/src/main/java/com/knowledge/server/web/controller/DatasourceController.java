package com.knowledge.server.web.controller;

import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.core.result.Result;
import com.knowledge.server.app.cmd.DatasourceCmd;
import com.knowledge.server.app.qry.DatasourceQry;
import com.knowledge.server.web.query.QueryParams;
import com.knowledge.server.web.request.datasource.DatasourceAddOrUpdRequest;
import com.knowledge.server.web.request.validate.DatasourceAddValidateGroup;
import com.knowledge.server.web.response.PageResponse;
import com.knowledge.server.web.response.table.TableFieldVo;
import com.knowledge.server.web.response.table.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api/v1/datasouce")
public class DatasourceController {
    @Autowired
    private DatasourceCmd datasourceCmd;
    @Autowired
    private DatasourceQry datasourceQry;
    @PostMapping("/add")
    public Result<String> add(@Validated(DatasourceAddValidateGroup.class) @RequestBody DatasourceAddOrUpdRequest req) {
        String conName = req.conName;
        String dbType = req.dbType;
        String conUrl = req.conUrl;
        String username = req.username;
        String password = req.password;
        int rows = datasourceCmd.add(username, password, conName, dbType, conUrl);
        if (rows > 0) {
            return Result.success();
        }
        return Result.failed(ResultCodeEnum.ADD_FAIL);
    }
    @PutMapping("/update/{id}")
    public Result<String> update(@PathVariable("id") Long id, @Validated @RequestBody DatasourceAddOrUpdRequest req) {
        String conName = req.conName;
        String dbType = req.dbType;
        String conUrl = req.conUrl;
        String username = req.username;
        String password = req.password;
        int rows = datasourceCmd.update(id, username, password, conName, dbType, conUrl);
        if (rows > 0) {
            return Result.success();
        }
        return Result.failed(ResultCodeEnum.UPDATE_FAIL);
    }
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable("id") Long id) {
        int rows = datasourceCmd.delete(id);
        if (rows > 0) {
            return Result.success();
        }
        return Result.failed(ResultCodeEnum.DELETE_FAIL);
    }

    @DeleteMapping("/batchDelete")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        int rows = datasourceCmd.batchDelete(ids);
        if (rows > 0) {
            return Result.success();
        }
        return Result.failed(ResultCodeEnum.DELETE_FAIL);
    }
    @GetMapping("/list")
    public Result<List<Map<String, Object>>> list() {
        List<Map<String, Object>> res = datasourceQry.getList();
        return Result.success(res);
    }
    @PostMapping("/page")
    public Result<PageResponse<Map<String, Object>>> page(@RequestBody @Validated QueryParams params) {
        Integer limit = params.limit;
        Integer page = params.page;
        Map<String, Object> queryParams = params.queryParams != null ? params.queryParams : new HashMap<>();
        PageResponse<Map<String, Object>> response = datasourceQry.getPage(page, limit, queryParams);
        return Result.success(response);
    }
    @GetMapping("/test/{id}")
    public Result<String> test(@PathVariable("id") Long id) {
        Boolean result = datasourceQry.test(id);
        if (result) {
            return Result.success();
        }
        return Result.failed("连接失败，请查看配置是否有无错误");
    }

    @GetMapping("/table/list/{id}")
    public Result<List<TableVo>> tableList(@PathVariable("id") Long id) {
        List<TableVo> list = datasourceQry.getTableList(id);
        return Result.success(list);
    }

    @GetMapping("/table/info/{id}/{name}")
    public Result<TableVo> getTable(@PathVariable("id") Long id, @PathVariable("name") String name) {
        TableVo tableVo = datasourceQry.getTable(id, name);
        Predicate<Object> predicate = x -> x == null;
        if (predicate.test(tableVo)) {
            return Result.failed("获取失败，请查看配置");
        }
        return Result.success(tableVo);
    }

    @GetMapping("/table/field/{id}/{name}")
    public Result<List<TableFieldVo>> getTableFields(@PathVariable("id") Long id, @PathVariable("name") String name) {
        List<TableFieldVo> list = datasourceQry.getTableFields(id, name);
        Predicate<List<TableFieldVo>> predicate = x -> x == null || x.size() <= 0;
        if (predicate.test(list)) {
            return Result.failed("获取失败，请查看配置");
        }
        return Result.success(list);
    }
}
