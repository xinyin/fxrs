package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "east_batch")
public class EastBatch extends BasePO {

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID", nullable = false, length = 11)
	private Integer id;

	/**
	 * 文件名称
	 */
	@Column(name = "FILE_NAME", nullable = true, length = 100)
	private String fileName;

	/**
	 * 文件类型
	 */
	@Column(name = "FILE_TYPE", nullable = true, length = 100)
	private String fileType;

	/**
	 * 确认状态
	 */
	@Column(name = "ACK_STATUS", nullable = true, length = 100)
	private String ackStatus;

	/**
	 * 原始数据批次号
	 */
	@Column(name = "BATCH_NO", nullable = true, length = 20)
	private String batchNo;

	/**
	 * 数据归属起始日期
	 */
	@Column(name = "DATA_START_DATE", nullable = true, length = 8)
	private String dataStartDate;

	/**
	 * 数据归属截止日期
	 */
	@Column(name = "DATA_END_DATE", nullable = true, length = 8)
	private String dataEndDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getAckStatus() {
		return ackStatus;
	}

	public void setAckStatus(String ackStatus) {
		this.ackStatus = ackStatus;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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

	@Override
	public String toString() {
		return "EastBatch{" +
				"id=" + id +
				", fileName='" + fileName + '\'' +
				", fileType='" + fileType + '\'' +
				", ackStatus='" + ackStatus + '\'' +
				", batchNo='" + batchNo + '\'' +
				", dataStartDate='" + dataStartDate + '\'' +
				", dataEndDate='" + dataEndDate + '\'' +
				'}';
	}
}
