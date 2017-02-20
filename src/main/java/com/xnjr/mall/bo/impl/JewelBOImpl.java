package com.xnjr.mall.bo.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IJewelBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.dao.IJewelDAO;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EJewelStatus;
import com.xnjr.mall.exception.BizException;

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
    public String saveJewel(Jewel data) {
        String code = null;
        if (data != null) {
            code = OrderNoGenerater.generateM(EGeneratePrefix.IEWEL.getCode());
            data.setCode(code);
            data.setStatus(EJewelStatus.RUNNING.getCode());
            jewelDAO.insert(data);
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
            data.setStatus(EJewelStatus.END.getCode());
            count = jewelDAO.updateWinInfo(data);
        }
        return count;
    }

}
