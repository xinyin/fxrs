/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:50
 *
 */

package com.yzj.jep.app.ins.model;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "ins_tra_funds")
public class TraFundsVo extends BasePO {

    public static final String TABLE_ALIAS = "关联交易表（保险）-资金运用类";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_REL_ID = "关联方ID";
    public static final String ALIAS_REL_TYPE = "关联方类型";
    public static final String ALIAS_REL_NAME = "关联方名称";
    public static final String ALIAS_REL_NUMBER = "关联方证件号码";
    public static final String ALIAS_TRADE_ORG = "交易机构";
    public static final String ALIAS_TRAN_TYPE = "交易类型";
    public static final String ALIAS_DATA_SOURCES = "数据来源";
    public static final String ALIAS_TRAN_DATE = "交易日期";
    public static final String ALIAS_MAJOR_TRADE = "是否重大关联交易";
    public static final String ALIAS_CALIBER = "口径";
    public static final String ALIAS_DEPOSIT_TERMS = "存款期限";
    public static final String ALIAS_STATUS = "状态";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_IS_DEAL_PRICE = "交易定价是否为国家规定";
    public static final String ALIAS_TOTOL_TRADE_AMOUNT = "本年度与该关联方发生的关联交易累计金额（万元）";
    public static final String ALIAS_CONTRACT_NO = "合同编号";
    public static final String ALIAS_TRADE_AMOUNT = "交易金额（万元）";
    public static final String ALIAS_BUS_START_TIME = "业务起始日期";
    public static final String ALIAS_BUS_END_TIME = "业务终止日期";
    public static final String ALIAS_INVESTMENT_TYPE = "投资类别";
    public static final String ALIAS_INVESTMENT_METHOD = "投资方式";
    public static final String ALIAS_INVESTED_ENTERPRISES = "被投资企业情况";
    public static final String ALIAS_EQUITY_LAST_TRADE_PRICE = "未上市权益类_前次交易价格";
    public static final String ALIAS_IM_LAST_TRADE_PRICE = "不动产类_前次交易价格";
    public static final String ALIAS_IM_SUBJECT_MATTER = "不动产类_标的情况";
    public static final String ALIAS_SUBJECT_MATTER = "境外投资类_标的情况";
    public static final String ALIAS_SHARE_DEBT_RATIO = "股债比";
    public static final String ALIAS_FINANCIAL_ASSSTS_TYPE = "金融资产类别";
    public static final String ALIAS_FINANCIAL_ASSSTS_NUM = "金融资产编号";
    public static final String ALIAS_FINANCIAL_PRO_SCALE = "金融产品发行规模";
    public static final String ALIAS_FINANCIAL_PRO_RATIO = "购买金融产品份额占比（%）";
    public static final String ALIAS_UNDERLYING_ASSET = "底层资产";
    public static final String ALIAS_UNDER_IS_REL = "底层资产是否为关联方";
    public static final String ALIAS_UNDER_INVOLVE_REL = "底层基础资产是否涉及控股股东或控股股东的关联方";
    public static final String ALIAS_UNDER_REL = "底层资产关联关系";
    public static final String ALIAS_INVESTED_ENTERPRISES_NAME = "被投企业名称";
    public static final String ALIAS_BUS_MODEL = "业务模式";
    public static final String ALIAS_EARNINGS_EXPECTAIONS = "盈利预期";
    public static final String ALIAS_INVESTMENT_RATIO = "投资比例（%）";
    public static final String ALIAS_INVESTMENT_ALLOCATION = "投资收益分配情况";
    public static final String ALIAS_LOSS_SHARE = "损失分担情况";
    public static final String ALIAS_TRADE_PRICE = "交易价格";
    public static final String ALIAS_PRICING_POLICY = "定价政策";
    public static final String ALIAS_TRADE_SUBJECT = "交易标的";
    public static final String ALIAS_REMARK = "其他事项说明";
    public static final String ALIAS_TRANS_NUM = "交易编号";
    public static final String ALIAS_IS_EFFCTIVE = "是否生效";
    public static final String ALIAS_APPROVE_TRADE_AMOUNT = "审批金额";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    private String no;

