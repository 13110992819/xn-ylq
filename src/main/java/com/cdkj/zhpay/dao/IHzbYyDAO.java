package com.cdkj.zhpay.dao;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.HzbYy;

//dao层 
public interface IHzbYyDAO extends IBaseDAO<HzbYy> {
    String NAMESPACE = IHzbYyDAO.class.getName().concat(".");
}
