package com.cdkj.zhpay.dto.req;

/**
 * @author: xieyj 
 * @since: 2017年1月11日 下午5:41:54 
 * @history:
 */
public class XN808315Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = -3572987603574928980L;

    // 用户编号(选填)
    private String userId;

    // 夺宝标的编号(选填)
    private String jewelCode;

    // 状态(0待开奖，1已中奖，2未中奖)(选填)
    private String status;

    // 系统编号(必填)
    private String systemCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getJewelCode() {
        return jewelCode;
    }

    public void setJewelCode(String jewelCode) {
        this.jewelCode = jewelCode;
    }
}
