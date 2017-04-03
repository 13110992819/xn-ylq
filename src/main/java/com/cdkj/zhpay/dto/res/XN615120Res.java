package com.cdkj.zhpay.dto.res;

public class XN615120Res {
    private Long yyAmount;

    private String yyCurrency;

    public XN615120Res() {
    }

    public XN615120Res(Long yyAmount, String yyCurrency) {
        this.yyAmount = yyAmount;
        this.yyCurrency = yyCurrency;
    }

    public Long getYyAmount() {
        return yyAmount;
    }

    public void setYyAmount(Long yyAmount) {
        this.yyAmount = yyAmount;
    }

    public String getYyCurrency() {
        return yyCurrency;
    }

    public void setYyCurrency(String yyCurrency) {
        this.yyCurrency = yyCurrency;
    }

}
