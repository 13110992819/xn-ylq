package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.HzbMgift;

//dao层 
public interface IHzbMgiftDAO extends IBaseDAO<HzbMgift> {
    String NAMESPACE = IHzbMgiftDAO.class.getName().concat(".");

    int updateStatus(HzbMgift data);

    int updateReciever(HzbMgift data);
}
