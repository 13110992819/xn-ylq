package com.cdkj.zhpay.dto.req;

public class XN802503Req {

    // 用户编号(必填)
    private String userId;

    // 币种(选填)
    private String currency;

    // 系统编号(待删除)
    private String systemCode;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
