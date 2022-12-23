package com.knowledge.server.infrastructure.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knowledge.server.app.mapper.TableFieldModelMapper;
import com.knowledge.server.domain.table.TableField;
import com.knowledge.server.entity.model.TableFieldModel;
import com.knowledge.server.infrastructure.gateway.TableFieldGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class TableFieldGatewayImpl extends BaseGatewayImpl<TableFieldModel, TableFieldModelMapper, TableField> implements TableFieldGateway {
    @Override
    public QueryWrapper<TableFieldModel> doGetWrapper(Map<String, Object> queryParams) {
        return null;
    }

    @Override
    TableField convertToDomainObject(TableFieldModel dataModel) {
        Predicate<TableFieldModel> predicate = x -> x == null;
        if (predicate.test(dataModel)) {
            return null;
        }
        TableField tableField = TableField.builder()
                .autoFill(dataModel.getAutoFill())
                .fieldComment(dataModel.getFieldComment())
                .fieldType(dataModel.getFieldType())
                .fieldName(dataModel.getFieldName())
                .primaryPk(dataModel.getPrimaryPk())
                .tableId(dataModel.getTableId())
                .fieldDefault(dataModel.getFieldDefault())
                .build();
        tableField.setCreatedAt(dataModel.getCreatedAt());
        tableField.setUpdatedAt(dataModel.getUpdatedAt());
        tableField.setDataVersion(dataModel.getDataVersion());
        return tableField;
    }

    @Override
    TableFieldModel convertToDataModel(TableField domainObject) {
        Predicate<TableField> predicate = x -> x == null;
        if (predicate.test(domainObject)) {
            return null;
        }
        TableFieldModel model = TableFieldModel.builder()
                .autoFill(domainObject.getAutoFill())
                .fieldName(domainObject.getFieldName())
                .fieldType(domainObject.getFieldType())
                .fieldComment(domainObject.getFieldComment())
                .tableId(domainObject.getTableId())
                .primaryPk(domainObject.getPrimaryPk())
                .sort(domainObject.getSort())
                .fieldDefault(domainObject.getFieldDefault())
                .build();
        model.setCreatedAt(domainObject.getCreatedAt());
        model.setUpdatedAt(domainObject.getUpdatedAt());
        model.setDataVersion(domainObject.getDataVersion());
        return model;
    }

    @Override
    public List<TableField> getFieldsList(Long tableId) {
        Predicate<TableField> predicate = x -> x != null;
        LambdaQueryWrapper<TableFieldModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TableFieldModel::getTableId, tableId);
        wrapper.orderByDesc(TableFieldModel::getCreatedAt);
        List<TableField> list = this.baseMapper.selectList(wrapper).stream().map(item -> {
            return this.convertToDomainObject(item);
        }).filter(item -> {
            return predicate.test(item);
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public TableField selectByTableIdAndName(Long tableId, String fieldName) {
        LambdaQueryWrapper<TableFieldModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TableFieldModel::getTableId, tableId);
        wrapper.eq(TableFieldModel::getFieldName, fieldName);
        TableFieldModel tableFieldModel = this.baseMapper.selectOne(wrapper);
        return this.convertToDomainObject(tableFieldModel);
    }

    @Override
    public void deleteTableField(Long tableId) {
        LambdaQueryWrapper<TableFieldModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TableFieldModel::getTableId, tableId);
        this.baseMapper.delete(wrapper);
    }
}
