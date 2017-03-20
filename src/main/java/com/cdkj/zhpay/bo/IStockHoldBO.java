package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.StockHold;

public interface IStockHoldBO extends IPaginableBO<StockHold> {

    public boolean isStockHoldExist(Long id);

    public int saveStockHold(StockHold data);

    public int refreshStockHold(StockHold data);

    public int removeStockHold(Long id);

    public List<StockHold> queryStockHoldList(StockHold condition);

    public StockHold getStockHold(Long id);

    public StockHold getStockHoldByUser(String userId);

    public int refreshStatus(Long id, String status);

}
