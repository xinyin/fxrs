/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月10日 10:36:06
 *
 */

package com.yzj.jep.app.bus.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bus_alig_map")
public class AligMap extends BasePO {

    public static final String TABLE_ALIAS = "AligMap";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_PA_ALIG_CODE = "父级定位码";
    public static final String ALIAS_REL_CATEGORY_VAL = "关联关系类别字典项值";
    public static final String ALIAS_ASSO_TYPE_VAL = "关联关系类型字典项值";
    public static final String ALIAS_CH_ALIG_CODE = "子节点定位码";
    public static final String ALIAS_CALIBER = "口径";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "pa_alig_code", nullable = false, length = 10)
    private String paAligCode;

    @Column(name = "rel_category_val", nullable = false, length = 20)
    private String relCategoryVal;

    @Column(name = "asso_type_val", nullable = false, length = 20)
    private String assoTypeVal;

    @Column(name = "ch_alig_code", nullable = false, length = 10)
    private String chAligCode;

    @Column(name = "caliber", nullable = false, length = 10)
    private String caliber;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getPaAligCode() {
        return this.paAligCode;
    }

    public void setPaAligCode(String value) {
        this.paAligCode = value;
    }


    public String getRelCategoryVal() {
        return this.relCategoryVal;
    }

    public void setRelCategoryVal(String value) {
        this.relCategoryVal = value;
    }


    public String getAssoTypeVal() {
        return this.assoTypeVal;
    }

    public void setAssoTypeVal(String value) {
        this.assoTypeVal = value;
    }


    public String getChAligCode() {
        return this.chAligCode;
    }

    public void setChAligCode(String value) {
        this.chAligCode = value;
    }


    public String getCaliber() {
        return this.caliber;
    }

    public void setCaliber(String value) {
        this.caliber = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("aligMap[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("paAligCode = ").append(paAligCode).append(", \n")
                .append("relCategoryVal = ").append(relCategoryVal).append(", \n")
                .append("assoTypeVal = ").append(assoTypeVal).append(", \n")
                .append("chAligCode = ").append(chAligCode).append(", \n")
                .append("caliber = ").append(caliber).append(", \n")
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
        if (obj instanceof AligMap == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        AligMap other = (AligMap) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

