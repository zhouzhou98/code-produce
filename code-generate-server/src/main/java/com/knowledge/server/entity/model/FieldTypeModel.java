package com.knowledge.server.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.knowledge.common.mybatis.model.BaseDataModelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 字段类型管理
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_field_type")
public class FieldTypeModel extends BaseDataModelEntity {

    /**
     * 字段类型
     */
    @TableField(value = "column_type")
    private String columnType;

    /**
     * Java属性类型
     */
    @TableField(value = "java_type")
    private String javaType;

    /**
     * Java属性包名
     */
    @TableField(value = "java_package_name")
    private String javaPackageName;

    /**
     * Js属性包名
     */
    @TableField(value = "js_package_name")
    private String jsPackageName;

    /**
     * Js属性类型
     */
    @TableField(value = "js_type")
    private String jsType;
}