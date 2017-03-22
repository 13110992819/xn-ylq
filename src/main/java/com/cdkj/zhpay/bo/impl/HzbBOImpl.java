package com.cdkj.zhpay.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.dao.IHzbDAO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.enums.EDiviFlag;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EHzbStatus;
import com.cdkj.zhpay.exception.BizException;

@Component
public class HzbBOImpl extends PaginableBOImpl<Hzb> implements IHzbBO {

    @Autowired
    private IHzbDAO hzbDAO;

    @Override
    public boolean isHzbExistByUser(String userId) {
        Hzb condition = new Hzb();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        if (hzbDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int buyHzb(String userId, HzbTemplate hzbTemplate, String payGroup) {
        int count = 0;
        if (StringUtils.isNotBlank(userId)) {
            Hzb data = new Hzb();
            String code = OrderNoGenerater.generateM(EGeneratePrefix.HZB
                .getCode());
            data.setCode(code);
            data.setUserId(userId);
            data.setTemplateCode(hzbTemplate.getCode());
            data.setPrice(hzbTemplate.getPrice());
            data.setCurrency(hzbTemplate.getCurrency());

            data.setPeriodRockNum(0);
            data.setTotalRockNum(0);
            data.setBackAmount1(0L);
            data.setBackAmount2(0L);
            data.setBackAmount3(0L);

            Date date = new Date();
            data.setCreateDatetime(date);
            data.setStatus(EHzbStatus.TO_PAY.getCode());
            data.setPayGroup(payGroup);
            data.setCompanyCode(hzbTemplate.getCompanyCode());
            data.setSystemCode(hzbTemplate.getSystemCode());
            count = hzbDAO.insert(data);
        }
        return count;
    }

    @Override
    public Hzb saveHzb(String userId, HzbTemplate hzbTemplate, Long frPayAmount) {
        Hzb data = null;
        if (StringUtils.isNotBlank(userId)) {
            data = new Hzb();
            String code = OrderNoGenerater.generateM(EGeneratePrefix.HZB
                .getCode());
            data.setCode(code);
            data.setUserId(userId);
            data.setTemplateCode(hzbTemplate.getCode());
            data.setPrice(hzbTemplate.getPrice());
            data.setCurrency(hzbTemplate.getCurrency());

            data.setPeriodRockNum(0);
            data.setTotalRockNum(0);
            data.setBackAmount1(0L);
            data.setBackAmount2(0L);
            data.setBackAmount3(0L);

            Date date = new Date();
            data.setCreateDatetime(date);
            data.setStatus(EHzbStatus.ACTIVATED.getCode());
            data.setPayDatetime(date);
            data.setPayAmount1(0L);
            data.setPayAmount2(frPayAmount);// 虚拟币_分润
            data.setPayAmount3(0L);
            data.setCompanyCode(hzbTemplate.getCompanyCode());
            data.setSystemCode(hzbTemplate.getSystemCode());
            hzbDAO.insert(data);
        }
        return data;
    }

    @Override
    public int refreshPayStatus(String code, String status, String payCode,
            Long payAmount) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Hzb data = new Hzb();
            data.setCode(payCode);
            data.setStatus(status);
            data.setPayCode(payCode);
            data.setPayDatetime(new Date());
            data.setPayAmount1(payAmount);// 人民币
            data.setPayAmount2(0L);
            data.setPayAmount3(0L);
            count = hzbDAO.updatePayStatus(data);
        }
        return count;
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#refreshPutStatus(java.lang.String, java.lang.String)
     */
    @Override
    public int refreshPutStatus(String code, String status) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Hzb data = new Hzb();
            data.setCode(code);
            data.setPayDatetime(new Date());
            count = hzbDAO.updatePutStatus(data);
        }
        return count;
    }

    @Override
    public Hzb getHzb(String code) {
        Hzb data = null;
        if (StringUtils.isNotBlank(code)) {
            Hzb condition = new Hzb();
            condition.setCode(code);
            data = hzbDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "汇赚宝不存在");
            }
        }
        return data;
    }

    @Override
    public int refreshRockNum(String code, Integer periodRockNum,
            Integer totalRockNum) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Hzb data = new Hzb();
            data.setCode(code);
            data.setPeriodRockNum(periodRockNum);
            data.setTotalRockNum(totalRockNum);
            count = hzbDAO.updateRockNum(data);
        }
        return count;
    }

    @Override
    public void resetPeriodRockNum() {
        hzbDAO.updatePeriodRockNumZero();
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#checkBuy(java.lang.String)
     */
    @Override
    public void checkBuy(String userId) {
        Hzb condition = new Hzb();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        if (hzbDAO.selectTotalCount(condition) > 0) {
            throw new BizException("xn0000", "您已经购买过汇赚宝");
        }
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#queryHzbList(java.lang.String)
     */
    @Override
    public List<Hzb> queryHzbList(String payGroup) {
        Hzb condition = new Hzb();
        condition.setStatus(EHzbStatus.TO_PAY.getCode());
        condition.setPayGroup(payGroup);
        return hzbDAO.selectList(condition);
    }

    @Override
    public List<Hzb> queryHzbList(Hzb condition) {
        return hzbDAO.selectList(condition);
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#queryDistanceHzbList(com.cdkj.zhpay.domain.Hzb)
     */
    @Override
    public List<Hzb> queryDistanceHzbList(Hzb condition) {
        return hzbDAO.selectDistanceList(condition);
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#queryHzbList(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<Hzb> queryHzbList(String userId, String companyCode,
            String systemCode) {
        Hzb condition = new Hzb();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        condition.setCompanyCode(companyCode);
        condition.setSystemCode(systemCode);
        return hzbDAO.selectList(condition);
    }
}
