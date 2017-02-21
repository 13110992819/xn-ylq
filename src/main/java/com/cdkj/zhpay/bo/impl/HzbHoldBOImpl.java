package com.cdkj.zhpay.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IHzbHoldBO;
import com.cdkj.zhpay.bo.base.Page;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.dao.IHzbHoldDAO;
import com.cdkj.zhpay.domain.HzbHold;
import com.cdkj.zhpay.exception.BizException;

@Component
public class HzbHoldBOImpl extends PaginableBOImpl<HzbHold> implements
        IHzbHoldBO {

    @Autowired
    private IHzbHoldDAO hzbHoldDAO;

    @Override
    public boolean isHzbHoldExist(Long id) {
        HzbHold condition = new HzbHold();
        condition.setId(id);
        if (hzbHoldDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isHzbHoldExistByUser(String userId) {
        HzbHold condition = new HzbHold();
        condition.setUserId(userId);
        if (hzbHoldDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int saveHzbHold(HzbHold data) {
        int count = 0;
        if (data != null) {
            count = hzbHoldDAO.insert(data);
        }
        return count;
    }

    @Override
    public int removeHzbHold(Long id) {
        int count = 0;
        if (id != null) {
            HzbHold data = new HzbHold();
            data.setId(id);
            count = hzbHoldDAO.delete(data);
        }
        return count;
    }

    @Override
    public List<HzbHold> queryHzbHoldList(HzbHold condition) {
        return hzbHoldDAO.selectList(condition);
    }

    @Override
    public HzbHold getHzbHold(Long id) {
        HzbHold data = null;
        if (id != null) {
            HzbHold condition = new HzbHold();
            condition.setId(id);
            data = hzbHoldDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "汇赚宝购买记录不存在");
            }
        }
        return data;
    }

    @Override
    public int refreshStatus(Long id, String status) {
        int count = 0;
        if (id != null) {
            HzbHold data = new HzbHold();
            data.setId(id);
            data.setStatus(status);
            count = hzbHoldDAO.updateStatus(data);
        }
        return count;
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbHoldBO#queryDistanceHzbHoldList(com.cdkj.zhpay.domain.HzbHold)
     */
    @Override
    public List<HzbHold> queryDistanceHzbHoldList(HzbHold condition) {
        return hzbHoldDAO.selectDistanceList(condition);
    }

    @Override
    public Paginable<HzbHold> queryDistancePaginable(int start, int pageSize,
            HzbHold condition) {
        long totalCount = hzbHoldDAO.selectDistanceTotalCount(condition);
        Paginable<HzbHold> page = new Page<HzbHold>(start, pageSize, totalCount);
        List<HzbHold> dataList = hzbHoldDAO.selectDistanceList(condition,
            page.getStart(), page.getPageSize());
        page.setList(dataList);
        return page;
    }

    @Override
    public int refreshRockNum(Long id, Integer periodRockNum,
            Integer totalRockNum) {
        int count = 0;
        if (id != null) {
            HzbHold data = new HzbHold();
            data.setId(id);
            data.setPeriodRockNum(periodRockNum);
            data.setTotalRockNum(totalRockNum);
            count = hzbHoldDAO.updateRockNum(data);
        }
        return count;
    }

    @Override
    public void resetPeriodRockNum() {
        hzbHoldDAO.resetPeriodRockNum();
    }
}
