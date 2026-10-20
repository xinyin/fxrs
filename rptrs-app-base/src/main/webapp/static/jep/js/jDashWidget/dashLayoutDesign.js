/*全局变量定义*/
/*模式，design： 设计模式； run： 运行模型*/
var _mode = 'design';
/*布局容器，默认为 document.body*/
var _container = $(document.body);

/*布局配置对象，该对象从当前布局读取，对页面的修改将保存到该对象中，最终返回该对象*/
var _config = null;


/*当前操作配置的版本号，如果没有就是新增的数据*/
var version = 1;

/*获得当前操作的code*/
var layoutCode;

/*改变列宽度相关变量*/
var _mc = false,	//是否开始调整列宽度的标识 
    _mcsx = 0, 		//列的开始X坐标
    _mcex = 0, 		//列的结束X坐标
    _mcmx = 0, 		//列的偏移坐标
    _mcsrc = null, 	//调整的列对象
    _mcmw = 150, 	//列的最小宽度(px)
    _mcew = 2;		//列之间的间隔

/*改变组件高度相关变量*/
var _mw = false,	//是否开始调整组件宽度的标识 
    _mwsy = 0, 		//组件的开始Y坐标
    _mwey = 0, 		//组件的结束Y坐标
    _mwmy = 0, 		//组件的偏移坐标
    _mwsrc = null, 	//调整的组件对象
    _mwcon = null,	//调整的组件内容对象
    _mwmh = 85; 	//组件的最小高度(px)


/*拖拽改变组件位置相关变量*/
var _item = null,
    _startId = null,
    _receiveId = null,
    _stopId = null,
    _receiveWidgets = null,
    _stopWidgets = null;

/**
 * 按照页面大小，初始化一个空的布局
 * 包含3个空列，列宽相同
 */
function _getDefaultConfig() {
    var _bw = _container.width() - _mcew * 3;

    return {
        columns: [
            {
                id: 'column1',
                width: parseInt(_bw / 3)
            },
            {
                id: 'column2',
                width: parseInt(_bw / 3)
            },
            {
                id: 'column3',
                width: _bw - parseInt(_bw / 3) * 2
            }
        ]
    };
}

/**
 * 初始化页面布局
 */
function _init() {
    console.log('首页init布局--------------')
    /*清空布局区*/
    _container.empty();

    if ('NORMAL' == _designType) //普通设计模式
    {
        var _columns = $('<div id="columns"></div>').appendTo(_container);
        if (_config.columns != null) {
            /*把所有的列初始化到columns*/
            for (var i = 0; i < _config.columns.length; i++) {
                var _cc = _config.columns[i];
                var _column = $('<ul class="column" id="' + _cc.id + '"></ul>').appendTo(_columns);
                /*设计模式，增加边界和列边框*/
                _addResizeBorder4Column(_column);
                /*设计模式，增加边界和列边框*/
                console.log('_cc.width--------------------', _cc.width)
                _column.width(_cc.width);
                //增加padding 扩大模块的间距
                _column.css("padding", "10px");
                /*加载列中的组件*/
                if (!_cc.widgets) continue;
                for (var j = 0; j < _cc.widgets.length; j++) {
                    var _cw = _cc.widgets[j];
                    // _addWidget2Column(_cc, _cw);
                    var _flag = true;
                    _addWidget2Column(_cc, _cw, _flag);
                }
            }
            /*初始化完毕后，调整总列宽*/
            _resizeColumns();

            /*加载完组件后，使得组件可以拖拽调整位置*/
            _makeColumnSortable();

            /*初始化页面事件*/
            _initEvent();
        }
    } else if ('PROFESSIONAL' == _designType) //专家设计模式
    {
        var widgetCode = _config.match(/(?<=\$\{)[\d\D]*?(?=\})/g);
        var aftetReplace = '';
        $.each(widgetCode, function (i, v) {

            /*请求组件的内容*/
            var replaceStr = '\$\{' + v + '\}';
            $.mask.post(base + "/sys/dashLayoutDesign/getWidgetContent/" + v, function (data) {
                if (data.success) {
                    /*替换组件内容*/
                    _container.empty();
                    _config = _config.replace(replaceStr, data.content);
                    _container.append(_config);
                    console.log(_config);
                } else {
                    tmpContent = '<div style="width:100%;height:30px;text-align:center;vertical-align:middle;padding-top:20%">组件加载失败，请联系管理员。</div>';
                    /*替换组件内容*/
                    _config = _config.replace(replaceStr, tmpContent);
                    _container.append(_config);
                }
            });
        });
    }
}

