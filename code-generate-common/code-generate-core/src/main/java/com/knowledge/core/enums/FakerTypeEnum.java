package com.knowledge.core.enums;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FakerTypeEnum {
    NONE("NONE"),
    INCREASE("INCREASE"),
    FIXED("FIXED"),
    RANDOM("RANDOM"),
    RULE("RULE"),
    PASSWORD("PASSWORD"),
    SNOWFLOWER("SNOWFLOWER");
    @Getter
    @Setter
    private  String value;
    FakerTypeEnum(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values()).map(FakerTypeEnum::getValue).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static FakerTypeEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (FakerTypeEnum fakerTypeEnum : FakerTypeEnum.values()) {
            if (fakerTypeEnum.value.equals(value)) {
                return fakerTypeEnum;
            }
        }
        return null;
    }

}
