package com.cdkj.zhpay.dao;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.Jewel;

/**
 * @author: shan 
 * @since: 2016年12月19日 下午3:18:16 
 * @history:
 */
public interface IJewelDAO extends IBaseDAO<Jewel> {
    String NAMESPACE = IJewelDAO.class.getName().concat(".");

    public int updateStatus(Jewel data);

    public int updateInvestInfo(Jewel data);

    public int updateWinInfo(Jewel data);
}
