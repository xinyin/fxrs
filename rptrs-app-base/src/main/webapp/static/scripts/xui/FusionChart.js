/*根路径*/
var chartbase = contextPath + '/scripts/FusionCharts';

/*是否加载了JS*/
var jsLoaded = false;

/*类型定义*/
var chartTypes = {
    Charts: {
        path: 'Charts',
        js: ['FusionCharts.js'],
        Area2D: {path: 'Area2D.swf'},
        Bar2D: {path: 'Bar2D.swf'},
        Bubble: {path: 'Bubble.swf'},
        Column2D: {path: 'Column2D.swf'},
        Column3D: {path: 'Column3D.swf'},
        Doughnut2D: {path: 'Doughnut2D.swf'},
        Doughnut3D: {path: 'Doughnut3D.swf'},
        Funnel: {path: 'Funnel.swf'},
        InverseMSArea: {path: 'InverseMSArea.swf'},
        InverseMSColumn2D: {path: 'InverseMSColumn2D.swf'},
        InverseMSLine: {path: 'InverseMSLine.swf'},
        Line: {path: 'Line.swf'},
        LogMSColumn2D: {path: 'LogMSColumn2D.swf'},
        LogMSLine: {path: 'LogMSLine.swf'},
        Marimekko: {path: 'Marimekko.swf'},
        MSArea: {path: 'MSArea.swf'},
        MSBar2D: {path: 'MSBar2D.swf'},
        MSBar3D: {path: 'MSBar3D.swf'},
        MSColumn2D: {path: 'MSColumn2D.swf'},
        MSColumn3D: {path: 'MSColumn3D.swf'},
        MSColumn2DLineDY: {path: 'MSColumn2DLineDY.swf'},
        MSColumnLine3D: {path: 'MSColumnLine3D.swf'},
        MSCombi2D: {path: 'MSCombi2D.swf'},
        MSCombi3D: {path: 'MSCombi3D.swf'},
        MSCombiDY2D: {path: 'MSCombiDY2D.swf'},
        MSLine: {path: 'MSLine.swf'},
        MSSpline: {path: 'MSSpline.swf'},
        MSSplineArea: {path: 'MSSplineArea.swf'},
        MSStackedColumn2D: {path: 'MSStackedColumn2D.swf'},
        MSStackedColumn3D: {path: 'MSStackedColumn3D.swf'},
        Pareto2D: {path: 'Pareto2D.swf'},
        Pareto3D: {path: 'Pareto3D.swf'},
        Pie2D: {path: 'Pie2D.swf'},
        Pie3D: {path: 'Pie3D.swf'},
        Pyramid: {path: 'Pyramid.swf'},
        Scatter: {path: 'Scatter.swf'},
        ScrollArea2D: {path: 'ScrollArea2D.swf'},
        ScrollColumn2D: {path: 'ScrollColumn2D.swf'},
        ScrollCombi2D: {path: 'ScrollCombi2D.swf'},
        ScrollLine2D: {path: 'ScrollLine2D.swf'},
        ScrollStackedColumn2D: {path: 'ScrollStackedColumn2D.swf'},
        Spline: {path: 'Spline.swf'},
        SplineArea: {path: 'SplineArea.swf'},
        SSGrid: {path: 'SSGrid.swf'},
        StackedArea2D: {path: 'StackedArea2D.swf'},
        StackedBar2D: {path: 'StackedBar2D.swf'},
        StackedBar3D: {path: 'StackedBar3D.swf'},
        StackedColumn2D: {path: 'StackedColumn2D.swf'},
        StackedColumn2DLine: {path: 'StackedColumn2DLine.swf'},
        StackedColumn3D: {path: 'StackedColumn3D.swf'},
        StackedColumn3DLine: {path: 'StackedColumn3DLine.swf'},
        StackedColumn3DLineDY: {path: 'StackedColumn3DLineDY.swf'},
        ZoomLine: {path: 'ZoomLine.swf'}
    },
    PowerCharts: {
        path: 'PowerCharts',
        js: ['FusionCharts.js'],
        CandleStick: {path: 'CandleStick.swf'},
        DragArea: {path: 'DragArea.swf'},
        DragColumn2D: {path: 'DragColumn2D.swf'},
        DragLine: {path: 'DragLine.swf'},
        DragNode: {path: 'DragNode.swf'},
        ErrorBar2D: {path: 'ErrorBar2D.swf'},
        HeatMap: {path: 'HeatMap.swf'},
        InverseMSArea: {path: 'InverseMSArea.swf'},
        InverseMSColumn2D: {path: 'InverseMSColumn2D.swf'},
        InverseMSLine: {path: 'InverseMSLine.swf'},
        Kagi: {path: 'Kagi.swf'},
        LogMSColumn2D: {path: 'LogMSColumn2D.swf'},
        LogMSLine: {path: 'LogMSLine.swf'},
        MSSpline: {path: 'MSSpline.swf'},
        MSSplineArea: {path: 'MSSplineArea.swf'},
        MultiAxisLine: {path: 'MultiAxisLine.swf'},
        MultiLevelPie: {path: 'MultiLevelPie.swf'},
        Radar: {path: 'Radar.swf'},
        SelectScatter: {path: 'SelectScatter.swf'},
        Spline: {path: 'Spline.swf'},
        SplineArea: {path: 'SplineArea.swf'},
        Waterfall2D: {path: 'Waterfall2D.swf'}
    },
    Widgets: {
        path: 'Widgets',
        js: ['FusionCharts.js'],
        AngularGauge: {path: 'AngularGauge.swf'},
        Bulb: {path: 'Bulb.swf'},
        Cylinder: {path: 'Cylinder.swf'},
        DrawingPad: {path: 'DrawingPad.swf'},
        Funnel: {path: 'Funnel.swf'},
        Gantt: {path: 'Gantt.swf'},
        HBullet: {path: 'HBullet.swf'},
        HLED: {path: 'HLED.swf'},
        HLinearGauge: {path: 'HLinearGauge.swf'},
        Pyramid: {path: 'Pyramid.swf'},
        RealTimeArea: {path: 'RealTimeArea.swf'},
        RealTimeColumn: {path: 'RealTimeColumn.swf'},
        RealTimeLine: {path: 'RealTimeLine.swf'},
        RealTimeLineDY: {path: 'RealTimeLineDY.swf'},
        RealTimeStackedArea: {path: 'RealTimeStackedArea.swf'},
        RealTimeStackedColumn: {path: 'RealTimeStackedColumn.swf'},
        SparkLine: {path: 'SparkLine.swf'},
        SparkColumn: {path: 'SparkColumn.swf'},
        SparkWinLoss: {path: 'SparkWinLoss.swf'},
        Thermometer: {path: 'Thermometer.swf'},
        VBullet: {path: 'VBullet.swf'},
        VLED: {path: 'VLED.swf'}
    }
};

