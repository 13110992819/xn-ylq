package com.cdkj.zhpay.dto.res;

import java.util.List;

/**
 * 提交订单Res
 * @author: xieyj 
 * @since: 2016年5月23日 上午8:46:53 
 * @history:
 */
public class XN808051Res {
    private List<String> codeList;

    private Long cnyAmount;

    private Long gwAmount;

    private Long qbAmount;

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

    public Long getCnyAmount() {
        return cnyAmount;
    }

    public void setCnyAmount(Long cnyAmount) {
        this.cnyAmount = cnyAmount;
    }

    public Long getGwAmount() {
        return gwAmount;
    }

    public void setGwAmount(Long gwAmount) {
        this.gwAmount = gwAmount;
    }

    public Long getQbAmount() {
        return qbAmount;
    }

    public void setQbAmount(Long qbAmount) {
        this.qbAmount = qbAmount;
    }
}
