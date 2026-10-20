<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>任务类型</title>

</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
			<bf:hidden name="param" id="param"></bf:hidden>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="类别编码" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="8" value="${group.code}" disabled="${opt == 'edit'}">
							<bv:remote url="${base}/sch/jobGroup/check" name="code" message="类别编码已存在"/>
							<bv:notEmpty/>
							<bv:regexp regexp="^[a-zA-Z][a-zA-Z0-9_]*$" message="只能以英文字母开头，只能包含英文字母、数字、下划线"/>
							<bv:stringLength max="64" message="类别编码不能大于64"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="类别名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${group.name}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="2">
						<bc:label text="参数" col="12" formControl="true"/>
					</bc:col>
					<bc:col col="10">
						<div id="optionTable_toolbar">
							<button id="optionTable_tb_add" type="button" onclick="addClick()" class=" btn btn-primary btn-outline">新增</button>
							<button id="optionTable_tb_delete" type="button" onclick="deleteClick()" class=" btn btn-primary btn-outline">删除</button>
				        </div>
						<table id="optionTable" >
						</table>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="描述" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="10" multiple="true" value="${group.remark}"></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button type="primary"  text="保存" icon="save"  click="btnSave();">
						</bc:button>
						<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/sch/jobGroup/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
					</bf:form>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
var opt = '${opt}';
var paramJosn = '${group.param }';
$(document).ready(function(){
	//禁用表单提交事件 btnSave 参考155行
	$("#form1").attr("onsubmit", "return false");
	
	/* 初始化参数表格*/
	initTable();
})

/* 初始化参数表格*/
function initTable(){
	//初始化表格
	$("#optionTable").bootstrapTable({
        toolbar: "#optionTable_toolbar",
        idField: "code",
        pagination: false,
        search: false,
        showRefresh: false,
        clickToSelect: true,
        columns: [{field: "check", checkbox: true},
        	{ field: "code", title: "编码", align: "center", width: 100,editable: { type: 'text', mode: "code", title: '参数编码', validate: codeValidate}},
        	{field: "type", title: "类型", align: "center", width: 100,editable: { type: 'select', mode: "type", source: [{ value: "string", text: "字符串" }, { value: "number", text: "数字" }, {value:"date", text:"日期"}], title: '参数类型'}},
        	{ field: "value", title: "默认值", width: 100, editable: { type: 'text', mode: "code", title: '默认值'}}]
    });
	
	//判断是否是编辑， 编辑加载数据
	if(opt == 'edit' && paramJosn)
	{
		var rows = JSON.parse(paramJosn);
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
		if(val.code == v)
		{
			isRepeat = true;
		}
	});
	
	if(isRepeat)
	{
		return '编码不能重复';
	}
}

//新增选项
function addClick(){
	var rows =  $('#optionTable').bootstrapTable('getData');
	var isAdd = validate(rows);
	//判断当前是否现有的已编辑完
	if(rows.length < 1 || isAdd)
	{
		var data = {"code": "", "type": "string", "value": ""};
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
			ids.push(v.code);
		});
		//移除选项
		$("#optionTable").bootstrapTable('remove', {field: 'code', values: ids});
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
		if(!v.code)
		{
			isAdd = false;
			$('#optionTable').bootstrapTable('check', i);
			$.messager.toastr('提示', '请先完善已有的选项值。', 'error');
		}
	});
	return isAdd;
}

function btnSave(){
	//TODO 行编辑 和 属性验证有冲突，  只要页面使用行编辑， 手动输入后 ， 或触发action 的事件 参考 122 行禁用onsubmit事件
	$('#form1').data('formValidation').validate();
	var isValid = $('#form1').data('formValidation').isValid();
	if(isValid)
	{
		//获取参数
		var rows =  $('#optionTable').bootstrapTable('getData');
		var isAdd = validate(rows);
		//判断当前是否现有的已编辑完
		if(isAdd)
		{
			$.each(rows, function(i, v){
				v.check = false;
			});
			
			var param = JSON.stringify(rows);
			$('#param').val(param);
			var data = $('#form1').serializeAll();
			$.mask.post('${base}/sch/jobGroup/save', data, function(data){
				if(data.success)
				{
					$.messager.alert('提示', '保存成功。', 'info', function(){
						window.location = '${base}/sch/jobGroup/list';
					});
				}
				else
				{
					$.messager.alert('错误', data.msg, 'error');
				}
			});
		}
	}
}
</script>
</body>
</html>
