package com.knowledge.server.domain.generate.generator;

import com.knowledge.server.domain.product.Project;
import com.knowledge.server.domain.table.Table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypescriptGenerator extends BaseGenerator {
    private List<String> pathList = Arrays.asList(
      "templates/typescript/Gateway.ts.ftl",
      "templates/typescript/Model.ts.ftl"
    );

    private Map<String, String> extMap = new HashMap<String, String>() {{
        put("templates/typescript/Gateway.ts.ftl", "Gateway.ts");
        put("templates/typescript/Model.ts.ftl", "Model.ts");
    }};

    private Map<String, String> fileMap = new HashMap<String, String>() {{
        put("templates/typescript/Gateway.ts.ftl", "gateway");
        put("templates/typescript/Model.ts.ftl", "model");
    }};


    private Map<String, String> templateNameMap = new HashMap<String, String>() {{
        put("templates/typescript/Gateway.ts.ftl", "Gateway.ts.ftl");
        put("templates/typescript/Model.ts.ftl", "Model.ts.ftl");
    }};
    public TypescriptGenerator(List<Table> tables, Project project) {
        super(tables, project);
    }

    @Override
    protected List<String> getPathList() {
        return this.pathList;
    }

    @Override
    protected String getExt(String key) {
        return this.extMap.get(key);
    }

    @Override
    protected String getFilePath(String key) {
        return this.fileMap.get(key);
    }

    @Override
    protected String getTemplatesName(String key) {
        return this.templateNameMap.get(key);
    }
}
