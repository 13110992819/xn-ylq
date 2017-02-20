package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IJewelRecordAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.IJewelBO;
import com.xnjr.mall.bo.IJewelInteractBO;
import com.xnjr.mall.bo.IJewelRecordBO;
import com.xnjr.mall.bo.IJewelRecordNumberBO;
import com.xnjr.mall.bo.ISmsOutBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.core.LuckyNumberGenerator;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.domain.JewelRecord;
import com.xnjr.mall.domain.JewelRecordNumber;
import com.xnjr.mall.dto.res.XN802180Res;
import com.xnjr.mall.dto.res.XN805901Res;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.EBoolean;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EJewelRecordStatus;
import com.xnjr.mall.enums.EJewelStatus;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.exception.BizException;

/**
 * @author: shan 
 * @since: 2016年12月20日 下午12:22:11 
 * @history:
 */
@Service
public class JewelRecordAOImpl implements IJewelRecordAO {
    private static final Logger logger = LoggerFactory
        .getLogger(JewelRecordAOImpl.class);

    @Autowired
    private IJewelRecordBO jewelRecordBO;

    @Autowired
    private IJewelBO jewelBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IJewelRecordNumberBO jewelRecordNumberBO;

    @Autowired
    private IJewelInteractBO jewelInteractBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Override
    @Transactional
    public Object buyJewel(String userId, String jewelCode, Integer times,
            String payType, String ip) {
        Object result = null;
        Jewel jewel = jewelBO.getJewel(jewelCode);
        if (!EJewelStatus.RUNNING.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "夺宝标的不处于募集中状态，不能进行购买操作");
        }
        // 判断是否大于剩余购买份数
        if (jewel.getTotalNum() - jewel.getInvestNum() < times) {
            throw new BizException("xn0000", "购买数量需不大于剩余份数");

        }
        XN805901Res userRes = userBO.getRemoteUser(userId, userId);
        // 夺宝记录落地
        JewelRecord data = new JewelRecord();
        data.setUserId(userId);
        data.setJewelCode(jewelCode);
        data.setTimes(times);
        data.setRemark("已分配夺宝号，待开奖");
        data.setSystemCode(jewel.getSystemCode());
        data.setPayAmount(jewel.getPrice() * times);
        String jewelRecordCode = OrderNoGenerater
            .generateM(EGeneratePrefix.IEWEL_RECORD.getCode());
        data.setCode(jewelRecordCode);
        String systemCode = userRes.getSystemCode();
        // 余额支付(余额支付)
        if (EPayType.YEZP.getCode().equals(payType)) {
            // 检验分润和贡献奖励是否充足
            accountBO.checkBalanceAmount(systemCode, userId,
                data.getPayAmount());
            String status = EJewelRecordStatus.LOTTERY.getCode();
            Date createDatetime = new Date();
            // 分配号码
            Map<String, String> resultMap = distributeNumber(userId, jewel,
                times, jewelRecordCode, createDatetime);
            // 未开奖
            if (resultMap != null) {
                String winJewelRecordCode = resultMap.get("winJewelRecordCode");
                if (!winJewelRecordCode.equals(jewelRecordCode)) {
                    status = EJewelRecordStatus.LOST.getCode();
                    data.setRemark("很遗憾，本次未中奖");
                } else {
                    status = EJewelRecordStatus.WINNING.getCode();
                    data.setRemark("已中奖，中奖号码" + resultMap.get("luckyNumber"));
                }
            }
            data.setStatus(status);
            data.setInvestDatetime(createDatetime);
            data.setPayDatetime(createDatetime);
            jewelRecordBO.saveJewelRecord(data);
            // 扣除余额
            accountBO.doBalancePay(systemCode, userId,
                ESysUser.SYS_USER.getCode(), data.getPayAmount(),
                EBizType.AJ_DUOBAO);
            result = jewelRecordCode;
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            // 检验购物币和钱包币是否充足
            String bizNote = "宝贝单号：" + jewelRecordCode + "——小目标";
            String body = "正汇钱包—小目标支付";
            XN802180Res res = accountBO.doWeiXinPay(systemCode, userId,
                EBizType.AJ_DUOBAO, bizNote, body, data.getPayAmount(), ip);
            data.setStatus(EJewelRecordStatus.TO_PAY.getCode());
            data.setPayCode(res.getJourCode());
            data.setRemark("宝贝待支付");
            jewelRecordBO.saveJewelRecord(data);
            result = res;
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {

        }
        return result;
    }

