package com.knowledge.common.mybatis.model;

import com.baomidou.mybatisplus.annotation.*;
import com.knowledge.common.mybatis.utils.IdUtils;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通用model类型
 */
@Data
public abstract class BaseDataModelEntity {
    // 创建时间
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    // 更新时间
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @Version
    @TableField(value = "data_version")
    private Integer dataVersion;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    public LocalDateTime getCreatedAt() {
        return createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt == null ? LocalDateTime.now() : updatedAt;
    }

    public Integer getDataVersion() {
        return dataVersion == null ? 0 : dataVersion;
    }

    public Long getId() {
        if (this.id == null) {
            IdUtils idUtils = new IdUtils();
            return idUtils.nextId();
        }
        return this.id;
    }

}
