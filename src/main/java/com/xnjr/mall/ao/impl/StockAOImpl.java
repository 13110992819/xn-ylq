package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IStockAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.IStockBO;
import com.xnjr.mall.bo.IStockBackBO;
import com.xnjr.mall.bo.IStockHoldBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.domain.Stock;
import com.xnjr.mall.domain.StockBack;
import com.xnjr.mall.domain.StockHold;
import com.xnjr.mall.domain.UserExt;
import com.xnjr.mall.dto.req.XN802180Req;
import com.xnjr.mall.dto.res.XN802180Res;
import com.xnjr.mall.dto.res.XN802503Res;
import com.xnjr.mall.dto.res.XN805060Res;
import com.xnjr.mall.dto.res.XN805901Res;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.EStockHoldStatus;
import com.xnjr.mall.enums.EStockStatus;
import com.xnjr.mall.enums.EStockType;
import com.xnjr.mall.enums.ESysAccount;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.http.BizConnecter;

@Service
public class StockAOImpl implements IStockAO {
    static Logger logger = Logger.getLogger(StockAOImpl.class);

    @Autowired
    private IStockBO stockBO;

    @Autowired
    private IStockHoldBO stockHoldBO;

    @Autowired
    private IStockBackBO stockBackBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IUserBO userBO;

    @Override
    @Transactional
    public int editStock(Stock data) {
        Stock stock = stockBO.getStock(data.getCode());
        if (!EStockStatus.IN_USE.getCode().equals(stock.getStatus())) {
            throw new BizException("xn0000", "福利月卡信息状态不允许修改操作");
        }
        // 原先记录假删除
        stockBO.refreshStockStatus(data.getCode(),
            EStockStatus.FAKE_DELETED.getCode());
        // 新增福利月卡记录
        Stock newStock = new Stock();
        newStock.setName(data.getName());
        newStock.setType(stock.getType());
        newStock.setPic(data.getPic());
        newStock.setDescription(data.getDescription());
        newStock.setCapital(data.getCapital());
        newStock.setPrice(data.getPrice());
        newStock.setCurrency(ECurrency.CNY.getCode());
        newStock.setBackInterval(data.getBackInterval());
        newStock.setBackCount(10);
        newStock.setWelfare1(data.getWelfare1());
        newStock.setWelfare2(data.getWelfare2());
        newStock.setStatus(EStockStatus.IN_USE.getCode());
        newStock.setSystemCode(stock.getSystemCode());
        return stockBO.saveStock(newStock);
    }

    @Override
    public int dropStock(String code) {
        if (!stockBO.isStockExist(code)) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return stockBO.removeStock(code);
    }

