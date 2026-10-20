/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:42
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

import java.util.Date;

@Entity
@Table(name = "bus_related_transactions_quarterly")
public class RelatedTransactionsQuarterly extends BasePO {

    public static final String TABLE_ALIAS = "关联交易季报";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_REPORT_ID = "季报主键（与季报表关联）";
    public static final String ALIAS_REPORT_ORG_CLS = "上报机构分类（1-银行，2-保险）";
    public static final String ALIAS_REPORT_ORG_TYPE = "上报机构类型";
    public static final String ALIAS_REPORT_ORG_CODE = "上报机构编码";
    public static final String ALIAS_REPORT_ORG_NAME = "报送机构名称";
    public static final String ALIAS_REPORT_YEAR = "季报年份";
    public static final String ALIAS_REPORT_QUARTER = "季报季度";
    public static final String ALIAS_REPORT_TIME = "季报上报时间";
    public static final String ALIAS_PREV_YEAR_TOTAL_ASSETS = "上年末总资产（万元）";
    public static final String ALIAS_PREV_YEAR_NET_ASSETS = "上年末净资产（万元）";
    public static final String ALIAS_PREV_QUARTER_TOTAL_ASSETS = "上季末总资产（万元）";
    public static final String ALIAS_PREV_QUARTER_NET_ASSETS = "上季末净资产（万元）";
    public static final String ALIAS_FIXED_PROFIT_BALANCE = "固定收益投资余额（万元）";
    public static final String ALIAS_LISTED_INVEST_BALANCE = "上市权益类资产投资余额（万元）";
    public static final String ALIAS_UNLISTED_INVEST_BALANCE = "未上市权益类资产投资余额（万元）";
    public static final String ALIAS_IMVBS_INVEST_BALANCE = "不动产类资产投资余额（万元）";
    public static final String ALIAS_COM_BANK_FIN_FUND = "商业银行理财产品（万元）";
    public static final String ALIAS_BANK_CREDIT_FUND = "银行业金融机构信贷资产支持证券（万元）";
    public static final String ALIAS_TRUST_FUND = "信托公司集合资金信托计划（万元）";
    public static final String ALIAS_BOND_SPECIAL_FUND = "证券公司专项资产管理计划（万元）";
    public static final String ALIAS_OTHER_FUND = "其它资金（万元）";
    public static final String ALIAS_OTHER_TOTAL_FUND = "其它金融资产投资余额合计（万元）";
    public static final String ALIAS_ALL_TOTAL_FUND = "全部投资余额（万元）";
    public static final String ALIAS_OTHER_NOTES = "其它说明事项";
    public static final String ALIAS_FIXED_PROFIT_TOTAL_PCT = "固定收益投资余额与上季末总资产百分比";
    public static final String ALIAS_FIXED_PROFIT_NET_PCT = "固定收益投资余额与上季末净资产百分比";
    public static final String ALIAS_LISTED_INVEST_TOTAL_PCT = "上市权益类资产投资余额与上季末总资产百分比";
    public static final String ALIAS_LISTED_INVEST_NET_PCT = "上市权益类资产投资余额与上季末净资产百分比";
    public static final String ALIAS_UNLISTED_INVEST_TOTAL_PCT = "未上市权益类资产投资余额与上季末总资产百分比";
    public static final String ALIAS_UNLISTED_INVEST_NET_PCT = "未上市权益类资产投资余额与上季末净资产百分比";
    public static final String ALIAS_IMVBS_INVEST_TOTAL_PCT = "不动产类资产投资余额与上季末总资产百分比";
    public static final String ALIAS_IMVBS_INVEST_NET_PCT = "不动产类资产投资余额与上季末净资产百分比";
    public static final String ALIAS_COM_BANK_FIN_TOTAL_PCT = "商业银行理财产品与上季末总资产百分比";
    public static final String ALIAS_COM_BANK_FIN_NET_PCT = "商业银行理财产品与上季末净资产百分比";
    public static final String ALIAS_BANK_CREDIT_TOTAL_PCT = "银行业金融机构信贷资产支持证券与上季末总资产百分比";
    public static final String ALIAS_BANK_CREDIT_NET_PCT = "银行业金融机构信贷资产支持证券与上季末净资产百分比";
    public static final String ALIAS_TRUST_TOTAL_PCT = "信托公司集合资金信托计划与上季末总资产百分比";
    public static final String ALIAS_TRUST_NET_PCT = "信托公司集合资金信托计划与上季末净资产百分比";
    public static final String ALIAS_BOND_SPECIAL_TOTAL_PCT = "证券公司专项资产管理计划与上季末总资产百分比";
    public static final String ALIAS_BOND_SPECIAL_NET_PCT = "证券公司专项资产管理计划与上季末净资产百分比";
    public static final String ALIAS_OTHER_TOTAL_PCT = "其它资金与上季末总资产百分比";
    public static final String ALIAS_OTHER_NET_PCT = "其它资金与上季末净资产百分比";
    public static final String ALIAS_OTHER_COUNT_TOTAL_PCT = "其它金融资产投资余额合计与上季末总资产百分比";
    public static final String ALIAS_OTHER_COUNT_NET_PCT = "其它金融资产投资余额合计与上季末净资产百分比";
    public static final String ALIAS_ALL_COUNT_TOTAL_PCT = "全部投资余额与上季末总资产百分比";
    public static final String ALIAS_ALL_COUNT_NET_PCT = "全部投资余额与上季末净资产百分比";
    public static final String ALIAS_FIXED_PROFIT_YTOTAL_PCT = "固定收益投资余额与上年末总资产百分比";
    public static final String ALIAS_FIXED_PROFIT_YNET_PCT = "固定收益投资余额与上年末净资产百分比";
    public static final String ALIAS_LISTED_INVEST_YTOTAL_PCT = "上市权益类资产投资余额与上年末总资产百分比";
    public static final String ALIAS_LISTED_INVEST_YNET_PCT = "上市权益类资产投资余额与上年末净资产百分比";
    public static final String ALIAS_UNLISTED_INVEST_YTOTAL_PCT = "未上市权益类资产投资余额与上年末总资产百分比";
    public static final String ALIAS_UNLISTED_INVEST_YNET_PCT = "未上市权益类资产投资余额与上年末净资产百分比";
    public static final String ALIAS_IMVBS_INVEST_YTOTAL_PCT = "不动产类资产投资余额与上年末总资产百分比";
    public static final String ALIAS_IMVBS_INVEST_YNET_PCT = "不动产类资产投资余额与上年末净资产百分比";
    public static final String ALIAS_COM_BANK_FIN_YTOTAL_PCT = "商业银行理财产品与上年末总资产百分比";
    public static final String ALIAS_COM_BANK_FIN_YNET_PCT = "商业银行理财产品与上年末净资产百分比";
    public static final String ALIAS_BANK_CREDIT_YTOTAL_PCT = "银行业金融机构信贷资产支持证券与上年末总资产百分比";
    public static final String ALIAS_BANK_CREDIT_YNET_PCT = "银行业金融机构信贷资产支持证券与上年末净资产百分比";
    public static final String ALIAS_TRUST_YTOTAL_PCT = "信托公司集合资金信托计划与上年末总资产百分比";
    public static final String ALIAS_TRUST_YNET_PCT = "信托公司集合资金信托计划与上年末净资产百分比";
    public static final String ALIAS_BOND_SPECIAL_YTOTAL_PCT = "证券公司专项资产管理计划与上年末总资产百分比";
    public static final String ALIAS_BOND_SPECIAL_YNET_PCT = "证券公司专项资产管理计划与上年末净资产百分比";
    public static final String ALIAS_OTHER_YTOTAL_PCT = "其它资金与上年末总资产百分比";
    public static final String ALIAS_OTHER_YNET_PCT = "其它资金与上年末净资产百分比";
    public static final String ALIAS_OTHER_COUNT_YTOTAL_PCT = "其它金融资产投资余额合计与上年末总资产百分比";
    public static final String ALIAS_OTHER_COUNT_YNET_PCT = "其它金融资产投资余额合计与上年末净资产百分比";
    public static final String ALIAS_ALL_COUNT_YTOTAL_PCT = "全部投资余额与上年末总资产百分比";
    public static final String ALIAS_ALL_COUNT_YNET_PCT = "全部投资余额与上年末净资产百分比";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Id
    @Column(name = "report_id", nullable = false, length = 10)
    private Integer reportId;

