package com.cdkj.zhpay.dao;

import java.util.List;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.JewelRecord;

/**
 * @author: xieyj 
 * @since: 2017年1月11日 下午8:50:09 
 * @history:
 */
public interface IJewelRecordDAO extends IBaseDAO<JewelRecord> {
    String NAMESPACE = IJewelRecordDAO.class.getName().concat(".");

    public int update(JewelRecord data);

    public int updateReAddress(JewelRecord data);

    public int updateLostInfo(JewelRecord data);

    public JewelRecord selectMy(JewelRecord condition);

    public List<JewelRecord> selectMyList(JewelRecord condition);

    public List<JewelRecord> selectMyList(JewelRecord condition, int start,
            int count);
}
