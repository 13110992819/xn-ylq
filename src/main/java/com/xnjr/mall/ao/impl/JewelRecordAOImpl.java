package com.xnjr.mall.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IJewelRecordAO;
import com.xnjr.mall.bo.IJewelBO;
import com.xnjr.mall.bo.IJewelRecordBO;
import com.xnjr.mall.bo.IJewelRecordNumberBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.core.LuckyNumberGenerator;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.domain.JewelRecord;
import com.xnjr.mall.domain.JewelRecordNumber;
import com.xnjr.mall.enums.EJewelRecordStatus;
import com.xnjr.mall.enums.EJewelStatus;
import com.xnjr.mall.exception.BizException;

/**
 * 
 * @author: shan 
 * @since: 2016年12月20日 下午12:22:11 
 * @history:
 */
@Service
public class JewelRecordAOImpl implements IJewelRecordAO {
    @Autowired
    private IJewelRecordBO jewelRecordBO;

    @Autowired
    private IJewelBO jewelBO;

    @Autowired
    private IJewelRecordNumberBO jewelRecordNumberBO;

    @Override
    @Transactional
    public String buy(String userId, String jewelCode, Integer times) {
        if (userId == null && jewelCode == null && times == null) {
            throw new BizException("xn0000", "数据不能为空");
        }
        Jewel jewel = jewelBO.getJewel(jewelCode);
        if (!EJewelStatus.PUT_ON.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "夺宝标的不处于可夺宝状态，不能进行购买操作");
        }
        // 夺宝记录落地
        JewelRecord data = new JewelRecord();
        data.setUserId(userId);
        data.setJewelCode(jewelCode);
        data.setTimes(times);
        data.setRemark("已分配夺宝号，待开奖");
        data.setSystemCode(jewel.getSystemCode());

        // 查询已有号码列表
        List<String> existNumbers = jewelRecordNumberBO
            .queryExistNumbers(jewelCode);

        // 自动生成夺宝号码
        List<String> numbers = LuckyNumberGenerator.generateLuckyNumbers(
            10000000L, Long.valueOf(jewel.getTotalNum()), existNumbers,
            Long.valueOf(times));
        String jewelRecordCode = jewelRecordBO.saveJewelRecord(data);
        for (int i = 0; i < numbers.size(); i++) {
            JewelRecordNumber jewelRecordNumber = new JewelRecordNumber();
            jewelRecordNumber.setJewelCode(jewelCode);
            jewelRecordNumber.setRecordCode(jewelRecordCode);
            jewelRecordNumber.setNumber(numbers.get(i));
            jewelRecordNumberBO.saveJewelRecordNumber(jewelRecordNumber);
        }

        JewelRecordNumber condition = new JewelRecordNumber();
        condition.setJewelCode(jewelCode);
        Long investNum = jewelRecordNumberBO.getTotalCount(condition);

        // 更新夺宝标的已投资人次及已投资金额
        jewelBO.refreshInvestInfo(jewelCode, investNum.intValue());

