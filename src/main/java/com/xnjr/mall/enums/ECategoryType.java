package com.xnjr.mall.enums;

import java.util.HashMap;
import java.util.Map;

public enum ECategoryType {
    O2O("B1", "O2O消费"), ST("B2", "买福利月卡分润"), HZB("B3", "买汇赚宝分润"), YYY("B4",
            "摇一摇红包业绩"), YYYGZ("C1", "摇一摇规则"), QBHL("D1", "钱币汇率规则"), DEF_STORE(
            "E1", "店铺定位"), YYJL("E2", "汇赚宝定位");

    public static Map<String, ECategoryType> getBooleanResultMap() {
        Map<String, ECategoryType> map = new HashMap<String, ECategoryType>();
        for (ECategoryType status : ECategoryType.values()) {
            map.put(status.getCode(), status);
        }
        return map;
    }

    ECategoryType(String code, String value) {
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
