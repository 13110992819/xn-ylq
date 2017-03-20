package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.domain.HzbHold;

public interface IHzbTemplateAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public void editHzb(HzbTemplate data);

    public Object buyHzb(String userId, String hzbCode, String payType,
            String fromIp);

    public void paySuccess(String payGroup, String payCode, Long transAmount);

    public void putOnOffHzb(String userId);

    public HzbHold myHzb(String userId);

    public Paginable<HzbTemplate> queryHzbPage(int start, int limit, HzbTemplate condition);

    public List<HzbTemplate> queryHzbList(HzbTemplate condition);

    public HzbTemplate getHzb(String code);

}
