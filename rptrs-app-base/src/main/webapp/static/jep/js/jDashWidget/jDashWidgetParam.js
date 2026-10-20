/**
 * 图表组件参数调整脚本
 * @author zhangyan <yan.zhang44@yzj.com>
 * @version: 1.0
 *
 */

/*==============================变量定义==============================*/

/*定义全局变量，用于存放选择的Axis、yAxis、series*/
var checkedAxis = 0, checkedyAxis = 0, checkedSeries = 0;

/*定义一个全局变量，存储当前激活状态图表的option脚本*/
var currOption;

/*定义一个全局变量，标志图表参数是否更新*/
var updateFlag = false;

/*==============================方法定义[参数调整]==============================*/

/*设置临时全局变量，用于参数调整使用*/
function initTmpOption(val) {
    currOption = $.parseJSON(val);
}

function initSettings() {
    var chartType = widgetConfig['chart-type'];
    if (chartType == TYPE_LINE || chartType == TYPE_BAR || chartType == TYPE_STACK) {
        $('.div-pie-fg ').hide();
        $('.div-gauge-fg ').hide();
        $('.div-radar-fg ').hide();
        $('.div-line-fg ').show();

        /*多个X轴*/
        var xAxisArr = widgetConfig['xAxis'];
        if (xAxisArr.length > 1) {
            $('#xAxisDiv').show();
            $.each(xAxisArr, function (i, v) {
                var html = '';
                if (i == 0) {
                    html = '<button type="button" id="xAxis' + i + '"'
                        + 'class="btn btn-xs btn-default btn-xAxis active" onclick="xAxisClick(this)"'
                        + ' style="margin-right: 5px;">X轴' + i + '</button>';
                    checkedAxis = 0;
                } else {
                    html = '<button type="button" id="xAxis' + i + '"'
                        + 'class="btn btn-xs btn-default btn-xAxis" onclick="xAxisClick(this)"'
                        + ' style="margin-right: 5px;">X轴' + i + '</button>';
                }
                $('#xAxisDiv').children('.form-group ').children('.margin-top-Fg').append(html);
            });

        } else {
            $('#xAxisDiv').hide();
        }

        /*多个Y轴*/
        var yAxisArr = widgetConfig['yAxis'];
        if (yAxisArr.length > 1) {
            $('#yAxisDiv').show();
            $.each(yAxisArr, function (i, v) {
                var html = '';
                if (i == 0) {
                    html = '<button type="button" id="yAxis' + i + '"'
                        + 'class="btn btn-xs btn-default btn-yAxis active" onclick="yAxisClick(this)"'
                        + ' style="margin-right: 5px;">Y轴' + i + '</button>';
                    checkedyAxis = 0;
                } else {
                    html = '<button type="button" id="yAxis' + i + '"'
                        + 'class="btn btn-xs btn-default btn-yAxis" onclick="yAxisClick(this)"'
                        + ' style="margin-right: 5px;">Y轴' + i + '</button>';
                }
                $('#yAxisDiv').children('.form-group ').children('.margin-top-Fg').append(html);
            });

        } else {
            $('#yAxisDiv').hide();
        }

    } else if (chartType == TYPE_PIE) {
        $('.div-line-fg ').hide();
        $('.div-gauge-fg ').hide();
        $('.div-radar-fg ').hide();
        $('.div-pie-fg ').show();
    } else if (chartType == TYPE_RADAR) {
        $('.div-line-fg ').hide();
        $('.div-pie-fg ').hide();
        $('.div-gauge-fg ').hide();
        $('.div-radar-fg ').show();

    } else if (chartType == TYPE_GAUGE) {
        $('.div-line-fg ').hide();
        $('.div-pie-fg ').hide();
        $('.div-radar-fg ').hide();
        $('.div-gauge-fg ').show();
    }

    /*加载参数设置的系列值*/
    $('#seriesBtnFg').children('.margin-top-Fg').empty(); //清空子元素
    var seriesArr = widgetConfig['series'];
    $.each(seriesArr, function (i, v) {
        var seriesHtml = '';
        if (i == 0) {
            seriesHtml = '<button type="button" id="series' + i + '"'
                + 'class="btn btn-xs btn-default btn-series active" onclick="seriesClick(this)" '
                + 'style="margin-right: 5px;">统计项' + i + '</button>';

            checkedSeries = 0;
        } else {
            seriesHtml = '<button type="button" id="series' + i + '"'
                + 'class="btn btn-xs btn-default btn-series" onclick="seriesClick(this)" '
                + 'style="margin-right: 5px;">统计项' + i + '</button>';
        }
        $('#seriesBtnFg').children('.margin-top-Fg').append(seriesHtml);
    });
}

function seriesClick(el) {
    var btnArr = $('.btn-series');
    $.each(btnArr, function (i, v) {
        if ($('#series' + i).hasClass('active')) {
            $('#series' + i).removeClass('active');
        }
    });
    $(el).toggleClass('active');
    var idStr = el.id;
    idStr = idStr.substring(idStr.length - 1, idStr.length);
    checkedSeries = Number(idStr);
}

function xAxisClick(el) {
    var btnArr = $('.btn-xAxis');
    $.each(btnArr, function (i, v) {
        if ($('#xAxis' + i).hasClass('active')) {
            $('#xAxis' + i).removeClass('active');
        }
    });
    $(el).toggleClass('active');
    var idStr = el.id;
    idStr = idStr.substring(idStr.length - 1, idStr.length);
    checkedAxis = Number(idStr);
}

function yAxisClick(el) {
    var btnArr = $('.btn-yAxis');
    $.each(btnArr, function (i, v) {
        if ($('#yAxis' + i).hasClass('active')) {
            $('#yAxis' + i).removeClass('active');
        }
    });
    $(el).toggleClass('active');
    var idStr = el.id;
    idStr = idStr.substring(idStr.length - 1, idStr.length);
    checkedyAxis = Number(idStr);
}

function updateWidgetConfig() {
    if (updateFlag) //缓存调整后的参数
    {
        var chartType = widgetConfig['chart-type'];
        var tmpWidgetConfig = option;
        if (chartType == TYPE_LINE || chartType == TYPE_BAR || chartType == TYPE_STACK) {
            /*更改后的脚本存入 widgetConfig 脚本中*/
            tmpWidgetConfig['category-def'] = widgetConfig['category-def'];
            tmpWidgetConfig['x-change-y'] = widgetConfig['x-change-y'];
        } else if (chartType == TYPE_PIE || chartType == TYPE_RADAR) {
            /*更改后的脚本存入 widgetConfig 脚本中*/
            tmpWidgetConfig['category-def'] = widgetConfig['category-def'];
        }

        /*更改后的脚本存入 widgetConfig 脚本中*/
        tmpWidgetConfig['provider'] = widgetConfig['provider'];
        tmpWidgetConfig['chart-type'] = widgetConfig['chart-type'];
        tmpWidgetConfig['series-def'] = widgetConfig['series-def'];
        widgetConfig = tmpWidgetConfig;
    }
}

/*更新脚本通用方法*/
function commonFn(optionScript) {
    updateFlag = true;
    var tmpOption = JSON.stringify(optionScript);
    initchart(TABS_CHART, tmpOption);
}

/*--------------基础[通用]--------------*/

/*动画 animation*/
function animationCheckChange(el, id) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        animationFn(val);
    } else {
        obj.attr('disabled', true);
        option['animation'] = currOption['animation'];
        commonFn(option);
    }
}

function animationChange(el) {
    animationFn($(el).val());
}

function animationFn(val) {
    if (val == 'Y')
        option['animation'] = true;
    else if (val == 'N')
        option['animation'] = false;
    commonFn(option);
}

/*图整体背景颜色 backgroundColor*/
function chartsBgColorCheckChange(el, clrpkrId) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                option['backgroundColor'] = color.tiny.toRgbString();
                commonFn(option);
            }
        });
        option['backgroundColor'] = $("#" + clrpkrId).val();
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        option['backgroundColor'] = currOption['backgroundColor'];
    }
    commonFn(option);
}

/*系列的图形类型*/
function seriesChartsTypeNormal(el) {
    var obj = $(el).parent().parent().parent('div .form-group ').next();
    var symbolObj = obj.parent('div .div-line-fg').next();

    var seriesArr = option['series'];
    var val = $(el).val();
    if (val == 'line') {
        obj.show();
        symbolObj.show();
    } else {
        obj.hide();
        symbolObj.hide();
    }
    $.each(seriesArr, function (i, v) {
        v.type = val;
    });
    option['series'] = seriesArr;
    commonFn(option);
}

/*折线转为平滑曲线*/
function line2SmoothCurveChange(el, id) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        smoothChange(val);
    } else {
        obj.attr('disabled', true);
        var seriesArr = option['series'];
        var currSeriesArr = currOption['series'];
        $.each(currSeriesArr, function (i, v) {
            seriesArr[i].smooth = v.smooth;
        });
        option['series'] = seriesArr;
        commonFn(option);
    }
}

function line2SmoothChange(el) {
    var val = $(el).val();
    smoothChange(val);
}

function smoothChange(val) {
    var seriesArr = option['series'];
    if (val == 'Y') {
        $.each(seriesArr, function (i, v) {
            v.smooth = true;
        });
    } else {
        $.each(seriesArr, function (i, v) {
            v.smooth = false;
        });
    }
    option['series'] = seriesArr;
    commonFn(option);
}

/*小标志大小: symbolSize*/
function symbolSizeCheckChange(el, id) {
    var seriesArr = option['series'];
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        var val = $('#' + id).val();
        $.each(seriesArr, function (i, v) {
            v.symbolSize = val;
        });
    } else {
        $('#' + id).attr('disabled', true);
        var currSeriesArr = currOption['series'];
        $.each(currSeriesArr, function (i, v) {
            seriesArr[i].symbolSize = v.symbolSize;
        });
    }
    option['series'] = seriesArr;
    commonFn(option);
}

