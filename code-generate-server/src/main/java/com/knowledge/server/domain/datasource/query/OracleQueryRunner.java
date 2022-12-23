package com.knowledge.server.domain.datasource.query;

import cn.hutool.core.util.StrUtil;
import com.knowledge.core.enums.DbTypeEnum;
import oracle.jdbc.OracleConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleQueryRunner implements QueryRunnerI {
    private static final int CONNECTION_TIMEOUTS_SECONDS = 15;
    @Override
    public DbTypeEnum dbTypeEnum() {
        return DbTypeEnum.Oracle;
    }

    @Override
    public String tableSql(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select dt.table_name, dtc.comments from user_tables dt,user_tab_comments dtc ");
        sql.append("where dt.table_name = dtc.table_name ");
        // 表名查询
        if (StrUtil.isNotBlank(tableName)) {
            sql.append("and dt.table_name = '").append(tableName).append("' ");
        }
        sql.append("order by dt.table_name asc");

        return sql.toString();
    }

    @Override
    public String tableName() {
        return "table_name";
    }

    @Override
    public String tableComment() {
        return "comments";
    }

    @Override
    public String tableFieldsSql() {
        return "SELECT A.COLUMN_DEF, A.COLUMN_NAME, A.DATA_TYPE, B.COMMENTS,DECODE(C.POSITION, '1', 'PRI') KEY FROM ALL_TAB_COLUMNS A "
                + " INNER JOIN ALL_COL_COMMENTS B ON A.TABLE_NAME = B.TABLE_NAME AND A.COLUMN_NAME = B.COLUMN_NAME AND B.OWNER = '#schema'"
                + " LEFT JOIN ALL_CONSTRAINTS D ON D.TABLE_NAME = A.TABLE_NAME AND D.CONSTRAINT_TYPE = 'P' AND D.OWNER = '#schema'"
                + " LEFT JOIN ALL_CONS_COLUMNS C ON C.CONSTRAINT_NAME = D.CONSTRAINT_NAME AND C.COLUMN_NAME=A.COLUMN_NAME AND C.OWNER = '#schema'"
                + "WHERE A.OWNER = '#schema' AND A.TABLE_NAME = '%s' ORDER BY A.COLUMN_ID ";
    }

    @Override
    public String fieldName() {
        return "COLUMN_NAME";
    }


    @Override
    public String fieldType() {
        return "DATA_TYPE";
    }


    @Override
    public String fieldComment() {
        return "COMMENTS";
    }


    @Override
    public String fieldKey() {
        return "KEY";
    }

    @Override
    public String columnDef() {
        return "COLUMN_DEF";
    }
    @Override
    public Connection getConnection(String connUrl, String username, String password) throws ClassNotFoundException, SQLException {
        DriverManager.setLoginTimeout(CONNECTION_TIMEOUTS_SECONDS);
        Class.forName(this.dbTypeEnum().getDriverClass());
        Connection connection = DriverManager.getConnection(connUrl, username, password);
        if (this.dbTypeEnum() == DbTypeEnum.Oracle) {
            ((OracleConnection) connection).setRemarksReporting(true);
        }
        return connection;
    }

    @Override
    public String formatTableFieldSql(String username, String tableName) {
        String tableFieldsSql = this.tableFieldsSql();
        return  String.format(tableFieldsSql.replace("#schema", username), tableName);
    }
}
