package com.cdkj.zhpay.enums;

public enum EevaluateType {
    GOOD("A", "好评"), MIDDLE("B", "中评"), NEGATIVE("C", "差评");

    EevaluateType(String code, String value) {
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
