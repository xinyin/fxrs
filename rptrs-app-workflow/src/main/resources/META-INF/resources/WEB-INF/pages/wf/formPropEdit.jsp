<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>表单属性表</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
			<bf:hidden name="id" id="id" value="${formProp.id}"></bf:hidden>
			<bf:hidden name="propOrder" id="propOrder" value="${formProp.propOrder}"></bf:hidden>
			<bf:hidden name="formCode" id="formCode" value="${formCode }"></bf:hidden>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="属性编码" col="4" formControl="true"/>
						<bf:text id="propCode" name="propCode" col="8" value="${formProp.propCode}" disabled="${opt == 'edit'}">
							<bv:remote url="${base}/workflow/formProp/check?formCode=${formCode}" name="propCode" message="属性编码重复"/>
							<bv:notEmpty/>
							<bv:regexp regexp="^[a-zA-Z][a-zA-Z0-9_]*$" message="只能以英文字母开头，只能包含英文字母、数字、下划线"/>
							<bv:stringLength max="64" message="属性编码不能大于64"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="属性名称" col="4" formControl="true"/>
						<bf:text id="propName" name="propName" col="8" value="${formProp.propName}">
						<bv:notEmpty/>
						<bv:stringLength max="64" message="属性名称不能大于64"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="是否是主键" col="4" formControl="true"/>
						<bf:select id="primaryKey" name="primaryKey" col="8" value="${formProp.primaryKey}" dataSource="fixedDict" url="YesNo">
						<bv:notEmpty message="是否是主键不能为空"/>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="输入方式" col="4" formControl="true"/>
						<bf:select id="inputType" name="inputType" col="8" value="${formProp.inputType}" dataSource="fixedDict" url="PropInputType">
						<bv:notEmpty/>
						<be:event type="select">inputTypeChange(e.target.value);</be:event>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows1">
					<bc:col col="12">
						<bc:col col="6">
							<bc:label text="最小长度" col="4" formControl="true"/>
							<bf:touchpin name="minLength" id="minLength" min="1" step="1" decimals="0" btnType="primary" col="8" verticalButtons="false"/>
						</bc:col>
						<bc:col col="6">
							<bc:label text="最大长度" col="4" formControl="true"/>
							<bf:touchpin name="maxLength" id="maxLength" min="1" step="1" decimals="0" btnType="primary" col="8" verticalButtons="false"/>
						</bc:col>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows1-1">
					<bc:col col="12">
						<bc:col col="6">
							<bc:label text="显示行数" col="4" formControl="true"/>
							<bf:touchpin name="rows" id="rows" min="3" max="10" step="1" decimals="0" btnType="primary" col="8" verticalButtons="false"/>
						</bc:col>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows2">
					<bc:col col="12">
						<bc:col col="6">
							<bc:label text="最小值" col="4" formControl="true"/>
							<bf:text id="min" name="min" col="8">
								<bv:regexp regexp="-?(0|[1-9]\d*)(\.\d+)?" message="只能输入数字，小数，负数"/>
							</bf:text>
						</bc:col>
						<bc:col col="6">
							<bc:label text="最大值" col="4" formControl="true"/>
							<bf:text id="max" name="max" col="8">
								<bv:regexp regexp="-?(0|[1-9]\d*)(\.\d+)?" message="只能输入数字，小数，负数"/>
							</bf:text>
						</bc:col>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows2-1">
					<bc:col col="12">
						<bc:col col="6">
							<bc:label text="步长" col="4" formControl="true"/>
							<bf:text id="step" name="step" col="8">
								<bv:regexp regexp="^\d+(\.\d{1,4})?$" message="只能输入小数(支持4位)或正整数"/>
							</bf:text>
						</bc:col>
						<bc:col col="6">
							<bc:label text="小数位数" col="4" formControl="true"/>
							<bf:touchpin name="precision" id="precision" min="0" max="20" step="1" value="0" decimals="0" btnType="primary" col="8" verticalButtons="false"/>
						</bc:col>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows3">
					<bc:col col="12">
						<bc:col col="6">
							<bc:label text="选项来源" col="4" formControl="true"/>
							<bf:select id="sourceType" name="sourceType" col="8" dataSource="fixedDict" url="PropSourceType">
								<be:event type="select">sourceTypeChange(e.target.value);</be:event>
							</bf:select>
						</bc:col>
						<bc:col col="6">
							<label id="dictName" class="control-label col-sm-4">字典名称</label>
							<bf:text id="sourceName" name="sourceName" col="8">
								<be:event type="blur">sourceNameBlur(e.target.value);</be:event>
							</bf:text>
						</bc:col>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows3-1">
					<bc:col col="6">
							<bc:label text="是否多选" col="4" formControl="true"/>
							<bf:checkGroup name="multiple" col="8">
								<bf:check id="multiple" value="true">
									<be:event>multipleClick();</be:event>
								</bf:check>
							</bf:checkGroup>
					</bc:col>
				</bf:formGroup>
						
				<bf:formGroup id="optionRows4">
					<bc:col col="8" colOffset="2">
						<div id="optionTable_toolbar">
							<button id="optionTable_tb_add" type="button" onclick="addClick()" class=" btn btn-primary btn-outline">新增</button>
							<button id="optionTable_tb_delete" type="button" onclick="deleteClick()" class=" btn btn-primary btn-outline">删除</button>
				        </div>
						<table id="optionTable" >
						</table>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows5">
					<bc:col col="12">
						<bc:label text="日期格式" col="2" formControl="true"/>
						<bf:text id="format" name="format" col="4" >
							<be:event type="blur">formatBlur(e.target.value);</be:event>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows6">
					<bc:col col="12">
						<bc:label text="选择范围" col="2" formControl="true"/>
						<bf:select id="scope" name="scope" col="4" dataSource="fixedDict" url="PropUnitScope">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows7">
					<bc:col col="12">
						<bc:label text="文件个数" col="2" formControl="true"/>
						<bf:text id="limit" name="limit" col="4" value="1">
							<bv:digits message="只能输入数字"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows8">
					<bc:col col="12">
						<bc:label text="文件类型" col="2" formControl="true"/>
						<bf:text id="ext" name="ext" col="4" placeholder="-1不限制,例: jpg,png" value="-1">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="备注" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="8"  multiple="true">${formProp.remark}</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary"  text="保存" icon="save" click="btnSave();">
						</bc:button>
						<bc:button  text="返回">
							<be:event>
								window.location = '${base}/workflow/formProp/list?formCode=${formCode}';
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
var optEdit = true;
var editConfig = '${formProp.inputConfig}';
var minLength = '', maxLength = '', rows = '';
//数字
var min = '', max = '', step = '', precision = '';
//多选
var sourceType = '', sourceName = '', multiple = false, defaultValue = '';
var dataOptions =  [];
//时间
var format = '';
//机构人员
var scope = '';
//附件
var limit = '';
var ext = '';
$(document).ready(function(){
	//禁用表单提交事件 //禁用表单提交事件 btnSave
	$("#form1").attr("onsubmit", "return false");
	$('#primaryKey').val('N').trigger('change');
	//初始化时隐藏行分割线， 因为无法隐藏
	$('.hr-line-dashed').hide();
	//初始化时全部隐藏
	$('#optionRows1').hide();
	$('#optionRows1-1').hide();
	$('#optionRows2').hide();
	$('#optionRows2-1').hide();
	$('#optionRows3').hide();
	$('#optionRows3-1').hide();
	$('#optionRows4').hide();
	$('#optionRows5').hide();
	$('#optionRows6').hide();
	$('#optionRows7').hide();
	$('#optionRows8').hide();
	
	//判断是新增还是编辑
	if(opt == 'edit')
	{
		var inputType = $('#inputType').val();
		initInputType(inputType);
	}
	else
	{
		initInputType('TEXT');
	}
});

