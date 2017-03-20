package com.cdkj.zhpay.dao;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.StockBack;

//dao层 
public interface IStockBackDAO extends IBaseDAO<StockBack> {
    String NAMESPACE = IStockBackDAO.class.getName().concat(".");
}
