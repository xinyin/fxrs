package com.yzj.jep.app.fil.model;

import java.math.BigDecimal;

/**
 * @author by Liqiang
 * @Date 2022/10/19
 * @Description 关联交易基本信息
 */
public class RelatedTranBaseInfoVo {
    /** 关联交易金额（元） */
    private BigDecimal relTrandeAmount;
    /** 关联方证件号码 */
    private String relNumber;
    /** 关联方类型 */
    private String relType;
    /** 是否可免于审议(1-是，0-否) */
    private String isconsiderSituation;
    /** 交易类型 */
    private String tranType;
    /** 是否统一交易协议（1-是，0-否；当交易类型为服务类2时或者保险业务及其它4时必传） */
    private String isAgreement;
    /** 免于审议情形(是否可免于审议为1时必填) */
    private String considerSituation;


    public BigDecimal getRelTrandeAmount() {
        return relTrandeAmount;
    }

    public void setRelTrandeAmount(BigDecimal relTrandeAmount) {
        this.relTrandeAmount = relTrandeAmount;
    }

    public String getRelNumber() {
        return relNumber;
    }

    public void setRelNumber(String relNumber) {
        this.relNumber = relNumber;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getIsconsiderSituation() {
        return isconsiderSituation;
    }

    public void setIsconsiderSituation(String isconsiderSituation) {
        this.isconsiderSituation = isconsiderSituation;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getIsAgreement() {
        return isAgreement;
    }

    public void setIsAgreement(String isAgreement) {
        this.isAgreement = isAgreement;
    }

    public String getConsiderSituation() {
        return considerSituation;
    }

    public void setConsiderSituation(String considerSituation) {
        this.considerSituation = considerSituation;
    }

    @Override
    public String toString() {
        return "RelatedTranBaseInfoVo{" +
                "relTrandeAmount=" + relTrandeAmount +
                ", relNumber='" + relNumber + '\'' +
                ", relType='" + relType + '\'' +
                ", isconsiderSituation='" + isconsiderSituation + '\'' +
                ", tranType='" + tranType + '\'' +
                ", isAgreement='" + isAgreement + '\'' +
                ", considerSituation='" + considerSituation + '\'' +
                '}';
    }
}
