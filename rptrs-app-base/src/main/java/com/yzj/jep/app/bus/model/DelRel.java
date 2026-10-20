/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月23日 15:01:48
 *
 */

package com.yzj.jep.app.bus.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "bus_del_rel")
public class DelRel extends BasePO {

    public static final String TABLE_ALIAS = "DelRel";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_NAME = "姓名";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内外标识";
    public static final String ALIAS_MGER_ORG = "管户机构";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_IDENTIFY_NUMBER = "证件号码";
    public static final String ALIAS_REGULATOR = "解除口径";
    public static final String ALIAS_TYPE = "关联方类型";
    public static final String ALIAS_REL_PID = "关联方主键";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "name", nullable = true, length = 100)
    private String name;

    @Column(name = "domestic_or_foreign", nullable = true, length = 10)
    private String domesticOrForeign;

    @Column(name = "mger_org", nullable = true, length = 10)
    private String mgerOrg;

    @Column(name = "identify_type", nullable = true, length = 30)
    private String identifyType;

    @Column(name = "identify_number", nullable = true, length = 20)
    private String identifyNumber;

    @Column(name = "regulator", nullable = true, length = 20)
    private String regulator;

    @Column(name = "type", nullable = true, length = 10)
    private String type;

    @Column(name = "rel_pid", nullable = true, length = 10)
    private Integer relPid;

    //经办部门
    @Column(name = "TRANSACTION_DEP", nullable = true, length = 50)
    private String transactionDep;

    //经办人
    @Column(name = "OPERATOR", nullable = true, length = 100)
    private String operator;
    //columns END

    //加密后的身份证号码
    @Transient
    private String encodeIdentifyNummber;

    public String getEncodeIdentifyNummber() {
        return encodeIdentifyNummber;
    }

    public void setEncodeIdentifyNummber(String encodeIdentifyNummber) {
        this.encodeIdentifyNummber = encodeIdentifyNummber;
    }

    public String getTransactionDep() {
        return transactionDep;
    }

    public void setTransactionDep(String transactionDep) {
        this.transactionDep = transactionDep;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }


    public String getDomesticOrForeign() {
        return this.domesticOrForeign;
    }

    public void setDomesticOrForeign(String value) {
        this.domesticOrForeign = value;
    }


    public String getMgerOrg() {
        return this.mgerOrg;
    }

    public void setMgerOrg(String value) {
        this.mgerOrg = value;
    }


    public String getIdentifyType() {
        return this.identifyType;
    }

    public void setIdentifyType(String value) {
        this.identifyType = value;
    }


    public String getIdentifyNumber() {
        return this.identifyNumber;
    }

    public void setIdentifyNumber(String value) {
        this.identifyNumber = value;
    }


    public String getRegulator() {
        return this.regulator;
    }

    public void setRegulator(String value) {
        this.regulator = value;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }


    public Integer getRelPid() {
        return this.relPid;
    }

    public void setRelPid(Integer value) {
        this.relPid = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("delRel[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("name = ").append(name).append(", \n")
                .append("domesticOrForeign = ").append(domesticOrForeign).append(", \n")
                .append("mgerOrg = ").append(mgerOrg).append(", \n")
                .append("identifyType = ").append(identifyType).append(", \n")
                .append("identifyNumber = ").append(identifyNumber).append(", \n")
                .append("regulator = ").append(regulator).append(", \n")
                .append("type = ").append(type).append(", \n")
                .append("relPid = ").append(relPid).append(", \n")
                .append("transactionDep = ").append(transactionDep).append(", \n")
                .append("operator = ").append(operator).append(", \n")
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
        if (obj instanceof DelRel == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        DelRel other = (DelRel) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