    @Column(name = "report_org_cls", nullable = true, length = 3)
    private String reportOrgCls;

    @Column(name = "report_org_type", nullable = true, length = 20)
    private String reportOrgType;

    @Column(name = "report_org_code", nullable = true, length = 20)
    private String reportOrgCode;

    @Column(name = "report_org_name", nullable = true, length = 100)
    private String reportOrgName;

    @Column(name = "report_year", nullable = true, length = 10)
    private Integer reportYear;

    @Column(name = "report_quarter", nullable = true, length = 10)
    private Integer reportQuarter;

    @Column(name = "report_time", nullable = false, length = 19)
    @Temporal(TemporalType.DATE)
    private Date reportTime;

    @Column(name = "prev_year_total_assets", nullable = true, precision = 10, scale = 0)
    private Long prevYearTotalAssets;

    @Column(name = "prev_year_net_assets", nullable = true, precision = 10, scale = 0)
    private Long prevYearNetAssets;

    @Column(name = "prev_quarter_total_assets", nullable = true, precision = 10, scale = 0)
    private Long prevQuarterTotalAssets;

    @Column(name = "prev_quarter_net_assets", nullable = true, precision = 10, scale = 0)
    private Long prevQuarterNetAssets;

    @Column(name = "fixed_profit_balance", nullable = true, precision = 10, scale = 0)
    private Long fixedProfitBalance;

