/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月15日 17:14:18
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bus_tra_credit")
public class TraCredit extends BasePO {

    public static final String TABLE_ALIAS = "关联交易表——授信类";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_TRANS_NUM = "交易编号";
    public static final String ALIAS_REL_ID = "关联方id";
    public static final String ALIAS_REL_TYPE = "关联方类型";
    public static final String ALIAS_RELATED_NAME = "关联方名称";
    public static final String ALIAS_MANAGE_ORG = "管户机构";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_IDENT_NUMBER = "证件号码";
    public static final String ALIAS_TRAN_TYPE = "交易类型";
    public static final String ALIAS_APPROVAL_TIME = "审批时间";
    public static final String ALIAS_CONTRACT_NO = "合同编号";
    public static final String ALIAS_CONTRACT_SIGN_DATE = "合同签署日期";
    public static final String ALIAS_TRAN_PRICE = "交易价格";
    public static final String ALIAS_BZ = "交易币种";
    public static final String ALIAS_VALIDITY_CONTRACT = "合同有效期";
    public static final String ALIAS_STATUS = "状态";
    public static final String ALIAS_CREDIT_AMOUNT = "授信金额";
    public static final String ALIAS_VALIDITY_BALANCE = "授信余额";
    public static final String ALIAS_CREDIT_START_DATE = "授信起始日期";
    public static final String ALIAS_CREDIT_END_DATE = "授信到期日";
    public static final String ALIAS_GUARA_MODE = "担保方式";
    public static final String ALIAS_COLLATERAL = "担保品情况";
    public static final String ALIAS_GUARANTOR = "保证人名称";
    public static final String ALIAS_FIVE_LEVEL = "五级分类";
    public static final String ALIAS_SECURIT_DEPOSIT = "保证金金额";
    public static final String ALIAS_MARGIN_RATIO = "保证金比例";
    public static final String ALIAS_BANK_DEPOSIT = "银行存单";
    public static final String ALIAS_NATIONAL_DEBT = "国债";
    public static final String ALIAS_BUSINESS_TYPE = "业务类型";
    public static final String ALIAS_IS_DEL = "是否删除";
    public static final String ALIAS_REGULATOR_YBJ = "银保监口径";
    public static final String ALIAS_REGULATOR_ZJH = "证监会口径（0|否1|是）";
    public static final String ALIAS_REGULATOR_CZB = "财政部口径（0|否1|是）";
    public static final String ALIAS_REGULATOR_OTH1 = "其他口径1（0|否1|是）";
    public static final String ALIAS_REGULATOR_OTH2 = "其他口径2（0|否1|是）";
    public static final String ALIAS_RELATED_TRAN_TYPE = "关联交易类别";
    public static final String ALIAS_TRAN_OMP_TIME = "交易完成时间";
    public static final String ALIAS_TRAN_TARGET = "tranTarget";
    public static final String ALIAS_PRICING_STRATEGY = "定价策略";
    public static final String ALIAS_TRAN_SCOPE = "授信范围";
    public static final String ALIAS_REMARK = "备注";
    public static final String ALIAS_TRAN_DATE = "交易日期";
    public static final String ALIAS_IMPORTANT_TRADE = "是否重大关联交易";
    public static final String ALIAS_PRODUCT_CODE = "产品编码";
    public static final String ALIAS_INTERNAL_APPLY_NO = "银行内部授信审批编号";
    public static final String ALIAS_RATE = "年利率/费率%";
    public static final String ALIAS_APPLY_TIME = "授信期限";
    public static final String ALIAS_APPLY_OVER_TIME = "授信批准日期";
    public static final String ALIAS_REGULATOR = "统计口径";
    public static final String ALIAS_CREDIT_PLAN = "授信方案";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_VOTING_PROPORTION = "表决权占比";
    public static final String ALIAS_DATA_RESOURCE = "数据来源";
    public static final String ALIAS_BZR_IDENTIFY_TYPE = "保证人证件类型";
    public static final String ALIAS_BZR_IDENT_NUMBER = "保证人证件号码";
    public static final String ALIAS_IS_INSIDER = "是否内部人";
    public static final String ALIAS_SHAREHOLDING_PROPORTION = "持股占比";
    public static final String ALIAS_VOTE_PROPORTION = "表决权占比";
    public static final String ALIAS_IS_LETTER = "是否为用信业务";
    public static final String ALIAS_TRA_PRICE_TYPE = "交易价格类型";
    public static final String ALIAS_RATE_TYPE = "利率类型";
    public static final String ALIAS_FLOAT_RATE_BENCH = "浮动利率基准";
    public static final String ALIAS_TRA_PRICE = "交易价格";
    public static final String ALIAS_FLOAT_INTEREST_RATE_SPREAD = "浮动利率利差";
    public static final String ALIAS_FLOAT_RATE_DISCOUNT_RATE = "浮动利率折扣率";
    public static final String ALIAS_TRA_CONTRACT_AMOUNT = "交易合同金额";
    public static final String ALIAS_CREDIT_NUMBER = "授信编号";
    public static final String ALIAS_CREDIT_LIMIT = "用信额度";
    public static final String ALIAS_BUS_TRA_BALANCE = "业务交易余额";
    public static final String ALIAS_CREDIT_BALANCE = "用信余额";
    public static final String ALIAS_IS_EFFECTIVE = "是否生效";
    public static final String ALIAS_THIS_TRA_AMOUNT = "本笔交易金额";
    public static final String ALIAS_THIS_TRA_BALANCE = "本笔交易余额";
    public static final String ALIAS_THIS_TRA_DIFF = "本笔交易差额";
    public static final String ALIAS_PRODUCT_NAME = "产品名称";
    public static final String ALIAS_TYPE_ONE_BUS = "一级业务种类";
    public static final String ALIAS_TYPE_TWO_BUS = "二级业务种类";
    public static final String ALIAS_TYPE_THREE_BUS = "三级业务种类";
    public static final String ALIAS_TYPE_FOUR_BUS = "四级业务种类";
    public static final String ALIAS_TABLE_ATTR_BUS = "业务表内外属性";
    public static final String ALIAS_BUS_LINE = "业务条线";
    public static final String ALIAS_TRA_APPROVE_TIME = "业务批准时间";
    public static final String ALIAS_FOREIGN_CURRENCY = "外币结算金额";

    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "trans_num", nullable = true, length = 100)
    private String transNum;

    @Column(name = "rel_id", nullable = true, length = 10)
    private Integer relId;

    @Column(name = "rel_type", nullable = true, length = 12)
    private String relType;

    @Column(name = "related_name", nullable = true, length = 20)
    private String relatedName;

    @Column(name = "manage_org", nullable = true, length = 100)
    private String manageOrg;

    @Column(name = "identify_type", nullable = true, length = 100)
    private String identifyType;

    @Column(name = "ident_number", nullable = true, length = 100)
    private String identNumber;

    @Column(name = "tran_type", nullable = true, length = 50)
    private String tranType;

    @Column(name = "approval_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date approvalTime;

    @Column(name = "contract_no", nullable = true, length = 50)
    private String contractNo;

    @Column(name = "contract_sign_date", nullable = true, length = 11)
    private String contractSignDate;

    @Column(name = "tran_price", nullable = true, length = 16)
    private String tranPrice;

    @Column(name = "bz", nullable = true, length = 16)
    private String bz;

    @Column(name = "validity_contract", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date validityContract;

    @Column(name = "status", nullable = true, length = 20)
    private String status;

    @Column(name = "credit_amount", nullable = true, precision = 10, scale = 0)
    private Long creditAmount;

    @Column(name = "validity_balance", nullable = true, precision = 10, scale = 0)
    private Long validityBalance;

    @Column(name = "credit_start_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date creditStartDate;

    @Column(name = "credit_end_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date creditEndDate;

    @Column(name = "guara_mode", nullable = true, length = 100)
    private String guaraMode;

    @Column(name = "collateral", nullable = true, length = 100)
    private String collateral;

    @Column(name = "guarantor", nullable = true, length = 100)
    private String guarantor;

    @Column(name = "five_level", nullable = true, length = 100)
    private String fiveLevel;

    @Column(name = "securit_deposit", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal securitDeposit;

    @Column(name = "margin_ratio", nullable = true, length = 100)
    private Float marginRatio;

    @Column(name = "bank_deposit", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal bankDeposit;

    @Column(name = "national_debt", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal nationalDebt;

    @Column(name = "business_type", nullable = true, length = 21)
    private String businessType;

    @Column(name = "is_del", nullable = true, length = 10)
    private Integer isDel;

    @Column(name = "regulator_ybj", nullable = true, length = 50)
    private String regulatorYbj;

    @Column(name = "regulator_zjh", nullable = true, length = 50)
    private String regulatorZjh;

    @Column(name = "regulator_czb", nullable = true, length = 50)
    private String regulatorCzb;

    @Column(name = "regulator_oth1", nullable = true, length = 50)
    private String regulatorOth1;

    @Column(name = "regulator_oth2", nullable = true, length = 50)
    private String regulatorOth2;

    @Column(name = "related_tran_type", nullable = true, length = 50)
    private String relatedTranType;

    @Column(name = "tran_omp_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date tranOmpTime;

    @Column(name = "tran_target", nullable = true, length = 255)
    private String tranTarget;

    @Column(name = "pricing_strategy", nullable = true, length = 1204)
    private String pricingStrategy;

    @Column(name = "tran_scope", nullable = true, length = 100)
    private String tranScope;

    @Column(name = "remark", nullable = true, length = 64)
    private String remark;

    @Column(name = "tran_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date tranDate;

    @Column(name = "important_trade", nullable = true, length = 1)
    private String importantTrade;

    @Column(name = "product_code", nullable = true, length = 20)
    private String productCode;

    @Column(name = "internal_apply_no", nullable = true, length = 20)
    private String internalApplyNo;

    @Column(name = "rate", nullable = true, length = 10)
    private String rate;

    @Column(name = "apply_time", nullable = true, length = 10)
    private String applyTime;

    @Column(name = "apply_over_time", nullable = true, length = 11)
    private String applyOverTime;

    @Column(name = "regulator", nullable = true, length = 20)
    private String regulator;

    @Column(name = "credit_plan", nullable = true, length = 100)
    private String creditPlan;

    @Column(name = "status_comment", nullable = true, length = 32)
    private String statusComment;

    @Column(name = "voting_proportion", nullable = true, length = 10)
    private String votingProportion;

    @Column(name = "data_resource", nullable = true, length = 10)
    private String dataResource;

    @Column(name = "BZR_IDENTIFY_TYPE", nullable = true, length = 100)
    private String bzrIdentifyType;

    @Column(name = "BZR_IDENT_NUMBER", nullable = true, length = 100)
    private String bzrIdentNumber;

    @Column(name = "is_insider", nullable = true, length = 1)
    private String isInsider;

    @Column(name = "shareholding_proportion", nullable = true, length = 10)
    private String shareholdingProportion;

    @Column(name = "vote_proportion", nullable = true, length = 10)
    private String voteProportion;

    @Column(name = "isLetter", nullable = true, length = 10)
    private String isLetter;

    @Column(name = "tra_price_type", nullable = true, length = 50)
    private String traPriceType;

    @Column(name = "rate_type", nullable = true, length = 50)
    private String rateType;

    @Column(name = "float_rate_bench", nullable = true, length = 50)
    private String floatRateBench;

    @Column(name = "tra_price", nullable = true, length = 50)
    private String traPrice;

    @Column(name = "float_interest_rate_spread", nullable = true, length = 50)
    private String floatInterestRateSpread;

    @Column(name = "float_rate_discount_rate", nullable = true, length = 50)
    private String floatRateDiscountRate;

    @Column(name = "tra_contract_amount", nullable = true, length = 50)
    private String traContractAmount;

    @Column(name = "credit_number", nullable = true, length = 50)
    private String creditNumber;

    @Column(name = "credit_limit", nullable = true, length = 50)
    private String creditLimit;

    @Column(name = "bus_tra_balance", nullable = true, length = 50)
    private String busTraBalance;

    @Column(name = "credit_balance", nullable = true, length = 50)
    private BigDecimal creditBalance;

    @Column(name = "is_effective", nullable = true, length = 10)
    private String isEffective;

    @Column(name = "this_tra_amount", nullable = true, length = 10)
    private BigDecimal thisTraAmount;

    @Column(name = "this_tra_balance", nullable = true, length = 10)
    private BigDecimal thisTraBalance;

    @Column(name = "this_tra_diff", nullable = true, length = 10)
    private BigDecimal thisTraDiff;

    @Column(name = "product_name", nullable = true, length = 10)
    private String productName;

    @Column(name = "type_one_bus", nullable = true, length = 10)
    private String typeOneBus;

    @Column(name = "type_two_bus", nullable = true, length = 10)
    private String typeTwoBus;

    @Column(name = "type_three_bus", nullable = true, length = 10)
    private String typeThreeBus;

    @Column(name = "type_four_bus", nullable = true, length = 10)
    private String typeFourBus;

    @Column(name = "table_attr_bus", nullable = true, length = 10)
    private String tableAttrBus;

    @Column(name = "bus_line", nullable = true, length = 10)
    private String busLine;

    @Column(name = "tra_approve_time", nullable = true, length = 10)
    private String traApproveTime;

    @Column(name = "foreign_currency", nullable = true, length = 10)
    private String foreignCurrency;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getTransNum() {
        return this.transNum;
    }

    public void setTransNum(String value) {
        this.transNum = value;
    }


    public Integer getRelId() {
        return this.relId;
    }

    public void setRelId(Integer value) {
        this.relId = value;
    }


    public String getRelType() {
        return this.relType;
    }

    public void setRelType(String value) {
        this.relType = value;
    }


    public String getRelatedName() {
        return this.relatedName;
    }

    public void setRelatedName(String value) {
        this.relatedName = value;
    }


    public String getManageOrg() {
        return this.manageOrg;
    }

    public void setManageOrg(String value) {
        this.manageOrg = value;
    }


    public String getIdentifyType() {
        return this.identifyType;
    }

    public void setIdentifyType(String value) {
        this.identifyType = value;
    }


    public String getIdentNumber() {
        return this.identNumber;
    }

    public void setIdentNumber(String value) {
        this.identNumber = value;
    }


    public String getTranType() {
        return this.tranType;
    }

    public void setTranType(String value) {
        this.tranType = value;
    }


    public Date getApprovalTime() {
        return this.approvalTime;
    }

    public void setApprovalTime(Date value) {
        this.approvalTime = value;
    }


    public String getContractNo() {
        return this.contractNo;
    }

    public void setContractNo(String value) {
        this.contractNo = value;
    }


    public String getContractSignDate() {
        return this.contractSignDate;
    }

    public void setContractSignDate(String value) {
        this.contractSignDate = value;
    }


    public String getTranPrice() {
        return this.tranPrice;
    }

    public void setTranPrice(String value) {
        this.tranPrice = value;
    }


    public String getBz() {
        return this.bz;
    }

    public void setBz(String value) {
        this.bz = value;
    }


    public Date getValidityContract() {
        return this.validityContract;
    }

    public void setValidityContract(Date value) {
        this.validityContract = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public Long getCreditAmount() {
        return this.creditAmount;
    }

    public void setCreditAmount(Long value) {
        this.creditAmount = value;
    }


    public Long getValidityBalance() {
        return this.validityBalance;
    }

    public void setValidityBalance(Long value) {
        this.validityBalance = value;
    }


    public Date getCreditStartDate() {
        return this.creditStartDate;
    }

    public void setCreditStartDate(Date value) {
        this.creditStartDate = value;
    }


    public Date getCreditEndDate() {
        return this.creditEndDate;
    }

    public void setCreditEndDate(Date value) {
        this.creditEndDate = value;
    }


    public String getGuaraMode() {
        return this.guaraMode;
    }

    public void setGuaraMode(String value) {
        this.guaraMode = value;
    }


    public String getCollateral() {
        return this.collateral;
    }

    public void setCollateral(String value) {
        this.collateral = value;
    }


    public String getGuarantor() {
        return this.guarantor;
    }

    public void setGuarantor(String value) {
        this.guarantor = value;
    }


    public String getFiveLevel() {
        return this.fiveLevel;
    }

    public void setFiveLevel(String value) {
        this.fiveLevel = value;
    }


    public java.math.BigDecimal getSecuritDeposit() {
        return this.securitDeposit;
    }

    public void setSecuritDeposit(java.math.BigDecimal value) {
        this.securitDeposit = value;
    }


    public Float getMarginRatio() {
        return this.marginRatio;
    }

    public void setMarginRatio(Float value) {
        this.marginRatio = value;
    }


    public java.math.BigDecimal getBankDeposit() {
        return this.bankDeposit;
    }

    public void setBankDeposit(java.math.BigDecimal value) {
        this.bankDeposit = value;
    }


    public java.math.BigDecimal getNationalDebt() {
        return this.nationalDebt;
    }

    public void setNationalDebt(java.math.BigDecimal value) {
        this.nationalDebt = value;
    }


    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String value) {
        this.businessType = value;
    }


    public Integer getIsDel() {
        return this.isDel;
    }

    public void setIsDel(Integer value) {
        this.isDel = value;
    }


    public String getRegulatorYbj() {
        return this.regulatorYbj;
    }

    public void setRegulatorYbj(String value) {
        this.regulatorYbj = value;
    }


    public String getRegulatorZjh() {
        return this.regulatorZjh;
    }

    public void setRegulatorZjh(String value) {
        this.regulatorZjh = value;
    }


    public String getRegulatorCzb() {
        return this.regulatorCzb;
    }

    public void setRegulatorCzb(String value) {
        this.regulatorCzb = value;
    }


    public String getRegulatorOth1() {
        return this.regulatorOth1;
    }

    public void setRegulatorOth1(String value) {
        this.regulatorOth1 = value;
    }


    public String getRegulatorOth2() {
        return this.regulatorOth2;
    }

    public void setRegulatorOth2(String value) {
        this.regulatorOth2 = value;
    }


    public String getRelatedTranType() {
        return this.relatedTranType;
    }

    public void setRelatedTranType(String value) {
        this.relatedTranType = value;
    }


    public Date getTranOmpTime() {
        return this.tranOmpTime;
    }

    public void setTranOmpTime(Date value) {
        this.tranOmpTime = value;
    }


    public String getTranTarget() {
        return this.tranTarget;
    }

    public void setTranTarget(String value) {
        this.tranTarget = value;
    }


    public String getPricingStrategy() {
        return this.pricingStrategy;
    }

    public void setPricingStrategy(String value) {
        this.pricingStrategy = value;
    }


    public String getTranScope() {
        return this.tranScope;
    }

    public void setTranScope(String value) {
        this.tranScope = value;
    }


    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }


    public Date getTranDate() {
        return this.tranDate;
    }

    public void setTranDate(Date value) {
        this.tranDate = value;
    }


    public String getImportantTrade() {
        return this.importantTrade;
    }

    public void setImportantTrade(String value) {
        this.importantTrade = value;
    }


    public String getProductCode() {
        return this.productCode;
    }

    public void setProductCode(String value) {
        this.productCode = value;
    }


    public String getInternalApplyNo() {
        return this.internalApplyNo;
    }

    public void setInternalApplyNo(String value) {
        this.internalApplyNo = value;
    }


    public String getRate() {
        return this.rate;
    }

    public void setRate(String value) {
        this.rate = value;
    }


    public String getApplyTime() {
        return this.applyTime;
    }

    public void setApplyTime(String value) {
        this.applyTime = value;
    }


    public String getApplyOverTime() {
        return this.applyOverTime;
    }

    public void setApplyOverTime(String value) {
        this.applyOverTime = value;
    }


    public String getRegulator() {
        return this.regulator;
    }

    public void setRegulator(String value) {
        this.regulator = value;
    }


    public String getCreditPlan() {
        return this.creditPlan;
    }

    public void setCreditPlan(String value) {
        this.creditPlan = value;
    }


    public String getStatusComment() {
        return this.statusComment;
    }

    public void setStatusComment(String value) {
        this.statusComment = value;
    }


    public String getVotingProportion() {
        return this.votingProportion;
    }

    public void setVotingProportion(String value) {
        this.votingProportion = value;
    }


    public String getDataResource() {
        return this.dataResource;
    }

    public void setDataResource(String value) {
        this.dataResource = value;
    }


    public String getBzrIdentifyType() {
        return this.bzrIdentifyType;
    }

    public void setBzrIdentifyType(String value) {
        this.bzrIdentifyType = value;
    }


    public String getBzrIdentNumber() {
        return this.bzrIdentNumber;
    }

    public void setBzrIdentNumber(String value) {
        this.bzrIdentNumber = value;
    }


    public String getIsInsider() {
        return this.isInsider;
    }

    public void setIsInsider(String value) {
        this.isInsider = value;
    }


    public String getShareholdingProportion() {
        return this.shareholdingProportion;
    }

    public void setShareholdingProportion(String value) {
        this.shareholdingProportion = value;
    }


    public String getVoteProportion() {
        return this.voteProportion;
    }

    public void setVoteProportion(String value) {
        this.voteProportion = value;
    }


    public String getIsLetter() {
        return this.isLetter;
    }

    public void setIsLetter(String value) {
        this.isLetter = value;
    }


    public String getTraPriceType() {
        return this.traPriceType;
    }

    public void setTraPriceType(String value) {
        this.traPriceType = value;
    }


    public String getRateType() {
        return this.rateType;
    }

    public void setRateType(String value) {
        this.rateType = value;
    }


    public String getFloatRateBench() {
        return this.floatRateBench;
    }

    public void setFloatRateBench(String value) {
        this.floatRateBench = value;
    }


    public String getTraPrice() {
        return this.traPrice;
    }

    public void setTraPrice(String value) {
        this.traPrice = value;
    }


    public String getFloatInterestRateSpread() {
        return this.floatInterestRateSpread;
    }

    public void setFloatInterestRateSpread(String value) {
        this.floatInterestRateSpread = value;
    }


    public String getFloatRateDiscountRate() {
        return this.floatRateDiscountRate;
    }

    public void setFloatRateDiscountRate(String value) {
        this.floatRateDiscountRate = value;
    }


    public String getTraContractAmount() {
        return this.traContractAmount;
    }

    public void setTraContractAmount(String value) {
        this.traContractAmount = value;
    }


    public String getCreditNumber() {
        return this.creditNumber;
    }

    public void setCreditNumber(String value) {
        this.creditNumber = value;
    }


    public String getCreditLimit() {
        return this.creditLimit;
    }

    public void setCreditLimit(String value) {
        this.creditLimit = value;
    }


    public String getBusTraBalance() {
        return this.busTraBalance;
    }

    public void setBusTraBalance(String value) {
        this.busTraBalance = value;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }

    public BigDecimal getThisTraAmount() {
        return thisTraAmount;
    }

    public void setThisTraAmount(BigDecimal thisTraAmount) {
        this.thisTraAmount = thisTraAmount;
    }

    public BigDecimal getThisTraBalance() {
        return thisTraBalance;
    }

    public void setThisTraBalance(BigDecimal thisTraBalance) {
        this.thisTraBalance = thisTraBalance;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTypeOneBus() {
        return typeOneBus;
    }

    public void setTypeOneBus(String typeOneBus) {
        this.typeOneBus = typeOneBus;
    }

    public String getTypeTwoBus() {
        return typeTwoBus;
    }

    public void setTypeTwoBus(String typeTwoBus) {
        this.typeTwoBus = typeTwoBus;
    }

    public String getTypeThreeBus() {
        return typeThreeBus;
    }

    public void setTypeThreeBus(String typeThreeBus) {
        this.typeThreeBus = typeThreeBus;
    }

    public String getTypeFourBus() {
        return typeFourBus;
    }

    public void setTypeFourBus(String typeFourBus) {
        this.typeFourBus = typeFourBus;
    }

    public String getTableAttrBus() {
        return tableAttrBus;
    }

    public void setTableAttrBus(String tableAttrBus) {
        this.tableAttrBus = tableAttrBus;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public BigDecimal getThisTraDiff() {
        return thisTraDiff;
    }

    public void setThisTraDiff(BigDecimal thisTraDiff) {
        this.thisTraDiff = thisTraDiff;
    }

    public String getTraApproveTime() {
        return traApproveTime;
    }

    public void setTraApproveTime(String traApproveTime) {
        this.traApproveTime = traApproveTime;
    }

    public String getForeignCurrency() {
        return foreignCurrency;
    }

    public void setForeignCurrency(String foreignCurrency) {
        this.foreignCurrency = foreignCurrency;
    }

    @Override
    public String toString() {
        return new StringBuilder("traCredit[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("relId = ").append(relId).append(", \n")
                .append("relType = ").append(relType).append(", \n")
                .append("relatedName = ").append(relatedName).append(", \n")
                .append("manageOrg = ").append(manageOrg).append(", \n")
                .append("identifyType = ").append(identifyType).append(", \n")
                .append("identNumber = ").append(identNumber).append(", \n")
                .append("tranType = ").append(tranType).append(", \n")
                .append("approvalTime = ").append(approvalTime).append(", \n")
                .append("contractNo = ").append(contractNo).append(", \n")
                .append("contractSignDate = ").append(contractSignDate).append(", \n")
                .append("tranPrice = ").append(tranPrice).append(", \n")
                .append("bz = ").append(bz).append(", \n")
                .append("validityContract = ").append(validityContract).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("creditAmount = ").append(creditAmount).append(", \n")
                .append("validityBalance = ").append(validityBalance).append(", \n")
                .append("creditStartDate = ").append(creditStartDate).append(", \n")
                .append("creditEndDate = ").append(creditEndDate).append(", \n")
                .append("guaraMode = ").append(guaraMode).append(", \n")
                .append("collateral = ").append(collateral).append(", \n")
                .append("guarantor = ").append(guarantor).append(", \n")
                .append("fiveLevel = ").append(fiveLevel).append(", \n")
                .append("securitDeposit = ").append(securitDeposit).append(", \n")
                .append("marginRatio = ").append(marginRatio).append(", \n")
                .append("bankDeposit = ").append(bankDeposit).append(", \n")
                .append("nationalDebt = ").append(nationalDebt).append(", \n")
                .append("businessType = ").append(businessType).append(", \n")
                .append("isDel = ").append(isDel).append(", \n")
                .append("regulatorYbj = ").append(regulatorYbj).append(", \n")
                .append("regulatorZjh = ").append(regulatorZjh).append(", \n")
                .append("regulatorCzb = ").append(regulatorCzb).append(", \n")
                .append("regulatorOth1 = ").append(regulatorOth1).append(", \n")
                .append("regulatorOth2 = ").append(regulatorOth2).append(", \n")
                .append("relatedTranType = ").append(relatedTranType).append(", \n")
                .append("tranOmpTime = ").append(tranOmpTime).append(", \n")
                .append("tranTarget = ").append(tranTarget).append(", \n")
                .append("pricingStrategy = ").append(pricingStrategy).append(", \n")
                .append("tranScope = ").append(tranScope).append(", \n")
                .append("remark = ").append(remark).append(", \n")
                .append("tranDate = ").append(tranDate).append(", \n")
                .append("importantTrade = ").append(importantTrade).append(", \n")
                .append("productCode = ").append(productCode).append(", \n")
                .append("internalApplyNo = ").append(internalApplyNo).append(", \n")
                .append("rate = ").append(rate).append(", \n")
                .append("applyTime = ").append(applyTime).append(", \n")
                .append("applyOverTime = ").append(applyOverTime).append(", \n")
                .append("regulator = ").append(regulator).append(", \n")
                .append("creditPlan = ").append(creditPlan).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("votingProportion = ").append(votingProportion).append(", \n")
                .append("dataResource = ").append(dataResource).append(", \n")
                .append("bzrIdentifyType = ").append(bzrIdentifyType).append(", \n")
                .append("bzrIdentNumber = ").append(bzrIdentNumber).append(", \n")
                .append("isInsider = ").append(isInsider).append(", \n")
                .append("shareholdingProportion = ").append(shareholdingProportion).append(", \n")
                .append("voteProportion = ").append(voteProportion).append(", \n")
                .append("isLetter = ").append(isLetter).append(", \n")
                .append("traPriceType = ").append(traPriceType).append(", \n")
                .append("rateType = ").append(rateType).append(", \n")
                .append("floatRateBench = ").append(floatRateBench).append(", \n")
                .append("traPrice = ").append(traPrice).append(", \n")
                .append("floatInterestRateSpread = ").append(floatInterestRateSpread).append(", \n")
                .append("floatRateDiscountRate = ").append(floatRateDiscountRate).append(", \n")
                .append("traContractAmount = ").append(traContractAmount).append(", \n")
                .append("creditNumber = ").append(creditNumber).append(", \n")
                .append("creditLimit = ").append(creditLimit).append(", \n")
                .append("busTraBalance = ").append(busTraBalance).append(", \n")
                .append("creditBalance = ").append(creditBalance).append(", \n")
                .append("isEffective = ").append(isEffective).append(", \n")
                .append("thisTraAmount = ").append(thisTraAmount).append(", \n")
                .append("thisTraBalance = ").append(thisTraBalance).append(", \n")
                .append("thisTraDiff = ").append(thisTraDiff).append(", \n")
                .append("productName = ").append(productName).append(", \n")
                .append("typeOneBus = ").append(typeOneBus).append(", \n")
                .append("typeTwoBus = ").append(typeTwoBus).append(", \n")
                .append("typeThreeBus = ").append(typeThreeBus).append(", \n")
                .append("typeFourBus = ").append(typeFourBus).append(", \n")
                .append("tableAttrBus = ").append(tableAttrBus).append(", \n")
                .append("busLine = ").append(busLine).append(", \n")
                .append("traApproveTime = ").append(traApproveTime).append(", \n")
                .append("foreignCurrency = ").append(foreignCurrency).append(", \n")
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
        if (obj instanceof TraCredit == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraCredit other = (TraCredit) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

