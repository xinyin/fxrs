/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月20日 11:53:28
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
@Table(name = "bus_doc_rel")
public class DocRel extends BasePO {

    public static final String TABLE_ALIAS = "DocRel";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_DOC_INNER_PID = "行内制度pid";
    public static final String ALIAS_DOC_OUTSIDE_PID = "行外制度pid";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 20)
    private Integer pid;

    @Column(name = "doc_inner_pid", nullable = true, length = 20)
    private Integer docInnerPid;

    @Column(name = "doc_outside_pid", nullable = true, length = 20)
    private Integer docOutsidePid;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getDocInnerPid() {
        return this.docInnerPid;
    }

    public void setDocInnerPid(Integer value) {
        this.docInnerPid = value;
    }


    public Integer getDocOutsidePid() {
        return this.docOutsidePid;
    }

    public void setDocOutsidePid(Integer value) {
        this.docOutsidePid = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("docRel[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("docInnerPid = ").append(docInnerPid).append(", \n")
                .append("docOutsidePid = ").append(docOutsidePid).append(", \n")
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
        if (obj instanceof DocRel == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        DocRel other = (DocRel) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

