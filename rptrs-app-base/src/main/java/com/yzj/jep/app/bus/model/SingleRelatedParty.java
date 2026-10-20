/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:43
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
@Table(name = "bus_single_related_party")
public class SingleRelatedParty extends BasePO {

    public static final String TABLE_ALIAS = "单笔关联交易对手方";
    public static final String ALIAS_PID = "物理主键，自增";
    public static final String ALIAS_TRANS_ID = "交易id";
    public static final String ALIAS_REPORT_VERSION = "报送记录版本";
    public static final String ALIAS_COUNTERPARTY_TYPE = "关联方类别（1-机构，2-人）";
    public static final String ALIAS_COUNTERPARTY = "关联方pid（当交易对手为机构时，对应法人信息表的pid；为个人时，对应人员信息表的pid）";
    public static final String ALIAS_COUNTERPARTY_NAME = "交易对手名称";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内外";
    public static final String ALIAS_IDENTIFY_NUMBER = "证件号码";
    public static final String ALIAS_UNISC_CODE = "统一社会信用代码";
    public static final String ALIAS_TRANS_COUNT_YEAR = "交易本年度累计金额";


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

    @Column(name = "counterparty_type", nullable = true, length = 1)
    private String counterpartyType;

    @Column(name = "counterparty", nullable = true, length = 10)
    private Integer counterparty;

    @Column(name = "counterparty_name", nullable = true, length = 100)
    private String counterpartyName;

    @Column(name = "identify_type", nullable = true, length = 10)
    private String identifyType;

    @Column(name = "domestic_or_foreign", nullable = true, length = 3)
    private String domesticOrForeign;

    @Column(name = "identify_number", nullable = true, length = 50)
    private String identifyNumber;

    @Column(name = "unisc_code", nullable = true, length = 50)
    private String uniscCode;

    @Column(name = "trans_count_year", nullable = true, precision = 10, scale = 0)
    private Long transCountYear;
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


    public String getCounterpartyType() {
        return this.counterpartyType;
    }

    public void setCounterpartyType(String value) {
        this.counterpartyType = value;
    }


    public Integer getCounterparty() {
        return this.counterparty;
    }

    public void setCounterparty(Integer value) {
        this.counterparty = value;
    }


    public String getCounterpartyName() {
        return this.counterpartyName;
    }

    public void setCounterpartyName(String value) {
        this.counterpartyName = value;
    }


    public String getIdentifyType() {
        return this.identifyType;
    }

    public void setIdentifyType(String value) {
        this.identifyType = value;
    }


    public String getDomesticOrForeign() {
        return this.domesticOrForeign;
    }

    public void setDomesticOrForeign(String value) {
        this.domesticOrForeign = value;
    }


    public String getIdentifyNumber() {
        return this.identifyNumber;
    }

    public void setIdentifyNumber(String value) {
        this.identifyNumber = value;
    }


    public String getUniscCode() {
        return this.uniscCode;
    }

    public void setUniscCode(String value) {
        this.uniscCode = value;
    }


    public Long getTransCountYear() {
        return this.transCountYear;
    }

    public void setTransCountYear(Long value) {
        this.transCountYear = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("singleRelatedParty[\n")
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
        if (obj instanceof SingleRelatedParty == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SingleRelatedParty other = (SingleRelatedParty) obj;
        return new EqualsBuilder()
                .isEquals();
    }
}

