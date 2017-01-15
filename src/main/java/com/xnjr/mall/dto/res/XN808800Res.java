package com.xnjr.mall.dto.res;

public class XN808800Res {

    // 店铺数量
    private String storeNum;

    // 福利月卡数量
    private String stockHoldNum;

    // 汇赚宝数量
    private String hzbHoldNum;

    public XN808800Res() {
    }

    public XN808800Res(long storeNum, long stockHoldNum, long hzbHoldNum) {
        this.storeNum = String.valueOf(storeNum);
        this.stockHoldNum = String.valueOf(stockHoldNum);
        this.hzbHoldNum = String.valueOf(hzbHoldNum);
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getStockHoldNum() {
        return stockHoldNum;
    }

    public void setStockHoldNum(String stockHoldNum) {
        this.stockHoldNum = stockHoldNum;
    }

    public String getHzbHoldNum() {
        return hzbHoldNum;
    }

    public void setHzbHoldNum(String hzbHoldNum) {
        this.hzbHoldNum = hzbHoldNum;
    }

}
