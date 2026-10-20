/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 15:18:56
 *
 */

package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sub_transbalance")
public class Transbalance extends BasePO{
	
	public static final String TABLE_ALIAS = "Transbalance";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_RELATED_PARTY_NAME = "relatedPartyName";
	public static final String ALIAS_RELATED_PARTY_CERT_NO = "relatedPartyCertNo";
	public static final String ALIAS_RELATED_PARTY_TYPE = "relatedPartyType";
	public static final String ALIAS_CREDIT_RISK_LIMIT = "creditRiskLimit";
	public static final String ALIAS_MAX_PERCENT_IN_NET_ASSETS = "maxPercentInNetAssets";
	public static final String ALIAS_LOANS_BALANCE = "loansBalance";
	public static final String ALIAS_SECURITIES_BALANCE = "securitiesBalance";
	public static final String ALIAS_SPVBALANCE = "spvbalance";
	public static final String ALIAS_OTHER_ON_BALANCE = "otherOnBalance";
	public static final String ALIAS_LIABILITIES_BALANCE = "liabilitiesBalance";
	public static final String ALIAS_NBEBALANCE = "nbebalance";
	public static final String ALIAS_OTHER_OFF_BALANCE = "otherOffBalance";
	public static final String ALIAS_DEPOSITS = "deposits";
	public static final String ALIAS_CREDIT_BALANCE = "creditBalance";
	public static final String ALIAS_PERCENT_IN_NET_ASSETS = "percentInNetAssets";
	public static final String ALIAS_ASSET_TRANSFER_BALANCE = "assetTransferBalance";
	public static final String ALIAS_SERVICE_BALANCE = "serviceBalance";
	public static final String ALIAS_OTHER_BALANCE = "otherBalance";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_STATUS_COMMENT = "statusComment";
	public static final String ALIAS_MODIFIED_TIME = "modifiedTime";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "related_party_name", nullable = false, length = 100)
	private String relatedPartyName;

	@Column(name = "related_party_cert_no", nullable = false, length = 50)
	private String relatedPartyCertNo;

	@Column(name = "related_party_type", nullable = false, length = 2)
	private String relatedPartyType;

	@Column(name = "credit_risk_limit", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal creditRiskLimit;

	@Column(name = "max_percentIn_net_assets", nullable = false, length = 50)
	private java.math.BigDecimal maxPercentInNetAssets;

	@Column(name = "loans_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal loansBalance;

	@Column(name = "securities_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal securitiesBalance;

	@Column(name = "SPV_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal spvbalance;

	@Column(name = "other_on_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal otherOnBalance;

	@Column(name = "liabilities_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal liabilitiesBalance;

	@Column(name = "NBE_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal nbebalance;

	@Column(name = "other_off_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal otherOffBalance;

	@Column(name = "deposits", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal deposits;

	@Column(name = "credit_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal creditBalance;

	@Column(name = "percent_in_netAssets", nullable = true, length = 65)
	private java.math.BigDecimal percentInNetAssets;

	@Column(name = "asset_transfer_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal assetTransferBalance;

	@Column(name = "service_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal serviceBalance;

	@Column(name = "other_balance", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal otherBalance;

	@Column(name = "status", nullable = false, length = 10)
	private String status;

	@Column(name = "status_comment", nullable = true, length = 255)
	private String statusComment;

	@Column(name = "modified_time_", nullable = false, length = 19)
	@Temporal(TemporalType.DATE)
	private Date modifiedTime;
	//columns END  modifiedTime  MODIFIED_TIME


	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public String getRelatedPartyName() {
		return this.relatedPartyName;
	}
	
	public void setRelatedPartyName(String value) {
		this.relatedPartyName = value;
	}
	

	public String getRelatedPartyCertNo() {
		return this.relatedPartyCertNo;
	}
	
	public void setRelatedPartyCertNo(String value) {
		this.relatedPartyCertNo = value;
	}
	

	public String getRelatedPartyType() {
		return this.relatedPartyType;
	}
	
	public void setRelatedPartyType(String value) {
		this.relatedPartyType = value;
	}
	

	public java.math.BigDecimal getCreditRiskLimit() {
		return this.creditRiskLimit;
	}
	
	public void setCreditRiskLimit(java.math.BigDecimal value) {
		this.creditRiskLimit = value;
	}
	

	public java.math.BigDecimal getMaxPercentInNetAssets() {
		return this.maxPercentInNetAssets;
	}
	
	public void setMaxPercentInNetAssets(java.math.BigDecimal value) {
		this.maxPercentInNetAssets = value;
	}
	

	public java.math.BigDecimal getLoansBalance() {
		return this.loansBalance;
	}
	
	public void setLoansBalance(java.math.BigDecimal value) {
		this.loansBalance = value;
	}
	

	public java.math.BigDecimal getSecuritiesBalance() {
		return this.securitiesBalance;
	}
	
	public void setSecuritiesBalance(java.math.BigDecimal value) {
		this.securitiesBalance = value;
	}
	

	public java.math.BigDecimal getSpvbalance() {
		return this.spvbalance;
	}
	
	public void setSpvbalance(java.math.BigDecimal value) {
		this.spvbalance = value;
	}
	

	public java.math.BigDecimal getOtherOnBalance() {
		return this.otherOnBalance;
	}
	
	public void setOtherOnBalance(java.math.BigDecimal value) {
		this.otherOnBalance = value;
	}
	

	public java.math.BigDecimal getLiabilitiesBalance() {
		return this.liabilitiesBalance;
	}
	
	public void setLiabilitiesBalance(java.math.BigDecimal value) {
		this.liabilitiesBalance = value;
	}
	

	public java.math.BigDecimal getNbebalance() {
		return this.nbebalance;
	}
	
	public void setNbebalance(java.math.BigDecimal value) {
		this.nbebalance = value;
	}
	

	public java.math.BigDecimal getOtherOffBalance() {
		return this.otherOffBalance;
	}
	
	public void setOtherOffBalance(java.math.BigDecimal value) {
		this.otherOffBalance = value;
	}
	

	public java.math.BigDecimal getDeposits() {
		return this.deposits;
	}
	
	public void setDeposits(java.math.BigDecimal value) {
		this.deposits = value;
	}
	

	public java.math.BigDecimal getCreditBalance() {
		return this.creditBalance;
	}
	
	public void setCreditBalance(java.math.BigDecimal value) {
		this.creditBalance = value;
	}
	

	public java.math.BigDecimal getPercentInNetAssets() {
		return this.percentInNetAssets;
	}
	
	public void setPercentInNetAssets(java.math.BigDecimal value) {
		this.percentInNetAssets = value;
	}
	

	public java.math.BigDecimal getAssetTransferBalance() {
		return this.assetTransferBalance;
	}
	
	public void setAssetTransferBalance(java.math.BigDecimal value) {
		this.assetTransferBalance = value;
	}
	

	public java.math.BigDecimal getServiceBalance() {
		return this.serviceBalance;
	}
	
	public void setServiceBalance(java.math.BigDecimal value) {
		this.serviceBalance = value;
	}
	

	public java.math.BigDecimal getOtherBalance() {
		return this.otherBalance;
	}
	
	public void setOtherBalance(java.math.BigDecimal value) {
		this.otherBalance = value;
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
    public Date getModifiedTime() {
		return modifiedTime;
	}

	@Override
    public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	@Override
    public String toString() {
		return new StringBuilder("transbalance[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("relatedPartyName = ").append(relatedPartyName).append(", \n")
						.append("relatedPartyCertNo = ").append(relatedPartyCertNo).append(", \n")
						.append("relatedPartyType = ").append(relatedPartyType).append(", \n")
						.append("creditRiskLimit = ").append(creditRiskLimit).append(", \n")
						.append("maxPercentInNetAssets = ").append(maxPercentInNetAssets).append(", \n")
						.append("loansBalance = ").append(loansBalance).append(", \n")
						.append("securitiesBalance = ").append(securitiesBalance).append(", \n")
						.append("spvbalance = ").append(spvbalance).append(", \n")
						.append("otherOnBalance = ").append(otherOnBalance).append(", \n")
						.append("liabilitiesBalance = ").append(liabilitiesBalance).append(", \n")
						.append("nbebalance = ").append(nbebalance).append(", \n")
						.append("otherOffBalance = ").append(otherOffBalance).append(", \n")
						.append("deposits = ").append(deposits).append(", \n")
						.append("creditBalance = ").append(creditBalance).append(", \n")
						.append("percentInNetAssets = ").append(percentInNetAssets).append(", \n")
						.append("assetTransferBalance = ").append(assetTransferBalance).append(", \n")
						.append("serviceBalance = ").append(serviceBalance).append(", \n")
						.append("otherBalance = ").append(otherBalance).append(", \n")
						.append("status = ").append(status).append(", \n")
						.append("statusComment = ").append(statusComment).append(", \n")
				        .append("modifiedTime=").append(modifiedTime).append(",\n")
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
		if(obj instanceof Transbalance == false) {return false;}
		if(this == obj) {return true;}
		Transbalance other = (Transbalance)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

