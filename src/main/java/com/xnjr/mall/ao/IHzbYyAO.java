package com.xnjr.mall.ao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.HzbYy;
import com.xnjr.mall.dto.res.XN808460Res;

@Component
public interface IHzbYyAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    /**
     * 汇赚宝摇一摇
     * @param userId
     * @param hzbHoldId
     * @param deviceNo
     * @return 
     * @create: 2017年1月10日 上午11:39:05 xieyj
     * @history:
     */
    public XN808460Res doHzbYy(String userId, Long hzbHoldId, String deviceNo);

    public Paginable<HzbYy> queryHzbYyPage(int start, int limit, HzbYy condition);

    public List<HzbYy> queryHzbYyList(HzbYy condition);

    public HzbYy getHzbYy(String code);

}
