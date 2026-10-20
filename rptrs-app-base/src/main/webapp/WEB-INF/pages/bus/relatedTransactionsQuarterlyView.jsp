<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联交易季报</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="relatedTransactionsQuarterlyBox" title="关联交易季报" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="自增主键" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="季报主键（与季报表关联）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.reportId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上报机构分类（1-银行，2-保险）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.reportOrgCls}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上报机构类型" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.reportOrgType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上报机构编码" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.reportOrgCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送机构名称" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.reportOrgName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="季报年份" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.reportYear}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="季报季度" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.reportQuarter}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="季报上报时间" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.reportTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上年末总资产（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.prevYearTotalAssets}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上年末净资产（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.prevYearNetAssets}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上季末总资产（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.prevQuarterTotalAssets}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上季末净资产（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.prevQuarterNetAssets}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="固定收益投资余额（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.fixedProfitBalance}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上市权益类资产投资余额（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.listedInvestBalance}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="未上市权益类资产投资余额（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.unlistedInvestBalance}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="不动产类资产投资余额（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.imvbsInvestBalance}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="商业银行理财产品（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.comBankFinFund}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="银行业金融机构信贷资产支持证券（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.bankCreditFund}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="信托公司集合资金信托计划（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.trustFund}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证券公司专项资产管理计划（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.bondSpecialFund}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它资金（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherFund}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它金融资产投资余额合计（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherTotalFund}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="全部投资余额（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.allTotalFund}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它说明事项" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherNotes}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="固定收益投资余额与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.fixedProfitTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="固定收益投资余额与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.fixedProfitNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上市权益类资产投资余额与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.listedInvestTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上市权益类资产投资余额与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.listedInvestNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="未上市权益类资产投资余额与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.unlistedInvestTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="未上市权益类资产投资余额与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.unlistedInvestNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="不动产类资产投资余额与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.imvbsInvestTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="不动产类资产投资余额与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.imvbsInvestNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="商业银行理财产品与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.comBankFinTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="商业银行理财产品与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.comBankFinNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="银行业金融机构信贷资产支持证券与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.bankCreditTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="银行业金融机构信贷资产支持证券与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.bankCreditNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="信托公司集合资金信托计划与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.trustTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="信托公司集合资金信托计划与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.trustNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证券公司专项资产管理计划与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.bondSpecialTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证券公司专项资产管理计划与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.bondSpecialNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它资金与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它资金与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它金融资产投资余额合计与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherCountTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它金融资产投资余额合计与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherCountNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="全部投资余额与上季末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.allCountTotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="全部投资余额与上季末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.allCountNetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="固定收益投资余额与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.fixedProfitYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="固定收益投资余额与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.fixedProfitYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上市权益类资产投资余额与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.listedInvestYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上市权益类资产投资余额与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.listedInvestYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="未上市权益类资产投资余额与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.unlistedInvestYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="未上市权益类资产投资余额与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.unlistedInvestYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="不动产类资产投资余额与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.imvbsInvestYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="不动产类资产投资余额与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.imvbsInvestYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="商业银行理财产品与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.comBankFinYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="商业银行理财产品与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.comBankFinYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="银行业金融机构信贷资产支持证券与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.bankCreditYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="银行业金融机构信贷资产支持证券与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.bankCreditYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="信托公司集合资金信托计划与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.trustYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="信托公司集合资金信托计划与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.trustYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证券公司专项资产管理计划与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.bondSpecialYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证券公司专项资产管理计划与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.bondSpecialYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它资金与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它资金与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它金融资产投资余额合计与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherCountYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其它金融资产投资余额合计与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.otherCountYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="全部投资余额与上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.allCountYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="全部投资余额与上年末净资产百分比" col="2" formControl="true"/>
					<bc:label text="${relatedTransactionsQuarterly.allCountYnetPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
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
</body>
</html>