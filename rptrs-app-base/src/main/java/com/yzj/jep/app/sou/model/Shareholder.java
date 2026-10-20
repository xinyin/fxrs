/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月07日 20:40:22
 *
 */

package com.yzj.jep.app.sou.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sou_shareholder")
public class Shareholder extends BasePO {

    public static final String TABLE_ALIAS = "Shareholder";
    public static final String ALIAS_IDNO = "idno";
    public static final String ALIAS_ENTITY_ID = "主体编号";
    public static final String ALIAS_ENTITY_NAME = "主体企业名称";
    public static final String ALIAS_NAME = "name";
    public static final String ALIAS_GENDER = "0-男，1-女";
    public static final String ALIAS_ADDRESS = "住所";
    public static final String ALIAS_ID_TYPE = "证件类型";
    public static final String ALIAS_ID_CARD = "证件号";
    public static final String ALIAS_CONTRIBUTION_AMOUNT = "出资额（万元）";
    public static final String ALIAS_PROPORTION = "占比（%）";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "idno", nullable = false, length = 10)
    private Integer idno;

    @Column(name = "entity_id", nullable = true, length = 255)
    private String entityId;

    @Column(name = "type", nullable = true, length = 20)
    private String type;

    @Column(name = "entity_name", nullable = true, length = 255)
    private String entityName;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "gender", nullable = true, length = 10)
    private Integer gender;

    @Column(name = "address", nullable = true, length = 255)
    private String address;

    @Column(name = "id_type", nullable = true, length = 10)
    private Integer idType;

    @Column(name = "id_card", nullable = true, length = 255)
    private String idCard;

    @Column(name = "contribution_amount", nullable = true, precision = 10, scale = 0)
    private Long contributionAmount;

    @Column(name = "proportion", nullable = true, length = 10)
    private Integer proportion;
    //columns END


    public Integer getIdno() {
        return this.idno;
    }

    public void setIdno(Integer value) {
        this.idno = value;
    }


    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String value) {
        this.entityId = value;
    }


    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String value) {
        this.entityName = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }


    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer value) {
        this.gender = value;
    }


    public String getAddress() {
        return this.address;
    }

    public void setAddress(String value) {
        this.address = value;
    }


    public Integer getIdType() {
        return this.idType;
    }

    public void setIdType(Integer value) {
        this.idType = value;
    }


    public String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(String value) {
        this.idCard = value;
    }


    public Long getContributionAmount() {
        return this.contributionAmount;
    }

    public void setContributionAmount(Long value) {
        this.contributionAmount = value;
    }


    public Integer getProportion() {
        return this.proportion;
    }

    public void setProportion(Integer value) {
        this.proportion = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("shareholder[\n")
                .append("idno = ").append(idno).append(", \n")
                .append("entityId = ").append(entityId).append(", \n")
                .append("entityName = ").append(entityName).append(", \n")
                .append("name = ").append(name).append(", \n")
                .append("gender = ").append(gender).append(", \n")
                .append("address = ").append(address).append(", \n")
                .append("idType = ").append(idType).append(", \n")
                .append("idCard = ").append(idCard).append(", \n")
                .append("contributionAmount = ").append(contributionAmount).append(", \n")
                .append("proportion = ").append(proportion).append(", \n")
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
        if (obj instanceof Shareholder == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Shareholder other = (Shareholder) obj;
        return new EqualsBuilder()
                .append(getIdno(), other.getIdno())
                .isEquals();
    }
}

