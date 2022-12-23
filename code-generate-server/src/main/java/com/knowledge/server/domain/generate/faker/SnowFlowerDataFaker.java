package com.knowledge.server.domain.generate.faker;

import com.knowledge.common.mybatis.utils.IdUtils;
import com.knowledge.server.web.request.table.GenerateDataField;

import java.util.ArrayList;
import java.util.List;

public class SnowFlowerDataFaker implements DataFaker {
    @Override
    public List<String> doFaker(GenerateDataField tableField, int rowNum) {
        List<String> list = new ArrayList<>(rowNum);
        IdUtils idUtils = new IdUtils();
        for (int i = 0; i < rowNum; i++) {
          list.add(idUtils.nextId() + "");
        }
        return list;
    }
}
