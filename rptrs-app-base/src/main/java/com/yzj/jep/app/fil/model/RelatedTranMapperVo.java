package com.yzj.jep.app.fil.model;

import java.math.BigDecimal;

/**
 * @author by Liqiang
 * @Date 2022/10/21
 * @Description 关联交易映射类
 */
public class RelatedTranMapperVo {
    /** 达到重大的情形 */
    private String majorType;
    /** 是否重大 */
    private String majorTrade;
    /** 单笔关联交易类型 */
    private String singleRelatedTranType;
    /** 审议类型 */
    private String considerType;
    /** 是否需逐笔报告 */
    private String eachReport;
    /** 是否需逐笔披露 */
    private String eachDisclosure;
    /** 是否需要补录关联方信息 */
    private String isCollectRelated;
    /** 是否可免于审议 */
    private String isconsiderSituation;
    /** 免于审议情形 */
    private String considerSituation;
    /** 关联方字符串 */
    private String relInfoListStr;
    /** 关联方id */
    private Integer relId;
    /** 本年度与该关联方发生的关联交易累计金额 */
    private BigDecimal totolTradeAmount;
    /** 累计重大后的金额 */
    private String majorNumber;
    /** 是否达到累计重大80% */
    private String majorTradeCritical;

    /** 是否统一交易协议 */
    private String isAgreement;


    public String getIsAgreement() {
        return isAgreement;
    }

    public void setIsAgreement(String isAgreement) {
        this.isAgreement = isAgreement;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getMajorTrade() {
        return majorTrade;
    }

    public void setMajorTrade(String majorTrade) {
        this.majorTrade = majorTrade;
    }

    public String getSingleRelatedTranType() {
        return singleRelatedTranType;
    }

    public void setSingleRelatedTranType(String singleRelatedTranType) {
        this.singleRelatedTranType = singleRelatedTranType;
    }

    public String getConsiderType() {
        return considerType;
    }

    public void setConsiderType(String considerType) {
        this.considerType = considerType;
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


    public String getIsCollectRelated() {
        return isCollectRelated;
    }

    public void setIsCollectRelated(String isCollectRelated) {
        this.isCollectRelated = isCollectRelated;
    }

    public String getIsconsiderSituation() {
        return isconsiderSituation;
    }

    public void setIsconsiderSituation(String isconsiderSituation) {
        this.isconsiderSituation = isconsiderSituation;
    }

    public String getConsiderSituation() {
        return considerSituation;
    }

    public void setConsiderSituation(String considerSituation) {
        this.considerSituation = considerSituation;
    }

    public String getRelInfoListStr() {
        return relInfoListStr;
    }

    public void setRelInfoListStr(String relInfoListStr) {
        this.relInfoListStr = relInfoListStr;
    }

    public Integer getRelId() {
        return relId;
    }

    public void setRelId(Integer relId) {
        this.relId = relId;
    }

    public BigDecimal getTotolTradeAmount() {
        return totolTradeAmount;
    }

    public void setTotolTradeAmount(BigDecimal totolTradeAmount) {
        this.totolTradeAmount = totolTradeAmount;
    }

    public String getMajorNumber() {
        return majorNumber;
    }

    public void setMajorNumber(String majorNumber) {
        this.majorNumber = majorNumber;
    }

    public String getMajorTradeCritical() {
        return majorTradeCritical;
    }

    public void setMajorTradeCritical(String majorTradeCritical) {
        this.majorTradeCritical = majorTradeCritical;
    }

    @Override
    public String toString() {
        return "RelatedTranMapperVo{" +
                "majorType='" + majorType + '\'' +
                ", majorTrade='" + majorTrade + '\'' +
                ", singleRelatedTranType='" + singleRelatedTranType + '\'' +
                ", considerType='" + considerType + '\'' +
                ", eachReport='" + eachReport + '\'' +
                ", eachDisclosure='" + eachDisclosure + '\'' +
                ", isCollectRelated='" + isCollectRelated + '\'' +
                ", isconsiderSituation='" + isconsiderSituation + '\'' +
                ", considerSituation='" + considerSituation + '\'' +
                ", relInfoListStr='" + relInfoListStr + '\'' +
                ", relId=" + relId +
                ", totolTradeAmount=" + totolTradeAmount +
                ", majorNumber='" + majorNumber + '\'' +
                ", majorTradeCritical='" + majorTradeCritical + '\'' +
                '}';
    }
}
