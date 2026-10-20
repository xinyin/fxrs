<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>调度任务管理</title>
<style type="text/css">
.btnCluster{
	background: white;
}
.btnCluster.active{
	background: #e7eaec;
}
</style>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
			<bf:hidden name="nodeScope" id="nodeScope" value="${jobEntity.nodeScope}"></bf:hidden>
			<bf:hidden name="status" id="status" value="${jobEntity.status}"></bf:hidden>
			<bf:hidden name="param" id="param"></bf:hidden>
			<bf:hidden name="cronExpression" id="cronExpression"></bf:hidden>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="任务编码" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="8" value="${jobEntity.code}" disabled="${opt == 'edit'}">
							<bv:remote url="${base}/sch/jobEntity/check" name="code" message="任务编码"/>
							<bv:notEmpty/>
							<bv:regexp regexp="^[a-zA-Z][a-zA-Z0-9_]*$" message="只能以英文字母开头，只能包含英文字母、数字、下划线"/>
							<bv:stringLength max="64" message="类别编码不能大于64"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${jobEntity.name}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="日历" col="4" formControl="true"/>
						<bf:select id="calendarCode" name="calendarCode" col="8" headEmpty="true" value="${jobEntity.calendarCode}" dataSource="custom" url="/sch/calendarDetail/queryAll">
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务类型" col="4" formControl="true"/>
						<bf:select id="groupCode" name="groupCode" col="8" value="${jobEntity.groupCode}" dataSource="custom" url="/sch/jobGroup/queryAll">
							<be:event type="select">initTableData('add');</be:event>
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="任务类型1" col="4" formControl="true"/>
						<bf:checkGroup id="jobType1" name="jobType1" col="8" value="${jobEntity.jobType1}">
							<bf:radio dataSource="fixedDict" url="JobType1Dict" checked="true"></bf:radio>
						</bf:checkGroup>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务类型2" col="4" formControl="true"/>
						<bf:checkGroup id="jobType2" name="jobType2" col="8" value="${jobEntity.jobType2}">
							<bf:radio dataSource="fixedDict" url="JobType2Dict" checked="true"></bf:radio>
						</bf:checkGroup>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="范围类型" col="4" formControl="true"/>
						<bf:checkGroup id="nodeScopeType" name="nodeScopeType" col="8" value="${jobEntity.nodeScopeType}">
							<bf:radio dataSource="fixedDict" url="NodeScopeTypeDict" checked="true" click="nodeScopeTypeClick()"></bf:radio>
						</bf:checkGroup>
					</bc:col>
					<bc:col col="6">
						<bc:label text="节点算法" col="4" formControl="true"/>
						<bf:checkGroup id="nodeStrategy" name="nodeStrategy" col="8" value="${jobEntity.nodeStrategy}">
							<bf:radio dataSource="fixedDict" url="NodeStrategyDict" checked="true"></bf:radio>
						</bf:checkGroup>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<div class="col-sm-10 col-sm-offset-2"  id="nodeScopeDiv">
						
					</div>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="开始日期" col="4" formControl="true"/>
						<bf:datebox id="startDate" name="startDate" col="8" startDate="${currentDate}" value="${jobEntity != null ? jobEntity.startDateString : currentDate}">
							<bv:notEmpty/>
							<be:event type="change">
								var startDate = $("input[name='startDate']").val();
								var endDate = $("input[name='endDate']").val();
								if(endDate)
								{
									var start= new Date(startDate.replace("-", "/").replace("-", "/")); 
									var end = new Date(endDate.replace("-", "/").replace("-", "/"));  
									if(start > end)
									{
										$.messager.toastr('提示', '任务开始日期必须小于任务结束日期。', 'error');
									}
								}
							</be:event>
						</bf:datebox>
					</bc:col>
					<bc:col col="6">
						<bc:label text="结束日期" col="4" formControl="true"/>
						<bf:datebox id="endDate" name="endDate" col="8" startDate="${currentDate}" value="${jobEntity.endDateString}">
							<be:event type="change">
							var startDate = $("input[name='startDate']").val();
							var endDate = $("input[name='endDate']").val();
							if(startDate)
							{
								var start= new Date(startDate.replace("-", "/").replace("-", "/")); 
								var end = new Date(endDate.replace("-", "/").replace("-", "/"));  
								if(start > end)
								{
									$.messager.toastr('提示', '任务结束日期必须大于任务开始日期。', 'error');
								}
							}
							</be:event>
						</bf:datebox>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="优先级" col="4" formControl="true"/>
						<bf:select name="priority" id="priority" col="8" value="${jobEntity.priority}">
							<bf:option text="1" value="1"/>
							<bf:option text="2" value="2"/>
							<bf:option text="3" value="3"/>
							<bf:option text="4" value="4"/>
							<bf:option text="5" value="5" selected="true"/>
							<bf:option text="6" value="6"/>
							<bf:option text="7" value="7"/>
							<bf:option text="8" value="8"/>
							<bf:option text="9" value="9"/>
							<bf:option text="10" value="10"/>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="超时时间" col="4" formControl="true"/>
						<bf:select id="timeout" name="timeout" col="5" value="${jobEntity.timeout}">
							<bv:notEmpty/>
						</bf:select>
						<bf:select id="timeoutUnit" name="timeoutUnit" col="3" value="${jobEntity.timeoutUnit}" dataSource="fixedDict" url="TimeoutUnitDict">
							<be:event type="select" >timeoutUnitClick(this)</be:event>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:label text="超时处理" col="2" formControl="true"/>
					<bf:checkGroup id="timeoutInstruction" name="timeoutInstruction" col="10" value="${jobEntity.timeoutInstruction}">
						<bf:radio dataSource="fixedDict" url="JobInstructionDict" checked="true"></bf:radio>
					</bf:checkGroup>
				</bf:formGroup>
				<bf:formGroup >
					<bc:label text="失败处理" col="2" formControl="true"/>
					<bf:checkGroup id="errorInstruction" name="errorInstruction" col="10" value="${jobEntity.errorInstruction}">
						<bf:radio dataSource="fixedDict" url="JobInstructionDict" checked="true"></bf:radio>
					</bf:checkGroup>
				</bf:formGroup>
				<bf:formGroup >
					<bc:label text="参数" col="2" formControl="true"/>
					<bc:col col="10">
						<table id="optionTable" >
						</table>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
						<bc:label text="任务表达式" col="2" formControl="true"/>
						<div id="jcron" data-toggle="jCron" data-data="${jobEntity.cronExpression }"></div>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="12">
						<bc:label text="备注" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="10" value="${jobEntity.remark}" multiple="true"></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button type="primary"  text="保存" icon="save" click="btnSave()"></bc:button>
						<bc:button  text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/sch/jobEntity/list';
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
//集群范围， 以逗号隔开
var clusterScopes = '';
//节点范围， 以逗号隔开
var nodeScopes = '';
var opt = '${opt}';
var paramJosn = '${jobEntity.param}';
var timeout = '${jobEntity.timeout}';
$(document).ready(function(){
	//禁用表单提交事件 btnSave 参考155行
	$("#form1").attr("onsubmit", "return false");
	//初始化超时时间
	timeoutUnitClick();
	//初始化类型选择
	nodeScopeTypeClick();
	/* 初始化参数表格*/
	initTable();
})

