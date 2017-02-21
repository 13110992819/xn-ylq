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
public enum EHzbHoldStatus {
    TO_PAY("0", "待支付"), NONACTIVATED("1", "未激活"), ACTIVATED("2", "激活"), OFFLINE(
            "3", "已冻结");

    EHzbHoldStatus(String code, String value) {
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
