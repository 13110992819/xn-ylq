package com.cdkj.zhpay.dao;

import java.util.List;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.HzbHold;

public interface IHzbHoldDAO extends IBaseDAO<HzbHold> {
    String NAMESPACE = IHzbHoldDAO.class.getName().concat(".");

    public int updateStatus(HzbHold data);

    public int updateRockNum(HzbHold data);

    public int resetPeriodRockNum();

    public Long selectDistanceTotalCount(HzbHold condition);

    public List<HzbHold> selectDistanceList(HzbHold condition);

    public List<HzbHold> selectDistanceList(HzbHold condition, int start,
            int count);
}
