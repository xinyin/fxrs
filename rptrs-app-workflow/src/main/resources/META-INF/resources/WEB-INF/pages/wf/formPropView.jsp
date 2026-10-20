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
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="属性编码" col="4" formControl="true"/>
						<bc:label text="${formProp.propCode}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="属性名称" col="4" formControl="true"/>
						<bc:label text="${formProp.propName}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="是否是主键" col="4" formControl="true"/>
						<bc:label text="fixedDict:YesNo:${formProp.primaryKey}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="输入方式" col="4" formControl="true"/>
						<bc:label text="fixedDict:PropInputType:${formProp.inputType}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows1">
					<bc:col col="12">
						<bc:col col="6">
							<bc:label text="最小长度" col="4" formControl="true"/>
							<label id="minLength" class="control-label col-sm-8" style="text-align:left"></label>
						</bc:col>
						<bc:col col="6">
							<bc:label text="最大长度" col="4" formControl="true"/>
							<label id="maxLength" class="control-label col-sm-8" style="text-align:left"></label>
						</bc:col>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows1-1">
					<bc:col col="12">
						<bc:col col="6">
							<bc:label text="显示行数" col="4" formControl="true"/>
							<label id="rows" class="control-label col-sm-8" style="text-align:left"></label>
						</bc:col>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows2">
					<bc:col col="12">
						<bc:col col="6">
							<bc:label text="最小值" col="4" formControl="true"/>
							<label id="min" class="control-label col-sm-8" style="text-align:left"></label>
						</bc:col>
						<bc:col col="6">
							<bc:label text="最大值" col="4" formControl="true"/>
							<label id="max" class="control-label col-sm-8" style="text-align:left"></label>
						</bc:col>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows2-1">
					<bc:col col="12">
						<bc:col col="6">
							<bc:label text="步长" col="4" formControl="true"/>
							<label id="step" class="control-label col-sm-8" style="text-align:left"></label>
						</bc:col>
						<bc:col col="6">
							<bc:label text="小数位数" col="4" formControl="true"/>
							<label id="precision" class="control-label col-sm-8" style="text-align:left"></label>
						</bc:col>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows3">
					<bc:col col="12">
						<bc:col col="6">
							<bc:label text="选项来源" col="4" formControl="true"/>
							<label id="sourceType" class="control-label col-sm-8" style="text-align:left"></label>
						</bc:col>
						<bc:col col="6">
							<label id="dictName" class="control-label col-sm-4">字典名称</label>
							<label id="sourceName" class="control-label col-sm-8" style="text-align:left"></label>
						</bc:col>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows3-1">
					<bc:col col="6">
							<bc:label text="是否多选" col="4" formControl="true"/>
							<label id="multiple" class="control-label col-sm-8" style="text-align:left"></label>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows4">
					<bc:col col="8" colOffset="2">
						<table id="optionTable" >
						</table>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows5">
					<bc:col col="12">
						<bc:label text="日期格式" col="2" formControl="true"/>
						<label id="format" class="control-label col-sm-8" style="text-align:left"></label>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows6">
					<bc:col col="12">
						<bc:label text="选择范围" col="2" formControl="true"/>
						<label id="scope" class="control-label col-sm-8" style="text-align:left"></label>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows7">
					<bc:col col="12">
						<bc:label text="文件个数" col="2" formControl="true"/>
						<label id="limit" class="control-label col-sm-8" style="text-align:left"></label>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="optionRows8">
					<bc:col col="12">
						<bc:label text="文件类型" col="2" formControl="true"/>
						<label id="ext" class="control-label col-sm-8" style="text-align:left"></label>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="备注" col="2" formControl="true"/>
						<bc:label text="${formProp.remark}" col="10" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button  text="返回" icon="arrow-circle-left">
							<be:event>
								if('${type}' == 'design'){
									window.location = '${base}/workflow/formProp/designView?formCode=${formCode}';
								}
								else
								{
									window.location = '${base}/workflow/formProp/list?formCode=${formCode}';
								}
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
<link href="${res}/js/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet" />
<script src="${res}/js/bootstrap3-editable/js/bootstrap-editable.js"></script>
<script src="${res}/js/bootstrap3-editable/js/bootstrap-table-editable.js"></script>
<script type="text/javascript">
var editConfig = '${formProp.inputConfig}';
var inputType = '${formProp.inputType}';
var multiple = false;
$(document).ready(function(){
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
	initInputType(inputType);
});

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
		
		if(value == 'MTEXT')$('#optionRows1-1').show();
		//加载数据
		var config = JSON.parse(editConfig);
		$('#minLength').text(config.minLength);
		$('#maxLength').text(config.maxLength);
		$('#rows').text(config.rows);
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
		var config = JSON.parse(editConfig);
		$('#min').text(config.min);
		$('#max').text(config.max);
		$('#step').text(config.step);
		$('#precision').text(config.precision);
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
		var config = JSON.parse(editConfig);
		//显示多选选项
		sourceTypeChange(config.sourceType);
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
		var config = JSON.parse(editConfig);
		$('#format').text(config.format);
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
		var config = JSON.parse(editConfig);
		$('#scope').text(config.scope);
		
		multiple = config.multiple;
		if(multiple)
		{
			$('#multiple').text('是');
		}
		else
		{
			$('#multiple').text('否')
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
		var config = JSON.parse(editConfig);
		$('#limit').text(config.limit);
		$('#ext').text(config.ext);
	}
}

