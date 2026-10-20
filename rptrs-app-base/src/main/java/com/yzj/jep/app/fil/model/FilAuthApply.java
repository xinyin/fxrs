package com.yzj.jep.app.fil.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.*;
import java.util.Date;

/**
 * @title: FilAuthApply
 * @Author Whx
 * @Date: 2021/8/12 19:36
 * @Version 1.0
 */
@Entity
@Table(name = "fil_sys_auth_apply")
public class FilAuthApply extends BasePO {

    @Id
    @Column(name = "pid", nullable = false, length = 11)
    private Integer pid;

    @Column(name = "APPLICANT", nullable = true, length = 100)
    private String applicant;

    @Column(name = "DEPARTMENT", nullable = false, length = 100)
    private String department;

    @Column(name = "CREATED_TIME_")
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

    @Column(name = "STATUS", nullable = true, length = 2)
    private String status;

    @Column(name = "POST", nullable = true, length = 100)
    private String post;

    @Column(name = "ROLE", nullable = true, length = 100)
    private String role;


    @Column(name = "REMARK", nullable = true, length = 1000)
    private String  remark;

    @Column(name = "flag_pid", nullable = true, length = 64)
    private String flagPid;

    @Column(name = "fil_num", nullable = true, length = 64)
    private String filNum;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFlagPid() {
        return flagPid;
    }

    public void setFlagPid(String flagPid) {
        this.flagPid = flagPid;
    }

    public String getFilNum() {
        return filNum;
    }

    public void setFilNum(String filNum) {
        this.filNum = filNum;
    }
}
