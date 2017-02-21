package com.cdkj.zhpay.dto.req;

/**
 * @author: xieyj 
 * @since: 2017年2月9日 上午11:30:14 
 * @history:
 */
public class XN808320Req {
    // 宝贝编号（必填）
    public String jewelCode;

    // 订单编号（必填）
    private String orderCode;

    // 评价类型(必填，1 好评 2 中评 3 差评)
    private String evaluateType;

    // 好评人（必填）
    public String interacter;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(String evaluateType) {
        this.evaluateType = evaluateType;
    }

    public String getJewelCode() {
        return jewelCode;
    }

    public void setJewelCode(String jewelCode) {
        this.jewelCode = jewelCode;
    }

    public String getInteracter() {
        return interacter;
    }

    public void setInteracter(String interacter) {
        this.interacter = interacter;
    }

}
