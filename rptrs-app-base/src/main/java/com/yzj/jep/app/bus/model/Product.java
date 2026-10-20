/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月08日 18:29:09
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;


@Entity
@Table(name = "bus_product")
public class Product extends BasePO {

    public static final String TABLE_ALIAS = "Product";
    public static final String ALIAS_PID = "ID";
    public static final String ALIAS_PRO_ID = "产品编号";
    public static final String ALIAS_PRO_NAME = "产品名称";
    public static final String ALIAS_PA_PRO_ID = "上级产品编号";
    public static final String ALIAS_PA_PRO_NAME = "上级产品名称";
    public static final String ALIAS_MIN_AMOUNT = "最低交易价格";
    public static final String ALIAS_MAX_AMOUNT = "最高交易价格";
    public static final String ALIAS_PRO_PATH = "产品路径";
    public static final String ALIAS_BUS_LEVEL = "业务级别";
    public static final String ALIAS_TRAN_ORG = "交易机构";
    public static final String ALIAS_BUS_DEPART = "业务部门";
    public static final String ALIAS_TRADE_TYPE = "交易类型";
    public static final String ALIAS_BUSINESS_TYPE = "业务类型";
    public static final String ALIAS_IS_PRO = "是否产品:0-产品 1-目录";
    public static final String ALIAS_STATUS = "状态:0-启用 1-未启用";
    public static final String ALIAS_DESCRIPTION = "产品描述";
    public static final String ALIAS_IS_LIMIT = "是否已配置额度:0-未配置 1-已配置";
    public static final String ALIAS_PRE_LIMIT_CURRENCY = "预计额度币种";
    public static final String ALIAS_PRE_LIMIT = "预计额度";
    public static final String ALIAS_PAS_LIMIT = "已用额度";
    public static final String ALIAS_REM_LIMIT = "剩余额度";
    public static final String ALIAS_LIMIT_RATE = "额度使用率";
    public static final String ALIAS_WARN_LEVE = "预警级别";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_EXPIRY_TIME = "失效时间";
    public static final String ALIAS_IS_ACTIVE = "是否生效:0-未生效 1-生效";
    public static final String ALIAS_APPROVE_STATUS = "审批状态";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "pro_id", nullable = false, length = 100)
    private String proId;

    @Column(name = "pro_name", nullable = true, length = 100)
    private String proName;

    @Column(name = "pa_pro_id", nullable = true, length = 10)
    private Integer paProId;

    @Column(name = "pa_pro_name", nullable = true, length = 64)
    private String paProName;

    @Column(name = "min_amount", nullable = true, length = 32)
    private String minAmount;

    @Column(name = "max_amount", nullable = true, length = 32)
    private String maxAmount;

    @Column(name = "pro_path", nullable = true, length = 100)
    private String proPath;

    @Column(name = "bus_level", nullable = true, length = 10)
    private String busLevel;

    @Column(name = "tran_org", nullable = true, length = 10)
    private String tranOrg;

    @Column(name = "bus_depart", nullable = true, length = 10)
    private String busDepart;

    @Column(name = "tradeType", nullable = true, length = 20)
    private String tradeType;

    @Column(name = "businessType", nullable = false, length = 20)
    private String businessType;

    @Column(name = "is_pro", nullable = false, length = 1)
    private String isPro;

    @Column(name = "status", nullable = true, length = 64)
    private String status;

    @Column(name = "description", nullable = true, length = 100)
    private String description;

    @Column(name = "is_limit", nullable = true, length = 1)
    private String isLimit;

    @Column(name = "pre_limit_currency", nullable = true, length = 10)
    private String preLimitCurrency;

    @Column(name = "pre_limit", nullable = true, length = 60)
    private String preLimit;

    @Column(name = "pas_limit", nullable = true, length = 60)
    private String pasLimit;

    @Column(name = "rem_limit", nullable = true, length = 60)
    private String remLimit;

    @Column(name = "limit_rate", nullable = true, length = 10)
    private Integer limitRate;

    @Column(name = "warn_leve", nullable = true, length = 10)
    private String warnLeve;

    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;

    @Column(name = "expiry_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date expiryTime;

    @Column(name = "is_active", nullable = true, length = 10)
    private Integer isActive;

    @Column(name = "approve_status", nullable = true, length = 10)
    private String approveStatus;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getProId() {
        return this.proId;
    }

    public void setProId(String value) {
        this.proId = value;
    }


    public String getProName() {
        return this.proName;
    }

    public void setProName(String value) {
        this.proName = value;
    }


    public Integer getPaProId() {
        return this.paProId;
    }

    public void setPaProId(Integer value) {
        this.paProId = value;
    }


    public String getPaProName() {
        return this.paProName;
    }

    public void setPaProName(String value) {
        this.paProName = value;
    }


    public String getMinAmount() {
        return this.minAmount;
    }

    public void setMinAmount(String value) {
        this.minAmount = value;
    }


    public String getMaxAmount() {
        return this.maxAmount;
    }

    public void setMaxAmount(String value) {
        this.maxAmount = value;
    }


    public String getProPath() {
        return this.proPath;
    }

    public void setProPath(String value) {
        this.proPath = value;
    }


    public String getBusLevel() {
        return this.busLevel;
    }

    public void setBusLevel(String value) {
        this.busLevel = value;
    }


    public String getTranOrg() {
        return this.tranOrg;
    }

    public void setTranOrg(String value) {
        this.tranOrg = value;
    }


    public String getBusDepart() {
        return this.busDepart;
    }

    public void setBusDepart(String value) {
        this.busDepart = value;
    }


    public String getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(String value) {
        this.tradeType = value;
    }


    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String value) {
        this.businessType = value;
    }


    public String getIsPro() {
        return this.isPro;
    }

    public void setIsPro(String value) {
        this.isPro = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }


    public String getIsLimit() {
        return this.isLimit;
    }

    public void setIsLimit(String value) {
        this.isLimit = value;
    }


    public String getPreLimitCurrency() {
        return this.preLimitCurrency;
    }

    public void setPreLimitCurrency(String value) {
        this.preLimitCurrency = value;
    }


    public String getPreLimit() {
        return this.preLimit;
    }

    public void setPreLimit(String value) {
        this.preLimit = value;
    }


    public String getPasLimit() {
        return this.pasLimit;
    }

    public void setPasLimit(String value) {
        this.pasLimit = value;
    }


    public String getRemLimit() {
        return this.remLimit;
    }

    public void setRemLimit(String value) {
        this.remLimit = value;
    }


    public Integer getLimitRate() {
        return this.limitRate;
    }

    public void setLimitRate(Integer value) {
        this.limitRate = value;
    }


    public String getWarnLeve() {
        return this.warnLeve;
    }

    public void setWarnLeve(String value) {
        this.warnLeve = value;
    }


    public Date getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(Date value) {
        this.activeTime = value;
    }


    public Date getExpiryTime() {
        return this.expiryTime;
    }

    public void setExpiryTime(Date value) {
        this.expiryTime = value;
    }


    public Integer getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Integer value) {
        this.isActive = value;
    }


    public String getApproveStatus() {
        return this.approveStatus;
    }

    public void setApproveStatus(String value) {
        this.approveStatus = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("product[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("proId = ").append(proId).append(", \n")
                .append("proName = ").append(proName).append(", \n")
                .append("paProId = ").append(paProId).append(", \n")
                .append("paProName = ").append(paProName).append(", \n")
                .append("minAmount = ").append(minAmount).append(", \n")
                .append("maxAmount = ").append(maxAmount).append(", \n")
                .append("proPath = ").append(proPath).append(", \n")
                .append("busLevel = ").append(busLevel).append(", \n")
                .append("tranOrg = ").append(tranOrg).append(", \n")
                .append("busDepart = ").append(busDepart).append(", \n")
                .append("tradeType = ").append(tradeType).append(", \n")
                .append("businessType = ").append(businessType).append(", \n")
                .append("isPro = ").append(isPro).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("description = ").append(description).append(", \n")
                .append("isLimit = ").append(isLimit).append(", \n")
                .append("preLimitCurrency = ").append(preLimitCurrency).append(", \n")
                .append("preLimit = ").append(preLimit).append(", \n")
                .append("pasLimit = ").append(pasLimit).append(", \n")
                .append("remLimit = ").append(remLimit).append(", \n")
                .append("limitRate = ").append(limitRate).append(", \n")
                .append("warnLeve = ").append(warnLeve).append(", \n")
                .append("activeTime = ").append(activeTime).append(", \n")
                .append("expiryTime = ").append(expiryTime).append(", \n")
                .append("isActive = ").append(isActive).append(", \n")
                .append("approveStatus = ").append(approveStatus).append(", \n")
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Product other = (Product) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}
