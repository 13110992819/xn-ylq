package com.cdkj.zhpay.ao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IHzbAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.IHzbMgiftBO;
import com.cdkj.zhpay.bo.IHzbTemplateBO;
import com.cdkj.zhpay.bo.IHzbYyBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbMgift;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.domain.UserExt;
import com.cdkj.zhpay.dto.res.XN802180Res;
import com.cdkj.zhpay.dto.res.XN802527Res;
import com.cdkj.zhpay.dto.res.XN805060Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.dto.res.XN808802Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.EBoolean;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EDiviFlag;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EHzbStatus;
import com.cdkj.zhpay.enums.EHzbTemplateStatus;
import com.cdkj.zhpay.enums.EPayType;
import com.cdkj.zhpay.enums.ESysUser;
import com.cdkj.zhpay.exception.BizException;

@Service
public class HzbAOImpl implements IHzbAO {

    @Autowired
    private IHzbTemplateBO hzbTemplateBO;

    @Autowired
    private IHzbBO hzbBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IHzbYyBO hzbYyBO;

    @Autowired
    private IHzbMgiftBO hzbMgiftBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Override
    @Transactional
    public Object buyHzbOfZH(String userId, String hzbTemplateCode,
            String payType) {
        Object result = null;
        // 判断用户是否实名认证
        XN805901Res userRes = userBO.getRemoteUser(userId, userId);
        if (!EBoolean.YES.getCode().equals(userRes.getIdentityFlag())) {
            throw new BizException("xn0000", "用户未实名认证，请先实名认证");
        }
        hzbBO.checkBuy(userId);
        HzbTemplate hzbTemplate = hzbTemplateBO.getHzbTemplate(hzbTemplateCode);
        if (!EHzbTemplateStatus.ON.getCode().equals(hzbTemplate.getStatus())) {
            throw new BizException("xn0000", "该汇赚宝模板未上线，不可购买");
        }

        // 购买摇钱树
        if (EPayType.YEFR.getCode().equals(payType)) {
            result = doFRPay(userRes, hzbTemplate);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            result = doWeixinPay(userId, hzbTemplate);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            return null;
        }
        return result;
    }

    @Override
    @Transactional
    public Object buyHzbOfCG(String userId, String hzbTemplateCode,
            String payType) {
        Object result = null;
        // 判断用户是否实名认证
        XN805901Res userRes = userBO.getRemoteUser(userId, userId);
        if (!EBoolean.YES.getCode().equals(userRes.getIdentityFlag())) {
            throw new BizException("xn0000", "用户未实名认证，请先实名认证");
        }
        hzbBO.checkBuy(userId);
        return result;
    }

