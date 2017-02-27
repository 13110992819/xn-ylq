package com.cdkj.zhpay.enums;

/**
 * @author: xieyj 
 * @since: 2016年12月29日 下午2:03:55 
 * @history:
 */
public enum ECategoryConfig {
    // 分类(B 汇赚宝规则，C摇一摇规则，D虚拟币规则，E定位规则，O其他规则)
    HZB("B", "汇赚宝规则"), YY("C", "摇一摇规则"), XNB("D", "虚拟币规则"), DW("E", "定位规则"), OTHER(
            "O", "系统参数");

    ECategoryConfig(String code, String value) {
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
