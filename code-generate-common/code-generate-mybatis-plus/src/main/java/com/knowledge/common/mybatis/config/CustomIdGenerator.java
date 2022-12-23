package com.knowledge.common.mybatis.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.knowledge.common.mybatis.utils.IdUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomIdGenerator implements IdentifierGenerator {
    IdUtils idUtils = new IdUtils();
    @Override
    public Long nextId(Object entity) {
        return idUtils.nextId();
    }
}
