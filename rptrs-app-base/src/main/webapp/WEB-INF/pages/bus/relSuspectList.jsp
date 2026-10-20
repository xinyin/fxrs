<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>RelSuspect</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="RelSuspect" subTitle="请输入RelSuspect查询信息" col="12" collapsible="true">
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
			<bc:table id="relSuspectTable" idField="pid" url="${base}/bus/relSuspect">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="自增主键" width="100"/>
				<bc:column field="toPid" title="关联关系pid" width="100"/>
				<bc:column field="fromPid" title="源主体pid" width="100"/>
				<bc:column field="relCategory" title="关系类型" width="100"/>
				<bc:column field="fromType" title="源主体类型  PSN|个人 ORG|机构" width="100"/>
				<bc:column field="toType" title="关联关系主体类型   PSN|个人 ORG|机构" width="100"/>
				<bc:column field="fromMemo" title="源主体显示名称" width="100"/>
				<bc:column field="toMemo" title="关联关系主体显示名称" width="100"/>
				<bc:column field="relation" title="亲属关系" width="100"/>
				<bc:column field="shareholdingRatio" title="持股比例" width="100"/>
				<bc:column field="toPidShareholders" title="是否关联关系主体股东    0-否 1-是" width="100"/>
				<bc:column field="isShow" title="是否显示" width="100"/>
				<bc:column field="level" title="关系层级" width="100"/>
				<bc:column field="liftReason" title="解除原因" width="100"/>
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