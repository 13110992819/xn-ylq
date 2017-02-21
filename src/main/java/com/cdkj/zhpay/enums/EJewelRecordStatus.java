package com.cdkj.zhpay.enums;

/**
 * @author: xieyj 
 * @since: 2017年1月12日 下午10:55:25 
 * @history:
 */
public enum EJewelRecordStatus {
    TO_PAY("0", "待支付"), LOTTERY("1", "待开奖"), WINNING("2", "已中奖"), LOST("3",
            "未中奖");

    EJewelRecordStatus(String code, String value) {
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
