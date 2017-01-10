package com.xnjr.mall.enums;

import java.util.HashMap;
import java.util.Map;

public enum EPrizeType {
    HBB("1", "红包币"), QBB("2", "钱包币"), GWB("3", "购物币");

    public static Map<String, EPrizeType> getResultMap() {
        Map<String, EPrizeType> map = new HashMap<String, EPrizeType>();
        for (EPrizeType prizeType : EPrizeType.values()) {
            map.put(prizeType.getCode(), prizeType);
        }
        return map;
    }

    EPrizeType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
