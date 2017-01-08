package com.xnjr.mall.enums;

/**
 * @author: xieyj 
 * @since: 2016年12月29日 下午2:03:55 
 * @history:
 */
public enum ECategoryConfig {
    // 分类(A 商品运费，B 分销规则，C摇一摇规则，D虚拟币规则，E定位规则)
    SP("A", "商品"), FXGZ("B", "分销规则"), YYYGZ("C", "摇一摇规则"), XNBGZ("D", "虚拟币规则"), DWGZ(
            "E", "定位规则"), Other("O", "系统参数");

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
