package com.cdkj.zhpay.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IHzbTemplateBO;
import com.cdkj.zhpay.bo.IHzbYyBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.PrizeUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.dao.IHzbYyDAO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.domain.HzbYy;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.XN615120Res;
import com.cdkj.zhpay.enums.EBoolean;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.ESystemCode;
import com.cdkj.zhpay.exception.BizException;

@Component
public class HzbYyBOImpl extends PaginableBOImpl<HzbYy> implements IHzbYyBO {

    @Autowired
    private IHzbYyDAO hzbYyDAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IHzbTemplateBO hzbTemplateBO;

    @Override
    public String saveHzbYy(XN615120Res prize, User yyUser, Hzb hzb,
            String deviceNo, String ownerFcCurrency, Long ownerFcAmount) {
        String code = null;
        if (prize != null && hzb != null) {
            HzbYy data = new HzbYy();
            code = OrderNoGenerater.generateM(EGeneratePrefix.SHAKE.getCode());
            data.setCode(code);
            data.setHzbCode(hzb.getCode());
            data.setYyCurrency(prize.getYyCurrency());
            data.setYyAmount(prize.getYyAmount());

            data.setUserId(yyUser.getUserId());
            data.setDeviceNo(deviceNo);
            data.setOwnerFcCurrency(ownerFcCurrency);
            data.setOwnerFcAmount(ownerFcAmount);

            data.setCreateDatetime(new Date());
            data.setSystemCode(hzb.getSystemCode());
            data.setCompanyCode(hzb.getCompanyCode());
            hzbYyDAO.insert(data);
        }
        return code;
    }

    @Override
    public void checkYyGlobalRule(Hzb hzb, User yyUser, String deviceNo) {
        checkYyGlobalRule(hzb);
        checkYyGlobalRule(hzb.getSystemCode(), yyUser, deviceNo);
    }

    private void checkYyGlobalRule(Hzb hzb) {
        // 取到摇钱树一天能摇的次数
        HzbTemplate hzbTemplate = hzbTemplateBO.getHzbTemplate(hzb
            .getTemplateCode());
        Integer periodRockNum = hzbTemplate.getPeriodRockNum();
        HzbYy yyCondition = new HzbYy();
        yyCondition.setHzbCode(hzb.getCode());
        yyCondition.setCreateDatetimeStart(DateUtil.getTodayStart());
        yyCondition.setCreateDatetimeEnd(DateUtil.getTodayEnd());
        if (getTotalCount(yyCondition) >= periodRockNum) {
            throw new BizException("xn0000", "对应树今天已摇" + periodRockNum
                    + "次，请明天再来哦");
        }
    }

