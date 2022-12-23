package com.knowledge.server.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.knowledge.common.mybatis.model.BaseDataModelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 项目名变更
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_project")
public class ProjectModel extends BaseDataModelEntity {
    @TableField(value = "user_id")
    private Long userId;
    /**
     * 项目名
     */
    @TableField(value = "project_name")
    private String projectName;
    /**
     * 项目标识
     */
    @TableField(value = "project_code")
    private String projectCode;
    /**
     * 项目包名
     */
    @TableField(value = "project_package")
    private String projectPackage;
    @TableField(value = "version")
    private String version;
    @TableField(value = "author")
    private String author;
    @TableField(value = "email")
    private String email;
}