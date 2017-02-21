package com.cdkj.zhpay.domain;

import com.cdkj.zhpay.dao.base.ABaseDO;

/**
 * 标的购买次数
 * @author: shan 
 * @since: 2016年12月19日 下午8:55:21 
 * @history:
 */
public class JewelRecordNumber extends ABaseDO {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 序号
    public Long id;

    // 夺宝标的编号
    public String jewelCode;

    // 记录编号
    public String recordCode;

    // 号码
    public String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJewelCode() {
        return jewelCode;
    }

    public void setJewelCode(String jewelCode) {
        this.jewelCode = jewelCode;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
