<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>集群管理</title>
<style type="text/css">

</style>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
				<bf:formGroup type="success">
					<bc:col col="6">
						<bc:label text="日历编码" col="4" formControl="true"/>
						<bc:label text="${calendarDetail.code}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="日历名称" col="4" formControl="true"/>
						<bc:label text="${calendarDetail.name}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:label text="排除星期" col="2" formControl="true"/>
					<bc:col col="10">
						<button class="btn btn-white btn-xs btnWeek" id="2week" onclick="clickView(this, '2')" data-placement="top">星期一</button>
						<button class="btn btn-white btn-xs btnWeek" id="3week" onclick="clickView(this, '3')" data-placement="top">星期二</button>
						<button class="btn btn-white btn-xs btnWeek" id="4week" onclick="clickView(this, '4')" data-placement="top">星期三</button>
						<button class="btn btn-white btn-xs btnWeek" id="5week" onclick="clickView(this, '5')" data-placement="top">星期四</button>
						<button class="btn btn-white btn-xs btnWeek" id="6week" onclick="clickView(this, '6')" data-placement="top">星期五</button>
						<button class="btn btn-white btn-xs btnWeek" id="7week" onclick="clickView(this, '7')" data-placement="top">星期六</button>
						<button class="btn btn-white btn-xs btnWeek" id="1week" onclick="clickView(this, '1')" data-placement="top">星期日</button>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:label text="排除指定时间段" col="2" formControl="true"/>
					<bc:col col="10">
						<div id="optionTable_toolbar">
							<button id="optionTable_tb_add" type="button" onclick="addClick()" class=" btn btn-primary btn-outline">新增</button>
				        </div>
						<table id="optionTable" >
						</table>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<div style="height: 600px;">
					<bc:col col="2">
					</bc:col>
					<bc:col col="10">
						<div id='container'></div>
					</bc:col>
					</div>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回">
							<be:event>
								window.location = '${base}/sch/calendarDetail/list';
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
<link href="${res}/js/plugins/date.Calendar/stylesheets/simple-calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${res}/js/plugins/date.Calendar/javascripts/simple-calendar.js"></script>
<script type="text/javascript">
var code = '${calendarDetail.code}';
$(document).ready(function(){
	//禁用表单提交事件 btnSave 参考155行
	$("#form1").attr("onsubmit", "return false");
	
	var myCalendar = new SimpleCalendar('#container');
	//初始化星期
	initWeekResult();
	/* 初始化参数表格*/
	init();
})

//选中样式设置
function clickView(el, index){
	$(el).toggleClass('active');
	var isWeek = false;
	//判断当前是否选中
	if($(el).hasClass('active'))
	{
		isWeek = true;
	}
	
	//保存数据
	$.mask.post('${base}/sch/calendarDetail/week/set', {code: code, index: index, isWeek: isWeek}, function(data){
		if(data.success)
		{
			$.messager.toastr('提示', data.msg, 'info');
		}
		else
		{
			$.messager.toastr('错误', data.msg, 'error');
		}
	});
}

//初始化星期
function initWeekResult(){
	//加载数据
	$.ajax({
		type: 'GET',
		url: '${base}/sch/calendarDetail/week/' + code,
		dataType: 'json',
		success: function(result){
			$.each(result, function(i, v){
				$('#' + v + 'week').toggleClass('active');
			});
		}
	});
}

/* 初始化参数表格*/
function init(){
	//初始化表格
	$("#optionTable").bootstrapTable({
        toolbar: "#optionTable_toolbar",
        idField: "code",
        pagination: false,
        search: false,
        showRefresh: false,
        clickToSelect: true,
        columns: [
        	{ field: "start", title: "开始时间", align: "center", width: 100},
        	{field: "end", title: "结束时间", align: "center", width: 100},
        	{field: "operation", title: "操作", align: "center", width: 50, formatter: operFormatter}
        	]
    });
	
	//初始化加载时间段
	initTimeResult();
	
	//判断是否是编辑， 编辑加载数据
	if(opt == 'edit' && paramJosn)
	{
		var rows = JSON.parse(paramJosn);
		$('#optionTable').bootstrapTable('load', rows);
	}
}

//新增选项
function addClick(){
	$.messager.modal({
		winId: 'winAddTime',
		width: 800,
		height: 300,
		title: '排除时间段设置',
		url: '${base}/sch/calendarDetail/time/add/' + code,
		onClose: function(){
			initTimeResult();
		}
	});
}

//初始化时间段
function initTimeResult(){
	//加载数据
	$.ajax({
		type: 'GET',
		url: '${base}/sch/calendarDetail/time/' + code,
		dataType: 'json',
		success: function(result){
			var data = new Array();
			$.each(result, function(i, v){
				var start = (v.startHour.toString().length < 2 ? '0' + v.startHour : v.startHour) + ":" + (v.startMinute.toString().length < 2 ? '0' + v.startMinute : v.startMinute);
				var end = (v.endHour.toString().length < 2 ? '0' + v.endHour : v.endHour)  + ":" + (v.endMinute.toString().length < 2 ? '0' + v.endMinute : v.endMinute);
				var obj = {"start": start, "end": end};
				data.push(obj);
			});
			
			$("#optionTable").bootstrapTable('load', data);
		}
	});
}

