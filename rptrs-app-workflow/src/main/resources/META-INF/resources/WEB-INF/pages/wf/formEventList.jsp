<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>表单事件表</title>
<x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="角色类型" subTitle="请输入角色类型查询信息" col="12" collapsible="true">
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
			<bc:table id="formEventTable" idField="id" url="${base}/wf/formEvent" detailView="true">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="id" title="id" width="100"/>
				<bc:column field="formCode" title="formCode" width="100"/>
				<bc:column field="propCode" title="属性编码" width="100"/>
				<bc:column field="type" title="FORM - 表单事件             PROP - 属性事件" width="100"/>
				<bc:column field="name" title="string, long, boolean, date" width="100"/>
				<bc:column field="script" title="TEXT - 单行文本             MTEXT - 多行文本             NTEXT - 数字输入             CHECK - 复选             RADIO - 单选             SELECT - 下拉选择             DATE - 日期选择             UNIT - 机构选择             STAFF - 人员选择             ATTACH - 附件" width="100"/>
				<bc:column field="remark" title="remark" width="100"/>
				<bc:column field="tenantId" title="租户" width="100"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
				<bc:operator id="view" icon="file-o" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>