    /**
     * 支付回调
     * @param payCode
     * @return 
     * @create: 2017年1月6日 下午9:25:22 xieyj
     * @history:
     */
    @Override
    @Transactional
    public void paySuccess(String payGroup, String payCode, Long transAmount) {
        Hzb condition = new Hzb();
        condition.setPayGroup(payGroup);
        List<Hzb> result = hzbBO.queryHzbHoldList(condition);
        if (CollectionUtils.isEmpty(result)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        if (!transAmount.equals(hzbBO.getTotalAmount(payGroup))) {
            throw new BizException("XN000000", "金额校验错误，非正常调用");
        }
        for (Hzb hzb : result) {
            if (!EHzbStatus.TO_PAY.getCode().equals(hzb.getStatus())) {
                throw new BizException("XN000000", "汇赚宝号：" + hzb.getId()
                        + "已支付，重复回调");
            }
        }
        for (Hzb hzb : result) {
            // 更新状态
            hzbBO.refreshPayStatus(hzb.getId(), EHzbStatus.ACTIVATED.getCode(),
                payCode, transAmount);
            // 分配分成
            distributeAmount(hzb.getSystemCode(), hzb.getUserId(),
                hzb.getPrice());
            // 产生红包
            hzbMgiftBO.sendHzbMgift(hzb.getUserId());
        }
    }

    /**
     * 分润支付
     * @param userRes
     * @param hzbTemplate
     * @return 
     * @create: 2017年2月25日 下午1:20:29 xieyj
     * @history:
     */
    @Transactional
    private Object doFRPay(XN805901Res userRes, HzbTemplate hzbTemplate) {
        // 余额支付
        accountBO.doFRPay(hzbTemplate.getSystemCode(), userRes,
            ESysUser.SYS_USER.getCode(), hzbTemplate.getPrice(),
            EBizType.AJ_GMHZB);
        // 汇赚宝购买成功
        Object result = hzbBO.doFRPay(userRes.getUserId(), hzbTemplate);
        // 分销规则
        distributeAmount(hzbTemplate.getSystemCode(), userRes.getUserId(),
            hzbTemplate.getPrice());
        // 产生红包
        hzbMgiftBO.sendHzbMgift(userRes.getUserId());
        return result;
    }

    /** 
     * 微信支付
     * @param userId
     * @param hzbTemplate
     * @param ip
     * @return 
     * @create: 2017年2月22日 下午4:43:17 xieyj
     * @history: 
     */
    @Transactional
    private XN802180Res doWeixinPay(String userId, HzbTemplate hzbTemplate,
            String ip) {
        // 生成支付组号
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        // 落地本地系统消费记录，状态为未支付
        hzbBO.buySuccess(userId, hzbTemplate, payGroup);
        XN802180Res res = accountBO.doWeiXinPay(hzbTemplate.getSystemCode(),
            userId, payGroup, EBizType.AJ_GMHZB, hzbTemplate.getPrice(), ip);
        return res;
    }

    // 汇赚宝分成:
    // 1、数据准备
    // 2、计算分成:针对用户_已购买汇赚宝的一级二级推荐人和所在辖区用户
    private void distributeAmount(String systemCode, String userId, Long price) {
        XN805901Res ownerUser = userBO.getRemoteUser(userId, userId);
        // 用户分成
        String cUserId = ownerUser.getUserReferee();
        if (StringUtils.isNotBlank(cUserId)) {
            XN805901Res cUser = userBO.getRemoteUser(cUserId, cUserId);
            boolean cHzbResult = hzbBO.isHzbHoldExistByUser(cUserId);
            if (cHzbResult) {
                this.userFcAmount(systemCode, cUser, ownerUser,
                    SysConstants.HZB_CUSER, price);
            }
            // B用户分成
            String bUserId = cUser.getUserReferee();
            if (StringUtils.isNotBlank(bUserId)) {
                XN805901Res bUser = userBO.getRemoteUser(bUserId, bUserId);
                boolean bHzbResult = hzbBO.isHzbHoldExistByUser(bUserId);
                if (bHzbResult) {
                    this.userFcAmount(systemCode, bUser, ownerUser,
                        SysConstants.HZB_BUSER, price);
                }
                // A用户分成
                String aUserId = bUser.getUserReferee();
                if (StringUtils.isNotBlank(aUserId)) {
                    XN805901Res aUser = userBO.getRemoteUser(aUserId, aUserId);
                    boolean aHzbResult = hzbBO.isHzbHoldExistByUser(aUserId);
                    if (aHzbResult) {
                        this.userFcAmount(systemCode, aUser, ownerUser,
                            SysConstants.HZB_AUSER, price);
                    }
                }
            }
        }
        // 辖区分成
        UserExt userExt = ownerUser.getUserExt();
        if (userExt != null) {
            if (StringUtils.isNotBlank(userExt.getProvince())) {
                // 省合伙人
                XN805060Res provinceUser = userBO.getPartnerUserInfo(
                    userExt.getProvince(), null, null);
                if (provinceUser != null) {
                    areaFcAmount(systemCode, provinceUser, ownerUser,
                        SysConstants.HZB_PROVINCE, price, "省");
                }
                if (StringUtils.isNotBlank(userExt.getCity())) {
                    // 市合伙人
                    XN805060Res cityUser = userBO.getPartnerUserInfo(
                        userExt.getProvince(), userExt.getCity(), null);
                    if (cityUser != null) {
                        areaFcAmount(systemCode, cityUser, ownerUser,
                            SysConstants.HZB_CITY, price, "市");
                    }
                    if (StringUtils.isNotBlank(userExt.getArea())) {
                        // 县合伙人
                        XN805060Res areaUser = userBO.getPartnerUserInfo(
                            userExt.getProvince(), userExt.getCity(),
                            userExt.getArea());
                        if (areaUser != null) {
                            areaFcAmount(systemCode, areaUser, ownerUser,
                                SysConstants.HZB_AREA, price, "县");
                        }
                    }
                }
            }
        }
    }

    private void userFcAmount(String systemCode, XN805901Res fcUser,
            XN805901Res ownerUser, String sysConstants, Long price) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        Double hzbUserRate = Double.valueOf(rateMap.get(sysConstants));
        Long transAmount = Double.valueOf(hzbUserRate * price).longValue();
        if (transAmount != null && transAmount != 0) {
            String fromBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + ","
                    + UserUtil.getUserMobile(fcUser.getMobile()) + "分成";
            String toBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue();
            accountBO.doTransferFcBySystem(systemCode, fcUser.getUserId(),
                ECurrency.FRB.getCode(), transAmount,
                EBizType.AJ_GMHZBFC.getCode(), fromBizNote, toBizNote);
        }
    }

