<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="../include/common.jsp"%>
	<br:css pack="list"/>
	<title>流程监控</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="流程总体统计" subTitle="" col="12" collapsible="true">
			<div id="div1" class="ibox float-e-margins col-sm-5" style="border: 1px solid #E7EAEC; clear: inherit; margin-left: 2%; border-radius: 5px;height: 200px;">

			</div>
			<div id="div2" class="ibox float-e-margins col-sm-5" style="border: 1px solid #E7EAEC; clear: inherit; margin-left: 3%; border-radius: 5px;height: 200px;">

			</div>
		</bc:ibox>

		<bc:ibox title="流程部署情况" subTitle="" col="12" collapsible="true">
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
	var option1 = {
        title : {
            text: '流程实例按状态统计',
            subtext: '',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:['已完成','运行中','已挂起','已终止','已退回']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1335
                        }
                    }
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'流程实例按状态',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:135, name:'已完成'},
                    {value:30, name:'运行中'},
                    {value:10, name:'已挂起'},
                    {value:5, name:'已终止'},
                    {value:15, name:'已退回'}
                ]
            }
        ]
    };
	initchart('div1', JSON.stringify(option1));


       var option2 = {
            title : {
                text: '流程实例按业务统计',
                subtext: '',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
                data:['后勤管理','公司业务','个人业务','信用卡业务','其他']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 30
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series : [
                {
                    name:'流程实例',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:5, name:'后勤管理'},
                        {value:30, name:'公司业务'},
                        {value:10, name:'个人业务'},
                        {value:25, name:'信用卡业务'},
                        {value:15, name:'其他'}
                    ]
                }
            ]
        };
        initchart('div2', JSON.stringify(option2));

        var option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data:['完成数','处理中','长时间未处理流程','无候选人流程']
            },
            toolbox: {
                show : true,
                orient: 'vertical',
                x: 'right',
                y: 'center',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : ['周一','周二','周三','周四','周五','周六','周日']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'完成数',
                    type:'bar',
                    data:[20, 32, 30, 34, 39, 33, 32]
                },
                {
                    name:'处理中',
                    type:'bar',
                    data:[12, 13, 10, 14, 9, 3, 21]
                },
                {
                    name:'长时间未处理流程',
                    type:'bar',
                    data:[2, 1, 9, 3, 2, 3, 1]
                },
                {
                    name:'无候选人流程',
                    type:'bar',
                    data:[0, 2, 1, 4, 0, 3, 1]
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