function symbolSizeChange(el) {
    var val = $(el).val();
    var seriesArr = option['series'];
    $.each(seriesArr, function (i, v) {
        v.symbolSize = val;
    });
    option['series'] = seriesArr;
    commonFn(option);
}

/*--------------基础[系列]--------------*/

/*更新第一级属性的值-通用方法*/
function updateCurrSeries(target, val) {

    /*取出属性，并赋值*/
    var _series = option['series'][checkedSeries];
    _series[target] = val;

    /*更新图表*/
    option['series'][checkedSeries] = _series;
    commonFn(option);
}

function getDefaultSeriesVal(target) {
    var _currSeries = currOption['series'][checkedSeries];
    if (_currSeries) return _currSeries[target];
    return undefined;
}

/*更新第二级属性的值-通用方法*/
function updateCurrSeriesSec(target, target1, val) {

    /*给对应属性赋值*/
    var _series = option['series'][checkedSeries];
    var _targetSeries = _series[target];
    if (!_targetSeries) _targetSeries = {};

    _targetSeries[target1] = val;

    /*更新图表*/
    _series[target] = _targetSeries
    option['series'][checkedSeries] = _series;
    commonFn(option);
}

function getDefaultSeriesValSec(target, target1) {
    var tmp = undefined;
    var _currSeries = currOption['series'][checkedSeries];
    if (_currSeries) {
        var _targetcurrSeries = _currSeries[target];
        if (_targetcurrSeries) tmp = _targetcurrSeries[target1];
    }
    return tmp;
}

/*更新第三级属性的值-通用方法
 * target：一级属性。如：axisLine
 * target1：二级属性。如：lineStyle
 * target2： 三级属性。如：color*/
function updateCurrSeriesThird(target, target1, target2, val) {

    var _series = option['series'][checkedSeries];
    var _targetSeries = _series[target];
    if (!_targetSeries) _targetSeries = {};
    var _style = _targetSeries[target1];
    if (!_style) _style = {};

    _style[target2] = val;

    /*更新图表*/
    _targetSeries[target1] = _style;
    _series[target] = _targetSeries
    option['series'][checkedSeries] = _series;
    commonFn(option);
}

function getDefaultSeriesValThird(target, target1, target2) {
    var tmp = undefined;
    var _currSeries = currOption['series'][checkedSeries];
    if (_currSeries) {
        var _targetcurrSeries = _currSeries[target];
        if (_targetcurrSeries) {
            var _currStyle = _targetcurrSeries[target1];
            if (_currStyle) tmp = _currStyle[target2];
        }
    }

    return tmp;
}

/*更新第四级属性的值-通用方法。如：series{itemStyle：{normal：{label：{position: "right",show: true}}}}*/
function updateCurrSeriesForth(status, target1, target2, val) {

    var _series = option['series'][checkedSeries];
    var _itemStyle = _series['itemStyle'];
    if (!_itemStyle) _itemStyle = {};

    var _status = _itemStyle[status];
    if (!_status) _status = {};

    var _target1 = _status[target1];
    if (!_target1) _target1 = {};

    _target1[target2] = val;

    /*更新图表*/
    _status[target1] = _target1;
    _itemStyle[status] = _status;
    _series['itemStyle'] = _itemStyle;
    option['series'][checkedSeries] = _series;
    commonFn(option);
}

function getDefaultSeriesValForth(status, target1, target2) {
    var tmp = undefined;
    var _currSeries = currOption['series'][checkedSeries];
    if (_currSeries) {
        var _currItemStyle = _currSeries['itemStyle'];
        if (_currItemStyle) {
            var _currStatus = _currItemStyle[status];
            if (_currStatus) {
                var _target1 = _currStatus[target1];
                if (_target1) tmp = _target1[target2];
            }
        }
    }
    return tmp;
}

/*更新第五级属性的值-通用方法。如：series{itemStyle：{normal：{label：{textStyle: {fontSize: 12}}}}}*/
function updateCurrSeriesFifth(status, target, val) {

    var _series = option['series'][checkedSeries];
    var _itemStyle = _series['itemStyle'];
    if (!_itemStyle) _itemStyle = {};

    var _status = _itemStyle[status];
    if (!_status) _status = {};

    var _label = _status['label'];
    if (!_label) _label = {};

    var _style = _label['textStyle'];
    if (!_style) _style = {};

    _style[target] = val;

    /*更新图表*/
    _label['textStyle'] = _style;
    _status['label'] = _label;
    _itemStyle[status] = _status;
    _series['itemStyle'] = _itemStyle;
    option['series'][checkedSeries] = _series;
    commonFn(option);
}

function getDefaultSeriesValFifth(status, target) {
    var tmp = undefined;
    var _currSeries = currOption['series'][checkedSeries];
    if (_currSeries) {
        var _currItemStyle = _currSeries['itemStyle'];
        if (_currItemStyle) {
            var _currStatus = _currItemStyle[status];
            if (_currStatus) {
                var _currLabel = _currStatus['label'];
                if (_currLabel) {
                    var _currStyle = _label['textStyle'];
                    if (_currStyle) tmp = _currStyle[target];
                }
            }
        }
    }
    return tmp;
}

/*系列的图形类型 type*/
function seriesChartsType(el, target) {
    var val = $(el).val();
    if (val == 'line') {
        $('.bar-div-show').hide();
        $('.bar-div').show();
    } else {
        $('.bar-div-show').show();
        $('.bar-div').hide();
    }
    updateCurrSeries(target, val);
}

/*折线转为平滑曲线 smooth*/
function seriesFirstPCheckChange(el, id, target) {
    var val;
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop('checked');
    if (isChecked) {
        obj.attr('disabled', false);
        val = $('input[name="' + id + '"]:checked').val();
        if (val == 'Y') val = true;
        else if (val == 'N' || val == 'none') val = false;
    } else {
        obj.attr('disabled', true);
        val = getDefaultSeriesVal(target);
    }
    updateCurrSeries(target, val);
}

function seriesFirstPChange(el, target) {
    var val = $(el).val();
    if (val == 'Y') val = true;
    else if (val == 'N' || val == 'none') val = false;
    updateCurrSeries(target, val);
}

/*堆积名称 stack*/
function stackCheckChange(el, id, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + id).attr('disabled', false);
        var val = $("#" + id).val();
        $("#" + id).blur(function () {
            var val = $(this).val();
            updateCurrSeries(target, val);
        });
        updateCurrSeries(target, val);
    } else {
        $("#" + id).attr('disabled', true);
        var val = getDefaultSeriesVal(target);
        updateCurrSeries(target, val);
    }
}

/*饼图圆心坐标 center, 饼图半径 radius*/
function seriesSliderXYChange(el, idx, idy, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + idx).slider("enable");
        $('#' + idy).slider("enable");

        $('#' + idx).on("slide", function (slideEvt) {
            let val = slideEvt.value;
            $('#' + idx + 'Val').text(val);
            if (target == 'center')
                val = val + '%';

            let valy = $('#' + idy + 'Val').text();
            var arr = [val, valy];
            updateCurrSeries(target, arr);
        });

        $('#' + idy).on("slide", function (slideEvt) {
            let val = slideEvt.value;
            $('#' + idy + 'Val').text(val);
            if (target == 'center')
                val = val + '%';

            let valx = $('#' + idx + 'Val').text();
            var arr = [valx, val];
            updateCurrSeries(target, arr);
        });

        let valx = $('#' + idx + 'Val').text();
        let valy = $('#' + idy + 'Val').text();
        var arr = [valx, valy];
        updateCurrSeries(target, arr);
    } else {
        $('#' + idx).slider("disable");
        $('#' + idy).slider("disable");

        var val = getDefaultSeriesVal(target);
        updateCurrSeries(target, val);
    }
}

/*起始角度 startAngle, 饼图最小角度 minAngle, 选中时扇区偏移量 selectedOffset*/
function seriesSingleSliderChange(el, id, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).slider("enable");
        $('#' + id).on("slide", function (slideEvt) {
            let val = slideEvt.value;
            $('#' + id + 'Val').text(val);
            if (target == 'radius')
                val = val + '%';
            updateCurrSeries(target, val);
        });

        let val = $('#' + id + 'Val').text();
        if (target == 'radius')
            val = val + '%';
        updateCurrSeries(target, val);
    } else {
        $('#' + id).slider("disable");
        var val = getDefaultSeriesVal(target);
        updateCurrSeries(target, val);
    }
}


/*小标志大小*/
function aSymbolSizeCheckChange(el, id, target) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        val = $('#' + id).val();
    } else {
        $('#' + id).attr('disabled', true);
        val = getDefaultSeriesVal(target);
    }
    updateCurrSeries(target, val);
}

function aSymbolSizeChange(el, target) {
    var val = $(el).val();
    updateCurrSeries(target, val);
}

/*旋转角度*/
function symbolRotateCheck(el, id, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).slider("enable");
        $('#' + id).on("slide", function (slideEvt) {
            let val = slideEvt.value;
            $('#' + id + 'Val').text(val);
            updateCurrSeries(target, val);
        });

        let val = $('#' + id + 'Val').text();
        updateCurrSeries(target, val);
    } else {
        $('#' + id).slider("disable");
        var val = getDefaultSeriesVal(target);
        updateCurrSeries(target, val);
    }
}

/*系列特定颜色、边框颜色*/
function seriesColorChange(el, clrpkrId, target, target1, target2) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                let val = color.tiny.toRgbString();
                updateCurrSeriesThird(target, target1, target2, val);
            }
        });
        var val = $("#" + clrpkrId).val();
        updateCurrSeriesThird(target, target1, target2, val);
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        var val = getDefaultSeriesValThird(target, target1, target2);
        updateCurrSeriesThird(target, target1, target2, val);
    }
}

