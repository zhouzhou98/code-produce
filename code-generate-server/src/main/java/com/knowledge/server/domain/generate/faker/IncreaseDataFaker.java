package com.knowledge.server.domain.generate.faker;

import com.knowledge.server.web.request.table.GenerateDataField;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class IncreaseDataFaker implements DataFaker {
    @Override
    public List<String> doFaker(GenerateDataField tableField, int rowNum)  {
        String fakerParam = tableField.getFakerParam();
        if (StringUtils.isBlank(fakerParam)) {
            fakerParam = "1";
        }
        int initValue = Integer.parseInt(fakerParam);
        List<String> list = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            list.add(String.valueOf(initValue + i));
        }
        return list;
    }
}
