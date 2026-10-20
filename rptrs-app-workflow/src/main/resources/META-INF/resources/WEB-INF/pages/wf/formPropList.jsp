<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>表单设计</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="表单信息" col="12" collapsible="true">
			<bc:row cls="m-b">
				<bf:formGroup col="6">
					<bc:label text="表单编码:" col="4" formControl="true"></bc:label>
					<bc:label text="${formDef.code }" col="8" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup col="6">
					<bc:label text="表单名称:" col="4" formControl="true"></bc:label>
					<bc:label text="${formDef.name }" col="8" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup col="12">
					<bc:button  text="返回">
							<be:event>
								window.location = '${base}/workflow/formDef/list';
							</be:event>
						</bc:button>
				</bf:formGroup>
			</bc:row>
		</bc:ibox>
		<bc:ibox id="box2" title="属性信息" col="12" collapsible="true">
			<bc:col col="12">
				<bc:table id="formPropTable" idField="id" url="${base}/workflow/formProp" queryUrl="/queryList?formCode=${formDef.code }" detailView="false" pagination="false">
					<bc:toolbar id="add1" text="新增" type="button" handler="addHandler"/>
					<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
					<bc:toolbar id="view" text="查看" type="button" handler="viewHandler"/>
					<bc:toolbar id="delete" text="删除" type="button" handler="deleteHandler"/>
					<bc:column field="propCode" title="属性编码" width="100"/>
					<bc:column field="propName" title="属性名称" width="100"/>
					<bc:column field="primaryKey" title="是否是主键" width="100" formatter="fixedDict:YesNo"/>
					<bc:column field="propType" title="数据类型" width="200" formatter="fixedDict:PropDataType"/>
					<bc:column field="inputType" title="输入方式" width="200" formatter="fixedDict:PropInputType"/>
					<bc:column field="propOrder" title="排序" width="200"/>
					<bc:column field="remark" title="备注" width="200"/>
					<bc:column field="_operator" title="操作" width="100" formatter="operatorFormatter"/>
				</bc:table>
			</bc:col>
		</bc:ibox>
			<bc:ibox id="box2" title="事件信息" col="12" collapsible="true">
			<bc:col col="12">
				<bc:table id="formEventTable" idField="id" url="${base}/workflow/formEvent" queryUrl="/queryList?formCode=${formDef.code }" detailView="false" pagination="false">
					<bc:toolbar id="add1" text="新增" type="button" handler="addEventHandler"/>
					<bc:toolbar id="edit" text="编辑" type="button" handler="editEventHandler"/>
					<bc:toolbar id="view" text="查看" type="button" handler="viewEventHandler"/>
					<bc:toolbar id="delete" text="删除" type="button" handler="deleteEventHandler"/>
					<bc:column field="propCode" title="属性编码" width="100"/>
					<bc:column field="type" title="事件类型" width="100" formatter="fixedDict:PropFormEventType"/>
					<bc:column field="name" title="事件名称" width="100" formatter="fixedDict:PropFormEvent"/>
					<bc:column field="script" title="脚本" width="100"/>
					<bc:column field="remark" title="备注" width="200"/>
				</bc:table>
			</bc:col>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
var formCode = '${formDef.code}';
var status = '${formDef.status}';
$(document).ready(function(){
	//表单只有在草稿状态下才能编辑
	if(status == 'ENABLED' || status == 'DISABLED')
	{	//表单属性
		$('#formPropTable_tb_add1').attr('disabled', true);
		$('#formPropTable_tb_edit').attr('disabled', true);
		$('#formPropTable_tb_delete').attr('disabled', true);
		//表单事件
		$('#formEventTable_tb_add1').attr('disabled', true);
		$('#formEventTable_tb_edit').attr('disabled', true);
		$('#formEventTable_tb_delete').attr('disabled', true);
	}
	
})
//新增属性
function addHandler(){
	window.location = '${base}/workflow/formProp/add?formCode=' + formCode;
}

//编辑属性
function editHandler(){
	var tables =  $('#formPropTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		window.location = '${base}/workflow/formProp/edit/' + tables[0].formCode + '/' + tables[0].propCode;
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要编辑的记录。', 'error');
	}
}