    @Override
    public void checkYyGlobalRule(String systemCode, User yyUser,
            String deviceNo) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode);
        // 取到一个账号一天能摇的次数
        int userDayMaxCount = Integer.valueOf(rateMap
            .get(SysConstants.USER_DAY_MAX_COUNT));
        // 取到一个设备一天能摇的次数
        int deviceDayMaxCount = Integer.valueOf(rateMap
            .get(SysConstants.DEVICE_DAY_MAX_COUNT));

        HzbYy yyCondition = new HzbYy();
        yyCondition.setCreateDatetimeStart(DateUtil.getTodayStart());
        yyCondition.setCreateDatetimeEnd(DateUtil.getTodayEnd());
        // 限制规则1:一个账号一天只能摇n次
        yyCondition.setUserId(yyUser.getUserId());
        yyCondition.setDeviceNo(null);
        long userTodayYyNum = getTotalCount(yyCondition);
        if (userTodayYyNum >= userDayMaxCount) {
            throw new BizException("xn0000", "您的账号今天已摇" + userDayMaxCount
                    + "次，请明天再来哦");
        }
        // 限制规则2:一个设备一天只能摇n次
        yyCondition.setUserId(null);
        yyCondition.setDeviceNo(deviceNo);
        long deviceTodayYyNum = getTotalCount(yyCondition);
        if (deviceTodayYyNum >= deviceDayMaxCount) {
            throw new BizException("xn0000", "您的手机今天已摇" + deviceDayMaxCount
                    + "次，请明天再来哦");
        }
    }

    @Override
    public XN615120Res calculatePrizeByCG(Hzb hzb) {
        // 确定金额
        Long randAmount = doGeneralAmount(ESystemCode.Caigo.getCode());
        // 开始确定币种-------
        // 首先判断参与的币种种类
        String currency = null;
        List<String> result = new ArrayList<String>();
        HzbTemplate hzbTemplate = hzbTemplateBO.getHzbTemplate(hzb
            .getTemplateCode());
        Long backAmount1 = hzbTemplate.getBackAmount1() - hzb.getBackAmount1();
        if (backAmount1 > 0) {
            result.add(ECurrency.CNY.getCode());
            if (backAmount1 < randAmount) {
                randAmount = backAmount1;
            }
        }
        Long backAmount2 = hzbTemplate.getBackAmount2() - hzb.getBackAmount2();
        if (backAmount2 > 0) {
            result.add(ECurrency.CGB.getCode());
            if (backAmount2 < randAmount) {
                randAmount = backAmount2;
            }
        }
        Long backAmount3 = hzbTemplate.getBackAmount3() - hzb.getBackAmount3();
        if (backAmount3 > 0) {
            result.add(ECurrency.JF.getCode());
            if (backAmount3 < randAmount) {
                randAmount = backAmount3;
            }
        }
        if (result.size() > 1) {
            currency = result.get(getRandom(0, result.size()));
        } else if (result.size() == 1) {
            currency = result.get(0);
        }
        // 产生随机数
        return new XN615120Res(randAmount, currency);
    }

    // 随机产生金额
    private Long doGeneralAmount(String systemCode) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode);
        Double yyAmountMin = Double.valueOf(rateMap
            .get(SysConstants.YY_AMOUNT_MIN));
        Double yyAmountMax = Double.valueOf(rateMap
            .get(SysConstants.YY_AMOUNT_MAX)) + 1;
        Long randAmount = Double.valueOf(
            (SysConstants.AMOUNT_RADIX
                    * (yyAmountMin + (yyAmountMax - yyAmountMin)) * Math
                .random())).longValue();
        return randAmount;
    }

    private static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    @Override
    public XN615120Res calculatePrizeByZH(Hzb hzb, User yyUser) {
        // 1、确定参与币种
        String yyCurrency = doGeneralCurrency(hzb, yyUser);
        // 2、抽奖确定金额
        Long yyAmount = doGeneralAmount(ESystemCode.ZHPAY.getCode());
        return new XN615120Res(yyAmount, yyCurrency);
    }

    /** 
     * @param hzb
     * @param yyUser
     * @return 
     * @create: 2017年4月3日 下午2:04:59 xieyj
     * @history: 
     */
    private String doGeneralCurrency(Hzb hzb, User yyUser) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(hzb
            .getSystemCode());
        // 0 代表 无/1 代表有/-1 代表前面两次都没有红包
        String hbFlag = isHaveHB(hzb.getCode(), yyUser.getUserId());
        List<String> currencyList = new ArrayList<String>();
        List<Double> prizeList = new ArrayList<Double>();
        Double ycQbbWeight = getWeight(rateMap, SysConstants.YC_QBB);
        Double ycGwbWeight = getWeight(rateMap, SysConstants.YC_GWB);
        Double ycHbbWeight = getWeight(rateMap, SysConstants.YC_HBB);
        if (EBoolean.NO.getCode().equals(hbFlag)) {
            currencyList.add(ECurrency.GWB.getCode());
            currencyList.add(ECurrency.QBB.getCode());
            currencyList.add(ECurrency.HBB.getCode());
            prizeList.add(ycGwbWeight);
            prizeList.add(ycQbbWeight);
            prizeList.add(ycHbbWeight);
        } else if (EBoolean.YES.getCode().equals(hbFlag)) {
            currencyList.add(ECurrency.GWB.getCode());
            currencyList.add(ECurrency.QBB.getCode());
            prizeList.add(ycGwbWeight);
            prizeList.add(ycQbbWeight);
        } else {// 前面两次都没有红包
            currencyList.add(ECurrency.HBB.getCode());
            prizeList.add(ycHbbWeight);
        }
        int prizeIndex = PrizeUtil.getPrizeIndex(prizeList);
        return currencyList.get(prizeIndex);
    }

    /**
     * 判断是否第三次且前面两次没有摇到红包币
     * @param hzbCode
     * @param userId
     * @return 
     * @create: 2017年4月3日 下午1:06:59 xieyj
     * @history:
     */
    private String isHaveHB(String hzbCode, String userId) {
        // 0 代表 无/1 代表有/-1 代表前面两次都没有红包
        String haveHbb = "0";
        HzbYy condition = new HzbYy();
        condition.setHzbCode(hzbCode);
        condition.setUserId(userId);
        int count = hzbYyDAO.selectTotalCount(condition).intValue();
        int start = (count - 2) < 0 ? 0 : (count - 2);
        List<HzbYy> dataList = hzbYyDAO.selectList(condition, start, count);
        if (CollectionUtils.isNotEmpty(dataList)) {
            // 判断前面第1次，第2次是否有红包
            if (count % 3 != 0) {
                for (HzbYy hzbYy : dataList) {
                    if (ECurrency.HBB.getCode().equals(hzbYy.getYyCurrency())) {
                        haveHbb = "1";
                        break;
                    }
                }
                // 判断当前是否第三次，且前面两次无红包
                if (count % 3 == 2 && haveHbb.equals(EBoolean.NO.getCode())) {
                    haveHbb = "-1";
                }
            }
        }
        return haveHbb;
    }

    /**
     * 获取权重，没有权重则随机产生一个
     * @param rateMap
     * @param ycType
     * @return 
     * @create: 2017年4月3日 下午2:10:30 xieyj
     * @history:
     */
    private Double getWeight(Map<String, String> rateMap, String ycType) {
        Double result = 0.0D;
        String weight = rateMap.get(ycType);
        if (StringUtils.isBlank(weight)) {
            result = Math.random();
        } else {
            result = Double.valueOf(weight);
        }
        return result;
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbYyBO#getTotalHzbYyCount(java.lang.Object, java.util.Date, java.lang.String)
     */
    @Override
    public Long getTotalHzbYyCount(Date dateStart, Date dateEnd, String hzbCode) {
        HzbYy condition = new HzbYy();
        condition.setCreateDatetimeStart(dateStart);
        condition.setCreateDatetimeEnd(dateEnd);
        condition.setHzbCode(hzbCode);
        return hzbYyDAO.selectTotalCount(condition);
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbYyBO#getTotalOwnerFcAmount(java.util.Date, java.util.Date, java.lang.String)
     */
    @Override
    public Long getTotalOwnerFcAmount(Date dateStart, Date dateEnd,
            String hzbCode) {
        Long ownerFcAmount = 0l;
        HzbYy condition = new HzbYy();
        condition.setCreateDatetimeStart(dateStart);
        condition.setCreateDatetimeEnd(dateEnd);
        condition.setHzbCode(hzbCode);
        List<HzbYy> list = hzbYyDAO.selectList(condition);
        for (HzbYy hzbYy : list) {
            ownerFcAmount += hzbYy.getOwnerFcAmount();
        }
        return ownerFcAmount;
    }
}
