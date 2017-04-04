package com.cdkj.zhpay.enums;

/**
 * @author: xieyj 
 * @since: 2016年11月11日 上午10:09:32 
 * @history:
 */
public enum EPayType {
    YEFR("1", "余额/分润支付"), WEIXIN_APP("2", "微信APP"), WEIXIN_H5("5", "微信H5"), ALIPAY(
            "3", "支付宝"), INTEGRAL("90", "单一虚拟币支付");

    EPayType(String code, String value) {
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