    @Column(name = "listed_invest_balance", nullable = true, precision = 10, scale = 0)
    private Long listedInvestBalance;

    @Column(name = "unlisted_invest_balance", nullable = true, precision = 10, scale = 0)
    private Long unlistedInvestBalance;

    @Column(name = "imvbs_invest_balance", nullable = true, precision = 10, scale = 0)
    private Long imvbsInvestBalance;

    @Column(name = "com_bank_fin_fund", nullable = true, precision = 10, scale = 0)
    private Long comBankFinFund;

    @Column(name = "bank_credit_fund", nullable = true, precision = 10, scale = 0)
    private Long bankCreditFund;

    @Column(name = "trust_fund", nullable = true, precision = 10, scale = 0)
    private Long trustFund;

    @Column(name = "bond_special_fund", nullable = true, precision = 10, scale = 0)
    private Long bondSpecialFund;

    @Column(name = "other_fund", nullable = true, precision = 10, scale = 0)
    private Long otherFund;

    @Column(name = "other_total_fund", nullable = true, precision = 10, scale = 0)
    private Long otherTotalFund;

    @Column(name = "all_total_fund", nullable = true, precision = 10, scale = 0)
    private Long allTotalFund;

    @Column(name = "other_notes", nullable = true, length = 100)
    private String otherNotes;

    @Column(name = "fixed_profit_total_pct", nullable = true, precision = 10, scale = 0)
    private Long fixedProfitTotalPct;

    @Column(name = "fixed_profit_net_pct", nullable = true, precision = 10, scale = 0)
    private Long fixedProfitNetPct;

    @Column(name = "listed_invest_total_pct", nullable = true, precision = 10, scale = 0)
    private Long listedInvestTotalPct;

    @Column(name = "listed_invest_net_pct", nullable = true, precision = 10, scale = 0)
    private Long listedInvestNetPct;

    @Column(name = "unlisted_invest_total_pct", nullable = true, precision = 10, scale = 0)
    private Long unlistedInvestTotalPct;

    @Column(name = "unlisted_invest_net_pct", nullable = true, precision = 10, scale = 0)
    private Long unlistedInvestNetPct;

    @Column(name = "imvbs_invest_total_pct", nullable = true, precision = 10, scale = 0)
    private Long imvbsInvestTotalPct;

    @Column(name = "imvbs_invest_net_pct", nullable = true, precision = 10, scale = 0)
    private Long imvbsInvestNetPct;

