<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>交易规则文件</title>
</head>
<body class="gray-bg">
	<div class="row animated fadeInRight">
		<bc:ibox title="交易规则文件" subTitle="请输入交易规则文件查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row>
					<bc:col col="4">
						<bc:label text="交易ID" col="4" formControl="true"/>
						<bf:text name="__LIKES_id" col="8" placeholder="交易ID"/>
					</bc:col>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="tradeDrlTable" idField="id" url="${base}/rul/tradeDrl">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="id" title="交易ID" width="100"/>
				<bc:column field="content" title="文件内容" width="100"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
				<bc:operator id="view" icon="search" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>