//下拉选择触发
function inputTypeChange(value){
	//清空表格数据 字典名称
	$('#sourceType').val('fixedDict').trigger('change');
	opt = 'add';
	$('#rows').val('');
	$('#multiple').attr('checked', false)
	initInputType(value);
}

//初始化加载数据类型
function initInputType(value){
	//判断不同类型显示不同属性
	if(value == 'TEXT' || value == 'MTEXT')
	{
		//初始化时全部隐藏
		$('#optionRows1').show();
		$('#optionRows1-1').hide();
		$('#optionRows2').hide();
		$('#optionRows2-1').hide();
		$('#optionRows3').hide();
		$('#optionRows4').hide();
		$('#optionRows5').hide();
		$('#optionRows6').hide();
		$('#optionRows7').hide();
		$('#optionRows8').hide();
		
		if(value == 'MTEXT'){ $('#optionRows1-1').show(); $('#rows').val('3');}
		//加载数据
		if(opt == 'edit')
		{
			var config = JSON.parse(editConfig);
			$('#minLength').val(config.minLength);
			$('#maxLength').val(config.maxLength);
			$('#rows').val(config.rows);
		}
	}
	else if(value == 'NTEXT')
	{
		//初始化时全部隐藏
		$('#optionRows1').hide();
		$('#optionRows1-1').hide();
		$('#optionRows2').show();
		$('#optionRows2-1').show();
		$('#optionRows3').hide();
		$('#optionRows3-1').hide();
		$('#optionRows4').hide();
		$('#optionRows5').hide();
		$('#optionRows6').hide();
		$('#optionRows7').hide();
		$('#optionRows8').hide();
		//加载数据
		if(opt == 'edit')
		{
			var config = JSON.parse(editConfig);
			$('#min').val(config.min);
			$('#max').val(config.max);
			$('#step').val(config.step);
			$('#precision').val(config.precision);
		}
	}
	else if(value == 'CHECK' || value == 'RADIO' || value == 'SELECT')
	{
		if(value == 'CHECK' || value == 'RADIO')$('#optionRows3-1').hide();
		else $('#optionRows3-1').show();
		
		//初始化时全部隐藏
		$('#optionRows1').hide();
		$('#optionRows1-1').hide();
		$('#optionRows2').hide();
		$('#optionRows2-1').hide();
		$('#optionRows3').show();
		$('#optionRows4').hide();
		$('#optionRows5').hide();
		$('#optionRows6').hide();
		$('#optionRows7').hide();
		$('#optionRows8').hide();
		//加载数据
		if(opt == 'edit')
		{
			var config = JSON.parse(editConfig);
			//显示多选选项
			sourceTypeChange(config.sourceType);
		}
		else
		{
			//显示多选选项
			sourceTypeChange('fixedDict');
		}
	}
	else if(value == 'DATE')
	{
		//初始化时全部隐藏
		$('#optionRows1').hide();
		$('#optionRows1-1').hide();
		$('#optionRows2').hide();
		$('#optionRows2-1').hide();
		$('#optionRows3').hide();
		$('#optionRows3-1').hide();
		$('#optionRows4').hide();
		$('#optionRows5').show();
		$('#optionRows6').hide();
		$('#optionRows7').hide();
		$('#optionRows8').hide();
		//加载数据
		if(opt == 'edit')
		{
			var config = JSON.parse(editConfig);
			$('#format').val(config.format);
		}
	}
	else if(value == 'UNIT' || value == 'STAFF')
	{
		//初始化时全部隐藏
		$('#optionRows1').hide();
		$('#optionRows1-1').hide();
		$('#optionRows2').hide();
		$('#optionRows2-1').hide();
		$('#optionRows3').hide();
		$('#optionRows3-1').show();
		$('#optionRows4').hide();
		$('#optionRows5').hide();
		$('#optionRows6').show();
		$('#optionRows7').hide();
		$('#optionRows8').hide();
		//加载数据
		if(opt == 'edit')
		{
			var config = JSON.parse(editConfig);
			$('#scope').val(config.scope);
			$('#scope').trigger('change');
		}
	}
	else if(value == 'ATTACH')
	{
		//初始化时全部隐藏
		$('#optionRows1').hide();
		$('#optionRows1-1').hide();
		$('#optionRows2').hide();
		$('#optionRows2-1').hide();
		$('#optionRows3').hide();
		$('#optionRows3-1').hide();
		$('#optionRows4').hide();
		$('#optionRows5').hide();
		$('#optionRows6').hide();
		$('#optionRows7').show();
		$('#optionRows8').show();
		//加载数据
		if(opt == 'edit')
		{
			var config = JSON.parse(editConfig);
			$('#limit').val(config.limit);
			$('#ext').val(config.ext);
		}
	}
}

