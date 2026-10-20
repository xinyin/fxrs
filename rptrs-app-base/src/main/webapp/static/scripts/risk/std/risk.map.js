;(function ($) {

    /**
     * 用于在某个对象上存取 option
     */
    function _options(target) {
        var data = $(target).data('riskMap');
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
        _redraw(target);
    }


    /**
     * 此函数的作用在于，根据给出的数字 t，返回一个数组，
     * 数组中的对象包含两个属性x 和 y, 使得 x + y = t 成立。
     * x 和 y 的最小值为 0， 最大值为 t
     */
    function _getIndexArray(t) {
        var rtn = [];
        for (var i = 0; i <= t; i++) {
            rtn.push({'x': i, 'y': t - i});
        }
        return rtn;
    }


    /**
     * 重绘
     */
    function _redraw(target) {
        _clear(target);
        var opts = _options(target);

        /*获得x，y，z轴的数据*/
        $.cache.cache('custom', contextPath + '/std/grade/listByGradeCateEx/' + opts.catex, opts.catex);
        var datax = $.cache.getCache(opts.catex);
        opts.datax = datax;

        $.cache.cache('custom', contextPath + '/std/grade/listByGradeCateEx/' + opts.catey, opts.catey);
        var datay = $.cache.getCache(opts.catey);
        opts.datay = datay;

        $.cache.cache('custom', contextPath + '/std/grade/listByGradeCateEx/' + opts.catez, opts.catez);
        var dataz = $.cache.getCache(opts.catez);
        opts.dataz = dataz;

        $.cache.cache('custom', opts.loadUrl, opts.loadUrl);
        var data = $.cache.getCache(opts.loadUrl);
        opts.data = data;

        /*计算整个区域的宽度及高度*/
        var rw = datax.length * opts.cellWidth + opts.axisyWidth + opts.legendyWidth;
        var rh = datay.length * opts.cellHeight + opts.legendxHeight + 50;

        $(target).width(rw);
        $(target).height(rh);
        $(target).addClass("riskmap");
        $(target).css({background: '#F8F8D8'});

        _createArea(target);

    }

    function _createArea(target) {
        var opts = _options(target);

        /*显示模式下创建消息盒子*/
        if (opts.mode == 'view') {
            _createEventBox(target);
        }

        /*标题区*/
        $('<div class="riskmap-title">' + opts.title + '</div>').appendTo(target);

        /*显示区*/
        var area = $('<div class="riskmap-area"></div>').appendTo(target);
        _createMainTable(target, area);

        /*x轴*/
        _createAxisxTable(target, area);

        /*编辑模式才创建编辑器*/
        if (opts.mode == 'edit') {
            _createEditBox(target);
        }
    }

    function _createMainTable(target, area) {
        var opts = _options(target);
        var datax = opts.datax;
        var datay = opts.datay;

        var tw = datax.length * opts.cellWidth + opts.axisyWidth + opts.legendyWidth;
        var th = datay.length * opts.cellHeight;

        var table = $('<table cellspacing="0" cellpadding="0" style="width:' + tw + 'px;height:' + th + 'px;"></table>').appendTo(area);
        var tr = $('<tr></tr>').appendTo(table);
        var td = $('<td></td>').appendTo(tr);
        _createAxisyTable(target, td);

        var td = $('<td style="width:' + (datax.length * opts.cellWidth) + 'px;height:' + (datay.length * opts.cellHeight) + 'px;"></td>').appendTo(tr);
        _createMapTable(target, td);
    }

    /**
     * 创建地图区域
     */
    function _createMapTable(target, mtd) {
        var opts = _options(target);
        var datax = opts.datax;
        var datay = opts.datay;
        var dataz = opts.dataz;
        var data = opts.data;

        var table = $('<table cellspacing="0" cellpadding="0" style="width:100%;height:100%;" class="riskmap-table"></table>').appendTo(mtd);
        if (opts.ordery == 'desc') {
            $.sort.selectSort(datay, _gradeDescComparator);
        } else {
            $.sort.selectSort(datay, _gradeAscComparator);
        }

        if (opts.orderx == 'desc') {
            $.sort.selectSort(datax, _gradeDescComparator);
        } else {
            $.sort.selectSort(datax, _gradeAscComparator);
        }


        for (var i = 0; i < datay.length; i++) {
            var dy = datay[i];
            var tr = $('<tr></tr>').appendTo(table);
            for (var j = 0; j < datax.length; j++) {
                var dx = datax[j];
                var dgc = null;
                for (var k = 0; k < data.length; k++) {
                    if (data[k].x == dx.gradeCode && data[k].y == dy.gradeCode) {
                        dgc = data[k].z;
                    }
                }

                var dz = null;
                for (var k = 0; k < dataz.length; k++) {
                    if (dataz[k].gradeCode == dgc) dz = dataz[k];
                }


                var td = $('<td class="riskmap-td" x="' + dx.gradeCode + '" y="' + dy.gradeCode + '" z="' + dz.gradeCode + '" style="background-color:' + dz.gradeColor + '"></td>').appendTo(tr);
                if (opts.showz) {
                    $('<p>' + dz.gradeName + '(' + dz.displayValue + ')</p>').appendTo(td);
                }

                /*编辑模式下，才可以增加编辑事件*/
                if (opts.mode == 'edit') {
                    $(td).dblclick('riskmap', function (target, td, dx, dy) {
                        return function (event) {
                            var opts = _options(target);
                            var dataz = opts.dataz;

                            /*计算z值*/
                            var z = $(td).attr('z');
                            var dz = null;
                            for (var i = 0; i < dataz.length; i++) {
                                if (dataz[i].gradeCode == z) {
                                    dz = dataz[i];
                                    break;
                                }
                            }

                            var left = $(td).position().left;
                            var top = $(td).position().top;
                            var editor = $(target).children('.riskmap-editor');
                            var combo = $(editor).children('.riskmap-combo');
                            var grid = $(combo).combogrid('grid');
                            var rows = $(grid).datagrid('getRows');
                            for (var i = 0; i < rows.length; i++) {
                                if (rows[i].gradeCode == dz.gradeCode) {
                                    $(grid).datagrid('selectRow', i);
                                }
                            }
                            $(combo).combogrid('setValue', dz.gradeCode);
                            var comboOpts = $(combo).combogrid('options');
                            comboOpts.onEditorChange = function () {
                                if (opts.saveUrl) {
                                    var x = dx.gradeCode;
                                    var y = dy.gradeCode;
                                    var z = $(combo).combogrid('getValue');
                                    var data = {x: x, y: y, z: z};
                                    $.mask.post(opts.saveUrl, data, function () {
                                        var dataz = opts.dataz;
                                        var dz = null;
                                        for (var k = 0; k < dataz.length; k++) {
                                            if (dataz[k].gradeCode == z) dz = dataz[k];
                                        }
                                        $(td).attr('z', z);
                                        $(td).css('background-color', dz.gradeColor);
                                        $(td).html(dz.gradeName + '(' + dz.displayValue + ')');
                                        $(combo).onEditorChange = null;
                                        $(editor).css('display', 'none');
                                    });
                                }
                            };
                            $(editor).css({'left': left, 'top': top, 'display': 'block'});
                        };
                    }(target, td, dx, dy));
                }


                /*显示模式下，显示风险点*/
                if (opts.mode == 'view') {

                    /*风险数据的总数*/
                    var count = 0;
                    /*风险数据的ID集合，放到对应的单元格的属性中*/
                    var events = [];
                    var tips = [];
                    if (opts.risks && opts.risks.length && opts.risks.length > 0) {
                        for (var k = 0; k < opts.risks.length; k++) {
                            var risk = opts.risks[k];
                            if (risk.fixedRiskGradeCode == dy.gradeCode && risk.controlGradeCode == dx.gradeCode) {
                                events.push(risk.riskId);
                                tips.push(risk.riskName);
                                count++;
                            }
                        }
                    }

                    /*设置td 的data，在点击td时，可以获得该td的风险点的id*/
                    $(td).attr('data', events.join(','));

                    /*数字*/
                    if (count > 0) {
                        /*事件盒子*/
                        var eventBox = opts.eventBox;

                        var html = '<span class="risk-map-point" >' + count + '</span>';
                        var point = $(html).appendTo(td);
                        /*增加鼠标悬停事件*/
                        var hover = function (tips) {
                            var e = eventBox;

                            return function (event) {
                                e.empty();
                                var html = '';
                                for (var i = 0; i < tips.length; i++) {
                                    html += '<div class="risk-event">' + (i + 1) + '.' + tips[i] + '</div>';
                                }
                                $(html).appendTo(e);

                                //重新定位事件盒子
                                _relocateEventBox(e, event);
                                e.fadeIn(350);
                            };
                        }(tips);

                        var out = function (event) {
                            var eventBox = opts.eventBox;
                            eventBox.fadeOut(250);
                        };
                        point.hover(hover, out);

                        /*增加鼠标移动事件，重新定位DIV的位置*/
                        point.mousemove(function (eventBox) {
                            return function (event) {
                                /*重定位消息盒子*/
                                _relocateEventBox(eventBox, event);
                            };
                        }(eventBox));

                        /*增加点击事件，回调 onCellClick 函数*/
                        point.click(function (td, opts) {
                            return function (event) {
                                var x = $(td).attr('x');
                                var y = $(td).attr('y');
                                var z = $(td).attr('z');
                                var data = $(td).attr('data');

                                if (opts.onCellClick && $.isFunction(opts.onCellClick)) {
                                    opts.onCellClick.call(this, x, y, z, data);
                                }
                            };
                        }(td, opts));
                    } else {
                        $('<span class="risk-map-point-empty">&nbsp;</span>').appendTo(td);
                    }

                }
            }
        }
    }

    /**
     * 创建Y轴图例
     */
    function _createAxisyTable(target, mtd) {
        var opts = _options(target);
        var datay = opts.datay;

        var table = $('<table cellspacing="0" cellpadding="0" style="width:' + (opts.axisyWidth + opts.legendyWidth) + 'px;"></table>').appendTo(mtd);
        var tr = $('<tr></tr>').appendTo(table);
        var ltd = $('<td style="width:' + opts.legendyWidth + 'px;height:100%" class="riskmap-ltdy">' + opts.titley + '</td>').appendTo(tr);
        var rtd = $('<td style="width:' + opts.axisyWidth + 'px;"></td>').appendTo(tr);

        var yt = $('<table cellspacing="0" cellpadding="0" style="width:100%;"></table>').appendTo(rtd);
        if (opts.ordery == 'desc') {
            $.sort.selectSort(datay, _gradeDescComparator);
        } else {
            $.sort.selectSort(datay, _gradeAscComparator);
        }

        for (var i = 0; i < datay.length; i++) {
            var tr = $('<tr></tr>').appendTo(yt);
            var td = $('<td class="riskmap-tdy" style="height:' + opts.cellHeight + 'px;width:' + opts.axisyWidth + 'px;"></td>').appendTo(tr);
            $(td).html(datay[i].gradeName + '(' + datay[i].displayValue + ')');
        }
    }

    /**
     * 创建Y轴图例
     */
    function _createAxisxTable(target, area) {
        var opts = _options(target);
        var datax = opts.datax;
        var datay = opts.datay;

        var xDiv = $('<div></div>').appendTo(area);
        var table = $('<table cellspacing="0" cellpadding="0" style="width:' + (opts.cellWidth * datax.length + opts.axisyWidth + opts.legendyWidth) + 'px;"></table>').appendTo(xDiv);
        var tr = $('<tr></tr>').appendTo(table);
        var ltd = $('<td style="width:' + (opts.axisyWidth + opts.legendyWidth) + 'px;"></td>').appendTo(tr);
        var rtd = $('<td style="width:' + (opts.cellWidth * datax.length) + 'px;"></td>').appendTo(tr);
        var xtable = $('<table cellspacing="0" cellpadding="0" style="width:100%;height:' + opts.legendxHeight + 'px;"></table>').appendTo(rtd);
        var tr1 = $('<tr></tr>').appendTo(xtable);

        if (opts.orderx == 'desc') {
            $.sort.selectSort(datax, _gradeDescComparator);
        } else {
            $.sort.selectSort(datax, _gradeAscComparator);
        }

        for (var i = 0; i < datax.length; i++) {
            $('<td class="riskmap-tdx" style="width:' + opts.cellWidth + 'px;height:' + opts.legendxHeight + 'px;">' + datax[i].gradeName + '(' + datax[i].displayValue + ')</td>').appendTo(tr1);
        }
        var tr2 = $('<tr></tr>').appendTo(xtable);
        $('<td class="riskmap-ltdx" colspan="' + datax.length + '">' + opts.titlex + '</td>').appendTo(tr2);
    }

    /**
     * Grade 类型的升序排序器
     */
    function _gradeAscComparator(o1, o2) {
        if (o1.calcValue < o2.calcValue) return -1;
        if (o1.calcValue > o2.calcValue) return 1;
        return 0;
    }

    /**
     * Grade 类型的降序排序器
     */
    function _gradeDescComparator(o1, o2) {
        if (o1.calcValue > o2.calcValue) return -1;
        if (o1.calcValue < o2.calcValue) return 1;
        return 0;
    }

    /**
     * 编辑模式下，创建编辑器
     */
    function _createEditBox(target) {
        var opts = _options(target);
        var inputId = $(target).attr('id') + '_editor';
        var eDiv = $('<div class="riskmap-editor" style="display:block;width:' + (opts.cellWidth - opts.editorPadding) + 'px;height:' + (opts.cellHeight - opts.editorPadding) + 'px;"></div>').appendTo(target);
        var editor = $('<input class="riskmap-combo" style="display:block;width:' + (opts.cellWidth - opts.editorPadding) + 'px;height:' + (opts.cellHeight - opts.editorPadding) + 'px;"/>').appendTo(eDiv);
        $(editor).combogrid({
            panelWidth: 400,
            panelHeight: 160,
            idField: 'gradeCode',
            textField: 'gradeName',
            editable: false,
            onChange: function (newValue, oldValue) {
                var row = $(editor).combogrid('grid').datagrid('getSelected');
                $(editor).combogrid('textbox').css('background', row.gradeColor);
                $(editor).combogrid('textbox').css('border', 'solid 1px #000000');
                $(editor).combogrid('textbox').css('text-align', 'center');
            },
            onClickRow: function () {
                var ecc = $(editor).combogrid('options').onEditorChange;
                if (ecc && $.isFunction(ecc)) {
                    ecc();
                }
            },
            columns: [[{
                field: 'gradeName',
                title: '名称',
                width: 80
            }, {
                field: 'gradeColor',
                title: '颜色',
                width: 150,
                formatter: forsetValue
            }, {
                field: 'displayValue',
                title: '显示值',
                width: 80
            }, {
                field: 'calcValue',
                title: '计算值',
                width: 80
            }]],
            onLoadSuccess: function () {

            }
        });

        var comboData = {};
        comboData.rows = opts.dataz;
        $(editor).combogrid('grid').datagrid('loadData', comboData);
        $(eDiv).css('display', 'none');

        function forsetValue(value) {
            if (value) {
                return "<div style=\"background-color:" + value
                    + ";padding:1px;width:95%;text-align:center;border:solid 1px #000000;\">"
                    + value + "</div>";
            } else {
                return '';
            }
        };
    }

    /**
     * 显示模式下，创建事件盒子
     */
    function _createEventBox(target) {
        var opts = _options(target);

        /*声明消息事件提示窗口*/
        var eventBox = null;
        var eventBoxArray = $('.risk-event-box');
        if (eventBoxArray.length > 0) {
            eventBox = $(eventBoxArray[0]);
        } else {
            eventBox = $('<div class="risk-event-box" style="display:none"></div>').appendTo($(document.body));
        }
        eventBox.empty();

        opts.eventBox = eventBox;
    }

    /**
     * 重定位事件盒子
     */
    function _relocateEventBox(eventBox, event) {
        var left = event.pageX;
        var top = event.pageY + 15;
        var width = eventBox.width();
        var height = eventBox.height();
        var pageWidth = $(document.body).width();
        var pageHeight = $(document.body).height();

        if ((top + height) > pageHeight && (left + width) <= pageWidth) {
            top = pageHeight - height;
        }

        if ((left + width) > pageWidth) {
            left = pageWidth - width - 20;
        }

        eventBox.css({left: left, top: top});
    }


    /**
     * 清空地图
     */
    function _clear(target) {
        var opts = _options(target);
        $(target).empty();
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
            var res = undefined;
            /*遍历所有的对象，判断方法是否存在，存在则调用该方法，并返回值*/
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
            var data = $(this).data('riskMap');
            /*
             * 该对象上保存过数据，说明该表格存在，先销毁，再重建。
             */
            if (data) {
                _destroy(this);
            }
            /*
             * 用 defaults 和新的 option 合并，建立新的 riskmap
             */
            var innerOpts = $.extend({}, $.fn.riskMap.defaults, options);
            /*将数据保存回当前对象*/
            $(this).data('riskMap', {options: innerOpts});
            /*初始化风险地图*/
            _init(this);
        });
    };

    /**
     * riskMap 默认参数定义
     */
    $.fn.riskMap.defaults = {
        /*宽度高度*/
        cellWidth: 100,
        cellHeight: 60,
        axisyWidth: 100,
        legendyWidth: 20,
        legendxHeight: 25,

        /*模式，编辑模式或展示模式 edit/view */
        mode: 'edit',
        /*是否显示色块的级别和描述*/
        showz: true,

        /*编辑选项*/
        editorPadding: 2,

        /*标题*/
        title: '风险地图',
        /*x, y 轴标题*/
        titlex: 'X',
        titley: 'Y',
        /*x, y, z轴的评估等级类别*/
        catex: '',
        catey: '',
        catez: '',

        orderx: 'asc',
        ordery: 'desc',

        /*url设置*/
        loadUrl: '',
        saveUrl: ''
    };

    $.fn.riskMap.methods = {
        /*取回选项*/
        options: function () {
            return _options(this);
        },
        /*销毁 riskMap*/
        destroy: function () {
            _destroy(this);
        }
    };

})(jQuery);