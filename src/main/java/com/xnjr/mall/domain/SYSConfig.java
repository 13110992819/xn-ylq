/**
 * @Title SYSConfig.java 
 * @Package com.xnjr.moom.domain 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月16日 下午9:45:46 
 * @version V1.0   
 */
package com.xnjr.mall.domain;

import com.xnjr.mall.dao.base.ABaseDO;

/** 
 * @author: haiqingzheng 
 * @since: 2016年4月16日 下午9:45:46 
 * @history:
 */
public class SYSConfig extends ABaseDO {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = -6136818068168453506L;

    // 编号（自增长）
    private Long id;

    // 分类(A 商品，B 分销规则，C摇一摇规则，D虚拟币规则，E定位规则)
    private String category;

    // 类型(A1,A2,B1,B2,C1,C2,D1,D2,E1,E2)
    //
    private String type;

    // 配置名
    private String cname;

    // key值
    private String ckey;

    // value值
    private String cvalue;

    // 备注
    private String remark;

    // 属于(0 地方默认，其他父节点序号)
    private Long belong;

    // 所属公司编号
    private String companyCode;

    // 所属系统
    private String systemCode;

    // ************* 模糊查询 *****************
    private String ckeyForQuery;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCkey() {
        return ckey;
    }

    public void setCkey(String ckey) {
        this.ckey = ckey;
    }

    public String getCvalue() {
        return cvalue;
    }

    public void setCvalue(String cvalue) {
        this.cvalue = cvalue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCkeyForQuery() {
        return ckeyForQuery;
    }

    public void setCkeyForQuery(String ckeyForQuery) {
        this.ckeyForQuery = ckeyForQuery;
    }

    public Long getBelong() {
        return belong;
    }

    public void setBelong(Long belong) {
        this.belong = belong;
    }
}
