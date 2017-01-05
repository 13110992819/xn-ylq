package com.xnjr.mall.dto.req;

/**
 * @author: xieyj 
 * @since: 2017年1月4日 下午10:10:02 
 * @history:
 */
public class XN808500Req {
    // 系统编号
    private String systemCode;

    // 用户编号
    private String userId;

    // 类型
    private String type;

    // 发生金额
    private String transAmount;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }
}
