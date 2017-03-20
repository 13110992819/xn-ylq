package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.dto.res.XN808802Res;

public interface IHzbAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    public Paginable<Hzb> queryHzbHoldPage(int start, int limit,
            Hzb condition);

    public Paginable<Hzb> queryDistanceHzbHoldPage(int start, int limit,
            Hzb condition);

    public List<Hzb> queryHzbHoldList(Hzb condition);

    public Object queryDistanceHzbHoldList(Hzb condition, String userId,
            String deviceNo);

    public Hzb getHzbHold(Long id);

    public XN808802Res doGetHzbTotalData(String systemCode, String userId);

    public void doResetRockNumDaily();
}
