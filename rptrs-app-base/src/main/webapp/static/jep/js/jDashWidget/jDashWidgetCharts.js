/**
 * 图表组件数据编辑脚本
 * @author zhangyan <yan.zhang44@yzj.com>
 * @version: 1.0
 *
 */

/*==============================变量定义==============================*/

/*图表类型: 折线图、柱形图、面积图、饼图、雷达图、油量图*/
var TYPE_LINE = 'line', TYPE_BAR = 'bar', TYPE_STACK = 'stack', TYPE_PIE = 'pie', TYPE_RADAR = 'radar',
    TYPE_GAUGE = 'gauge';

/*常量定义：表达式求和、图表系列名称、图表显示容器*/
var GROUP_SUM = 'Sum', SERIES_NAME = 'series', TABS_CHART = 'container';

/*初始化脚本：直系图、饼图、雷达图、油量图*/
var INIT_WIDGETCONFIG_SCRIPT = '{"provider":[],"chart-type":"line","category-def":[],"series-def":[],"x-change-y":false,"title":{"text":"自定义图","subtext":"副标题","x":"center"},"tooltip":{"trigger":"axis"},"legend":{"x":"center","y":"bottom","bottom":10},"toolbox":{"show":true,"feature":{"dataView":{"show":true,"readOnly":true},"restore":{"show":true},"saveAsImage":{"show":true}}},"calculable":true,"xAxis":[{"type":"category","axisTick":{"alignWithLabel":true}}],"yAxis":[{"type":"value","name":"单位1","axisLabel":{"formatter":"{value}"}},{"type":"value","name":"单位2","show":false,"axisLabel":{"formatter":"{value}"}}],"series":[]}';
var INIT_WIDGETCONFIG_PIE_SCRIPT = '{"provider":[],"chart-type":"pie","category-def":[],"series-def":[],"title":{"text":"自定义图","subtext":"副标题","x":"center"},"tooltip":{"trigger":"item","islandFormatter":"{a} <br>{b} : {c}","formatter":"a} <br>{b} : {c} ({d}%)","show": true},"legend":{"x":"center","y":"bottom","bottom":10},"toolbox":{"show":true,"feature":{"dataView":{"show":true,"readOnly":true},"restore":{"show":true},"saveAsImage":{"show":true}}},"calculable":true,"animation":true,"series":[]}';
var INIT_WIDGETCONFIG_RADAR_SCRIPT = '{"provider":[],"chart-type":"radar","category-def":[],"series-def":[],"title":{"text":"自定义图","subtext":"副标题"},"tooltip":{"trigger":"item"},"legend":{"x":"right","y":"bottom"},"toolbox":{"show":true,"feature":{"dataView":{"show":true,"readOnly":true},"restore":{"show":true},"saveAsImage":{"show":true}}}, "radar": [{"indicator":[{"name":"","max":""}],"startAngle":90,"splitNumber":4,"shape":"circle","name":{"formatter":"【{value}】","textStyle":{"color":"#72ACD1"}},"splitArea":{"areaStyle":{"color":["rgba(114, 172, 209, 0.2)","rgba(114, 172, 209, 0.4)","rgba(114, 172, 209, 0.6)","rgba(114, 172, 209, 0.8)","rgba(114, 172, 209, 1)"],"shadowColor":"rgba(0, 0, 0, 0.3)","shadowBlur":10}},"axisLine":{"lineStyle":{"color":"rgba(255, 255, 255, 0.5)"}},"splitLine":{"lineStyle":{"color":"rgba(255, 255, 255, 0.5)"}}}],"series":[]}';
var INIT_WIDGETCONFIG_GAUGE_SCRIPT = '{"provider":[],"chart-type":"gauge", "series-def":[],"title":{"text":"自定义图","subtext":"副标题"},"tooltip":{"formatter":"{a} <br/>{b} : {c}%"},"toolbox":{"feature":{"saveAsImage":{},"restore":{}}},"series":[]}';

