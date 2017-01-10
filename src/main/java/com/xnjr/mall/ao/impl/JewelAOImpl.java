package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IJewelAO;
import com.xnjr.mall.bo.IJewelBO;
import com.xnjr.mall.bo.IJewelRecordBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.enums.EBoolean;
import com.xnjr.mall.enums.EJewelStatus;
import com.xnjr.mall.exception.BizException;

/**
 * @author: shan 
 * @since: 2016年12月19日 下午5:05:29 
 * @history:
 */
@Service
public class JewelAOImpl implements IJewelAO {
    @Autowired
    IJewelBO jewelBO;

    @Autowired
    IJewelRecordBO jewelRecordBO;

    @Override
    public String applyJewel(Jewel data) {
        String code = null;
        if (data == null) {
            throw new BizException("xn0000", "编写内容不能为空");
        }
        data.setStatus(EJewelStatus.NEW.getCode());
        code = jewelBO.saveJewel(data);
        return code;
    }

    @Override
    public void approveJewel(String code, String approveResult,
            String approver, String approveNote) {
        Jewel jewel = jewelBO.getJewel(code);
        if (!EJewelStatus.NEW.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "该夺宝标的不处于新建待审核状态，不能进行审核操作");
        }
        String status = EJewelStatus.PASS.getCode();
        if (EBoolean.NO.getCode().equals(approveResult)) {
            status = EJewelStatus.UNPASS.getCode();
        }
        jewel.setStatus(status);
        jewel.setApprover(approver);
        jewel.setApproveDatetime(new Date());
        jewel.setRemark(approveNote);
        jewelBO.refreshApprove(jewel);
    }

    @Override
    public void reApplyJewel(Jewel data) {
        Jewel jewel = jewelBO.getJewel(data.getCode());
        if (EJewelStatus.UNPASS.getCode().equals(jewel.getStatus())) {
            data.setStatus(EJewelStatus.NEW.getCode());
            data.setRemark("宝贝重新提交");
            jewelBO.refreshJewel(data);
        } else {
            throw new BizException("xn0000", "宝贝不处于审核不通过状态，不用进行重提操作");
        }
    }

    @Override
    public Paginable<Jewel> queryJewelPage(int start, int limit, Jewel condition) {
        return jewelBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Jewel> queryJewelList(Jewel condition) {
        return jewelBO.queryJewelList(condition);
    }

    @Override
    public Jewel getJewel(String code) {
        if (code == null) {
            throw new BizException("xn0000", "宝贝编号不存在");
        }
        return jewelBO.getJewel(code);
    }

    @Override
    public int putOn(Jewel data) {
        int count = 0;
        if (data != null) {
            Jewel jewel = jewelBO.getJewel(data.getCode());
            if (!EJewelStatus.PASS.getCode().equals(jewel.getStatus())) {
                throw new BizException("xn0000", "宝贝不处于审核通过状态，不能进行上架操作");
            }
            count = jewelBO.refreshPutOn(data);
        }
        return count;
    }

    @Override
    @Transactional
    public int putOff(String code, String updater, String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Jewel jewel = jewelBO.getJewel(code);
            if (!EJewelStatus.PUT_ON.getCode().equals(jewel.getStatus())) {
                throw new BizException("xn0000", "宝贝不处于夺宝状态中，不能进行下架操作");
            }
            count = jewelBO.refreshPutOff(code, updater, remark);
            // 夺宝金额原路退回逻辑，所有投资记录状态更新为强制结束,待实现
        }
        return count;
    }
}
