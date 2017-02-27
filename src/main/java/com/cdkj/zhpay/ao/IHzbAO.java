package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbHold;

public interface IHzbAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public void editHzb(Hzb data);

    public Object buyHzb(String userId, String hzbCode, String payType,
            String fromIp);

    public void paySuccess(String payGroup, String payCode, Long transAmount);

    public void putOnOffHzb(String userId);

    public HzbHold myHzb(String userId);

    public Paginable<Hzb> queryHzbPage(int start, int limit, Hzb condition);

    public List<Hzb> queryHzbList(Hzb condition);

    public Hzb getHzb(String code);

}
