/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:56:00
 *
 */

package com.yzj.jep.app.fil.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import com.yzj.jep.commons.DateUtils;

import java.util.Date;


@Entity
@Table(name = "ins_fil_service")
public class FilService extends BasePO {

    public static final String TABLE_ALIAS = "关联交易表（保险）-提供服务类";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_FLAG_PID = "备案标识pid";
    public static final String ALIAS_FIL_NUM = "备案号";
    public static final String ALIAS_REL_ID = "关联方ID";
    public static final String ALIAS_REL_TYPE = "关联方类型";
    public static final String ALIAS_REL_NAME = "关联方名称";
    public static final String ALIAS_REL_NUMBER = "关联方证件号码";
    public static final String ALIAS_TRADE_ORG = "交易机构";
    public static final String ALIAS_TRAN_TYPE = "交易类型";
    public static final String ALIAS_BUS_TYPE = "业务类型";
    public static final String ALIAS_DATA_SOURCES = "数据来源";
    public static final String ALIAS_TRAN_DATE = "交易日期";
    public static final String ALIAS_MAJOR_TRADE = "是否重大关联交易";
    public static final String ALIAS_CALIBER = "口径";
    public static final String ALIAS_STATUS = "状态";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_TOTOL_TRADE_AMOUNT = "本年度与该关联方发生的关联交易累计金额（万元）";
    public static final String ALIAS_CONTRACT_NO = "合同编号";
    public static final String ALIAS_TRADE_AMOUNT = "交易金额（万元）";
    public static final String ALIAS_BUS_START_TIME = "业务起始日期";
    public static final String ALIAS_BUS_END_TIME = "业务终止日期";
    public static final String ALIAS_TRADE_OCCUR = "交易发生原因及合同、证明文件等";
    public static final String ALIAS_TRADE_PRICE = "交易价格";
    public static final String ALIAS_PRICING_POLICY = "定价政策";
    public static final String ALIAS_TRADE_SUBJECT = "交易标的";
    public static final String ALIAS_REMARK = "其他事项说明";
    public static final String ALIAS_IS_EFFECTIVE = "是否生效";
    public static final String ALIAS_APPROVE_TRADE_AMOUNT = "审批金额";
    public static final String ALIAS_TRANS_NUM = "交易编号";
    public static final String ALIAS_UNITF_TRADE_AGREMENT_BUSINESS = "统一交易协议业务情况";
    public static final String ALIAS_IS_DEAL_PRICE = "交易定价是否为国家规定";
    public static final String ALIAS_RULE_RESULT = "规则结果";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "PID", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "flag_pid", nullable = true, length = 64)
    private String flagPid;

    @Column(name = "fil_num", nullable = true, length = 64)
    private String filNum;

    @Column(name = "REL_ID", nullable = true, length = 10)
    private Integer relId;

    @Column(name = "REL_TYPE", nullable = true, length = 12)
    private String relType;

    @Column(name = "REL_NAME", nullable = true, length = 20)
    private String relName;

    @Column(name = "REL_NUMBER", nullable = true, length = 100)
    private String relNumber;

    @Column(name = "TRADE_ORG", nullable = true, length = 100)
    private String tradeOrg;

    @Column(name = "TRAN_TYPE", nullable = true, length = 50)
    private String tranType;

    @Column(name = "BUS_TYPE", nullable = true, length = 50)
    private String busType;

    @Column(name = "DATA_SOURCES", nullable = true, length = 10)
    private String dataSources;

    @Column(name = "TRAN_DATE", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date tranDate;

    @Column(name = "MAJOR_TRADE", nullable = true, length = 10)
    private String majorTrade;

    @Column(name = "CALIBER", nullable = true, length = 50)
    private String caliber;

    @Column(name = "STATUS", nullable = true, length = 10)
    private String status;

    @Column(name = "STATUS_COMMENT", nullable = true, length = 100)
    private String statusComment;

    @Column(name = "TOTOL_TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal totolTradeAmount;

    @Column(name = "CONTRACT_NO", nullable = true, length = 50)
    private String contractNo;

    @Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal tradeAmount;

    @Column(name = "BUS_START_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date busStartTime;

    @Column(name = "BUS_END_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date busEndTime;

    @Column(name = "TRADE_OCCUR", nullable = true, length = 100)
    private String tradeOccur;

    @Column(name = "TRADE_PRICE", nullable = true, length = 1000)
    private String tradePrice;

    @Column(name = "PRICING_POLICY", nullable = true, length = 1000)
    private String pricingPolicy;

    @Column(name = "TRADE_SUBJECT", nullable = true, length = 1000)
    private String tradeSubject;

    @Column(name = "REMARK", nullable = true, length = 1000)
    private String remark;

    @Column(name = "IS_EFFECTIVE", nullable = true, length = 1)
    private String isEffective;

    @Column(name = "APPROVE_TRADE_AMOUNT", nullable = true, precision = 10, scale = 0)
    private Long approveTradeAmount;

    @Column(name = "TRANS_NUM", nullable = true, length = 32)
    private String transNum;

    @Column(name = "UNITF_TRADE_AGREMENT_BUSINESS", nullable = true, length = 10)
    private String unitfTradeAgrementBusiness;

    @Column(name = "IS_DEAL_PRICE", nullable = true, length = 5)
    private String isDealPrice;

    @Column(name = "RULE_RESULT", nullable = true, length = 100)
    private String ruleResult;
    //columns END
    @Transient
    private String tranDateString;

    @Transient
    private String busStartTimeString;

    @Transient
    private String busEndTimeString;

    @Transient
    private String inTimeString;


    public String getTranDateString() {
        if (tranDate != null) {
            return DateUtils.formatDate(tranDate);
        }
        return "";
    }

    public void setTranDateString(String value) {
        if (value != null) {
            setTranDate(DateUtils.parseDate(value));
        }
    }

    public String getBusStartTimeString() {
        if (busStartTime != null) {
            return DateUtils.formatDate(busStartTime);
        }
        return "";
    }

    public void setBusStartTimeString(String value) {
        if (value != null) {
            setBusStartTime(DateUtils.parseDate(value));
        }
    }


    public String getBusEndTimeString() {
        if (busEndTime != null) {
            return DateUtils.formatDate(busEndTime);
        }
        return "";
    }

    public void setBusEndTimeString(String value) {
        if (value != null) {
            setBusEndTime(DateUtils.parseDate(value));
        }
    }
//	public String getInTimeString() {
//		if (inTime != null) {
//			return DateUtils.formatDate(inTime);
//		}
//		return "";
//	}
//	public void setInTimeString(String value) {
//		if (value != null) {
//			setInTime(DateUtils.parseDate(value));
//		}
//	}


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }

    public String getFlagPid() {
        return flagPid;
    }

    public void setFlagPid(String flagPid) {
        this.flagPid = flagPid;
    }

    public String getFilNum() {
        return this.filNum;
    }

    public void setFilNum(String value) {
        this.filNum = value;
    }


    public Integer getRelId() {
        return this.relId;
    }

    public void setRelId(Integer value) {
        this.relId = value;
    }


    public String getRelType() {
        return this.relType;
    }

    public void setRelType(String value) {
        this.relType = value;
    }


    public String getRelName() {
        return this.relName;
    }

    public void setRelName(String value) {
        this.relName = value;
    }


    public String getRelNumber() {
        return this.relNumber;
    }

    public void setRelNumber(String value) {
        this.relNumber = value;
    }


    public String getTradeOrg() {
        return this.tradeOrg;
    }

    public void setTradeOrg(String value) {
        this.tradeOrg = value;
    }


    public String getTranType() {
        return this.tranType;
    }

    public void setTranType(String value) {
        this.tranType = value;
    }


    public String getBusType() {
        return this.busType;
    }

    public void setBusType(String value) {
        this.busType = value;
    }


    public String getDataSources() {
        return this.dataSources;
    }

    public void setDataSources(String value) {
        this.dataSources = value;
    }


    public Date getTranDate() {
        return this.tranDate;
    }

    public void setTranDate(Date value) {
        this.tranDate = value;
    }


    public String getMajorTrade() {
        return this.majorTrade;
    }

    public void setMajorTrade(String value) {
        this.majorTrade = value;
    }


    public String getCaliber() {
        return this.caliber;
    }

    public void setCaliber(String value) {
        this.caliber = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getStatusComment() {
        return this.statusComment;
    }

    public void setStatusComment(String value) {
        this.statusComment = value;
    }


    public java.math.BigDecimal getTotolTradeAmount() {
        return this.totolTradeAmount;
    }

    public void setTotolTradeAmount(java.math.BigDecimal value) {
        this.totolTradeAmount = value;
    }


    public String getContractNo() {
        return this.contractNo;
    }

    public void setContractNo(String value) {
        this.contractNo = value;
    }


    public java.math.BigDecimal getTradeAmount() {
        return this.tradeAmount;
    }

    public void setTradeAmount(java.math.BigDecimal value) {
        this.tradeAmount = value;
    }


    public Date getBusStartTime() {
        return this.busStartTime;
    }

    public void setBusStartTime(Date value) {
        this.busStartTime = value;
    }


    public Date getBusEndTime() {
        return this.busEndTime;
    }

    public void setBusEndTime(Date value) {
        this.busEndTime = value;
    }


    public String getTradeOccur() {
        return this.tradeOccur;
    }

    public void setTradeOccur(String value) {
        this.tradeOccur = value;
    }


    public String getTradePrice() {
        return this.tradePrice;
    }

    public void setTradePrice(String value) {
        this.tradePrice = value;
    }


    public String getPricingPolicy() {
        return this.pricingPolicy;
    }

    public void setPricingPolicy(String value) {
        this.pricingPolicy = value;
    }


    public String getTradeSubject() {
        return this.tradeSubject;
    }

    public void setTradeSubject(String value) {
        this.tradeSubject = value;
    }


    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }


    public String getIsEffective() {
        return this.isEffective;
    }

    public void setIsEffective(String value) {
        this.isEffective = value;
    }


    public Long getApproveTradeAmount() {
        return this.approveTradeAmount;
    }

    public void setApproveTradeAmount(Long value) {
        this.approveTradeAmount = value;
    }


    public String getTransNum() {
        return this.transNum;
    }

    public void setTransNum(String value) {
        this.transNum = value;
    }

    public String getUnitfTradeAgrementBusiness() {
        return this.unitfTradeAgrementBusiness;
    }

    public void setUnitfTradeAgrementbusiness(String value) {
        this.unitfTradeAgrementBusiness = value;
    }

    public String getIsDealPrice() {
        return this.isDealPrice;
    }

    public void setIsDealPrice(String value) {
        this.isDealPrice = value;
    }


    public String getRuleResult() {
        return this.ruleResult;
    }

    public void setRuleResult(String value) {
        this.ruleResult = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("filService[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("filNum = ").append(filNum).append(", \n")
                .append("relId = ").append(relId).append(", \n")
                .append("relType = ").append(relType).append(", \n")
                .append("relName = ").append(relName).append(", \n")
                .append("relNumber = ").append(relNumber).append(", \n")
                .append("tradeOrg = ").append(tradeOrg).append(", \n")
                .append("tranType = ").append(tranType).append(", \n")
                .append("busType = ").append(busType).append(", \n")
                .append("dataSources = ").append(dataSources).append(", \n")
                .append("tranDate = ").append(tranDate).append(", \n")
                .append("majorTrade = ").append(majorTrade).append(", \n")
                .append("caliber = ").append(caliber).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("totolTradeAmount = ").append(totolTradeAmount).append(", \n")
                .append("contractNo = ").append(contractNo).append(", \n")
                .append("tradeAmount = ").append(tradeAmount).append(", \n")
                .append("busStartTime = ").append(busStartTime).append(", \n")
                .append("busEndTime = ").append(busEndTime).append(", \n")
                .append("tradeOccur = ").append(tradeOccur).append(", \n")
                .append("tradePrice = ").append(tradePrice).append(", \n")
                .append("pricingPolicy = ").append(pricingPolicy).append(", \n")
                .append("tradeSubject = ").append(tradeSubject).append(", \n")
                .append("remark = ").append(remark).append(", \n")
                .append("isEffective = ").append(isEffective).append(", \n")
                .append("approveTradeAmount = ").append(approveTradeAmount).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("unitfTradeAgrementBusiness = ").append(unitfTradeAgrementBusiness).append(", \n")
                .append("isDealPrice = ").append(isDealPrice).append(", \n")
                .append("ruleResult = ").append(ruleResult).append(", \n")
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FilService == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        FilService other = (FilService) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}
