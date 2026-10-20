/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:52
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
@Table(name = "ins_tra_investment")
public class TraInvestment extends BasePO {

    public static final String TABLE_ALIAS = "关联交易表（保险）-投资入股类";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_REL_ID = "关联方ID";
    public static final String ALIAS_REL_TYPE = "关联方类型";
    public static final String ALIAS_REL_NAME = "关联方名称";
    public static final String ALIAS_REL_NUMBER = "关联方证件号码";
    public static final String ALIAS_TRADE_ORG = "交易机构";
    public static final String ALIAS_TRAN_TYPE = "交易类型";
    public static final String ALIAS_DATA_SOURCES = "数据来源";
    public static final String ALIAS_TRAN_DATE = "合同签署日期";
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
    public static final String ALIAS_IS_CASH_SUBSCRIBE = "是否现金认购";
    public static final String ALIAS_INVESTMENT_TYPE = "投资方式";
    public static final String ALIAS_APPROVAL_NUM = "行政审批文号";
    public static final String ALIAS_SECURITIES_TYPE = "证券类型";
    public static final String ALIAS_IN_TIME = "入股时间";
    public static final String ALIAS_SHARE_NUM = "股份数";
    public static final String ALIAS_SHARE_RETIO = "股份占比";
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
    public static final String ALIAS_YIELD_ = "收益率";
    public static final String ALIAS_VARIABLESHARERATIO = "变动股比或份额（%）";
    public static final String ALIAS_VARIABLESHARERATIO_AFTER = "变动后股比或份额(%)";
    public static final String ALIAS_EFFECTIVE_TIME = "生效时间";
    public static final String ALIAS_TOTALASSETS = "关联方总资产";
    public static final String ALIAS_FINANCIAL_IMPACT = "对财务状况及经营成果的影响";
    public static final String ALIAS_TRA_SUB_NAME = "交易主体名称";
    public static final String ALIAS_MAJOR_TYPE = "达到重大关联交易的情形";
    public static final String ALIAS_CONTRACT_AMOUNT = "合同金额";
    public static final String ALIAS_AMEND_ID = "操作人id";
    public static final String ALIAS_AMEND_NAME = "操作人姓名";
    public static final String ALIAS_TRAN_NAME = "交易名称";
    public static final String ALIAS_TRAN_OPPONENT = "交易对手";
    public static final String ALIAS_CERT_TYPE = "证件类型";

    public static final String ALIAS_MANAGER_ORG = "管理机构";
    public static final String ALIAS_ACCU_PRAC_TRADE_AMOUNT = "累计实际交易金额（万元）";
    public static final String ALIAS_REL_TRADE_AMOUNT = "关联交易金额";
    public static final String ALIAS_PRAC_TRADE_AMOUNT = "实际交易金额";


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
    private java.math.BigDecimal totolTradeAmount;

    @Column(name = "CONTRACT_NO", nullable = true, length = 50)
    private String contractNo;

    @Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal tradeAmount;

