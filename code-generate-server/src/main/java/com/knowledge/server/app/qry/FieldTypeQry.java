package com.knowledge.server.app.qry;

import com.alibaba.fastjson.JSON;
import com.knowledge.core.constant.RedisConstants;
import com.knowledge.server.domain.fieldtype.FieldType;
import com.knowledge.server.entity.dto.FieldTypeDto;
import com.knowledge.server.infrastructure.gateway.FieldTypeGateway;
import com.knowledge.server.web.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class FieldTypeQry {
    @Autowired
    private FieldTypeGateway fieldTypeGateway;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public PageResponse<FieldType> getPage(Integer pageNum, Integer pageSize, Map<String, Object> queryParams) {
        PageResponse<FieldType> response = fieldTypeGateway.selectPageResult(pageNum, pageSize, queryParams);
        return response;
    }

    public List<FieldType> getList() {
        return fieldTypeGateway.selectList();
    }

    public Map<String, FieldTypeDto> fieldTypeMap() {
        Predicate<Object> predicate = x -> x == null;
        Object result = redisTemplate.opsForValue().get(RedisConstants.FIELD_TYPE_LIST);
        List<FieldTypeDto> list = new ArrayList<>();
        if (predicate.test(result)) {
            list = this.getList().stream().map(item -> {
                return FieldTypeDto.builder()
                        .columnType(item.getColumnType())
                        .id(item.getId())
                        .javaType(item.getJavaType())
                        .jsType(item.getJsType())
                        .javaPackageName(item.getJavaPackageName())
                        .jsPackageName(item.getJsPackageName())
                        .build();
            }).collect(Collectors.toList());
            redisTemplate.opsForValue().set(RedisConstants.FIELD_TYPE_LIST, JSON.toJSON(list), 24, TimeUnit.HOURS);
        } else {
            list = JSON.parseArray(result.toString(), FieldTypeDto.class);
        }
        Map<String, FieldTypeDto> map = new HashMap<>();
        for (FieldTypeDto type: list) {
            map.put(type.getColumnType(), type);
        }
        return map;
    }
}
