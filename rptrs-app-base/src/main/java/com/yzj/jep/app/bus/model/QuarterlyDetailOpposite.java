/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:40
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
@Table(name = "bus_quarterly_detail_opposite")
public class QuarterlyDetailOpposite extends BasePO {

    public static final String TABLE_ALIAS = "关联交易季报-关联交易明细表-交易对手情况";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_TRANS_NUM = "交易编码（与季报关联交易明细表关联）";
    public static final String ALIAS_RELATED_TYPE = "关联方类型";
    public static final String ALIAS_RELATED_ID = "关联方主键（与关联方档案关联）";
    public static final String ALIAS_RELATED_NAME = "关联方名称";
    public static final String ALIAS_RELATED_CREDENTIALS_TYPE = "关联方证件类型编码";
    public static final String ALIAS_RELATED_CREDENTIALS_TYPE_NAME = "关联方证件类型名称";
    public static final String ALIAS_RELATED_CREDENTIALS_NUM = "关联方证件号码";
    public static final String ALIAS_RELATED_UNISC_CODE = "统一社会信用代码";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "trans_num", nullable = true, length = 50)
    private String transNum;

    @Column(name = "related_type", nullable = true, length = 20)
    private String relatedType;

    @Column(name = "related_id", nullable = true, length = 10)
    private Integer relatedId;

    @Column(name = "related_name", nullable = true, length = 100)
    private String relatedName;

    @Column(name = "related_credentials_type", nullable = true, length = 10)
    private String relatedCredentialsType;

    @Column(name = "related_credentials_type_name", nullable = true, length = 100)
    private String relatedCredentialsTypeName;

    @Column(name = "related_credentials_num", nullable = true, length = 20)
    private String relatedCredentialsNum;

    @Column(name = "related_unisc_code", nullable = true, length = 20)
    private String relatedUniscCode;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getTransNum() {
        return this.transNum;
    }

    public void setTransNum(String value) {
        this.transNum = value;
    }


    public String getRelatedType() {
        return this.relatedType;
    }

    public void setRelatedType(String value) {
        this.relatedType = value;
    }


    public Integer getRelatedId() {
        return this.relatedId;
    }

    public void setRelatedId(Integer value) {
        this.relatedId = value;
    }


    public String getRelatedName() {
        return this.relatedName;
    }

    public void setRelatedName(String value) {
        this.relatedName = value;
    }


    public String getRelatedCredentialsType() {
        return this.relatedCredentialsType;
    }

    public void setRelatedCredentialsType(String value) {
        this.relatedCredentialsType = value;
    }


    public String getRelatedCredentialsTypeName() {
        return this.relatedCredentialsTypeName;
    }

    public void setRelatedCredentialsTypeName(String value) {
        this.relatedCredentialsTypeName = value;
    }


    public String getRelatedCredentialsNum() {
        return this.relatedCredentialsNum;
    }

    public void setRelatedCredentialsNum(String value) {
        this.relatedCredentialsNum = value;
    }


    public String getRelatedUniscCode() {
        return this.relatedUniscCode;
    }

    public void setRelatedUniscCode(String value) {
        this.relatedUniscCode = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("quarterlyDetailOpposite[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("relatedType = ").append(relatedType).append(", \n")
                .append("relatedId = ").append(relatedId).append(", \n")
                .append("relatedName = ").append(relatedName).append(", \n")
                .append("relatedCredentialsType = ").append(relatedCredentialsType).append(", \n")
                .append("relatedCredentialsTypeName = ").append(relatedCredentialsTypeName).append(", \n")
                .append("relatedCredentialsNum = ").append(relatedCredentialsNum).append(", \n")
                .append("relatedUniscCode = ").append(relatedUniscCode).append(", \n")
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
        if (obj instanceof QuarterlyDetailOpposite == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        QuarterlyDetailOpposite other = (QuarterlyDetailOpposite) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

