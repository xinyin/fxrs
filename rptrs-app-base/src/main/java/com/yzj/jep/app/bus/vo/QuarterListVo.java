package com.yzj.jep.app.bus.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/*
* 季度明细表
* */
public class QuarterListVo {

    //交易时间
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date tranDate;
    //交易对象
    private String relName;
    //关联关系说明
    private String relStatement;
    //交易类型
    private String tranType;
    //交易概述
    private String tranOverView;
    //交易金额(亿元)
    private BigDecimal contractAmount;
    //备注
    private String remark;
    //关联方ID
    private String relId;

    //交易主体
    private String traSubName;

    //管理机构
    private String managerOrg;

//    @Temporal(TemporalType.DATE)
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    //年度
    private String year;
    //季度
    private String quarter;
    //统计口径
    private String caliber;


    //交易时间
    private String activeTime;

    //交易时间
    private String activeTime1;

    private String  quartertTitle;
    private String totalAmount;
    private Integer no;

    public String getManagerOrg() {
        return managerOrg;
    }

    public void setManagerOrg(String managerOrg) {
        this.managerOrg = managerOrg;
    }

    public String getQuartertTitle() {
        return quartertTitle;
    }

    public void setQuartertTitle(String quartertTitle) {
        this.quartertTitle = quartertTitle;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getTranTime() {
        if (tranDate != null) {
            return DateUtils.formatDate(tranDate);
        }
        return "";
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getRelStatement() {
        return relStatement;
    }

    public void setRelStatement(String relStatement) {
        this.relStatement = relStatement;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTranOverView() {
        return tranOverView;
    }

    public void setTranOverView(String tranOverView) {
        this.tranOverView = tranOverView;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTraSubName() {
        return traSubName;
    }

    public void setTraSubName(String traSubName) {
        this.traSubName = traSubName;
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

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getActiveTime1() {
        return activeTime1;
    }

    public void setActiveTime1(String activeTime1) {
        this.activeTime1 = activeTime1;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }
}
