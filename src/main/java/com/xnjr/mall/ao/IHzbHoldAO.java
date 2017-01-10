package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.HzbHold;

public interface IHzbHoldAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    public Paginable<HzbHold> queryHzbHoldPage(int start, int limit,
            HzbHold condition);

    public Paginable<HzbHold> queryDistanceHzbHoldPage(int start, int limit,
            HzbHold condition);

    public List<HzbHold> queryHzbHoldList(HzbHold condition);

    public Object queryDistanceHzbHoldList(HzbHold condition, String userId,
            String deviceNo);

    public HzbHold getHzbHold(Long id);
}
