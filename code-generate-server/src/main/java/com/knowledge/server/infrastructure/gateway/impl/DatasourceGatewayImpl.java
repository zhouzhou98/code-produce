package com.knowledge.server.infrastructure.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.server.app.mapper.DatasourceModelMapper;
import com.knowledge.server.app.qry.UserQry;
import com.knowledge.server.common.AES128Utils;
import com.knowledge.server.domain.datasource.Datasource;
import com.knowledge.server.entity.model.DatasourceModel;
import com.knowledge.server.infrastructure.gateway.DatasourceGateway;
import com.knowledge.server.web.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class DatasourceGatewayImpl extends BaseGatewayImpl<DatasourceModel, DatasourceModelMapper, Datasource> implements DatasourceGateway {
    @Autowired
    private UserQry userQry;
    @Override
    public QueryWrapper<DatasourceModel> doGetWrapper(Map<String, Object> queryParams) {
        Predicate<Object> predicate = x -> x != null;
        if (!predicate.test(queryParams.get("userId"))) {
            throw new BizException(ResultCodeEnum.PARAM_ERROR);
        }
        QueryWrapper<DatasourceModel> wrapper = new QueryWrapper<>();
        wrapper.eq(predicate.test(queryParams.get("userId")) , "user_id", Long.valueOf(queryParams.get("userId").toString()));
        wrapper.eq(predicate.test(queryParams.get("dbType")), "db_type", predicate.test(queryParams.get("dbType")) ? queryParams.get("dbType").toString() : null);
        wrapper.like(predicate.test(queryParams.get("conName")), "con_name", predicate.test(queryParams.get("conName")) ? queryParams.get("conName").toString() : null);
        wrapper.like(predicate.test(queryParams.get("conUrl")), "con_url", predicate.test(queryParams.get("conUrl")) ? queryParams.get("conUrl").toString() : null);
        return wrapper;
    }

    @Override
    Datasource convertToDomainObject(DatasourceModel dataModel) {
        if (dataModel == null) {
            return null;
        }
        UserDto userDto = userQry.getUserAuthDto(dataModel.getUserId());
        Datasource datasource = Datasource.builder()
                .conName(dataModel.getConName())
                .conUrl(dataModel.getConUrl())
                .user(userDto)
                .dbType(dataModel.getDbType())
                .password(AES128Utils.decryption(dataModel.getPassword()))
                .username(AES128Utils.decryption(dataModel.getUsername()))
                .build();
        datasource.setId(dataModel.getId());
        datasource.setCreatedAt(dataModel.getCreatedAt());
        datasource.setUpdatedAt(dataModel.getUpdatedAt());
        datasource.setDataVersion(dataModel.getDataVersion());
        return datasource;
    }

    @Override
    DatasourceModel convertToDataModel(Datasource domainObject) {
        if (domainObject == null) {
            return null;
        }
        DatasourceModel model = DatasourceModel.builder()
                .conUrl(domainObject.getConUrl())
                .userId(domainObject.getUser().getId())
                .conName(domainObject.getConName())
                .dbType(domainObject.getDbType())
                .password(AES128Utils.encryption(domainObject.getPassword()))
                .username(AES128Utils.encryption(domainObject.getUsername()))
                .build();
        model.setId(domainObject.getId());
        model.setCreatedAt(domainObject.getCreatedAt());
        model.setUpdatedAt(domainObject.getUpdatedAt());
        model.setDataVersion(domainObject.getDataVersion());
        return model;
    }

    @Override
    public List<Datasource> selectListByUserId(Long id) {
        Predicate<Datasource> predicate = x -> x != null;
        LambdaQueryWrapper<DatasourceModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DatasourceModel::getUserId, id);
        return this.baseMapper.selectList(wrapper).stream().map(item -> {
            return this.convertToDomainObject(item);
        }).filter(item -> {
            return predicate.test(item);
        }).collect(Collectors.toList());
    }

    @Override
    public PageResponse<Map<String, Object>> selectPageResult(Integer pageNum, Integer pageSize, Map<String, Object> queryParams) {
        IPage<DatasourceModel> page = this.getPage(pageNum, pageSize);
        Predicate<Datasource> predicate = x -> x != null;
        QueryWrapper<DatasourceModel> wrapper = this.getWrapper(queryParams);
        IPage<DatasourceModel> iPage = this.baseMapper.selectPage(page, wrapper);
        List<Map<String, Object>> list = iPage.getRecords().stream().map(item -> {
            return this.convertToDomainObject(item);
        }).filter(item -> {
            return predicate.test(item);
        }).map(item -> {
            return item.toJSON();
        }).collect(Collectors.toList());
        return new PageResponse<>(list, page.getTotal());
    }

    @Override
    public Datasource selectDatasource(Long id, String conName, String conUrl, String dbType, String username, String password) {
        LambdaQueryWrapper<DatasourceModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DatasourceModel::getUserId, id);
        wrapper.eq(DatasourceModel::getConName, conName);
        wrapper.eq(DatasourceModel::getConUrl, conUrl);
        wrapper.eq(DatasourceModel::getDbType, dbType);
        wrapper.eq(DatasourceModel::getPassword, AES128Utils.encryption(password));
        wrapper.eq(DatasourceModel::getUsername, AES128Utils.encryption(username));
        DatasourceModel datasourceModel = this.baseMapper.selectOne(wrapper);
        return this.convertToDomainObject(datasourceModel);
    }
}
