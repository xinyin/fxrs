/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月16日 14:41:49
 *
 */

package com.yzj.jep.app.log.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


@Entity
@Table(name = "sys_log_record")
public class LogRecord extends BasePO {

    public static final String TABLE_ALIAS = "LogRecord";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_LOG_CONTENT = "logContent";
    public static final String ALIAS_LOG_DESCRIPTION = "logDescription";
    public static final String ALIAS_LOGIN_IP = "loginIp";
    public static final String ALIAS_MODULE = "module";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "log_content", nullable = true, length = 1000)
    private String logContent;

    @Column(name = "log_description", nullable = true, length = 100)
    private String logDescription;

    @Column(name = "login_ip", nullable = true, length = 25)
    private String loginIp;

    @Column(name = "module", nullable = true, length = 50)
    private String module;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getLogContent() {
        return this.logContent;
    }

    public void setLogContent(String value) {
        this.logContent = value;
    }


    public String getLogDescription() {
        return this.logDescription;
    }

    public void setLogDescription(String value) {
        this.logDescription = value;
    }


    public String getLoginIp() {
        return this.loginIp;
    }

    public void setLoginIp(String value) {
        this.loginIp = value;
    }


    public String getModule() {
        return this.module;
    }

    public void setModule(String value) {
        this.module = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("logRecord[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("logContent = ").append(logContent).append(", \n")
                .append("logDescription = ").append(logDescription).append(", \n")
                .append("loginIp = ").append(loginIp).append(", \n")
                .append("module = ").append(module).append(", \n")
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
        if (obj instanceof LogRecord == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LogRecord other = (LogRecord) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

