/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:43
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

import java.util.Date;

@Entity
@Table(name = "bus_single_related_transaction")
public class SingleRelatedTransaction extends BasePO {

    public static final String TABLE_ALIAS = "单笔关联交易交易";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_TRANS_ID = "交易id";
    public static final String ALIAS_REPORT_VERSION = "报送记录版本";
    public static final String ALIAS_ORIGINAL_TYPE = "主体类型（1-保险，2-银行）";
    public static final String ALIAS_ORIGINAL_CLASS = "机构类型";
    public static final String ALIAS_ORIGINAL_CLASS_NAME = "机构类型名称";
    public static final String ALIAS_ORIGINAL_REG_CODE = "源主体机构登记编码";
    public static final String ALIAS_ORIGINAL_REG_NAME = "源主体机构登记名称";
    public static final String ALIAS_TRANS_TYPE = "交易类型";
    public static final String ALIAS_TRANS_OBJECT = "交易标的";
    public static final String ALIAS_TRANS_OBJECT_DESC = "交易标的情况说明";
    public static final String ALIAS_SIGN_TIME = "交易提交时间";
    public static final String ALIAS_APPROVAL_TIME = "交易批准日期";
    public static final String ALIAS_COUNTERPARTYS = "该笔交易所有关联方名称";
    public static final String ALIAS_TRANS_COMMENT = "交易评价";
    public static final String ALIAS_TRANS_AMOUNT = "交易金额";
    public static final String ALIAS_TRANS_PRICE = "交易价格";
    public static final String ALIAS_TRANS_COUNT_YEAR = "交易本年度累计金额";
    public static final String ALIAS_TRANS_PURPOSE = "交易目的";
    public static final String ALIAS_PRICING_STRATEGY = "定价策略";
    public static final String ALIAS_IMPACT_FINANCIAL_OPERATING = "对财务状况及经营成果的影响";
    public static final String ALIAS_LAST_YEAR_AMOUNT = "上一年末总资产（万元）";
    public static final String ALIAS_LAST_YEAR_NET_ASSETS = "保险：上一年末净资产（万元），银行：上季末资本净额（万元）";
    public static final String ALIAS_OTHER_MATTERS = "其他说明事项";
    public static final String ALIAS_INCOME_LOSS = "交易收益与损失";
    public static final String ALIAS_NATURE_PROPORTION = "交易方在交易中所占权益的性质与比重";
    public static final String ALIAS_OPERATE_TIME = "最近报送时间";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Id
    @Column(name = "trans_id", nullable = false, length = 50)
    private String transId;

    @Column(name = "report_version", nullable = true, length = 10)
    private Integer reportVersion;

    @Column(name = "original_type", nullable = true, length = 3)
    private String originalType;

    @Column(name = "original_class", nullable = true, length = 3)
    private String originalClass;

    @Column(name = "original_class_name", nullable = true, length = 100)
    private String originalClassName;

    @Column(name = "original_reg_code", nullable = true, length = 20)
    private String originalRegCode;

    @Column(name = "original_reg_name", nullable = true, length = 100)
    private String originalRegName;

    @Column(name = "trans_type", nullable = true, length = 3)
    private String transType;

    @Column(name = "trans_object", nullable = true, length = 200)
    private String transObject;

    @Column(name = "trans_object_desc", nullable = true, length = 1000)
    private String transObjectDesc;

