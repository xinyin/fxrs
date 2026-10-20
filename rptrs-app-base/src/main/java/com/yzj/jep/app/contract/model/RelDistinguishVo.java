package com.yzj.jep.app.contract.model;

import java.util.Objects;

public class RelDistinguishVo {
    //关联方类别（如果是总公司及为客户本身，如果是分公司即为客户的上级总公司）
    String relType;
    //关联方名称（如果是总公司及为客户本身，如果是分公司即为客户的上级总公司）
    String relName;
    //关联方证件类型（如果是总公司及为客户本身，如果是分公司即为客户的上级总公司）
    String relUscType;
    //关联方证件号码（如果是总公司及为客户本身，如果是分公司即为客户的上级总公司）
    String relUscCode;
    //客户类别（1-自然人，2-法人或机构组织）
    String customerType;
    //客户（公司）名称
    String customerName;
    //客户证件类型(字典参考：证件类型_ uscType)
    String uscType;
    //客户证件号码（统一社会信用代码）
    String uscCode;
    //是否为关联方（0:否，1:是）
    String IsRel;


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

    public String getRelUscType() {
        return relUscType;
    }

    public void setRelUscType(String relUscType) {
        this.relUscType = relUscType;
    }

    public String getRelUscCode() {
        return relUscCode;
    }

    public void setRelUscCode(String relUscCode) {
        this.relUscCode = relUscCode;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getIsRel() {
        return IsRel;
    }

    public void setIsRel(String isRel) {
        IsRel = isRel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelDistinguishVo)) return false;
        RelDistinguishVo that = (RelDistinguishVo) o;
        return Objects.equals(getRelType(), that.getRelType()) &&
                Objects.equals(getRelName(), that.getRelName()) &&
                Objects.equals(getRelUscType(), that.getRelUscType()) &&
                Objects.equals(getRelUscCode(), that.getRelUscCode()) &&
                Objects.equals(getCustomerType(), that.getCustomerType()) &&
                Objects.equals(getCustomerName(), that.getCustomerName()) &&
                Objects.equals(getUscType(), that.getUscType()) &&
                Objects.equals(getUscCode(), that.getUscCode()) &&
                Objects.equals(getIsRel(), that.getIsRel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRelType(), getRelName(), getRelUscType(), getRelUscCode(), getCustomerType(), getCustomerName(), getUscType(), getUscCode(), getIsRel());
    }

    @Override
    public String toString() {
        return "RelDistinguishVo{" +
                "relType='" + relType + '\'' +
                ", relName='" + relName + '\'' +
                ", relUscType='" + relUscType + '\'' +
                ", relUscCode='" + relUscCode + '\'' +
                ", customerType='" + customerType + '\'' +
                ", customerName='" + customerName + '\'' +
                ", uscType='" + uscType + '\'' +
                ", uscCode='" + uscCode + '\'' +
                ", IsRel='" + IsRel + '\'' +
                '}';
    }
}
