<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>集群节点状态表</title>
<x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="服务器列表" subTitle="" col="12" collapsible="true">
			<bf:formGroup id="groupDiv" cls="col-sm-12">
			</bf:formGroup>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script src="${res}/js/echarts/echarts.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	//初始化加载节点状态数据
	load();
	
	//定时器每隔20秒执行
	setInterval("load()", 20000);
});

//加载服务器集群数据
function load(){
	$.ajax({
		type: 'GET',
		url: '${base}/sch/cluster/queryClusterAndNodeAll',
		dataType: 'json',
		success: function(data){
			$('#groupDiv').empty();
			//加载数据 包含
			$.each(data.clusters, function(i, v){
				//循环节点
				$.each(data.nodes, function(j, n){
					if(v.code == n.clusterCode)
					{	// btn btn-default 按钮样式
						var html = '<div class="ibox float-e-margins col-sm-5" style="border: 1px solid #E7EAEC; clear: inherit; margin-left: 5px; border-radius: 5px;">';
						html += '<div class="ibox-title" style="height: 10px; padding: 5px 5px 5px 5px; min-height: 20px;">';
						html += '<h5><small>' + v.name + ' - ' + n.name + '</small></h5>';
						if(n.status == 'ONLINE') html += '<a href="javaScript: viod(0);" class="btn btn-large btn-primary disabled" style="margin-left:140px;height: 20px;line-height:50%;font-size:12px;">启动</a>';
						else if(n.status == 'OFFLINE')  html += '<a href="javaScript:startHandler(\'' + n.code + '\');" class="btn btn-large btn-primary" style="margin-left:140px;height: 20px;line-height:50%;font-size:12px;">启动</a>';
						else html += '<a href="javaScript: viod(0);" class="btn btn-large btn-primary disabled" style="margin-left:140px;height: 20px;line-height:50%;font-size:12px;">启动</a>';
						if(n.status == 'ONLINE') html += '<a href="javaScript:stopHandler(\'' + n.code + '\');" class="btn btn-large btn-primary" style="margin-left:10px;height: 20px;line-height:50%;font-size:12px;">停止</a>';
						else if(n.status == 'OFFLINE')  html += '<a href="javaScript: viod(0);" class="btn btn-large btn-primary disabled" style="margin-left:10px;height: 20px;line-height:50%;font-size:12px;">停止</a>';
						else html += '<a href="javaScript: viod(0);" class="btn btn-large btn-primary disabled" style="margin-left:10px;height: 20px;line-height:50%;font-size:12px;">停止</a>';
						html += '</div>';
						html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;">';
						html += '<label class="control-label col-xs-5">状态: ';
						if(n.status == 'ONLINE') html += '<i class="fa fa-circle text-navy" title="正常"></i>';
						else if(n.status == 'OFFLINE') html += '<i class="fa fa-circle text-danger" title="断开"></i>';
						else html += '<i class="fa fa-circle text-warning" title="重连中"></i>';
						html +='</label>';
						html += '<label class="control-label col-xs-4">当前任务数: ' + (n.jobCount == null ? 0 : n.jobCount) + '</label>';
						html += '</div>';
						html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;text-align: center;">';
						html += '<div id="' + n.code + 'Memory" class="col-xs-6" style="height: 200px;"></div>';
						html += '<div id="' + n.code + 'Cpu" class="col-xs-6" style="height: 200px;"></div>';
						html += '<div id="' + n.code + 'Jvm" class="col-xs-6" style="height: 200px;"></div>';
						html += '<div id="' + n.code + 'Disk" class="col-xs-6" style="height: 200px;"></div>';
						html += '</div>';
				/* 		html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;text-align: center;">';
						html += '<button class="btn btn-white btn-xs btnCluster" id="' + n.code + 'Node" ';
						if(n.status == 'ONLINE') html += ' onclick="startHandler(\'' + n.code + '\'); disabled="true"';
						else if(n.status == 'OFFLINE')  html += ' onclick="startHandler(\'' + n.code + '\');"';
						else html += ' onclick="startHandler(\'' + n.code + '\'); disabled="true"';
						html += 'data-placement="top">启动</button>';
						html += '<button class="btn btn-white btn-xs btnCluster" id="' + n.code + 'Node"';
						if(n.status == 'ONLINE') html += ' onclick="stopHandler(\'' + n.code + '\');" ';
						else if(n.status == 'OFFLINE')  html += 'onclick="stopHandler(\'' + n.code + '\');" disabled="true" ';
						else html += ' onclick="stopHandler(\'' + n.code + '\');" disabled="true"';
						html += ' data-placement="top">停止</button>';
						html += '</div>'; */
						html += '</div>';
						$('#groupDiv').append(html);
					
						var optionM = '{"tooltip":{"formatter":"{a} <br/>{c} {b}"},"toolbox":{"show":false},"series":[{"name":"内存","type":"gauge","z":3,"min":0,"max":100,"splitNumber":10,"radius":"100%","axisLine":{"lineStyle":{"width":3}},"axisTick":{"length":8,"lineStyle":{"color":"auto"}},"splitLine":{"length":12,"lineStyle":{"color":"auto"}},"title":{"textStyle":{"fontSize":15},"offsetCenter":[0, 60]},"detail": {"formatter": "{value}%", "textStyle":{"fontSize":15}},"data":[{"value":' + (n.memoryUsage == null || n.status != 'ONLINE' ? 0 : n.memoryUsage) + ',"name":"内存"}]}]}';
						initchart(n.code + 'Memory', optionM);
						var optionC = '{"tooltip":{"formatter":"{a} <br/>{c} {b}"},"toolbox":{"show":false},"series":[{"name":"CPU","type":"gauge","z":3,"min":0,"max":100,"splitNumber":10,"radius":"100%","axisLine":{"lineStyle":{"width":3}},"axisTick":{"length":8,"lineStyle":{"color":"auto"}},"splitLine":{"length":12,"lineStyle":{"color":"auto"}},"title":{"textStyle":{"fontSize":15},"offsetCenter":[0, 60]},"detail": {"formatter": "{value}%", "textStyle":{"fontSize":15}},"data":[{"value":' + (n.cpuUsage == null || n.status != 'ONLINE' ? 0 : n.cpuUsage) + ',"name":"CPU"}]}]}';
						initchart(n.code + 'Cpu', optionC);
						var jvmUsage = n.totalJvmMemory - n.freeJvmMemory;
						var _jvm = jvmUsage/1024 > 1 ? 'k' : 'b';
						_jvm = (_jvm == 'k' ? (jvmUsage/1024/1024 > 1 ? 'm' : 'k') : 'k');
						console.info(_jvm + ':' + n.freeJvmMemory);
						var _total = n.totalJvmMemory;
						var _usage = jvmUsage;
						if(_jvm == 'k')
						{
							_total = n.totalJvmMemory/1024;
							_usage = _usage/1024;
						}
						else if(_jvm == 'm')
						{
							_total = n.totalJvmMemory/1024/1024;
							_usage = _usage/1024/1024;
						}
						_total = _total.toString() == '0' ? '100' : _total.toString();
						_usage = _usage.toString();
						if(_total.indexOf('.') > -1) _total = _total.substr(0, _total.indexOf('.')); 
						if(_usage.indexOf('.') > -1) _usage = _usage.substr(0, _usage.indexOf('.') + 2); 
						var optionJ = '{"tooltip":{"formatter":"{a} <br/>{c} {b}"},"toolbox":{"show":false},"series":[{"name":"JVM","type":"gauge","z":3,"min":0,"max":' + _total + ',"radius":"100%","axisLine":{"lineStyle":{"width":3}},"axisTick":{"length":8,"lineStyle":{"color":"auto"}},"splitLine":{"length":12,"lineStyle":{"color":"auto"}},"title":{"textStyle":{"fontSize":15},"offsetCenter":[0, 60]},"detail": {"formatter": "{value}' + _jvm + '", "textStyle":{"fontSize":15}},"data":[{"value":' + (n.status != 'ONLINE' ? 0 : _usage) + ',"name":"JVM"}]}]}';
						initchart(n.code + 'Jvm', optionJ); //parseInt(_total/10) < 1 ? 1 : parseInt(_total/10)
						var optionD = '{"tooltip":{"formatter":"{a} <br/>{c} {b}"},"toolbox":{"show":false},"series":[{"name":"磁盘","type":"gauge","z":3,"min":0,"max":100,"splitNumber":10,"radius":"100%","axisLine":{"lineStyle":{"width":3}},"axisTick":{"length":8,"lineStyle":{"color":"auto"}},"splitLine":{"length":12,"lineStyle":{"color":"auto"}},"title":{"textStyle":{"fontSize":15},"offsetCenter":[0, 60]},"detail": {"formatter": "{value}%", "textStyle":{"fontSize":15}},"data":[{"value":' + (n.diskUsage == null || n.status != 'ONLINE' ? 0 : n.diskUsage) + ',"name":"磁盘"}]}]}';
						initchart(n.code + 'Disk', optionD);
					}
				});
			});
		}
	});
}

//启动
function startHandler(code){
	$.mask.post('${base}/sch/nodeState/start/' + code, function(data){
		if(data.success)
		{
			$('#nodeStateTable').jTable('refresh');
			$.messager.alert('提示', data.msg, 'info')
		}
		else
		{
			$.messager.alert('错误', data.msg, 'error');
		}
	});
}

//停止
function stopHandler(code){
	$.mask.post('${base}/sch/nodeState/stop/' + code, function(data){
		if(data.success)
		{
			$('#nodeStateTable').jTable('refresh');
			$.messager.alert('提示', data.msg, 'info')
		}
		else
		{
			$.messager.alert('错误', data.msg, 'error');
		}
	});
}

//初始化图表
function initchart(id, chartOptions){
	
	var dom = document.getElementById(id);
	var myChart = echarts.init(dom);
	option = null;
	option = $.parseJSON(chartOptions);
	if (option && typeof option === "object") 
	{
		myChart.setOption(option, true);
	}
}
</script>
</body>
</html>