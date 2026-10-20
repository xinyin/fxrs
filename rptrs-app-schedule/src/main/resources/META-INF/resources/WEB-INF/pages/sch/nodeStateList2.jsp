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
//脚本
var optionObj =  {
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            animation: false
	        }
	    },
	    xAxis: {
	        type: 'time',
	        name: '时间',
	        splitLine: {
	            show: false
	        },
	        axisLabel:{
	            formatter: function (value, index) {
	                var date = new Date(value);
	                var texts = [date.getHours(), date.getMinutes(), date.getSeconds()];
                    if (index === 0)
                    {
                        texts.unshift(date.getDate());
                    }
	                return texts.join(':');
	            }
	        },
	        axisTick: {
	            interval: 2,
	            inside: true
	        }
	    },
	    yAxis: {
	        type: 'value',
	        name: '百分比',
	        boundaryGap: [0, '100%'],
	        splitLine: {
	            show: false
	        },
	         axisTick: {
	            inside: true
	        }
	    },
	    series: []
	};
//用于存放所有data数据
var dataAll = [];
$(document).ready(function(){
	//初始化加载节点状态数据
	load();
	
	//定时器每隔20秒执行
	setInterval("updateLoad()", 10000);
	
	//	initchart('xcw', JSON.stringify(option));
	
	//setInterval("updateLoad()", 2000);
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
					{
						var html = '<div class="ibox float-e-margins col-sm-5" style="border: 1px solid #E7EAEC; clear: inherit; margin-left: 5px; border-radius: 5px;">';
						html += '<div class="ibox-title" style="height: 10px; padding: 5px 5px 5px 5px; min-height: 20px;">';
						html += '<h5><small>' + v.name + ' - ' + n.name + '</small></h5>';
						html += '</div>';
						html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;">';
						html += '<label class="control-label col-xs-4">状态: ';
						if(n.status == 'ONLINE') html += '<i class="fa fa-circle text-navy" title="正常"></i>';
						else if(n.status == 'OFFLINE') html += '<i class="fa fa-circle text-danger" title="断开"></i>';
						else html += '<i class="fa fa-circle text-warning" title="重连中"></i>';
						html +='</label>';
						html += '<label class="control-label col-xs-5">当前任务数: ' + (n.jobCount == null ? 0 : n.jobCount) + '</label>';
						html += '</div>';
						html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;text-align: center;">';
						html += '<div id="' + n.code + 'Charts" class="col-xs-12" style="height: 300px;"></div>';
						html += '</div>';
						html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;text-align: center;">';
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
						html += '</div>';
						html += '</div>';
						$('#groupDiv').append(html);
					
						var date = new Date();
					    var dateD =  [date.getFullYear(), (date.getMonth() + 1), date.getDate()].join('/');
					    var dateT =  [date.getHours(), date.getMinutes(), date.getSeconds()].join(':');
					    
					    //获取series 数据
					    var $series = optionObj.series;
						//内存
						var $dataMs = new Array();
						var $dataM = {
								name: (dateD + ' ' + dateT),
					            value: [(dateD + ' ' + dateT), (n.memoryUsage == null ? 0 : n.memoryUsage)]
						};
						$dataMs.push($dataM);
						var $objM = {
								name: "内存",
						        type: "line",
						        showSymbol: true,
						        hoverAnimation: false,
						        symbolSize: 15,
						        data: $dataMs
						}
						$series.push($objM);
						
						//CPU
						var $dataCs = new Array();
						var $dataC = {
								name: (dateD + ' ' + dateT),
					            value: [(dateD + ' ' + dateT), (n.cpuUsage == null ? 0 : n.cpuUsage)]
						};
						$dataCs.push($dataC);
						var $objC = {
								name: "CPU",
						        type: "line",
						        showSymbol: true,
						        hoverAnimation: false,
						        symbolSize: 15,
						        data: $dataCs
						}
						$series.push($objC);
						
						//硬盘
						var $dataDs = new Array();
						var $dataD = {
								name: (dateD + ' ' + dateT),
					            value: [(dateD + ' ' + dateT), (n.diskUsage == null ? 0 : n.diskUsage)]
						};
						$dataDs.push($dataD);
						var $objD = {
								name: "磁盘",
						        type: "line",
						        showSymbol: true,
						        hoverAnimation: false,
						        symbolSize: 15,
						        data: $dataDs
						}
						$series.push($objD);
						//缓存在当前页面
						var obj = {
								id: n.code,
								data: optionObj
						}
						dataAll.push(obj);
						
						initchart(n.code + 'Charts', optionObj);
					}
				});
			});
		}
	});
}


