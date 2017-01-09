package com.xnjr.mall.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IHzbHoldAO;
import com.xnjr.mall.bo.IHzbHoldBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.domain.HzbHold;

@Service
public class HzbHoldAOImpl implements IHzbHoldAO {

    @Autowired
    private IHzbHoldBO hzbHoldBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    public Paginable<HzbHold> queryHzbHoldPage(int start, int limit,
            HzbHold condition) {
        return hzbHoldBO.getPaginable(start, limit, condition);
    }

    // 分页无法统计，暂时不用
    @Override
    public Paginable<HzbHold> queryDistanceHzbHoldPage(int start, int limit,
            HzbHold condition) {
        String distance = sysConfigBO.getConfigValue(null, null, null,
            SysConstants.HZB_DISTANCE);
        if (StringUtils.isBlank(distance)) {
            // 默认1000米
            distance = SysConstants.HZB_DISTANCE_DEF;
        }
        condition.setDistance(distance);
        return hzbHoldBO.queryDistancePaginable(start, limit, condition);
    }

    @Override
    public List<HzbHold> queryDistanceHzbHoldList(HzbHold condition) {
        String distance = sysConfigBO.getConfigValue(null, null, null,
            SysConstants.HZB_DISTANCE);
        if (StringUtils.isBlank(distance)) {
            // 默认1000米
            distance = SysConstants.HZB_DISTANCE_DEF;
        }
        condition.setDistance(distance);
        List<HzbHold> list = hzbHoldBO.queryDistanceHzbHoldList(condition);
        if (CollectionUtils.isNotEmpty(list) && list.size() > 100) {
            list = list.subList(0, 100);
        }
        return list;
    }

    @Override
    public List<HzbHold> queryHzbHoldList(HzbHold condition) {
        return hzbHoldBO.queryHzbHoldList(condition);
    }

    @Override
    public HzbHold getHzbHold(Long id) {
        return hzbHoldBO.getHzbHold(id);
    }
}
