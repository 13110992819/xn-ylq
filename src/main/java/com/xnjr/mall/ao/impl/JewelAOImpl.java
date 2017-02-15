package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IJewelAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.IJewelBO;
import com.xnjr.mall.bo.IJewelRecordBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.domain.JewelRecord;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.EBoolean;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EJewelRecordStatus;
import com.xnjr.mall.enums.EJewelStatus;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.exception.BizException;

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
    private ISYSConfigBO sysConfigBO;

    @Autowired
    IAccountBO accountBO;

    @Override
    public String applyJewel(Jewel data) {
        return jewelBO.saveJewel(data);
    }

    @Override
    public void approveJewel(String code, String approveResult,
            String approver, String approveNote) {
        Jewel jewel = jewelBO.getJewel(code);
        if (!EJewelStatus.NEW.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "该夺宝标的不处于待审批状态，不能进行审核操作");
        }
        String status = EJewelStatus.PASS.getCode();
        if (EBoolean.NO.getCode().equals(approveResult)) {
            status = EJewelStatus.UNPASS.getCode();
        }
        jewel.setStatus(status);
        jewel.setApprover(approver);
        jewel.setRemark(approveNote);
        jewelBO.refreshApprove(jewel);
    }

    /** 
     * @see com.xnjr.mall.ao.IJewelAO#approveJewelList(java.util.List, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void approveJewelList(List<String> codeList, String approveResult,
            String approver, String approveNote) {
        for (String code : codeList) {
            this.approveJewel(code, approveResult, approver, approveNote);
        }
    }

    @Override
    public void reApplyJewel(Jewel data) {
        Jewel jewel = jewelBO.getJewel(data.getCode());
        if (!EJewelStatus.NEW.getCode().equals(jewel.getStatus())
                && !EJewelStatus.UNPASS.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "宝贝不处于待审批或审核不通过状态，无法重提提交");
        }
        data.setStatus(EJewelStatus.NEW.getCode());
        jewelBO.refreshJewel(data);
    }

    @Override
    public Paginable<Jewel> queryJewelPage(int start, int limit, Jewel condition) {
        return jewelBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Jewel> queryJewelList(Jewel condition) {
        return jewelBO.queryJewelList(condition);
    }

    @Override
    public Jewel getJewel(String code) {
        return jewelBO.getJewel(code);
    }

    @Override
    public void putOn(Jewel data) {
        Jewel jewel = jewelBO.getJewel(data.getCode());
        if (!EJewelStatus.PUT_OFF.getCode().equals(jewel.getStatus())
                && !EJewelStatus.PASS.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "宝贝不处于审核通过或下架状态，无法上架");
        }
        jewelBO.refreshPutOn(data);
    }

    @Override
    @Transactional
    public void putOff(String code, String updater, String remark) {
        Jewel jewel = jewelBO.getJewel(code);
        if (!EJewelStatus.PUT_ON.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "宝贝不处于夺宝状态中，不能进行下架操作");
        }
        JewelRecord jrCondition = new JewelRecord();
        jrCondition.setJewelCode(jewel.getCode());
        if (jewelRecordBO.getTotalCount(jrCondition) > 0) {
            throw new BizException("xn0000", "该宝贝已有人购买，无法下架");
        }
        jewelBO.refreshPutOff(code, updater, remark);
    }

    @Override
    public void doChangeStatusDaily() {
        logger.info("***************扫描需流标的宝贝***************");
        Jewel condition = new Jewel();
        condition.setStatus(EJewelStatus.PUT_ON.getCode());
        condition.setLotteryDatetimeEnd(new Date());
        List<Jewel> list = jewelBO.queryJewelList(condition);
        for (Jewel jewel : list) {
            handleJewel(jewel);
        }
        logger.info("***************扫描需流标的宝贝***************");
    }

    /**
     * @param jewel 
     * @create: 2017年1月12日 下午5:10:29 xieyj
     * @history:
     */
    @Transactional
    private void handleJewel(Jewel jewel) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(
            jewel.getSystemCode(), null);
        Double gxjl2cnyRate = Double
            .valueOf(rateMap.get(SysConstants.GXJL2CNY));
        // 业务逻辑：判断是否满；不满金额全部退回，状态更新为到期流标；
        if (jewel.getTotalNum() > jewel.getInvestNum()) {
            jewelBO
                .refreshStatus(jewel.getCode(), EJewelStatus.EXPIRED.getCode(),
                    EJewelStatus.EXPIRED.getValue());
            JewelRecord condition = new JewelRecord();
            condition.setJewelCode(jewel.getCode());
            condition.setStatus(EJewelRecordStatus.LOTTERY.getCode());
            List<JewelRecord> jewelRecordList = jewelRecordBO
                .queryJewelRecordList(condition);
            for (JewelRecord jewelRecord : jewelRecordList) {
                // 人民币退贡献奖励,贡献奖励，钱包币
                accountBO.doTransferAmountByUser(jewel.getSystemCode(),
                    ESysUser.SYS_USER.getCode(), jewelRecord.getUserId(),
                    ECurrency.GXJL.getCode(),
                    Double.valueOf(gxjl2cnyRate * jewelRecord.getPayAmount1())
                        .longValue(), EBizType.AJ_DBFLOW.getCode(), "单号["
                            + jewelRecord.getCode() + "]的" + jewel.getName()
                            + "宝贝流标退款。");
                accountBO.doTransferAmountByUser(jewel.getSystemCode(),
                    ESysUser.SYS_USER.getCode(), jewelRecord.getUserId(),
                    ECurrency.GWB.getCode(), jewelRecord.getPayAmount2(),
                    EBizType.AJ_DBFLOW.getCode(), "单号[" + jewelRecord.getCode()
                            + "]的" + jewel.getName() + "宝贝流标退款。");
                accountBO.doTransferAmountByUser(jewel.getSystemCode(),
                    ESysUser.SYS_USER.getCode(), jewelRecord.getUserId(),
                    ECurrency.QBB.getCode(), jewelRecord.getPayAmount3(),
                    EBizType.AJ_DBFLOW.getCode(), "单号[" + jewelRecord.getCode()
                            + "]的" + jewel.getName() + "宝贝流标退款。");
            }
        }
    }
}
