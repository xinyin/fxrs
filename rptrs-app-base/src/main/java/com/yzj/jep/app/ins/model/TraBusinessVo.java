/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:38
 *
 */

package com.yzj.jep.app.ins.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ins_tra_business")
public class TraBusinessVo extends BasePO {

    public static final String TABLE_ALIAS = "关联交易表（保险）-保险业务类";
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
    public static final String ALIAS_UNITF_TRADE_AGREMENT_BUSINESS = "统一交易协议业务情况";
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
    public static final String ALIAS_IS_EFFCTIVE = "是否生效";
    public static final String ALIAS_APPROVE_TRADE_AMOUNT = "审批金额";


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

    @Column(name = "UNITF_TRADE_AGREMENT_BUSINESS", nullable = true, length = 5)
    private String unitfTradeAgrmentAbusiness;

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

    @Column(name = "TRANS_NUM", nullable = true, length = 32)
    private String transNum;

    @Column(name = "IS_EFFECTIVE", nullable = true, length = 32)
    private String isEffective;

    @Column(name = "APPROVE_TRADE_AMOUNT", nullable = true, length = 32)
    private String approveTradeAmount;
    //columns END

    private String no;//序号
    private String tranDateString;
    private String busStartTimeString;
    private String busEndTimeString;

    private String contractDateString;

    //2021.8.18
    private String managerOrg;//管理机构名称
    private String tranName;//交易名称
    private String isHoldingSubsidiary;//发起方是否控股子公司
    private String holdingSubsidiaryName;//发起方公司全称
    private String holdingSubsidiaryType;//发起方公司证件类型
    private String holdingSubsidiaryNumber;//发起方公司证件号码
    private String tranOpponent;//交易对手名称
    private Date contractDate;//合同签署日期
    private String relTradeAmount;//关联交易金额
    private String isCashSubscript;//是否现金认购
    private String isAgreement;//是否统一交易协议
    private String isAgreementImpl;//统一交易协议执行情况
    private String agreementNum;//统一交易协议编号
    private String financialImpact;//对财务状况及经营成果的影响
    //交易主体名称
    private String traSubName;
    //达到重大关联交易的情形
    private String majorType;
    //关联方总资产
    private String totalAssets;
    //关联方证件类型
    private String certType;
    //关联交易实际金额（万元）
    private String pracTradeAmount;
    /** 免于审议情形 */
    private String considerSituation;

    private String tradeAgreementType;

    /** 是否可免于审议 */

    private String isconsiderSituation;

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

    /** 单笔关联交易类型 */
    @Column(name = "single_related_trantype", nullable = true, length = 1)
    private String singleRelatedTranType;
    //审议类型
    @Column(name = "consider_type", nullable = true, length = 10)
    private String considerType;

    /** 是否需逐笔报告 */
    @Column(name = "each_report", nullable = true, length = 30)
    private String eachReport;
    /** 是否需逐笔披露 */
    @Column(name = "each_disclosure", nullable = true, length = 30)
    private String eachDisclosure;

    /**  是否需要补录关联方信息 */
    @Column(name = "is_collect_related", nullable = true, length = 1)
    private String isCollectRelated;


    //保单号
    @Column(name = "NUMBER_OF_POLICY", nullable = true, length = 50)
    private String  numberOfPolicy;

    //团个性质
    @Column(name = "GROUP_THE_CHAR", nullable = true, length = 10)
    private String  groupTheChar;


    //协议交易标志
    @Column(name = "PROTOCOL_TRANSACTION_SYMBOL", nullable = true, length = 100)
    private String protocolTransactionSymbol;

    //合同状态
    @Column(name = "CONTRACT_STATUS", nullable = true, length = 100)
    private String contractStatus;

    // 数据版本
    @Column(name = "data_version", nullable = true, length = 64)
    private String dataVersion;

    @Column(name = "FUNDS_FLOW_NUMBER", nullable = true, length = 100)
    private String fundsFlowNumber;

    public String getFundsFlowNumber() {
        return fundsFlowNumber;
    }

    public void setFundsFlowNumber(String fundsFlowNumber) {
        this.fundsFlowNumber = fundsFlowNumber;
    }

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

    public String getNumberOfPolicy() {
        return numberOfPolicy;
    }

    public void setNumberOfPolicy(String numberOfPolicy) {
        this.numberOfPolicy = numberOfPolicy;
    }

    public String getGroupTheChar() {
        return groupTheChar;
    }

    public void setGroupTheChar(String groupTheChar) {
        this.groupTheChar = groupTheChar;
    }

    public String getContractDateString() {
        return contractDateString;
    }

    public void setContractDateString(String contractDateString) {
        this.contractDateString = contractDateString;
    }

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


    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getTraSubName() {
        return traSubName;
    }

    public void setTraSubName(String traSubName) {
        this.traSubName = traSubName;
    }

    public String getRelTradeAmount() {
        return relTradeAmount;
    }

    public void setRelTradeAmount(String relTradeAmount) {
        this.relTradeAmount = relTradeAmount;
    }

    public String getManagerOrg() {
        return managerOrg;
    }

