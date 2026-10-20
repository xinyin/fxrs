/**
 * 风险地图组件
 * 提供风险地图的显示、编辑和显示风险事件的功能。
 *
 * 风险事件发生频率数据结构：
 * [
 *        {
 * 			name: '极为常见',
 * 			value: '5'
 * 		},
 *        {
 * 			name: '很常见',
 * 			value: '4'
 * 		}
 * ]
 *
 *
 * 风险事件影响数据结构：
 *
 * [
 *        {
 * 			name: '5(极高)',
 * 			value: '5'
 * 		},
 *        {
 * 			name: '4(高)',
 * 			value: '4'
 * 		}
 *        ...
 * ]
 *
 *
 * 风险事件严重程度数据结构：
 *
 * [
 *        {
 * 			name: '5(极高)',
 * 			value: '5'
 * 		},
 *        {
 * 			name: '4(高)',
 * 			value: '4'
 * 		}
 *        ...
 * ]
 *
 * 颜色配置的数据结构：
 *
 * [
 *        {
 * 			code: '12',
 * 			name: '绿',
 * 			color: '#00FF00'
 * 		},
 *        {
 * 			code: '14',
 * 			name: '灰',
 * 			color: '#939393'
 * 		}
 *        ...
 * ]
 *
 *
 * 最终配置数据结构:
 * [
 *        {
 * 			freq: '1',
 * 			effect: '1',
 * 			severity: '1',
 * 			color: '12',
 * 		}
 *        ...
 * ]
 *
 */