    /**
     * 分配号码:
     * 1、查询已有号码，自动生成夺宝号码列表，并保持
     * 2、更新宝贝的投资人次
     * 3、判断是否满标，是则产生中奖号码；否则结束
     * 4、判断中奖号码是否是当前号码，是则保存时状态更改为已中奖，否则更新其他记录状态；更新宝贝中奖人信息
     * @param userId 假设一开始是中奖用户
     * @param jewel
     * @param times
     * @param jewelRecordCode 假设一开始是中奖记录
     * @param curCreateDatetime 本条记录创建时间
     * @create: 2017年1月12日 下午9:12:37 xieyj
     * @history:
     */
    private Map<String, String> distributeNumber(String userId, Jewel jewel,
            Integer times, String jewelRecordCode, Date curCreateDatetime) {
        Map<String, String> resultMap = null;
        String jewelCode = jewel.getCode();
        // 查询已有号码列表，自动生成夺宝号码
        List<String> existNumbers = jewelRecordNumberBO
            .queryExistNumbers(jewelCode);
        List<String> numbers = LuckyNumberGenerator.generateLuckyNumbers(
            SysConstants.JEWEL_NUM_RADIX, Long.valueOf(jewel.getTotalNum()),
            existNumbers, Long.valueOf(times));
        for (int i = 0; i < numbers.size(); i++) {
            JewelRecordNumber jewelRecordNumber = new JewelRecordNumber();
            jewelRecordNumber.setJewelCode(jewelCode);
            jewelRecordNumber.setRecordCode(jewelRecordCode);
            jewelRecordNumber.setNumber(numbers.get(i));
            jewelRecordNumberBO.saveJewelRecordNumber(jewelRecordNumber);
        }
        // 更新夺宝标的已投资人次
        int investNum = existNumbers.size();
        jewelBO.refreshInvestInfo(jewelCode, investNum);
        // 如果已投满，产生中奖名单
        String luckyNumber = null;
        if (investNum == jewel.getTotalNum()) {
            // 取最后投资五条记录的时间
            Long randomA = jewelRecordBO.getLastRecordsTimes(jewelCode,
                curCreateDatetime);
            // 产生中奖号码
            luckyNumber = LuckyNumberGenerator.getLuckyNumber(
                SysConstants.JEWEL_NUM_RADIX,
                Long.valueOf(jewel.getTotalNum()), randomA, 0L);
            if (!numbers.contains(luckyNumber)) {
                // 根据幸运号码找到夺宝记录
                JewelRecordNumber condition1 = new JewelRecordNumber();
                condition1.setJewelCode(jewelCode);
                condition1.setNumber(luckyNumber);
                JewelRecordNumber jewelRecordNumber = jewelRecordNumberBO
                    .queryJewelRecordNumberList(condition1).get(0);
                JewelRecord jewelRecord = jewelRecordBO
                    .getJewelRecord(jewelRecordNumber.getRecordCode());
                // 更新中奖记录的状态
                jewelRecordBO.refreshStatus(jewelRecord.getCode(),
                    EJewelRecordStatus.WINNING.getCode(), "夺宝号" + luckyNumber
                            + "已中奖");
                userId = jewelRecord.getUserId();
                jewelRecordCode = jewelRecord.getCode();
            }
            // 更新夺宝标的中奖人信息,其他记录状态更改
            jewelRecordBO.refreshLostInfo(jewelRecordCode, jewelCode,
                EJewelRecordStatus.LOST.getCode(), "很遗憾，本次未中奖");
            jewelBO.refreshWinInfo(jewelCode, luckyNumber, userId);
            // 新增和修改根据如下两个字段判断是否开奖
            resultMap = new HashMap<String, String>();
            resultMap.put("winJewelRecordCode", jewelRecordCode);
            resultMap.put("luckyNumber", luckyNumber);
        }
        return resultMap;
    }

