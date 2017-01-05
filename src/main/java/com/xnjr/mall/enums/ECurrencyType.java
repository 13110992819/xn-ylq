package com.xnjr.mall.enums;

public enum ECurrencyType {
    HB2FR("1", "红包兑换分润"), HBYJ2FR("2", "红包业绩兑换分润"), HBYJ2GXJL("3", "红包业绩兑换贡献奖励"), FR2CNY(
            "4", "分润兑换人民币"), GXJL2CNY("5", "贡献奖励兑换人民币");

    ECurrencyType(String code, String value) {
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
