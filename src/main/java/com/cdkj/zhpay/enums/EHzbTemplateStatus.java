package com.cdkj.zhpay.enums;

public enum EHzbTemplateStatus {
    TO_ON("0", "待上架"), ONED("1", "已上架"), OFFED("2", "已下架");

    EHzbTemplateStatus(String code, String value) {
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
