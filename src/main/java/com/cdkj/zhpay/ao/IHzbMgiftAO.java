package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.HzbMgift;

public interface IHzbMgiftAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public void doDailyHzbMgift();

    public void doSendHzbMgift(String hzbMgiftCode);

    public void doReceiveHzbMgift(String userId, String hzbMgiftCode);

    public Paginable<HzbMgift> queryHzbMgiftPage(int start, int limit,
            HzbMgift condition);

    public List<HzbMgift> queryHzbMgiftList(HzbMgift condition);

    public HzbMgift getHzbMgift(String code);

}
