package com.cdkj.zhpay.dto.res;

public class XN808800Res {

    // 汇赚宝数量
    private String hzbHoldNum;

    public XN808800Res() {
    }

    public XN808800Res(long hzbHoldNum) {
        this.hzbHoldNum = String.valueOf(hzbHoldNum);
    }

    public String getHzbHoldNum() {
        return hzbHoldNum;
    }

    public void setHzbHoldNum(String hzbHoldNum) {
        this.hzbHoldNum = hzbHoldNum;
    }

}
