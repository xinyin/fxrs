/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年06月28日 16:09:20
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.commons.DateUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "bus_msg_deploy")
public class MsgDeploy extends BasePO{
	
	public static final String TABLE_ALIAS = "MsgDeploy";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_BUS_NAME = "通知标题";
	public static final String ALIAS_BUS_WAY = "通知方式";
	public static final String ALIAS_BUS_MESSAGE = "通知模板";
	public static final String ALIAS_RELEASE_TYPE = "发布状态";
	public static final String ALIAS_TOP_TYPE = "置顶状态";
	public static final String ALIAS_RELEASE_TIME = "发布时间";
	public static final String ALIAS_TOP_TIME = "置顶时间";
	public static final String ALIAS_IMG1 = "图片1";
	public static final String ALIAS_IMG2 = "图片2";
	public static final String ALIAS_IMG3 = "图片3";
	public static final String ALIAS_IMG4 = "图片4";
	public static final String ALIAS_IMG5 = "图片5";
	public static final String ALIAS_importantMsg="是否为重大信息";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "PID", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "BUS_NAME", nullable = false, length = 20)
	private String busName;

	@Column(name = "BUS_WAY", nullable = false, length = 10)
	private String busWay;

	@Column(name = "BUS_MESSAGE", nullable = false, length = 1000)
	private String busMessage;

	@Column(name = "release_type", nullable = true, length = 10)
	private String releaseType;

	@Column(name = "top_type", nullable = true, length = 10)
	private String topType;

	@Column(name = "release_time", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date releaseTime;

	@Column(name = "top_time", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date topTime;

	@Column(name = "img1", nullable = true, length = 50)
	private String img1;

	@Column(name = "img2", nullable = true, length = 50)
	private String img2;

	@Column(name = "img3", nullable = true, length = 50)
	private String img3;

	@Column(name = "img4", nullable = true, length = 50)
	private String img4;

	@Column(name = "img5", nullable = true, length = 50)
	private String img5;
	@Column(name="important_Msg")
	private String importantMsg;

	//columns END


	public String getImportantMsg() {
		return importantMsg;
	}

	public void setImportantMsg(String importantMsg) {
		this.importantMsg = importantMsg;
	}

	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public String getBusName() {
		return this.busName;
	}
	
	public void setBusName(String value) {
		this.busName = value;
	}
	

	public String getBusWay() {
		return this.busWay;
	}
	
	public void setBusWay(String value) {
		this.busWay = value;
	}
	

	public String getBusMessage() {
		return this.busMessage;
	}
	
	public void setBusMessage(String value) {
		this.busMessage = value;
	}
	

	public String getReleaseType() {
		return this.releaseType;
	}
	
	public void setReleaseType(String value) {
		this.releaseType = value;
	}
	

	public String getTopType() {
		return this.topType;
	}
	
	public void setTopType(String value) {
		this.topType = value;
	}
	

	public Date getReleaseTime() {
		return this.releaseTime;
	}
	
	public void setReleaseTime(Date value) {
		this.releaseTime = value;
	}
	

	public Date getTopTime() {
		return this.topTime;
	}
	
	public void setTopTime(Date value) {
		this.topTime = value;
	}
	

	public String getImg1() {
		return this.img1;
	}
	
	public void setImg1(String value) {
		this.img1 = value;
	}
	

	public String getImg2() {
		return this.img2;
	}
	
	public void setImg2(String value) {
		this.img2 = value;
	}
	

	public String getImg3() {
		return this.img3;
	}
	
	public void setImg3(String value) {
		this.img3 = value;
	}
	

	public String getImg4() {
		return this.img4;
	}
	
	public void setImg4(String value) {
		this.img4 = value;
	}
	

	public String getImg5() {
		return this.img5;
	}
	
	public void setImg5(String value) {
		this.img5 = value;
	}
	

	public String toString() {
		return new StringBuilder("msgDeploy[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("busName = ").append(busName).append(", \n")
						.append("busWay = ").append(busWay).append(", \n")
						.append("busMessage = ").append(busMessage).append(", \n")
						.append("releaseType = ").append(releaseType).append(", \n")
						.append("topType = ").append(topType).append(", \n")
						.append("releaseTime = ").append(releaseTime).append(", \n")
						.append("topTime = ").append(topTime).append(", \n")
						.append("img1 = ").append(img1).append(", \n")
						.append("img2 = ").append(img2).append(", \n")
						.append("img3 = ").append(img3).append(", \n")
						.append("img4 = ").append(img4).append(", \n")
						.append("img5 = ").append(img5).append(", \n")
						.append("]")
						.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MsgDeploy == false) return false;
		if(this == obj) return true;
		MsgDeploy other = (MsgDeploy)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}

	public void setReleaseTimeString(String value){
		if (value != null){
			setReleaseTime(DateUtils.parseDate(value));
		}
	}

	public String getReleaseTimeString(){
		if (Optional.ofNullable(releaseTime).isPresent()) {
			return DateUtils.formatDate(releaseTime);
		}
		return "";
	}
}