/*边框宽度*/
function seriesBorderWthCheckChange(el, id, target, target1, target2) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        val = $('#' + id).val();
    } else {
        $('#' + id).attr('disabled', true);
        val = getDefaultSeriesValThird(target, target1, target2);
    }
    updateCurrSeriesThird(target, target1, target2, val);
}

function seriesBorderWthChange(el, target, target1, target2) {
    var val = $(el).val();
    updateCurrSeriesThird(target, target1, target2, val);
}

/*系列线条颜色 lineStyle: color*/
function lineColorChange(el, clrpkrId, status, target1, target2) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                let val = color.tiny.toRgbString();
                updateCurrSeriesForth(status, target1, target2, val);
            }
        });
        var val = $("#" + clrpkrId).val();
        updateCurrSeriesForth(status, target1, target2, val);
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        var val = getDefaultSeriesValForth(status, target1, target2);
        updateCurrSeriesForth(status, target1, target2, val);
    }
}

/*线条宽度*/
function lineWidthCheckChange(el, id, status, target1, target2) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        val = $('#' + id).val();
    } else {
        $('#' + id).attr('disabled', true);
        val = getDefaultSeriesValForth(status, target1, target2);
    }
    updateCurrSeriesForth(status, target1, target2, val);
}

function lineWidthChange(el, status, target1, target2) {
    var val = $(el).val();
    updateCurrSeriesForth(status, target1, target2, val);
}


/*是否显示数据项标签 label true、项标签显示位置 position*/
function labelForthCheckChange(el, id, status, target1, target2) {
    var val;
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop('checked');
    if (isChecked) {
        obj.attr('disabled', false);
        val = $('input[name="' + id + '"]:checked').val();
        if (val == 'Y') val = true;
        else if (val == 'N') val = false;
    } else {
        obj.attr('disabled', true);
        val = getDefaultSeriesValForth(status, target1, target2);
    }
    updateCurrSeriesForth(status, target1, target2, val);
}

function labelForthChange(el, status, target1, target2) {
    var val = $(el).val();
    if (val == 'Y') val = true;
    else if (val == 'N') val = false;
    updateCurrSeriesForth(status, target1, target2, val);
}

/*系列文本颜色 label: color*/
function labelColorChange(el, clrpkrId, status, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                let val = color.tiny.toRgbString();
                updateCurrSeriesFifth(status, target, val);
            }
        });
        var val = $("#" + clrpkrId).val();
        updateCurrSeriesFifth(status, target, val);
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        var val = getDefaultSeriesValFifth(status, target);
        updateCurrSeriesFifth(status, target, val);
    }
}

/*字体大小*/
function labelSizeCheckChange(el, id, status, target) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        val = $('#' + id).val();
    } else {
        $('#' + id).attr('disabled', true);
        val = getDefaultSeriesValFifth(status, target);
    }
    updateCurrSeriesFifth(status, target, val);
}

function labelSizeChange(el, status, target) {
    var val = $(el).val();
    updateCurrSeriesFifth(status, target, val);
}

/*字体样式 fontStyle、fontWeight*/
function labelStyleCheckChange(el, id, status, target, boldId) {
    var obj = $('input[name="' + id + '"]');
    var objBold = $('input[name="' + id + 'Bold"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        objBold.attr('disabled', false);
        var _bold = $('#' + boldId).prop("checked");
        if (_bold) updateCurrSeriesFifth(status, 'fontWeight', 'bold');
        else updateCurrSeriesFifth(status, 'fontWeight', 'normal');

        var val = $('input[name="' + id + '"]:checked').val();
        updateCurrSeriesFifth(status, target, val);
    } else {
        obj.attr('disabled', true);
        objBold.attr('disabled', true);
        var val1 = getDefaultSeriesValFifth(status, 'fontWeight');
        updateCurrSeriesFifth(status, 'fontWeight', val1);

        var val2 = getDefaultSeriesValFifth(status, target);
        updateCurrSeriesFifth(status, target, val2);
    }
}

function labelStyleChange(el, status, target) {
    var val = $(el).val();
    updateCurrSeriesFifth(status, target, val);
}

function labelStyleBoldChange(el, status, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) updateCurrSeriesFifth(status, target, 'bold');
    else updateCurrSeriesFifth(status, target, 'normal');

}

/*--------------标题[内容]--------------*/

/*主标题文本 text*/
function titleCheckChange(el, id) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + id).attr('disabled', false);
        var _title = option['title'].text;
        $("#" + id).val(_title);
        $("#" + id).blur(function () {
            option['title'].text = $(this).val();
            commonFn(option);
        });
    } else {
        $("#" + id).attr('disabled', true);
        option['title'].text = currOption['title'].text;
        commonFn(option);
    }
}

/*主标题文本超链接 link*/
function titleLinkCheckChange(el, id) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + id).attr('disabled', false);
        var linkDefault = option['title'].link;
        if (linkDefault) $("#" + id).val(base + linkDefault);
        else $("#" + id).val(base)
        $("#" + id).blur(function () {
            option['title'].link = $(this).val();
            commonFn(option);
        });
    } else {
        $("#" + id).attr('disabled', true);
        option['title'].link = currOption['title'].link;
        commonFn(option);
    }
}

/*副标题文本 subtext*/
function subTitleCheckChange(el, id) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + id).attr('disabled', false);
        var _subtext = option['title'].subtext;
        $("#" + id).val(_subtext)
        $("#" + id).blur(function () {
            option['title'].subtext = $(this).val();
            commonFn(option);
        });
    } else {
        $("#" + id).attr('disabled', true);
        option['title'].subtext = currOption['title'].subtext;
        commonFn(option);
    }
}

/*副标题文本超链接 sublink*/
function subTitleLinkCheckChange(el, id) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + id).attr('disabled', false);
        var _sublink = option['title'].sublink;
        if (_sublink) $("#" + id).val(base + _sublink);
        else $("#" + id).val(base);
        $("#" + id).blur(function () {
            option['title'].sublink = $(this).val();
            commonFn(option);
        });
    } else {
        $("#" + id).attr('disabled', true);
        option['title'].sublink = currOption['title'].sublink;
        commonFn(option);
    }
}

/*--------------标题[样式]--------------*/

/*水平安放位置 x*/
function positionHCheckChange(el, id) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        option['title'].x = val;
    } else {
        obj.attr('disabled', true);
        option['title'].x = currOption['title'].x;
    }
    commonFn(option);
}

function positionHChange(el) {
    option['title'].x = $(el).val();
    commonFn(option);
}

/*垂直安放位置 y*/
function positionVCheckChange(el, id) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        option['title'].y = val;
    } else {
        obj.attr('disabled', true);
        option['title'].y = currOption['title'].y;
    }
    commonFn(option);
}

function positionVChange(el) {
    option['title'].y = $(el).val();
    commonFn(option);
}

/*标题对齐方式 textAlign*/
function titleAlignTypeCheckChange(el, id) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        option['title'].textAlign = val;
    } else {
        obj.attr('disabled', true);
        option['title'].textAlign = currOption['title'].textAlign;
    }
    commonFn(option);
}

function titleAlignTypeChange(el) {
    option['title'].textAlign = $(el).val();
    commonFn(option);
}

/*[主标题、提示]: 标题背景颜色 backgroundColor*/
function bgColorChange(el, clrpkrId, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                option[target].backgroundColor = color.tiny.toRgbString();
                commonFn(option);
            }
        });
        option[target].backgroundColor = $("#" + clrpkrId).val();
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        option[target].backgroundColor = currOption[target].backgroundColor;
    }
    commonFn(option);
}

/*[主标题、提示]: 边框线宽 borderWidth*/
function borderWidthCheckChange(el, id, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        option[target].borderWidth = $('#' + id).val();
    } else {
        $('#' + id).attr('disabled', true);
        option[target].borderWidth = currOption[target].borderWidth;
    }
    commonFn(option);
}

function borderWidthChange(el, target) {
    option[target].borderWidth = $(el).val();
    commonFn(option);
}

/*[主标题、提示]: 边框颜色 borderColor*/
function borderColorChange(el, clrpkrId, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                option[target].borderColor = color.tiny.toRgbString();
                commonFn(option);
            }
        });
        option[target].borderColor = $("#" + clrpkrId).val();
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        option[target].borderColor = currOption[target].borderColor;
    }
    commonFn(option);
}

/*[主标题、图例、提示]: 文本颜色 textStyle: color*/
function fontColorChange(el, clrpkrId, target) {
    var _textStyle = option[target].textStyle; //取出当前字体颜色属性值
    if (!_textStyle) _textStyle = {};

    var _currColor = undefined; //获取默认字体大小属性值
    var _currTextStyle = currOption[target].textStyle;
    if (_currTextStyle) _currColor = _currTextStyle['color'];

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                _textStyle['color'] = color.tiny.toRgbString();
                option[target].textStyle = _textStyle;
                commonFn(option);
            }
        });
        _textStyle['color'] = $("#" + clrpkrId).val();
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        _textStyle['color'] = _currColor; //恢复默认设置
    }
    option[target].textStyle = _textStyle;
    commonFn(option);
}

/*[主标题、图例、提示]: 字体大小 textStyle: fontSize*/
function fontSizeCheckChange(el, id, target) {

    /*取出当前字体大小属性值*/
    var _textStyle = option[target].textStyle;
    if (!_textStyle) _textStyle = {};

    /*获取默认字体大小属性值*/
    var _fontSize = undefined;
    var _currTextStyle = currOption[target].textStyle;
    if (_currTextStyle) _fontSize = _currTextStyle['fontSize'];

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        _textStyle['fontSize'] = $('#' + id).val();
    } else {
        $('#' + id).attr('disabled', true);
        _textStyle['fontSize'] = _fontSize;
    }
    option[target].textStyle = _textStyle;
    commonFn(option);
}