    @Override
    @Transactional
    public void paySuccess(String payCode) {
        JewelRecord condition = new JewelRecord();
        condition.setPayCode(payCode);
        List<JewelRecord> result = jewelRecordBO
            .queryJewelRecordList(condition);
        if (CollectionUtils.isEmpty(result)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        JewelRecord jewelRecord = result.get(0);
        if (EJewelRecordStatus.TO_PAY.getCode().equals(jewelRecord.getStatus())) {
            Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
            Map<String, String> resultMap = distributeNumber(
                jewelRecord.getUserId(), jewel, jewelRecord.getTimes(),
                jewelRecord.getCode(), null);
            String status = EJewelRecordStatus.LOTTERY.getCode();
            String remark = null;
            // 未开奖
            if (resultMap != null) {
                String winJewelRecordCode = resultMap.get("winJewelRecordCode");
                if (!winJewelRecordCode.equals(jewelRecord.getJewelCode())) {
                    status = EJewelRecordStatus.LOST.getCode();
                } else {
                    status = EJewelRecordStatus.WINNING.getCode();
                    remark = "已中奖，中奖号码" + resultMap.get("luckyNumber");
                }
            }
            jewelRecordBO.refreshPaySuccess(jewelRecord.getCode(), status,
                remark);
        } else {
            logger.info("订单号：" + jewelRecord.getCode() + "已支付，重复回调");
        }
    }

    @Override
    public Paginable<JewelRecord> queryJewelRecordPage(int start, int limit,
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
                // 判断是否评论
                boolean result = jewelInteractBO.isComment(
                    jewelRecord.getUserId(), jewelRecord.getCode(),
                    jewelRecord.getJewelCode());
                jewelRecord.setIsComment(EBoolean.NO.getCode());
                if (result) {
                    jewelRecord.setIsComment(EBoolean.YES.getCode());
                }
            }
        }
        return page;
    }

    @Override
    public Paginable<JewelRecord> queryMyJewelRecordPage(int start, int limit,
            JewelRecord condition) {
        return jewelRecordBO.queryMyJewelRecordPage(start, limit, condition);

    }

    @Override
    public List<JewelRecord> queryJewelRecordList(JewelRecord condition) {
        List<JewelRecord> list = jewelRecordBO.queryJewelRecordList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (JewelRecord jewelRecord : list) {
                JewelRecordNumber imCondition = new JewelRecordNumber();
                imCondition.setRecordCode(jewelRecord.getCode());
                List<JewelRecordNumber> recordNumberList = jewelRecordNumberBO
                    .queryJewelRecordNumberList(imCondition);
                jewelRecord.setJewelRecordNumberList(recordNumberList);
                Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
                jewelRecord.setJewel(jewel);
            }
        }
        return list;
    }

    @Override
    public JewelRecord getJewelRecord(String code, String userId) {
        JewelRecord jewelRecord = jewelRecordBO.getJewelRecord(code);
        if (StringUtils.isNotBlank(userId)) {
            if (!jewelRecord.getUserId().equals(userId)) {
                throw new BizException("xn0000", "您没有这个号码");
            }
        }
        Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
        jewelRecord.setJewel(jewel);
        JewelRecordNumber jewelRecordNumber = new JewelRecordNumber();
        jewelRecordNumber.setRecordCode(code);
        List<JewelRecordNumber> jewelRecordNumberList = jewelRecordNumberBO
            .queryJewelRecordNumberList(jewelRecordNumber);
        jewelRecord.setJewelRecordNumberList(jewelRecordNumberList);
        // 判断是否评论
        boolean result = jewelInteractBO.isComment(jewelRecord.getUserId(),
            jewelRecord.getCode(), jewelRecord.getJewelCode());
        jewelRecord.setIsComment(EBoolean.NO.getCode());
        if (result) {
            jewelRecord.setIsComment(EBoolean.YES.getCode());
        }
        return jewelRecord;
    }
}
