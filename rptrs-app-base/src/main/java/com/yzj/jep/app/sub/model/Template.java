/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月01日 17:05:10
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
@Table(name = "sub_template")
public class Template extends BasePO {

    public static final String TABLE_ALIAS = "保送模板表";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_TEMPLATE_NAME = "模板名称";
    public static final String ALIAS_SERVICE = "service";
    public static final String ALIAS_SUB_TYPE = "报送类型";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "template_name", nullable = false, length = 64)
    private String templateName;

    @Column(name = "service", nullable = true, length = 64)
    private String service;

    @Column(name = "sub_type", nullable = true, length = 64)
    private String subType;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String value) {
        this.templateName = value;
    }


    public String getService() {
        return this.service;
    }

    public void setService(String value) {
        this.service = value;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    @Override
    public String toString() {
        return new StringBuilder("template[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("templateName = ").append(templateName).append(", \n")
                .append("service = ").append(service).append(", \n")
                .append("subType = ").append(subType).append(", \n")
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
        if (obj instanceof Template == false) {return false;}
        if (this == obj) {return true;}
        Template other = (Template) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