/*初始化默认图表显示*/
var INIT_CHARTOPTION_LINE_SCRIPT = '{"title":{"text":"折线图示例","subtext":"副标题"},"tooltip":{"trigger":"axis"},"legend":{"data":["示例"]},"toolbox":{"show":true,"feature":{"dataZoom":{"yAxisIndex":"none"},"dataView":{"readOnly":true},"restore":{},"saveAsImage":{}}},"xAxis":{"type":"category","boundaryGap":false,"data":["周一","周二","周三","周四","周五","周六","周日"]},"yAxis":{"type":"value","axisLabel":{"formatter":"{value} °C"}},"series":[{"name":"示例","type":"line","data":[2,3,3,2,4,2,3],"markPoint":{"data":[{"type":"max","name":"最大值"},{"type":"min","name":"最小值"}]},"markLine":{"data":[{"type":"average","name":"平均值"},[{"symbol":"none","x":"90%","yAxis":"max"},{"symbol":"circle","label":{"normal":{"position":"start","formatter":"最大值"}},"type":"max","name":"最高点"}]]}}]}';
var INIT_CHARTOPTION_BAR_SCRIPT = '{"title":{"text":"柱形图示例","subtext":"副标题"},"tooltip":{"trigger":"axis"},"legend":{"data":["示例01","示例02"]},"toolbox":{"show":true,"feature":{"dataView":{"show":true,"readOnly":true},"magicType":{"show":true,"type":["line","bar"]},"restore":{"show":true},"saveAsImage":{"show":true}}},"calculable":true,"xAxis":[{"type":"category","data":["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]}],"yAxis":[{"type":"value"}],"series":[{"name":"示例01","type":"bar","data":[2,4.9,7,23.2,25.6,76.7,135.6,162.2,32.6,20,6.4,3.3],"markPoint":{"data":[{"type":"max","name":"最大值"},{"type":"min","name":"最小值"}]},"markLine":{"data":[{"type":"average","name":"平均值"}]}},{"name":"示例02","type":"bar","data":[2.6,5.9,9,26.4,28.7,70.7,175.6,182.2,48.7,18.8,6,2.3],"markPoint":{"data":[{"name":"年最高","value":182.2,"xAxis":7,"yAxis":183},{"name":"年最低","value":2.3,"xAxis":11,"yAxis":3}]},"markLine":{"data":[{"type":"average","name":"平均值"}]}}]}';
var INIT_CHARTOPTION_STACK_SCRIPT = '{"title":{"text":"面积图示例","subtext":"副标题","x":"center"},"tooltip":{"trigger":"axis","axisPointer":{"type":"cross","label":{"backgroundColor":"#6a7985"}}},"legend":{"x":"center","y":"bottom","bottom":10,"data":["示例01","示例02","示例03","示例04"]},"toolbox":{"feature":{"saveAsImage":{},"restore":{}}},"xAxis":[{"type":"category","boundaryGap":false,"data":["周一","周二","周三","周四","周五","周六","周日"]}],"yAxis":[{"type":"value"}],"series":[{"name":"示例01","type":"line","stack":"总量","areaStyle":{"normal":{}},"data":[120,132,101,134,90,230,210]},{"name":"示例02","type":"line","stack":"总量","areaStyle":{"normal":{}},"data":[220,182,191,234,290,330,310]},{"name":"示例03","type":"line","stack":"总量","areaStyle":{"normal":{}},"data":[150,232,201,154,190,330,410]},{"name":"示例04","type":"line","stack":"总量","areaStyle":{"normal":{}},"data":[320,332,301,334,390,330,320]}]}';
var INIT_CHARTOPTION_PIE_SCRIPT = '{"title":{"text":"饼图示例","subtext":"副标题","x":"center"},"tooltip":{"trigger":"item","formatter":"{a} <br/>{b} : {c} ({d}%)"},"legend":{"x":"center","y":"bottom","bottom":10,"data":["示例01","示例02","示例03","示例04","示例05"]},"toolbox":{"show":true,"feature":{"mark":{},"dataView":{"show":true,"readOnly":true},"magicType":{"show":true,"type":["pie","funnel"]},"restore":{},"saveAsImage":{}}},"calculable":true,"series":[{"name":"统计项","type":"pie","radius":"55%","roseType":"radius","data":[{"value":235,"name":"示例01"},{"value":274,"name":"示例02"},{"value":310,"name":"示例03","itemStyle":{"normal":{"shadowBlur":200,"shadowColor":"rgba(0, 0, 0, 0.5)"}}},{"value":335,"name":"示例04"},{"value":400,"name":"示例05","itemStyle":{"emphasis":{"shadowBlur":200,"shadowColor":"rgba(0, 0, 0, 0.5)"}}}]}]}';
var INIT_CHARTOPTION_RADAR_SCRIPT = '{"title":{"text":"雷达图示例","subtext":"副标题"},"tooltip":{"trigger":"item"},"legend":{"x":"right","y":"bottom","data":["图一","图二"]},"toolbox":{"show":true,"feature":{"dataView":{"show":true,"readOnly":true},"restore":{"show":true},"saveAsImage":{"show":true}}},"radar":[{"indicator":[{"text":"指标一"},{"text":"指标二"},{"text":"指标三"},{"text":"指标四"},{"text":"指标五"}],"startAngle":90,"splitNumber":4,"shape":"circle","name":{"formatter":"【{value}】","textStyle":{"color":"#72ACD1"}},"splitArea":{"areaStyle":{"color":["rgba(114, 172, 209, 0.2)","rgba(114, 172, 209, 0.4)","rgba(114, 172, 209, 0.6)","rgba(114, 172, 209, 0.8)","rgba(114, 172, 209, 1)"],"shadowColor":"rgba(0, 0, 0, 0.3)","shadowBlur":10}},"axisLine":{"lineStyle":{"color":"rgba(255, 255, 255, 0.5)"}},"splitLine":{"lineStyle":{"color":"rgba(255, 255, 255, 0.5)"}}}],"series":[{"name":"雷达图","type":"radar","itemStyle":{"emphasis":{"lineStyle":{"width":4}}},"symbol":"rect","symbolSize":5,"lineStyle":{"normal":{"type":"dashed"}},"data":[{"value":[100,8,0.4,-80,2000],"name":"图一"}]},{"name":"雷达图","type":"radar","itemStyle":{"emphasis":{"lineStyle":{"width":4}}},"areaStyle":{"normal":{"color":"rgba(255, 255, 255, 0.5)"}},"data":[{"value":[60,5,0.3,-100,1500],"name":"图二"}]}]}';
var INIT_CHARTOPTION_GAUGE_SCRIPT = '{"title":{"text":"油量图示例","subtext":"副标题"},"tooltip":{"formatter":"{a} <br/>{b} : {c}%"},"toolbox":{"feature":{"saveAsImage":{},"restore":{}}},"series":[{"name":"示例01","type":"gauge","detail":{"formatter":"{value}%"},"data":[{"value":10,"name":"统计项"}]}]}';