    private void areaFcAmount(String systemCode, XN805060Res areaUser,
            XN805901Res ownerUser, String sysConstants, Long price,
            String remark) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        Double rate = Double.valueOf(rateMap.get(sysConstants));
        Long transAmount = Double.valueOf(price * rate).longValue();
        if (transAmount != null && transAmount != 0) {
            String fromBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + "," + remark + "合伙人"
                    + UserUtil.getUserMobile(areaUser.getMobile()) + "分成";
            String toBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + "," + remark + "合伙人分成";
            accountBO.doTransferFcBySystem(systemCode, areaUser.getUserId(),
                ECurrency.FRB.getCode(), transAmount,
                EBizType.AJ_GMHZBFC.getCode(), fromBizNote, toBizNote);
        }
    }

    // 分页无法统计，暂时不用
    @Override
    public Paginable<Hzb> queryDistanceHzbHoldPage(int start, int limit,
            Hzb condition) {
        String distance = sysConfigBO.getConfigValue(null, null, null,
            SysConstants.HZB_DISTANCE);
        if (StringUtils.isBlank(distance)) {
            // 默认1000米
            distance = SysConstants.HZB_DISTANCE_DEF;
        }
        condition.setDistance(distance);
        return hzbBO.queryDistancePaginable(start, limit, condition);
    }

    @Override
    @Transactional
    public Object queryHzbList(String latitude, String longitude,
            String userId, String deviceNo, String companyCode,
            String systemCode) {
        XN805901Res userRes = userBO.getRemoteUser(userId, userId);
        hzbYyBO.checkHzbYyCondition(userRes.getSystemCode(), userId, deviceNo);
        // 取距离
        String distance = sysConfigBO.getConfigValue(SysConstants.HZB_DISTANCE,
            companyCode, systemCode);
        condition.setDistance(distance);
        // 设置最多被摇次数
        Integer periodRockNum = SysConstants.HZB_YY_DAY_MAX_COUNT_DEF;
        String periodRockNumString = sysConfigBO.getConfigValue(null, null,
            null, SysConstants.HZB_YY_DAY_MAX_COUNT);
        if (StringUtils.isNotBlank(periodRockNumString)) {
            // 默认900次
            periodRockNum = Integer.valueOf(periodRockNumString);
        }
        condition.setPeriodRockNum(periodRockNum);
        List<Hzb> list = hzbBO.queryDistanceHzbHoldList(condition);
        // 截取数量
        String hzbMaxNumStr = sysConfigBO.getConfigValue(null, null, null,
            SysConstants.HZB_MAX_NUM);
        int hzbMaxNum = SysConstants.HZB_MAX_NUM_DEF;
        if (StringUtils.isNotBlank(hzbMaxNumStr)) {
            hzbMaxNum = Integer.valueOf(hzbMaxNumStr);
        }
        if (CollectionUtils.isNotEmpty(list) && list.size() > hzbMaxNum) {
            list = list.subList(0, hzbMaxNum);
        }
        for (Hzb hzb : list) {
            hzb.setShareUrl(PropertiesUtil.Config.SHARE_URL);
        }
        return list;
    }

    @Override
    public Paginable<Hzb> queryHzbHoldPage(int start, int limit, Hzb condition) {
        return hzbBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Hzb> queryHzbHoldList(Hzb condition) {
        return hzbBO.queryHzbHoldList(condition);
    }

    @Override
    public Hzb getHzb(String code) {
        return hzbBO.getHzbByUser(id);
    }

    /** 
     * @see com.cdkj.zhpay.ao.IHzbAO#doGetHzbTotalData(java.lang.String, java.lang.String)
     */
    @Override
    public XN808802Res doGetHzbTotalData(String systemCode, String userId) {
        Hzb hzb = hzbBO.getHzbByUser(userId);
        XN808802Res res = new XN808802Res();
        Date todayStart = DateUtil.getTodayStart();
        Date todayEnd = DateUtil.getTodayEnd();
        Date yesterdayEnd = DateUtil.getRelativeDate(todayStart, -1);
        // 历史被摇一摇次数
        Long historyYyTimes = hzbYyBO.getTotalHzbYyCount(null, yesterdayEnd,
            hzb.getId());
        res.setHistoryYyTimes(historyYyTimes);
        // 今日被摇一摇次数
        Long todayYyTimes = hzbYyBO.getTotalHzbYyCount(todayStart, todayEnd,
            hzb.getId());
        res.setTodayYyTimes(todayYyTimes);
        // 总的摇一摇分成
        XN802527Res accountRes = accountBO.doGetBizTotalAmount(systemCode,
            userId, ECurrency.HBYJ.getCode(), EBizType.AJ_YYFC.getCode());
        res.setYyTotalAmount(accountRes.getAmount());
        // 历史发一发次数
        Long historyMgiftTimes = hzbMgiftBO.getReceiveHzbMgiftCount(null,
            yesterdayEnd, userId);
        res.setHistoryHbTimes(historyMgiftTimes);
        // 今日发一发次数
        Long todayMgiftTimes = hzbMgiftBO.getReceiveHzbMgiftCount(todayStart,
            todayEnd, userId);
        res.setTodayHbTimes(todayMgiftTimes);
        // 总的发一发福利
        List<HzbMgift> hzbMgiftList = hzbMgiftBO.queryReceiveHzbMgift(null,
            null, userId);
        Long ffTotalHbAmount = 0L;
        for (HzbMgift hzbMgift : hzbMgiftList) {
            ffTotalHbAmount += hzbMgift.getOwnerAmount();
        }
        res.setFfTotalHbAmount(ffTotalHbAmount);
        return res;
    }

    @Override
    public Hzb myHzb(String userId) {
        Hzb hzb = null;
        // 查询是否已经购买摇钱树
        Hzb condition = new Hzb();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        List<Hzb> list = hzbBO.queryHzbHoldList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            hzb = list.get(0);
        }
        return hzb;
    }

    @Override
    public void doResetRockNumDaily() {
        hzbBO.resetPeriodRockNum();
    }
}
