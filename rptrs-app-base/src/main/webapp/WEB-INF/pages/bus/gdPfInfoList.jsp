<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>派发方案明细表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="派发方案明细表" subTitle="请输入派发方案明细表查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="主键" col="5" formControl="true"/>
						<bf:text name="__LIKES_pid" col="7" placeholder="主键"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="gdPfInfoTable" idField="pid" url="${base}/bus/gdPfInfo">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="主键" width="100"/>
				<bc:column field="pfPid" title="方案主键" width="100"/>
				<bc:column field="gdPid" title="股东主键" width="100"/>
				<bc:column field="gdType" title="股东类型" width="100"/>
				<bc:column field="shareholdCode" title="股东账号" width="100"/>
				<bc:column field="gdDj" title="账户冻结" width="100"/>
				<bc:column field="tradeAccount" title="交易账户账号" width="100"/>
				<bc:column field="holeShareAmount" title="持股数量" width="100"/>
				<bc:column field="shareholdingProportion" title="持股占比" width="100"/>
				<bc:column field="frozenShareAmount" title="已冻结股数" width="100"/>
				<bc:column field="pledgedShareAmount" title="已质押股数" width="100"/>
				<bc:column field="cashBonus" title="奖金分红" width="100"/>
				<bc:column field="bonusIssue" title="派送红利" width="100"/>
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