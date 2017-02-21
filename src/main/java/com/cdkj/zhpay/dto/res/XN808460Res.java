package com.cdkj.zhpay.dto.res;

public class XN808460Res {
    // 类型
    private String type;

    // 数量
    private String quantity;

    public XN808460Res() {
    }

    public XN808460Res(String type, String quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