/*系列值2的图表类型*/
var chartType2Data = [];

/*定义一个全局变量，用于存放图表类型改变前的值*/
var oldChartTypeVal;

/*图表类型所有值*/
var chartTypes = $('#chartType option').map(function () {
    var typeObj = {
        name: $(this).text(),
        value: $(this).val()
    };
    return typeObj;
});

/*==============================方法定义[数据编辑]==============================*/

/*初始化数据源*/
function initTable(metaColumns, sql) {

    /*数据列名*/
    var columns = [];
    var c = metaColumns.length;
    for (var i = 0; i < c; i++) {
        var column = {
            field: metaColumns[i].columnCode,
            title: (metaColumns[i].columnName) ? metaColumns[i].columnName : metaColumns[i].columnCode,
            align: "center",
            width: 100
        };
        columns.push(column);
    }

    /* 加载数据源数据 */
    $("#dashWidgetTable").bootstrapTable({
        url: base + '/sys/dashWidget/getDataByPage',
        pagination: true,
        sidePagination: 'server',
        pageNumber: 1,
        pageSize: 10,
        pageList: [2, 5, 10, 25],
        search: false,
        clickToSelect: true,
        columns: columns,
        queryParams: function (params) {
            var dataParams = {
                sql: sql,
                pageSize: params.limit,
                pageNumber: (params.offset / params.limit) + 1,
            }
            return dataParams;
        }
    });
}

/*初始化组件配置，为空则初始化一个默认脚本*/
function initWidgetConfig(script) {

    widgetConfig = $.parseJSON(script);
    if (widgetConfig['provider'].length < 1) {
        /*增加 provierId 到脚本中*/
        var provider = {code: provierId};
        widgetConfig['provider'].push(provider);
    }
}

/* 此方法主要用于初始化图形，传入显示ID和配置 */
function initchart(id, chartOptions) {

    var dom = document.getElementById(id);
    var myChart = echarts.init(dom);

    option = null;
    option = $.parseJSON(chartOptions);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
    $(window).resize(myChart.resize);
}

/* 回显组件配置数据。 初始化状态 status: true 是；false 否*/
function showWidgetConfig(status) {

    /* 初始化时 */
    if (status) {
        /* 选择图表类型 */
        var chartType = widgetConfig['chart-type'];
        $('#chartType').val(chartType).trigger('change');
        if (chartType == TYPE_BAR) dashWidgetInit = 'false';
    }

    /*初始化加载【系列值2】数据*/
    initSeries();

    /*回显【种类系列】的数据*/
    var categoryDef = widgetConfig['category-def'];
    if (categoryDef) {
        if (categoryDef.length > 0) {
            $('#categoryDef').val(categoryDef[0].expr);
        } else {
            $('#categoryDef').val('');
        }
    }

    /*回显【系列值1】、【系列值2】的数据*/
    var seriesDef = widgetConfig['series-def'];
    showSeriesName(seriesDef, '', '', 'init', status);
    if (seriesDef.length > 0) {
        initChartOptions(TABS_CHART);
    }
}

/*刷新页面数据：【系列值1】、【系列值2】
 *参数含义：
 *1. seriesDef：统计项脚本数组；
 *2. xSeriesName：x轴默认数据；
 *3. ySeriesName：y轴默认数据；
 *4. status：是否初始化 true|false；
 *5. type：当前支持；
 *6. xAdd：系列值1增加统计项数据；
 *7. yAdd：系列值2增加统计项数据。
 **/
