package com.knowledge.server.common;

import cn.hutool.core.io.IoUtil;
import com.knowledge.common.web.exception.BizException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Map;

public class FreeMarkerUtils {
//    private static Template getTemplate(String template_path,String templateFileName){
//        Configuration configuration = new Configuration();
//        Template template =null;
//        try {
//            configuration.setDirectoryForTemplateLoading(new File(template_path));
//            configuration.setObjectWrapper(new DefaultObjectWrapper());
//            configuration.setDefaultEncoding("UTF-8");   //设置编码
//            //模板文件
//            template=configuration.getTemplate(templateFileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return template;
//    }
//
//    public static String generatorFile(Map<String,Object> input, String template_path, String templateFileName, String savePath, String fileName) {
//        Template template=getTemplate(template_path,templateFileName);
//        if (template == null) {
//            throw new BizException("模板不能为空");
//        }
//        File file = new File(savePath + "/" + input.get("packagePath")+ "/" + input.get("sub")) ;
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        Writer writer = null;
//        try {
//            writer = new OutputStreamWriter(new FileOutputStream(savePath + "/" + input.get("packagePath") + "/" + input.get("sub") + "/"+ fileName), "UTF-8");
//            template.process(input, writer);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String content = writer.toString();
//        IoUtil.close(writer);
//        return content;
//    }
private static Template getTemplate(String templatePath,String templateFileName){
    Template template =null;
    try {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        template = cfg.getTemplate(templateFileName);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return template;
}

    public static String generatorFile(Map<String,Object> input, String templatePath, String templateFileName, String savePath, String fileName) {
        Template template=getTemplate(templatePath, templateFileName);
        if (template == null) {
            throw new BizException("模板不能为空");
        }
        String packagePath = savePath + "/" + input.get("packagePath")+ "/" + input.get("sub");
        File file = new File(packagePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(packagePath + "/"+ fileName), "UTF-8");
            template.process(input, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = writer.toString();
        IoUtil.close(writer);
        return content;
    }
}
