<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>单笔关联交易交易</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="singleRelatedTransactionBox" title="单笔关联交易交易" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="自增主键" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易id" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.transId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送记录版本" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.reportVersion}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="主体类型（1-保险，2-银行）" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.originalType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="机构类型" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.originalClass}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="机构类型名称" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.originalClassName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体机构登记编码" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.originalRegCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体机构登记名称" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.originalRegName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易类型" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.transType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易标的" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.transObject}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易标的情况说明" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.transObjectDesc}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易提交时间" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.signTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易批准日期" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.approvalTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="该笔交易所有关联方名称" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.counterpartys}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易评价" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.transComment}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易金额" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.transAmount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易价格" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.transPrice}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易本年度累计金额" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.transCountYear}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易目的" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.transPurpose}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="定价策略" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.pricingStrategy}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="对财务状况及经营成果的影响" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.impactFinancialOperating}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上一年末总资产（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.lastYearAmount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="保险：上一年末净资产（万元/人民币），银行：上季末资本净额（万元/人民币）" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.lastYearNetAssets}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其他说明事项" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.otherMatters}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易收益与损失" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.incomeLoss}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易方在交易中所占权益的性质与比重" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.natureProportion}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="最近报送时间" col="2" formControl="true"/>
					<bc:label text="${singleRelatedTransaction.operateTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/singleRelatedTransaction/list';
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