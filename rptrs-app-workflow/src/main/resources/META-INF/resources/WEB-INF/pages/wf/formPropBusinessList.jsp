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
				<div id="optionTable_toolbar">
					<button id="optionTable_tb_add" type="button" onclick="addClick()" class=" btn btn-primary">新增</button>
					<button id="optionTable_tb_delete" type="button" onclick="deleteClick()" class=" btn btn-primary">删除</button>
					<button id="optionTable_tb_save" type="button" onclick="saveClick()" class=" btn btn-primary">保存</button>
		        </div>
				<table id="optionTable" >
				</table>
			</bc:col>
			<%-- <bc:col col="12">
				<bc:table id="formPropTable" idField="id" url="${base}/workflow/formProp" queryUrl="/queryList?formCode=${formDef.code }" detailView="false" pagination="false">
					<bc:toolbar id="add1" text="新增" type="button" handler="addHandler"/>
					<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
					<bc:toolbar id="view" text="查看" type="button" handler="viewHandler"/>
					<bc:toolbar id="delete" text="删除" type="button" handler="deleteHandler"/>
					<bc:column field="propCode" title="属性编码" width="100"/>
					<bc:column field="propName" title="属性名称" width="100"/>
					<bc:column field="primaryKey" title="是否是主键" width="100" formatter="fixedDict:YesNo"/>
					<bc:column field="propOrder" title="排序" width="200"/>
					<bc:column field="_operator" title="操作" width="100" formatter="operatorFormatter"/>
				</bc:table>
			</bc:col> --%>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
var formCode = '${formDef.code}';
var status = '${formDef.status}';
var formProp = '${formProp}';
$(document).ready(function(){
	/* //表单只有在草稿状态下才能编辑
	if(status == 'ENABLED' || status == 'DISABLED')
	{	//表单属性
		$('#formPropTable_tb_add1').attr('disabled', true);
		$('#formPropTable_tb_edit').attr('disabled', true);
		$('#formPropTable_tb_delete').attr('disabled', true);
	} */
	/* 初始化参数表格*/
	initTable();
	
})

/* 初始化参数表格*/
function initTable(){
	//初始化表格
	$("#optionTable").bootstrapTable({
        toolbar: "#optionTable_toolbar",
        idField: "propCode",
        pagination: false,
        search: false,
        showRefresh: false,
        clickToSelect: true,
        columns: [{field: "check", checkbox: true},
        	{ field: "propCode", title: "属性编码", align: "center", width: 100,editable: { type: 'text', mode: "inline", title: '参数编码', validate: codeValidate}},
        	{ field: "propName", title: "属性名称", align: "center", width: 100,editable: { type: 'text', mode: "inline",title: '参数编码', validate: nameValidate}},
        	{field: "primaryKey", title: "是否是主键", align: "center", width: 100,editable: { type: 'select', mode: "inline", source: [{ value: "Y", text: "是" }, { value: "N", text: "否" }]}},
        	{ field: "remark", title: "备注", width: 100, editable: { type: 'text', mode: "inline", title: '备注'}}]
    });
	
	//判断是否是编辑， 编辑加载数据
	if(formProp)
	{
		var rows = JSON.parse(formProp);
		$('#optionTable').bootstrapTable('load', rows);
	}
}

//验证编码不能重复
function codeValidate(v){
	if (!v)return '文本不能为空';
	var reg = /^[a-zA-Z][a-zA-Z0-9_]{0,64}/g;
	if(!reg.test(v)) 
		return '只能以英文字母开头，只能包含英文字母、数字、下划线';
	
	var rows =  $('#optionTable').bootstrapTable('getData');
	//查询当前编码是否已存在
	var isRepeat = false;
	$.each(rows, function(i, val){
		if(val.propCode == v)
		{
			isRepeat = true;
		}
	});
	
	if(isRepeat)
	{
		return '编码不能重复';
	}
}

//验证编码不能重复
function nameValidate(v){
	if (!v)return '文本不能为空';
}

//新增选项
function addClick(){
	var rows =  $('#optionTable').bootstrapTable('getData');
	var isAdd = validate(rows);
	//判断当前是否现有的已编辑完
	if(rows.length < 1 || isAdd)
	{
		var data = {"propCode": "", "propName": "", "primaryKey": "N", "remark": ""};
		//新增一条数据
		$("#optionTable").bootstrapTable('append', data);
	}
}

//删除选项
function deleteClick(){
	var datas =  $('#optionTable').bootstrapTable('getSelections');
	
	//判断当前是否现有的已编辑完
	if(datas.length > 0)
	{
		var ids = [];
		$.each(datas, function(i, v){
			ids.push(v.propCode);
		});
		//移除选项
		$("#optionTable").bootstrapTable('remove', {field: 'propCode', values: ids});
	}
	else
	{
		$.messager.toastr('提示', '请选择需要删除的行。', 'error');
	}
}

//验证是否数据填完
function validate(data){
	var isAdd = true;
	$.each(data, function(i, v){
		if(!v.propCode || !v.propName)
		{
			isAdd = false;
			$('#optionTable').bootstrapTable('check', i);
			$.messager.toastr('提示', '请先完善已有的选项值。', 'error');
		}
	});
	return isAdd;
}

function saveClick(){
	
	var rows =  $('#optionTable').bootstrapTable('getData');
	var v = validate(rows);
	//判断当前是否现有的已编辑完
	if(v)
	{
		$.mask.post('${base}/workflow/formProp/saveProps', {formCode : formCode, formProps : JSON.stringify(rows)}, function(data){
			if(data.success)
			{
				$.messager.alert('提示', data.msg, 'info');
			}
			else
			{
				$.messager.alert('错误', data.msg, 'error');
			}
		});
	}
	
}

</script>
</body>
</html>