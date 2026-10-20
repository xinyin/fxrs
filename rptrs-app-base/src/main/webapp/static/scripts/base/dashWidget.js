/*图表类型为折线图*/
var TYPE_LINE = 'line';
/*图表类型为柱形图*/
var TYPE_BAR = 'bar';
/*图表类型为面积图*/
var TYPE_STACK = 'stack';
/*图表类型为饼图*/
var TYPE_PIE = 'pie';
/*图表类型为油量图*/
var TYPE_GAUGE = 'gauge';
/*图表类型为雷达图*/
var TYPE_RADAR = 'radar';
/*图表分组类型*/
var GROUP_SUM = 'Sum';
/*Value Series name定义规则*/
var SERIES_NAME = 'series';
/*图表显示页面div*/
var TABS_CHART0 = 'container';
var TABS_CHART1 = 'container1';
/*初始化脚本*/
//直接系
var INIT_WIDGETCONFIG_SCRIPT = '{"provider":[],"chart-type":"bar","category-def":[],"series-def":[],"x-change-y":false,"backgroundColor":"rgba(255, 255, 0, 0.15)","color":["#ff7f50","#87cefa","#da70d6","#32cd32","#6495ed","#6b8e23","#ff00ff","#3cb371","#b8860b","#30e0e0","#1e90ff","#ff6347","#7b68ee","#00fa9a","#ffd700","#ff69b4","#ba55d3","#cd5c5c","#ffa500","#40e0d0"],"title":{"show":true,"text":"数据统计图","subtext":"数据统计图","x":"center","y":"top","textAlign":"center","textStyle":{"fontFamily":"微软雅黑","fontSize":20,"fontStyle":"italic","fontWeight":"bolder","color":"#87cefa","align":"right"}},"toolbox":{"show":true,"orient":"vertical","x":"right","y":"center","itemSize":16,"itemGap":5,"feature":{"mark":{"show":true},"dataZoom":{"show":true},"dataView":{"show":false},"magicType":{"show":false},"restore":{"show":true},"saveAsImage":{"show":true}}},"tooltip":{"show":true,"trigger":"item"},"calculable":true,"animation":true,"legend":{"show":false,"orient":"horizontal","textStyle":{"color":"auto","fontSize":12},"x":"right","y":"bottom","backgroundColor":"rgba(255, 0, 255, 0.15)","itemGap":10,"borderWidth":2},"xAxis":[{"show":true,"name":"日期","axisLine":{"show":true},"axisTick":{"show":false},"axisLabel":{"show":true,"rotate":0},"type":"category"}],"yAxis":[{"name":"值(万元)","type":"value","show":true,"axisLine":{"show":true},"axisTick":{"show":false},"axisLabel":{"show":true,"rotate":45}},{"name":"件数","type":"value","show":false,"axisLine":{"show":true},"axisTick":{"show":true},"axisLabel":{"show":true}}],"series":[]}';
//饼图
var INIT_WIDGETCONFIG_PIE_SCRIPT = '{"provider":[],"chart-type":"pie","category-def":[],"series-def":[],"backgroundColor":"rgba(255, 255, 255, 0)","color":["#ff7f50","#87cefa","#da70d6","#32cd32","#6495ed","#6b8e23","#ff00ff","#3cb371","#b8860b","#30e0e0","#1e90ff","#ff6347","#7b68ee","#00fa9a","#ffd700","#ff69b4","#ba55d3","#cd5c5c","#ffa500","#40e0d0"],"title":{"show":true,"text":"饼图实例","subtext":"饼图实例","x":"center","y":"top","textAlign":"center","textStyle":{"fontFamily":"微软雅黑","fontSize":20,"fontStyle":"italic","fontWeight":"bolder","color":"#87cefa","align":"right"}},"toolbox":{"show":true,"orient":"vertical","x":"right","y":"center","itemSize":16,"itemGap":5,"feature":{"mark":{"show":true},"dataZoom":{"show":true},"dataView":{"show":false},"magicType":{"show":false},"restore":{"show":true},"saveAsImage":{"show":true}}},"tooltip":{"show":true,"trigger":"item"},"calculable":true,"animation":true,"legend":{"show" : true,	"orient" :"vertical","textStyle" : {"color" : "auto","fontSize" : 12},"x" : "left","y" :"top","itemGap" : 10,"data" : [ "实例1", "实例2", "实例2"]},"series":[]}';
//雷达图
var INIT_WIDGETCONFIG_RADAR_SCRIPT = '{"provider":[],"chart-type":"radar","category-def":[],"series-def":[],"backgroundColor":"rgba(255, 255, 255, 0)","color":["#ff7f50","#87cefa","#da70d6","#32cd32","#6495ed","#6b8e23","#ff00ff","#3cb371","#b8860b","#30e0e0","#1e90ff","#ff6347","#7b68ee","#00fa9a","#ffd700","#ff69b4","#ba55d3","#cd5c5c","#ffa500","#40e0d0"],"title":{"show":true,"text":"雷达图实例","subtext":"雷达图实例","x":"center","y":"top","textAlign":"center","textStyle":{"fontFamily":"微软雅黑","fontSize":20,"fontStyle":"italic","fontWeight":"bolder","color":"#87cefa","align":"right"}},"toolbox":{"show":true,"orient":"vertical","x":"right","y":"center","itemSize":16,"itemGap":5,"feature":{"mark":{"show":true},"dataZoom":{"show":true},"dataView":{"show":false},"magicType":{"show":false},"restore":{"show":true},"saveAsImage":{"show":true}}},"tooltip":{"show":true,"trigger":"item"},"calculable":true,"animation":true,"legend":{"show" : true,	"orient" :"vertical","textStyle" : {"color" : "auto","fontSize" : 12},"x" : "left","y" :"top","itemGap" : 10,"data" : [ "实例1", "实例2", "实例2"]}, "polar": [{}],  "calculable": true,"series":[]}';
//油量图
var INIT_WIDGETCONFIG_GAUGE_SCRIPT = '{"provider":[],"chart-type":"gauge", "series-def":[],"backgroundColor":"rgba(255, 255, 255, 0)","color":["#ff7f50","#87cefa","#da70d6","#32cd32","#6495ed","#6b8e23","#ff00ff","#3cb371","#b8860b","#30e0e0","#1e90ff","#ff6347","#7b68ee","#00fa9a","#ffd700","#ff69b4","#ba55d3","#cd5c5c","#ffa500","#40e0d0"],"title":{"show":true,"text":"油量图实例","subtext":"油量图实例","x":"center","y":"top","textAlign":"center","textStyle":{"fontFamily":"微软雅黑","fontSize":20,"fontStyle":"italic","fontWeight":"bolder","color":"#87cefa","align":"right"}},"toolbox":{"show":true,"orient":"vertical","x":"right","y":"center","itemSize":16,"itemGap":5,"feature":{"mark":{"show":true},"dataZoom":{"show":true},"dataView":{"show":false},"magicType":{"show":false},"restore":{"show":true},"saveAsImage":{"show":true}}},"tooltip":{"show":true,"trigger":"item"},"calculable":true,"animation":true, "calculable": true,"series":[]}';
/*初始化图表显示*/
var INIT_CHARTOPTION_BAR_SCRIPT = '{"backgroundColor":"rgba(255, 255, 0, 0.15)","color":["#ff7f50","#87cefa","#da70d6","#32cd32","#6495ed","#6b8e23","#ff00ff","#3cb371","#b8860b","#30e0e0","#1e90ff","#ff6347","#7b68ee","#00fa9a","#ffd700","#ff69b4","#ba55d3","#cd5c5c","#ffa500","#40e0d0"],"title":{"show":true,"text":"数据统计图","subtext":"数据统计图","x":"center","y":"top","textAlign":"center","textStyle":{"fontFamily":"微软雅黑","fontSize":20,"fontStyle":"italic","fontWeight":"bolder","color":"#87cefa","align":"right"}},"toolbox":{"show":true,"orient":"vertical","x":"right","y":"center","itemSize":16,"itemGap":5,"feature":{"mark":{"show":true},"dataZoom":{"show":true},"dataView":{"show":false},"magicType":{"show":false},"restore":{"show":true},"saveAsImage":{"show":true}}},"tooltip":{"show":true,"trigger":"item"},"calculable":true,"animation":true,"legend":{"show":false,"orient":"horizontal","textStyle":{"color":"auto","fontSize":12},"x":"right","y":"bottom","backgroundColor":"rgba(255, 0, 255, 0.15)","itemGap":10,"data":["2015年07月10日","2015年07月13日","2015年07月14日","2015年07月15日","2015年07月16日"],"borderWidth":2},"xAxis":[{"show":true,"name":"日期","axisLine":{"show":true},"axisTick":{"show":false},"axisLabel":{"show":true,"rotate":0},"type":"category","data":["2015年07月10日","2015年07月13日","2015年07月14日","2015年07月15日","2015年07月16日"]}],"yAxis":[{"name":"值(万元)","type":"value","show":true,"axisLine":{"show":true},"axisTick":{"show":false},"axisLabel":{"show":true,"rotate":45}},{"name":"件数","type":"value","show":false,"axisLine":{"show":true},"axisTick":{"show":true},"axisLabel":{"show":true}}],"series":[{"name":"总和","type":"bar","markPoint":{"data":[{"type":"max","name":"最大值","symbol":"pin","symbolSize":16},{"type":"min","name":"最小值"}]},"markLine":{"symbol":["circle","circle"],"data":[{"type":"average","name":"平均值"}]},"data":[840000,150000,3380018,980000,7350000,14300000,1050000,323620000]},{"name":"件数","type":"bar","yAxisIndex":1,"data":[33620000,13620000,73620000,53620000,93620000,83620000,23620000,543620000]}]}';

/*图表参数设置tree初始数据*/
var init_Set_Script = '[{"id":"option","text":"图表选项","children":[{"id":"title","text":"标题"},{"id":"toolbar","text":"工具栏"},{"id":"legend","text":"图例"},{"id":"axis","text":"Axis","state":"closed","children":[{"id":"X-Axis","text":"X-Axis"},{"id":"Y-Axis1","text":"Y-Axis1"},{"id":"Y-Axis2","text":"Y-Axis2"}]}]},{"id":"series","text":"Series","state":"closed","children":[]}]';

var init_WidgetConfig_line = '{"provider":[{"code":"20160126163750729-4756ad5594fb42a6bc01479e5e488836"}],"chart-type":"line","category-def":[],"series-def":[],"backgroundColor":"rgba(255, 255, 0, 0.15)","color":["#ff7f50","#87cefa","#da70d6","#32cd32","#6495ed","#6b8e23","#ff00ff","#3cb371","#b8860b","#30e0e0","#1e90ff","#ff6347","#7b68ee","#00fa9a","#ffd700","#ff69b4","#ba55d3","#cd5c5c","#ffa500","#40e0d0"],"title":{"show":true,"text":"数据统计图","subtext":"数据统计图","x":"center","y":"top","textAlign":"center","textStyle":{"fontFamily":"微软雅黑","fontSize":20,"fontStyle":"italic","fontWeight":"bolder","color":"#87cefa","align":"right"}},"toolbox":{"show":true,"orient":"vertical","x":"right","y":"center","itemSize":16,"itemGap":5,"feature":{"mark":{"show":true},"dataZoom":{"show":true},"restore":{"show":true},"saveAsImage":{"show":true}}},"tooltip":{"show":true,"trigger":"item"},"calculable":true,"animation":true,"legend":{"show":false,"orient":"horizontal","textStyle":{"color":"auto","fontSize":12},"x":"right","y":"bottom","backgroundColor":"rgba(255, 0, 255, 0.15)","itemGap":10,"borderWidth":2},"xAxis":[{"show":true,"name":"日期","axisLine":{"show":true},"axisTick":{"show":false},"axisLabel":{"show":true,"rotate":0},"type":"category"}],"yAxis":[{"name":"值(万元)","type":"value","show":true,"axisLine":{"show":true},"axisTick":{"show":false},"axisLabel":{"show":true,"rotate":45}},{"name":"件数","type":"value","show":false,"axisLine":{"show":true},"axisTick":{"show":true},"axisLabel":{"show":true}}],"series":[]}';
var init_ChartOption_Line_Script = '{"backgroundColor":"rgba(255, 255, 0, 0.15)","color":["#ff7f50","#87cefa","#da70d6","#32cd32","#6495ed","#6b8e23","#ff00ff","#3cb371","#b8860b","#30e0e0","#1e90ff","#ff6347","#7b68ee","#00fa9a","#ffd700","#ff69b4","#ba55d3","#cd5c5c","#ffa500","#40e0d0"],"title":{"show":true,"text":"数据统计图","subtext":"数据统计图","x":"center","y":"top","textAlign":"center","textStyle":{"fontFamily":"微软雅黑","fontSize":20,"fontStyle":"italic","fontWeight":"bolder","color":"#87cefa","align":"right"}},"toolbox":{"show":true,"orient":"vertical","x":"right","y":"center","itemSize":16,"itemGap":5,"feature":{"mark":{"show":true},"dataZoom":{"show":true},"dataView":{"show":false},"magicType":{"show":false},"restore":{"show":true},"saveAsImage":{"show":true}}},"tooltip":{"show":true,"trigger":"item"},"calculable":true,"animation":true,"legend":{"show":false,"orient":"horizontal","textStyle":{"color":"auto","fontSize":12},"x":"right","y":"bottom","backgroundColor":"rgba(255, 0, 255, 0.15)","itemGap":10,"data":["2015年07月10日","2015年07月13日","2015年07月14日","2015年07月15日","2015年07月16日"],"borderWidth":2},"xAxis":[{"show":true,"name":"日期","axisLine":{"show":true},"axisTick":{"show":false},"axisLabel":{"show":true,"rotate":0},"type":"category","data":["2015年07月10日","2015年07月13日","2015年07月14日","2015年07月15日","2015年07月16日"]}],"yAxis":[{"name":"值(万元)","type":"value","show":true,"axisLine":{"show":true},"axisTick":{"show":false},"axisLabel":{"show":true,"rotate":45}},{"name":"件数","type":"value","show":false,"axisLine":{"show":true},"axisTick":{"show":true},"axisLabel":{"show":true}}],"series":[{"name":"总和","type":"line","markPoint":{"data":[{"type":"max","name":"最大值","symbol":"pin","symbolSize":16},{"type":"min","name":"最小值"}]},"markLine":{"symbol":["circle","circle"],"data":[{"type":"average","name":"平均值"}]},"data":[840000,150000,3380018,980000,7350000,14300000,1050000,323620000]},{"name":"件数","type":"line","yAxisIndex":1,"data":[33620000,13620000,73620000,53620000,93620000,83620000,23620000,543620000]}]}';
var init_ChartOption_Stack_Script = '{"backgroundColor":"rgba(255, 255, 0, 0.15)","color":["#ff7f50","#87cefa","#da70d6","#32cd32","#6495ed","#6b8e23","#ff00ff","#3cb371","#b8860b","#30e0e0","#1e90ff","#ff6347","#7b68ee","#00fa9a","#ffd700","#ff69b4","#ba55d3","#cd5c5c","#ffa500","#40e0d0"],"title":{"show":true,"text":"数据统计图","subtext":"数据统计图","x":"center","y":"top","textAlign":"center","textStyle":{"fontFamily":"微软雅黑","fontSize":20,"fontStyle":"italic","fontWeight":"bolder","color":"#87cefa","align":"right"}},"toolbox":{"show":true,"orient":"vertical","x":"right","y":"center","itemSize":16,"itemGap":5,"feature":{"mark":{"show":true},"dataZoom":{"show":true},"dataView":{"show":false},"magicType":{"show":false},"restore":{"show":true},"saveAsImage":{"show":true}}},"tooltip":{"show":true,"trigger":"item"},"calculable":true,"animation":true,"legend":{"show":false,"orient":"horizontal","textStyle":{"color":"auto","fontSize":12},"x":"right","y":"bottom","backgroundColor":"rgba(255, 0, 255, 0.15)","itemGap":10,"data":["2015年07月10日","2015年07月13日","2015年07月14日","2015年07月15日","2015年07月16日"],"borderWidth":2},"xAxis":[{"show":true,"name":"日期","axisLine":{"show":true},"axisTick":{"show":false},"axisLabel":{"show":true,"rotate":0},"type":"category","data":["2015年07月10日","2015年07月13日","2015年07月14日","2015年07月15日","2015年07月16日"]}],"yAxis":[{"name":"值(万元)","type":"value","show":true,"axisLine":{"show":true},"axisTick":{"show":false},"axisLabel":{"show":true,"rotate":45}},{"name":"件数","type":"value","show":false,"axisLine":{"show":true},"axisTick":{"show":true},"axisLabel":{"show":true}}],"series":[{"name":"总和","type":"line","itemStyle": {"normal": {"areaStyle": {"type": "default"}}},"markPoint":{"data":[{"type":"max","name":"最大值","symbol":"pin","symbolSize":16},{"type":"min","name":"最小值"}]},"markLine":{"symbol":["circle","circle"],"data":[{"type":"average","name":"平均值"}]},"data":[840000,150000,3380018,980000,7350000,14300000,1050000,323620000]},{"name":"件数","type":"line","yAxisIndex":1,"itemStyle": {"normal": {"areaStyle": {"type": "default"}}},"data":[33620000,13620000,73620000,53620000,93620000,83620000,23620000,543620000]}]}';

