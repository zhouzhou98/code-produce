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
@TableName(value = "t_table")
public class TableModel extends BaseDataModelEntity {
    /**
     * 数据源id
     */
    @TableField(value = "datasource_id")
    private Long datasourceId;

    /**
     * 表名
     */
    @TableField(value = "table_name")
    private String tableName;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 类说明
     */
    @TableField(value = "comment")
    private String comment;

}