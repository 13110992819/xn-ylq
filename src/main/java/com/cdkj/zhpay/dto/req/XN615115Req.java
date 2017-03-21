/**
 * @Title XN808458Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月21日 下午4:21:59 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * 分页查询汇赚宝购买记录
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午4:21:59 
 * @history:
 */
public class XN615115Req extends APageReq {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 3890719196325687634L;

    // 用户编号(选填)
    private String userId;

    // 汇赚宝模板编号(选填)
    private String templateCode;

    // 状态(选填)
    private String status;

    // 支付组号(选填)
    private String payGroup;

    // 橙账本流水号(选填)
    private String payCode;

    // 系统编号(必填)
    private String systemCode;

    // 公司编号(必填)
    private String companyCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayGroup() {
        return payGroup;
    }

    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

}
