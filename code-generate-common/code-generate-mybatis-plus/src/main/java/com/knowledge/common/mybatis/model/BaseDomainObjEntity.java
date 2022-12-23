package com.knowledge.common.mybatis.model;

import com.knowledge.common.mybatis.utils.IdUtils;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通用model类型
 */
@Data
public abstract class BaseDomainObjEntity {
    // 创建时间
    private LocalDateTime createdAt;

    // 更新时间
    private LocalDateTime updatedAt;

    private Integer dataVersion;
    private Long id;

   public Long getId() {
     if (this.id == null) {
         IdUtils idUtils = new IdUtils();
         return idUtils.nextId();
     }
     return id;
   }

}
