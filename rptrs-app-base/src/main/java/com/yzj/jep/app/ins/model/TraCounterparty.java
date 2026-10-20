/**
 * 2021/10/21
 */
package com.yzj.jep.app.ins.model;

import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ins_tra_counterparty")
public class TraCounterparty extends BasePO {
    @Id
    @Column(name = "PID", nullable = false, length = 10)
    private Integer pid;
    //关联方id
    @Column(name = "REL_ID", nullable = true, length = 10)
    private Integer relId;
    //关联方类型
    @Column(name = "REL_TYPE", nullable = true, length = 12)
    private String relType;
    //关联方名称
    @Column(name = "REL_NAME", nullable = true, length = 20)
    private String relName;
    //关联方证件号码
    @Column(name = "REL_NUMBER", nullable = true, length = 100)
    private String relNumber;
    //总资产
    @Column(name = "TOTAL_TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private BigDecimal totalTradeAmount;

    @Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private BigDecimal tradeAmount;
    //交易编号
    @Column(name = "TRANS_NUM", nullable = true, length = 32)
    private String transNum;
    //是否重大关联交易
    @Column(name ="MAJOR_TRADE",length = 10)
    private String majorTrade;
    //达到重大交易的情形
    @Column(name ="Major_Type",length = 10)
    private String majorType;
    //是否进行归零操作
    @Column(name = "IS_ACCUMULATED",length = 10)
    private String isAccumulated;
    @Column(name = "Tra_sub_Name",length = 20)
    private String traSubName;
    //状态
    @Column(name = "STATUS",length = 10)
    private String status;
    //交易日期
    @Column(name = "TRAN_DATE")
    private Date tranDate;
    @Column(name ="Contract_Amount")
    private BigDecimal  contractAmount;
    //实际交易金额
    @Column(name ="PRAC_TRADE_AMOUNT")
    private String pracTradeAmount;
    @Column(name ="REL_TRADE_AMOUNT")
    private BigDecimal relTradeAmount;
    @Column(name="TOTAL_ASSETS")
    private BigDecimal totalAssets;
    //年度未清零累计总交易金额
    @Column(name="TOTAL_NOT_CLEAN_TRADE_AMOUNT")
    private BigDecimal totalNotCleanTradeAmount;
    //关联方证件类型
    @Column(name ="CERT_TYPE")
    private String certType;
    //是否统一交易协议
    @Column(name ="IS_TRADE_AGREEMENT")
    private String isTradeAgreement;

    public String getIsTradeAgreement() {
        return isTradeAgreement;
    }

    public void setIsTradeAgreement(String isTradeAgreement) {
        this.isTradeAgreement = isTradeAgreement;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public BigDecimal getTotalNotCleanTradeAmount() {
        return totalNotCleanTradeAmount;
    }

    public void setTotalNotCleanTradeAmount(BigDecimal totalNotCleanTradeAmount) {
        this.totalNotCleanTradeAmount = totalNotCleanTradeAmount;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getPracTradeAmount() {
        return pracTradeAmount;
    }

    public void setPracTradeAmount(String pracTradeAmount) {
        this.pracTradeAmount = pracTradeAmount;
    }

    public BigDecimal getRelTradeAmount() {
        return relTradeAmount;
    }

    public void setRelTradeAmount(BigDecimal relTradeAmount) {
        this.relTradeAmount = relTradeAmount;
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

    public String getTraSubName() {
        return traSubName;
    }

    public void setTraSubName(String traSubName) {
        this.traSubName = traSubName;
    }

    public String getMajorTrade() {
        return majorTrade;
    }

    public void setMajorTrade(String majorTrade) {
        this.majorTrade = majorTrade;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getIsAccumulated() {
        return isAccumulated;
    }

    public void setIsAccumulated(String isAccumulated) {
        this.isAccumulated = isAccumulated;
    }

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

    public String getRelNumber() {
        return relNumber;
    }

    public void setRelNumber(String relNumber) {
        this.relNumber = relNumber;
    }

    public BigDecimal getTotalTradeAmount() {
        return totalTradeAmount;
    }

    public void setTotalTradeAmount(BigDecimal totalTradeAmount) {
        this.totalTradeAmount = totalTradeAmount;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    /**
     * 用来组装更新SQL
     * @return
     */
    public String [] toStringsArray(){
        return new String []{"relId","relType","relName","relNumber","totalTradeAmount","tradeAmount","majorTrade","majorType","status"};
    }
}

