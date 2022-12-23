package com.knowledge.server.domain.datasource.query;

import cn.hutool.core.util.StrUtil;
import com.knowledge.core.enums.DbTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class MySqlQueryRunner implements QueryRunnerI {
    private static final int CONNECTION_TIMEOUTS_SECONDS = 15;
    @Override
    public DbTypeEnum dbTypeEnum() {
        return DbTypeEnum.MySQL;
    }

    @Override
    public String tableSql(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select table_name, table_comment from information_schema.tables ");
        sql.append("where table_schema = (select database()) ");
        // 表名查询
        if (StrUtil.isNotBlank(tableName)) {
            sql.append("and table_name = '").append(tableName).append("' ");
        }
        sql.append("order by table_name asc");

        return sql.toString();
    }

    @Override
    public String tableName() {
        return "table_name";
    }

    @Override
    public String tableComment() {
        return "table_comment";
    }

    @Override
    public String tableFieldsSql() {
        return "select column_default, column_name, data_type, column_comment, column_key from information_schema.columns "
                + "where table_name = '%s' and table_schema = (select database()) order by ordinal_position";
    }

    @Override
    public String fieldName() {
        return "column_name";
    }

    @Override
    public String fieldType() {
        return "data_type";
    }

    @Override
    public String fieldComment() {
        return "column_comment";
    }

    @Override
    public String fieldKey() {
        return "column_key";
    }

    @Override
    public String columnDef() {
        return "column_default";
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
