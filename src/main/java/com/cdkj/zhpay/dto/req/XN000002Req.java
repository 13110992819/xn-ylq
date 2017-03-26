package com.cdkj.zhpay.dto.req;

/**
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:46:35 
 * @history:
 */
public class XN000002Req {
    // 系统编号（必填）
    private String systemCode;

    // 用户编号（必填）
    private String userId;

    // 币种（必填）
    private String currency;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