    @Override
    public Paginable<Stock> queryStockPage(int start, int limit, Stock condition) {
        return stockBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Stock> queryStockList(Stock condition) {
        return stockBO.queryStockList(condition);
    }

    @Override
    public Stock getStock(String code) {
        return stockBO.getStock(code);
    }

    @Override
    @Transactional
    public Object buyStock(String code, String userId, String payType, String ip) {
        StockHold condition = new StockHold();
        condition.setUserId(userId);
        if (stockHoldBO.getTotalCount(condition) > 0) {
            throw new BizException("xn0000", "用户已经购买过福利月卡，不允许多次购买");
        }
        Stock stock = stockBO.getStock(code);
        if (!EStockStatus.IN_USE.getCode().equals(stock.getStatus())) {
            throw new BizException("xn0000", "该福利月卡不可用");
        }
        String systemCode = stock.getSystemCode();
        if (EPayType.NBHZ.getCode().equals(payType)) {
            Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
                null);
            // 余额支付业务规则：优先扣贡献奖励，其次扣分润
            Long gxjlCnyAmount = 0L;
            Long frCnyAmount = 0L;
            // 查询用户贡献奖励账户
            XN802503Res gxjlAccount = accountBO.getAccountByUserId(systemCode,
                userId, ECurrency.GXJL.getCode());
            // 查询用户分润账户
            XN802503Res frAccount = accountBO.getAccountByUserId(systemCode,
                userId, ECurrency.FRB.getCode());
            Double gxjl2cny = Double
                .valueOf(rateMap.get(SysConstants.GXJL2CNY));
            Double fr2cny = Double.valueOf(rateMap.get(SysConstants.FR2CNY));
            gxjlCnyAmount = Double.valueOf(gxjlAccount.getAmount() / gxjl2cny)
                .longValue();
            frCnyAmount = Double.valueOf(frAccount.getAmount() / fr2cny)
                .longValue();
            Long price = stock.getPrice();
            // 1、贡献奖励+分润<价格 余额不足
            if (gxjlCnyAmount + frCnyAmount < price) {
                throw new BizException("xn0000", "余额不足");
            }
            // 2、贡献奖励=0 直接扣分润
            if (gxjlAccount.getAmount() <= 0L) {
                Long frPrice = Double.valueOf(stock.getPrice() * fr2cny)
                    .longValue();
                // 扣除分润
                accountBO.doTransferAmount(systemCode,
                    frAccount.getAccountNumber(), ESysAccount.FRB.getCode(),
                    frPrice, EBizType.AJ_GMFLYK.getCode(),
                    EBizType.AJ_GMFLYK.getValue());
            }
            // 3、0<贡献奖励<price 先扣贡献奖励，再扣分润
            if (gxjlCnyAmount > 0L && gxjlCnyAmount < price) {
                // 扣除贡献奖励
                accountBO.doTransferAmount(systemCode,
                    gxjlAccount.getAccountNumber(), ESysAccount.GXJL.getCode(),
                    gxjlAccount.getAmount(), EBizType.AJ_GMFLYK.getCode(),
                    EBizType.AJ_GMFLYK.getValue());
                // 再扣除分润
                Long frPrice = Double.valueOf((price - gxjlCnyAmount) * fr2cny)
                    .longValue();
                accountBO.doTransferAmount(systemCode,
                    frAccount.getAccountNumber(), ESysAccount.FRB.getCode(),
                    frPrice, EBizType.AJ_GMFLYK.getCode(),
                    EBizType.AJ_GMFLYK.getValue());
            }
            // 4、贡献奖励>=price 直接扣贡献奖励
            if (gxjlCnyAmount >= price) {
                Long gxjlPrice = Double.valueOf(stock.getPrice() * gxjl2cny)
                    .longValue();
                // 扣除贡献奖励
                accountBO.doTransferAmount(systemCode,
                    gxjlAccount.getAccountNumber(), ESysAccount.GXJL.getCode(),
                    gxjlPrice, EBizType.AJ_GMFLYK.getCode(),
                    EBizType.AJ_GMFLYK.getValue());
            }
            StockHold stockHold = new StockHold();
            stockHold.setUserId(userId);
            stockHold.setStockCode(code);
            stockHold.setStatus(EStockHoldStatus.UNCLEARED.getCode());
            stockHold.setBackWelfare1(0L);
            stockHold.setBackWelfare2(0L);
            stockHold.setBackNum(0);
            stockHold.setNextBack(DateUtil.getRelativeDate(
                DateUtil.getTodayStart(), 24 * 60 * 60));
            stockHold.setSystemCode(systemCode);
            distributeAmount(stockHold);
            return stockHoldBO.saveStockHold(stockHold);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            // 获取微信APP支付信息
            XN802180Req req = new XN802180Req();
            req.setSystemCode(systemCode);
            req.setCompanyCode(systemCode);
            req.setUserId(userId);
            req.setBizType(EBizType.AJ_GW.getCode());
            req.setBizNote(stock.getName() + "——福利月卡购买");
            req.setBody("正汇钱包—福利月卡");
            req.setTotalFee(String.valueOf(stock.getPrice()));
            req.setSpbillCreateIp(ip);
            XN802180Res res = BizConnecter.getBizData("802180",
                JsonUtil.Object2Json(req), XN802180Res.class);
            // 落地本地系统消费记录，状态为未支付
            StockHold data = new StockHold();
            data.setUserId(userId);
            data.setStockCode(code);
            data.setStatus(EStockHoldStatus.TO_PAY.getCode());
            data.setBackWelfare1(0L);
            data.setBackWelfare2(0L);
            data.setBackNum(0);
            data.setNextBack(DateUtil.getRelativeDate(DateUtil.getTodayStart(),
                24 * 60 * 60));
            // 橙账本流水号
            data.setPayCode(res.getJourCode());
            data.setSystemCode(systemCode);
            stockHoldBO.saveStockHold(data);
            return res;
        }
        return null;
    }

