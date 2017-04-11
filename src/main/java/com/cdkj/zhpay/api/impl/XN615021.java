package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.ao.IJewelRecordAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615021Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.enums.EPayType;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 参与夺宝(正汇余额支付)
 * @author: xieyj 
 * @since: 2017年1月11日 下午6:21:36 
 * @history:
 */
public class XN615021 extends AProcessor {
    private IJewelAO jewelAO = SpringContextHolder.getBean(IJewelAO.class);

    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN615021Req req = null;

    @Override
    public synchronized Object doBusiness() throws BizException {
        Integer times = StringValidater.toInteger(req.getTimes());
        // 开始业务处理
        String payType = req.getPayType();
        if (EPayType.YEFR.getCode().equals(payType)) {
            boolean isManBiao = jewelRecordAO.buyJewelByZHYE(req.getUserId(),
                req.getJewelCode(), times, req.getIp());
            if (isManBiao) {
                String jewelTemplateCode = jewelAO
                    .doManBiao(req.getJewelCode());
                jewelAO.publishNextPeriods(jewelTemplateCode);
            }
            return new BooleanRes(true);
        } else if (EPayType.WEIXIN_APP.getCode().equals(payType)) {
            return jewelRecordAO.buyJewelByWxApp(req.getUserId(),
                req.getJewelCode(), times, req.getIp());
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            return jewelRecordAO.buyJewelByZFB(req.getUserId(),
                req.getJewelCode(), times, req.getIp());
        } else {
            throw new BizException("xn0000", "支付类型不支持");
        }
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN615021Req.class);
        StringValidater.validateNumber(req.getTimes());
        StringValidater.validateBlank(req.getUserId(), req.getJewelCode(),
            req.getPayType(), req.getIp());
    }
}
