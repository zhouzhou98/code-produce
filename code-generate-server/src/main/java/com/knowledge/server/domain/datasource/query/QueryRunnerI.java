package com.knowledge.server.domain.datasource.query;

import com.knowledge.core.enums.DbTypeEnum;

import java.sql.Connection;
import java.sql.SQLException;

public interface QueryRunnerI {


    DbTypeEnum dbTypeEnum();

    /**
     * 表信息查询 SQL
     */
    String tableSql(String tableName);

    /**
     * 表名称
     */
    String tableName();

    /**
     * 表注释
     */
    String tableComment();

    /**
     * 表字段信息查询 SQL
     */
    String tableFieldsSql();

    /**
     * 字段名称
     */
    String fieldName();

    /**
     * 字段类型
     */
    String fieldType();

    /**
     * 字段注释
     */
    String fieldComment();

    /**
     * 主键字段
     */
    String fieldKey();

    String columnDef();

    /**
     * 连接
     */
    Connection getConnection(String connUrl, String username, String password) throws ClassNotFoundException, SQLException;

    String formatTableFieldSql(String username, String tableName);

}
