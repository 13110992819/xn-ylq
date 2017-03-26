package com.cdkj.zhpay.enums;

import java.util.HashMap;
import java.util.Map;

public enum EConfigType {
    HZB("B", "汇赚宝规则"), YY("C", "摇一摇规则"), XNB("X", "虚拟币规则"), MGIFT("M", "定向红包"), OTHER(
            "O", "系统参数");

    public static Map<String, EConfigType> getResultMap() {
        Map<String, EConfigType> map = new HashMap<String, EConfigType>();
        for (EConfigType status : EConfigType.values()) {
            map.put(status.getCode(), status);
        }
        return map;
    }

    EConfigType(String code, String value) {
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
