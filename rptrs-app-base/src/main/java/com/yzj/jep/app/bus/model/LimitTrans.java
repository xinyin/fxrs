/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月03日 09:44:42
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;


@Entity
@Table(name = "bus_limit_trans")
public class LimitTrans extends BasePO {

    public static final String TABLE_ALIAS = "LimitTrans";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_BEF_PID = "调剂pid";
    public static final String ALIAS_TRAN_ORG = "被调剂交易机构";
    public static final String ALIAS_TRADE_TYPE = "被调剂交易类型";
    public static final String ALIAS_BUSINESS_TYPE = "被调剂业务类型";
    public static final String ALIAS_PRO_NAME = "被调剂产品名称";
    public static final String ALIAS_PRE_LIMIT_CURRENCY = "被调剂预计额度币种";
    public static final String ALIAS_PRE_LIMIT = "被调剂前预计额度";
    public static final String ALIAS_PRE_LIMIT_LATER = "被调剂后预计额度";
    public static final String ALIAS_PAS_LIMIT = "被调剂前剩余额度";
    public static final String ALIAS_PAS_LIMIT_LATER = "被调剂后剩余额度";
    public static final String ALIAS_WARN_LEVE = "被调剂前预警级别";
    public static final String ALIAS_WARN_LEVE_LATER = "被调剂后预警级别";
    public static final String ALIAS_TRAN_AMOUNT = "调剂金额";
    public static final String ALIAS_STATUS = "状态:0-启用 1-未启用";
    public static final String ALIAS_LAT_PID = "被调剂pid";
    public static final String ALIAS_TRAN_ORG_L = "调剂交易机构";
    public static final String ALIAS_TRADE_TYPE_L = "调剂交易类型";
    public static final String ALIAS_BUSINESS_TYPE_L = "调剂业务类型";
    public static final String ALIAS_PRO_NAME_L = "调剂产品名称";
    public static final String ALIAS_PRE_LIMIT_CURRENCY_L = "调剂预计额度币种";
    public static final String ALIAS_PRE_LIMIT_L = "被调剂前预计额度";
    public static final String ALIAS_PRE_LIMIT_LATER_L = "被调剂后预计额度";
    public static final String ALIAS_PAS_LIMIT_L = "被调剂前剩余额度";
    public static final String ALIAS_PAS_LIMIT_LATER_L = "被调剂后剩余额度";
    public static final String ALIAS_WARN_LEVE_L = "被调剂前预警级别";
    public static final String ALIAS_WARN_LEVE_LATER_L = "被调剂后预警级别";
    public static final String ALIAS_SEQ_TRADE = "流水号";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "bef_pid", nullable = false, length = 10)
    private Integer befPid;

    @Column(name = "tran_org", nullable = true, length = 10)
    private String tranOrg;

    @Column(name = "tradeType", nullable = true, length = 20)
    private String tradeType;

    @Column(name = "businessType", nullable = false, length = 20)
    private String businessType;

    @Column(name = "pro_name", nullable = true, length = 20)
    private String proName;

    @Column(name = "pre_limit_currency", nullable = true, length = 10)
    private String preLimitCurrency;

    @Column(name = "pre_limit", nullable = true, length = 60)
    private String preLimit;

    @Column(name = "pre_limit_later", nullable = true, length = 60)
    private String preLimitLater;

    @Column(name = "pas_limit", nullable = true, length = 60)
    private String pasLimit;

    @Column(name = "pas_limit_later", nullable = true, length = 60)
    private String pasLimitLater;

    @Column(name = "warn_leve", nullable = true, length = 20)
    private String warnLeve;

    @Column(name = "warn_leve_later", nullable = true, length = 20)
    private String warnLeveLater;

    @Column(name = "tran_amount", nullable = true, length = 60)
    private String tranAmount;

    @Column(name = "status", nullable = true, length = 64)
    private String status;

    @Column(name = "lat_pid", nullable = false, length = 10)
    private Integer latPid;

    @Column(name = "tran_orgL", nullable = true, length = 10)
    private String tranOrgL;

    @Column(name = "tradeTypeL", nullable = true, length = 20)
    private String tradeTypeL;

    @Column(name = "businessTypeL", nullable = false, length = 20)
    private String businessTypeL;

    @Column(name = "pro_nameL", nullable = true, length = 20)
    private String proNameL;

    @Column(name = "pre_limit_currencyL", nullable = true, length = 10)
    private String preLimitCurrencyL;

    @Column(name = "pre_limitL", nullable = true, length = 60)
    private String preLimitL;

    @Column(name = "pre_limit_laterL", nullable = true, length = 60)
    private String preLimitLaterL;

    @Column(name = "pas_limitL", nullable = true, length = 60)
    private String pasLimitL;

    @Column(name = "pas_limit_laterL", nullable = true, length = 60)
    private String pasLimitLaterL;

    @Column(name = "warn_leveL", nullable = true, length = 20)
    private String warnLeveL;

    @Column(name = "warn_leve_laterL", nullable = true, length = 20)
    private String warnLeveLaterL;

    @Column(name = "seq_trade", nullable = true, length = 255)
    private String seqTrade;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getBefPid() {
        return this.befPid;
    }

    public void setBefPid(Integer value) {
        this.befPid = value;
    }


    public String getTranOrg() {
        return this.tranOrg;
    }

    public void setTranOrg(String value) {
        this.tranOrg = value;
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


    public String getProName() {
        return this.proName;
    }

    public void setProName(String value) {
        this.proName = value;
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


    public String getPreLimitLater() {
        return this.preLimitLater;
    }

    public void setPreLimitLater(String value) {
        this.preLimitLater = value;
    }


    public String getPasLimit() {
        return this.pasLimit;
    }

    public void setPasLimit(String value) {
        this.pasLimit = value;
    }


    public String getPasLimitLater() {
        return this.pasLimitLater;
    }

    public void setPasLimitLater(String value) {
        this.pasLimitLater = value;
    }


    public String getWarnLeve() {
        return this.warnLeve;
    }

    public void setWarnLeve(String value) {
        this.warnLeve = value;
    }


    public String getWarnLeveLater() {
        return this.warnLeveLater;
    }

    public void setWarnLeveLater(String value) {
        this.warnLeveLater = value;
    }


    public String getTranAmount() {
        return this.tranAmount;
    }

    public void setTranAmount(String value) {
        this.tranAmount = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public Integer getLatPid() {
        return this.latPid;
    }

    public void setLatPid(Integer value) {
        this.latPid = value;
    }


    public String getTranOrgL() {
        return this.tranOrgL;
    }

    public void setTranOrgL(String value) {
        this.tranOrgL = value;
    }


    public String getTradeTypeL() {
        return this.tradeTypeL;
    }

    public void setTradeTypeL(String value) {
        this.tradeTypeL = value;
    }


    public String getBusinessTypeL() {
        return this.businessTypeL;
    }

    public void setBusinessTypeL(String value) {
        this.businessTypeL = value;
    }


    public String getProNameL() {
        return this.proNameL;
    }

    public void setProNameL(String value) {
        this.proNameL = value;
    }


    public String getPreLimitCurrencyL() {
        return this.preLimitCurrencyL;
    }

    public void setPreLimitCurrencyL(String value) {
        this.preLimitCurrencyL = value;
    }


    public String getPreLimitL() {
        return this.preLimitL;
    }

    public void setPreLimitL(String value) {
        this.preLimitL = value;
    }


    public String getPreLimitLaterL() {
        return this.preLimitLaterL;
    }

    public void setPreLimitLaterL(String value) {
        this.preLimitLaterL = value;
    }


    public String getPasLimitL() {
        return this.pasLimitL;
    }

    public void setPasLimitL(String value) {
        this.pasLimitL = value;
    }


    public String getPasLimitLaterL() {
        return this.pasLimitLaterL;
    }

    public void setPasLimitLaterL(String value) {
        this.pasLimitLaterL = value;
    }


    public String getWarnLeveL() {
        return this.warnLeveL;
    }

    public void setWarnLeveL(String value) {
        this.warnLeveL = value;
    }


    public String getWarnLeveLaterL() {
        return this.warnLeveLaterL;
    }

    public void setWarnLeveLaterL(String value) {
        this.warnLeveLaterL = value;
    }


    public String getSeqTrade() {
        return this.seqTrade;
    }

    public void setSeqTrade(String value) {
        this.seqTrade = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("limitTrans[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("befPid = ").append(befPid).append(", \n")
                .append("tranOrg = ").append(tranOrg).append(", \n")
                .append("tradeType = ").append(tradeType).append(", \n")
                .append("businessType = ").append(businessType).append(", \n")
                .append("proName = ").append(proName).append(", \n")
                .append("preLimitCurrency = ").append(preLimitCurrency).append(", \n")
                .append("preLimit = ").append(preLimit).append(", \n")
                .append("preLimitLater = ").append(preLimitLater).append(", \n")
                .append("pasLimit = ").append(pasLimit).append(", \n")
                .append("pasLimitLater = ").append(pasLimitLater).append(", \n")
                .append("warnLeve = ").append(warnLeve).append(", \n")
                .append("warnLeveLater = ").append(warnLeveLater).append(", \n")
                .append("tranAmount = ").append(tranAmount).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("latPid = ").append(latPid).append(", \n")
                .append("tranOrgL = ").append(tranOrgL).append(", \n")
                .append("tradeTypeL = ").append(tradeTypeL).append(", \n")
                .append("businessTypeL = ").append(businessTypeL).append(", \n")
                .append("proNameL = ").append(proNameL).append(", \n")
                .append("preLimitCurrencyL = ").append(preLimitCurrencyL).append(", \n")
                .append("preLimitL = ").append(preLimitL).append(", \n")
                .append("preLimitLaterL = ").append(preLimitLaterL).append(", \n")
                .append("pasLimitL = ").append(pasLimitL).append(", \n")
                .append("pasLimitLaterL = ").append(pasLimitLaterL).append(", \n")
                .append("warnLeveL = ").append(warnLeveL).append(", \n")
                .append("warnLeveLaterL = ").append(warnLeveLaterL).append(", \n")
                .append("seqTrade = ").append(seqTrade).append(", \n")
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
        if (obj instanceof LimitTrans == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LimitTrans other = (LimitTrans) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