//删除属性
function deleteHandler(){
	var tables =  $('#formPropTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		$.messager.confirm('提示', '确定要删除吗？', function(r){
			if(r)
			{
				$.mask.post('${base}/workflow/formProp/remove/'  + tables[0].formCode + '/' + tables[0].propCode, function(data){
					if(data.success)
					{
						$('#formPropTable').jTable('refresh');
						$('#formPropTable').jTable('clearCheckData');
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
		$.messager.toastr('提示', '请先选择需要删除的记录。', 'error');
	}
}

//查看属性
function viewHandler(){
	var tables =  $('#formPropTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		window.location = '${base}/workflow/formProp/view/' + tables[0].formCode + '/' + tables[0].propCode + '/view';
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要查看的记录。', 'error');
	}
}

//上下移动操作
function operatorFormatter(value, row, index){
	//表单只有在草稿状态下才能编辑
	if(status == 'ENABLED' || status == 'DISABLED')
	{
		return '';
	}
	
	//格式化 返回可以操作的移动标志
 	var data = $("#formPropTable").bootstrapTable('getData');
	if(data.length > 1 && index == 0)
	{
		return '<div style="width:100%; hight: 100%; text-align:center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="${base}/static/workflow/img/down.gif" style="cursor:pointer" title="下移" onClick="downClick(\'' + row.formCode + '\', \'' + row.propCode + '\', ' + index+ ')"  /></div>';
	}
	else if(data.length > 1 && (data.length - 1) == index)
	{
		return '<div style="width:100%; hight: 100%; text-align:center"><img src="${base}/static/workflow/img/up.gif" onClick="upClick(\'' + row.formCode + '\', \'' + row.propCode + '\', ' + index+ ')" style="cursor:pointer" title="上移"/>&nbsp;&nbsp;&nbsp;&nbsp;</div>';
	}
	else if(data.length > 1)
	{
		return '<div style="width:100%; hight: 100%; text-align:center"><img src="${base}/static/workflow/img/up.gif" onClick="upClick(\'' + row.formCode + '\', \'' + row.propCode + '\', ' + index+ ')" style="cursor:pointer" title="上移"/>&nbsp;&nbsp;<img src="${base}/static/workflow/img/down.gif" style="cursor:pointer" title="下移" onClick="downClick(\'' + row.formCode + '\', \'' + row.propCode + '\', ' + index+ ')"  /></div>';
	}
	else
	{
		return '<div style="width:100%; hight: 100%; text-align:center"></div>';
	}
}

//上移
function upClick(formCode, propCode, index){
	$.mask.post('${base}/workflow/formProp/formPropUp?formCode=' + formCode + '&propCode=' + propCode + '&propOrder=' + (parseInt(index) + parseInt(1)), function(data){
		if(data.success)
		{
			$('#formPropTable').jTable('refresh');
		}
	});
}

//下移
function downClick(formCode, propCode, index){
	$.mask.post('${base}/workflow/formProp/formPropDown?formCode=' + formCode + '&propCode=' + propCode + '&propOrder=' + (parseInt(index) + parseInt(1)), function(data){
		if(data.success)
		{
			$('#formPropTable').jTable('refresh');
		}
	});
}

//新增事件
function addEventHandler(){
	window.location = '${base}/workflow/formEvent/add?formCode=' + formCode;
}

//编辑事件
function editEventHandler(){
	var tables =  $('#formEventTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		window.location = '${base}/workflow/formEvent/edit/' + tables[0].id;
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要编辑的记录。', 'error');
	}
}

//删除事件
function deleteEventHandler(){
	var tables =  $('#formEventTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		$.messager.confirm('提示', '确定要删除吗？', function(r){
			if(r)
			{
				$.mask.post('${base}/workflow/formEvent/remove/'  + tables[0].id, function(data){
					if(data.success)
					{
						$('#formEventTable').jTable('refresh');
						$('#formEventTable').jTable('clearCheckData');
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
		$.messager.toastr('提示', '请先选择需要删除的记录。', 'error');
	}
}

//查看事件
function viewEventHandler(){
	var tables =  $('#formEventTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		window.location = '${base}/workflow/formEvent/view/' + tables[0].id + '/view';
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要查看的记录。', 'error');
	}
}
</script>
</body>
</html>