/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月01日 17:05:11
 *
 */

package com.yzj.jep.app.sub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

@Entity
@Table(name = "sub_template_details")
public class TemplateDetails extends BasePO {

    public static final String TABLE_ALIAS = "保送模板明细表";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_TEMPLATE_ID = "模板id";
    public static final String ALIAS_PARENT_ID = "上级id";
    public static final String ALIAS_ELEMENT_NAME = "元素名称";
    public static final String ALIAS_COL_NAME = "字段名称";
    public static final String ALIAS_COL_LENGTH = "字段长度";
    public static final String ALIAS_COL_MUST = "是否必填";
    public static final String ALIAS_CONDITION_COL = "是否必填";
    public static final String ALIAS_CONDITION_VALUE = "是否必填";

    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "template_id", nullable = true, length = 10)
    private Integer templateId;

    @Column(name = "parent_id", nullable = true, length = 10)
    private Integer parentId;

    @Column(name = "element_name", nullable = true, length = 64)
    private String elementName;

    @Column(name = "col_name", nullable = true, length = 64)
    private String colName;

    @Column(name = "col_length", nullable = true, length = 64)
    private Integer colLength;

    @Column(name = "col_must", nullable = true, length = 64)
    private String colMust;

    @Column(name = "condition_col", nullable = true, length = 64)
    private String conditionCol;

    @Column(name = "condition_value", nullable = true, length = 64)
    private String conditionValue;

    @Column(name = "match_name", nullable = true, length = 64)
    private String matchName;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Integer value) {
        this.templateId = value;
    }


    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer value) {
        this.parentId = value;
    }


    public String getElementName() {
        return this.elementName;
    }

    public void setElementName(String value) {
        this.elementName = value;
    }


    public String getColName() {
        return this.colName;
    }

    public void setColName(String value) {
        this.colName = value;
    }

    public Integer getColLength() {
        return colLength;
    }

    public void setColLength(Integer colLength) {
        this.colLength = colLength;
    }

    public String getColMust() {
        return colMust;
    }

    public void setColMust(String colMust) {
        this.colMust = colMust;
    }

    public String getConditionCol() {
        return conditionCol;
    }

    public void setConditionCol(String conditionCol) {
        this.conditionCol = conditionCol;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    @Override
    public String toString() {
        return new StringBuilder("templateDetails[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("templateId = ").append(templateId).append(", \n")
                .append("parentId = ").append(parentId).append(", \n")
                .append("elementName = ").append(elementName).append(", \n")
                .append("colName = ").append(colName).append(", \n")
                .append("colLength = ").append(colLength).append(", \n")
                .append("colMust = ").append(colMust).append(", \n")
                .append("conditionCol = ").append(conditionCol).append(", \n")
                .append("conditionValue = ").append(conditionValue).append(", \n")
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
        if (obj instanceof TemplateDetails == false) {return false;}
        if (this == obj) {return true;}
        TemplateDetails other = (TemplateDetails) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

