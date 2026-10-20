;(function ($) {

    /**
     * 用于在某个对象上存取 option
     */
    function _options(target) {
        var data = $.data(target, 'riskMap');
        if (data) {
            return data.options;
        }
        return undefined;
    }

    /**
     * 初始化方法
     */
    function _init(target) {
        var opts = _options(target);

        /*获得风险事件严重程度和风险事件频率*/
        var severities = [];
        var freqs = [];
        $.ajax({
            async: false,
            type: 'post',
            url: contextPath + '/bpa/riskboundary/getEventSeverity',
            success: function (data) {
                severities = data;
            }
        });

        $.ajax({
            async: false,
            type: 'post',
            url: contextPath + '/bpa/riskboundary/getEventFreq',
            success: function (data) {
                freqs = data;
            }
        });

        opts.severities = severities;
        opts.freqs = freqs;

        var containerId = $(target).attr('id');
        var areaId = containerId + '_mapArea';
        opts.containerId = containerId;
        opts.areaId = areaId;
        $(target).css({background: '#F8F8D8'});
        //$('<div  style="width:' +opts.cellWidth * freqs.length + 'px;" class="risk-map-title">' + opts.title + '</div>').appendTo($(target));
        $('<div id="' + areaId + '" class="risk-map-area"></div>').appendTo($(target));

        _redraw(target);
    }

    /**
     * 获得风险严重程度
     */
    function _getSeverity(target, disorder) {
        var opts = _options(target);
        var severities = opts.severities;
        for (var i = 0; i < severities.length; i++) {
            var severity = severities[i];
            if (severity.disorder == disorder) {
                return severity;
            }
        }
        return null;
    }

    /**
     * 获得风险频率
     */
    function _getFreq(target, disorder) {
        var opts = _options(target);
        var freqs = opts.freqs;
        for (var i = 0; i < freqs.length; i++) {
            var freq = freqs[i];
            if (freq.disorder == disorder) {
                return freq;
            }
        }
        return null;
    }

    /**
     * 获得风险事件对象
     */
    function _getData(target, id) {
        var opts = _options(target);
        var data = opts.data;
        if (data != undefined && data != null) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].id == id) {
                    return data[i];
                }
            }
        }
        return null;
    }

    /**
     * 重绘
     */
    function _redraw(target) {
        _clear(target);
        var opts = _options(target);
        var areaId = opts.areaId;
        var bounds = opts.bounds;
        var data = opts.data;
        if (bounds != undefined && bounds != null && bounds.length > 0) {
            var severities = opts.severities;
            var freqs = opts.freqs;

            /*主表格*/
            var mt = $('<table class="risk-map-mt" cellspacing="0" cellpadding="0"></table>').appendTo($('#' + areaId));
            /*标题TR*/
            var btr = $('<tr></tr>').appendTo(mt);
            /*标题显示*/
            if (opts.showAxis) {
                /*留出Y轴图例TD*/
                if (opts.showLegend) {
                    $('<td class="risk-map-legend-td-y"></td>').appendTo(btr);
                }
                /*留出Y轴TD*/
                $('<td class="risk-map-axis-td-y"></td>').appendTo(btr);
            }
            /*标题列*/
            var btd = $('<td style="width:' + opts.cellWidth * freqs.length + 'px;" class="risk-map-title"></td>').appendTo(btr);
            /*定义了点击标题的事件*/
            if (opts.onTitleClick != undefined) {
                var ta = $('<a href="#">' + opts.title + '</a>').appendTo(btd);
                ta.click(function (event) {
                    //TODO 参数未定义
                    opts.onTitleClick.call(target, event);
                });
            } else {
                btd.text(opts.title);
            }


            /*上层 TR*/
            var ttr = $('<tr></tr>').appendTo(mt);

            /*Y轴显示TD*/
            if (opts.showAxis) {
                /*Y轴图例*/
                if (opts.showLegend) {
                    var ytd = $('<td></td>').appendTo(ttr);
                    var table = $('<table class="risk-map-legend-table-y" cellspacing="0" cellpadding="0"></table>').appendTo(ytd);
                    var tbody = $('<tbody></tbody>').appendTo(table);
                    $('<tr><td  class="risk-map-legend-td-y" style="height:' + opts.cellHeight + 'px;" class="risk-map-legend-td-y">风险事件严重程度(单位：万元)</td></tr>').appendTo(tbody);
                }

                var ytd = $('<td></td>').appendTo(ttr);
                var table = $('<table class="risk-map-axis-table-y" cellspacing="0" cellpadding="0"></table>').appendTo(ytd);
                var tbody = $('<tbody></tbody>').appendTo(table);
                for (var i = severities.length - 1; i >= 0; i--) {
                    var severity = severities[i];
                    var value = severity.upperValue / 10000;
                    var strValue = '' + value;
                    if (strValue.length > 5) {
                        strValue = strValue.substring(0, 4) + '...';
                    }
                    var tr = $('<tr></tr>').appendTo(tbody);
                    var td = $('<td style="height:' + opts.cellHeight + 'px;cursor:help;" class="risk-map-axis-td-y" title="' + value + '">' + strValue + '</td>').appendTo(tr);

                }
            }

            /*主地图区域 TD*/
            var mtd = $('<td width="100%"></td>').appendTo(ttr);
            var table = $('<table style="width:' + opts.cellWidth * freqs.length + 'px;" class="risk-map-table" cellspacing="0" cellpadding="0"></table>').appendTo(mtd);
            var tbody = $('<tbody></tbody>').appendTo(table);
            for (var i = severities.length - 1; i >= 0; i--) {
                var severity = severities[i];
                var tr = $('<tr></tr>').appendTo(tbody);
                for (var j = freqs.length - 1; j >= 0; j--) {
                    var freq = freqs[j];
                    var value = severity.calcValue * freq.calcValue;
                    var color = "#FFFFFF";
                    for (var k = 0; k < bounds.length; k++) {
                        /*计算颜色*/
                        var bound = bounds[k];
                        if (value >= bound.min * opts.unit && value <= bound.max * opts.unit) {
                            color = bound.color;
                        }
                        /*计算风险数据的个数*/
                    }

                    /*风险数据的总数*/
                    var count = 0;
                    /*风险数据的ID集合，放到对应的单元格的属性中*/
                    var events = [];
                    if (data != undefined && data != null && data.length > 0) {
                        for (var k = 0; k < data.length; k++) {
                            var riskData = data[k];
                            if (parseFloat(riskData.severity) == parseFloat(severity.calcValue) && parseFloat(riskData.freq) == parseFloat(freq.calcValue)) {
                                events.push(riskData.id);
                                count++;
                            }
                        }
                    }

                    var coor = severity.disorder + ',' + freq.disorder;
                    var html = '<td class="risk-map-td" coor="' + coor + '" ';
                    if (count > 0) {
                        html += ' data="' + events.join(',') + '"';
                    }
                    html += ' style="height:' + opts.cellHeight + 'px;background:' + color + ';';
                    if (opts.showTips) {
                        html += 'cursor:help;" title="' + value + '">';
                    } else {
                        html += '">';
                    }

                    if (count > 0) {
                        html += count;
                    } else {
                        html += '&nbsp;';
                    }
                    html += '</td>';
                    var td = $(html).appendTo(tr);

                    /*增加鼠标点击事件，调用事件回调接口 onCellSelect */
                    td.click(function (event) {
                        /*根据坐标返回严重程度和频率*/
                        var coor = $(this).attr('coor').split(',');

                        var severity = _getSeverity(target, parseInt(coor[0]));
                        var freq = _getFreq(target, parseInt(coor[1]));

                        /*根据事件返回风险事件数组*/
                        var dataArray = [];
                        var events = $(this).attr('data');
                        if (events != undefined && events != null && $.trim(events) != '') {
                            var eventArr = events.split(',');
                            for (var i = 0; i < eventArr.length; i++) {
                                var data = _getData(target, eventArr[i]);
                                if (data != null) dataArray.push(data);
                            }
                        }
                        if (opts.onCellClick != undefined) {
                            opts.onCellClick.call(target, severity, freq, dataArray);
                        }

                    });


                    /*增加鼠标悬停事件*/
                    td.hover(function (event) {
                        $(this).removeClass('risk-map-td');
                        $(this).addClass('risk-map-td-hover');
                    }, function (event) {
                        $(this).removeClass('risk-map-td-hover');
                        $(this).addClass('risk-map-td');
                    });
                }
            }

            /*X轴显示 TR*/
            if (opts.showAxis) {
                var btr = $('<tr></tr>').appendTo(mt);

                /*X轴图例*/
                if (opts.showLegend) {
                    $('<td></td>').appendTo(btr);
                }

                /*添加一个空的TD*/
                $('<td>&nbsp;</td>').appendTo(btr);
                var xtd = $('<td  style="width:' + opts.cellWidth * freqs.length + 'px;"></td>').appendTo(btr);
                var table = $('<table class="risk-map-axis-table-x" cellspacing="0" cellpadding="0"></table>').appendTo(xtd);
                var tbody = $('<tbody></tbody>').appendTo(table);
                var tr = $('<tr></tr>').appendTo(tbody);
                for (var i = freqs.length - 1; i >= 0; i--) {
                    var freq = freqs[i];
                    $('<td style="cursor:help;" class="risk-map-axis-td-x" title="' + freq.eventFreqName + '">' + freq.eventFreqName + '</td>').appendTo(tr);
                }

                if (opts.showLegend) {
                    var tr = $('<tr></tr>').appendTo(tbody);
                    $('<td class="risk-map-legend-td-x" colspan="' + freqs.length + '">风险事件频率(单位：次/年)</td>').appendTo(tr);
                }
            }
        } else {
            $('<div class="risk-map-nodata">未提供边界数据</div>').appendTo($('#' + areaId));

        }
    }

    /**
     * 清空地图
     */
    function _clear(target) {
        var opts = _options(target);
        $('#' + opts.areaId).empty();
    }

    /**
     * 销毁一个riskMap对象
     */
    function _destroy(target) {
        /*清空数据*/
        $.removeData(target, 'riskMap');
        $(target).empty();
    }

    $.fn.riskMap = function (options) {
        /*如果第一个参数为 string 类型，则为调用方法，其他的参数作为方法参数*/
        if (typeof options == 'string') {
            /*将参数中的第一个参数去掉，剩下的作为参数传入方法调用*/
            var args = Array.prototype.slice.call(arguments, 1);
            /*返回值*/
            var res;
            this.each(function () {
                /*判断传入的方法是否有定义，如果有，在当前的jQuery对象上进行调用*/
                if ($.isFunction($.fn.riskMap.methods[options])) {
                    var r = $.fn.riskMap.methods[options].apply(this, args);
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

        /*初始化 riskMap*/
        options = options || {};
        return this.each(function () {
            /*取回在当前对象上上存放的选项*/
            var data = $.data(this, 'riskMap');
            /*
             * 该对象上保存过数据，说明该表格存在，先销毁，再重建。
             */
            if (data) {
                _destroy(this);
            }
            /*
             * 用 defaults 和新的 option 合并，建立新的 datagrid
             */
            var innerOpts = $.extend({}, $.fn.riskMap.defaults, options);
            /*将数据保存回当前对象*/
            $.data(this, 'riskMap', {options: innerOpts});
            /*初始化风险地图*/
            _init(this);
        });
    };

    /**
     * riskMap 默认参数定义
     */
    $.fn.riskMap.defaults = {
        title: '风险地图',
        /*边界定义*/
        bounds: [],
        /*风险数据*/
        data: [],
        /*是否显示坐标轴*/
        showAxis: true,
        /*是否显示图例*/
        showLegend: true,
        /*是否显示提示*/
        showTips: false,
        /*单元格宽度*/
        cellWidth: 25,
        /*单元格高度*/
        cellHeight: 25,
        /*单位：默认万元*/
        unit: 10000,
        /*放在map中的附加信息*/
        addtional: null
    };

    $.fn.riskMap.methods = {
        /*取回选项*/
        options: function () {
            return _options(this);
        },
        /*清空*/
        clear: function () {
            return _clear(this);
        },
        /*加载边界*/
        loadBounds: function (bounds) {
            var opts = _options(this);
            opts.bounds = bounds;
            _redraw(this);
        },
        /*加载数据*/
        loadData: function (data) {
            var opts = _options(this);
            opts.data = data;
            _redraw(this);
        },
        /*获得标题*/
        getTitle: function () {
            var opts = _options(this);
            return opts.title;
        },
        /*获得附加信息*/
        getAdditional: function () {
            var opts = _options(this);
            return opts.additional;
        },
        /*销毁 riskMap*/
        destroy: function () {
            _destroy(this);
        }
    };

})(jQuery);