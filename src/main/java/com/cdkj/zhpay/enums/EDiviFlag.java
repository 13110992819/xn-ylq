package com.cdkj.zhpay.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xieyj 
 * @since: 2016年11月11日 上午10:09:32 
 * @history:
 */
public enum EDiviFlag {
    EFFECT("effect", "有效值"), NO_EFFECT("no_effect", "有效值");
    public static Map<String, EDiviFlag> getBizTypeMap() {
        Map<String, EDiviFlag> map = new HashMap<String, EDiviFlag>();
        for (EDiviFlag bizType : EDiviFlag.values()) {
            map.put(bizType.getCode(), bizType);
        }
        return map;
    }

    EDiviFlag(String code, String value) {
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