    @Override
    @Transactional
    public void returnStock(String userId) {
        StockHold condition = new StockHold();
        condition.setUserId(userId);
        condition.setStatus(EStockHoldStatus.UNCLEARED.getCode());
        List<StockHold> list = stockHoldBO.queryStockHoldList(condition);
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("xn0000", "用户没有未清算的福利月卡，不允许返还操作");
        }
        StockHold stockHold = list.get(0);
        if (DateUtil.daysBetweenDate(new Date(), stockHold.getNextBack()) > 0) {
            if (stockHold.getBackNum() == 0) {
                throw new BizException("xn0000", "购买后首次领取时间为次日开始");
            } else {
                throw new BizException("xn0000", "未到返还时间，不允许返还");
            }
        }
        // 更新返还金额
        Stock stock = stockBO.getStock(stockHold.getStockCode());
        String status = EStockHoldStatus.UNCLEARED.getCode();
        int backNum = stockHold.getBackNum() + 1;
        Long backWelfare1 = stockHold.getBackWelfare1() + stock.getWelfare1();
        Long backWelfare2 = stockHold.getBackWelfare2() + stock.getWelfare2();
        Date nextBack = DateUtil.getRelativeDate(DateUtil.getTodayStart(),
            stock.getBackInterval() * 24 * 60 * 60);
        if (backNum == stock.getBackCount()) {
            status = EStockHoldStatus.CLEARED.getCode();
            nextBack = null;
        }
        stockHold.setStatus(status);
        stockHold.setBackNum(backNum);
        stockHold.setBackWelfare1(backWelfare1);
        stockHold.setBackWelfare2(backWelfare2);
        stockHold.setNextBack(nextBack);
        stockHoldBO.refreshStockHold(stockHold);
        // 落地返还记录
        StockBack stockBack = new StockBack();
        stockBack.setUserId(userId);
        stockBack.setStockCode(stockHold.getStockCode());
        stockBack.setBackDatetime(new Date());
        stockBack.setSystemCode(stockHold.getSystemCode());
        stockBackBO.saveStockBack(stockBack);
        // 发放贡献奖励和购物币
        transStockAmount(stockHold.getSystemCode(), userId, backWelfare1,
            backWelfare2, "用户[" + userId + "]福利月卡返还，已返第" + backNum + "期");
    }

    @Override
    @Transactional
    public void clearStock(String userId) {
        StockHold condition = new StockHold();
        condition.setUserId(userId);
        condition.setStatus(EStockHoldStatus.UNCLEARED.getCode());
        List<StockHold> list = stockHoldBO.queryStockHoldList(condition);
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("xn0000", "用户没有未清算的福利月卡，不允许清算操作");
        }
        StockHold stockHold = list.get(0);
        // 更新返还金额
        Stock stock = stockBO.getStock(stockHold.getStockCode());
        String status = EStockHoldStatus.CLEARED.getCode();
        int backNum = stockHold.getBackNum() + 1;
        Long backWelfare1 = stock.getWelfare1()
                * (stock.getBackCount() - stockHold.getBackNum());
        Long backWelfare2 = stock.getWelfare2()
                * (stock.getBackCount() - stockHold.getBackNum());

        stockHold.setStatus(status);
        stockHold.setBackNum(backNum);
        stockHold.setBackWelfare1(stockHold.getBackWelfare1() + backWelfare1);
        stockHold.setBackWelfare2(stockHold.getBackWelfare2() + backWelfare2);
        stockHold.setNextBack(null);
        stockHoldBO.refreshStockHold(stockHold);

        // 落地返还记录
        StockBack stockBack = new StockBack();
        stockBack.setUserId(userId);
        stockBack.setStockCode(stockHold.getStockCode());
        stockBack.setBackDatetime(new Date());
        stockBack.setSystemCode(stockHold.getSystemCode());
        stockBackBO.saveStockBack(stockBack);
        // 发放贡献奖励和购物币
        transStockAmount(stockHold.getSystemCode(), userId, backWelfare1,
            backWelfare2, "用户[" + userId + "]福利月卡清算");
    }

    private void transStockAmount(String systemCode, String userId,
            Long BackWelfare1, Long BackWelfare2, String bizNote) {
        XN802503Res accountRes = accountBO.getAccountByUserId(systemCode,
            userId, ECurrency.GXJL.getCode());
        accountBO.doTransferAmount(systemCode, ESysAccount.GXJL.getCode(),
            accountRes.getAccountNumber(), BackWelfare1,
            EBizType.AJ_FLYKHH.getCode(), bizNote);
        accountBO.doTransferAmount(systemCode, ESysAccount.GWB.getCode(),
            accountRes.getAccountNumber(), BackWelfare2,
            EBizType.AJ_FLYKHH.getCode(), bizNote);
    }

    @Override
    public StockHold myStock(String userId) {
        StockHold condition = new StockHold();
        condition.setUserId(userId);
        List<StockHold> list = stockHoldBO.queryStockHoldList(condition);
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("xn0000", "用户没有购买过福利月卡");
        }
        StockHold stockHold = list.get(0);
        return stockHoldBO.getStockHold(stockHold.getId());
    }

    @Override
    public int paySuccess(String payCode) {
        StockHold condition = new StockHold();
        condition.setPayCode(payCode);
        List<StockHold> result = stockHoldBO.queryStockHoldList(condition);
        if (CollectionUtils.isEmpty(result)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        // 分配分成
        distributeAmount(result.get(0));
        return stockHoldBO.refreshStatus(result.get(0).getId(),
            EStockHoldStatus.UNCLEARED.getCode());
    }

    private void distributeAmount(StockHold stockHold) {
        String systemCode = stockHold.getSystemCode();
        // 分销规则
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        // 获取当前用户的推荐人，判断A用户是否有购买股份
        XN805901Res res = userBO.getRemoteUser(stockHold.getUserId(),
            stockHold.getUserId());
        UserExt userExt = res.getUserExt();
        XN805901Res refereeUser = userBO.getRemoteUser(res.getUserReferee(),
            res.getUserReferee());
        // 推荐人不存在无分销
        if (refereeUser == null) {
            logger.info("用户" + stockHold.getUserId() + "无推荐人,不分配福利月卡分成");
            return;
        }
        Long a1FrAmount = 0L;
        Long areaFrAmount = 0L;
        Long cityFrAmount = 0L;
        Long provinceFrAmount = 0L;
        // 获取当前购买福利月卡价格
        Stock stock = stockBO.getStock(stockHold.getStockCode());
        Long stockPrice = stock.getPrice();
        // 判断a是否购买
        StockHold aStockHold = stockHoldBO.getStockHoldByUser(refereeUser
            .getUserId());
        String bizNote = null;
        if (aStockHold == null) {
            Double areaRateNotA1 = Double.valueOf(rateMap
                .get(SysConstants.ST_AREA_NOTA1));
            Double cityRateNotA1 = Double.valueOf(rateMap
                .get(SysConstants.ST_CITY_NOTA1));
            Double provinceRateNotA1 = Double.valueOf(rateMap
                .get(SysConstants.ST_PROVINCE_NOTA1));
            Double a10Rate = Double.valueOf(rateMap.get(SysConstants.ST_A10));
            a1FrAmount = Double.valueOf(stockPrice * a10Rate).longValue();
            areaFrAmount = Double.valueOf(stockPrice * areaRateNotA1)
                .longValue();
            cityFrAmount = Double.valueOf(stockPrice * cityRateNotA1)
                .longValue();
            provinceFrAmount = Double.valueOf(stockPrice * provinceRateNotA1)
                .longValue();
            bizNote = "A未买福利月卡，A和各辖区分销分成";
        } else {
            Stock aStock = stockBO.getStock(aStockHold.getStockCode());
            if (EStockType.A.getCode().equals(aStock.getType())) {
                Double a12kRate = Double.valueOf(rateMap
                    .get(SysConstants.ST_A12000));
                a1FrAmount = Double.valueOf(stockPrice * a12kRate).longValue();
                bizNote = "A买第一档福利月卡，A和各辖区分销分成";
            }
            if (EStockType.B.getCode().equals(aStock.getType())) {
                Double a11wRate = Double.valueOf(rateMap
                    .get(SysConstants.ST_A110000));
                a1FrAmount = Double.valueOf(stockPrice * a11wRate).longValue();
                bizNote = "A买第二档福利月卡，A和各辖区分销分成";
            }
            if (EStockType.C.getCode().equals(aStock.getType())) {
                Double a13wRate = Double.valueOf(rateMap
                    .get(SysConstants.ST_A130000));
                a1FrAmount = Double.valueOf(stockPrice * a13wRate).longValue();
                bizNote = "A买第三档福利月卡，A和各辖区分销分成";
            }
            if (EStockType.D.getCode().equals(aStock.getType())) {
                Double a15wRate = Double.valueOf(rateMap
                    .get(SysConstants.ST_A150000));
                a1FrAmount = Double.valueOf(stockPrice * a15wRate).longValue();
                bizNote = "A买第四档福利月卡，A和各辖区分销分成";
            }
            Double areaRate = Double.valueOf(rateMap.get(SysConstants.ST_AREA));
            Double cityRate = Double.valueOf(rateMap.get(SysConstants.ST_CITY));
            Double provinceRate = Double.valueOf(rateMap
                .get(SysConstants.ST_PROVINCE));
            areaFrAmount = Double.valueOf(stockPrice * areaRate).longValue();
            cityFrAmount = Double.valueOf(stockPrice * cityRate).longValue();
            provinceFrAmount = Double.valueOf(stockPrice * provinceRate)
                .longValue();
        }
        distributeFrAmount(a1FrAmount, areaFrAmount, cityFrAmount,
            provinceFrAmount, bizNote, stock.getSystemCode(),
            refereeUser.getUserId(), userExt);
    }

    private void distributeFrAmount(Long a1Amount, Long areaAmount,
            Long cityAmount, Long provinceAmount, String bizNote,
            String systemCode, String aUserId, UserExt userExt) {
        if (userExt != null) {
            XN805060Res areaRes = null;
            XN805060Res cityRes = null;
            XN805060Res provinceRes = null;
            if (StringUtils.isNotBlank(userExt.getProvince())) {
                // 省合伙人
                provinceRes = userBO.getPartnerUserInfo(userExt.getProvince(),
                    null, null);
                if (StringUtils.isNotBlank(userExt.getCity())) {
                    // 市合伙人
                    cityRes = userBO.getPartnerUserInfo(userExt.getProvince(),
                        userExt.getCity(), null);
                    if (StringUtils.isNotBlank(userExt.getArea())) {
                        // 县合伙人
                        areaRes = userBO.getPartnerUserInfo(
                            userExt.getProvince(), userExt.getCity(),
                            userExt.getArea());
                    }
                }
            }
            // a1分成
            if (a1Amount != null && a1Amount != 0L) {
                XN802503Res accountRes = accountBO.getAccountByUserId(
                    systemCode, aUserId, ECurrency.FRB.getCode());
                accountBO.doTransferAmount(systemCode,
                    ESysAccount.FRB.getCode(), accountRes.getAccountNumber(),
                    a1Amount, EBizType.AJ_FLYKFC.getCode(), bizNote
                            + ",A用户分成分润");
            }
            if (areaRes != null && areaAmount != null && areaAmount != 0L) {
                XN802503Res areaAccount = accountBO.getAccountByUserId(
                    systemCode, areaRes.getUserId(), ECurrency.FRB.getCode());
                accountBO.doTransferAmount(systemCode,
                    ESysAccount.FRB.getCode(), areaAccount.getAccountNumber(),
                    areaAmount, EBizType.AJ_FLYKFC.getCode(), bizNote
                            + ",县分成分润");
            }
            if (cityRes != null && cityAmount != null && cityAmount != 0L) {
                XN802503Res cityAccount = accountBO.getAccountByUserId(
                    systemCode, cityRes.getUserId(), ECurrency.FRB.getCode());
                accountBO.doTransferAmount(systemCode,
                    ESysAccount.FRB.getCode(), cityAccount.getAccountNumber(),
                    cityAmount, EBizType.AJ_FLYKFC.getCode(), bizNote
                            + ",市分成分润");
            }
            if (provinceRes != null && provinceAmount != null
                    && provinceAmount != 0L) {
                XN802503Res provinceAccount = accountBO.getAccountByUserId(
                    systemCode, areaRes.getUserId(), ECurrency.FRB.getCode());
                accountBO.doTransferAmount(systemCode,
                    ESysAccount.FRB.getCode(),
                    provinceAccount.getAccountNumber(), provinceAmount,
                    EBizType.AJ_FLYKFC.getCode(), bizNote + ",省分成分润");
            }
        }
    }
}