function showSeriesName(seriesDef, xSeriesName, ySeriesName, type, status) {

    /*如果xSeriesName有值，表示当前只更新系列值1数据*/
    if (type == 'init' || type == 'xAdd') {
        $('#exprVal1').val('');
        $('#seriesName1').empty();

        /* 记录当前选中的X轴 和 Y 轴 */
        $.each(seriesDef, function (i, v) {

            var yAxisIndex = v['yAxisIndex'];
            if (!yAxisIndex) //x轴时
            {
                var seriesValue = v.name;
                if (!xSeriesName) //获取当前选中的数据
                {
                    xSeriesName = seriesValue;
                }
                var index = seriesValue.substr(seriesValue.length - 1, 1);
                $('#seriesName1').append('<option value="' + seriesValue + '">统计项' + index + '</option>');
            }
        });

        /*设置下拉列表默认选中的值*/
        $('#seriesName1').val(xSeriesName).trigger('change');

        /* 系列值1对应的表达式数据*/
        var xSeriesDef = querySeriesDef(seriesDef, xSeriesName);
        if (xSeriesDef) {
            $('#exprType1').val(xSeriesDef.group).trigger('change');
            $('#exprVal1').val(xSeriesDef.expr);
        }
    }

    /*如果ySeriesName有值，表示当前只更新ySeriesName数据*/
    if (type == 'init' || type == 'yAdd') {
        $('#exprVal2').val('');
        $('#seriesName2').empty();

        /* 记录当前选中的X轴 和 Y 轴 */
        $.each(seriesDef, function (i, v) {

            var yAxisIndex = v['yAxisIndex'];
            if (yAxisIndex) //y轴时
            {
                var seriesValue = v.name;
                /* 获取当前选中数据 */
                if (!ySeriesName) //获取当前选中的数据
                {
                    ySeriesName = seriesValue;
                }
                var index = seriesValue.substr(seriesValue.length - 1, 1);
                $('#seriesName2').append('<option value="' + seriesValue + '">统计项' + index + '</option>');
            }
        });

        /*设置下拉列表默认选中的值*/
        $('#seriesName2').val(ySeriesName).trigger('change');

        /* 系列值2对应的表达式数据*/
        var ySeriesDef = querySeriesDef(seriesDef, ySeriesName);
        if (ySeriesDef) {
            $('#seriesCheck').prop('checked', true);
            seriesChange($('#seriesCheck'));
            $('#exprType2').val(ySeriesDef.group).trigger('change');
            $('#exprVal2').val(ySeriesDef.expr);

            if (status) {
                if (ySeriesDef.type == TYPE_LINE) {
                    var index = querySeriesDefIndex(seriesDef, ySeriesName);
                    if (index != -1) {
                        var series = widgetConfig['series'];
                        var ySeries = series[index];
                        var _areaStyle = ySeries['areaStyle'];
                        if (_areaStyle) {
                            $('#chartType2').val(TYPE_STACK).trigger('change');
                        } else {
                            $('#chartType2').val(ySeriesDef.type).trigger('change');
                        }
                    } else {
                        $.messager.toastr('提示', '未查询到该参数索引，请检查初始化数据是否正确。', 'warning');
                    }
                } else {
                    $('#chartType2').val(ySeriesDef.type).trigger('change');
                }
            }
        }
    }
}

/*根据seriesName获取数组中对象当前索引并返回, -1代表没有找到*/
function querySeriesDefIndex(seriesDef, name) {
    var i = -1;

    if (!name) return i;

    $.each(seriesDef, function (index, val) {

        /*查找到后，跳出循环，返回当前查找对象索引*/
        if (val.name == name) {
            i = index;
            return false;
        }
    });

    return i;
}

/* 1. 初始化时，改变x轴图表类型，需要重新初始化图表，并更改Y轴(系列值2)图表类型可选项 
 * 2. 不是初始化时，改变图表类型需要缓存之前的组件配置脚本[widgetConfig]。
 * 3. 回显组件配置数据*/
