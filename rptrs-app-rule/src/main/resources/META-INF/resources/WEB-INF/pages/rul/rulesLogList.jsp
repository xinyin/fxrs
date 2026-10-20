<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>规则引擎的调用日志</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="规则总体统计" subTitle="" col="12" collapsible="true">
			<div class="ibox float-e-margins col-sm-3" style="border: 1px solid #E7EAEC; clear: inherit; margin-left: 2%; border-radius: 5px;">
					<div class="ibox-title" style="height: 10px; padding: 5px 5px 5px 5px; min-height: 20px;">
						<h5 style="font-weight: normal;">规则调用量</h5>
					</div>
					<div class="ibox-content" style="padding: 5px 5px 5px 5px;">
						<label class="control-label col-xs-12" style="text-align: right;height: 30px;line-height: 30px;font-size: 18px;color: #FF8C00">177,222</label>
					</div>
					<div class="ibox-content" style="padding: 5px 5px 5px 5px; text-align: center;">
						<div class="col-xs-6" style="height: 50px;text-align: left;line-height: 50px;">环比： <label class="control-label" style="color: #EE4000">5%</label></div>
						<div class="col-xs-6" style="height: 50px;text-align: left;line-height: 50px;">同比： <label class="control-label" style="color: #71C671">2%</label></div>
					</div>
			</div>
			<div class="ibox float-e-margins col-sm-3" style="border: 1px solid #E7EAEC; clear: inherit; margin-left: 3%; border-radius: 5px;">
					<div class="ibox-title" style="height: 10px; padding: 5px 5px 5px 5px; min-height: 20px;">
						<h5 style="font-weight: normal;">规则平均执行时间</h5>
					</div>
					<div class="ibox-content" style="padding: 5px 5px 5px 5px;">
						<label class="control-label col-xs-12" style="text-align: right;height: 30px;line-height: 30px;font-size: 18px;color: #548B54">10ms</label>
					</div>
					<div class="ibox-content" style="padding: 5px 5px 5px 5px; text-align: center;">
						<div class="col-xs-6" style="height: 50px;text-align: left;line-height: 50px;">环比： <label class="control-label" style="color: #EE4000">5%</label></div>
						<div class="col-xs-6" style="height: 50px;text-align: left;line-height: 50px;">同比： <label class="control-label" style="color: #71C671">2%</label></div>
					</div>
				</div>
			<div class="ibox float-e-margins col-sm-3" style="border: 1px solid #E7EAEC; clear: inherit; margin-left: 3%; border-radius: 5px;">
					<div class="ibox-title" style="height: 10px; padding: 5px 5px 5px 5px; min-height: 20px;">
						<h5 style="font-weight: normal;">规则调用量</h5>
					</div>
					<div class="ibox-content" style="padding: 5px 5px 5px 5px;">
						<label class="control-label col-xs-12" style="text-align: right;height: 30px;line-height: 30px;font-size: 18px;color:#1E90FF">177,222</label>
					</div>
					<div class="ibox-content" style="padding: 5px 5px 5px 5px; text-align: center;">
						<div class="col-xs-6" style="height: 50px;text-align: left;line-height: 50px;">已部署： <label class="control-label" style="color: #EE4000">15</label> 个</div>
						<div class="col-xs-6" style="height: 50px;text-align: left;line-height: 50px;">编辑中： <label class="control-label" style="color: #71C671">15</label> 个</div>
					</div>
			</div>
		</bc:ibox>
		<bc:ibox title="规则模型分析" subTitle="" col="12" collapsible="true">
			<table data-toggle="table" data-url="data1.json">
			    <thead>
			        <tr>
			            <th data-field="id">编号</th>
			            <th data-field="model">模型</th>
			            <th data-field="score">是否评分</th>
			            <th data-field="time">最大处理时间</th>
			            <th data-field="error">错误率</th>
			            <th data-field="avgTime">平均处理时间</th>
			            <th data-field="status" align="center">状态</th>
			        </tr>
			    </thead>
			    <tbody>
			    	<tr>
			    		<td>1</td>
			    		<td>分派模型1</td>
			    		<td>否</td>
			    		<td>3s</td>
			    		<td>0.0000%</td>
			    		<td>16ms</td>
			    		<td>正常</td>
			    	</tr>
			    	<tr>
			    		<td>2</td>
			    		<td>分派模型2</td>
			    		<td>否</td>
			    		<td>670ms</td>
			    		<td>0.0002%</td>
			    		<td>18ms</td>
			    		<td>正常</td>
			    	</tr>
			    	<tr>
			    		<td>3</td>
			    		<td>评分模型1</td>
			    		<td>是</td>
			    		<td>0</td>
			    		<td>0</td>
			    		<td>0</td>
			    		<td>未部署</td>
			    	</tr>
			    </tbody>
			</table>
		</bc:ibox>
		<bc:ibox title="规则部署情况" subTitle="" col="12" collapsible="true">
		<div id="optionId" style="width: 100%;height: 300px;margin-bottom: 10px;">
		
		</div>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script src="${res}/js/echarts/echarts.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	var option = {
		     title: {
		        text: '访问量',
		        left:'center'
		    },
		    tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['访问量']
            },
            toolbox: {
                show : true,
                feature : {
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
		    color: ['#3398DB'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            name: '周',
		            data : ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name: '访问量'
		        }
		    ],
		    series : [
		        {
		            name:'直接访问',
		            type:'bar',
		            barWidth: '50%',
		            data:[10, 52, 46, 57, 45, 34, 57]
		        }
		    ]
		};

	
	initchart('optionId', JSON.stringify(option));
});

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