/*定义一个常量保存左中右数据*/
var L_R_DATA = [{
    label: '居中',
    value: 'center'
}, {
    label: '居左',
    value: 'left'
}, {
    label: '居右',
    value: 'right'
}];

/*定义一个常量保存水平或者垂直排列数据*/
var H_V_DATA = [{
    label: '水平',
    value: 'horizontal'
}, {
    label: '垂直',
    value: 'vertical'
}];


/*定义一个常量保存上中下数据*/
var T_B_DATA = [{
    label: '居中',
    value: 'center'
}, {
    label: '居上',
    value: 'top'
}, {
    label: '居下',
    value: 'bottom'
}];


/*定义一个常量保存标注点形状数据*/
var SYMBOL_DATA = [{
    label: '圆形',
    value: 'circle'
}, {
    label: '正方形',
    value: 'rectangle'
}, {
    label: '三角形',
    value: 'triangle'
}, {
    label: '菱形',
    value: 'diamond'
}, {
    label: '空心圆',
    value: 'emptyCircle'
}, {
    label: '空心正方形',
    value: 'emptyRectangle'
}, {
    label: '空心三角',
    value: 'emptyTriangle'
}, {
    label: '空心菱形',
    value: 'emptyDiamond'
}, {
    label: '心形',
    value: 'heart'
}, {
    label: '水滴',
    value: 'droplet'
}, {
    label: '标注',
    value: 'pin'
}, {
    label: '箭头',
    value: 'arrow'
}, {
    label: '五角型',
    value: 'star'
}];


/*定义一个全局变量，用于存放选择的Axis*/
var checkedAxis;

/*定义一个全局变量，用于存放选择的Series*/
var checkedSeries;

/*根据图表数据生成参数选项的tree*/
function generateTreeData(data, treeData) {


    /*根据axis数据生成tree数据*/
    var xAxis = data['xAxis'];
    var axisArray = [];
    if (xAxis) {
        $.each(xAxis, function (i, n) {
            if (n.show || n.show == 'true') {
                var no = i + 1;
                var xAxisMenu = {id: 'xAxis' + no, text: 'X-Axis' + no};
                axisArray.push(xAxisMenu);
            }
        });


    }

    var yAxis = data['yAxis'];
    if (yAxis) {
        $.each(yAxis, function (i, n) {
            //console.info(n.show);
            if (n.show || n.show == 'true') {
                var no = i + 1;
                var yAxisMenu = {id: 'yAxis' + no, text: 'Y-Axis' + no};
                axisArray.push(yAxisMenu);
            }
        });
    }

    /*当axis没有数据的时候，就把axis的数据删除*/
    if (axisArray.length == 0) {
        treeData[0]['children'].splice(3, 1);
    } else {
        treeData[0]['children'][3]['children'] = axisArray;
    }


    /*根据series数据生成tree数据*/
    var series = data['series-def'];
    var seriesArray = [];
    if (series) {
        if (data['chart-type'] == TYPE_GAUGE) {
            $.each(series, function (i, n) {
                var no = i + 1;
                var xSeriesMenu = {
                    id: 'seriesS' + no, text: n['name'],
                    children: [{id: 'gaugePl' + no, text: '表盘'},
                        {id: 'pointer' + no, text: '指针'},
                        {id: 'scaleGG' + no, text: '刻度'},
                        {id: 'valueGG' + no, text: '数值'}]
                };
                seriesArray.push(xSeriesMenu);
            });
        } else {
            $.each(series, function (i, n) {
                var no = i + 1;
                var xSeriesMenu = {id: 'seriesC' + no, text: n['name']};
                seriesArray.push(xSeriesMenu);
            });
        }

    }
    treeData[1]['children'] = seriesArray;

    /*根据polar数据生成tree数据*/
    var polar = data['polar'];
    if (polar) {
        treeData[0]['children'].splice(3, 1, {id: 'polar', text: 'Polar Series'});
    }

    /*仪表盘中没有图例，所以要删除图例节点*/
    if (data['chart-type'] == TYPE_GAUGE) {
        treeData[0]['children'].splice(2, 1);
    }

    return treeData;
}

/*根据name获取数组中对象, 返回当前查找对象*/
function querySeriesDef(seriesDef, name) {
    var series = null;

    /*Y轴name为空，直接返回*/
    if (!name) {
        return series;
    }

    /*循环找对应name的对象*/
    $.each(seriesDef, function (index, val) {

        /*查找到后，跳出循环，返回当前查找对象*/
        if (val.name == name) {
            series = val;
            return false;
        }
    });

    return series;
}

/*根据name获取数组中对象, 并更新数组中的对象。seriesDef 数组、 parameter 更新参数对象、 seriess 数组可以传入null,需要更新图表类型，seriess必传*/
function updateSeriesDef(seriesDef, parameter, seriess) {


    /*更新series-def和series*/
    $.each(seriesDef, function (index, val) {

        /*查找到后，更新当前对象对象*/
        if (val.name == parameter.name) {
            if (parameter.expr) {
                val.expr = parameter.expr;
            } else if (parameter.group) {
                val.group = parameter.group;
            } else if (parameter.type) {
                /*更新seriesDef同时更新series*/
                if (seriess) {
                    var _series = seriess[index];

                    /*判断如果为面积，需要单独处理*/
                    if (parameter.type == TYPE_STACK) {
                        val.type = TYPE_LINE;
                        _series.type = TYPE_LINE;
                        /*添加面积类型属性*/
                        if (!_series.itemStyle) {
                            var _itemStyle = {
                                normal: {
                                    areaStyle: {
                                        type: "default"
                                    }
                                }
                            };

                            _series.itemStyle = _itemStyle;
                        }
                    } else {
                        val.type = parameter.type;
                        _series.type = parameter.type;

                        /*删除面积类型属性*/
                        delete _series.itemStyle;
                    }
                } else {
                    alert('series 数组为空，无法更新图表类型。');
                }
            }

        }
    });

    /*更新后，重新刷新图表*/
    initChartOptions(TABS_CHART0);
}

/*根据name获取数组中对象, 并删除数组中的对象。seriesDef 数组和series 数组, name 参数*/
function deleteSeriesDef(seriesDef, name, seriess) {

    /*删除series-def和series*/
    $.each(seriesDef, function (index, val) {

        /*查找到后，删除当前对象对象*/
        if (val.name == name) {
            seriesDef.splice(index, 1);
            seriess.splice(index, 1);

            return false;
        }
    });

    /*删除后，重新刷新图表*/
    initChartOptions(TABS_CHART0);
}

/*根据name获取数组中对象, 增加对象到数组中。seriesDef 数组和series 数组, parameter 新增参数对象 ,
 *  _name 新增参数位置的name, type 为X轴或者Y轴, seriesNameObj 可传null，为null时不更新页面*/
function addSeriesDef(seriesDef, seriess, parameter, _name, type) {

    var index = querySeriesDefIndex(seriesDef, _name) + 1;
    /*1000000 代表没有找到*/
    if (index != 1000000) {
        /*判断是X轴还是Y轴*/
        if (type == 'X') {
            /*更新seriesDef数组*/
            var _seriesDef = {
                name: parameter.name,
                expr: parameter.expr,
                group: parameter.group,
                type: parameter.type
            };

            seriesDef.splice(index, 0, _seriesDef);

            var _seriess;
            /*更新seriess数组, 先判断series类型是否为面积，如果是面积图，需要特殊处理 */
            if (parameter.type == TYPE_STACK) {
                _seriess = {
                    name: parameter.name,
                    type: TYPE_LINE,
                    itemStyle: {
                        normal: {
                            areaStyle: {
                                type: "default"
                            }
                        }
                    }
                };
            } else {
                if (parameter.type == TYPE_GAUGE) {
                    _seriess = {
                        name: parameter.name,
                        type: parameter.type,
                        z: 3,
                        max: 1000,
                        min: 0,
                        splitNumber: 10
                    };
                } else {
                    _seriess = {
                        name: parameter.name,
                        type: parameter.type,
                    };
                }

            }

            seriess.splice(index, 0, _seriess);

            /*更新页面显示*/
            showSeriesName(seriesDef, parameter.name, '', 'xAdd', true);
        } else {
            /*更新seriesDef数组*/
            var _seriesDef = {
                name: parameter.name,
                expr: parameter.expr,
                group: parameter.group,
                yAxisIndex: parameter.yAxisIndex,
                type: parameter.type
            };

            seriesDef.splice(index, 0, _seriesDef);


            var _seriess;
            /*更新seriess数组, 先判断series类型是否为面积，如果是面积图，需要特殊处理 */
            if (parameter.type == TYPE_STACK) {
                _seriess = {
                    name: parameter.name,
                    type: TYPE_LINE,
                    yAxisIndex: parameter.yAxisIndex,
                    itemStyle: {
                        normal: {
                            areaStyle: {
                                type: "default"
                            }
                        }
                    }
                };
            } else {
                _seriess = {
                    name: parameter.name,
                    type: parameter.type,
                    yAxisIndex: parameter.yAxisIndex
                };
            }
            seriess.splice(index, 0, _seriess);

            /*更新页面显示*/
            showSeriesName(seriesDef, '', parameter.name, 'yAdd', true);
        }
        //更新坐标
        if (parameter.type == TYPE_PIE || parameter.type == TYPE_GAUGE) {
            $.each(seriess, function (index, ser) {
                var x = 75;
                var y = 75;
                if (index == 0) {
                    x = seriess.length > 1 ? 25 : 50;
                    y = seriess.length > 2 ? 25 : 50;
                } else if (index == 1) {
                    x = 75;
                    y = seriess.length > 2 ? 25 : 50;
                } else if (index == 2) {
                    x = seriess.length > 1 ? 25 : 50;
                    y = 75;
                }
                ser.center = [x + '%', y + '%'];
                ser.radius = '50%';
            });
        }
    }
}

/*根据name获取数组中对象当前索引, 返回当前查找对象的索引, 1000000 代表没有找到*/
function querySeriesDefIndex(seriesDef, name) {
    var i = 1000000;

    /*Y轴name为空，直接返回*/
    if (!name) {
        return i;
    }

    /*循环找对应name的对象*/
    $.each(seriesDef, function (index, val) {

        /*查找到后，跳出循环，返回当前查找对象*/
        if (val.name == name) {
            i = index;
            return false;
        }
    });

    return i;
}

/*根据name获取数组中最大的series_name, 返回当前查找对象最大值 + 1SERIES_NAME*/
function querySeriesDefMax(seriesDef) {
    var length = 0;

    /*循环找name最大的, Number(*) 截取出来的数字类型为字符串，需要进行转义*/
    $.each(seriesDef, function (index, val) {

        var name = val.name;
        var nameIndex = name.indexOf(SERIES_NAME) + 6;
        var _length = name.substr(nameIndex);

        /*如果当前查找的名字大于lengt，覆盖，循环找出最大的 */
        if (Number(_length) > Number(length)) {
            length = Number(_length);
        }
    });

    /*当前最大name加1构造下一个name*/
    length = Number(length) + 1;

    return SERIES_NAME + length;
}

/*选择下列表切换数据*/
function seriesChange(el, type) {
    /* Value Series1、2 */
    var seriesDef = widgetConfig['series-def'];

    var series = querySeriesDef(seriesDef, el.value);

    if (type == 'X') {
        $('#seriesGroup1').combobox('setValue', series.group);
        $('#seriesExpr1').val(series.expr);
    } else {
        $('#seriesGroup2').combobox('setValue', series.group);
        $('#seriesExpr2').val(series.expr);
        $('#seriesChartType').combobox('setValue', series.type);
    }
}

/*判断选中事件*/
function seriesClick(el) {
    /*选中Value Series 2可以编辑，否则不可编辑*/
    if ($(el).prop("checked")) {
        /*启用文本框选择*/
        $('#seriesName2').removeAttr("disabled");
        $('#seriesChartType').combobox({disabled: false});
        $('#seriesGroup2').combobox({disabled: false});
        $('#editExpr2').attr('onclick', 'editExpr(\'seriesName2\', \'seriesExpr2\')');
        $('#addSeries2').attr('onclick', 'addSeries(\'seriesName2\')');
        $('#delSeries2').attr('onclick', 'delSeries(\'seriesName2\')');
        if (seriesChartTypeData.length > 0) {
            $('#seriesChartType').combobox('setValue', seriesChartTypeData[0].name);
        }

        /*设置第二根Y轴的show为true*/
        if (widgetConfig['yAxis']) {
            widgetConfig['yAxis'][1]['show'] = true;
        }

    } else {
        /*禁止文本框选择*/
        $('#seriesName2').attr('disabled', 'disabled');
        $('#seriesChartType').combobox({disabled: true});
        $('#seriesGroup2').combobox({disabled: true});
        $('#editExpr2').attr('onclick', 'close()');
        $('#addSeries2').attr('onclick', 'close()');
        $('#delSeries2').attr('onclick', 'close()');

        /*清除widgetConfig对象中的Y轴数据*/
        if (removeAttrWidgetConfig('yCheck', null)) {
            /*重新加载图表*/
            initChartOptions(TABS_CHART0);
        }


        if (seriesChartTypeData.length > 0) {
            $('#seriesChartType').combobox('setValue', seriesChartTypeData[0].name);
        }

        /*清除文本框中的数据*/
        $('#seriesName2').empty();
        $('#seriesExpr2').val('');
        /*设置第二根Y轴的show为false*/
        if (widgetConfig['yAxis']) {
            widgetConfig['yAxis'][1]['show'] = false;
        }
    }
}

/*提供一处widgetConfig对象中的属性值方法，并更新widgetConfig对象, type 更新属性key, parameter 可接受参数，对象，数组*/
function removeAttrWidgetConfig(type, parameter) {

    var isEnable = false;

    /*yCheck: 是否存在Y轴，如果不存在，清除数组中的数据*/
    if (type == 'yCheck') {
        var seriesDef = widgetConfig['series-def'];
        var series = widgetConfig['series'];

        /*判断当前数组存不存在*/
        if (null == seriesDef || seriesDef.length < 1) {
            return isEnable;
        }

        /*移除seriesDef中Y轴数据*/
        $.each(seriesDef, function (index, val) {
            var yAxisIndex = val['yAxisIndex'];

            /* 判断是X轴还是Y轴 ,X轴数据不做处理*/
            if (yAxisIndex) {
                seriesDef.splice(index, 1);
            }
        });

        /*移除series中Y轴数据*/
        $.each(series, function (index, val) {
            var yAxisIndex = val['yAxisIndex'];

            /* 判断是X轴还是Y轴 ,X轴数据不做处理*/
            if (yAxisIndex) {
                series.splice(index, 1);
            }
        });

        isEnable = true;
    }
    /*此处可继续添加*/
    else if (1 > 2) {

    }

    return isEnable;
}

/* 回显页面数据,数据直接从widgetConfig中获取 */
function showWidgetConfig(init) {
    /* 选择图表类型 */
    chartType = widgetConfig['chart-type'];
    if (init) {
        /*面积需要特殊处理*/
        if (chartType == TYPE_LINE) {
            var series = widgetConfig['series'];

            if (series.length > 0) {
                var _ySeries = series[0];

                /*获取图形样式, line下出现默认样式，会变成面积图*/
                var itemStyle = _ySeries['itemStyle'];
                if (itemStyle) {
                    /*normal 默认样式*/
                    var normal = itemStyle['normal'];
                    if (normal) {
                        $('#chartType').combobox('setValue', 'stack');
                    } else {
                        /*emphasis 强调样式*/
                        $('#chartType').combobox('setValue', 'stack');
                    }
                } else {
                    if (_ySeries.type == TYPE_BAR)
                        dashWidgetInit = 'false';
                    $('#chartType').combobox('setValue', _ySeries.type);
                }
            }
        } else {
            if (chartType == TYPE_BAR)
                dashWidgetInit = 'false';
            $('#chartType').combobox('setValue', chartType);
        }
    }

    /*Y轴图表类型加载*/
    chartTypeInit();

    /* Value Series1、2 */
    var seriesDef = widgetConfig['series-def'];
    /*页面显示X轴和Y轴数据*/
    showSeriesName(seriesDef, '', '', 'init', init);

    /* Category Series */
    var categoryDef = widgetConfig['category-def'];
    if (categoryDef) {
        if (categoryDef.length > 0) {
            $('#categoryDef').val(categoryDef[0].expr);
        } else {
            $('#categoryDef').val('');
        }
    }
    if (seriesDef.length > 0) {
        /* 初始化图表 */
        initChartOptions(TABS_CHART0);
    }

    if (chartType == TYPE_BAR) {
        $('#series2Div').show();
        $('#categoryDefDiv').show();
    } else if (chartType == TYPE_LINE) {
        $('#series2Div').show();
        $('#categoryDefDiv').show();
    } else if (chartType == TYPE_STACK) {
        $('#series2Div').show();
        $('#categoryDefDiv').show();
    } else if (chartType == TYPE_PIE) {
        $('#series2Div').hide();
        $('#categoryDefDiv').show();
    } else if (chartType == TYPE_GAUGE) {
        $('#series2Div').hide();
        $('#categoryDefDiv').hide();
    } else if (chartType == TYPE_RADAR) {
        $('#series2Div').hide();
        $('#categoryDefDiv').show();
    }
}

