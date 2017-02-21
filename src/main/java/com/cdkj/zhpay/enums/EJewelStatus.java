package com.cdkj.zhpay.enums;

/**
 * @author: xieyj 
 * @since: 2017年1月12日 下午11:03:01 
 * @history:
 */
public enum EJewelStatus {
    RUNNING("0", "募集中"), END("1", "已揭晓");

    EJewelStatus(String code, String value) {
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
