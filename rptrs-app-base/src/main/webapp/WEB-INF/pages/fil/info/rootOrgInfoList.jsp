<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>备案基础信息表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="备案基础信息表" subTitle="请输入备案基础信息表查询信息" col="12" collapsible="true">
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
			<bc:table id="rootOrgInfoTable" idField="pid" url="${base}/fil/rootOrgInfo">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="主键" width="100"/>
				<bc:column field="filNum" title="备案编号" width="100"/>
				<bc:column field="relOrgId" title="关联机构主键" width="100"/>
				<bc:column field="totalShareAmount" title="总股数" width="100"/>
				<bc:column field="netCaptial" title="资本净额" width="100"/>
				<bc:column field="totalCapital" title="总资产" width="100"/>
				<bc:column field="frozenShareAmount" title="冻结股数(股)" width="100"/>
				<bc:column field="pledgedShareAmount" title="质押股数(股)" width="100"/>
				<bc:column field="endLastQuarter" title="上季末总资产" width="100"/>
				<bc:column field="endLastYear" title="上年末总资产" width="100"/>
				<bc:column field="netCaptialLastYear" title="上年末净资产" width="100"/>
				<bc:column field="netCaptialLastQuarter" title="上季末净资产" width="100"/>
				<bc:column field="quarterCompreSolvencyRate" title="季度综合偿付能力充足率" width="100"/>
				<bc:column field="years" title="年度" width="100"/>
				<bc:column field="quarter" title="季度" width="100"/>
				<bc:column field="approvalStatus" title="审批状态" width="100"/>
				<bc:operator id="view" icon="eye" title="查看"/>
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