;(function ($) {

    /**
     * 用于在某个对象上存取 option
     */
    function _options(target) {
        var data = $.data(target, 'riskMapEditor');
        if (data) {
            return data.options;
        }
        return undefined;
    }

    /**
     * 初始化方法
     */
    function _init(target) {
        $(target).addClass('riskMapEditor');

        _redraw(target);
    }

    /**
     * 重绘
     */
    function _redraw(target) {
        _clear(target);
        var opts = _options(target);
        var freqData = opts.freqData;
        var effectData = opts.effectData;
        var severityData = opts.severityData;
        var colorData = opts.colorData;
        var configData = opts.configData;

        if (!freqData || !effectData || !severityData || !colorData) {
            $('<div class="nodata">缺失数据。</div>').appendTo(target);
            return;
        }

        /*计算整个DIV 所需的宽度与高度*/
        var w = opts.cellWidth * (effectData.length + 1) + 20;
        var h = opts.cellHeight * (freqData.length + 1) + 20 + 20 + 20;
        $(target).width(w);
        $(target).height(h);


        /*创建table*/
        var mt = $('<table cellspacing="0" cellpadding="0"></table>').appendTo(target);
        var mb = $('<tbody></tbody>').appendTo(mt);

        /*顶层TR*/
        var ttr = $('<tr></tr>').appendTo(mb);

        /*显示标题行*/
        if (opts.showLegend) {
            /*顶层TR中用来给图例预留的TD*/
            var _t = $('<td></td>').appendTo(ttr);
            _t.width(opts.cellWidth + 20);
        }

        var ttd = $('<td class="title" style="width:' + opts.cellWidth * effectData.length + 'px;"></td>').appendTo(ttr);
        /*定义了点击标题的事件*/
        if (opts.onTitleClick != undefined) {
            var ta = $('<a href="javascript:void(0);">' + opts.title + '</a>').appendTo(ttd);
            ta.click(function (event) {
                //TODO 参数未定义
                opts.onTitleClick.call(target, event);
            });
        } else {
            ttd.text(opts.title);
        }
        /*显示标题行*/


        /*中层TR，包括Y轴图例与地图*/
        var ctr = $('<tr></tr>').appendTo(mb);


        /*显示Y轴图例，包括标题与坐标轴*/
        if (opts.showLegend) {
            var yttd = $('<td style="width:' + (opts.cellWidth + 20) + 'px;height:' + (freqData.length * opts.cellHeight) + '"></td>').appendTo(ctr);
            var yttable = $('<table class="axisy" cellspacing="0" cellpadding="0"></table>').appendTo(yttd);
            var ytbody = $('<tbody></tbody').appendTo(yttable);

            /*显示y轴标题*/
            var yftr = $('<tr></tr>').appendTo(ytbody);
            $('<td class="legend">' + opts.axisyTitle + '</td>').appendTo(yftr);

            /*显示y轴图例*/
            var yftd = $('<td class="title" style="width:' + opts.cellWidth + 'px;"></td>').appendTo(yftr);
            var _table = $('<table class="ytable" cellspacing="0" cellpadding="0"></table>').appendTo(yftd);
            var _tbody = $('<tbody></tbody').appendTo(_table);
            for (var i = 0; i < freqData.length; i++) {
                var _tr = $('<tr></tr>').appendTo(_tbody);
                var _td = $('<td class="ytd" style="width:' + opts.cellWidth + ';height:' + opts.cellHeight + '"></td>').appendTo(_tr);
                _td.width(opts.cellWidth);
                _td.height(opts.cellHeight);
                if (opts.axisyFormatter) {
                    _td.html(opts.axisyFormatter(freqData[i]));
                } else {
                    _td.text(freqData[i].value);
                }
            }
        }
        /*显示Y轴图例，包括标题与坐标轴*/


        /*显示风险地图*/
        var ctd = $('<td class="map"></td>').appendTo(ctr);
        ctd.width(effectData.length * opts.cellWidth);
        ctd.height(freqData.length * opts.cellHeight);

        var mtable = $('<table cellspacing="0" cellpadding="0"></table>').appendTo(ctd);
        var mbody = $('<tbody></tbody>').appendTo(mtable);

        for (var i = 0; i < freqData.length; i++) {
            var mtr = $('<tr></tr>').appendTo(mbody);
            for (var j = effectData.length - 1; j >= 0; j--) {
                var mtd = $('<td></td>').appendTo(mtr);
                mtd.width(opts.cellWidth);
                mtd.height(opts.cellHeight);

                var freq = freqData[i];
                var effect = effectData[j];
                var color = _getColor(configData, colorData, freq, effect);
                var severity = _getSeverity(configData, severityData, freq, effect);

                mtd.css('background', color.color);
                if (opts.mapFormatter) {
                    mtd.html(opts.mapFormatter(freq, effect, severity));
                } else {
                    mtd.text(severity.name);
                }
            }
        }

        /*显示风险地图*/

        /*底层TR，包括X轴图例*/
        var btr = $('<tr></tr>').appendTo(mb);


        /*显示X轴图例，包括标题与坐标轴*/
        if (opts.showLegend) {
            var xttd = $('<td></td>').appendTo(btr);
            xttd.width(opts.cellWidth + 20);
            xttd.height(opts.cellHeight);

            var xttable = $('<table class="axisx" cellspacing="0" cellpadding="0"></table>').appendTo(xttd);
            var xtbody = $('<tbody></tbody').appendTo(xttable);

            /*显示x轴与y轴的交叉内容*/
            var _hh = 23;
            if ($.browser.msie && $.browser.version == '6.0') {
                _hh = 20;
            }
            var xttr = $('<tr></tr>').appendTo(xtbody);
            $('<td style="width:' + _hh + 'px;"></td>').appendTo(xttr);
            $('<td style="width:' + (opts.cellWidth) + 'px;height:' + opts.cellHeight + 'px;border: solid 1px #000000;border-top:none;font-weight: bold;text-align:center;vertical-align:center;">0<br>(不适用)</td>').appendTo(xttr);


            var xftd = $('<td></td>').appendTo(btr);
            xftd.width(effectData.length * opts.cellWidth);
            xftd.height(opts.cellHeight);


            /*显示y轴图例*/
            var _table = $('<table cellspacing="0" cellpadding="0" style="border:none"></table>').appendTo(xftd);
            var _tbody = $('<tbody></tbody').appendTo(_table);
            var _tr = $('<tr></tr>').appendTo(_tbody);
            for (var i = effectData.length - 1; i >= 0; i--) {
                var _td = $('<td style="border:solid 1px #000000;border-top:none;border-left:none;font-weight:bold;text-align:center;"></td>').appendTo(_tr);
                _td.width(opts.cellWidth);
                _td.height(opts.cellHeight);
                if (opts.axisxFormatter) {
                    _td.html(opts.axisxFormatter(effectData[i]));
                } else {
                    _td.text(effectData[i].value);
                }
            }
        }
        /*显示X轴图例，包括标题与坐标轴*/


        /*最底层TR，显示Y轴legend*/
        if (opts.showLegend) {
            var tbtr = $('<tr></tr>').appendTo(mb);
            var tttd = $('<td colspan="2" style="font-size:12px;font-weight: bold;text-align:center;"></td>').appendTo(tbtr);
            if (opts.axisxTitle) {
                tttd.text(opts.axisxTitle);
            }
        }

    }


    function _getColor(config, color, freq, effect) {
        var colorCode;
        for (var i = 0; i < config.length; i++) {
            if (config[i].freq == freq.value && config[i].effect == effect.value) {
                colorCode = config[i].color;
            }
        }
        if (colorCode) {
            for (var i = 0; i < color.length; i++) {
                if (colorCode == color[i].code) return color[i];
            }
        }
    }

    function _getSeverity(config, severityData, freq, effect) {
        var sv;
        for (var i = 0; i < config.length; i++) {
            if (config[i].freq == freq.value && config[i].effect == effect.value) {
                sv = config[i].severity;
            }
        }
        if (sv) {
            for (var i = 0; i < severityData.length; i++) {
                if (sv == severityData[i].value) return severityData[i];
            }
        }
    }


    /**
     * 清空地图
     */
    function _clear(target) {
        $(target).empty();
    }

    /**
     * 销毁一个riskMapEditor对象
     */
    function _destroy(target) {
        /*清空数据*/
        $.removeData(target, 'riskMapEditor');
        $(target).empty();
    }

    $.fn.riskMapEditor = function (options) {
        /*如果第一个参数为 string 类型，则为调用方法，其他的参数作为方法参数*/
        if (typeof options == 'string') {
            /*将参数中的第一个参数去掉，剩下的作为参数传入方法调用*/
            var args = Array.prototype.slice.call(arguments, 1);
            /*返回值*/
            var res;
            this.each(function () {
                /*判断传入的方法是否有定义，如果有，在当前的jQuery对象上进行调用*/
                if ($.isFunction($.fn.riskMapEditor.methods[options])) {
                    var r = $.fn.riskMapEditor.methods[options].apply(this, args);
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


        /*初始化 riskMapEditor*/
        options = options || {};
        return this.each(function () {
            /*取回在当前对象上上存放的选项*/
            var data = $.data(this, 'riskMapEditor');
            /*
             * 该对象上保存过数据，说明该对象存在，先销毁，再重建。
             */
            if (data) {
                _destroy(this);
            }

            /*
             * 用 defaults 和新的 option 合并，建立新的 riskMapEditor
             */
            var innerOpts = $.extend({}, $.fn.riskMapEditor.defaults, options);
            /*将数据保存回当前对象*/
            $.data(this, 'riskMapEditor', {options: innerOpts});

            /*初始化风险地图*/
            _init(this);
        });
    };

    /**
     * riskMapEditor 默认参数定义
     */
    $.fn.riskMapEditor.defaults = {
        title: '风险地图',
        /*是否显示坐标轴*/
        showAxis: true,
        /*是否显示图例*/
        showLegend: true,
        /*是否显示提示*/
        showTips: false,
        /*单元格宽度*/
        cellWidth: 100,
        /*单元格高度*/
        cellHeight: 60,
        /*单位：默认万元*/
        unit: 10000,
        /*放在map中的附加信息*/
        addtional: '',
        axisyTitle: '风险频率',
        axisyFormatter: function (freq) {
            return freq.name;
        },
        axisxTitle: '风险影响',
        axisxFormatter: function (effect) {
            return effect.name;
        },
        mapFormatter: function (freq, effect, severity) {
            return severity.name + '<br>(' + freq.value + ', ' + effect.value + ')';
        }
    };

    $.fn.riskMapEditor.methods = {
        /*取回选项*/
        options: function () {
            return _options(this);
        },
        /*清空*/
        clear: function () {
            return _clear(this);
        },
        /*获得附加信息*/
        getAdditional: function () {
            var opts = _options(this);
            return opts.additional;
        },
        /*销毁 riskMapEditor*/
        destroy: function () {
            _destroy(this);
        }
    };

})(jQuery);