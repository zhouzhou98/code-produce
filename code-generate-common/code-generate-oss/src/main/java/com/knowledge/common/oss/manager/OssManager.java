package com.knowledge.common.oss.manager;
import com.knowledge.common.oss.client.OssClient;

/**
 * oss manager 接口层
 * @author suyuzhou
 * @date 2022-07-15
 */
public interface OssManager {
    /**
     * 构建oss client
     */
    OssClient buildClient();


}
