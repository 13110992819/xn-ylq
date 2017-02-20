package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.JewelTemplate;

public interface IJewelTemplateDAO extends IBaseDAO<JewelTemplate> {
    String NAMESPACE = IJewelTemplateDAO.class.getName().concat(".");

    public int update(JewelTemplate data);
}
