package com.cdkj.zhpay.enums;

/**
 * @author: xieyj 
 * @since: 2016年5月24日 上午8:29:02 
 * @history:
 */
public enum EGeneratePrefix {
    HZB_TEMPLETE("HT", "摇钱树模板"), HZB("H", "汇赚宝"), JEWEL_TEMPLETE("JT", "小目标模板"), JEWEL(
            "J", "小目标"), JEWEL_RECORD("JR", "小目标参与记录"), SHAKE("YY", "摇一摇"), PAY_GROUP(
            "PG", "支付组号");

    EGeneratePrefix(String code, String value) {
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