    @Column(name = "com_bank_fin_total_pct", nullable = true, precision = 10, scale = 0)
    private Long comBankFinTotalPct;

    @Column(name = "com_bank_fin_net_pct", nullable = true, precision = 10, scale = 0)
    private Long comBankFinNetPct;

    @Column(name = "bank_credit_total_pct", nullable = true, precision = 10, scale = 0)
    private Long bankCreditTotalPct;

    @Column(name = "bank_credit_net_pct", nullable = true, precision = 10, scale = 0)
    private Long bankCreditNetPct;

    @Column(name = "trust_total_pct", nullable = true, precision = 10, scale = 0)
    private Long trustTotalPct;

    @Column(name = "trust_net_pct", nullable = true, precision = 10, scale = 0)
    private Long trustNetPct;

    @Column(name = "bond_special_total_pct", nullable = true, precision = 10, scale = 0)
    private Long bondSpecialTotalPct;

    @Column(name = "bond_special_net_pct", nullable = true, precision = 10, scale = 0)
    private Long bondSpecialNetPct;

    @Column(name = "other_total_pct", nullable = true, precision = 10, scale = 0)
    private Long otherTotalPct;

    @Column(name = "other_net_pct", nullable = true, precision = 10, scale = 0)
    private Long otherNetPct;

    @Column(name = "other_count_total_pct", nullable = true, precision = 10, scale = 0)
    private Long otherCountTotalPct;

    @Column(name = "other_count_net_pct", nullable = true, precision = 10, scale = 0)
    private Long otherCountNetPct;

    @Column(name = "all_count_total_pct", nullable = true, precision = 10, scale = 0)
    private Long allCountTotalPct;

    @Column(name = "all_count_net_pct", nullable = true, precision = 10, scale = 0)
    private Long allCountNetPct;

