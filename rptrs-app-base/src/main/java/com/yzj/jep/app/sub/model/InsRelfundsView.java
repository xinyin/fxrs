/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:37
 *
 */

package com.yzj.jep.app.sub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yzj.jep.orm.entity.BasePO;
import io.swagger.models.auth.In;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;



@Entity
@Table(name = "sub_ins_relfunds_view")
public class InsRelfundsView extends BasePO {

	public static final String TABLE_ALIAS = "InsRelfundsView";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_REL_NUMBER = "关联方类型";
	public static final String ALIAS_REL_NAME = "关联方名称";
	public static final String ALIAS_CORP_INVEST_FUND = "资金运用投资余额";
	public static final String ALIAS_CORP_TOTAL_FUND = "关联方总资产投资";
	public static final String ALIAS_RELFUNDS_SEQ = "关联流水号";
	public static final String PORT_PID="报送文件对应pid";


	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "PID", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "REL_NUMBER", nullable = true, length = 12)
	private String relNumber;

	@Column(name = "REL_NAME", nullable = true, length = 20)
	private String relName;

	@Column(name = "CorpInvestFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal corpInvestFund;

	@Column(name = "CorpTotalFund", nullable = true, length = 100)
	private String corpTotalFund;

	@Column(name = "relfunds_seq", nullable = true, length = 64)
	private String relfundsSeq;

	@Column(name="port_pid")
	private Integer portPid;
	//columns END

	@Column(name="rel_type" , nullable = true )
	private String relType; //关联方类型

	public String getRelType() {
		return relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public Integer getPortPid() {
		return portPid;
	}

	public void setPortPid(Integer portPid) {
		this.portPid = portPid;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer value) {
		this.pid = value;
	}


	public String getRelNumber() {
		return this.relNumber;
	}

	public void setRelNumber(String value) {
		this.relNumber = value;
	}


	public String getRelName() {
		return this.relName;
	}

	public void setRelName(String value) {
		this.relName = value;
	}


	public java.math.BigDecimal getCorpInvestFund() {
		return this.corpInvestFund;
	}

	public void setCorpInvestFund(java.math.BigDecimal value) {
		this.corpInvestFund = value;
	}


	public String getCorpTotalFund() {
		return this.corpTotalFund;
	}

	public void setCorpTotalFund(String value) {
		this.corpTotalFund = value;
	}


	public String getRelfundsSeq() {
		return this.relfundsSeq;
	}

	public void setRelfundsSeq(String value) {
		this.relfundsSeq = value;
	}


	@Override
	public String toString() {
		return "InsRelfundsView{" +
				"pid=" + pid +
				", relNumber='" + relNumber + '\'' +
				", relName='" + relName + '\'' +
				", corpInvestFund=" + corpInvestFund +
				", corpTotalFund='" + corpTotalFund + '\'' +
				", relfundsSeq='" + relfundsSeq + '\'' +
				", portPid=" + portPid +
				", relType='" + relType + '\'' +
				'}';
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof InsRelfundsView == false) return false;
		if(this == obj) return true;
		InsRelfundsView other = (InsRelfundsView)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

