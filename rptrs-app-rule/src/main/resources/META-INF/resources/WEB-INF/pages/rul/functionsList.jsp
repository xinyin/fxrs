<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>自定义函数库</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="自定义函数库" subTitle="请输入自定义函数库查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="编码" col="5" formControl="true"/>
						<bf:text name="__LIKES_code" col="7" placeholder="编码"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_name" col="7" placeholder="名称"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="primary" text="查询" icon="search"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="functionsTable" idField="id" url="${base}/rul/functions">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="code" title="编码" width="100"/>
				<bc:column field="name" title="名称" width="100"/>
				<bc:column field="remark" title="描述" width="150"/>
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