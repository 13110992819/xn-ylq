package com.cdkj.zhpay.dto.req;

public class XN808465Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 针对持有汇赚宝序号(选填)
    private String hzbHoldId;

    // 类型(选填)
    private String type;

    // 用户编号(选填)
    private String userId;

    public String getHzbHoldId() {
        return hzbHoldId;
    }

    public void setHzbHoldId(String hzbHoldId) {
        this.hzbHoldId = hzbHoldId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
