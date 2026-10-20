/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:52
 *
 */

package com.yzj.jep.app.sub.model;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sub_related_relation")
public class RelatedRelation extends BasePO{

	public static final String TABLE_ALIAS = "RelatedRelation";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_SR_PID = "源pid";
	public static final String ALIAS_SUPER_ENTITY_NAME = "上级关联方名称";
	public static final String ALIAS_SUPER_ENTITY_TYPE = "上级关联方类型";
	public static final String ALIAS_SUPER_ENTITY_UNISC_CODE = "上级关联方统一社会信用代码/证件号码";
	public static final String ALIAS_ENTITY_NAME = "关联方名称";
	public static final String ALIAS_ENTITY_TYPE = "关联方类型";
	public static final String ALIAS_ENTITY_UNISC_UODE = "关联方统一社会信用代码/证件号码";
	public static final String ALIAS_RELATION_TYPE = "与前一层的关联关系类型";
	public static final String ALIAS_REGULATOR = "统计口径";
	public static final String ALIAS_IS_SHARE_HOLDING = "是否持股";
	public static final String ALIAS_HOLDING_PERCENT = "持股比例";
	public static final String ALIAS_RELATION_INFO = "关联关系说明";
	public static final String ALIAS_VALID_STATUS = "该关系是否仍然有效";
	public static final String ALIAS_ACTIVE_TIME = "生效日期";
	public static final String ALIAS_INVALID_TIME = "失效日期";
	public static final String ALIAS_STATUS = "前置流程状态";
	public static final String ALIAS_STATUS_COMMENT = "前置流程状态说明";
	public static final String ALIAS_EQUITY_SHARE = "与前一层是否有权益份额 是，对前一层持有 是，被前一层持有 否";
	public static final String ALIAS_PLEDGE_RATIO = "质押股权比例";

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "sr_pid", nullable = true, length = 20)
	private String srPid;

	@Column(name = "super_entity_name", nullable = true, length = 50)
	private String superEntityName;

	@Column(name = "super_entity_type", nullable = true, length = 10)
	private String superEntityType;

	@Column(name = "super_entity_unisc_code", nullable = true, length = 20)
	private String superEntityUniscCode;

	@Column(name = "entity_name", nullable = true, length = 50)
	private String entityName;

	@Column(name = "entity_type", nullable = true, length = 10)
	private String entityType;

	@Column(name = "entity_unisc_uode", nullable = true, length = 20)
	private String entityUniscUode;

	@Column(name = "relation_type", nullable = true, length = 10)
	private String relationType;

	@Column(name = "regulator", nullable = true, length = 16)
	private String regulator;

	@Column(name = "is_share_holding", nullable = true, length = 2)
	private String isShareHolding;

	@Column(name = "holding_percent", nullable = true, length = 10)
	private String holdingPercent;

	@Column(name = "relation_info", nullable = true, length = 255)
	private String relationInfo;

	@Column(name = "valid_status", nullable = true, length = 2)
	private String validStatus;

	@Column(name = "active_time", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date activeTime;

	@Column(name = "invalid_time", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date invalidTime;

	@Column(name = "status", nullable = true, length = 10)
	private String status;

	@Column(name = "status_comment", nullable = true, length = 255)
	private String statusComment;

	@Column(name = "equity_share", nullable = true, length = 10)
	private String equityShare;

	@Column(name = "pledge_ratio", nullable = true, length = 10)
	private String pledgeRatio;

	@Column(name = "is_extract", nullable = true, length = 6)
	private String isExtract;

	//columns END

	public String getEquityShare() {
		return equityShare;
	}

	public void setEquityShare(String equityShare) {
		this.equityShare = equityShare;
	}

	public String getPledgeRatio() {
		return pledgeRatio;
	}

	public void setPledgeRatio(String pledgeRatio) {
		this.pledgeRatio = pledgeRatio;
	}

	@Transient
	private String activeTimeString;

	@Transient
	private String invalidTimeString;

	public String getActiveTimeString() {
		if (activeTime != null) {
			return DateUtils.formatDateShort(activeTime);
		}
		return "";
	}

	public void setActiveTimeString(String activeTimeString) {
		if (activeTimeString != null) {
			setActiveTime(DateUtils.parseDate(activeTimeString));
		}
	}

	public String getInvalidTimeString() {
		if (invalidTime != null) {
			return DateUtils.formatDateShort(invalidTime);
		}
		return "";
	}

	public void setInvalidTimeString(String invalidTimeString) {
		if (invalidTimeString != null) {
			setInvalidTime(DateUtils.parseDate(invalidTimeString));
		}
	}


	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer value) {
		this.pid = value;
	}


	public String getSrPid() {
		return this.srPid;
	}

	public void setSrPid(String value) {
		this.srPid = value;
	}


	public String getSuperEntityName() {
		return this.superEntityName;
	}

	public void setSuperEntityName(String value) {
		this.superEntityName = value;
	}


	public String getSuperEntityType() {
		return this.superEntityType;
	}

	public void setSuperEntityType(String value) {
		this.superEntityType = value;
	}


	public String getSuperEntityUniscCode() {
		return this.superEntityUniscCode;
	}

	public void setSuperEntityUniscCode(String value) {
		this.superEntityUniscCode = value;
	}


	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String value) {
		this.entityName = value;
	}


	public String getEntityType() {
		return this.entityType;
	}

	public void setEntityType(String value) {
		this.entityType = value;
	}


	public String getEntityUniscUode() {
		return this.entityUniscUode;
	}

	public void setEntityUniscUode(String value) {
		this.entityUniscUode = value;
	}


	public String getRelationType() {
		return this.relationType;
	}

	public void setRelationType(String value) {
		this.relationType = value;
	}


	public String getRegulator() {
		return this.regulator;
	}

	public void setRegulator(String value) {
		this.regulator = value;
	}


	public String getIsShareHolding() {
		return this.isShareHolding;
	}

	public void setIsShareHolding(String value) {
		this.isShareHolding = value;
	}


	public String getHoldingPercent() {
		return this.holdingPercent;
	}

	public void setHoldingPercent(String value) {
		this.holdingPercent = value;
	}


	public String getRelationInfo() {
		return this.relationInfo;
	}

	public void setRelationInfo(String value) {
		this.relationInfo = value;
	}


	public String getValidStatus() {
		return this.validStatus;
	}

	public void setValidStatus(String value) {
		this.validStatus = value;
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


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}


	public String getStatusComment() {
		return this.statusComment;
	}

	public void setStatusComment(String value) {
		this.statusComment = value;
	}

	public String getIsExtract() {
		return isExtract;
	}

	public void setIsExtract(String isExtract) {
		this.isExtract = isExtract;
	}

	public String toString() {
		return new StringBuilder("relatedRelation[\n")
				.append("pid = ").append(pid).append(", \n")
				.append("srPid = ").append(srPid).append(", \n")
				.append("superEntityName = ").append(superEntityName).append(", \n")
				.append("superEntityType = ").append(superEntityType).append(", \n")
				.append("superEntityUniscCode = ").append(superEntityUniscCode).append(", \n")
				.append("entityName = ").append(entityName).append(", \n")
				.append("entityType = ").append(entityType).append(", \n")
				.append("entityUniscUode = ").append(entityUniscUode).append(", \n")
				.append("relationType = ").append(relationType).append(", \n")
				.append("regulator = ").append(regulator).append(", \n")
				.append("isShareHolding = ").append(isShareHolding).append(", \n")
				.append("holdingPercent = ").append(holdingPercent).append(", \n")
				.append("relationInfo = ").append(relationInfo).append(", \n")
				.append("validStatus = ").append(validStatus).append(", \n")
				.append("activeTime = ").append(activeTime).append(", \n")
				.append("invalidTime = ").append(invalidTime).append(", \n")
				.append("status = ").append(status).append(", \n")
				.append("statusComment = ").append(statusComment).append(", \n")
				.append("]")
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getPid())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof RelatedRelation == false) return false;
		if(this == obj) return true;
		RelatedRelation other = (RelatedRelation)obj;
		return new EqualsBuilder()
				.append(getPid(),other.getPid())
				.isEquals();
	}
}