//实现下拉选择加载数据
function sourceTypeChange(value){
	//显示选项相关属性
	$('#optionRows4').show();
	$('#dictName').show();
	$('#sourceName').show();
	
	//禁用新增按钮
	$('#optionTable_tb_add').attr('disabled', true);
	$('#optionTable_tb_delete').attr('disabled', true);

	//清空表格数据 字典名称
	$("#optionTable").bootstrapTable('removeAll');
	$('#sourceName').val('');
	
	//根据不同的选项显示不同的标题
	if(value == 'fixedDict' || value == 'dict')
	{
		$('#dictName').text('字典名称');	
	}
	else if(value == 'custom')
	{
		$('#dictName').text('字典路径');	
	}
	else if(value == 'manual')
	{
		$('#dictName').hide();
		$('#sourceName').hide();
		$('#optionTable_tb_add').attr('disabled', false);
		$('#optionTable_tb_delete').attr('disabled', false);
	}
	
	//编辑框的模式：支持popup和inline两种模式，默认是popup
	var status = { field: "check", checkbox: true};
    var labelCol = { field: "name", title: "文本", align: "center", width: 100};
	var labelEditCol = { field: "name1", title: "文本", width: 100, align: "center", editable: { type: 'text', mode: "popup", title: '文本', validate: function (v) { if (!v) return '文本不能为空'; } }};
	var valueCol = {field: "value", title: "值", align: "center", width: 100};
	var valueEditCol = {field: "value1", title: "值", width: 100, align: "center", editable: { type: 'text', mode: "popup", title: '值', validate: valueValidate}};
	var defaultCol = {field: "default", title: "是否默认", width: 50, formatter: defaultFormatter};
	var operatorCol = { field: "operator", title: "操作", width: 50, formatter: operatorFormatter};
	
	var columns = [];
	columns.push(status);
	columns.push(labelCol);
	columns.push(valueCol);
	columns.push(labelEditCol);
	columns.push(valueEditCol);
	columns.push(defaultCol);
	columns.push(operatorCol);
	
	//初始化表格
	$("#optionTable").bootstrapTable({
        toolbar: "#optionTable_toolbar",
        idField: "name",
        pagination: false,
        search: false,
        showRefresh: false,
        clickToSelect: true,
        columns: columns
    });
	//初始化列是否可编辑
	if(value == 'manual')
	{
		$("#optionTable").bootstrapTable('hideColumn', 'name');
		$("#optionTable").bootstrapTable('hideColumn', 'value');
		$("#optionTable").bootstrapTable('showColumn', 'name1');
		$("#optionTable").bootstrapTable('showColumn', 'value1');
	}
	else
	{
		$("#optionTable").bootstrapTable('showColumn', 'name');
		$("#optionTable").bootstrapTable('showColumn', 'value');
		$("#optionTable").bootstrapTable('hideColumn', 'name1');
		$("#optionTable").bootstrapTable('hideColumn', 'value1');
	}
	
	//加载数据
	if(optEdit && opt == 'edit')
	{
		optEdit = false;
		var config = JSON.parse(editConfig);
		$('#sourceType').val(config.sourceType);
		$('#sourceType').trigger('change');
		if(config.multiple)
		{
			$('#multiple').attr('checked', 'checked');
		}
		$('#sourceName').val(config.sourceName);
		//加载数据
		if(config.sourceType == 'manual')
		{
			//将值设置到data中
			var data = [];
			$.each(config.options, function(i, v){
				var obj = {
						name1: v.name,
						value1: v.value
				};
				data.push(obj);
			});
			initBootStrapTable(data);
		}
	}
	
	//判断 当前类型下  字典名称存在， 初始化表格
	var sourceName = $('#sourceName').val();
	if(null != sourceName && '' != sourceName)
	{
		$('#optionRows4').show();
		//初始化表格
		sourceNameBlur(sourceName);
	}
}

