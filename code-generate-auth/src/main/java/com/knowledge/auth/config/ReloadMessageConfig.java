package com.knowledge.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ReloadMessageConfig {

    /**
     * 加载中文的认证提示信息
     * @return
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages_zh_CN"); // 不要有后缀名.properties
        return messageSource;
    }

}