function getWidgetCode(str) {
    var reg = '/\{(\S*)\}/', ret = [];
    while (arr = reg.exec(str)) ret.push(arr[1]);
    return ret;
}


/**
 * 将组件添加到列
 */
function _addWidget2Column(_cc, _cw, flag) {
    var _column = $('#' + _cc.id);
    var _wt;
    var _wth;
    if (!_isDesignMode()) {
        //_wt = $('<li class="widget" style="padding: 1px 0 0 1px; margin: 1px 0 0 1px;" id="' + _cw.id + '"></li>').appendTo(_column);
        _wt = $('<li class="widget" style="padding: 15px 20px 12px 20px;" id="' + _cw.id + '"></li>').appendTo(_column);

        //_wth = $('<div class="panel panel-default" style="margin-bottom: 0px;"></div>').appendTo(_wt);
        _wth = $('<div class="panel panel-default" style="overflow:hidden;margin-bottom: 0px;"></div>').appendTo(_wt);
    } else {
        _wt = $('<li class="widget" style="padding-bottom: 11px;" id="' + _cw.id + '"></li>').appendTo(_column);
        var _wth = $('<div class="panel panel-info widget-content" style="margin-bottom: 0px;"></div>').appendTo(_wt);
    }

    /*组件头部分*/
    //var _heading = $('<div class="panel-heading"></div>').appendTo(_wth);
    //添加按钮---更多
    var _heading = $('<div class="panel-heading" style="overflow: hidden;"></div>').appendTo(_wth);
    // var _h3 = $('<div class="panel-title" style="font-size: 13px;">' + _cw.title + '</div>').appendTo(_heading);
    var _h3 = $('<div class="panel-title" style="font-size: 13px;float: left;">' + _cw.title + '</div><a href="sys/staffNotify/list" style="float: right;">更多>></a>').appendTo(_heading);

    /*关闭按钮*/
    _addRemoveButton(_h3);

    /*组件内容部分*/
    var _wtc = $('<div class="panel-body" id="' + _cw.id + '_content" style="padding: 10px;"></div>').appendTo(_wth);

    $('<div style="width:100%;height:100%;"></div>').appendTo(_wtc);
    _wtc.height(_cw.height);

    //如果不是添加组件，重新调整组件
    if (!flag) {
        console.log('进入调整组件-----')
        var widgetHeight = $('#' + _cc.id + ' .widget').height() - 41;
        $('#' + _cc.id + ' .widget').css('height', widgetHeight + "px");
        var widgetConentHeight = $('#' + _cc.id + ' .widget' + ' .widget-content').height() - 67;
        $('#' + _cc.id + ' .widget' + ' .widget-content').css('height', widgetConentHeight + "px");
    }

    /*设计模式，增加组件尾部，调整组件大小*/
    /*组件尾部分*/
    _addResizeBorder4Widget(_wt);

    /*请求组件的内容*/
    $.mask.post(base + "/sys/dashLayoutDesign/getWidgetContent/" + _cw.code, function (data) {
        if (data.success) {
            console.log('当前widget请求得到的data------------', data)
            /*请求组件内容成功，清空content，并追加组件内容*/
            _wtc.empty();
            $(data.content).appendTo(_wtc);
        } else {
            _wtc.empty();
            $('<div style="width:100%;height:30px;text-align:center;vertical-align:middle;padding-top:20%">组件加载失败，请联系管理员。</div>').appendTo(_wtc);
        }

    });

}


/**
 * 为 Column 增加可改变大小的边框
 */
