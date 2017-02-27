package com.cdkj.zhpay.enums;

import java.util.HashMap;
import java.util.Map;

public enum ECategoryType {
    HZB_PRICE("B1", "汇赚宝价格"), HZB_FC("B2", "汇赚宝分成"), HZB_YY("B3", "汇赚宝摇一摇"), YY_FC(
            "C1", "摇一摇规则分成"), YY_GV("C2", "摇一摇概率"), QBHL("D1", "钱币汇率规则"), HB_GZ(
            "E1", "发红包规则");

    public static Map<String, ECategoryType> getResultMap() {
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
