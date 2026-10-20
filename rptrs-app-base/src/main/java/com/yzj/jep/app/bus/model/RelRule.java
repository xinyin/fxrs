/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月10日 14:29:15
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
@Table(name = "bus_rel_rule")
public class RelRule extends BasePO {

    public static final String TABLE_ALIAS = "疑似关联方规则配置";
    public static final String ALIAS_PID = "自增长主键";
    public static final String ALIAS_RULE_NAME = "规则名称";
    public static final String ALIAS_RULE_TYPE = "规则所属类型  SUSORG或SUSPSN";
    public static final String ALIAS_RULE_STATUS = "启用状态 0停用 1启用";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "rule_name", nullable = true, length = 100)
    private String ruleName;

    @Column(name = "rule_type", nullable = true, length = 100)
    private String ruleType;

    @Column(name = "rule_status", nullable = true, length = 10)
    private String ruleStatus;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getRuleName() {
        return this.ruleName;
    }

    public void setRuleName(String value) {
        this.ruleName = value;
    }


    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String value) {
        this.ruleType = value;
    }


    public String getRuleStatus() {
        return this.ruleStatus;
    }

    public void setRuleStatus(String value) {
        this.ruleStatus = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("relRule[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("ruleName = ").append(ruleName).append(", \n")
                .append("ruleType = ").append(ruleType).append(", \n")
                .append("ruleStatus = ").append(ruleStatus).append(", \n")
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
        if (obj instanceof RelRule == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelRule other = (RelRule) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

