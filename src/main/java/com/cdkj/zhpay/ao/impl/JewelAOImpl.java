package com.cdkj.zhpay.ao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.bo.IJewelBO;
import com.cdkj.zhpay.bo.IJewelRecordBO;
import com.cdkj.zhpay.bo.IJewelTemplateBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.enums.EJewelStatus;
import com.cdkj.zhpay.enums.EJewelTemplateStatus;
import com.cdkj.zhpay.exception.BizException;

/**
 * @author: xieyj 
 * @since: 2017年1月11日 下午6:16:26 
 * @history:
 */
@Service
public class JewelAOImpl implements IJewelAO {
    protected static final Logger logger = LoggerFactory
        .getLogger(JewelAOImpl.class);

    @Autowired
    IJewelBO jewelBO;

    @Autowired
    IJewelRecordBO jewelRecordBO;

    @Autowired
    IJewelTemplateBO jewelTemplateBO;

    @Override
    public Paginable<Jewel> queryJewelPage(int start, int limit, Jewel condition) {
        return jewelBO.getPaginable(start, limit, condition);
    }

    @Override
    public Jewel getJewel(String code) {
        return jewelBO.getJewel(code);
    }

    @Override
    @Transactional
    public void publishNextPeriods(String templateCode) {
        JewelTemplate jewelTemplate = jewelTemplateBO
            .getJewelTemplate(templateCode);
        if (!EJewelTemplateStatus.PUTON.getCode().equals(
            jewelTemplate.getStatus())) {
            // 模板必须属于上架状态
            throw new BizException("xn0000", "模板不处于上架状态，不能发布标的");
        }
        // 如果没有正在募集中的宝贝,发布新一期
        if (!jewelBO.isExist(templateCode, EJewelStatus.RUNNING)) {
            Integer nextPeriods = jewelTemplate.getCurrentPeriods() + 1;
            jewelTemplate.setCurrentPeriods(nextPeriods);
            jewelBO.saveJewel(jewelTemplate);
            // 更新模板当前发布期数
            jewelTemplateBO.refreshPeriods(templateCode, nextPeriods);
        } else {// 如果有正在募集中的宝贝,则不做任何处理
            return;
        }
    }

}
