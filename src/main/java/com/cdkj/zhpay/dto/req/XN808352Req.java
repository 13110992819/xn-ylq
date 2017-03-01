/**
 * @Title XN808350Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午12:45:24 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午12:45:24 
 * @history:
 */
public class XN808352Req {
    // 编号（必填）
    private String code;

    // 中奖币种（必填）
    private String currency;

    // 中奖金额（必填）
    private String amount;

    // 总人次（必填）
    private String totalNum;

    // 人次单价（必填）
    private String price;

    // 单人最大投资次数（必填）
    private String maxInvestNum;

    // 宣传文字（必填）
    private String advText;

    // 宣传图（必填）
    private String advPic;

    // 更新人（必填）
    private String updater;

    // 备注
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMaxInvestNum() {
        return maxInvestNum;
    }

    public void setMaxInvestNum(String maxInvestNum) {
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

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

}
