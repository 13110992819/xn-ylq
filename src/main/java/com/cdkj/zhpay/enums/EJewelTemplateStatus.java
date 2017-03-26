/**
 * @Title EJewelTemplateStatus.java 
 * @Package com.xnjr.mall.enums 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午2:10:12 
 * @version V1.0   
 */
package com.cdkj.zhpay.enums;

/** 
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午2:10:12 
 * @history:
 */
public enum EJewelTemplateStatus {
    NEW("0", "待上架"), PUTON("1", "已上架"), PUTOFF("2", "已下架");

    EJewelTemplateStatus(String code, String value) {
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
