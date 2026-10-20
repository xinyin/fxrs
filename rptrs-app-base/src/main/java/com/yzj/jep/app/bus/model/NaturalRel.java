/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月17日 10:34:26
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
@Table(name = "bus_natural_rel")
public class NaturalRel extends BasePO {

    public static final String TABLE_ALIAS = "自然人亲属关系表";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_NATURAL_ID = "自然人id";
    public static final String ALIAS_REL_NATURAL_ID = "家属自然人id";
    public static final String ALIAS_NAME = "姓名";
    public static final String ALIAS_SEX = "性别";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_IDENTIFY_NUMBER = "证件号码";
    public static final String ALIAS_RELATION = "关系";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "natural_id", nullable = true, length = 10)
    private Integer naturalId;

    @Column(name = "rel_natural_id", nullable = true, length = 10)
    private Integer relNaturalId;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Column(name = "sex", nullable = true, length = 10)
    private String sex;

    @Column(name = "identify_type", nullable = false, length = 10)
    private String identifyType;

    @Column(name = "identify_number", nullable = false, length = 20)
    private String identifyNumber;

    @Column(name = "relation", nullable = true, length = 64)
    private String relation;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getNaturalId() {
        return this.naturalId;
    }

    public void setNaturalId(Integer value) {
        this.naturalId = value;
    }


    public Integer getRelNaturalId() {
        return this.relNaturalId;
    }

    public void setRelNaturalId(Integer value) {
        this.relNaturalId = value;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }


    public String getSex() {
        return this.sex;
    }

    public void setSex(String value) {
        this.sex = value;
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


    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String value) {
        this.relation = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("naturalRel[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("naturalId = ").append(naturalId).append(", \n")
                .append("relNaturalId = ").append(relNaturalId).append(", \n")
                .append("name = ").append(name).append(", \n")
                .append("sex = ").append(sex).append(", \n")
                .append("identifyType = ").append(identifyType).append(", \n")
                .append("identifyNumber = ").append(identifyNumber).append(", \n")
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
        if (obj instanceof NaturalRel == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        NaturalRel other = (NaturalRel) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

