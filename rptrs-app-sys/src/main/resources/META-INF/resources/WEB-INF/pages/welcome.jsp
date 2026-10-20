<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN" xmlns:th="">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>

    <br:css pack="list"/>
    <link rel="stylesheet" type="text/css" href="${res}/css/dashLayout.css"/>

    <link rel="stylesheet" type="text/css" href="../../static/bootstrap/css/bootstrap.min.css"/>
    <title>首页</title>
    <style>
        /*设置body*/
        #main {
            margin: 0;
            padding: 0;
            font: 16px "微软雅黑", sans-serif;
        }

        /*设置轮播图的边框相对定位*/
        .play-box {
            position: relative;
            height: 266px;
            margin: 0px 0px 0px 0px;
            border: 1px solid #ccc;
        }

        /*设置边框下的图片*/
        .play-box img {
            width: 100%;
            height: 265px;
            border: 0;
        }

        /*设置超链接下的所有图片不显示*/
        .play-box a {
            display: block; /*现在是文本状态，要修改为块状*/
            overflow: hidden; /*隐藏超过边框高度的图片*/
            opacity: 0; /*默认图片为全透明*/
            height: 0; /*默认高度为0*/
            transition: opacity .5s; /*默认透明度过渡时间为0.5s*/
        }

        #imgList {
            height: 100%; /*默认高度为自动*/
            width: 100%;
            text-align: center;
        }

        /*设置当前图片显示*/
        #imgList a.current {
            opacity: 1; /*默认图片显示*/
            height: auto; /*默认高度为自动*/
            width: auto;

        }

        /*设置列表ul*/
        ul {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        /*设置列表ul下的li*/
        ul li {
            width: 10px;
            height: 10px;
            margin: 0 4px;
            font-size: 0;
            border-radius: 50%;
            background-color: #fff;
            cursor: pointer;
            float: left;
        }

        /*当图片变化时，图标也跟着变化*/
        ul li.current {
            background-color: red;
        }

        /*设置图标绝对定位*/
        .iconList {
            position: absolute;
            bottom: 10px;
            left: 50%;
            margin-left: -45px;
        }

        /*设置向左向右图标*/
        .sliderbar {
            position: absolute;
            top: 50%;
            margin-top: -25px;
            width: 30px;
            height: 50px;
            font-family: simsun;
            color: #fff;
            text-align: center;
            line-height: 50px;
            background-color: #000;
            opacity: .6;
            display: none;
            cursor: pointer;
        }

        /*设置向左图标*/
        .slidebar-left {
            left: 0;
        }

        /*设置向右图标*/
        .slidebar-right {
            right: 0;
        }

        .table {
            height: 86%;
        }

        a:link {
            color: dodgerblue;
        }

        /*设置文字绝对定位*/
        .msgtext {
            position: absolute;
            bottom: 0px;
            margin: 0px 0px 5px 10px;
            color: white;
            font-size: 20px;
        }

    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="container">
    <div class="row" style="padding-top: 20px">
        <%--饼图--%>
       <%-- <div class="col-sm-6" style="height:400px;">
            <div id="main1" style="width: 98%;height:400px;border: 1px solid #CCCCCC"></div>
        </div>--%>

        <div class="col-sm-6" style="height:400px;">
            <div id="main2" style="width: 98%;height:400px;border: 1px solid #CCCCCC"></div>
        </div>
    </div>
    <%--<div class="row" style="padding-top: 20px">

        &lt;%&ndash;        待补录交易&ndash;%&gt;
        <div class="col-sm-6" id="sysnation" style="height:400px">
            <div id="main3" style="width: 98%;height:300px;border: 1px solid #CCCCCC">
                <div class="col-md-10" style="margin: 5px 0px 0px 0px">
                    <h2><strong>信息资讯</strong></h2>
                </div>
                <br><br>
                <table class="table" id="msgNotice" style="height: 80%">
                    <tr class="tr_Supplement">
                        &lt;%&ndash;动态插入数据&ndash;%&gt;
                    </tr>
                </table>
            </div>
        </div>
        <div class="col-sm-6" id="PendingDivId" style="height:400px">
            <div id="main4" style="width: 98%;height:300px;border: 1px solid #CCCCCC">
                <div class="col-md-10" style="margin: 5px 0px 0px 0px">
                    <h2><strong>我的待办</strong></h2>
                </div>

                <div class="col-md-2" style="margin: 8px 0px 0px 0px">
                    <a href="${base}/workflow/approve/todoList">更多 »</a>

                </div>
                <br><br>
                <table class="table" id="table3" style="height: 80%" >
                    <tr class="tr_Supplement">
                        &lt;%&ndash;动态插入数据&ndash;%&gt;
                    </tr>
                </table>
            </div>
        </div>
    </div>--%>

</div>



<br:js pack="list"/>
<be:processEvent/>

<script src="${res}/js/echarts/echarts.min.js" type="text/javascript"></script>
<script src="${res}/jep/js/jDashWidget/dashLayoutDesign.js" type="text/javascript"></script>

<script type="text/javascript">
    function morelDone(){
        window.location = "${base}/workflow/approve/doneList";
    }

    $(document).ready(function () {
        /*信息咨询*/
        /*$.mask.post('${base}/bus/msgDeploy/selectReleaseMsg', function (data) {
            var MsgList = data;
            $.each(MsgList, function (index, n) {
                if(MsgList[index].importantMsg=='0'){
                    var rowTr = document.createElement('tr');
                    //找到html的tr节点
                    rowTr.className = "tr_Supplement";
                    var child = '<td>';
                    child += '<a class="msg" style="color:red;font-size:18px" href="${base}/bus/msgDeploy/view/' + MsgList[index].pid + '">' + MsgList[index].busName+'[重要消息]</a>';
                    child += '</td>';
                }else{

                    var rowTr = document.createElement('tr');
                    //找到html的tr节点
                    rowTr.className = "tr_Supplement";
                    var child = '<td>';
                    child += '<a class="msg" style="font-size:18px" href="${base}/bus/msgDeploy/view/' + MsgList[index].pid + '">' + MsgList[index].busName+'</a>';

                    child += '</td>';
                }




                //将要展示的信息写入页面
                rowTr.innerHTML = child;
                //将信息追加
                $("#msgNotice").append(rowTr)
            });

        });*/

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
                                    c = "权益类投资";
                                } else if (c == "002") {
                                    c = "不动产投资";
                                } else if (c == "003") {
                                    c = "境外投资";
                                } else if (c == "004") {
                                    c = "金融产品投资";
                                }/*else if (c == "005") {
                                    c = "全部关联方投资";
                                }*/
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
                        padding: [50, 0, 0, 50],	//---坐标轴名称相对位置
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

        /*待补录交易*/
        // $.mask.post('ins/traCramcInfo/Supplement', function (data) {
        //     var ArryList = data;
        //
        //     $.each(ArryList, function (index, n) {
        //         var rowTr = document.createElement('tr');
        //         //找到html的tr节点
        //         rowTr.className = "tr_Supplement";
        //         var child = "<td>\n" +
        //             " <div class=\"row1-1\">\n" +
        //             " <div class=\"col-md-9\">\n" +
        //             /*" <a class=\"text-muted\" href=\"\">"+ArryList[index].subject+"</a>\n" +*/
        //             " <a class=\"text-muted\" href=\"ins/" + ArryList[index].tranType + "/edit/" + ArryList[index].id + "\">" + ArryList[index].tranTypeC + "</a>" +
        //             "</div>\n" +
        //             " <div class=\"col-md-3\">\n" +
        //             " <p class=\"time\">" + ArryList[index].modifiedTime + "</p>\n" +
        //             " </div>\n" +
        //             " </div>\n" +
        //             " </td>";
        //
        //         //将要展示的信息写入页面
        //         rowTr.innerHTML = child;
        //         //将信息追加
        //         $("#table3").append(rowTr)
        //     });
        //
        // });

        //我的待办
        $.mask.post('${base}/workflow/approve/waitnotice', function (data) {
            var ArryList = data;
            if (ArryList.length == 0) {
                return;
            }
            $.each(ArryList, function (index, n) {
                var rowTr1 = document.createElement('tr');
                //找到html的tr节点
                rowTr1.className = "tr_asd";
                var child1 = "<td>\n" +
                    // " <div class=\"row1-1\">\n" +
                    " <div class=\"col-md-12\" style='text-align: left; padding: 0px'>\n" +
                    " <a class=\"text-muted\"  style=\"font-size:18px \" href=\"${base}/workflow/approve/process?procInstId=" + ArryList[index].procInstId + "&taskId=" + ArryList[index].taskId + "&staffCode=" + '' + "\">" + ArryList[index].procName + "</a>" +
                    // "</div>\n" +
                    // " <div class=\"col-md-2\" style='padding: 0px'>\n" +
                    " <p class=\"time\" style='text-align: right ; font-size:18px ; float:right '>" + ArryList[index].createtime + "</p>\n" +
                    " </div>\n" +
                    // " </div>\n" +
                    " </td>";

                //将要展示的信息写入页面
                rowTr1.innerHTML = child1;
                //将信息追加
                $("#table3").append(rowTr1)
            });
        });

        /*待查阅交易*/
        $.mask.post('ins/traCramcInfo/Supplement1', function (data) {
            var ArryList = data;

            $.each(ArryList, function (index, n) {
                var rowTr = document.createElement('tr');
                //找到html的tr节点
                rowTr.className = "tr_Supplement";
                var child = "<td>\n" +
                    " <div class=\"row1-1\">\n" +
                    " <div class=\"col-md-9\">\n" +
                    /*" <a class=\"text-muted\" href=\"\">"+ArryList[index].subject+"</a>\n" +*/
                    " <a class=\"text-muted\" href=\"ins/" + ArryList[index].tranType + "/view/" + ArryList[index].id + "\">" + ArryList[index].tranTypeC + "</a>" +
                    "</div>\n" +
                    " <div class=\"col-md-3\">\n" +
                    " <p class=\"time\">" + ArryList[index].modifiedTime + "</p>\n" +
                    " </div>\n" +
                    " </div>\n" +
                    " </td>";

                //将要展示的信息写入页面
                rowTr.innerHTML = child;
                //将信息追加
                $("#table6").append(rowTr)
            });


        });

        //将字典值添加到cookie
        $.post("${base}/fixedDict/ProcInstStatus", function (data) {
            var arr = data;
            $.each(arr, function (i, val) {
                document.cookie = val.value + "=" + val.name;
            });
        });
        /*流程追踪*/
        var strcookie = document.cookie;
        var jsonStr = JSON.stringify(strcookie);
        var arrcookie = strcookie.split(";");
        for (var i = 0; i < arrcookie.length; i++) {
            var arr = arrcookie[i].split("=");
            if (arr[0].replace(/\s+/g, "") == "cookie.user.name") {
                var name = arr[1];
            }
            if (arr[0].replace(/\s+/g, "") == "10") {
                var status_10 = arr[1];
            }
            if (arr[0].replace(/\s+/g, "") == "20") {
                var status_20 = arr[1];
            }
            if (arr[0].replace(/\s+/g, "") == "30") {
                var status_30 = arr[1];
            }
            if (arr[0].replace(/\s+/g, "") == "40") {
                var status_40 = arr[1];
            }
            if (arr[0].replace(/\s+/g, "") == "50") {
                var status_50 = arr[1];
            }
            if (arr[0].replace(/\s+/g, "") == "60") {
                var status_60 = arr[1];
            }
        }

        if (null != name) {
            var url = "${base}/workflow/approve/query";
            $.get(url, function (data) {
                var ArryList = data.rows;
                $.each(ArryList, function (i, val) {
                    var rowTr = document.createElement('tr');
                    switch (val.status) {
                        case "10":
                            status = status_10;
                            break;
                        case "20":
                            status = status_20;
                            break;
                        case "30":
                            status = status_30;
                            break;
                        case "40":
                            status = status_40;
                            break;
                        case "50":
                            status = status_50;
                            break;
                        case "60":
                            status = status_60;
                            break;

                    }
                    //找到html的tr节点
                    rowTr.className = "tr_asd";
                    var child = "<td>\n" +
                        "<div class=\"row1-1\">\n" +
                        " <div class=\"col-md-9\">\n" +
                        " <a class=\"text-muted\" href=\"workflow/approve/process?procInstId=" + val.id + "&taskId=" + val.taskId + "\">" + "由" + val.starterName + "发起的" + "[" + val.procName + "]" + "需要您审批" + "<a/>" +   /*" <a class=\"text-muted\" href=\"#\">" + val.procName + "状态:" + status + "</a>" +*/
                        "</div>" +
                        " </div>\n" +
                        "</td>";
                    rowTr.innerHTML = child;
                    //将信息追加
                    $("#table1").append(rowTr)
                });

            });

        }
        // eachImg();
        //设置变量速度为3秒
        var speed = 3000;
        //循环变量为1，是避免定时器再等第一章图片
        var m = 1;
        //设置定时器的函数和时间
        var playTimer = setInterval(runPlay, speed);
        //给整个轮播图绑定鼠标事件，当鼠标放到轮播图上停止轮播图，当鼠标离开轮播图继续滚动
        $("#playBox").mouseenter(function () {
            //停止定时
            clearInterval(playTimer);
            //左右控制按钮显示
            $(".sliderbar").fadeIn(300);
        }).mouseleave(function () {
            //重新开始定时
            playTimer = setInterval(runPlay, speed);
            //左右控制按钮隐藏
            $(".sliderbar").fadeOut(300);
        });
        //给li绑定控制图标绑定单击事件
        $("ul li").click(function () {
            controlPlay($(this).index());
            //鼠标点击过后修改m的值
            m = $(this).index() + 1;
        }).hover(function () {
            //给li取消鼠标悬停的冒泡
            return false;
        })
        //给左右的控制图标取消 鼠标悬停的冒泡
        $(".sliderbar").hover(function () {
            return false;
        })
        //下一张图片
        $(".slidebar-right").click(function () {
            //判断m的值
            if (m > 4) {
                m = 0;
            }
            //显示下一站图片
            controlPlay(m);
            m++;
        })
        //上一张图片
        $(".slidebar-left").click(function () {
            //m默认显示下一张，所以为了显示上一张，m必须减2
            m -= 2;
            if (m < 0) {
                m = 4;
            }
            //显示上一张图片
            controlPlay(m);
            //保证m要显示下一张
            m++;
        })

        //定时函数
        function runPlay() {
            //判读如果m大于4，就设置m=0
            if (m > 4) {
                m = 0;
            }
            //调用controlPlay函数来控制图片变化
            controlPlay(m);
            m++;
        }

        //通过参数控制图片的变化,图标变化
        function controlPlay(n) {
            $("#imgList a").removeClass("current").eq(n).addClass("current");
            $("ul li").removeClass("current").eq(n).addClass("current");

        }
    });

    function eachImg() {


    }
</script>
</body>
</html>