    public void setManagerOrg(String managerOrg) {
        this.managerOrg = managerOrg;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
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

    public String getHoldingSubsidiaryType() {
        return holdingSubsidiaryType;
    }

    public void setHoldingSubsidiaryType(String holdingSubsidiaryType) {
        this.holdingSubsidiaryType = holdingSubsidiaryType;
    }

    public String getHoldingSubsidiaryNumber() {
        return holdingSubsidiaryNumber;
    }

    public void setHoldingSubsidiaryNumber(String holdingSubsidiaryNumber) {
        this.holdingSubsidiaryNumber = holdingSubsidiaryNumber;
    }

    public String getTranOpponent() {
        return tranOpponent;
    }

    public void setTranOpponent(String tranOpponent) {
        this.tranOpponent = tranOpponent;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getIsCashSubscript() {
        return isCashSubscript;
    }

    public void setIsCashSubscript(String isCashSubscript) {
        this.isCashSubscript = isCashSubscript;
    }

    public String getIsAgreement() {
        return isAgreement;
    }

    public void setIsAgreement(String isAgreement) {
        this.isAgreement = isAgreement;
    }

    public String getIsAgreementImpl() {
        return isAgreementImpl;
    }

    public void setIsAgreementImpl(String isAgreementImpl) {
        this.isAgreementImpl = isAgreementImpl;
    }

    public String getAgreementNum() {
        return agreementNum;
    }

    public void setAgreementNum(String agreementNum) {
        this.agreementNum = agreementNum;
    }

    public String getFinancialImpact() {
        return financialImpact;
    }

    public void setFinancialImpact(String financialImpact) {
        this.financialImpact = financialImpact;
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

    public String getUnitfTradeAgrmentAbusiness() {
        return unitfTradeAgrmentAbusiness;
    }

    public void setUnitfTradeAgrmentAbusiness(String unitfTradeAgrmentAbusiness) {
        this.unitfTradeAgrmentAbusiness = unitfTradeAgrmentAbusiness;
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
        return tranDateString;
    }

    public void setTranDateString(String tranDateString) {
        this.tranDateString = tranDateString;
    }

    public String getBusStartTimeString() {
        return busStartTimeString;
    }

    public void setBusStartTimeString(String busStartTimeString) {
        this.busStartTimeString = busStartTimeString;
    }

    public String getBusEndTimeString() {
        return busEndTimeString;
    }

    public void setBusEndTimeString(String busEndTimeString) {
        this.busEndTimeString = busEndTimeString;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPracTradeAmount() {
        return pracTradeAmount;
    }

    public void setPracTradeAmount(String pracTradeAmount) {
        this.pracTradeAmount = pracTradeAmount;
    }

    public String getConsiderSituation() {
        return considerSituation;
    }

    public void setConsiderSituation(String considerSituation) {
        this.considerSituation = considerSituation;
    }

    public String getTradeAgreementType() {
        return tradeAgreementType;
    }

    public void setTradeAgreementType(String tradeAgreementType) {
        this.tradeAgreementType = tradeAgreementType;
    }

    public String getIsconsiderSituation() {
        return isconsiderSituation;
    }

    public void setIsconsiderSituation(String isconsiderSituation) {
        this.isconsiderSituation = isconsiderSituation;
    }

    @Override
    public String toString() {
        return new StringBuilder("traBusiness[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("relId = ").append(relId).append(", \n")
                .append("relType = ").append(relType).append(", \n")
                .append("relName = ").append(relName).append(", \n")
                .append("relNumber = ").append(relNumber).append(", \n")
                .append("tradeOrg = ").append(tradeOrg).append(", \n")
                .append("tranType = ").append(tranType).append(", \n")
                .append("busType = ").append(busType).append(", \n")
                .append("dataSources = ").append(dataSources).append(", \n")
                .append("tranDate = ").append(tranDate).append(", \n")
                .append("majorTrade = ").append(majorTrade).append(", \n")
                .append("caliber = ").append(caliber).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("pracTradeAmount = ").append(pracTradeAmount).append(", \n")
                .append("considerSituation = ").append(considerSituation).append(", \n")
                .append("tradeAgreementType = ").append(tradeAgreementType).append(", \n")
                .append("isconsiderSituation = ").append(isconsiderSituation).append(", \n")
                .append("totolTradeAmount = ").append(totolTradeAmount).append(", \n")
                .append("contractNo = ").append(contractNo).append(", \n")
                .append("tradeAmount = ").append(tradeAmount).append(", \n")
                .append("busStartTime = ").append(busStartTime).append(", \n")
                .append("busEndTime = ").append(busEndTime).append(", \n")
                .append("tradeOccur = ").append(tradeOccur).append(", \n")
                .append("tradePrice = ").append(tradePrice).append(", \n")
                .append("pricingPolicy = ").append(pricingPolicy).append(", \n")
                .append("tradeSubject = ").append(tradeSubject).append(", \n")
                .append("remark = ").append(remark).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("isEffective = ").append(isEffective).append(", \n")
                .append("approveTradeAmount = ").append(approveTradeAmount).append(", \n")
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
        if (obj instanceof TraBusinessVo == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraBusinessVo other = (TraBusinessVo) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

