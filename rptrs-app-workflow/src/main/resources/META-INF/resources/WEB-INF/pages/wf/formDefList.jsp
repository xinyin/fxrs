<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>表单定义表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="表单定义" subTitle="请输入表单定义查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="表单编码" col="5" formControl="true"></bc:label>
						<bf:text name="__LIKES_code" col="7" placeholder="表单编码"></bf:text>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="表单名称" col="5" formControl="true"></bc:label>
						<bf:text name="__LIKES_name" col="7" placeholder="表单名称"></bf:text>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="formDefTable" idField="code" url="${base}/workflow/formDef" detailView="false" queryForm="queryForm">
				<bc:toolbar id="add" text="新增" type="button" />
				<bc:toolbar id="design" text="设计" type="button"  handler="designHandler"/>
				<bc:toolbar id="view" text="查看" type="button"  handler="viewHandler"/>
				<bc:toolbar id="preview" text="预览" type="button"  handler="previewHandler"/>
				<bc:toolbar id="enable" text="启用" type="button"  handler="enabledHandler"/>
				<bc:toolbar id="disable" text="停用" type="button"  handler="disableHandler"/>
				<bc:column field="code" title="表单编码" width="100"/>
				<bc:column field="name" title="表单名称" width="100"/>
				<bc:column field="formType" title="表单类型" width="100" formatter="fixedDict:PropFormType"/>
				<bc:column field="status" title="状态" width="100" formatter="fixedDict:Status"/>
				<bc:column field="remark" title="备注" width="200"/>
				<bc:operator id="edit" icon="edit" title="编辑" visible="editVisible1"/>
				<bc:operator id="remove" icon="trash" title="移除" visible="removeVisible1"/>
				<be:event type="onClickRow">onClickRow</be:event>
				<be:event type="onCheck">onClickRow</be:event>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
$(document).ready(function () { 

});
function onClickRow(row){
	if(row['status'] == 'DRAFT'){
		$('#formDefTable_tb_design').attr('disabled', false);
		$('#formDefTable_tb_enable').attr('disabled', false);
		$('#formDefTable_tb_disable').attr('disabled', true);
	}
	else if((row['status'] == 'ENABLED'))
	{
		$('#formDefTable_tb_design').attr('disabled', true);
		$('#formDefTable_tb_enable').attr('disabled', true);
		$('#formDefTable_tb_disable').attr('disabled', false);
	} 
	else
	{
		$('#formDefTable_tb_design').attr('disabled', true);
		$('#formDefTable_tb_enable').attr('disabled', false);
		$('#formDefTable_tb_disable').attr('disabled', true);
	} 
	
	if(row['formType'] == 'CUSTOM'){
		$('#formDefTable_tb_preview').attr('disabled', true);
	}
	else 
	{
		debugger;
		
		$.mask.post('${base}/workflow/formProp/existsFormProp', {formCode: row['code']}, function(data){
			if(data.exists)
			{
				$('#formDefTable_tb_preview').attr('disabled', false);
			}
			else
			{
				$('#formDefTable_tb_preview').attr('disabled', true);
			}
		});
	} 
}


//表单设计
function designHandler(){
	var tables =  $('#formDefTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		window.location = '${base}/workflow/formProp/list?formCode=' + tables[0].code;		
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要设计的记录。', 'error');
	}
}

//表单查看
function viewHandler(){
	var tables =  $('#formDefTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		window.location = '${base}/workflow/formProp/designView?formCode=' + tables[0].code;		
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要设计的记录。', 'error');
	}
}

//预览
function previewHandler(){
	var tables =  $('#formDefTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		window.location = '${base}/workflow/formDef/preview?formCode=' + tables[0].code;		
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要预览的记录。', 'error');
	}
}

//启用
function enabledHandler(){
	var tables =  $('#formDefTable').jTable('getCheckData');
	if(tables.length > 0){
		$.messager.confirm('提示', '确定要启动该记录吗？', function(r){
			if(r)
			{
				$.mask.post('${base}/workflow/formDef/enable/' + tables[0].code, function(data){
					if(data.success)
					{
						$('#formDefTable').jTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要启动的记录。', 'error');
	}
}

//停用
function disableHandler(){
	var tables =  $('#formDefTable').jTable('getCheckData');
	if(tables.length > 0){
		$.messager.confirm('提示', '确定要停用该记录吗？', function(r){
			if(r)
			{
				$.mask.post('${base}/workflow/formDef/disable/' + tables[0].code, function(data){
					if(data.success)
					{
						$('#formDefTable').jTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要停用的记录。', 'error');
	}
}

//编辑先验证
function editVisible1(row){
		if(row['status'] == 'ENABLED' || row['status'] == 'DISABLED')
		{
			return false;
		}
	return true;
}

//删除时先验证
function removeVisible1(row){
		if(row['status'] == 'ENABLED' || row['status'] == 'DISABLED')
		{
			return false;
		}
	return true;
}
</script>
</body>
</html>