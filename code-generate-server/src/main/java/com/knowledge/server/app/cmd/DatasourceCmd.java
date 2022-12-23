package com.knowledge.server.app.cmd;

import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.server.common.AuthUtils;
import com.knowledge.server.domain.datasource.Datasource;
import com.knowledge.server.infrastructure.gateway.DatasourceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class DatasourceCmd {

    @Autowired
    private DatasourceGateway datasourceGateway;
    public int add(String username, String password, String conName, String dbType, String conUrl) {
        UserDto userDto = AuthUtils.getUserDto();
        Datasource isExistDatasource = datasourceGateway.selectDatasource(userDto.getId(), conName, conUrl, dbType, username, password);
        Predicate<Object> predicate = x -> x != null;
        if (predicate.test(isExistDatasource)) {
            throw new BizException(ResultCodeEnum.DATASOURCE_IS_EXIST);
        }
        Datasource datasource = Datasource.builder()
                .user(userDto)
                .conName(conName)
                .conUrl(conUrl)
                .password(password)
                .dbType(dbType)
                .username(username)
                .build();
        return datasourceGateway.saveDomainObj(datasource);
    }

    public int update(Long id, String username, String password, String conName, String dbType, String conUrl) {
        Datasource datasource = datasourceGateway.selectById(id);
        if (datasource == null) {
            throw new BizException(ResultCodeEnum.DATASOURCE_NOT_FOUND);
        }
        UserDto userDto = AuthUtils.getUserDto();
        if (!userDto.getId().equals(datasource.getUser().getId())) {
            throw new BizException(ResultCodeEnum.NO_RIGHT);
        }
        Predicate<Object> predicate = x -> x != null;
        if (predicate.test(username)) {
            datasource.setUsername(username);
        }
        if (predicate.test(password)) {
            datasource.setPassword(password);
        }
        if (predicate.test(conName)) {
            datasource.setConName(conName);
        }
        if (predicate.test(dbType)) {
            datasource.setDbType(dbType);
        }
        if (predicate.test(conUrl)) {
            datasource.setConUrl(conUrl);
        }
        return datasourceGateway.updateDomainObj(datasource);
    }

    public int delete(Long id) {
        Datasource datasource = datasourceGateway.selectById(id);
        if (datasource == null) {
            throw new BizException(ResultCodeEnum.DATASOURCE_NOT_FOUND);
        }
        UserDto userDto = AuthUtils.getUserDto();
        if (!userDto.getId().equals(datasource.getUser().getId())) {
            throw new BizException(ResultCodeEnum.NO_RIGHT);
        }
        return datasourceGateway.deleteDomainObj(id);
    }

    public int batchDelete(List<Long> ids) {
        List<Datasource> datasources = datasourceGateway.selectBatchDomainObj(ids);
        UserDto userDto = AuthUtils.getUserDto();
        datasources.stream().forEach(item -> {
            if (!item.getUser().getId().equals(userDto.getId())) {
                throw new BizException(ResultCodeEnum.NO_RIGHT);
            }
        });
        return datasourceGateway.batchDeleteDomainObj(ids);
    }
}
