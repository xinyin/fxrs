<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联交易季报</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="relatedTransactionsQuarterlyBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${relatedTransactionsQuarterly.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/relatedTransactionsQuarterly/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="季报主键（与季报表关联）" col="4" formControl="true"/>
						<bf:text id="reportId" name="reportId" col="8" value="${relatedTransactionsQuarterly.reportId}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/relatedTransactionsQuarterly/check" name="reportId" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上报机构分类（1-银行，2-保险）" col="4" formControl="true"/>
						<bf:text id="reportOrgCls" name="reportOrgCls" col="8" value="${relatedTransactionsQuarterly.reportOrgCls}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上报机构类型" col="4" formControl="true"/>
						<bf:text id="reportOrgType" name="reportOrgType" col="8" value="${relatedTransactionsQuarterly.reportOrgType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上报机构编码" col="4" formControl="true"/>
						<bf:text id="reportOrgCode" name="reportOrgCode" col="8" value="${relatedTransactionsQuarterly.reportOrgCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="报送机构名称" col="4" formControl="true"/>
						<bf:text id="reportOrgName" name="reportOrgName" col="8" value="${relatedTransactionsQuarterly.reportOrgName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="季报年份" col="4" formControl="true"/>
						<bf:text id="reportYear" name="reportYear" col="8" value="${relatedTransactionsQuarterly.reportYear}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="季报季度" col="4" formControl="true"/>
						<bf:text id="reportQuarter" name="reportQuarter" col="8" value="${relatedTransactionsQuarterly.reportQuarter}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="季报上报时间" col="4" formControl="true"/>
						<bf:datebox id="reportTime" name="reportTime" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上年末总资产（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="prevYearTotalAssets" name="prevYearTotalAssets" col="8" value="${relatedTransactionsQuarterly.prevYearTotalAssets}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上年末净资产（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="prevYearNetAssets" name="prevYearNetAssets" col="8" value="${relatedTransactionsQuarterly.prevYearNetAssets}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上季末总资产（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="prevQuarterTotalAssets" name="prevQuarterTotalAssets" col="8" value="${relatedTransactionsQuarterly.prevQuarterTotalAssets}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上季末净资产（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="prevQuarterNetAssets" name="prevQuarterNetAssets" col="8" value="${relatedTransactionsQuarterly.prevQuarterNetAssets}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="固定收益投资余额（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="fixedProfitBalance" name="fixedProfitBalance" col="8" value="${relatedTransactionsQuarterly.fixedProfitBalance}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上市权益类资产投资余额（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="listedInvestBalance" name="listedInvestBalance" col="8" value="${relatedTransactionsQuarterly.listedInvestBalance}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="未上市权益类资产投资余额（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="unlistedInvestBalance" name="unlistedInvestBalance" col="8" value="${relatedTransactionsQuarterly.unlistedInvestBalance}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="不动产类资产投资余额（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="imvbsInvestBalance" name="imvbsInvestBalance" col="8" value="${relatedTransactionsQuarterly.imvbsInvestBalance}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="商业银行理财产品（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="comBankFinFund" name="comBankFinFund" col="8" value="${relatedTransactionsQuarterly.comBankFinFund}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="银行业金融机构信贷资产支持证券（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="bankCreditFund" name="bankCreditFund" col="8" value="${relatedTransactionsQuarterly.bankCreditFund}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="信托公司集合资金信托计划（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="trustFund" name="trustFund" col="8" value="${relatedTransactionsQuarterly.trustFund}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证券公司专项资产管理计划（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="bondSpecialFund" name="bondSpecialFund" col="8" value="${relatedTransactionsQuarterly.bondSpecialFund}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="其它资金（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="otherFund" name="otherFund" col="8" value="${relatedTransactionsQuarterly.otherFund}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="其它金融资产投资余额合计（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="otherTotalFund" name="otherTotalFund" col="8" value="${relatedTransactionsQuarterly.otherTotalFund}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="全部投资余额（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="allTotalFund" name="allTotalFund" col="8" value="${relatedTransactionsQuarterly.allTotalFund}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="其它说明事项" col="4" formControl="true"/>
						<bf:text id="otherNotes" name="otherNotes" col="8" value="${relatedTransactionsQuarterly.otherNotes}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="固定收益投资余额与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="fixedProfitTotalPct" name="fixedProfitTotalPct" col="8" value="${relatedTransactionsQuarterly.fixedProfitTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="固定收益投资余额与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="fixedProfitNetPct" name="fixedProfitNetPct" col="8" value="${relatedTransactionsQuarterly.fixedProfitNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上市权益类资产投资余额与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="listedInvestTotalPct" name="listedInvestTotalPct" col="8" value="${relatedTransactionsQuarterly.listedInvestTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上市权益类资产投资余额与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="listedInvestNetPct" name="listedInvestNetPct" col="8" value="${relatedTransactionsQuarterly.listedInvestNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="未上市权益类资产投资余额与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="unlistedInvestTotalPct" name="unlistedInvestTotalPct" col="8" value="${relatedTransactionsQuarterly.unlistedInvestTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="未上市权益类资产投资余额与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="unlistedInvestNetPct" name="unlistedInvestNetPct" col="8" value="${relatedTransactionsQuarterly.unlistedInvestNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="不动产类资产投资余额与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="imvbsInvestTotalPct" name="imvbsInvestTotalPct" col="8" value="${relatedTransactionsQuarterly.imvbsInvestTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="不动产类资产投资余额与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="imvbsInvestNetPct" name="imvbsInvestNetPct" col="8" value="${relatedTransactionsQuarterly.imvbsInvestNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="商业银行理财产品与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="comBankFinTotalPct" name="comBankFinTotalPct" col="8" value="${relatedTransactionsQuarterly.comBankFinTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="商业银行理财产品与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="comBankFinNetPct" name="comBankFinNetPct" col="8" value="${relatedTransactionsQuarterly.comBankFinNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="银行业金融机构信贷资产支持证券与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="bankCreditTotalPct" name="bankCreditTotalPct" col="8" value="${relatedTransactionsQuarterly.bankCreditTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="银行业金融机构信贷资产支持证券与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="bankCreditNetPct" name="bankCreditNetPct" col="8" value="${relatedTransactionsQuarterly.bankCreditNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="信托公司集合资金信托计划与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="trustTotalPct" name="trustTotalPct" col="8" value="${relatedTransactionsQuarterly.trustTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="信托公司集合资金信托计划与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="trustNetPct" name="trustNetPct" col="8" value="${relatedTransactionsQuarterly.trustNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证券公司专项资产管理计划与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="bondSpecialTotalPct" name="bondSpecialTotalPct" col="8" value="${relatedTransactionsQuarterly.bondSpecialTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证券公司专项资产管理计划与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="bondSpecialNetPct" name="bondSpecialNetPct" col="8" value="${relatedTransactionsQuarterly.bondSpecialNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="其它资金与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="otherTotalPct" name="otherTotalPct" col="8" value="${relatedTransactionsQuarterly.otherTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="其它资金与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="otherNetPct" name="otherNetPct" col="8" value="${relatedTransactionsQuarterly.otherNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="其它金融资产投资余额合计与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="otherCountTotalPct" name="otherCountTotalPct" col="8" value="${relatedTransactionsQuarterly.otherCountTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="其它金融资产投资余额合计与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="otherCountNetPct" name="otherCountNetPct" col="8" value="${relatedTransactionsQuarterly.otherCountNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="全部投资余额与上季末总资产百分比" col="4" formControl="true"/>
						<bf:text id="allCountTotalPct" name="allCountTotalPct" col="8" value="${relatedTransactionsQuarterly.allCountTotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="全部投资余额与上季末净资产百分比" col="4" formControl="true"/>
						<bf:text id="allCountNetPct" name="allCountNetPct" col="8" value="${relatedTransactionsQuarterly.allCountNetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="固定收益投资余额与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="fixedProfitYtotalPct" name="fixedProfitYtotalPct" col="8" value="${relatedTransactionsQuarterly.fixedProfitYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="固定收益投资余额与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="fixedProfitYnetPct" name="fixedProfitYnetPct" col="8" value="${relatedTransactionsQuarterly.fixedProfitYnetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上市权益类资产投资余额与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="listedInvestYtotalPct" name="listedInvestYtotalPct" col="8" value="${relatedTransactionsQuarterly.listedInvestYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上市权益类资产投资余额与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="listedInvestYnetPct" name="listedInvestYnetPct" col="8" value="${relatedTransactionsQuarterly.listedInvestYnetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="未上市权益类资产投资余额与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="unlistedInvestYtotalPct" name="unlistedInvestYtotalPct" col="8" value="${relatedTransactionsQuarterly.unlistedInvestYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="未上市权益类资产投资余额与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="unlistedInvestYnetPct" name="unlistedInvestYnetPct" col="8" value="${relatedTransactionsQuarterly.unlistedInvestYnetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="不动产类资产投资余额与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="imvbsInvestYtotalPct" name="imvbsInvestYtotalPct" col="8" value="${relatedTransactionsQuarterly.imvbsInvestYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="不动产类资产投资余额与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="imvbsInvestYnetPct" name="imvbsInvestYnetPct" col="8" value="${relatedTransactionsQuarterly.imvbsInvestYnetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="商业银行理财产品与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="comBankFinYtotalPct" name="comBankFinYtotalPct" col="8" value="${relatedTransactionsQuarterly.comBankFinYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="商业银行理财产品与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="comBankFinYnetPct" name="comBankFinYnetPct" col="8" value="${relatedTransactionsQuarterly.comBankFinYnetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="银行业金融机构信贷资产支持证券与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="bankCreditYtotalPct" name="bankCreditYtotalPct" col="8" value="${relatedTransactionsQuarterly.bankCreditYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="银行业金融机构信贷资产支持证券与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="bankCreditYnetPct" name="bankCreditYnetPct" col="8" value="${relatedTransactionsQuarterly.bankCreditYnetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="信托公司集合资金信托计划与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="trustYtotalPct" name="trustYtotalPct" col="8" value="${relatedTransactionsQuarterly.trustYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="信托公司集合资金信托计划与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="trustYnetPct" name="trustYnetPct" col="8" value="${relatedTransactionsQuarterly.trustYnetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证券公司专项资产管理计划与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="bondSpecialYtotalPct" name="bondSpecialYtotalPct" col="8" value="${relatedTransactionsQuarterly.bondSpecialYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证券公司专项资产管理计划与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="bondSpecialYnetPct" name="bondSpecialYnetPct" col="8" value="${relatedTransactionsQuarterly.bondSpecialYnetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="其它资金与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="otherYtotalPct" name="otherYtotalPct" col="8" value="${relatedTransactionsQuarterly.otherYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="其它资金与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="otherYnetPct" name="otherYnetPct" col="8" value="${relatedTransactionsQuarterly.otherYnetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="其它金融资产投资余额合计与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="otherCountYtotalPct" name="otherCountYtotalPct" col="8" value="${relatedTransactionsQuarterly.otherCountYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="其它金融资产投资余额合计与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="otherCountYnetPct" name="otherCountYnetPct" col="8" value="${relatedTransactionsQuarterly.otherCountYnetPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="全部投资余额与上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="allCountYtotalPct" name="allCountYtotalPct" col="8" value="${relatedTransactionsQuarterly.allCountYtotalPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="全部投资余额与上年末净资产百分比" col="4" formControl="true"/>
						<bf:text id="allCountYnetPct" name="allCountYnetPct" col="8" value="${relatedTransactionsQuarterly.allCountYnetPct}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/relatedTransactionsQuarterly/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/relatedTransactionsQuarterly/list';
											});
										}
										else
										{
											$.messager.alert('错误', data.msg, 'error');
										}
									});
								}
							</be:event>
						</bc:button>
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/relatedTransactionsQuarterly/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
			</bf:form>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
</body>
</html>