function _addResizeBorder4Column(column) {
    /*非设计模式下，不增加边框*/
    if (!_isDesignMode()) return;
    column.css({border: 'dashed 1px rgb(206, 194, 194)'});
    var _edge = $('<div class="column-edge">EDGE</div>').appendTo(column);
    _edge.mousedown(function (e) {
        e.stopPropagation();
        _mc = true;
        _mcsx = e.pageX;
        _mcsrc = $(this).parent();
    });
}

/**
 * 是否为设计模式
 * @returns
 */
function _isDesignMode() {
    return _mode == 'design';
}


/**
 * 调整列的宽度
 * 1. 获取所有column的宽度的和 _cstw
 * 2. 计算列的间隙 _cetw
 * 3. 调整 columns 的列宽
 */
function _resizeColumns() {
    var _columns = $('#columns > .column');
    var _cstw = 0;
    for (var i = 0; i < _columns.length; i++) {
        _cstw += $(_columns[i]).width();
        /*更新 _config*/
        $.extend(_config.columns[i], {
            width: $(_columns[i]).width()
        });

        /*计算每个组件的高度，更新_config 中的组件高度*/
        var _widgets = $(_columns[i]).children('.widget');
        if (_widgets.length == 0) continue;
        for (var j = 0; j < _widgets.length; j++) {
            //var _ht = _widgets.children('.widget-content').height();
            var _ht = $(_widgets.children('.widget-content')[j]).height();
            $.extend(_config.columns[i].widgets[j], {height: _ht});
        }
    }
    _cetw = _columns.length * _mcew;
    $('#columns').width(_cstw + _cetw);
}

//初始化工具栏
function _initToolbar() {
    //在右侧增加列
    $("#rightAddColumns").click(function () {
        /*获取最右侧列的对象，增加一个列和最右侧的列宽度相同*/
        var _rc = _config.columns[_config.columns.length - 1];
        _insertColumnLast(_config.columns.length + 1, _rc.width);
    })
    //删除最右侧的列
    $("#removeRightColumn").click(function () {
        if (_config.columns.length == 1) {
            $.messager.toastr('提示', '布局中至少要包含一列。', 'info');
            return;
        }
        var _cc = _config.columns[_config.columns.length - 1];
        var _column = $('#' + _cc.id);
        if (_cc.widgets && _cc.widgets.length > 0) {
            $.messager.confirm('提示', '该列中还有组件，要一并删除吗？', function (r) {
                if (r) {
                    _column.remove();
                    _config.columns.pop();
                    /*重新设置列的总宽度*/
                    _resizeColumns();
                }
            });
        } else {
            _column.remove();
            _config.columns.pop();
            /*重新设置列的总宽度*/
            _resizeColumns();
        }

    })

    //添加组件
    $("#addWidget").click(function () {
        var _cmw = $("#wigetSelect");
        /*检查组件是否存在*/
        for (var i = 0; i < _config.columns.length; i++) {
            var _column = _config.columns[i];
            if (!_column.widgets) continue;

            for (var j = 0; j < _column.widgets.length; j++) {
                if (_column.widgets[j].code === _cmw.val()) {
                    $.messager.toastr('提示', '组件已存在，不能重复添加。', 'info');
                    return;
                }
            }
        }

        /*增加组件到最左侧的列*/
        var _cc = _config.columns[0];
        var _cw = {
            id: 'widget_' + _cmw.val(),
            code: _cmw.val(),
            title: _cmw.find('option:selected').text(),
            height: 300
        };

        var flag = true;
        _addWidget2Column(_cc, _cw, flag);
        /*将配置加入到列*/
        _config.columns[0].widgets.push(_cw);
    })

    //保存配置
    $("#saveConfig").click(function () {
        $.messager.confirm('系统提示', '确定保存这些数据吗？', function (result) {
            var formData = {
                config: $.toJSON(_config),
                layoutCode: layoutCode,
                version: version
            };
            if (result) {
                $.mask.post(base + "/sys/dashLayoutConfig/save", formData, function (data) {
                    if (data.success) {
                        version = data.version;
                        $.messager.toastr('提示', data.msg, 'info');
                    } else {
                        $.messager.toastr('提示', data.msg, 'info');
                    }

                });
            }

        });

    })

    //返回
    $('#back').click(function () {

        window.location = base + "/sys/dashLayout/list";
    })


}

