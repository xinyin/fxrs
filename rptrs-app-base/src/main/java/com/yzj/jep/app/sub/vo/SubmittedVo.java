package com.yzj.jep.app.sub.vo;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class SubmittedVo extends BasePO implements Serializable {
    private Integer pid;
    private String relName; //关联方名称

    private String relType;//关联方类别

    private String relComment; //关联关系说明

    private BigDecimal contractAmount;//关联交易金额（实际交易金额）

    private String majorTrade;//是否重大 0：是，1：否

    private String pricingPolicy;//定价政策

    private String isAgreement;//统一交易协议
    private String tranType;//统一交易协议
    private String relNumber;
    private Date archDate;

    public Date getArchDate() {
        return archDate;
    }

    public void setArchDate(Date archDate) {
        this.archDate = archDate;
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
        return isAgreement;
    }

    public void setIsAgreement(String isAgreement) {
        this.isAgreement = isAgreement;
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

    public void setArchDateString(String value){
        if (value != null){
            setArchDate(DateUtils.parseDate(value));
        }
    }

    public String getArchDateString(){
        if (Optional.ofNullable(archDate).isPresent()) {
            return DateUtils.formatDate(archDate);
        }
        return "";
    }
}
