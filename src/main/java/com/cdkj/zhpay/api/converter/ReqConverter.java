/**
 * @Title DealerConverter.java 
 * @Package com.cdkj.pipe.api.converter 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年3月12日 下午5:16:41 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.converter;

import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.dto.req.XN615000Req;
import com.cdkj.zhpay.dto.req.XN615002Req;

/** 
 * @author: haiqingzheng 
 * @since: 2017年3月12日 下午5:16:41 
 * @history:
 */
public class ReqConverter {

    public static JewelTemplate converter(XN615000Req req) {
        JewelTemplate data = new JewelTemplate();
        data.setToAmount(StringValidater.toLong(req.getToAmount()));
        data.setToCurrency(req.getToCurrency());
        data.setTotalNum(StringValidater.toInteger(req.getTotalNum()));
        data.setMaxNum(StringValidater.toInteger(req.getMaxNum()));
        data.setFromAmount(StringValidater.toLong(req.getFromAmount()));
        data.setFromCurrency(req.getFromCurrency());
        data.setSlogan(req.getSlogan());
        data.setAdvPic(req.getAdvPic());
        data.setUpdater(req.getUpdater());
        data.setRemark(req.getRemark());
        data.setCompanyCode(req.getCompanyCode());
        data.setSystemCode(req.getSystemCode());
        return data;
    }

    public static JewelTemplate converter(XN615002Req req) {
        JewelTemplate data = new JewelTemplate();
        data.setCode(req.getCode());
        data.setToAmount(StringValidater.toLong(req.getToAmount()));
        data.setToCurrency(req.getToCurrency());
        data.setTotalNum(StringValidater.toInteger(req.getTotalNum()));
        data.setMaxNum(StringValidater.toInteger(req.getMaxNum()));
        data.setFromAmount(StringValidater.toLong(req.getFromAmount()));
        data.setFromCurrency(req.getFromCurrency());
        data.setSlogan(req.getSlogan());
        data.setAdvPic(req.getAdvPic());
        data.setUpdater(req.getUpdater());
        data.setRemark(req.getRemark());
        return data;
    }
}
