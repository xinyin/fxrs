/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月12日 11:10:44
 *
 */

package com.yzj.jep.app.ins.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;

@Entity
@Table(name = "ins_tra_cramc_info")
public class TraCramcInfo extends BasePO {
	
	public static final String TABLE_ALIAS = "TraCramcInfo";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_DTRADE_DATE = "交易日期";
	public static final String ALIAS_FREFCON = "交易编号";
	public static final String ALIAS_DVAL_DATE = "交割日期";
	public static final String ALIAS_VC_NAME = "证券名称";
	public static final String ALIAS_VC_REFERENCE = "证券类型";
	public static final String ALIAS_VC_ISSUER_CODE = "证券发行人代码";
	public static final String ALIAS_VC_ISSUER_NAME = "证券发行人";
	public static final String ALIAS_VC_TYPE_LEVEL1 = "资产分类一级";
	public static final String ALIAS_VC_TYPE_LEVEL2 = "资产分类二级";
	public static final String ALIAS_VC_TYPE_LEVEL3 = "资产分类三级";
	public static final String ALIAS_VC_CURRENCY = "币种";
	public static final String ALIAS_FQUANTITY = "交易数量";
	public static final String ALIAS_FAMOUNT = "成交金额";
	public static final String ALIAS_FUNIT_PRICE = "成交单价";
	public static final String ALIAS_FINTEREST = "利息";
	public static final String ALIAS_FFRAISMARCHE = "全球市场费用";
	public static final String ALIAS_FFRAISCOURTAGE = "佣金";
	public static final String ALIAS_FFRAISCOUNTERPARTY = "交易费用";
	public static final String ALIAS_VC_WAY = "买卖方向";
	public static final String ALIAS_VC_ENTITY = "公司名称";
	public static final String ALIAS_VC_ENTITY_CODE = "公司代码";
	public static final String ALIAS_VC_COUNTERPARTY = "交易对手名称";
	public static final String ALIAS_VC_COUNTERPARTY_CODE = "交易对手代码";
	public static final String ALIAS_VC_OPERATOR = "交易发起人id（需确保该id的唯一性，以便与集中认证平台的用户id进行映射）";
	public static final String ALIAS_IS_EFFECTIVE = "是否生效";
	public static final String ALIAS_APPROVE_TRADE_AMOUNT = "审批金额";
	public static final String ALIAS_TRANS_NUM = "关联交易数据交易编号（关联依据）";
	public static final String ALIAS_IS_RELATION = "是否关联";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "PID", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "d_trade_date", nullable = false, length = 19)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date dtradeDate;

	@Column(name = "f_refcon", nullable = true, length = 32)
	private String frefcon;

	@Column(name = "d_val_date", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date dvalDate;

	@Column(name = "vc_name", nullable = true, length = 50)
	private String vcName;

	@Column(name = "vc_reference", nullable = true, length = 50)
	private String vcReference;

	@Column(name = "vc_issuer_code", nullable = true, length = 8)
	private String vcIssuerCode;

	@Column(name = "vc_issuer_name", nullable = true, length = 200)
	private String vcIssuerName;

	@Column(name = "vc_type_level_1", nullable = true, length = 30)
	private String vcTypeLevel1;

	@Column(name = "vc_type_level_2", nullable = true, length = 30)
	private String vcTypeLevel2;

	@Column(name = "vc_type_level_3", nullable = true, length = 30)
	private String vcTypeLevel3;

	@Column(name = "vc_currency", nullable = true, length = 10)
	private String vcCurrency;

	@Column(name = "f_Quantity", nullable = true, length = 38)
	private String fquantity;

	@Column(name = "f_amount", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal famount;

	@Column(name = "f_unit_price", nullable = true, precision = 50, scale = 2)
	private java.math.BigDecimal funitPrice;

	@Column(name = "f_interest", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal finterest;

	@Column(name = "f_fraismarche", nullable = true, length = 255)
	private String ffraismarche;

	@Column(name = "f_fraiscourtage", nullable = true, length = 30)
	private String ffraiscourtage;

	@Column(name = "f_fraiscounterparty", nullable = true, length = 30)
	private String ffraiscounterparty;

	@Column(name = "vc_way", nullable = true, length = 5)
	private String vcWay;

	@Column(name = "vc_entity", nullable = true, length = 50)
	private String vcEntity;

	@Column(name = "vc_entity_code", nullable = true, length = 50)
	private String vcEntityCode;

	@Column(name = "vc_counterparty", nullable = true, length = 50)
	private String vcCounterparty;

	@Column(name = "vc_counterparty_code", nullable = true, length = 50)
	private String vcCounterpartyCode;

	@Column(name = "vc_operator", nullable = true, length = 50)
	private String vcOperator;

	@Column(name = "IS_EFFECTIVE", nullable = true, length = 1)
	private String isEffective;

	@Column(name = "APPROVE_TRADE_AMOUNT", nullable = true, precision = 10, scale = 0)
	private Long approveTradeAmount;

	@Column(name = "TRANS_NUM", nullable = true, length = 32)
	private String transNum;

	@Column(name = "is_relation", nullable = true, length = 5)
	private String isRelation;
	//columns END


	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public Date getDtradeDate() {
		return this.dtradeDate;
	}
	
	public void setDtradeDate(Date value) {
		this.dtradeDate = value;
	}
	

	public String getFrefcon() {
		return this.frefcon;
	}
	
	public void setFrefcon(String value) {
		this.frefcon = value;
	}
	

	public Date getDvalDate() {
		return this.dvalDate;
	}
	
	public void setDvalDate(Date value) {
		this.dvalDate = value;
	}
	

	public String getVcName() {
		return this.vcName;
	}
	
	public void setVcName(String value) {
		this.vcName = value;
	}
	

	public String getVcReference() {
		return this.vcReference;
	}
	
	public void setVcReference(String value) {
		this.vcReference = value;
	}
	

	public String getVcIssuerCode() {
		return this.vcIssuerCode;
	}
	
	public void setVcIssuerCode(String value) {
		this.vcIssuerCode = value;
	}
	

	public String getVcIssuerName() {
		return this.vcIssuerName;
	}
	
	public void setVcIssuerName(String value) {
		this.vcIssuerName = value;
	}
	

	public String getVcTypeLevel1() {
		return this.vcTypeLevel1;
	}
	
	public void setVcTypeLevel1(String value) {
		this.vcTypeLevel1 = value;
	}
	

	public String getVcTypeLevel2() {
		return this.vcTypeLevel2;
	}
	
	public void setVcTypeLevel2(String value) {
		this.vcTypeLevel2 = value;
	}
	

	public String getVcTypeLevel3() {
		return this.vcTypeLevel3;
	}
	
	public void setVcTypeLevel3(String value) {
		this.vcTypeLevel3 = value;
	}
	

	public String getVcCurrency() {
		return this.vcCurrency;
	}
	
	public void setVcCurrency(String value) {
		this.vcCurrency = value;
	}
	

	public String getFquantity() {
		return this.fquantity;
	}
	
	public void setFquantity(String value) {
		this.fquantity = value;
	}
	

	public java.math.BigDecimal getFamount() {
		return this.famount;
	}
	
	public void setFamount(java.math.BigDecimal value) {
		this.famount = value;
	}
	

	public java.math.BigDecimal getFunitPrice() {
		return this.funitPrice;
	}
	
	public void setFunitPrice(java.math.BigDecimal value) {
		this.funitPrice = value;
	}
	

	public java.math.BigDecimal getFinterest() {
		return this.finterest;
	}
	
	public void setFinterest(java.math.BigDecimal value) {
		this.finterest = value;
	}
	

	public String getFfraismarche() {
		return this.ffraismarche;
	}
	
	public void setFfraismarche(String value) {
		this.ffraismarche = value;
	}
	

	public String getFfraiscourtage() {
		return this.ffraiscourtage;
	}
	
	public void setFfraiscourtage(String value) {
		this.ffraiscourtage = value;
	}
	

	public String getFfraiscounterparty() {
		return this.ffraiscounterparty;
	}
	
	public void setFfraiscounterparty(String value) {
		this.ffraiscounterparty = value;
	}
	

	public String getVcWay() {
		return this.vcWay;
	}
	
	public void setVcWay(String value) {
		this.vcWay = value;
	}
	

	public String getVcEntity() {
		return this.vcEntity;
	}
	
	public void setVcEntity(String value) {
		this.vcEntity = value;
	}
	

	public String getVcEntityCode() {
		return this.vcEntityCode;
	}
	
	public void setVcEntityCode(String value) {
		this.vcEntityCode = value;
	}
	

	public String getVcCounterparty() {
		return this.vcCounterparty;
	}
	
	public void setVcCounterparty(String value) {
		this.vcCounterparty = value;
	}
	

	public String getVcCounterpartyCode() {
		return this.vcCounterpartyCode;
	}
	
	public void setVcCounterpartyCode(String value) {
		this.vcCounterpartyCode = value;
	}
	

	public String getVcOperator() {
		return this.vcOperator;
	}
	
	public void setVcOperator(String value) {
		this.vcOperator = value;
	}
	

	public String getIsEffective() {
		return this.isEffective;
	}
	
	public void setIsEffective(String value) {
		this.isEffective = value;
	}
	

	public Long getApproveTradeAmount() {
		return this.approveTradeAmount;
	}
	
	public void setApproveTradeAmount(Long value) {
		this.approveTradeAmount = value;
	}
	

	public String getTransNum() {
		return this.transNum;
	}
	
	public void setTransNum(String value) {
		this.transNum = value;
	}
	

	public String getIsRelation() {
		return this.isRelation;
	}
	
	public void setIsRelation(String value) {
		this.isRelation = value;
	}
	

	public String toString() {
		return new StringBuilder("traCramcInfo[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("dtradeDate = ").append(dtradeDate).append(", \n")
						.append("frefcon = ").append(frefcon).append(", \n")
						.append("dvalDate = ").append(dvalDate).append(", \n")
						.append("vcName = ").append(vcName).append(", \n")
						.append("vcReference = ").append(vcReference).append(", \n")
						.append("vcIssuerCode = ").append(vcIssuerCode).append(", \n")
						.append("vcIssuerName = ").append(vcIssuerName).append(", \n")
						.append("vcTypeLevel1 = ").append(vcTypeLevel1).append(", \n")
						.append("vcTypeLevel2 = ").append(vcTypeLevel2).append(", \n")
						.append("vcTypeLevel3 = ").append(vcTypeLevel3).append(", \n")
						.append("vcCurrency = ").append(vcCurrency).append(", \n")
						.append("fquantity = ").append(fquantity).append(", \n")
						.append("famount = ").append(famount).append(", \n")
						.append("funitPrice = ").append(funitPrice).append(", \n")
						.append("finterest = ").append(finterest).append(", \n")
						.append("ffraismarche = ").append(ffraismarche).append(", \n")
						.append("ffraiscourtage = ").append(ffraiscourtage).append(", \n")
						.append("ffraiscounterparty = ").append(ffraiscounterparty).append(", \n")
						.append("vcWay = ").append(vcWay).append(", \n")
						.append("vcEntity = ").append(vcEntity).append(", \n")
						.append("vcEntityCode = ").append(vcEntityCode).append(", \n")
						.append("vcCounterparty = ").append(vcCounterparty).append(", \n")
						.append("vcCounterpartyCode = ").append(vcCounterpartyCode).append(", \n")
						.append("vcOperator = ").append(vcOperator).append(", \n")
						.append("isEffective = ").append(isEffective).append(", \n")
						.append("approveTradeAmount = ").append(approveTradeAmount).append(", \n")
						.append("transNum = ").append(transNum).append(", \n")
						.append("isRelation = ").append(isRelation).append(", \n")
						.append("]")
						.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TraCramcInfo == false) return false;
		if(this == obj) return true;
		TraCramcInfo other = (TraCramcInfo)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

