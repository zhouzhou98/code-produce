package com.knowledge.common.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * oss 配置
 * @author suyuzhou
 * @date 2022-07-15
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Configuration
@Data
public class OssConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketname;
    private String dir;
}
