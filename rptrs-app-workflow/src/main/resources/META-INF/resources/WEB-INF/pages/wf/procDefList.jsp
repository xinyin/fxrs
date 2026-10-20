<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>业务流程管理</title>
<x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="业务流程定义表" subTitle="请输入业务流程定义表查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup>
						<bc:col col="6">
							<bc:label text="流程编码" col="4" formControl="true"></bc:label>
							<bf:text name="__LIKES_code" col="6" placeholder="流程编码"></bf:text>
						</bc:col>
						<bc:col col="6">
							<bc:label text="流程名称" col="4" formControl="true"></bc:label>
							<bf:text name="__LIKES_name" col="6" placeholder="流程名称"></bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup >
						<bc:col col="6">
							<bc:label text="模块名称" col="4" formControl="true"></bc:label>
							<bf:text name="__LIKES_moduleName" col="6" placeholder="模块名称"></bf:text>
						</bc:col>
						<bc:col col="6">
							<bc:label text="模板名称" col="4" formControl="true"></bc:label>
							<bf:text name="__LIKES_procTemplateName" col="6" placeholder="模板名称"></bf:text>
						</bc:col>
					</bf:formGroup>
					<bc:col col="12" colOffset="5" >
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="procDefTable" idField="id" url="${base}/workflow/procDef">
				<bc:toolbar id="add" text="新增" type="button" />
				<bc:toolbar id="design" text="设计" type="button"  handler="design"/>
				<bc:toolbar id="copy" text="复制" type="button"  handler="copy"/>
				<bc:toolbar id="enable" text="启用" type="button" handler="enable"/>
				<bc:toolbar id="disable" text="停用" type="button" handler="disable"/>
				<bc:toolbar id="update" text="升级" type="button" handler="update"/>
				<bc:column field="code" title="流程编码" width="100"/>
				<bc:column field="name" title="流程名称" width="100"/>
				<bc:column field="moduleName" title="模块名称" width="100"/>
				<bc:column field="procTemplateName" title="模板名称" width="100"/>
				<bc:column field="formName" title="表单名称" width="100"/>
				<bc:column field="procVersion" title="版本号" width="100"/>
				<bc:column field="procVersionStatus" title="版本状态" width="100" formatter="fixedDict:VersionStatus"/>
				<bc:column field="status" title="状态" width="100" formatter="fixedDict:Status"/>
				<bc:operator id="editProcDef" icon="edit" title="编辑" handler="editHandler" visible="editVisible"/>
				<bc:operator id="remove" icon="trash" title="移除" visible="removeVisible"/>
				<bc:operator id="viewProcDef" icon="file-o" title="查看" handler="viewHandler"/>
				<be:event type="onClickRow">onClickRow</be:event>
				<be:event type="onCheck">onClickRow</be:event>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

function onClickRow(row){
	if(row['status'] == 'DRAFT'){
		$('#procDefTable_tb_design').attr('disabled',false);
		$('#procDefTable_tb_enable').attr('disabled',false);
		$('#procDefTable_tb_disable').attr('disabled',true);
		$('#procDefTable_tb_copy').attr('disabled',true);
		$('#procDefTable_tb_update').attr('disabled',true);
	}
	else if((row['status'] == 'ENABLED'))
	{
		$('#procDefTable_tb_design').attr('disabled',true);
		$('#procDefTable_tb_enable').attr('disabled',true);
		$('#procDefTable_tb_disable').attr('disabled',false);
		$('#procDefTable_tb_copy').attr('disabled',false);
		$('#procDefTable_tb_update').attr('disabled',false);
	} 
	else
	{
		$('#procDefTable_tb_design').attr('disabled',true);
		$('#procDefTable_tb_enable').attr('disabled',false);
		$('#procDefTable_tb_disable').attr('disabled',true);
		$('#procDefTable_tb_copy').attr('disabled',true);
		$('#procDefTable_tb_update').attr('disabled',true);
	} 
}
/* 编辑 */
function editHandler(e, field, row, index){
	console.log(row.code);
	window.location = '${base}/workflow/procDef/edit?code=' + row.code + '&procVersion=' + row.procVersion;
}

/* 查看 */
function viewHandler(e, field, row, index){
	window.location = '${base}/workflow/procDef/view?code=' + row.code + '&procVersion=' + row.procVersion;
}

/* 设计 */
function design(){
	
	var row = $('#procDefTable').bootstrapTable('getSelections');
	if(row.length != 0)
	{
		window.location = '${base}/workflow/procDef/designPage?code=' + row[0].code + '&procVersion=' + row[0].procVersion;
	}
	else
	{
		$.messager.toastr('提示','请先选择一条业务流程数据');
	}
}

/* 复制 */
function copy(){
	
	var row = $('#procDefTable').bootstrapTable('getSelections');
	if(row.length == 1)
	{
		$.messager.modal({
			winId: 'winProcDefCopy',
			width: 800,
			height: 200,
			title: '复制业务流程',
			url: '${base}/workflow/procDef/copyDlg',
			args:{code: row[0].code, version: row[0].procVersion},
			callback: result,
			onClose: function(){
				$('#procDefTable').bootstrapTable('refresh');
			}
		});
	}
	else
	{
		$.messager.toastr('提示','请先选择一条业务流程数据');
	}
}

/* 复制方法回调函数 */
function result(){
	$('#procDefTable').bootstrapTable('refresh');
}

/* 启用 */
function enable(){
	var tables =  $('#procDefTable').jTable('getSelections');
	if(tables.length == 1){
		$.messager.confirm('提示', '确定要启动该流程吗？', function(r){
			if(r)
			{
				var obj = {
						code: tables[0].code,
						procVersion: tables[0].procVersion
				}
				$.mask.post('${base}/workflow/procDef/enable', obj, function(data){
					if(data.success)
					{
						$.messager.alert('提示', data.msg, 'info');
						$('#procDefTable').bootstrapTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要启动的流程。');
	}
}

/* 停用 */
function disable(){
	var tables =  $('#procDefTable').jTable('getSelections');
	if(tables.length == 1){
		$.messager.confirm('提示', '确定要启动该流程吗？', function(r){
			if(r)
			{
				var obj = {
						code: tables[0].code,
						procVersion: tables[0].procVersion
				}
				$.mask.post('${base}/workflow/procDef/disable', obj, function(data){
					if(data.success)
					{
						$('#procDefTable').bootstrapTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要启动的流程。');
	}
}

/* 升级启用的业务流程 */
function update(){
	var tables =  $('#procDefTable').jTable('getCheckData');
	if(tables.length == 1){
		$.messager.confirm('提示', '确定要升级该流程吗？', function(r){
			if(r)
			{
				var obj = {
						code: tables[0].code,
						version: tables[0].procVersion
				}
				$.mask.post('${base}/workflow/procDef/newProcDef', obj, function(data){
					if(data.success)
					{
						$.messager.alert('提示', data.msg, 'info');
						$('#procDefTable').bootstrapTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要升级的流程。');
	}
	
}

/* 编辑时验证 */
function editVisible(row){
	if(row['status'] == 'ENABLED' || row['status'] == 'DISABLED')
	{
		return false;
	}
	return true;
}

/* 删除时先验证 */
function removeVisible(row){
	if(row['status'] == 'ENABLED')
	{
		return false;
	}
	return true;
}
</script>
</body>
</html>