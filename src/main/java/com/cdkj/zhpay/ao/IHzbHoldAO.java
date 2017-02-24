package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.HzbHold;
import com.cdkj.zhpay.dto.res.XN808802Res;

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

    public XN808802Res doGetHzbTotalData(String systemCode, String userId);
}
