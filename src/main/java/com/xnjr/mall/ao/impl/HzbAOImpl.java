package com.xnjr.mall.ao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IHzbAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.IHzbBO;
import com.xnjr.mall.bo.IHzbHoldBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.domain.Hzb;
import com.xnjr.mall.domain.HzbHold;
import com.xnjr.mall.domain.UserExt;
import com.xnjr.mall.dto.res.PayBalanceRes;
import com.xnjr.mall.dto.res.XN802180Res;
import com.xnjr.mall.dto.res.XN805060Res;
import com.xnjr.mall.dto.res.XN805901Res;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.EBoolean;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EDiviFlag;
import com.xnjr.mall.enums.EHzbHoldStatus;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.exception.BizException;

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
        String systemCode = hzb.getSystemCode();
        Long price = hzb.getPrice();
        if (EPayType.YEZP.getCode().equals(payType)) {
            // 余额支付
            PayBalanceRes payRes = accountBO.doBalancePay(systemCode, userId,
                ESysUser.SYS_USER.getCode(), price, EBizType.AJ_GMHZB);
            HzbHold hzbHold = new HzbHold();
            hzbHold.setUserId(userId);
            hzbHold.setHzbCode(hzbCode);
            hzbHold.setStatus(EHzbHoldStatus.ACTIVATED.getCode());
            hzbHold.setPrice(hzb.getPrice());
            hzbHold.setCurrency(hzb.getCurrency());
            hzbHold.setPeriodRockNum(0);
            hzbHold.setTotalRockNum(0);
            hzbHold.setPayAmount1(0L);
            hzbHold.setPayAmount2(payRes.getGxjlAmount());
            hzbHold.setPayAmount3(payRes.getFrAmount());
            hzbHold.setSystemCode(hzb.getSystemCode());
            result = hzbHoldBO.saveHzbHold(hzbHold);
            // 分销规则
            distributeAmount(hzbHold);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            // 获取微信APP支付信息
            String bizNote = hzb.getName() + "——汇赚宝购买";
            String body = "正汇钱包—汇赚宝";
            XN802180Res res = accountBO.doWeiXinPay(systemCode, userId,
                EBizType.AJ_GMHZB, bizNote, body, price, ip);
            // 落地本地系统消费记录，状态为未支付
            HzbHold data = new HzbHold();
            data.setUserId(userId);
            data.setHzbCode(hzbCode);
            data.setStatus(EHzbHoldStatus.TO_PAY.getCode());
            data.setPrice(hzb.getPrice());
            data.setCurrency(hzb.getCurrency());
            data.setPeriodRockNum(0);
            data.setTotalRockNum(0);
            data.setPayCode(res.getJourCode());
            data.setSystemCode(hzb.getSystemCode());
            hzbHoldBO.saveHzbHold(data);
            result = res;
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            return null;
        }
        return result;
    }

    // private void transAmount(String systemCode, String userId, Long price,
    // EBizType bizType) {
    // Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
    // null);
    // // 余额支付业务规则：优先扣贡献奖励，其次扣分润
    // Long gxjlCnyAmount = 0L;
    // Long frCnyAmount = 0L;
    // // 查询用户贡献奖励账户
    // XN802503Res gxjlAccount = accountBO.getAccountByUserId(systemCode,
    // userId, ECurrency.GXJL.getCode());
    // // 查询用户分润账户
    // XN802503Res frAccount = accountBO.getAccountByUserId(systemCode,
    // userId, ECurrency.FRB.getCode());
    // Double gxjl2cny = Double.valueOf(rateMap.get(SysConstants.GXJL2CNY));
    // Double fr2cny = Double.valueOf(rateMap.get(SysConstants.FR2CNY));
    // gxjlCnyAmount = Double.valueOf(gxjlAccount.getAmount() / gxjl2cny)
    // .longValue();
    // frCnyAmount = Double.valueOf(frAccount.getAmount() / fr2cny)
    // .longValue();
    // // 1、贡献奖励+分润<价格 余额不足
    // if (gxjlCnyAmount + frCnyAmount < price) {
    // throw new BizException("xn0000", "余额不足");
    // }
    // // 2、贡献奖励=0 直接扣分润
    // if (gxjlAccount.getAmount() <= 0L) {
    // Long frPrice = Double.valueOf(price * fr2cny).longValue();
    // // 扣除分润
    // accountBO.doTransferAmount(systemCode,
    // frAccount.getAccountNumber(), ESysAccount.FRB.getCode(),
    // frPrice, bizType.getCode(), bizType.getValue());
    // }
    // // 3、0<贡献奖励<price 先扣贡献奖励，再扣分润
    // if (gxjlCnyAmount > 0L && gxjlCnyAmount < price) {
    // // 扣除贡献奖励
    // accountBO.doTransferAmount(systemCode,
    // gxjlAccount.getAccountNumber(), ESysAccount.GXJL.getCode(),
    // gxjlAccount.getAmount(), bizType.getCode(), bizType.getValue());
    // // 再扣除分润
    // Long frPrice = Double.valueOf((price - gxjlCnyAmount) * fr2cny)
    // .longValue();
    // accountBO.doTransferAmount(systemCode,
    // frAccount.getAccountNumber(), ESysAccount.FRB.getCode(),
    // frPrice, bizType.getCode(), bizType.getValue());
    // }
    // // 4、贡献奖励>=price 直接扣贡献奖励
    // if (gxjlCnyAmount >= price) {
    // Long gxjlPrice = Double.valueOf(price * gxjl2cny).longValue();
    // // 扣除贡献奖励
    // accountBO.doTransferAmount(systemCode,
    // gxjlAccount.getAccountNumber(), ESysAccount.GXJL.getCode(),
    // gxjlPrice, bizType.getCode(), bizType.getValue());
    // }
    // }

    @Override
    public void activateHzb(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            // 查询是否已经购买摇钱树
            HzbHold condition = new HzbHold();
            condition.setUserId(userId);
            condition.setStatus(EHzbHoldStatus.NONACTIVATED.getCode());
            List<HzbHold> list = hzbHoldBO.queryHzbHoldList(condition);
            if (CollectionUtils.isEmpty(list)) {
                throw new BizException("xn0000", "您还未成功购买汇赚宝，无法激活");
            }
            HzbHold hzbHold = list.get(0);
            hzbHoldBO.refreshStatus(hzbHold.getId(),
                EHzbHoldStatus.ACTIVATED.getCode());
        }
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
            if (EHzbHoldStatus.NONACTIVATED.getCode().equals(
                hzbHold.getStatus())) {
                throw new BizException("xn0000", "该用户汇赚宝处于未激活状态，不能进行冻结/解冻操作");
            }
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

    @Override
    public void editHzb(Hzb data) {
        if (!hzbBO.isHzbExist(data.getCode())) {
            throw new BizException("xn0000", "汇赚宝记录不存在");
        }
        hzbBO.refreshHzb(data);
    }

    @Override
    public void dropHzb(String code) {
        if (!hzbBO.isHzbExist(code)) {
            throw new BizException("xn0000", "汇赚宝记录不存在");
        }
        hzbBO.removeHzb(code);
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
        // 更新状态
        hzbHoldBO.refreshStatus(result.get(0).getId(),
            EHzbHoldStatus.ACTIVATED.getCode());
        // 分成
        distributeAmount(result.get(0));
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
}