//输入字典选名称时，离开触发
function sourceNameBlur(val){
	
	var code = $('#sourceType').val();
	if(val != null && val != '')
	{
		//根据不同的选项显示不同的标题
		if(code == 'fixedDict')
		{
			//先请求数据， 再把数据加载到bootstrapTable 
			$.mask.post('${base}/fixedDict/' + val, function(data){
				
				initBootStrapTable(data);
			});
		}
		else if(code == 'dict')
		{
			//先请求数据， 再把数据加载到bootstrapTable 
			$.mask.post('${base}/sys/dictItem/getDictItems/' + val, function(data){
				
				initBootStrapTable(data);
			});
		}
		else if(code == 'custom')
		{
			//先请求数据， 再把数据加载到bootstrapTable 
			$.mask.post('${base}/workflow/formProp/queryDict', {dictType: code, source: val}, function(data){
				
				initBootStrapTable(data);
			});
		}
	}
}

//设置数据
function initBootStrapTable(data){

	//初始化表格
	$("#optionTable").bootstrapTable('load', data);
	
	//重设已选中的默认值
	var config = JSON.parse(editConfig);
	var def = config.defaultValue;
	var array = def.split(',');
	$.each(array, function(i, v){
		var index = getDataByCode(v);
		if(index > -1)
		{
			$('#multiple' + index).attr('checked', 'checked');
		}
	});
	
}

