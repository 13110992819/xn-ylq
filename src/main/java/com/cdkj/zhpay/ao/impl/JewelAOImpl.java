package com.cdkj.zhpay.ao.impl;

import java.util.Date;
import java.util.List;

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
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.enums.EJewelStatus;

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
    IJewelTemplateBO jewelTemplateBO;

    @Autowired
    IJewelRecordBO jewelRecordBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    IAccountBO accountBO;

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
    public void doChangeStatusDaily() {
        // logger.info("***************扫描需流标的宝贝***************");
        // Jewel condition = new Jewel();
        // condition.setStatus(EJewelStatus.PUT_ON.getCode());
        // condition.setLotteryDatetimeEnd(new Date());
        // List<Jewel> list = jewelBO.queryJewelList(condition);
        // for (Jewel jewel : list) {
        // handleJewel(jewel);
        // }
        // logger.info("***************扫描需流标的宝贝***************");
    }

    /**
     * @param jewel 
     * @create: 2017年1月12日 下午5:10:29 xieyj
     * @history:
     */
    @Transactional
    private void handleJewel(Jewel jewel) {
        // Map<String, String> rateMap = sysConfigBO.getConfigsMap(
        // jewel.getSystemCode(), null);
        // Double gxjl2cnyRate = Double
        // .valueOf(rateMap.get(SysConstants.GXJL2CNY));
        // // 业务逻辑：判断是否满；不满金额全部退回，状态更新为到期流标；
        // if (jewel.getTotalNum() > jewel.getInvestNum()) {
        // jewelBO
        // .refreshStatus(jewel.getCode(), EJewelStatus.EXPIRED.getCode(),
        // EJewelStatus.EXPIRED.getValue());
        // JewelRecord condition = new JewelRecord();
        // condition.setJewelCode(jewel.getCode());
        // condition.setStatus(EJewelRecordStatus.LOTTERY.getCode());
        // List<JewelRecord> jewelRecordList = jewelRecordBO
        // .queryJewelRecordList(condition);
        // for (JewelRecord jewelRecord : jewelRecordList) {
        // // 人民币退贡献奖励,贡献奖励，钱包币
        // accountBO.doTransferAmountByUser(jewel.getSystemCode(),
        // ESysUser.SYS_USER.getCode(), jewelRecord.getUserId(),
        // ECurrency.GXJL.getCode(),
        // Double.valueOf(gxjl2cnyRate * jewelRecord.getPayAmount1())
        // .longValue(), EBizType.AJ_DBFLOW.getCode(), "单号["
        // + jewelRecord.getCode() + "]的" + jewel.getName()
        // + "宝贝流标退款。");
        // accountBO.doTransferAmountByUser(jewel.getSystemCode(),
        // ESysUser.SYS_USER.getCode(), jewelRecord.getUserId(),
        // ECurrency.GWB.getCode(), jewelRecord.getPayAmount2(),
        // EBizType.AJ_DBFLOW.getCode(), "单号[" + jewelRecord.getCode()
        // + "]的" + jewel.getName() + "宝贝流标退款。");
        // accountBO.doTransferAmountByUser(jewel.getSystemCode(),
        // ESysUser.SYS_USER.getCode(), jewelRecord.getUserId(),
        // ECurrency.QBB.getCode(), jewelRecord.getPayAmount3(),
        // EBizType.AJ_DBFLOW.getCode(), "单号[" + jewelRecord.getCode()
        // + "]的" + jewel.getName() + "宝贝流标退款。");
        // }
        // }
    }

    @Override
    @Transactional
    public void publishNextPeriods(String templateCode) {
        JewelTemplate jewelTemplate = jewelTemplateBO
            .getJewelTemplate(templateCode);
        Jewel condition = new Jewel();
        condition.setTemplateCode(templateCode);
        condition.setStatus(EJewelStatus.RUNNING.getCode());
        // 如果没有正在募集中的项目
        if (jewelBO.getTotalCount(condition) <= 0) {
            // 发布下一期项目
            Jewel jewel = new Jewel();
            jewel.setTemplateCode(templateCode);
            if (jewelTemplate.getCurrentPeriods() == null) {
                jewel.setPeriods(1000000001);
            } else {
                jewel.setPeriods(jewelTemplate.getCurrentPeriods() + 1);
            }
            jewel.setCurrency(jewelTemplate.getCurrency());
            jewel.setAmount(jewelTemplate.getAmount());
            jewel.setTotalNum(jewelTemplate.getTotalNum());
            jewel.setPrice(jewelTemplate.getPrice());
            jewel.setMaxInvestNum(jewelTemplate.getMaxInvestNum());
            jewel.setAdvText(jewelTemplate.getAdvText());
            jewel.setAdvPic(jewelTemplate.getAdvPic());
            jewel.setCreateDatetime(new Date());
            jewel.setSystemCode(jewelTemplate.getSystemCode());
            jewelBO.saveJewel(jewel);
            // 更新模板当前发布期号
            jewelTemplateBO.refreshPeriods(templateCode, jewel.getPeriods());
        }
    }
}