function chartTypeChange(val) {

    if (val == oldChartTypeVal) return;

    /*缓存改变之前编辑的的组件配置脚本[widgetConfig]*/
    if (oldChartTypeVal) {
        if (oldChartTypeVal == TYPE_LINE || oldChartTypeVal == TYPE_BAR || oldChartTypeVal == TYPE_STACK) {
            INIT_WIDGETCONFIG_SCRIPT = JSON.stringify(widgetConfig);
        } else if (oldChartTypeVal == TYPE_PIE) {
            INIT_WIDGETCONFIG_PIE_SCRIPT = JSON.stringify(widgetConfig);
        } else if (oldChartTypeVal == TYPE_RADAR) {
            INIT_WIDGETCONFIG_RADAR_SCRIPT = JSON.stringify(widgetConfig);
        } else if (oldChartTypeVal == TYPE_GAUGE) {
            INIT_WIDGETCONFIG_GAUGE_SCRIPT = JSON.stringify(widgetConfig);
        }
    }

    oldChartTypeVal = val; //保存改变值

    /*根据图表类型，初始化组件脚本，给出相应的初始化图表*/
    if (val == TYPE_LINE) {
        /* 初始化脚本 */
        initWidgetConfig(INIT_WIDGETCONFIG_SCRIPT);
        widgetConfig['chart-type'] = TYPE_LINE;
        var series = widgetConfig['series'];
        if (series.length == 0) {
            /* 显示默认图表 */
            initchart(TABS_CHART, INIT_CHARTOPTION_LINE_SCRIPT);
        } else {
            $.each(series, function (i, v) {
                var yAxisIndex = v['yAxisIndex'];
                if (!yAxisIndex) //X轴
                {
                    v['type'] = TYPE_LINE;
                    delete v.areaStyle;
                } else {
                    v['type'] = TYPE_BAR;
                }
            });
        }
    } else if (val == TYPE_BAR) {
        /* 初始化脚本 */
        initWidgetConfig(INIT_WIDGETCONFIG_SCRIPT);
        widgetConfig['chart-type'] = TYPE_BAR;
        var series = widgetConfig['series'];
        if (series.length == 0) {
            /* 显示默认图表 */
            initchart(TABS_CHART, INIT_CHARTOPTION_BAR_SCRIPT);
        } else {
            $.each(series, function (i, v) {
                var yAxisIndex = v['yAxisIndex'];
                if (!yAxisIndex) //X轴
                {
                    v['type'] = TYPE_BAR;
                    delete v.areaStyle;
                } else {
                    v['type'] = TYPE_LINE;
                }
            });
        }
    } else if (val == TYPE_STACK) {
        /* 初始化脚本 */
        initWidgetConfig(INIT_WIDGETCONFIG_SCRIPT);
        widgetConfig['chart-type'] = TYPE_STACK;
        var series = widgetConfig['series'];
        if (series.length == 0) {
            /* 显示默认图表 */
            initchart(TABS_CHART, INIT_CHARTOPTION_STACK_SCRIPT);
        } else {
            $.each(series, function (i, v) {
                var yAxisIndex = v['yAxisIndex'];
                if (!yAxisIndex) //X轴
                {
                    v['type'] = TYPE_LINE;
                    v['areaStyle'] = {normal: {}};
                } else {
                    v['type'] = TYPE_BAR;
                }
            });
        }
    } else if (val == TYPE_PIE) {
        /* 初始化脚本 */
        initWidgetConfig(INIT_WIDGETCONFIG_PIE_SCRIPT);
        widgetConfig['chart-type'] = TYPE_PIE;
        var series = widgetConfig['series'];
        if (series.length == 0) {
            /* 显示默认图表 */
            initchart(TABS_CHART, INIT_CHARTOPTION_PIE_SCRIPT);
        }
    } else if (val == TYPE_RADAR) {
        /* 初始化脚本 */
        initWidgetConfig(INIT_WIDGETCONFIG_RADAR_SCRIPT);
        widgetConfig['chart-type'] = TYPE_RADAR;
        var series = widgetConfig['series'];
        if (series.length == 0) {
            /* 显示默认图表 */
            initchart(TABS_CHART, INIT_CHARTOPTION_RADAR_SCRIPT);
        }
    } else if (val == TYPE_GAUGE) {
        /* 初始化脚本 */
        initWidgetConfig(INIT_WIDGETCONFIG_GAUGE_SCRIPT);
        widgetConfig['chart-type'] = TYPE_GAUGE;
        var series = widgetConfig['series'];
        if (series.length == 0) {
            /* 显示默认图表 */
            initchart(TABS_CHART, INIT_CHARTOPTION_GAUGE_SCRIPT);
        }
    }

    /*显示页面数据*/
    showWidgetConfig(false);
}

/* 初始化系列值 */
function initSeries() {

    var chartTypeVal = $('#chartType').val();

    /*清空Y轴图表类型缓存数据*/
    $('#chartType2').empty();
    chartType2Data = [];
    $('#categoryDefGrp').show();

    /* 图表类型为：折线图、柱形图、面积图时，
     * 1. 显示系列值2，
     * 2. 更改Y轴图表类型可选项*/
    if (chartTypeVal == TYPE_LINE || chartTypeVal == TYPE_BAR || chartTypeVal == TYPE_STACK) {
        $('.seriesGrp2').show();

        /*设置系列值2的图表类型值*/
        if (chartTypeVal == TYPE_LINE || chartTypeVal == TYPE_STACK) //折线图、面积图
        {
            $.each(chartTypes, function (i, val) {
                if (val.value == TYPE_BAR) {
                    var typeObj = {id: val.value, text: val.name};
                    chartType2Data.push(typeObj);
                    return false;
                }
            });
            $('#chartType2').append('<option value="bar">柱形图</option>');
        } else if (chartTypeVal == TYPE_BAR) //柱状图
        {
            $.each(chartTypes, function (i, val) {
                if (val.value == TYPE_LINE || val.value == TYPE_STACK) {
                    var typeObj = {id: val.value, text: val.name};
                    chartType2Data.push(typeObj);
                }
            });
            $('#chartType2').append('<option value="line">折线图</option>');
            $('#chartType2').append('<option value="stack">面积图</option>');
        }
    }
    /* 图表类型为：饼图、雷达图、油量图时，隐藏系列值2 */
    else {
        $('.seriesGrp2').hide();
        if (chartTypeVal == TYPE_GAUGE) {
            $('#categoryDefGrp').hide();
        }
    }
}