//点击多选后 处理
function multipleClick(){
	var multipleCheck = $("input[name='multiple']:checked").val();
	var data = $("#optionTable").bootstrapTable('getData');
	$.each(data, function(i, v){
		$('#optionTable').bootstrapTable('updateCell', {index: i, field: 'default', value: ''});
	});
}

//初始化选项
function defaultFormatter(value, row, index){
	var inputType = $('#inputType').val();
	var multipleCheck = $("input[name='multiple']:checked").val();
	if(inputType == 'CHECK' || multipleCheck)
	{
		return '<div style="width:100%; hight: 100%; text-align:center"><input type="checkbox" id="multiple' + index + '" name="multipleDef" value="' + index + '" /></div>';
	}
	else
	{
		return '<div style="width:100%; hight: 100%; text-align:center"><input type="radio" id="multiple' + index + '" name="multipleDef" value="' + index + '" /></div>';
	}
}

//上下移动操作
function operatorFormatter(value, row, index){
	var sourceType = $('#sourceType').val();
	if(sourceType == 'manual')
	{	//获取总行数
		var data = $("#optionTable").bootstrapTable('getData');
		if(data.length > 1 && index == 0)
		{
			return '<div style="width:100%; hight: 100%; text-align:center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="${base}/static/workflow/img/down.gif" style="cursor:pointer" title="下移" onClick="downClick(' + index+ ')"  /></div>';
		}
		else if(data.length > 1 && (data.length - 1) == index)
		{
			return '<div style="width:100%; hight: 100%; text-align:center"><img src="${base}/static/workflow/img/up.gif" onClick="upClick(' + index+ ')" style="cursor:pointer" title="上移"/>&nbsp;&nbsp;&nbsp;&nbsp;</div>';
		}
		else if(data.length > 1)
		{
			return '<div style="width:100%; hight: 100%; text-align:center"><img src="${base}/static/workflow/img/up.gif" onClick="upClick(' + index+ ')" style="cursor:pointer" title="上移"/>&nbsp;&nbsp;<img src="${base}/static/workflow/img/down.gif" style="cursor:pointer" title="下移" onClick="downClick(' + index+ ')"  /></div>';
		}
		else
		{
			return '<div style="width:100%; hight: 100%; text-align:center"></div>';
		}
	}
}

