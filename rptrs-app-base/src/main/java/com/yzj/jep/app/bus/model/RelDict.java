/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月26日 18:23:04
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "bus_rel_dict")
public class RelDict extends BasePO {

    public static final String TABLE_ALIAS = "RelDict";
    public static final String ALIAS_REL_CATEGORY = "关联关系类型";
    public static final String ALIAS_REL_CATEGORY_CODE = "关联关系类型code";
    public static final String ALIAS_REL_CATEGORY_SUB = "关联关系子类型";
    public static final String ALIAS_REL_CATEGORY_SUB_CODE = "关联关系子类型code";
    public static final String ALIAS_REL_DIRECTION = "关系方向";
    public static final String ALIAS_FROM_NAME = "fromName";
    public static final String ALIAS_TO_NAME = "toName";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Column(name = "rel_category", nullable = true, length = 16)
    private String relCategory;

    @Column(name = "rel_category_code", nullable = true, length = 16)
    private String relCategoryCode;

    @Column(name = "rel_category_sub", nullable = true, length = 16)
    private String relCategorySub;

    @Id
    @Column(name = "rel_category_sub_code", nullable = false, length = 16)
    private String relCategorySubCode;

    @Column(name = "rel_direction", nullable = true, length = 32)
    private String relDirection;

    @Column(name = "from_name", nullable = true, length = 32)
    private String fromName;

    @Column(name = "to_name", nullable = true, length = 32)
    private String toName;
    //columns END


    public String getRelCategory() {
        return this.relCategory;
    }

    public void setRelCategory(String value) {
        this.relCategory = value;
    }


    public String getRelCategoryCode() {
        return this.relCategoryCode;
    }

    public void setRelCategoryCode(String value) {
        this.relCategoryCode = value;
    }


    public String getRelCategorySub() {
        return this.relCategorySub;
    }

    public void setRelCategorySub(String value) {
        this.relCategorySub = value;
    }


    public String getRelCategorySubCode() {
        return this.relCategorySubCode;
    }

    public void setRelCategorySubCode(String value) {
        this.relCategorySubCode = value;
    }


    public String getRelDirection() {
        return this.relDirection;
    }

    public void setRelDirection(String value) {
        this.relDirection = value;
    }


    public String getFromName() {
        return this.fromName;
    }

    public void setFromName(String value) {
        this.fromName = value;
    }


    public String getToName() {
        return this.toName;
    }

    public void setToName(String value) {
        this.toName = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("relDict[\n")
                .append("relCategory = ").append(relCategory).append(", \n")
                .append("relCategoryCode = ").append(relCategoryCode).append(", \n")
                .append("relCategorySub = ").append(relCategorySub).append(", \n")
                .append("relCategorySubCode = ").append(relCategorySubCode).append(", \n")
                .append("relDirection = ").append(relDirection).append(", \n")
                .append("fromName = ").append(fromName).append(", \n")
                .append("toName = ").append(toName).append(", \n")
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getRelCategorySubCode())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RelDict == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelDict other = (RelDict) obj;
        return new EqualsBuilder()
                .append(getRelCategorySubCode(), other.getRelCategorySubCode())
                .isEquals();
    }
}

