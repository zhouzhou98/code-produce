package com.knowledge.server.domain.generate;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.knowledge.core.enums.FakerTypeEnum;
import com.knowledge.server.domain.generate.faker.DataFaker;
import com.knowledge.server.domain.generate.faker.DataFakerFactory;
import com.knowledge.server.web.request.table.GenerateDataField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class GenerateData implements Serializable {
    private List<Map<String, Object>> dataList;
    private Integer fakerNum;
    private List<GenerateDataField> fieldList;
    private String tableName;

    public GenerateData(String tableName, Integer fakerNum, List<GenerateDataField> fieldList) {
        this.tableName = tableName;
        this.fakerNum = fakerNum;
        this.fieldList = fieldList;
        this.dataList = new ArrayList<>(fakerNum);
    }

    private List<Map<String, Object>> getDataList() {
        if (dataList != null && dataList.size() > 0) {
            return this.dataList;
        }
        for (int i = 0; i < fakerNum; i++) {
            dataList.add(new HashMap<>());
        }
        for (GenerateDataField field : fieldList) {
            FakerTypeEnum fakerTypeEnum = Optional.ofNullable(FakerTypeEnum.getEnumByValue(field.getFakerType()))
                    .orElse(FakerTypeEnum.NONE);
            DataFaker dataFaker = DataFakerFactory.getDataFaker(fakerTypeEnum);
            List<String> list = dataFaker.doFaker(field, fakerNum);
            String fieldName = field.getFieldName();
            if (CollectionUtils.isNotEmpty(list)) {
                for (int i = 0; i < fakerNum; i++) {
                    dataList.get(i).put(fieldName, list.get(i));
                }
            }
        }
        return dataList;
    }

    private String buildSql() {
        // 构造模板
        String template = "insert into %s (%s) values (%s);";
        StringBuilder resultStringBuilder = new StringBuilder();
        for (int i = 0; i < dataList.size(); i++ ) {
            Map<String, Object> dataRow = dataList.get(i);
            String keyStr = fieldList.stream()
                    .map(field -> field.getFieldName())
                    .collect(Collectors.joining(", "));
            String valueStr = fieldList.stream()
                    .map(field -> field.getValueStr(dataRow.get(field.getFieldName())))
                    .collect(Collectors.joining(", "));
            // 填充模板
            String result = String.format(template, tableName, keyStr, valueStr);
            resultStringBuilder.append(result);
            // 最后一个字段后没有换行
            if (i != dataList.size() - 1) {
                resultStringBuilder.append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
    public Map<String,Object> toJSON() {
        List<Map<String, Object>> dataList = this.getDataList();
        String sql = this.buildSql();
        Map<String,Object> map = new HashMap<>();
        map.put("dataList", dataList);
        map.put("sql", sql);
        return map;
    }
}
