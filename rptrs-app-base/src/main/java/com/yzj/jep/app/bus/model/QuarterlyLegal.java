/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:41
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
@Table(name = "bus_quarterly_legal")
public class QuarterlyLegal extends BasePO {

    public static final String TABLE_ALIAS = "季报保险公司与单一法人主体资金运用关联关系统计表";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_REPORT_ID = "关联交易季报主键";
    public static final String ALIAS_CORP_ID = "法人主键";
    public static final String ALIAS_CORP_NAME = "法人主体名称";
    public static final String ALIAS_CORP_UNISC_CODE = "法人统一社会信用代码";
    public static final String ALIAS_CORP_REGISTE_ADDRESS = "法人境内外";
    public static final String ALIAS_CORP_INVEST_FUND = "法人投资金额";
    public static final String ALIAS_CORP_TOTAL_FUND = "法人主体总资产";
    public static final String ALIAS_CORP_INVEST_YTOTAL_PCT = "法人投资金额占保险公司上年末总资产百分比";
    public static final String ALIAS_CORP_ASSETS_YTOTAL_PCT = "法人主体总资产占保险公司上年末总资产百分比";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "report_id", nullable = true, length = 10)
    private Integer reportId;

    @Column(name = "corp_id", nullable = true, length = 10)
    private Integer corpId;

    @Column(name = "corp_name", nullable = true, length = 100)
    private String corpName;

    @Column(name = "corp_unisc_code", nullable = true, length = 20)
    private String corpUniscCode;

    @Column(name = "corp_registe_address", nullable = true, length = 20)
    private String corpRegisteAddress;

    @Column(name = "corp_invest_fund", nullable = true, precision = 10, scale = 0)
    private Long corpInvestFund;

    @Column(name = "corp_total_fund", nullable = true, precision = 10, scale = 0)
    private Long corpTotalFund;

    @Column(name = "corp_invest_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long corpInvestYtotalPct;

    @Column(name = "corp_assets_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long corpAssetsYtotalPct;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getReportId() {
        return this.reportId;
    }

    public void setReportId(Integer value) {
        this.reportId = value;
    }


    public Integer getCorpId() {
        return this.corpId;
    }

    public void setCorpId(Integer value) {
        this.corpId = value;
    }


    public String getCorpName() {
        return this.corpName;
    }

    public void setCorpName(String value) {
        this.corpName = value;
    }


    public String getCorpUniscCode() {
        return this.corpUniscCode;
    }

    public void setCorpUniscCode(String value) {
        this.corpUniscCode = value;
    }


    public String getCorpRegisteAddress() {
        return this.corpRegisteAddress;
    }

    public void setCorpRegisteAddress(String value) {
        this.corpRegisteAddress = value;
    }


    public Long getCorpInvestFund() {
        return this.corpInvestFund;
    }

    public void setCorpInvestFund(Long value) {
        this.corpInvestFund = value;
    }


    public Long getCorpTotalFund() {
        return this.corpTotalFund;
    }

    public void setCorpTotalFund(Long value) {
        this.corpTotalFund = value;
    }


    public Long getCorpInvestYtotalPct() {
        return this.corpInvestYtotalPct;
    }

    public void setCorpInvestYtotalPct(Long value) {
        this.corpInvestYtotalPct = value;
    }


    public Long getCorpAssetsYtotalPct() {
        return this.corpAssetsYtotalPct;
    }

    public void setCorpAssetsYtotalPct(Long value) {
        this.corpAssetsYtotalPct = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("quarterlyLegal[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("reportId = ").append(reportId).append(", \n")
                .append("corpId = ").append(corpId).append(", \n")
                .append("corpName = ").append(corpName).append(", \n")
                .append("corpUniscCode = ").append(corpUniscCode).append(", \n")
                .append("corpRegisteAddress = ").append(corpRegisteAddress).append(", \n")
                .append("corpInvestFund = ").append(corpInvestFund).append(", \n")
                .append("corpTotalFund = ").append(corpTotalFund).append(", \n")
                .append("corpInvestYtotalPct = ").append(corpInvestYtotalPct).append(", \n")
                .append("corpAssetsYtotalPct = ").append(corpAssetsYtotalPct).append(", \n")
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
        if (obj instanceof QuarterlyLegal == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        QuarterlyLegal other = (QuarterlyLegal) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

