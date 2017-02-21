package com.cdkj.zhpay.dao;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.JewelTemplate;

public interface IJewelTemplateDAO extends IBaseDAO<JewelTemplate> {
    String NAMESPACE = IJewelTemplateDAO.class.getName().concat(".");

    public int update(JewelTemplate data);

    public int updateStatus(JewelTemplate data);

    public int updatePeriods(JewelTemplate data);
}
