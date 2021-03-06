package com.cdkj.zhpay.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.IHzbTemplateBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.dao.IHzbDAO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.XN615120Res;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EDiviFlag;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EHzbStatus;
import com.cdkj.zhpay.enums.ESystemCode;
import com.cdkj.zhpay.exception.BizException;

@Component
public class HzbBOImpl extends PaginableBOImpl<Hzb> implements IHzbBO {

    @Autowired
    private IHzbDAO hzbDAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IHzbTemplateBO hzbTemplateBO;

    @Override
    public boolean isBuyHzb(String userId) {
        boolean result = false;
        if (StringUtils.isNotBlank(userId)) {
            Hzb condition = new Hzb();
            condition.setUserId(userId);
            condition.setStatus(EDiviFlag.EFFECT.getCode());
            if (hzbDAO.selectTotalCount(condition) > 0) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String buyHzb(String userId, HzbTemplate hzbTemplate, String payGroup) {
        String code = null;
        if (StringUtils.isNotBlank(userId)) {
            code = OrderNoGenerater.generateM(EGeneratePrefix.HZB.getCode());
            Hzb data = new Hzb();
            data.setCode(code);
            data.setUserId(userId);
            data.setTemplateCode(hzbTemplate.getCode());
            data.setPrice(hzbTemplate.getPrice());

            data.setCurrency(hzbTemplate.getCurrency());
            data.setPeriodRockNum(0);
            data.setTotalRockNum(0);
            data.setBackAmount1(0L);
            data.setBackAmount2(0L);
            data.setBackAmount3(0L);

            Date date = new Date();
            data.setCreateDatetime(date);
            data.setStatus(EHzbStatus.TO_PAY.getCode());
            data.setPayGroup(payGroup);
            data.setCompanyCode(hzbTemplate.getCompanyCode());
            data.setSystemCode(hzbTemplate.getSystemCode());
            hzbDAO.insert(data);
        }
        return code;
    }

    @Override
    public Hzb saveHzb(User user, HzbTemplate hzbTemplate) {
        Hzb data = null;
        String userId = user.getUserId();
        if (StringUtils.isNotBlank(userId)) {
            data = new Hzb();
            String code = OrderNoGenerater.generateM(EGeneratePrefix.HZB
                .getCode());
            data.setCode(code);
            data.setUserId(userId);
            data.setTemplateCode(hzbTemplate.getCode());
            data.setPrice(hzbTemplate.getPrice());
            data.setCurrency(hzbTemplate.getCurrency());

            data.setPeriodRockNum(0);
            data.setTotalRockNum(0);
            data.setBackAmount1(0L);
            data.setBackAmount2(0L);
            data.setBackAmount3(0L);

            Date date = new Date();
            data.setCreateDatetime(date);
            data.setStatus(EHzbStatus.ACTIVATED.getCode());
            data.setPayDatetime(date);
            data.setPayAmount1(hzbTemplate.getPrice());
            data.setPayAmount2(0L);
            data.setPayAmount3(0L);
            data.setCompanyCode(hzbTemplate.getCompanyCode());
            data.setSystemCode(hzbTemplate.getSystemCode());
            hzbDAO.insert(data);
        }
        return data;
    }

    @Override
    public int refreshPayStatus(String code, String status, String payCode,
            Long payAmount) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Hzb data = new Hzb();
            data.setCode(code);
            data.setStatus(status);
            data.setPayCode(payCode);
            data.setPayDatetime(new Date());
            data.setPayAmount1(payAmount);// 人民币
            data.setPayAmount2(0L);
            data.setPayAmount3(0L);
            count = hzbDAO.updatePayStatus(data);
        }
        return count;
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#refreshPutStatus(java.lang.String, java.lang.String)
     */
    @Override
    public int refreshPutStatus(String code, String status) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Hzb data = new Hzb();
            data.setCode(code);
            data.setStatus(status);
            count = hzbDAO.updatePutStatus(data);
        }
        return count;
    }

    @Override
    public Hzb getHzb(String code) {
        Hzb data = null;
        if (StringUtils.isNotBlank(code)) {
            Hzb condition = new Hzb();
            condition.setCode(code);
            data = hzbDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "汇赚宝不存在");
            }
        }
        return data;
    }

    @Override
    public int refreshRockNum(String code, Integer periodRockNum,
            Integer totalRockNum) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Hzb data = new Hzb();
            data.setCode(code);
            data.setPeriodRockNum(periodRockNum);
            data.setTotalRockNum(totalRockNum);
            count = hzbDAO.updateYy(data);
        }
        return count;
    }

    @Override
    public void resetPeriodRockNum() {
        hzbDAO.updatePeriodRockNumZero();
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#checkBuy(java.lang.String)
     */
    @Override
    public void checkBuy(String userId) {
        Hzb condition = new Hzb();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        if (hzbDAO.selectTotalCount(condition) > 0) {
            throw new BizException("xn0000", "您已经购买过汇赚宝");
        }
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#queryHzbList(java.lang.String)
     */
    @Override
    public List<Hzb> queryHzbList(String payGroup) {
        Hzb condition = new Hzb();
        condition.setStatus(EHzbStatus.TO_PAY.getCode());
        condition.setPayGroup(payGroup);
        return hzbDAO.selectList(condition);
    }

    @Override
    public List<Hzb> queryHzbList(Hzb condition) {
        return hzbDAO.selectList(condition);
    }

    @Override
    public List<Hzb> queryDistanceHzbList(String userLatitude,
            String userLongitude, String companyCode, String systemCode) {
        // 查询激活，距离内的摇钱树
        Map<String, String> sysConfigMap = sysConfigBO
            .getConfigsMap(systemCode);
        Hzb condition = new Hzb();
        condition.setUserLatitude(userLatitude);
        condition.setUserLongitude(userLongitude);
        condition.setStatus(EHzbStatus.ACTIVATED.getCode());
        condition.setDistance(sysConfigMap.get(SysConstants.HZB_DISTANCE));
        condition.setCompanyCode(companyCode);
        condition.setSystemCode(systemCode);
        List<Hzb> distanceList = hzbDAO.selectDistanceList(condition);
        // 截取数量
        String hzbMaxNumStr = sysConfigMap.get(SysConstants.HZB_MAX_NUM);
        Integer hzbMaxNum = Integer.valueOf(hzbMaxNumStr);
        if (CollectionUtils.isNotEmpty(distanceList)
                && distanceList.size() > hzbMaxNum) {
            distanceList = distanceList.subList(0, hzbMaxNum);
        }
        List<Hzb> resultList = new ArrayList<Hzb>();
        // 根据模板,判断今天被摇摇次数是否超限制
        for (Hzb hzb : distanceList) {
            HzbTemplate hzbTemplate = hzbTemplateBO.getHzbTemplate(hzb
                .getTemplateCode());
            if (hzbTemplate.getPeriodRockNum() > hzb.getPeriodRockNum()) {
                resultList.add(hzb);
            }
        }
        // 设置分享链接
        for (Hzb hzb : resultList) {
            hzb.setShareUrl(PropertiesUtil.Config.SHARE_URL);
        }
        return resultList;

    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#queryHzbListByUser(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<Hzb> queryHzbListByUser(String userId) {
        Hzb condition = new Hzb();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        return hzbDAO.selectList(condition);
    }

    @Override
    public Hzb checkActivated(String hzbCode) {
        Hzb hzb = this.getHzb(hzbCode);
        if (EHzbStatus.ACTIVATED.getCode().equalsIgnoreCase(hzb.getStatus())) {
            return hzb;
        } else {
            throw new BizException("xn0000", "该摇钱树不处于激活状态");
        }
    }

    @Override
    public void refreshYyAmount(Hzb hzb, XN615120Res prize) {
        hzb.setPeriodRockNum(hzb.getPeriodRockNum() + 1);
        hzb.setTotalRockNum(hzb.getTotalRockNum() + 1);
        Long yyTotalAmount = prize.getYyAmount() + prize.getYyFcAmount();
        if (ESystemCode.Caigo.getCode().equals(hzb.getSystemCode())) {
            if (ECurrency.CNY.getCode().equals(prize.getYyCurrency())) {
                hzb.setBackAmount1(hzb.getBackAmount1() + yyTotalAmount);
            } else if (ECurrency.CG_CGB.getCode().equals(prize.getYyCurrency())) {
                hzb.setBackAmount2(hzb.getBackAmount2() + yyTotalAmount);
            } else if (ECurrency.CG_JF.getCode().equals(prize.getYyCurrency())) {
                hzb.setBackAmount3(hzb.getBackAmount3() + yyTotalAmount);
            }
        }
        // 判断树是否“耗尽”
        HzbTemplate template = hzbTemplateBO.getHzbTemplate(hzb
            .getTemplateCode());
        if (hzb.getBackAmount1().longValue() >= template.getBackAmount1()
            .longValue()
                && hzb.getBackAmount2().longValue() >= template
                    .getBackAmount2().longValue()
                && hzb.getBackAmount3().longValue() >= template
                    .getBackAmount3().longValue()) {
            hzb.setStatus(EHzbStatus.DIED.getCode());
        }
        hzbDAO.updateYyAmount(hzb);
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbBO#refreshYyTimes(com.cdkj.zhpay.domain.Hzb, com.cdkj.zhpay.dto.res.XN000001Res)
     */
    @Override
    public void refreshYyTimes(Hzb hzb, XN615120Res prize) {
        hzb.setPeriodRockNum(hzb.getPeriodRockNum() + 1);
        hzb.setTotalRockNum(hzb.getTotalRockNum() + 1);
        // 判断树是否“耗尽”
        HzbTemplate template = hzbTemplateBO.getHzbTemplate(hzb
            .getTemplateCode());
        if (hzb.getTotalRockNum() >= template.getTotalRockNum()) {
            hzb.setStatus(EHzbStatus.DIED.getCode());
        }
        hzbDAO.updateYyTimes(hzb);
    }
}
