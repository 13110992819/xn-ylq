package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.StockHold;

public interface IStockHoldAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    public int addStockHold(StockHold data);

    public int dropStockHold(Long id);

    public Paginable<StockHold> queryStockHoldPage(int start, int limit,
            StockHold condition);

    public List<StockHold> queryStockHoldList(StockHold condition);

    public StockHold getStockHold(Long id);
}
