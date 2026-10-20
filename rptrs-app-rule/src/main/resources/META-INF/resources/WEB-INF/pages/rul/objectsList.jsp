<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>对象库</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="对象库" subTitle="请输入对象库查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="对象名称英文" col="5" formControl="true"/>
						<bf:text name="__LIKES_code" col="7" placeholder="对象名称英文"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="对象名称中文" col="5" formControl="true"/>
						<bf:text name="__LIKES_name" col="7" placeholder="对象名称中文"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="objectsTable" idField="id" url="${base}/rul/objects">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="code" title="对象名称英文" width="100"/>
				<bc:column field="name" title="对象名称中文" width="100"/>
				<bc:column field="dataType" title="数据类型" width="100" formatter="fixedDict:RuleDataType"/>
<%--
				<bc:column field="objType" title="对象类型" width="100" formatter="fixedDict:TableType"/>
--%>
				<bc:column field="inOut" title="传递方向" width="100" formatter="fixedDict:InOut"/>
				<bc:column field="group" title="分组" width="100"/>
				<bc:column field="remark" title="描述" width="100"/>
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