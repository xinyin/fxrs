/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月27日 16:35:32
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

@Entity
@Table(name = "bus_tra_usecredit")
public class TraUsecredit extends BasePO {

    public static final String TABLE_ALIAS = "关联交易表-用信类";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_REL_ID = "关联方id";
    public static final String ALIAS_REL_TYPE = "关联方类型";
    public static final String ALIAS_RELATED_NAME = "关联方名称";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_IDENT_NUMBER = "证件号码";
    public static final String ALIAS_INTERNAL_APPLY_NO = "银行内部审批编号";
    public static final String ALIAS_USE_CREDIT_NUM = "用信编号";
    public static final String ALIAS_CONTRACT_SIGN_DATE = "合同签署日期";
    public static final String ALIAS_BUSINESS_START_TIME = "业务起始时间";
    public static final String ALIAS_BUSINESS_END_TIME = "业务结束时间";
    public static final String ALIAS_BUSINESS_TYPE = "业务类型";
    public static final String ALIAS_TRA_PRICE_TYPE = "交易价格类型";
    public static final String ALIAS_FLOAT_RATE_DISCOUNT_RATE = "浮动利率折扣率";
    public static final String ALIAS_TRA_PRICE = "交易价格";
    public static final String ALIAS_FLOAT_RATE_BENCH = "浮动利率基准";
    public static final String ALIAS_FLOAT_INTEREST_RATE_SPREAD = "浮动利率利差";
    public static final String ALIAS_RATE_TYPE = "利率类型";
    public static final String ALIAS_THIS_TRA_AMOUNT = "本笔交易金额";
    public static final String ALIAS_THIS_TRA_BALANCE = "本笔交易余额";
    public static final String ALIAS_THIS_TRA_DIFF = "本笔交易差额";
    public static final String ALIAS_PRODUCT_REG_NUM = "产品批注/注册/备案号";
    public static final String ALIAS_FINANCIAL_PRODUCT = "金融产品名称";
    public static final String ALIAS_CAPITAL_INVESTMENT = "资金投向";
    public static final String ALIAS_IS_EFFECTIVE = "是否生效";
    public static final String ALIAS_INVESTMENT_AMOUNT = "投资关联方金额";
    public static final String ALIAS_PRICING_STRATEGY = "是否生效";
    public static final String ALIAS_FUNDS_USE = "资金用途";
    public static final String ALIAS_TARGETS_INVESTMENT_DESC = "投资标的的描述";
    public static final String ALIAS_REMARK = "其他说明事项";
    public static final String ALIAS_TRANS_NUM = "交易编号";
    public static final String ALIAS_MANAGE_ORG = "管户机构";
    public static final String ALIAS_TRAN_TYPE = "交易类型";
    public static final String ALIAS_STATUS = "状态";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_DATA_SOURCES = "数据来源";
    public static final String ALIAS_PRODUCT_NAME = "产品名称";
    public static final String ALIAS_TYPE_ONE_BUS = "一级业务种类";
    public static final String ALIAS_TYPE_TWO_BUS = "二级业务种类";
    public static final String ALIAS_TYPE_THREE_BUS = "三级业务种类";
    public static final String ALIAS_TYPE_FOUR_BUS = "四级业务种类";
    public static final String ALIAS_TABLE_ATTR_BUS = "业务表内外属性";
    public static final String ALIAS_BUS_LINE = "业务条线";
    public static final String ALIAS_PRODUCT_CODE = "产品编码";

    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "rel_id", nullable = true, length = 10)
    private Integer relId;

    @Column(name = "rel_type", nullable = true, length = 11)
    private String relType;

    @Column(name = "RELATED_NAME", nullable = true, length = 20)
    private String relatedName;

    @Column(name = "IDENTIFY_TYPE", nullable = true, length = 100)
    private String identifyType;

    @Column(name = "IDENT_NUMBER", nullable = true, length = 100)
    private String identNumber;

    @Column(name = "internal_apply_no", nullable = true, length = 20)
    private String internalApplyNo;

    @Column(name = "use_credit_num", nullable = true, length = 20)
    private String useCreditNum;

    @Column(name = "contract_sign_date", nullable = true, length = 11)
    private String contractSignDate;

    @Column(name = "business_start_time", nullable = true, length = 11)
    private String businessStartTime;

    @Column(name = "business_end_time", nullable = true, length = 11)
    private String businessEndTime;

    @Column(name = "BUSINESS_TYPE", nullable = true, length = 21)
    private String businessType;

    @Column(name = "tra_price_type", nullable = true, length = 50)
    private String traPriceType;

    @Column(name = "float_rate_discount_rate", nullable = true, length = 50)
    private String floatRateDiscountRate;

    @Column(name = "tra_price", nullable = true, length = 50)
    private String traPrice;

    @Column(name = "float_rate_bench", nullable = true, length = 50)
    private String floatRateBench;

    @Column(name = "float_interest_rate_spread", nullable = true, length = 50)
    private String floatInterestRateSpread;

    @Column(name = "rate_type", nullable = true, length = 50)
    private String rateType;

    @Column(name = "this_tra_amount", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal thisTraAmount;

    @Column(name = "this_tra_balance", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal thisTraBalance;

    @Column(name = "this_tra_diff", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal thisTraDiff;

    @Column(name = "product_reg_num", nullable = true, length = 20)
    private String productRegNum;

    @Column(name = "financial_product", nullable = true, length = 50)
    private String financialProduct;

    @Column(name = "capital_investment", nullable = true, length = 20)
    private String capitalInvestment;

    @Column(name = "is_effective", nullable = true, length = 10)
    private String isEffective;

    @Column(name = "investment_amount", nullable = true, precision = 20, scale = 0)
    private java.math.BigDecimal investmentAmount;

    @Column(name = "pricing_strategy", nullable = true, length = 1024)
    private String pricingStrategy;

    @Column(name = "funds_use", nullable = true, length = 1024)
    private String fundsUse;

    @Column(name = "targets_investment_desc", nullable = true, length = 1024)
    private String targetsInvestmentDesc;

    @Column(name = "remark", nullable = true, length = 1024)
    private String remark;

    @Column(name = "TRANS_NUM", nullable = true, length = 100)
    private String transNum;

    @Column(name = "MANAGE_ORG", nullable = true, length = 100)
    private String manageOrg;

    @Column(name = "TRAN_TYPE", nullable = true, length = 50)
    private String tranType;

    @Column(name = "STATUS", nullable = true, length = 20)
    private String status;

    @Column(name = "status_comment", nullable = true, length = 32)
    private String statusComment;

    @Column(name = "data_sources", nullable = true, length = 32)
    private String dataSources;

    @Column(name = "product_name", nullable = true, length = 10)
    private String productName;

    @Column(name = "type_one_bus", nullable = true, length = 10)
    private String typeOneBus;

    @Column(name = "type_two_bus", nullable = true, length = 10)
    private String typeTwoBus;

    @Column(name = "type_three_bus", nullable = true, length = 10)
    private String typeThreeBus;

    @Column(name = "type_four_bus", nullable = true, length = 10)
    private String typeFourBus;

    @Column(name = "table_attr_bus", nullable = true, length = 10)
    private String tableAttrBus;

    @Column(name = "bus_line", nullable = true, length = 10)
    private String busLine;

    @Column(name = "product_code", nullable = true, length = 32)
    private String productCode;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
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


    public String getRelatedName() {
        return this.relatedName;
    }

    public void setRelatedName(String value) {
        this.relatedName = value;
    }


    public String getIdentifyType() {
        return this.identifyType;
    }

    public void setIdentifyType(String value) {
        this.identifyType = value;
    }


    public String getIdentNumber() {
        return this.identNumber;
    }

    public void setIdentNumber(String value) {
        this.identNumber = value;
    }


    public String getInternalApplyNo() {
        return this.internalApplyNo;
    }

    public void setInternalApplyNo(String value) {
        this.internalApplyNo = value;
    }


    public String getUseCreditNum() {
        return this.useCreditNum;
    }

    public void setUseCreditNum(String value) {
        this.useCreditNum = value;
    }


    public String getContractSignDate() {
        return this.contractSignDate;
    }

    public void setContractSignDate(String value) {
        this.contractSignDate = value;
    }


    public String getBusinessStartTime() {
        return this.businessStartTime;
    }

    public void setBusinessStartTime(String value) {
        this.businessStartTime = value;
    }


    public String getBusinessEndTime() {
        return this.businessEndTime;
    }

    public void setBusinessEndTime(String value) {
        this.businessEndTime = value;
    }


    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String value) {
        this.businessType = value;
    }


    public String getTraPriceType() {
        return this.traPriceType;
    }

    public void setTraPriceType(String value) {
        this.traPriceType = value;
    }


    public String getFloatRateDiscountRate() {
        return this.floatRateDiscountRate;
    }

    public void setFloatRateDiscountRate(String value) {
        this.floatRateDiscountRate = value;
    }


    public String getTraPrice() {
        return this.traPrice;
    }

    public void setTraPrice(String value) {
        this.traPrice = value;
    }


    public String getFloatRateBench() {
        return this.floatRateBench;
    }

    public void setFloatRateBench(String value) {
        this.floatRateBench = value;
    }


    public String getFloatInterestRateSpread() {
        return this.floatInterestRateSpread;
    }

    public void setFloatInterestRateSpread(String value) {
        this.floatInterestRateSpread = value;
    }


    public String getRateType() {
        return this.rateType;
    }

    public void setRateType(String value) {
        this.rateType = value;
    }


    public java.math.BigDecimal getThisTraAmount() {
        return this.thisTraAmount;
    }

    public void setThisTraAmount(java.math.BigDecimal value) {
        this.thisTraAmount = value;
    }


    public java.math.BigDecimal getThisTraBalance() {
        return this.thisTraBalance;
    }

    public void setThisTraBalance(java.math.BigDecimal value) {
        this.thisTraBalance = value;
    }


    public java.math.BigDecimal getThisTraDiff() {
        return this.thisTraDiff;
    }

    public void setThisTraDiff(java.math.BigDecimal value) {
        this.thisTraDiff = value;
    }


    public String getProductRegNum() {
        return this.productRegNum;
    }

    public void setProductRegNum(String value) {
        this.productRegNum = value;
    }


    public String getFinancialProduct() {
        return this.financialProduct;
    }

    public void setFinancialProduct(String value) {
        this.financialProduct = value;
    }


    public String getCapitalInvestment() {
        return this.capitalInvestment;
    }

    public void setCapitalInvestment(String value) {
        this.capitalInvestment = value;
    }


    public String getIsEffective() {
        return this.isEffective;
    }

    public void setIsEffective(String value) {
        this.isEffective = value;
    }


    public java.math.BigDecimal getInvestmentAmount() {
        return this.investmentAmount;
    }

    public void setInvestmentAmount(java.math.BigDecimal value) {
        this.investmentAmount = value;
    }


    public String getPricingStrategy() {
        return this.pricingStrategy;
    }

    public void setPricingStrategy(String value) {
        this.pricingStrategy = value;
    }


    public String getFundsUse() {
        return this.fundsUse;
    }

    public void setFundsUse(String value) {
        this.fundsUse = value;
    }


    public String getTargetsInvestmentDesc() {
        return this.targetsInvestmentDesc;
    }

    public void setTargetsInvestmentDesc(String value) {
        this.targetsInvestmentDesc = value;
    }


    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }


    public String getTransNum() {
        return this.transNum;
    }

    public void setTransNum(String value) {
        this.transNum = value;
    }


    public String getManageOrg() {
        return this.manageOrg;
    }

    public void setManageOrg(String value) {
        this.manageOrg = value;
    }


    public String getTranType() {
        return this.tranType;
    }

    public void setTranType(String value) {
        this.tranType = value;
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

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTypeOneBus() {
        return typeOneBus;
    }

    public void setTypeOneBus(String typeOneBus) {
        this.typeOneBus = typeOneBus;
    }

    public String getTypeTwoBus() {
        return typeTwoBus;
    }

    public void setTypeTwoBus(String typeTwoBus) {
        this.typeTwoBus = typeTwoBus;
    }

    public String getTypeThreeBus() {
        return typeThreeBus;
    }

    public void setTypeThreeBus(String typeThreeBus) {
        this.typeThreeBus = typeThreeBus;
    }

    public String getTypeFourBus() {
        return typeFourBus;
    }

    public void setTypeFourBus(String typeFourBus) {
        this.typeFourBus = typeFourBus;
    }

    public String getTableAttrBus() {
        return tableAttrBus;
    }

    public void setTableAttrBus(String tableAttrBus) {
        this.tableAttrBus = tableAttrBus;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return new StringBuilder("traUsecredit[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("relId = ").append(relId).append(", \n")
                .append("relType = ").append(relType).append(", \n")
                .append("relatedName = ").append(relatedName).append(", \n")
                .append("identifyType = ").append(identifyType).append(", \n")
                .append("identNumber = ").append(identNumber).append(", \n")
                .append("internalApplyNo = ").append(internalApplyNo).append(", \n")
                .append("useCreditNum = ").append(useCreditNum).append(", \n")
                .append("contractSignDate = ").append(contractSignDate).append(", \n")
                .append("businessStartTime = ").append(businessStartTime).append(", \n")
                .append("businessEndTime = ").append(businessEndTime).append(", \n")
                .append("businessType = ").append(businessType).append(", \n")
                .append("traPriceType = ").append(traPriceType).append(", \n")
                .append("floatRateDiscountRate = ").append(floatRateDiscountRate).append(", \n")
                .append("traPrice = ").append(traPrice).append(", \n")
                .append("floatRateBench = ").append(floatRateBench).append(", \n")
                .append("floatInterestRateSpread = ").append(floatInterestRateSpread).append(", \n")
                .append("rateType = ").append(rateType).append(", \n")
                .append("thisTraAmount = ").append(thisTraAmount).append(", \n")
                .append("thisTraBalance = ").append(thisTraBalance).append(", \n")
                .append("thisTraDiff = ").append(thisTraDiff).append(", \n")
                .append("productRegNum = ").append(productRegNum).append(", \n")
                .append("financialProduct = ").append(financialProduct).append(", \n")
                .append("capitalInvestment = ").append(capitalInvestment).append(", \n")
                .append("isEffective = ").append(isEffective).append(", \n")
                .append("investmentAmount = ").append(investmentAmount).append(", \n")
                .append("pricingStrategy = ").append(pricingStrategy).append(", \n")
                .append("fundsUse = ").append(fundsUse).append(", \n")
                .append("targetsInvestmentDesc = ").append(targetsInvestmentDesc).append(", \n")
                .append("remark = ").append(remark).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("manageOrg = ").append(manageOrg).append(", \n")
                .append("tranType = ").append(tranType).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("dataSources = ").append(dataSources).append(", \n")
                .append("productName = ").append(productName).append(", \n")
                .append("typeOneBus = ").append(typeOneBus).append(", \n")
                .append("typeTwoBus = ").append(typeTwoBus).append(", \n")
                .append("typeThreeBus = ").append(typeThreeBus).append(", \n")
                .append("typeFourBus = ").append(typeFourBus).append(", \n")
                .append("tableAttrBus = ").append(tableAttrBus).append(", \n")
                .append("busLine = ").append(busLine).append(", \n")
                .append("productCode = ").append(productCode).append(", \n")
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
        if (obj instanceof TraUsecredit == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraUsecredit other = (TraUsecredit) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

