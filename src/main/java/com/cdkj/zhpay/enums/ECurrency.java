/**
 * @Title ECurrency.java 
 * @Package com.ibis.account.enums 
 * @Description 
 * @author miyb  
 * @date 2015-3-15 下午4:41:06 
 * @version V1.0   
 */
package com.cdkj.zhpay.enums;

import java.util.HashMap;
import java.util.Map;

import com.cdkj.zhpay.exception.BizException;

/**
 * @author: xieyj 
 * @since: 2016年12月24日 下午1:51:38 
 * @history:
 */
public enum ECurrency {
    CNY("CNY", "人民币"), XNB("XNB", "虚拟币"), CGB("CGB", "菜狗币"), FRB("FRB", "分润币"), GXJL(
            "GXJL", "贡献值"), QBB("QBB", "钱包币"), GWB("GWB", "购物币"), HBB("HBB",
            "红包币"), HBYJ("HBYJ", "红包业绩");

    public static Map<String, ECurrency> getResultMap() {
        Map<String, ECurrency> map = new HashMap<String, ECurrency>();
        for (ECurrency currency : ECurrency.values()) {
            map.put(currency.getCode(), currency);
        }
        return map;
    }

    public static ECurrency getECurrency(String code) {
        Map<String, ECurrency> map = getResultMap();
        ECurrency currency = map.get(code);
        if (null == currency) {
            throw new BizException("xn0000", "币种不存在");
        }
        return currency;
    }

    ECurrency(String code, String value) {
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
