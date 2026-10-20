/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月19日 14:20:05
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
@Table(name = "bus_gd_pf_info")
public class GdPfInfo extends BasePO {

    public static final String TABLE_ALIAS = "派发方案明细表";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_PF_PID = "方案主键";
    public static final String ALIAS_GD_PID = "股东主键";
    public static final String ALIAS_GD_TYPE = "股东类型";
    public static final String ALIAS_SHAREHOLD_CODE = "股东账号";
    public static final String ALIAS_GD_DJ = "账户冻结";
    public static final String ALIAS_TRADE_ACCOUNT = "交易账户账号";
    public static final String ALIAS_HOLE_SHARE_AMOUNT = "持股数量";
    public static final String ALIAS_SHAREHOLDING_PROPORTION = "持股占比";
    public static final String ALIAS_FROZEN_SHARE_AMOUNT = "已冻结股数";
    public static final String ALIAS_PLEDGED_SHARE_AMOUNT = "已质押股数";
    public static final String ALIAS_CASH_BONUS = "奖金分红";
    public static final String ALIAS_BONUS_ISSUE = "派送红利";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "pf_pid", nullable = false, length = 10)
    private Integer pfPid;

    @Column(name = "gd_pid", nullable = false, length = 10)
    private Integer gdPid;

    @Column(name = "gd_type", nullable = true, length = 20)
    private String gdType;

    @Column(name = "sharehold_code", nullable = true, length = 50)
    private String shareholdCode;

    @Column(name = "gd_dj", nullable = true, length = 10)
    private String gdDj;

    @Column(name = "trade_account", nullable = true, length = 64)
    private String tradeAccount;

    @Column(name = "hole_share_amount", nullable = true, length = 64)
    private String holeShareAmount;

    @Column(name = "shareholding_proportion", nullable = true, length = 64)
    private String shareholdingProportion;

    @Column(name = "frozen_share_amount", nullable = true, length = 64)
    private String frozenShareAmount;

    @Column(name = "pledged_share_amount", nullable = true, length = 64)
    private String pledgedShareAmount;

    @Column(name = "cash_bonus", nullable = true, length = 11)
    private String cashBonus;

    @Column(name = "bonus_issue", nullable = true, length = 11)
    private String bonusIssue;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getPfPid() {
        return this.pfPid;
    }

    public void setPfPid(Integer value) {
        this.pfPid = value;
    }


    public Integer getGdPid() {
        return this.gdPid;
    }

    public void setGdPid(Integer value) {
        this.gdPid = value;
    }


    public String getGdType() {
        return this.gdType;
    }

    public void setGdType(String value) {
        this.gdType = value;
    }


    public String getShareholdCode() {
        return this.shareholdCode;
    }

    public void setShareholdCode(String value) {
        this.shareholdCode = value;
    }


    public String getGdDj() {
        return this.gdDj;
    }

    public void setGdDj(String value) {
        this.gdDj = value;
    }


    public String getTradeAccount() {
        return this.tradeAccount;
    }

    public void setTradeAccount(String value) {
        this.tradeAccount = value;
    }


    public String getHoleShareAmount() {
        return this.holeShareAmount;
    }

    public void setHoleShareAmount(String value) {
        this.holeShareAmount = value;
    }


    public String getShareholdingProportion() {
        return this.shareholdingProportion;
    }

    public void setShareholdingProportion(String value) {
        this.shareholdingProportion = value;
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


    public String getCashBonus() {
        return this.cashBonus;
    }

    public void setCashBonus(String value) {
        this.cashBonus = value;
    }


    public String getBonusIssue() {
        return this.bonusIssue;
    }

    public void setBonusIssue(String value) {
        this.bonusIssue = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("gdPfInfo[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("pfPid = ").append(pfPid).append(", \n")
                .append("gdPid = ").append(gdPid).append(", \n")
                .append("gdType = ").append(gdType).append(", \n")
                .append("shareholdCode = ").append(shareholdCode).append(", \n")
                .append("gdDj = ").append(gdDj).append(", \n")
                .append("tradeAccount = ").append(tradeAccount).append(", \n")
                .append("holeShareAmount = ").append(holeShareAmount).append(", \n")
                .append("shareholdingProportion = ").append(shareholdingProportion).append(", \n")
                .append("frozenShareAmount = ").append(frozenShareAmount).append(", \n")
                .append("pledgedShareAmount = ").append(pledgedShareAmount).append(", \n")
                .append("cashBonus = ").append(cashBonus).append(", \n")
                .append("bonusIssue = ").append(bonusIssue).append(", \n")
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
        if (obj instanceof GdPfInfo == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GdPfInfo other = (GdPfInfo) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

