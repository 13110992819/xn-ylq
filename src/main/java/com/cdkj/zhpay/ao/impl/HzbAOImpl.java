package com.cdkj.zhpay.ao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IHzbAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.IHzbHoldBO;
import com.cdkj.zhpay.bo.IHzbMgiftBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbHold;
import com.cdkj.zhpay.domain.UserExt;
import com.cdkj.zhpay.dto.res.PayBalanceRes;
import com.cdkj.zhpay.dto.res.XN802180Res;
import com.cdkj.zhpay.dto.res.XN805060Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.EBoolean;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EDiviFlag;
import com.cdkj.zhpay.enums.EHzbHoldStatus;
import com.cdkj.zhpay.enums.EPayType;
import com.cdkj.zhpay.enums.ESysUser;
import com.cdkj.zhpay.exception.BizException;

@Service
public class HzbAOImpl implements IHzbAO {
    @Autowired
    private IHzbBO hzbBO;

    @Autowired
    private IHzbHoldBO hzbHoldBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IHzbMgiftBO hzbMgiftBO;

    @Override
    public void editHzb(Hzb data) {
        if (!hzbBO.isHzbExist(data.getCode())) {
            throw new BizException("xn0000", "汇赚宝记录不存在");
        }
        hzbBO.refreshHzb(data);
    }

    @Override
    @Transactional
    public Object buyHzb(String userId, String hzbCode, String payType,
            String ip) {
        Object result = null;
        // 判断用户是否实名认证
        XN805901Res userRes = userBO.getRemoteUser(userId, userId);
        if (!EBoolean.YES.getCode().equals(userRes.getIdentityFlag())) {
            throw new BizException("xn0000", "用户未实名认证，请先实名认证");
        }
        // 查询是否已经购买摇钱树
        HzbHold condition = new HzbHold();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        if (hzbHoldBO.getTotalCount(condition) > 0) {
            throw new BizException("xn0000", "您已经购买过汇赚宝");
        }
        // 落地汇赚宝购买记录
        Hzb hzb = hzbBO.getHzb(hzbCode);
        if (EPayType.YEFR.getCode().equals(payType)) {
            result = doFRPay(userId, hzb);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            result = doWeixinPay(userId, hzb, ip);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            return null;
        }
        return result;
    }

    /** 
     * 分润支付
     * @param userId
     * @param hzb
     * @return 
     * @create: 2017年2月22日 下午4:45:09 xieyj
     * @history: 
     */
    @Transactional
    private Object doFRPay(String userId, Hzb hzb) {
        // 余额支付
        PayBalanceRes payRes = accountBO.doFRPay(hzb.getSystemCode(), userId,
            ESysUser.SYS_USER.getCode(), hzb.getPrice(), EBizType.AJ_GMHZB);
        HzbHold hzbHold = new HzbHold();
        hzbHold.setUserId(userId);
        hzbHold.setStatus(EHzbHoldStatus.ACTIVATED.getCode());
        hzbHold.setPrice(hzb.getPrice());
        hzbHold.setCurrency(hzb.getCurrency());
        hzbHold.setPeriodRockNum(0);
        hzbHold.setTotalRockNum(0);
        hzbHold.setPayAmount1(0L);
        hzbHold.setPayAmount2(0L);
        hzbHold.setPayAmount3(payRes.getFrAmount());
        hzbHold.setSystemCode(hzb.getSystemCode());
        Object result = hzbHoldBO.saveHzbHold(hzbHold);
        // 分销规则
        distributeAmount(hzbHold);
        // 产生红包
        hzbMgiftBO.sendHzbMgift(userId);
        return result;
    }

    /** 
     * 微信支付
     * @param userId
     * @param hzb
     * @param ip
     * @return 
     * @create: 2017年2月22日 下午4:43:17 xieyj
     * @history: 
     */
    private XN802180Res doWeixinPay(String userId, Hzb hzb, String ip) {
        // 获取微信APP支付信息
        String bizNote = hzb.getName() + "——汇赚宝购买";
        String body = "正汇钱包—汇赚宝";
        XN802180Res res = accountBO.doWeiXinPay(hzb.getSystemCode(), userId,
            EBizType.AJ_GMHZB, bizNote, body, hzb.getPrice(), ip);
        // 落地本地系统消费记录，状态为未支付
        HzbHold data = new HzbHold();
        data.setUserId(userId);
        data.setHzbCode(hzb.getCode());
        data.setStatus(EHzbHoldStatus.TO_PAY.getCode());
        data.setPrice(hzb.getPrice());
        data.setCurrency(hzb.getCurrency());
        data.setPeriodRockNum(0);
        data.setTotalRockNum(0);
        data.setPayCode(res.getJourCode());
        data.setSystemCode(hzb.getSystemCode());
        hzbHoldBO.saveHzbHold(data);
        return res;
    }