function fontSizeChange(el, target) {
    var _textStyle = option[target].textStyle;
    if (!_textStyle) {
        _textStyle = {};
    }
    _textStyle['fontSize'] = $(el).val();
    option[target].textStyle = _textStyle;
    commonFn(option);
}

/*[主标题、图例、提示]: 字体样式 textStyle: fontStyle*/
function fontStyleCheckChange(el, id, target, boldId) {
    var _textStyle = option[target].textStyle;
    if (!_textStyle) _textStyle = {};

    var _currFontStyle = undefined;
    var _currFontWeight = undefined;
    var _currTextStyle = currOption[target].textStyle;
    if (_currTextStyle) {
        _currFontWeight = _currTextStyle['fontWeight'];
        _currFontStyle = _currTextStyle['fontStyle'];
    }

    var obj = $('input[name="' + id + '"]');
    var objBold = $('input[name="' + id + 'Bold"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        objBold.attr('disabled', false);

        var _bold = $('#' + boldId).prop("checked");
        if (_bold) _textStyle['fontWeight'] = 'bold';
        else _textStyle['fontWeight'] = 'normal';

        var val = $('input[name="' + id + '"]:checked').val();
        _textStyle['fontStyle'] = val;
    } else {
        obj.attr('disabled', true);
        objBold.attr('disabled', true);
        _textStyle['fontWeight'] = _currFontWeight;
        _textStyle['fontStyle'] = _currFontStyle;
    }
    option[target].textStyle = _textStyle;
    commonFn(option);
}

function fontStyleChange(el, target) {
    var _textStyle = option[target].textStyle;
    if (!_textStyle) _textStyle = {};
    _textStyle['fontStyle'] = $(el).val();
    option[target].textStyle = _textStyle;
    commonFn(option);
}

function fontStyleBoldChange(el, target) {
    var _textStyle = option[target].textStyle;
    if (!_textStyle) _textStyle = {};

    var _bold = $(el).prop("checked");
    if (_bold) _textStyle['fontWeight'] = 'bold';
    else _textStyle['fontWeight'] = 'normal';

    option[target].textStyle = _textStyle;
    commonFn(option);
}

/*副标题*/

/*文本颜色 subtextStyle: color*/
function subTitleTextCheckChange(el, clrpkrId) {

    var _subtextStyle = option['title'].subtextStyle; //取出当前标题字体颜色属性值
    if (!_subtextStyle) _subtextStyle = {};

    var _currColor = undefined; //获取默认标题字体大小属性值
    var _currSubTextStyle = currOption['title'].subtextStyle;
    if (_currSubTextStyle) _currColor = _currSubTextStyle['color'];

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                var _color = color.tiny.toRgbString();
                _subtextStyle['color'] = _color;
                option['title'].subtextStyle = _subtextStyle;
                commonFn(option);
            }
        });
        _subtextStyle['color'] = $("#" + clrpkrId).val();
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        _subtextStyle['color'] = _currColor;
    }
    option['title'].subtextStyle = _subtextStyle;
    commonFn(option);
}

/*字体大小 subtextStyle: fontSize*/
function subTitleSizeCheckChange(el, id) {

    /*取出当前主标题字体大小属性值*/
    var _subtextStyle = option['title'].subtextStyle;
    if (!_subtextStyle) _subtextStyle = {};

    /*获取默认主标题字体大小属性值*/
    var _fontSize = undefined;
    var _currSubTextStyle = currOption['title'].subtextStyle;
    if (_currSubTextStyle) _fontSize = _currSubTextStyle['fontSize'];

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        _subtextStyle['fontSize'] = $('#' + id).val();
    } else {
        $('#' + id).attr('disabled', true);
        _subtextStyle['fontSize'] = _fontSize;
    }
    option['title'].subtextStyle = _subtextStyle;
    commonFn(option);
}

function subTitleSizeTchPinChange(el) {
    var _subtextStyle = option['title'].subtextStyle;
    if (!_subtextStyle) {
        _subtextStyle = {};
    }
    _subtextStyle['fontSize'] = $(el).val();
    option['title'].subtextStyle = _subtextStyle;
    commonFn(option);
}

/*字体样式 subtextStyle: fontStyle*/
function subTitleStyleCheckChange(el, id) {
    var _subtextStyle = option['title'].subtextStyle;
    if (!_subtextStyle) _subtextStyle = {};

    var _currFontStyle = undefined;
    var _currFontWeight = undefined;
    var _currSubTextStyle = currOption['title'].subtextStyle;
    if (_currSubTextStyle) {
        _currFontWeight = _currSubTextStyle['fontWeight'];
        _currFontStyle = _currSubTextStyle['fontStyle'];
    }

    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);

        var _bold = $('#subTitleStyleBold').prop("checked");
        if (_bold) _subtextStyle['fontWeight'] = 'bold';
        else _subtextStyle['fontWeight'] = 'normal';

        var val = $('input[name="' + id + '"]:checked').val();
        _subtextStyle['fontStyle'] = val;
    } else {
        obj.attr('disabled', true);
        _subtextStyle['fontWeight'] = _currFontWeight;
        _subtextStyle['fontStyle'] = _currFontStyle;
    }
    option['title'].subtextStyle = _subtextStyle;
    commonFn(option);
}

function subTitleStyleChange(el) {
    var _subtextStyle = option['title'].subtextStyle;
    if (!_subtextStyle) _subtextStyle = {};
    _subtextStyle['fontStyle'] = $(el).val();
    option['title'].subtextStyle = _subtextStyle;
    commonFn(option);
}

function subTitleStyleBoldChange(el) {
    var _subtextStyle = option['title'].subtextStyle;
    if (!_subtextStyle) _subtextStyle = {};

    var _bold = $(el).prop("checked");
    if (_bold) _subtextStyle['fontWeight'] = 'bold';
    else _subtextStyle['fontWeight'] = 'normal';

    option['title'].subtextStyle = _subtextStyle;
    commonFn(option);
}

/*--------------图例--------------*/

/*是否显示图例 legend*/
function isShowLegendCheckChange(el, id) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        showLegendFn(val);
    } else {
        obj.attr('disabled', true);
        option['legend'].data = currOption['legend'].data;
        commonFn(option);
    }
}

function showLegendChange(el) {
    showLegendFn($(el).val());
}

function showLegendFn(val) {
    if (val == 'Y') {
        var _data = option['legend'].data;
        if (!_data) //is empty
        {
            _data = [];
            var _seriesName = option['series'];
            if (_seriesName.length != 0) {
                $.each(_seriesName, function (i, v) {
                    _data[i] = v.name;
                });
            }
            option['legend'].data = _data;
        }
    } else if (val == 'N') //不显示图例
    {
        delete option['legend'].data;
    }
    commonFn(option);
}

/*图例选择模式 selectedMode*/
function selectedModeChange(el, id) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        if (val == 'false') option['legend'].selectedMode = false;
        else option['legend'].selectedMode = val;
    } else {
        obj.attr('disabled', true);
        option['legend'].selectedMode = currOption['legend'].selectedMode;
    }
    commonFn(option);
}

function legendChoiceType(el) {
    var val = $(el).val();
    if (val == 'false') option['legend'].selectedMode = false;
    else option['legend'].selectedMode = val;
    commonFn(option);
}

/*[图例、工具]： 水平安放位置 x*/
function putHCheckChange(el, id, target) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        option[target].x = val;
    } else {
        obj.attr('disabled', true);
        option[target].x = currOption[target].x;
    }
    commonFn(option);
}

function putH(el, target) {
    option[target].x = $(el).val();
    commonFn(option);
}

/*[图例、工具]：垂直安放位置 y*/
function putVCheckChange(el, id, target) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        option[target].y = val;
    } else {
        obj.attr('disabled', true);
        option[target].y = currOption[target].y;
    }
    commonFn(option);
}

function putV(el, target) {
    option[target].y = $(el).val();
    commonFn(option);
}

/*[图例、工具]：图例放置方式 orient*/
function putTypeCheckChange(el, id, target) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        option[target].orient = val;
    } else {
        obj.attr('disabled', true);
        option[target].orient = currOption[target].orient;
    }
    commonFn(option);
}

function putType(el, target) {
    option[target].orient = $(el).val();
    commonFn(option);
}

/*--------------提示[tooltip]--------------*/

/*是否使用提示 show*/
function toolCheckChange(el, id, target) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        toolFn(val, target);
    } else {
        obj.attr('disabled', true);
        option[target].show = currOption[target].show;
        commonFn(option);
    }
}

function toolChange(el, target) {
    var val = $(el).val();
    toolFn(val, target);
}

function toolFn(val, target) {
    if (val == 'Y')
        option[target].show = true;
    else if (val == 'N')
        option[target].show = false;
    commonFn(option);
}

/*提示模板 formatter*/
function tooltipFormatterChange(el, id) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + id).attr('disabled', false);
        $("#" + id).blur(function () {
            option['tooltip'].formatter = $(this).val();
            commonFn(option);
        });
        option['tooltip'].formatter = $("#" + id).val();
        commonFn(option);
    } else {
        $("#" + id).attr('disabled', true);
        option['tooltip'].formatter = currOption['tooltip'].formatter;
        commonFn(option);
    }
}

/*文本水平对齐方式 textStyle: align*/
function rmdTextAlignHCheckChange(el, id) {
    var obj = $('input[name="' + id + '"]');

    var _textStyle = option['tooltip'].textStyle;
    if (!_textStyle) _textStyle = {};

    var _align = undefined;
    var _currTextStyle = currOption['tooltip'].textStyle;
    if (_currTextStyle) _align = _currTextStyle['align'];

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        _textStyle['align'] = $('input[name="' + id + '"]:checked').val();
    } else {
        obj.attr('disabled', true);
        _textStyle['align'] = _align;
    }
    option['tooltip'].textStyle = _textStyle;
    commonFn(option);
}

