package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.HzbHold;

public interface IHzbHoldBO extends IPaginableBO<HzbHold> {

    public boolean isHzbHoldExist(Long id);

    public int saveHzbHold(HzbHold data);

    public int removeHzbHold(Long id);

    public int refreshStatus(Long id, String status);

    public List<HzbHold> queryHzbHoldList(HzbHold condition);

    public List<HzbHold> queryDistanceHzbHoldList(HzbHold condition);

    public Paginable<HzbHold> queryDistancePaginable(int start, int pageSize,
            HzbHold condition);

    public HzbHold getHzbHold(Long id);

}
