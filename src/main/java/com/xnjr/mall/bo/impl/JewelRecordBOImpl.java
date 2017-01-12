package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IJewelRecordBO;
import com.xnjr.mall.bo.IJewelRecordNumberBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.dao.IJewelRecordDAO;
import com.xnjr.mall.domain.JewelRecord;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EJewelRecordStatus;
import com.xnjr.mall.exception.BizException;

/**
 * 
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
            code = OrderNoGenerater.generateM(EGeneratePrefix.IEWEL_RECORD
                .getCode());
            data.setCode(code);
            data.setCreateDatetime(new Date());
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
                throw new BizException("xn0000", "夺宝记录不存在");
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
    public int refreshPayAmount(String code, Long payAmount1, Long payAmount2,
            Long payAmount3) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelRecord jewelRecord = getJewelRecord(code);
            JewelRecord data = new JewelRecord();
            data.setCode(code);
            data.setPayAmount1(jewelRecord.getPayAmount1() + payAmount1);
            data.setPayAmount2(jewelRecord.getPayAmount2() + payAmount2);
            data.setPayAmount3(jewelRecord.getPayAmount3() + payAmount3);
            data.setPayDatetime(new Date());
            data.setRemark("追加号码");
            count = jewelRecordDAO.updatePayAmount(data);
        }
        return count;
    }

    @Override
    public int refreshPaySuccess(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelRecord data = new JewelRecord();
            data.setCode(code);
            data.setStatus(EJewelRecordStatus.LOTTERY.getCode());
            data.setPayDatetime(new Date());
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
    public int refreshTimes(String code, Integer times) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelRecord data = new JewelRecord();
            data.setCode(code);
            data.setTimes(times);
            count = jewelRecordDAO.updateTimes(data);
        }
        return count;
    }
}
