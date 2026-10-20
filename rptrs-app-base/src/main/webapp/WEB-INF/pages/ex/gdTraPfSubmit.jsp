<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<%--	<br:css pack="list"/>--%>
<title>GdTraPf</title>
	<script>
		function getIdSelections() {
			return $.map($('#gdTraPfTable').bootstrapTable('getSelections'), function (row) {
				return row.pid;
			});
		}

		function viewDetailsHandler(){
			var pid = getIdSelections();
			window.location = '${base}/bus/gdTraPf/infoSubmit/' + pid;
		}
	</script>
</head>
<body class="gray-bg" style="background: white">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:formGroup hr="dashed" hrp="bottom">
				</bf:formGroup>
			</bf:form>
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
<%--					<bf:formGroup col="4">--%>
<%--						<bc:label text="派发年份" col="5" formControl="true"/>--%>
<%--						<bf:text name="__LIKES_year" col="7" placeholder="派发年份"/>--%>
<%--					</bf:formGroup>--%>
<%--					<bc:col col="3" colOffset="1">--%>
<%--						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>--%>
<%--						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>--%>
<%--					</bc:col>--%>
				</bc:row>
			</bf:form>
			<bc:table id="gdTraPfTable" idField="pid" url="${base}/bus/gdTraPf" queryUrl="getSubmit/${gdTraPf.pid}">
<%--				<bc:toolbar id="add" text="新增" type="button"/>--%>
<%--				<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>--%>
<%--				<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>--%>
<%--				<bc:toolbar id="sub" text="提交审批" type="button" handler="subHandler"/>--%>
<%--				<bc:toolbar id="export" text="导出" type="button" handler="exportHandler"/>--%>
				<bc:column field="pid" title="主键" width="100" visible="false"/>
				<bc:column field="name" title="派发方案名称" width="100"/>
				<bc:column field="year" title="派发年份" width="100"/>
				<bc:column field="createTime" title="文件生成时间" width="100"/>
				<bc:column field="totalShare" title="当前总股本" width="100"/>
				<bc:column field="allocationAmount" title="股利分配额" width="100"/>
				<bc:column field="allocationProportion" title="股利分配比例" width="100"/>
				<bc:column field="cashBonus" title="现金分红" width="100"/>
				<bc:column field="bonusIssue" title="派送红股" width="100"/>
				<bc:column field="status" title="审批状态" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus" visible="false"/>
				<bc:column field="route" title="路径" width="100" visible="false"/>
				<bc:operator id="viewDetails" icon="eye" title="查看" handler="viewDetailsHandler"/>
<%--				<bc:operator id="edit" icon="edit" title="编辑"/>--%>
<%--				<bc:operator id="remove" icon="trash" title="移除"/>--%>
			</bc:table>
<%--			<bc:buttonGroup cls="btn-group">--%>
<%--				<bc:button type="success" outline="true" text="确认提交" icon="save" click="sub()">--%>
<%--					<be:event>--%>

<%--					</be:event>--%>
<%--				</bc:button>--%>
<%--				<bc:button type="info" outline="true" text="返回">--%>
<%--					<be:event>--%>
<%--						window.location = '${base}/bus/gdTraPf/list';--%>
<%--					</be:event>--%>
<%--				</bc:button>--%>
<%--			</bc:buttonGroup>--%>
		</bc:ibox>
<%--<br:js pack="list"/>--%>
<br:js pack="form"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">
	$(document).ready(function () {

	});
</script>
</body>
</html>