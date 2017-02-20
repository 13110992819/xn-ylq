package com.xnjr.mall.domain;

import java.util.Date;

import com.xnjr.mall.dao.base.ABaseDO;

/**
 * 夺宝标的
 * @author: shan 
 * @since: 2016年12月19日 下午2:07:02 
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

    // 中奖币种
    private String currency;

    // 中奖金额
    private Long amount;

    // 所需总人次
    private Integer totalNum;

    // 人次单价
    private Long price;

    // 单人最大投资次数
    private Integer maxInvestNum;

    // 宣传文字
    private String advText;

    // 宣传图
    private String advPic;

    // 已投人次
    private Integer investNum;

    // 创建时间
    private Date createDatetime;

    // 中奖号码
    private String winNumber;

    // 中奖人编号
    private String winUser;

    // 中奖时间
    private Date winDatetime;

    // 状态（0 募集中，1 已揭晓）
    private String status;

    // 系统编号
    private String systemCode;

    // **********db properties ******************
    // 开奖时间起
    private Date lotteryDatetimeStart;

    // 开奖时间止
    private Date lotteryDatetimeEnd;

    // 名称模糊查询
    private String nameForQuery;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getMaxInvestNum() {
        return maxInvestNum;
    }

    public void setMaxInvestNum(Integer maxInvestNum) {
        this.maxInvestNum = maxInvestNum;
    }

    public String getAdvText() {
        return advText;
    }

    public void setAdvText(String advText) {
        this.advText = advText;
    }

    public String getAdvPic() {
        return advPic;
    }

    public void setAdvPic(String advPic) {
        this.advPic = advPic;
    }

    public Integer getInvestNum() {
        return investNum;
    }

    public void setInvestNum(Integer investNum) {
        this.investNum = investNum;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Date getLotteryDatetimeStart() {
        return lotteryDatetimeStart;
    }

    public void setLotteryDatetimeStart(Date lotteryDatetimeStart) {
        this.lotteryDatetimeStart = lotteryDatetimeStart;
    }

    public Date getLotteryDatetimeEnd() {
        return lotteryDatetimeEnd;
    }

    public void setLotteryDatetimeEnd(Date lotteryDatetimeEnd) {
        this.lotteryDatetimeEnd = lotteryDatetimeEnd;
    }

    public String getNameForQuery() {
        return nameForQuery;
    }

    public void setNameForQuery(String nameForQuery) {
        this.nameForQuery = nameForQuery;
    }

}
