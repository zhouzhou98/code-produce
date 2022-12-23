package com.knowledge.server.domain.generate.generator;

import com.knowledge.core.enums.GeneratorEnum;
import com.knowledge.server.domain.product.Project;
import com.knowledge.server.domain.table.Table;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GeneratorFactory {
    public static final Map<GeneratorEnum, String> map = new HashMap<GeneratorEnum, String>() {{
        put(GeneratorEnum.JAVA, "com.knowledge.server.domain.generate.generator.JavaGenetator");
        put(GeneratorEnum.TYPESCRIPT, "com.knowledge.server.domain.generate.generator.TypescriptGenerator");
    }};

    private GeneratorFactory() {}

    public static LanguageGenerator getGenerator(List<Table> tables, Project project, GeneratorEnum generatorEnum) {
        GeneratorEnum anEnum = Optional.ofNullable(generatorEnum).orElse(GeneratorEnum.JAVA);
        String className = map.get(anEnum);
        try {
            Class<?> clazz = Class.forName(className);
            Constructor constructor = clazz.getConstructor(List.class, Project.class);
            return  (LanguageGenerator) constructor.newInstance(tables, project);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
