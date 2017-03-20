package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.StockBack;

public interface IStockBackBO extends IPaginableBO<StockBack> {

    public boolean isStockBackExist(Long id);

    public int saveStockBack(StockBack data);

    public List<StockBack> queryStockBackList(StockBack condition);

    public StockBack getStockBack(Long id);

}