//实现下拉选择加载数据
function sourceTypeChange(value){
	//显示选项相关属性
	$('#optionRows4').show();
	$('#dictName').show();
	$('#sourceName').show();
	
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
	}
	
	//编辑框的模式：支持popup和inline两种模式，默认是popup
 	var labelCol = { field: "name", title: "文本", align: "center", width: 100};
	var valueCol = {field: "value", title: "值", align: "center", width: 100};
	var defaultCol = {field: "default", title: "是否默认", width: 50, formatter: defaultFormatter};
	
	var columns = [];
	columns.push(labelCol);
	columns.push(valueCol);
	columns.push(defaultCol);
	//初始化表格
	$("#optionTable").bootstrapTable({
        idField: "name",
        pagination: false,
        search: false,
        showRefresh: false,
        clickToSelect: true,
        columns: columns
    });
	
	//加载数据
	var config = JSON.parse(editConfig);
	$('#sourceType').text(config.sourceType);
	multiple = config.multiple;
	if(multiple)
	{
		$('#multiple').text('是');
	}
	else
	{
		$('#multiple').text('否')
	}
	
	$('#sourceName').text(config.sourceName);
	//加载数据
	if(config.sourceType == 'manual')
	{
		//将值设置到data中
		var data = [];
		$.each(config.options, function(i, v){
			var obj = {
					name: v.name,
					value: v.value
			};
			data.push(obj);
		});
		initBootStrapTable(data);
	}
	
	//判断 当前类型下  字典名称存在， 初始化表格
	var sourceName = config.sourceName;
	if(null != sourceName && '' != sourceName)
	{
		$('#optionRows4').show();
		//初始化表格
		sourceNameBlur(sourceName, config.sourceType);
	}
}

//输入字典选名称时，离开触发
function sourceNameBlur(val, code){
	
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

//初始化选项
function defaultFormatter(value, row, index){
	var multipleCheck = $("input[name='multiple']:checked").val();
	if(inputType == 'CHECK' || multiple)
	{
		return '<div style="width:100%; hight: 100%; text-align:center"><input type="checkbox" disabled="disabled" id="multiple' + index + '" name="multipleDef" value="' + index + '" /></div>';
	}
	else
	{
		return '<div style="width:100%; hight: 100%; text-align:center"><input type="radio" disabled="disabled" id="multiple' + index + '" name="multipleDef" value="' + index + '" /></div>';
	}
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
</script>
</body>
</html>
