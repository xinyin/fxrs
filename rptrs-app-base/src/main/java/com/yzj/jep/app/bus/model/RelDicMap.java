/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月06日 18:46:52
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
@Table(name = "bus_rel_dic_map")
public class RelDicMap extends BasePO {

    public static final String TABLE_ALIAS = "RelDicMap";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_PARENT_CODE = "父字典码";
    public static final String ALIAS_PARENT_NAME = "父字典码名称";
    public static final String ALIAS_SUB_CODE = "子字典码";
    public static final String ALIAS_SUB_NAME = "子字典码";
    public static final String ALIAS_SUB_REL_TYPE = "子码关联方类型";
    public static final String ALIAS_SUB_REL_CLASS = "子码关联方类别";
    public static final String ALIAS_VIEW_SEQ = "显示顺序";
    public static final String ALIAS_HAVE_SUB_DIC = "是否有下级字典";
    public static final String ALIAS_SUB_DIC_CODE = "下级字典编码";
    public static final String ALIAS_RULE_ID = "判定规则ID";
    public static final String ALIAS_REMARK = "备注";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "parent_code", nullable = true, length = 16)
    private String parentCode;

    @Column(name = "parent_name", nullable = true, length = 255)
    private String parentName;

    @Column(name = "sub_code", nullable = true, length = 16)
    private String subCode;

    @Column(name = "sub_name", nullable = true, length = 255)
    private String subName;

    @Column(name = "sub_rel_type", nullable = true, length = 16)
    private String subRelType;

    @Column(name = "sub_rel_class", nullable = true, length = 16)
    private String subRelClass;

    @Column(name = "view_seq", nullable = true, length = 10)
    private Integer viewSeq;

    @Column(name = "have_sub_dic", nullable = true, length = 16)
    private String haveSubDic;

    @Column(name = "sub_dic_code", nullable = true, length = 64)
    private String subDicCode;

    @Column(name = "rule_id", nullable = true, length = 64)
    private String ruleId;

    @Column(name = "remark", nullable = true, length = 255)
    private String remark;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String value) {
        this.parentCode = value;
    }


    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String value) {
        this.parentName = value;
    }


    public String getSubCode() {
        return this.subCode;
    }

    public void setSubCode(String value) {
        this.subCode = value;
    }


    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String value) {
        this.subName = value;
    }


    public String getSubRelType() {
        return this.subRelType;
    }

    public void setSubRelType(String value) {
        this.subRelType = value;
    }


    public String getSubRelClass() {
        return this.subRelClass;
    }

    public void setSubRelClass(String value) {
        this.subRelClass = value;
    }


    public Integer getViewSeq() {
        return this.viewSeq;
    }

    public void setViewSeq(Integer value) {
        this.viewSeq = value;
    }


    public String getHaveSubDic() {
        return this.haveSubDic;
    }

    public void setHaveSubDic(String value) {
        this.haveSubDic = value;
    }


    public String getSubDicCode() {
        return this.subDicCode;
    }

    public void setSubDicCode(String value) {
        this.subDicCode = value;
    }


    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String value) {
        this.ruleId = value;
    }


    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("relDicMap[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("parentCode = ").append(parentCode).append(", \n")
                .append("parentName = ").append(parentName).append(", \n")
                .append("subCode = ").append(subCode).append(", \n")
                .append("subName = ").append(subName).append(", \n")
                .append("subRelType = ").append(subRelType).append(", \n")
                .append("subRelClass = ").append(subRelClass).append(", \n")
                .append("viewSeq = ").append(viewSeq).append(", \n")
                .append("haveSubDic = ").append(haveSubDic).append(", \n")
                .append("subDicCode = ").append(subDicCode).append(", \n")
                .append("ruleId = ").append(ruleId).append(", \n")
                .append("remark = ").append(remark).append(", \n")
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
        if (obj instanceof RelDicMap == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelDicMap other = (RelDicMap) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

