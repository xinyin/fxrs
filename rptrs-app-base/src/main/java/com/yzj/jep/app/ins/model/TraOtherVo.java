/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月08日 19:41:07
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
import java.util.List;


@Entity
@Table(name = "ins_tra_other")
public class TraOtherVo extends BasePO {

    public static final String TABLE_ALIAS = "TraOther";
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
    public static final String ALIAS_IS_EFFECTIVE = "是否生效";
    public static final String ALIAS_APPROVE_TRADE_AMOUNT = "审批金额";
    public static final String ALIAS_TRANS_NUM = "交易编号";
    public static final String ALIAS_UNITF_TRADE_AGREMENT_BUSINESS = "统一交易协议业务情况";
    public static final String ALIAS_IS_DEAL_PRICE = "交易定价是否为国家规定";
    public static final String ALIAS_CONSIDER_TYPE = "审议类型(1:可免予审议，2:可集中审议，3逐笔审议)";
    public static final String ALIAS_IS_HOLDING_SUBSIDIARY = "是否为控股子公司";
    public static final String ALIAS_HOLDING_SUBSIDIARY_NAME = "控股子公司名称";
    public static final String ALIAS_HOLDING_SUBSIDIARY_NUMBER = "控股子公司证件号码";
    public static final String ALIAS_TOTAL_ASSETS = "关联方总资产";
    public static final String ALIAS_FINANCIAL_IMPACT = "对财务状况及经营成果的影响";
    public static final String ALIAS_TRA_SUB_NAME = "交易主体名称";
    public static final String ALIAS_MAJOR_TYPE = "达到重大关联交易的情形";
    public static final String ALIAS_CONTRACT_AMOUNT = "合同金额（万元）";
    public static final String ALIAS_HOLDING_SUBSIDIARY_TYPE = "控股公司证件类型";
    public static final String ALIAS_IS_CASH_SUBSCRIBE = "是否现金认购";
    public static final String ALIAS_AMEND_ID = "操作人id";
    public static final String ALIAS_AMEND_NAME = "操作人姓名";
    public static final String ALIAS_TRAN_NAME = "交易名称";
    public static final String ALIAS_TRAN_OPPONENT = "交易对手";
    public static final String ALIAS_CERT_TYPE = "证件类型";
    public static final String ALIAS_ACTUAL_TOTAL = "实际累计交易金额（万元）";
    public static final String AlIAS_CONTRACT_TOTAL="关联交易金额";
    public static final String ALIAS_CONTRACT_DATE="合同签署日期";


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

