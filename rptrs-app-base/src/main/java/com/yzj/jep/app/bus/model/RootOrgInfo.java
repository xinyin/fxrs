/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月10日 11:18:54
 *
 */

package com.yzj.jep.app.bus.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bus_root_org_info")
public class RootOrgInfo extends BasePO {

    public static final String TABLE_ALIAS = "基础信息表";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_REL_ORG_ID = "关联机构主键";
    public static final String ALIAS_TOTAL_SHARE_AMOUNT = "总股数";
    public static final String ALIAS_NET_CAPTIAL = "资本净额";
    public static final String ALIAS_TOTAL_CAPITAL = "总资产";
    public static final String ALIAS_FROZEN_SHARE_AMOUNT = "冻结股数(股)";
    public static final String ALIAS_PLEDGED_SHARE_AMOUNT = "质押股数(股)";
    public static final String ALIAS_END_LAST_QUARTER = "上季末";
    public static final String ALIAS_END_LAST_YEAR = "上年末";

    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "rel_org_id", nullable = false, length = 10)
    private Integer relOrgId;

    @Column(name = "total_share_amount", nullable = true, length = 100)
    private String totalShareAmount;

    @Column(name = "net_captial", nullable = true, precision = 20, scale = 0)
    private BigDecimal netCaptial;

    @Column(name = "total_capital", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal totalCapital;

    @Column(name = "frozen_share_amount", nullable = true, length = 50)
    private String frozenShareAmount;

    @Column(name = "pledged_share_amount", nullable = true, length = 50)
    private String pledgedShareAmount;


    @Column(name = "end_last_quarter", nullable = true, length = 100)
    private String endLastQuarter;

    @Column(name = "end_last_year", nullable = true, length = 100)
    private String endLastYear;

    @Column(name = "net_captial_last_year", nullable = true, length = 50)
    private String netCaptialLastYear;

    @Column(name = "quarter_compre_solvency_rate", nullable = true, length = 50)
    private String quarterCompreSolvencyRate;

    @Column(name = "years", nullable = false, length = 8)
    private Integer years;

    @Column(name = "quarter", nullable = false, length = 2)
    private Integer quarter;

    @Column(name = "approval_status", nullable = true, length = 100)
    private String approvalStatus;

    /**权益类投资上限比例*/
    @Column(name = "equity_upperlimit_scale")
    private String equityUpperLimitScale;

    @Transient
    private String netCaptialStr;
    @Transient
    private String totalCapitalStr;

    public String getNetCaptialStr() {
        return netCaptialStr;
    }

    public void setNetCaptialStr(String netCaptialStr) {
        this.netCaptialStr = netCaptialStr;
    }

    public String getTotalCapitalStr() {
        return totalCapitalStr;
    }

    public void setTotalCapitalStr(String totalCapitalStr) {
        this.totalCapitalStr = totalCapitalStr;
    }

    public String getEquityUpperLimitScale() {
        return equityUpperLimitScale;
    }


    public void setEquityUpperLimitScale(String equityUpperLimitScale) {
        this.equityUpperLimitScale = equityUpperLimitScale;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public String getNetCaptialLastYear() {
        return netCaptialLastYear;
    }

    public void setNetCaptialLastYear(String netCaptialLastYear) {
        this.netCaptialLastYear = netCaptialLastYear;
    }

    public String getQuarterCompreSolvencyRate() {
        return quarterCompreSolvencyRate;
    }

    public void setQuarterCompreSolvencyRate(String quarterCompreSolvencyRate) {
        this.quarterCompreSolvencyRate = quarterCompreSolvencyRate;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getRelOrgId() {
        return this.relOrgId;
    }

    public void setRelOrgId(Integer value) {
        this.relOrgId = value;
    }


    public String getTotalShareAmount() {
        return this.totalShareAmount;
    }

    public void setTotalShareAmount(String value) {
        this.totalShareAmount = value;
    }

    public BigDecimal getNetCaptial() {
        return netCaptial;
    }

    public void setNetCaptial(BigDecimal netCaptial) {
        this.netCaptial = netCaptial;
    }

    public java.math.BigDecimal getTotalCapital() {
        return this.totalCapital;
    }

    public void setTotalCapital(java.math.BigDecimal value) {
        this.totalCapital = value;
    }


    public String getFrozenShareAmount() {
        return this.frozenShareAmount;
    }

    public void setFrozenShareAmount(String value) {
        this.frozenShareAmount = value;
    }


    public String getPledgedShareAmount() {
        return this.pledgedShareAmount;
    }

    public void setPledgedShareAmount(String value) {
        this.pledgedShareAmount = value;
    }

    public String getEndLastQuarter() {
        return endLastQuarter;
    }

    public void setEndLastQuarter(String endLastQuarter) {
        this.endLastQuarter = endLastQuarter;
    }

    public String getEndLastYear() {
        return endLastYear;
    }

    public void setEndLastYear(String endLastYear) {
        this.endLastYear = endLastYear;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public String toString() {
        return new StringBuilder("rootOrgInfo[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("relOrgId = ").append(relOrgId).append(", \n")
                .append("totalShareAmount = ").append(totalShareAmount).append(", \n")
                .append("netCaptial = ").append(netCaptial).append(", \n")
                .append("totalCapital = ").append(totalCapital).append(", \n")
                .append("frozenShareAmount = ").append(frozenShareAmount).append(", \n")
                .append("pledgedShareAmount = ").append(pledgedShareAmount).append(", \n")
                .append("endLastQuarter = ").append(endLastQuarter).append(", \n")
                .append("endLastYear = ").append(endLastYear).append(", \n")
                .append("approvalStatus = ").append(approvalStatus).append(", \n")
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
        if (obj instanceof RootOrgInfo == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RootOrgInfo other = (RootOrgInfo) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

