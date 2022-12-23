package com.knowledge.server.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.knowledge.common.mybatis.model.BaseDataModelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_datasource")
public class DatasourceModel extends BaseDataModelEntity {

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 数据库类型
     */
    @TableField(value = "db_type")
    private String dbType;

    /**
     * 连接名称
     */
    @TableField(value = "con_name")
    private String conName;

    /**
     * 连接url
     */
    @TableField(value = "con_url")
    private String conUrl;

    /**
     * 连接名称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 连接密码
     */
    @TableField(value = "password")
    private String password;
}