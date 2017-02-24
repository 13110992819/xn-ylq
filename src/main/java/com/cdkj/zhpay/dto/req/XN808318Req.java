package com.cdkj.zhpay.dto.req;

/**
 * @author: xieyj 
 * @since: 2017年1月11日 下午5:41:54 
 * @history:
 */
public class XN808318Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = -3572987603574928980L;

    // 夺宝标的编号(必填)
    private String jewelCode;

    // 用户编号(选填)
    private String userId;

    public String getJewelCode() {
        return jewelCode;
    }

    public void setJewelCode(String jewelCode) {
        this.jewelCode = jewelCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
