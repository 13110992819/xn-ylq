package com.cdkj.zhpay.dto.req;

public class XN615131Req {
    // 领取人（必填）
    private String userId;

    // 定向红包编号（必填）
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
