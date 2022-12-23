package com.knowledge.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill.....");
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        LocalDateTime localDateTime = LocalDateTime.now(zoneId);
        // setFieldValByName(String fieldName, Object fieldVal, MetaObject
        this.setFieldValByName("createdAt", localDateTime, metaObject);
        this.setFieldValByName("updatedAt", localDateTime, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        LocalDateTime localDateTime = LocalDateTime.now(zoneId);
        log.info("start update fill.....");
        this.setFieldValByName("updatedAt", localDateTime, metaObject);
    }
}