/*刷新页面数据: xSeriesName X轴默认数据 ， ySeriesName Y轴默认数据, type 当前支持， init 初始化， xAdd x新增, yAdd Y新增*/
function showSeriesName(seriesDef, xSeriesName, ySeriesName, type, init) {
    /*如果ySeriesName有值，表示当前只更新ySeriesName数据*/
    if (type == 'init' || type == 'xAdd') {
        /*X轴页面显示数据*/
        $('#seriesName1 option').remove();
        $('#seriesGroup1').combobox('clear');
        $('#seriesExpr1').val('');

        /* 记录当前选中的X轴 和 Y 轴 */
        $.each(seriesDef, function (index, val) {

            var yAxisIndex = val['yAxisIndex'];

            /* 判断是X轴还是Y轴 */
            if (!yAxisIndex) {
                /* 获取当前选中数据 */
                if (!xSeriesName) {
                    xSeriesName = val.name;
                }

                /* X 轴 */
                $('#seriesName1').append('<option value="' + val.name + '">' + val.name + '</option>');
            }
        });

        /*设置下拉列表默认选中的值*/
        $('#seriesName1').val(xSeriesName);

        /* Value Series1 expr */
        var xSeries = querySeriesDef(seriesDef, xSeriesName);
        if (xSeries) {
            $('#seriesGroup1').combobox('setValue', xSeries.group);
            $('#seriesExpr1').val(xSeries.expr);
        }
    }

    /*如果xSeriesName有值，表示当前只更新xSeriesName数据*/
    if (type == 'init' || type == 'yAdd') {
        /*添加数据之前，先清除原有数据*/
        $('#seriesName2 option').remove();
        $('#seriesGroup2').combobox('clear');
        $('#seriesExpr2').val('');

        /* 记录当前选中的X轴 和 Y 轴 */
        $.each(seriesDef, function (index, val) {

            var yAxisIndex = val['yAxisIndex'];

            /* 判断是X轴还是Y轴 */
            if (yAxisIndex) {
                /* 获取当前选中数据 */
                if (!ySeriesName) {
                    ySeriesName = val.name;
                }

                /* Y 轴 */
                $('#seriesName2').append('<option value="' + val.name + '">' + val.name + '</option>');
            }
        });

        /*设置下拉列表默认选中的值*/
        $('#seriesName2').val(ySeriesName);

        /* Value Series2 expr */
        var ySeries = querySeriesDef(seriesDef, ySeriesName);
        /* 为空则表示Y轴无数据 */
        if (ySeries) {
            $('#seriesCheckbox').prop('checked', true);
            var dom = document.getElementById('seriesCheckbox');
            seriesClick(dom);

            $('#seriesGroup2').combobox('setValue', ySeries.group);
            $('#seriesExpr2').val(ySeries.expr);

            if (init) {
                /*Y轴为面积需要特殊处理*/
                if (ySeries.type == TYPE_LINE) {
                    var series = widgetConfig['series'];

                    /*获取索引*/
                    var index = querySeriesDefIndex(seriesDef, ySeriesName);

                    /*1000000 代表没有找到*/
                    if (index != 1000000) {
                        var _ySeries = series[index];

                        /*获取图形样式, line下出现默认样式，会变成面积图*/
                        var itemStyle = _ySeries['itemStyle'];
                        if (itemStyle) {
                            /*normal 默认样式*/
                            var normal = itemStyle['normal'];
                            if (normal) {
                                $('#seriesChartType').combobox('setValue', 'stack');
                            } else {
                                /*emphasis 强调样式*/
                                $('#seriesChartType').combobox('setValue', 'stack');
                            }
                        } else {
                            $('#seriesChartType').combobox('setValue', ySeries.type);
                        }
                    } else {
                        alert('未查询到该参数索引，请检查初始化数据是否正确。');
                    }
                } else {
                    $('#seriesChartType').combobox('setValue', ySeries.type);
                }
            }
        }
    }
}

/*选择group下来框事件，更新widgetConfig*/
function seriesGroupChange(newValue, oldValue, type, exprId) {

    /*判断是第一次加载，还是选择触发该事件*/
    if (oldValue) {
        /*验证expr是否存在*/
        if (!verifyExpr(exprId)) {
            return false;
        }

        var seriesDef = widgetConfig['series-def'];

        /*获取当前选择的Value Series*/
        var seriesName = $('#' + type).val();

        /*传入更新的参数*/
        var parameter = {
            name: seriesName,
            group: newValue
        };

        /*更新配置*/
        updateSeriesDef(seriesDef, parameter, null);
    }
}

/*选择seriesChartType下来框事件，更新widgetConfig*/
function seriesChartTypeChange(newValue, oldValue, type, exprId) {

    /*判断是第一次加载，还是选择触发该事件*/
    if (oldValue) {
        /*验证expr是否存在*/
        if (!verifyExpr(exprId)) {
            return false;
        }

        var seriesDef = widgetConfig['series-def'];
        var series = widgetConfig['series'];

        /*获取当前选择的Value Series*/
        var seriesName = $('#' + type).val();

        /*传入更新的参数*/
        var parameter = {
            name: seriesName,
            type: newValue
        };

        /*更新配置*/
        updateSeriesDef(seriesDef, parameter, series);
    }
}

/*增加当前选中X 或者 Y轴数据*/
function addSeries(name) {

    /*获取当钱下拉列表的值*/
    var seriesName = $('#' + name).val();

    var seriesDef = widgetConfig['series-def'];
    var series = widgetConfig['series'];

    /*参数对象*/
    var parameter = null;

    /*当前新增的X轴还是Y轴*/
    var type = 'X';

    /*新增加一项到列表中, 判断是X轴还是Y轴*/
    if (name == 'seriesName1') {
        /*增加项名字*/
        var _seriesName = querySeriesDefMax(seriesDef);

        var chartType = $('#chartType').combobox('getValue');

        /*更新seriesDef数组*/
        parameter = {
            name: _seriesName,
            expr: '',
            group: GROUP_SUM,
            type: chartType
        };

        type = 'X';
    } else {
        /*增加项名字*/
        var _seriesName = querySeriesDefMax(seriesDef);

        /*判断图表类型数组是否存在*/
        if (seriesChartTypeData) {
            /*更新seriesDef数组*/
            parameter = {
                name: _seriesName,
                expr: '',
                group: GROUP_SUM,
                yAxisIndex: 1,
                type: seriesChartTypeData[0].name
            };

            type = 'Y';
        } else {
            $.messager.alert('系统提示', '图表类型加载出错。', 'info');
            return;
        }
    }

    /*更新widgetConfig脚本*/
    addSeriesDef(seriesDef, series, parameter, seriesName, type);
}

/*删除当前选中X 或者 Y轴数据*/
function delSeries(name) {

    /*判断当前select的数组长度, 小于2不能删除*/
    var length = $('#' + name + ' option').size();
    if (length < 2) {
        $.messager.alert('系统提示', '至少保留一个Series。', 'info');
        return;
    }

    var seriesDef = widgetConfig['series-def'];
    var series = widgetConfig['series'];

    var val = $('#' + name).val();
    $('#' + name + ' option[value=\'' + val + '\']').remove();

    var seriesName = document.getElementById(name);

    /*判断是x轴还是Y轴,清空页面显示数据*/
    if (name == 'seriesName1') {
        seriesChange(seriesName, 'X');
    } else {
        seriesChange(seriesName, 'Y');
    }

    /*删除选中数据*/
    deleteSeriesDef(seriesDef, val, series);
}

/*验证expr是否有值*/
function verifyExpr(id) {

    var expr = $('#' + id).val();

    if (!expr) {
        return false;
    }

    return true;
}

/*验证当前脚本中：expr\ group是否已设置完成*/
function verifyWidgetConfig() {
    var isVerify = '';

    //验证的时候获取当前数据设计界面的类型
    var type = $('#chartType').combobox('getValue');
    if (type != TYPE_GAUGE) {
        var categorydef = widgetConfig['category-def'];

        /*查找到后，跳出循环，返回当前查找对象*/
        if (categorydef.length < 1) {
            isVerify = 'Category Series还未选择, 请先选择。';
            return isVerify;
        }
    }

    var seriesDef = widgetConfig['series-def'];

    /*查找到后，跳出循环，返回当前查找对象*/
    if (seriesDef.length < 1) {
        isVerify = 'Value Series 1至少添加一项。';
        return isVerify;
    }

    /*循环查找还未填写的expr\ group*/
    $.each(seriesDef, function (index, val) {

        /*查找到后，跳出循环，返回当前查找对象*/
        if (null == val.expr || val.expr == '') {
            isVerify = val.name + '中的 Expr还未填写,请先填写。';
            return false;
        }

        if (null == val.group || val.group == '') {
            isVerify = val.name + '中的 Group还未填写,请先填写。';
            ;
            return false;
        }
    });

    return isVerify;
}

/*更新category-def*/
function updateCategoryDef(expr) {

    if (expr) {
        var categoryDef = widgetConfig['category-def'];

        var exprObj = {
            expr: expr
        };

        categoryDef[0] = exprObj;

        /*刷新图表*/
        initChartOptions(TABS_CHART0);
    }
}

/*初始化加载seriesChartType数据*/
function chartTypeInit() {
    chartType = $('#chartType').combobox('getValue');

    /* 初始化seriesChartTypes */
    var chartTypes = $('#chartType').combobox('getData');

    /*判断，如果chartType 为bar，则chartType为 line stack*/
    seriesChartTypeData = [];
    if (chartType == TYPE_BAR) {
        $.each(chartTypes, function (index, val) {
            if (val.value == TYPE_LINE || val.value == TYPE_STACK) {
                var typeObj = {
                    name: val.value,
                    value: val.name
                };
                seriesChartTypeData.push(typeObj);
            }
        });
    }
    /* 如果chartType 为line 或者 stack，则为bar*/
    else if (chartType == TYPE_LINE || chartType == TYPE_STACK) {
        $.each(chartTypes, function (index, val) {
            if (val.value == TYPE_BAR || val.value == TYPE_BAR) {
                var typeObj = {
                    name: val.value,
                    value: val.name
                };
                seriesChartTypeData.push(typeObj);
            }
        });
    }

    if (seriesChartTypeData.length > 0) {
        /*初始化seriesChartType*/
        $('#seriesChartType').combobox({
            valueField: 'name',
            textField: 'value',
            data: seriesChartTypeData
        });
        $('#seriesChartType').combobox('setValue', seriesChartTypeData[0].name);
    }

}

/*页面初始化时，判断脚本是否为空，为空则初始化一个脚本*/
function initWidgetConfig(script) {
    /* 初始化脚本 */
    widgetConfig = $.parseJSON(script);
    if (widgetConfig['provider'].length < 1) {
        /*增加widgetProvierId到脚本中*/
        var provider = {
            code: widgetProvierId
        };

        widgetConfig['provider'].push(provider);
    }
}

/*改变X轴图表类型， 需要重新初始化数据，并更改Y轴图表类型可选项*/
function chartTypeChange(newValue, oldValue) {
    /*当不是初始化的时候，改变图表类型，就重新初始化图表数据*/
    if (oldValue) {
        /*判断当前是否是初始化*/
        if (dashWidgetInit == 'init') {
            dashWidgetInit = 'false';
            return;
        }

        if (oldValue == newValue) {
            return;
        }

        /*记录上次编辑的图表脚本*/
        if (oldValue == TYPE_BAR) {
            INIT_WIDGETCONFIG_SCRIPT = $.toJSON(widgetConfig);
        } else if (oldValue == TYPE_LINE) {
            INIT_WIDGETCONFIG_SCRIPT = $.toJSON(widgetConfig);
        } else if (oldValue == TYPE_STACK) {
            INIT_WIDGETCONFIG_SCRIPT = $.toJSON(widgetConfig);
        } else if (oldValue == TYPE_PIE) {
            INIT_WIDGETCONFIG_PIE_SCRIPT = $.toJSON(widgetConfig);
        } else if (oldValue == TYPE_GAUGE) {
            INIT_WIDGETCONFIG_GAUGE_SCRIPT = $.toJSON(widgetConfig);
        } else if (oldValue == TYPE_RADAR) {
            INIT_WIDGETCONFIG_RADAR_SCRIPT = $.toJSON(widgetConfig);
        }

        /*柱形图表*/
        if (newValue == TYPE_BAR) {
            /* 初始化脚本 */
            initWidgetConfig(INIT_WIDGETCONFIG_SCRIPT);
            /* 显示默认图表 */
            initchart("container", INIT_CHARTOPTION_BAR_SCRIPT);
            widgetConfig['chart-type'] = TYPE_BAR;
            var series = widgetConfig['series'];
            $.each(series, function (index, obj) {
                var yAxisIndex = obj['yAxisIndex'];
                /* 判断是X轴还是Y轴 */
                if (!yAxisIndex) {
                    obj['type'] = TYPE_BAR;
                } else {
                    obj['type'] = TYPE_LINE;
                }
            });
            /*初始化图表的时候，seriesCheckbox设定为未选中*/
            $('#seriesCheckbox').attr("checked", false);
        } else if (newValue == TYPE_LINE) {
            /* 初始化脚本 */
            initWidgetConfig(INIT_WIDGETCONFIG_SCRIPT);
            /* 初始化脚本 */
            widgetConfig['chart-type'] = TYPE_LINE;
            var series = widgetConfig['series'];
            $.each(series, function (index, obj) {
                var yAxisIndex = obj['yAxisIndex'];
                /* 判断是X轴还是Y轴 */
                if (!yAxisIndex) {
                    obj['type'] = TYPE_LINE;
                    delete obj.itemStyle;
                } else {
                    obj['type'] = TYPE_BAR;
                }
            });
            /*初始化图表的时候，seriesCheckbox设定为未选中*/
            $('#seriesCheckbox').attr("checked", false);
            $('#series2Div').show();
        } else if (newValue == TYPE_STACK) {
            /* 初始化脚本 */
            initWidgetConfig(INIT_WIDGETCONFIG_SCRIPT);
            widgetConfig['chart-type'] = TYPE_STACK;
            var series = widgetConfig['series'];
            $.each(series, function (index, obj) {
                var yAxisIndex = obj['yAxisIndex'];
                /* 判断是X轴还是Y轴 */
                if (!yAxisIndex) {
                    obj['type'] = TYPE_LINE;
                    obj['itemStyle'] = {normal: {areaStyle: {type: 'default'}}};
                } else {
                    obj['type'] = TYPE_BAR;
                }
            });
            /*初始化图表的时候，seriesCheckbox设定为未选中*/
            $('#seriesCheckbox').attr("checked", false);
        } else if (newValue == TYPE_PIE) {
            /* 初始化脚本 */
            initWidgetConfig(INIT_WIDGETCONFIG_PIE_SCRIPT);
            widgetConfig['chart-type'] = TYPE_PIE;
            /*初始化图表的时候，seriesCheckbox设定为未选中*/
            $('#seriesCheckbox').attr("checked", false);

        } else if (newValue == TYPE_GAUGE) {
            /* 初始化脚本 */
            initWidgetConfig(INIT_WIDGETCONFIG_GAUGE_SCRIPT);
            widgetConfig['chart-type'] = TYPE_GAUGE;
            /*初始化图表的时候，seriesCheckbox设定为未选中*/
            $('#seriesCheckbox').attr("checked", false);
        } else if (newValue == TYPE_RADAR) {
            initWidgetConfig(INIT_WIDGETCONFIG_RADAR_SCRIPT);
            widgetConfig['chart-type'] = TYPE_RADAR;

            /*初始化图表的时候，seriesCheckbox设定为未选中*/
            $('#seriesCheckbox').attr("checked", false);
        }

        /*显示页面数据*/
        showWidgetConfig(false);
    }
}

/* 此方法主要用于初始化图形，传入显示ID和配置 */
function initchart(id, chartOptions) {
    var dom = document.getElementById(id);

    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = $.parseJSON(chartOptions);

    if (option && typeof option === "object") {
        var startTime = +new Date();
        myChart.setOption(option, true);
        var endTime = +new Date();
        var updateTime = endTime - startTime;
        console.log("Time used:", updateTime);
    }
}


/*显示数据查询出来的结果*/
function showResult(metaColumns, data) {
    /*数据列名*/
    var columns = [];
    var c = metaColumns.length;
    for (var i = 0; i < c; i++) {
        var column = {
            field: metaColumns[i].columnCode,
            title: (metaColumns[i].columnName) ? metaColumns[i].columnName : metaColumns[i].columnCode
        };
        columns.push(column);
    }

    $('#queryResultTable').datagrid({
        noheader: true,
        method: 'post',
        singleSelect: true, //多选
        fitColumns: true,
        fit: true,
        collapsible: true,//可折叠
        pagination: false, //显示分页
        rownumbers: true,
        columns: [columns],
        data: data,
        onLoadSuccess: function (data) {
            var array = [];
            $.each(columns, function (i, col) {
                array.push(col.field);
            });
            $('#queryResultTable').datagrid('doCellTip', {delay: 500, fields: array});
        }
    });
}

