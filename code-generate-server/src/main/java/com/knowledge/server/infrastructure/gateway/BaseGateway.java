package com.knowledge.server.infrastructure.gateway;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface BaseGateway<Z, T> extends IService<T> {
    default IPage<T> getPage(Integer pageNum, Integer pageSize) {
        Page<T> page = new Page<>(pageNum, pageSize);
        page.addOrder(OrderItem.desc("created_at"));
        return page;
    }

    default QueryWrapper<T> getWrapper(Map<String, Object> queryParams) {
        Predicate<Map<String, Object>> predicate = x -> x == null;
        if (predicate.test(queryParams)) {
            return new QueryWrapper<T>();
        }
        return this.doGetWrapper(queryParams);
    }

    QueryWrapper<T> doGetWrapper(Map<String, Object> queryParams);


    int saveDomainObj(Z domainObject);

    int updateDomainObj(Z domainObject);

    int deleteDomainObj(Serializable id);

    int batchDeleteDomainObj(Collection<?> idList);

    Z selectById(Serializable id);

    List<Z> selectBatchDomainObj(Collection<? extends Serializable> idList);

    List<Z> selectList();
}
