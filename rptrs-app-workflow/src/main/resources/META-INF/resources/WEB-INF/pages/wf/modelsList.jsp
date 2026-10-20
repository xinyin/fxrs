<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>ReModel</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="流程库" subTitle="请输入流程查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="id" col="5" formControl="true"></bc:label>
						<bf:text name="__LIKES_id" col="7" placeholder="id"></bf:text>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="reModelTable" idField="id" url="${base}/workflow/model" detailView="false">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="name" title="名称" width="100"/>
				<bc:column field="key" title="键" width="100"/>
				<bc:column field="version" title="版本" width="20"/>
				<bc:column field="createTimeString" title="创建时间" width="50"/>
				<bc:column field="lastUpdateTimeString" title="更新时间" width="50"/>
				<bc:column field="metaInfo" title="元数据" width="100"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="deploy" icon="file-o" title="发布"/>
				<bc:operator id="export" icon="file-o" title="导出"/>
				<bc:operator id="remove" icon="trash" title="删除"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>