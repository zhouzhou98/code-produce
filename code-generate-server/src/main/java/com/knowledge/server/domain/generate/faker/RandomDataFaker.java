package com.knowledge.server.domain.generate.faker;

import com.knowledge.core.enums.FakerParamsRandomTypeEnum;
import com.knowledge.server.common.FakerUtils;
import com.knowledge.server.web.request.table.GenerateDataField;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RandomDataFaker implements DataFaker {
    @Override
    public List<String> doFaker(GenerateDataField tableField, int rowNum) {
        List<String> list = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            FakerParamsRandomTypeEnum randomTypeEnum = Optional.ofNullable(
                    FakerParamsRandomTypeEnum.getEnumByValue(tableField.fakerParam))
                    .orElse(FakerParamsRandomTypeEnum.STRING);
            String randomString = FakerUtils.getRandomValue(randomTypeEnum);
            list.add(randomString);
        }
        return list;
    }
}
