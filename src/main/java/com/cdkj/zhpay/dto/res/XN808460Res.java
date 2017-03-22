package com.cdkj.zhpay.dto.res;

import com.cdkj.zhpay.enums.EPrizeCurrency;

public class XN808460Res {

    // 摇出金额
    private Long yyAmount;

    // 摇出币种
    private String yyCurrency;

    public XN808460Res() {
    }

    public XN808460Res(Long yyAmount, EPrizeCurrency currency) {
        this.yyAmount = yyAmount;
        this.yyCurrency = currency.getCode();
    }

    public String getYyCurrency() {
        return yyCurrency;
    }

    public void setYyCurrency(String yyCurrency) {
        this.yyCurrency = yyCurrency;
    }

    public Long getYyAmount() {
        return yyAmount;
    }

    public void setYyAmount(Long yyAmount) {
        this.yyAmount = yyAmount;
    }

}