/*表达式类型改变*/
function exprTypeChange(val, seriesName, exprTextId) {

    /*验证表达式是否存在*/
    if (!verifyExpr(exprTextId)) {
        return false;
    }

    var seriesDef = widgetConfig['series-def'];

    /*获取当前选择的【统计项】值*/
    var series = $('#' + seriesName).val();

    /*传入更新的参数，并更新 series-def*/
    var parameter = {
        name: series,
        group: val
    };

    updateSeriesDef(seriesDef, parameter, null);
}

/*验证表达式是否有值*/
function verifyExpr(id) {

    var expr = $('#' + id).val();
    if (!expr) {
        return false;
    }
    return true;
}

/*判断选中【系列值2】状态*/
function seriesChange(el) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#chartType2').attr('disabled', false);
        $('#seriesName2').attr('disabled', false);
        $('#addSeries2').attr('disabled', false);
        $('#delSeries2').attr('disabled', false);
        $('#exprType2').attr('disabled', false);
        $('#editExpr2').attr('disabled', false);

        /*设置第二根Y轴的show为true*/
        if (widgetConfig['yAxis']) {
            widgetConfig['yAxis'][1]['show'] = true;
        }

    } else //未选中系列值2，禁用组件
    {
        $('#chartType2').attr('disabled', true);
        $('#seriesName2').attr('disabled', true);
        $('#addSeries2').attr('disabled', true);
        $('#delSeries2').attr('disabled', true);
        $('#exprType2').attr('disabled', true);
        $('#editExpr2').attr('disabled', true);
        $('#seriesName2').empty();
        $('#exprVal2').val('');
        $('#exprType2').val(GROUP_SUM).trigger('change');
        if (chartType2Data.length > 0) {
            $('#chartType2').val(chartType2Data[0].id).trigger('change');
        }

        /*设置第二根Y轴的show为false*/
        if (widgetConfig['yAxis']) {
            widgetConfig['yAxis'][1]['show'] = false;
        }

        /*清除widgetConfig对象中的Y轴数据*/
        if (removeAttrWidgetConfig()) {
            /*重新加载图表*/
            initChartOptions(TABS_CHART);
        }
    }
}

/*更新widgetConfig*/
function removeAttrWidgetConfig() {

    var seriesDef = widgetConfig['series-def'];
    var series = widgetConfig['series'];

    /*判断当前数组存不存在*/
    if (null == seriesDef || seriesDef.length < 1) {
        return false;
    }

    /*移除seriesDef中Y轴数据*/
    $.each(seriesDef, function (index, val) {
        var yAxisIndex = val['yAxisIndex'];
        if (val['yAxisIndex']) seriesDef.splice(index, 1);
    });

    /*移除series中Y轴数据*/
    $.each(series, function (index, val) {
        var yAxisIndex = val['yAxisIndex'];
        if (val['yAxisIndex']) series.splice(index, 1);
    });

    return true;
}

/*添加统计项
 * 1. 添加 seriesDef
 * 2. 添加 series
 * 3. 更新 widgetConfig */
function addSeries(name) {

    /*添加一个统计项*/
    var seriesIndex = calculateSeriesIndex(); //获取当前统计项最大值
    $('#' + name).append('<option value="series' + seriesIndex + '">统计项' + seriesIndex + '</option>');
    $('#' + name).val(SERIES_NAME + seriesIndex);

    /*清空表达式值*/
    var _length = name.substr(name.length - 1, 1);
    $('#exprType' + _length).val(GROUP_SUM).trigger('change');

    /*更新widgetConfig脚本*/
    var seriesDef = widgetConfig['series-def'];
    var seriesName = SERIES_NAME + seriesIndex;
    var axis = 'x'; //设置当前数据为x轴

    if (name == 'seriesName1') //判断是[系列值1]或是[系列值2]
    {
        /*添加 seriesDef 数组*/
        var params = {
            name: seriesName,
            expr: '',
            group: GROUP_SUM,
            type: $('#chartType').val()
        };
        seriesDef.splice(seriesIndex, 0, params);
        axis = 'x';
    } else {
        if (chartType2Data) //判断y轴图表类型是否存在
        {
            var params = {
                name: seriesName,
                expr: '',
                group: GROUP_SUM,
                yAxisIndex: 1,
                type: chartType2Data[0].id
            };
            seriesDef.splice(seriesIndex, 0, params);
            axis = 'y';
        } else {
            $.messager.toastr('错误', '【系列值2】图表类型加载出错', 'error');
            return;
        }
    }

    /*更新 series 数组*/
    updateSeries(seriesDef, params, axis, seriesIndex);
}

