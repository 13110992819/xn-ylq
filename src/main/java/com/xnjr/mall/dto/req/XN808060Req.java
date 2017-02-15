package com.xnjr.mall.dto.req;

import java.util.List;

/**
 * 支付订单
 * @author: xieyj 
 * @since: 2016年5月23日 上午8:46:53 
 * @history:
 */
public class XN808060Req {

    // 编号（必填）
    private List<String> codeList;

    // 支付渠道（必填）
    private String payType;

    // ip地址(选填)
    private String ip;

    // 申请人（必填）
    private String applyUser;

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

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
}
