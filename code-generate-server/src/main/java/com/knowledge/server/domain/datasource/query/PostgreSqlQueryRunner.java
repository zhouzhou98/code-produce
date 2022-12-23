package com.knowledge.server.domain.datasource.query;

import cn.hutool.core.util.StrUtil;
import com.knowledge.core.enums.DbTypeEnum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlQueryRunner implements QueryRunnerI {
    private static final int CONNECTION_TIMEOUTS_SECONDS = 15;
    @Override
    public DbTypeEnum dbTypeEnum() {
        return DbTypeEnum.PostgreSQL;
    }

    @Override
    public String tableSql(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t1.tablename, obj_description(relfilenode, 'pg_class') as comments from pg_tables t1, pg_class t2 ");
        sql.append("where t1.tablename not like 'pg%' and t1.tablename not like 'sql_%' and t1.tablename = t2.relname ");
        // 表名查询
        if (StrUtil.isNotBlank(tableName)) {
            sql.append("and t1.tablename = '").append(tableName).append("' ");
        }

        return sql.toString();
    }

    @Override
    public String tableFieldsSql() {
        return "select t2.attname as columnName, pg_type.typname as dataType, col_description(t2.attrelid,t2.attnum) as columnComment,"
                + "(CASE t3.contype WHEN 'p' THEN 'PRI' ELSE '' END) as columnKey "
                + "from pg_class as t1, pg_attribute as t2 inner join pg_type on pg_type.oid = t2.atttypid "
                + "left join pg_constraint t3 on t2.attnum = t3.conkey[1] and t2.attrelid = t3.conrelid "
                + "where t1.relname = '%s' and t2.attrelid = t1.oid and t2.attnum>0";
    }


    @Override
    public String tableName() {
        return "tablename";
    }

    @Override
    public String tableComment() {
        return "comments";
    }

    @Override
    public String fieldName() {
        return "columnName";
    }

    @Override
    public String fieldType() {
        return "dataType";
    }

    @Override
    public String fieldComment() {
        return "columnComment";
    }

    @Override
    public String fieldKey() {
        return "columnKey";
    }

    @Override
    public String columnDef() {
        return "columnDef";
    }

    @Override
    public Connection getConnection(String connUrl, String username, String password) throws ClassNotFoundException, SQLException {
        DriverManager.setLoginTimeout(CONNECTION_TIMEOUTS_SECONDS);
        Class.forName(this.dbTypeEnum().getDriverClass());
        return DriverManager.getConnection(connUrl, username, password);
    }

    @Override
    public String formatTableFieldSql(String username, String tableName) {
        String tableFieldsSql = this.tableFieldsSql();
        return  String.format(tableFieldsSql, tableName);
    }
}
