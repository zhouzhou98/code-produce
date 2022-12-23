package com.knowledge.server.domain.datasource;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.knowledge.common.mybatis.model.BaseDataModelEntity;
import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.DbTypeEnum;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.server.domain.datasource.query.QueryRunnerBuilder;
import com.knowledge.server.domain.datasource.query.QueryRunnerI;
import com.knowledge.server.web.response.table.TableFieldVo;
import com.knowledge.server.web.response.table.TableVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class Datasource extends BaseDataModelEntity {
    private Connection connection;
    private UserDto user;

    private String dbType;

    /**
     * 连接名称
     */
    private String conName;

    /**
     * 连接url
     */
    private String conUrl;

    /**
     * 连接名称
     */
    private String username;

    /**
     * 连接密码
     */
    private String password;


    public Connection getConnection()  {
        Predicate<Object> predicate = x -> x == null;
        if (predicate.test(this.connection)) {
            QueryRunnerI runner = this.getQueryRunner();
            try {
                this.connection =  runner.getConnection(this.getConUrl(), this.getUsername(), this.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("unknown exception {}", e.getMessage());
                throw new BizException(ResultCodeEnum.DATASOURCE_ERROR_SET);
            }
        }
        return this.connection;
    }
    public QueryRunnerI getQueryRunner() {
        QueryRunnerBuilder queryRunnerBuilder = new QueryRunnerBuilder(DbTypeEnum.valueOf(this.getDbType()));
        return queryRunnerBuilder.getClazz();
    }
    public Map<String, Object> toJSON() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.getId());
        map.put("user", user);
        map.put("dbType", dbType);
        map.put("conName", conName);
        map.put("conUrl", conUrl);
        return map;
    }

    private Func getFunc() {
        Func func = name -> {
            List<TableVo> tableList = new ArrayList<>();
            Connection connection = this.getConnection();
            QueryRunnerI runner = this.getQueryRunner();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(runner.tableSql(name));
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String tableName = rs.getString(runner.tableName());
                    TableVo table = new TableVo(tableName, rs.getString(runner.tableComment()), this.getId());
                    List<TableFieldVo> tableFields = getTableFields(tableName);
                    table.setFieldVos(tableFields);
                    tableList.add(table);
                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("unknow exception {}", e.getMessage());
                throw new BizException(ResultCodeEnum.DATASOURCE_ERROR_SET);
            }
            return tableList;
        };
        return func;
    }

    public List<TableVo> getTableList() {
        try {
            List<TableVo> list = this.getFunc().getTableVos(null);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(ResultCodeEnum.DATASOURCE_ERROR_SET);
        }
    }

    public TableVo getTableVo(String name) {
        List<TableVo> list = this.getFunc().getTableVos(name);
        return list.get(0);
    }

    public List<TableFieldVo> getTableFields(String tableName) {
        List<TableFieldVo> list = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            DatabaseMetaData md = connection.getMetaData();
            QueryRunnerI runner = this.getQueryRunner();
            String tableFieldSql = runner.formatTableFieldSql(md.getUserName(), tableName);
            PreparedStatement preparedStatement = connection.prepareStatement(tableFieldSql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String key = rs.getString(runner.fieldKey());
                Integer pky = StringUtils.isNotBlank(key) && "PRI".equalsIgnoreCase(key) ? 1 : 0;
                TableFieldVo field = new TableFieldVo(
                        this.getId(),
                        tableName,
                        rs.getString(runner.fieldName()),
                        rs.getString(runner.fieldType()),
                        rs.getString(runner.fieldComment()),
                        pky,
                        rs.getString(runner.columnDef())
                        );
               list.add(field);
            }
        } catch (Exception e) {
            log.error("unknown exception {}", e);
            e.printStackTrace();
            throw new BizException(ResultCodeEnum.DATASOURCE_ERROR_SET);
        }
        return list;
    }
}

@FunctionalInterface
interface Func {
    List<TableVo> getTableVos(String name);
}