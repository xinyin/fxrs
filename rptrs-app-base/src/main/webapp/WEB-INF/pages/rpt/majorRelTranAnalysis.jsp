<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>ReportAnnual</title>
    <style type="text/css">
        .form-group .input-group.date,
        .form-group .select2 {
            width: 100% !important;
        }

        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .show_data {
            width: 90%;
            height: 180px;
            position: absolute;
            margin-top: 50px;
            margin-left: 50px;
        }

        .show_data .ewm {
            width: 220px;
            height: 220px;
            float: left;
            margin-right: 40px;
            margin-top: 40px;
        }

        .show_data .ewm img {
            width: 220px;
            height: 220px;
            border: #dfdfdf 4px solid;
        }

        .show_data .txt {
            overflow: hidden;
            font-size: 16px;
            line-height: 28px;
        }

        .show_data .txt p {
            padding: 10px 0;
        }
    </style>

</head>

<body class="gray-bg">
<div class="mainLbox" style="position: relative;">
    <div class="show_data">
        <div style="text-align: center;">

            <span id="app1" onclick="app(1)"
                  style="padding: 1%;background:#28c6e3; cursor: pointer; color: #fff; font-family: 'Arial Negreta', 'Arial';font-weight: 700;font-style: normal;font-size: 16px;">累计关联交易金额前十的关联方</span>
            <span id="app2" onclick="app(2)"
                  style="padding: 1%;background:#efefef; cursor: pointer; color: black;  font-family: 'Arial Negreta', 'Arial';font-weight: 700;font-style: normal;font-size: 16px;">累计关联交易金额达到重大关联交易限额60%的关联方</span>
        </div>
        <!--重大关联交易累计重大前十关联方-->
        <div id="appdesc1">
            <div id="wrappe" class="container">
                <div class="row" style="padding-top: 20px">
                    <div class="col-sm-6" style="height:400px; width: 100%;">
                        <div id="main" style="width: 100%;height:400px"></div>
                    </div>
                </div>
            </div>
        </div>
        <!--累计重大金额占比60%以上关联方-->
        <div id="appdesc2" style="display: none;">
            <div id="wrapper" class="animated fadeInRight">
                <div class="row" style="margin: auto">
                        <bc:table id="traInfo" idField="transNum" url="${base}/bus/relPsn" selectFirst="true"
                                  queryUrl="/relTotalQuery">
                            <bc:toolbar id="out" text="导出" type="button" handler="out"/>
                            <bc:column field="name" title="关联方名称" width="100"/>
                            <bc:column field="type" title="关联方类型" width="100"
                                       formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
                            <bc:column field="identifyType" title="关联方证件类型" width="100"/>
                            <bc:column field="identifyNumber" title="关联方证件号码" width="100"/>
                            <bc:column field="totalTradeAmount" title="累计重大金额（万元）" width="100"/>
                            <bc:column field="rattotalTradeAmount" title="累计重大金额比例" width="100"/>
                        </bc:table>
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

    var opt = '${opt}';
    $(document).ready(function () {
        if (opt == 'list') {
            $("#reback").css("display", "none");
        }
    })
    //关联方名称 name 关联方类型 type 关联方证件类型 identifyType 关联方证件号码 identifyNumber
    //累计重大金额（万元）totalTradeAmount 累计重大金额比例 rattotalTradeAmount
    function out() {
        var data = "1";
        var url = '${base}/sys/xlsie/MajorAlysislExportCallBackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    function getTraSubName() {
        return $("[name='traSubName']").val();
    }

    //获取表单选择的交易编号
    function getIdSelections() {
        return $.map($('#traInfo').bootstrapTable('getSelections'), function (row) {
            return row.transNum;
        });
    }

    //重置
    function frmReset() {
        window.location = '${base}/report/design/traInfoList';
    }

    $("#year").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true,
    });

    /*累计重大金额查询*/
    $.mask.post('${base}/bus/relPsn/relatedPartyTran', function (data) {
        var isinder1 = [];//关联方
        var isinder2 = [];//累计金额
        var isinder3 = [];//重大金额

        //data = [{"relname":"qzp","amount":100,"level":1000},{'relname':"cyx","amount":900,"level":1500},{'relname':"fgc","amount":900,"level":1050}];
        var JsonArry = data;
        for (i = 0; i < JsonArry.length; i++) {
            isinder1.push(JsonArry[i].relName);//关联方
            isinder2.push(JsonArry[i].amount);//累计金额
            isinder3.push(JsonArry[i].percentAmount);//重大金额
        }
        option = {
            grid: {
                left: '60',
                right: '50',
                containLable: true,
            },
            color: ["#7179CB", "#4CCEFE"],
            title: {
                text: "关联交易累计金额情况统计",
                textStyle: {
                    fontSize: 16,
                    padding: "10px"
                }
            },
            legend: {
                type: 'scroll',				//----图例类型，默认为'plain'，当图例很多时可使用'scroll'
                bottom: '5%',					//----图例相对容器位置,top\bottom\left\right
                selected: {
                    '重大额度': true,			//----图例选择,图形加载出来会显示选择的图例，默认为true
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
                data: ["重大额度", "已发生额度"],
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
                name: '关联方',				//---轴名称
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
                    name: "重大额度",//---系列名称
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
        echarts.init(document.getElementById('main')).setOption(option);
    });




</script>
<script type="text/javascript">
    function app(type) {
        if (type == '1') {
            $("#app1").css('background', '#28c6e3');
            $("#app1").css('color', '#fff');
            $("#appdesc1").css('display', 'block');

            $("#app2").css('background', '#efefef');
            $("#app2").css('color', 'black');
            $("#appdesc2").css('display', 'none');
        } else {
            $("#app2").css('background', '#28c6e3');
            $("#app2").css('color', '#fff');
            $("#appdesc2").css('display', 'block');

            $("#app1").css('background', '#efefef');
            $("#app1").css('color', 'black');
            $("#appdesc1").css('display', 'none');
        }

    }

</script>


</body>
</html>