        // 如果已投满，产生中奖名单
        if (investNum.intValue() == jewel.getTotalNum()) {
            // 产生中奖号码
            String luckyNumber = LuckyNumberGenerator.getLuckyNumber(10000000L,
                Long.valueOf(jewel.getTotalNum()), 0L);
            // 根据幸运号码招到夺宝记录ID
            JewelRecordNumber condition1 = new JewelRecordNumber();
            condition1.setJewelCode(jewelCode);
            condition1.setNumber(luckyNumber);
            JewelRecordNumber jewelRecordNumber = jewelRecordNumberBO
                .queryJewelRecordNumberList(condition1).get(0);
            JewelRecord jewelRecord = jewelRecordBO
                .getJewelRecord(jewelRecordNumber.getRecordCode());
            String winUserId = jewelRecord.getUserId();
            // 更新此次夺宝所有夺宝记录的状态
            jewelRecordBO.refreshStatus(jewelRecord.getCode(),
                EJewelRecordStatus.WINNING.getCode(), "夺宝号" + luckyNumber
                        + "已中奖");
            jewelRecordBO.refreshLostInfo(jewelRecord.getCode(), jewelCode,
                EJewelRecordStatus.LOST.getCode(), "很遗憾，本次未中奖");

            // 更新夺宝标的中奖人信息
            jewelBO.refreshWinInfo(jewelCode, luckyNumber, winUserId);

        }
        return jewelRecordCode;
    }

    @Override
    @Transactional
    public void additionalBuy(String jewelRecordCode, Integer times) {
        JewelRecord record = jewelRecordBO.getJewelRecord(jewelRecordCode);
        Jewel jewel = jewelBO.getJewel(record.getJewelCode());
        if (!EJewelStatus.PUT_ON.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "夺宝标的不处于可夺宝状态，不能进行追加操作");
        }
        if (times > jewel.getTotalNum() - jewel.getInvestNum()) {
            throw new BizException("xn0000", "剩余可参与人次不足");
        }

        // 更新夺宝记录参与次数
        Integer newTimes = record.getTimes() + times;
        jewelRecordBO.refreshTimes(jewelRecordCode, newTimes);

        // 查询已有号码列表
        List<String> existNumbers = jewelRecordNumberBO.queryExistNumbers(jewel
            .getCode());

        // 自动生成夺宝号码
        List<String> numbers = LuckyNumberGenerator.generateLuckyNumbers(
            10000000L, Long.valueOf(jewel.getTotalNum()), existNumbers,
            Long.valueOf(times));
        for (int i = 0; i < numbers.size(); i++) {
            JewelRecordNumber jewelRecordNumber = new JewelRecordNumber();
            jewelRecordNumber.setJewelCode(jewel.getCode());
            jewelRecordNumber.setRecordCode(jewelRecordCode);
            jewelRecordNumber.setNumber(numbers.get(i));
            jewelRecordNumberBO.saveJewelRecordNumber(jewelRecordNumber);
        }

        // 更新夺宝标的已投资人次及已投资金额
        JewelRecordNumber condition = new JewelRecordNumber();
        condition.setJewelCode(jewel.getCode());
        Long investNum = jewelRecordNumberBO.getTotalCount(condition);
        jewelBO.refreshInvestInfo(jewel.getCode(), investNum.intValue());

        // 如果已投满，产生中奖名单
        if (investNum.intValue() == jewel.getTotalNum()) {
            // 产生中奖号码
            String luckyNumber = LuckyNumberGenerator.getLuckyNumber(10000000L,
                Long.valueOf(jewel.getTotalNum()), 0L);
            // 根据幸运号码招到夺宝记录ID
            JewelRecordNumber condition1 = new JewelRecordNumber();
            condition1.setJewelCode(jewel.getCode());
            condition1.setNumber(luckyNumber);
            JewelRecordNumber jewelRecordNumber = jewelRecordNumberBO
                .queryJewelRecordNumberList(condition1).get(0);
            JewelRecord jewelRecord = jewelRecordBO
                .getJewelRecord(jewelRecordNumber.getRecordCode());
            String winUserId = jewelRecord.getUserId();
            // 更新此次夺宝所有夺宝记录的状态
            jewelRecordBO.refreshStatus(jewelRecord.getCode(),
                EJewelRecordStatus.WINNING.getCode(), "夺宝号" + luckyNumber
                        + "已中奖");
            jewelRecordBO
                .refreshLostInfo(jewelRecord.getCode(), jewel.getCode(),
                    EJewelRecordStatus.LOST.getCode(), "很遗憾，本次未中奖");

            // 更新夺宝标的中奖人信息
            jewelBO.refreshWinInfo(jewel.getCode(), luckyNumber, winUserId);

        }

    }

    @Override
    public void editJewelRecord(JewelRecord data) {
        if (!jewelRecordBO.isJewelRecordExist(data.getCode())) {
            throw new BizException("xn0000", "不存在该记录");
        }
        if (EJewelRecordStatus.WINNING.getCode().equals(
            jewelRecordBO.getJewelRecord(data.getCode()).getStatus())) {
            jewelRecordBO.refreshStatus(data.getCode(),
                EJewelRecordStatus.DELIVERY.getCode(), data.getRemark());
        } else {
            throw new BizException("xn0000", "该号码未中奖，不可发货");
        }
    }

    @Override
    public void dropJewelRecord(String code) {
        if (!jewelRecordBO.isJewelRecordExist(code)) {
            throw new BizException("xn0000", "不存在该记录");
        }
        jewelRecordBO.removeJewelRecord(code);
    }

    @Override
    public Paginable<JewelRecord> queryJewelRecordPage(int start, int limit,
            JewelRecord condition) {
        Paginable<JewelRecord> page = jewelRecordBO.getPaginable(start, limit,
            condition);
        return page;
    }

    @Override
    public Paginable<JewelRecord> queryMyJewelRecordPage(int start, int limit,
            JewelRecord condition) {
        Paginable<JewelRecord> page = jewelRecordBO.getPaginable(start, limit,
            condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            for (JewelRecord jewelRecord : page.getList()) {
                JewelRecordNumber imCondition = new JewelRecordNumber();
                imCondition.setRecordCode(jewelRecord.getCode());
                List<JewelRecordNumber> recordNumberList = jewelRecordNumberBO
                    .queryJewelRecordNumberList(imCondition);
                jewelRecord.setJewelRecordNumberList(recordNumberList);

                Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
                jewelRecord.setJewel(jewel);
            }
        }
        return page;
    }

    @Override
    public List<JewelRecord> queryJewelRecordList(JewelRecord condition) {
        return jewelRecordBO.queryJewelRecordList(condition);
    }

    @Override
    public JewelRecord getJewelRecord(String code, String userId) {
        if (!jewelRecordBO.isJewelRecordExist(code)) {
            throw new BizException("xn0000", "不存在该记录");
        }
        JewelRecord jewelRecode = jewelRecordBO.getJewelRecord(code);
        if (!jewelRecode.getUserId().equals(userId)) {
            throw new BizException("xn0000", "您没有这个号码");
        }
        JewelRecordNumber jewelRecordNumber = new JewelRecordNumber();
        jewelRecordNumber.setRecordCode(code);
        List<JewelRecordNumber> jewelRecordNumberList = jewelRecordNumberBO
            .queryJewelRecordNumberList(jewelRecordNumber);
        JewelRecord jewelRecord = jewelRecordBO.getJewelRecord(code);
        jewelRecord.setJewelRecordNumberList(jewelRecordNumberList);
        return jewelRecord;
    }

}