//新增选项
function addClick(){
	var datas =  $('#optionTable').bootstrapTable('getData');

	var isAdd = true;
	$.each(datas, function(i, v){
		if(!v.name1 || !v.value1)
		{
			isAdd = false;
			$('#optionTable').bootstrapTable('check', i);
			$.messager.toastr('提示', '请先完善已有的选项值。', 'error');
		}
	});
	
	//判断当前是否现有的已编辑完
	if(datas.length < 1 || isAdd)
	{
		//记录已选中的默认值
		var check = $("input[name='multipleDef']:checked");
		var data = {"name1": "", "value1": "", "default": "", "operator": ""};
		$("#optionTable").bootstrapTable('append', data);
		//重设已选中的默认值
		$.each(check, function(){
			var v = $(this).val();
			$('#multiple' + v).attr('checked', 'checked');
		});
	}
}

//删除选项
function deleteClick(){
	var datas =  $('#optionTable').bootstrapTable('getSelections');
	var ids = [];
	$.each(datas, function(i, v){
		ids.push(v.value1);
	});
	var check = $("input[name='multipleDef']:checked");
	//TODO 删除后， 第二行变第一行， 默认值失效。 需处理
	//移除选项
	$("#optionTable").bootstrapTable('remove', {field: 'value1', values: ids});
	//重设已选中的默认值
	$.each(check, function(){
		var v = $(this).val();
		$('#multiple' + v).attr('checked', 'checked');
	});
}

//验证编码不能重复
function valueValidate(v){
	if (!v) return '值不能为空';

	var datas =  $('#optionTable').bootstrapTable('getData');
	//查询当前编码是否已存在
	var isRepeat = false;
	$.each(datas, function(i, val){
		if(val.value1 == v)
		{
			isRepeat = true;
		}
	});
	if(isRepeat)
	{
		return '值不能重复';
	}
}

//上移
function upClick(index){
	//当前数据
	var row = getDataByIndex(index);
	//需要交换的数据
	var data = getDataByIndex((parseInt(index) - parseInt(1)));
	//当前更新对象
	var obj =
	{
		name1: row.name1,
		value1: row.value1
	};
	//当前需要交换对象
	var obj1 =
	{
		name1: data.name1,
		value1: data.value1
	};
	//记录已选中的默认值
	var check = $("input[name='multipleDef']:checked");
	//更新交换之前的数据
	$('#optionTable').bootstrapTable('updateRow', {index: index, row: obj1});
	//更新当前数据
	$('#optionTable').bootstrapTable('updateRow', {index: (index - 1), row: obj});
	//重设已选中的默认值
	$.each(check, function(){
		var v = $(this).val();
		if(v == index)
		{
			$('#multiple' + (parseInt(v) - parseInt(1))).attr('checked', 'checked');
		}
		else if(v == (parseInt(index) - parseInt(1)))
		{
			$('#multiple' + index).attr('checked', 'checked');
		}
		else 
		{
			$('#multiple' + v).attr('checked', 'checked');
		}
	});
}

