package com.yzj.jep.app.pub.model;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;


public class PublogRegularVo  extends BasePO implements Serializable {
    private Integer pid;
    private String relName; //关联方名称

    private String relType;//关联方类别

    private String relComment; //关联关系说明

    private BigDecimal contractAmount;//关联交易金额（实际交易金额）

    private String majorTrade;//是否重大 0：是，1：否

    private String pricingPolicy;//定价政策

    private String IsAgreement;//统一交易协议
    private String tranType;//交易类型
    private String relNumber;
    private String relStatement;//关联说明
    private String tranName;//交易名称
    private Date tranDate ;//交易日期
    private BigDecimal sumQuarter;//本季度各类关联交易总量
    private BigDecimal sumYear;//本年度各类关联交易累计金额
    private int num;//序号
    private String busType;//业务类型
    private String managerOrg;//管理结构
    private String flag;//0不带管理机构 1带管理机构

    public String getManagerOrg() {
        return managerOrg;
    }

    public void setManagerOrg(String managerOrg) {
        this.managerOrg = managerOrg;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public BigDecimal getSumQuarter() {
        return sumQuarter;
    }

    public void setSumQuarter(BigDecimal sumQuarter) {
        this.sumQuarter = sumQuarter;
    }

    public BigDecimal getSumYear() {
        return sumYear;
    }

    public void setSumYear(BigDecimal sumYear) {
        this.sumYear = sumYear;
    }

    public String getRelStatement() {
        return relStatement;
    }

    public void setRelStatement(String relStatement) {
        this.relStatement = relStatement;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }
    public void setTranDateString(String value){
        if (value != null){
            setTranDate(DateUtils.parseDate(value));
        }
    }

    public String getTranDateString(){
        if (Optional.ofNullable(tranDate).isPresent()) {
            return DateUtils.formatDate(tranDate);
        }
        return "";
    }
    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getRelNumber() {
        return relNumber;
    }

    public void setRelNumber(String relNumber) {
        this.relNumber = relNumber;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public String getRelComment() {
        return relComment;
    }

    public void setRelComment(String relComment) {
        this.relComment = relComment;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getMajorTrade() {
        return majorTrade;
    }

    public void setMajorTrade(String majorTrade) {
        this.majorTrade = majorTrade;
    }

    public String getPricingPolicy() {
        return pricingPolicy;
    }

    public void setPricingPolicy(String pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }

    public String getIsAgreement() {
        return IsAgreement;
    }

    public void setIsAgreement(String IsAgreement) {
        this.IsAgreement = IsAgreement;
    }
}
