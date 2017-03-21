package com.cdkj.zhpay.dto.req;

public class XN615111Req {
    // 用户编号(必填)
    private String userId;

    // 汇赚宝模板编号(必填)
    private String hzbTemplateCode;

    // 支付类型(必填)
    private String payType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHzbTemplateCode() {
        return hzbTemplateCode;
    }

    public void setHzbTemplateCode(String hzbTemplateCode) {
        this.hzbTemplateCode = hzbTemplateCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