    @Id
    @Column(name = "PID", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "REL_ID", nullable = true, length = 10)
    private Integer relId;

    @Column(name = "REL_TYPE", nullable = true, length = 12)
    private String relType;

    @Column(name = "REL_NAME", nullable = true, length = 20)
    private String relName;

    @Column(name = "REL_NUMBER", nullable = true, length = 100)
    private String relNumber;

    @Column(name = "TRADE_ORG", nullable = true, length = 100)
    private String tradeOrg;

    @Column(name = "TRAN_TYPE", nullable = true, length = 50)
    private String tranType;

    @Column(name = "DATA_SOURCES", nullable = true, length = 10)
    private String dataSources;

    @Column(name = "TRAN_DATE", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date tranDate;

    @Transient
    private String tranDateString;

    @Column(name = "MAJOR_TRADE", nullable = true, length = 10)
    private String majorTrade;

    @Column(name = "CALIBER", nullable = true, length = 50)
    private String caliber;

    @Column(name = "DEPOSIT_TERMS", nullable = true, length = 50)
    private String depositTerms;

    @Column(name = "STATUS", nullable = true, length = 10)
    private String status;

    @Column(name = "STATUS_COMMENT", nullable = true, length = 100)
    private String statusComment;

    @Column(name = "IS_DEAL_PRICE", nullable = true, length = 5)
    private String isDealPrice;

    @Column(name = "TOTOL_TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private String totolTradeAmount;

    @Column(name = "CONTRACT_NO", nullable = true, length = 50)
    private String contractNo;

    @Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private String tradeAmount;

    @Column(name = "BUS_START_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date busStartTime;

    @Transient
    private String busStartTimeString;

    @Column(name = "BUS_END_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date busEndTime;

    @Transient
    private String busEndTimeString;

    @Column(name = "INVESTMENT_TYPE", nullable = true, length = 10)
    private String investmentType;

    @Column(name = "INVESTMENT_METHOD", nullable = true, length = 10)
    private String investmentMethod;

    @Column(name = "INVESTED_ENTERPRISES", nullable = true, length = 50)
    private String investedEnterprises;

    @Column(name = "EQUITY_LAST_TRADE_PRICE", nullable = true, precision = 10, scale = 0)
    private String equityLastTradePrice;

    @Column(name = "IM_LAST_TRADE_PRICE", nullable = true, precision = 10, scale = 0)
    private String imLastTradePrice;

    @Column(name = "IM_SUBJECT_MATTER", nullable = true, length = 50)
    private String imSubjectMatter;

    @Column(name = "OUT_SUBJECT_MATTER", nullable = true, length = 50)
    private String outSubjectMatter;

    @Column(name = "SHARE_DEBT_RATIO", nullable = true, length = 50)
    private String shareDebtRatio;

    @Column(name = "FINANCIAL_ASSSTS_TYPE", nullable = true, length = 50)
    private String financialAssstsType;

    @Column(name = "FINANCIAL_ASSSTS_NUM", nullable = true, length = 50)
    private String financialAssstsNum;

    @Column(name = "FINANCIAL_PRO_SCALE", nullable = true, length = 50)
    private String financialProScale;

    @Column(name = "FINANCIAL_PRO_RATIO", nullable = true, length = 50)
    private String financialProRatio;

    @Column(name = "UNDERLYING_ASSET", nullable = true, length = 50)
    private String underlyingAsset;

    @Column(name = "UNDER_IS_REL", nullable = true, length = 10)
    private String underIsRel;

    @Column(name = "UNDER_INVOLVE_REL", nullable = true, length = 10)
    private String underInvolveRel;

    @Column(name = "UNDER_REL", nullable = true, length = 50)
    private String underRel;

    @Column(name = "INVESTED_ENTERPRISES_NAME", nullable = true, length = 50)
    private String investedEnterprisesName;

    @Column(name = "BUS_MODEL", nullable = true, length = 50)
    private String busModel;

    @Column(name = "EARNINGS_EXPECTAIONS", nullable = true, length = 50)
    private String earningsExpectaions;

    @Column(name = "INVESTMENT_RATIO", nullable = true, length = 50)
    private String investmentRatio;

    @Column(name = "INVESTMENT_ALLOCATION", nullable = true, length = 50)
    private String investmentAllocation;

    @Column(name = "LOSS_SHARE", nullable = true, length = 50)
    private String lossShare;

    @Column(name = "TRADE_PRICE", nullable = true, length = 1000)
    private String tradePrice;

    @Column(name = "PRICING_POLICY", nullable = true, length = 1000)
    private String pricingPolicy;

    @Column(name = "TRADE_SUBJECT", nullable = true, length = 1000)
    private String tradeSubject;

    @Column(name = "REMARK", nullable = true, length = 1000)
    private String remark;

    @Column(name = "TRANS_NUM", nullable = true, length = 32)
    private String transNum;

    @Column(name = "IS_EFFECTIVE", nullable = true, length = 32)
    private String isEffective;

    @Column(name = "APPROVE_TRADE_AMOUNT", nullable = true, length = 32)
    private String approveTradeAmount;
    //columns END

    //达到重大关联交易的情况
    private String majorType;

    //金融产品名称
    private String financialAssstsName;
    //是否为控股子公司
    private String isHoldingSubsidiary;
    //控股子公司名称
    private String holdingSubsidiaryName;
    //控股子公司证件号码
    private String holdingSubsidiaryNumber;
    //是否境外投资
    private String isOverseasinvestment;
    //对财务状况及经营成果的影响
    private String financialImpact;
    //关联方总资产
    private String totalAssets;
    private String busType;

    private String tranName;//交易名称

    private BigDecimal relTradeAmount;//关联交易金额
    private String relTradeAmountString;//关联交易金额

    private String pracTradeAmount;//关联交易实际金额（万元）

    /**
     * 是否可免于审议
     */
    private String isconsiderSituation;

    /**
     * 免于审议情形
     */
    private String considerSituation;
    //来源系统唯一编码
    @Column(name = "unique_code", nullable = true, length = 100)
    private String uniqueCode;
    //来源系统
    @Column(name = "source_system", nullable = true, length = 60)
    private String sourceSystem;
    //来源模块
    @Column(name = "source_module", nullable = true, length = 60)
    private String sourceModule;
    //收付款标识
    @Column(name = "payment_id", nullable = true, length = 10)
    private String paymentId;
    //场景
    @Column(name = "scene", nullable = true, length = 10)
    private String scene;

    /**
     * 单笔关联交易类型
     */
    @Column(name = "single_related_trantype", nullable = true, length = 1)
    private String singleRelatedTranType;
    //审议类型
    @Column(name = "consider_type", nullable = true, length = 10)
    private String considerType;

    /**
     * 是否需逐笔报告
     */
    @Column(name = "each_report", nullable = true, length = 30)
    private String eachReport;
    /**
     * 是否需逐笔披露
     */
    @Column(name = "each_disclosure", nullable = true, length = 30)
    private String eachDisclosure;

    /**
     * 是否需要补录关联方信息
     */
    @Column(name = "is_collect_related", nullable = true, length = 1)
    private String isCollectRelated;

    // *********************************************************************************************************
    // 三期新增字段
    // 货币代码
    @Column(name = "currency_code", nullable = true, length = 100)
    private String currencyCode;

    // 交易金额（原币）
    @Column(name = "ORIGINAL_CURRENCY_AMOUNT", nullable = true, length = 10)
    private BigDecimal originalCurrencyAmount;

    private String originalCurrencyAmountString;

    // 资金流水编号
    @Column(name = "FUNDS_FLOW_NUMBER", nullable = true, length = 10)
    private String fundsFlowNumber;

    //协议交易标志
    @Column(name = "PROTOCOL_TRANSACTION_SYMBOL", nullable = true, length = 100)
    private String protocolTransactionSymbol;

    //合同状态
    @Column(name = "CONTRACT_STATUS", nullable = true, length = 100)
    private String contractStatus;

    // 数据版本
    @Column(name = "data_version", nullable = true, length = 64)
    private String dataVersion;

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getProtocolTransactionSymbol() {
        return protocolTransactionSymbol;
    }

    public void setProtocolTransactionSymbol(String protocolTransactionSymbol) {
        this.protocolTransactionSymbol = protocolTransactionSymbol;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getOriginalCurrencyAmount() {
        return originalCurrencyAmount;
    }

    public void setOriginalCurrencyAmount(BigDecimal originalCurrencyAmount) {
        this.originalCurrencyAmount = originalCurrencyAmount;
    }

    public String getOriginalCurrencyAmountString() {
        return originalCurrencyAmountString;
    }

    public void setOriginalCurrencyAmountString(String originalCurrencyAmountString) {
        this.originalCurrencyAmountString = originalCurrencyAmountString;
    }

    public String getFundsFlowNumber() {
        return fundsFlowNumber;
    }

    public void setFundsFlowNumber(String fundsFlowNumber) {
        this.fundsFlowNumber = fundsFlowNumber;
    }

    // *********************************************************************************************************

    public String getIsCollectRelated() {
        return isCollectRelated;
    }

    public void setIsCollectRelated(String isCollectRelated) {
        this.isCollectRelated = isCollectRelated;
    }

    public String getEachReport() {
        return eachReport;
    }

    public void setEachReport(String eachReport) {
        this.eachReport = eachReport;
    }

    public String getEachDisclosure() {
        return eachDisclosure;
    }

    public void setEachDisclosure(String eachDisclosure) {
        this.eachDisclosure = eachDisclosure;
    }

    public String getConsiderType() {
        return considerType;
    }

    public void setConsiderType(String considerType) {
        this.considerType = considerType;
    }

    public String getSingleRelatedTranType() {
        return singleRelatedTranType;
    }

    public void setSingleRelatedTranType(String singleRelatedTranType) {
        this.singleRelatedTranType = singleRelatedTranType;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getSourceModule() {
        return sourceModule;
    }

    public void setSourceModule(String sourceModule) {
        this.sourceModule = sourceModule;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }


    public String getIsHoldingSubsidiary() {
        return isHoldingSubsidiary;
    }

    public void setIsHoldingSubsidiary(String isHoldingSubsidiary) {
        this.isHoldingSubsidiary = isHoldingSubsidiary;
    }

    public String getHoldingSubsidiaryName() {
        return holdingSubsidiaryName;
    }

    public void setHoldingSubsidiaryName(String holdingSubsidiaryName) {
        this.holdingSubsidiaryName = holdingSubsidiaryName;
    }

    public String getHoldingSubsidiaryNumber() {
        return holdingSubsidiaryNumber;
    }

    public void setHoldingSubsidiaryNumber(String holdingSubsidiaryNumber) {
        this.holdingSubsidiaryNumber = holdingSubsidiaryNumber;
    }

    public String getIsOverseasinvestment() {
        return isOverseasinvestment;
    }

    public void setIsOverseasinvestment(String isOverseasinvestment) {
        this.isOverseasinvestment = isOverseasinvestment;
    }

    public String getFinancialImpact() {
        return financialImpact;
    }

    public void setFinancialImpact(String financialImpact) {
        this.financialImpact = financialImpact;
    }

    public String getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getFinancialAssstsName() {
        return financialAssstsName;
    }

    public void setFinancialAssstsName(String financialAssstsName) {
        this.financialAssstsName = financialAssstsName;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
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


    public String getRelName() {
        return this.relName;
    }

    public void setRelName(String value) {
        this.relName = value;
    }


    public String getRelNumber() {
        return this.relNumber;
    }

    public void setRelNumber(String value) {
        this.relNumber = value;
    }


    public String getTradeOrg() {
        return this.tradeOrg;
    }

    public void setTradeOrg(String value) {
        this.tradeOrg = value;
    }


    public String getTranType() {
        return this.tranType;
    }

    public void setTranType(String value) {
        this.tranType = value;
    }


    public String getDataSources() {
        return this.dataSources;
    }

    public void setDataSources(String value) {
        this.dataSources = value;
    }


    public Date getTranDate() {
        return this.tranDate;
    }

    public void setTranDate(Date value) {
        this.tranDate = value;
    }


    public String getMajorTrade() {
        return this.majorTrade;
    }

    public void setMajorTrade(String value) {
        this.majorTrade = value;
    }


    public String getCaliber() {
        return this.caliber;
    }

    public void setCaliber(String value) {
        this.caliber = value;
    }

    public String getDepositTerms() {
        return depositTerms;
    }

    public void setDepositTerms(String depositTerms) {
        this.depositTerms = depositTerms;
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

    public String getIsDealPrice() {
        return isDealPrice;
    }

    public void setIsDealPrice(String isDealPrice) {
        this.isDealPrice = isDealPrice;
    }

    public String getTotolTradeAmount() {
        return this.totolTradeAmount;
    }

    public void setTotolTradeAmount(String value) {
        this.totolTradeAmount = value;
    }


    public String getContractNo() {
        return this.contractNo;
    }

    public void setContractNo(String value) {
        this.contractNo = value;
    }


    public String getTradeAmount() {
        return this.tradeAmount;
    }

    public void setTradeAmount(String value) {
        this.tradeAmount = value;
    }


    public Date getBusStartTime() {
        return this.busStartTime;
    }

    public void setBusStartTime(Date value) {
        this.busStartTime = value;
    }


    public Date getBusEndTime() {
        return this.busEndTime;
    }

    public void setBusEndTime(Date value) {
        this.busEndTime = value;
    }


    public String getInvestmentType() {
        return this.investmentType;
    }

    public void setInvestmentType(String value) {
        this.investmentType = value;
    }


    public String getInvestmentMethod() {
        return this.investmentMethod;
    }

    public void setInvestmentMethod(String value) {
        this.investmentMethod = value;
    }


    public String getInvestedEnterprises() {
        return this.investedEnterprises;
    }

    public void setInvestedEnterprises(String value) {
        this.investedEnterprises = value;
    }

    public String getEquityLastTradePrice() {
        return equityLastTradePrice;
    }

    public void setEquityLastTradePrice(String equityLastTradePrice) {
        this.equityLastTradePrice = equityLastTradePrice;
    }

    public String getImLastTradePrice() {
        return imLastTradePrice;
    }

    public void setImLastTradePrice(String imLastTradePrice) {
        this.imLastTradePrice = imLastTradePrice;
    }

    public String getImSubjectMatter() {
        return imSubjectMatter;
    }

    public void setImSubjectMatter(String imSubjectMatter) {
        this.imSubjectMatter = imSubjectMatter;
    }

    public String getOutSubjectMatter() {
        return outSubjectMatter;
    }

    public void setOutSubjectMatter(String outSubjectMatter) {
        this.outSubjectMatter = outSubjectMatter;
    }

    public String getShareDebtRatio() {
        return this.shareDebtRatio;
    }

    public void setShareDebtRatio(String value) {
        this.shareDebtRatio = value;
    }


    public String getFinancialAssstsType() {
        return this.financialAssstsType;
    }

    public void setFinancialAssstsType(String value) {
        this.financialAssstsType = value;
    }


    public String getFinancialAssstsNum() {
        return this.financialAssstsNum;
    }

    public void setFinancialAssstsNum(String value) {
        this.financialAssstsNum = value;
    }


    public String getFinancialProScale() {
        return this.financialProScale;
    }

    public void setFinancialProScale(String value) {
        this.financialProScale = value;
    }


    public String getFinancialProRatio() {
        return this.financialProRatio;
    }

    public void setFinancialProRatio(String value) {
        this.financialProRatio = value;
    }


    public String getUnderlyingAsset() {
        return this.underlyingAsset;
    }

    public void setUnderlyingAsset(String value) {
        this.underlyingAsset = value;
    }


    public String getUnderIsRel() {
        return this.underIsRel;
    }

    public void setUnderIsRel(String value) {
        this.underIsRel = value;
    }


    public String getUnderInvolveRel() {
        return this.underInvolveRel;
    }

    public void setUnderInvolveRel(String value) {
        this.underInvolveRel = value;
    }


    public String getUnderRel() {
        return this.underRel;
    }

    public void setUnderRel(String value) {
        this.underRel = value;
    }


    public String getInvestedEnterprisesName() {
        return this.investedEnterprisesName;
    }

    public void setInvestedEnterprisesName(String value) {
        this.investedEnterprisesName = value;
    }


    public String getBusModel() {
        return this.busModel;
    }

    public void setBusModel(String value) {
        this.busModel = value;
    }


    public String getEarningsExpectaions() {
        return this.earningsExpectaions;
    }

    public void setEarningsExpectaions(String value) {
        this.earningsExpectaions = value;
    }


    public String getInvestmentRatio() {
        return this.investmentRatio;
    }

    public void setInvestmentRatio(String value) {
        this.investmentRatio = value;
    }


    public String getInvestmentAllocation() {
        return this.investmentAllocation;
    }

    public void setInvestmentAllocation(String value) {
        this.investmentAllocation = value;
    }


    public String getLossShare() {
        return this.lossShare;
    }

    public void setLossShare(String value) {
        this.lossShare = value;
    }


    public String getTradePrice() {
        return this.tradePrice;
    }

    public void setTradePrice(String value) {
        this.tradePrice = value;
    }


    public String getPricingPolicy() {
        return this.pricingPolicy;
    }

    public void setPricingPolicy(String value) {
        this.pricingPolicy = value;
    }


    public String getTradeSubject() {
        return this.tradeSubject;
    }

    public void setTradeSubject(String value) {
        this.tradeSubject = value;
    }


    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }


    public String getTransNum() {
        return this.transNum;
    }

    public void setTransNum(String value) {
        this.transNum = value;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
    }

    public String getApproveTradeAmount() {
        return approveTradeAmount;
    }

    public void setApproveTradeAmount(String approveTradeAmount) {
        this.approveTradeAmount = approveTradeAmount;
    }

    public String getTranDateString() {
        return tranDateString;
    }

    public String getBusStartTimeString() {
        return busStartTimeString;
    }

    public String getBusEndTimeString() {
        return busEndTimeString;
    }

    public void setTranDateString(String tranDateString) {
        this.tranDateString = tranDateString;
    }

    public void setBusStartTimeString(String busStartTimeString) {
        this.busStartTimeString = busStartTimeString;
    }

    public void setBusEndTimeString(String busEndTimeString) {
        this.busEndTimeString = busEndTimeString;
    }

    public String getConsiderSituation() {
        return considerSituation;
    }

    public void setConsiderSituation(String considerSituation) {
        this.considerSituation = considerSituation;
    }

    public String getIsconsiderSituation() {
        return isconsiderSituation;
    }

    public void setIsconsiderSituation(String isconsiderSituation) {
        this.isconsiderSituation = isconsiderSituation;
    }

    public String getPracTradeAmount() {
        return pracTradeAmount;
    }

    public void setPracTradeAmount(String pracTradeAmount) {
        this.pracTradeAmount = pracTradeAmount;
    }

    public String getRelTradeAmountString() {
        return relTradeAmountString;
    }

    public void setRelTradeAmountString(String relTradeAmountString) {
        this.relTradeAmountString = relTradeAmountString;
    }

    public BigDecimal getRelTradeAmount() {
        return relTradeAmount;
    }

    public void setRelTradeAmount(BigDecimal relTradeAmount) {
        this.relTradeAmount = relTradeAmount;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }


    @Override
    public String toString() {
        return new StringBuilder("traFunds[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("relId = ").append(relId).append(", \n")
                .append("relType = ").append(relType).append(", \n")
                .append("relName = ").append(relName).append(", \n")
                .append("relNumber = ").append(relNumber).append(", \n")
                .append("tradeOrg = ").append(tradeOrg).append(", \n")
                .append("tranType = ").append(tranType).append(", \n")
                .append("dataSources = ").append(dataSources).append(", \n")
                .append("tranDate = ").append(tranDate).append(", \n")
                .append("majorTrade = ").append(majorTrade).append(", \n")
                .append("caliber = ").append(caliber).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("totolTradeAmount = ").append(totolTradeAmount).append(", \n")
                .append("contractNo = ").append(contractNo).append(", \n")
                .append("tradeAmount = ").append(tradeAmount).append(", \n")
                .append("busStartTime = ").append(busStartTime).append(", \n")
                .append("busEndTime = ").append(busEndTime).append(", \n")
                .append("investmentType = ").append(investmentType).append(", \n")
                .append("investmentMethod = ").append(investmentMethod).append(", \n")
                .append("investedEnterprises = ").append(investedEnterprises).append(", \n")
                .append("equityLastTradePrice = ").append(equityLastTradePrice).append(", \n")
                .append("imLastTradePrice = ").append(imLastTradePrice).append(", \n")
                .append("imSubjectMatter = ").append(imSubjectMatter).append(", \n")
                .append("outSubjectMatter = ").append(outSubjectMatter).append(", \n")
                .append("shareDebtRatio = ").append(shareDebtRatio).append(", \n")
                .append("financialAssstsType = ").append(financialAssstsType).append(", \n")
                .append("financialAssstsNum = ").append(financialAssstsNum).append(", \n")
                .append("financialProScale = ").append(financialProScale).append(", \n")
                .append("financialProRatio = ").append(financialProRatio).append(", \n")
                .append("underlyingAsset = ").append(underlyingAsset).append(", \n")
                .append("underIsRel = ").append(underIsRel).append(", \n")
                .append("underInvolveRel = ").append(underInvolveRel).append(", \n")
                .append("underRel = ").append(underRel).append(", \n")
                .append("investedEnterprisesName = ").append(investedEnterprisesName).append(", \n")
                .append("busModel = ").append(busModel).append(", \n")
                .append("earningsExpectaions = ").append(earningsExpectaions).append(", \n")
                .append("investmentRatio = ").append(investmentRatio).append(", \n")
                .append("investmentAllocation = ").append(investmentAllocation).append(", \n")
                .append("lossShare = ").append(lossShare).append(", \n")
                .append("tradePrice = ").append(tradePrice).append(", \n")
                .append("pricingPolicy = ").append(pricingPolicy).append(", \n")
                .append("tradeSubject = ").append(tradeSubject).append(", \n")
                .append("remark = ").append(remark).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("isEffective = ").append(isEffective).append(", \n")
                .append("approveTradeAmount = ").append(approveTradeAmount).append(", \n")
                .append("tranName = ").append(tranName).append(", \n")
                .append("relTradeAmount = ").append(relTradeAmount).append(", \n")
                .append("pracTradeAmount = ").append(pracTradeAmount).append(", \n")
                .append("isconsiderSituation = ").append(isconsiderSituation).append(", \n")
                .append("considerSituation = ").append(considerSituation).append(", \n")
                .append("uniqueCode = ").append(uniqueCode).append(", \n")
                .append("sourceSystem = ").append(sourceSystem).append(", \n")
                .append("sourceModule = ").append(sourceModule).append(", \n")
                .append("paymentId = ").append(paymentId).append(", \n")
                .append("scene = ").append(scene).append(", \n")
                .append("singleRelatedTranType = ").append(singleRelatedTranType).append(", \n")
                .append("considerType = ").append(considerType).append(", \n")
                .append("eachReport = ").append(eachReport).append(", \n")
                .append("eachDisclosure = ").append(eachDisclosure).append(", \n")
                .append("isCollectRelated = ").append(isCollectRelated).append(", \n")
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
        if (obj instanceof TraFundsVo == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraFundsVo other = (TraFundsVo) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusType() {
        return busType;
    }
}