/*删除统计项*/
function delSeries(name) {

    /*至少保留一个统计项 */
    var length = $('#' + name + ' option').length;
    if (length < 2) {
        $.messager.toastr('提示', '至少保留一个统计项!', 'warning');
        return;
    }

    var value = $('#' + name).val();
    $('#' + name + ' option:selected').remove();

    /*删除 series、series-def*/
    var seriesDef = widgetConfig['series-def'];
    var series = widgetConfig[SERIES_NAME];

    $.each(seriesDef, function (i, v) {
        if (value == v.name) {
            seriesDef.splice(i, 1);
            series.splice(i, 1);
            return false;
        }
    });

    /*根据x、y轴，更新页面【系列值1】、【系列值2】显示数据*/
    var seriesName = document.getElementById(name); //获取当前操作的【统计项】对象
    if (name == 'seriesName1') {
        seriesNameChange(seriesName, 'x');
    } else {
        seriesNameChange(seriesName, 'y');
    }

    /*删除后，重新刷新图表*/
    initChartOptions(TABS_CHART);
}

/*计算统计项当前最大值*/
function calculateSeriesIndex() {
    var length = 0;

    var options1 = $('#seriesName1 option').map(function () {
        return $(this).val();
    });
    var options2 = $('#seriesName2 option').map(function () {
        return $(this).val();
    });
    var arr = $.merge(options1, options2);

    $.each(arr, function (i, v) {
        var index = v.indexOf(SERIES_NAME) + 6
        var _length = v.substr(index);
        if (Number(_length) > Number(length)) {
            length = Number(_length);
        }
    });

    return length + 1;
}

/* 1. 更新 widgetconfig 脚本中的 series-def、series;
 * 2. series可传入null，更新图表类型时，series不能为空。*/
function updateSeriesDef(seriesDef, parameter, series) {

    $.each(seriesDef, function (i, v) {
        if (parameter.name == v.name) {
            if (parameter.expr) {
                v.expr = parameter.expr;
            }
            if (parameter.group) {
                v.group = parameter.group;
            }
            if (parameter.type) //[系列值2]中类型值改变
            {
                if (series) //更新 seriesDef 同时更新 series
                {
                    var _series = series[i];
                    if (parameter.type == TYPE_STACK) //图表类型为面积图
                    {
                        v.type = TYPE_LINE;
                        _series.type = TYPE_LINE;
                        if (!_series.itemStyle) //添加面积属性
                        {
                            var _itemStyle = {
                                normal: {
                                    areaStyle: {
                                        type: "default"
                                    }
                                }
                            };
                            _series.itemStyle = _itemStyle;
                        }
                    } else //其他类型的图表
                    {
                        v.type = parameter.type;
                        _series.type = parameter.type;
                        delete _series.itemStyle; //删除面积图表属性
                    }
                } else {
                    $.messager.toastr('提示', '统计项为空，无法更新图表类型！', 'warning');
                }
            }
        }
    });

    /*重新刷新图表*/
    initChartOptions(TABS_CHART);
}

