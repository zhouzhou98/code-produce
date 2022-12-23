package com.knowledge.core.enums;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GeneratorEnum {
    JAVA("JAVA"),
    TYPESCRIPT("TYPESCRIPT");
    @Getter
    @Setter
    private  String value;
    GeneratorEnum(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values()).map(GeneratorEnum::getValue).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static GeneratorEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (GeneratorEnum fakerTypeEnum : GeneratorEnum.values()) {
            if (fakerTypeEnum.value.equals(value)) {
                return fakerTypeEnum;
            }
        }
        return null;
    }
}
