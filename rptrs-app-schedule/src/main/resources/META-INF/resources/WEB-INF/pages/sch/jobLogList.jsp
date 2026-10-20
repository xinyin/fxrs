<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>任务日志</title>

</head>
<body class="gray-bg">
	<div class="row animated fadeInRight">
		<bc:ibox title="角色类型" subTitle="请输入角色类型查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row>
					<bc:col col="4">
						<bc:label text="id" col="4" formControl="true"/>
						<bf:text name="__LIKES_id" col="8" placeholder="id"/>
					</bc:col>
					<bc:col col="4">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="jobLogTable" idField="id" url="${base}/sch/jobLog" detailView="true">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="id" title="id" width="100"/>
				<bc:column field="jobCode" title="任务编码" width="100"/>
				<bc:column field="jobInstId" title="实例ID" width="100"/>
				<bc:column field="logTime" title="记录时间" width="100"/>
				<bc:column field="content" title="日志内容" width="100"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
				<bc:operator id="view" icon="file-o" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>