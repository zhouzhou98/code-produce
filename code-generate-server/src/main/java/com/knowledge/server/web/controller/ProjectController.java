package com.knowledge.server.web.controller;

import com.knowledge.core.result.Result;
import com.knowledge.server.app.cmd.ProjectCmd;
import com.knowledge.server.app.qry.ProjectQry;
import com.knowledge.server.web.query.QueryParams;
import com.knowledge.server.web.request.project.ProjectAddOrUpdRequest;
import com.knowledge.server.web.request.validate.ProjectAddValidate;
import com.knowledge.server.web.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
public class ProjectController {
    @Autowired
    private ProjectCmd projectCmd;
    @Autowired
    private ProjectQry projectQry;
    @PostMapping("/add")
    public Result<String> add(@Validated(ProjectAddValidate.class) @RequestBody ProjectAddOrUpdRequest req) {
        String author = req.author;
        String email = req.email;
        String projectCode = req.projectCode;
        String projectName = req.projectName;
        String version = req.version;
        String projectPackage = req.projectPackage;
        int row = projectCmd.saveObject(author, email, projectCode, projectName, projectPackage, version);
        if (row > 0) {
            return Result.success();
        }
        return Result.failed("添加工程失败");
    }

    @PutMapping("/update/{id}")
    public Result<String> update(@PathVariable("id") Long id, @Validated @RequestBody ProjectAddOrUpdRequest req) {
        String author = req.author;
        String email = req.email;
        String projectCode = req.projectCode;
        String projectName = req.projectName;
        String version = req.version;
        String projectPackage = req.projectPackage;
        int row = projectCmd.updateObject(id, author, email, projectCode, projectName, projectPackage, version);
        if (row > 0) {
            return Result.success();
        }
        return Result.failed("更新工程失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable("id") Long id) {
        int row = projectCmd.delete(id);
        if (row > 0) {
            return Result.success();
        }
        return Result.failed("删除失败");
    }

    @GetMapping("/list")
    public Result<List<Map<String, Object>>> list() {
        List<Map<String, Object>> list = projectQry.getList();
        return Result.success(list);
    }

    @PostMapping("/page")
    public Result<PageResponse<Map<String, Object>>> page(@RequestBody @Validated QueryParams params) {
        Integer limit = params.limit;
        Integer page = params.page;
        Map<String, Object> queryParams = params.queryParams != null ? params.queryParams : new HashMap<>();
        PageResponse<Map<String, Object>> response = projectQry.getPage(page, limit, queryParams);
        return Result.success(response);
    }
}
