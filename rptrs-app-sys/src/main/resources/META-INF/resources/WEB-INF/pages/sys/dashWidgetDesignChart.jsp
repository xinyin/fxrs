<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>组件设计</title>
    <link href="${res}/css/plugins/colorpicker/bootstrap.colorpickersliders.min.css" rel="stylesheet">
    <link href="${res}/css/plugins/bootstrap-slider/bootstrap-slider.min.css" rel="stylesheet">
    <style type="text/css">

        #paramDesign div.wrapper {
            padding-left: 0px;
            padding-right: 0px;
            font-size: 12px;
        }

        .panel-default {
            border: none;
        }

        .panel-default .panel-body {
            padding-left: 10px;
            padding-right: 10px;
            padding-top: 10px;
            padding-bottom: 10px;
        }

        .param-tab {
            height: 28px;
        }

        .param-tab li {
            height: 28px;
        }

        .param-tab li a {
            height: 28px;
            padding-top: 6px;
            padding-bottom: 2px;
        }

        .param-content {
            padding-top: 5px;
        }

        .param-content .control-label {
            padding-left: 5px;
            text-align: left
        }

        .param-content input {
            height: 28px;
        }

        .title-tag-grp {
            margin-bottom: 5px;
        }

        .title-tag-grp .control-label {
            padding-left: 15px;
            color: rgb(167, 177, 194);
        }

        .bootstrap-touchspin-up {
            height: 28px;
            margin-bottom: 5px;
            padding-top: 4px;
        }

        .bootstrap-touchspin-down {
            height: 28px;
            margin-bottom: 5px;
            padding-top: 4px;
        }

        .slider-selection {
            background: #BABABA;
        }

        .slider-handle {
            background: #BABABA;
        }

        .slider-horizontal {
            margin-left: 15px;
            margin-right: 5px;
        }

        .slider-label-x {
            width: 177.41px;
        }

        .slider-label-y {
            width: 224px;
        }

        .margin-small {
            margin-bottom: 5px;
        }

        .slider-margin-large {
            margin-bottom: 18px;
        }

        .gauge-color-css {
            padding-left: 0px;
        }

        .gauge-color-div {
            width: 28px;
            height: 28px;
            border: solid rgb(167, 177, 194) 1px;
            border-radius: 50px;
        }

        .margin-top-Fg {
            margin-top: 20px;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight" style="overflow: hidden;">
    <div class="row">
        <bc:ibox id="widgetDesignBox" title="${dashWidget.name}_组件设计" subTitle="" col="12" collapsible="true">
            <bf:form id="chartForm">
                <bc:col col="7" cls="left-col">
                    <bc:tabs id="widgetTabs">
                        <bc:tab id="chartDesign" title="数据编辑" visible="true">
                            <bf:formGroup cls="margin-top-Fg">
                                <bc:label text="图表类型" col="2" formControl="true"/>
                                <bf:select id="chartType" name="chartType" col="4"
                                           dataSource="fixedDict" url="DashChartType">
                                    <be:event type="select">chartTypeChange(e.target.value);</be:event>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup id="categoryDefGrp">
                                <bc:label text="种类系列" col="2" formControl="true"/>
                                <bf:text id="categoryDef" name="categoryDef" disabled="true" col="8"/>
                                <bc:button id="editExpr3" icon="pencil" tooltip="表达式" size="xs"
                                           click="editExpr('categoryDef', 'categoryDef')"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bc:label text="系列值1" col="2" formControl="true"/>
                                <bf:check></bf:check>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bc:label text="统计项" col="2" formControl="true"/>
                                <bf:select id="seriesName1" name="seriesName1" col="4">
                                    <be:event type="select">seriesNameChange(e.target, 'x');</be:event>
                                </bf:select>
                                <bc:button id="addSeries1" icon="plus" tooltip="添加" size="xs"
                                           click="addSeries('seriesName1')"/>
                                <bc:button id="delSeries1" icon="times" tooltip="移除" size="xs"
                                           click="delSeries('seriesName1')"/>
                            </bf:formGroup>
                            <bf:formGroup>
                                <bc:label text="表达式" col="2" formControl="true"/>
                                <bf:select id="exprType1" name="exprType1" col="4" dataSource="fixedDict"
                                           url="DashGroupType">
                                    <be:event
                                            type="select">exprTypeChange(e.target.value, 'seriesName1', 'exprVal1');</be:event>
                                </bf:select>
                                <bf:text id="exprVal1" name="exprVal1" col="4" disabled="true"/>
                                <bc:button id="editExpr1" icon="pencil" tooltip="表达式" size="xs"
                                           click="editExpr('seriesName1', 'exprVal1')"/>
                            </bf:formGroup>

                            <bf:formGroup cls="seriesGrp2">
                                <bc:label text="系列值2" col="2" formControl="true"/>
                                <bf:checkGroup col="2" name="seriesCheck">
                                    <bf:check id="seriesCheck" change="seriesChange(this)"/>
                                </bf:checkGroup>
                            </bf:formGroup>
                            <bf:formGroup cls="seriesGrp2">
                                <bc:label text="类型" col="2" formControl="true"/>
                                <bf:select id="chartType2" name="chartType2" col="4">
                                    <be:event
                                            type="select">seriesChartTypeChange(e.target.value, 'seriesName2', 'exprVal2');</be:event>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup cls="seriesGrp2">
                                <bc:label text="统计项" col="2" formControl="true"/>
                                <bf:select id="seriesName2" name="seriesName2" col="4">
                                    <be:event type="select">seriesNameChange(e.target, 'y');</be:event>
                                </bf:select>
                                <bc:button id="addSeries2" icon="plus" tooltip="添加" size="xs"
                                           click="addSeries('seriesName2')"/>
                                <bc:button id="delSeries2" icon="times" tooltip="移除" size="xs"
                                           click="delSeries('seriesName2')"/>
                            </bf:formGroup>
                            <bf:formGroup cls="seriesGrp2">
                                <bc:label text="表达式" col="2" formControl="true"/>
                                <bf:select id="exprType2" name="exprType2" col="4" dataSource="fixedDict"
                                           url="DashGroupType">
                                    <be:event
                                            type="select">exprTypeChange(e.target.value, 'seriesName2', 'exprVal2');</be:event>
                                </bf:select>
                                <bf:text id="exprVal2" name="exprVal2" col="4" disabled="true"/>
                                <bc:button id="editExpr2" icon="pencil" tooltip="表达式" size="xs"
                                           click="editExpr('seriesName2', 'exprVal2')"/>
                            </bf:formGroup>
                        </bc:tab>
                        <bc:tab id="paramDesign" title="参数调整">
                            <%@ include file="/WEB-INF/pages/sys/dashWidgetDesignChartParam.jsp" %>
                        </bc:tab>
                    </bc:tabs>
                </bc:col>
                <bc:col col="5" cls="seriesCol">
                    <div id="container" style="width:100%; height:400px;" class="col-xs"></div>
                    <bf:formGroup hr="dashed" hrp="top">
                        <div align="center">
                            <bc:button id="btnSave" type="primary" text="保存" icon="save" click="btnSave()"/>
                            <bc:button id="btnBack" text="返回" icon="times">
                                <be:event>
                                    window.location = '${base}/sys/dashWidget/list';
                                </be:event>
                            </bc:button>
                        </div>
                    </bf:formGroup>
                </bc:col>
            </bf:form>
        </bc:ibox>
        <bc:ibox id="widgetDesignBox" title="${dashWidgetProvider.providerName}_数据源预览" subTitle="" col="12"
                 collapsible="true">
            <table id="dashWidgetTable"></table>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${res}/js/echarts/echarts.min.js" type="text/javascript"></script>
<script src="${res}/js/plugins/colorpicker/bootstrap.colorpickersliders.min.js" type="text/javascript"></script>
<script src="${res}/js/plugins/bootstrap-slider/bootstrap-slider.min.js" type="text/javascript"></script>
<script src="${res}/js/plugins/colorpicker/tinycolor-min.js" type="text/javascript"></script>
<script src="${res}/jep/js/jDashWidget/jDashWidgetCharts.js" type="text/javascript"></script>
<script src="${res}/jep/js/jDashWidget/jDashWidgetParam.js" type="text/javascript"></script>
<script type="text/javascript">

    var provierId = '${dashWidgetProvider.provierId}'; //组件数据提供源
    var widgetCode = '${dashWidget.code}'; //组件编码

    var option = ''; //组件图表option脚本
    var widgetConfig = ''; //组件配置JSON脚本
    var dashWidgetInit = 'init'; //组件初始化标识

    $(document).ready(function () {

        /* 样式加载 */
        initCss();

        /* 初始化Provider以及加载组件配置 */
        initProvider();

        /*判断选中系列值2状态*/
        seriesChange($('#seriesCheck'));

        /* 获取被激活的标签页 */
        getActiveTabFn();
    });

    /* 初始化样式 */
    function initCss() {
        $('.seriesCol').attr('style', 'border-left: 1px dashed rgb(231, 234, 236);');
        $('#exprVal1').parent('div').attr('style', 'padding-left: 0px;');
        $('#exprVal2').parent('div').attr('style', 'padding-left: 0px;');
        $('.align-radio').parent('div').attr('style', 'margin-left: 0px;');
        $('#addGaugeColor').attr('disabled', true);
        $('#delGaugeColor').attr('disabled', true);

        $("#pieCenterX").slider({tooltip: 'hide'}); //饼图圆心坐标x
        $("#pieCenterY").slider({tooltip: 'hide'}); //饼图圆心坐标y
        $("#pieRadius").slider({tooltip: 'hide'}); //饼图半径
        $("#startAngle").slider({tooltip: 'hide'}); //饼图起始角度
        $("#minAngle").slider({tooltip: 'hide'}); //饼图最小角度
        $("#selectedOffset").slider({tooltip: 'hide'}); //扇区偏移量
        $("#symbolRotate").slider({tooltip: 'hide'}); //旋转角度

        /* 横坐标距图左侧边缘宽度 */
        $("#gridX1").slider({tooltip: 'hide'});
        $("#gridX2").slider({tooltip: 'hide'});
        $("#gridY1").slider({tooltip: 'hide'});
        $("#gridY2").slider({tooltip: 'hide'});

        $("#axisRotate").slider({tooltip: 'hide'}); //文本标签旋转角度x
        $("#axisRotateY").slider({tooltip: 'hide'}); //文本标签旋转角度 y
        $("#axisLabelInterval").slider({tooltip: 'hide'}); //文本标签与坐标轴间距x
        $("#axisLabelIntervalY").slider({tooltip: 'hide'}); //文本标签与坐标轴间距y

        $("#gaugeRange").slider({range: true}); //油量图值域范围
        $("#splitNumber").slider({tooltip: 'hide'}); //油量图分割段数
        $("#gaugeCenterX").slider({tooltip: 'hide'}); //油量图圆心坐标x
        $("#gaugeCenterY").slider({tooltip: 'hide'}); //油量图圆心坐标y
        $("#gaugeRadius").slider({tooltip: 'hide'}); //油量图半径
        $("#gaugeStartAngle").slider({tooltip: 'hide'}); //油量图起始角度
        $("#gaugeEndAngle").slider({tooltip: 'hide'}); //油量图结束角度
        $("#radarCenterX").slider({tooltip: 'hide'}); //雷达图圆心坐标x
        $("#radarCenterY").slider({tooltip: 'hide'}); //雷达图圆心坐标y
        $("#radarRadius").slider({tooltip: 'hide'}); //雷达图半径长度
        $("#radarStartAngle").slider({tooltip: 'hide'}); //雷达图起始角度
    }

    /* 获取活跃的tab页 */
    function getActiveTabFn() {
        $('ul .tab-list').on('shown.bs.tab', function (e) {
            var target = $(e.target).attr("href");

            /* 切换到数据编辑tab页 时，保存调整后的参数到widgetConfig中  */
            if (target == '#chartDesign') {
                /* 更新 widgetConfig */
                updateWidgetConfig();
                updateFlag = false;
            } else if (target == '#paramDesign') //参数调整tab页
            {
                var msg = verifyWidgetConfig();
                if (msg) {
                    var tab = $('a[name="chartDesign"]');
                    $(tab).parent().addClass('active');
                    $('#chartDesign').addClass('active');

                    var tab = $('a[name="paramDesign"]');
                    $(tab).parent().removeClass('active');
                    $('#paramDesign').removeClass('active');

                    $.messager.toastr('提示', msg, 'warning');
                    return;
                } else {
                    /*调整当前图表类型的 option 脚本的全局变量*/
                    initTmpOption(JSON.stringify(widgetConfig));
                    initSettings();
                }
            }
        });
    }

    /* 初始化数据提供程序 */
    function initProvider() {
        $.mask.post('${base}/sys/dashWidget/initProvider', {code: widgetCode}, function (data) {
            if (data.success) {
                /* 初始化数据源数据 */
                initTable(data.columns, data.sql);

                /* 判断组件是否已存在配置 */
                if (null != data.widgetConfig && data.widgetConfig != '') {
                    /* 设置组件配置 */
                    widgetConfig = $.parseJSON(data.widgetConfig);

                    /* 回显数据 */
                    showWidgetConfig(true);

                    /* 初始化Chart配置 */
                    initChartOptions(TABS_CHART);

                } else {
                    /* 初始化脚本 */
                    initWidgetConfig(INIT_WIDGETCONFIG_SCRIPT);

                    /* 显示默认图表 */
                    initchart(TABS_CHART, INIT_CHARTOPTION_LINE_SCRIPT);

                    /*设置临时全局变量，用于参数调整使用*/
                    initTmpOption(INIT_CHARTOPTION_LINE_SCRIPT);

                    /* 初始化【系列值2】 */
                    initSeries($('#chartType').val());

                    dashWidgetInit = 'false'; //取消初始化标识，设为 false
                }
                oldChartTypeVal = widgetConfig['chart-type'];
            }
        });
    }

    /* 初始化 Chart 配置 */
    function initChartOptions(id) {

        /* 提交之前，检查脚本是否填写完成 */
        var msg = verifyWidgetConfig();
        if (msg) {
            $.messager.toastr('提示', msg, 'warning');
            return;
        } else {
            var obj = {
                widgetCode: widgetCode,
                chartType: $('#chartType').val(),
                config: JSON.stringify(widgetConfig)
            };
            $.mask.post('${base}/sys/dashWidget/initChartOptions', obj, function (data) {
                if (data.success) {

                    /* 初始化主界面图表 */
                    initchart(id, data.chartOptions);

                    /*设置临时全局变量，用于参数调整使用*/
                    initTmpOption(data.chartOptions);
                } else {
                    $.messager.alert('系统提示', '图表脚本出错。', 'info');
                }
            });
        }
    }

    /* 编辑表达式 */
    function editExpr(seriesName, exprName) {
        var expr = $('#' + exprName).val();
        var params = {
            provierId: provierId,
            expr: expr,
            exprName: exprName,
            seriesName: seriesName
        };

        $.messager.modal({
            winId: 'winSeriesExprEdit',
            width: 850,
            height: 480,
            title: '表达式编辑',
            url: '${base}/sys/dashWidget/expr',
            args: {callback: result, params: params},
            onClose: function () {
            }
        });
    }

    /* 表达式回调函数 */
    function result(params) {

        var expr = params.expr;
        $('#' + params.exprName).val(expr);
        var tmp = params.seriesName;
        var index = tmp.substr(tmp.length - 1, 1);
        var exprType = $('#exprType' + index).val(); //获取表达式类型的值

        /* 判断当前更新的 expr 属于哪个属性中，并更新对应 widgetConfig 脚本 */
        if (params.exprName == 'categoryDef') {
            /* 更新 category-def */
            if (expr) {
                var obj = {
                    expr: expr
                };
                widgetConfig['category-def'][0] = obj;

                /*刷新图表*/
                initChartOptions(TABS_CHART);
            }
        } else {
            var seriesDef = widgetConfig['series-def'];
            var name = $('#' + params.seriesName).val();

            /* 封装更新参数 ，并更新 series-def*/
            var parameter = {
                name: name,
                expr: expr,
                group: exprType
            };
            updateSeriesDef(seriesDef, parameter, null);
        }
    }

    /* 保存 */
    function btnSave() {

        /* 检查脚本是否填写完成 */
        var msg = verifyWidgetConfig();
        if (msg) {
            $.messager.toastr('提示', msg, 'warning');
            return;
        } else {
            /* 更新 widgetConfig */
            updateWidgetConfig();
            updateFlag = false;

            var obj = {
                widgetCode: widgetCode,
                config: JSON.stringify(widgetConfig)
            };
            $.mask.post('${base}/sys/dashWidgetConfig/save', obj, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        window.location = '${base}/sys/dashWidget/list';
                    });
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            });
        }
    }


</script>
</body>
</html>
