package com.cdkj.zhpay.dto.req;

/**
 * @author: xieyj 
 * @since: 2017年1月11日 下午5:41:54 
 * @history:
 */
public class XN808316Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = -3572987603574928980L;

    // 用户编号(必填)
    private String userId;

    // 系统编号(必填)
    private String systemCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
