package com.cdkj.zhpay.domain;

import java.util.Date;

import com.cdkj.zhpay.dao.base.ABaseDO;

/**
 * 小目标
 * @author: xieyj 
 * @since: 2017年3月20日 下午9:22:58 
 * @history:
 */
public class Jewel extends ABaseDO {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 模板编号
    private String templateCode;

    // 期号
    private Integer periods;

    // 中奖金额
    private Long toAmount;

    // 中奖币种
    private String toCurrency;

    // 所需总人次
    private Integer totalNum;

    // 单人最大投资次数
    private Integer maxNum;

    // 已投人次
    private Integer investNum;

    // 人次单价
    private Long fromAmount;

    // 单价币种
    private String fromCurrency;

    // 宣传标语
    private String slogan;

    // 宣传图片
    private String advPic;

    // 开始时间
    private Date startDatetime;

    // 状态（0 募集中，1 已揭晓）
    private String status;

    // 中奖号码
    private String winNumber;

    // 中奖人编号
    private String winUser;

    // 中奖时间
    private Date winDatetime;

    // 公司编号
    private String companyCode;

    // 系统编号
    private String systemCode;

    // **********db properties ******************
    // 开奖时间起
    private Date winDatetimeStart;

    // 开奖时间止
    private Date winDatetimeEnd;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public Long getToAmount() {
        return toAmount;
    }

    public void setToAmount(Long toAmount) {
        this.toAmount = toAmount;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getInvestNum() {
        return investNum;
    }

    public void setInvestNum(Integer investNum) {
        this.investNum = investNum;
    }

    public Long getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(Long fromAmount) {
        this.fromAmount = fromAmount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAdvPic() {
        return advPic;
    }

    public void setAdvPic(String advPic) {
        this.advPic = advPic;
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinNumber() {
        return winNumber;
    }

    public void setWinNumber(String winNumber) {
        this.winNumber = winNumber;
    }

    public String getWinUser() {
        return winUser;
    }

    public void setWinUser(String winUser) {
        this.winUser = winUser;
    }

    public Date getWinDatetime() {
        return winDatetime;
    }

    public void setWinDatetime(Date winDatetime) {
        this.winDatetime = winDatetime;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Date getWinDatetimeStart() {
        return winDatetimeStart;
    }

    public void setWinDatetimeStart(Date winDatetimeStart) {
        this.winDatetimeStart = winDatetimeStart;
    }

    public Date getWinDatetimeEnd() {
        return winDatetimeEnd;
    }

    public void setWinDatetimeEnd(Date winDatetimeEnd) {
        this.winDatetimeEnd = winDatetimeEnd;
    }
}
