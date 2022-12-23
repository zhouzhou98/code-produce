package com.knowledge.server.domain.generate.generator;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.knowledge.common.mybatis.utils.IdUtils;
import com.knowledge.common.oss.client.OssClient;
import com.knowledge.common.oss.manager.OssManager;
import com.knowledge.common.web.utils.SpringContextHooks;
import com.knowledge.server.app.qry.FieldTypeQry;
import com.knowledge.server.common.FreeMarkerUtils;
import com.knowledge.server.domain.generate.GenerateTableField;
import com.knowledge.server.domain.product.Project;
import com.knowledge.server.domain.table.Table;
import com.knowledge.server.entity.dto.FieldTypeDto;
import com.knowledge.server.infrastructure.manager.ZipManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Slf4j
public abstract class BaseGenerator implements LanguageGenerator {
    private static final FieldTypeQry fieldTypeQry = SpringContextHooks.getBean(FieldTypeQry.class);
    private static final OssManager ossManager = SpringContextHooks.getBean(OssManager.class);
    private static final ZipManager zipManager = SpringContextHooks.getBean(ZipManager.class);
    private List<Table> tables;
    private Project project;
    public BaseGenerator(List<Table> tables, Project project) {
        this.project = project;
        this.tables = tables;
    }

    private Map<String, Object> toJSON(Table table) {
        Map<String, FieldTypeDto> fieldTypeMap = fieldTypeQry.fieldTypeMap();
        Predicate<Object> predicate = x -> x == null;
        String projectPackage = this.project.getProjectPackage();
        String moduleName = table.getModuleName(projectPackage);
        List<GenerateTableField> fieldList = table.getTableFieldList().stream().map(item -> {
            FieldTypeDto type = fieldTypeMap.get(item.getFieldType().toLowerCase());
            String attrType = "Object";
            String jsType = "any";
            if (!predicate.test(type)) {
                attrType = type.getJavaType();
                jsType = type.getJsType();
            }
            return GenerateTableField.builder()
                    .attrName(NamingCase.toCamelCase(item.getFieldName()))
                    .attrType(attrType)
                    .jsAttrType(jsType)
                    .autoFill(item.getAutoFill())
                    .fieldComment(item.getFieldComment())
                    .fieldType(item.getFieldType())
                    .fieldName(item.getFieldName())
                    .packageName(type.getJavaPackageName())
                    .primaryPk(item.getPrimaryPk() == 1 ? true : false)
                    .sort(item.getSort())
                    .tableId(item.getTableId())
                    .build();
        }).filter(item -> {
            return item != null;
        }).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("dbType", table.getDatasource().getDbType());
        map.put("package", projectPackage);
        map.put("packagePath", projectPackage.replace(".", File.separator));
        map.put("version", project.getVersion());
        map.put("moduleName", moduleName);
        map.put("ModuleName", StrUtil.upperFirst(moduleName));
        map.put("functionName", table.getFunctionName());
        map.put("FunctionName", StrUtil.upperFirst(table.getFunctionName()));
        map.put("fieldList", fieldList);
        map.put("tableName", table.getTableName());
        map.put("tableComment", table.getComment());
        map.put("className", StrUtil.lowerFirst(table.getClassName()));
        map.put("ClassName", table.getClassName());
        map.put("author", project.getAuthor());
        map.put("email", project.getEmail());
        return map;
    }

    protected abstract List<String> getPathList();

    protected abstract String getExt(String key);

    protected abstract String getTemplatesName(String key);
    protected abstract String getFilePath(String key);
//    @Override
//    public String download() throws FileNotFoundException {
//        IdUtils idUtils = new IdUtils();
//        String templatePath = Thread.currentThread().getContextClassLoader().
//                getResource("").getPath() + "templates";
//        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "root" + '/' + idUtils.nextId();
//        OssClient ossClient = ossManager.buildClient();
//        for (Table table: tables) {
//            Map<String, Object> map = this.toJSON(table);
//            for (String path : this.getPathList()) {
//                String ext = this.getName(path);
//                String filePath = this.getFilePath(path);
//                String name = table.getClassName().concat(ext);
//                map.put("sub", filePath);
//                log.info("templatePath:" + templatePath);
//                log.info("rootPath:" + rootPath);
//                FreeMarkerUtils.generatorFile(map, templatePath, path, rootPath, name);
//            }
//        }
//        String zipName = idUtils.nextId() + ".zip";
//        String zipPath =  rootPath + "/" + zipName;
//        File srcFile = new File(rootPath);
//        File targetFile = new File(zipPath);
//        zipManager.zipFiles(srcFile, targetFile);
//        //根据路径获取刚生成的zip包文件
//        InputStream in = new FileInputStream(targetFile);
//        String url = ossClient.uploadInputStream(in, "code-genrator", "zip");
//        IoUtil.close(in);
//        // 递归删除文件
//        zipManager.deleteFile(srcFile);
//        zipManager.deleteFile(new File(zipPath));
//        return url;
//    }
    @Override
    public String download() throws FileNotFoundException {
        IdUtils idUtils = new IdUtils();
//        String zipPath = "/Users/suyuzhou/project/test/zip/" + idUtils.nextId();
//        String rootPath = "/Users/suyuzhou/project/test/root/" + idUtils.nextId();
//        String templates = "/Users/suyuzhou/project/test/templates";
//        "/Users/suyuzhou/project/test"
        String zipPath = "/opt/java/zip/" + idUtils.nextId();
        File zipFile = new File(zipPath);
        if (!zipFile.exists()) {
            zipFile.mkdirs();
        }
        String rootPath = "/opt/java/root/" + idUtils.nextId();
        String templates = "/opt/java/templates";
        OssClient ossClient = ossManager.buildClient();
        for (Table table: tables) {
            Map<String, Object> map = this.toJSON(table);
            for (String path : this.getPathList()) {

                String ext = this.getExt(path);
                String filePath = this.getFilePath(path);
                String name = table.getClassName().concat(ext);
                String templatesName = this.getTemplatesName(path);
                ClassPathResource resource = new ClassPathResource(path);
                File inModel = new File(templates + "/" + templatesName);
                try {
                    FileUtils.copyInputStreamToFile(resource.getInputStream(), inModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                map.put("sub", filePath);
                log.info("rootPath:" + rootPath);
                FreeMarkerUtils.generatorFile(map, templates, templatesName, rootPath, name);
            }
        }
        String zipName = idUtils.nextId() + ".zip";
        String zipSavePath =  zipPath + "/" + zipName;
        File srcFile = new File(rootPath);
        File targetFile = new File(zipSavePath);
        zipManager.zipFiles(srcFile, targetFile);
        //根据路径获取刚生成的zip包文件
        InputStream in = new FileInputStream(targetFile);
        String url = ossClient.uploadInputStream(in, "code-genrator", "zip");
        IoUtil.close(in);
        // 递归删除文件
        zipManager.deleteFile(srcFile);
        zipManager.deleteFile(new File(zipPath));
        return url;
    }
}
