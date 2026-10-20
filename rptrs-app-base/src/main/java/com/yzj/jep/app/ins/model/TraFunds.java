/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:50
 *
 */

package com.yzj.jep.app.ins.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "ins_tra_funds")
public class TraFunds extends BasePO {

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
    public static final String ALIAS_SUBJECT_MATTER = "银行存款_标的情况";
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
    public static final String ALIAS_RULE_RESULT = "规则结果";
    public static final String ALIAS_PRODUCT_CODE = "产品代码";
    public static final String ALIAS_PRODUCT_NAME = "产品名称";
    public static final String ALIAS_CONSIDER_TYPE = "审议类型(1:可免予审议，2:可集中审议，3逐笔审议)";
    public static final String ALIAS_IS_HOLDINGSUBSIDIARY = "是否为控股子公司";
    public static final String ALIAS_HOLDINGSUBSIDIARY_NAME = "控股子公司名称";
    public static final String ALIAS_HOLDINGSUBSIDIARY_NUMBER = "控股子公司证件号码";
    public static final String ALIAS_TOTALASSETS = "关联方总资产";
    public static final String ALIAS_ISOVERSEASINVESTMENT = "是否境外投资";
    public static final String ALIAS_FINANCIAL_IMPACT = "对财务状况及经营成果的影响";
    public static final String ALIAS_TRA_SUB_NAME = "交易主体名称";
    public static final String ALIAS_MAJOR_TYPE = "达到重大关联交易的情形";
    public static final String ALIAS_CONTRACT_AMOUNT = "合同金额";
    public static final String ALIAS_FINANCIAL_ASSSTS_NAME = "金融产品名称";
    public static final String ALIAS_STOCKRIGHT_TYPE = "股权分类";
    public static final String ALIAS_AMOUNT_RECEIVED = "累计回款金额";
    public static final String ALIAS_AMEND_ID = "操作人id";
    public static final String ALIAS_AMEND_NAME = "操作人姓名";
    public static final String ALIAS_TRAN_NAME = "交易名称";
    public static final String ALIAS_TRAN_OPPONENT = "交易对手";
    public static final String ALIAS_CERT_TYPE = "证件类型";

    public static final String ALIAS_MANAGER_ORG = "管理机构";
    public static final String ALIAS_ACCU_PRAC_TRADE_AMOUNT = "累计实际交易金额（万元）";
    public static final String ALIAS_REL_TRADE_AMOUNT = "关联交易金额";
    public static final String ALIAS_PRAC_TRADE_AMOUNT = "实际交易金额";
    public static final String ALIAS_TOTALASSETS_LATE_LAST = "关联方上年末总资产";




    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
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
    private java.math.BigDecimal totolTradeAmount;
    @Column(name = "HoldingSubsidiary_type", nullable = true, length = 10)
    private String holdingSubsidiaryType;
    @Column(name = "CONTRACT_NO", nullable = true, length = 50)
    private String contractNo;

    @Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal tradeAmount;

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

    @Column(name = "bus_type", nullable = true, length = 10)
    private String busType;

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    @Column(name = "INVESTMENT_METHOD", nullable = true, length = 10)
    private String investmentMethod;

    @Column(name = "INVESTED_ENTERPRISES", nullable = true, length = 50)
    private String investedEnterprises;

    @Column(name = "EQUITY_LAST_TRADE_PRICE", nullable = true, precision = 100, scale = 0)
    private String equityLastTradePrice;

    @Column(name = "IM_LAST_TRADE_PRICE", nullable = true, precision = 100, scale = 0)
    private String imLastTradePrice;

    @Column(name = "IM_SUBJECT_MATTER", nullable = true, length = 50)
    private String imSubjectMatter;

    @Column(name = "OUT_SUBJECT_MATTER", nullable = true, length = 50)
    private String outSubjectMatter;

    @Column(name = "SHARE_DEBT_RATIO", nullable = true, length = 50)
    private String shareDebtRatio;