function rmdTextAlignHChange(el) {
    var _textStyle = option['tooltip'].textStyle;
    if (!_textStyle) {
        _textStyle = {};
    }
    _textStyle['align'] = $(el).val();
    option['tooltip'].textStyle = _textStyle;
    commonFn(option);
}

/*提示触发点 trigger*/
function remindTiggerCheckChange(el, id) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        option['tooltip'].trigger = val;
    } else {
        obj.attr('disabled', true);
        option['tooltip'].trigger = currOption['tooltip'].trigger;
    }
    commonFn(option);
}

function remindTiggerChange(el) {
    option['tooltip'].trigger = $(el).val();
    commonFn(option);
}

/*提示指示器类型  axisPointer: type*/
function pointerTypeCheckChange(el, id) {
    var obj = $('input[name="' + id + '"]');

    var _axisPointer = option['tooltip'].axisPointer;
    if (!_axisPointer) _axisPointer = {};

    var _type = undefined;
    var _currAxisPointer = currOption['tooltip'].axisPointer;
    if (_currAxisPointer) _type = _currAxisPointer['type'];

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        _axisPointer['type'] = $('input[name="' + id + '"]:checked').val();
    } else {
        obj.attr('disabled', true);
        _axisPointer['type'] = _type;
    }
    option['tooltip'].axisPointer = _axisPointer;
    commonFn(option);
}

function pointerTypeChange(el) {

    var _axisPointer = option['tooltip'].axisPointer;
    if (!_axisPointer) _axisPointer = {};

    _axisPointer['type'] = $(el).val();
    option['tooltip'].axisPointer = _axisPointer;
    commonFn(option);
}

/*线条颜色 axisPointer[lineStyle]: color*/
function pointerLineColorChange(el, clrpkrId) {

    var _lineStyle = {};
    var _axisPointer = option['tooltip'].axisPointer;
    if (!_axisPointer)
        _axisPointer = {};
    else {
        _lineStyle = _axisPointer['lineStyle'];
        if (!_lineStyle) _lineStyle = {};
    }

    var _color = undefined;
    var _currLineStyle = {};
    var _currAxisPointer = currOption['tooltip'].axisPointer;
    if (_currAxisPointer) {
        _currLineStyle = _currAxisPointer['lineStyle'];
        if (_currLineStyle)
            _color = _currLineStyle['color'];
    }

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {

                var _lineStyle = {};
                var _axisPointer = option['tooltip'].axisPointer;
                if (!_axisPointer)
                    _axisPointer = {};
                else {
                    _lineStyle = _axisPointer['lineStyle'];
                    if (!_lineStyle) _lineStyle = {};
                }

                _lineStyle['color'] = color.tiny.toRgbString();
                _axisPointer['lineStyle'] = _lineStyle;
                option['tooltip'].axisPointer = _axisPointer;
                commonFn(option);
            }
        });
        _lineStyle['color'] = $("#" + clrpkrId).val();
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        _lineStyle['color'] = _color; //恢复默认设置
    }
    _axisPointer['lineStyle'] = _lineStyle;
    option['tooltip'].axisPointer = _axisPointer;
    commonFn(option);
}

/*线条宽度 axisPointer[lineStyle]: width*/
function pointerLineWidthChange(el, id) {

    var _lineStyle = {};
    var _axisPointer = option['tooltip'].axisPointer;
    if (!_axisPointer)
        _axisPointer = {};
    else {
        _lineStyle = _axisPointer['lineStyle'];
        if (!_lineStyle) _lineStyle = {};
    }

    var _width = undefined;
    var _currLineStyle = {};
    var _currAxisPointer = currOption['tooltip'].axisPointer;
    if (_currAxisPointer) {
        _currLineStyle = _currAxisPointer['lineStyle'];
        if (_currLineStyle)
            _width = _currLineStyle['width'];
    }

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + id).attr('disabled', false);
        _lineStyle['width'] = $("#" + id).val();

    } else {
        $("#" + id).attr('disabled', true);
        _lineStyle['width'] = _width; //恢复默认设置
    }
    _axisPointer['lineStyle'] = _lineStyle;
    option['tooltip'].axisPointer = _axisPointer;
    commonFn(option);
}

function pLineWidthChange(el) {
    var _lineStyle = {};
    var _axisPointer = option['tooltip'].axisPointer;
    if (!_axisPointer)
        _axisPointer = {};
    else {
        _lineStyle = _axisPointer['lineStyle'];
        if (!_lineStyle) _lineStyle = {};
    }
    _lineStyle['width'] = $(el).val();
    _axisPointer['lineStyle'] = _lineStyle;
    option['tooltip'].axisPointer = _axisPointer;
    commonFn(option);
}

/*线条类型  axisPointer[lineStyle]: type*/
function pointerLineTypeCheckChange(el, id) {
    var obj = $('input[name="' + id + '"]');

    var _lineStyle = {};
    var _axisPointer = option['tooltip'].axisPointer;
    if (!_axisPointer)
        _axisPointer = {};
    else {
        _lineStyle = _axisPointer['lineStyle'];
        if (!_lineStyle) _lineStyle = {};
    }

    var _type = undefined;
    var _currLineStyle = {};
    var _currAxisPointer = currOption['tooltip'].axisPointer;
    if (_currAxisPointer) {
        _currLineStyle = _currAxisPointer['lineStyle'];
        if (_currLineStyle)
            _type = _currLineStyle['type'];
    }

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        _lineStyle['type'] = $('input[name="' + id + '"]:checked').val();
    } else {
        obj.attr('disabled', true);
        _lineStyle['type'] = _type;
    }
    _axisPointer['lineStyle'] = _lineStyle;
    option['tooltip'].axisPointer = _axisPointer;
    commonFn(option);
}

function pointerLineTypeChange(el) {
    var _lineStyle = {};
    var _axisPointer = option['tooltip'].axisPointer;
    if (!_axisPointer)
        _axisPointer = {};
    else {
        _lineStyle = _axisPointer['lineStyle'];
        if (!_lineStyle) _lineStyle = {};
    }

    _lineStyle['type'] = $(el).val();
    _axisPointer['lineStyle'] = _lineStyle;
    option['tooltip'].axisPointer = _axisPointer;
    commonFn(option);
}

/*--------------工具[tooltbox]--------------*/

/*选择显示的工具 feature*/
function choiceToolCheckChange(el, id) {

    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var _tmpArr = [], _unTmpArr = [];
        var tmpArr = $('input[name="' + id + '"]:checked');
        var unTmpArr = $('input[name="' + id + '"]').not("input:checked");

        $.each(tmpArr, function (i, v) {
            _tmpArr[i] = v.value;
        });
        $.each(unTmpArr, function (i, v) {
            _unTmpArr[i] = v.value;
        });
        getFeature(_tmpArr, _unTmpArr);
    } else {
        obj.attr('disabled', true);
        option['toolbox'].feature = currOption['toolbox'].feature;
        commonFn(option);
    }
}

function choiceToolChange(el) {
    var arr = [];
    arr[0] = $(el).val();

    var isChecked = $(el).prop("checked");
    if (isChecked)
        getFeature(arr, null);
    else
        getFeature(null, arr);
}

function getFeature(_tmpArr, _unTmpArr) {
    var _feature = option['toolbox'].feature;
    if (!_feature) _feature = {};

    var _contentTrue = {show: true};
    var _contentFalse = {show: false};
    $.each(_tmpArr, function (i, v) {
        if (v == 'dataView') {
            _contentTrue = {
                readOnly: true,
                show: true
            }
        } else if (v == 'magicType') {
            _contentTrue = {
                show: true,
                type: ["line", "bar"]
            }
        } else if (v == 'brush') {
            _contentTrue = {
                show: true,
                type: ['lineX', 'lineY', 'clear']
            }
        }
        _feature[v] = _contentTrue;
    });
    $.each(_unTmpArr, function (i, v) {
        _feature[v] = _contentFalse;
    });

    option['toolbox'].feature = _feature;
    commonFn(option);
}

/*--------------视区[grid]--------------*/

/*坐标边缘宽度 grid: x,y,x2,y2*/
function gridPositionCheck(el, id, target) {
    var _grid = option['grid'];
    if (!_grid) _grid = {};

    var _x = undefined; //获取默认字体大小属性值
    var _currGrid = currOption['grid'];
    if (_currGrid) _x = _currGrid[target];

    var isChecked = $(el).prop('checked');
    if (isChecked) {
        $('#' + id).slider("enable");
        $('#' + id).on("slide", function (slideEvt) {
            var value = slideEvt.value;
            $('#' + id + 'Val').text(value);

            var _grid = option['grid'];
            if (!_grid) _grid = {};
            _grid[target] = value;
            option['grid'] = _grid;
            commonFn(option);
        });

        _grid[target] = $('#' + id + 'Val').text();
    } else {
        $("#" + id).slider("disable");
        _grid[target] = _x;
    }

    option['grid'] = _grid;
    commonFn(option);
}

/*绘图区域外观背景色  grid: backgroundColor borderColor*/
function gridColorCheck(el, clrpkrId, target) {
    var _grid = option['grid'];
    if (!_grid) _grid = {show: true};
    else _grid['show'] = true;

    var _tmp = undefined; //获取默认字体大小属性值
    var _currGrid = currOption['grid'];
    if (_currGrid) _tmp = _currGrid[target];

    var isChecked = $(el).prop('checked');
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                var color = color.tiny.toRgbString();
                var _grid = option['grid'];
                if (!_grid) _grid = {show: true};
                _grid[target] = color;
                option['grid'] = _grid;
                commonFn(option);
            }
        });
        _grid[target] = $("#" + clrpkrId).val();
        ;
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        _grid[target] = _tmp;
    }

    option['grid'] = _grid;
    commonFn(option);
}

