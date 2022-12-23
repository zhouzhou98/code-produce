package com.knowledge.server.domain.generate.generator;

import com.knowledge.server.domain.product.Project;
import com.knowledge.server.domain.table.Table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaGenetator extends BaseGenerator {
    private List<String> pathList = Arrays.asList(
            "templates/java/Domain.java.ftl",
            "templates/java/Mapper.java.ftl",
            "templates/mapper/Mapper.xml.ftl"
    );
    private Map<String, String> extMap = new HashMap<String, String>() {{
        put("templates/java/Domain.java.ftl", ".java");
        put("templates/java/Mapper.java.ftl", "Mapper.java");
        put("templates/mapper/Mapper.xml.ftl", "Mapper.xml");
    }};

    private Map<String, String> fileMap = new HashMap<String, String>() {{
        put("templates/java/Domain.java.ftl", "domain");
        put("templates/java/Mapper.java.ftl", "mapper");
        put("templates/mapper/Mapper.xml.ftl", "mapper");
    }};

    private Map<String, String> templateNameMap = new HashMap<String, String>() {{
        put("templates/java/Domain.java.ftl", "Domain.java.ftl");
        put("templates/java/Mapper.java.ftl", "Mapper.java.ftl");
        put("templates/mapper/Mapper.xml.ftl", "Mapper.xml.ftl");
    }};
    public JavaGenetator(List<Table> tables, Project project) {
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
    protected String getTemplatesName(String key) {
        return this.templateNameMap.get(key);
    }

    @Override
    protected String getFilePath(String key) {
        return this.fileMap.get(key);
    }
}
