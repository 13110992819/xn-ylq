package com.cdkj.zhpay.bo.impl;

import java.util.Date;
import java.util.List;

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
import com.cdkj.zhpay.domain.JewelRecord;
import com.cdkj.zhpay.domain.JewelRecordNumber;
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
    public boolean isJewelRecordExist(String code) {
        JewelRecord condition = new JewelRecord();
        condition.setCode(code);
        if (jewelRecordDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveJewelRecord(String userId, String jewelCode,
            Integer times, Long amount, String ip, String payGroup,
            String systemCode) {
        String code = null;
        if (StringUtils.isNotBlank(userId)) {
            JewelRecord data = new JewelRecord();
            code = OrderNoGenerater.generateM(EGeneratePrefix.JEWEL_RECORD
                .getCode());
            data.setCode(code);
            data.setUserId(userId);
            data.setJewelCode(jewelCode);
            data.setTimes(times);
            data.setInvestDatetime(new Date());
            data.setPayAmount(amount);
            data.setStatus(EJewelRecordStatus.TO_PAY.getCode());
            data.setRemark("小目标待支付");
            data.setIp(ip);
            data.setPayGroup(payGroup);
            data.setSystemCode(systemCode);
            jewelRecordDAO.insert(data);
        }
        return code;
    }

    @Override
    public String saveJewelRecord(String userId, String jewelCode,
            Integer times, Long amount, String ip, String systemCode) {
        String code = null;
        if (StringUtils.isNotBlank(userId)) {
            JewelRecord data = new JewelRecord();
            code = OrderNoGenerater.generateM(EGeneratePrefix.JEWEL_RECORD
                .getCode());
            data.setCode(code);
            data.setUserId(userId);
            data.setJewelCode(jewelCode);
            data.setTimes(times);
            data.setRemark("已分配号码，待开奖");
            data.setPayAmount(amount);
            data.setIp(ip);
            data.setStatus(EJewelRecordStatus.LOTTERY.getCode());
            Date payDatetime = new Date();
            data.setInvestDatetime(payDatetime);
            data.setPayDatetime(DateUtil.getToday(DateUtil.DATA_TIME_PATTERN_7));
            data.setSystemCode(systemCode);
            jewelRecordDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeJewelRecord(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelRecord data = new JewelRecord();
            data.setCode(code);
            count = jewelRecordDAO.delete(data);
        }
        return count;
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
            data.setRemark(remark);
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
            data.setRemark(remark);
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
            data.setRemark(remark);
            count = jewelRecordDAO.updateLostInfo(data);
        }
        return count;
    }

    @Override
    public List<JewelRecord> queryMyJewelRecordList(JewelRecord condition) {
        return jewelRecordDAO.selectMyList(condition);
    }

    @Override
    public Paginable<JewelRecord> queryMyJewelRecordPage(int start,
            int pageSize, JewelRecord condition) {
        long totalCount = jewelRecordDAO.selectMyList(condition).size();
        Paginable<JewelRecord> page = new Page<JewelRecord>(start, pageSize,
            totalCount);
        List<JewelRecord> dataList = jewelRecordDAO.selectMyList(condition,
            page.getStart(), page.getPageSize());
        page.setList(dataList);
        return page;
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

    /** 
     * @see com.cdkj.zhpay.bo.IJewelRecordBO#checkTimes(java.lang.String, java.lang.String)
     */
    @Override
    public void checkTimes(String userId, String jewelCode,
            Integer maxInvestTimes, Integer times) {
        if (times <= 0) {
            throw new BizException("xn0000", "购买人次请选择");
        }
        // 验证最大投资人次
        Long numberTimes = jewelRecordNumberBO.getJewelNumberTotalCount(userId,
            jewelCode);
        if (maxInvestTimes != null && maxInvestTimes < (numberTimes + times)) {
            throw new BizException("xn0000", "投资人次超限，每个用户最多投资" + maxInvestTimes
                    + "人次");
        }
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
}