    @Column(name = "sign_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date signTime;

    @Column(name = "approval_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date approvalTime;

    @Column(name = "counterpartys", nullable = true, length = 500)
    private String counterpartys;

    @Column(name = "trans_comment", nullable = true, length = 1000)
    private String transComment;

    @Column(name = "trans_amount", nullable = true, precision = 10, scale = 0)
    private Long transAmount;

    @Column(name = "trans_price", nullable = true, length = 300)
    private String transPrice;

    @Column(name = "trans_count_year", nullable = true, precision = 10, scale = 0)
    private Long transCountYear;

    @Column(name = "trans_purpose", nullable = true, length = 1000)
    private String transPurpose;

    @Column(name = "pricing_strategy", nullable = true, length = 1000)
    private String pricingStrategy;

    @Column(name = "impact_financial_operating", nullable = true, length = 1000)
    private String impactFinancialOperating;

    @Column(name = "last_year_amount", nullable = true, precision = 10, scale = 0)
    private Long lastYearAmount;

    @Column(name = "last_year_net_assets", nullable = true, precision = 10, scale = 0)
    private Long lastYearNetAssets;

    @Column(name = "other_matters", nullable = true, length = 1000)
    private String otherMatters;

    @Column(name = "income_loss", nullable = true, length = 1000)
    private String incomeLoss;

    @Column(name = "nature_proportion", nullable = true, length = 1000)
    private String natureProportion;

    @Column(name = "operate_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date operateTime;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getTransId() {
        return this.transId;
    }

    public void setTransId(String value) {
        this.transId = value;
    }


    public Integer getReportVersion() {
        return this.reportVersion;
    }

    public void setReportVersion(Integer value) {
        this.reportVersion = value;
    }


    public String getOriginalType() {
        return this.originalType;
    }

    public void setOriginalType(String value) {
        this.originalType = value;
    }


    public String getOriginalClass() {
        return this.originalClass;
    }

    public void setOriginalClass(String value) {
        this.originalClass = value;
    }


    public String getOriginalClassName() {
        return this.originalClassName;
    }

    public void setOriginalClassName(String value) {
        this.originalClassName = value;
    }


    public String getOriginalRegCode() {
        return this.originalRegCode;
    }

    public void setOriginalRegCode(String value) {
        this.originalRegCode = value;
    }


    public String getOriginalRegName() {
        return this.originalRegName;
    }

    public void setOriginalRegName(String value) {
        this.originalRegName = value;
    }


    public String getTransType() {
        return this.transType;
    }

    public void setTransType(String value) {
        this.transType = value;
    }


    public String getTransObject() {
        return this.transObject;
    }

    public void setTransObject(String value) {
        this.transObject = value;
    }


    public String getTransObjectDesc() {
        return this.transObjectDesc;
    }

    public void setTransObjectDesc(String value) {
        this.transObjectDesc = value;
    }


    public Date getSignTime() {
        return this.signTime;
    }

    public void setSignTime(Date value) {
        this.signTime = value;
    }


    public Date getApprovalTime() {
        return this.approvalTime;
    }

    public void setApprovalTime(Date value) {
        this.approvalTime = value;
    }


    public String getCounterpartys() {
        return this.counterpartys;
    }

    public void setCounterpartys(String value) {
        this.counterpartys = value;
    }


    public String getTransComment() {
        return this.transComment;
    }

    public void setTransComment(String value) {
        this.transComment = value;
    }


    public Long getTransAmount() {
        return this.transAmount;
    }

    public void setTransAmount(Long value) {
        this.transAmount = value;
    }


    public String getTransPrice() {
        return this.transPrice;
    }

    public void setTransPrice(String value) {
        this.transPrice = value;
    }


    public Long getTransCountYear() {
        return this.transCountYear;
    }

    public void setTransCountYear(Long value) {
        this.transCountYear = value;
    }


    public String getTransPurpose() {
        return this.transPurpose;
    }

    public void setTransPurpose(String value) {
        this.transPurpose = value;
    }


    public String getPricingStrategy() {
        return this.pricingStrategy;
    }

    public void setPricingStrategy(String value) {
        this.pricingStrategy = value;
    }


    public String getImpactFinancialOperating() {
        return this.impactFinancialOperating;
    }

    public void setImpactFinancialOperating(String value) {
        this.impactFinancialOperating = value;
    }


    public Long getLastYearAmount() {
        return this.lastYearAmount;
    }

    public void setLastYearAmount(Long value) {
        this.lastYearAmount = value;
    }


    public Long getLastYearNetAssets() {
        return this.lastYearNetAssets;
    }

    public void setLastYearNetAssets(Long value) {
        this.lastYearNetAssets = value;
    }


    public String getOtherMatters() {
        return this.otherMatters;
    }

    public void setOtherMatters(String value) {
        this.otherMatters = value;
    }


    public String getIncomeLoss() {
        return this.incomeLoss;
    }

    public void setIncomeLoss(String value) {
        this.incomeLoss = value;
    }


    public String getNatureProportion() {
        return this.natureProportion;
    }

    public void setNatureProportion(String value) {
        this.natureProportion = value;
    }


    public Date getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Date value) {
        this.operateTime = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("singleRelatedTransaction[\n")
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SingleRelatedTransaction == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SingleRelatedTransaction other = (SingleRelatedTransaction) obj;
        return new EqualsBuilder()
                .isEquals();
    }
}

