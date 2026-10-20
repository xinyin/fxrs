/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月15日 14:40:20
 *
 */

package com.yzj.jep.app.sub.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;
import java.util.Date;

@Entity
@Table(name = "sub_related_relation_view")
public class RelatedRelationView extends BasePO{

	public static final String TABLE_ALIAS = "RelatedRelationView";
	public static final String ALIAS_PID = "自增长主键";
	public static final String ALIAS_RELATION_SEQ = "法人和其他关联方流水号";
	public static final String ALIAS_ENTITY_NAME = "关联方名称";
	public static final String ALIAS_ENTITY_TYPE = "关联方类型";
	public static final String ALIAS_ENTITY_UNISC_UODE = "关联方统一社会信用代码/证件号码";
	public static final String ALIAS_SUPER_ENTITY_NAME = "上级关联方名称";
	public static final String ALIAS_SUPER_ENTITY_TYPE = "上级关联方类型";
	public static final String ALIAS_SUPER_ENTITY_UNISC_CODE = "上级关联方统一社会信用代码/证件号码";
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


	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "relation_seq", nullable = false, length = 64)
	private String relationSeq;

	@Column(name = "entity_name", nullable = true, length = 10)
	private String entityName;

	@Column(name = "entity_type", nullable = true, length = 10)
	private String entityType;

	@Column(name = "entity_unisc_uode", nullable = true, length = 20)
	private String entityUniscUode;

	@Column(name = "super_entity_name", nullable = true, length = 10)
	private String superEntityName;

	@Column(name = "super_entity_type", nullable = true, length = 10)
	private String superEntityType;

	@Column(name = "super_entity_unisc_code", nullable = true, length = 20)
	private String superEntityUniscCode;

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
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date activeTime;

	@Column(name = "invalid_time", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date invalidTime;

	@Column(name = "status", nullable = true, length = 10)
	private String status;

	@Column(name = "status_comment", nullable = true, length = 255)
	private String statusComment;
	@Column(name="prot_pid")
	private Integer prodPid;
	//columns END

	// 与前一层是否有权益份额
	@Column(name = "equity_share", nullable = true, length = 10)
	private String equityShare;

	// 质押比例
	@Column(name = "pledge_ratio", nullable = true, length = 10)
	private String pledgeRatio;

	public String getPledgeRatio() {
		return pledgeRatio;
	}

	public void setPledgeRatio(String pledgeRatio) {
		this.pledgeRatio = pledgeRatio;
	}

	public String getEquityShare() {
		return equityShare;
	}

	public void setEquityShare(String equityShare) {
		this.equityShare = equityShare;
	}

	public Integer getProdPid() {
		return prodPid;
	}

	public void setProdPid(Integer prodPid) {
		this.prodPid = prodPid;
	}

	@Transient
	private String activeTimeString;

	@Transient
	private String invalidTimeString;

	public String getActiveTimeString() {
		if (activeTime != null) {
			return DateUtils.formatDate(activeTime);
		}
		return "";
	}

	public void setActiveTimeString(String activeTimeString) {
		if (activeTimeString != null) {
			setActiveTime(DateUtils.parseDateShort(activeTimeString));
		}
	}

	public String getInvalidTimeString() {
		if (invalidTime != null) {
			return DateUtils.formatDate(invalidTime);
		}
		return "";
	}

	public void setInvalidTimeString(String invalidTimeString) {
		if (invalidTimeString != null) {
			setInvalidTime(DateUtils.parseDateShort(invalidTimeString));
		}
	}


	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer value) {
		this.pid = value;
	}


	public String getRelationSeq() {
		return this.relationSeq;
	}

	public void setRelationSeq(String value) {
		this.relationSeq = value;
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


	@Override
	public String toString() {
		return "RelatedRelationView{" +
				"pid=" + pid +
				", relationSeq='" + relationSeq + '\'' +
				", entityName='" + entityName + '\'' +
				", entityType='" + entityType + '\'' +
				", entityUniscUode='" + entityUniscUode + '\'' +
				", superEntityName='" + superEntityName + '\'' +
				", superEntityType='" + superEntityType + '\'' +
				", superEntityUniscCode='" + superEntityUniscCode + '\'' +
				", relationType='" + relationType + '\'' +
				", regulator='" + regulator + '\'' +
				", isShareHolding='" + isShareHolding + '\'' +
				", holdingPercent='" + holdingPercent + '\'' +
				", relationInfo='" + relationInfo + '\'' +
				", validStatus='" + validStatus + '\'' +
				", activeTime=" + activeTime +
				", invalidTime=" + invalidTime +
				", status='" + status + '\'' +
				", statusComment='" + statusComment + '\'' +
				", prodPid=" + prodPid +
				", equityShare='" + equityShare + '\'' +
				", pledgeRatio='" + pledgeRatio + '\'' +
				", activeTimeString='" + activeTimeString + '\'' +
				", invalidTimeString='" + invalidTimeString + '\'' +
				'}';
	}

	@Override
    public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}

	@Override
    public boolean equals(Object obj) {
		if(obj instanceof RelatedRelationView == false) {return false;}
		if(this == obj) {return true;}
		RelatedRelationView other = (RelatedRelationView)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

