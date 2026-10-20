/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:56
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

@Entity
@Table(name = "ins_tra_interest")
public class TraInterest extends BasePO {

    public static final String TABLE_ALIAS = "关联交易表（保险）-利益转移类";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_REL_ID = "关联方ID";
    public static final String ALIAS_REL_TYPE = "关联方类型";
    public static final String ALIAS_REL_NAME = "关联方名称";
    public static final String ALIAS_REL_NUMBER = "关联方证件号码";
    public static final String ALIAS_TRADE_ORG = "交易机构";
    public static final String ALIAS_TRAN_TYPE = "交易类型";
    public static final String ALIAS_BUS_TYPE = "业务类型";
    public static final String ALIAS_DATA_SOURCES = "数据来源";
    public static final String ALIAS_TRAN_DATE = "交易日期";
    public static final String ALIAS_MAJOR_TRADE = "是否重大关联交易";
    public static final String ALIAS_CALIBER = "口径";
    public static final String ALIAS_STATUS = "状态";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_IS_DEAL_PRICE = "交易定价是否为国家规定";
    public static final String ALIAS_TOTOL_TRADE_AMOUNT = "本年度与该关联方发生的关联交易累计金额（万元）";
    public static final String ALIAS_CONTRACT_NO = "合同编号";
    public static final String ALIAS_TRADE_AMOUNT = "交易金额（万元）";
    public static final String ALIAS_BUS_START_TIME = "业务起始日期";
    public static final String ALIAS_BUS_END_TIME = "业务终止日期";
    public static final String ALIAS_TRADE_OCCUR = "交易发生原因及合同、证明文件等";
    public static final String ALIAS_TRADE_PRICE = "交易价格";
    public static final String ALIAS_PRICING_POLICY = "定价政策";
    public static final String ALIAS_TRADE_SUBJECT = "交易标的";
    public static final String ALIAS_REMARK = "其他事项说明";
    public static final String ALIAS_TRANS_NUM = "交易编号";
    public static final String ALIAS_RULE_RESULT = "规则结果";
    public static final String ALIAS_IS_HOLDINGSUBSIDIARY = "是否为控股子公司";
    public static final String ALIAS_HOLDINGSUBSIDIARY_NAME = "控股子公司名称";
    public static final String ALIAS_HOLDINGSUBSIDIARY_NUMBER = "控股子公司证件号码";
    public static final String ALIAS_TOTALASSETS = "关联方总资产";
    public static final String ALIAS_UNITF_TRADE_AGREMENT_INTEREST = "统一交易协议业务情况";
    public static final String ALIAS_IS_TRADE_AGREEMENT = "是否为统一交易协议";
    public static final String ALIAS_LAST_TRADE_PRICE = "前次交易价格";
    public static final String ALIAS_LAST_TRADE_TIME = "前次交易时间";
    public static final String ALIAS_GUARANTEE_CONDITIONS = "担保条件";
    public static final String ALIAS_CONSIDER_TYPE = "审议类型(1:可免予审议，2:可集中审议，3逐笔审议)";
    public static final String ALIAS_TRADE_SUBJECT_ASSETS = "标的资产情况";
    public static final String ALIAS_FINANCIAL_IMPACT = "对财务状况及经营成果的影响";
    public static final String ALIAS_TRA_SUB_NAME = "交易主体名称";
    public static final String ALIAS_MAJOR_TYPE = "达到重大关联交易的情形";
    public static final String ALIAS_CONTRACT_AMOUNT = "合同金额";
    public static final String ALIAS_SUBJECT_SITUATION = "标的情况";
    public static final String ALIAS_HOLDINGSUBSIDIARY_TYPE = "控股子公司证件号码";
    public static final String ALIAS_IS_CASHSUBSCRIPTION = "是否是现金认购";
    public static final String ALIAS_AMEND_ID = "操作人id";
    public static final String ALIAS_AMEND_NAME = "操作人姓名";
    public static final String ALIAS_TRAN_NAME = "交易名称";
    public static final String ALIAS_TRAN_OPPONENT = "交易对手";
    public static final String ALIAS_CERT_TYPE = "证件类型";
    public static final String ALIAS_actual_Total="实际累计交易金额";
    public static final String ALIAS_contract_Date="合同签署日期";
    public static final String ALIAS_contra_Total="合同金额";
    public static final String ALIAS_isAgreement_Impl="是否属于统一交易协议执行情况";
    public static final String ALIAS_tradeAgreement_Number="主协议编号";
    public static final String AlIAS_CONTRACT_TOTAL="关联交易金额";


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

