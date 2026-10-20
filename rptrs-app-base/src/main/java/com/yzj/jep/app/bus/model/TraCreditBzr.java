/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月22日 14:42:43
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


@Entity
@Table(name = "bus_tra_credit_bzr")
public class TraCreditBzr extends BasePO {

    public static final String TABLE_ALIAS = "关联交易表-授信-保证人信息表";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_CREDIT_ID = "授信类ID";
    public static final String ALIAS_GUARANTOR_NAME = "保证人名称";
    public static final String ALIAS_IDENTLFY_TYPE = "证件类型";
    public static final String ALIAS_IDENT_NUMBER = "证件号码";
    public static final String ALIAS_SEX = "性别";
    public static final String ALIAS_RELATION = "关系";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "credit_id", nullable = true, length = 16)
    private String creditId;

    @Column(name = "guarantor_name", nullable = true, length = 20)
    private String guarantorName;

    @Column(name = "identlfy_type", nullable = true, length = 5)
    private String identlfyType;

    @Column(name = "ident_number", nullable = true, length = 100)
    private String identNumberBzr;

    @Column(name = "sex", nullable = true, length = 5)
    private String sex;

    @Column(name = "relation", nullable = true, length = 5)
    private String relation;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getCreditId() {
        return this.creditId;
    }

    public void setCreditId(String value) {
        this.creditId = value;
    }


    public String getGuarantorName() {
        return this.guarantorName;
    }

    public void setGuarantorName(String value) {
        this.guarantorName = value;
    }


    public String getIdentlfyType() {
        return this.identlfyType;
    }

    public void setIdentlfyType(String value) {
        this.identlfyType = value;
    }


    public String getIdentNumberBzr() {
        return identNumberBzr;
    }

    public void setIdentNumberBzr(String identNumberBzr) {
        this.identNumberBzr = identNumberBzr;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String value) {
        this.sex = value;
    }


    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String value) {
        this.relation = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("traCreditBzr[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("creditId = ").append(creditId).append(", \n")
                .append("guarantorName = ").append(guarantorName).append(", \n")
                .append("identlfyType = ").append(identlfyType).append(", \n")
                .append("identNumberBzr = ").append(identNumberBzr).append(", \n")
                .append("sex = ").append(sex).append(", \n")
                .append("relation = ").append(relation).append(", \n")
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
        if (obj instanceof TraCreditBzr == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraCreditBzr other = (TraCreditBzr) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

