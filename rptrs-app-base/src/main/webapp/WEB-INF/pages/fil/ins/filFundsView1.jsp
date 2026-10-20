<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联交易表（保险）-资金运用类</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="filFundsBox" title="关联交易表（保险）-资金运用类" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="主键" col="2" formControl="true"/>
					<bc:label text="${filFunds.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方ID" col="2" formControl="true"/>
					<bc:label text="${filFunds.relId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方类型" col="2" formControl="true"/>
					<bc:label text="${filFunds.relType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方名称" col="2" formControl="true"/>
					<bc:label text="${filFunds.relName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方证件号码" col="2" formControl="true"/>
					<bc:label text="${filFunds.relNumber}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易机构" col="2" formControl="true"/>
					<bc:label text="${filFunds.tradeOrg}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易类型" col="2" formControl="true"/>
					<bc:label text="${filFunds.tranType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="数据来源" col="2" formControl="true"/>
					<bc:label text="${filFunds.dataSources}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易日期" col="2" formControl="true"/>
					<bc:label text="${filFunds.tranDate}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否重大关联交易" col="2" formControl="true"/>
					<bc:label text="${filFunds.majorTrade}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="口径" col="2" formControl="true"/>
					<bc:label text="${filFunds.caliber}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="状态" col="2" formControl="true"/>
					<bc:label text="${filFunds.status}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="状态说明" col="2" formControl="true"/>
					<bc:label text="${filFunds.statusComment}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="本年度与该关联方发生的关联交易累计金额（万元）" col="2" formControl="true"/>
					<bc:label text="${filFunds.totolTradeAmount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="合同编号" col="2" formControl="true"/>
					<bc:label text="${filFunds.contractNo}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易金额（万元）" col="2" formControl="true"/>
					<bc:label text="${filFunds.tradeAmount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="业务起始日期" col="2" formControl="true"/>
					<bc:label text="${filFunds.busStartTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="业务终止日期" col="2" formControl="true"/>
					<bc:label text="${filFunds.busEndTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="投资类别" col="2" formControl="true"/>
					<bc:label text="${filFunds.investmentType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="投资方式" col="2" formControl="true"/>
					<bc:label text="${filFunds.investmentMethod}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="被投资企业情况" col="2" formControl="true"/>
					<bc:label text="${filFunds.investedEnterprises}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="未上市权益类_前次交易价格" col="2" formControl="true"/>
					<bc:label text="${filFunds.equityLastTradePrice}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="不动产类_前次交易价格" col="2" formControl="true"/>
					<bc:label text="${filFunds.imLastTradePrice}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="不动产类_标的情况" col="2" formControl="true"/>
					<bc:label text="${filFunds.imSubjectMatter}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="境外投资_标的情况" col="2" formControl="true"/>
					<bc:label text="${filFunds.outSubjectMatter}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="股债比" col="2" formControl="true"/>
					<bc:label text="${filFunds.shareDebtRatio}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="金融资产类别" col="2" formControl="true"/>
					<bc:label text="${filFunds.financialAssstsType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="金融资产编号" col="2" formControl="true"/>
					<bc:label text="${filFunds.financialAssstsNum}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="金融产品发行规模" col="2" formControl="true"/>
					<bc:label text="${filFunds.financialProScale}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="购买金融产品份额占比（%）" col="2" formControl="true"/>
					<bc:label text="${filFunds.financialProRatio}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="底层资产" col="2" formControl="true"/>
					<bc:label text="${filFunds.underlyingAsset}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="底层资产是否为关联方" col="2" formControl="true"/>
					<bc:label text="${filFunds.underIsRel}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="底层基础资产是否涉及控股股东或控股股东的关联方" col="2" formControl="true"/>
					<bc:label text="${filFunds.underInvolveRel}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="底层资产关联关系" col="2" formControl="true"/>
					<bc:label text="${filFunds.underRel}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="被投企业名称" col="2" formControl="true"/>
					<bc:label text="${filFunds.investedEnterprisesName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="业务模式" col="2" formControl="true"/>
					<bc:label text="${filFunds.busModel}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="盈利预期" col="2" formControl="true"/>
					<bc:label text="${filFunds.earningsExpectaions}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="投资比例（%）" col="2" formControl="true"/>
					<bc:label text="${filFunds.investmentRatio}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="投资收益分配情况" col="2" formControl="true"/>
					<bc:label text="${filFunds.investmentAllocation}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="损失分担情况" col="2" formControl="true"/>
					<bc:label text="${filFunds.lossShare}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易价格" col="2" formControl="true"/>
					<bc:label text="${filFunds.tradePrice}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="定价政策" col="2" formControl="true"/>
					<bc:label text="${filFunds.pricingPolicy}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易标的" col="2" formControl="true"/>
					<bc:label text="${filFunds.tradeSubject}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其他事项说明" col="2" formControl="true"/>
					<bc:label text="${filFunds.remark}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否生效" col="2" formControl="true"/>
					<bc:label text="${filFunds.isEffective}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="审批金额" col="2" formControl="true"/>
					<bc:label text="${filFunds.approveTradeAmount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易编号" col="2" formControl="true"/>
					<bc:label text="${filFunds.transNum}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="存款期限" col="2" formControl="true"/>
					<bc:label text="${filFunds.depositTerms}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="规则结果" col="2" formControl="true"/>
					<bc:label text="${filFunds.ruleResult}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易定价是否为国家规定" col="2" formControl="true"/>
					<bc:label text="${filFunds.isDealPrice}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/ins/filFunds/list';
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