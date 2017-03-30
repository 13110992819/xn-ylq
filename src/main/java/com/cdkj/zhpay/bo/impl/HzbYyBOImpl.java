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
import com.cdkj.zhpay.dto.res.XN000001Res;
import com.cdkj.zhpay.enums.EBoolean;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EPrizeCurrency;
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
    public String saveHzbYy(XN000001Res prize, User yyUser, Hzb hzb,
            String deviceNo) {
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

            data.setCreateDatetime(new Date());
            data.setSystemCode(hzb.getSystemCode());
            data.setCompanyCode(hzb.getCompanyCode());
            hzbYyDAO.insert(data);
        }
        return code;
    }

    /**
     * 判断是否第三次且前面两次没有摇到红包币
     * @see com.cdkj.zhpay.bo.IHzbYyBO#isHaveHB(java.lang.String)
     */
    private String isHaveHB(String userId) {
        // 0 代表 无/1 代表有/-1 代表前面两次都没有红包
        String haveHbb = "0";
        HzbYy condition = new HzbYy();
        condition.setUserId(userId);
        int count = hzbYyDAO.selectTotalCount(condition).intValue();
        int start = (count - 2) < 0 ? 0 : (count - 2);
        List<HzbYy> dataList = hzbYyDAO.selectList(condition, start, count);
        if (CollectionUtils.isNotEmpty(dataList)) {
            // 判断前面1次，2次是否有红包
            if (count % 3 != 0) {
                for (HzbYy hzbYy : dataList) {
                    if (EPrizeCurrency.ZH_HBB.getCode().equals(
                        hzbYy.getYyCurrency())) {
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

    // 摇一摇全局规则：
    // 摇钱树一天只能摇n次
    // 一个设备一天只能摇n次
    // 一个账号一天只能摇n次

    @Override
    public void checkYyGlobalRule(Hzb hzb, User yyUser, String deviceNo) {
        checkYyGlobalRule(hzb);
        checkYyGlobalRule(hzb.getSystemCode(), yyUser, deviceNo);
    }

    @Override
    public void checkYyGlobalRule(Hzb hzb) {
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

        // 限制规则1：手机和设备加起来总次数小于最小次数
        int minMaxCount = userDayMaxCount > deviceDayMaxCount ? deviceDayMaxCount
                : userDayMaxCount;
        HzbYy yyCondition = new HzbYy();
        yyCondition.setCreateDatetimeStart(DateUtil.getTodayStart());
        yyCondition.setCreateDatetimeEnd(DateUtil.getTodayEnd());
        yyCondition.setUserId(yyUser.getUserId());
        yyCondition.setDeviceNo(deviceNo);
        if (getTotalCount(yyCondition) >= minMaxCount) {
            throw new BizException("xn0000", "您今天已摇" + minMaxCount + "次，请明天再来哦");
        }
        // 限制规则2:一个账号一天只能摇n次
        yyCondition.setUserId(yyUser.getUserId());
        yyCondition.setDeviceNo(null);
        if (getTotalCount(yyCondition) >= userDayMaxCount) {
            throw new BizException("xn0000", "您的账号今天已摇" + userDayMaxCount
                    + "次，请明天再来哦");
        }
        // 限制规则3:一个设备一天只能摇n次
        yyCondition.setUserId(null);
        yyCondition.setDeviceNo(deviceNo);
        if (getTotalCount(yyCondition) >= deviceDayMaxCount) {
            throw new BizException("xn0000", "您的手机今天已摇" + deviceDayMaxCount
                    + "次，请明天再来哦");
        }
    }

    @Override
    public XN000001Res calculatePrizeByCG(Hzb hzb) {
        // 产生随机数
        Long randAmount = doGeneralAmount();
        // 判断人民币，菜狗币和积分币是否有机会参与中奖抽取
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
        Map<String, String> rateMap = sysConfigBO
            .getConfigsMap(ESystemCode.Caigo.getCode());
        List<Double> prizeList = new ArrayList<Double>();
        for (String currency : result) {
            if (ECurrency.CNY.getCode().equals(currency)) {
                prizeList.add(Double.valueOf(rateMap
                    .get(SysConstants.CG_YC_CNY_WEIGHT)));
            } else if (ECurrency.CGB.getCode().equals(currency)) {
                prizeList.add(Double.valueOf(rateMap
                    .get(SysConstants.CG_YC_CGB_WEIGHT)));
            } else if (ECurrency.JF.getCode().equals(currency)) {
                prizeList.add(Double.valueOf(rateMap
                    .get(SysConstants.CG_YC_JF_WEIGHT)));
            }
        }
        int prizeIndex = PrizeUtil.getPrizeIndex(prizeList);
        // 产生随机数
        return new XN000001Res(randAmount, result.get(prizeIndex));
    }

    /** 
     *  产生随机数
     * @create: 2017年3月30日 下午9:25:38 xieyj
     * @history: 
     */
    private Long doGeneralAmount() {
        Map<String, String> rateMap = sysConfigBO
            .getConfigsMap(ESystemCode.Caigo.getCode());
        Long yyAmountMin = Long
            .valueOf(rateMap.get(SysConstants.YY_AMOUNT_MIN));
        Long yyAmountMax = Long
            .valueOf(rateMap.get(SysConstants.YY_AMOUNT_MAX));
        Long randAmount = Double.valueOf(
            (SysConstants.AMOUNT_RADIX
                    * (yyAmountMin + (yyAmountMax - yyAmountMin)) * Math
                .random())).longValue();
        return randAmount;
    }

    @Override
    public XN000001Res calculatePrizeByZH(User yyUser) {
        /*
         * List<Prize> prizeList = new ArrayList<Prize>(); String haveHb =
         * hzbYyBO.isHaveHB(yyUser.getUserId()); String type = null; if
         * (EBoolean.YES.getCode().equals(haveHb)) { type =
         * String.valueOf(PrizeUtil.getPrizeIndex(prizeList) + 1); } else if
         * (EBoolean.NO.getCode().equals(haveHb)) { prizeList.add(new
         * Prize(EPrizeType.HBB.getCode(), ycHbbWeight)); type =
         * String.valueOf(PrizeUtil.getPrizeIndex(prizeList) + 1); } else { type
         * = EPrizeType.HBB.getCode(); } Map<String, String> rateMap =
         * sysConfigBO.getConfigsMap(hzb .getSystemCode()); Double ycQbbWeight =
         * getWeight(rateMap, SysConstants.YC_QBB); Double ycGwbWeight =
         * getWeight(rateMap, SysConstants.YC_GWB); Double ycHbbWeight =
         * Double.valueOf(rateMap.get(SysConstants.YC_HBB)); prizeList.add(new
         * Prize(EPrizeType.QBB.getCode(), ycQbbWeight)); prizeList.add(new
         * Prize(EPrizeType.GWB.getCode(), ycGwbWeight)); // 获取数量 int quantity =
         * getQuantity(rateMap);
         */
        return new XN000001Res(1000L, ECurrency.QBB.getCode());

    }

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

    private int getQuantity(Map<String, String> rateMap) {
        Integer yyAmountMin = Double.valueOf(
            Double.valueOf(rateMap.get(SysConstants.YY_AMOUNT_MIN))
                    * SysConstants.AMOUNT_RADIX).intValue();
        Integer yyAmountMAX = Double.valueOf(
            Double.valueOf(rateMap.get(SysConstants.YY_AMOUNT_MAX))
                    * SysConstants.AMOUNT_RADIX).intValue();
        Random rand = new Random();
        return rand.nextInt(yyAmountMAX - yyAmountMin) + yyAmountMin;
    }

}
