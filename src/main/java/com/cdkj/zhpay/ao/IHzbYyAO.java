package com.cdkj.zhpay.ao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.HzbYy;
import com.cdkj.zhpay.dto.res.XN808460Res;

@Component
public interface IHzbYyAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    /**
     * 汇赚宝摇一摇
     * @param userId
     * @param hzbCode
     * @param deviceNo
     * @return 
     * @create: 2017年3月21日 上午11:08:24 myb858
     * @history:
     */
    public XN808460Res doHzbYy(String userId, String hzbCode, String deviceNo);

    public Paginable<HzbYy> queryHzbYyPage(int start, int limit, HzbYy condition);

    public List<HzbYy> queryHzbYyList(HzbYy condition);

    public HzbYy getHzbYy(String code);

}
