<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <link rel="stylesheet" type="text/css" href="${res}/css/dashLayout.css"/>
    <link rel="stylesheet" type="text/css" href="../../static/bootstrap/css/bootstrap.min.css"/>
    <title>资金运用投资余额预警信息</title>
    <style>
    </style>
    <%--<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>--%>
</head>
<body class="gray-bg">
<div >
    <div class="row" style="background-color:whitesmoke">

        <div id="contin" class="container">
                <%-- 总资产净资产展示 --%>
            <div class="row" style="padding-top: 10px">
                <div class="col-sm-3" style="height:80px;">
                    <div id="te1" style="width: 98%;height:80px;border: 1px;background-color:white">
                        <p align="center" style="padding-top: 10px" >总资产(上季末) <br><br><font color="black" size="5">${lastQuarter}</font>&nbsp;万元</p>
                    </div>
                    <br><br>
                    <div>
                    </div>
                </div>
                <div class="col-sm-3" style="height:80px;">
                    <div id="te2" style="width: 98%;height:80px;border: 1px ;background-color:white">
                        <p align="center" style="padding-top: 10px" >净资产(上季末) <br><br><font color="black" size="5">${lastQuarterNet}</font>&nbsp;万元</p>
                    </div>
                    <br><br>
                    <div>

                    </div>
                </div>
                <div class="col-sm-3" style="height:80px;">
                    <div id="te3" style="width: 98%;height:80px;border: 1px;background-color:white">
                        <p align="center" style="padding-top: 10px" >总资产(上年末) <br><br><font color="black" size="5">${allYear}</font>&nbsp;万元</p>
                    </div>
                    <br><br>
                    <div>

                    </div>
                </div>
                <div class="col-sm-3" style="height:80px;">
                    <div id="te4" style="width: 98%;height:80px;border: 1px ;background-color:white">
                        <p align="center" style="padding-top: 10px" >净资产(上年末) <br><br><font color="black" size="5">${allNet}</font>&nbsp;万元</p>
                    </div>
                    <br><br>
                    <div>

                    </div>
                </div>
            </div>

            <br><br>
                <%-- echart图--%>
            <div class="row" style="padding-top: 10px">
                    <%--环型图--%>
                <div class="col-sm-6" style="height:300px; ">
                    <div class="col-sm-12" style="width: 100%;height:30px;border: 1px;background-color:white">
                        <bf:formGroup>
                            <bc:col col="6">
                                <font style="align-content: center;font-size:large" >全部关联方投资余额监控</font>
                            </bc:col>
                            <bc:col col="6">
                                <font style="font-size:large;padding-left: 50%" color="blue">蓝色预警</font>
                            </bc:col>
                        </bf:formGroup>
                    </div>
                    <div class="col-sm-12" style="width: 100%;height:270px;border: 1px;background-color:white">
                        <div id="TotalPercent" class="col-xs-6"
                             style="width: 50%;height:270px;">
                        </div>
                        <div id="NetAssetsPercent" class="col-xs-6"
                             style="width: 50%;height:270px;">
                        </div>
                    </div>
                </div>
                    <%-- 柱状图1--%>
                <div class="col-sm-6" style="height:300px;background-color:white">
                    <div id="main" style="width: 98%;height:300px;">
                    </div>
                </div>
            </div>

            <br><br>

            <div class="row" style="padding-top: 10px">
                <%-- 柱状图2--%>
                <div class="col-sm-6" style="height:300px;background-color:white">
                    <div id="main1" style="width: 98%;height:300px;">
                    </div>
                </div>
                <%-- 柱状图3--%>
                <div class="col-sm-6" style="height:300px;background-color:white">
                    <div id="main2" style="width: 98%;height:300px;">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<br:js pack="list"/>
<be:processEvent/>
<script src="${res}/js/echarts/echarts.min.js" type="text/javascript"></script>
<script src="${res}/jep/js/jDashWidget/dashLayoutDesign.js" type="text/javascript"></script>

