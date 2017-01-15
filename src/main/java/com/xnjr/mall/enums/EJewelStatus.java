package com.xnjr.mall.enums;

/**
 * @author: xieyj 
 * @since: 2017年1月12日 下午11:03:01 
 * @history:
 */
public enum EJewelStatus {
    NEW("0", "待审批"), PASS("1", "审批通过"), UNPASS("2", "审批不通过"), PUT_ON("3",
            "夺宝进行中"), PUT_OFF("4", "下架"), EXPIRED("5", "到期流标"), TO_SEND("7",
            "已开奖");
    // SUCCESSED("6","夺宝成功，待开奖"), TO_RECEIVE("8", "已发货"), COMPLETE("9", "已签收")

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
