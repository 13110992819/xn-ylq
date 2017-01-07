/**
 * @Title XN808403Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月19日 下午5:46:29 
 * @version V1.0   
 */
package com.xnjr.mall.dto.req;

/** 
 * 股份购买
 * @author: haiqingzheng 
 * @since: 2016年12月19日 下午5:46:29 
 * @history:
 */
public class XN808403Req {
    // 股份编号
    private String code;

    // 用户编号
    private String userId;

    // 支付类型
    private String payType;

    // ip
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

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
