package com.cdkj.zhpay.dto.res;

/**
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:46:35 
 * @history:
 */
public class XN000000Res {

    // 虚拟币
    private Long xnbAmount;

    // 比率
    private Double rate;

    // 人民币
    private Long cnyAmount;

    XN000000Res() {

    }

    public XN000000Res(Long xnbAmount, Double rate, Long cnyAmount) {
        this.xnbAmount = xnbAmount;
        this.rate = rate;
        this.cnyAmount = cnyAmount;
    }

    public Long getXnbAmount() {
        return xnbAmount;
    }

    public void setXnbAmount(Long xnbAmount) {
        this.xnbAmount = xnbAmount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getCnyAmount() {
        return cnyAmount;
    }

    public void setCnyAmount(Long cnyAmount) {
        this.cnyAmount = cnyAmount;
    }
}
