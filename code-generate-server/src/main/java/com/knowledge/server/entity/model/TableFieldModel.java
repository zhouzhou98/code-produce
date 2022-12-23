package com.knowledge.server.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import com.knowledge.common.mybatis.model.BaseModelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 代码生成表字段
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_table_field")
public class TableFieldModel extends BaseModelEntity {
   
    /**
     * 表ID
     */
    @MppMultiId
    private Long tableId;

    /**
     * 字段名称
     */
    @MppMultiId
    private String fieldName;

    /**
     * 字段类型
     */
    @TableField(value = "field_type")
    private String fieldType;

    /**
     * 字段说明
     */
    @TableField(value = "field_comment")
    private String fieldComment;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 自动填充  DEFAULT、INSERT、UPDATE、INSERT_UPDATE
     */
    @TableField(value = "auto_fill")
    private String autoFill;

    /**
     * 主键 0：否  1：是
     */
    @TableField(value = "primary_pk")
    private Integer primaryPk;

    @TableField(value = "field_default")
    private String fieldDefault;
}