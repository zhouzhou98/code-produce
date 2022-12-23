package com.knowledge.core.result;
/**
 * result通用类
 * @author suyuzhou
 * @date 2022-07-15
 */
public interface IResultCode {
    /**
     * 返回码
     */
    Integer getCode();

    /**
     * 返回msg
     */
    String getMsg();
}
