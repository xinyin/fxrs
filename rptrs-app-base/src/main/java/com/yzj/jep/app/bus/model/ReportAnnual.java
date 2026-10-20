package com.yzj.jep.app.bus.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.*;

public class ReportAnnual {

    String pid;
    //交易类型
    String tranType;
    //一般交易笔数
    String generalRelTranNumber;
    //重大交易笔数
    String majorRelTranNumber;
    //重大交易金额
    String majorRelTranAmount;
    //一般交易金额
    String generalRelTranAmount;
    //总交易金额
    String tranAmount;
    //免审议金额
    String considerAmount;
    String year;
    String quarter;

    //交易编号
    private String transNum;
    //交易主体名称
    private String traSubName;
    //交易对手名称
    private String relName;
    //交易对手类型
    private String relType;
    //是否重大关联交易
    private String majorTrade;
    //审议类型
    private String considerType;
    //合同签署日期
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date tranDate;
    //合同金额
    private java.math.BigDecimal contractAmount;
    //交易状态
    private String status;
    private String tranTime;

    public String getTranTime() {
        if (tranDate != null) {
            return DateUtils.formatDate(tranDate);
        }
        return "";
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getQuarter() {
        if (quarter == null) {
            return "全季度";
        }
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getGeneralRelTranNumber() {
        if (generalRelTranNumber == null) {
            return "0";
        }
        return generalRelTranNumber;
    }

    public void setGeneralRelTranNumber(String generalRelTranNumber) {
        this.generalRelTranNumber = generalRelTranNumber;
    }

    public String getMajorRelTranNumber() {
        if (majorRelTranNumber == null) {
            return "0";
        }
        return majorRelTranNumber;
    }

    public void setMajorRelTranNumber(String majorRelTranNumber) {
        this.majorRelTranNumber = majorRelTranNumber;
    }

    public String getMajorRelTranAmount() {
        if (majorRelTranAmount == null) {
            return "0";
        }
        return majorRelTranAmount;
    }

    public void setMajorRelTranAmount(String majorRelTranAmount) {
        this.majorRelTranAmount = majorRelTranAmount;
    }

    public String getGeneralRelTranAmount() {
        if (generalRelTranAmount == null) {
            return "0";
        }
        return generalRelTranAmount;
    }

    public void setGeneralRelTranAmount(String generalRelTranAmount) {
        this.generalRelTranAmount = generalRelTranAmount;
    }

    public String getTranAmount() {
        if (tranAmount == null) {
            return "0";
        }
        return tranAmount;
    }

    public void setTranAmount(String tranAmount) {
        this.tranAmount = tranAmount;
    }

    public String getConsiderAmount() {
        if (considerAmount == null) {
            return "0";
        }
        return considerAmount;
    }

    public void setConsiderAmount(String considerAmount) {
        this.considerAmount = considerAmount;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getTraSubName() {
        return traSubName;
    }

    public void setTraSubName(String traSubName) {
        this.traSubName = traSubName;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getMajorTrade() {
        return majorTrade;
    }

    public void setMajorTrade(String majorTrade) {
        this.majorTrade = majorTrade;
    }

    public String getConsiderType() {
        return considerType;
    }

    public void setConsiderType(String considerType) {
        this.considerType = considerType;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ReportAnnual() {

    }

    public ReportAnnual(String generalRelTranNumber, String majorRelTranNumber,
                        String majorRelTranAmount, String generalRelTranAmount,
                        String tranAmount, String considerAmount) {
        this.generalRelTranNumber = generalRelTranNumber;
        this.majorRelTranNumber = majorRelTranNumber;
        this.majorRelTranAmount = majorRelTranAmount;
        this.generalRelTranAmount = generalRelTranAmount;
        this.tranAmount = tranAmount;
        this.considerAmount = considerAmount;
    }
}
