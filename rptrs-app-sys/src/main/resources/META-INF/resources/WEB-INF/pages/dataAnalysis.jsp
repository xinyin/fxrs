<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN" xmlns:th="">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>

    <br:css pack="list"/>
    <link rel="stylesheet" type="text/css" href="${res}/css/dashLayout.css"/>

    <link rel="stylesheet" type="text/css" href="../../static/bootstrap/css/bootstrap.min.css"/>
    <title>首页</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="container">

    <div class="row" style="padding-top: 20px">
        <%--饼图--%>
        <div class="col-sm-4" style="height:300px;">
            <div id="main1" style="width: 98%;height:300px;border: 1px solid #CCCCCC"></div>
        </div>
        <div class="col-sm-4" style="height:300px;">
            <div id="main2" style="width: 98%;height:300px;border: 1px solid #CCCCCC"></div>
        </div>
    </div>
    <br><br>
    <div class="row" style="margin-bottom: 20px">


        <div class="col-sm-4" style="height:300px;">
            <div id="main3" style="width: 98%;height:300px;border: 1px solid #CCCCCC"></div>
        </div>

        <div class="col-sm-4" style="height:300px;">
            <div id="main4" style="width: 98%;height:300px;border: 1px solid #CCCCCC"></div>
        </div>

    </div>

    <div class="col-sm-1" id="wsx"></div>
</div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script src="${res}/js/echarts/echarts.min.js" type="text/javascript"></script>
<script src="${res}/jep/js/jDashWidget/dashLayoutDesign.js" type="text/javascript"></script>

