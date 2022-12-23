package com.knowledge.core.converter;

/**
 * 数据转换 防腐
 * @author suyuzhou
 * @date 2022/07/19
 */
public interface Converter<T, Z> {
    /**
     * 转换为实体类
     */
    abstract T convertToDomainObject(Z dataModel);

    /**
     * 转换为数据库模型
     */
    abstract Z convertToDataModel(T domainObject);
}