//下移
function downClick(index){
	//当前数据
	var row = getDataByIndex(index);
	//需要交换的数据
	var data = getDataByIndex((parseInt(index) + parseInt(1)));
	//当前更新对象
	var obj =
	{
		name1: row.name1,
		value1: row.value1
	};
	//当前需要交换对象
	var obj1 =
	{
		name1: data.name1,
		value1: data.value1
	};
	//记录已选中的默认值
	var check = $("input[name='multipleDef']:checked");
	//更新交换之前的数据
	$('#optionTable').bootstrapTable('updateRow', {index: index, row: obj1});
	//更新当前数据
	$('#optionTable').bootstrapTable('updateRow', {index: (index + 1), row: obj});
	//重设已选中的默认值
	$.each(check, function(){
		var v = $(this).val();
		if(v == index)
		{
			$('#multiple' + (parseInt(v) + parseInt(1))).attr('checked', 'checked');
		}
		else if(v == (parseInt(index) + parseInt(1)))
		{
			$('#multiple' + index).attr('checked', 'checked');
		}
		else 
		{
			$('#multiple' + v).attr('checked', 'checked');
		}
	});
}

//根据index找到当前数组的对象
function getDataByIndex(index){
	var data = {};
	var datas =  $('#optionTable').bootstrapTable('getData');
	$.each(datas, function(i, val){
		if(index == i)
		{
			data = val;
		}
	});
	
	return data;
}

//根据code找到当前数组的索引
function getDataByCode(code){
	var index = -1;
	var datas =  $('#optionTable').bootstrapTable('getData');
	$.each(datas, function(i, val){
		if(code == val.value || code == val.value1)
		{
			index = i;
		}
	});
	
	return index;
}

//验证日期格式是否正确
function formatBlur(v){
	if(v)
	{
		var $date = $.date.format(new Date(), v);
		var newDate = new Date($date);
		//格式错误，提示
		if(newDate == 'Invalid Date')
		{
			$.messager.toastr('提示', '你输入的日期格式错误。', 'error');	
		}
	}
}