    @Override
    public void putOnOffHzb(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            // 查询是否已经购买摇钱树
            HzbHold condition = new HzbHold();
            condition.setUserId(userId);
            List<HzbHold> list = hzbHoldBO.queryHzbHoldList(condition);
            if (CollectionUtils.isEmpty(list)) {
                throw new BizException("xn0000", "该用户未购买过汇赚宝");
            }
            HzbHold hzbHold = list.get(0);
            if (EHzbHoldStatus.ACTIVATED.getCode().equals(hzbHold.getStatus())) {
                hzbHoldBO.refreshStatus(hzbHold.getId(),
                    EHzbHoldStatus.OFFLINE.getCode());
            } else if (EHzbHoldStatus.OFFLINE.getCode().equals(
                hzbHold.getStatus())) {
                hzbHoldBO.refreshStatus(hzbHold.getId(),
                    EHzbHoldStatus.ACTIVATED.getCode());
            }
        }
    }

    @Override
    public HzbHold myHzb(String userId) {
        HzbHold hzbHold = null;
        // 查询是否已经购买摇钱树
        HzbHold condition = new HzbHold();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        List<HzbHold> list = hzbHoldBO.queryHzbHoldList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            hzbHold = list.get(0);
        }
        return hzbHold;
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
    public void paySuccess(String payCode) {
        HzbHold condition = new HzbHold();
        condition.setPayCode(payCode);
        List<HzbHold> result = hzbHoldBO.queryHzbHoldList(condition);
        if (CollectionUtils.isEmpty(result)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        HzbHold hzbHold = result.get(0);
        // 更新状态
        hzbHoldBO.refreshStatus(hzbHold.getId(),
            EHzbHoldStatus.ACTIVATED.getCode());
        // 分配分成
        distributeAmount(hzbHold);
        // 产生红包
        hzbMgiftBO.sendHzbMgift(hzbHold.getUserId());
    }

    // 汇赚宝分成:
    // 1、数据准备
    // 2、计算分成
    private void distributeAmount(HzbHold hzbHold) {
        String systemCode = hzbHold.getSystemCode();
        String userId = hzbHold.getUserId();
        Long price = hzbHold.getPrice();
        XN805901Res dUser = userBO.getRemoteUser(userId, userId);
        // 用户分成
        String cUserId = dUser.getUserReferee();
        if (StringUtils.isNotBlank(cUserId)) {
            XN805901Res cUser = userBO.getRemoteUser(cUserId, cUserId);
            boolean cHzbResult = hzbHoldBO.isHzbHoldExistByUser(cUserId);
            if (cHzbResult) {
                this.userFcAmount(systemCode, cUser, SysConstants.HZB_CUSER,
                    price);
            }
            // B用户分成
            String bUserId = cUser.getUserReferee();
            if (StringUtils.isNotBlank(bUserId)) {
                XN805901Res bUser = userBO.getRemoteUser(bUserId, bUserId);
                boolean bHzbResult = hzbHoldBO.isHzbHoldExistByUser(bUserId);
                if (bHzbResult) {
                    this.userFcAmount(systemCode, bUser,
                        SysConstants.HZB_BUSER, price);
                }
                // A用户分成
                String aUserId = bUser.getUserReferee();
                if (StringUtils.isNotBlank(aUserId)) {
                    XN805901Res aUser = userBO.getRemoteUser(aUserId, aUserId);
                    boolean aHzbResult = hzbHoldBO
                        .isHzbHoldExistByUser(aUserId);
                    if (aHzbResult) {
                        this.userFcAmount(systemCode, aUser,
                            SysConstants.HZB_AUSER, price);
                    }
                }
            }
        }
        // 辖区分成
        UserExt userExt = dUser.getUserExt();
        if (userExt != null) {
            if (StringUtils.isNotBlank(userExt.getProvince())) {
                // 省合伙人
                XN805060Res provinceRes = userBO.getPartnerUserInfo(
                    userExt.getProvince(), null, null);
                if (provinceRes != null) {
                    areaFcAmount(systemCode, provinceRes.getUserId(),
                        SysConstants.HZB_PROVINCE, price, "省");
                }
                if (StringUtils.isNotBlank(userExt.getCity())) {
                    // 市合伙人
                    XN805060Res cityRes = userBO.getPartnerUserInfo(
                        userExt.getProvince(), userExt.getCity(), null);
                    if (cityRes != null) {
                        areaFcAmount(systemCode, cityRes.getUserId(),
                            SysConstants.HZB_CITY, price, "市");
                    }
                    if (StringUtils.isNotBlank(userExt.getArea())) {
                        // 县合伙人
                        XN805060Res areaRes = userBO.getPartnerUserInfo(
                            userExt.getProvince(), userExt.getCity(),
                            userExt.getArea());
                        if (areaRes != null) {
                            areaFcAmount(systemCode, areaRes.getUserId(),
                                SysConstants.HZB_AREA, price, "县");
                        }
                    }
                }
            }
        }
    }

    private void userFcAmount(String systemCode, XN805901Res user,
            String sysConstants, Long price) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        Double hzbUserRate = Double.valueOf(rateMap.get(sysConstants));
        Long transAmount = Double.valueOf(hzbUserRate * price).longValue();
        if (transAmount != null && transAmount != 0) {
            String bizNote = EBizType.AJ_GMHZBFC.getValue() + ",用户["
                    + user.getMobile() + "]分润分成";
            accountBO.doTransferFcBySystem(systemCode, user.getUserId(),
                ECurrency.FRB.getCode(), transAmount,
                EBizType.AJ_GMHZBFC.getCode(), bizNote);
        }
    }

    private void areaFcAmount(String systemCode, String userId,
            String sysConstants, Long price, String remark) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        XN805901Res user = userBO.getRemoteUser(userId, userId);
        Double rate = Double.valueOf(rateMap.get(sysConstants));
        Long transAmount = Double.valueOf(price * rate).longValue();
        if (transAmount != null && transAmount != 0) {
            String bizNote = EBizType.AJ_GMHZBFC.getValue() + ",合伙人" + remark
                    + "用户[" + user.getMobile() + "]分润分成";
            accountBO.doTransferFcBySystem(systemCode, userId,
                ECurrency.FRB.getCode(), transAmount,
                EBizType.AJ_GMHZBFC.getCode(), bizNote);
        }
    }

    @Override
    public Paginable<Hzb> queryHzbPage(int start, int limit, Hzb condition) {
        return hzbBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Hzb> queryHzbList(Hzb condition) {
        return hzbBO.queryHzbList(condition);
    }

    @Override
    public Hzb getHzb(String code) {
        return hzbBO.getHzb(code);
    }
}
