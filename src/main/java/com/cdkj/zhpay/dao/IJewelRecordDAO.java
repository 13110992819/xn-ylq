package com.cdkj.zhpay.dao;

import java.util.List;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecord;

/**
 * @author: xieyj 
 * @since: 2017年1月11日 下午8:50:09 
 * @history:
 */
public interface IJewelRecordDAO extends IBaseDAO<JewelRecord> {
    String NAMESPACE = IJewelRecordDAO.class.getName().concat(".");

    public int updateStatus(JewelRecord data);

    public int updatePayStatus(JewelRecord data);

    public int updateLostInfo(JewelRecord data);

    public List<Jewel> selectMyJewelList(JewelRecord condition, int start,
            int count);

    public Long selectMyJewelTotalCount(JewelRecord condition);

    public Long getTotalAmount(JewelRecord condition);
}
