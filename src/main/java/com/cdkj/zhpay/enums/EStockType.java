package com.cdkj.zhpay.enums;

import java.util.HashMap;
import java.util.Map;

public enum EStockType {
    A("A", "2000"), B("B", "10000"), C("C", "30000"), D("D", "50000");

    public static Map<String, EStockType> getResultMap() {
        Map<String, EStockType> map = new HashMap<String, EStockType>();
        for (EStockType status : EStockType.values()) {
            map.put(status.getCode(), status);
        }
        return map;
    }

    EStockType(String code, String value) {
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
