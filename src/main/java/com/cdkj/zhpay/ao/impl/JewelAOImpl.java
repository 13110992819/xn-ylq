package com.cdkj.zhpay.ao.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IJewelBO;
import com.cdkj.zhpay.bo.IJewelRecordBO;
import com.cdkj.zhpay.bo.IJewelTemplateBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.core.LuckyNumberGenerator;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecord;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EJewelRecordStatus;
import com.cdkj.zhpay.enums.EJewelStatus;
import com.cdkj.zhpay.enums.EJewelTemplateStatus;

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
    IAccountBO accountBO;

    @Autowired
    IUserBO userBO;

    @Autowired
    IJewelTemplateBO jewelTemplateBO;

    @Override
    public Paginable<Jewel> queryJewelPage(int start, int limit, Jewel condition) {
        Paginable<Jewel> results = jewelBO
            .getPaginable(start, limit, condition);
        for (Jewel jewel : results.getList()) {
            if (StringUtils.isNotBlank(jewel.getWinUser())) {
                jewel.setUser(userBO.getRemoteUser(jewel.getWinUser()));
            }
        }
        return results;
    }

    @Override
    public Jewel getJewel(String code) {
        Jewel jewel = jewelBO.getJewel(code);
        if (StringUtils.isNotBlank(jewel.getWinUser())) {
            jewel.setUser(userBO.getRemoteUser(jewel.getWinUser()));
        }
        return jewel;
    }

    @Override
    @Transactional
    public void publishNextPeriods(String templateCode) {
        JewelTemplate jewelTemplate = jewelTemplateBO
            .getJewelTemplate(templateCode);
        // 模板必须属于上架状态
        if (!EJewelTemplateStatus.PUTON.getCode().equals(
            jewelTemplate.getStatus())) {
            return;
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

    @Override
    @Transactional
    public String doManBiao(String jewelCode) {
        Jewel jewel = jewelBO.getJewel(jewelCode);
        // 取最后投资五条记录的时间之和
        Long randomA = jewelRecordBO.getLastRecordsTimes(jewelCode);
        // 产生中奖号码
        String luckyNumber = LuckyNumberGenerator.getLuckyNumber(
            SysConstants.JEWEL_NUM_RADIX, Long.valueOf(jewel.getTotalNum()),
            randomA, 0L);
        JewelRecord jewelRecord = jewelRecordBO.getWinJewelRecord(jewelCode,
            luckyNumber);
        // 更新中奖记录的状态
        jewelRecordBO.refreshStatus(jewelRecord.getCode(),
            EJewelRecordStatus.WINNING.getCode(), "号码" + luckyNumber + "已中奖");
        // 中奖用户和中奖记录
        String userId = jewelRecord.getUserId();
        String jewelRecordCode = jewelRecord.getCode();
        // 更新参与记录状态
        jewelRecordBO.refreshLostInfo(jewelRecordCode, jewelCode,
            EJewelRecordStatus.LOST.getCode(), "很遗憾，本次未中奖");
        // 更新宝贝中奖号码
        jewelBO.refreshWinInfo(jewelCode, luckyNumber, userId);
        User user = userBO.getRemoteUser(userId);
        // 中奖者加上奖金
        String toBizNote = EBizType.AJ_DUOBAO_PRIZE.getValue();
        String fromBizNote = UserUtil.getUserMobile(user.getMobile())
                + toBizNote;
        ECurrency currency = ECurrency.getECurrency(jewel.getToCurrency());
        String systemUserId = userBO.getSystemUser(jewel.getSystemCode());
        accountBO.doTransferAmountRemote(systemUserId, userId, currency,
            jewel.getToAmount(), EBizType.AJ_DUOBAO_PRIZE, fromBizNote,
            toBizNote, jewelCode);
        return jewel.getTemplateCode();
    }
}
