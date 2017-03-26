package com.cdkj.zhpay.enums;

import java.util.HashMap;
import java.util.Map;

public enum EPrizeCurrency {
    ZH_HBB("ZH_HB", "红包"), ZH_QBB("ZH_QBB", "钱包币"), ZH_GWB("ZH_GWB", "购物币"), CG_RMB(
            "CG_RMB", "人民币"), CG_CGB("CG_CGB", "菜狗币"), CG_JF("CG_JF", "积分");

    public static Map<String, EPrizeCurrency> getResultMap() {
        Map<String, EPrizeCurrency> map = new HashMap<String, EPrizeCurrency>();
        for (EPrizeCurrency prizeType : EPrizeCurrency.values()) {
            map.put(prizeType.getCode(), prizeType);
        }
        return map;
    }

    EPrizeCurrency(String code, String value) {
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
