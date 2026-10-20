/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:34
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

import java.util.Date;

@Entity
@Table(name = "bus_incidence_relation")
public class IncidenceRelation extends BasePO {

    public static final String TABLE_ALIAS = "关联关系表";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_ENTITY_PID = "关联主体pid";
    public static final String ALIAS_ENTITY_REG_CODE = "关联主体登记编码";
    public static final String ALIAS_ENTITY_TYPE = "关联主体是类别（1-法人，2-自然人）";
    public static final String ALIAS_ENTITY_NAME = "关联主体名称";
    public static final String ALIAS_SUPER_ENTITY_PID = "上层关联主体pid";
    public static final String ALIAS_SUPER_ENTITY_REG_CODE = "上层关联主体登记编码";
    public static final String ALIAS_SUPER_ENTITY_TYPE = "上层关联主体是类别（1-法人，2-自然人）";
    public static final String ALIAS_SUPER_ENTITY_NAME = "上层关联主体名称";
    public static final String ALIAS_RELATION_ID = "主体与上层主体关联关系id";
    public static final String ALIAS_RELATION_DESC = "主体与上层主体关联关描述";
    public static final String ALIAS_RELATION_INFO = "关联关系说明";
    public static final String ALIAS_IS_SHARE_HOLDING = "是否持有或控制上一级关联方股权(0-否，1-是)";
    public static final String ALIAS_HOLDING_PERCENT = "持股占比（默认为0）";
    public static final String ALIAS_ORIGNAL_REGCODE = "银行保险机构编码";
    public static final String ALIAS_ORIGNAL_ORG_NAME = "源主体（银行/保险机构）名称";
    public static final String ALIAS_RELATION_ORIGNAL = "关联主体与银行保险机构关联关系";
    public static final String ALIAS_REPORT_VERSION = "报送版本号";
    public static final String ALIAS_PARENT_LEVEL_PID = "上一层pid";
    public static final String ALIAS_LEVEL = "关联关系层级（1为根节点(银行/保险)，该表中只有以2开始的子节点）";
    public static final String ALIAS_VALID_STATUS = "是否有效 0-无效，1-有效";
    public static final String ALIAS_IS_REPORTED = "是否被报送过（0-历史上从未报送过，1-已被报送过）";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_INVALID_TIME = "失效时间";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "entity_pid", nullable = true, length = 10)
    private Integer entityPid;

    @Column(name = "entity_reg_code", nullable = true, length = 32)
    private String entityRegCode;

    @Column(name = "entity_type", nullable = true, length = 10)
    private String entityType;

    @Column(name = "entity_name", nullable = true, length = 500)
    private String entityName;

    @Column(name = "super_entity_pid", nullable = true, length = 30)
    private String superEntityPid;

    @Column(name = "super_entity_reg_code", nullable = true, length = 32)
    private String superEntityRegCode;

    @Column(name = "super_entity_type", nullable = true, length = 10)
    private String superEntityType;

    @Column(name = "super_entity_name", nullable = true, length = 500)
    private String superEntityName;

    @Column(name = "relation_id", nullable = true, length = 10)
    private String relationId;

    @Column(name = "relation_desc", nullable = true, length = 100)
    private String relationDesc;

    @Column(name = "relation_info", nullable = true, length = 2000)
    private String relationInfo;

    @Column(name = "is_share_holding", nullable = true, length = 5)
    private String isShareHolding;

    @Column(name = "holding_percent", nullable = true, precision = 10, scale = 0)
    private Long holdingPercent;

    @Column(name = "orignal_regcode", nullable = true, length = 20)
    private String orignalRegcode;

    @Column(name = "orignal_org_name", nullable = true, length = 100)
    private String orignalOrgName;

    @Column(name = "relation_orignal", nullable = true, length = 100)
    private String relationOrignal;

    @Column(name = "report_version", nullable = true, length = 10)
    private Integer reportVersion;

    @Column(name = "parent_level_pid", nullable = true, length = 10)
    private Integer parentLevelPid;

    @Column(name = "level", nullable = true, length = 20)
    private String level;

    @Column(name = "valid_status", nullable = true, length = 10)
    private String validStatus;

    @Column(name = "is_reported", nullable = true, length = 10)
    private String isReported;

    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;

    @Column(name = "invalid_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date invalidTime;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getEntityPid() {
        return this.entityPid;
    }

    public void setEntityPid(Integer value) {
        this.entityPid = value;
    }


    public String getEntityRegCode() {
        return this.entityRegCode;
    }

    public void setEntityRegCode(String value) {
        this.entityRegCode = value;
    }


    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String value) {
        this.entityType = value;
    }


    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String value) {
        this.entityName = value;
    }


    public String getSuperEntityPid() {
        return this.superEntityPid;
    }

    public void setSuperEntityPid(String value) {
        this.superEntityPid = value;
    }


    public String getSuperEntityRegCode() {
        return this.superEntityRegCode;
    }

    public void setSuperEntityRegCode(String value) {
        this.superEntityRegCode = value;
    }


    public String getSuperEntityType() {
        return this.superEntityType;
    }

    public void setSuperEntityType(String value) {
        this.superEntityType = value;
    }


    public String getSuperEntityName() {
        return this.superEntityName;
    }

    public void setSuperEntityName(String value) {
        this.superEntityName = value;
    }


    public String getRelationId() {
        return this.relationId;
    }

    public void setRelationId(String value) {
        this.relationId = value;
    }


    public String getRelationDesc() {
        return this.relationDesc;
    }

    public void setRelationDesc(String value) {
        this.relationDesc = value;
    }


    public String getRelationInfo() {
        return this.relationInfo;
    }

    public void setRelationInfo(String value) {
        this.relationInfo = value;
    }


    public String getIsShareHolding() {
        return this.isShareHolding;
    }

    public void setIsShareHolding(String value) {
        this.isShareHolding = value;
    }


    public Long getHoldingPercent() {
        return this.holdingPercent;
    }

    public void setHoldingPercent(Long value) {
        this.holdingPercent = value;
    }


    public String getOrignalRegcode() {
        return this.orignalRegcode;
    }

    public void setOrignalRegcode(String value) {
        this.orignalRegcode = value;
    }


    public String getOrignalOrgName() {
        return this.orignalOrgName;
    }

    public void setOrignalOrgName(String value) {
        this.orignalOrgName = value;
    }


    public String getRelationOrignal() {
        return this.relationOrignal;
    }

    public void setRelationOrignal(String value) {
        this.relationOrignal = value;
    }


    public Integer getReportVersion() {
        return this.reportVersion;
    }

    public void setReportVersion(Integer value) {
        this.reportVersion = value;
    }


    public Integer getParentLevelPid() {
        return this.parentLevelPid;
    }

    public void setParentLevelPid(Integer value) {
        this.parentLevelPid = value;
    }


    public String getLevel() {
        return this.level;
    }

    public void setLevel(String value) {
        this.level = value;
    }


    public String getValidStatus() {
        return this.validStatus;
    }

    public void setValidStatus(String value) {
        this.validStatus = value;
    }


    public String getIsReported() {
        return this.isReported;
    }

    public void setIsReported(String value) {
        this.isReported = value;
    }


    public Date getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(Date value) {
        this.activeTime = value;
    }


    public Date getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(Date value) {
        this.invalidTime = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("incidenceRelation[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("entityPid = ").append(entityPid).append(", \n")
                .append("entityRegCode = ").append(entityRegCode).append(", \n")
                .append("entityType = ").append(entityType).append(", \n")
                .append("entityName = ").append(entityName).append(", \n")
                .append("superEntityPid = ").append(superEntityPid).append(", \n")
                .append("superEntityRegCode = ").append(superEntityRegCode).append(", \n")
                .append("superEntityType = ").append(superEntityType).append(", \n")
                .append("superEntityName = ").append(superEntityName).append(", \n")
                .append("relationId = ").append(relationId).append(", \n")
                .append("relationDesc = ").append(relationDesc).append(", \n")
                .append("relationInfo = ").append(relationInfo).append(", \n")
                .append("isShareHolding = ").append(isShareHolding).append(", \n")
                .append("holdingPercent = ").append(holdingPercent).append(", \n")
                .append("orignalRegcode = ").append(orignalRegcode).append(", \n")
                .append("orignalOrgName = ").append(orignalOrgName).append(", \n")
                .append("relationOrignal = ").append(relationOrignal).append(", \n")
                .append("reportVersion = ").append(reportVersion).append(", \n")
                .append("parentLevelPid = ").append(parentLevelPid).append(", \n")
                .append("level = ").append(level).append(", \n")
                .append("validStatus = ").append(validStatus).append(", \n")
                .append("isReported = ").append(isReported).append(", \n")
                .append("activeTime = ").append(activeTime).append(", \n")
                .append("invalidTime = ").append(invalidTime).append(", \n")
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
        if (obj instanceof IncidenceRelation == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        IncidenceRelation other = (IncidenceRelation) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

