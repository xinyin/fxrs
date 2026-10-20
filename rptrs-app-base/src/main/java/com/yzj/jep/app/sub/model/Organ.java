/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:44
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
@Table(name = "sub_organ")
public class Organ extends BasePO{

	public static final String TABLE_ALIAS = "Organ";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_NAME = "姓名";
	public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内(外)标志";
	public static final String ALIAS_IDENTITY_NO = "证件号码";
	public static final String ALIAS_REG_CAPITAL = "注册资本(万元)";
	public static final String ALIAS_REG_CAPITAL_CURRENCY = "注册资本币种代码";
	public static final String ALIAS_BUSINESS_SCOPE = "营业范围";
	public static final String ALIAS_ORG_STATUS = "机构状态";
	public static final String ALIAS_LEGAL_PERSON = "法定代表人";
	public static final String ALIAS_LEGAL_IDENTIFY_TYPE = "法定代表人_证件类型";
	public static final String ALIAS_LEGAL_IDENTIFY_NUMBER = "社会统一信用代码";
	public static final String ALIAS_REG_ADDRESS = "注册地";
	public static final String ALIAS_VALID_STATUS = "是否有效";
	public static final String ALIAS_ACTIVE_TIME = "生效时间";
	public static final String ALIAS_INVALID_TIME = "失效时间";
	public static final String ALIAS_STATUS = "前置流程状态";
	public static final String ALIAS_STATUS_COMMENT = "前置流程状态说明";
	public static final String ALIAS_MGER_ORG = "管户机构";
	public static final String ALIAS_REGULATOR_OTH = "口径";
	public static final String ALIAS_IS_BLACK = "是否黑名单";
	public static final String ALIAS_BLACK_REASON = "加入黑名单原因";
	public static final String ALIAS_IS_SHAREHOLDER = "是否为股东";
	public static final String ALIAS_SHAREHOLDERS_CODE = "股东代码";
	public static final String ALIAS_STATE_OWNED = "是否国有企业";
	public static final String ALIAS_OPEN_BANK = "账户关联银行账户开户行";
	public static final String ALIAS_TRADE_ACCOUNT = "关联银行户账号";
	public static final String ALIAS_HOLE_SHARE_AMOUNT = "持股数量";
	public static final String ALIAS_SHAREHOLDING_PROPORTION = "持股占比";
	public static final String ALIAS_IN_TIME = "入股时间";
	public static final String ALIAS_IN_PRICE = "入股价格";
	public static final String ALIAS_FROZEN_SHARE = "总冻结股数（股）";
	public static final String ALIAS_PLEDGED_SHARE = "总质押股数（股）";
	public static final String ALIAS_REL_TYPE = "关联方类型";
	public static final String ALIAS_DATA_SOURCE = "数据来源";
	public static final String ALIAS_IS_MAIN_GD = "是否本行主要股东";
	public static final String ALIAS_CAPITAL_NUMBER = "股权证编号";
	public static final String ALIAS_IS_NO_SHARE_VOTE = "是否具有非持股表决权";
	public static final String ALIAS_VOTE_PROPORTION = "表决权占比";
	public static final String ALIAS_LIMIT_VOTE_SITUATION = "限制表决权情况";
	public static final String ALIAS_IS_INSIDER = "是否内部人";
	public static final String ALIAS_WARN_LEVE = "预警级别";
	public static final String ALIAS_PRESET_LIMIT_CURRENCY = "预设额度币种";
	public static final String ALIAS_PRESET_LIMIT = "预设额度";
	public static final String ALIAS_AVAILABLE_LIMIT = "可用额度";
	public static final String ALIAS_MEM_GROUP = "集团证件号";
	public static final String ALIAS_GROUP_NAME = "集团名称";
	public static final String ALIAS_IDENTIFY_TYPE = "证件类型";


	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "name_", nullable = false, length = 100)
	private String name;

	@Column(name = "domestic_or_foreign", nullable = false, length = 2)
	private String domesticOrForeign;

	@Column(name = "identity_no", nullable = false, length = 25)
	private String identityNo;

	@Column(name = "identify_type", nullable = false, length = 20)
	private String identifyType;

	@Column(name = "reg_capital", nullable = true, precision = 10, scale = 0)
	private Long regCapital;

	@Column(name = "reg_capital_currency", nullable = true, length = 10)
	private String regCapitalCurrency;

	@Column(name = "business_scope", nullable = true, length = 255)
	private String businessScope;

	@Column(name = "org_status", nullable = true, length = 10)
	private String orgStatus;

	@Column(name = "legal_person", nullable = true, length = 100)
	private String legalPerson;

	@Column(name = "legal_identify_type", nullable = true, length = 10)
	private String legalIdentifyType;

	@Column(name = "legal_identify_number", nullable = true, length = 20)
	private String legalIdentifyNumber;

	@Column(name = "reg_address", nullable = true, length = 100)
	private String regAddress;

	@Column(name = "valid_status", nullable = true, length = 10)
	private String validStatus;

	@Column(name = "active_time", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date activeTime;

	@Column(name = "invalid_time", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date invalidTime;

	@Column(name = "status", nullable = false, length = 10)
	private String status;

	@Column(name = "status_comment", nullable = true, length = 255)
	private String statusComment;

	@Column(name = "mger_org", nullable = true, length = 50)
	private String mgerOrg;

	@Column(name = "regulator_oth", nullable = true, length = 50)
	private String regulatorOth;

	@Column(name = "is_black", nullable = true, length = 4)
	private String isBlack;

	@Column(name = "black_reason", nullable = true, length = 255)
	private String blackReason;

	@Column(name = "is_shareholder", nullable = true, length = 16)
	private String isShareholder;

	@Column(name = "shareholders_code", nullable = true, length = 100)
	private String shareholdersCode;

	@Column(name = "state_owned", nullable = true, length = 10)
	private String stateOwned;

	@Column(name = "open_bank", nullable = true, length = 100)
	private String openBank;

	@Column(name = "trade_account", nullable = true, length = 50)
	private String tradeAccount;

	@Column(name = "hole_share_amount", nullable = true, length = 50)
	private String holeShareAmount;

	@Column(name = "shareholding_proportion", nullable = true, length = 10)
	private String shareholdingProportion;

	@Column(name = "in_time", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date inTime;

	@Column(name = "in_price", nullable = true, precision = 10, scale = 2)
	private java.math.BigDecimal inPrice;

	@Column(name = "frozen_share", nullable = true, length = 50)
	private String frozenShare;

	@Column(name = "pledged_share", nullable = true, length = 50)
	private String pledgedShare;

	@Column(name = "rel_type", nullable = true, length = 20)
	private String relType;

	@Column(name = "data_source", nullable = true, length = 2)
	private String dataSource;

	@Column(name = "is_main_gd", nullable = true, length = 16)
	private String isMainGd;

	@Column(name = "capital_number", nullable = true, length = 64)
	private String capitalNumber;

	@Column(name = "is_no_share_vote", nullable = true, length = 16)
	private String isNoShareVote;

	@Column(name = "vote_proportion", nullable = true, length = 20)
	private String voteProportion;

	@Column(name = "limit_vote_situation", nullable = true, length = 200)
	private String limitVoteSituation;

	@Column(name = "is_insider", nullable = true, length = 2)
	private String isInsider;

	@Column(name = "warn_leve", nullable = true, length = 1)
	private String warnLeve;

	@Column(name = "preset_limit_currency", nullable = true, length = 11)
	private String presetLimitCurrency;

	@Column(name = "preset_limit", nullable = true, length = 16)
	private String presetLimit;

	@Column(name = "available_limit", nullable = true, length = 16)
	private String availableLimit;

	@Column(name = "mem_group", nullable = true, length = 20)
	private String memGroup;

	@Column(name = "group_name", nullable = true, length = 10)
	private String groupName;


	@Column(name = "relate_number", nullable = true, length = 20)
	private String relateNumber;

	@Column(name = "is_extract", nullable = true, length = 6)
	private String isExtract;
	@Column(name="effect_relation")
	private String effectRelation;
	//columns END
	// 经济性质或类型
	@Column(name="ORG_NATURE")
	private String orgNature;

	@Column(name = "GLFBH", nullable = true, length = 65)
	private String glfbh;

	public String getGlfbh() {
		return glfbh;
	}

	public void setGlfbh(String glfbh) {
		this.glfbh = glfbh;
	}

	public String getOrgNature() {
		return orgNature;
	}

	public void setOrgNature(String orgNature) {
		this.orgNature = orgNature;
	}

	public String getEffectRelation() {
		return effectRelation;
	}

	public void setEffectRelation(String effectRelation) {
		this.effectRelation = effectRelation;
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

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer value) {
		this.pid = value;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}


	public String getDomesticOrForeign() {
		return this.domesticOrForeign;
	}

	public void setDomesticOrForeign(String value) {
		this.domesticOrForeign = value;
	}


	public String getIdentityNo() {
		return this.identityNo;
	}

	public void setIdentityNo(String value) {
		this.identityNo = value;
	}

	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public Long getRegCapital() {
		return this.regCapital;
	}

	public void setRegCapital(Long value) {
		this.regCapital = value;
	}


	public String getRegCapitalCurrency() {
		return this.regCapitalCurrency;
	}

	public void setRegCapitalCurrency(String value) {
		this.regCapitalCurrency = value;
	}


	public String getBusinessScope() {
		return this.businessScope;
	}

	public void setBusinessScope(String value) {
		this.businessScope = value;
	}


	public String getOrgStatus() {
		return this.orgStatus;
	}

	public void setOrgStatus(String value) {
		this.orgStatus = value;
	}


	public String getLegalPerson() {
		return this.legalPerson;
	}

	public void setLegalPerson(String value) {
		this.legalPerson = value;
	}


	public String getLegalIdentifyType() {
		return this.legalIdentifyType;
	}

	public void setLegalIdentifyType(String value) {
		this.legalIdentifyType = value;
	}


	public String getLegalIdentifyNumber() {
		return this.legalIdentifyNumber;
	}

	public void setLegalIdentifyNumber(String value) {
		this.legalIdentifyNumber = value;
	}


	public String getRegAddress() {
		return this.regAddress;
	}

	public void setRegAddress(String value) {
		this.regAddress = value;
	}


	public String getValidStatus() {
		return this.validStatus;
	}

	public void setValidStatus(String value) {
		this.validStatus = value;
	}


	public Date getActiveTime() {
		return this.activeTime;
	}

	public void setActiveTime(Date value) {
		this.activeTime = value;
	}


	public Date getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(Date value) {
		this.invalidTime = value;
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


	public String getMgerOrg() {
		return this.mgerOrg;
	}

	public void setMgerOrg(String value) {
		this.mgerOrg = value;
	}


	public String getRegulatorOth() {
		return this.regulatorOth;
	}

	public void setRegulatorOth(String value) {
		this.regulatorOth = value;
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


	public String getStateOwned() {
		return this.stateOwned;
	}

	public void setStateOwned(String value) {
		this.stateOwned = value;
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


	public Date getInTime() {
		return this.inTime;
	}

	public void setInTime(Date value) {
		this.inTime = value;
	}


	public java.math.BigDecimal getInPrice() {
		return this.inPrice;
	}

	public void setInPrice(java.math.BigDecimal value) {
		this.inPrice = value;
	}


	public String getFrozenShare() {
		return this.frozenShare;
	}

	public void setFrozenShare(String value) {
		this.frozenShare = value;
	}


	public String getPledgedShare() {
		return this.pledgedShare;
	}

	public void setPledgedShare(String value) {
		this.pledgedShare = value;
	}


	public String getRelType() {
		return this.relType;
	}

	public void setRelType(String value) {
		this.relType = value;
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


	public String getIsInsider() {
		return this.isInsider;
	}

	public void setIsInsider(String value) {
		this.isInsider = value;
	}


	public String getWarnLeve() {
		return this.warnLeve;
	}

	public void setWarnLeve(String value) {
		this.warnLeve = value;
	}


	public String getPresetLimitCurrency() {
		return this.presetLimitCurrency;
	}

	public void setPresetLimitCurrency(String value) {
		this.presetLimitCurrency = value;
	}


	public String getPresetLimit() {
		return this.presetLimit;
	}

	public void setPresetLimit(String value) {
		this.presetLimit = value;
	}


	public String getAvailableLimit() {
		return this.availableLimit;
	}

	public void setAvailableLimit(String value) {
		this.availableLimit = value;
	}


	public String getMemGroup() {
		return this.memGroup;
	}

	public void setMemGroup(String value) {
		this.memGroup = value;
	}


	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String value) {
		this.groupName = value;
	}

	@Override
	public String toString() {
		return "Organ{" +
				"pid=" + pid +
				", name='" + name + '\'' +
				", domesticOrForeign='" + domesticOrForeign + '\'' +
				", identityNo='" + identityNo + '\'' +
				", identifyType='" + identifyType + '\'' +
				", regCapital=" + regCapital +
				", regCapitalCurrency='" + regCapitalCurrency + '\'' +
				", businessScope='" + businessScope + '\'' +
				", orgStatus='" + orgStatus + '\'' +
				", legalPerson='" + legalPerson + '\'' +
				", legalIdentifyType='" + legalIdentifyType + '\'' +
				", legalIdentifyNumber='" + legalIdentifyNumber + '\'' +
				", regAddress='" + regAddress + '\'' +
				", validStatus='" + validStatus + '\'' +
				", activeTime=" + activeTime +
				", invalidTime=" + invalidTime +
				", status='" + status + '\'' +
				", statusComment='" + statusComment + '\'' +
				", mgerOrg='" + mgerOrg + '\'' +
				", regulatorOth='" + regulatorOth + '\'' +
				", isBlack='" + isBlack + '\'' +
				", blackReason='" + blackReason + '\'' +
				", isShareholder='" + isShareholder + '\'' +
				", shareholdersCode='" + shareholdersCode + '\'' +
				", stateOwned='" + stateOwned + '\'' +
				", openBank='" + openBank + '\'' +
				", tradeAccount='" + tradeAccount + '\'' +
				", holeShareAmount='" + holeShareAmount + '\'' +
				", shareholdingProportion='" + shareholdingProportion + '\'' +
				", inTime=" + inTime +
				", inPrice=" + inPrice +
				", frozenShare='" + frozenShare + '\'' +
				", pledgedShare='" + pledgedShare + '\'' +
				", relType='" + relType + '\'' +
				", dataSource='" + dataSource + '\'' +
				", isMainGd='" + isMainGd + '\'' +
				", capitalNumber='" + capitalNumber + '\'' +
				", isNoShareVote='" + isNoShareVote + '\'' +
				", voteProportion='" + voteProportion + '\'' +
				", limitVoteSituation='" + limitVoteSituation + '\'' +
				", isInsider='" + isInsider + '\'' +
				", warnLeve='" + warnLeve + '\'' +
				", presetLimitCurrency='" + presetLimitCurrency + '\'' +
				", presetLimit='" + presetLimit + '\'' +
				", availableLimit='" + availableLimit + '\'' +
				", memGroup='" + memGroup + '\'' +
				", groupName='" + groupName + '\'' +
				", relateNumber='" + relateNumber + '\'' +
				", isExtract='" + isExtract + '\'' +
				", effectRelation='" + effectRelation + '\'' +
				", orgNature='" + orgNature + '\'' +
				'}';
	}

	@Override
    public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}

	@Override
    public boolean equals(Object obj) {
		if(obj instanceof Organ == false) {return false;}
		if(this == obj) {return true;}
		Organ other = (Organ)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

