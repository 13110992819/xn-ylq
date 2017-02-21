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
import com.cdkj.zhpay.enums.ESysUser;

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
        if (EBoolean.YES.getCode().equals(haveHb)) {
            List<Prize> prizeList = new ArrayList<Prize>();
            prizeList.add(new Prize(EPrizeType.QBB.getCode(), ycQbbWeight));
            prizeList.add(new Prize(EPrizeType.GWB.getCode(), ycGwbWeight));
            type = String.valueOf(PrizeUtil.getPrizeIndex(prizeList) + 1);
        } else if (EBoolean.NO.getCode().equals(haveHb)) {
            List<Prize> prizeList = new ArrayList<Prize>();
            prizeList.add(new Prize(EPrizeType.QBB.getCode(), ycQbbWeight));
            prizeList.add(new Prize(EPrizeType.GWB.getCode(), ycGwbWeight));
            prizeList.add(new Prize(EPrizeType.HBB.getCode(), ycHbbWeight));
            type = String.valueOf(PrizeUtil.getPrizeIndex(prizeList) + 1);
        } else {
            type = EPrizeType.HBB.getCode();
        }
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
            distributeAmount(hzbHold.getSystemCode(), hzbHold.getUserId());
        }
        String quantityStr = CalculationUtil.divi(Long.valueOf(quantity));
        // 奖励划拨
        accountBO.doTransferAmountByUser(hzbHold.getSystemCode(),
            ESysUser.SYS_USER.getCode(), userId, currency,
            Long.valueOf(quantity), EBizType.AJ_YYJL.getCode(),
            EBizType.AJ_YYJL.getValue()
                    + EPrizeType.getResultMap().get(type).getValue() + ",数量:"
                    + quantityStr);
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
    // 2、计算分成
    private void distributeAmount(String systemCode, String userId) {
        // C用户摇一摇分成
        XN805901Res cUser = userFcAmount(systemCode, userId,
            SysConstants.YY_CUSER);
        // B用户摇一摇分成
        String bUserId = cUser.getUserReferee();
        boolean bHzbResult = hzbHoldBO.isHzbHoldExistByUser(bUserId);
        if (StringUtils.isNotBlank(bUserId) && bHzbResult) {
            XN805901Res bUser = this.userFcAmount(systemCode, bUserId,
                SysConstants.YY_BUSER);
            String aUserId = bUser.getUserReferee();
            boolean aHzbResult = hzbHoldBO.isHzbHoldExistByUser(aUserId);
            if (StringUtils.isNotBlank(aUserId) && aHzbResult) {
                // A用户摇一摇分成
                userFcAmount(systemCode, aUserId, SysConstants.YY_AUSER);
            }
        }
        // 辖区分成
        UserExt userExt = cUser.getUserExt();
        if (userExt != null) {
            if (StringUtils.isNotBlank(userExt.getProvince())) {
                // 省合伙人
                XN805060Res provinceRes = userBO.getPartnerUserInfo(
                    userExt.getProvince(), null, null);
                if (provinceRes != null) {
                    areaFcAmount(systemCode, provinceRes.getUserId(),
                        SysConstants.YY_PROVINCE, "省");
                }
                if (StringUtils.isNotBlank(userExt.getCity())) {
                    // 市合伙人
                    XN805060Res cityRes = userBO.getPartnerUserInfo(
                        userExt.getProvince(), userExt.getCity(), null);
                    if (cityRes != null) {
                        areaFcAmount(systemCode, cityRes.getUserId(),
                            SysConstants.YY_CITY, "市");
                    }
                    if (StringUtils.isNotBlank(userExt.getArea())) {
                        // 县合伙人
                        XN805060Res areaRes = userBO.getPartnerUserInfo(
                            userExt.getProvince(), userExt.getCity(),
                            userExt.getArea());
                        if (areaRes != null) {
                            areaFcAmount(systemCode, areaRes.getUserId(),
                                SysConstants.YY_AREA, "县");
                        }
                    }
                }
            }
        }
    }

    private XN805901Res userFcAmount(String systemCode, String userId,
            String sysConstants) {
        // 分销规则
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        XN805901Res refUser = userBO.getRemoteUser(userId, userId);
        Double fc = Double.valueOf(rateMap.get(sysConstants));
        Long fcAmount = Double.valueOf(fc * SysConstants.AMOUNT_RADIX)
            .longValue();
        String cBizNote = EBizType.AJ_YYFC.getValue() + ",用户["
                + refUser.getMobile() + "]红包业绩分成";
        accountBO.doTransferFcBySystem(systemCode, userId,
            ECurrency.HBYJ.getCode(), fcAmount, EBizType.AJ_YYFC.getCode(),
            cBizNote);
        return refUser;
    }

    private void areaFcAmount(String systemCode, String userId,
            String sysConstants, String remark) {
        // 分销规则
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        Double fc = Double.valueOf(rateMap.get(sysConstants));
        Long fcAmount = Double.valueOf(fc * SysConstants.AMOUNT_RADIX)
            .longValue();
        XN805901Res user = userBO.getRemoteUser(userId, userId);
        String bizNote = EBizType.AJ_YYFC.getValue() + ",合伙人" + remark + "用户["
                + user.getMobile() + "]分成";
        accountBO.doTransferFcBySystem(systemCode, userId,
            ECurrency.HBYJ.getCode(), fcAmount, EBizType.AJ_YYFC.getCode(),
            bizNote);
    }
}