    @Column(name = "BUS_TYPE", nullable = true, length = 50)
    private String busType;

    @Column(name = "DATA_SOURCES", nullable = true, length = 10)
    private String dataSources;
    //交易日期
    @Column(name = "TRAN_DATE", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tranDate;

    @Column(name = "MAJOR_TRADE", nullable = true, length = 10)
    private String majorTrade;

    @Column(name = "CALIBER", nullable = true, length = 50)
    private String caliber;

    @Column(name = "STATUS", nullable = true, length = 10)
    private String status;

    @Column(name = "STATUS_COMMENT", nullable = true, length = 100)
    private String statusComment;

    @Column(name = "IS_DEAL_PRICE", nullable = true, length = 5)
    private String isDealPrice;

    @Column(name = "TOTOL_TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private BigDecimal totolTradeAmount;

    @Column(name = "CONTRACT_NO", nullable = true, length = 50)
    private String contractNo;

    @Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private BigDecimal tradeAmount;

    @Column(name = "BUS_START_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date busStartTime;

    @Column(name = "BUS_END_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date busEndTime;

    @Column(name = "TRADE_OCCUR", nullable = true, length = 100)
    private String tradeOccur;

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
    @Column(name = "product_code", nullable = true, length = 20)
    private String productCode;
    @Column(name = "product_name", nullable = true, length = 50)
    private String productName;

    @Column(name = "TRAN_NAME", nullable = true, length = 10)
    private String tranName;

    @Column(name = "TRAN_OPPONENT", nullable = true, length = 10)
    private String tranOpponent;

    @Column(name = "CERT_TYPE", nullable = true, length = 10)
    private String certType;

    @Column(name = "PRAC_TRADE_AMOUNT", nullable = true, length = 10)
    private String pracTradeAmount;

    public String getPracTradeAmount() {
        return pracTradeAmount;
    }

    public void setPracTradeAmount(String pracTradeAmount) {
        this.pracTradeAmount = pracTradeAmount;
    }

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
    @Column(name = "REL_TRADE_AMOUNT")
    private BigDecimal relTradeAmount;
    @Column(name = "ACCU_PRAC_TRADE_AMOUNT")
    private BigDecimal accuPracTradeAmount; //累计实际金额

    public BigDecimal getRelTradeAmount() {
        return relTradeAmount;
    }

    public void setRelTradeAmount(BigDecimal relTradeAmount) {
        this.relTradeAmount = relTradeAmount;
    }


    @Column(name="concentrated_approval")
    private String concentratedApproval;//是否是集中审议 0 是，1否

    @Column(name="not_major_rel_number")
    private String notMajorRelNumber;//未清零的关联方relNumber

    @Column(name="agreement_num", length = 32)
    private String agreementNum;//主协议编号
    @Column(name="quarter_Rep_Status")
    private String quarterRepStatus;//1：确认季度定期报告

    //新增字段
    @Column(name = "transaction_name", nullable = true, length = 100)
    private String transactionName;
    @Column(name = "transaction_description", nullable = true, length = 1000)
    private String transactionDescription;
    @Column(name = "unique_code", nullable = true, length = 100)
    private String uniqueCode;
    @Column(name = "source_system", nullable = true, length = 60)
    private String sourceSystem;
    @Column(name = "effectOrPay", nullable = true, length = 10)
    private String effectOrPay;
    @Column(name = "source_module", nullable = true, length = 60)
    private String sourceModule;
    @Column(name = "payment_id", nullable = true, length = 10)
    private String paymentId;
    @Column(name = "scene", nullable = true, length = 10)
    private String scene;



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
    /**
     * 交易方向
     */
    @Column(name = "Trading_direction", nullable = true, length = 6)
    private String tradingDirection;


    //三期新增字段
    //资金流水编号
    @Column(name = "FUNDS_FLOW_NUMBER", nullable = true, length = 10)
    private String fundsFlowNumber;
    //货币代码
    @Column(name = "currency_code",nullable = true,length = 100)
    private String currencyCode;
    //交易金额（原币）
    @Column(name = "ORIGINAL_CURRENCY_AMOUNT", nullable = true, length = 10)
    private BigDecimal originalCurrencyAmount;

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

    public String getFundsFlowNumber() {
        return fundsFlowNumber;
    }

    public void setFundsFlowNumber(String fundsFlowNumber) {
        this.fundsFlowNumber = fundsFlowNumber;
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

    public String getTradingDirection() {
        return tradingDirection;
    }

    public void setTradingDirection(String tradingDirection) {
        this.tradingDirection = tradingDirection;
    }

    public String getIsAccumulated() {
        return isAccumulated;
    }

    public void setIsAccumulated(String isAccumulated) {
        this.isAccumulated = isAccumulated;
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

    public String getEffectOrPay() {
        return effectOrPay;
    }

    public void setEffectOrPay(String effectOrPay) {
        this.effectOrPay = effectOrPay;
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

    public String getQuarterRepStatus() {
        return quarterRepStatus;
    }

    public void setQuarterRepStatus(String quarterRepStatus) {
        this.quarterRepStatus = quarterRepStatus;
    }

    public String getAgreementNum() {
        return agreementNum;
    }

    public void setAgreementNum(String agreementNum) {
        this.agreementNum = agreementNum;
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

    public BigDecimal getAccuPracTradeAmount() {
        return accuPracTradeAmount;
    }

    public void setAccuPracTradeAmount(BigDecimal accuPracTradeAmount) {
        this.accuPracTradeAmount = accuPracTradeAmount;
    }

    @Transient
    private String tranDateString;

    @Transient
    private String busStartTimeString;

    @Transient
    private String busEndTimeString;

    @Transient
    private String traSubNameStr;
    @Transient
    private String relTypeStr;
    @Transient
    private String busTypeStr;

    public BigDecimal getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(BigDecimal actualTotal) {
        this.actualTotal = actualTotal;
    }

    @Transient
    private String majorTradeStr;
    @Transient
    private String statusStr;
    @Transient
    private String MajorTypeStr;

    /**
     * 交易对手（关联方）列表
     */
    @Transient
    private List<TraRelInfo> relInfoList;

    @Column(name = "rel_info_list_str", nullable = true, length = 1000)
    private String relInfoListStr;

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

    //归零操作统计金额
    @Transient
    private java.math.BigDecimal tradeZeroAmount;

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

    public java.math.BigDecimal getTradeZeroAmount() {
        return tradeZeroAmount;
    }

    public void setTradeZeroAmount(java.math.BigDecimal tradeZeroAmount) {
        this.tradeZeroAmount = tradeZeroAmount;
    }

    public String getBusTypeStr() {
        return busTypeStr;
    }

    public void setBusTypeStr(String busTypeStr) {
        this.busTypeStr = busTypeStr;
    }

    public String getMajorTypeStr() {
        return MajorTypeStr;
    }

    public void setMajorTypeStr(String majorTypeStr) {
        MajorTypeStr = majorTypeStr;
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



    public String getMajorTradeStr() {
        return majorTradeStr;
    }

    public void setMajorTradeStr(String majorTradeStr) {
        this.majorTradeStr = majorTradeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }



    @Column(name = "RULE_RESULT", nullable = true, length = 32)
    private String ruleResult;

    @Column(name = "is_HoldingSubsidiary", nullable = true, length = 20)
    private String isHoldingSubsidiary;

    @Column(name = "HoldingSubsidiary_name", nullable = true, length = 100)
    private String holdingSubsidiaryName;

    @Column(name = "HoldingSubsidiary_number", nullable = true, length = 10)
    private String holdingSubsidiaryNumber;

    @Column(name = "totalAssets", nullable = true, length = 100)
    private String totalAssets;
    //前次交易价格
    @Column(name = "LAST_TRADE_PRICE", nullable = true, length = 10)
    private String lastTradePrice;
    //前次交易日期
    @Column(name = "LAST_TRADE_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastTradeTime;
    //是否为统一交易协议
    @Column(name = "is_agreement", nullable = true, length = 6)
    private String isAgreement;
    //担保条件
    @Column(name = "Guarantee_conditions", nullable = true, length = 10)
    private String guaranteeConditions;

    @Column(name = "consider_type", nullable = true, length = 10)
    private String considerType;

    @Column(name = "TRADE_SUBJECT_ASSETS", nullable = true, length = 1000)
    private String tradeSubjectAssets;

    @Column(name = "Financial_Impact", nullable = true, length = 400)
    private String financialImpact;

    @Column(name = "Tra_sub_Name", nullable = true, length = 20)
    private String traSubName;

    @Column(name = "Major_Type", nullable = true, length = 10)
    private String majorType;

    @Column(name = "SUBJECT_SITUATION", nullable = true, length = 1000)
    private String subjectSituation;
    @Column(name = "HOLDINGSUBSIDIARY_TYPE", nullable = true, length = 20)
    private String holdingSubsidiaryType;
    @Column(name = "IS_CASHSUBSCRIPTION", nullable = true, length = 10)
    private String isCashSubscription;

    @Column(name = "Contract_Amount", nullable = true, precision = 50, scale = 0)
    private BigDecimal contractAmount;

    @Column(name = "AMEND_ID", nullable = true, length = 10)
    private String amendId;

    @Column(name = "AMEND_NAME", nullable = true, length = 10)
    private String amendName;

    @Column(name="actual_Total")
    private BigDecimal actualTotal;
    @Column(name="contract_Date")
    private Date  contractDate;
    @Column(name="contra_Total")
    private BigDecimal contraTotal;
    //是否属于统一交易协议执行情况
    @Column(name="is_Agreement_Impl")
    private  String  isAgreementImpl;

    @Column(name="trade_Agreement_Number")
    private String tradeAgreementNumber;

    @Column(name="manager_org")
    private String managerOrg;

    @Column(name = "proc_inst_id", nullable = true, length = 10)
    private String procInstId;

    //columns END

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }


    public String getManagerOrg() {
        return managerOrg;
    }

    public void setManagerOrg(String managerOrg) {
        this.managerOrg = managerOrg;
    }
    //columns END
    @Transient
    private String lastTradeTimeString;

    public String getLastTradeTimeString() {
        if (lastTradeTime != null) {
            return DateUtils.formatDate(lastTradeTime);
        }
        return "";
    }
    //EXCEL 业务类型
    @Transient
    private String investmentType;
    @Transient
    private  String contractDateString;

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public void setLastTradeTimeString(String lastTradeTimeString) {
        if (lastTradeTimeString!=null){
            setLastTradeTime(DateUtils.parseDate(lastTradeTimeString));
        }
    }


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

    public String getIsCashSubscription() {
        return isCashSubscription;
    }

    public void setIsCashSubscription(String isCashSubscription) {
        this.isCashSubscription = isCashSubscription;
    }


    public String getHoldingSubsidiaryType() {
        return holdingSubsidiaryType;
    }

    public void setHoldingSubsidiaryType(String holdingSubsidiaryType) {
        this.holdingSubsidiaryType = holdingSubsidiaryType;
    }

    public String getSubjectSituation() {
        return subjectSituation;
    }

    public void setSubjectSituation(String subjectSituation) {
        this.subjectSituation = subjectSituation;
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


    public String getBusType() {
        return this.busType;
    }

    public void setBusType(String value) {
        this.busType = value;
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

    public BigDecimal getTotolTradeAmount() {
        return this.totolTradeAmount;
    }

    public void setTotolTradeAmount(BigDecimal value) {
        this.totolTradeAmount = value;
    }


    public String getContractNo() {
        return this.contractNo;
    }

    public void setContractNo(String value) {
        this.contractNo = value;
    }


    public BigDecimal getTradeAmount() {
        return this.tradeAmount;
    }

    public void setTradeAmount(BigDecimal value) {
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


    public String getTradeOccur() {
        return this.tradeOccur;
    }

    public void setTradeOccur(String value) {
        this.tradeOccur = value;
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
        if (tranDate != null) {
            return DateUtils.formatDate(tranDate);
        }
        return "";
    }

    public void setTranDateString(String tranDateString) {
        if (tranDateString != null) {
            setTranDate(DateUtils.parseDate(tranDateString));
        }
    }

    public String getBusStartTimeString() {
        if (busStartTime != null) {
            return DateUtils.formatDate(busStartTime);
        }
        return "";
    }

    public void setBusStartTimeString(String busStartTimeString) {
        if (busStartTimeString!=null){
            setBusStartTime(DateUtils.parseDate(busStartTimeString));
        }
    }

    public String getBusEndTimeString() {
        if (busEndTime != null) {
            return DateUtils.formatDate(busEndTime);
        }
        return "";
    }

    public String getConsiderType() {
        return considerType;
    }

    public void setConsiderType(String considerType) {
        this.considerType = considerType;
    }

    public void setBusEndTimeString(String busEndTimeString) {
        if (busEndTimeString!=null){
            setBusEndTime(DateUtils.parseDate(busEndTimeString));
        }
    }
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
    public String getRuleResult() {
        return ruleResult;
    }

    public void setRuleResult(String ruleResult) {
        this.ruleResult = ruleResult;
    }

    public String getTradeSubjectAssets() {
        return tradeSubjectAssets;
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


    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public BigDecimal getContraTotal() {
        return contraTotal;
    }

    public void setContraTotal(BigDecimal contraTotal) {
        this.contraTotal = contraTotal;
    }

    public String getIsAgreementImpl() {
        return isAgreementImpl;
    }

    public void setIsAgreementImpl(String isAgreementImpl) {
        this.isAgreementImpl = isAgreementImpl;
    }

    public String getTradeAgreementNumber() {
        return tradeAgreementNumber;
    }

    public void setTradeAgreementNumber(String tradeAgreementNumber) {
        this.tradeAgreementNumber = tradeAgreementNumber;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSingleRelatedTranType() {
        return singleRelatedTranType;
    }

    public void setSingleRelatedTranType(String singleRelatedTranType) {
        this.singleRelatedTranType = singleRelatedTranType;
    }
//    public String getLastTradeTimeString() {
//        return lastTradeTimeString;
//    }
//
//    public void setLastTradeTimeString(String lastTradeTimeString) {
//        this.lastTradeTimeString = lastTradeTimeString;
//    }

    public String getContractDateString() {
        if (contractDate != null) {
            return DateUtils.formatDate(contractDate);
        }
        return "";
    }

    public void setContractDateString(String contractDateString) {
        if (contractDateString != null) {
            setContractDate(DateUtils.parseDate(contractDateString));
        }
    }


    @Override
    public String toString() {
        return "TraInterest{" +
                "pid=" + pid +
                ", relId=" + relId +
                ", relType='" + relType + '\'' +
                ", relName='" + relName + '\'' +
                ", relNumber='" + relNumber + '\'' +
                ", tradeOrg='" + tradeOrg + '\'' +
                ", tranType='" + tranType + '\'' +
                ", busType='" + busType + '\'' +
                ", dataSources='" + dataSources + '\'' +
                ", tranDate=" + tranDate +
                ", majorTrade='" + majorTrade + '\'' +
                ", caliber='" + caliber + '\'' +
                ", status='" + status + '\'' +
                ", statusComment='" + statusComment + '\'' +
                ", isDealPrice='" + isDealPrice + '\'' +
                ", totolTradeAmount=" + totolTradeAmount +
                ", contractNo='" + contractNo + '\'' +
                ", tradeAmount=" + tradeAmount +
                ", busStartTime=" + busStartTime +
                ", busEndTime=" + busEndTime +
                ", tradeOccur='" + tradeOccur + '\'' +
                ", tradePrice='" + tradePrice + '\'' +
                ", pricingPolicy='" + pricingPolicy + '\'' +
                ", tradeSubject='" + tradeSubject + '\'' +
                ", remark='" + remark + '\'' +
                ", transNum='" + transNum + '\'' +
                ", isEffective='" + isEffective + '\'' +
                ", approveTradeAmount='" + approveTradeAmount + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", tranName='" + tranName + '\'' +
                ", tranOpponent='" + tranOpponent + '\'' +
                ", certType='" + certType + '\'' +
                ", pracTradeAmount='" + pracTradeAmount + '\'' +
                ", archivingStatus='" + archivingStatus + '\'' +
                ", reportStatus='" + reportStatus + '\'' +
                ", quarterArcStatus='" + quarterArcStatus + '\'' +
                ", yearArcStatus='" + yearArcStatus + '\'' +
                ", yearRepStatus='" + yearRepStatus + '\'' +
                ", relTradeAmount=" + relTradeAmount +
                ", accuPracTradeAmount=" + accuPracTradeAmount +
                ", concentratedApproval='" + concentratedApproval + '\'' +
                ", notMajorRelNumber='" + notMajorRelNumber + '\'' +
                ", agreementNum='" + agreementNum + '\'' +
                ", quarterRepStatus='" + quarterRepStatus + '\'' +
                ", transactionName='" + transactionName + '\'' +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", sourceSystem='" + sourceSystem + '\'' +
                ", effectOrPay='" + effectOrPay + '\'' +
                ", sourceModule='" + sourceModule + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", scene='" + scene + '\'' +
                ", isAccumulated='" + isAccumulated + '\'' +
                ", singleRelatedTranType='" + singleRelatedTranType + '\'' +
                ", eachReport='" + eachReport + '\'' +
                ", eachDisclosure='" + eachDisclosure + '\'' +
                ", isCollectRelated='" + isCollectRelated + '\'' +
                ", isconsiderSituation='" + isconsiderSituation + '\'' +
                ", considerSituation='" + considerSituation + '\'' +
                ", deptName='" + deptName + '\'' +
                ", tradingDirection='" + tradingDirection + '\'' +
                ", tranDateString='" + tranDateString + '\'' +
                ", busStartTimeString='" + busStartTimeString + '\'' +
                ", busEndTimeString='" + busEndTimeString + '\'' +
                ", traSubNameStr='" + traSubNameStr + '\'' +
                ", relTypeStr='" + relTypeStr + '\'' +
                ", busTypeStr='" + busTypeStr + '\'' +
                ", majorTradeStr='" + majorTradeStr + '\'' +
                ", statusStr='" + statusStr + '\'' +
                ", MajorTypeStr='" + MajorTypeStr + '\'' +
                ", relInfoList=" + relInfoList +
                ", relInfoListStr='" + relInfoListStr + '\'' +
                ", tradeZeroAmount=" + tradeZeroAmount +
                ", ruleResult='" + ruleResult + '\'' +
                ", isHoldingSubsidiary='" + isHoldingSubsidiary + '\'' +
                ", holdingSubsidiaryName='" + holdingSubsidiaryName + '\'' +
                ", holdingSubsidiaryNumber='" + holdingSubsidiaryNumber + '\'' +
                ", totalAssets='" + totalAssets + '\'' +
                ", lastTradePrice='" + lastTradePrice + '\'' +
                ", lastTradeTime=" + lastTradeTime +
                ", isAgreement='" + isAgreement + '\'' +
                ", guaranteeConditions='" + guaranteeConditions + '\'' +
                ", considerType='" + considerType + '\'' +
                ", tradeSubjectAssets='" + tradeSubjectAssets + '\'' +
                ", financialImpact='" + financialImpact + '\'' +
                ", traSubName='" + traSubName + '\'' +
                ", majorType='" + majorType + '\'' +
                ", subjectSituation='" + subjectSituation + '\'' +
                ", holdingSubsidiaryType='" + holdingSubsidiaryType + '\'' +
                ", isCashSubscription='" + isCashSubscription + '\'' +
                ", contractAmount=" + contractAmount +
                ", amendId='" + amendId + '\'' +
                ", amendName='" + amendName + '\'' +
                ", actualTotal=" + actualTotal +
                ", contractDate=" + contractDate +
                ", contraTotal=" + contraTotal +
                ", isAgreementImpl='" + isAgreementImpl + '\'' +
                ", tradeAgreementNumber='" + tradeAgreementNumber + '\'' +
                ", managerOrg='" + managerOrg + '\'' +
                ", procInstId='" + procInstId + '\'' +
                ", lastTradeTimeString='" + lastTradeTimeString + '\'' +
                ", investmentType='" + investmentType + '\'' +
                ", contractDateString='" + contractDateString + '\'' +
                ", creater='" + creater + '\'' +
                '}';
    }

    public void setTradeSubjectAssets(String tradeSubjectAssets) {
        this.tradeSubjectAssets = tradeSubjectAssets;
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

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getLastTradePrice() {
        return lastTradePrice;
    }

    public void setLastTradePrice(String lastTradePrice) {
        this.lastTradePrice = lastTradePrice;
    }

    public Date getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(Date lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public String getIsAgreement() {
        return isAgreement;
    }

    public void setIsAgreement(String isAgreement) {
        this.isAgreement = isAgreement;
    }

    public String getGuaranteeConditions() {
        return guaranteeConditions;
    }

    public void setGuaranteeConditions(String guaranteeConditions) {
        this.guaranteeConditions = guaranteeConditions;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TraInterest == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraInterest other = (TraInterest) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

