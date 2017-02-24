package com.cdkj.zhpay.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IHzbHoldAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IHzbHoldBO;
import com.cdkj.zhpay.bo.IHzbMgiftBO;
import com.cdkj.zhpay.bo.IHzbYyBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.domain.HzbHold;
import com.cdkj.zhpay.domain.HzbMgift;
import com.cdkj.zhpay.dto.res.XN802527Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.dto.res.XN808802Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.ECurrency;

@Service
public class HzbHoldAOImpl implements IHzbHoldAO {

    @Autowired
    private IHzbHoldBO hzbHoldBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IHzbYyBO hzbYyBO;

    @Autowired
    private IHzbMgiftBO hzbMgiftBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

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
        List<HzbHold> list = hzbHoldBO.queryDistanceHzbHoldList(condition);
        // 截取数量
        String hzbMaxNumStr = sysConfigBO.getConfigValue(null, null, null,
            SysConstants.HZB_MAX_NUM);
        int hzbMaxNum = SysConstants.HZB_MAX_NUM_DEF;
        if (StringUtils.isNotBlank(hzbMaxNumStr)) {
            hzbMaxNum = Integer.valueOf(hzbMaxNumStr);
        }
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

    /** 
     * @see com.cdkj.zhpay.ao.IHzbHoldAO#doGetHzbTotalData(java.lang.String, java.lang.String)
     */
    @Override
    public XN808802Res doGetHzbTotalData(String systemCode, String userId) {
        HzbHold hzbHold = hzbHoldBO.getHzbHold(userId);
        XN808802Res res = new XN808802Res();
        Date todayStart = DateUtil.getTodayStart();
        Date todayEnd = DateUtil.getTodayEnd();
        Date yesterdayEnd = DateUtil.getRelativeDate(todayStart, -1);
        // 历史被摇一摇次数
        Long historyYyTimes = hzbYyBO.getTotalHzbYyCount(null, yesterdayEnd,
            hzbHold.getId());
        res.setHistoryYyTimes(historyYyTimes);
        // 今日被摇一摇次数
        Long todayYyTimes = hzbYyBO.getTotalHzbYyCount(todayStart, todayEnd,
            hzbHold.getId());
        res.setTodayYyTimes(todayYyTimes);
        // 总的摇一摇分成
        XN802527Res accountRes = accountBO.doGetBizTotalAmount(systemCode,
            userId, ECurrency.HBYJ.getCode(), EBizType.AJ_YYFC.getCode());
        res.setYyTotalAmount(accountRes.getAmount());
        // 历史发一发次数
        Long historyMgiftTimes = hzbMgiftBO.getReceiveHzbMgiftCount(null,
            yesterdayEnd, userId);
        res.setHistoryHbTimes(historyMgiftTimes);
        // 今日发一发次数
        Long todayMgiftTimes = hzbMgiftBO.getReceiveHzbMgiftCount(todayStart,
            todayEnd, userId);
        res.setTodayHbTimes(todayMgiftTimes);
        // 总的发一发福利
        List<HzbMgift> hzbMgiftList = hzbMgiftBO.queryReceiveHzbMgift(null,
            null, userId);
        Long ffTotalHbAmount = 0L;
        for (HzbMgift hzbMgift : hzbMgiftList) {
            ffTotalHbAmount += hzbMgift.getOwnerAmount();
        }
        res.setFfTotalHbAmount(ffTotalHbAmount);
        return res;
    }
}