/**
 * 加载单个 JS 文件
 * @param url
 * @param callback
 * @returns
 */
function loadSingleJs(url, callback) {
    var done = false;
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.language = 'javascript';
    script.src = url;
    script.onload = script.onreadystatechange = function () {
        if (!done && (!script.readyState || script.readyState == 'loaded' || script.readyState == 'complete')) {
            done = true;
            script.onload = script.onreadystatechange = null;
            if (callback) callback.call(script);
        }
    };
    document.getElementsByTagName("head")[0].appendChild(script);
}


/*图表的加载器*/
chartloader = {
    /*加载某种特定图表类型的 JS */
    load: function (chartType) {
        if (jsLoaded) {
            return;
        }

        var type = chartTypes[chartType];
        if (type == undefined) return;

        var baseurl = chartbase + '/' + type.path + '/';
        var count = 0;
        for (var i = 0; i < type.js.length; i++) {
            var url = baseurl + type.js[i];
            loadSingleJs(url, function () {
                count++;
                if (count == type.js.length) {
                    //jsLoaded = true;
                }
            });
        }
    }
};

/*加载图表的js*/
chartloader.load('Charts');


/*定义 FunsionChart 类型*/
;(function ($) {

    /**
     * 用于在某个对象上存取 option
     */
    function _options(target, opts) {
        /*未给出第二个参数，取回option*/
        if (opts == undefined) {
            var data = $.data(target, 'FusionChart');
            if (data) {
                return data.options;
            }
            return null;
        }
    }


    /**
     * 初始化方法
     */
    function _init(target) {
        var opts = _options(target);

        var type = chartTypes[opts.type];
        var swfUrl = chartbase + '/' + type.path + '/' + type[opts.subType].path;
        var width = opts.width;
        var height = opts.height;
        if (width == undefined) {
            width = $(target).width();
        }
        if (height == undefined) {
            height = $(target).height();
        }

        var chart = new FusionCharts(swfUrl, opts.chartId, width, height, '0', '1');
        if ('json' == opts.dataFormat) {
            chart.setJSONData(opts.dataSource);
        } else {
            chart.setXMLData(opts.dataSource);
        }
        chart.render($(target).attr('id'));
    }


    /**
     * 销毁一个 FusionChart 对象
     */
    function _destroy(target) {
        var opts = _options(target);
        var chartId = opts.chartId;
        /*销毁图表*/
        var chart = FusionCharts(chartId);
        if (chart != undefined && chart != null) {
            chart.dispose();
        }
        /*清空数据*/
        $.removeData(target, 'FusionChart');
    }


    /**
     * FusionChart 定义
     */
    $.fn.FusionChart = function (options) {
        /*如果第一个参数为 string 类型，则为调用方法，其他的参数作为方法参数*/
        if (typeof options == 'string') {
            /*将参数中的第一个参数去掉，剩下的作为参数传入方法调用*/
            var args = Array.prototype.slice.call(arguments, 1);
            /*返回值*/
            var res = undefined;
            this.each(function () {
                /*判断传入的方法是否有定义，如果有，在当前的jQuery对象上进行调用*/
                if ($.isFunction($.fn.FusionChart.methods[options])) {
                    var r = $.fn.FusionChart.methods[options].apply(this, args);
                    if (res === undefined) {
                        res = r;
                    }
                }
            });

            if (res !== undefined) {
                return res;
            }
            return this;
        }

        /*初始化 FusionChart*/
        options = options || {};
        return this.each(function () {
            /*取回在当前对象上上存放的选项*/
            var data = $.data(this, 'FusionChart');
            /*
             * 该对象上保存过数据
             */
            if (data) {
                _destroy(this);
            }
            /*
             * 用 defaults 和新的 option 合并，建立新的 FusionChart
             */
            var opts = $.extend({}, $.fn.FusionChart.defaults, options);
            /*将数据保存回当前对象*/
            $.data(this, 'FusionChart', {options: opts});
            /*初始化FusionChart*/
            _init(this);
        });
    };

    /**
     * FusionChart 默认参数定义
     */
    $.fn.FusionChart.defaults = {
        /*主图表类型, Charts, Widgets or PowerCharts*/
        type: 'Charts',
        /*副图表类型， 如： Column3D, Funnl, HeatMap 等*/
        subType: 'Column3D',
        /*图表对象的 ID*/
        chartId: 'chartId',
        /*数据格式，默认为 json*/
        dataFormat: 'json'
    };


})(jQuery);