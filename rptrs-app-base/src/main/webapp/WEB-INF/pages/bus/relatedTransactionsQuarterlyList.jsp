<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>关联交易季报</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="关联交易季报" subTitle="请输入关联交易季报查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="自增主键" col="5" formControl="true"/>
						<bf:text name="__LIKES_pid" col="7" placeholder="自增主键"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="relatedTransactionsQuarterlyTable" idField="pid" url="${base}/bus/relatedTransactionsQuarterly">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="自增主键" width="100"/>
				<bc:column field="reportId" title="季报主键（与季报表关联）" width="100"/>
				<bc:column field="reportOrgCls" title="上报机构分类（1-银行，2-保险）" width="100"/>
				<bc:column field="reportOrgType" title="上报机构类型" width="100"/>
				<bc:column field="reportOrgCode" title="上报机构编码" width="100"/>
				<bc:column field="reportOrgName" title="报送机构名称" width="100"/>
				<bc:column field="reportYear" title="季报年份" width="100"/>
				<bc:column field="reportQuarter" title="季报季度" width="100"/>
				<bc:column field="reportTime" title="季报上报时间" width="100"/>
				<bc:column field="prevYearTotalAssets" title="上年末总资产（万元/人民币）" width="100"/>
				<bc:column field="prevYearNetAssets" title="上年末净资产（万元/人民币）" width="100"/>
				<bc:column field="prevQuarterTotalAssets" title="上季末总资产（万元/人民币）" width="100"/>
				<bc:column field="prevQuarterNetAssets" title="上季末净资产（万元/人民币）" width="100"/>
				<bc:column field="fixedProfitBalance" title="固定收益投资余额（万元/人民币）" width="100"/>
				<bc:column field="listedInvestBalance" title="上市权益类资产投资余额（万元/人民币）" width="100"/>
				<bc:column field="unlistedInvestBalance" title="未上市权益类资产投资余额（万元/人民币）" width="100"/>
				<bc:column field="imvbsInvestBalance" title="不动产类资产投资余额（万元/人民币）" width="100"/>
				<bc:column field="comBankFinFund" title="商业银行理财产品（万元/人民币）" width="100"/>
				<bc:column field="bankCreditFund" title="银行业金融机构信贷资产支持证券（万元/人民币）" width="100"/>
				<bc:column field="trustFund" title="信托公司集合资金信托计划（万元/人民币）" width="100"/>
				<bc:column field="bondSpecialFund" title="证券公司专项资产管理计划（万元/人民币）" width="100"/>
				<bc:column field="otherFund" title="其它资金（万元/人民币）" width="100"/>
				<bc:column field="otherTotalFund" title="其它金融资产投资余额合计（万元/人民币）" width="100"/>
				<bc:column field="allTotalFund" title="全部投资余额（万元/人民币）" width="100"/>
				<bc:column field="otherNotes" title="其它说明事项" width="100"/>
				<bc:column field="fixedProfitTotalPct" title="固定收益投资余额与上季末总资产百分比" width="100"/>
				<bc:column field="fixedProfitNetPct" title="固定收益投资余额与上季末净资产百分比" width="100"/>
				<bc:column field="listedInvestTotalPct" title="上市权益类资产投资余额与上季末总资产百分比" width="100"/>
				<bc:column field="listedInvestNetPct" title="上市权益类资产投资余额与上季末净资产百分比" width="100"/>
				<bc:column field="unlistedInvestTotalPct" title="未上市权益类资产投资余额与上季末总资产百分比" width="100"/>
				<bc:column field="unlistedInvestNetPct" title="未上市权益类资产投资余额与上季末净资产百分比" width="100"/>
				<bc:column field="imvbsInvestTotalPct" title="不动产类资产投资余额与上季末总资产百分比" width="100"/>
				<bc:column field="imvbsInvestNetPct" title="不动产类资产投资余额与上季末净资产百分比" width="100"/>
				<bc:column field="comBankFinTotalPct" title="商业银行理财产品与上季末总资产百分比" width="100"/>
				<bc:column field="comBankFinNetPct" title="商业银行理财产品与上季末净资产百分比" width="100"/>
				<bc:column field="bankCreditTotalPct" title="银行业金融机构信贷资产支持证券与上季末总资产百分比" width="100"/>
				<bc:column field="bankCreditNetPct" title="银行业金融机构信贷资产支持证券与上季末净资产百分比" width="100"/>
				<bc:column field="trustTotalPct" title="信托公司集合资金信托计划与上季末总资产百分比" width="100"/>
				<bc:column field="trustNetPct" title="信托公司集合资金信托计划与上季末净资产百分比" width="100"/>
				<bc:column field="bondSpecialTotalPct" title="证券公司专项资产管理计划与上季末总资产百分比" width="100"/>
				<bc:column field="bondSpecialNetPct" title="证券公司专项资产管理计划与上季末净资产百分比" width="100"/>
				<bc:column field="otherTotalPct" title="其它资金与上季末总资产百分比" width="100"/>
				<bc:column field="otherNetPct" title="其它资金与上季末净资产百分比" width="100"/>
				<bc:column field="otherCountTotalPct" title="其它金融资产投资余额合计与上季末总资产百分比" width="100"/>
				<bc:column field="otherCountNetPct" title="其它金融资产投资余额合计与上季末净资产百分比" width="100"/>
				<bc:column field="allCountTotalPct" title="全部投资余额与上季末总资产百分比" width="100"/>
				<bc:column field="allCountNetPct" title="全部投资余额与上季末净资产百分比" width="100"/>
				<bc:column field="fixedProfitYtotalPct" title="固定收益投资余额与上年末总资产百分比" width="100"/>
				<bc:column field="fixedProfitYnetPct" title="固定收益投资余额与上年末净资产百分比" width="100"/>
				<bc:column field="listedInvestYtotalPct" title="上市权益类资产投资余额与上年末总资产百分比" width="100"/>
				<bc:column field="listedInvestYnetPct" title="上市权益类资产投资余额与上年末净资产百分比" width="100"/>
				<bc:column field="unlistedInvestYtotalPct" title="未上市权益类资产投资余额与上年末总资产百分比" width="100"/>
				<bc:column field="unlistedInvestYnetPct" title="未上市权益类资产投资余额与上年末净资产百分比" width="100"/>
				<bc:column field="imvbsInvestYtotalPct" title="不动产类资产投资余额与上年末总资产百分比" width="100"/>
				<bc:column field="imvbsInvestYnetPct" title="不动产类资产投资余额与上年末净资产百分比" width="100"/>
				<bc:column field="comBankFinYtotalPct" title="商业银行理财产品与上年末总资产百分比" width="100"/>
				<bc:column field="comBankFinYnetPct" title="商业银行理财产品与上年末净资产百分比" width="100"/>
				<bc:column field="bankCreditYtotalPct" title="银行业金融机构信贷资产支持证券与上年末总资产百分比" width="100"/>
				<bc:column field="bankCreditYnetPct" title="银行业金融机构信贷资产支持证券与上年末净资产百分比" width="100"/>
				<bc:column field="trustYtotalPct" title="信托公司集合资金信托计划与上年末总资产百分比" width="100"/>
				<bc:column field="trustYnetPct" title="信托公司集合资金信托计划与上年末净资产百分比" width="100"/>
				<bc:column field="bondSpecialYtotalPct" title="证券公司专项资产管理计划与上年末总资产百分比" width="100"/>
				<bc:column field="bondSpecialYnetPct" title="证券公司专项资产管理计划与上年末净资产百分比" width="100"/>
				<bc:column field="otherYtotalPct" title="其它资金与上年末总资产百分比" width="100"/>
				<bc:column field="otherYnetPct" title="其它资金与上年末净资产百分比" width="100"/>
				<bc:column field="otherCountYtotalPct" title="其它金融资产投资余额合计与上年末总资产百分比" width="100"/>
				<bc:column field="otherCountYnetPct" title="其它金融资产投资余额合计与上年末净资产百分比" width="100"/>
				<bc:column field="allCountYtotalPct" title="全部投资余额与上年末总资产百分比" width="100"/>
				<bc:column field="allCountYnetPct" title="全部投资余额与上年末净资产百分比" width="100"/>
				<bc:operator id="view" icon="search" title="查看"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>