//删除选项
function deleteClick(start, end){
	$.messager.confirm('提示', '确定要删除该时段吗？', function(r){
		if(r)
		{
			var starts = start.split(':');
			var ends = end.split(':');
			//加载数据
			$.ajax({
				type: 'GET',
				url: '${base}/sch/calendarDetail/time/remove?code=' + code + '&startHour=' + starts[0] + '&startMinute=' + starts[1] + '&endHour=' + ends[0] +'&endMinute=' + ends[1],
				dataType: 'json',
				success: function(data){
					if(data.success)
					{
						$.messager.toastr('提示', data.msg, 'info');
						initTimeResult();
					}
					else
					{
						$.messager.toastr('错误', data.msg, 'error');
					}
				}
			});
		}
	});
}

//处理删除按钮
function operFormatter(value, row, index){
	
	return '<a class="remove" href="javascript: deleteClick(\'' + row.start + '\',\'' + row.end + '\')" title="删除"><i class="fa fa-trash"></i></a>';
}

function initMonthDay(year, month){
	//加载数据
	$.ajax({
		type: 'GET',
		url: '${base}/sch/calendarDetail/days/' + code + '/' + month,
		dataType: 'json',
		success: function(data){
			if(data.success)
			{
				//加载数据 包含
				$.each(data.includes, function(i, v){
					$('#' + v + 'include').toggleClass('active');
					var obj = $('#' + v + 'include').parent().parent();
					$(obj).removeClass('sc-item');
					$(obj).addClass('sc-item-include');
				});
				//加载数据 排除
				$.each(data.excludes, function(i, v){
					$('#' + v + 'exclude').toggleClass('active');
					var obj = $('#' + v + 'exclude').parent().parent();
					$(obj).removeClass('sc-item');
					$(obj).addClass('sc-item-exclude');
				});
			}
		}
	});
}

//包含处理
function includeClick(el, i, day){
	//默认是未选中
	var isSelect = false;
	var obj = $(el).parent().parent();
	//如果当前选中排除，需要清除
	if($('#' + i + 'exclude').hasClass('active'))
	{
		$('#' + i + 'exclude').removeClass('active');
	}
		
	//设置选中包含
	$(el).toggleClass('active');
	
	//如果当前选默认颜色
	if($(obj).hasClass('sc-item'))
	{
		$(obj).removeClass('sc-item');
		$(obj).addClass('sc-item-include');
	}
	else if($(obj).hasClass('sc-item-include'))//如果当前已经包含， 需取消
	{
		$(obj).removeClass('sc-item-include');
		$(obj).addClass('sc-item');
		isSelect = true;
	}
	else if($(obj).hasClass('sc-item-exclude'))//如果当前排除
	{
		$(obj).removeClass('sc-item-exclude');
		$(obj).addClass('sc-item-include');
	}
	
	//保存数据
	$.mask.post('${base}/sch/calendarDetail/set/include/days', {code: code, date: day, isSelect: isSelect}, function(data){
		if(data.success)
		{
			$.messager.toastr('提示', data.msg, 'info');
		}
		else
		{
			$.messager.toastr('错误', data.msg, 'error');
		}
	});
}

//排除处理
function excludeClick(el, i, day){
	var isSelect = false;
	var obj = $(el).parent().parent();
	//如果当前选中包含，需要清除
	if($('#' + i + 'include').hasClass('active'))
	{
		$('#' + i + 'include').removeClass('active');
	}
	
	//设置选中排除
	$(el).toggleClass('active');
	
	//如果当前选默认颜色
	if($(obj).hasClass('sc-item'))
	{
		$(obj).removeClass('sc-item');
		$(obj).addClass('sc-item-exclude');
	}
	else if($(obj).hasClass('sc-item-exclude'))//如果当前已经排除， 需取消
	{
		$(obj).removeClass('sc-item-exclude');
		$(obj).addClass('sc-item');
		isSelect = true;
	}
	else if($(obj).hasClass('sc-item-include'))//如果当前包含
	{
		$(obj).removeClass('sc-item-include');
		$(obj).addClass('sc-item-exclude');
	}
	
	//保存数据
	$.mask.post('${base}/sch/calendarDetail/set/exclude/days', {code: code, date: day, isSelect: isSelect}, function(data){
		if(data.success)
		{
			$.messager.toastr('提示', data.msg, 'info');
		}
		else
		{
			$.messager.toastr('错误', data.msg, 'error');
		}
	});
}
</script>
</body>
</html>
