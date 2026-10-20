<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <link rel='stylesheet' href='${res}/css/plugins/jqPropertyGrid/jqPropertyGrid.css'/>
    <link rel='stylesheet' href='${res}/css/plugins/jQueryUI/jquery-ui.css'/>
    <link rel='stylesheet' href='${res}/css/plugins/jqPropertyGrid/spectrum.css'/>
    <link rel='stylesheet' href='${res}/css/plugins/jqPropertyGrid/font-awesome.min.css'/>
    <title>组件设计</title>
    <style type="text/css">

        body {
            font-family: 'verdana';
            font-size: 12px;
        }

        .column {
            float: left;
            /*border: dashed 1px #b9d5f6;*/
        }

        .column-edge {
            float: right;
            width: 1px;
            background-color: #edf0f3;
            text-indent: -9999em;
            cursor: w-resize;
            min-height: 54px;
            height: auto !important;
            height: 54px;
        }

        .cellBorder {
            border: #ccc 1px solid;
        }

        .cellSelected {
            border: #95B8E7 1px dashed;
        }

        .header {
            height: 27px;
            list-style-type: none;
            line-height: 27px;
            overflow: hidden;
        }

        .detail {
            height: 27px;
            list-style-type: none;
            line-height: 27px;
            overflow: hidden;
        }

        .document-class {
            width: 150px;
            border: 1px solid #CCCCCC;
            position: absolute;
            display: none;
            background: #ffffff;
            z-index: 9999;
        }

        .document-class ul {
            padding: 0px;
            margin: 0px;
            list-style: none;
        }

        .document-class ul li {
            line-height: 30px;
            text-indent: 20px;
        }

        .document-class ul li a {
            color: #000000;
            text-decoration: none;
        }

        #dataWidgetIbox {
            font-size: 13px;
        }

        /* #dataWidgetTabs{ */
        /*  position: relative; */
        /*  top : -29px; */
        /* } */
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="dataWidgetIbox" title="${dashWidget.name}_组件设计" subTitle="" col="12" collapsible="true">
            <bf:form id="form">
                <bc:tabs id="dataWidgetTabs"></bc:tabs>
                <div style="position:relative;top:-130px;color:inherit"/>
                <div style="position:relative;top:60px; left:400px;">
                    <bc:button text="新增页面" click="btnAddTab()"/>
                    <bc:button type="primary" text="保存" click="saveDesign()" icon="save"/>
                    <bc:button text="返回" click="back()" icon="arrow-circle-left"/>
                </div>
            </bf:form>
        </bc:ibox>
    </div>

    <div class="document-class">
        <ul>
            <li>
                <ul>
                    <li><a href="#" onclick="addPriorColumns()">在之前插入列</a></li>
                    <li><a href="#" onclick="addAfterColumns()">在之后插入列</a></li>
                    <li><a href="#" onclick="delColumns()">删除列</a></li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript" src="${res}/js/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="${res}/js/jqPropertyGrid/spectrum.js"></script>
<script type="text/javascript" src="${res}/js/jqPropertyGrid/jqPropertyGrid.js"></script>
<script type="text/javascript" src="${res}/jep/js/jDashWidget/dataWidgetDesign.js"></script>
<script type="text/javascript">
    $(function () {
        widgetCode = '${dashWidget.code}';
        var widgetName = '${dashWidget.name}';
        //查询该数据组件设计过的数据
        $.mask.post("${base}/sys/dashWidgetConfig/getWidgetConfigByWidgetCode", {widgetCode: widgetCode}, function (data) {
            if (data.success) {
                var dashWidgetConfig = data.dashWidgetConfig;
                //如果是编辑，获取当前的版本号
                version = dashWidgetConfig.version;
                //获取组件设计的数据
                var widgetConfig = dashWidgetConfig.config;
                if (!widgetConfig || widgetConfig == '' || widgetConfig == null) {
                    //把页面数据重新转化一遍，防止程序认为是同一个对象
                    var tabJson = $.parseJSON(initTab);
                    // 设置默认标题
                    tabJson.title = 'tab' + tabNo;
                    tabData = {
                        'title': widgetName,
                        'tabs': [tabJson]
                    };
                } else {
                    //取出配置对象，并转为对象
                    tabData = $.parseJSON(widgetConfig);
                }
            } else {
                //把页面数据重新转化一遍，防止程序认为是同一个对象
                var tabJson = $.parseJSON(initTab);
                // 设置默认标题
                tabJson.title = 'tab' + tabNo;
                tabData = {
                    'title': widgetName,
                    'tabs': [tabJson]
                };
            }

            //获取该数据组件的数据绑定程序
            $.mask.post('${base}/sys/dashWidgetProvider/getWidgetProvidersByWidgetCode',
                {widgetCode: widgetCode}, function (data) {
                    widgetProvider = data;
                    //初始化整个页面
                    init();
                    //点击当前面板的第一个单元格
                    clickCell(0);
                    var pgRows = $(".pgRow");
                    $.each(pgRows, function (i, el) {
                        var td = $(el).children()[1];
                        $(td).children().css('width', '200px');
                    })

                })

        })
    })
</script>
</body>
</html>
