package com.cdkj.zhpay.dao;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.HzbTemplate;

//dao层 
public interface IHzbTemplateDAO extends IBaseDAO<HzbTemplate> {
    String NAMESPACE = IHzbTemplateDAO.class.getName().concat(".");

    public int update(HzbTemplate data);
}