/************************图表参数设置*****************************/


/*定义一个常量保存南丁格尔玫瑰图模式数据*/
var ROSETYPE_DATA = [{
    label: '半径',
    value: 'radius'
}, {
    label: '面积',
    value: 'area'
}];

/*定义一个常量保存极坐标的形状数据*/
var POLARTYPE_DATA = [{
    label: '多边形',
    value: 'polygon'
}, {
    label: '圆形',
    value: 'circle'
}];


/*定义一个常量定义仪表盘指针颜色数据*/
var POINTER_DATA = [{
    label: '自动',
    value: 'auto'
}, {
    label: '自定义',
    value: 'custom'
}];


/*初始化参数设置*/
function initParameterSet(isTree, type) {

    /*isTree 为true时初始化tree*/
    if (isTree) {
        initTree();
    }

    /*进入tabs页时，默认初始化图表选项*/
    if (type == 'option') {
        /*背景色设置*/
        var backgroundColor = widgetConfig['backgroundColor'];
        if (backgroundColor) {
            $('#bgColorId').attr("checked", true);
            $('#bgColor').css('backgroundColor', backgroundColor);
            $('#bgColor').val(RGBA2HEX(backgroundColor));
            $('#bgAlpha').numberbox('setValue', RGBA2A(backgroundColor));

            var rgb = document.getElementById("bgColorId");
            bgColorClick(rgb);

            changeBgAlpha();
        }

        /*是否开启鼠标悬停提示*/
        var tooltip = widgetConfig['tooltip'];
        if (tooltip) {
            var show = tooltip['show'];
            if (show) {
                $('#tooltipShow').attr("checked", true);
            }
        }

        /*是否开启动画效果 */
        var animation = widgetConfig['animation'];
        if (animation) {
            $('#animation').attr("checked", true);
        }

        /*是否显示标题*/
        var title = widgetConfig['title'];
        if (title) {
            /*初始化title数据*/
            initTitle(title);

        }

        /*是否选中工具栏*/
        var toolbox = widgetConfig['toolbox'];

        if (toolbox) {
            /*初始化工具栏*/
            initToolbox(toolbox);
        }

        /*是否选中图例*/
        var legend = widgetConfig['legend'];

        if (legend) {
            /*初始化图例*/
            initLegend(legend);
        }

    }

}