<script type="text/javascript">

    function handleToa() {
        window.location = "${base}/workflow/approve/todoList";
    }

    $(document).ready(function () {
        /*关联方统计*/
        $.mask.post('${base}/bus/relPsn/psnAndOrgTotal', function (data) {
            var Jsonlist = data;

            option1 = {
                title: {
                    text: '关联方统计',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['自然人', '法人或其他组织关联方']
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '50%'],
                        data: Jsonlist,
                        emphasis: {
                            itemStyle: {
                                shadowRed: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            var myChart1 = echarts.init(document.getElementById('main1'));
            myChart1.setOption(option1);
            // 饼图点击跳转到指定页面
            myChart1.on('click', function (param) {
                window.location.href = param.data.url;
            });
        });

        /*业务类型限额汇总*/
        $.mask.post('${base}/bus/relPsn/investmentAmtQuery', function (data) {
            var isinder1 = [];//业务类型
            var isinder2 = [];//累计金额
            var isinder3 = [];//限额

            var JsonArry = data;
            for (i = 0; i < JsonArry.length; i++) {
                isinder1.push(JsonArry[i].investmentDesc);//业务类型
                isinder2.push(JsonArry[i].amount);//累计金额
                isinder3.push(JsonArry[i].percentAmount);//限额
            }

            option2 = {
                grid: {
                    left: '50',
                    right: '50',
                    containLable: true,
                },
                color: ["#7179CB", "#4CCEFE"],
                title: {
                    text: "资金运用类限额",
                    textStyle: {
                        fontSize: 16,
                        padding: "10px"
                    }
                },
                legend: {
                    type: 'plain',				//----图例类型，默认为'plain'，当图例很多时可使用'scroll'
                    bottom: '5%',					//----图例相对容器位置,top\bottom\left\right
                    selected: {
                        '限额': true,			//----图例选择,图形加载出来会显示选择的图例，默认为true
                        '已发生额度': true,			//----图例选择,图形加载出来会显示选择的图例，默认为true
                    },
                    textStyle: {					//----图例内容样式
                        color: '#000',				//---所有图例的字体颜色
                        //backgroundColor:'black',	//---所有图例的字体背景色
                    },
                    tooltip: {					//图例提示框，默认不显示
                        show: true,
                        color: 'red',
                    },
                    data: ["限额", "已发生额度"],
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
                    name: '业务类型',				//---轴名称
                    nameLocation: 'end',			//---轴名称相对位置
                    nameTextStyle: {				//---坐标轴名称样式
                        color: "#000000",
                        padding: [5, 0, 0, -5],	//---坐标轴名称相对位置
                    },
                    nameGap: 6,					//---坐标轴名称与轴线之间的距离
                    // nameRotate:-30,			//---坐标轴名字旋转
                    axisLine: {					//---坐标轴 轴线
                        show: true,					//---是否显示
                        //------------------- 箭头 -------------------------
                        symbol: ['none', 'arrow'],	//---是否显示轴线箭头
                        symbolSize: [8, 8],			//---箭头大小
                        symbolOffset: [0, 7],			//---箭头位置
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
                                if (c == "001") {
                                    c = "股权";
                                } else if (c == "002") {
                                    c = "不动产";
                                } else if (c == "003") {
                                    c = "境外投资";
                                } else if (c == "004") {
                                    c = "金融产品";
                                }
                                return c;
                            }
                        }
                    },
                    data: isinder1
                },
                yAxis: {
                    show: true,					//---是否显示
                    position: 'left',			//---y轴位置
                    offset: 0,					//---y轴相对于默认位置的偏移
                    type: 'value',			//---轴类型，默认'category'
                    name: '额度值（万元/人民币）',				//---轴名称
                    nameLocation: 'end',			//---轴名称相对位置value
                    nameTextStyle: {				//---坐标轴名称样式
                        color: "#000",
                        padding: [5, 0, 0, 5],	//---坐标轴名称相对位置
                    },
                    nameGap: 15,					//---坐标轴名称与轴线之间的距离
                    //nameRotate:270,			//---坐标轴名字旋转

                    axisLine: {					//---坐标轴 轴线
                        show: true,					//---是否显示
                        //------------------- 箭头 -------------------------
                        symbol: ['none', 'arrow'],	//---是否显示轴线箭头
                        symbolSize: [8, 8],			//---箭头大小
                        symbolOffset: [0, 7],			//---箭头位置
                        //------------------- 线 -------------------------
                        lineStyle: {
                            color: '#000',
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
                        name: "限额",//---系列名称
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
                        barWidth: '20',				//---柱形宽度
                        barCategoryGap: '20%',		//---柱形间距
                    },
                    {
                        barGap: "-100%", /*这里设置包含关系，只需要这一句话*/
                        data: isinder2,
                        name: "已发生额度",
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
                        barWidth: '20',				//---柱形宽度
                        barCategoryGap: '20%',		//---柱形间距
                    }
                ]
            };
            echarts.init(document.getElementById('main2')).setOption(option2);
        });


        $.mask.post('${base}/base/welcome/statistics', function (data) {

            option4 = {
                title: {
                    text: '关联交易金额统计',
                    subtext: ''
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: ['']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '9%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01]
                },
                yAxis: {
                    type: 'category',
                    data: ['其他类', '提供货物或服务类', '利益转移类', '保险业务类', '资金运用类', '投资入股类']
                },
                series: [
                    {
                        name: '',
                        type: 'bar',
                        data: data
                    },

                ]
            };
            echarts.init(document.getElementById('main4')).setOption(option4);
        });


        $.mask.post('${base}/base/welcome/approval/statistics', function (data) {
            console.log(data);
            option3 = {
                title: {
                    text: '审批情况统计',
                    subtext: ''
                },
                tooltip: {
                    trigger: 'axis'
                },
                calculable: true,
                xAxis: [
                    {
                        type: 'category',
                        data: data.subjectList,
                        axisTick: {
                            alignWithLabel: true
                        },
                        axisLabel: {    //重点在这一块，其余可以忽略
                            interval: 0,   //这个一定要有，别忘记了
                            rotate: 15,
                            textStyle: {
                                color: '#000',
                                fontSize: 10
                            }
                        },
                        axisLine: {
                            show: true,
                            interval: 0,
                            lineStyle: {
                                color: "RGB(210,221,217)"
                            }
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '已审批',
                        type: 'bar',
                        data: data.adoptList
                    },
                    {
                        name: '总发起数量',tiaozhneg
                        type: 'bar',
                        data: data.allList
                    }
                ]
            };
            echarts.init(document.getElementById('main3')).setOption(option3);
        });
    });
</script>
</body>
</html>