    @Column(name = "BUS_START_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date busStartTime;

    @Column(name = "BUS_END_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date busEndTime;

    @Column(name = "IS_CASH_SUBSCRIBE", nullable = true, length = 10)
    private String isCashSubscript;

    @Column(name = "INVESTMENT_TYPE", nullable = true, length = 10)
    private String investmentType;

    @Column(name = "APPROVAL_NUM", nullable = true, length = 50)
    private String approvalNum;

    @Column(name = "SECURITIES_TYPE", nullable = true, length = 10)
    private String securitiesType;

    @Column(name = "IN_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date inTime;

    @Column(name = "SHARE_NUM", nullable = true, length = 50)
    private String shareNum;

    @Column(name = "SHARE_RETIO", nullable = true, length = 50)
    private String shareRetio;

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

    @Column(name = "RULE_RESULT", nullable = true, length = 32)
    private String ruleResult;

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

    @Column(name = "HoldingSubsidiary_type", nullable = true, length = 10)
    private String holdingSubsidiaryType;

    @Column(name = "Yield_", nullable = true, length = 10)
    private String yield_;

    @Column(name = "Variableshareratio", nullable = true, length = 10)
    private String variableshareratio;

    @Column(name = "Variableshareratio_after", nullable = true, length = 10)
    private String variableshareratioAfter;

    @Column(name = "EFFECTIVE_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date effectiveTime;

    @Column(name = "totalAssets", nullable = true, length = 100)
    private String totalAssets;

    @Column(name = "Financial_Impact", nullable = true, length = 400)
    private String financialImpact;

    @Column(name = "Tra_sub_Name", nullable = true, length = 20)
    private String traSubName;

    @Column(name = "Major_Type", nullable = true, length = 10)
    private String majorType;

    @Column(name = "Contract_Amount", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal contractAmount;

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



    @Column(name="Archiving_Status")
    private String archivingStatus ;//1：确认逐笔披露； 2：确认逐笔报告；  3：确认季度定期披露；4.确认年度定期披露；5：确认定期报告
    @Column(name="report_Status")
    private String reportStatus;//1：确认逐笔报告；

    @Column(name="quarter_Arc_Status")
    private String quarterArcStatus;//1：确认季度定期披露;

    @Column(name="year_Arc_Status")
    private String yearArcStatus;//1.确认年度定期披露；

    @Column(name="year_Rep_Status")
    private String yearRepStatus;//1：确认年度定期报告

    @Column(name="concentrated_approval")
    private String concentratedApproval;//是否是集中审议 0 是，1否

    @Column(name="not_major_rel_number")
    private String notMajorRelNumber;//未清零的关联方relNumber

    @Column(name="quarter_Rep_Status")
    private String quarterRepStatus;//1：确认季度定期报告

    @Column(name = "proc_inst_id", nullable = true, length = 10)
    private String procInstId;
    //交易主体
    @Column(name = "TENANT_ID_")
    private String tenantId;


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

    @Column(name = "payment_id", nullable = true, length = 10)
    private String paymentId;

    @Column(name = "scene", nullable = true, length = 10)
    private String scene;

    @Column(name = "effectOrPay", nullable = true, length = 10)
    private String effectOrPay;

    @Transient
    private String traSubNameStr;
    @Transient
    private String instead;//导出数据investmentType在xls中无法显示数据，替换investmentType

    @Transient
    private String relTypeStr;
    //业务类型
    @Transient
    private String busTypeStr;
    //是否重大关联交易
    @Transient
    private String majorTypeStr;
    //状态
    @Transient
    private String statusStr;
    //归零操作统计金额
    @Transient
    private java.math.BigDecimal tradeZeroAmount;

    //创建人加姓名
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
    /**
     * 交易对手（关联方）列表
     */
    @Transient
    private List<TraRelInfo> relInfoList;

    @Column(name = "rel_info_list_str", nullable = true, length = 1000)
    private String relInfoListStr;

    //columns END


    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getEffectOrPay() {
        return effectOrPay;
    }

    public void setEffectOrPay(String effectOrPay) {
        this.effectOrPay = effectOrPay;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getQuarterRepStatus() {
        return quarterRepStatus;
    }

    public void setQuarterRepStatus(String quarterRepStatus) {
        this.quarterRepStatus = quarterRepStatus;
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


    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
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

    public String getTraSubNameStr() {
        return traSubNameStr;
    }

    public void setTraSubNameStr(String traSubNameStr) {
        this.traSubNameStr = traSubNameStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getMajorTypeStr() {
        return majorTypeStr;
    }

    public void setMajorTypeStr(String majorTypeStr) {
        this.majorTypeStr = majorTypeStr;
    }

    public String getBusTypeStr() {
        return busTypeStr;
    }

    public void setBusTypeStr(String busTypeStr) {
        this.busTypeStr = busTypeStr;
    }

    public String getRelTypeStr() {
        return relTypeStr;
    }

    public void setRelTypeStr(String relTypeStr) {
        this.relTypeStr = relTypeStr;
    }


    public String getInstead() {
        return instead;
    }

    public void setInstead(String instead) {
        this.instead = instead;
    }
//columns END

    @Transient
    private String tranDateString;
    @Transient
    private String effectiveTimeString;
    @Transient
    private String busStartTimeString;

    @Transient
    private String busEndTimeString;

    @Transient
    private String inTimeString;


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

    public void setEffectiveTimeString(String effectiveTimeString) {
        if (effectiveTimeString != null) {
            setTranDate(DateUtils.parseDate(effectiveTimeString));
        }
    }

    public String getEffectiveTimeString() {
        if (effectiveTime != null) {
            return DateUtils.formatDate(effectiveTime);
        }
        return "";
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

    public String getBusStartTimeString() {
        if (busStartTime != null) {
            return DateUtils.formatDate(busStartTime);
        }
        return "";
    }

    public void setBusStartTimeString(String value) {
        if (value != null) {
            setBusStartTime(DateUtils.parseDate(value));
        }
    }


    public Date getBusEndTime() {
        return this.busEndTime;
    }

    public void setBusEndTime(Date value) {
        this.busEndTime = value;
    }

    public String getBusEndTimeString() {
        if (busEndTime != null) {
            return DateUtils.formatDate(busEndTime);
        }
        return "";
    }

    public void setBusEndTimeString(String value) {
        if (value != null) {
            setBusEndTime(DateUtils.parseDate(value));
        }
    }

    public String getIsCashSubscript() {
        return isCashSubscript;
    }

    public void setIsCashSubscript(String isCashSubscript) {
        this.isCashSubscript = isCashSubscript;
    }

    public String getInvestmentType() {
        return this.investmentType;
    }

    public void setInvestmentType(String value) {
        this.investmentType = value;
    }


    public String getApprovalNum() {
        return this.approvalNum;
    }

    public void setApprovalNum(String value) {
        this.approvalNum = value;
    }


    public String getSecuritiesType() {
        return this.securitiesType;
    }

    public void setSecuritiesType(String value) {
        this.securitiesType = value;
    }


    public Date getInTime() {
        return this.inTime;
    }

    public void setInTime(Date value) {
        this.inTime = value;
    }

    public String getInTimeString() {
        if (inTime != null) {
            return DateUtils.formatDate(inTime);
        }
        return "";
    }

    public void setInTimeString(String value) {
        if (value != null) {
            setInTime(DateUtils.parseDate(value));
        }
    }


    public String getShareNum() {
        return this.shareNum;
    }

    public void setShareNum(String value) {
        this.shareNum = value;
    }


    public String getShareRetio() {
        return this.shareRetio;
    }

    public void setShareRetio(String value) {
        this.shareRetio = value;
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

    public String getHoldingSubsidiaryType() {
        return holdingSubsidiaryType;
    }

    public void setHoldingSubsidiaryType(String holdingSubsidiaryType) {
        this.holdingSubsidiaryType = holdingSubsidiaryType;
    }

    public String getYield_() {
        return yield_;
    }

    public void setYield_(String yield_) {
        this.yield_ = yield_;
    }

    public String getVariableshareratio() {
        return variableshareratio;
    }

    public void setVariableshareratio(String variableshareratio) {
        this.variableshareratio = variableshareratio;
    }

    public String getVariableshareratioAfter() {
        return variableshareratioAfter;
    }

    public void setVariableshareratioAfter(String variableshareratioAfter) {
        this.variableshareratioAfter = variableshareratioAfter;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
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

    @Override
    public String toString() {
        return "TraInvestment{" +
                "pid=" + pid +
                ", relId=" + relId +
                ", relType='" + relType + '\'' +
                ", relName='" + relName + '\'' +
                ", relNumber='" + relNumber + '\'' +
                ", tradeOrg='" + tradeOrg + '\'' +
                ", tranType='" + tranType + '\'' +
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
                ", isCashSubscript='" + isCashSubscript + '\'' +
                ", investmentType='" + investmentType + '\'' +
                ", approvalNum='" + approvalNum + '\'' +
                ", securitiesType='" + securitiesType + '\'' +
                ", inTime=" + inTime +
                ", shareNum='" + shareNum + '\'' +
                ", shareRetio='" + shareRetio + '\'' +
                ", tradePrice='" + tradePrice + '\'' +
                ", pricingPolicy='" + pricingPolicy + '\'' +
                ", tradeSubject='" + tradeSubject + '\'' +
                ", remark='" + remark + '\'' +
                ", transNum='" + transNum + '\'' +
                ", isEffective='" + isEffective + '\'' +
                ", approveTradeAmount='" + approveTradeAmount + '\'' +
                ", ruleResult='" + ruleResult + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", considerType='" + considerType + '\'' +
                ", isHoldingSubsidiary='" + isHoldingSubsidiary + '\'' +
                ", holdingSubsidiaryName='" + holdingSubsidiaryName + '\'' +
                ", holdingSubsidiaryNumber='" + holdingSubsidiaryNumber + '\'' +
                ", holdingSubsidiaryType='" + holdingSubsidiaryType + '\'' +
                ", yield_='" + yield_ + '\'' +
                ", variableshareratio='" + variableshareratio + '\'' +
                ", variableshareratioAfter='" + variableshareratioAfter + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", totalAssets='" + totalAssets + '\'' +
                ", financialImpact='" + financialImpact + '\'' +
                ", traSubName='" + traSubName + '\'' +
                ", majorType='" + majorType + '\'' +
                ", contractAmount=" + contractAmount +
                ", amendId='" + amendId + '\'' +
                ", amendName='" + amendName + '\'' +
                ", tranName='" + tranName + '\'' +
                ", tranOpponent='" + tranOpponent + '\'' +
                ", certType='" + certType + '\'' +
                ", managerOrg='" + managerOrg + '\'' +
                ", accuPracTradeAmount=" + accuPracTradeAmount +
                ", relTradeAmount=" + relTradeAmount +
                ", pracTradeAmount='" + pracTradeAmount + '\'' +
                ", archivingStatus='" + archivingStatus + '\'' +
                ", reportStatus='" + reportStatus + '\'' +
                ", quarterArcStatus='" + quarterArcStatus + '\'' +
                ", yearArcStatus='" + yearArcStatus + '\'' +
                ", yearRepStatus='" + yearRepStatus + '\'' +
                ", concentratedApproval='" + concentratedApproval + '\'' +
                ", notMajorRelNumber='" + notMajorRelNumber + '\'' +
                ", quarterRepStatus='" + quarterRepStatus + '\'' +
                ", procInstId='" + procInstId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", transactionName='" + transactionName + '\'' +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", sourceSystem='" + sourceSystem + '\'' +
                ", sourceModule='" + sourceModule + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", scene='" + scene + '\'' +
                ", effectOrPay='" + effectOrPay + '\'' +
                ", traSubNameStr='" + traSubNameStr + '\'' +
                ", instead='" + instead + '\'' +
                ", relTypeStr='" + relTypeStr + '\'' +
                ", busTypeStr='" + busTypeStr + '\'' +
                ", majorTypeStr='" + majorTypeStr + '\'' +
                ", statusStr='" + statusStr + '\'' +
                ", tradeZeroAmount=" + tradeZeroAmount +
                ", creater=" + creater + '\'' +
                ", relInfoList=" + relInfoList +
                ", relInfoListStr='" + relInfoListStr + '\'' +
                ", tranDateString='" + tranDateString + '\'' +
                ", effectiveTimeString='" + effectiveTimeString + '\'' +
                ", busStartTimeString='" + busStartTimeString + '\'' +
                ", busEndTimeString='" + busEndTimeString + '\'' +
                ", inTimeString='" + inTimeString + '\'' +
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
        if (obj instanceof TraInvestment == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraInvestment other = (TraInvestment) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

