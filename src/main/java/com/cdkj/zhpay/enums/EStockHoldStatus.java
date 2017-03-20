/**
 * @Title EStockHoldStatus.java 
 * @Package com.xnjr.mall.enums 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月19日 下午5:54:11 
 * @version V1.0   
 */
package com.cdkj.zhpay.enums;

/** 
 * @author: haiqingzheng 
 * @since: 2016年12月19日 下午5:54:11 
 * @history:
 */
public enum EStockHoldStatus {
    TO_PAY("0", "待支付"), UNCLEARED("1", "未清算"), CLEARED("2", "已清算");

    EStockHoldStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
