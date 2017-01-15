package com.xnjr.mall.dao;

import java.util.List;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.HzbHold;

public interface IHzbHoldDAO extends IBaseDAO<HzbHold> {
    String NAMESPACE = IHzbHoldDAO.class.getName().concat(".");

    public int updateStatus(HzbHold data);

    public int updateRockNum(HzbHold data);

    public Long selectDistanceTotalCount(HzbHold condition);

    public List<HzbHold> selectDistanceList(HzbHold condition);

    public List<HzbHold> selectDistanceList(HzbHold condition, int start,
            int count);
}
