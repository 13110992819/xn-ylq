package com.cdkj.zhpay.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xieyj 
 * @since: 2016年11月11日 上午10:09:32 
 * @history:
 */
public enum EBizType {

    AJ_GMHZB("-50", "购买汇赚宝"), AJ_GMHZBFC("51", "购买汇赚宝分成"), AJ_YYJL("52",
            "汇赚宝摇一摇奖励"), AJ_YYFC("53", "摇一摇分成"), AJ_FSDHB("60", "发送得红包"), AJ_LQHB(
            "61", "领取红包"), AJ_DUOBAO("-70", "参与小目标"), AJ_DUOBAO_PRIZE("71",
            "小目标中奖");

    public static Map<String, EBizType> getBizTypeMap() {
        Map<String, EBizType> map = new HashMap<String, EBizType>();
        for (EBizType bizType : EBizType.values()) {
            map.put(bizType.getCode(), bizType);
        }
        return map;
    }

    EBizType(String code, String value) {
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
