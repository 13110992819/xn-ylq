package com.cdkj.zhpay.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IJewelRecordBO;
import com.cdkj.zhpay.bo.IJewelRecordNumberBO;
import com.cdkj.zhpay.bo.base.Page;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.dao.IJewelRecordDAO;
import com.cdkj.zhpay.domain.JewelRecord;
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
    public String saveJewelRecord(JewelRecord data) {
        String code = null;
        if (data != null) {
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
            count = jewelRecordDAO.update(data);
        }
        return count;
    }

    @Override
    public int refreshPaySuccess(String code, String status, String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelRecord data = new JewelRecord();
            data.setCode(code);
            data.setStatus(status);
            data.setPayDatetime(new Date());
            data.setRemark(remark);
            count = jewelRecordDAO.update(data);
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
    public Long getLastRecordsTimes(String jewelCode, Date curCreateDatetime) {
        Long outRandomA = 0L;
        Long curCount = 0L;
        if (curCreateDatetime != null) {
            curCount = 1L;
            outRandomA = curCreateDatetime.getTime();
            System.out.println("outRandomA:" + curCreateDatetime.getTime());
        }
        JewelRecord condition = new JewelRecord();
        condition.setJewelCode(jewelCode);
        condition.setStatus(EJewelRecordStatus.LOTTERY.getCode());
        Long totalCount = jewelRecordDAO.selectTotalCount(condition);
        Long start = 0L;
        Long timesNum = 5 - curCount;
        if (totalCount >= timesNum) {
            start = totalCount - timesNum;
        } else {
            start = 0L;
        }
        List<JewelRecord> list = jewelRecordDAO.selectList(condition,
            start.intValue(), timesNum.intValue());
        for (JewelRecord jewelRecord : list) {
            outRandomA += jewelRecord.getInvestDatetime().getTime();
            System.out.println("outRandomA:" + String.valueOf(outRandomA));
        }
        System.out.println("totalOutRandomA:" + String.valueOf(outRandomA));
        return outRandomA;
    }
}
