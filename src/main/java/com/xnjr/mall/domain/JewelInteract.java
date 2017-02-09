package com.xnjr.mall.domain;

import java.util.Date;

import com.xnjr.mall.dao.base.ABaseDO;

/**
 * 宝贝互动
 * @author: shan 
 * @since: 2016年12月19日 下午5:55:37 
 * @history:
 */
public class JewelInteract extends ABaseDO {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 序号
    private Long id;

    // 类型（1 好评）
    private String type;

    // 好评类型(A 好评，B 中评，C差评)
    private String evaluateType;

    // 互动人
    private String interacter;

    // 互动时间
    private Date interactDatetime;

    // 标的编号
    private String jewelCode;

    // 订单编号
    private String orderCode;

    // 系统编号
    private String systemCode;

    private User interacterUser;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInteracter() {
        return interacter;
    }

    public void setInteracter(String interacter) {
        this.interacter = interacter;
    }

    public Date getInteractDatetime() {
        return interactDatetime;
    }

    public void setInteractDatetime(Date interactDatetime) {
        this.interactDatetime = interactDatetime;
    }

    public String getJewelCode() {
        return jewelCode;
    }

    public void setJewelCode(String jewelCode) {
        this.jewelCode = jewelCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public User getInteracterUser() {
        return interacterUser;
    }

    public void setInteracterUser(User interacterUser) {
        this.interacterUser = interacterUser;
    }
}
