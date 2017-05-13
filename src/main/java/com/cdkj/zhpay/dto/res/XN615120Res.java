package com.cdkj.zhpay.dto.res;

public class XN615120Res {
    // 摇一摇C端用户金额
    private Long yyAmount;

    // 摇一摇树主人分成金额
    private Long yyFcAmount;

    private String yyCurrency;

    public XN615120Res() {
    }

    public XN615120Res(Long yyAmount, String yyCurrency) {
        this.yyAmount = yyAmount;
        this.yyCurrency = yyCurrency;
    }

    public XN615120Res(Long yyAmount, Long yyFcAmount, String yyCurrency) {
        this.yyAmount = yyAmount;
        this.yyFcAmount = yyFcAmount;
        this.yyCurrency = yyCurrency;
    }

    public Long getYyAmount() {
        return yyAmount;
    }

    public void setYyAmount(Long yyAmount) {
        this.yyAmount = yyAmount;
    }

    public Long getYyFcAmount() {
        return yyFcAmount;
    }

    public void setYyFcAmount(Long yyFcAmount) {
        this.yyFcAmount = yyFcAmount;
    }

    public String getYyCurrency() {
        return yyCurrency;
    }

    public void setYyCurrency(String yyCurrency) {
        this.yyCurrency = yyCurrency;
    }
}
