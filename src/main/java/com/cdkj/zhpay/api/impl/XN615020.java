package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.ao.IJewelRecordAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615020Req;
import com.cdkj.zhpay.enums.EPayType;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 参与夺宝
 * @author: xieyj 
 * @since: 2017年1月11日 下午6:21:36 
 * @history:
 */
public class XN615020 extends AProcessor {
    private IJewelAO jewelAO = SpringContextHolder.getBean(IJewelAO.class);

    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN615020Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Integer times = StringValidater.toInteger(req.getTimes());
        String payType = req.getPayType();
        // 开始业务处理
        if (EPayType.YEFR.getCode().equals(req.getPayType())) {
            boolean isManBiao = jewelRecordAO.buyJewelByYE(req.getUserId(),
                req.getJewelCode(), times, req.getIp());
            if (isManBiao) {
                String jewelTemplateCode = jewelAO
                    .doManBiao(req.getJewelCode());
                jewelAO.publishNextPeriods(jewelTemplateCode);
            }
            return true;
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            return jewelRecordAO.buyJewelByWX(req.getUserId(),
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
        req = JsonUtils.json2Bean(inputparams, XN615020Req.class);
        StringValidater.validateNumber(req.getTimes());
        StringValidater.validateBlank(req.getUserId(), req.getJewelCode(),
            req.getPayType(), req.getIp());
    }
}