/*初始化title*/
function initTitle(title) {
    /*初始化标题x坐标位置下拉数据*/
    $('#titleX').combobox({
        onChange: function (newValue, oldValue) {
            titleXChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        editable: false,
        valueField: 'value',
        textField: 'label',
        data: L_R_DATA
    });

    /*初始化标题y坐标位置下拉数据*/
    $('#titleY').combobox({
        editable: false,
        onChange: function (newValue, oldValue) {
            titleYChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        valueField: 'value',
        textField: 'label',
        data: T_B_DATA
    });

    /*初始化标题y坐标位置下拉数据*/
    $('#titleTextAlign').combobox({
        editable: false,
        onChange: function (newValue, oldValue) {
            titleTextAlignChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        valueField: 'value',
        textField: 'label',
        data: L_R_DATA
    });


    /*判断是否显示标题*/
    var show = title['show'];
    if (show) {
        $('#titleShow').attr("checked", true);
    }

    /*设置标题在x坐标对应的位置*/
    var x = title['x'];
    if (x) {
        $('#titleX').combobox('setValue', x);
    }

    /*设置标题在Y坐标对应的位置*/
    var y = title['y'];
    if (y) {
        $('#titleY').combobox('setValue', y);
    }

    /*设置标题在Y坐标对应的位置*/
    var textAlign = title['textAlign'];
    if (textAlign) {
        $('#titleTextAlign').combobox('setValue', textAlign);
    }

    /*设置标题的padding*/
    var padding = title['padding'];
    if (padding) {
        var paddingArray = padding;

        if (paddingArray[0]) {
            $('#titlePaddingTop').numberbox('setValue', paddingArray[0]);
        }

        if (paddingArray[1]) {
            $('#titlePaddingRight').numberbox('setValue', paddingArray[1]);
        }

        if (paddingArray[2]) {
            $('#titlePaddingBottom').numberbox('setValue', paddingArray[2]);
        }

        if (paddingArray[3]) {
            $('#titlePaddingLeft').numberbox('setValue', paddingArray[3]);
        }
    }


    /*初始化padding设置事件*/
    $('#titlePaddingTop').blur(function () {
        titlePaddingSet();
    });

    $('#titlePaddingRight').blur(function () {
        titlePaddingSet();
    });

    $('#titlePaddingBottom').blur(function () {
        titlePaddingSet();
    });

    $('#titlePaddingLeft').blur(function () {
        titlePaddingSet();
    });


    /*获得标题的默认值*/
    var text = title['text'];
    if (text) {
        $('#titleText').val(text);
    }

    /*初始化修改标题事件*/
    $('#titleText').blur(function () {
        widgetConfig['title']['text'] = $('#titleText').val();
        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    });


    /*获得副标题的默认值*/
    var subtext = title['subtext'];
    if (subtext) {
        $('#titleSubtext').val(subtext);
    }

    /*初始化修改副标题事件*/
    $('#titleSubtext').blur(function () {
        widgetConfig['title']['subtext'] = $('#titleSubtext').val();
        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    });
}


/*初始化toolBox*/
function initToolbox(toolbox) {
    /*初始化工具箱x坐标位置下拉数据*/
    $('#toolboxX').combobox({
        onChange: function (newValue, oldValue) {
            toolboxXChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        editable: false,
        valueField: 'value',
        textField: 'label',
        data: L_R_DATA
    });

    /*初始化工具箱y坐标位置下拉数据*/
    $('#toolboxY').combobox({
        editable: false,
        onChange: function (newValue, oldValue) {
            toolboxYChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        valueField: 'value',
        textField: 'label',
        data: T_B_DATA
    });


    /*判断是否显示工具箱*/
    var show = toolbox['show'];
    if (show) {
        $('#toolboxShow').attr("checked", true);
    }

    /*判断是否显示工具箱中工具*/
    var feature = toolbox['feature'];
    if (feature) {

        /*判断是否显示辅助线*/
        var mark = feature['mark'];
        if (mark && mark['show']) {
            $('#toolboxMarkShow').attr("checked", true);
        }

        /*判断是否显示框选区域缩放*/
        var dataZoom = feature['dataZoom'];
        if (dataZoom && dataZoom['show']) {
            $('#toolboxDataZoomShow').attr("checked", true);
        }

        /*判断是否显示数据视图*/
        var dataView = feature['dataView'];
        if (dataView && dataView['show']) {
            $('#toolboxDataViewShow').attr("checked", true);
        }

        /*判断是否显示动态类型切换*/
        var magicType = feature['magicType'];
        if (magicType && magicType['show']) {
            $('#toolboxMagicTypeShow').attr("checked", true);
        }

        /*判断是否显示还原*/
        var restore = feature['restore'];
        if (restore && restore['show']) {
            $('#toolboxRestoreShow').attr("checked", true);
        }

        /*判断是否显示保存图片（IE8-不支持）*/
        var saveAsImage = feature['saveAsImage'];
        if (saveAsImage && saveAsImage['show']) {
            $('#toolboxSaveAsImageShow').attr("checked", true);
        }
    }

    /*设置工具箱在x坐标对应的位置*/
    var x = toolbox['x'];
    if (x) {
        $('#toolboxX').combobox('setValue', x);
    }

    /*设置工具箱在Y坐标对应的位置*/
    var y = toolbox['y'];
    if (y) {
        $('#toolboxY').combobox('setValue', y);
    }


    /*获得工具箱icon大小*/
    var itemSize = toolbox['itemSize'];
    if (itemSize) {
        $('#toolboxItemSize').numberbox('setValue', itemSize);
    }

    /*初始化修改工具箱icon大小事件*/
    $('#toolboxItemSize').blur(function () {

        var itemSize = $('#toolboxItemSize').numberbox('getValue');

        if (IsNum(itemSize)) {
            toolbox['itemSize'] = parseFloat(itemSize);
        } else {
            delete toolbox['itemSize'];
        }
        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    });


    /*获得工具箱各个item之间的间隔的默认值*/
    var itemGap = toolbox['itemGap'];
    if (itemGap) {
        $('#toolboxItemGap').numberbox('setValue', itemGap);
    }

    /*初始化修改工具箱各个item之间的间隔事件*/
    $('#toolboxItemGap').blur(function () {
        var itemGap = $('#toolboxItemGap').numberbox('getValue');
        if (IsNum(itemGap)) {
            toolbox['itemGap'] = parseFloat(itemGap);
        } else {
            delete toolbox['itemGap'];
        }
        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    });
}


/*初始化legend*/
function initLegend(legend) {

    /*初始化图例y坐标位置下拉数据*/
    $('#legendOrient').combobox({
        editable: false,
        onChange: function (newValue, oldValue) {
            legendOrientChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        valueField: 'value',
        textField: 'label',
        data: H_V_DATA
    });

    /*初始化图例x坐标位置下拉数据*/
    $('#legendX').combobox({
        onChange: function (newValue, oldValue) {
            legendXChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        editable: false,
        valueField: 'value',
        textField: 'label',
        data: L_R_DATA
    });

    /*初始化图例y坐标位置下拉数据*/
    $('#legendY').combobox({
        editable: false,
        onChange: function (newValue, oldValue) {
            legendYChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        valueField: 'value',
        textField: 'label',
        data: T_B_DATA
    });


    /*判断是否显示图例*/
    var show = legend['show'];
    if (show) {
        $('#legendShow').attr("checked", true);
    }

    /*设置图例排列方式 */
    var orient = legend['orient'];
    if (orient) {
        $('#legendOrient').combobox('setValue', orient);
    }


    /*设置图例在x坐标对应的位置*/
    var x = legend['x'];
    if (x) {
        $('#legendX').combobox('setValue', x);
    }

    /*设置图例在Y坐标对应的位置*/
    var y = legend['y'];
    if (y) {
        $('#legendY').combobox('setValue', y);
    }

    /*背景色设置*/
    var backgroundColor = legend['backgroundColor'];
    if (backgroundColor) {
        $('#legendBackgroundColor').css('backgroundColor', backgroundColor);
        $('#legendBackgroundColor').val(RGBA2HEX(backgroundColor));
        $('#legendBackgroundAlpha').numberbox('setValue', RGBA2A(backgroundColor));

    }
    legendBgColor();
    changeLegendBgAlpha();
    legendBorderColor();

    /*背景色设置*/
    var borderColor = legend['borderColor'];
    if (borderColor) {
        $('#legendBorderColor').css('backgroundColor', borderColor);
        $('#legendBorderColor').val(borderColor);
    }

    /*获得工具箱各个item之间的间隔的默认值*/
    var itemGap = legend['itemGap'];
    if (itemGap) {
        $('#legendItemGap').val(itemGap);
    }
    /*初始化修改图例各个item之间的间隔事件*/
    $('#legendItemGap').blur(function () {
        var itemGap = $('#legendItemGap').numberbox('getValue');
        if (IsNum(itemGap)) {
            legend['itemGap'] = parseFloat(itemGap);
        } else {
            delete legend['itemGap'];
        }
        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    });

}

/*hex转rgba*/
function HEX2RGBA(rgb_color, alp) {
    //注：rgb_color的格式为#FFFFFFF
    var r = parseInt("0x" + rgb_color.substr(1, 2));
    var g = parseInt("0x" + rgb_color.substr(3, 2));
    var b = parseInt("0x" + rgb_color.substr(5, 2));
    var a = parseFloat(alp) / 100;//为透明度
    return "rgba(" + r + "," + g + "," + b + "," + a + ")";
}

/*rgba转hex*/
function RGBA2HEX(rgba_color) {
    //注：rgba_color的格式为rgba(0,0,0,0.1)
    var arr = rgba_color.split("(")[1].split(")")[0].split(",");
    var rgb = "rgb(" + arr[0] + "," + arr[1] + "," + arr[2] + ")";
    return RGB2HEX(rgb);
}

/*rgba转hex*/
function changeRGBA(rgba_color, alp) {
    //注：rgba_color的格式为rgba(0,0,0,0.1)
    if (rgba_color) {
        var arr = rgba_color.split("(")[1].split(")")[0].split(",");
        var a = parseFloat(alp) / 100;//为透明度
        return "rgba(" + arr[0] + "," + arr[1] + "," + arr[2] + "," + a + ")";
    }
}

/*从rgba中获得透明度*/
function RGBA2A(rgba_color) {
    //注：rgba_color的格式为rgba(0,0,0,0.1)
    var arr = rgba_color.split("(")[1].split(")")[0].split(",");
    var a = parseFloat(arr[3]) * 100;
    return a;
}

/*当0不够时，补全操作*/
function zero_fill_hex(num, digits) {
    var s = num.toString(16);
    while (s.length < digits)
        s = "0" + s;
    return s;
}

/*rgb转hex*/
function RGB2HEX(rgb) {

    if (rgb.charAt(0) == '#')
        return rgb;

    var ds = rgb.split(/\D+/);
    var decimal = Number(ds[1]) * 65536 + Number(ds[2]) * 256 + Number(ds[3]);
    return "#" + zero_fill_hex(decimal, 6);
}

/*判断是否为数字*/
function IsNum(num) {

    var reNum = /^\d*$/;
    return (reNum.test(num));
}

/*初始化，左侧tree*/
function initTree() {
    var data = $.parseJSON(init_Set_Script);
    data = generateTreeData(widgetConfig, data);
    $('#tableTree').tree({
        data: data,
        onLoadSuccess: function (node, data) {
            loadSuccess(node, data);
        },
        onSelect: function (node) {
            $('.preferences').hide();
            if (node.id == 'option') {
                $('#parameterOption').show();
            } else if (node.id == 'title') {
                $('#parameterTitle').show();
            } else if (node.id == 'toolbar') {
                $('#parameterToolbox').show();
            } else if (node.id == 'legend') {
                $('#parameterLegend').show();
            } else if (node.id == 'axis') {
                $('#parameterAxis').show();
                $('#XChangeY').attr('checked', widgetConfig['x-change-y']);
            } else if (node.id == 'xAxis1') {
                $('#parameterCAxis').show();
                checkedAxis = node.id;
                initAxis(checkedAxis);
            } else if (node.id == 'xAxis2') {
                $('#parameterCAxis').show();
                checkedAxis = node.id;
                initAxis(checkedAxis);
            } else if (node.id == 'yAxis1') {
                $('#parameterCAxis').show();
                checkedAxis = node.id;
                initAxis(checkedAxis);
            } else if (node.id == 'yAxis2') {
                $('#parameterCAxis').show();
                checkedAxis = node.id;
                initAxis(checkedAxis);
            } else if (node.id == 'series') {
                $('#parameterMainSeries').show();
            } else if (node.id == 'polar') {
                $('#parameterPolarRadar').show();
                initPolar();
            } else if (node.id.substr(0, 7) == 'scaleGG') {
                checkedSeries = node.id;
                $('#parameterSeriesScaleGauge').show();
                initSeriesScale(checkedSeries);
            } else if (node.id.substr(0, 7) == 'valueGG') {
                checkedSeries = node.id;
                $('#parameterSeriesValueGauge').show();
                initSeriesValue(checkedSeries);
            } else if (node.id.substr(0, 7) == 'seriesC') {

                checkedSeries = node.id;
                var chartType = $('#chartType').combobox('getValue');
                if (chartType == TYPE_PIE) {
                    $('#parameterSeriesPie').show();
                    initSeriesPie(checkedSeries);
                } else if (chartType == TYPE_RADAR) {
                    $('#parameterSeriesRadar').show();
                    initSeriesRadar(checkedSeries);
                } else {
                    $('#parameterSeries').show();
                    initSeriesRectangular(checkedSeries);
                }

            } else if (node.id.substr(0, 7) == 'seriesS') {
                checkedSeries = node.id;
                $('#parameterSeriesGauge').show();
                initSeriesGague(checkedSeries);
            } else if (node.id.substr(0, 7) == 'pointer') {
                checkedSeries = node.id;
                $('#parameterSeriesPointerGauge').show();
                initSeriesPointer(checkedSeries);
            } else if (node.id.substr(0, 7) == 'gaugePl') {
                checkedSeries = node.id;
                $('#parameterSeriesGaugePanel').show();
                initSeriesPanel(checkedSeries);
            }
        }
    });
}

//TODO 初始化仪表盘的表盘数据
function initSeriesPanel(seriesId) {
    var series = getSeries(seriesId);
    console.info('series:', series);
    var min = series['min'];
    if (min) {
        $('#seriesMinGauge').numberbox('setValue', min);
    } else {
        series['min'] = 0;
        $('#seriesMinGauge').numberbox('setValue', 0);
    }

    var max = series['max'];
    if (max) {
        $('#seriesMaxGauge').numberbox('setValue', max);
    } else {
        series['max'] = 100;
        $('#seriesMaxGauge').numberbox('setValue', 100);
    }

    var startAngle = series['startAngle'];
    if (startAngle) {
        $('#seriesStartAngleGauge').numberbox('setValue', startAngle);
    } else {
        series['startAngle'] = 225;
        $('#seriesStartAngleGauge').numberbox('setValue', 225);
    }

    var endAngle = series['endAngle'];
    if (endAngle) {
        $('#seriesEndAngleGauge').numberbox('setValue', endAngle);
    } else {
        series['endAngle'] = -45;
        $('#seriesEndAngleGauge').numberbox('setValue', -45);
    }

    var splitNumber = series['splitNumber'];
    if (splitNumber) {
        $('#seriesSplitNumberGauge').numberbox('setValue', splitNumber);
    } else {
        series['splitNumber'] = 10;
        $('#seriesSplitNumberGauge').numberbox('setValue', 10);
    }

    var axisLine = series['axisLine'];

    var color;
    if (axisLine) {
        var show = axisLine['show'];
        if (show != false) {
            show = true;
        }
        $('#seriesAxisLineShow').attr('checked', show);
        var lineStyle = axisLine['lineStyle'];
        if (lineStyle) {
            color = lineStyle['color'];

            if (!color) {
                lineStyle['color'] = [
                    [0.2, '#228b22'],
                    [0.8, '#48b'],
                    [1, '#ff4500']
                ];
            }
        } else {
            axisLine['lineStyle'] = {
                color: [
                    [0.2, '#228b22'],
                    [0.8, '#48b'],
                    [1, '#ff4500']
                ]
            };
        }
    } else {
        series['axisLine'] = {
            show: true,
            lineStyle: {
                color: [
                    [0.2, '#228b22'],
                    [0.8, '#48b'],
                    [1, '#ff4500']
                ]
            }
        };
        $('#seriesAxisLineShow').attr('checked', true);
    }

    if (!color) {
        color = [
            [0.2, '#228b22'],
            [0.8, '#48b'],
            [1, '#ff4500']
        ];
    }

    /*生成表盘颜色数据*/
    generateColorPage(color);

    /*修改显示仪表盘的最小值*/
    $('#seriesMinGauge').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var min = series['min'];
            if (newValue != min) {
                series['min'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的最大值*/
    $('#seriesMaxGauge').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var max = series['max'];
            if (newValue != max) {
                series['max'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的开始角度*/
    $('#seriesStartAngleGauge').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var startAngle = series['startAngle'];
            if (newValue != startAngle) {
                series['startAngle'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的结束角度*/
    $('#seriesEndAngleGauge').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var endAngle = series['endAngle'];
            if (newValue != endAngle) {
                series['endAngle'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的段数*/
    $('#seriesMinGauge').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var min = series['min'];
            if (newValue != min) {
                series['min'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的最小值*/
    $('#seriesSplitNumberGauge').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var splitNumber = series['splitNumber'];
            if (newValue != splitNumber) {
                series['splitNumber'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

}

/*是否显示仪表盘点击事件*/
function seriesAxisLineShowClick() {
    var series = getSeries(checkedSeries);

    var isChecked = $('#seriesAxisLineShow').is(':checked');

    series['axisLine']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}


/*生成仪表盘表盘颜色数据设置*/
function generateColorPage(color) {

    /*先删除原来的数据*/
    $('.pageColor').remove();
    var colorHtml = '';
    $.each(color, function (i, n) {
        var colorTitle = '';
        var placeholder = '';
        var btn_ = '';
        if (i == 0) {
            colorTitle = '颜色定义：';
            placeholder = '&nbsp;&nbsp;';
            btn_ = '<img onclick="addColor();" style = "cursor: pointer;"  src="' + contextPath + '/resource/images/dash/add.png" />';
        } else {
            colorTitle = '　　　　　';
            btn_ = '<img onclick="delColor(' + i + ');" style = "cursor: pointer;"  src="' + contextPath + '/resource/images/dash/del.png" />';
        }
        colorHtml += '<div style="width: 100%;height: 28px;line-height: 28px;" class="pageColor">';
        colorHtml += '<span style="width:70">' + colorTitle + '  </span>' + placeholder;
        colorHtml += '<input    style="width: 40" value="' + n[0] + '" class="easyui-numberbox"  onblur="updateColorValue(this, ' + i + ' )" data-options="min:0, max:100, precision:2"/>&nbsp;&nbsp;';
        colorHtml += '<input  style="width: 60px;background : ' + n[1] + ';"   value="' + n[1] + '" name="color_' + i + '" class = "gaugeColor" readonly="readonly" /> &nbsp;&nbsp;';
        colorHtml += btn_;
        colorHtml += '</div>';
    });
    $('#parameterSeriesGaugePanel').append(colorHtml);
    $.parser.parse($('#parameterSeriesGaugePanel'));
    /*可编辑的情况下,设置 ColorPicker*/
    $('.gaugeColor').ColorPicker({
        onSubmit: function (hsb, hex, rgb, el) {
            var strHex = '#' + hex.toUpperCase();
            $(el).val(strHex);
            $(el)[0].style['backgroundColor'] = strHex;
            $(el)[0].style['z-index'] = 1000;
            $(el).ColorPickerHide();
            var name = $(el)[0]['name'];
            var index = name.replace('color_', '');
            updateSeriesAxisLineColor(strHex, index);
        },
        onBeforeShow: function () {
            $(this).ColorPickerSetColor(this.value);
        }
    }).bind('keyup', function () {
        $(this).ColorPickerSetColor(this.value);
    });
}

/*更新仪表盘颜色显示位置*/
function updateColorValue(e, index) {
    var val = $(e).val();
    var series = getSeries(checkedSeries);
    series['axisLine']['lineStyle']['color'][index][0] = val;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);

}

/*更新仪表盘颜色*/
function updateSeriesAxisLineColor(color, index) {
    var series = getSeries(checkedSeries);
    series['axisLine']['lineStyle']['color'][index][1] = color;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*删除颜色输入框*/
function delColor(i) {
    var series = getSeries(checkedSeries);
    var color = series['axisLine']['lineStyle']['color'];
    color.splice(i, 1);
    generateColorPage(color);
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*添加颜色输入框*/
function addColor() {
    var series = getSeries(checkedSeries);
    var color = series['axisLine']['lineStyle']['color'];
    color.push([1, '#FFFFFF']);
    generateColorPage(color);
}

//初始化仪表盘刻度
function initSeriesScale(seriesId) {
    var series = getSeries(seriesId);

    var splitLine = series['splitLine'];
    /*当splitLine不存在时，初始化上默认值*/
    if (!splitLine) {
        series['splitLine'] = {
            show: true,
            length: 30,
            lineStyle: {
                color: '#eee',
                width: 2,
                type: 'solid'
            }
        };
        splitLine = series['splitLine'];
    }

    var axisTick = series['axisTick'];
    /*当splitLine不存在时，初始化上默认值*/
    if (!axisTick) {
        series['axisTick'] = {
            show: true,
            splitNumber: 5,
            length: 8,
            lineStyle: {
                color: '#eee',
                width: 1,
                type: 'solid'
            }
        };
        axisTick = series['axisTick'];
    }

    $('#seriesLineGaugeCheckBox').attr('checked', splitLine['show']);

    var splitNumber = axisTick['splitNumber'];


    $('#seriesAxisTickSplitNumberGauge').numberbox('setValue', splitNumber);

    $('#seriesSplitLineLengthGauge').numberbox('setValue', splitLine['length']);

    $('#seriesAxisTickLengthGauge').numberbox('setValue', axisTick['length']);

    /*修改显示仪表盘的分割线长度*/
    $('#seriesSplitLineLengthGauge').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var splitLine = series['splitLine'];
            var length = splitLine['length'];
            if (newValue != length) {
                splitLine['length'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的分割线长度*/
    $('#seriesAxisTickLengthGauge').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var axisTick = series['axisTick'];
            var length = axisTick['length'];
            if (newValue != length) {
                axisTick['length'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的分割的段数*/
    $('#seriesAxisTickSplitNumberGauge').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var splitNumber = series['axisTick']['splitNumber'];
            if (newValue != splitNumber) {
                series['axisTick']['splitNumber'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });
}

/*是否显示仪表盘刻度点击事件*/
function seriesLineGaugeClick() {
    var series = getSeries(checkedSeries);

    var isChecked = $('#seriesLineGaugeCheckBox').is(':checked');

    series['splitLine']['show'] = isChecked;
    series['axisTick']['show'] = isChecked;

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

// 初始化仪表盘数值数据
function initSeriesValue(seriesId) {
    var series = getSeries(seriesId);
    /*修改显示仪表盘的圆心横坐标位置*/
    $('#seriesDetailOffsetCenter1').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var offsetCenter = series['detail']['offsetCenter'];
            var center1 = offsetCenter[0];

            var centerValue = parseInt(center1.substr(0, center1.length - 1));

            if (newValue != centerValue) {
                offsetCenter[0] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的圆心横坐标位置*/
    $('#seriesDetailOffsetCenter2').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var offsetCenter = series['detail']['offsetCenter'];
            var center2 = offsetCenter[1];

            var centerValue = parseInt(center2.substr(0, center2.length - 1));

            if (newValue != centerValue) {
                offsetCenter[1] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    var detail = series['detail'];

    if (detail) {

        $('#seriesDetailShowGauge').attr('checked', detail['show']);

        var offsetCenter = detail['offsetCenter'];
        if (offsetCenter instanceof Array) {
            setGaugeSeriesDetailCenter(offsetCenter);
        } else {
            detail['offsetCenter'] = ['0%', '40%'];
            $('#seriesDetailOffsetCenter1').numberbox('setValue', 0);
            $('#seriesDetailOffsetCenter2').numberbox('setValue', 40);
        }
    } else {
        series['detail'] = {
            show: true,
            offsetCenter: ['0%', '40%']
        };
        $('#seriesDetailOffsetCenter1').numberbox('setValue', 0);
        $('#seriesDetailOffsetCenter2').numberbox('setValue', 40);
        $('#seriesDetailShowGauge').attr('checked', true);
    }
}


/*根据参数的center，设置显示center*/
function setGaugeSeriesDetailCenter(center) {
    var center1 = center[0];
    var center2 = center[1];

    center1 = center1.substr(0, center1.length - 1);
    center2 = center2.substr(0, center2.length - 1);

    $('#seriesDetailOffsetCenter1').numberbox('setValue', parseInt(center1));
    $('#seriesDetailOffsetCenter2').numberbox('setValue', parseInt(center2));
}

/*初始化点击显示仪表盘数值事件*/
function seriesDetailShowGaugeClick() {
    var series = getSeries(checkedSeries);

    var isChecked = $('#seriesDetailShowGauge').is(':checked');

    series['detail']['show'] = isChecked;

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

// 初始化仪表盘指针数据
function initSeriesPointer(seriesId) {


    /*初始化指针颜色工具箱数据*/
    $('#seriesGaugePointerColorCtrl').combobox({
        onChange: function (newValue, oldValue) {
            seriesGaugePointerColorCtrlChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        valueField: 'value',
        textField: 'label',
        data: POINTER_DATA
    });

    var series = getSeries(seriesId);
    var pointer = series['pointer'];
    if (pointer) {
        seriesPointerSetPage(pointer);
    } else {
        series['pointer'] = {
            length: '80%',
            width: 8,
            color: 'auto'
        };
        seriesPointerSetPage(series['pointer']);

    }

    /*修改显示仪表盘的指针长度*/
    $('#seriesGaugePointerLength').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var pointer = series['pointer'];
            var length = pointer['length'];

            var lengthValue = parseInt(length.substr(0, length.length - 1));

            if (newValue != lengthValue) {
                pointer['length'] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的指针宽度*/
    $('#seriesGaugePointerWidth').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var pointer = series['pointer'];
            var width = pointer['width'];

            if (newValue != width) {
                pointer['width'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

}

/*设置显示仪表盘页面数据*/
function seriesPointerSetPage(pointer) {

    var length = pointer['length'];
    if (length) {
        $('#seriesGaugePointerLength').numberbox('setValue', length.substr(0, length.length - 1));
    }

    var width = pointer['width'];
    if (width) {
        $('#seriesGaugePointerWidth').numberbox('setValue', width);
    }

    var color = pointer['color'];
    if (color) {
        if ('auto' == color) {
            $('#seriesGaugePointerColorCtrl').combobox('setValue', color);
        } else {
            $('#seriesGaugePointerColorCtrl').combobox('setValue', 'custom');
            seriesPointerColorGauge();
            $('#seriesGaugePointerColor').attr('backgroundColor', color);
        }
    }
}

/*设置仪表盘指针颜色选择框*/
function seriesPointerColorGauge() {

    $('#seriesGaugePointerColor').attr('readonly', 'readonly');
    /*可编辑的情况下,设置 ColorPicker*/
    $('#seriesGaugePointerColor').ColorPicker({
        onSubmit: function (hsb, hex, rgb, el) {
            var strHex = '#' + hex.toUpperCase();
            $(el).val(strHex);
            $(el)[0].style['backgroundColor'] = strHex;
            $(el)[0].style['z-index'] = 1000;
            $(el).ColorPickerHide();

            updateSeriesPointerColor($('#seriesGaugePointerColor').val());
        },
        onBeforeShow: function () {
            $(this).ColorPickerSetColor(this.value);
        }
    }).bind('keyup', function () {
        updateSeriesPointerColor($('#seriesGaugePointerColor').val());
        $(this).ColorPickerSetColor(this.value);
    });

}

/*更新图表指针颜色*/
function updateSeriesPointerColor(color) {
    /*把color转化为RGBA*/
    if ('' != color) {
        var series = getSeries(checkedSeries);

        var pointer = series['pointer'];
        pointer['color'] = color;

        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    }
}

/*设置颜色自动或者自定义修改框*/
function seriesGaugePointerColorCtrlChange(newValue, oldValue) {
    if (newValue == 'auto') {
        var series = getSeries(checkedSeries);
        var pointer = series['pointer'];
        pointer['color'] = newValue;
        $('#seriesGaugePointerColor').attr('disabled', 'disabled');
        $('#seriesGaugePointerColor').val('');
        $('#seriesGaugePointerColor').css('background', 'none');

        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    } else {
        $('#seriesGaugePointerColor').removeAttr('disabled');
        seriesPointerColorGauge();
    }
}

/*初始化仪表盘series数据*/
function initSeriesGague(seriesId) {
    var series = getSeries(seriesId);
    /*修改显示仪表盘的圆心横坐标位置*/
    $('#seriesGaugeCenter1').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var center = series['center'];
            var center1 = center[0];

            var centerValue = parseInt(center1.substr(0, center1.length - 1));

            if (newValue != centerValue) {
                center[0] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的圆心横坐标位置*/
    $('#seriesGaugeCenter2').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var center = series['center'];
            var center2 = center[1];

            var centerValue = parseInt(center2.substr(0, center2.length - 1));

            if (newValue != centerValue) {
                center[1] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示仪表盘的半径*/
    $('#seriesGaugeRadius').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var radius = series['radius'];
            var radiusValue = radius[1];
            radiusValue = parseInt(radiusValue.substr(0, radiusValue.length - 1));

            if (newValue != radiusValue) {
                series['radius'] = [0, newValue + '%'];
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示雷达图的圆心横坐标位置*/
    $('#axisLineLineStyleWidth').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var series = getSeries(checkedSeries);
            var width = series['axisLine']['lineStyle']['width'];

            if (newValue != width) {
                series['axisLine']['lineStyle']['width'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });


    /*显示标题*/
    var title = series['title'];
    if (title) {
        $('#seriesGaugeSeriesTitleShow').attr('checked', title['show']);
    } else {
        series['title'] = {show: true};
        $('#seriesGaugeSeriesTitleShow').attr('checked', true);
    }

    /*显示名称*/
    var name = series['name'];
    if (name) {
        $('#seriesGaugeName').val(name);
    }

    /*修改名称事件*/
    $('#seriesGaugeName').blur(function () {
        var name = $('#seriesGaugeName').val();
        var series = getSeries(checkedSeries);
        /*当seriesName发生改变时，重新加载图表*/
        if (name != series['name']) {
            series['name'] = name;
            /* 初始化图表 */
            initChartOptions(TABS_CHART1);
        }
    });

    /*初始化圆心坐标*/
    var center = series['center'];
    if (center instanceof Array) {
        setGaugeSeriesCenter(center);
    } else {
        series['center'] = ['50%', '50%'];
        $('#seriesGaugeCenter1').numberbox('setValue', 50);
        $('#seriesGaugeCenter2').numberbox('setValue', 50);
    }

    /*初始化显示半径值*/
    var radius = series['radius'];
    if (radius) {
        if (radius instanceof Array) {
            var radiusValue = radius[1];
            $('#seriesGaugeRadius').numberbox('setValue', parseInt(radiusValue.substr(0, radiusValue.length - 1)));
        } else {
            series['radius'] = [0, radius];
            var radiusValue = radius.substr(0, radius.length - 1);

            $('#seriesGaugeRadius').numberbox('setValue', parseInt(radiusValue));
        }

    } else {
        series['radius'] = [0, '75%'];
        $('#seriesGaugeRadius').numberbox('setValue', 75);
    }

    /*初始化显示圆环半径值*/
    var axisLine = series['axisLine'];
    if (axisLine) {
        var lineStyle = axisLine['lineStyle'];
        if (lineStyle) {
            var width = lineStyle['width'];
            if (width) {
                $('#axisLineLineStyleWidth').numberbox('setValue', width);
            } else {
                lineStyle['width'] = 30;
                $('#axisLineLineStyleWidth').numberbox('setValue', 30);
            }
        } else {
            axisLine['lineStyle'] = {width: 30};
            $('#axisLineLineStyleWidth').numberbox('setValue', 30);
        }
    } else {
        series['axisLine'] = {lineStyle: {width: 30}};
        $('#axisLineLineStyleWidth').numberbox('setValue', 30);
    }

}


/*根据参数的center，设置显示center*/
function setGaugeSeriesCenter(center) {
    var center1 = center[0];
    var center2 = center[1];

    center1 = center1.substr(0, center1.length - 1);
    center2 = center2.substr(0, center2.length - 1);

    $('#seriesGaugeCenter1').numberbox('setValue', parseInt(center1));
    $('#seriesGaugeCenter2').numberbox('setValue', parseInt(center2));
}

/*是否显示标题*/
function seriesGaugeSeriesTitleShowClick() {
    var series = getSeries(checkedSeries);

    var isChecked = $('#seriesGaugeSeriesTitleShow').is(':checked');

    series['title']['show'] = isChecked;

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*初始化Polar数据*/
function initPolar() {

    /*初始化极点数据*/
    var polar = widgetConfig['polar'];

    $('#polarRadarType').combobox({
        onChange: function (newValue, oldValue) {
            polarRadarTypeChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        editable: false,
        valueField: 'value',
        textField: 'label',
        data: POLARTYPE_DATA
    });


    /*修改显示雷达图的圆心横坐标位置*/
    $('#polarRadarCenter1').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var center = polar[0]['center'];
            var center1 = center[0];

            var centerValue = parseInt(center1.substr(0, center1.length - 1));

            if (newValue != centerValue) {
                center[0] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示雷达图的圆心竖坐标位置*/
    $('#polarRadarCenter2').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var center = polar[0]['center'];
            var center2 = center[1];

            var centerValue = parseInt(center2.substr(0, center2.length - 1));

            if (newValue != centerValue) {
                center[1] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示雷达图的半径长度*/
    $('#polarRadarRadius').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var radius = polar[0]['radius'];
            var radiusValue;
            if (radius) {
                radiusValue = parseInt(radius.substr(0, radius.length - 1));
            } else {
                radiusValue = 75;
            }
            if (newValue != radiusValue) {
                polar[0]['radius'] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示雷达图的开始角度*/
    $('#polarRadarStartAngle').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            var startAngle = polar[0]['startAngle'];

            if (newValue != startAngle) {
                polar[0]['startAngle'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });


    if (polar instanceof Array) {
        /*当数组中没有数据的情况下，初始化一个数据，防止出现语法错误*/
        if (polar.length == 0) {
            polar.push({});
        }
        /*初始化圆心坐标*/
        var center = polar[0]['center'];
        if (center) {
            setRadarPolarCenter(center);
        } else {
            polar[0]['center'] = ['50%', '50%'];
            $('#polarRadarCenter1').numberbox('setValue', 50);
            $('#polarRadarCenter2').numberbox('setValue', 50);
        }

        /*初始化半径数据*/
        var radius = polar[0]['radius'];

        if (radius) {
            radius = radius.substr(0, radius.length - 1);
            $('#polarRadarRadius').numberbox('setValue', parseInt(radius));
        } else {
            $('#polarRadarRadius').numberbox('clear');
        }

        /*初始化开始角度*/
        var startAngle = polar[0]['startAngle'];

        if (startAngle) {
            $('#polarRadarStartAngle').numberbox('setValue', startAngle);
        } else {
            $('#polarRadarStartAngle').numberbox('clear');
        }

        /*初始化极点形状*/
        var type = polar[0]['type'];

        if (type) {
            $('#polarRadarType').combobox('setValue', type);
        } else {
            $('#polarRadarType').combobox('clear');
        }

        /*初始化极点形状*/
        var name = polar[0]['name'];
        if (name) {
            var show = name['show'];
            $('#polarNameShowRadar').attr("checked", show);
        } else {
            $('#polarNameShowRadar').attr("checked", true);
        }

    }

}

function polarNameShowRadarClick() {

    var polar = widgetConfig['polar'];
    var name = polar[0]['name'];

    var isChecked = $('#polarNameShowRadar').is(':checked');

    if (isChecked) {
        name['show'] = true;
    } else {
        if (name) {
            name['show'] = false;
        } else {
            polar[0]['name'] = {show: false};
        }
    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

function polarRadarTypeChange(newValue, oldValue) {
    /*初始化极点数据*/
    var polar = widgetConfig['polar'];

    if (polar) {
        var type = polar[0]['type'];
        if (type != newValue) {
            polar[0]['type'] = newValue;
        }
    } else {
        widgetConfig['polar'] = [{type: newValue}];
    }
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*根据参数的center，设置显示center*/
function setRadarPolarCenter(center) {
    var center1 = center[0];
    var center2 = center[1];

    center1 = center1.substr(0, center1.length - 1);
    center2 = center2.substr(0, center2.length - 1);

    $('#polarRadarCenter1').numberbox('setValue', parseInt(center1));
    $('#polarRadarCenter2').numberbox('setValue', parseInt(center2));
}

// 初始化RADAR数据
function initSeriesRadar(seriesId) {
    $('#seriesRadarSymbol').combobox({
        onChange: function (newValue, oldValue) {
            seriesRadarSymbolChange(newValue, oldValue);
        },
        panelHeight: 100,
        editable: false,
        valueField: 'value',
        textField: 'label',
        data: SYMBOL_DATA
    });


    var series = getSeries(seriesId);

    /*修改自定义极点大小*/
    $('#seriesRadarSymbolSize').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            /*获得当前选中的series*/
            var series = getSeries(checkedSeries);
            var symbolSize = series['symbolSize'];

            if (newValue != symbolSize) {
                series['symbolSize'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    var symbol = series['symbol'];
    if (symbol) {
        $('#seriesRadarCheckBox').attr("checked", true);
        $('#seriesRadarSymbol').combobox('enable');
        $('#seriesRadarSymbolSize').numberbox('enable');
        $('#seriesRadarSymbol').combobox('setValue', symbol);
        /*因为只要存在symbol就一定存在symbolSize，所以可以直接设置*/
        $('#seriesRadarSymbolSize').numberbox('setValue', series['symbolSize']);
    } else {
        $('#seriesRadarCheckBox').attr("checked", false);
        $('#seriesRadarSymbol').combobox('clear');
        $('#seriesRadarSymbolSize').numberbox('clear');
        $('#seriesRadarSymbol').combobox('disable');
        $('#seriesRadarSymbolSize').numberbox('disable');
    }

}

function seriesRadarSymbolChange(newValue, oldValue) {
    var series = getSeries(checkedSeries);
    var symbol = series['symbol'];

    if (newValue != symbol) {
        series['symbol'] = newValue;
        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    }
}

function seriesRadarSymbolClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);

    var isChecked = $('#seriesRadarCheckBox').is(':checked');

    if (isChecked) {
        $('#seriesRadarSymbol').combobox('enable');
        $('#seriesRadarSymbolSize').numberbox('enable');
        series['symbol'] = 'emptyCircle';
        series['symbolSize'] = 3;

        $('#seriesRadarSymbol').combobox('setValue', 'emptyCircle');
        $('#seriesRadarSymbolSize').numberbox('setValue', 3);

    } else {
        delete series['symbol'];
        delete series['symbolSize'];
        $('#seriesRadarSymbol').combobox('clear');
        $('#seriesRadarSymbolSize').numberbox('clear');
        $('#seriesRadarSymbol').combobox('disable');
        $('#seriesRadarSymbolSize').numberbox('disable');
    }
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

//初始化PIE数据
function initSeriesPie(seriesId) {

    /*初始化雷丁格尔玫瑰下拉选项*/
    $('#seriesPieRoseTypeCombo').combobox({
        onChange: function (newValue, oldValue) {
            seriesPieRoseTypeChange(newValue, oldValue);
        },
        panelHeight: 'auto',
        editable: false,
        valueField: 'value',
        textField: 'label',
        data: ROSETYPE_DATA
    });

    var series = getSeries(seriesId);

    /*修改显示圆环内径的大小*/
    $('#seriesPieRadius1').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            /*获得当前选中的series*/
            var series = getSeries(checkedSeries);
            var radius = series['radius'];
            var radius1 = radius[0];

            var radiusValue = parseInt(radius1.substr(0, radius1.length - 1));

            if (newValue != radiusValue) {
                radius[0] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示圆环内径的大小*/
    $('#seriesPieRadius2').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            /*获得当前选中的series*/
            var series = getSeries(checkedSeries);
            var radius = series['radius'];
            var radius2 = radius[1];

            var radiusValue = parseInt(radius2.substr(0, radius2.length - 1));

            if (newValue != radiusValue) {
                radius[1] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示饼图的圆心位置*/
    $('#seriesPieCenter1').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            /*获得当前选中的series*/
            var series = getSeries(checkedSeries);
            var center = series['center'];
            var center1 = center[0];

            var centerValue = parseInt(center1.substr(0, center1.length - 1));

            if (newValue != centerValue) {
                center[0] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*修改显示饼图的圆心位置*/
    $('#seriesPieCenter2').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            /*获得当前选中的series*/
            var series = getSeries(checkedSeries);
            var center = series['center'];
            var center2 = center[1];

            var centerValue = parseInt(center2.substr(0, center2.length - 1));

            if (newValue != centerValue) {
                center[1] = newValue + '%';
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*初始化修改seriesName事件*/
    $('#seriesNamePie').blur(function () {
        var name = $('#seriesNamePie').val();
        /*获得当前选中的series*/
        var series = getSeries(checkedSeries);
        /*当seriesName发生改变时，重新加载图表*/
        if (name != series['name']) {
            series['name'] = name;
            /* 初始化图表 */
            initChartOptions(TABS_CHART1);
        }
    });

    /*判断是否存在series*/
    if (series) {

        /*显示seriesName*/
        var seriesName = series['name'];
        if (seriesName) {
            $('#seriesNamePie').val(seriesName);
        } else {
            $('#seriesNamePie').val('');
        }

        /*回显标签和连接线*/
        var itemStyle = series['itemStyle'];
        /*因为itemStyle默认是显示标签和连接线，所以当不存在itemStyle的时候 选中该标签，当不显示的时候设置为false*/
        if (!itemStyle || itemStyle == '' || itemStyle == 'undefined') {
            $('#seriesPieItemStyle').attr("checked", true);
        } else {
            $('#seriesPieItemStyle').attr("checked", false);
        }

        /*回显是否显示圆环*/
        var radius = series['radius'];
        if (radius instanceof Array) {
            $('#seriesPieRadius').attr("checked", true);
            $('#seriesPieRadius1').numberbox('enable');
            $('#seriesPieRadius2').numberbox('enable');
            setSeriesPieRadius(radius);
        } else {
            $('#seriesPieRadius').attr("checked", false);
            $('#seriesPieRadius1').numberbox('clear');
            $('#seriesPieRadius2').numberbox('clear');
            $('#seriesPieRadius1').numberbox('disable');
            $('#seriesPieRadius2').numberbox('disable');
        }

        var center = series['center'];
        if (center) {
            setSeriesPieCenter(center);
        } else {
            $('#seriesPieCenter1').numberbox('clear');
            $('#seriesPieCenter2').numberbox('clear');
        }

        var roseType = series['roseType'];
        if (roseType) {
            $('#seriesPieRoseType').attr("checked", true);
            $('#seriesPieRoseTypeCombo').combobox('setValue', roseType);
        } else {
            $('#seriesPieRoseType').attr("checked", false);
            $('#seriesPieRoseTypeCombo').combobox('clear');
            $('#seriesPieRoseTypeCombo').combobox('disable');
        }
    }
}

/*根据参数的center，设置显示center*/
function setSeriesPieCenter(center) {
    var center1 = center[0];
    var center2 = center[1];

    center1 = center1.substr(0, center1.length - 1);
    center2 = center2.substr(0, center2.length - 1);

    $('#seriesPieCenter1').numberbox('setValue', parseInt(center1));
    $('#seriesPieCenter2').numberbox('setValue', parseInt(center2));
}

function seriesPieRoseTypeChange(newValue, oldValue) {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);
    var roseType = series['roseType'];
    if (roseType != newValue) {
        series['roseType'] = newValue;
        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    }
}

/*点击标注点最小值事件*/
function seriesPieItemStyleClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);

    var isChecked = $('#seriesPieItemStyle').is(':checked');

    if (isChecked) {
        delete series['itemStyle'];
    } else {
        series['itemStyle'] = {
            normal: {
                label: {
                    show: false
                },
                labelLine: {
                    show: false
                }
            }
        };
    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*点击标注点最小值事件*/
function seriesPieRadiusClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);

    var isChecked = $('#seriesPieRadius').is(':checked');

    if (isChecked) {
        series['radius'] = ['20%', '50%'];
        $('#seriesPieRadius1').numberbox('enable');
        $('#seriesPieRadius2').numberbox('enable');
        setSeriesPieRadius(series['radius']);
    } else {
        series['radius'] = '50%';
        $('#seriesPieRadius1').numberbox('clear');
        $('#seriesPieRadius2').numberbox('clear');
        $('#seriesPieRadius1').numberbox('disable');
        $('#seriesPieRadius2').numberbox('disable');
    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*根据参数的radius，设置显示radius*/
function setSeriesPieRadius(radius) {
    var radius1 = radius[0];
    var radius2 = radius[1];

    radius1 = radius1.substr(0, radius1.length - 1);
    radius2 = radius2.substr(0, radius2.length - 1);

    $('#seriesPieRadius1').numberbox('setValue', parseInt(radius1));
    $('#seriesPieRadius2').numberbox('setValue', parseInt(radius2));
}


/*点击标注点最小值事件*/
function seriesPieRoseTypeClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);

    var isChecked = $('#seriesPieRoseType').is(':checked');

    if (isChecked) {
        $('#seriesPieRoseTypeCombo').combobox('enable');
        $('#seriesPieRoseTypeCombo').combobox('setValue', 'area');
        series['roseType'] = 'area';
    } else {
        delete series['roseType'];
        $('#seriesPieRoseTypeCombo').combobox('clear');
        $('#seriesPieRoseTypeCombo').combobox('disable');
    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}


function initSeriesRectangular(seriesId) {
    /*初始化标题x坐标位置下拉数据*/
    $('#seriesMarkPointMaxSymbol').combobox({
        onChange: function (newValue, oldValue) {
            seriesMarkPointMaxSymbolChange(newValue, oldValue);
        },
        panelHeight: 100,
        editable: false,
        valueField: 'value',
        textField: 'label',
        data: SYMBOL_DATA
    });

    /*初始化标题x坐标位置下拉数据*/
    $('#seriesMarkPointMinSymbol').combobox({
        onChange: function (newValue, oldValue) {
            seriesMarkPointMinSymbolChange(newValue, oldValue);
        },
        panelHeight: 100,
        editable: false,
        valueField: 'value',
        textField: 'label',
        data: SYMBOL_DATA
    });

    /*初始化标题x坐标位置下拉数据*/
    $('#seriesMarkLineStart').combobox({
        onChange: function (newValue, oldValue) {
            seriesMarkLineStartChange(newValue, oldValue);
        },
        panelHeight: 100,
        editable: false,
        valueField: 'value',
        textField: 'label',
        data: SYMBOL_DATA
    });

    /*初始化标题x坐标位置下拉数据*/
    $('#seriesMarkLineEnd').combobox({
        onChange: function (newValue, oldValue) {
            seriesMarkLineEndChange(newValue, oldValue);
        },
        panelHeight: 100,
        editable: false,
        valueField: 'value',
        textField: 'label',
        data: SYMBOL_DATA
    });


    var series = getSeries(seriesId);

    /*因为maxName能编辑的时候，说明最大标注点是存在的，所以可以直接修改其值*/
    $('#seriesMarkPointMaxName').blur(function () {
        var data = series['markPoint']['data'];
        var flag = -1;
        $.each(data, function (i, n) {
            if (n['type'] == 'max') {
                flag = i;
            }
        });

        var maxName = $('#seriesMarkPointMaxName').val();
        if (!maxName || maxName == '') {
            $.messager.alert('系统提示', '名字不能为空。', 'info');
            $('#seriesMarkPointMaxName').val('最大值');
            data[flag]['name'] = '最大值';
            /* 初始化图表 */
            initChartOptions(TABS_CHART1);
        } else {
            if (maxName != data[flag]['name']) {
                data[flag]['name'] = maxName;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });


    /*因为minName能编辑的时候，说明最小标注点是存在的，所以可以直接修改其值*/
    $('#seriesMarkPointMinName').blur(function () {
        /*获得当前选中的series*/
        var series = getSeries(checkedSeries);
        var data = series['markPoint']['data'];
        var flag = -1;
        $.each(data, function (i, n) {
            if (n['type'] == 'min') {
                flag = i;
            }
        });

        var minName = $('#seriesMarkPointMinName').val();
        if (!minName || minName == '') {
            $.messager.alert('系统提示', '名字不能为空。', 'info');
            $('#seriesMarkPointMinName').val('最小值');
            data[flag]['name'] = '最小值';
            /* 初始化图表 */
            initChartOptions(TABS_CHART1);
        } else {
            if (minName != data[flag]['name']) {
                data[flag]['name'] = minName;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*因为SymbolSize能编辑的时候，说明最大标注点是存在的，所以可以直接修改其值*/
    $('#seriesMarkPointMaxSymbolSize').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            /*获得当前选中的series*/
            var series = getSeries(checkedSeries);
            var data = series['markPoint']['data'];
            var flag = -1;
            $.each(data, function (i, n) {
                if (n['type'] == 'max') {
                    flag = i;
                }
            });

            if (newValue != data[flag]['symbolSize']) {
                data[flag]['symbolSize'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*因为SymbolSize能编辑的时候，说明最小标注点是存在的，所以可以直接修改其值*/
    $('#seriesMarkPointMinSymbolSize').numberbox({
        onChange: function (newValue, oldValue) {
            newValue = parseInt(newValue);
            /*获得当前选中的series*/
            var series = getSeries(checkedSeries);
            var data = series['markPoint']['data'];
            var flag = -1;
            $.each(data, function (i, n) {
                if (n['type'] == 'min') {
                    flag = i;
                }
            });

            if (newValue != data[flag]['symbolSize']) {
                data[flag]['symbolSize'] = newValue;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        }
    });

    /*判断是否存在series*/
    if (series) {

        /*显示seriesName*/
        var seriesName = series['name'];
        if (seriesName) {
            $('#seriesName').val(seriesName);
        } else {
            $('#seriesName').val('');
        }

        /*初始化修改seriesName事件*/
        $('#seriesName').blur(function () {
            var name = $('#seriesName').val();
            /*获得当前选中的series*/
            var series = getSeries(checkedSeries);
            /*当seriesName发生改变时，重新加载图表*/
            if (name != series['name']) {
                series['name'] = name;
                /* 初始化图表 */
                initChartOptions(TABS_CHART1);
            }
        });

        /*获得标注点数据，判断标注点数据项显示*/
        var markPoint = series['markPoint'];
        if (markPoint) {
            $('#seriesMarkPointShow').attr("checked", true);
            enableMarkPoint();
            /*是否存在标注点*/
            var data = markPoint['data'];
            if (data) {
                /*循环取出标注点数据*/
                $.each(data, function (i, n) {
                    var type = n['type'];

                    /*当标注类型为max时，初始化max数据*/
                    if (type && type == 'max') {
                        $('#seriesMarkPointMaxShow').attr("checked", true);
                        enableMarkPointMax();
                        var name = n['name'];
                        if (name) {
                            $('#seriesMarkPointMaxName').val(name);
                        }

                        var symbol = n['symbol'];
                        if (symbol) {
                            $('#seriesMarkPointMaxSymbol').combobox('setValue', symbol);
                        }

                        var symbolSize = n['symbolSize'];
                        if (symbolSize) {
                            $('#seriesMarkPointMaxSymbolSize').numberbox('setValue', parseInt(symbolSize));
                        }
                    }

                    /*当标注类型为min时，初始化min数据*/
                    if (type && type == 'min') {
                        $('#seriesMarkPointMinShow').attr("checked", true);
                        enableMarkPointMin();
                        var name = n['name'];
                        if (name) {
                            $('#seriesMarkPointMinName').val(name);
                        }

                        var symbol = n['symbol'];
                        if (symbol) {
                            $('#seriesMarkPointMinSymbol').combobox('setValue', symbol);
                        }

                        var symbolSize = n['symbolSize'];
                        if (symbolSize) {
                            $('#seriesMarkPointMinSymbolSize').numberbox('setValue', parseInt(symbolSize));
                        }
                    }
                });
            }
        } else {
            $('#seriesMarkPointShow').attr("checked", false);
            disableMarkPoint();
        }

        /*获得标注点数据，判断标注点数据项显示*/
        var markLine = series['markLine'];
        if (markLine) {
            $('#seriesMarkLineShow').attr("checked", true);
            enableMarkLine();
            /*是否存在标注点*/
            var data = markLine['data'];
            if (data) {
                /*循环取出标注点数据*/
                $.each(data, function (i, n) {
                    var type = n['type'];

                    /*当标注类型为max时，初始化max数据*/
                    if (type && type == 'max') {
                        $('#seriesMarkLineMax').attr("checked", true);
                    }

                    /*当标注类型为min时，初始化min数据*/
                    if (type && type == 'min') {
                        $('#seriesMarkLineMin').attr("checked", true);
                    }

                    /*当标注类型为average时，初始化average数据*/
                    if (type && type == 'average') {
                        $('#seriesMarkLineAvg').attr("checked", true);
                    }

                });
            }

            var symbol = markLine['symbol'];
            if (symbol) {
                /*循环取出标线的形状， 起点为第1个，终点为第2个*/
                $.each(symbol, function (i, n) {
                    if (i == 1) {
                        $('#seriesMarkLineStart').combobox('setValue', symbol);
                    }
                    if (i == 2) {
                        $('#seriesMarkLineEnd').combobox('setValue', symbol);
                    }
                });
            }
        } else {
            $('#seriesMarkLineShow').attr("checked", false);
            disableMarkLine();
        }


    }
}

/*根据seriesId获得当前选中的Series*/
function getSeries(seriesId) {
    var no = seriesId.substr(7, seriesId.length - 7);
    return widgetConfig['series'][parseInt(no) - 1];
}


/*根据axis的ID确定是初始化哪个axis*/
function initAxis(axisId) {
    var axis = getAxis(axisId);

    if (axis) {
        /*是否显示坐标轴*/
        var show = axis['show'];
        if (show) {
            $('#axisShow').attr("checked", true);
        } else {
            $('#axisShow').attr("checked", false);
        }

        /*是否显示坐标标题*/
        var name = axis['name'];
        if (name) {
            $('#axisNameShow').attr("checked", true);
            $('#axisName').removeAttr('disabled');
            $('#axisName').val(name);
        } else {
            $('#axisNameShow').attr("checked", false);
            $('#axisName').val('');
            $('#axisName').attr('disabled', 'disabled');
        }

        /*修改标题事件*/
        $('#axisName').blur(function () {
            var axis = getAxis(checkedAxis);

            var isChecked = $('#axisNameShow').is(':checked');

            /*如果选中，就显示标题，并且可以修改标题内容*/
            if (isChecked) {
                axis['name'] = $('#axisName').val();
            }
            /* 初始化图表 */
            initChartOptions(TABS_CHART1);
        });

        /*是否显示坐标线*/
        var axisLine = axis['axisLine'];
        if (axisLine && axisLine['show']) {
            $('#axisLineShow').attr("checked", true);
        } else {
            $('#axisLineShow').attr("checked", false);
        }

        /*是否显示刻度*/
        var axisTick = axis['axisTick'];
        if (axisTick && axisTick['show']) {
            $('#axisTickShow').attr("checked", true);
        } else {
            $('#axisTickShow').attr("checked", false);
        }

        /*是否显示标签*/
        var axisLabel = axis['axisLabel'];
        if (axisLabel && axisLabel['show']) {
            $('#axisLabelShow').attr("checked", true);
        } else {
            $('#axisLabelShow').attr("checked", false);
        }

        /*标签是否旋转*/
        if (axisLabel && axisLabel['rotate']) {
            $('#axisLabelRotate').numberbox("setValue", axisLabel['rotate']);
        } else {
            $('#axisLabelRotate').numberbox("setValue", 0);
        }


        /*修改标签旋转角度*/
        $('#axisLabelRotate').blur(function () {
            var axis = getAxis(checkedAxis);

            /*如果选中，就显示标题，并且可以修改标题内容*/
            if (axis['axisLabel']) {
                axis['axisLabel']['rotate'] = parseInt($('#axisLabelRotate').numberbox('getValue'));
            }
            /* 初始化图表 */
            initChartOptions(TABS_CHART1);
        });

    }
}

/* tree加载成功后触发 */
function loadSuccess(node, data) {

    var roots = $('#tableTree').tree('getRoots');
    $('#tableTree').tree('select', roots[0].target);

    /* 移除图标 */
    cancelIcon(roots);
}

/* js循环去掉tree文件夹图标 */
function cancelIcon(roots) {

    /* 移除图标 */
    for (var i = 0; i < roots.length; i++) {
        var icons = $(roots[i].target).find("span.tree-icon");
        $(icons).removeClass("tree-folder  tree-file");

        /* 判断父节点下面的子节点是否还存在 */
        var childs = $('#tableTree').tree('getChildren', roots[i].target);
        if (childs.length > 0) {
            cancelIcon(childs);
        }
    }
}

function bgColorClick(el) {

    /*选中Value Series 2可以编辑，否则不可编辑*/
    if ($(el).prop("checked")) {
        $('#bgColor').removeAttr('disabled');
        $('#bgColor').attr('readonly', 'readonly');
        $('#bgAlpha').numberbox('enable');
        changeBgAlpha();
        /*可编辑的情况下,设置 ColorPicker*/
        $('#bgColor').ColorPicker({
            onSubmit: function (hsb, hex, rgb, el) {
                var strHex = '#' + hex.toUpperCase();
                $(el).val(strHex);
                $(el)[0].style['backgroundColor'] = strHex;
                $(el)[0].style['z-index'] = 1000;
                $(el).ColorPickerHide();

                uptBackgroundColor($('#bgColor').val());
            },
            onBeforeShow: function () {
                $(this).ColorPickerSetColor(this.value);
            }
        }).bind('keyup', function () {
            uptBackgroundColor($('#bgColor').val());
            $(this).ColorPickerSetColor(this.value);
        });
    } else {
        $('#bgColor').attr('disabled', 'disabled');
        $('#bgAlpha').numberbox('disable', true);
        uptBackgroundColor('');
        $('#bgColor').val('');
        $('#bgAlpha').numberbox('setValue', 0);
        $('#bgColor').css('background', 'none');
    }
}

/*更新图表背景颜色*/
function uptBackgroundColor(color) {
    /*把color转化为RGBA*/
    if ('' != color) {
        color = HEX2RGBA(color, $('#bgAlpha').numberbox('getValue'));
    }
    widgetConfig['backgroundColor'] = color;

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*修改背景颜色的透明度*/
function changeBgAlpha() {
    $('#bgAlpha').numberbox({
        onChange: function (newValue, oldValue) {
            var color = widgetConfig['backgroundColor'];

            widgetConfig['backgroundColor'] = changeRGBA(color, newValue);
            /* 初始化图表 */
            initChartOptions(TABS_CHART1);
        }
    });
}

/*是否开启动画效果*/
function clickAnimation() {
    var isChecked = $('#animation').is(':checked');
    widgetConfig['animation'] = isChecked;

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否鼠标悬停提示*/
function clickTooltipShow() {
    var isChecked = $('#tooltipShow').is(':checked');

    widgetConfig['tooltip']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示标题 */
function titleClick() {
    var isChecked = $('#titleShow').is(':checked');

    widgetConfig['title']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/* 标题相对于x轴的位置 */
function titleXChange(newValue, oldValue) {
    widgetConfig['title']['x'] = newValue;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/* 标题相对于y轴的位置*/
function titleYChange(newValue, oldValue) {
    widgetConfig['title']['y'] = newValue;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/* 标题水平对齐方式*/
function titleTextAlignChange(newValue, oldValue) {
    widgetConfig['title']['textAlign'] = newValue;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/* 设置padding */
function titlePaddingSet() {
    var titlePaddingTop = $('#titlePaddingTop').numberbox('getValue');
    var top = 0;
    if (titlePaddingTop) {
        top = titlePaddingTop;
    }

    var titlePaddingRight = $('#titlePaddingRight').numberbox('getValue');
    var right = 0;
    if (titlePaddingRight) {
        right = titlePaddingRight;
    }


    var titlePaddingBottom = $('#titlePaddingBottom').numberbox('getValue');
    var bottom = 0;
    if (titlePaddingBottom) {
        bottom = titlePaddingBottom;
    }

    var titlePaddingLeft = $('#titlePaddingLeft').numberbox('getValue');
    var left = 0;
    if (titlePaddingLeft) {
        left = titlePaddingLeft;
    }

    if ((top + right + bottom + left) == 0) {
        if (widgetConfig['title']['padding']) {
            delete widgetConfig['title']['padding'];
        }
    } else {
        widgetConfig['title']['padding'] = [top, right, bottom, left];
    }


    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示工具栏*/
function toolboxClick() {
    var isChecked = $('#toolboxShow').is(':checked');

    widgetConfig['toolbox']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}


/*是否显示工具栏的辅助线*/
function toolboxMarkClick() {
    var isChecked = $('#toolboxMarkShow').is(':checked');

    widgetConfig['toolbox']['feature']['mark']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示工具栏缩放*/
function toolboxDataZoomClick() {
    var isChecked = $('#toolboxDataZoomShow').is(':checked');

    widgetConfig['toolbox']['feature']['dataZoom']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示工具栏查看数据*/
function toolboxDataViewClick() {
    var isChecked = $('#toolboxDataViewShow').is(':checked');

    widgetConfig['toolbox']['feature']['dataView']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示工具栏类型切换*/
function toolboxMagicTypeClick() {
    var isChecked = $('#toolboxMagicTypeShow').is(':checked');

    widgetConfig['toolbox']['feature']['magicType']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示工具栏还原*/
function toolboxRestoreClick() {
    var isChecked = $('#toolboxRestoreShow').is(':checked');

    widgetConfig['toolbox']['feature']['restore']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示工具栏另存为*/
function toolboxSaveAsImageClick() {
    var isChecked = $('#toolboxSaveAsImageShow').is(':checked');

    widgetConfig['toolbox']['feature']['saveAsImage']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/* 工具栏相对于x轴的位置 */
function toolboxXChange(newValue, oldValue) {
    widgetConfig['toolbox']['x'] = newValue;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/* 工具栏相对于y轴的位置*/
function toolboxYChange(newValue, oldValue) {
    widgetConfig['toolbox']['y'] = newValue;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示图例*/
function legendClick() {
    var isChecked = $('#legendShow').is(':checked');

    widgetConfig['legend']['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/* 图例排列方式 */
function legendOrientChange(newValue, oldValue) {
    widgetConfig['legend']['orient'] = newValue;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/* 图例相对于x轴的位置 */
function legendXChange(newValue, oldValue) {
    widgetConfig['legend']['x'] = newValue;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/* 图例相对于y轴的位置*/
function legendYChange(newValue, oldValue) {
    widgetConfig['legend']['y'] = newValue;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}


function legendBgColor() {

    $('#legendBackgroundColor').attr('readonly', 'readonly');
    /*可编辑的情况下,设置 ColorPicker*/
    $('#legendBackgroundColor').ColorPicker({
        onSubmit: function (hsb, hex, rgb, el) {
            var strHex = '#' + hex.toUpperCase();
            $(el).val(strHex);
            $(el)[0].style['backgroundColor'] = strHex;
            $(el)[0].style['z-index'] = 1000;
            $(el).ColorPickerHide();

            updateLegendBgColor($('#legendBackgroundColor').val());
        },
        onBeforeShow: function () {
            $(this).ColorPickerSetColor(this.value);
        }
    }).bind('keyup', function () {
        updateLegendBgColor($('#legendBackgroundColor').val());
        $(this).ColorPickerSetColor(this.value);
    });

}

/*更新图表背景颜色*/
function updateLegendBgColor(color) {
    /*把color转化为RGBA*/
    if ('' != color) {
        color = HEX2RGBA(color, $('#legendBackgroundAlpha').numberbox('getValue'));
    }
    widgetConfig['legend']['backgroundColor'] = color;

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*修改背景颜色的透明度*/
function changeLegendBgAlpha() {
    $('#legendBackgroundAlpha').numberbox({
        onChange: function (newValue, oldValue) {
            var color = widgetConfig['legend']['backgroundColor'];
            if (color) {
                widgetConfig['legend']['backgroundColor'] = changeRGBA(color, newValue);
            }

            /* 初始化图表 */
            initChartOptions(TABS_CHART1);
        }
    });
}

function legendBorderColor() {

    $('#legendBorderColor').attr('readonly', 'readonly');
    /*可编辑的情况下,设置 ColorPicker*/
    $('#legendBorderColor').ColorPicker({
        onSubmit: function (hsb, hex, rgb, el) {
            var strHex = '#' + hex.toUpperCase();
            $(el).val(strHex);
            $(el)[0].style['backgroundColor'] = strHex;
            $(el)[0].style['z-index'] = 1000;
            $(el).ColorPickerHide();

            updateLegendBorderColor($('#legendBorderColor').val());
        },
        onBeforeShow: function () {
            $(this).ColorPickerSetColor(this.value);
        }
    }).bind('keyup', function () {
        updateLegendBorderColor($('#legendBorderColor').val());
        $(this).ColorPickerSetColor(this.value);
    });

}

/*更新图表背景颜色*/
function updateLegendBorderColor(color) {
    widgetConfig['legend']['borderColor'] = color;

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*根据axisId获得当前选中的axis*/
function getAxis(id) {
    var axis = id.substr(0, 5);
    var no = id.substr(5, id.length - 5);
    return widgetConfig[axis][parseInt(no) - 1];
}

/*是否显示坐标轴*/
function axisClick() {
    var axis = getAxis(checkedAxis);

    var isChecked = $('#axisShow').is(':checked');

    axis['show'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示标题*/
function axisNameClick() {
    var axis = getAxis(checkedAxis);

    var isChecked = $('#axisNameShow').is(':checked');

    /*如果选中，就显示标题，并且可以修改标题内容*/
    if (isChecked) {
        axis['name'] = $('#axisName').val();
        $('#axisName').removeAttr('disabled');
    } else {
        $('#axisName').val('');
        $('#axisName').attr('disabled', 'disabled');
        delete axis['name'];
    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示坐标轴线*/
function axisLineClick() {
    var axis = getAxis(checkedAxis);

    var isChecked = $('#axisLineShow').is(':checked');

    var axisLine = axis['axisLine'];
    if (axisLine) {
        axisLine['show'] = isChecked;
    }
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}


/*是否显示坐标轴刻度线*/
function axisTickClick() {
    var axis = getAxis(checkedAxis);

    var isChecked = $('#axisTickShow').is(':checked');

    var axisTick = axis['axisTick'];
    if (axisTick) {
        axisTick['show'] = isChecked;
    }
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*是否显示坐标轴标签*/
function axisLabelClick() {
    var axis = getAxis(checkedAxis);

    var isChecked = $('#axisLabelShow').is(':checked');

    var axisLabel = axis['axisLabel'];
    if (axisLabel) {
        axisLabel['show'] = isChecked;
    }
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*series标注点最大值标注形状发生改变事件*/
function seriesMarkPointMaxSymbolChange(newValue, oldValue) {

    if (newValue && newValue != '') {

        /*初始化一个最大标注点*/
        var markPointMax = {
            name: $('#seriesMarkPointMaxName').val(),
            symbol: newValue,
            symbolSize: $('#seriesMarkPointMaxSymbolSize').numberbox('getValue'),
            type: 'max'
        };


        /*获得当前选中的series*/
        var series = getSeries(checkedSeries);

        var markPoint = series['markPoint'];
        if (markPoint) {
            /*是否存在标注点*/
            var data = markPoint['data'];
            if (data) {
                var flag = -1;
                /*循环取出标注点数据*/
                $.each(data, function (i, n) {
                    var type = n['type'];

                    /*当标注类型为max时，初始化max数据*/
                    if (type && type == 'max') {
                        flag = i;
                    }
                });

                /*当flag等于-1的时候就说明没有标注最大值*/
                if (flag == -1) {
                    data.push(markPointMax);
                } else {
                    data[flag]['symbol'] = newValue;
                }
            }
        } else {

            var data = [markPointMax];
            series['markPoint'] = data;
        }

        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    }
}


/*series标注点最小值标注形状发生改变事件*/
function seriesMarkPointMinSymbolChange(newValue, oldValue) {
    if (newValue && newValue != '') {

        /*初始化一个最大标注点*/
        var markPointMin = {
            name: $('#seriesMarkPointMinName').val(),
            symbol: newValue,
            symbolSize: $('#seriesMarkPointMinSymbolSize').numberbox('getValue'),
            type: "min"
        };


        /*获得当前选中的series*/
        var series = getSeries(checkedSeries);

        var markPoint = series['markPoint'];
        if (markPoint) {
            /*是否存在标注点*/
            var data = markPoint['data'];
            if (data) {
                var flag = -1;
                /*循环取出标注点数据*/
                $.each(data, function (i, n) {
                    var type = n['type'];

                    /*当标注类型为Min时，初始化Min数据*/
                    if (type && type == 'min') {
                        flag = i;
                    }
                });

                /*当flag等于-1的时候就说明没有标注最大值*/
                if (flag == -1) {
                    data.push(markPointMin);
                } else {
                    data[flag]['symbol'] = newValue;
                }
            }
        } else {

            var data = [markPointMin];
            series['markPoint'] = data;
        }

        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    }
}

/*显示标注点事件*/
function seriesMarkPointClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);

    var isChecked = $('#seriesMarkPointShow').is(':checked');
    if (isChecked) {
        enableMarkPoint();
    } else {
        disableMarkPoint();
        delete series['markPoint'];
    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*点击标注点最大值事件*/
function seriesMarkPointMaxClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);
    var markPoint = series['markPoint'];

    /*初始化一个变量，用于存放最大值标注点在数组中的位置*/
    var flag = -1;

    /*初始化一个bool值的变量，用于判断是否存在数据*/
    var haveData = false;

    var data = [];
    /* 查找标注点最大值，在数组中的位置 */
    if (markPoint) {
        data = markPoint['data'];
        /*判断是否存在数组*/
        if (data) {
            /*循环取出标注点数据*/
            $.each(data, function (i, n) {
                var type = n['type'];
                /*当标注类型为Min时，初始化Min数据*/
                if (type && type == 'max') {
                    flag = i;
                }
            });

            /*存在数据，把haveData设为true*/
            haveData = true;
        }
    }

    var isChecked = $('#seriesMarkPointMaxShow').is(':checked');

    var maxPoint = {
        type: 'max',
        name: '最大值'
    };

    /*是否选中*/
    if (isChecked) {
        /*启用标注点最大值*/
        enableMarkPointMax();
        if (markPoint) {
            if (haveData) {
                markPoint['data'].push(maxPoint);
            } else {
                markPoint['data'] = [maxPoint];
            }
        } else {
            series['markPoint'] = {
                data: [maxPoint]
            };
        }

        setMarkPointLable(maxPoint);

    } else {
        /*禁用标注点最大值，并删除标注点最大值*/
        disableMarkPointMax();
        if (haveData && flag > -1) {
            markPoint['data'].splice(flag, 1);
        }
    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}


/*点击标注点最小值事件*/
function seriesMarkPointMinClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);
    var markPoint = series['markPoint'];

    /*初始化一个变量，用于存放最小值标注点在数组中的位置*/
    var flag = -1;

    /*初始化一个bool值的变量，用于判断是否存在数据*/
    var haveData = false;

    var data = [];
    /* 查找标注点最小值，在数组中的位置 */
    if (markPoint) {
        data = markPoint['data'];
        /*判断是否存在数组*/
        if (data) {
            /*循环取出标注点数据*/
            $.each(data, function (i, n) {
                var type = n['type'];
                /*当标注类型为Min时，初始化Min数据*/
                if (type && type == 'min') {
                    flag = i;
                }
            });

            /*存在数据，把haveData设为true*/
            haveData = true;
        }
    }

    var isChecked = $('#seriesMarkPointMinShow').is(':checked');

    var minPoint = {
        type: 'min',
        name: '最小值'
    };

    /*是否选中*/
    if (isChecked) {
        /*启用标注点最小值*/
        enableMarkPointMin();

        if (markPoint) {
            if (haveData) {
                markPoint['data'].push(minPoint);
            } else {
                markPoint['data'] = [minPoint];
            }
        } else {
            series['markPoint'] = {
                data: [minPoint]
            };
        }

        setMarkPointLable(minPoint);

    } else {
        /*禁用标注点最小值，并删除标注点最小值*/
        disableMarkPointMin();
        if (haveData && flag > -1) {
            markPoint['data'].splice(flag, 1);
        }
    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*根据标注点数据，初始会标注点页面数据*/
function setMarkPointLable(point) {
    if (point) {
        var type = point['type'];
        if (type == 'max') {
            var name = point['name'];
            if (name) {
                $('#seriesMarkPointMaxName').val(name);
            }

            var symbol = point['symbol'];
            if (symbol) {
                $('#seriesMarkPointMaxSymbol').combobox('setValue', symbol);
            }

            var symbolSize = point['symbolSize'];
            if (symbolSize) {
                $('#seriesMarkPointMaxSymbolSize').numberbox('setValue', symbolSize);
            }
        }

        if (type == 'min') {
            var name = point['name'];
            if (name) {
                $('#seriesMarkPointMinName').val(name);
            }

            var symbol = point['symbol'];
            if (symbol) {
                $('#seriesMarkPointMinSymbol').combobox('setValue', symbol);
            }

            var symbolSize = point['symbolSize'];
            if (symbolSize) {
                $('#seriesMarkPointMinSymbolSize').numberbox('setValue', symbolSize);
            }
        }
    }
}

/*禁用标注点*/
function disableMarkPoint() {
    $('#seriesMarkPointMaxShow').attr("checked", false);
    $('#seriesMarkPointMaxName').val('');
    $('#seriesMarkPointMaxSymbol').combobox('clear');
    $('#seriesMarkPointMaxSymbolSize').numberbox('clear');

    $('#seriesMarkPointMaxShow').attr("disabled", 'diasbled');
    $('#seriesMarkPointMaxName').attr('disabled', 'diasbled');
    $('#seriesMarkPointMaxSymbol').combobox('disable');
    $('#seriesMarkPointMaxSymbolSize').numberbox('disable');

    $('#seriesMarkPointMinShow').attr("checked", false);
    $('#seriesMarkPointMinName').val('');
    $('#seriesMarkPointMinSymbol').combobox('clear');
    $('#seriesMarkPointMinSymbolSize').numberbox('clear');

    $('#seriesMarkPointMinShow').attr("disabled", 'diasbled');
    $('#seriesMarkPointMinName').attr('disabled', 'diasbled');
    $('#seriesMarkPointMinSymbol').combobox('disable');
    $('#seriesMarkPointMinSymbolSize').numberbox('disable');
}

/*启用标注点*/
function enableMarkPoint() {

    $('#seriesMarkPointMaxShow').removeAttr('disabled');
    $('#seriesMarkPointMinShow').removeAttr('disabled');
}

/*禁用最大值标注点*/
function disableMarkPointMax() {
    $('#seriesMarkPointMaxName').val('');
    $('#seriesMarkPointMaxSymbol').combobox('clear');
    $('#seriesMarkPointMaxSymbolSize').numberbox('clear');

    $('#seriesMarkPointMaxName').attr('disabled', 'diasbled');
    $('#seriesMarkPointMaxSymbol').combobox('disable');
    $('#seriesMarkPointMaxSymbolSize').numberbox('disable');
}

/*启用最大值标注点*/
function enableMarkPointMax() {

    $('#seriesMarkPointMaxName').removeAttr('disabled');
    $('#seriesMarkPointMaxSymbol').combobox('enable');
    $('#seriesMarkPointMaxSymbolSize').numberbox('enable');
}

/*禁用最小值标注点*/
function disableMarkPointMin() {

    $('#seriesMarkPointMinName').val('');
    $('#seriesMarkPointMinSymbol').combobox('clear');
    $('#seriesMarkPointMinSymbolSize').numberbox('clear');

    $('#seriesMarkPointMinName').attr('disabled', 'diasbled');
    $('#seriesMarkPointMinSymbol').combobox('disable');
    $('#seriesMarkPointMinSymbolSize').numberbox('disable');
}

/*启用最小值标注点*/
function enableMarkPointMin() {

    $('#seriesMarkPointMinName').removeAttr('disabled');
    $('#seriesMarkPointMinSymbol').combobox('enable');
    $('#seriesMarkPointMinSymbolSize').numberbox('enable');
}


/*显示标注点事件*/
function seriesMarkLineClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);

    var isChecked = $('#seriesMarkLineShow').is(':checked');
    /*当选中标注线的时候，设置默认的标注线为平均值*/
    if (isChecked) {
        enableMarkLine();
        series['markLine'] = {data: [{name: '平均值', type: 'average'}]};
        /*页面上默认选中平均值*/
        $('#seriesMarkLineAvg').attr("checked", true);
    } else {
        disableMarkLine();
        delete series['markLine'];
    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}


/*series标注线开始标注形状发生改变事件*/
function seriesMarkLineStartChange(newValue, oldValue) {
    if (newValue && newValue != '') {
        /*获得当前选中的series*/
        var series = getSeries(checkedSeries);

        var markLine = series['markLine'];

        /*判断是否存在标注线*/
        if (markLine) {
            var symbol = markLine['symbol'];

            /*判断是否存在标注线形状*/
            if (symbol && symbol.length > 0) {
                symbol[0] = newValue;
            } else {
                markLine['symbol'] = [newValue];
            }
        }


        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    }
}

/*series标注线结束标注形状发生改变事件*/
function seriesMarkLineEndChange(newValue, oldValue) {
    if (newValue && newValue != '') {
        /*获得当前选中的series*/
        var series = getSeries(checkedSeries);

        var markLine = series['markLine'];

        /*判断是否存在标注线*/
        if (markLine) {
            var symbol = markLine['symbol'];
            /*判断是否存在标注线形状*/
            if (symbol && symbol.length > 1) {
                if (symbol.length == 1) {
                    symbol.push(newValue);
                }
                if (symbol.length > 1) {
                    symbol[1] = newValue;
                }

            } else {
                markLine['symbol'] = ['circle', newValue];
            }
        }

        /* 初始化图表 */
        initChartOptions(TABS_CHART1);
    }
}

/*标注线最大值选择*/
function seriesMarkLineMaxClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);
    var markLine = series['markLine'];
    var data = markLine['data'];

    var max = {name: '最大值', type: 'max'};

    var isChecked = $('#seriesMarkLineMax').is(':checked');

    /*当选中标注线的时候，添加最小值数据 */
    if (isChecked) {
        data.push(max);
    } else {
        /*如果数据长度等于1的话，就说明数组中只有最大值一个数据， 可以直接删除markLine*/
        if (data.length == 1) {
            disableMarkLine();
            $('#seriesMarkLineShow').attr("checked", false);
            delete series['markLine'];
        } else {
            var flag = -1;
            /*循环数据，查询出当前数据在数组中的第几个*/
            $.each(data, function (i, n) {
                if (n['type'] == 'max') {
                    flag = i;
                    return false;
                }
            });

            /*找到位置后，删除该数据*/
            data.splice(flag, 1);
        }

    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}

/*标注线最小值选择*/
function seriesMarkLineMinClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);
    var markLine = series['markLine'];
    var data = markLine['data'];

    var min = {name: '最小值', type: 'min'};

    var isChecked = $('#seriesMarkLineMin').is(':checked');

    /*当选中标注线的时候，添加最小值数据 */
    if (isChecked) {
        data.push(min);
    } else {
        /*如果数据长度等于1的话，就说明数组中只有最小值一个数据， 可以直接删除markLine*/
        if (data.length == 1) {
            disableMarkLine();
            $('#seriesMarkLineShow').attr("checked", false);
            delete series['markLine'];
        } else {
            var flag = -1;
            /*循环数据，查询出当前数据在数组中的第几个*/
            $.each(data, function (i, n) {
                if (n['type'] == 'min') {
                    flag = i;
                    return false;
                }
            });

            /*找到位置后，删除该数据*/
            data.splice(flag, 1);
        }

    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}


/*标注线平均值选择*/
function seriesMarkLineAvgClick() {
    /*获得当前选中的series*/
    var series = getSeries(checkedSeries);
    var markLine = series['markLine'];
    var data = markLine['data'];

    var avg = {name: '平均值', type: 'average'};

    var isChecked = $('#seriesMarkLineAvg').is(':checked');

    /*当选中标注线的时候，设置默认的标注线为平均值*/
    if (isChecked) {
        data.push(avg);
    } else {
        /*如果数据长度等于1的话，就说明数组中只有最大值一个数据， 可以直接删除markLine*/
        if (data.length == 1) {
            disableMarkLine();
            $('#seriesMarkLineShow').attr("checked", false);
            delete series['markLine'];
        } else {
            var flag = -1;
            /*循环数据，查询出当前数据在数组中的第几个*/
            $.each(data, function (i, n) {
                if (n['type'] == 'average') {
                    flag = i;
                    return false;
                }
            });

            /*找到位置后，删除该数据*/
            data.splice(flag, 1);
        }

    }

    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}


/*禁用标注线*/
function disableMarkLine() {
    $('#seriesMarkLineStart').combobox('clear');
    $('#seriesMarkLineEnd').combobox('clear');
    $('#seriesMarkLineMax').attr("checked", false);
    $('#seriesMarkLineMin').attr("checked", false);
    $('#seriesMarkLineAvg').attr("checked", false);

    $('#seriesMarkLineStart').combobox('disable');
    $('#seriesMarkLineEnd').combobox('disable');
    $('#seriesMarkLineMax').attr("disabled", "disabled");
    $('#seriesMarkLineMin').attr("disabled", "disabled");
    $('#seriesMarkLineAvg').attr("disabled", "disabled");
}

/*启用标注点*/
function enableMarkLine() {
    $('#seriesMarkLineStart').combobox('enable');
    $('#seriesMarkLineEnd').combobox('enable');
    $('#seriesMarkLineMax').removeAttr("disabled");
    $('#seriesMarkLineMin').removeAttr("disabled");
    $('#seriesMarkLineAvg').removeAttr("disabled");
}


function XChangeY() {
    var isChecked = $('#XChangeY').is(':checked');

    widgetConfig['x-change-y'] = isChecked;
    /* 初始化图表 */
    initChartOptions(TABS_CHART1);
}