/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月18日 17:42:12
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
@Table(name = "bus_tra_balance_deduction")
public class TraBalanceDeduction extends BasePO {

    public static final String TABLE_ALIAS = "关联交易余额扣除规则表";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_SECURIT_DEPOSIT = "保证金存款";
    public static final String ALIAS_BANK_DEPOSIT = "质押的银行存单";
    public static final String ALIAS_NATIONAL_DEBT = "国债";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "SECURIT_DEPOSIT", nullable = true, length = 1)
    private String securitDeposit;

    @Column(name = "bank_deposit", nullable = true, length = 1)
    private String bankDeposit;

    @Column(name = "national_debt", nullable = true, length = 1)
    private String nationalDebt;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getSecuritDeposit() {
        return this.securitDeposit;
    }

    public void setSecuritDeposit(String value) {
        this.securitDeposit = value;
    }


    public String getBankDeposit() {
        return this.bankDeposit;
    }

    public void setBankDeposit(String value) {
        this.bankDeposit = value;
    }


    public String getNationalDebt() {
        return this.nationalDebt;
    }

    public void setNationalDebt(String value) {
        this.nationalDebt = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("traBalanceDeduction[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("securitDeposit = ").append(securitDeposit).append(", \n")
                .append("bankDeposit = ").append(bankDeposit).append(", \n")
                .append("nationalDebt = ").append(nationalDebt).append(", \n")
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
        if (obj instanceof TraBalanceDeduction == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraBalanceDeduction other = (TraBalanceDeduction) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

