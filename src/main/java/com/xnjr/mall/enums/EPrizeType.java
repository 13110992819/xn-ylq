package com.xnjr.mall.enums;

import java.util.HashMap;
import java.util.Map;

public enum EPrizeType {
    QBB("1", "钱包币"), GWB("2", "购物币"), HBB("3", "红包");

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
