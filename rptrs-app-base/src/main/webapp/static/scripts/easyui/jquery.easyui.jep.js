/*解决没有 console 对象的问题*/
if (!window.console) {
    window.console = {};
}
if (!console.log) {
    console.log = function () {
    };
}
if (!console.info) {
    console.info = function () {
    };
}
if (!console.error) {
    console.error = function () {
    };
}
/*数据加载成功后,默认选中第一条记录*/
$.fn.datagrid.defaults = $.extend({}, $.fn.datagrid.defaults, {
    striped: true,
    onLoadSuccess: function (data) {
        $(this).datagrid('clearSelections');
        var rows = $(this).datagrid('getRows');
        if (rows && rows.length > 0) {
            $(this).datagrid('selectRow', 0);
        } else {
            var body = $(this).data().datagrid.dc.body2;
            body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;">没有数据</td></tr>');
        }
    }
});
/*为grid添加自己重新加载方法,解决带条件查询的时候分页栏不能回到首页问题*/
$.extend($.fn.datagrid.methods, {
    reset: function (jq, newposition) {
        return jq.each(function () {
            //显示第一页数据
            $(this).datagrid("options").pageNumber = 1;
            //分页栏上跳转到第一页
            $(this).datagrid('getPager').pagination({pageNumber: 1});
            $(this).datagrid("reload", newposition);
        });
    },

    /**
     * 开打提示功能
     * @param {} jq
     * @param {} params参数：
     *            类型     名称    说明
     *            {}     cls:   object类型，可存放自定义的toolTip显示样式，如字体、背景色等；
     *            Number delay: Number类型，设置显示的时间；
     *            []     fields:Array类型，设置可以显示toolTip的列，如['col1', 'col2', ...]
     * @return {}
     */
    doCellTip: function (jq, params) {
        function showTip(data, ele, e) {
            if ($(ele).text() == "")
                return;
            data.tooltip.text($(ele).text()).css({
                top: (e.pageY + 10) + 'px',
                left: (e.pageX + 20) + 'px',
                'z-index': $.fn.window.defaults.zIndex,
                display: 'block'
            });
        };
        return jq.each(function () {
            var options = $(this).data('datagrid');
//	        if (!options.tooltip) {
            var panel = $(this).datagrid('getPanel').panel('panel');
            var defaultCls = {
                'border': '1px solid #333',
                'padding': '2px',
                'font-family': '微软雅黑,宋体',
                //				'color': '#333',
                'background': '#fafafa',
                'position': 'absolute',
                'max-width': '200px',
                'border-radius': '4px',
                '-moz-border-radius': '4px',
                '-webkit-border-radius': '4px',
                'display': 'none',
                'word-break': 'break-all',
                'word-wrap': 'break-word'
            };

            var tooltip;//提示框
            if ($('body').find('#cellTip').length > 0) {
                tooltip = $('#cellTip');
            } else {
                tooltip = $("<div id='cellTip'></div>").appendTo('body');
            }
            var extendCls = defaultCls;
            var jqParams = params;
            if (!jqParams) {
                jqParams = {};
            }
            if (jqParams.cls) {
                extendCls = $.extend({}, defaultCls, jqParams.cls);
            }
            if (!jqParams.delay) {
                jqParams.delay = 1000;
            }
            tooltip.css($.extend({}, extendCls));
            options.tooltip = tooltip;
            if (jqParams.fields) {
                var fields = jqParams.fields;
                $.each(fields, function () {
                    var field = this;
                    panel.find('.datagrid-body td[field=' + field + '] .datagrid-cell').each(function () {
                        bindEvent(this, options, jqParams);
                    });
                });
            } else {
                panel.find('.datagrid-body .datagrid-cell').each(function () {
                    bindEvent(this, options, jqParams);
                });
            }
//	        }
        });

        function bindEvent(elem, options, jqParams) {
            $(elem).unbind('mouseover').unbind('mouseout').unbind('mousemove').live({
                mouseover: function (e) {
                    if (jqParams.delay) {
                        if (options.tipDelayTime)
                            clearTimeout(options.tipDelayTime);
                        var that = this;
                        options.tipDelayTime = setTimeout(function () {
                            showTip(options, that, e);
                        }, jqParams.delay);
                    } else {
                        showTip(options, this, e);
                    }

                },
                mouseout: function (e) {
                    if (options.tipDelayTime)
                        clearTimeout(options.tipDelayTime);
                    options.tooltip.css({
                        'display': 'none'
                    });
                },
                mousemove: function (e) {
                    var that = this;
                    if (options.tipDelayTime)
                        clearTimeout(options.tipDelayTime);
                    options.tipDelayTime = setTimeout(function () {
                        showTip(options, that, e);
                    }, jqParams.delay);
                }
            });
        }
    }
});

$.extend($.fn.tree.defaults, {
    onDblClick: function (node) {
        $(this).tree("toggle", node.target);
    }
});

/**
 * linkbutton方法扩展
 * @param {Object} jq
 */
//$.extend($.fn.linkbutton.methods, {
//	/**
//	 * 激活选项（覆盖重写）
//	 * @param {Object} jq
//	 */
//	enable: function(jq){
//	    return jq.each(function(){
//	        var state = $.data(this, 'linkbutton');
//	        if ($(this).hasClass('l-btn-disabled')) {
//	            var itemData = state._eventsStore;
//	            //恢复超链接
//	            if (itemData.href) {
//	                $(this).attr("href", itemData.href);
//	            }
//	            //回复点击事件
//	            if (itemData.onclicks) {
//	                for (var j = 0; j < itemData.onclicks.length; j++) {
//	                    $(this).bind('click', itemData.onclicks[j]);
//	                }
//	            }
//	            //设置target为null，清空存储的事件处理程序
//	            itemData.target = null;
//	            itemData.onclicks = [];
//	            $(this).removeClass('l-btn-disabled');
//	        }
//	    });
//	},
//	/**
//	 * 禁用选项（覆盖重写）
//	 * @param {Object} jq
//	 */
//	disable: function(jq){
//	    return jq.each(function(){
//	        var state = $.data(this, 'linkbutton');
//	        
//	        if (!state._eventsStore) 
//	            state._eventsStore = {};
//	        if (!$(this).hasClass('l-btn-disabled')) {
//	            var eventsStore = {};
//	            eventsStore.target = this;
//	            eventsStore.onclicks = [];
//	            //处理超链接
//	            var strHref = $(this).attr("href");
//	            if (strHref) {
//	                eventsStore.href = strHref;
//	                $(this).attr("href", "javascript:void(0)");
//	            }
//	            //处理直接耦合绑定到onclick属性上的事件
//	            var onclickStr = $(this).attr("onclick");
//	            if (onclickStr && onclickStr != "") {
//	                eventsStore.onclicks[eventsStore.onclicks.length] = new Function(onclickStr);
//	                $(this).attr("onclick", "");
//	            }
//	            //处理使用jquery绑定的事件
//	            var eventDatas = $(this).data("events") || $._data(this, 'events');
//	            if (eventDatas["click"]) {
//	                var eventData = eventDatas["click"];
//	                for (var i = 0; i < eventData.length; i++) {
//	                    if (eventData[i].namespace != "menu") {
//	                        eventsStore.onclicks[eventsStore.onclicks.length] = eventData[i]["handler"];
//	                        $(this).unbind('click', eventData[i]["handler"]);
//	                        i--;
//	                    }
//	                }
//	            }
//	            state._eventsStore = eventsStore;
//	            $(this).addClass('l-btn-disabled');
//	        }
//	    });
//	}
//});
