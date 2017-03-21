/**
 * @Title XN808452Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月21日 下午3:05:19 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * 汇赚宝购买
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午3:05:19 
 * @history:
 */
public class XN615110Req {
    // 用户编号(必填)
    private String userId;

    // 汇赚宝模板编号(必填)
    private String hzbTemplateCode;

    // 支付类型(必填)
    private String payType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHzbTemplateCode() {
        return hzbTemplateCode;
    }

    public void setHzbTemplateCode(String hzbTemplateCode) {
        this.hzbTemplateCode = hzbTemplateCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
