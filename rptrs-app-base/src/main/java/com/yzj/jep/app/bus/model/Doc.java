/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月20日 14:23:05
 *
 */

package com.yzj.jep.app.bus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bus_doc")
public class Doc extends BasePO {

    public static final String TABLE_ALIAS = "Doc";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_SYS_NAME = "制度名称";
    public static final String ALIAS_ARTICLE_NUMBER = "文号";
    public static final String ALIAS_DISPATCH_DEP = "发文部门";
    public static final String ALIAS_UPLOAD_ORG = "上传机构";
    public static final String ALIAS_UPLOAD_DEP = "上传部门";
    public static final String ALIAS_EFFECTIVE_TIME = "生效时间";
    public static final String ALIAS_EXPIRY_TIME = "失效时间";
    public static final String ALIAS_ISSUE_ORG = "颁布机构";
    public static final String ALIAS_IS_VAL = "是否有效";
    public static final String ALIAS_SYSTEM_TYPE = "制度类型";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "sys_name", nullable = true, length = 200)
    private String sysName;

    @Column(name = "article_number", nullable = true, length = 200)
    private String articleNumber;

    @Column(name = "dispatch_dep", nullable = true, length = 200)
    private String dispatchDep;

    @Column(name = "upload_org", nullable = true, length = 200)
    private String uploadOrg;

    @Column(name = "upload_dep", nullable = true, length = 200)
    private String uploadDep;

    @Column(name = "effective_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date effectiveTime;

    @Column(name = "expiry_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date expiryTime;

    @Column(name = "issue_org", nullable = true, length = 200)
    private String issueOrg;

    @Column(name = "is_val", nullable = true, length = 50)
    private String isVal;

    @Column(name = "system_type", nullable = true, length = 20)
    private String systemType;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getSysName() {
        return this.sysName;
    }

    public void setSysName(String value) {
        this.sysName = value;
    }


    public String getArticleNumber() {
        return this.articleNumber;
    }

    public void setArticleNumber(String value) {
        this.articleNumber = value;
    }


    public String getDispatchDep() {
        return this.dispatchDep;
    }

    public void setDispatchDep(String value) {
        this.dispatchDep = value;
    }


    public String getUploadOrg() {
        return this.uploadOrg;
    }

    public void setUploadOrg(String value) {
        this.uploadOrg = value;
    }


    public String getUploadDep() {
        return this.uploadDep;
    }

    public void setUploadDep(String value) {
        this.uploadDep = value;
    }


    public Date getEffectiveTime() {
        return this.effectiveTime;
    }

    public void setEffectiveTime(Date value) {
        this.effectiveTime = value;
    }


    public Date getExpiryTime() {
        return this.expiryTime;
    }

    public void setExpiryTime(Date value) {
        this.expiryTime = value;
    }


    public String getIssueOrg() {
        return this.issueOrg;
    }

    public void setIssueOrg(String value) {
        this.issueOrg = value;
    }


    public String getIsVal() {
        return this.isVal;
    }

    public void setIsVal(String value) {
        this.isVal = value;
    }


    public String getSystemType() {
        return this.systemType;
    }

    public void setSystemType(String value) {
        this.systemType = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("doc[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("sysName = ").append(sysName).append(", \n")
                .append("articleNumber = ").append(articleNumber).append(", \n")
                .append("dispatchDep = ").append(dispatchDep).append(", \n")
                .append("uploadOrg = ").append(uploadOrg).append(", \n")
                .append("uploadDep = ").append(uploadDep).append(", \n")
                .append("effectiveTime = ").append(effectiveTime).append(", \n")
                .append("expiryTime = ").append(expiryTime).append(", \n")
                .append("issueOrg = ").append(issueOrg).append(", \n")
                .append("isVal = ").append(isVal).append(", \n")
                .append("systemType = ").append(systemType).append(", \n")
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
        if (obj instanceof Doc == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Doc other = (Doc) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

