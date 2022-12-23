package com.knowledge.server.domain.generate.faker;

import com.knowledge.server.web.request.table.GenerateDataField;
import com.mifmif.common.regex.Generex;

import java.util.ArrayList;
import java.util.List;

public class RuleDataFaker implements DataFaker {
    @Override
    public List<String> doFaker(GenerateDataField tableField, int rowNum) {
        List<String> list = new ArrayList<>(rowNum);
        Generex generex = new Generex(tableField.getFakerParam());
        for (int i = 0; i < rowNum; i++) {
            String randomStr = generex.random();
            list.add(randomStr);
        }
        return list;
    }
}