/**
 * 在最右侧增加列
 */
function _insertColumnLast(index, width) {
    var _columns = $('#columns');
    var _column = $('<ul class="column" id="column' + index + '"></ul>').appendTo(_columns);
    /*编辑模式，增加边界和列边框*/
    _column.css({border: 'dashed 1px rgb(206, 194, 194)'});
    var _edge = $('<div class="column-edge">EDGE</div>').appendTo(_column);
    _edge.mousedown(function (e) {
        e.stopPropagation();
        _mc = true;
        _mcsx = e.pageX;
        _mcsrc = $(this).parent();
    });
    /*编辑模式，增加边界和列边框*/
    _column.width(width);

    /*更新 _config*/
    _config.columns.push({
        id: 'column' + index,
        width: width
    });

    /*重新设置列的总宽度*/
    _resizeColumns();
    /*设置列为可拖拽*/
    _makeColumnSortable();
}


/**
 * 使组件可以拖动
 */
function _makeColumnSortable() {
    /*非设计模式下，组件不可拖动*/
    if (!_isDesignMode()) return;

    $(".column").sortable({
        cursor: "move",
        items: 'li', //只是li可以拖动
        handle: "div",
        opacity: 0.6, //拖动时，透明度为0.6
        revert: true, //释放时，增加动画
        connectWith: '.column',
        /*拖拽开始时，记录开始的列和组件列表*/
        start: function (event, ui) {
            _item = ui.item.attr('id');
            _startId = $(this).attr('id');
        },
        /*接收时，记录接收的列和组件列表，接收列一定和开始的列不同*/
        receive: function (event, ui) {
            _receiveId = $(this).attr('id');
            _receiveWidgets = $(this).sortable("toArray");
        },
        /*拖拽结束时，记录结束的列和组件列表，结束时的列和开始的列为同一列，并对组件重新排序*/
        stop: function (event, ui) {
            _stopWidgets = $(this).sortable("toArray");
            /*重新排序组件*/
            _reorderWidgets();
        }
    });
}

/**
 * 对组件进行重新排序，排序后清空相关变量
 */
function _reorderWidgets() {
    /*receiveId 不为 null， 代表是从一列移动到另外一列*/
    if (_receiveId) {
        /*将移动的item对象从原始列中移除*/
        var _widget = null;
        for (var i = 0; i < _config.columns.length; i++) {
            if (_config.columns[i].id != _startId) continue;
            var _widgets = _config.columns[i].widgets;
            for (var j = 0; j < _widgets.length; j++) {
                if (_widgets[j].id === _item) {
                    _widget = _widgets.splice(j, 1)[0];
                    break;
                }
            }
        }

        /*计算移动的item在目标列中的位置*/
        var _index = 0;
        var _widgetIndex = -1;
        for (var i = 0; i < _receiveWidgets.length; i++) {
            if (!_receiveWidgets[i]) continue;
            if (_receiveWidgets[i] === _item) {
                _widgetIndex = _index;
            }
            _index++;
        }

        /*将移动的item对象插入到目标列中*/
        for (var i = 0; i < _config.columns.length; i++) {
            if (_config.columns[i].id != _receiveId) continue;
            if (!_config.columns[i].widgets) _config.columns[i].widgets = [];
            _config.columns[i].widgets.splice(_widgetIndex, 0, _widget);
        }

    }
    /*receiveId 为 null， 代表是在一列中移动，改变了组件顺序*/
    else {
        /*将移动的item对象从原始列中移除*/
        var _widget = null;
        for (var i = 0; i < _config.columns.length; i++) {
            if (_config.columns[i].id != _startId) continue;
            var _widgets = _config.columns[i].widgets;
            for (var j = 0; j < _widgets.length; j++) {
                if (_widgets[j].id === _item) {
                    _widget = _widgets.splice(j, 1)[0];
                    break;
                }
            }
        }

        /*计算移动的item在目标列中的位置*/
        var _index = 0;
        var _widgetIndex = -1;
        for (var i = 0; i < _stopWidgets.length; i++) {
            if (!_stopWidgets[i]) continue;
            if (_stopWidgets[i] === _item) {
                _widgetIndex = _index;
            }
            _index++;
        }

        /*将移动的item对象插入到目标列中*/
        for (var i = 0; i < _config.columns.length; i++) {
            if (_config.columns[i].id != _startId) continue;
            _config.columns[i].widgets.splice(_widgetIndex, 0, _widget);
        }

    }

    _startId = null;
    _receiveId = null;
    _stopId = null;
    _receiveWidgets = null;
    _stopWidgets = null;
}