//选择超时时间单位后处理
function timeoutUnitClick(){
	var value = $('#timeoutUnit').val();
	$('#timeout').empty();
	//小时 1 -23
	if(value == 'HOUR')
	{
		for(var i = 1; i < 24; i++)
		{
			 $('#timeout').append('<option value="' + i + '">' + (i.toString().length < 2 ? '0' + i : i) + '</option>');
		}
	}//分钟 1 - 59
	else
	{
		for(var i = 1; i < 60; i++)
		{
			$('#timeout').append('<option value="' + i + '">' + (i.toString().length < 2 ? '0' + i : i) + '</option>');
		}
	}
	$('#timeout').val(timeout).trigger('change');
}

//初始化类型选择
function nodeScopeTypeClick(){
	//清空已选择的数据
	clusterScopes = '';
	nodeScopes = '';
	$('#nodeScopeDiv').empty();
	var nodeScopeType = $('input[name="nodeScopeType"]:checked').val();
	if(nodeScopeType == 'CLUSTER')
	{
		$.ajax({
			type: 'GET',
			url: '${base}/sch/cluster/queryAll',
			dataType: 'json',
			success: function(data){
				//加载数据 包含
				$.each(data, function(i, v){
					var html = '<button class="btn btn-white btn-xs btnCluster" id="' + v.value + 'Cluster" onclick="clickClusterView(this, \'' + v.value + '\')" data-placement="top">' + v.name + '</button>&nbsp;';
					$('#nodeScopeDiv').append(html);
				});
				//编辑加载数据
				if(opt == 'edit')
				{
					var nodeScope = $('#nodeScope').val();
					clusterScopes = nodeScope;
					var nodes = nodeScope.split(',');
					$.each(nodes, function(i, v){
						$('#' + v + 'Cluster').toggleClass('active');
					});
				}
			}
		});
	}
	else
	{
		$.ajax({
			type: 'GET',
			url: '${base}/sch/cluster/queryClusterAndNodeAll',
			dataType: 'json',
			success: function(data){
				//加载数据 包含
				$.each(data.clusters, function(i, v){
					var html = '<div class="ibox float-e-margins col-sm-3" style="border: 1px solid #E7EAEC;clear: inherit;margin-left: 5px;border-radius:5px;">';
					html += '<div class="ibox-title" style="height: 10px;padding: 5px 5px 5px 5px;min-height: 20px;"><h5><small>' + v.name + '</small></h5></div>';
					html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;">';
					//循环节点
					$.each(data.nodes, function(j, n){
						if(v.code == n.clusterCode)
						{
							html += '<button class="btn btn-white btn-xs btnCluster" id="' + n.code + 'Node" onclick="clickNodeView(this, \'' + n.code + '\')" data-placement="top">' + n.name + '</button>';
						}
					});
					html += '</div></div>';
					$('#nodeScopeDiv').append(html);
				});
				//编辑加载数据
				if(opt = 'edit')
				{
					var nodeScope = $('#nodeScope').val();
					nodeScopes = nodeScope;
					var ns = nodeScope.split(',');
					$.each(ns, function(i, v){
						$('#' + v + 'Node').toggleClass('active');
					});
				}
				
			}
		});
	}
}