    @Column(name = "FINANCIAL_ASSSTS_TYPE", nullable = true, length = 50)
    private String financialAssstsType;
    //金融资产编号
    @Column(name = "FINANCIAL_ASSSTS_NUM", nullable = true, length = 50)
    private String financialAssstsNum;
    //金融产品发行规模
    @Column(name = "FINANCIAL_PRO_SCALE", nullable = true, length = 50)
    private String financialProScale;

    @Column(name = "FINANCIAL_PRO_RATIO", nullable = true, length = 50)
    private String financialProRatio;
    //底层资产
    @Column(name = "UNDERLYING_ASSET", nullable = true, length = 50)
    private String underlyingAsset;
    //底层资产是否为关联方
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
    private BigDecimal approveTradeAmount;

    @Column(name = "RULE_RESULT", nullable = true, length = 300)
    private String ruleResult;
    @Column(name = "is_CashSubscription",nullable = true,length = 20)
    private String isCashSubscription;

    @Column(name = "product_code", nullable = true, length = 20)
    private String productCode;

    @Column(name = "product_name", nullable = true, length = 50)
    private String productName;

    @Column(name = "consider_type", nullable = true, length = 10)
    private String considerType;

    @Column(name = "is_HoldingSubsidiary", nullable = true, length = 20)
    private String isHoldingSubsidiary;

    @Column(name = "HoldingSubsidiary_name", nullable = true, length = 100)
    private String holdingSubsidiaryName;

    @Column(name = "HoldingSubsidiary_number", nullable = true, length = 10)
    private String holdingSubsidiaryNumber;

    @Column(name = "totalAssets", nullable = true, length = 100)
    private String totalAssets;

    @Column(name = "is_Overseasinvestment", nullable = true, length = 20)
    private String isOverseasinvestment;


    @Column(name = "Financial_Impact", nullable = true, length = 400)
    private String financialImpact;

    @Column(name = "Tra_sub_Name", nullable = true, length = 20)
    private String traSubName;

    @Column(name = "Major_Type", nullable = true, length = 10)
    private String majorType;