/*绘图区域外观边框  grid: borderWidth*/
function gridBorderWidthCheck(el, id) {
    var _grid = option['grid'];
    if (!_grid) _grid = {show: true};
    else _grid['show'] = true;

    var _tmp = undefined; //获取默认字体大小属性值
    var _currGrid = currOption['grid'];
    if (_currGrid) _tmp = _currGrid['borderWidth'];

    var isChecked = $(el).prop('checked');
    if (isChecked) {
        $("#" + id).attr('disabled', false);
        _grid['borderWidth'] = $("#" + id).val();
    } else {
        $("#" + id).attr('disabled', true);
        _grid['borderWidth'] = _tmp;
    }

    option['grid'] = _grid;
    commonFn(option);
}

function gridBorderWidthChange(el) {
    option['grid'].borderWidth = $(el).val();
    commonFn(option);
}

/*--------------X轴[xAxis]、Y轴[yAxis]--------------*/

/*更新第一级属性的值-通用方法*/
function updateCurrAxis(axis, target, val) {
    var index = 0; //获取具体属性下标
    if (axis == 'xAxis')
        index = checkedAxis;
    else
        index = checkedyAxis;

    /*给具体属性赋值*/
    var axisArr = option[axis];
    var _axis = axisArr[index];
    _axis[target] = val;

    /*更新图表*/
    axisArr[index] = _axis;
    option[axis] = axisArr;
    commonFn(option);
}

function getDefaultAxisVal(axis, target) {
    var tmp = undefined;
    var currAxisArr = currOption[axis];

    if (axis == 'xAxis')
        var _currAxis = currAxisArr[checkedAxis];
    else
        var _currAxis = currAxisArr[checkedyAxis];

    tmp = _currAxis[target];
    return tmp;
}

/*坐标轴名称  name*/
function axisNameCheckChange(el, id, axis, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + id).attr('disabled', false); //启用输入框
        var defaultVal = $("#" + id).val(); //获取当前默认值
        if (!defaultVal) {
            var tmp = getDefaultAxisVal(axis, target);
            updateCurrAxis(axis, target, tmp);
            if (tmp) $("#" + id).val(tmp);
        } else {
            updateCurrAxis(axis, target, defaultVal);
        }

        $("#" + id).blur(function () { //获取失焦点后的值
            var val = $(this).val();
            updateCurrAxis(axis, target, val);
        });
    } else {
        $("#" + id).attr('disabled', true);
        var val = getDefaultAxisVal(axis, target);
        updateCurrAxis(axis, target, val);
    }
}

/*类目两端空白策略 boundaryGap、坐标轴位置  position、 坐标轴名称位置 nameLocation*/
function axisCheckChange(el, id, axis, target) {
    var val;
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop('checked');
    if (isChecked) {
        obj.attr('disabled', false);
        val = $('input[name="' + id + '"]:checked').val();
        if (val == 'Y') val = true;
        else if (val == 'N') val = false;
    } else {
        obj.attr('disabled', true);
        val = getDefaultAxisVal(axis, target);
    }
    updateCurrAxis(axis, target, val);
}

function axisChange(el, axis, target) {
    var val = $(el).val();
    if (val == 'Y') val = true;
    else if (val == 'N') val = false;
    updateCurrAxis(axis, target, val);
}

/*更新第二级属性的值-通用方法*/
function updateCurrAxisSec(axis, target, target1, val) {
    var index = 0; //获得具体的属性下标
    if (axis == 'xAxis')
        index = checkedAxis;
    else
        index = checkedyAxis;

    /*给对应属性赋值*/
    var axisArr = option[axis];
    var _axis = axisArr[index];
    var _targetAxis = _axis[target];
    if (!_targetAxis) _targetAxis = {show: true};

    _targetAxis[target1] = val;

    /*更新图表*/
    _axis[target] = _targetAxis
    axisArr[index] = _axis;
    option[axis] = axisArr;
    commonFn(option);
}

function getDefaultAxisValSec(axis, target, target1) {
    var tmp = undefined;
    var currAxisArr = currOption[axis];

    if (axis == 'xAxis')
        var _currAxis = currAxisArr[checkedAxis];
    else
        var _currAxis = currAxisArr[checkedyAxis];

    var _targetcurrAxis = _currAxis[target];
    if (_targetcurrAxis) tmp = _targetcurrAxis[target1];
    return tmp;
}

/*是否显示隔区 splitArea、是否显示轴线 axisLine、是否显示文本标签 axisLabel*/
function axisShowCheckChange(el, id, axis, target, target1) {
    var val;
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop('checked');
    if (isChecked) {
        obj.attr('disabled', false);
        val = $('input[name="' + id + '"]:checked').val();
        if (val == 'Y') val = true;
        else val = false;
    } else {
        obj.attr('disabled', true);
        val = getDefaultAxisValSec(axis, target, target1);
    }
    updateCurrAxisSec(axis, target, target1, val);
}

function axisShowChange(el, axis, target, target1) {
    var val = $(el).val();
    if (val == 'Y') val = true;
    else val = false;
    updateCurrAxisSec(axis, target, target1, val);
}

/*坐标轴文本颜色 nameTextStyle: color*/
function axisNameColorChange(el, clrpkrId, axis, target, target1) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                let val = color.tiny.toRgbString();
                updateCurrAxisSec(axis, target, target1, val);
            }
        });
        val = $("#" + clrpkrId).val();
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        val = getDefaultAxisValSec(axis, target, target1);
    }
    updateCurrAxisSec(axis, target, target1, val);
}

/*坐标轴文字大小 fontSize*/
function axisFontSizeCheckChange(el, id, axis, target, target1) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        val = $('#' + id).val();
    } else {
        $('#' + id).attr('disabled', true);
        val = getDefaultAxisValSec(axis, target, target1);
    }
    updateCurrAxisSec(axis, target, target1, val);
}

function axisFontSizeChange(el, axis, target, target1) {
    var val = $(el).val();
    updateCurrAxisSec(axis, target, target1, val);
}

/*坐标轴字体样式 fontStyle、fontWeight*/
function axisNameStyleCheckChange(el, id, axis, target, target1, boldId) {
    var obj = $('input[name="' + id + '"]');
    var objBold = $('input[name="' + id + 'Bold"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        objBold.attr('disabled', false);
        var _bold = $('#' + boldId).prop("checked");
        if (_bold) updateCurrAxisSec(axis, target, 'fontWeight', 'bold');
        else updateCurrAxisSec(axis, target, 'fontWeight', 'normal');

        var val = $('input[name="' + id + '"]:checked').val();
        updateCurrAxisSec(axis, target, target1, val);
    } else {
        obj.attr('disabled', true);
        objBold.attr('disabled', true);
        var val1 = getDefaultAxisValSec(axis, target, 'fontWeight');
        updateCurrAxisSec(axis, target, 'fontWeight', val1);

        var val2 = getDefaultAxisValSec(axis, target, target1);
        updateCurrAxisSec(axis, target, target1, val2);
    }
}

function axisNameStyleChange(el, axis, target, target1) {
    var val = $(el).val();
    updateCurrAxisSec(axis, target, target1, val);
}

function axisNameStyleBoldChange(el, axis, target) {
    var _bold = $(el).prop("checked");
    if (_bold) updateCurrAxisSec(axis, target, 'fontWeight', 'bold');
    else updateCurrAxisSec(axis, target, 'fontWeight', 'normal');
}

/*标签内容模板 formatter*/
function axisLabelFormatterChange(el, id, axis, target, target1) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + id).attr('disabled', false);
        $("#" + id).blur(function () {
            let val = $(this).val();
            updateCurrAxisSec(axis, target, target1, val);
        });

        let val = $("#" + id).val();
        updateCurrAxisSec(axis, target, target1, val);
    } else {
        $("#" + id).attr('disabled', true);
        let val = getDefaultAxisValSec(axis, target, target1);
        updateCurrAxisSec(axis, target, target1, val);
    }
}

/*文本标签旋转角度、文本标签与坐标轴间距*/
function axisSliderCheckChange(el, id, axis, target, target1) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).slider("enable");

        $('#' + id).on("slide", function (slideEvt) {
            let val = slideEvt.value;
            $('#' + id + 'Val').text(val);
            updateCurrAxisSec(axis, target, target1, val);
        });

        let val = $('#' + id + 'Val').text();
        updateCurrAxisSec(axis, target, target1, val);
    } else {
        $('#' + id).slider("disable");
        let val = getDefaultAxisValSec(axis, target, target1);
        updateCurrAxisSec(axis, target, target1, val);
    }
}

/*更新第三级属性的值-通用方法*/
function updateCurrAxisThird(axis, target, target1, target2, val) {
    var index = 0; //获取当前具体的轴
    if (axis == 'xAxis')
        index = checkedAxis;
    else
        index = checkedyAxis;

    /*取出对应属性赋值*/
    var axisArr = option[axis];
    var _axis = axisArr[index];
    var _targetAxis = _axis[target]; //axisLine
    if (!_targetAxis) _targetAxis = {show: true};

    var _style = _targetAxis[target1]; //lineStyle
    if (!_style) _style = {};

    _style[target2] = val; //color

    /*更新图表*/
    _targetAxis[target1] = _style;
    _axis[target] = _targetAxis
    axisArr[index] = _axis;
    option[axis] = axisArr;
    commonFn(option);
}

function getDefaultAxisValThird(axis, target, target1, target2) {
    var tmp = undefined;
    var currAxisArr = currOption[axis];

    if (axis == 'xAxis')
        var _currAxis = currAxisArr[checkedAxis];
    else
        var _currAxis = currAxisArr[checkedyAxis];

    var _targetcurrAxis = _currAxis[target];
    if (_targetcurrAxis) {
        var _currStyle = _targetcurrAxis[target1];
        if (_currStyle) tmp = _currStyle[target2];
    }
    return tmp;
}

/*坐标轴线颜色 axisLine: lineStyle: color、标签文字颜色、隔线颜色*/
function axisColorChange(el, clrpkrId, axis, target, target1, target2) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                let val = color.tiny.toRgbString();
                updateCurrAxisThird(axis, target, target1, target2, val);
            }
        });
        val = $("#" + clrpkrId).val();
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        val = getDefaultAxisValThird(axis, target, target1, target2);
    }
    updateCurrAxisThird(axis, target, target1, target2, val);
}

