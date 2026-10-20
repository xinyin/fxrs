/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月07日 20:40:21
 *
 */

package com.yzj.jep.app.sou.model;

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
@Table(name = "sou_organization")
public class Organization extends BasePO {

    public static final String TABLE_ALIAS = "Organization";
    public static final String ALIAS_IDNO = "idno";
    public static final String ALIAS_REG_CODE = "机构代码";
    public static final String ALIAS_ORG_NAME = "机构名称";
    public static final String ALIAS_UNISC_CODE = "社会统一信用代码";
    public static final String ALIAS_LEGAL_PERSON = "法定代表人";
    public static final String ALIAS_REG_ADDRESS = "机构注册地址";
    public static final String ALIAS_SHAREHOLDING_PROPORTION = "成都农商控股比例";
    public static final String ALIAS_ORG_PROPERTY = "机构属性";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "idno", nullable = false, length = 10)
    private Integer idno;

    @Column(name = "reg_code", nullable = true, length = 255)
    private String regCode;

    @Column(name = "org_name", nullable = true, length = 255)
    private String orgName;

    @Column(name = "unisc_code", nullable = true, length = 255)
    private String uniscCode;

    @Column(name = "legal_person", nullable = true, length = 255)
    private String legalPerson;

    @Column(name = "reg_address", nullable = true, length = 255)
    private String regAddress;

    @Column(name = "shareholding_proportion", nullable = true, length = 255)
    private String shareholdingProportion;

    @Column(name = "org_property", nullable = true, length = 255)
    private String orgProperty;
    //columns END


    public Integer getIdno() {
        return this.idno;
    }

    public void setIdno(Integer value) {
        this.idno = value;
    }


    public String getRegCode() {
        return this.regCode;
    }

    public void setRegCode(String value) {
        this.regCode = value;
    }


    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String value) {
        this.orgName = value;
    }


    public String getUniscCode() {
        return this.uniscCode;
    }

    public void setUniscCode(String value) {
        this.uniscCode = value;
    }


    public String getLegalPerson() {
        return this.legalPerson;
    }

    public void setLegalPerson(String value) {
        this.legalPerson = value;
    }


    public String getRegAddress() {
        return this.regAddress;
    }

    public void setRegAddress(String value) {
        this.regAddress = value;
    }


    public String getShareholdingProportion() {
        return this.shareholdingProportion;
    }

    public void setShareholdingProportion(String value) {
        this.shareholdingProportion = value;
    }


    public String getOrgProperty() {
        return this.orgProperty;
    }

    public void setOrgProperty(String value) {
        this.orgProperty = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("organization[\n")
                .append("idno = ").append(idno).append(", \n")
                .append("regCode = ").append(regCode).append(", \n")
                .append("orgName = ").append(orgName).append(", \n")
                .append("uniscCode = ").append(uniscCode).append(", \n")
                .append("legalPerson = ").append(legalPerson).append(", \n")
                .append("regAddress = ").append(regAddress).append(", \n")
                .append("shareholdingProportion = ").append(shareholdingProportion).append(", \n")
                .append("orgProperty = ").append(orgProperty).append(", \n")
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getIdno())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Organization == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Organization other = (Organization) obj;
        return new EqualsBuilder()
                .append(getIdno(), other.getIdno())
                .isEquals();
    }
}

