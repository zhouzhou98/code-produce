package com.knowledge.server.domain.generate.generator;

import cn.hutool.core.io.IoUtil;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

@Slf4j
@Component
public class FreeMarker {
    @Autowired
    private Configuration configuration;

    public String generatorFile(Map<String,Object> input, String template_path, String templateFileName, String savePath, String fileName) throws Exception {
        Template template = configuration.getTemplate(templateFileName);
        log.info("path:" + savePath + "/" + input.get("packagePath")+ "/" + input.get("sub"));
        File file = new File(savePath + "/" + input.get("packagePath")+ "/" + input.get("sub")) ;
        if (!file.exists()) {
            file.mkdirs();
        }
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(savePath + "/" + input.get("packagePath") + "/" + input.get("sub") + "/"+ fileName), "UTF-8");
            template.process(input, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = writer.toString();
        log.info("content:" + content);
        IoUtil.close(writer);
        return content;
    }
}
