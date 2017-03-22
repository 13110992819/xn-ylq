package com.cdkj.zhpay.enums;

import java.util.HashMap;
import java.util.Map;

public enum EPrizeCurrency {
    ZH_QBB("ZH1", "钱包币"), ZH_GWB("ZH2", "购物币"), ZH_HBB("ZH3", "红包"), CG_RMB(
            "CG1", "人民币"), CG_CGB("CG2", "菜狗币"), CG_JF("CG3", "积分");

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