<script type="text/javascript">
    var ringTotalData;//环形图数据
    var ringNetData;//环形图数据
    $(document).ready(function () {
        /*全部关联方投资余额监控*/
        getAllFunds();
        /**/
    });

    //重置
    function frmReset() {
        window.location = '${base}/bus/fundsInvestmentSta/list';
    }
    /*全部关联方投资余额监控*/
    function getAllFunds(){
        $.mask.post('${base}/report/warning/getAllFunds', function (data) {
            if(data.success) {
                ringTotalData = [
                    { name: '已占额度', value: data.allInvestmentBalance},
                    { name: '剩余额度', value:data.totalCapital},
                ]
                ringNetData = [
                    { name: '已占额度', value: data.allInvestmentBalance},
                    { name: '剩余额度', value:data.netCaptial},
                ]
                getTotalPercent();
                getNetAssetsPercent();
            }
        });

    }

    /*全部关联方投资余额监控---上一年总资产占比*/
    function getTotalPercent() {
        option = {
            title: {
                text: '占上年末总资产比例',
                left: 'center',
                top: 'bottom'
            },
            color: ['#7179CB', '#afafaf'],
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                top: 10,
                left: 'center',
                data: ['已占额度', '剩余额度']
            },
            series: [
                {
                    type: 'pie',
                    name: '详情：',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        show: false,
                        position: 'right'
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    },
                    data: ringTotalData
                }
            ]
        };

        echarts.init(document.getElementById('TotalPercent')).setOption(option);
    }

    /*全部关联方投资余额监控---上一年净资产占比*/
    function getNetAssetsPercent() {
        option = {
            title: {
                text: '占上年末净资产比例',
                left: 'center',
                top: 'bottom'
            },
            color: ['#7179CB', '#afafaf'],
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                top: 10,
                left: 'center',
                data: ['已占额度', '剩余额度']
            },
            series: [
                {
                    type: 'pie',
                    name: '详情：',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        show: false,
                        position: 'right'
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    },
                    data: ringNetData
                }
            ]
        };

        echarts.init(document.getElementById('NetAssetsPercent')).setOption(option);
    }

    $.mask.post('${base}/report/warning/relTotalRat', function (data) {
        var isinder1 = [];//关联方
        var isinder2 = [];//累计金额
        var isinder3 = [];//重大金额totalCapital
        var isinder4 = [];//重大金额totalCapital
        var isinder5 = [];//重大金额totalCapital

        /*data = [{"relname": "qzp", "amount": 100, "level": 1000}, {
            'relname': "cyx",
            "amount": 900,
            "level": 1500
        }, {'relname': "fgc", "amount": 900, "level": 1050}];*/
        var JsonArry = data;
        for (i = 0; i < JsonArry.length; i++) {
            isinder1.push(JsonArry[i].relName);//关联方
            isinder2.push(JsonArry[i].corpInvestFund);//累计金额
            isinder3.push(JsonArry[i].totalCapital);//重大金额totalCapital
            isinder4.push(JsonArry[i].warningNumRed);//重大金额totalCapital
            isinder5.push(JsonArry[i].warningNumYellow);//重大金额totalCapital
        }
        option = {
            grid: {
                left: '60',
                right: '50',
                containLable: true,
            },
            color: ["#7179CB", "#4CCEFE"],
            legend: {
                type: 'scroll',				//----图例类型，默认为'plain'，当图例很多时可使用'scroll'
                right: '5%',					//----图例相对容器位置,top\bottom\left\right
                selected: {
                    '投资余额': true,			//----图例选择,图形加载出来会显示选择的图例，默认为true
                    '限额': true,			//----图例选择,图形加载出来会显示选择的图例，默认为true
                },
                textStyle: {					//----图例内容样式
                    color: '#000',				//---所有图例的字体颜色
                    //backgroundColor:'black',	//---所有图例的字体背景色
                },
                tooltip: {					//图例提示框，默认不显示
                    show: true,
                    color: 'red',
                },
                data: ["投资余额", "限额"],
            },
            //--------------   提示框 -----------------
            tooltip: {
                show: true,					//---是否显示提示框,默认为true
                trigger: 'item',				//---数据项图形触发
                axisPointer: {				//---指示样式
                    type: 'shadow',
                    axis: 'auto',
                },
                padding: 5,
                textStyle: {					//---提示框内容样式
                    color: "#fff",
                },
            },
            xAxis: {
                show: true,					//---是否显示
                position: 'bottom',			//---x轴位置
                offset: 0,					//---x轴相对于默认位置的偏移
                type: 'category',			//---轴类型，默认'category'
                name: '关联方名称',				//---轴名称
                nameLocation: 'end',			//---轴名称相对位置
                nameTextStyle: {				//---坐标轴名称样式
                    color: "#000000",
                    padding: [15, 0, 0, -15],	//---坐标轴名称相对位置
                },
                nameGap: 6,					//---坐标轴名称与轴线之间的距离
                //nameRotate:270,			//---坐标轴名字旋转
                axisLine: {					//---坐标轴 轴线
                    show: true,					//---是否显示
                    //------------------- 箭头 -------------------------
                    // symbol: ['none', 'arrow'],	//---是否显示轴线箭头
                    // symbolSize: [8, 8],			//---箭头大小
                    // symbolOffset: [0, 7],			//---箭头位置
                    //------------------- 线 -------------------------
                    lineStyle: {
                        color: '#000000',
                        width: 1,
                        type: 'solid',
                    },
                },
                axisTick: {					//---坐标轴 刻度
                    show: true,					//---是否显示
                    inside: true,				//---是否朝内
                    lengt: 3,					//---长度
                    lineStyle: {
                        //color:'red',			//---默认取轴线的颜色
                        width: 1,
                        type: 'solid',
                    },
                },
                axisLabel: {					//---坐标轴 标签
                    show: true,					//---是否显示
                    inside: false,				//---是否朝内
                    rotate: 0,					//---旋转角度
                    margin: 5,					//---刻度标签与轴线之间的距离
                    //color:'red',				//---默认取轴线的颜色
                    fontSize: 11,
                    interval: 0,             //横轴信息全部显示
                    formatter: function (c) {  //axisLabel里的formatter为function自己做文本截断
                        for (var i in c) {
                            return c.substring(0, 6);   //截取X轴坐标值的长度
                        }
                    }
                },
                splitLine: {					//---grid 区域中的分隔线
                    show: false,					//---是否显示，'category'类目轴不显示，此时我的X轴为类目轴，splitLine属性是无意义的
                },
                splitArea: {					//--网格区域
                    show: false,					//---是否显示，默认false
                },
                data: isinder1
            },
            yAxis: {
                show: true,					//---是否显示
                position: 'left',			//---y轴位置
                // offset: 0,					//---y轴相对于默认位置的偏移
                type: 'value',			//---轴类型，默认'category'
                name: '',				//---轴名称
                nameLocation: 'end',			//---轴名称相对位置value
                nameTextStyle: {				//---坐标轴名称样式
                    color: "#ffffff",
                    padding: [5, 0, 0, 5],	//---坐标轴名称相对位置
                },
                nameGap: 15,					//---坐标轴名称与轴线之间的距离
                //nameRotate:270,			//---坐标轴名字旋转

                axisLine: {					//---坐标轴 轴线
                    show: true,					//---是否显示
                    //------------------- 箭头 -------------------------
                    // symbol: ['none', 'arrow'],	//---是否显示轴线箭头
                    // symbolSize: [8, 8],			//---箭头大小
                    // symbolOffset: [0, 7],			//---箭头位置
                    //------------------- 线 -------------------------
                    lineStyle: {
                        color: '#ffffff',
                        width: 1,
                        type: 'solid',
                    },
                },
                axisTick: {					//---坐标轴 刻度
                    show: true,					//---是否显示
                    inside: true,				//---是否朝内
                    lengt: 3,					//---长度
                    lineStyle: {
                        //color:'red',			//---默认取轴线的颜色
                        width: 1,
                        type: 'solid',
                    },
                },
                axisLabel: {					//---坐标轴 标签
                    show: true,					//---是否显示
                    inside: false,				//---是否朝内
                    rotate: 0,					//---旋转角度
                    margin: 8,					//---刻度标签与轴线之间的距离
                    //color:'red',				//---默认取轴线的颜色
                },
                splitLine: {					//---grid 区域中的分隔线
                    show: true,					//---是否显示，'category'类目轴不显示，此时我的y轴为类目轴，splitLine属性是有意义的
                    lineStyle: {
                        color: '#666',
                        width: 1,
                        type: 'dashed',			//---类型
                    },
                },
                splitArea: {					//--网格区域
                    show: false,					//---是否显示，默认false
                }
            },
            //------------ 内容数据  -----------------
            series: [
                {
                    data: isinder3,
                    name: "投资余额",//---系列名称
                    legendHoverLink: true,		//---是否启用图例 hover 时的联动高亮
                    type: "bar",//---类型
                    label: {						//---图形上的文本标签
                        show: false,
                        position: 'insideTop',	//---相对位置
                        rotate: 0,				//---旋转角度
                        color: '#000',
                    },
                    itemStyle: {					//---图形形状
                        color: 'blue',
                        barBorderRadius: [18, 18, 0, 0],
                        normal: {
                            label: {
                                show: true, //开启显示
                                position: 'top', //在上方显示
                                textStyle: { //数值样式
                                    color: 'black',
                                    fontSize: 8
                                }
                            }
                        }
                    },
                    barWidth: '30',				//---柱形宽度
                    barCategoryGap: '15%',		//---柱形间距
                },
                {
                    barGap: "-100%", /*这里设置包含关系，只需要这一句话*/
                    data: isinder2,
                    name: "限额",
                    type: "bar",
                    legendHoverLink: true,		//---是否启用图例 hover 时的联动高亮
                    label: {						//---图形上的文本标签
                        show: false,
                        position: 'insideTop',	//---相对位置
                        rotate: 0,				//---旋转角度
                        color: '#000',
                    },
                    itemStyle: {					//---图形形状
                        color: 'blue',
                        barBorderRadius: [18, 18, 0, 0],
                        normal: {
                            label: {
                                show: true, //开启显示
                                position: 'top', //在上方显示
                                textStyle: { //数值样式
                                    color: 'black',
                                    fontSize: 8
                                }
                            }
                        }
                    },
                    barWidth: '30',				//---柱形宽度
                    barCategoryGap: '15%',		//---柱形间距
                }
            ]
        };
        echarts.init(document.getElementById('main')).setOption(option);
    });

    //资金运用类相关的数据
    $.mask.post('${base}/report/warning/fundsTotalRat', function (data) {
        var isinder1 = [];//关联方
        var isinder2 = [];//balance
        var isinder3 = [];//limit

        // data = [{"relname": "qzp", "amount": 100, "level": 1000}, {
        //     'relname': "cyx",
        //     "amount": 900,
        //     "level": 1500
        // }, {'relname': "fgc", "amount": 900, "level": 1050}];
        var JsonArry = data;
        for (i = 0; i < JsonArry.length; i++) {
            isinder1.push(JsonArry[i].relName);//关联方
            isinder2.push(JsonArry[i].balanceFunds);//
            isinder3.push(JsonArry[i].limitFunds);//
        }
        option = {
            grid: {
                left: '60',
                right: '50',
                containLable: true,
            },
            color: ["#7179CB", "#4CCEFE"],
            legend: {
                type: 'scroll',				//----图例类型，默认为'plain'，当图例很多时可使用'scroll'
                right: '1%',					//----图例相对容器位置,top\bottom\left\right
                selected: {
                    '投资余额': true,			//----图例选择,图形加载出来会显示选择的图例，默认为true
                    '限额': true,			//----图例选择,图形加载出来会显示选择的图例，默认为true
                },
                textStyle: {					//----图例内容样式
                    color: '#000',				//---所有图例的字体颜色
                    //backgroundColor:'black',	//---所有图例的字体背景色
                },
                tooltip: {					//图例提示框，默认不显示
                    show: true,
                    color: 'red',
                },
                data: ["投资余额", "限额"],
            },
            //--------------   提示框 -----------------
            tooltip: {
                show: true,					//---是否显示提示框,默认为true
                trigger: 'item',				//---数据项图形触发
                axisPointer: {				//---指示样式
                    type: 'shadow',
                    axis: 'auto',
                },
                padding: 5,
                textStyle: {					//---提示框内容样式
                    color: "#fff",
                },
            },
            xAxis: {
                show: true,					//---是否显示
                position: 'bottom',			//---x轴位置
                offset: 0,					//---x轴相对于默认位置的偏移
                type: 'category',			//---轴类型，默认'category'
                name: '',				//---轴名称
                nameLocation: 'end',			//---轴名称相对位置
                nameTextStyle: {				//---坐标轴名称样式
                    color: "#000000",
                    padding: [15, 0, 0, -15],	//---坐标轴名称相对位置
                },
                nameGap: 6,					//---坐标轴名称与轴线之间的距离
                //nameRotate:270,			//---坐标轴名字旋转
                axisLine: {					//---坐标轴 轴线
                    show: true,					//---是否显示
                    //------------------- 箭头 -------------------------
                    // symbol: ['none', 'arrow'],	//---是否显示轴线箭头
                    // symbolSize: [8, 8],			//---箭头大小
                    // symbolOffset: [0, 7],			//---箭头位置
                    //------------------- 线 -------------------------
                    lineStyle: {
                        color: '#000000',
                        width: 1,
                        type: 'solid',
                    },
                },
                axisTick: {					//---坐标轴 刻度
                    show: true,					//---是否显示
                    inside: true,				//---是否朝内
                    lengt: 3,					//---长度
                    lineStyle: {
                        //color:'red',			//---默认取轴线的颜色
                        width: 1,
                        type: 'solid',
                    },
                },
                axisLabel: {					//---坐标轴 标签
                    show: true,					//---是否显示
                    inside: false,				//---是否朝内
                    rotate: 0,					//---旋转角度
                    margin: 5,					//---刻度标签与轴线之间的距离
                    //color:'red',				//---默认取轴线的颜色
                    fontSize: 11,
                    interval: 0,             //横轴信息全部显示
                    formatter: function (c) {  //axisLabel里的formatter为function自己做文本截断
                        for (var i in c) {
                            return c.substring(0, 6);   //截取X轴坐标值的长度
                        }
                    }
                },
                splitLine: {					//---grid 区域中的分隔线
                    show: false,					//---是否显示，'category'类目轴不显示，此时我的X轴为类目轴，splitLine属性是无意义的
                    lineStyle: {
                        //color:'red',
                        //width:1,
                        //type:'solid',
                    },
                },
                splitArea: {					//--网格区域
                    show: false,					//---是否显示，默认false
                },
                data: isinder1
            },
            yAxis: {
                show: true,					//---是否显示
                position: 'left',			//---y轴位置
                offset: 0,					//---y轴相对于默认位置的偏移
                type: 'value',			//---轴类型，默认'category'
                name: '',				//---轴名称
                nameLocation: 'end',			//---轴名称相对位置value
                nameTextStyle: {				//---坐标轴名称样式
                    color: "#ffffff",
                    padding: [5, 0, 0, 5],	//---坐标轴名称相对位置
                },
                nameGap: 15,					//---坐标轴名称与轴线之间的距离
                //nameRotate:270,			//---坐标轴名字旋转

                axisLine: {					//---坐标轴 轴线
                    show: true,					//---是否显示
                    //------------------- 箭头 -------------------------
                    // symbol: ['none', 'arrow'],	//---是否显示轴线箭头
                    // symbolSize: [8, 8],			//---箭头大小
                    // symbolOffset: [0, 7],			//---箭头位置
                    //------------------- 线 -------------------------
                    lineStyle: {
                        color: '#ffffff',
                        width: 1,
                        type: 'solid',
                    },
                },
                axisTick: {					//---坐标轴 刻度
                    show: true,					//---是否显示
                    inside: true,				//---是否朝内
                    lengt: 3,					//---长度
                    lineStyle: {
                        //color:'red',			//---默认取轴线的颜色
                        width: 1,
                        type: 'solid',
                    },
                },
                axisLabel: {					//---坐标轴 标签
                    show: true,					//---是否显示
                    inside: false,				//---是否朝内
                    rotate: 0,					//---旋转角度
                    margin: 8,					//---刻度标签与轴线之间的距离
                    //color:'red',				//---默认取轴线的颜色
                },
                splitLine: {					//---grid 区域中的分隔线
                    show: true,					//---是否显示，'category'类目轴不显示，此时我的y轴为类目轴，splitLine属性是有意义的
                    lineStyle: {
                        color: '#666',
                        width: 1,
                        type: 'dashed',			//---类型
                    },
                },
                splitArea: {					//--网格区域
                    show: false,					//---是否显示，默认false
                }
            },
            //------------ 内容数据  -----------------
            series: [
                {
                    data: isinder3,
                    name: "投资余额",//---系列名称
                    legendHoverLink: true,		//---是否启用图例 hover 时的联动高亮
                    type: "bar",//---类型
                    label: {						//---图形上的文本标签
                        show: false,
                        position: 'insideTop',	//---相对位置
                        rotate: 0,				//---旋转角度
                        color: '#000',
                    },
                    itemStyle: {					//---图形形状
                        color: 'blue',
                        barBorderRadius: [18, 18, 0, 0],
                        normal: {
                            label: {
                                show: true, //开启显示
                                position: 'top', //在上方显示
                                textStyle: { //数值样式
                                    color: 'black',
                                    fontSize: 8
                                }
                            }
                        }
                    },
                    barWidth: '30',				//---柱形宽度
                    barCategoryGap: '15%',		//---柱形间距
                },
                {
                    barGap: "-100%", /*这里设置包含关系，只需要这一句话*/
                    data: isinder2,
                    name: "限额",
                    type: "bar",
                    legendHoverLink: true,		//---是否启用图例 hover 时的联动高亮
                    label: {						//---图形上的文本标签
                        show: false,
                        position: 'insideTop',	//---相对位置
                        rotate: 0,				//---旋转角度
                        color: '#000',
                    },
                    itemStyle: {					//---图形形状
                        color: 'blue',
                        barBorderRadius: [18, 18, 0, 0],
                        normal: {
                            label: {
                                show: true, //开启显示
                                position: 'top', //在上方显示
                                textStyle: { //数值样式
                                    color: 'black',
                                    fontSize: 8
                                }
                            }
                        }
                    },
                    barWidth: '30',				//---柱形宽度
                    barCategoryGap: '15%',		//---柱形间距
                }
            ]
        };
        echarts.init(document.getElementById('main1')).setOption(option);
    });

    //底层基础资产涉及关联方产品限额
    $.mask.post('${base}/report/warning/baseTotal', function (data) {
        var isinder1 = [];//关联方
        var isinder2 = [];//累计金额
        var isinder3 = [];//重大金额

        // data = [{"relname": "yyds", "amount": 100, "level": 1000}, {
        //     'relname': "mdzz",
        //     "amount": 900,
        //     "level": 1500
        // }, {'relname': "fgc", "amount": 900, "level": 1050}];
        var JsonArry = data;
        for (i = 0; i < JsonArry.length; i++) {
            isinder1.push(JsonArry[i].relName);//关联方
            isinder2.push(JsonArry[i].FinancialProRatio);//累计金额
            isinder3.push(JsonArry[i].limitFunds);//重大金额
        }
        option = {
            grid: {
                left: '60',
                right: '50',
                containLable: true,
            },
            color: ["#7179CB", "#4CCEFE"],
            legend: {
                type: 'scroll',				//----图例类型，默认为'plain'，当图例很多时可使用'scroll'
                right: '1%',					//----图例相对容器位置,top\bottom\left\right
                selected: {
                    '投资余额': true,			//----图例选择,图形加载出来会显示选择的图例，默认为true
                    '限额': true,			//----图例选择,图形加载出来会显示选择的图例，默认为true
                },
                textStyle: {					//----图例内容样式
                    color: '#000',				//---所有图例的字体颜色
                    //backgroundColor:'black',	//---所有图例的字体背景色
                },
                tooltip: {					//图例提示框，默认不显示
                    show: true,
                    color: 'red',
                },
                data: ["投资余额", "限额"],
            },
            //--------------   提示框 -----------------
            tooltip: {
                show: true,					//---是否显示提示框,默认为true
                trigger: 'item',				//---数据项图形触发
                axisPointer: {				//---指示样式
                    type: 'shadow',
                    axis: 'auto',
                },
                padding: 5,
                textStyle: {					//---提示框内容样式
                    color: "#fff",
                },
            },
            xAxis: {
                show: true,					//---是否显示
                position: 'bottom',			//---x轴位置
                offset: 0,					//---x轴相对于默认位置的偏移
                type: 'category',			//---轴类型，默认'category'
                name: '',				//---轴名称
                nameLocation: 'end',			//---轴名称相对位置
                nameTextStyle: {				//---坐标轴名称样式
                    color: "#000000",
                    padding: [15, 0, 0, -15],	//---坐标轴名称相对位置
                },
                nameGap: 6,					//---坐标轴名称与轴线之间的距离
                //nameRotate:270,			//---坐标轴名字旋转
                axisLine: {					//---坐标轴 轴线
                    show: true,					//---是否显示
                    //------------------- 箭头 -------------------------
                    // symbol: ['none', 'arrow'],	//---是否显示轴线箭头
                    // symbolSize: [8, 8],			//---箭头大小
                    // symbolOffset: [0, 7],			//---箭头位置
                    //------------------- 线 -------------------------
                    lineStyle: {
                        color: '#000000',
                        width: 1,
                        type: 'solid',
                    },
                },
                axisTick: {					//---坐标轴 刻度
                    show: true,					//---是否显示
                    inside: true,				//---是否朝内
                    lengt: 3,					//---长度
                    lineStyle: {
                        //color:'red',			//---默认取轴线的颜色
                        width: 1,
                        type: 'solid',
                    },
                },
                axisLabel: {					//---坐标轴 标签
                    show: true,					//---是否显示
                    inside: false,				//---是否朝内
                    rotate: 0,					//---旋转角度
                    margin: 5,					//---刻度标签与轴线之间的距离
                    //color:'red',				//---默认取轴线的颜色
                    fontSize: 11,
                    interval: 0,             //横轴信息全部显示
                    formatter: function (c) {  //axisLabel里的formatter为function自己做文本截断
                        for (var i in c) {
                            return c.substring(0, 6);   //截取X轴坐标值的长度
                        }
                    }
                },
                splitLine: {					//---grid 区域中的分隔线
                    show: false,					//---是否显示，'category'类目轴不显示，此时我的X轴为类目轴，splitLine属性是无意义的
                    lineStyle: {
                        //color:'red',
                        //width:1,
                        //type:'solid',
                    },
                },
                splitArea: {					//--网格区域
                    show: false,					//---是否显示，默认false
                },
                data: isinder1
            },
            yAxis: {
                show: true,					//---是否显示
                position: 'left',			//---y轴位置
                offset: 0,					//---y轴相对于默认位置的偏移
                type: 'value',			//---轴类型，默认'category'
                name: '',				//---轴名称
                nameLocation: 'end',			//---轴名称相对位置value
                nameTextStyle: {				//---坐标轴名称样式
                    color: "#ffffff",
                    padding: [5, 0, 0, 5],	//---坐标轴名称相对位置
                },
                nameGap: 15,					//---坐标轴名称与轴线之间的距离
                //nameRotate:270,			//---坐标轴名字旋转

                axisLine: {					//---坐标轴 轴线
                    show: true,					//---是否显示
                    //------------------- 箭头 -------------------------
                    // symbol: ['none', 'arrow'],	//---是否显示轴线箭头
                    // symbolSize: [8, 8],			//---箭头大小
                    // symbolOffset: [0, 7],			//---箭头位置
                    //------------------- 线 -------------------------
                    lineStyle: {
                        color: '#ffffff',
                        width: 1,
                        type: 'solid',
                    },
                },
                axisTick: {					//---坐标轴 刻度
                    show: true,					//---是否显示
                    inside: true,				//---是否朝内
                    lengt: 3,					//---长度
                    lineStyle: {
                        //color:'red',			//---默认取轴线的颜色
                        width: 1,
                        type: 'solid',
                    },
                },
                axisLabel: {					//---坐标轴 标签
                    show: true,					//---是否显示
                    inside: false,				//---是否朝内
                    rotate: 0,					//---旋转角度
                    margin: 8,					//---刻度标签与轴线之间的距离
                    //color:'red',				//---默认取轴线的颜色
                },
                splitLine: {					//---grid 区域中的分隔线
                    show: true,					//---是否显示，'category'类目轴不显示，此时我的y轴为类目轴，splitLine属性是有意义的
                    lineStyle: {
                        color: '#666',
                        width: 1,
                        type: 'dashed',			//---类型
                    },
                },
                splitArea: {					//--网格区域
                    show: false,					//---是否显示，默认false
                }
            },
            //------------ 内容数据  -----------------
            series: [
                {
                    data: isinder3,
                    name: "投资余额",//---系列名称
                    legendHoverLink: true,		//---是否启用图例 hover 时的联动高亮
                    type: "bar",//---类型
                    label: {						//---图形上的文本标签
                        show: false,
                        position: 'insideTop',	//---相对位置
                        rotate: 0,				//---旋转角度
                        color: '#000',
                    },
                    itemStyle: {					//---图形形状
                        color: 'blue',
                        barBorderRadius: [18, 18, 0, 0],
                        normal: {
                            label: {
                                show: true, //开启显示
                                position: 'top', //在上方显示
                                textStyle: { //数值样式
                                    color: 'black',
                                    fontSize: 8
                                }
                            }
                        }
                    },
                    barWidth: '30',				//---柱形宽度
                    barCategoryGap: '15%',		//---柱形间距
                },
                {
                    barGap: "-100%", /*这里设置包含关系，只需要这一句话*/
                    data: isinder2,
                    name: "限额",
                    type: "bar",
                    legendHoverLink: true,		//---是否启用图例 hover 时的联动高亮
                    label: {						//---图形上的文本标签
                        show: false,
                        position: 'insideTop',	//---相对位置
                        rotate: 0,				//---旋转角度
                        color: '#000',
                    },
                    itemStyle: {					//---图形形状
                        color: 'blue',
                        barBorderRadius: [18, 18, 0, 0],
                        normal: {
                            label: {
                                show: true, //开启显示
                                position: 'top', //在上方显示
                                textStyle: { //数值样式
                                    color: 'black',
                                    fontSize: 8
                                }
                            }
                        }
                    },
                    barWidth: '30',				//---柱形宽度
                    barCategoryGap: '15%',		//---柱形间距
                }
            ]
        };
        echarts.init(document.getElementById('main2')).setOption(option);
    });

</script>


<%--<script>
    new Vue({
        el: '#app',
        data: function () {
            return {visible: false}
        }
    })
</script>--%>


</body>
</html>