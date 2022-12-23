package com.knowledge.core.enums;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FakerParamsRandomTypeEnum {
    STRING("STRING"),
    NAME("NAME"),
    CITY("CITY"),
    URL("URL"),
    EMAIL("EMAIL"),
    IP("IP"),
    INTEGER("INTEGER"),
    DECIMAL("DECIMAL"),
    UNIVERSITY("UNIVERSITY"),
    DATE("DATE"),
    TIMESTAMP("TIMESTAMP"),
    PHONE("PHONE");

    @Getter
    @Setter
    private String value;

    FakerParamsRandomTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(FakerParamsRandomTypeEnum::getValue).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static FakerParamsRandomTypeEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (FakerParamsRandomTypeEnum mockTypeEnum : FakerParamsRandomTypeEnum.values()) {
            if (mockTypeEnum.value.equals(value)) {
                return mockTypeEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
