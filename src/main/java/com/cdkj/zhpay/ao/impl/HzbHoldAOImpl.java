package com.cdkj.zhpay.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IHzbHoldAO;
import com.cdkj.zhpay.bo.IHzbHoldBO;
import com.cdkj.zhpay.bo.IHzbYyBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.domain.HzbHold;
import com.cdkj.zhpay.dto.res.XN805901Res;

@Service
public class HzbHoldAOImpl implements IHzbHoldAO {

    @Autowired
    private IHzbHoldBO hzbHoldBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IHzbYyBO hzbYyBO;

    @Autowired
    private IUserBO userBO;

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
    @Transactional
    public Object queryDistanceHzbHoldList(HzbHold condition, String userId,
            String deviceNo) {
        XN805901Res userRes = userBO.getRemoteUser(userId, userId);
        hzbYyBO.checkHzbYyCondition(userRes.getSystemCode(), userId, deviceNo);
        // 设置距离
        String distance = sysConfigBO.getConfigValue(null, null, null,
            SysConstants.HZB_DISTANCE);
        if (StringUtils.isBlank(distance)) {
            // 默认1000米
            distance = SysConstants.HZB_DISTANCE_DEF;
        }
        condition.setDistance(distance);
        // 设置最多被摇次数
        Integer periodRockNum = SysConstants.HZB_YY_DAY_MAX_COUNT_DEF;
        String periodRockNumString = sysConfigBO.getConfigValue(null, null,
            null, SysConstants.HZB_YY_DAY_MAX_COUNT);
        if (StringUtils.isNotBlank(periodRockNumString)) {
            // 默认900次
            periodRockNum = Integer.valueOf(periodRockNumString);
        }
        condition.setPeriodRockNum(periodRockNum);
        // 设置数量
        String hzbMaxNumStr = sysConfigBO.getConfigValue(null, null, null,
            SysConstants.HZB_MAX_NUM);
        int hzbMaxNum = SysConstants.HZB_MAX_NUM_DEF;
        if (StringUtils.isNotBlank(hzbMaxNumStr)) {
            hzbMaxNum = Integer.valueOf(hzbMaxNumStr);
        }
        List<HzbHold> list = hzbHoldBO.queryDistanceHzbHoldList(condition);
        if (CollectionUtils.isNotEmpty(list) && list.size() > hzbMaxNum) {
            list = list.subList(0, hzbMaxNum);
        }
        for (HzbHold hzbHold : list) {
            hzbHold.setShareUrl(PropertiesUtil.Config.SHARE_URL);
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

    @Override
    public void doResetRockNumDaily() {
        hzbHoldBO.resetPeriodRockNum();
    }
}
