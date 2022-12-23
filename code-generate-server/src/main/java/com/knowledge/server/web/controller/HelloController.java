package com.knowledge.server.web.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.knowledge.common.mybatis.utils.IdUtils;
import com.knowledge.common.oss.client.OssClient;
import com.knowledge.common.oss.manager.OssManager;
import com.knowledge.server.app.qry.FieldTypeQry;
import com.knowledge.server.domain.datasource.Datasource;
import com.knowledge.server.domain.generate.GenerateTableField;
import com.knowledge.server.domain.product.Project;
import com.knowledge.server.domain.table.Table;
import com.knowledge.server.entity.dto.FieldTypeDto;
import com.knowledge.server.infrastructure.gateway.DatasourceGateway;
import com.knowledge.server.infrastructure.gateway.ProjectGateway;
import com.knowledge.server.infrastructure.gateway.TableGateway;
import com.knowledge.server.infrastructure.manager.ZipManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class HelloController {
    @Autowired
    private DatasourceGateway datasourceGateway;
    @Autowired
    private ProjectGateway projectGateway;
    @Autowired
    private TableGateway tableGateway;
    @Autowired
    private FieldTypeQry fieldTypeQry;
    @Autowired
    private OssManager ossManager;
    @Autowired
    private ZipManager zipManager;
    @RequestMapping("/test")
    public String test() throws Exception {
        IdUtils idUtils = new IdUtils();
        String savePath = "/opt/java/root/" + idUtils.nextId();
        ClassPathResource resource = new ClassPathResource("templates/java/Domain.java.ftl");
        File inModel = new File("/opt/java/templates/Domain.java.ftl");
        FileUtils.copyInputStreamToFile(resource.getInputStream(), inModel);
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File("/opt/java/templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        Template template = cfg.getTemplate("Domain.java.ftl");
        Datasource datasource = datasourceGateway.selectById("1604424260049178624");
        Project project = projectGateway.selectById("1594177081851469824");
        Table table = tableGateway.selectById("1604472324688449536");
        Map<String, FieldTypeDto> fieldTypeMap = fieldTypeQry.fieldTypeMap();
        Predicate<Object> predicate = x -> x == null;
        String projectPackage = project.getProjectPackage();
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
        String ext = ".java";
        String filePath = "domain";
        String name = table.getClassName().concat(ext);
        map.put("sub", filePath);
        File file = new File(savePath + "/" + map.get("packagePath")+ "/" + map.get("sub")) ;
        if (!file.exists()) {
            file.mkdirs();
        }
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(savePath + "/" + map.get("packagePath") + "/" + map.get("sub") + "/" + name), "UTF-8");
            template.process(map, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = writer.toString();
        IoUtil.close(writer);
        OssClient ossClient = ossManager.buildClient();
        String zipName = idUtils.nextId() + ".zip";
        String zipPath =  savePath + "/" + zipName;
        File srcFile = new File(savePath);
        File targetFile = new File(zipPath);
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