//保存
function btnSave(){
	//TODO 行编辑 和 属性验证有冲突，  只要页面使用行编辑， 手动输入后 ， 或触发action 的事件 参考 122 行禁用onsubmit事件
	$('#form1').data('formValidation').validate();
	var isValid = $('#form1').data('formValidation').isValid();
	if(isValid)
	{
		//验证配置	
		var inputType = $('#inputType').val();
		var multipleCheck = $("input[name='multiple']:checked").val();
		if(multipleCheck)
		{
			multiple = true;	
		}
	
		//判断不同类型显示不同属性
		if(inputType == 'TEXT' || inputType == 'MTEXT')
		{
			minLength = $('#minLength').val();
			maxLength = $('#maxLength').val();
			rows = $('#rows').val();
			//最大位数一定要比最小位数大
			if(minLength && maxLength)
			{
				if(parseInt(maxLength) < parseInt(minLength))
				{
					$.messager.toastr('提示', '最大长度要大于最小长度。', 'error');
					return;
				}
			}
		}
		else if(inputType == 'NTEXT')
		{
			min = $('#min').val();
			max = $('#max').val();
			step = $('#step').val();
			precision = $('#precision').val();
			//最大位数一定要比最小位数大
			if(max && min)
			{
				if(parseInt(max) <= parseInt(min))
				{
					$.messager.toastr('提示', '最大值要大于最小值。', 'error');
					return;
				}
			}
			//最大位数一定要比最小位数大
			if(parseInt(precision) > 20)
			{
				$.messager.toastr('提示', '小数位数最多20位。', 'error');
				return;
			}
		}
		else if(inputType == 'CHECK' || inputType == 'RADIO' || inputType == 'SELECT')
		{
			sourceType = $('#sourceType').val();
			sourceName = $('#sourceName').val();
			var check = $("input[name='multipleDef']:checked");
			var datas = $('#optionTable').bootstrapTable('getData')
			if(sourceType == 'manual')
			{
				var isAdd = false;
				$.each(datas, function(i, v){
					if(!v.name1 || !v.value1)
					{
						isAdd = true;
					}
				});
				//判断选项当前是否有选项值
				if(datas.length < 1 )
				{
					$.messager.toastr('提示', '选项值至少设置一项。', 'error');
					return;
				}
				//验证当前的选项值是否已完善
				if(isAdd)
				{
					$.messager.toastr('提示', '请先完善已有的选项值。', 'error');
					return;
				}
				//将值设置到data中
				$.each(datas, function(i, v){
					var obj = {
							name: v.name1,
							value: v.value1
					};
					dataOptions.push(obj);
				});
				//默认值处理
				$.each(check, function(){
					var v = $(this).val();
					var r = getDataByIndex(v);
					defaultValue += r.value1 + ',';
				});
			}
			else if(sourceType ==  'custom')
			{
				//字典名称不能为空
				if(!sourceName)
				{
					$.messager.toastr('提示', '字典路径不能为空。', 'error');
					return;
				}
				//判断选项当前是否有选项值
				if(datas.length < 1 )
				{
					$.messager.toastr('提示', '当前添加的字典有误。', 'error');
					return;
				}
				//默认值处理
				$.each(check, function(){
					var v = $(this).val();
					var r = getDataByIndex(v);
					defaultValue += r.value + ',';
				});
			}
			else
			{
				//字典名称不能为空
				if(!sourceName)
				{
					$.messager.toastr('提示', '字典名称不能为空。', 'error');
					return;
				}
				//判断选项当前是否有选项值
				if(datas.length < 1 )
				{
					$.messager.toastr('提示', '当前添加的字典有误。', 'error');
					return;
				}
				
				//默认值处理
				$.each(check, function(){
					var v = $(this).val();
					var r = getDataByIndex(v);
					defaultValue = r.value + ',';
				});
				//默认值处理
				$.each(check, function(){
					var v = $(this).val();
					var r = getDataByIndex(v);
					defaultValue += r.value + ',';
				});
			}
		}
		else if(inputType == 'DATE')
		{
			format = $('#format').val();
			if(format)
			{
				var $date = $.date.format(new Date(), format);
				var newDate = new Date($date);
				//格式错误，提示
				if(newDate == 'Invalid Date')
				{
					$.messager.toastr('提示', '你输入的日期格式错误。', 'error');	
					return;
				}
			}
			else
			{
				$.messager.toastr('提示', '你输入的日期格式错误。', 'error');	
				return;
			}
		}
		else if(inputType == 'UNIT' || inputType == 'STAFF')
		{
			//TODO 暂时未做限制
			scope = $('#scope').val();
		}
		else if(inputType == 'ATTACH')
		{
			limit = $('#limit').val();
			ext = $('#ext').val();
			if(!limit)
			{
				$.messager.toastr('提示', '文件上传个数不能为空。', 'error');	
				return;
			}
			if(!ext)
			{
				$.messager.toastr('提示', '文件上传格式不能为空。', 'error');	
				return;
			}
		}
		//获取基础配置
		var inputConfig = {
				minLength: minLength,
			    maxLength: maxLength,
			    rows: rows,
			    min: min,
			    max: max,
			    step: step,
			    precision: precision,
			    multiple: multiple,
			    sourceType: sourceType,
			    sourceName: sourceName,
			    defaultValue: defaultValue,
			    options: dataOptions,
			    format: format,
			    scope: scope,
			    limit: limit,
			    ext: ext
		};
		var config = JSON.stringify(inputConfig);
		var dataAll = $('#form1').serializeAll();
		var obj = {name: "inputConfig", value: config};
		dataAll.push(obj);
		$.mask.post('${base}/workflow/formProp/save', dataAll, function(data){
			if(data.success)
			{
				$.messager.alert('提示', '保存成功。', 'info', function(){
					window.location = '${base}/workflow/formProp/list?formCode=${formCode}';
				});
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
