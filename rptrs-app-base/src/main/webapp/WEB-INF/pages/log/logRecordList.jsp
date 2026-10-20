<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>LogRecord</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="LogRecord" subTitle="请输入LogRecord查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="操作人姓名" col="5" formControl="true"/>
						<bf:text name="__LIKES_createByName" col="7" placeholder="操作人姓名"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="操作模块" col="5" formControl="true"/>
						<bf:text name="__LIKES_module" col="7" placeholder="操作模块"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="操作功能" col="5" formControl="true"/>
						<bf:text name="__LIKES_logDescription" col="7" placeholder="操作功能"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="开始时间" col="5" formControl="true"/>
						<bf:datebox id="startDate" name="__GES_createTime" col="7" format="date"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="截止时间" col="5" formControl="true"/>
						<bf:datebox id="endDate" name="__LTS_createTime" col="7" format="date"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="logRecordTable" idField="pid" url="${base}/log/logRecord" queryUrl="/query">
				<bc:column field="pid" title="pid" width="100" visible="false"/>
				<bc:column field="createById" title="操作人id" width="100" visible="false"/>
				<bc:column field="createByName" title="操作人姓名" width="100"/>
				<bc:column field="module" title="操作模块" width="100"/>
				<bc:column field="logDescription" title="操作功能" width="100"/>
				<bc:column field="loginIp" title="操作ip地址" width="100"/>
				<bc:column field="logContent" title="详细内容" width="100" visible="false"/>
				<bc:column field="createTimeString" title="操作时间" width="100"/>
				<bc:operator id="view" icon="search" title="查看"/>
				<%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
				<%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>