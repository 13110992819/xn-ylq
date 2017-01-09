package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IHzbYyBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.dao.IHzbYyDAO;
import com.xnjr.mall.domain.HzbYy;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EPrizeType;
import com.xnjr.mall.exception.BizException;

@Component
public class HzbYyBOImpl extends PaginableBOImpl<HzbYy> implements IHzbYyBO {

    @Autowired
    private IHzbYyDAO hzbYyDAO;

    @Override
    public boolean isHzbYyExist(String code) {
        HzbYy condition = new HzbYy();
        condition.setCode(code);
        if (hzbYyDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveHzbYy(HzbYy data) {
        String code = null;
        if (data != null) {
            code = OrderNoGenerater.generateM(EGeneratePrefix.SHAKE.getCode());
            data.setCode(code);
            data.setCreateDatetime(new Date());
            hzbYyDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeHzbYy(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            HzbYy data = new HzbYy();
            data.setCode(code);
            count = hzbYyDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshHzbYy(HzbYy data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            // count = hzbYyDAO.update(data);
        }
        return count;
    }

    @Override
    public List<HzbYy> queryHzbYyList(HzbYy condition) {
        return hzbYyDAO.selectList(condition);
    }

    @Override
    public HzbYy getHzbYy(String code) {
        HzbYy data = null;
        if (StringUtils.isNotBlank(code)) {
            HzbYy condition = new HzbYy();
            condition.setCode(code);
            data = hzbYyDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "该摇摇记录不存在");
            }
        }
        return data;
    }

    /**
     * 判断是否第三次且前面两次没有摇到红包币
     * @see com.xnjr.mall.bo.IHzbYyBO#isThirdYyNoHB(java.lang.String)
     */
    @Override
    public boolean isThirdYyNoHB(String userId) {
        boolean result = false;
        HzbYy condition = new HzbYy();
        condition.setUserId(userId);
        int count = hzbYyDAO.selectTotalCount(condition).intValue();
        int start = (count - 2) < 0 ? 0 : (count - 2);
        List<HzbYy> dataList = hzbYyDAO.selectList(condition, start, count);
        if (CollectionUtils.isNotEmpty(dataList)) {
            if (count % 3 == 2) {
                boolean haveHbb = false;
                for (HzbYy hzbYy : dataList) {
                    if (EPrizeType.HBB.getCode().equals(hzbYy.getType())) {
                        haveHbb = true;
                        break;
                    }
                }
                if (!haveHbb) {
                    result = true;
                }
            }
        }
        return result;
    }
}