// 选择集群处理
function clickClusterView(el, val){
	//如果当前选中包含，需要清除
	if($(el).hasClass('active'))
	{
		clusterScopes = clusterScopes.replace(val + ',', '');
	}
	else
	{
		clusterScopes += val + ',';
	}
	$(el).toggleClass('active');
}

//点击处理
function clickNodeView(el, val){
	//如果当前选中包含，需要清除
	if($(el).hasClass('active'))
	{
		nodeScopes = nodeScopes.replace(val + ',', '');
	}
	else
	{
		nodeScopes += val + ',';
	}
	$(el).toggleClass('active');
}

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
        columns: [
        	{ field: "code", title: "编码", align: "center", width: 100},
        	{field: "type", title: "类型", align: "center", width: 100, editable: { type: 'select',  disabled: true,  mode: "type", source: [{ value: "string", text: "字符串" }, { value: "number", text: "数字" }, {value:"date", text:"日期"}], title: '参数类型'}},
        	{ field: "value", title: "值", width: 100, formatter: valFormatter}]
    });
	
	//加载数据，新增加载类型数据， 编辑加载原始数据， 选择类型后加载累心数据
	initTableData(opt);
}

//格式化值
function valFormatter(value, row, index){
	if(row.type == 'date')
	{
		return '<a href="javascript:void(0);" id="' + row.code + 'Id">' + value + '</a>';
	}
	else
	{
		return '<a href="javascript: void(0);" id="' + row.code + 'Id">' + value + '</a>';
	}
}

