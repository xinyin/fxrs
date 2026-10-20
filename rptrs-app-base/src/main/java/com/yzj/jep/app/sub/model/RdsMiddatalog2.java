/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月13日 15:41:26
 *
 */

package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;
import com.yzj.jep.orm.entity.PO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rds_middatalog_2")
public class RdsMiddatalog2 extends BasePO {

	public static final String TABLE_ALIAS = "RdsMiddatalog2";

	@Id
	@Column(name = "ID", nullable = false, length = 10)
	private Integer id;

	@Column(name = "LogId", nullable = false, length = 20)
	private String logId;

	@Column(name = "DataStartDate", nullable = false, length = 8)
	private String dataStartDate;

	@Column(name = "DataEndDate", nullable = false, length = 8)
	private String dataEndDate;

	@Column(name = "DataSource", nullable = false, length = 2)
	private String dataSource;

	@Column(name = "Datasrcdes", nullable = true, length = 2000)
	private String datasrcdes;

	@Column(name = "BatchNo", nullable = false, length = 20)
	private String batchNo;

	@Column(name = "PrepareSTime")
	@Temporal(TemporalType.DATE)
	private Date prepareSTime;

	@Column(name = "PrepareETime")
	@Temporal(TemporalType.DATE)
	private Date prepareETime;

	@Column(name = "PrepareStatus", nullable = true, length = 1)
	private String prepareStatus;

	@Column(name = "PrepareDesc", nullable = true, length = 2000)
	private String prepareDesc;

	@Column(name = "EtlSTime")
	@Temporal(TemporalType.DATE)
	private Date etlSTime;

	@Column(name = "EtlETime")
	@Temporal(TemporalType.DATE)
	private Date etlETime;

	@Column(name = "EtlStatus", nullable = true, length = 1)
	private String etlStatus;

	@Column(name = "EtlDesc", nullable = true, length = 2000)
	private String etlDesc;

	@Column(name = "ProcessSTime")
	@Temporal(TemporalType.DATE)
	private Date processSTime;

	@Column(name = "ProcessETime")
	@Temporal(TemporalType.DATE)
	private Date processETime;

	@Column(name = "ProcessStatus", nullable = true, length = 1)
	private String processStatus;

	@Column(name = "ProcessDesc", nullable = true, length = 2000)
	private String processDesc;

	@Column(name = "DealStatus", nullable = true, length = 2)
	private String dealStatus;

	@Column(name = "Remark", nullable = true, length = 2000)
	private String remark;

	@Column(name = "makeuser", nullable = true, length = 20)
	private String makeuser;

	@Column(name = "modifuser", nullable = true, length = 20)
	private String modifuser;

	@Column(name = "maketime")
	@Temporal(TemporalType.DATE)
	private Date maketime;

	@Column(name = "modiftime")
	@Temporal(TemporalType.DATE)
	private Date modiftime;



	//columns END
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getDataStartDate() {
		return dataStartDate;
	}

	public void setDataStartDate(String dataStartDate) {
		this.dataStartDate = dataStartDate;
	}

	public String getDataEndDate() {
		return dataEndDate;
	}

	public void setDataEndDate(String dataEndDate) {
		this.dataEndDate = dataEndDate;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getDatasrcdes() {
		return datasrcdes;
	}

	public void setDatasrcdes(String datasrcdes) {
		this.datasrcdes = datasrcdes;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getPrepareSTime() {
		return prepareSTime;
	}

	public void setPrepareSTime(Date prepareSTime) {
		this.prepareSTime = prepareSTime;
	}

	public Date getPrepareETime() {
		return prepareETime;
	}

	public void setPrepareETime(Date prepareETime) {
		this.prepareETime = prepareETime;
	}

	public String getPrepareStatus() {
		return prepareStatus;
	}

	public void setPrepareStatus(String prepareStatus) {
		this.prepareStatus = prepareStatus;
	}

	public String getPrepareDesc() {
		return prepareDesc;
	}

	public void setPrepareDesc(String prepareDesc) {
		this.prepareDesc = prepareDesc;
	}

	public Date getEtlSTime() {
		return etlSTime;
	}

	public void setEtlSTime(Date etlSTime) {
		this.etlSTime = etlSTime;
	}

	public Date getEtlETime() {
		return etlETime;
	}

	public void setEtlETime(Date etlETime) {
		this.etlETime = etlETime;
	}

	public String getEtlStatus() {
		return etlStatus;
	}

	public void setEtlStatus(String etlStatus) {
		this.etlStatus = etlStatus;
	}

	public String getEtlDesc() {
		return etlDesc;
	}

	public void setEtlDesc(String etlDesc) {
		this.etlDesc = etlDesc;
	}

	public Date getProcessSTime() {
		return processSTime;
	}

	public void setProcessSTime(Date processSTime) {
		this.processSTime = processSTime;
	}

	public Date getProcessETime() {
		return processETime;
	}

	public void setProcessETime(Date processETime) {
		this.processETime = processETime;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMakeuser() {
		return makeuser;
	}

	public void setMakeuser(String makeuser) {
		this.makeuser = makeuser;
	}

	public String getModifuser() {
		return modifuser;
	}

	public void setModifuser(String modifuser) {
		this.modifuser = modifuser;
	}

	public Date getMaketime() {
		return maketime;
	}

	public void setMaketime(Date maketime) {
		this.maketime = maketime;
	}

	public Date getModiftime() {
		return modiftime;
	}

	public void setModiftime(Date modiftime) {
		this.modiftime = modiftime;
	}
}

