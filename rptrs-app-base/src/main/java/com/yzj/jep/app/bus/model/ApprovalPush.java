/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月20日 11:53:28
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
@Table(name = "bus_approval_result_push")
public class ApprovalPush extends BasePO {

    @Id
    @Column(name = "pid", nullable = false, length = 64)
    private String pid;

    @Column(name = "status", nullable = true, length = 2)
    private String status;

    @Column(name = "contractno", nullable = true, length = 64)
    private String contractno;

    @Column(name = "code", nullable = true, length = 10)
    private String code;


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return new StringBuilder("docRel[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("contractno = ").append(contractno).append(", \n")
                .append("code = ").append(code).append(", \n")
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
        if (obj instanceof ApprovalPush == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ApprovalPush other = (ApprovalPush) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