//加载数据，新增加载类型数据， 编辑加载原始数据， 选择类型后加载累心数据
function initTableData(type){
	
	//判断是否是编辑， 编辑加载数据
	if(type == 'edit')//加载表中数据
	{
		var rows = JSON.parse(paramJosn);
		$('#optionTable').bootstrapTable('load', rows);
		editParam(rows);
	}
	else//新增加载类型数据
	{
		var code = $('#groupCode').val();
		$.ajax({
			type: 'GET',
			url: '${base}/sch/jobGroup/getJobGroup/' + code,
			dataType: 'json',
			success: function(data){
				var rows = JSON.parse(data.param);
				$('#optionTable').bootstrapTable('load', rows);
				editParam(rows);
			}
		});
	}
}

//编辑参数
function editParam(rows){
	$.each(rows, function(i, r){
		if(r.type == 'date')
		{
			$('#' + r.code + 'Id').editable({
		        type: "date",                //编辑框的类型。支持text|textarea|select|date|checklist等
		        title: "值",              //编辑框的标题
		        mode: "popup"              //编辑框的模式：支持popup和inline两种模式，默认是popup
		    });
		}
		else
		{
			$('#' + r.code + 'Id').editable({
		        type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等
		        title: "值",              //编辑框的标题
		        mode: "popup",             //编辑框的模式：支持popup和inline两种模式，默认是popup
		        validate: function (v) {
		        	//验证是数字
					if(r.type == 'number')
					{
						var reg = new RegExp("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
						return !reg.test(v) ? '请输入数字' : false;
					}
                }
		    });
		}
	});
}

//保存
function btnSave(){
	$('#form1').data('formValidation').validate();
	var isValid = $('#form1').data('formValidation').isValid();
	if(isValid)
	{
		//任务开始时间必须小于结束时间
		var startDate = $("input[name='startDate']").val();
		var endDate = $("input[name='endDate']").val();
		if(endDate)
		{
			var start= new Date(startDate.replace("-", "/").replace("-", "/"));  
			var end = new Date(endDate.replace("-", "/").replace("-", "/"));  
			if(start > end)
			{
				$.messager.toastr('提示', '任务开始时日期须小于任务结束日期。', 'error');
				return false;
			}
		}
		
		//获取节点范围
		var nodeScopeType = $('input[name="nodeScopeType"]:checked').val();
		if(nodeScopeType == 'CLUSTER')
		{
			if(!clusterScopes)
			{
				$.messager.toastr('提示', '请选择任务执行的范围。', 'error');
				return false;
			}
			//clusterScopes = clusterScopes.substring(0, clusterScopes.length -1);
			$('#nodeScope').val(clusterScopes);
		}
		else
		{
			if(!nodeScopes)
			{
				$.messager.toastr('提示', '请选择任务执行的范围。', 'error');
				return false;
			}
			//nodeScopes = nodeScopes.substring(0, nodeScopes.length -1);
			$('#nodeScope').val(nodeScopes);
		}
		
		//获取参数
		var rows =  $('#optionTable').bootstrapTable('getData');
		$.each(rows, function(i, v){
			v.value = $('#' + v.code + 'Id').html();
		});
		var param = JSON.stringify(rows);
		$('#param').val(param);
		
		//获取表达式
		var cron =  $('#jcron').jCron('getCron');
		$('#cronExpression').val(cron);
		var data = $('#form1').serializeAll();
		 $.mask.post('${base}/sch/jobEntity/save', data, function(data){
			if(data.success)
			{
				$.messager.alert('提示', '保存成功。', 'info', function(){
					window.location = '${base}/sch/jobEntity/list';
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