/*轴线宽度 width、标签字体大小、隔线线宽*/
function axisPXCheckChange(el, id, axis, target, target1, target2) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        val = $('#' + id).val();
    } else {
        $('#' + id).attr('disabled', true);
        val = getDefaultAxisValThird(axis, target, target1, target2);
    }
    updateCurrAxisThird(axis, target, target1, target2, val);
}

function axisPXChange(el, axis, target, target1, target2) {
    var val = $(el).val();
    updateCurrAxisThird(axis, target, target1, target2, val);
}

/*轴线线条类型、隔线线条类型*/
function axisLineTypeCheckChange(el, id, axis, target, target1, target2) {
    var val;
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop('checked');
    if (isChecked) {
        obj.attr('disabled', false);
        val = $('input[name="' + id + '"]:checked').val();
    } else {
        obj.attr('disabled', true);
        val = getDefaultAxisValThird(axis, target, target1, target2);
    }
    updateCurrAxisThird(axis, target, target1, target2, val);
}

function axisLineTypeChange(el, axis, target, target1, target2) {
    var val = $(el).val();
    updateCurrAxisThird(axis, target, target1, target2, val);
}

/*轴标签字体样式 fontStyle、fontWeight*/
function axisLabelCheckChange(el, id, axis, target, target1, target2, boldId) {
    var obj = $('input[name="' + id + '"]');
    var objBold = $('input[name="' + id + 'Bold"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        objBold.attr('disabled', false);
        var _bold = $('#' + boldId).prop("checked");
        if (_bold) updateCurrAxisThird(axis, target, target1, 'fontWeight', 'bold');
        else updateCurrAxisThird(axis, target, target1, 'fontWeight', 'normal');

        var val = $('input[name="' + id + '"]:checked').val();
        updateCurrAxisThird(axis, target, target1, target2, val);
    } else {
        obj.attr('disabled', true);
        objBold.attr('disabled', true);
        var val1 = getDefaultAxisValThird(axis, target, target1, 'fontWeight');
        updateCurrAxisThird(axis, target, target1, 'fontWeight', val1);

        var val2 = getDefaultAxisValThird(axis, target, target1, target2);
        updateCurrAxisThird(axis, target, target1, target2, val2);
    }
}

function axisLabelChange(el, axis, target, target1, target2) {
    var val = $(el).val();
    updateCurrAxisThird(axis, target, target1, target2, val);
}

function axisLabelBoldChange(el, axis, target, target1) {
    var _bold = $(el).prop("checked");
    if (_bold) updateCurrAxisThird(axis, target, target1, 'fontWeight', 'bold');
    else updateCurrAxisThird(axis, target, target1, 'fontWeight', 'normal');
}


/*--------------雷达[radar]--------------*/

/*更新第一级属性的值-通用方法*/
function updateCurrRadar(target, val) {
    /*给具体属性赋值*/
    var _radar = option['radar'][0];
    _radar[target] = val;

    /*更新图表*/
    option['radar'][0] = _radar;
    commonFn(option);
}

function getDefaultRadarVal(target) {
    var tmp = undefined;
    var _currRadar = currOption['radar'][0];
    tmp = _currRadar[target];
    return tmp;
}

/*底盘形状：shape*/
function shapeCheckChange(el, id, target) {
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        var val = $('input[name="' + id + '"]:checked').val();
        updateCurrRadar(target, val);
    } else {
        obj.attr('disabled', true);
        var val = getDefaultRadarVal(target);
        updateCurrRadar(target, val);
    }
}

function shapeChange(el, target) {
    var val = $(el).val();
    updateCurrRadar(target, val);
}

/*圆心位置：center*/
function radarCenterChange(el, idx, idy) {
    var _radar = option['radar'][0];
    var _centerArr = _radar['center'];
    if (!_centerArr) _centerArr = [];

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + idx).slider("enable");
        $('#' + idy).slider("enable");

        $('#' + idx).on("slide", function (slideEvt) {
            let val = slideEvt.value;
            $('#' + idx + 'Val').text(val);

            var _radar = option['radar'][0];
            var _centerArr = _radar['center'];
            if (!_centerArr) _centerArr = [];
            _centerArr[0] = val + '%';
            _radar['center'] = _centerArr;
            option['radar'][0] = _radar;
            commonFn(option);
        });

        $('#' + idy).on("slide", function (slideEvt) {
            let valy = slideEvt.value;
            $('#' + idy + 'Val').text(valy);

            var _radar = option['radar'][0];
            var _centerArr = _radar['center'];
            if (!_centerArr) _centerArr = [];
            _centerArr[1] = valy + '%';
            _radar['center'] = _centerArr;
            option['radar'][0] = _radar;
            commonFn(option);
        });

        let valx = $('#' + idx + 'Val').text();
        let valy = $('#' + idy + 'Val').text();
        _centerArr[0] = valx + '%';
        _centerArr[1] = valy + '%';
        _radar['center'] = _centerArr;
    } else {
        $('#' + idx).slider("disable");
        $('#' + idy).slider("disable");

        var _currRadar = currOption['radar'][0];
        _radar['center'] = _currRadar['center'];
    }

    option['radar'][0] = _radar;
    commonFn(option);
}

/*半径长度：radius、起始角度 startAngle*/
function radarFirstChange(el, id, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).slider("enable");
        $('#' + id).on("slide", function (slideEvt) {
            let val = slideEvt.value;
            $('#' + id + 'Val').text(val);
            if (target == 'radius')
                val = val + '%';
            updateCurrRadar(target, val);
        });

        let val = $('#' + id + 'Val').text();
        if (target == 'radius')
            val = val + '%';
        updateCurrRadar(target, val);
    } else {
        $('#' + id).slider("disable");
        var val = getDefaultRadarVal(target);
        updateCurrRadar(target, val);
    }
}

/*环形分割数: splitNumber*/
function radarSplitNumberCheckChange(el, id, target) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        var val = $('#' + id).val();
        updateCurrRadar(target, val);
    } else {
        $('#' + id).attr('disabled', true);
        var val = getDefaultRadarVal(target);
        updateCurrRadar(target, val);
    }
}

function radarSplitNumberChange(el, target) {
    var val = $(el).val();
    updateCurrRadar(target, val);
}

/*更新第二级属性的值-通用方法*/
function updateCurrRadarSec(target, target1, val) {

    /*给对应属性赋值*/
    var _radar = option['radar'][0];
    var _targetRadar = _radar[target];
    if (!_targetRadar) _targetRadar = {show: true};

    _targetRadar[target1] = val;

    /*更新图表*/
    _radar[target] = _targetRadar
    option['radar'][0] = _radar;
    commonFn(option);
}

function getDefaultRadarValSec(target, target1) {
    var tmp = undefined;
    var _currRadar = currOption['radar'][0];
    var _targetcurrRadar = _currRadar[target];
    if (_targetcurrRadar) tmp = _targetcurrRadar[target1];
    return tmp;
}

/*是否显示隔区 splitArea、是否显示轴线 axisLine、是否显示文本标签 axisLabel、是否显示隔线 splitLine*/
function radarShowCheckChange(el, id, target, target1) {
    var val;
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop('checked');
    if (isChecked) {
        obj.attr('disabled', false);
        val = $('input[name="' + id + '"]:checked').val();
        if (val == 'Y') val = true;
        else val = false;
    } else {
        obj.attr('disabled', true);
        val = getDefaultRadarValSec(target, target1);
    }
    updateCurrRadarSec(target, target1, val);
}

function radarShowChange(el, target, target1) {
    var val = $(el).val();
    if (val == 'Y') val = true;
    else val = false;
    updateCurrRadarSec(target, target1, val);
}


/*更新第三级属性的值-通用方法
 * target：一级属性。如：axisLine
 * target1：二级属性。如：lineStyle
 * target2： 三级属性。如：color*/
function updateCurrRadarThird(target, target1, target2, val) {

    var _radar = option['radar'][0];
    var _targetRadar = _radar[target];
    if (!_targetRadar) _targetRadar = {show: true};
    var _style = _targetRadar[target1];

    _style[target2] = val;

    /*更新图表*/
    _targetRadar[target1] = _style;
    _radar[target] = _targetRadar
    option['radar'][0] = _radar;
    commonFn(option);
}

function getDefaultRadarValThird(axis, target, target1, target2) {
    var tmp = undefined;
    var _currRadar = currOption['radar'][0];
    var _targetcurrRadar = _currRadar[target];
    if (_targetcurrRadar) {
        var _currStyle = _targetcurrRadar[target1];
        if (_currStyle) tmp = _currStyle[target2];
    }
    return tmp;
}

/*坐标轴线颜色 axisLine、网格线颜色 splitLine*/
function radarColorChange(el, clrpkrId, target, target1, target2) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                let val = color.tiny.toRgbString();
                updateCurrRadarThird(target, target1, target2, val);
            }
        });
        val = $("#" + clrpkrId).val();
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        val = getDefaultRadarValThird(target, target1, target2);
    }
    updateCurrRadarThird(target, target1, target2, val);
}

/*轴线宽度 width、标签字体大小、隔线线宽*/
function radarPXCheckChange(el, id, target, target1, target2) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        val = $('#' + id).val();
    } else {
        $('#' + id).attr('disabled', true);
        val = getDefaultRadarValThird(target, target1, target2);
    }
    updateCurrRadarThird(target, target1, target2, val);
}

/*轴线线条类型 axisLine.type、网格线条类型 splitLine.type*/
function radarLineTypeCheckChange(el, id, target, target1, target2) {
    var val;
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop('checked');
    if (isChecked) {
        obj.attr('disabled', false);
        val = $('input[name="' + id + '"]:checked').val();
    } else {
        obj.attr('disabled', true);
        val = getDefaultRadarValThird(target, target1, target2);
    }
    updateCurrRadarThird(target, target1, target2, val);
}