function updateLoad(){
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
					{
						var html = '<div class="ibox float-e-margins col-sm-5" style="border: 1px solid #E7EAEC; clear: inherit; margin-left: 5px; border-radius: 5px;">';
						html += '<div class="ibox-title" style="height: 10px; padding: 5px 5px 5px 5px; min-height: 20px;">';
						html += '<h5><small>' + v.name + ' - ' + n.name + '</small></h5>';
						html += '</div>';
						html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;">';
						html += '<label class="control-label col-xs-4">状态: ';
						if(n.status == 'ONLINE') html += '<i class="fa fa-circle text-navy" title="正常"></i>';
						else if(n.status == 'OFFLINE') html += '<i class="fa fa-circle text-danger" title="断开"></i>';
						else html += '<i class="fa fa-circle text-warning" title="重连中"></i>';
						html +='</label>';
						html += '<label class="control-label col-xs-5">当前任务数: ' + (n.jobCount == null ? 0 : n.jobCount) + '</label>';
						html += '</div>';
						html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;text-align: center;">';
						html += '<div id="' + n.code + 'Charts" class="col-xs-12" style="height: 300px;"></div>';
						html += '</div>';
						html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;text-align: center;">';
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
						html += '</div>';
						html += '</div>';
						$('#groupDiv').append(html);
					    //获取series 数据
					    var option = setData(n.code, n);
						//缓存在当前页面
						var obj = {
								id: n.code,
								data: option
						}
						dataAll.push(obj);
						
						initchart(n.code + 'Charts', option);
					}
				});
			});
		}
	});
}

//获取数组中当前对象
function getObj(id){
	var obj = null;
	$.each(dataAll, function(i, v){
		if(v.id == id)
		{
			obj = v.data;
			return false;
		}
	})
	
	return obj;
}

function setData(id, n){
	var date = new Date();
    var dateD =  [date.getFullYear(), (date.getMonth() + 1), date.getDate()].join('/');
    var dateT =  [date.getHours(), date.getMinutes(), date.getSeconds()].join(':');
    
	var $obj = getObj(id);
    //获取series 数据
    var $series = $obj.series;
    $.each($series, function(i, v){
    	if(v.name == '内存')
    	{
    		var data = v.data;
    		var $dataM = {
    				name: (dateD + ' ' + dateT),
    	            value: [(dateD + ' ' + dateT), (n.memoryUsage == null ? 0 : n.memoryUsage)]
    		};
    		//大于5 截取掉前面的
    		if(data.length > 5)
    		{
    			data.slice((data.length - 5));
    		}
    		console.info(data);
    		data.push($dataM);
    	}
    	else if(v.name == 'CPU')
    	{
    		var data = v.data;
    		var $dataC = {
    				name: (dateD + ' ' + dateT),
    	            value: [(dateD + ' ' + dateT), (n.cpuUsage == null ? 0 : n.cpuUsage)]
    		};
    		//大于5 截取掉前面的
    		if(data.length > 5)
    		{
    			data.slice((data.length - 5));
    		}
    		data.push($dataC);
    	}
    	else if(v.name == '磁盘')
    	{
    		var data = v.data;
    		var $dataD = {
    				name: (dateD + ' ' + dateT),
    	            value: [(dateD + ' ' + dateT), (n.diskUsage == null ? 0 : n.diskUsage)]
    		};
    		//大于5 截取掉前面的
    		if(data.length > 5)
    		{
    			data.slice((data.length - 5));
    		}
    		data.push($dataD);
    	}
    });
    
     return $obj;
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
	
	option = chartOptions;
	if (option && typeof option === "object") 
	{
		myChart.setOption(option, true);
	}
}

//更新图表
function updatechart(id, data){
	
	var dom = document.getElementById(id);
	var myChart = echarts.init(dom);
	myChart.setOption({
	      series: [{
	            data: data
	        }]
	});
}
</script>
</body>
</html>