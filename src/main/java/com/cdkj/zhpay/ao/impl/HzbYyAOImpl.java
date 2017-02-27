package com.cdkj.zhpay.ao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IHzbYyAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IHzbHoldBO;
import com.cdkj.zhpay.bo.IHzbYyBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.PrizeUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.core.CalculationUtil;
import com.cdkj.zhpay.domain.HzbHold;
import com.cdkj.zhpay.domain.HzbYy;
import com.cdkj.zhpay.domain.Prize;
import com.cdkj.zhpay.domain.UserExt;
import com.cdkj.zhpay.dto.res.XN805060Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.dto.res.XN808460Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.EBoolean;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EPrizeType;

@Service
public class HzbYyAOImpl implements IHzbYyAO {
    @Autowired
    private IHzbHoldBO hzbHoldBO;

    @Autowired
    private IHzbYyBO hzbYyBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IUserBO userBO;

    /**
     * 业务逻辑：
     * 1、限制规则判断
     * 2、获取参数数据
     * 3、判断三次只能有一次领到红包
     * 4、领到红包时，触发摇一摇分销规则
     * @see com.cdkj.zhpay.ao.IHzbYyAO#doHzbYy(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public XN808460Res doHzbYy(String userId, Long hzbHoldId, String deviceNo) {
        XN805901Res yyUser = userBO.getRemoteUser(userId, userId);
        HzbHold hzbHold = hzbHoldBO.getHzbHold(hzbHoldId);
        // 验证次数
        hzbYyBO.checkHzbYyCondition(hzbHold.getSystemCode(), hzbHoldId, userId,
            deviceNo);
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(
            hzbHold.getSystemCode(), null);
        String type = null;
        Double ycQbbWeight = getWeight(rateMap, SysConstants.YC_QBB);
        Double ycGwbWeight = getWeight(rateMap, SysConstants.YC_GWB);
        Double ycHbbWeight = Double.valueOf(rateMap.get(SysConstants.YC_HBB));
        String haveHb = hzbYyBO.isHaveHB(userId);
        List<Prize> prizeList = new ArrayList<Prize>();
        prizeList.add(new Prize(EPrizeType.QBB.getCode(), ycQbbWeight));
        prizeList.add(new Prize(EPrizeType.GWB.getCode(), ycGwbWeight));
        if (EBoolean.YES.getCode().equals(haveHb)) {
            type = String.valueOf(PrizeUtil.getPrizeIndex(prizeList) + 1);
        } else if (EBoolean.NO.getCode().equals(haveHb)) {
            prizeList.add(new Prize(EPrizeType.HBB.getCode(), ycHbbWeight));
            type = String.valueOf(PrizeUtil.getPrizeIndex(prizeList) + 1);
        } else {
            type = EPrizeType.HBB.getCode();
        }
        // 获取数量
        int quantity = getQuantity(rateMap);
        HzbYy data = new HzbYy();
        data.setUserId(userId);
        data.setHzbHoldId(hzbHoldId);
        data.setDeviceNo(deviceNo);
        data.setType(type);
        data.setQuantity(quantity);
        hzbYyBO.saveHzbYy(data);
        // 更新被摇次数
        hzbHoldBO.refreshRockNum(hzbHold.getId(),
            hzbHold.getPeriodRockNum() + 1, hzbHold.getTotalRockNum() + 1);
        String currency = null;
        if (EPrizeType.GWB.getCode().equals(type)) {
            currency = ECurrency.GWB.getCode();
        } else if (EPrizeType.QBB.getCode().equals(type)) {
            currency = ECurrency.QBB.getCode();
        } else if (EPrizeType.HBB.getCode().equals(type)) {
            currency = ECurrency.HBB.getCode();
            distributeAmount(hzbHold.getSystemCode(), yyUser,
                hzbHold.getUserId());
        }
        String quantityStr = CalculationUtil.divi(Long.valueOf(quantity));
        String toBizNote = EBizType.AJ_YYJL.getValue()
                + EPrizeType.getResultMap().get(type).getValue() + ",数量:"
                + quantityStr;
        String fromBizNote = UserUtil.getUserMobile(yyUser.getMobile())
                + toBizNote;
        // 奖励划拨
        accountBO.doTransferFcBySystem(hzbHold.getSystemCode(), userId,
            currency, Long.valueOf(quantity), EBizType.AJ_YYJL.getCode(),
            fromBizNote, toBizNote);
        return new XN808460Res(type, quantityStr);
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

    @Override
    public Paginable<HzbYy> queryHzbYyPage(int start, int limit, HzbYy condition) {
        return hzbYyBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<HzbYy> queryHzbYyList(HzbYy condition) {
        return hzbYyBO.queryHzbYyList(condition);
    }

    @Override
    public HzbYy getHzbYy(String code) {
        return hzbYyBO.getHzbYy(code);
    }

    // 汇赚宝分成:
    // 1、数据准备
    // 2、计算分成:针对用户_已购买汇赚宝的一级二级推荐人和所在辖区用户
    private void distributeAmount(String systemCode, XN805901Res yyUser,
            String userId) {
        // C用户摇一摇分成
        XN805901Res cUser = userFcAmount(systemCode, yyUser, userId,
            SysConstants.YY_CUSER);
        // B用户摇一摇分成
        String bUserId = cUser.getUserReferee();
        boolean bHzbResult = hzbHoldBO.isHzbHoldExistByUser(bUserId);
        if (StringUtils.isNotBlank(bUserId) && bHzbResult) {
            XN805901Res bUser = this.userFcAmount(systemCode, yyUser, bUserId,
                SysConstants.YY_BUSER);
            String aUserId = bUser.getUserReferee();
            boolean aHzbResult = hzbHoldBO.isHzbHoldExistByUser(aUserId);
            if (StringUtils.isNotBlank(aUserId) && aHzbResult) {
                // A用户摇一摇分成
                userFcAmount(systemCode, yyUser, aUserId, SysConstants.YY_AUSER);
            }
        }
        // 辖区分成
        UserExt userExt = cUser.getUserExt();
        if (userExt != null) {
            if (StringUtils.isNotBlank(userExt.getProvince())) {
                // 省合伙人
                XN805060Res provinceUser = userBO.getPartnerUserInfo(
                    userExt.getProvince(), null, null);
                if (provinceUser != null) {
                    areaFcAmount(systemCode, yyUser, provinceUser,
                        SysConstants.YY_PROVINCE, "省");
                }
                if (StringUtils.isNotBlank(userExt.getCity())) {
                    // 市合伙人
                    XN805060Res cityUser = userBO.getPartnerUserInfo(
                        userExt.getProvince(), userExt.getCity(), null);
                    if (cityUser != null) {
                        areaFcAmount(systemCode, yyUser, cityUser,
                            SysConstants.YY_CITY, "市");
                    }
                    if (StringUtils.isNotBlank(userExt.getArea())) {
                        // 县合伙人
                        XN805060Res areaRes = userBO.getPartnerUserInfo(
                            userExt.getProvince(), userExt.getCity(),
                            userExt.getArea());
                        if (areaRes != null) {
                            areaFcAmount(systemCode, yyUser, areaRes,
                                SysConstants.YY_AREA, "县");
                        }
                    }
                }
            }
        }
    }

    private XN805901Res userFcAmount(String systemCode, XN805901Res yyUser,
            String userId, String sysConstants) {
        // 分销规则
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        XN805901Res refUser = userBO.getRemoteUser(userId, userId);
        Double fc = Double.valueOf(rateMap.get(sysConstants));
        Long fcAmount = Double.valueOf(fc * SysConstants.AMOUNT_RADIX)
            .longValue();
        String toBizNote = UserUtil.getUserMobile(yyUser.getMobile())
                + EBizType.AJ_YYFC.getValue();
        String fromBizNote = toBizNote + ","
                + UserUtil.getUserMobile(refUser.getMobile()) + "分成";
        accountBO.doTransferFcBySystem(systemCode, userId,
            ECurrency.HBYJ.getCode(), fcAmount, EBizType.AJ_YYFC.getCode(),
            fromBizNote, toBizNote);
        return refUser;
    }

    private void areaFcAmount(String systemCode, XN805901Res yyUser,
            XN805060Res areaUser, String sysConstants, String remark) {
        // 分销规则
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        Double fc = Double.valueOf(rateMap.get(sysConstants));
        Long fcAmount = Double.valueOf(fc * SysConstants.AMOUNT_RADIX)
            .longValue();
        String toBizNote = UserUtil.getUserMobile(yyUser.getMobile())
                + EBizType.AJ_YYFC.getValue();
        String fromBizNote = toBizNote + "," + remark + "合伙人"
                + UserUtil.getUserMobile(areaUser.getMobile()) + "分成";
        toBizNote = toBizNote + "," + remark + "合伙人分成";
        accountBO.doTransferFcBySystem(systemCode, areaUser.getUserId(),
            ECurrency.HBYJ.getCode(), fcAmount, EBizType.AJ_YYFC.getCode(),
            fromBizNote, toBizNote);
    }
}
