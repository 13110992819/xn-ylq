package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.HzbHold;

public interface IHzbHoldBO extends IPaginableBO<HzbHold> {

    public boolean isHzbHoldExist(Long id);

    public boolean isHzbHoldExistByUser(String userId);

    public int saveHzbHold(HzbHold data);

    public int removeHzbHold(Long id);

    public int refreshStatus(Long id, String status);

    public int refreshRockNum(Long id, Integer periodRockNum,
            Integer totalRockNum);

    public void resetPeriodRockNum();

    public List<HzbHold> queryHzbHoldList(HzbHold condition);

    public List<HzbHold> queryDistanceHzbHoldList(HzbHold condition);

    public Paginable<HzbHold> queryDistancePaginable(int start, int pageSize,
            HzbHold condition);

    public HzbHold getHzbHold(Long id);
}
