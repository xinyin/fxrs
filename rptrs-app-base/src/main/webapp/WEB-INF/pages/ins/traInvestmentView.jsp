<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联交易表（保险）-投资入股类</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="traInvestmentBox" title="关联交易表（保险）-投资入股类" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="主键" col="2" formControl="true"/>
					<bc:label text="${traInvestment.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易主体名称" col="2" formControl="true"/>
					<bc:label text="${traInvestment.subjectName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易对手名称" col="2" formControl="true"/>
					<bc:label text="${traInvestment.enemyName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否为控股子公司" col="2" formControl="true"/>
					<bc:label text="${traInvestment.isHoldingSubsidiary}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方证件号码" col="2" formControl="true"/>
					<bc:label text="${traInvestment.relNumber}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易机构" col="2" formControl="true"/>
					<bc:label text="${traInvestment.tradeOrg}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易类型" col="2" formControl="true"/>
					<bc:label text="${traInvestment.tranType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="数据来源" col="2" formControl="true"/>
					<bc:label text="${traInvestment.dataSources}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易日期" col="2" formControl="true"/>
					<bc:label text="${traInvestment.tranDate}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否重大关联交易" col="2" formControl="true"/>
					<bc:label text="${traInvestment.majorTrade}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<%--<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否现金认购" col="2" formControl="true"/>
					<bc:label text="${traInvestment.isCashSubscript}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易定价是否为国家规定" col="2" formControl="true"/>
					<bc:label text="${traInvestment.isDealPrice}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>--%>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="口径" col="2" formControl="true"/>
					<bc:label text="${traInvestment.caliber}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="状态" col="2" formControl="true"/>
					<bc:label text="${traInvestment.status}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="状态说明" col="2" formControl="true"/>
					<bc:label text="${traInvestment.statusComment}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="本年度与该关联方发生的关联交易累计金额（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${traInvestment.totolTradeAmount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="合同/交易编号" col="2" formControl="true"/>
					<bc:label text="${traInvestment.contractNo}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易金额（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${traInvestment.tradeAmount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="业务起始日期" col="2" formControl="true"/>
					<bc:label text="${traInvestment.busStartTimeString}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="业务终止日期" col="2" formControl="true"/>
					<bc:label text="${traInvestment.busEndTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="投资方式" col="2" formControl="true"/>
					<bc:label text="${traInvestment.investmentType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="行政审批文号" col="2" formControl="true"/>
					<bc:label text="${traInvestment.approvalNum}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证券类型" col="2" formControl="true"/>
					<bc:label text="${traInvestment.securitiesType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="入股时间" col="2" formControl="true"/>
					<bc:label text="${traInvestment.inTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="股份数" col="2" formControl="true"/>
					<bc:label text="${traInvestment.shareNum}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="股份占比" col="2" formControl="true"/>
					<bc:label text="${traInvestment.shareRetio}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易价格" col="2" formControl="true"/>
					<bc:label text="${traInvestment.tradePrice}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="定价政策" col="2" formControl="true"/>
					<bc:label text="${traInvestment.pricingPolicy}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易标的" col="2" formControl="true"/>
					<bc:label text="${traInvestment.tradeSubject}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其他事项说明" col="2" formControl="true"/>
					<bc:label text="${traInvestment.remark}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易编号" col="2" formControl="true"/>
					<bc:label text="${traInvestment.transNum}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/ins/traInvestment/list';
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