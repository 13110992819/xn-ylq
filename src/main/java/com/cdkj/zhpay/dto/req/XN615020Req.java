package com.cdkj.zhpay.dto.req;

/**
 * 我的夺宝分页查询
 * @author: asus 
 * @since: 2016年12月21日 下午4:53:57 
 * @history:
 */
public class XN615020Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 用户ID(必填)
    public String userId;

    // 状态(选填)
    private String jewelStatus;

    public String getJewelStatus() {
        return jewelStatus;
    }

    public void setJewelStatus(String jewelStatus) {
        this.jewelStatus = jewelStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
