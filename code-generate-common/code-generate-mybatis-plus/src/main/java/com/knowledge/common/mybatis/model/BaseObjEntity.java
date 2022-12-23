package com.knowledge.common.mybatis.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseObjEntity {
    // 创建时间
    private LocalDateTime createdAt;

    // 更新时间
    private LocalDateTime updatedAt;

    private Integer dataVersion;

}
