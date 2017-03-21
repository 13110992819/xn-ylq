package com.cdkj.zhpay.bo.impl;

import java.util.Date;

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
    public String saveJewel(JewelTemplate data) {
        String code = null;
        if (data != null) {
            Jewel jewel = new Jewel();
            code = OrderNoGenerater.generateM(EGeneratePrefix.JEWEL.getCode());
            jewel.setCode(code);
            jewel.setTemplateCode(data.getCode());
            jewel.setPeriods(data.getCurrentPeriods());
            jewel.setToAmount(data.getToAmount());
            jewel.setToCurrency(data.getToCurrency());

            jewel.setTotalNum(data.getTotalNum());
            jewel.setMaxNum(data.getMaxNum());
            jewel.setInvestNum(0);
            jewel.setFromAmount(data.getFromAmount());
            jewel.setFromCurrency(data.getFromCurrency());

            jewel.setSlogan(data.getSlogan());
            jewel.setAdvPic(data.getAdvPic());
            jewel.setStartDatetime(new Date());
            jewel.setStatus(EJewelStatus.RUNNING.getCode());
            jewel.setCompanyCode(data.getCompanyCode());

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
                throw new BizException("xn0000", "小目标不存在");
            }
        }
        return data;
    }

    @Override
    public boolean isExist(String templateCode, EJewelStatus status) {
        Jewel condition = new Jewel();
        condition.setTemplateCode(templateCode);
        condition.setStatus(status.getCode());
        Long count = jewelDAO.selectTotalCount(condition);
        if (count > 0) {
            return true;
        }
        return false;

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
