package com.cdkj.zhpay.dao;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.Hzb;

//dao层 
public interface IHzbDAO extends IBaseDAO<Hzb> {
    String NAMESPACE = IHzbDAO.class.getName().concat(".");

    public int update(Hzb data);
}
