/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月06日 16:15:35
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
@Table(name = "bus_risk_ratio")
public class RiskRatio extends BasePO {

	public static final String TABLE_ALIAS = "RiskRatio";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_CREATE_TIME = "上传时间";
	public static final String ALIAS_WARNING_VALUE = "预警比例";
	public static final String ALIAS_MARKED_WORDS = "预警提示语";


	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "create_time", nullable = false, length = 19)
	@Temporal(TemporalType.DATE)
	private Date createTimes;

	@Column(name = "warning_value", nullable = true, length = 30)
	private String warningValue;

	@Column(name = "marked_words", nullable = true, length = 100)
	private String markedWords;

	private String num="1";
	//columns END


	public String getNum() {
		return num;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer value) {
		this.pid = value;
	}


	public Date getCreateTimes() {
		return this.createTimes;
	}

	public void setCreateTimes(Date value) {
		this.createTimes = value;
	}


	public String getWarningValue() {
		return this.warningValue;
	}

	public void setWarningValue(String value) {
		this.warningValue = value;
	}


	public String getMarkedWords() {
		return this.markedWords;
	}

	public void setMarkedWords(String value) {
		this.markedWords = value;
	}


	public String toString() {
		return new StringBuilder("riskRatio[\n")
				.append("pid = ").append(pid).append(", \n")
				.append("createTimes = ").append(createTimes).append(", \n")
				.append("warningValue = ").append(warningValue).append(", \n")
				.append("markedWords = ").append(markedWords).append(", \n")
				.append("]")
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getPid())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof RiskRatio == false) return false;
		if(this == obj) return true;
		RiskRatio other = (RiskRatio)obj;
		return new EqualsBuilder()
				.append(getPid(),other.getPid())
				.isEquals();
	}

	public void setCreateTimesString(String value){
		if (value != null){
			setCreateTimes(DateUtils.parseDate(value));
		}
	}

	public String getCreateTimesString(){
		if (Optional.ofNullable(createTimes).isPresent()) {
			return DateUtils.formatDate(createTimes);
		}
		return "";
	}
}

