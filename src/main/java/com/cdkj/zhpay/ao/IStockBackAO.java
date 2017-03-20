package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.StockBack;

public interface IStockBackAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    public int addStockBack(StockBack data);

    public Paginable<StockBack> queryStockBackPage(int start, int limit,
            StockBack condition);

    public List<StockBack> queryStockBackList(StockBack condition);

    public StockBack getStockBack(Long id);

}
