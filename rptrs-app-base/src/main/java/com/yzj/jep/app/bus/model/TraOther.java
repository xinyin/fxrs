/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月15日 17:14:19
 *
 */

package com.yzj.jep.app.bus.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bus_tra_other")
public class TraOther extends BasePO {

    public static final String TABLE_ALIAS = "关联交易表-其它关联交易类";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_TRANS_NUM = "交易编号             ";
    public static final String ALIAS_RELATED_NAME = "关联方名称";
    public static final String ALIAS_HOUSE_MANAGE_ORG = "管户机构";
    public static final String ALIAS_IDENTLFY_TYPE = "证件类型";
    public static final String ALIAS_IDENT_NUMBER = "证件号码";
    public static final String ALIAS_TRAN_TYPE = "交易类型";
    public static final String ALIAS_APPROVAL_TIME = "审批时间 ";
    public static final String ALIAS_VALIDITY_CONTRACT = "合同有效期";
    public static final String ALIAS_STATUS = "状态";
    public static final String ALIAS_TRAN_AMOUNT = "交易金额";
    public static final String ALIAS_TRAN_DATE = "交易日期";
    public static final String ALIAS_TRAN_INFO = "交易情况";
    public static final String ALIAS_BUSINESS_TYPE = "业务类型";
    public static final String ALIAS_IS_DEL = "是否删除";
    public static final String ALIAS_REGULATOR_YBJ = "银保监口径";
    public static final String ALIAS_REGULATOR_ZJH = "证监会口径（0|否1|是）";
    public static final String ALIAS_REGULATOR_CZB = "财政部口径（0|否1|是）";
    public static final String ALIAS_REGULATOR_OTH1 = "其他口径1（0|否1|是）";
    public static final String ALIAS_REGULATOR_OTH2 = "其他口径2（0|否1|是）";
    public static final String ALIAS_RELATED_TRAN_TYPE = "关联交易类别";
    public static final String ALIAS_TRAN_OMP_TIME = "交易完成时间";
    public static final String ALIAS_TRAN_TARGET = "交易标的";
    public static final String ALIAS_PRICING_STRATEGY = "定价政策";
    public static final String ALIAS_REL_ID = "关联方id";
    public static final String ALIAS_REL_TYPE = "关联方类型";
    public static final String ALIAS_CONTRACT_NO = "合同编号";
    public static final String ALIAS_CONTRACT_SIGN_DATE = "合同签署日期";
    public static final String ALIAS_TRAN_PRICE = "交易价格";
    public static final String ALIAS_BZ = "交易币种";
    public static final String ALIAS_REMARK = "备注";
    public static final String ALIAS_IMPORTANT_TRADE = "是否重大关联交易";
    public static final String ALIAS_REGULATOR = "统计口径";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_PRODUCT_CODE = "产品代码";
    public static final String ALIAS_RATE = "年利率/费率%";
    public static final String ALIAS_DATA_RESOURCE = "数据来源";
    public static final String ALIAS_BUSINESS_START_TIME = "业务起始时间";
    public static final String ALIAS_BUSINESS_END_TIME = "业务结束时间";
    public static final String ALIAS_IS_INSIDER = "是否内部人";
    public static final String ALIAS_SHAREHOLDING_PROPORTION = "持股占比";
    public static final String ALIAS_VOTE_PROPORTION = "表决权占比";
    public static final String ALIAS_TRANSATION_AMOUNT = "交易发生金额";
    public static final String ALIAS_IS_EFFECTIVE = "是否生效";
    public static final String ALIAS_PRODUCT_NAME = "产品名称";
    public static final String ALIAS_TYPE_ONE_BUS = "一级业务种类";
    public static final String ALIAS_TYPE_TWO_BUS = "二级业务种类";
    public static final String ALIAS_TYPE_THREE_BUS = "三级业务种类";
    public static final String ALIAS_TYPE_FOUR_BUS = "四级业务种类";
    public static final String ALIAS_TABLE_ATTR_BUS = "业务表内外属性";
    public static final String ALIAS_BUS_LINE = "业务条线";

    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "trans_num", nullable = true, length = 100)
    private String transNum;

    @Column(name = "related_name", nullable = true, length = 20)
    private String relatedName;

    @Column(name = "house_manage_org", nullable = true, length = 100)
    private String houseManageOrg;

    @Column(name = "identlfy_type", nullable = true, length = 100)
    private String identlfyType;

    @Column(name = "ident_number", nullable = true, length = 100)
    private String identNumber;

    @Column(name = "tran_type", nullable = true, length = 50)
    private String tranType;

    @Column(name = "approval_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date approvalTime;

    @Column(name = "validity_contract", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date validityContract;

    @Column(name = "status", nullable = true, length = 20)
    private String status;

    @Column(name = "tran_amount", nullable = true, precision = 10, scale = 0)
    private Long tranAmount;

    @Column(name = "tran_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date tranDate;

    @Column(name = "tran_info", nullable = true, length = 100)
    private String tranInfo;

    @Column(name = "business_type", nullable = true, length = 21)
    private String businessType;

    @Column(name = "is_del", nullable = true, length = 10)
    private Integer isDel;

    @Column(name = "regulator_ybj", nullable = true, length = 50)
    private String regulatorYbj;

    @Column(name = "regulator_zjh", nullable = true, length = 50)
    private String regulatorZjh;

    @Column(name = "regulator_czb", nullable = true, length = 50)
    private String regulatorCzb;

    @Column(name = "regulator_oth1", nullable = true, length = 50)
    private String regulatorOth1;

    @Column(name = "regulator_oth2", nullable = true, length = 50)
    private String regulatorOth2;

    @Column(name = "related_tran_type", nullable = true, length = 50)
    private String relatedTranType;

    @Column(name = "tran_omp_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date tranOmpTime;

    @Column(name = "tran_target", nullable = true, length = 255)
    private String tranTarget;

    @Column(name = "pricing_strategy", nullable = true, length = 1024)
    private String pricingStrategy;

    @Column(name = "rel_id", nullable = true, length = 10)
    private Integer relId;

    @Column(name = "rel_type", nullable = true, length = 12)
    private String relType;

    @Column(name = "contract_no", nullable = true, length = 50)
    private String contractNo;

    @Column(name = "contract_sign_date", nullable = true, length = 11)
    private String contractSignDate;

    @Column(name = "tran_price", nullable = true, length = 16)
    private String tranPrice;

    @Column(name = "bz", nullable = true, length = 16)
    private String bz;

    @Column(name = "remark", nullable = true, length = 64)
    private String remark;

    @Column(name = "important_trade", nullable = true, length = 1)
    private String importantTrade;

    @Column(name = "regulator", nullable = true, length = 20)
    private String regulator;

    @Column(name = "status_comment", nullable = true, length = 100)
    private String statusComment;

    @Column(name = "product_code", nullable = true, length = 20)
    private String productCode;

    @Column(name = "rate", nullable = true, length = 10)
    private String rate;

    @Column(name = "data_resource", nullable = true, length = 1)
    private String dataResource;

    @Column(name = "business_start_time", nullable = true, length = 11)
    private String businessStartTime;

    @Column(name = "business_end_time", nullable = true, length = 11)
    private String businessEndTime;

    @Column(name = "is_insider", nullable = true, length = 11)
    private String isInsider;

    @Column(name = "shareholding_proportion", nullable = true, length = 10)
    private String shareholdingProportion;

    @Column(name = "vote_proportion", nullable = true, length = 10)
    private String voteProportion;

    @Column(name = "transation_amount", nullable = true, precision = 50, scale = 0)
    private java.math.BigDecimal transationAmount;

    @Column(name = "is_effective", nullable = true, length = 10)
    private String isEffective;

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
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getTransNum() {
        return this.transNum;
    }

    public void setTransNum(String value) {
        this.transNum = value;
    }


    public String getRelatedName() {
        return this.relatedName;
    }

    public void setRelatedName(String value) {
        this.relatedName = value;
    }


    public String getHouseManageOrg() {
        return this.houseManageOrg;
    }

    public void setHouseManageOrg(String value) {
        this.houseManageOrg = value;
    }


    public String getIdentlfyType() {
        return this.identlfyType;
    }

    public void setIdentlfyType(String value) {
        this.identlfyType = value;
    }


    public String getIdentNumber() {
        return this.identNumber;
    }

    public void setIdentNumber(String value) {
        this.identNumber = value;
    }


    public String getTranType() {
        return this.tranType;
    }

    public void setTranType(String value) {
        this.tranType = value;
    }


    public Date getApprovalTime() {
        return this.approvalTime;
    }

    public void setApprovalTime(Date value) {
        this.approvalTime = value;
    }


    public Date getValidityContract() {
        return this.validityContract;
    }

    public void setValidityContract(Date value) {
        this.validityContract = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public Long getTranAmount() {
        return this.tranAmount;
    }

    public void setTranAmount(Long value) {
        this.tranAmount = value;
    }


    public Date getTranDate() {
        return this.tranDate;
    }

    public void setTranDate(Date value) {
        this.tranDate = value;
    }


    public String getTranInfo() {
        return this.tranInfo;
    }

    public void setTranInfo(String value) {
        this.tranInfo = value;
    }


    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String value) {
        this.businessType = value;
    }


    public Integer getIsDel() {
        return this.isDel;
    }

    public void setIsDel(Integer value) {
        this.isDel = value;
    }


    public String getRegulatorYbj() {
        return this.regulatorYbj;
    }

    public void setRegulatorYbj(String value) {
        this.regulatorYbj = value;
    }


    public String getRegulatorZjh() {
        return this.regulatorZjh;
    }

    public void setRegulatorZjh(String value) {
        this.regulatorZjh = value;
    }


    public String getRegulatorCzb() {
        return this.regulatorCzb;
    }

    public void setRegulatorCzb(String value) {
        this.regulatorCzb = value;
    }


    public String getRegulatorOth1() {
        return this.regulatorOth1;
    }

    public void setRegulatorOth1(String value) {
        this.regulatorOth1 = value;
    }


    public String getRegulatorOth2() {
        return this.regulatorOth2;
    }

    public void setRegulatorOth2(String value) {
        this.regulatorOth2 = value;
    }


    public String getRelatedTranType() {
        return this.relatedTranType;
    }

    public void setRelatedTranType(String value) {
        this.relatedTranType = value;
    }


    public Date getTranOmpTime() {
        return this.tranOmpTime;
    }

    public void setTranOmpTime(Date value) {
        this.tranOmpTime = value;
    }


    public String getTranTarget() {
        return this.tranTarget;
    }

    public void setTranTarget(String value) {
        this.tranTarget = value;
    }


    public String getPricingStrategy() {
        return this.pricingStrategy;
    }

    public void setPricingStrategy(String value) {
        this.pricingStrategy = value;
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


    public String getContractNo() {
        return this.contractNo;
    }

    public void setContractNo(String value) {
        this.contractNo = value;
    }


    public String getContractSignDate() {
        return this.contractSignDate;
    }

    public void setContractSignDate(String value) {
        this.contractSignDate = value;
    }


    public String getTranPrice() {
        return this.tranPrice;
    }

    public void setTranPrice(String value) {
        this.tranPrice = value;
    }


    public String getBz() {
        return this.bz;
    }

    public void setBz(String value) {
        this.bz = value;
    }


    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }


    public String getImportantTrade() {
        return this.importantTrade;
    }

    public void setImportantTrade(String value) {
        this.importantTrade = value;
    }


    public String getRegulator() {
        return this.regulator;
    }

    public void setRegulator(String value) {
        this.regulator = value;
    }


    public String getStatusComment() {
        return this.statusComment;
    }

    public void setStatusComment(String value) {
        this.statusComment = value;
    }


    public String getProductCode() {
        return this.productCode;
    }

    public void setProductCode(String value) {
        this.productCode = value;
    }


    public String getRate() {
        return this.rate;
    }

    public void setRate(String value) {
        this.rate = value;
    }


    public String getDataResource() {
        return this.dataResource;
    }

    public void setDataResource(String value) {
        this.dataResource = value;
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


    public String getIsInsider() {
        return this.isInsider;
    }

    public void setIsInsider(String value) {
        this.isInsider = value;
    }


    public String getShareholdingProportion() {
        return this.shareholdingProportion;
    }

    public void setShareholdingProportion(String value) {
        this.shareholdingProportion = value;
    }


    public String getVoteProportion() {
        return this.voteProportion;
    }

    public void setVoteProportion(String value) {
        this.voteProportion = value;
    }


    public java.math.BigDecimal getTransationAmount() {
        return this.transationAmount;
    }

    public void setTransationAmount(java.math.BigDecimal value) {
        this.transationAmount = value;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
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

    @Override
    public String toString() {
        return new StringBuilder("traOther[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("relatedName = ").append(relatedName).append(", \n")
                .append("houseManageOrg = ").append(houseManageOrg).append(", \n")
                .append("identlfyType = ").append(identlfyType).append(", \n")
                .append("identNumber = ").append(identNumber).append(", \n")
                .append("tranType = ").append(tranType).append(", \n")
                .append("approvalTime = ").append(approvalTime).append(", \n")
                .append("validityContract = ").append(validityContract).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("tranAmount = ").append(tranAmount).append(", \n")
                .append("tranDate = ").append(tranDate).append(", \n")
                .append("tranInfo = ").append(tranInfo).append(", \n")
                .append("businessType = ").append(businessType).append(", \n")
                .append("isDel = ").append(isDel).append(", \n")
                .append("regulatorYbj = ").append(regulatorYbj).append(", \n")
                .append("regulatorZjh = ").append(regulatorZjh).append(", \n")
                .append("regulatorCzb = ").append(regulatorCzb).append(", \n")
                .append("regulatorOth1 = ").append(regulatorOth1).append(", \n")
                .append("regulatorOth2 = ").append(regulatorOth2).append(", \n")
                .append("relatedTranType = ").append(relatedTranType).append(", \n")
                .append("tranOmpTime = ").append(tranOmpTime).append(", \n")
                .append("tranTarget = ").append(tranTarget).append(", \n")
                .append("pricingStrategy = ").append(pricingStrategy).append(", \n")
                .append("relId = ").append(relId).append(", \n")
                .append("relType = ").append(relType).append(", \n")
                .append("contractNo = ").append(contractNo).append(", \n")
                .append("contractSignDate = ").append(contractSignDate).append(", \n")
                .append("tranPrice = ").append(tranPrice).append(", \n")
                .append("bz = ").append(bz).append(", \n")
                .append("remark = ").append(remark).append(", \n")
                .append("importantTrade = ").append(importantTrade).append(", \n")
                .append("regulator = ").append(regulator).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("productCode = ").append(productCode).append(", \n")
                .append("rate = ").append(rate).append(", \n")
                .append("dataResource = ").append(dataResource).append(", \n")
                .append("businessStartTime = ").append(businessStartTime).append(", \n")
                .append("businessEndTime = ").append(businessEndTime).append(", \n")
                .append("isInsider = ").append(isInsider).append(", \n")
                .append("shareholdingProportion = ").append(shareholdingProportion).append(", \n")
                .append("voteProportion = ").append(voteProportion).append(", \n")
                .append("transationAmount = ").append(transationAmount).append(", \n")
                .append("isEffective = ").append(isEffective).append(", \n")
                .append("productName = ").append(productName).append(", \n")
                .append("typeOneBus = ").append(typeOneBus).append(", \n")
                .append("typeTwoBus = ").append(typeTwoBus).append(", \n")
                .append("typeThreeBus = ").append(typeThreeBus).append(", \n")
                .append("typeFourBus = ").append(typeFourBus).append(", \n")
                .append("tableAttrBus = ").append(tableAttrBus).append(", \n")
                .append("busLine = ").append(busLine).append(", \n")
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
        if (obj instanceof TraOther == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraOther other = (TraOther) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

