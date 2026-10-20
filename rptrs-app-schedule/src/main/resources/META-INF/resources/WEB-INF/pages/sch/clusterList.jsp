<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>集群管理</title>

</head>
<body class="gray-bg">
	<div class="row animated fadeInRight">
		<bc:ibox title="集群管理" subTitle="请输入集群管理查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row>
					<bc:col col="4">
						<bc:label text="集群编码" col="4" formControl="true"/>
						<bf:text name="__LIKES_code" col="8" placeholder="集群编码"/>
					</bc:col>
					<bc:col col="4">
						<bc:label text="集群名称" col="4" formControl="true"/>
						<bf:text name="__LIKES_name" col="8" placeholder="集群名称"/>
					</bc:col>
					<bc:col col="4">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="clusterTable" idField="code" url="${base}/sch/cluster" detailView="false">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="code" title="集群编码" width="100"/>
				<bc:column field="name" title="集群名称" width="100"/>
				<bc:column field="remark" title="备注" width="100"/>
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