    @Column(name = "TRAN_DATE", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date tranDate;

    @Column(name = "MAJOR_TRADE", nullable = true, length = 10)
    private String majorTrade;

    @Column(name = "CALIBER", nullable = true, length = 50)
    private String caliber;

    @Column(name = "STATUS", nullable = true, length = 10)
    private String status;

    @Column(name = "STATUS_COMMENT", nullable = true, length = 100)
    private String statusComment;

    @Column(name = "TOTOL_TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private BigDecimal totolTradeAmount;

    @Column(name = "CONTRACT_NO", nullable = true, length = 50)
    private String contractNo;

    @Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private BigDecimal tradeAmount;

    @Column(name = "BUS_START_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date busStartTime;

    @Column(name = "BUS_END_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
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

    @Column(name = "IS_EFFECTIVE", nullable = true, length = 1)
    private String isEffective;

    @Column(name = "APPROVE_TRADE_AMOUNT", nullable = true, precision = 10, scale = 0)
    private Long approveTradeAmount;

    @Column(name = "TRANS_NUM", nullable = true, length = 32)
    private String transNum;

    @Column(name = "UNITF_TRADE_AGREMENT_BUSINESS", nullable = true, length = 10)
    private String unitfTradeAgrementBusiness;

    @Column(name = "IS_DEAL_PRICE", nullable = true, length = 5)
    private String isDealPrice;

    @Column(name = "consider_type", nullable = true, length = 10)
    private String considerType;

    @Column(name = "is_HoldingSubsidiary", nullable = true, length = 10)
    private String isHoldingSubsidiary;

    @Column(name = "HoldingSubsidiary_name", nullable = true, length = 20)
    private String holdingSubsidiaryName;

    @Column(name = "HoldingSubsidiary_number", nullable = true, length = 100)
    private String holdingSubsidiaryNumber;

    @Column(name = "totalAssets", nullable = true, length = 100)
    private String totalAssets;

    @Column(name = "Financial_Impact", nullable = true, length = 400)
    private String financialImpact;

    @Column(name = "Tra_sub_Name", nullable = true, length = 20)
    private String traSubName;

    @Column(name = "Major_Type", nullable = true, length = 10)
    private String majorType;

    @Column(name = "Contract_Amount", nullable = true, precision = 50, scale = 0)
    private BigDecimal contractAmount;

    @Column(name = "HoldingSubsidiary_type", nullable = true, length = 10)
    private String holdingSubsidiaryType;

    @Column(name = "IS_CASH_SUBSCRIBE", nullable = true, length = 10)
    private String isCashSubscribe;

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
    @Column(name="actual_total")
    private BigDecimal actualTotal;
    @Column(name="CONTRACT_TOTAL")
    private BigDecimal contractTotal;
    @Column(name="CONTRACT_DATE")
    private Date contractDate;

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
    @Column(name="manager_org")
    private String managerOrg;
    @Column(name="quarter_Rep_Status")
    private String quarterRepStatus;//1：确认季度定期报告

    public String getQuarterRepStatus() {
        return quarterRepStatus;
    }

    public void setQuarterRepStatus(String quarterRepStatus) {
        this.quarterRepStatus = quarterRepStatus;
    }




    public String getManagerOrg() {
        return managerOrg;
    }

    public void setManagerOrg(String managerOrg) {
        this.managerOrg = managerOrg;
    }

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

    @Column(name = "PRAC_TRADE_AMOUNT", nullable = true, length = 100)
    private String pracTradeAmount;

    public String getPracTradeAmount() {
        return pracTradeAmount;
    }

    public void setPracTradeAmount(String pracTradeAmount) {
        this.pracTradeAmount = pracTradeAmount;
    }
    public String getArchivingStatus() {
        return archivingStatus;
    }

    public void setArchivingStatus(String archivingStatus) {
        this.archivingStatus = archivingStatus;
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
//columns END

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
    private String busTypStr;
    @Transient
    private String majorTradeStr;
    @Transient
    private String statusStr;
    //EXCEL 业务类型
    @Transient
    private String investmentType;

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    /**
     * 交易对手（关联方）列表
     */
    @Transient
    private List<TraRelInfo> relInfoList;

    @Column(name = "rel_info_list_str", nullable = true, length = 1000)
    private String relInfoListStr;

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
    private BigDecimal tradeZeroAmount;

    public BigDecimal getTradeZeroAmount() {
        return tradeZeroAmount;
    }

    public void setTradeZeroAmount(BigDecimal tradeZeroAmount) {
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

    public String getRelTypeStr() {
        return relTypeStr;
    }

    public void setRelTypeStr(String relTypeStr) {
        this.relTypeStr = relTypeStr;
    }

    public String getBusTypStr() {
        return busTypStr;
    }

    public void setBusTypStr(String busTypStr) {
        this.busTypStr = busTypStr;
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
        if (busStartTimeString != null) {
            setBusStartTime(DateUtils.parseDate(busStartTimeString));
        }
    }

    public String getBusEndTimeString() {
        if (busEndTime != null) {
            return DateUtils.formatDate(busEndTime);
        }
        return "";
    }

    public void setBusEndTimeString(String busEndTimeString) {
        if (busEndTimeString != null) {
            setBusEndTime(DateUtils.parseDate(busEndTimeString));
        }
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


    public BigDecimal getTotolTradeAmount() {
        return this.totolTradeAmount;
    }

    public void setTotolTradeAmount(BigDecimal value) {
        this.totolTradeAmount = value;
    }

    private String totalTradeAmountStr;

    public String getTotalTradeAmountStr() {
        if (totolTradeAmount != null) {
            return String.valueOf(totolTradeAmount);
        }
        return totalTradeAmountStr;
    }

    public void setTotalTradeAmountStr(String totalTradeAmountStr) {
        if (totalTradeAmountStr != null) {
            setTotolTradeAmount(new BigDecimal(totalTradeAmountStr));
        }
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


    public String getUnitfTradeAgrementBusiness() {
        return this.unitfTradeAgrementBusiness;
    }

    public void setUnitfTradeAgrementBusiness(String value) {
        this.unitfTradeAgrementBusiness = value;
    }


    public String getIsDealPrice() {
        return this.isDealPrice;
    }

    public void setIsDealPrice(String value) {
        this.isDealPrice = value;
    }


    public String getConsiderType() {
        return this.considerType;
    }

    public void setConsiderType(String value) {
        this.considerType = value;
    }


    public String getIsHoldingSubsidiary() {
        return this.isHoldingSubsidiary;
    }

    public void setIsHoldingSubsidiary(String value) {
        this.isHoldingSubsidiary = value;
    }


    public String getHoldingSubsidiaryName() {
        return this.holdingSubsidiaryName;
    }

    public void setHoldingSubsidiaryName(String value) {
        this.holdingSubsidiaryName = value;
    }


    public String getHoldingSubsidiaryNumber() {
        return this.holdingSubsidiaryNumber;
    }

    public void setHoldingSubsidiaryNumber(String value) {
        this.holdingSubsidiaryNumber = value;
    }


    public String getTotalAssets() {
        return this.totalAssets;
    }

    public void setTotalAssets(String value) {
        this.totalAssets = value;
    }


    public String getFinancialImpact() {
        return this.financialImpact;
    }

    public void setFinancialImpact(String value) {
        this.financialImpact = value;
    }


    public String getTraSubName() {
        return this.traSubName;
    }

    public void setTraSubName(String value) {
        this.traSubName = value;
    }


    public String getMajorType() {
        return this.majorType;
    }

    public void setMajorType(String value) {
        this.majorType = value;
    }


    public BigDecimal getContractAmount() {
        return this.contractAmount;
    }

    public void setContractAmount(BigDecimal value) {
        this.contractAmount = value;
    }


    public String getHoldingSubsidiaryType() {
        return this.holdingSubsidiaryType;
    }

    public void setHoldingSubsidiaryType(String value) {
        this.holdingSubsidiaryType = value;
    }


    public String getIsCashSubscribe() {
        return this.isCashSubscribe;
    }

    public void setIsCashSubscribe(String value) {
        this.isCashSubscribe = value;
    }


    @Override
    public String toString() {
        return "TraOther{" +
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
                ", isEffective='" + isEffective + '\'' +
                ", approveTradeAmount=" + approveTradeAmount +
                ", transNum='" + transNum + '\'' +
                ", unitfTradeAgrementBusiness='" + unitfTradeAgrementBusiness + '\'' +
                ", isDealPrice='" + isDealPrice + '\'' +
                ", considerType='" + considerType + '\'' +
                ", isHoldingSubsidiary='" + isHoldingSubsidiary + '\'' +
                ", holdingSubsidiaryName='" + holdingSubsidiaryName + '\'' +
                ", holdingSubsidiaryNumber='" + holdingSubsidiaryNumber + '\'' +
                ", totalAssets='" + totalAssets + '\'' +
                ", financialImpact='" + financialImpact + '\'' +
                ", traSubName='" + traSubName + '\'' +
                ", majorType='" + majorType + '\'' +
                ", contractAmount=" + contractAmount +
                ", holdingSubsidiaryType='" + holdingSubsidiaryType + '\'' +
                ", isCashSubscribe='" + isCashSubscribe + '\'' +
                ", amendId='" + amendId + '\'' +
                ", amendName='" + amendName + '\'' +
                ", tranDateString='" + tranDateString + '\'' +
                ", busStartTimeString='" + busStartTimeString + '\'' +
                ", busEndTimeString='" + busEndTimeString + '\'' +
                ", traSubNameStr='" + traSubNameStr + '\'' +
                ", relTypeStr='" + relTypeStr + '\'' +
                ", busTypStr='" + busTypStr + '\'' +
                ", majorTradeStr='" + majorTradeStr + '\'' +
                ", statusStr='" + statusStr + '\'' +
                '}';
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof TraOtherVo == false) return false;
        if (this == obj) return true;
        TraOtherVo other = (TraOtherVo) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }

	public BigDecimal getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(BigDecimal actualTotal) {
		this.actualTotal = actualTotal;
	}
    public BigDecimal getContractTotal() {
        return contractTotal;
    }

    public void setContractTotal(BigDecimal contractTotal) {
        this.contractTotal = contractTotal;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }
    public String getContractDateString() {
        if (contractDate != null) {
            return DateUtils.formatDate(contractDate);
        }
        return "";
    }

    public void setContractDateString(String contractDateString) {
        if (contractDateString != null) {
            setTranDate(DateUtils.parseDate(contractDateString));
        }
    }
}