function updateSeries(seriesDef, params, axis, seriesIndex) {

    var series = widgetConfig[SERIES_NAME];
    var chartType = params.type;
    var _series;

    if (axis == 'x') {
        if (chartType == TYPE_LINE || chartType == TYPE_BAR) //折线图、柱形图
        {
            _series = {
                name: params.name,
                type: chartType,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            };
        } else if (chartType == TYPE_STACK) //面积图
        {
            _series = {
                name: params.name,
                type: TYPE_LINE,
                stack: '总量',
                areaStyle: {normal: {}}
            };
        } else if (chartType == TYPE_PIE) //饼图
        {
            _series = {
                name: params.name,
                type: chartType,
                radius: '55%',
                roseType: 'radius',
                itemStyle: {
                    emphasis: {
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        } else if (chartType == TYPE_RADAR) //雷达图
        {
            _series = {
                name: params.name,
                type: chartType,
                itemStyle: {
                    emphasis: {
                        symbol: 'rect',
                        symbolSize: 5,
                        lineStyle: {
                            width: 4,
                            normal: {type: 'dashed'}
                        },
                        areaStyle: {
                            normal: {
                                color: 'rgba(255, 255, 255, 0.5)'
                            }
                        }
                    }
                }
            }
        } else if (chartType == TYPE_GAUGE) //油量图
        {
            _series = {
                name: params.name,
                type: chartType,
                detail: {
                    formatter: '{value}%'
                },
                title: {
                    show: true
                }

            }
        }

        /*更新 series*/
        series.splice(seriesIndex, 0, _series);

        /*更新页面显示*/
        showSeriesName(seriesDef, params.name, '', 'xAdd', true);
    } else if (axis == 'y') //添加【系列值2】上的统计项时，只需处理 折线图、柱形图、面积图
    {
        if (chartType == TYPE_STACK) //面积图
        {
            _series = {
                name: params.name,
                type: TYPE_LINE,
                yAxisIndex: params.yAxisIndex,
                stack: '总量',
                areaStyle: {normal: {}}
            };
        } else {
            _series = {
                name: params.name,
                type: chartType,
                yAxisIndex: params.yAxisIndex,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            };
        }

        /*更新 series*/
        series.splice(seriesIndex, 0, _series);

        /*更新页面显示*/
        showSeriesName(seriesDef, '', params.name, 'yAdd', true);
    }

    //更新坐标，饼图、油量图
    if (params.type == TYPE_PIE || params.type == TYPE_GAUGE) {
        $.each(series, function (index, ser) {
            var x = 75;
            var y = 75;
            if (index == 0) {
                x = series.length > 1 ? 25 : 50;
                y = series.length > 2 ? 25 : 50;
            } else if (index == 1) {
                x = 75;
                y = series.length > 2 ? 25 : 50;
            } else if (index == 2) {
                x = series.length > 1 ? 25 : 50;
                y = 75;
            }
            ser.center = [x + '%', y + '%'];
            if (params.type == TYPE_GAUGE) ser.radius = '70%';
            else ser.radius = '50%';
        });
    }
}

/*选择下拉列表切换数据*/
function seriesNameChange(el, axis) {

    /* 【系列值1】【系列值2】*/
    var seriesDef = widgetConfig['series-def'];
    var thisSeriesDef = querySeriesDef(seriesDef, el.value);

    if (axis == 'x') {
        $('#exprType1').val(thisSeriesDef.group).trigger('change');
        $('#exprVal1').val(thisSeriesDef.expr);
    } else {
        $('#exprType2').val(thisSeriesDef.group).trigger('change');
        $('#exprVal2').val(thisSeriesDef.expr);
        $('#chartType2').val(thisSeriesDef.type).trigger('change');
    }
}

/*改变【系列值2】类型值，更新组件配置脚本：widgetConfig*/
function seriesChartTypeChange(val, seriesName, exprId) {

    /*验证对应表达式值是否存在*/
    if (!verifyExpr(exprId)) return false;

    var seriesDef = widgetConfig['series-def'];
    var series = widgetConfig['series'];

    /*获取当前选择的统计项*/
    var seriesVal = $('#' + seriesName).val();

    /*传入更新的参数*/
    var parameter = {
        name: seriesVal,
        type: val
    };

    /*更新配置*/
    updateSeriesDef(seriesDef, parameter, series);
}

/* 根据当前统计项[seriesName]值，获取对应的 seriesDef 对象 */
function querySeriesDef(seriesDef, value) {
    var thisSeriesDef = null;

    /*value为空，直接返回*/
    if (!name) return thisSeriesDef;

    /*循环找对应的 seriesDef 对象，并返回*/
    $.each(seriesDef, function (i, v) {
        if (v.name == value) {
            thisSeriesDef = v;
            return false;
        }
    });
    return thisSeriesDef;
}


/*验证当前脚本中：expr\ group是否已设置完成*/
function verifyWidgetConfig() {
    var isVerify = '';

    //验证的时候获取当前数据设计界面的类型
    var type = $('#chartType').val();
    if (type != TYPE_GAUGE) {
        var categorydef = widgetConfig['category-def'];

        /*查找到后，跳出循环，返回当前查找对象*/
        if (categorydef.length < 1) {
            isVerify = '【种类系列】还未选择, 请先选择。';
            return isVerify;
        }
    }

    var seriesDef = widgetConfig['series-def'];

    /*查找到后，跳出循环，返回当前查找对象*/
    if (seriesDef.length < 1) {
        isVerify = '【系列值1】中【统计项】至少添加一项。';
        return isVerify;
    }

    /*循环查找还未填写的expr\ group*/
    $.each(seriesDef, function (index, val) {

        var tmpName = val.name;
        var i = tmpName.substr(tmpName.length - 1, 1);

        /*查找到后，跳出循环，返回当前查找对象*/
        if (null == val.expr || val.expr == '') {
            isVerify = '【统计项' + i + '】中的 表达式值还未填写,请先填写。';
            return false;
        }

        if (null == val.group || val.group == '') {
            isVerify = '【统计项' + i + '】中的 表达式函数还未填写,请先填写。';
            ;
            return false;
        }
    });

    return isVerify;
}
