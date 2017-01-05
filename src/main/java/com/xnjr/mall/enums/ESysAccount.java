/**
 * @Title EDirection.java 
 * @Package com.ibis.account.enums 
 * @Description 
 * @author miyb  
 * @date 2015-2-26 下午3:37:06 
 * @version V1.0   
 */
package com.xnjr.mall.enums;

import java.util.HashMap;
import java.util.Map;

/** 
 * @author: miyb 
 * @since: 2015-2-26 下午3:37:06 
 * @history:
 */
public enum ESysAccount {
    SYS_ACCOUNT("SYS_ACCOUNT", "系统账户"), CNY("A2016100000000000000", "人民币"), FRB(
            "A2016100000000000001", "分润"), GXJL("A2016100000000000002", "贡献奖励"), QBB(
            "A2016100000000000003", "钱包币"), GWB("A2016100000000000004", "购物币"), HBB(
            "A2016100000000000005", "红包币"), HBYJ("A2016100000000000006", "红包业绩");

    public static Map<String, ESysAccount> getDirectionMap() {
        Map<String, ESysAccount> map = new HashMap<String, ESysAccount>();
        for (ESysAccount direction : ESysAccount.values()) {
            map.put(direction.getCode(), direction);
        }
        return map;
    }

    ESysAccount(String code, String value) {
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
