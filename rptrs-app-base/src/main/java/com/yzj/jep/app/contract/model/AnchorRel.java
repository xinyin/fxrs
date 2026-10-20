package com.yzj.jep.app.contract.model;

import java.math.BigDecimal;
import java.util.Objects;

public class AnchorRel {
    //关联方类别（1-自然人，2-法人或机构组织）
    String relType;
    //关联方名称
    String relName;
    //证件类型(字典参考：证件类型_ uscType)
    String uscType;
    //证件号码（统一社会信用代码）
    String uscCode;
    //关联交易金额，保留小数点后6位
    String relTranAmt;
    //关联交易类型（01:投资入股类，02:资金运用类，03:保险业务类，04:利益转移类，05:提供服务类）
    String relTranType;
    //关联交易类别（0:一般关联交易，1:重大关联交易），当其所在锚点的anchorType（锚点类型）为M0时（属于资产），该项必填
    String relTranCategory;

    public String getRelTranCategory() {
        return relTranCategory;
    }

    public void setRelTranCategory(String relTranCategory) {
        this.relTranCategory = relTranCategory;
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

    public String getUscType() {
        return uscType;
    }

    public void setUscType(String uscType) {
        this.uscType = uscType;
    }

    public String getUscCode() {
        return uscCode;
    }

    public void setUscCode(String uscCode) {
        this.uscCode = uscCode;
    }

    public String getRelTranAmt() {
        return relTranAmt;
    }

    public void setRelTranAmt(String relTranAmt) {
        this.relTranAmt = relTranAmt;
    }

    public String getRelTranType() {
        return relTranType;
    }

    public void setRelTranType(String relTranType) {
        this.relTranType = relTranType;
    }

    @Override
    public String toString() {
        return "AnchorRel{" +
                "relType='" + relType + '\'' +
                ", relName='" + relName + '\'' +
                ", uscType='" + uscType + '\'' +
                ", uscCode='" + uscCode + '\'' +
                ", relTranAmt=" + relTranAmt +
                ", relTranType='" + relTranType + '\'' +
                ", relTranCategory='" + relTranCategory + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnchorRel anchorRel = (AnchorRel) o;
        return Objects.equals(relType, anchorRel.relType) &&
                Objects.equals(relName, anchorRel.relName) &&
                Objects.equals(uscType, anchorRel.uscType) &&
                Objects.equals(uscCode, anchorRel.uscCode) &&
                Objects.equals(relTranAmt, anchorRel.relTranAmt) &&
                Objects.equals(relTranType, anchorRel.relTranType) &&
                Objects.equals(relTranCategory, anchorRel.relTranCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relType, relName, uscType, uscCode, relTranAmt, relTranType, relTranCategory);
    }
}