    @Column(name = "fixed_profit_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long fixedProfitYtotalPct;

    @Column(name = "fixed_profit_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long fixedProfitYnetPct;

    @Column(name = "listed_invest_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long listedInvestYtotalPct;

    @Column(name = "listed_invest_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long listedInvestYnetPct;

    @Column(name = "unlisted_invest_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long unlistedInvestYtotalPct;

    @Column(name = "unlisted_invest_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long unlistedInvestYnetPct;

    @Column(name = "imvbs_invest_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long imvbsInvestYtotalPct;

    @Column(name = "imvbs_invest_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long imvbsInvestYnetPct;

    @Column(name = "com_bank_fin_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long comBankFinYtotalPct;

    @Column(name = "com_bank_fin_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long comBankFinYnetPct;

    @Column(name = "bank_credit_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long bankCreditYtotalPct;

    @Column(name = "bank_credit_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long bankCreditYnetPct;

    @Column(name = "trust_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long trustYtotalPct;

    @Column(name = "trust_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long trustYnetPct;

    @Column(name = "bond_special_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long bondSpecialYtotalPct;

    @Column(name = "bond_special_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long bondSpecialYnetPct;

    @Column(name = "other_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long otherYtotalPct;

    @Column(name = "other_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long otherYnetPct;

    @Column(name = "other_count_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long otherCountYtotalPct;

    @Column(name = "other_count_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long otherCountYnetPct;

    @Column(name = "all_count_y_total_pct", nullable = true, precision = 10, scale = 0)
    private Long allCountYtotalPct;

    @Column(name = "all_count_y_net_pct", nullable = true, precision = 10, scale = 0)
    private Long allCountYnetPct;
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


    public String getReportOrgCls() {
        return this.reportOrgCls;
    }

    public void setReportOrgCls(String value) {
        this.reportOrgCls = value;
    }


    public String getReportOrgType() {
        return this.reportOrgType;
    }

    public void setReportOrgType(String value) {
        this.reportOrgType = value;
    }


    public String getReportOrgCode() {
        return this.reportOrgCode;
    }

    public void setReportOrgCode(String value) {
        this.reportOrgCode = value;
    }


    public String getReportOrgName() {
        return this.reportOrgName;
    }

    public void setReportOrgName(String value) {
        this.reportOrgName = value;
    }


    public Integer getReportYear() {
        return this.reportYear;
    }

    public void setReportYear(Integer value) {
        this.reportYear = value;
    }


    public Integer getReportQuarter() {
        return this.reportQuarter;
    }

    public void setReportQuarter(Integer value) {
        this.reportQuarter = value;
    }


    public Date getReportTime() {
        return this.reportTime;
    }

    public void setReportTime(Date value) {
        this.reportTime = value;
    }


    public Long getPrevYearTotalAssets() {
        return this.prevYearTotalAssets;
    }

    public void setPrevYearTotalAssets(Long value) {
        this.prevYearTotalAssets = value;
    }


    public Long getPrevYearNetAssets() {
        return this.prevYearNetAssets;
    }

    public void setPrevYearNetAssets(Long value) {
        this.prevYearNetAssets = value;
    }


    public Long getPrevQuarterTotalAssets() {
        return this.prevQuarterTotalAssets;
    }

    public void setPrevQuarterTotalAssets(Long value) {
        this.prevQuarterTotalAssets = value;
    }


    public Long getPrevQuarterNetAssets() {
        return this.prevQuarterNetAssets;
    }

    public void setPrevQuarterNetAssets(Long value) {
        this.prevQuarterNetAssets = value;
    }


    public Long getFixedProfitBalance() {
        return this.fixedProfitBalance;
    }

    public void setFixedProfitBalance(Long value) {
        this.fixedProfitBalance = value;
    }


    public Long getListedInvestBalance() {
        return this.listedInvestBalance;
    }

    public void setListedInvestBalance(Long value) {
        this.listedInvestBalance = value;
    }


    public Long getUnlistedInvestBalance() {
        return this.unlistedInvestBalance;
    }

    public void setUnlistedInvestBalance(Long value) {
        this.unlistedInvestBalance = value;
    }


    public Long getImvbsInvestBalance() {
        return this.imvbsInvestBalance;
    }

    public void setImvbsInvestBalance(Long value) {
        this.imvbsInvestBalance = value;
    }


    public Long getComBankFinFund() {
        return this.comBankFinFund;
    }

    public void setComBankFinFund(Long value) {
        this.comBankFinFund = value;
    }


    public Long getBankCreditFund() {
        return this.bankCreditFund;
    }

    public void setBankCreditFund(Long value) {
        this.bankCreditFund = value;
    }


    public Long getTrustFund() {
        return this.trustFund;
    }

    public void setTrustFund(Long value) {
        this.trustFund = value;
    }


    public Long getBondSpecialFund() {
        return this.bondSpecialFund;
    }

    public void setBondSpecialFund(Long value) {
        this.bondSpecialFund = value;
    }


    public Long getOtherFund() {
        return this.otherFund;
    }

    public void setOtherFund(Long value) {
        this.otherFund = value;
    }


    public Long getOtherTotalFund() {
        return this.otherTotalFund;
    }

    public void setOtherTotalFund(Long value) {
        this.otherTotalFund = value;
    }


    public Long getAllTotalFund() {
        return this.allTotalFund;
    }

    public void setAllTotalFund(Long value) {
        this.allTotalFund = value;
    }


    public String getOtherNotes() {
        return this.otherNotes;
    }

    public void setOtherNotes(String value) {
        this.otherNotes = value;
    }


    public Long getFixedProfitTotalPct() {
        return this.fixedProfitTotalPct;
    }

    public void setFixedProfitTotalPct(Long value) {
        this.fixedProfitTotalPct = value;
    }


    public Long getFixedProfitNetPct() {
        return this.fixedProfitNetPct;
    }

    public void setFixedProfitNetPct(Long value) {
        this.fixedProfitNetPct = value;
    }


    public Long getListedInvestTotalPct() {
        return this.listedInvestTotalPct;
    }

    public void setListedInvestTotalPct(Long value) {
        this.listedInvestTotalPct = value;
    }


    public Long getListedInvestNetPct() {
        return this.listedInvestNetPct;
    }

    public void setListedInvestNetPct(Long value) {
        this.listedInvestNetPct = value;
    }


    public Long getUnlistedInvestTotalPct() {
        return this.unlistedInvestTotalPct;
    }

    public void setUnlistedInvestTotalPct(Long value) {
        this.unlistedInvestTotalPct = value;
    }


    public Long getUnlistedInvestNetPct() {
        return this.unlistedInvestNetPct;
    }

    public void setUnlistedInvestNetPct(Long value) {
        this.unlistedInvestNetPct = value;
    }


    public Long getImvbsInvestTotalPct() {
        return this.imvbsInvestTotalPct;
    }

    public void setImvbsInvestTotalPct(Long value) {
        this.imvbsInvestTotalPct = value;
    }


    public Long getImvbsInvestNetPct() {
        return this.imvbsInvestNetPct;
    }

    public void setImvbsInvestNetPct(Long value) {
        this.imvbsInvestNetPct = value;
    }


    public Long getComBankFinTotalPct() {
        return this.comBankFinTotalPct;
    }

    public void setComBankFinTotalPct(Long value) {
        this.comBankFinTotalPct = value;
    }


    public Long getComBankFinNetPct() {
        return this.comBankFinNetPct;
    }

    public void setComBankFinNetPct(Long value) {
        this.comBankFinNetPct = value;
    }


    public Long getBankCreditTotalPct() {
        return this.bankCreditTotalPct;
    }

    public void setBankCreditTotalPct(Long value) {
        this.bankCreditTotalPct = value;
    }


    public Long getBankCreditNetPct() {
        return this.bankCreditNetPct;
    }

    public void setBankCreditNetPct(Long value) {
        this.bankCreditNetPct = value;
    }


    public Long getTrustTotalPct() {
        return this.trustTotalPct;
    }

    public void setTrustTotalPct(Long value) {
        this.trustTotalPct = value;
    }


    public Long getTrustNetPct() {
        return this.trustNetPct;
    }

    public void setTrustNetPct(Long value) {
        this.trustNetPct = value;
    }


    public Long getBondSpecialTotalPct() {
        return this.bondSpecialTotalPct;
    }

    public void setBondSpecialTotalPct(Long value) {
        this.bondSpecialTotalPct = value;
    }


    public Long getBondSpecialNetPct() {
        return this.bondSpecialNetPct;
    }

    public void setBondSpecialNetPct(Long value) {
        this.bondSpecialNetPct = value;
    }


    public Long getOtherTotalPct() {
        return this.otherTotalPct;
    }

    public void setOtherTotalPct(Long value) {
        this.otherTotalPct = value;
    }


    public Long getOtherNetPct() {
        return this.otherNetPct;
    }

    public void setOtherNetPct(Long value) {
        this.otherNetPct = value;
    }


    public Long getOtherCountTotalPct() {
        return this.otherCountTotalPct;
    }

    public void setOtherCountTotalPct(Long value) {
        this.otherCountTotalPct = value;
    }


    public Long getOtherCountNetPct() {
        return this.otherCountNetPct;
    }

    public void setOtherCountNetPct(Long value) {
        this.otherCountNetPct = value;
    }


    public Long getAllCountTotalPct() {
        return this.allCountTotalPct;
    }

    public void setAllCountTotalPct(Long value) {
        this.allCountTotalPct = value;
    }


    public Long getAllCountNetPct() {
        return this.allCountNetPct;
    }

    public void setAllCountNetPct(Long value) {
        this.allCountNetPct = value;
    }


    public Long getFixedProfitYtotalPct() {
        return this.fixedProfitYtotalPct;
    }

    public void setFixedProfitYtotalPct(Long value) {
        this.fixedProfitYtotalPct = value;
    }


    public Long getFixedProfitYnetPct() {
        return this.fixedProfitYnetPct;
    }

    public void setFixedProfitYnetPct(Long value) {
        this.fixedProfitYnetPct = value;
    }


    public Long getListedInvestYtotalPct() {
        return this.listedInvestYtotalPct;
    }

    public void setListedInvestYtotalPct(Long value) {
        this.listedInvestYtotalPct = value;
    }


    public Long getListedInvestYnetPct() {
        return this.listedInvestYnetPct;
    }

    public void setListedInvestYnetPct(Long value) {
        this.listedInvestYnetPct = value;
    }


    public Long getUnlistedInvestYtotalPct() {
        return this.unlistedInvestYtotalPct;
    }

    public void setUnlistedInvestYtotalPct(Long value) {
        this.unlistedInvestYtotalPct = value;
    }


    public Long getUnlistedInvestYnetPct() {
        return this.unlistedInvestYnetPct;
    }

    public void setUnlistedInvestYnetPct(Long value) {
        this.unlistedInvestYnetPct = value;
    }


    public Long getImvbsInvestYtotalPct() {
        return this.imvbsInvestYtotalPct;
    }

    public void setImvbsInvestYtotalPct(Long value) {
        this.imvbsInvestYtotalPct = value;
    }


    public Long getImvbsInvestYnetPct() {
        return this.imvbsInvestYnetPct;
    }

    public void setImvbsInvestYnetPct(Long value) {
        this.imvbsInvestYnetPct = value;
    }


    public Long getComBankFinYtotalPct() {
        return this.comBankFinYtotalPct;
    }

    public void setComBankFinYtotalPct(Long value) {
        this.comBankFinYtotalPct = value;
    }


    public Long getComBankFinYnetPct() {
        return this.comBankFinYnetPct;
    }

    public void setComBankFinYnetPct(Long value) {
        this.comBankFinYnetPct = value;
    }


    public Long getBankCreditYtotalPct() {
        return this.bankCreditYtotalPct;
    }

    public void setBankCreditYtotalPct(Long value) {
        this.bankCreditYtotalPct = value;
    }


    public Long getBankCreditYnetPct() {
        return this.bankCreditYnetPct;
    }

    public void setBankCreditYnetPct(Long value) {
        this.bankCreditYnetPct = value;
    }


    public Long getTrustYtotalPct() {
        return this.trustYtotalPct;
    }

    public void setTrustYtotalPct(Long value) {
        this.trustYtotalPct = value;
    }


    public Long getTrustYnetPct() {
        return this.trustYnetPct;
    }

    public void setTrustYnetPct(Long value) {
        this.trustYnetPct = value;
    }


    public Long getBondSpecialYtotalPct() {
        return this.bondSpecialYtotalPct;
    }

    public void setBondSpecialYtotalPct(Long value) {
        this.bondSpecialYtotalPct = value;
    }


    public Long getBondSpecialYnetPct() {
        return this.bondSpecialYnetPct;
    }

    public void setBondSpecialYnetPct(Long value) {
        this.bondSpecialYnetPct = value;
    }


    public Long getOtherYtotalPct() {
        return this.otherYtotalPct;
    }

    public void setOtherYtotalPct(Long value) {
        this.otherYtotalPct = value;
    }


    public Long getOtherYnetPct() {
        return this.otherYnetPct;
    }

    public void setOtherYnetPct(Long value) {
        this.otherYnetPct = value;
    }


    public Long getOtherCountYtotalPct() {
        return this.otherCountYtotalPct;
    }

    public void setOtherCountYtotalPct(Long value) {
        this.otherCountYtotalPct = value;
    }


    public Long getOtherCountYnetPct() {
        return this.otherCountYnetPct;
    }

    public void setOtherCountYnetPct(Long value) {
        this.otherCountYnetPct = value;
    }


    public Long getAllCountYtotalPct() {
        return this.allCountYtotalPct;
    }

    public void setAllCountYtotalPct(Long value) {
        this.allCountYtotalPct = value;
    }


    public Long getAllCountYnetPct() {
        return this.allCountYnetPct;
    }

    public void setAllCountYnetPct(Long value) {
        this.allCountYnetPct = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("relatedTransactionsQuarterly[\n")
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
        if (obj instanceof RelatedTransactionsQuarterly == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelatedTransactionsQuarterly other = (RelatedTransactionsQuarterly) obj;
        return new EqualsBuilder()
                .isEquals();
    }
}

