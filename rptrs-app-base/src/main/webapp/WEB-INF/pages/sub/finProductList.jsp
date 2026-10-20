<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>FinProduct</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="FinProduct" subTitle="请输入FinProduct查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="pid" col="5" formControl="true"/>
						<bf:text name="__LIKES_pid" col="7" placeholder="pid"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="finProductTable" idField="pid" url="${base}/sub/finProduct">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="pid" width="100"/>
				<bc:column field="productName" title="产品全称" width="100"/>
				<bc:column field="productRegisterNo" title="产品代码" width="100"/>
				<bc:column field="productType" title="产品分类" width="100"/>
				<bc:column field="productIssueNo" title="产品批准/注册/备案号" width="100"/>
				<bc:column field="registerOrg" title="产品登记机构" width="100"/>
				<bc:column field="productOwner" title="产品管理人全称" width="100"/>
				<bc:column field="productOwnerUniscCode" title="管理人统一社会信用代码" width="100"/>
				<bc:column field="productCustodian" title="产品托管人全称" width="100"/>
				<bc:column field="productCustodianUniscCode" title="托管人统一社会信用代码" width="100"/>
				<bc:column field="status" title="前置流程状态" width="100"/>
				<bc:column field="statusComment" title="前置流程状态说明" width="100"/>
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