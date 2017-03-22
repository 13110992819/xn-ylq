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
import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.IHzbYyBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.PrizeUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.core.CalculationUtil;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbYy;
import com.cdkj.zhpay.domain.Prize;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.domain.UserExt;
import com.cdkj.zhpay.dto.res.XN805060Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.dto.res.XN808460Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.EBoolean;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EPrizeType;
import com.cdkj.zhpay.enums.ESystemCode;
import com.cdkj.zhpay.exception.BizException;

@Service
public class HzbYyAOImpl implements IHzbYyAO {
    @Autowired
    private IHzbBO hzbBO;

    @Autowired
    private IHzbYyBO hzbYyBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IUserBO userBO;

    /**
     * 业务逻辑：树不同，摇一摇整个逻辑是不同的
     * A、菜狗的摇钱树
     * 1、能不能摇：树是否失效；人/设备是否超过次数。
     * 2、摇到什么：摇一摇的算法。
     * 3、刷新对应摇钱树生命值。
     * 
     * B、正汇的摇钱树
     * 1、能不能摇：树是否失效；人/设备是否超过次数。
     * 2、摇到什么：摇一摇的算法
     * 3、刷新对应摇钱树生命值。
     * 4、特殊处理：正汇系统摇到红包时，将促发分销规则
     * 
     */
    @Override
    @Transactional
    public XN808460Res doHzbYy(String userId, String hzbCode, String deviceNo) {
        // A、能不能摇：树是否失效；人/设备是否超过次数
        User yyUser = userBO.getRemoteUser(userId);
        Hzb hzb = hzbBO.checkActivated(hzbCode);
        hzbYyBO.checkYy(hzb, yyUser, deviceNo);
        // B、树不同，摇一摇整个逻辑是不同的
        if (ESystemCode.ZHPAY.getCode().equalsIgnoreCase(hzb.getSystemCode())) {
            return doYyByZH(yyUser, hzb, deviceNo);
        } else if (ESystemCode.Caigo.getCode().equalsIgnoreCase(
            hzb.getSystemCode())) {
            return doYyByCG(yyUser, hzb, deviceNo);
        } else {
            throw new BizException("xn0000", "所选摇钱树不属于本系统");
        }

    }

    private XN808460Res doYyByCG(User yyUser, Hzb hzb, String deviceNo) {
        // TODO Auto-generated method stub
        return null;
    }

    private XN808460Res doYyByZH(User yyUser, Hzb hzb, String deviceNo) {

        Map<String, String> rateMap = sysConfigBO.getConfigsMap(hzb
            .getSystemCode());
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
        hzbBO.refreshRockNum(hzb.getId(), hzb.getPeriodRockNum() + 1,
            hzb.getTotalRockNum() + 1);
        String currency = null;
        if (EPrizeType.GWB.getCode().equals(type)) {
            currency = ECurrency.GWB.getCode();
        } else if (EPrizeType.QBB.getCode().equals(type)) {
            currency = ECurrency.QBB.getCode();
        } else if (EPrizeType.HBB.getCode().equals(type)) {
            currency = ECurrency.HBB.getCode();
            distributeAmount(hzb.getSystemCode(), yyUser, hzb.getUserId());
        }
        String quantityStr = CalculationUtil.divi(Long.valueOf(quantity));
        String toBizNote = EBizType.AJ_YYJL.getValue()
                + EPrizeType.getResultMap().get(type).getValue() + ",数量:"
                + quantityStr;
        String fromBizNote = UserUtil.getUserMobile(yyUser.getMobile())
                + toBizNote;
        // 奖励划拨
        accountBO.doTransferFcBySystem(hzb.getSystemCode(), userId, currency,
            Long.valueOf(quantity), EBizType.AJ_YYJL.getCode(), fromBizNote,
            toBizNote);
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
        boolean bHzbResult = hzbBO.isHzbHoldExistByUser(bUserId);
        if (StringUtils.isNotBlank(bUserId) && bHzbResult) {
            XN805901Res bUser = this.userFcAmount(systemCode, yyUser, bUserId,
                SysConstants.YY_BUSER);
            String aUserId = bUser.getUserReferee();
            boolean aHzbResult = hzbBO.isHzbHoldExistByUser(aUserId);
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

    @Override
    public Paginable<HzbYy> queryHzbYyPage(int start, int limit, HzbYy condition) {
        return hzbYyBO.getPaginable(start, limit, condition);
    }
}
