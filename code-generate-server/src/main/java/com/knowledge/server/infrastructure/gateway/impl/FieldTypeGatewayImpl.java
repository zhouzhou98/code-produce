package com.knowledge.server.infrastructure.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.knowledge.server.app.mapper.FieldTypeModelMapper;
import com.knowledge.server.domain.fieldtype.FieldType;
import com.knowledge.server.entity.model.FieldTypeModel;
import com.knowledge.server.infrastructure.gateway.FieldTypeGateway;
import com.knowledge.server.web.response.PageResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class FieldTypeGatewayImpl extends BaseGatewayImpl<FieldTypeModel, FieldTypeModelMapper, FieldType> implements FieldTypeGateway {
    @Override
    public QueryWrapper<FieldTypeModel> doGetWrapper(Map<String, Object> queryParams) {
        QueryWrapper<FieldTypeModel> wrapper = new QueryWrapper<>();
        wrapper.eq(queryParams.get("id") != null, "id", queryParams.get("id") != null ? Long.valueOf(queryParams.get("id").toString()) : "");
        return wrapper;
    }


    @Override
    FieldType convertToDomainObject(FieldTypeModel dataModel) {
        FieldType type = FieldType.builder()
                .columnType(dataModel.getColumnType())
                .javaType(dataModel.getJavaType())
                .jsType(dataModel.getJsType())
                .javaPackageName(dataModel.getJavaPackageName())
                .jsPackageName(dataModel.getJsPackageName())
                .build();
        type.setId(dataModel.getId());
        type.setCreatedAt(dataModel.getCreatedAt());
        type.setUpdatedAt(dataModel.getUpdatedAt());
        type.setDataVersion(dataModel.getDataVersion());
        return type;
    }

    @Override
    FieldTypeModel convertToDataModel(FieldType domainObject) {
        FieldTypeModel typeModel = FieldTypeModel.builder()
                .columnType(domainObject.getColumnType())
                .javaType(domainObject.getJavaType())
                .jsType(domainObject.getJsType())
                .javaPackageName(domainObject.getJavaPackageName())
                .jsPackageName(domainObject.getJsPackageName())
                .build();
        typeModel.setId(domainObject.getId());
        typeModel.setCreatedAt(domainObject.getCreatedAt());
        typeModel.setUpdatedAt(domainObject.getUpdatedAt());
        typeModel.setDataVersion(domainObject.getDataVersion());
        return typeModel;
    }

    @Override
    public PageResponse<FieldType> selectPageResult(Integer pageNum, Integer pageSize, Map<String, Object> queryParams) {
        IPage<FieldTypeModel> page = this.getPage(pageNum, pageSize);
        Predicate<FieldType> predicate = x -> x != null;
        QueryWrapper<FieldTypeModel> wrapper = this.getWrapper(queryParams);
        IPage<FieldTypeModel> iPage = this.baseMapper.selectPage(page, wrapper);
        List<FieldType> fieldTypeList = iPage.getRecords().stream().map(item -> {
            return this.convertToDomainObject(item);
        }).filter(item -> {
            return predicate.test(item);
        }).collect(Collectors.toList());
        return new PageResponse<>(fieldTypeList, page.getTotal());
    }
}
