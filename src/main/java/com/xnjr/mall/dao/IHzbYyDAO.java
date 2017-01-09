package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.HzbYy;

//dao层 
public interface IHzbYyDAO extends IBaseDAO<HzbYy> {
    String NAMESPACE = IHzbYyDAO.class.getName().concat(".");
}
