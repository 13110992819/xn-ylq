package com.cdkj.zhpay.dao;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.StockHold;

//dao层 
public interface IStockHoldDAO extends IBaseDAO<StockHold> {
    String NAMESPACE = IStockHoldDAO.class.getName().concat(".");

    public int update(StockHold data);

    public int updateStatus(StockHold data);
}
