<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>关联交易季报-关联交易明细表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="关联交易季报-关联交易明细表" subTitle="请输入关联交易季报-关联交易明细表查询信息" col="12" collapsible="true">
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
			<bc:table id="quarterlyDetailTable" idField="pid" url="${base}/bus/quarterlyDetail">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="自增主键" width="100"/>
				<bc:column field="reportId" title="关联交易季报主键" width="100"/>
				<bc:column field="transNum" title="交易编码" width="100"/>
				<bc:column field="transTime" title="交易时间" width="100"/>
				<bc:column field="transType" title="交易类型" width="100"/>
				<bc:column field="transFund" title="交易金额" width="100"/>
				<bc:column field="transTarget" title="交易标的" width="100"/>
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