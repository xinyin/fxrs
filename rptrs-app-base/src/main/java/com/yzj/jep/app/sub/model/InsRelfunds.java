/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:36
 *
 */

package com.yzj.jep.app.sub.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "sub_ins_relfunds")
public class InsRelfunds extends BasePO {

	public static final String TABLE_ALIAS = "InsRelfunds";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_REL_TYPE = "关联方类型";
	public static final String ALIAS_REL_NUMBER= "关联方名称";
	public static final String ALIAS_CORP_INVEST_FUND = "资金运用投资余额";
	public static final String ALIAS_CORP_TOTAL_FUND = "关联方总资产投资";
	public static final String ALIAS_STATUS = "数据报送状态";
	public static final String ALIAS_SUB_YEAR = "报送年限";
	public static final String ALIAS_SUB_QUARTER = "报送季度";
	public static final String INS_FUNDS_DATE="投资余额时间";


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

	@Column(name = "STATUS", nullable = true, length = 100)
	private String status;

	@Column(name = "sub_year", nullable = true, length = 10)
	private String subYear;

	@Column(name = "sub_quarter", nullable = true, length = 10)
	private String subQuarter;

	@Column(name="ins_funds_date")
	private Date insFundsDate;

	public String getRelType() {
		return relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	@Column(name="rel_type" , nullable = true )
	private String relType; //关联方类型
	@Transient
	private BigDecimal corpTotalFundSub;

//	public BigDecimal getCorpTotalFundSub() {
//		return new BigDecimal(corpTotalFund).setScale(6,BigDecimal.ROUND_HALF_UP);
//	}
	//columns END


	public Date getInsFundsDate() {
		return insFundsDate;
	}

	public void setInsFundsDate(Date insFundsDate) {
		this.insFundsDate = insFundsDate;
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


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}


	public String getSubYear() {
		return this.subYear;
	}

	public void setSubYear(String value) {
		this.subYear = value;
	}


	public String getSubQuarter() {
		return this.subQuarter;
	}

	public void setSubQuarter(String value) {
		this.subQuarter = value;
	}


	@Override
	public String toString() {
		return "InsRelfunds{" +
				"pid=" + pid +
				", relNumber='" + relNumber + '\'' +
				", relName='" + relName + '\'' +
				", corpInvestFund=" + corpInvestFund +
				", corpTotalFund='" + corpTotalFund + '\'' +
				", status='" + status + '\'' +
				", subYear='" + subYear + '\'' +
				", subQuarter='" + subQuarter + '\'' +
				", insFundsDate=" + insFundsDate +
				", relType='" + relType + '\'' +
				", corpTotalFundSub=" + corpTotalFundSub +
				'}';
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getPid())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof InsRelfunds == false) return false;
		if(this == obj) return true;
		InsRelfunds other = (InsRelfunds)obj;
		return new EqualsBuilder()
				.append(getPid(),other.getPid())
				.isEquals();
	}
}

