package com.yzj.jep.app.pub.model;

import com.yzj.jep.orm.entity.BasePO;
import com.yzj.jep.orm.entity.PO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


public class InsTraView  extends BasePO {


    private Integer pid;

    private Integer relId;

    private String transNum;

    private String traSubName;

    private String relType;

    private String relName;

    private String busType;

    private String majorTrade;

    private BigDecimal contractAmount;

    private Date tranDate;

    private String tranDateString;

    private String status;

    private String type;

    private Integer quarter;

    private Integer year;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getRelId() {
        return relId;
    }

    public void setRelId(Integer relId) {
        this.relId = relId;
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

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getMajorTrade() {
        return majorTrade;
    }

    public void setMajorTrade(String majorTrade) {
        this.majorTrade = majorTrade;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getTranDateString() {
        return tranDateString;
    }

    public void setTranDateString(String tranDateString) {
        this.tranDateString = tranDateString;
    }

    @Override
    public String toString() {
        return "InsTraView{" +
                "pid=" + pid +
                ", relId=" + relId +
                ", transNum='" + transNum + '\'' +
                ", traSubName='" + traSubName + '\'' +
                ", relType='" + relType + '\'' +
                ", relName='" + relName + '\'' +
                ", busType='" + busType + '\'' +
                ", majorTrade='" + majorTrade + '\'' +
                ", contractAmount=" + contractAmount +
                ", tranDate=" + tranDate +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", quarter=" + quarter +
                ", year=" + year +
                '}';
    }


    public InsTraView() {
    }

    public InsTraView(Integer pid, Integer relId, String transNum, String traSubName, String relType, String relName, String busType, String majorTrade, BigDecimal contractAmount, Date tranDate, String status, String type, Integer quarter, Integer year) {
        this.pid = pid;
        this.relId = relId;
        this.transNum = transNum;
        this.traSubName = traSubName;
        this.relType = relType;
        this.relName = relName;
        this.busType = busType;
        this.majorTrade = majorTrade;
        this.contractAmount = contractAmount;
        this.tranDate = tranDate;
        this.status = status;
        this.type = type;
        this.quarter = quarter;
        this.year = year;
    }


}