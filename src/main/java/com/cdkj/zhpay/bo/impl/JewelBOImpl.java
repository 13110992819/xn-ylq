package com.cdkj.zhpay.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IJewelBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.dao.IJewelDAO;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EJewelStatus;
import com.cdkj.zhpay.exception.BizException;

/**
 * @author: xieyj 
 * @since: 2017年1月11日 下午6:12:17 
 * @history:
 */
@Component
public class JewelBOImpl extends PaginableBOImpl<Jewel> implements IJewelBO {
    @Autowired
    private IJewelDAO jewelDAO;

    @Override
    public boolean isJewelExist(String code) {
        Jewel condition = new Jewel();
        condition.setCode(code);
        if (jewelDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveJewel(JewelTemplate data) {
        String code = null;
        if (data != null) {
            Jewel jewel = new Jewel();
            code = OrderNoGenerater.generateM(EGeneratePrefix.JEWEL.getCode());
            jewel.setCode(code);
            jewel.setTemplateCode(data.getCode());
            jewel.setPeriods(data.getCurrentPeriods());
            jewel.setCurrency(data.getCurrency());
            jewel.setAmount(data.getAmount());
            jewel.setTotalNum(data.getTotalNum());
            jewel.setPrice(data.getPrice());
            jewel.setMaxInvestNum(data.getMaxInvestNum());
            jewel.setAdvText(data.getAdvText());
            jewel.setAdvPic(data.getAdvPic());
            jewel.setStatus(EJewelStatus.RUNNING.getCode());
            jewel.setCreateDatetime(new Date());
            jewel.setSystemCode(data.getSystemCode());
            jewelDAO.insert(jewel);
        }
        return code;
    }

    @Override
    public Jewel getJewel(String code) {
        Jewel data = null;
        if (StringUtils.isNotBlank(code)) {
            Jewel condition = new Jewel();
            condition.setCode(code);
            data = jewelDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "编号不存在");
            }
        }
        return data;
    }

    /** 
     * @see com.cdkj.zhpay.bo.IJewelBO#getJewelTotalCount(java.lang.String, com.cdkj.zhpay.enums.EJewelStatus)
     */
    @Override
    public Long getJewelTotalCount(String templateCode, EJewelStatus status) {
        Jewel condition = new Jewel();
        condition.setTemplateCode(templateCode);
        condition.setStatus(EJewelStatus.RUNNING.getCode());
        return jewelDAO.selectTotalCount(condition);
    }

    @Override
    public List<Jewel> queryJewelList(Jewel data) {
        return jewelDAO.selectList(data);
    }

    @Override
    public int refreshStatus(String code, String status) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Jewel data = new Jewel();
            data.setCode(code);
            data.setStatus(status);
            count = jewelDAO.updateStatus(data);
        }
        return count;
    }

    @Override
    public int refreshInvestInfo(String code, Integer investNum) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Jewel data = new Jewel();
            data.setCode(code);
            data.setInvestNum(investNum);
            count = jewelDAO.updateInvestInfo(data);
        }
        return count;
    }

    @Override
    public int refreshWinInfo(String code, String winNumber, String winUserId) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Jewel data = new Jewel();
            data.setCode(code);
            data.setWinNumber(winNumber);
            data.setWinUser(winUserId);
            data.setWinDatetime(new Date());
            data.setStatus(EJewelStatus.END.getCode());
            count = jewelDAO.updateWinInfo(data);
        }
        return count;
    }
}
