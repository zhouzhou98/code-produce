package com.knowledge.server.infrastructure.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knowledge.server.infrastructure.gateway.BaseGateway;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public abstract class BaseGatewayImpl<T, Y extends BaseMapper<T>, Z> extends ServiceImpl<Y, T> implements BaseGateway<Z, T> {
    /**
     * 转换为实体类
     */
    abstract Z convertToDomainObject(T dataModel);

    /**
     * 转换为数据库模型
     */
    abstract T convertToDataModel(Z domainObject);

    @Override
    public int saveDomainObj(Z domainObject) {
        T t = this.convertToDataModel(domainObject);
        return this.baseMapper.insert(t);
    }

    @Override
    public List<Z> selectList() {
        Predicate<Z> predicate = x -> x != null;
        return this.baseMapper.selectList(new QueryWrapper<>()).stream().map(item -> {
            return this.convertToDomainObject(item);
        }).filter(item -> {
            return predicate.test(item);
        }).collect(Collectors.toList());
    }

    @Override
    public int updateDomainObj(Z domainObject) {
        T t = this.convertToDataModel(domainObject);
        return this.baseMapper.updateById(t);
    }

    @Override
    public int deleteDomainObj(Serializable id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int batchDeleteDomainObj(Collection<?> ids) {
        return this.baseMapper.deleteBatchIds(ids);
    }

    @Override
    public Z selectById(Serializable id) {
        T t = this.baseMapper.selectById(id);
        return this.convertToDomainObject(t);
    }

    @Override
    public List<Z> selectBatchDomainObj(Collection<? extends Serializable> ids) {
        Predicate<Z> predicate = x -> x != null;
        return this.baseMapper.selectBatchIds(ids).stream().map(item -> {
            return this.convertToDomainObject(item);
        }).filter(item -> {
            return predicate.test(item);
        }).collect(Collectors.toList());
    }
}
