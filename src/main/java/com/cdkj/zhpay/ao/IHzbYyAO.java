package com.cdkj.zhpay.ao;

import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.HzbYy;
import com.cdkj.zhpay.dto.res.XN808460Res;

@Component
public interface IHzbYyAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    /**
     * 摇一摇领取奖励
     * @param userId
     * @param hzbCode
     * @param deviceNo
     * @return 
     * @create: 2017年3月22日 下午5:42:26 myb858
     * @history:
     */
    public XN808460Res doHzbYy(String userId, String hzbCode, String deviceNo);

    public Paginable<HzbYy> queryHzbYyPage(int start, int limit, HzbYy condition);

}
