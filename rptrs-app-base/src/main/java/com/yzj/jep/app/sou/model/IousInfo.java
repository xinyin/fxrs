/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月07日 20:50:24
 *
 */

package com.yzj.jep.app.sou.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sou_ious_info")
public class IousInfo extends BasePO {

    public static final String TABLE_ALIAS = "IousInfo";
    public static final String ALIAS_IDNO = "idno";
    public static final String ALIAS_LOAN_NUM = "借据号";
    public static final String ALIAS_CUS_NAME = "客户姓名";
    public static final String ALIAS_CUS_ID_TYPE = "借款人证件类型";
    public static final String ALIAS_CUS_ID_CARD = "证件号码";
    public static final String ALIAS_BUS_TYPE = "业务种类";
    public static final String ALIAS_PRO_CODE = "产品编号";
    public static final String ALIAS_APPROVE_AMOUNT = "审批金额";
    public static final String ALIAS_LOANS_AMOUNT = "贷款金额";
    public static final String ALIAS_GRANT_DATE = "发放日期";
    public static final String ALIAS_EXPIRE_DATE = "到期日期";
    public static final String ALIAS_APPLY_DATE = "申请日期";
    public static final String ALIAS_RECEIPT_PERIODS = "借据总期数";
    public static final String ALIAS_CONTRACT_CODE = "合同编码";
    public static final String ALIAS_DUE_DATE = "固定还款日";
    public static final String ALIAS_LOAN_SERIAL_NO = "放款流水号";
    public static final String ALIAS_LOANS_BUS_NO = "贷款业务号";
    public static final String ALIAS_PROCESS_STATUS = "处理状态";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "idno", nullable = false, length = 10)
    private Integer idno;

    @Column(name = "loan_num", nullable = true, length = 255)
    private String loanNum;

    @Column(name = "cus_name", nullable = true, length = 255)
    private String cusName;

    @Column(name = "cus_id_type", nullable = true, length = 255)
    private String cusIdType;

    @Column(name = "cus_id_card", nullable = true, length = 255)
    private String cusIdCard;

    @Column(name = "bus_type", nullable = true, length = 255)
    private String busType;

    @Column(name = "pro_code", nullable = true, length = 255)
    private String proCode;

    @Column(name = "approve_amount", nullable = true, precision = 10, scale = 0)
    private Long approveAmount;

    @Column(name = "loans_amount", nullable = true, precision = 10, scale = 0)
    private Long loansAmount;

    @Column(name = "grant_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date grantDate;

    @Column(name = "expire_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @Column(name = "apply_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date applyDate;

    @Column(name = "receipt_periods", nullable = true, length = 255)
    private String receiptPeriods;

    @Column(name = "contract_code", nullable = true, length = 255)
    private String contractCode;

    @Column(name = "due_date", nullable = true, length = 10)
    private Integer dueDate;

    @Column(name = "loan_serial_no", nullable = true, length = 255)
    private String loanSerialNo;

    @Column(name = "loans_bus_no", nullable = true, length = 10)
    private Integer loansBusNo;

    @Column(name = "process_status", nullable = true, length = 255)
    private String processStatus;
    //columns END


    public Integer getIdno() {
        return this.idno;
    }

    public void setIdno(Integer value) {
        this.idno = value;
    }


    public String getLoanNum() {
        return this.loanNum;
    }

    public void setLoanNum(String value) {
        this.loanNum = value;
    }


    public String getCusName() {
        return this.cusName;
    }

    public void setCusName(String value) {
        this.cusName = value;
    }


    public String getCusIdType() {
        return this.cusIdType;
    }

    public void setCusIdType(String value) {
        this.cusIdType = value;
    }


    public String getCusIdCard() {
        return this.cusIdCard;
    }

    public void setCusIdCard(String value) {
        this.cusIdCard = value;
    }


    public String getBusType() {
        return this.busType;
    }

    public void setBusType(String value) {
        this.busType = value;
    }


    public String getProCode() {
        return this.proCode;
    }

    public void setProCode(String value) {
        this.proCode = value;
    }


    public Long getApproveAmount() {
        return this.approveAmount;
    }

    public void setApproveAmount(Long value) {
        this.approveAmount = value;
    }


    public Long getLoansAmount() {
        return this.loansAmount;
    }

    public void setLoansAmount(Long value) {
        this.loansAmount = value;
    }


    public Date getGrantDate() {
        return this.grantDate;
    }

    public void setGrantDate(Date value) {
        this.grantDate = value;
    }


    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(Date value) {
        this.expireDate = value;
    }


    public Date getApplyDate() {
        return this.applyDate;
    }

    public void setApplyDate(Date value) {
        this.applyDate = value;
    }


    public String getReceiptPeriods() {
        return this.receiptPeriods;
    }

    public void setReceiptPeriods(String value) {
        this.receiptPeriods = value;
    }


    public String getContractCode() {
        return this.contractCode;
    }

    public void setContractCode(String value) {
        this.contractCode = value;
    }


    public Integer getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Integer value) {
        this.dueDate = value;
    }


    public String getLoanSerialNo() {
        return this.loanSerialNo;
    }

    public void setLoanSerialNo(String value) {
        this.loanSerialNo = value;
    }


    public Integer getLoansBusNo() {
        return this.loansBusNo;
    }

    public void setLoansBusNo(Integer value) {
        this.loansBusNo = value;
    }


    public String getProcessStatus() {
        return this.processStatus;
    }

    public void setProcessStatus(String value) {
        this.processStatus = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("iousInfo[\n")
                .append("idno = ").append(idno).append(", \n")
                .append("loanNum = ").append(loanNum).append(", \n")
                .append("cusName = ").append(cusName).append(", \n")
                .append("cusIdType = ").append(cusIdType).append(", \n")
                .append("cusIdCard = ").append(cusIdCard).append(", \n")
                .append("busType = ").append(busType).append(", \n")
                .append("proCode = ").append(proCode).append(", \n")
                .append("approveAmount = ").append(approveAmount).append(", \n")
                .append("loansAmount = ").append(loansAmount).append(", \n")
                .append("grantDate = ").append(grantDate).append(", \n")
                .append("expireDate = ").append(expireDate).append(", \n")
                .append("applyDate = ").append(applyDate).append(", \n")
                .append("receiptPeriods = ").append(receiptPeriods).append(", \n")
                .append("contractCode = ").append(contractCode).append(", \n")
                .append("dueDate = ").append(dueDate).append(", \n")
                .append("loanSerialNo = ").append(loanSerialNo).append(", \n")
                .append("loansBusNo = ").append(loansBusNo).append(", \n")
                .append("processStatus = ").append(processStatus).append(", \n")
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getIdno())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IousInfo == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        IousInfo other = (IousInfo) obj;
        return new EqualsBuilder()
                .append(getIdno(), other.getIdno())
                .isEquals();
    }
}