/**
 * 增加页面的处理事件
 */
function _initEvent() {
    /*增加改变组件大小和列大小的相关事件*/
    $(document).mouseup(function (e) {

        /*在body上放开鼠标时，如果是调整列宽度状态，则停止调整*/
        if (_mc) {
            _mc = false;
            _mcsrc = null;
        }

        /*在body上放开鼠标时，如果是调整组件高度状态，则停止调整*/
        if (_mw) {
            _mw = false;
            _mwsrc = null;
            _mwcon = null;
        }

    }).mousemove(function (e) {
        /*
         * 调整列的宽度，按以下步骤：
         * 1. 记录调整的结束坐标
         * 2. 开始坐标 - 结束坐标 = 偏移量
         * 3. 将开始坐标设置为结束坐标
         * 4. 如果正在调整过程中，并且当前列调整后的宽度大于最小宽度，则调整列的宽度
         * 5. 调整后计算所有列的宽度
         */
        _mcex = e.pageX;
        _mcmx = _mcsx - _mcex;
        _mcsx = _mcex;
        if (_mc && (_mcsrc.width() - _mcmx >= _mcmw)) {
            _mcsrc.width(_mcsrc.width() - _mcmx);
            _resizeColumns();
        }


        /*
         * 调整组件的高度，按以下步骤：
         * 1. 记录调整的结束坐标
         * 2. 开始坐标 - 结束坐标 = 偏移量
         * 3. 将开始坐标设置为结束坐标
         * 4. 如果正在调整过程中，并且当前组件调整后的高度大于最小高度，则调整列的高度
         */
        _mwey = e.pageY;
        _mwmy = _mwsy - _mwey;
        _mwsy = _mwey;
        if (_mw && (_mwcon.height() - _mwmy >= _mwmh)) {
            _mwsrc.height(_mwsrc.height() - _mwmy);
            _mwcon.height(_mwcon.height() - _mwmy);
            _resizeColumns();
        }

    });
}

/**
 * 为 widget 增加可改变大小的边框
 */
function _addResizeBorder4Widget(widget) {
    /*非设计模式下，不增加边框*/
    if (!_isDesignMode()) return;
    var _wtt = $('<div class="widget-tail">RESIZE</div>').appendTo(widget);
    _wtt.mousedown(function (e) {
        e.stopPropagation();
        _mw = true;
        _mwsy = e.pageY;
        _mwsrc = $(this).parent();
        _mwcon = $(this).siblings('.widget-content');
    });
}

/**
 * 增加移除按钮
 */
function _addRemoveButton(widget) {
    /*非设计模式下，不增加删除按钮*/
    if (!_isDesignMode()) return;
    var _btn = $('<a href="#" class="remove"><i class="fa fa-times pull-right"></i></a>').appendTo(widget);
    _btn.mousedown(function (e) {
        e.stopPropagation();
    }).click(function () {
        var _self = this;
        $.messager.confirm('提示', "确定移除该组件？", function (r) {

            if (!r) return;
            /*从 _config 中移除组件*/
            _removeWidget($(_self).parents('.widget').attr('id'));

            $(_self).parents('.widget').animate({
                opacity: 0
            }, function () {
                $(this).wrap('<div/>').parent().slideUp(function () {
                    $(this).remove();
                });
            });

        });
        return false;
    });
}

/**
 * 从 _config 中移除组件
 */
function _removeWidget(id) {
    for (var i = 0; i < _config.columns.length; i++) {
        var _column = _config.columns[i];
        if (!_column.widgets) continue;
        for (var j = 0; j < _column.widgets.length; j++) {
            var _widget = _column.widgets[j];
            if (_widget.id === id) {
                _column.widgets.splice(j, 1);
                break;
            }
        }
    }
}



