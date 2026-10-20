<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>流程模板</title>
<x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="流程模板" subTitle="请输入模板查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="模板编码" col="5" formControl="true"></bc:label>
						<bf:text name="__LIKES_code" col="7" placeholder="模板编码"></bf:text>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="procTemplateTable" idField="code" url="${base}/workflow/procTemplate">
				<bc:toolbar id="addTemplate" text="新增" type="button" handler="addTempHandler"/>
				<bc:toolbar id="editTemplate" text="编辑" type="button" handler="editTempHandler"/>
				<bc:toolbar id="uploadTemplate" text="上传" type="button" handler="uploadTempHandler"/>
				<bc:toolbar id="deployTemplate" text="部署" type="button" handler="deployTempHandler"/>
				<bc:toolbar id="delTemplate" text="卸载" type="button" handler="delTempHandler"/>
				<bc:toolbar id="enableTemplate" text="启用" type="button" handler="enableTempHandler"/>
				<bc:toolbar id="disableTemplate" text="停用" type="button" handler="disableTempHandler"/>
				<bc:column field="code" title="模板编码" width="100"/>
				<bc:column field="name" title="模板名称" width="100"/>
				<bc:column field="status" title="状态" width="100" formatter="fixedDict:Status"/>
				<bc:column field="remark" title="备注" width="100"/>
				<be:event type="onClickRow">onClickRow</be:event>
				<be:event type="onCheck">onClickRow</be:event>
			</bc:table>
		</bc:ibox>
		<bc:ibox title="模板图像" col="12" collapsible="true" >
			<div style="overflow:auto;">
				<img id="img" alt="流程图片"/> 
			</div>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
/* 选中模板，展示模板内容 */
function onClickRow(row){
	/*图片路径*/
	var imgUrl = '${base}/workflow/diagram/image/' + row['code'];
	/*加载流程图片  */
	document.getElementById("img").src=imgUrl;
	
	if(row['status'] == 'ENABLED'){
		$('#procTemplateTable_tb_enableTemplate').attr('disabled', true);
		$('#procTemplateTable_tb_disableTemplate').attr('disabled', false);
		$('#procTemplateTable_tb_delTemplate').attr('disabled', true);
		$('#procTemplateTable_tb_editTemplate').attr('disabled', true);
		$('#procTemplateTable_tb_deployTemplate').attr('disabled', true);
	}
	else if(row['status'] == 'DISABLED')
	{
		$('#procTemplateTable_tb_enableTemplate').attr('disabled', false);
		$('#procTemplateTable_tb_disableTemplate').attr('disabled', true);
		$('#procTemplateTable_tb_delTemplate').attr('disabled', true);
		$('#procTemplateTable_tb_editTemplate').attr('disabled', true);
		$('#procTemplateTable_tb_deployTemplate').attr('disabled', true);
	} 
	else
	{
		$('#procTemplateTable_tb_enableTemplate').attr('disabled', true);
		$('#procTemplateTable_tb_disableTemplate').attr('disabled', true);
		$('#procTemplateTable_tb_delTemplate').attr('disabled', false);
		$('#procTemplateTable_tb_editTemplate').attr('disabled', false);
		$('#procTemplateTable_tb_deployTemplate').attr('disabled', false);
	}
}

/**
 * 新增流程模板
 */
function addTempHandler(){
	window.location = '${base}/workflow/procTemplate/add';
}

/**
 * 编辑流程模板
 */
function editTempHandler(){
	var tables =  $('#procTemplateTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		window.location = '${base}/workflow/model/edit/' + tables[0].modelId;
	}
}

/**
 * 部署流程模板
 */
function deployTempHandler(){
	var tables =  $('#procTemplateTable').jTable('getCheckData');
	if(tables.length > 0){
		$.messager.confirm('提示', '你确定要部署该流程吗？', function(r){
			
			if(r)
			{
				$.mask.post('${base}/workflow/procTemplate/deploy/' + tables[0].code, function(data){
					if(data.success)
					{
						$('#procTemplateTable').jTable('clearCheckData');
						$('#procTemplateTable').jTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要卸载的模板。');
	}
}

/**
 * 上传流程模板
 */
function uploadTempHandler(){
	
	window.location = '${base}/workflow/procTemplate/upload';
}

/**
 * 移除流程模板
 */
function delTempHandler(){
	var tables =  $('#procTemplateTable').jTable('getCheckData');
	if(tables.length == 1){
		$.messager.confirm('提示', '确定要卸载该模板吗？', function(r){
			
			if(r)
			{
				var data = {code : tables[0].code};
				$.mask.post('${base}/workflow/procTemplate/undeploy', data, function(data){
					if(data.success)
					{
						$('#procTemplateTable').jTable('clearCheckData');
						$('#procTemplateTable').jTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要卸载的模板。');
	}
}


function enableTempHandler(){
	var tables =  $('#procTemplateTable').jTable('getCheckData');
	if(tables.length == 1){
		$.messager.confirm('提示', '确定要启用该模板吗？', function(r){
			
			if(r)
			{
				var data = {code : tables[0].code};
				$.mask.post('${base}/workflow/procTemplate/enable', data, function(data){
					if(data.success)
					{
						$('#procTemplateTable').jTable('clearCheckData');
						$('#procTemplateTable').jTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要启用的模板。');
	}
}


function disableTempHandler(){
	var tables =  $('#procTemplateTable').jTable('getCheckData');
	if(tables.length == 1){
		$.messager.confirm('提示', '确定要停用该模板吗？', function(r){
			
			if(r)
			{
				var data = {code : tables[0].code};
				$.mask.post('${base}/workflow/procTemplate/disable', data, function(data){
					if(data.success)
					{
						$('#procTemplateTable').jTable('clearCheckData');
						$('#procTemplateTable').jTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要停用的模板。');
	}
}

</script>
</body>
</html>