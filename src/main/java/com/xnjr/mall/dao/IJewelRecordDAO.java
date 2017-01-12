package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.JewelRecord;

/**
 * @author: xieyj 
 * @since: 2017年1月11日 下午8:50:09 
 * @history:
 */
public interface IJewelRecordDAO extends IBaseDAO<JewelRecord> {
    String NAMESPACE = IJewelRecordDAO.class.getName().concat(".");

    public int update(JewelRecord data);

    public int updatePayAmount(JewelRecord data);

    public int updateLostInfo(JewelRecord data);

    public int updateTimes(JewelRecord data);
}
