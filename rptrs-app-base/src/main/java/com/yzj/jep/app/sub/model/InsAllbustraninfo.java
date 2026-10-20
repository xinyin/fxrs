/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:30
 *
 */

package com.yzj.jep.app.sub.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;


@Entity
@Table(name = "sub_ins_allbustraninfo")
public class InsAllbustraninfo extends BasePO {

    public static final String TABLE_ALIAS = "InsAllbustraninfo";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_REL_TYPE = "关联方类型";
    public static final String ALIAS_REL_NAME = "关联方名称";
    public static final String ALIAS_REL_NUMBER = "关联方证件号码";
    public static final String ALIAS_TRAN_TYPE = "交易类型";
    public static final String ALIAS_INVESTMENT_TYPE = "业务类型";
    public static final String ALIAS_CONTRACT_NO = "合同编号";
    public static final String ALIAS_TRADE_AMOUNT = "交易金额（万元）";
    public static final String ALIAS_BUS_START_TIME = "业务起始日期";
    public static final String ALIAS_STATUS = "数据校验结果";
    public static final String ALIAS_REPORT_STATUS = "报送文件状态";
    public static final String ALIAS_TRADE_SUBJECT = "交易标的";
    public static final String ALIAS_TRADE_PRICE = "交易价格";
    public static final String ALIAS_PRICING_POLICY = "定价政策";
    public static final String ALIAS_IS_HOLDING_SUBSIDIARY = "是否控股子公司交易";
    public static final String ALIAS_HOLDING_SUBSIDIARY_NAME = "控股子公司全称";
    public static final String ALIAS_HOLDING_SUBSIDIARY_NUMBER = "控股子公司有效证件号码";
    public static final String ALIAS_REMARK = "其他事项说明";
    public static final String ALIAS_TRAN_DATE = "合同签署日期";
    public static final String ALIAS_IS_OVERSEASINVESTMENT = "是否境外投资";
    public static final String ALIAS_SUB_TYPE = "subQuarter";
    public static final String ALIAS_SUB_YEAR = "subYear";
    public static final String PORT_PID = "对应报送文件的pid";
    public static final String TRA_STATUS = "对应报送文件的文档状态";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "PID", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "REL_TYPE", nullable = true, length = 100)
    private String relType;

    @Column(name = "REL_NAME", nullable = true, length = 100)
    private String relName;

    @Column(name = "REL_NUMBER", nullable = true, length = 100)
    private String relNumber;

    @Column(name = "TRAN_TYPE", nullable = true, length = 100)
    private String tranType;

    @Column(name = "INVESTMENT_TYPE", nullable = true, length = 100)
    private String investmentType;  //原业务类型

    @Column(name = "CONTRACT_NO", nullable = true, length = 100)
    private String contractNo;

    @Column(name = "TRADE_AMOUNT", nullable = true, precision = 64, scale = 2)
    private java.math.BigDecimal tradeAmount;

    @Column(name = "BUS_START_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date busStartTime;

    @Column(name = "STATUS", nullable = true, length = 100)
    private String status;

    @Column(name = "REPORT_STATUS", nullable = true, length = 100)
    private String reportStatus;

    @Column(name = "TRADE_SUBJECT", nullable = true, length = 100)
    private String tradeSubject;

    @Column(name = "TRADE_PRICE", nullable = true, length = 300)
    private String tradePrice;

    @Column(name = "PRICING_POLICY", nullable = true, length = 255)
    private String pricingPolicy;

    @Column(name = "is_HoldingSubsidiary", nullable = true, length = 255)
    private String isHoldingSubsidiary;

    @Column(name = "HoldingSubsidiary_name", nullable = true, length = 255)
    private String holdingSubsidiaryName;

    @Column(name = "HoldingSubsidiary_number", nullable = true, length = 50)
    private String holdingSubsidiaryNumber;

    @Column(name = "REMARK", nullable = true, length = 255)
    private String remark;

    @Column(name = "TRAN_DATE", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date tranDate;

    @Column(name = "is_Overseasinvestment", nullable = true, length = 10)
    private String isOverseasinvestment;

    @Column(name = "sub_year", nullable = false, length = 10)
    private String subYear;

    @Column(name = "sub_quarter", nullable = false, length = 10)
    private String subQuarter;

    @Column(name = "port_pid")
    private Integer portPid;
    @Column(name = "tra_status")
    private String traStatus;
    @Column(name = "is_discovery", nullable = true, length = 6)
    private String isDiscovery;
    @Column(name = "FINANCIAL_ASSSTS_NAME", nullable = true, length = 10)
    private String financialAssstsName;

    @Column(name = "FINANCIAL_PRO_SCALE", nullable = true, length = 50)
    private String financialProScale;

    @Column(name = "UNDER_INVOLVE_REL", nullable = true, length = 10)
    private String underInvolveRel;

    @Column(name = "FINANCIAL_PRO_RATIO", nullable = true, length = 50)
    private String financialProRatio;
    //新增
    @Column(name = "MAJOR_TRADE", nullable = true, length = 10)
    private String majorTrade;
    @Column(name = "IS_AGREEMENT_IMPL")
    private String isAgreementImpl;//统一交易情况
    @Column(name = "is_agreement", nullable = true, length = 8)
    private String isAgreement;//是否是统一交易
    @Column(name = "agreement_num", length = 32)
    private String agreementNum;//主协议编号
    @Column(name = "pub_id", length = 32)
    private String pubId;//披露文件标识

    @Column(name = "Trading_direction", length = 32)
    private String tradingDirection;// 交易方向

    @Column(name = "BUS_TYPE", nullable = true, length = 50)
    private String busType; //业务类型
    //columns END
    //金融资产编号
    @Column(name = "FINANCIAL_ASSSTS_NUM", nullable = true, length = 50)
    private String financialAssstsNum;


    public String getFinancialAssstsNum() {
        return financialAssstsNum;
    }

    public void setFinancialAssstsNum(String financialAssstsNum) {
        this.financialAssstsNum = financialAssstsNum;
    }

    public String getTradingDirection() {
        return tradingDirection;
    }

    public void setTradingDirection(String tradingDirection) {
        this.tradingDirection = tradingDirection;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    @Transient
    private String busStartTimeString;
    @Transient
    private String investmentTypeStr;

    public String getInvestmentTypeStr() {
        return investmentTypeStr;
    }

    public void setInvestmentTypeStr(String investmentTypeStr) {
        this.investmentTypeStr = investmentTypeStr;
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

    public String getFinancialAssstsName() {
        return financialAssstsName;
    }

    public void setFinancialAssstsName(String financialAssstsName) {
        this.financialAssstsName = financialAssstsName;
    }

    public String getFinancialProScale() {
        return financialProScale;
    }

    public void setFinancialProScale(String financialProScale) {
        this.financialProScale = financialProScale;
    }

    public String getUnderInvolveRel() {
        return underInvolveRel;
    }

    public void setUnderInvolveRel(String underInvolveRel) {
        this.underInvolveRel = underInvolveRel;
    }

    public String getFinancialProRatio() {
        return financialProRatio;
    }

    public void setFinancialProRatio(String financialProRatio) {
        this.financialProRatio = financialProRatio;
    }

    //columns END


    public String getMajorTrade() {
        return majorTrade;
    }

    public void setMajorTrade(String majorTrade) {
        this.majorTrade = majorTrade;
    }

    public String getIsAgreementImpl() {
        return isAgreementImpl;
    }

    public void setIsAgreementImpl(String isAgreementImpl) {
        this.isAgreementImpl = isAgreementImpl;
    }

    public String getIsAgreement() {
        return isAgreement;
    }

    public void setIsAgreement(String isAgreement) {
        this.isAgreement = isAgreement;
    }

    public String getAgreementNum() {
        return agreementNum;
    }

    public void setAgreementNum(String agreementNum) {
        this.agreementNum = agreementNum;
    }

    public String getIsDiscovery() {
        return isDiscovery;
    }

    public void setIsDiscovery(String isDiscovery) {
        this.isDiscovery = isDiscovery;
    }

    public String getTraStatus() {
        return traStatus;
    }

    public void setTraStatus(String traStatus) {
        this.traStatus = traStatus;
    }

    public Integer getPortPid() {
        return portPid;
    }

    public void setPortPid(Integer portPid) {
        this.portPid = portPid;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
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


    public String getTranType() {
        return this.tranType;
    }

    public void setTranType(String value) {
        this.tranType = value;
    }


    public String getInvestmentType() {
        return this.investmentType;
    }

    public void setInvestmentType(String value) {
        this.investmentType = value;
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


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getReportStatus() {
        return this.reportStatus;
    }

    public void setReportStatus(String value) {
        this.reportStatus = value;
    }


    public String getTradeSubject() {
        return this.tradeSubject;
    }

    public void setTradeSubject(String value) {
        this.tradeSubject = value;
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


    public String getIsOverseasinvestment() {
        return this.isOverseasinvestment;
    }

    public void setIsOverseasinvestment(String value) {
        this.isOverseasinvestment = value;
    }

    public String getSubYear() {
        return this.subYear;
    }

    public void setSubYear(String value) {
        this.subYear = value;
    }


    public String getSubQuarter() {
        return this.subQuarter;
    }

    public void setSubQuarter(String value) {
        this.subQuarter = value;
    }


    //报送文件需要的映射
    @Transient
    private String relTypeSub;
    @Transient
    private String tranDateSub;
    @Transient
    private String tranTypeSub;
    @Transient
    private String IsHoldingSubsidiarySub;
    @Transient
    private String isOverseasinvestmentSub;


    public String getRelTypeSub() {
        if ("ORG".equals(relType)) {
            return "1";
        }
        return "2";
    }

    public String getTranDateSub() {
        if (tranDate != null) {
            return DateUtils.formatDateShort(tranDate);
        }
        return "";
    }


    public String getTranTypeSub() {
        switch (tranType) {
            case "01":
                return "1";
            case "02":
                return "2";
            case "03":
                return "3";
            case "04":
                return "4";
            case "05":
                return "5";
            case "06":
                return "6";
            default:
                return null;
        }
    }

    public String getIsHoldingSubsidiarySub() {
        return "1".equals(isHoldingSubsidiary) ? "0" : "1";
    }

    public String getIsOverseasinvestmentSub() {
        return "1".equals(isOverseasinvestment) ? "0" : "1";
    }


    @Override
    public String toString() {
        return "InsAllbustraninfo{" +
                "pid=" + pid +
                ", relType='" + relType + '\'' +
                ", relName='" + relName + '\'' +
                ", relNumber='" + relNumber + '\'' +
                ", tranType='" + tranType + '\'' +
                ", investmentType='" + investmentType + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", tradeAmount=" + tradeAmount +
                ", busStartTime=" + busStartTime +
                ", status='" + status + '\'' +
                ", reportStatus='" + reportStatus + '\'' +
                ", tradeSubject='" + tradeSubject + '\'' +
                ", tradePrice=" + tradePrice +
                ", pricingPolicy='" + pricingPolicy + '\'' +
                ", isHoldingSubsidiary='" + isHoldingSubsidiary + '\'' +
                ", holdingSubsidiaryName='" + holdingSubsidiaryName + '\'' +
                ", holdingSubsidiaryNumber='" + holdingSubsidiaryNumber + '\'' +
                ", remark='" + remark + '\'' +
                ", tranDate=" + tranDate +
                ", isOverseasinvestment='" + isOverseasinvestment + '\'' +
                ", subYear='" + subYear + '\'' +
                ", subQuarter='" + subQuarter + '\'' +
                '}';
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof InsAllbustraninfo == false) return false;
        if (this == obj) return true;
        InsAllbustraninfo other = (InsAllbustraninfo) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

