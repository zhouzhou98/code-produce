package com.knowledge.common.oss.manager.impl;

import com.knowledge.common.oss.client.OssClient;
import com.knowledge.common.oss.config.OssConfig;
import com.knowledge.common.oss.manager.OssManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * oss manager
 * @author suyuzhou
 * @date 2022-07-15
 */
@Component
public class OssManagerImpl implements OssManager {
    @Autowired
    private OssConfig ossConfig;

    /**
     * 构建client
     */
    @Override
    public OssClient buildClient() {
        return new OssClient(ossConfig.getBucketname(), ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(), ossConfig.getDir());
    }
}
