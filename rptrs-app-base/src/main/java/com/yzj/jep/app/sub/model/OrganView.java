/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月15日 14:40:18
 *
 */

package com.yzj.jep.app.sub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

/**
 * @author
 */
@Entity
@Table(name = "sub_organ_view")
public class OrganView extends BasePO{

	public static final String TABLE_ALIAS = "OrganView";
	public static final String ALIAS_PID = "自增主键";
	public static final String ALIAS_ORGAN_SEQ = "法人和其他关联方流水号";
	public static final String ALIAS_NAME = "法人名称";
	public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内(外)";
	public static final String ALIAS_LEGAL_IDENTIFY_NUMBER = "社会统一信用代码";
	public static final String ALIAS_IDENTIFY_TYPE = "证件类型";


	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "organ_seq", nullable = false, length = 64)
	private String organSeq;

	@Column(name = "name_", nullable = true, length = 255)
	private String name;

	@Column(name = "domestic_or_foreign", nullable = true, length = 10)
	private String domesticOrForeign;

	@Column(name = "legal_identify_number", nullable = true, length = 64)
	private String legalIdentifyNumber;

	@Column(name = "identify_type", nullable = false, length = 20)
	private String identifyType;
	//columns END
	@Column(name="prot_pid")
	private Integer protPid;

	// 经济性质或类型
	@Column(name="ORG_NATURE")
	private String orgNature;

	// 主营业务或经营范围
	@Column(name = "business_scope", nullable = true, length = 255)
	private String businessScope;

	// 法定代表人
	@Column(name = "legal_person", nullable = true, length = 100)
	private String legalPerson;

	// 注册地
	@Column(name = "reg_address", nullable = true, length = 100)
	private String regAddress;

	// 注册资本
	@Column(name = "reg_capital", nullable = true, precision = 10, scale = 0)
	private Long regCapital;

	public String getOrgNature() {
		return orgNature;
	}

	public void setOrgNature(String orgNature) {
		this.orgNature = orgNature;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public Long getRegCapital() {
		return regCapital;
	}

	public void setRegCapital(Long regCapital) {
		this.regCapital = regCapital;
	}

	public Integer getProtPid() {
		return protPid;
	}

	public void setProtPid(Integer protPid) {
		this.protPid = protPid;
	}


	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer value) {
		this.pid = value;
	}


	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public String getSubSeq() {
		return this.organSeq;
	}

	public void setSubSeq(String value) {
		this.organSeq = value;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}


	public String getDomesticOrForeign() {
		return this.domesticOrForeign;
	}

	public void setDomesticOrForeign(String value) {
		this.domesticOrForeign = value;
	}

	public String getLegalIdentifyNumber() {
		return legalIdentifyNumber;
	}

	public void setLegalIdentifyNumber(String legalIdentifyNumber) {
		this.legalIdentifyNumber = legalIdentifyNumber;
	}

	@Override
	public String toString() {
		return "OrganView{" +
				"pid=" + pid +
				", organSeq='" + organSeq + '\'' +
				", name='" + name + '\'' +
				", domesticOrForeign='" + domesticOrForeign + '\'' +
				", legalIdentifyNumber='" + legalIdentifyNumber + '\'' +
				", identifyType='" + identifyType + '\'' +
				", protPid=" + protPid +
				", orgNature='" + orgNature + '\'' +
				", businessScope='" + businessScope + '\'' +
				", legalPerson='" + legalPerson + '\'' +
				", regAddress='" + regAddress + '\'' +
				", regCapital=" + regCapital +
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
		if(obj instanceof OrganView == false) {return false;}
		if(this == obj) {return true;}
		OrganView other = (OrganView)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

