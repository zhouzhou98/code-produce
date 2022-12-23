package com.knowledge.server.domain.generate.faker;

import com.knowledge.core.enums.FakerTypeEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataFakerFactory {
    public static final Map<FakerTypeEnum, DataFaker> map = new HashMap<FakerTypeEnum, DataFaker>() {{
        put(FakerTypeEnum.NONE, new DefaultDataFaker());
        put(FakerTypeEnum.FIXED, new FixedDataFaker());
        put(FakerTypeEnum.RANDOM, new RandomDataFaker());
        put(FakerTypeEnum.RULE, new RuleDataFaker());
        put(FakerTypeEnum.SNOWFLOWER, new SnowFlowerDataFaker());
        put(FakerTypeEnum.PASSWORD, new PasswordEncodedDataFaker());
        put(FakerTypeEnum.INCREASE, new IncreaseDataFaker());
    }};

    private DataFakerFactory() {}

    public static DataFaker getDataFaker(FakerTypeEnum fakerTypeEnum) {
        fakerTypeEnum = Optional.ofNullable(fakerTypeEnum).orElse(FakerTypeEnum.NONE);
        return map.get(fakerTypeEnum);
    }
}
