package com.knowledge.server.domain.generate.faker;

import com.knowledge.server.web.request.table.GenerateDataField;

import java.util.List;

public interface DataFaker {
    List<String> doFaker(GenerateDataField tableField, int rowNum);
}
