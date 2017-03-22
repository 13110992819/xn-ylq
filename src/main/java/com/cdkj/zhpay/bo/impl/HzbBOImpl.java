package com.cdkj.zhpay.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.base.Page;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.dao.IHzbDAO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.enums.EDiviFlag;
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
    public int buySuccess(String userId, HzbTemplate hzbTemplate,
            String payGroup) {
        int count = 0;
        if (StringUtils.isNotBlank(userId)) {
            Hzb data = new Hzb();
            data.setUserId(userId);
            data.setTemplateCode(hzbTemplate.getCode());
            data.setStatus(EHzbStatus.TO_PAY.getCode());
            data.setPrice(hzbTemplate.getPrice());
            data.setCurrency(hzbTemplate.getCurrency());
            data.setPeriodRockNum(0);
            data.setTotalRockNum(0);
            data.setPayGroup(payGroup);
            data.setSystemCode(hzbTemplate.getSystemCode());
            count = hzbDAO.insert(data);
        }
        return count;
    }

    @Override
    public int saveHzb(String userId, HzbTemplate hzbTemplate, Long amount) {
        int count = 0;
        if (StringUtils.isNotBlank(userId)) {
            Hzb data = new Hzb();
            data.setUserId(userId);
            data.setTemplateCode(hzbTemplate.getCode());
            data.setStatus(EHzbStatus.ACTIVATED.getCode());
            data.setPrice(hzbTemplate.getPrice());
            data.setCurrency(hzbTemplate.getCurrency());
            data.setPeriodRockNum(0);
            data.setTotalRockNum(0);
            data.setPayAmount1(0L);
            data.setPayAmount2(0L);
            data.setPayAmount3(amount);
            data.setSystemCode(hzbTemplate.getSystemCode());
            count = hzbDAO.insert(data);
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
    public Hzb getHzbByUser(String userId) {
        Hzb data = null;
        if (StringUtils.isNotBlank(userId)) {
            Hzb condition = new Hzb();
            condition.setUserId(userId);
            condition.setStatus(EDiviFlag.EFFECT.getCode());
            data = hzbDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "汇赚宝购买记录不存在");
            }
        }
        return data;
    }

    @Override
    public int refreshPayStatus(Long id, String status, String payCode,
            Long payAmount) {
        int count = 0;
        if (id != null) {
            Hzb data = new Hzb();
            data.setId(id);
            data.setStatus(status);
            data.setPayAmount1(0L);
            data.setPayAmount2(0L);
            data.setPayAmount3(payAmount);
            data.setPayCode(payCode);
            data.setPayDatetime(new Date());
            count = hzbDAO.updatePayStatus(data);
        }
        return count;
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#queryDistanceHzbList(com.cdkj.zhpay.domain.Hzb)
     */
    @Override
    public List<Hzb> queryDistanceHzbList(Hzb condition) {
        return hzbDAO.selectDistanceList(condition);
    }

    @Override
    public Paginable<Hzb> queryDistancePaginable(int start, int pageSize,
            Hzb condition) {
        long totalCount = hzbDAO.selectDistanceTotalCount(condition);
        Paginable<Hzb> page = new Page<Hzb>(start, pageSize, totalCount);
        List<Hzb> dataList = hzbDAO.selectDistanceList(condition,
            page.getStart(), page.getPageSize());
        page.setList(dataList);
        return page;
    }

    @Override
    public int refreshRockNum(Long id, Integer periodRockNum,
            Integer totalRockNum) {
        int count = 0;
        if (id != null) {
            Hzb data = new Hzb();
            data.setId(id);
            data.setPeriodRockNum(periodRockNum);
            data.setTotalRockNum(totalRockNum);
            count = hzbDAO.updateRockNum(data);
        }
        return count;
    }

    @Override
    public void resetPeriodRockNum() {
        hzbDAO.resetPeriodRockNum();
    }

    @Override
    public Long getTotalAmount(String payGroup) {
        Hzb data = new Hzb();
        data.setPayGroup(payGroup);
        return hzbDAO.getTotalAmount(data);
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

}
