/**
 * @Title EHzbHoldStatus.java 
 * @Package com.xnjr.mall.enums 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月21日 下午3:21:14 
 * @version V1.0   
 */
package com.cdkj.zhpay.enums;

/** 
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午3:21:14 
 * @history:
 */
public enum EHzbStatus {
    TO_PAY("0", "待支付"), ACTIVATED("1", "激活"), OFFLINE("2", "已冻结");

    EHzbStatus(String code, String value) {
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
