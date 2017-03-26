package com.cdkj.zhpay.dto.res;

public class XN000003Res {

    // 汇赚宝数量
    private String hzbHoldNum;

    public XN000003Res() {
    }

    public XN000003Res(long hzbHoldNum) {
        this.hzbHoldNum = String.valueOf(hzbHoldNum);
    }

    public String getHzbHoldNum() {
        return hzbHoldNum;
    }

    public void setHzbHoldNum(String hzbHoldNum) {
        this.hzbHoldNum = hzbHoldNum;
    }

}
