package com.cdkj.zhpay.bo.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IJewelRecordNumberBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.dao.IJewelRecordNumberDAO;
import com.cdkj.zhpay.domain.JewelRecordNumber;
import com.cdkj.zhpay.exception.BizException;

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
    public boolean isJewelRecordNumberExist(Long id) {
        JewelRecordNumber condition = new JewelRecordNumber();
        condition.setId(id);
        if (jewelRecordNumberDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveJewelRecordNumber(JewelRecordNumber data) {
        String code = null;
        if (data != null) {
            jewelRecordNumberDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeJewelRecordNumber(Long id) {
        int count = 0;
        if (StringUtils.isNotBlank(String.valueOf(id))) {
            JewelRecordNumber data = new JewelRecordNumber();
            data.setId(id);
            count = jewelRecordNumberDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshJewelRecordNumber(JewelRecordNumber data) {
        int count = 0;
        if (StringUtils.isNotBlank(String.valueOf(data.getId()))) {
            count = jewelRecordNumberDAO.update(data);
        }
        return count;
    }

    @Override
    public JewelRecordNumber getJewelRecordNumber(Long id) {
        JewelRecordNumber data = null;
        if (StringUtils.isNotBlank(String.valueOf(id))) {
            JewelRecordNumber condition = new JewelRecordNumber();
            condition.setId(id);
            data = jewelRecordNumberDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "编号不存在");
            }
        }
        return data;
    }

    @Override
    public List<JewelRecordNumber> queryJewelRecordNumberList(
            JewelRecordNumber data) {
        return jewelRecordNumberDAO.selectList(data);
    }

    /** （）
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

}
