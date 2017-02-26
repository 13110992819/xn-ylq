package com.cdkj.zhpay.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xieyj 
 * @since: 2016年11月11日 上午10:09:32 
 * @history:
 */
public enum EHzbMgiftStatus {
    TO_SEND("0", "待发送"), SENT("1", "已发送,待领取"), RECEIVE("2", "已领取"), INVALID(
            "3", "已失效"), TO_INVALID("01", "待失效(待发送/已发送，待领取)");
    public static Map<String, EHzbMgiftStatus> getMap() {
        Map<String, EHzbMgiftStatus> map = new HashMap<String, EHzbMgiftStatus>();
        for (EHzbMgiftStatus bizType : EHzbMgiftStatus.values()) {
            map.put(bizType.getCode(), bizType);
        }
        return map;
    }

    EHzbMgiftStatus(String code, String value) {
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
