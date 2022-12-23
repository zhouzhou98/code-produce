package com.knowledge.server.domain.generate.faker;

import cn.hutool.core.date.DateUtil;
import com.knowledge.common.mybatis.utils.IdUtils;
import com.knowledge.server.web.request.table.GenerateDataField;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DefaultDataFaker  implements DataFaker {
    @Override
    public List<String> doFaker(GenerateDataField tableField, int rowNum) {
        List<String> list = new ArrayList<>(rowNum);
        IdUtils idUtils = new IdUtils();
        if (tableField.getPrimaryPk() == 1) {
            for (int i = 0; i < rowNum; i++) {
                if (tableField.getFieldType() == "varchar") {
                    list.add(UUID.randomUUID().toString());
                } else {
                    list.add(idUtils.nextId() + "");
                }
            }
            return list;
        }
        // 使用默认值
        String defaultValue = tableField.getFieldDefault();
        // 特殊逻辑，日期要伪造数据
        if ("CURRENT_TIMESTAMP".equals(defaultValue)) {
            defaultValue = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        if (StringUtils.isNotBlank(defaultValue)) {
            for (int i = 0; i < rowNum; i++) {
                list.add(defaultValue);
            }
        }
        return list;
    }
}