function radarChange(el, target, target1, target2) {
    var val = $(el).val();
    updateCurrRadarThird(target, target1, target2, val);
}

/*--------------油量图[系列]--------------*/

/*值域范围 min max*/
function gaugeRangeCheckChange(el, id, min, max) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).slider("enable");
        $('#' + id).on("slide", function (slideEvt) {
            let val = slideEvt.value;
            $('#' + id + 'Val').text(val);
            updateCurrSeries(min, val[0]);
            updateCurrSeries(max, val[1]);
        });

        let val = $('#' + id + 'Val').text();
        var tmpArr = val.split(',');
        updateCurrSeries(min, tmpArr[0]);
        updateCurrSeries(max, tmpArr[1]);
    } else {
        $('#' + id).slider("disable");
        var val = getDefaultSeriesVal(min);
        updateCurrSeries(min, val);

        var val = getDefaultSeriesVal(max);
        updateCurrSeries(max, val);
    }
}


/*油量图颜色: axisLine: lineStyle: color*/
function gaugeColorChange(el, inputId, clrpkrId, target, target1, target2) {
    var _color = [];
    var arrayDiv = $('#gaugeColorDiv').children('div .gauge-color-fg');
    var array = [];
    for (var i = 0; i < arrayDiv.length; i++) {
        array[i] = i;
    }

    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#addGaugeColor').attr('disabled', false);
        $('#delGaugeColor').attr('disabled', false);

        $.each(array, function (i, v) {
            var inputObj = $("#" + inputId + i);
            var clrObj = $("#" + clrpkrId + i);

            inputObj.attr('disabled', false);
            clrObj.attr('disabled', false);

            let num = inputObj.val();
            let rgb = clrObj.css('background-color');
            _color[i] = [num, rgb];

            inputObj.blur(function () { //初始化失焦事件
                let num = $(this).val();
                let rgb = clrObj.css('background-color');
                _color[i] = [num, rgb];
                updateCurrSeriesThird(target, target1, target2, _color);
            });

            clrObj.ColorPickerSliders({ //初始化颜色选择器
                size: 'sm',
                placement: 'right',
                sliders: false,
                swatches: false,
                hsvpanel: true,
                onchange: function (container, color) {
                    let num = inputObj.val();
                    let rgb = color.tiny.toRgbString();
                    _color[i] = [num, rgb];
                    clrObj.css('background-color', rgb);
                    updateCurrSeriesThird(target, target1, target2, _color);
                }
            });
        });
    } else {
        $('#addGaugeColor').attr('disabled', true);
        $('#delGaugeColor').attr('disabled', true);
        $.each(array, function (i, v) {
            $("#" + clrpkrId + i).attr('disabled', true);
            $('#' + inputId + i).attr('disabled', true);
        });
        _color = getDefaultSeriesValThird(target, target1, target2);
    }
    updateCurrSeriesThird(target, target1, target2, _color);
}


/*添加油量图颜色*/
function addGaugeColor() {
    var array = $('#gaugeColorDiv').children('div .gauge-color-fg');
    var idIndex = array.length;

    var html = '<div class="form-group gauge-color-fg">';
    html += '<div class="col-xs-7 col-xs-offset-5 gauge-color-css">';
    html += '<div class="col-xs-8">';
    html += '<input type="text" class="form-control" id="gaugeColorVal' + idIndex + '" value="1">';
    html += '</div><div class="col-xs-4">';
    html += '<div id="gaugeColorClrpkr' + idIndex + '" class="gauge-color-div" style="background-color: rgb(255, 139, 34)"></div>';
    html += '</div></div></div>';

    $('#gaugeColorDiv').append(html);

    var tmp = [];
    var _currSeries = option['series'][checkedSeries];
    if (_currSeries) {
        var _targetcurrSeries = _currSeries['axisLine'];
        if (_targetcurrSeries) {
            var _currStyle = _targetcurrSeries['lineStyle'];
            if (_currStyle) tmp = _currStyle['color'];
        }
    }

    $('#gaugeColorVal' + idIndex).blur(function () { //初始化失焦事件
        var num = $(this).val();
        let rgb = $('#gaugeColorClrpkr' + idIndex).css('background-color');
        tmp[idIndex] = [num, rgb];
        updateCurrSeriesThird('axisLine', 'lineStyle', 'color', tmp);
    });

    $('#gaugeColorClrpkr' + idIndex).ColorPickerSliders({ //初始化颜色选择器
        size: 'sm',
        placement: 'right',
        sliders: false,
        swatches: false,
        hsvpanel: true,
        onchange: function (container, color) {
            let num = $('#gaugeColorVal' + idIndex).val();
            var rgb = color.tiny.toRgbString();
            tmp[idIndex] = [num, rgb];
            updateCurrSeriesThird('axisLine', 'lineStyle', 'color', tmp);
        }
    });
}

/*添加油量图颜色*/
function delGaugeColor() {
    var array = $('#gaugeColorDiv').children('div .gauge-color-fg');
    var index = array.length - 1;

    var lastDiv = array[index];

    var tmp = undefined;
    var _currSeries = option['series'][checkedSeries];
    if (_currSeries) {
        var _targetcurrSeries = _currSeries['axisLine'];
        if (_targetcurrSeries) {
            var _currStyle = _targetcurrSeries['lineStyle'];
            if (_currStyle) tmp = _currStyle['color'];
        }
    }

    tmp.splice(index, 1);
    updateCurrSeriesThird('axisLine', 'lineStyle', 'color', tmp);

    $(lastDiv).remove();
}

/*添加油量图颜色*/
function gaugeShowCheckChange(el, id, target, target1) {
    var val;
    var obj = $('input[name="' + id + '"]');
    var isChecked = $(el).prop('checked');
    if (isChecked) {
        obj.attr('disabled', false);
        val = $('input[name="' + id + '"]:checked').val();
        if (val == 'Y') val = true;
        else val = false;
    } else {
        obj.attr('disabled', true);
        val = getDefaultSeriesValSec(target, target1);
    }
    updateCurrSeriesSec(target, target1, val);
}

function gaugeShowChange(el, target, target1) {
    var val = $(el).val();
    if (val == 'Y') val = true;
    else val = false;
    updateCurrSeriesSec(target, target1, val);
}

/*油量图宽度及长度设置*/
function seriesGaugeCheckChange(el, id, target, target1) {
    var val;
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $('#' + id).attr('disabled', false);
        val = $('#' + id).val();
        if (target == 'pointer' && target1 == 'length')
            val = val + '%';
    } else {
        $('#' + id).attr('disabled', true);
        val = getDefaultSeriesValSec(target, target1);
    }
    updateCurrSeriesSec(target, target1, val);
}

function seriesGaugeChange(el, target, target1) {
    var val = $(el).val();
    if (target == 'pointer' && target1 == 'length')
        val = val + '%';
    updateCurrSeriesSec(target, target1, val);
}

/*油量图字体样式*/
function gaugeStyleCheckChange(el, id, target, target1, target2, boldId) {
    var obj = $('input[name="' + id + '"]');
    var objBold = $('input[name="' + id + 'Bold"]');
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        obj.attr('disabled', false);
        objBold.attr('disabled', false);

        var isChecked = $('#' + boldId).prop("checked");
        if (isChecked) updateCurrSeriesThird(target, target1, 'fontWeight', 'bold');
        else updateCurrSeriesThird(target, target1, 'fontWeight', 'normal');

        var val = $('input[name="' + id + '"]:checked').val();
        updateCurrSeriesThird(target, target1, target2, val);
    } else {
        obj.attr('disabled', true);
        objBold.attr('disabled', true);
        var val1 = getDefaultSeriesValThird(target, target1, 'fontWeight');
        updateCurrSeriesThird(target, target1, 'fontWeight', val);

        var val2 = getDefaultSeriesValThird(target, target1, target2);
        updateCurrSeriesThird(target, target1, target2, val2);
    }
}

function gaugeStyleChange(el, target, target1, target2) {
    var val = $(el).val();
    updateCurrSeriesThird(target, target1, target2, val);
}

function gaugeStyleBoldChange(el, target, target1, target2) {
    var isChecked = $(el).prop("checked");
    if (isChecked) updateCurrSeriesThird(target, target1, target2, 'bold');
    else updateCurrSeriesThird(target, target1, target2, 'normal');
}

/*油量图标签内容模板 formatter*/
function gaugeFormatterChange(el, id, target, target1) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + id).attr('disabled', false);
        $("#" + id).blur(function () {
            let val = $(this).val();
            updateCurrSeriesSec(target, target1, val);
        });

        let val = $("#" + id).val();
        updateCurrSeriesSec(target, target1, val);
    } else {
        $("#" + id).attr('disabled', true);
        let val = getDefaultSeriesValSec(target, target1);
        updateCurrSeriesSec(target, target1, val);
    }
}

/*油量图数据描述颜色调整*/
function seriesGaugeColorChange(el, clrpkrId, target, target1) {
    var isChecked = $(el).prop("checked");
    if (isChecked) {
        $("#" + clrpkrId).attr('disabled', false);
        $("#" + clrpkrId).ColorPickerSliders({ //初始化颜色选择器
            size: 'sm',
            placement: 'right',
            sliders: false,
            swatches: false,
            hsvpanel: true,
            onchange: function (container, color) {
                let val = color.tiny.toRgbString();
                updateCurrSeriesSec(target, target1, val);
            }
        });
        var val = $("#" + clrpkrId).val();
        updateCurrSeriesSec(target, target1, val);
    } else {
        $("#" + clrpkrId).attr('disabled', true);
        var val = getDefaultSeriesValSec(target, target1);
        updateCurrSeriesSec(target, target1, val);
    }
}
