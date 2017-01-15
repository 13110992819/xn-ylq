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

    /**
     * 重置用户汇赚宝周期内被摇次数（暂定周期为一天）
     *  
     * @create: 2017年1月15日 下午3:52:47 haiqingzheng
     * @history:
     */
    public void clearPeriodRockNum();
}
