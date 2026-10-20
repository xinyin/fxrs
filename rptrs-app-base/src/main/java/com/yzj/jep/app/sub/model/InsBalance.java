/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月14日 17:01:19
 *
 */

package com.yzj.jep.app.sub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sub_ins_balance")
public class InsBalance extends BasePO {
	
	@Id
	@Column(name = "id", nullable = false, length = 10)
	private Integer id;

	@Column(name = "pid", nullable = true, length = 10)
	private Integer pid;

	@Column(name = "REL_ID", nullable = true, length = 10)
	private Integer relId;

	@Column(name = "REL_TYPE", nullable = true, length = 12)
	private String relType;

	@Column(name = "REL_NAME", nullable = true, length = 20)
	private String relName;

	@Column(name = "REL_CODE", nullable = true, length = 20)
	private String relCode;

	@Column(name = "REL_NUMBER", nullable = true, length = 100)
	private String relNumber;

	@Column(name = "TRADE_ORG", nullable = true, length = 100)
	private String tradeOrg;

	@Column(name = "TRAN_TYPE", nullable = true, length = 50)
	private String tranType;

	@Column(name = "BUS_TYPE", nullable = true, length = 50)
	private String busType;

	@Column(name = "contract_signing_date", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date contractSigningDate;

	@Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
	private BigDecimal tradeAmount;

	@Column(name = "is_extract", nullable = true,length = 6)
	private String isExtract;
	//columns END


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getRelId() {
		return relId;
	}

	public void setRelId(Integer relId) {
		this.relId = relId;
	}

	public String getRelType() {
		return relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getRelCode() {
		return relCode;
	}

	public void setRelCode(String relCode) {
		this.relCode = relCode;
	}

	public String getRelNumber() {
		return relNumber;
	}

	public void setRelNumber(String relNumber) {
		this.relNumber = relNumber;
	}

	public String getTradeOrg() {
		return tradeOrg;
	}

	public void setTradeOrg(String tradeOrg) {
		this.tradeOrg = tradeOrg;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public Date getContractSigningDate() {
		return contractSigningDate;
	}

	public void setContractSigningDate(Date contractSigningDate) {
		this.contractSigningDate = contractSigningDate;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getIsExtract() {
		return isExtract;
	}

	public void setIsExtract(String isExtract) {
		this.isExtract = isExtract;
	}

	@Override
	public String toString() {
		return "InsBalance{" +
				"id=" + id +
				", pid=" + pid +
				", relId=" + relId +
				", relType='" + relType + '\'' +
				", relName='" + relName + '\'' +
				", relCode='" + relCode + '\'' +
				", relNumber='" + relNumber + '\'' +
				", tradeOrg='" + tradeOrg + '\'' +
				", tranType='" + tranType + '\'' +
				", busType='" + busType + '\'' +
				", contractSigningDate=" + contractSigningDate +
				", tradeAmount=" + tradeAmount +
				", isExtract='" + isExtract + '\'' +
				'}';
	}
}

