package com.cdkj.zhpay.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IJewelRecordNumberBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.core.LuckyNumberGenerator;
import com.cdkj.zhpay.dao.IJewelRecordNumberDAO;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecordNumber;

/**
 * @author: xieyj 
 * @since: 2017年1月11日 下午6:29:08 
 * @history:
 */
@Component
public class JewelRecordNumberBOImpl extends PaginableBOImpl<JewelRecordNumber>
        implements IJewelRecordNumberBO {
    @Autowired
    private IJewelRecordNumberDAO jewelRecordNumberDAO;

    @Override
    public List<JewelRecordNumber> queryJewelRecordNumberList(
            JewelRecordNumber data) {
        return jewelRecordNumberDAO.selectList(data);
    }

    /**
     * @see com.cdkj.zhpay.bo.IJewelRecordNumberBO#queryJewelRecordNumberList(java.lang.String)
     */
    @Override
    public List<JewelRecordNumber> queryJewelRecordNumberList(
            String jewelRecordCode) {
        JewelRecordNumber data = new JewelRecordNumber();
        data.setRecordCode(jewelRecordCode);
        return jewelRecordNumberDAO.selectList(data);
    }

    @Override
    public List<String> queryExistNumbers(String jewelCode) {
        return jewelRecordNumberDAO.selectExistNumbers(jewelCode);
    }

    /** 
     * @see com.cdkj.zhpay.bo.IJewelRecordNumberBO#getJewelNumberTotalCount(java.lang.String)
     */
    @Override
    public Long getJewelNumberTotalCount(String userId, String jewelCode) {
        JewelRecordNumber condition = new JewelRecordNumber();
        condition.setUserId(userId);
        condition.setJewelCode(jewelCode);
        return jewelRecordNumberDAO.selectTotalCount(condition);
    }

    @Override
    public void saveJewelRecordNumber(String jewelRecordCode, Jewel jewel,
            Integer times) {
        String jewelCode = jewel.getCode();
        // 查询已有号码列表，自动生成号码
        List<String> existNumbers = queryExistNumbers(jewelCode);
        // existNumbers 将包含全部号码
        List<String> numbers = LuckyNumberGenerator.generateLuckyNumbers(
            SysConstants.JEWEL_NUM_RADIX, Long.valueOf(jewel.getTotalNum()),
            existNumbers, Long.valueOf(times));
        for (int i = 0; i < numbers.size(); i++) {
            JewelRecordNumber data = new JewelRecordNumber();
            data.setJewelCode(jewelCode);
            data.setRecordCode(jewelRecordCode);
            data.setNumber(numbers.get(i));
            data.setCompanyCode(jewel.getCompanyCode());
            data.setSystemCode(jewel.getSystemCode());
            jewelRecordNumberDAO.insert(data);
        }

    }
}
