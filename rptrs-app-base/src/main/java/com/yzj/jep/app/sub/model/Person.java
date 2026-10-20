/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:47
 *
 */

package com.yzj.jep.app.sub.model;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sub_person")
public class Person extends BasePO{

	public static final String TABLE_ALIAS = "Person";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_ORIGNAL_ORG = "源主体银行机构";
	public static final String ALIAS_ORIGNAL_ORG_NAME = "源主体银行机构名称";
	public static final String ALIAS_NAME = "姓名";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_NATIONALITY = "国别/地区";
	public static final String ALIAS_RACE = "民族";
	public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内(外)标志";
	public static final String ALIAS_IDENTITY_TYPE = "证件类型";
	public static final String ALIAS_IDENTITY_NO = "证件号码";
	public static final String ALIAS_BIRTHDATE = "出生日期";
	public static final String ALIAS_MOBILE = "移动电话";
	public static final String ALIAS_EMAIL = "邮箱";
	public static final String ALIAS_DUTY_CODE = "职位";
	public static final String ALIAS_STATUS = "前置流程状态0-已抽取 1-校验不通过 2-待统计 3-待报送 4-已报送";
	public static final String ALIAS_STATUS_COMMENT = "前置流程状态说明";
	public static final String ALIAS_MARITAL_STATUS = "婚姻状况";
	public static final String ALIAS_MGER_ORG = "管户机构";
	public static final String ALIAS_REGULATOR = "口径";
	public static final String ALIAS_IS_BLACK = "是否黑名单";
	public static final String ALIAS_BLACK_REASON = "加入黑名单原因";
	public static final String ALIAS_IS_SHAREHOLDER = "是否为股东";
	public static final String ALIAS_SHAREHOLDERS_CODE = "股东代码（账户号）";
	public static final String ALIAS_OPEN_BANK = "交易账户开户行";
	public static final String ALIAS_TRADE_ACCOUNT = "交易账户账号";
	public static final String ALIAS_IN_TIME = "入股时间";
	public static final String ALIAS_IN_PRICE = "入股价格";
	public static final String ALIAS_HOLE_SHARE_AMOUNT = "持股数量（股）";
	public static final String ALIAS_SHAREHOLDING_PROPORTION = "持股占比";
	public static final String ALIAS_FROZEN_SHARE_AMOUNT = "总冻结股数（股）";
	public static final String ALIAS_PLEDGED_SHARE_AMOUNT = "总质押股数（股）";
	public static final String ALIAS_REL_TYPE = "关联方类型";
	public static final String ALIAS_POLITICS_STATUS = "政治面貌";
	public static final String ALIAS_EDUCATION_BACKGROUND = "学历";
	public static final String ALIAS_RESIDENTIAL_ADDRESS = "居住地址";
	public static final String ALIAS_WORK_UNITS = "工作单位";
	public static final String ALIAS_UNIT_ADDRESS = "单位地址";
	public static final String ALIAS_DATA_SOURCE = "数据来源";
	public static final String ALIAS_IS_MAIN_GD = "是否本行主要股东";
	public static final String ALIAS_CAPITAL_NUMBER = "股权证编号";
	public static final String ALIAS_IS_NO_SHARE_VOTE = "是否具有非持股表决权";
	public static final String ALIAS_VOTE_PROPORTION = "表决权占比";
	public static final String ALIAS_LIMIT_VOTE_SITUATION = "限制表决权情况";
	public static final String ALIAS_TRADE_BALANCE = "交易余额";
	public static final String ALIAS_EFFECT_RELATION = "有效关系";

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "orignal_org", nullable = true, length = 20)
	private String orignalOrg;

	@Column(name = "orignal_org_name", nullable = true, length = 100)
	private String orignalOrgName;

	@Column(name = "name_", nullable = false, length = 10)
	private String name;

	@Column(name = "sex", nullable = true, length = 2)
	private String sex;

	@Column(name = "nationality", nullable = true, length = 30)
	private String nationality;

	@Column(name = "race", nullable = true, length = 20)
	private String race;

	@Column(name = "domestic_or_foreign", nullable = false, length = 2)
	private String domesticOrForeign;

	@Column(name = "identity_type", nullable = false, length = 2)
	private String identityType;

	@Column(name = "identity_no", nullable = false, length = 50)
	private String identityNo;

	@Column(name = "birthdate", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date birthdate;

	@Column(name = "mobile", nullable = true, length = 11)
	private String mobile;

	@Column(name = "email", nullable = true, length = 32)
	private String email;

	@Column(name = "duty_code", nullable = true, length = 11)
	private String dutyCode;

	@Column(name = "status", nullable = false, length = 2)
	private String status;

	@Column(name = "status_comment", nullable = true, length = 255)
	private String statusComment;

	@Column(name = "marital_status", nullable = true, length = 32)
	private String maritalStatus;

	@Column(name = "mger_org", nullable = true, length = 50)
	private String mgerOrg;

	@Column(name = "regulator", nullable = true, length = 50)
	private String regulator;

	@Column(name = "is_black", nullable = true, length = 4)
	private String isBlack;

	@Column(name = "black_reason", nullable = true, length = 255)
	private String blackReason;

	@Column(name = "is_shareholder", nullable = true, length = 16)
	private String isShareholder;

	@Column(name = "shareholders_code", nullable = true, length = 100)
	private String shareholdersCode;

	@Column(name = "open_bank", nullable = true, length = 100)
	private String openBank;

	@Column(name = "trade_account", nullable = true, length = 50)
	private String tradeAccount;

	@Column(name = "in_time", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date inTime;

	@Column(name = "in_price", nullable = true, length = 20)
	private String inPrice;

	@Column(name = "hole_share_amount", nullable = true, length = 50)
	private String holeShareAmount;

	@Column(name = "shareholding_proportion", nullable = true, length = 10)
	private String shareholdingProportion;

	@Column(name = "frozen_share_amount", nullable = true, length = 20)
	private String frozenShareAmount;

	@Column(name = "pledged_share_amount", nullable = true, length = 20)
	private String pledgedShareAmount;

	@Column(name = "rel_type", nullable = true, length = 20)
	private String relType;

	@Column(name = "politics_status", nullable = true, length = 64)
	private String politicsStatus;

	@Column(name = "education_background", nullable = true, length = 64)
	private String educationBackground;

	@Column(name = "residentialAddress", nullable = true, length = 64)
	private String residentialAddress;

	@Column(name = "workUnits", nullable = true, length = 64)
	private String workUnits;

	@Column(name = "unitAddress", nullable = true, length = 64)
	private String unitAddress;

	@Column(name = "data_source", nullable = true, length = 10)
	private String dataSource;

	@Column(name = "is_main_gd", nullable = true, length = 2)
	private String isMainGd;

	@Column(name = "capital_number", nullable = true, length = 64)
	private String capitalNumber;

	@Column(name = "is_no_share_vote", nullable = true, length = 16)
	private String isNoShareVote;

	@Column(name = "vote_proportion", nullable = true, length = 20)
	private String voteProportion;

	@Column(name = "limit_vote_situation", nullable = true, length = 200)
	private String limitVoteSituation;

	@Column(name = "trade_balance", nullable = true, precision = 10, scale = 0)
	private Long tradeBalance;

	//columns END
	@Column(name="effect_relation")
	private String effectRelation;

	@Column(name = "relate_number", nullable = true, length = 20)
	private String relateNumber;

	@Column(name = "is_extract", nullable = true, length = 6)
	private String isExtract;

	@Column(name = "GLFBH", nullable = true, length = 65)
	private String glfbh;
	//columns END


	public String getGlfbh() {
		return glfbh;
	}

	public void setGlfbh(String glfbh) {
		this.glfbh = glfbh;
	}

	public String getIsExtract() {
		return isExtract;
	}

	public void setIsExtract(String isExtract) {
		this.isExtract = isExtract;
	}

	public String getRelateNumber() {
		return relateNumber;
	}

	public void setRelateNumber(String relateNumber) {
		this.relateNumber = relateNumber;
	}
	public String getEffectRelation() {
		return effectRelation;
	}

	public void setEffectRelation(String effectRelation) {
		this.effectRelation = effectRelation;
	}


	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer value) {
		this.pid = value;
	}


	public String getOrignalOrg() {
		return this.orignalOrg;
	}

	public void setOrignalOrg(String value) {
		this.orignalOrg = value;
	}


	public String getOrignalOrgName() {
		return this.orignalOrgName;
	}

	public void setOrignalOrgName(String value) {
		this.orignalOrgName = value;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}


	public String getSex() {
		return this.sex;
	}

	public void setSex(String value) {
		this.sex = value;
	}


	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String value) {
		this.nationality = value;
	}


	public String getRace() {
		return this.race;
	}

	public void setRace(String value) {
		this.race = value;
	}


	public String getDomesticOrForeign() {
		return this.domesticOrForeign;
	}

	public void setDomesticOrForeign(String value) {
		this.domesticOrForeign = value;
	}


	public String getIdentityType() {
		return this.identityType;
	}

	public void setIdentityType(String value) {
		this.identityType = value;
	}


	public String getIdentityNo() {
		return this.identityNo;
	}

	public void setIdentityNo(String value) {
		this.identityNo = value;
	}


	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date value) {
		this.birthdate = value;
	}


	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String value) {
		this.mobile = value;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String value) {
		this.email = value;
	}


	public String getDutyCode() {
		return this.dutyCode;
	}

	public void setDutyCode(String value) {
		this.dutyCode = value;
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


	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(String value) {
		this.maritalStatus = value;
	}


	public String getMgerOrg() {
		return this.mgerOrg;
	}

	public void setMgerOrg(String value) {
		this.mgerOrg = value;
	}


	public String getRegulator() {
		return this.regulator;
	}

	public void setRegulator(String value) {
		this.regulator = value;
	}


	public String getIsBlack() {
		return this.isBlack;
	}

	public void setIsBlack(String value) {
		this.isBlack = value;
	}


	public String getBlackReason() {
		return this.blackReason;
	}

	public void setBlackReason(String value) {
		this.blackReason = value;
	}


	public String getIsShareholder() {
		return this.isShareholder;
	}

	public void setIsShareholder(String value) {
		this.isShareholder = value;
	}


	public String getShareholdersCode() {
		return this.shareholdersCode;
	}

	public void setShareholdersCode(String value) {
		this.shareholdersCode = value;
	}


	public String getOpenBank() {
		return this.openBank;
	}

	public void setOpenBank(String value) {
		this.openBank = value;
	}


	public String getTradeAccount() {
		return this.tradeAccount;
	}

	public void setTradeAccount(String value) {
		this.tradeAccount = value;
	}


	public Date getInTime() {
		return this.inTime;
	}

	public void setInTime(Date value) {
		this.inTime = value;
	}


	public String getInPrice() {
		return this.inPrice;
	}

	public void setInPrice(String value) {
		this.inPrice = value;
	}


	public String getHoleShareAmount() {
		return this.holeShareAmount;
	}

	public void setHoleShareAmount(String value) {
		this.holeShareAmount = value;
	}


	public String getShareholdingProportion() {
		return this.shareholdingProportion;
	}

	public void setShareholdingProportion(String value) {
		this.shareholdingProportion = value;
	}


	public String getFrozenShareAmount() {
		return this.frozenShareAmount;
	}

	public void setFrozenShareAmount(String value) {
		this.frozenShareAmount = value;
	}


	public String getPledgedShareAmount() {
		return this.pledgedShareAmount;
	}

	public void setPledgedShareAmount(String value) {
		this.pledgedShareAmount = value;
	}


	public String getRelType() {
		return this.relType;
	}

	public void setRelType(String value) {
		this.relType = value;
	}


	public String getPoliticsStatus() {
		return this.politicsStatus;
	}

	public void setPoliticsStatus(String value) {
		this.politicsStatus = value;
	}


	public String getEducationBackground() {
		return this.educationBackground;
	}

	public void setEducationBackground(String value) {
		this.educationBackground = value;
	}


	public String getResidentialAddress() {
		return this.residentialAddress;
	}

	public void setResidentialAddress(String value) {
		this.residentialAddress = value;
	}


	public String getWorkUnits() {
		return this.workUnits;
	}

	public void setWorkUnits(String value) {
		this.workUnits = value;
	}


	public String getUnitAddress() {
		return this.unitAddress;
	}

	public void setUnitAddress(String value) {
		this.unitAddress = value;
	}


	public String getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(String value) {
		this.dataSource = value;
	}


	public String getIsMainGd() {
		return this.isMainGd;
	}

	public void setIsMainGd(String value) {
		this.isMainGd = value;
	}


	public String getCapitalNumber() {
		return this.capitalNumber;
	}

	public void setCapitalNumber(String value) {
		this.capitalNumber = value;
	}


	public String getIsNoShareVote() {
		return this.isNoShareVote;
	}

	public void setIsNoShareVote(String value) {
		this.isNoShareVote = value;
	}


	public String getVoteProportion() {
		return this.voteProportion;
	}

	public void setVoteProportion(String value) {
		this.voteProportion = value;
	}


	public String getLimitVoteSituation() {
		return this.limitVoteSituation;
	}

	public void setLimitVoteSituation(String value) {
		this.limitVoteSituation = value;
	}


	public Long getTradeBalance() {
		return this.tradeBalance;
	}

	public void setTradeBalance(Long value) {
		this.tradeBalance = value;
	}


	@Override
    public String toString() {
		return new StringBuilder("person[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("orignalOrg = ").append(orignalOrg).append(", \n")
						.append("orignalOrgName = ").append(orignalOrgName).append(", \n")
						.append("name = ").append(name).append(", \n")
						.append("sex = ").append(sex).append(", \n")
						.append("nationality = ").append(nationality).append(", \n")
						.append("race = ").append(race).append(", \n")
						.append("domesticOrForeign = ").append(domesticOrForeign).append(", \n")
						.append("identityType = ").append(identityType).append(", \n")
						.append("identityNo = ").append(identityNo).append(", \n")
						.append("birthdate = ").append(birthdate).append(", \n")
						.append("mobile = ").append(mobile).append(", \n")
						.append("email = ").append(email).append(", \n")
						.append("dutyCode = ").append(dutyCode).append(", \n")
						.append("status = ").append(status).append(", \n")
						.append("statusComment = ").append(statusComment).append(", \n")
						.append("maritalStatus = ").append(maritalStatus).append(", \n")
						.append("mgerOrg = ").append(mgerOrg).append(", \n")
						.append("regulator = ").append(regulator).append(", \n")
						.append("isBlack = ").append(isBlack).append(", \n")
						.append("blackReason = ").append(blackReason).append(", \n")
						.append("isShareholder = ").append(isShareholder).append(", \n")
						.append("shareholdersCode = ").append(shareholdersCode).append(", \n")
						.append("openBank = ").append(openBank).append(", \n")
						.append("tradeAccount = ").append(tradeAccount).append(", \n")
						.append("inTime = ").append(inTime).append(", \n")
						.append("inPrice = ").append(inPrice).append(", \n")
						.append("holeShareAmount = ").append(holeShareAmount).append(", \n")
						.append("shareholdingProportion = ").append(shareholdingProportion).append(", \n")
						.append("frozenShareAmount = ").append(frozenShareAmount).append(", \n")
						.append("pledgedShareAmount = ").append(pledgedShareAmount).append(", \n")
						.append("relType = ").append(relType).append(", \n")
						.append("politicsStatus = ").append(politicsStatus).append(", \n")
						.append("educationBackground = ").append(educationBackground).append(", \n")
						.append("residentialAddress = ").append(residentialAddress).append(", \n")
						.append("workUnits = ").append(workUnits).append(", \n")
						.append("unitAddress = ").append(unitAddress).append(", \n")
						.append("dataSource = ").append(dataSource).append(", \n")
						.append("isMainGd = ").append(isMainGd).append(", \n")
						.append("capitalNumber = ").append(capitalNumber).append(", \n")
						.append("isNoShareVote = ").append(isNoShareVote).append(", \n")
						.append("voteProportion = ").append(voteProportion).append(", \n")
						.append("limitVoteSituation = ").append(limitVoteSituation).append(", \n")
						.append("tradeBalance = ").append(tradeBalance).append(", \n")
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
		if(obj instanceof Person == false) {return false;}
		if(this == obj) {return true;}
		Person other = (Person)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

