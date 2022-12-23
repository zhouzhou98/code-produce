package com.knowledge.server.domain.datasource.query;

import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.enums.DbTypeEnum;
import com.knowledge.core.enums.ResultCodeEnum;

public class QueryRunnerBuilder {
    private DbTypeEnum typeEnum;
    public QueryRunnerBuilder(DbTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }
    public QueryRunnerI getClazz() {
        switch (typeEnum) {
            case MySQL:
                return new MySqlQueryRunner();
            case Oracle:
                return new OracleQueryRunner();
            case PostgreSQL:
                return new PostgreSqlQueryRunner();
            case SQLServer:
                return new SQLServerQueryRunner();
        }
        throw new BizException(ResultCodeEnum.DATASOURCE_NOT_FOUND);
    }
 }
