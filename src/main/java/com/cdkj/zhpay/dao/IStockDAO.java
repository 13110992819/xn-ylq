package com.cdkj.zhpay.dao;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.Stock;

public interface IStockDAO extends IBaseDAO<Stock> {
    String NAMESPACE = IStockDAO.class.getName().concat(".");

    public int updateStatus(Stock data);
}
