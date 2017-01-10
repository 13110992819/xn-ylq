/**
 * @Title Status.java 
 * @Package com.ibis.pz.enums 
 * @Description 
 * @author miyb  
 * @date 2015-3-7 上午8:41:50 
 * @version V1.0   
 */
package com.xnjr.mall.enums;

/**
 * 
 * @author: shan 
 * @since: 2016年12月19日 下午2:32:44 
 * @history:
 */
public enum EJewelStatus {
    NEW("0", "待审批"), PASS("1", "审批通过"), UNPASS("2", "审批不通过"), PUT_ON("3",
            "夺宝进行中"), PUT_OFF("4", "强制下架"), EXPIRED("5", "到期流标"), SUCCESSED(
            "6", "夺宝成功，待开奖"), TO_SEND("7", "已开奖，待发货"), DELIVERED("8", "已发货，待收货"), RECEIVED(
            "9", "已收货");

    EJewelStatus(String code, String value) {
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
