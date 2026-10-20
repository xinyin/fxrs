<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>流程实例表</title>
	<style>
		.form-group {
			margin: 0.3% 0 !important;
		}

		.form-group .col-xs-12 {
			margin: 0.3% 0 !important;
		}

		.form-group .col-xs-12 label {
			display: inline-block;
			float: left;
			margin: 0 0 0 3%;
			padding: 0 2% 0 0;
		}

		.form-group .col-xs-12 label {
			display: inline-block;
			float: left;
			margin: 0 0 0 3%;
			padding: 0 2% 0 0;
			width: 10%;
		}

		.has-success .form-control, .boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}

		.gray-bg {
			background: white !important;
		}
		.form-group .input-group.date,
		.form-group .select2{
			width: 100% !important;
		}
		.boxse .form-group .col-xs-4 {
			margin: 0.3% 0 !important;
		}
		.boxse .has-success .form-control, .boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}
	</style>
<x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bf:hidden name="__INS_status"  value="10,20" />
				<bf:hidden name="__EQS_starterCode"  value="${userCode}" />
				<bf:hidden name="passToken"  value="${passToken}" />
				<bc:row cls="m-b">
					<bf:formGroup hrp="bottom">
					<bf:formGroup col="4">
						<bc:label text="业务流程名称" col="5" formControl="true"></bc:label>
						<bf:text name="__LIKES_procName" col="7" placeholder="业务流程名称"></bf:text>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="模块名称" col="5" formControl="true"></bc:label>
						<bf:text name="__LIKES_moduleName" col="7" placeholder="模块名称"></bf:text>
					</bf:formGroup>
					</bf:formGroup>
<%--					<bc:row cls="m-b">--%>
<%--						<div class="col-sm-12 col-sm-offset-5">--%>
<%--							<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>--%>
<%--							<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>--%>
<%--						</div>--%>

<%--					</bc:row>--%>
			<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
			</div>
				</bc:row>
			</bf:form>
			<bc:table id="procInstTable" idField="id" url="${base}/workflow/process" queryUrl="queryData">
				<%--<bc:toolbar id="editBiz" text="编辑业务数据" type="button" handler="editBizHandler"/>--%>
				<bc:toolbar id="viewBiz" text="查看业务数据" type="button" handler="viewBizHandler"/>
<%--				<bc:toolbar id="submitInst" text="提交" type="button" handler="submitInstHandler"/>--%>
<%--				<bc:toolbar id="delBiz" text="删除" type="button" handler="delBizHandler"/>--%>
				<bc:column field="procCode" title="业务流程编码" width="100"/>
				<bc:column field="procName" title="业务流程名称" width="100"/>
				<bc:column field="moduleName" title="模块名称" width="100"/>
				<bc:column field="templateName" title="模板名称" width="100"/>
				<bc:column field="formName" title="表单名称" width="100"/>
				<bc:column field="starterName" title="发起人" width="100"/>
				<bc:column field="startTimeString" title="开始时间" width="100"/>
				<bc:column field="status" title="状态" width="100" formatter="fixedDict:ProcInstStatus"/>
				<bc:column field="bizNameString" title="相关信息" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
function editBizHandler(){
	var tables =  $('#procInstTable').jTable('getCheckData');
	if(tables.length == 1){
		window.location = '${base}/workflow/process/editDraftProcInst?passToken=${passToken}&procInstId=' + tables[0].id + '&status=' + tables[0].status; 
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要编辑的数据。');
	}
}

function viewBizHandler(){
	var tables =  $('#procInstTable').jTable('getCheckData');
	if(tables.length == 1){
		window.location = '${base}/workflow/process/view?passToken=${passToken}&procInstId=' + tables[0].id + '&option=process/draftList'; 
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要查看的数据。');
	}
}

function submitInstHandler(){
	var tables =  $('#procInstTable').jTable('getCheckData');
	if(tables.length == 1){
		$.messager.confirm('提示', '确定要提交这条数据吗？', function(r){
			
			if(r)
			{
				var data = {procInstId : tables[0].id, passToken: '${passToken}'};
				$.mask.post('${base}/workflow/process/submit', data, function(data){
					if(data.success)
					{
						$('#procInstTable').jTable('clearCheckData');
						$('#procInstTable').jTable('refresh');
						$.messager.alert('提示', data.msg, 'info')
					}
					else
					{
						$.messager.alert('错误', data.msg, 'error');
					}
				});
			}
		});
		
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要提交的数据。');
	}
}

function delBizHandler(){
	var tables =  $('#procInstTable').jTable('getCheckData');
	if(tables.length == 1){
		$.messager.confirm('提示', '确定要删除这条数据吗？', function(r){
			
			if(r)
			{
				var data = {procInstId : tables[0].id};
				$.mask.post('${base}/workflow/process/delete', data, function(data){
					if(data.success)
					{
						$('#procInstTable').jTable('clearCheckData');
						$('#procInstTable').jTable('refresh');
						$.messager.alert('提示', data.msg, 'info')
					}
					else
					{
						$.messager.alert('错误', data.msg, 'error');
					}
				});
			}
		});
		
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要删除的数据。');
	}
}
</script>
</body>
</html>