    @Column(name = "Contract_Amount", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal contractAmount;
    //金融产品名称
    @Column(name = "FINANCIAL_ASSSTS_NAME", nullable = true, length = 10)
    private String financialAssstsName;

    @Column(name = "stockright_type", nullable = true, length = 10)
    private String stockrightType;

    @Column(name = "Amount_received", nullable = true, length = 32)
    private BigDecimal amountReceived;

    @Column(name = "AMEND_ID", nullable = true, length = 10)
    private String amendId;

    @Column(name = "AMEND_NAME", nullable = true, length = 10)
    private String amendName;

    @Column(name = "TRAN_NAME", nullable = true, length = 10)
    private String tranName;

    @Column(name = "TRAN_OPPONENT", nullable = true, length = 10)
    private String tranOpponent;

    @Column(name = "CERT_TYPE", nullable = true, length = 10)
    private String certType;

    @Column(name = "MANAGER_ORG", nullable = true, length = 10)
    private String managerOrg;

    @Column(name = "ACCU_PRAC_TRADE_AMOUNT", nullable = true, length = 10)
    private BigDecimal accuPracTradeAmount;

    @Column(name = "REL_TRADE_AMOUNT", nullable = true, length = 10)
    private BigDecimal relTradeAmount;

    @Column(name = "PRAC_TRADE_AMOUNT", nullable = true, length = 10)
    private String pracTradeAmount;

    @Column(name = "TOTALASSETS_LATE_LAST", nullable = true, length = 10)
    private String totalassetsLateLast;

    /**
     * 风险预警
     */
    @Column(name = "LIMIT_HINT", nullable = true, length = 1000)
    private String limitHint;

    @Column(name="concentrated_approval")
    private String concentratedApproval;//是否是集中审议 0 是，1否

    @Column(name="not_major_rel_number")
    private String notMajorRelNumber;//同一笔累计重大，参与的交易对手未达到累计重大的关联方relNumber

    @Column(name="trad_time")
    private Date tradTime;

    public Date getTradTime() {
        return tradTime;
    }

    public void setTradTime(Date tradTime) {
        this.tradTime = tradTime;
    }

    public String getConcentratedApproval() {
        return concentratedApproval;
    }

    public void setConcentratedApproval(String concentratedApproval) {
        this.concentratedApproval = concentratedApproval;
    }

    public String getNotMajorRelNumber() {
        return notMajorRelNumber;
    }

    public void setNotMajorRelNumber(String notMajorRelNumber) {
        this.notMajorRelNumber = notMajorRelNumber;
    }
//    //前次交易价格
//    @Column(name = "LAST_TRADE_PRICE", nullable = true, length = 1000)
//    private String lastTradePrice;
//
//    //被投资企业情况
//    @Column(name = "BY_INVEST_COMPANY_CONDITION", nullable = true, length = 1000)
//    private String byInvestCompanyCondition;

    //底层资产涉及的关联方名称
    @Column(name = "UNDER_INVOLVE_REL_NAME", nullable = true, length = 100)
    private String underInvolveRelName;

    @Column(name="Archiving_Status")
    private String archivingStatus ;//1：确认逐笔披露； 2：确认逐笔报告；  3：确认季度定期披露；4.确认年度定期披露；5：确认定期报告
    @Column(name="report_Status")
    private String reportStatus;//1：确认逐笔报告；

    @Column(name="quarter_Arc_Status")
    private String quarterArcStatus;//1：确认季度定期披露;

    @Column(name="year_Arc_Status")
    private String yearArcStatus;//1.确认年度定期披露；

    @Column(name="year_Rep_Status")
    private String yearRepStatus;//1：确认定期报告
    @Column(name="quarter_Rep_Status")
    private String quarterRepStatus;//1：确认季度定期报告

    @Column(name = "effectOrPay", nullable = true, length = 10)
    private String effectOrPay;

    /* 复星保险二期需求改动 */

    /** 是否进行归零操作  0：未进行  1：已完成 */
    @Column(name = "IS_ACCUMULATED", nullable = true, length = 10)
    private String isAccumulated;
    /** 单笔关联交易类型 */
    @Column(name = "single_related_trantype", nullable = true, length = 1)
    private String singleRelatedTranType;
    /** 是否需逐笔报告 */
    @Column(name = "each_report", nullable = true, length = 30)
    private String eachReport;
    /** 是否需逐笔披露 */
    @Column(name = "each_disclosure", nullable = true, length = 30)
    private String eachDisclosure;
    /**  是否需要补录关联方信息 */
    @Column(name = "is_collect_related", nullable = true, length = 1)
    private String isCollectRelated;
    /** 是否可免于审议 */
    @Column(name = "is_consider_situation", nullable = true, length = 50)
    private String isconsiderSituation;
    /** 免于审议情形 */
    @Column(name = "consider_situation", nullable = true, length = 50)
    private String considerSituation;

    // 部门
    @Column(name = "dept_name", nullable = true, length = 100)
    private String deptName;

    //三期新增字段
    //货币代码
    @Column(name = "currency_code",nullable = true,length = 100)
    private String currencyCode;
    //交易金额（原币）
    @Column(name = "ORIGINAL_CURRENCY_AMOUNT", nullable = true, length = 10)
    private BigDecimal originalCurrencyAmount;
    //资金流水编号
    @Column(name = "FUNDS_FLOW_NUMBER", nullable = true, length = 10)
    private String fundsFlowNumber;

    //协议交易标志
    @Column(name = "PROTOCOL_TRANSACTION_SYMBOL", nullable = true, length = 100)
    private String protocolTransactionSymbol;
    //主协议编号
    @Column(name = "MASTER_AGREEMENT_NUMBER", nullable = true, length = 100)
    private String masterAgreementNumber;
    //合同状态
    @Column(name = "CONTRACT_STATUS", nullable = true, length = 100)
    private String contractStatus;

    // 数据版本
    @Column(name = "data_version", nullable = true, length = 64)
    private String dataVersion;

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

    public String getMasterAgreementNumber() {
        return masterAgreementNumber;
    }

    public void setMasterAgreementNumber(String masterAgreementNumber) {
        this.masterAgreementNumber = masterAgreementNumber;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
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

    public String getFundsFlowNumber() {
        return fundsFlowNumber;
    }

    public void setFundsFlowNumber(String fundsFlowNumber) {
        this.fundsFlowNumber = fundsFlowNumber;
    }

    public String getIsAccumulated() {
        return isAccumulated;
    }

    public void setIsAccumulated(String isAccumulated) {
        this.isAccumulated = isAccumulated;
    }

    public String getEffectOrPay() {
        return effectOrPay;
    }

    public void setEffectOrPay(String effectOrPay) {
        this.effectOrPay = effectOrPay;
    }

    public String getQuarterRepStatus() {
        return quarterRepStatus;
    }

    public void setQuarterRepStatus(String quarterRepStatus) {
        this.quarterRepStatus = quarterRepStatus;
    }

    public String getLimitHint() {
        return limitHint;
    }

    public void setLimitHint(String limitHint) {
        this.limitHint = limitHint;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getQuarterArcStatus() {
        return quarterArcStatus;
    }

    public void setQuarterArcStatus(String quarterArcStatus) {
        this.quarterArcStatus = quarterArcStatus;
    }

    public String getYearArcStatus() {
        return yearArcStatus;
    }

    public void setYearArcStatus(String yearArcStatus) {
        this.yearArcStatus = yearArcStatus;
    }

    public String getYearRepStatus() {
        return yearRepStatus;
    }

    public void setYearRepStatus(String yearRepStatus) {
        this.yearRepStatus = yearRepStatus;
    }

    public String getArchivingStatus() {
        return archivingStatus;
    }

    public void setArchivingStatus(String archivingStatus) {
        this.archivingStatus = archivingStatus;
    }

    public String getUnderInvolveRelName() {
        return underInvolveRelName;
    }

    public void setUnderInvolveRelName(String underInvolveRelName) {
        this.underInvolveRelName = underInvolveRelName;
    }

    public String getTotalassetsLateLast() {
        return totalassetsLateLast;
    }

    public void setTotalassetsLateLast(String totalassetsLateLast) {
        this.totalassetsLateLast = totalassetsLateLast;
    }

    public String getManagerOrg() {
        return managerOrg;
    }

    public void setManagerOrg(String managerOrg) {
        this.managerOrg = managerOrg;
    }

    public BigDecimal getAccuPracTradeAmount() {
        return accuPracTradeAmount;
    }

    public void setAccuPracTradeAmount(BigDecimal accuPracTradeAmount) {
        this.accuPracTradeAmount = accuPracTradeAmount;
    }

    public BigDecimal getRelTradeAmount() {
        return relTradeAmount;
    }

    public void setRelTradeAmount(BigDecimal relTradeAmount) {
        this.relTradeAmount = relTradeAmount;
    }

    public String getPracTradeAmount() {
        return pracTradeAmount;
    }

    public void setPracTradeAmount(String pracTradeAmount) {
        this.pracTradeAmount = pracTradeAmount;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public String getTranOpponent() {
        return tranOpponent;
    }

    public void setTranOpponent(String tranOpponent) {
        this.tranOpponent = tranOpponent;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    @Column(name = "transaction_name", nullable = true, length = 100)
    private String transactionName;

    @Column(name = "transaction_description", nullable = true, length = 1000)
    private String transactionDescription;
    @Column(name = "unique_code", nullable = true, length = 100)
    private String uniqueCode;
    @Column(name = "source_system", nullable = true, length = 60)
    private String sourceSystem;

    @Column(name = "source_module", nullable = true, length = 60)
    private String sourceModule;

    public static String getAliasPid() {
        return ALIAS_PID;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
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

    @Column(name = "payment_id", nullable = true, length = 10)
    private String paymentId;

    @Column(name = "scene", nullable = true, length = 10)
    private String scene;

    @Transient
    private String creater;

    public String getCreater() {
        if ("System".equals(getAmendName())){
            return "其它系统";
        }else{
            creater = amendId+"-"+amendName;
            return creater;
        }
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }



    //业务类型
    @Transient
    private String relTypeStr;
    //是否重大关联交易
    @Transient
    private String majorTypeStr;
    //状态
    @Transient
    private String statusStr;
    @Transient
    private String traSubNameStr;
    //归零操作统计金额
    @Transient
    private java.math.BigDecimal tradeZeroAmount;

    /**
     * 交易对手（关联方）列表
     */
    @Transient
    private List<TraRelInfo> relInfoList;

    @Column(name = "rel_info_list_str", nullable = true, length = 1000)
    private String relInfoListStr;

    @Column(name = "proc_inst_id", nullable = true, length = 10)
    private String procInstId;

    //columns END

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public static String getTableAlias() {
        return TABLE_ALIAS;
    }

    public List<TraRelInfo> getRelInfoList() {
        return relInfoList;
    }

    public void setRelInfoList(List<TraRelInfo> relInfoList) {
        this.relInfoList = relInfoList;
    }

    public String getRelInfoListStr() {
        return relInfoListStr;
    }

    public void setRelInfoListStr(String relInfoListStr) {
        this.relInfoListStr = relInfoListStr;
    }

    public java.math.BigDecimal getTradeZeroAmount() {
        return tradeZeroAmount;
    }

    public void setTradeZeroAmount(java.math.BigDecimal tradeZeroAmount) {
        this.tradeZeroAmount = tradeZeroAmount;
    }

    public String getTraSubNameStr() {
        return traSubNameStr;
    }

    public void setTraSubNameStr(String traSubNameStr) {
        this.traSubNameStr = traSubNameStr;
    }

    public String getRelTypeStr() {
        return relTypeStr;
    }

    public void setRelTypeStr(String relTypeStr) {
        this.relTypeStr = relTypeStr;
    }



    public String getMajorTypeStr() {
        return majorTypeStr;
    }

    public void setMajorTypeStr(String majorTypeStr) {
        this.majorTypeStr = majorTypeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    //columns END


    public String getAmendId() {
        return amendId;
    }

    public void setAmendId(String amendId) {
        this.amendId = amendId;
    }

    public String getAmendName() {
        return amendName;
    }

    public void setAmendName(String amendName) {
        this.amendName = amendName;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getIsCashSubscription() {
        return isCashSubscription;
    }

    public void setIsCashSubscription(String isCashSubscription) {
        this.isCashSubscription = isCashSubscription;
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

    public java.math.BigDecimal getTotolTradeAmount() {
        return this.totolTradeAmount;
    }

    public void setTotolTradeAmount(java.math.BigDecimal value) {
        this.totolTradeAmount = value;
    }


    public String getContractNo() {
        return this.contractNo;
    }

    public void setContractNo(String value) {
        this.contractNo = value;
    }


    public java.math.BigDecimal getTradeAmount() {
        return this.tradeAmount;
    }

    public void setTradeAmount(java.math.BigDecimal value) {
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

    public BigDecimal getApproveTradeAmount() {
        return approveTradeAmount;
    }

    public void setApproveTradeAmount(BigDecimal approveTradeAmount) {
        this.approveTradeAmount = approveTradeAmount;
    }

    public String getTranDateString() {
        if (Optional.ofNullable(tranDate).isPresent()) {
            return DateUtils.formatDate(tranDate);
        }
        return "";
    }

    public String getBusStartTimeString() {
        if (Optional.ofNullable(busStartTime).isPresent()) {
            return DateUtils.formatDate(busStartTime);
        }
        return "";
    }

    public String getBusEndTimeString() {
        if (Optional.ofNullable(busEndTime).isPresent()) {
            return DateUtils.formatDate(busEndTime);
        }
        return "";
    }

    public void setTranDateString(String value) {
        if (value != null) {
            setTranDate(DateUtils.parseDate(value));
        }
    }

    public void setBusStartTimeString(String busStartTimeString) {
        this.busStartTimeString = busStartTimeString;
    }

    public void setBusEndTimeString(String busEndTimeString) {
        this.busEndTimeString = busEndTimeString;
    }

    public String getRuleResult() {
        return ruleResult;
    }

    public void setRuleResult(String ruleResult) {
        this.ruleResult = ruleResult;
    }

    public String getConsiderType() {
        return considerType;
    }

    public void setConsiderType(String considerType) {
        this.considerType = considerType;
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

    public String getTotalAssets() {
        return totalAssets;
    }
    public String getHoldingSubsidiaryType() {
        return holdingSubsidiaryType;
    }

    public void setHoldingSubsidiaryType(String holdingSubsidiaryType) {
        this.holdingSubsidiaryType = holdingSubsidiaryType;
    }
    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
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

    public String getTraSubName() {
        return traSubName;
    }

    public void setTraSubName(String traSubName) {
        this.traSubName = traSubName;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getFinancialAssstsName() {
        return financialAssstsName;
    }

    public void setFinancialAssstsName(String financialAssstsName) {
        this.financialAssstsName = financialAssstsName;
    }

    public String getStockrightType() {
        return stockrightType;
    }

    public void setStockrightType(String stockrightType) {
        this.stockrightType = stockrightType;
    }


    public String getSingleRelatedTranType() {
        return singleRelatedTranType;
    }

    public void setSingleRelatedTranType(String singleRelatedTranType) {
        this.singleRelatedTranType = singleRelatedTranType;
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

    public String getIsCollectRelated() {
        return isCollectRelated;
    }

    public void setIsCollectRelated(String isCollectRelated) {
        this.isCollectRelated = isCollectRelated;
    }

    public String getIsconsiderSituation() {
        return isconsiderSituation;
    }

    public void setIsconsiderSituation(String isconsiderSituation) {
        this.isconsiderSituation = isconsiderSituation;
    }

    public String getConsiderSituation() {
        return considerSituation;
    }

    public void setConsiderSituation(String considerSituation) {
        this.considerSituation = considerSituation;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setAmountReceived(BigDecimal amountReceived) {
        this.amountReceived = amountReceived;
    }

    @Override
    public String toString() {
        return "TraFunds{" +
                "pid=" + pid +
                ", relId=" + relId +
                ", relType='" + relType + '\'' +
                ", relName='" + relName + '\'' +
                ", relNumber='" + relNumber + '\'' +
                ", tradeOrg='" + tradeOrg + '\'' +
                ", tranType='" + tranType + '\'' +
                ", dataSources='" + dataSources + '\'' +
                ", tranDate=" + tranDate +
                ", tranDateString='" + tranDateString + '\'' +
                ", majorTrade='" + majorTrade + '\'' +
                ", caliber='" + caliber + '\'' +
                ", depositTerms='" + depositTerms + '\'' +
                ", status='" + status + '\'' +
                ", statusComment='" + statusComment + '\'' +
                ", isDealPrice='" + isDealPrice + '\'' +
                ", totolTradeAmount=" + totolTradeAmount +
                ", holdingSubsidiaryType='" + holdingSubsidiaryType + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", tradeAmount=" + tradeAmount +
                ", busStartTime=" + busStartTime +
                ", busStartTimeString='" + busStartTimeString + '\'' +
                ", busEndTime=" + busEndTime +
                ", busEndTimeString='" + busEndTimeString + '\'' +
                ", investmentType='" + investmentType + '\'' +
                ", busType='" + busType + '\'' +
                ", investmentMethod='" + investmentMethod + '\'' +
                ", investedEnterprises='" + investedEnterprises + '\'' +
                ", equityLastTradePrice='" + equityLastTradePrice + '\'' +
                ", imLastTradePrice='" + imLastTradePrice + '\'' +
                ", imSubjectMatter='" + imSubjectMatter + '\'' +
                ", outSubjectMatter='" + outSubjectMatter + '\'' +
                ", shareDebtRatio='" + shareDebtRatio + '\'' +
                ", financialAssstsType='" + financialAssstsType + '\'' +
                ", financialAssstsNum='" + financialAssstsNum + '\'' +
                ", financialProScale='" + financialProScale + '\'' +
                ", financialProRatio='" + financialProRatio + '\'' +
                ", underlyingAsset='" + underlyingAsset + '\'' +
                ", underIsRel='" + underIsRel + '\'' +
                ", underInvolveRel='" + underInvolveRel + '\'' +
                ", underRel='" + underRel + '\'' +
                ", investedEnterprisesName='" + investedEnterprisesName + '\'' +
                ", busModel='" + busModel + '\'' +
                ", earningsExpectaions='" + earningsExpectaions + '\'' +
                ", investmentRatio='" + investmentRatio + '\'' +
                ", investmentAllocation='" + investmentAllocation + '\'' +
                ", lossShare='" + lossShare + '\'' +
                ", tradePrice='" + tradePrice + '\'' +
                ", pricingPolicy='" + pricingPolicy + '\'' +
                ", tradeSubject='" + tradeSubject + '\'' +
                ", remark='" + remark + '\'' +
                ", transNum='" + transNum + '\'' +
                ", isEffective='" + isEffective + '\'' +
                ", approveTradeAmount=" + approveTradeAmount +
                ", ruleResult='" + ruleResult + '\'' +
                ", isCashSubscription='" + isCashSubscription + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", considerType='" + considerType + '\'' +
                ", isHoldingSubsidiary='" + isHoldingSubsidiary + '\'' +
                ", holdingSubsidiaryName='" + holdingSubsidiaryName + '\'' +
                ", holdingSubsidiaryNumber='" + holdingSubsidiaryNumber + '\'' +
                ", totalAssets='" + totalAssets + '\'' +
                ", isOverseasinvestment='" + isOverseasinvestment + '\'' +
                ", financialImpact='" + financialImpact + '\'' +
                ", traSubName='" + traSubName + '\'' +
                ", majorType='" + majorType + '\'' +
                ", contractAmount=" + contractAmount +
                ", financialAssstsName='" + financialAssstsName + '\'' +
                ", stockrightType='" + stockrightType + '\'' +
                ", amountReceived=" + amountReceived +
                ", amendId='" + amendId + '\'' +
                ", amendName='" + amendName + '\'' +
                ", tranName='" + tranName + '\'' +
                ", tranOpponent='" + tranOpponent + '\'' +
                ", certType='" + certType + '\'' +
                ", managerOrg='" + managerOrg + '\'' +
                ", accuPracTradeAmount=" + accuPracTradeAmount +
                ", relTradeAmount=" + relTradeAmount +
                ", pracTradeAmount='" + pracTradeAmount + '\'' +
                ", totalassetsLateLast='" + totalassetsLateLast + '\'' +
                ", limitHint='" + limitHint + '\'' +
                ", concentratedApproval='" + concentratedApproval + '\'' +
                ", notMajorRelNumber='" + notMajorRelNumber + '\'' +
                ", underInvolveRelName='" + underInvolveRelName + '\'' +
                ", archivingStatus='" + archivingStatus + '\'' +
                ", reportStatus='" + reportStatus + '\'' +
                ", quarterArcStatus='" + quarterArcStatus + '\'' +
                ", yearArcStatus='" + yearArcStatus + '\'' +
                ", yearRepStatus='" + yearRepStatus + '\'' +
                ", quarterRepStatus='" + quarterRepStatus + '\'' +
                ", effectOrPay='" + effectOrPay + '\'' +
                ", isAccumulated='" + isAccumulated + '\'' +
                ", transactionName='" + transactionName + '\'' +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", sourceSystem='" + sourceSystem + '\'' +
                ", sourceModule='" + sourceModule + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", scene='" + scene + '\'' +
                ", creater='" + creater + '\'' +
                ", relTypeStr='" + relTypeStr + '\'' +
                ", majorTypeStr='" + majorTypeStr + '\'' +
                ", statusStr='" + statusStr + '\'' +
                ", traSubNameStr='" + traSubNameStr + '\'' +
                ", tradeZeroAmount=" + tradeZeroAmount +
                ", relInfoList=" + relInfoList +
                ", relInfoListStr='" + relInfoListStr + '\'' +
                ", procInstId='" + procInstId + '\'' +
                ", singleRelatedTranType='" + singleRelatedTranType + '\'' +
                ", eachReport='" + eachReport + '\'' +
                ", eachDisclosure='" + eachDisclosure + '\'' +
                ", isCollectRelated='" + isCollectRelated + '\'' +
                ", isconsiderSituation='" + isconsiderSituation + '\'' +
                ", considerSituation='" + considerSituation + '\'' +
                ", deptName='" + deptName + '\'' +
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
        if (obj instanceof TraFunds == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraFunds other = (TraFunds) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

