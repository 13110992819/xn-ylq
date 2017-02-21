package com.cdkj.zhpay.dto.res;


/**
 * @author: xieyj 
 * @since: 2017年2月9日 下午2:20:59 
 * @history:
 */
public class XN808326Res {
    // 好评率
    private Double goodRate;

    // 购买人数
    private Long bugNums;

    public Double getGoodRate() {
        return goodRate;
    }

    public void setGoodRate(Double goodRate) {
        this.goodRate = goodRate;
    }

    public Long getBugNums() {
        return bugNums;
    }

    public void setBugNums(Long bugNums) {
        this.bugNums = bugNums;
    }
}
