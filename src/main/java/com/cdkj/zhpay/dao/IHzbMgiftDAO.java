package com.cdkj.zhpay.dao;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.HzbMgift;

//dao层 
public interface IHzbMgiftDAO extends IBaseDAO<HzbMgift> {
    String NAMESPACE = IHzbMgiftDAO.class.getName().concat(".");

    int updateStatus(HzbMgift data);

    int updateReciever(HzbMgift data);
}
