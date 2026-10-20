/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年06月29日 17:31:09
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;
import java.util.Optional;


@Entity
@Table(name = "bus_knowledge_base")
public class KnowledgeBase extends BasePO {
	
	public static final String TABLE_ALIAS = "KnowledgeBase";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_FILE_NAME = "文件名称";
	public static final String ALIAS_FILE_TYPE = "文件类型";
	public static final String ALIAS_FILE_SIZE = "文件大小";
	public static final String ALIAS_DEPARTMENT = "上传部门";
	public static final String ALIAS_UPLOAD_TIME = "上传时间";
	public static final String ALIAS_SUBMODULE_TYPE = "子模块类型";
	public static final String ALIAS_PATH_PATH = "文件路径";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "file_name", nullable = true, length = 100)
	private String fileName;

	@Column(name = "file_type", nullable = true, length = 30)
	private String fileType;

	@Column(name = "file_size", nullable = true, length = 30)
	private String fileSize;

	@Column(name = "department", nullable = true, length = 30)
	private String department;

	@Column(name = "upload_time", nullable = false, length = 19)
	@Temporal(TemporalType.DATE)
	private Date uploadTime;

	@Column(name = "submodule_type", nullable = true, length = 50)
	private String submoduleType;

	@Column(name = "file_path", nullable = true, length = 50)
	private String filePath;
	//columns END


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(String value) {
		this.fileName = value;
	}
	

	public String getFileType() {
		return this.fileType;
	}
	
	public void setFileType(String value) {
		this.fileType = value;
	}
	

	public String getFileSize() {
		return this.fileSize;
	}
	
	public void setFileSize(String value) {
		this.fileSize = value;
	}
	

	public String getDepartment() {
		return this.department;
	}
	
	public void setDepartment(String value) {
		this.department = value;
	}
	

	public Date getUploadTime() {
		return this.uploadTime;
	}
	
	public void setUploadTime(Date value) {
		this.uploadTime = value;
	}
	

	public String getSubmoduleType() {
		return this.submoduleType;
	}
	
	public void setSubmoduleType(String value) {
		this.submoduleType = value;
	}
	

	public String toString() {
		return new StringBuilder("knowledgeBase[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("fileName = ").append(fileName).append(", \n")
						.append("fileType = ").append(fileType).append(", \n")
						.append("fileSize = ").append(fileSize).append(", \n")
						.append("department = ").append(department).append(", \n")
						.append("uploadTime = ").append(uploadTime).append(", \n")
						.append("submoduleType = ").append(submoduleType).append(", \n")
						.append("filePath=").append(filePath).append(", \n")
						.append("]")
						.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof KnowledgeBase == false) return false;
		if(this == obj) return true;
		KnowledgeBase other = (KnowledgeBase)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
	public void setUploadTimeString(String value){
		if (value != null){
			setUploadTime(DateUtils.parseDate(value));
		}
	}

	public String getUploadTimeString(){
		if (Optional.ofNullable(uploadTime).isPresent()) {
			return DateUtils.formatDate(uploadTime);
		}
		return "";
	}
}

