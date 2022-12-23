package com.knowledge.server.web.controller;

import com.knowledge.core.result.Result;
import com.knowledge.server.app.qry.GenerateQry;
import com.knowledge.server.web.request.table.GenerateDataField;
import com.knowledge.server.web.request.table.GenerateDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/generate")
public class GeneratorController {
    @Autowired
    private GenerateQry generateQry;
    @PostMapping("/code/{datasourceId}/{projectId}/{type}")
    public Result<String> generate(
            @RequestBody List<Long> tableIds,
            @PathVariable("type") String type,
            @PathVariable("datasourceId") Long datasourceId,
            @PathVariable("projectId") Long projectId
    ) {
        try {
            String url = generateQry.download(datasourceId, tableIds, projectId, type);
            return Result.success(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.failed();
    }

    @PostMapping("/code/data")
    public Result<Map<String, Object>> generateData(@RequestBody GenerateDataRequest req) {
        Integer fakerNum = req.fakerNum;
        List<GenerateDataField> fieldList = req.fieldList;
        String tableName = req.tableName;
        Map<String, Object> map = generateQry.generateDataJSON(fakerNum, tableName, fieldList);
        return Result.success(map);
    }
}
