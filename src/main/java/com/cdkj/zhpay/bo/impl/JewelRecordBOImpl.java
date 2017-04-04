package com.cdkj.zhpay.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IJewelRecordBO;
import com.cdkj.zhpay.bo.IJewelRecordNumberBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.base.Page;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.dao.IJewelRecordDAO;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecord;
import com.cdkj.zhpay.domain.JewelRecordNumber;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EJewelRecordStatus;
import com.cdkj.zhpay.exception.BizException;

/**
 * @author: shan 
 * @since: 2016年12月20日 下午12:53:21 
 * @history:
 */
@Component
public class JewelRecordBOImpl extends PaginableBOImpl<JewelRecord> implements
        IJewelRecordBO {
    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IJewelRecordDAO jewelRecordDAO;

    @Autowired
    private IJewelRecordNumberBO jewelRecordNumberBO;

    @Override
    public String saveJewelRecord(String userId, String jewelCode,
            Integer times, Long amount, String ip, String payGroup,
            String systemCode) {
        String code = null;
        if (StringUtils.isNotBlank(userId)) {
            JewelRecord data = new JewelRecord();
            Date now = new Date();
            code = OrderNoGenerater.generateM(EGeneratePrefix.JEWEL_RECORD
                .getCode());
            data.setCode(code);
            data.setUserId(userId);
            data.setJewelCode(jewelCode);
            data.setInvestDatetime(now);
            data.setTimes(times);

            data.setIp(ip);
            data.setStatus(EJewelRecordStatus.LOTTERY.getCode());
            data.setPayAmount(amount);
            data.setPayDatetime(DateUtil.getToday(DateUtil.DATA_TIME_PATTERN_7));
            data.setPayGroup(payGroup);

            data.setCompanyCode(systemCode);
            data.setSystemCode(systemCode);
            jewelRecordDAO.insert(data);
        }
        return payGroup;
    }

    @Override
    public JewelRecord getJewelRecord(String code) {
        JewelRecord data = null;
        if (StringUtils.isNotBlank(code)) {
            JewelRecord condition = new JewelRecord();
            condition.setCode(code);
            data = jewelRecordDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "夺宝购买记录不存在");
            }
        }
        return data;
    }

    @Override
    public List<JewelRecord> queryJewelRecordList(JewelRecord data) {
        return jewelRecordDAO.selectList(data);
    }

    @Override
    public int refreshStatus(String code, String status, String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelRecord data = new JewelRecord();
            data.setCode(code);
            data.setStatus(status);
            count = jewelRecordDAO.updateStatus(data);
        }
        return count;
    }

    @Override
    public int refreshPayStatus(String code, String status, String payCode,
            String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelRecord data = new JewelRecord();
            data.setCode(code);
            data.setStatus(status);
            data.setPayCode(payCode);
            data.setPayDatetime(DateUtil.getToday(DateUtil.DATA_TIME_PATTERN_7));
            count = jewelRecordDAO.updatePayStatus(data);
        }
        return count;
    }

    @Override
    public int refreshLostInfo(String code, String jewelCode, String status,
            String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelRecord data = new JewelRecord();
            data.setCode(code);
            data.setJewelCode(jewelCode);
            data.setStatus(status);
            count = jewelRecordDAO.updateLostInfo(data);
        }
        return count;
    }

    @Override
    public Long getLastRecordsTimes(String jewelCode) {
        JewelRecord condition = new JewelRecord();
        condition.setJewelCode(jewelCode);
        condition.setStatus(EJewelRecordStatus.LOTTERY.getCode());
        Long totalCount = jewelRecordDAO.selectTotalCount(condition);
        Long start = 0L;
        Long lastInvestRecords = Long
            .valueOf(PropertiesUtil.Config.LAST_INVEST_RECORDS);
        if (lastInvestRecords == null || lastInvestRecords == 0L) {
            lastInvestRecords = 5L;
        }
        Long timesNum = lastInvestRecords;
        if (totalCount >= timesNum) {
            start = totalCount - timesNum;
        } else {
            start = 0L;
        }
        List<JewelRecord> list = jewelRecordDAO.selectList(condition,
            start.intValue(), timesNum.intValue());
        Long outRandomA = 0L;
        for (JewelRecord jewelRecord : list) {
            // 获取时分秒毫秒的合集
            String payDate = jewelRecord.getPayDatetime();
            Date payDatetime = DateUtil.strToDate(payDate,
                DateUtil.DATA_TIME_PATTERN_7);
            outRandomA += Long.valueOf(DateUtil.dateToStr(payDatetime,
                DateUtil.DATA_TIME_PATTERN_5).substring(8));
        }
        return outRandomA;
    }

    @Override
    public void checkTimes(User user, Jewel jewel, Integer times) {
        if (times <= 0) {
            throw new BizException("xn0000", "购买人次请选择");
        }
        // 验证最大投资人次
        Long numberTimes = jewelRecordNumberBO.getJewelNumberTotalCount(
            user.getUserId(), jewel.getCode());
        // 单人最大投资次数
        Integer maxNum = jewel.getMaxNum();
        if (maxNum != null && maxNum < (numberTimes + times)) {
            throw new BizException("xn0000", "投资人次超限，每个用户最多投资" + maxNum + "人次");
        }
        // 判断是否大于剩余购买份数
        if (jewel.getTotalNum() - jewel.getInvestNum() < times) {
            throw new BizException("xn0000", "剩余份数不足");
        }
    }

    @Override
    public Paginable<Jewel> queryMyJewelRecordPage(int start, int pageSize,
            String userId) {
        JewelRecord condition = new JewelRecord();
        condition.setUserId(userId);
        long totalCount = jewelRecordDAO.selectMyJewelTotalCount(condition);
        Paginable<Jewel> page = new Page<Jewel>(start, pageSize, totalCount);
        List<Jewel> dataList = jewelRecordDAO.selectMyJewelList(condition,
            page.getStart(), page.getPageSize());
        page.setList(dataList);
        return page;
    }

    /**
     * @see com.cdkj.zhpay.bo.IJewelRecordBO#getWinJewelRecord(java.lang.String, java.lang.String)
     */
    @Override
    public JewelRecord getWinJewelRecord(String jewelCode, String luckyNumber) {
        // 根据幸运号码找到夺宝记录
        JewelRecordNumber condition = new JewelRecordNumber();
        condition.setJewelCode(jewelCode);
        condition.setNumber(luckyNumber);
        JewelRecordNumber jewelRecordNumber = jewelRecordNumberBO
            .queryJewelRecordNumberList(condition).get(0);
        JewelRecord jrCondition = new JewelRecord();
        jrCondition.setCode(jewelRecordNumber.getRecordCode());
        return jewelRecordDAO.select(jrCondition);
    }

    @Override
    public Long getTotalAmount(String payGroup) {
        JewelRecord data = new JewelRecord();
        data.setPayGroup(payGroup);
        return jewelRecordDAO.getTotalAmount(data);
    }

    @Override
    public JewelRecord getJewelRecordByPayGroup(String payGroup) {
        JewelRecord condition = new JewelRecord();
        condition.setPayGroup(payGroup);
        List<JewelRecord> list = this.queryJewelRecordList(condition);
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        return list.get(0);
    }
}
