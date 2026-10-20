<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>单笔关联交易交易</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="单笔关联交易交易" subTitle="请输入单笔关联交易交易查询信息" col="12" collapsible="true">
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
			<bc:table id="singleRelatedTransactionTable" idField="pid" url="${base}/bus/singleRelatedTransaction">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="自增主键" width="100"/>
				<bc:column field="transId" title="交易id" width="100"/>
				<bc:column field="reportVersion" title="报送记录版本" width="100"/>
				<bc:column field="originalType" title="主体类型（1-保险，2-银行）" width="100"/>
				<bc:column field="originalClass" title="机构类型" width="100"/>
				<bc:column field="originalClassName" title="机构类型名称" width="100"/>
				<bc:column field="originalRegCode" title="源主体机构登记编码" width="100"/>
				<bc:column field="originalRegName" title="源主体机构登记名称" width="100"/>
				<bc:column field="transType" title="交易类型" width="100"/>
				<bc:column field="transObject" title="交易标的" width="100"/>
				<bc:column field="transObjectDesc" title="交易标的情况说明" width="100"/>
				<bc:column field="signTime" title="交易提交时间" width="100"/>
				<bc:column field="approvalTime" title="交易批准日期" width="100"/>
				<bc:column field="counterpartys" title="该笔交易所有关联方名称" width="100"/>
				<bc:column field="transComment" title="交易评价" width="100"/>
				<bc:column field="transAmount" title="交易金额" width="100"/>
				<bc:column field="transPrice" title="交易价格" width="100"/>
				<bc:column field="transCountYear" title="交易本年度累计金额" width="100"/>
				<bc:column field="transPurpose" title="交易目的" width="100"/>
				<bc:column field="pricingStrategy" title="定价策略" width="100"/>
				<bc:column field="impactFinancialOperating" title="对财务状况及经营成果的影响" width="100"/>
				<bc:column field="lastYearAmount" title="上一年末总资产（万元/人民币）" width="100"/>
				<bc:column field="lastYearNetAssets" title="保险：上一年末净资产（万元/人民币），银行：上季末资本净额（万元/人民币）" width="100"/>
				<bc:column field="otherMatters" title="其他说明事项" width="100"/>
				<bc:column field="incomeLoss" title="交易收益与损失" width="100"/>
				<bc:column field="natureProportion" title="交易方在交易中所占权益的性质与比重" width="100"/>
				<bc:column field="operateTime" title="最近报送时间" width="100"/>
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