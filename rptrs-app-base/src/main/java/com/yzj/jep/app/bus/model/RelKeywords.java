/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月10日 14:29:13
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "bus_rel_keywords")
public class RelKeywords extends BasePO {

    public static final String TABLE_ALIAS = "疑似关联方规则关键字";
    public static final String ALIAS_PID = "自增长主键";
    public static final String ALIAS_KEY_NO = "编号";
    public static final String ALIAS_KEY_ORG_NAME = "企业名称中关键字";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "key_no", nullable = true, length = 100)
    private String keyNo;

    @Column(name = "key_org_name", nullable = true, length = 100)
    private String keyOrgName;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getKeyNo() {
        return this.keyNo;
    }

    public void setKeyNo(String value) {
        this.keyNo = value;
    }


    public String getKeyOrgName() {
        return this.keyOrgName;
    }

    public void setKeyOrgName(String value) {
        this.keyOrgName = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("relKeywords[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("keyNo = ").append(keyNo).append(", \n")
                .append("keyOrgName = ").append(keyOrgName).append(", \n")
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
        if (obj instanceof RelKeywords == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelKeywords other = (RelKeywords) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

