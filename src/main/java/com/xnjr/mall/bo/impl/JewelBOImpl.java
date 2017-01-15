package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IJewelBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.common.DateUtil;
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
            data.setStatus(EJewelStatus.NEW.getCode());
            jewelDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeJewwl(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Jewel data = new Jewel();
            data.setCode(code);
            count = jewelDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshJewel(Jewel data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            count = jewelDAO.update(data);
        }
        return count;
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
    public int refreshStatus(String code, String status, String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Jewel data = new Jewel();
            data.setCode(code);
            data.setStatus(status);
            data.setUpdateDatetime(new Date());
            count = jewelDAO.updateStatus(data);
        }
        return count;
    }

    @Override
    public int refreshApprove(Jewel data) {
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getCode())) {
            data.setApproveDatetime(new Date());
            count = jewelDAO.updateApprove(data);
        }
        return count;
    }

    @Override
    public int refreshPutOn(Jewel data) {
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getCode())) {
            data.setStatus(EJewelStatus.PUT_ON.getCode());
            Date date = new Date();
            data.setStartDatetime(date);
            data.setLotteryDatetime(DateUtil.getRelativeDateOfDays(
                data.getStartDatetime(), data.getRaiseDays()));
            data.setUpdateDatetime(date);
            count = jewelDAO.updatePutOn(data);
        }
        return count;
    }

    @Override
    public int refreshPutOff(String code, String updater, String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Jewel data = new Jewel();
            data.setCode(code);
            data.setStatus(EJewelStatus.PUT_OFF.getCode());
            data.setUpdateDatetime(new Date());
            data.setUpdater(updater);
            data.setRemark(remark);
            count = jewelDAO.updatePutOff(data);
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
            data.setWinUserId(winUserId);
            data.setStatus(EJewelStatus.TO_SEND.getCode());
            data.setRemark("已开奖");
            count = jewelDAO.updateWinInfo(data);
        }
        return count;
    }

}
