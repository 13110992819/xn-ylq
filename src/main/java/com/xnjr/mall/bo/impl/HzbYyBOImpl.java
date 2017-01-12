package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IHzbYyBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.dao.IHzbYyDAO;
import com.xnjr.mall.domain.HzbYy;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EPrizeType;
import com.xnjr.mall.exception.BizException;

@Component
public class HzbYyBOImpl extends PaginableBOImpl<HzbYy> implements IHzbYyBO {

    @Autowired
    private IHzbYyDAO hzbYyDAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    public boolean isHzbYyExist(String code) {
        HzbYy condition = new HzbYy();
        condition.setCode(code);
        if (hzbYyDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveHzbYy(HzbYy data) {
        String code = null;
        if (data != null) {
            code = OrderNoGenerater.generateM(EGeneratePrefix.SHAKE.getCode());
            data.setCode(code);
            data.setCreateDatetime(new Date());
            hzbYyDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeHzbYy(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            HzbYy data = new HzbYy();
            data.setCode(code);
            count = hzbYyDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshHzbYy(HzbYy data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            // count = hzbYyDAO.update(data);
        }
        return count;
    }

    @Override
    public List<HzbYy> queryHzbYyList(HzbYy condition) {
        return hzbYyDAO.selectList(condition);
    }

    @Override
    public HzbYy getHzbYy(String code) {
        HzbYy data = null;
        if (StringUtils.isNotBlank(code)) {
            HzbYy condition = new HzbYy();
            condition.setCode(code);
            data = hzbYyDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "该摇摇记录不存在");
            }
        }
        return data;
    }

    /**
     * 判断是否第三次且前面两次没有摇到红包币
     * @see com.xnjr.mall.bo.IHzbYyBO#isThirdYyNoHB(java.lang.String)
     */
    @Override
    public boolean isThirdYyNoHB(String userId) {
        boolean result = false;
        HzbYy condition = new HzbYy();
        condition.setUserId(userId);
        int count = hzbYyDAO.selectTotalCount(condition).intValue();
        int start = (count - 2) < 0 ? 0 : (count - 2);
        List<HzbYy> dataList = hzbYyDAO.selectList(condition, start, count);
        if (CollectionUtils.isNotEmpty(dataList)) {
            if (count % 3 == 2) {
                boolean haveHbb = false;
                for (HzbYy hzbYy : dataList) {
                    if (EPrizeType.HBB.getCode().equals(hzbYy.getType())) {
                        haveHbb = true;
                        break;
                    }
                }
                if (!haveHbb) {
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public void checkHzbYyCondition(String systemCode, String userId,
            String deviceNo) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        // 限制规则：
        // 一个账号一天只能摇n次
        HzbYy yyCondition = new HzbYy();
        yyCondition.setHzbHoldId(null);
        yyCondition.setUserId(userId);
        yyCondition.setCreateDatetimeStart(DateUtil.getTodayStart());
        yyCondition.setCreateDatetimeEnd(DateUtil.getTodayEnd());
        String USER_DAY_MAX_COUNT = rateMap
            .get(SysConstants.USER_DAY_MAX_COUNT);
        int userDayMaxCount = SysConstants.USER_DAY_MAX_COUNT_DEF;
        if (StringUtils.isNotBlank(USER_DAY_MAX_COUNT)) {
            userDayMaxCount = Integer.valueOf(USER_DAY_MAX_COUNT);
        }
        if (getTotalCount(yyCondition) >= userDayMaxCount) {
            throw new BizException("xn0000", "您的账号今天已摇" + userDayMaxCount
                    + "次，请明天再来哦");
        }
        // 一个手机一天只能摇n次
        yyCondition.setUserId(null);
        yyCondition.setDeviceNo(deviceNo);
        String DEVICE_DAY_MAX_COUNT = rateMap
            .get(SysConstants.DEVICE_DAY_MAX_COUNT);
        int DeviceDayMaxCount = SysConstants.DEVICE_DAY_MAX_COUNT_DEF;
        if (StringUtils.isNotBlank(DEVICE_DAY_MAX_COUNT)) {
            DeviceDayMaxCount = Integer.valueOf(DEVICE_DAY_MAX_COUNT);
        }
        if (getTotalCount(yyCondition) >= DeviceDayMaxCount) {
            throw new BizException("xn0000", "您的手机今天已摇" + DeviceDayMaxCount
                    + "次，请明天再来哦");
        }
    }

    @Override
    public void checkHzbYyCondition(String systemCode, Long hzbHoldId,
            String userId, String deviceNo) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        // 限制规则：
        // 一个手机一天只能摇n次
        // 一个账号一天只能摇n次
        // 传染性
        // 一个汇赚宝权限，一天最多可以被摇n次
        HzbYy yyCondition = new HzbYy();
        yyCondition.setHzbHoldId(hzbHoldId);
        String HZB_MAX_NUM = rateMap.get(SysConstants.HZB_MAX_NUM);
        int hzbMaxNum = SysConstants.HZB_MAX_NUM_DEF;
        if (StringUtils.isNotBlank(HZB_MAX_NUM)) {
            hzbMaxNum = Integer.valueOf(HZB_MAX_NUM);
        }
        if (getTotalCount(yyCondition) >= hzbMaxNum) {
            throw new BizException("xn0000", "该汇赚宝已摇次数超出限制次数，请选择其他汇赚宝");
        }
        checkHzbYyCondition(systemCode, userId, deviceNo);
    }
}