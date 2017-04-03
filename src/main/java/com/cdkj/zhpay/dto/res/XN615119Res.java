package com.cdkj.zhpay.dto.res;

/**
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:46:35 
 * @history:
 */
public class XN615119Res {
    // 历史摇一摇次数
    private Long historyYyTimes;

    // 今天摇一摇次数
    private Long todayYyTimes;

    // 摇一摇红包业绩
    private Long yyTotalAmount;

    // 历史发红包次数
    private Long historyHbTimes;

    // 今天发红包次数
    private Long todayHbTimes;

    // 发一发红包业绩
    private Long ffTotalHbAmount;

    public Long getHistoryYyTimes() {
        return historyYyTimes;
    }

    public void setHistoryYyTimes(Long historyYyTimes) {
        this.historyYyTimes = historyYyTimes;
    }

    public Long getTodayYyTimes() {
        return todayYyTimes;
    }

    public void setTodayYyTimes(Long todayYyTimes) {
        this.todayYyTimes = todayYyTimes;
    }

    public Long getYyTotalAmount() {
        return yyTotalAmount;
    }

    public void setYyTotalAmount(Long yyTotalAmount) {
        this.yyTotalAmount = yyTotalAmount;
    }

    public Long getHistoryHbTimes() {
        return historyHbTimes;
    }

    public void setHistoryHbTimes(Long historyHbTimes) {
        this.historyHbTimes = historyHbTimes;
    }

    public Long getTodayHbTimes() {
        return todayHbTimes;
    }

    public void setTodayHbTimes(Long todayHbTimes) {
        this.todayHbTimes = todayHbTimes;
    }

    public Long getFfTotalHbAmount() {
        return ffTotalHbAmount;
    }

    public void setFfTotalHbAmount(Long ffTotalHbAmount) {
        this.ffTotalHbAmount = ffTotalHbAmount;
    }
}
