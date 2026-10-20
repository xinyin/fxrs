/*自定义事件日历控件*/
;(function ($) {

    /**
     * 获取保存在日历对象上的 options
     * param: target 用于显示日历控件的DOM对象
     */
    function _options(target, newOptions) {
        /*从该对象上获取保存的数据*/
        var data = $(target).data('eventCalendar');
        var options;
        if (data) {
            options = data.options;
        } else {
            options = $.extend({}, $.fn.eventCalendar.defaults);
            $(target).data('eventCalendar', {options: options});
        }

        /*没有第二个参数，获取options对象*/
        if (arguments.length == 1) {
            return options;
        }
        /*有第二个参数，保存 options 对象*/
        else {
            if (newOptions == undefined || newOptions == null) return options;
            $(target).data('eventCalendar', {options: $.extend(options, newOptions)});
        }
    }

    /**
     * 初始化插件的方法
     * param: target 用于显示日历控件的DOM对象
     */
    function _init(target) {
        var options = _options(target);

        /*使用原有的 calendar 插件显示日历*/
        $(target).calendar(options);
    }


    /**
     * 初始化事件的方法
     * param: target 用于显示日历控件的DOM对象
     */
    function _initEvent(target) {
        var options = _options(target);

        /*上年，上月，下月，下年四个按钮绑定事件*/
        $.each($(target).find('.calendar-prevmonth,.calendar-nextmonth,.calendar-prevyear,.calendar-nextyear'), function () {
            $(arguments[1]).bind('click.eventCalendar', _monthChange);
        });

        /*为标题增加点击事件，在点击时，为选择月份的层对象增加点击事件*/
        $(target).find('.calendar-title').bind('click', function () {
            $.each($(target).find('.calendar-menu-month'), function () {
                $(arguments[1]).bind('click.eventCalendar', _monthChange);
            });
        });

        /*内部函数，用于绑定事件月份改变事件*/
        function _monthChange() {
            var year = _getFullYear(target);
            var month = _getMonth(target);
            var returnValue = true;

            /*月份发生改变，激活事件*/
            if (options.year != year || options.month != month) {
                returnValue = options.onMonthChange.call($(target), year, month);
            }
            /*对于弹出的选择月份层，由于每次弹出时绑定事件，因此事件执行后要进行解绑*/
            if ($(event.srcElement).hasClass('calendar-menu-month')) {
                $.each($(target).find('.calendar-menu-month'), function () {
                    $(arguments[1]).unbind('click.eventCalendar', this);
                });
            }
            /*加载事件*/
            _reload(target);
            //_loadData(target, options.dataObject);
            /*设置当前年月*/
            options.year = year;
            options.month = month;
            _options(target, options);
            return returnValue;
        };
    };

    /*获得当前月份*/
    function _getMonth(target) {
        var abbr = $($(target).find('.calendar-day.calendar-saturday')[1]).attr('abbr');
        return parseInt(abbr.split(',')[1]);
    };

    /*获得当前年份*/
    function _getFullYear(target) {
        var abbr = $($(target).find('.calendar-day.calendar-saturday')[1]).attr('abbr');
        return parseInt(abbr.split(',')[0]);
    };

    /**
     * 重新加载事件
     */
    function _reload(target, queryParams) {
        var options = _options(target);
        if (options && (options.url || options.data)) {
            var data = options.data;
            /*此处逻辑为 url 属性优先, 如果设置了 url 属性，则先取 url 属性中的值*/
            options.isUrl = options.url && options.url.length > 0 ? true : false;
            /*判断数据是否合法, data应该是一个数组对象*/
            if (options.isUrl) {
                data = _requestData(target, options.url, queryParams);
            }
            /*将事件按日期保存到对象*/
            if (data != undefined && data != null) {
                options.dataObject = _transferData(data);
                options.data = data;
                _options(target, options);

                /*加载数据到日历对象*/
                _loadData(target, options.dataObject);
            }
        }
    }

    /**
     * 返回日期区间
     */
    function _getDateRange(target) {
        var days = $(target).find('.calendar-day');
        var range = {};

        var startStr = $(days[0]).attr('abbr');
        var startDate = _getShortDate(startStr);
        var endStr = $(days[days.length - 1]).attr('abbr');
        var endDate = _getShortDate(endStr);

        range.start = startDate.getTime();
        range.end = endDate.getTime();
        return range;
    }

    /**
     * 根据 url 的路径加载数据到 data
     */
    function _requestData(target, url, queryParams) {
        var options = _options(target);
        var queryData = null;
        if (queryParams == undefined) queryParams = options.queryParams;
        var range = _getDateRange(target);
        queryParams.start = range.start;
        queryParams.end = range.end;
        queryParams.dateType = 'java';
        $.ajax({
            async: false,
            type: options.method,
            url: url,
            data: queryParams,
            dataType: "json",
            success: function (data) {
                queryData = data;
            }
        });
        return queryData;
    }

    /**
     * 将事件数据保存到可以按日期索引的对象数组
     */
    function _transferData(data) {
        var dataObject = {};
        $.map(data, function (object, index) {
            try {
                var date = $.date.parse(object.start, $.date.DATE_FORMAT_2);
                var dateStr = $.date.format(date, _getShortDateFormat(date));
                var array = dataObject[dateStr];
                array = array || [];
                array.push(object);
                dataObject[dateStr] = array;
            } catch (ex) {
            }
        });
        /*排序*/
        _sortData(dataObject);
        return dataObject;
    }

    /**
     * 将生成的事件数组排序
     * 排序的规则是按时间正序排列
     */
    function _sortData(dataObject) {
        /*对象比较器*/
        var comparator = function (o1, o2) {
            var d1 = $.date.parse(o1.start, $.date.DATE_FORMAT_2);
            var d2 = $.date.parse(o2.start, $.date.DATE_FORMAT_2);
            var h1 = d1.getHours(), h2 = d2.getHours();
            var m1 = d1.getMinutes(), m2 = d2.getMinutes();
            if (h1 > h2) {
                return -1;
            } else if (h1 < h2) {
                return 1;
            } else {
                return m1 - m2;
            }
        };

        /*对数组进行排序*/
        for (key in dataObject) {
            var objects = dataObject[key];
            $.sort.bubbleSort(objects, comparator);
        }
    }

    /**
     * 将短格式时期字符串转换成日期对象
     */
    function _getShortDate(dateStr) {
        var array = dateStr.split(',');
        var date = new Date();
        date.setYear(parseInt(array[0]));
        date.setMonth(parseInt(array[1] - 1));
        date.setDate(parseInt(array[2]));
        date.setHours(0, 0, 0, 0);
        return date;
    }

    /**
     * 获得 easyui - calendar 使用的短日期格式
     */
    function _getShortDateFormat(date) {
        var format = 'yyyy,';
        if ((date.getMonth() + 1) >= 10) {
            format += 'MM,';
        } else {
            format += 'M,';
        }
        if (date.getDate() >= 10) {
            format += 'dd';
        } else {
            format += 'd';
        }
        return format;
    }

    /**
     * 从指定元素上移除所有定义的样式
     */
    function _removeCls(target, element) {
        var opts = _options(target);
        var statusCls = opts.statusCls;
        for (var i = 0; i < statusCls.length; i++) {
            $(element).removeClass(statusCls[i]);
        }
    }

    /**
     * 获得一个数组中的所有事件的最终状态
     * 最终状态的确定按照定义的顺序排列
     */
    function _getClassName(target, eventArray) {
        var opts = _options(target);
        var status = opts.status;
        var statusCls = opts.statusCls;
        var className = '';
        if (eventArray != undefined && eventArray != null) {
            var min = 10000;
            for (var i = 0; i < eventArray.length; i++) {
                var event = eventArray[i];
                for (var j = 0; j < status.length; j++) {
                    if (event.status == status[j]) {
                        if (j < min) min = j;
                    }
                }
            }
            if (min >= 0 && min < statusCls.length) className = statusCls[min];
        }
        return className;
    }

    /**
     * 加载自定义的事件，如果加载成功
     */
    function _loadData(target, dataObject) {
        var opts = _options(target);
        /*确认显示悬浮窗*/
        if (opts.showTips) {
            /*声明消息事件提示窗口*/
            var eventBox = null;
            var eventBoxArray = $('.calendar-event-box');
            if (eventBoxArray.length > 0) {
                eventBox = $(eventBoxArray[0]);
            } else {
                eventBox = $('<div class="calendar-event-box" style="display:none"></div>').appendTo($(document.body));
            }
            eventBox.empty();
        }

        /*获取日历中的所有日期对象*/
        var days = $(target).find('.calendar-day');
        /*去掉所有日期的样式和事件*/
        for (var i = 0; i < days.length; i++) {
            var day = $(days[i]);
            /*解除事件绑定并移除样式*/
            day.unbind('hover');
            _removeCls(target, days[i]);
//		day.removeClass('calendar-day-event-open');
//		day.removeClass('calendar-day-event-closed');
        }

        /*遍历数据，将数据绑定到对应的日期上*/
        for (var key in dataObject) {
            for (var i = 0; i < days.length; i++) {
                var day = $(days[i]);
                /*匹配的日期增加样式和事件绑定*/
                if (day.attr('abbr') == key) {
                    var eventArray = dataObject[key];
                    var className = _getClassName(target, eventArray);
//				var className = 'calendar-day-event-closed';
//				for(var k = 0; k < eventArray.length; k++)
//				{
//					if(eventArray[k].status == 'OPEN')
//					{
//						className = 'calendar-day-event-open';
//						break;
//					}
//				}
                    if (className != '') day.addClass(className);
                    if (opts.showTips) {
                        day.hover(function (event) {
                            /*为消息盒子增加内容*/
//						var html = '<table border="0" cellpadding="5" cellspacing="5" style="width:200;"><tbody>';
//						var eventArray = dataObject[$(this).attr('abbr')];
//						for(var j = 0; j < eventArray.length; j++)
//						{
//							var startDate = $.date.format($.date.parse(eventArray[j].start, $.date.DATE_FORMAT_2), $.date.DATE_FORMAT_3);
//							html += '<tr><td class="calendar-event">' + startDate + ' ' + eventArray[j].title + '</td></tr>';
//						}
//						html += '</tbody></table>';
                            var html = '';
                            var eventArray = dataObject[$(this).attr('abbr')];
                            for (var j = 0; j < eventArray.length; j++) {
                                var startDate = $.date.format($.date.parse(eventArray[j].start, $.date.DATE_FORMAT_2), $.date.DATE_FORMAT_5);
                                var calssName = 'calendar-event';
                                if (eventArray[j].status == 'CLOSED') {

                                }
                                if (eventArray[j].allDay) {
                                    html += '<div class="calendar-event">' + _getShortString(eventArray[j].title) + '</div>';
                                } else {
                                    html += '<div class="calendar-event">' + startDate + ' ' + _getShortString(eventArray[j].title) + '</div>';
                                }
                            }
                            $(html).appendTo(eventBox);
                            /*重新定位事件盒子*/
                            _relocateEventBox(eventBox, event);
                            /*设置事件盒子为显示*/
                            eventBox.css({display: 'block'});
                        }, function () {
                            /*清空内容*/
                            eventBox.empty();
                            /*隐藏事件盒子*/
                            eventBox.css({display: 'none'});
                        });
                        /*增加鼠标移动事件，重新定位DIV的位置*/
                        day.mousemove(function (event) {
                            var eventBoxArray = $('.calendar-event-box');
                            if (eventBoxArray.length > 0) {
                                var eventBox = $(eventBoxArray[0]);
                                /*重定位消息盒子*/
                                _relocateEventBox(eventBox, event);
                            }
                        });
                    }
                }
            }
        }
        ;
    }

    /**
     * 返回截断的字符串
     */
    function _getShortString(str) {
        if (str.length >= 15) return str.substring(0, 15) + "...";
        return str;
    }

    /**
     * 重定位事件盒子
     */
    function _relocateEventBox(eventBox, event) {
        var left = event.pageX;
        var top = event.pageY + 20;
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
     * 自定义的 eventCalendar 对象
     *
     */
    $.fn.eventCalendar = function (method, param) {
        /*调用 eventCalendar 的方法, 调用形式: $('calendar').eventCalendar('reload', [参数])*/
        if (typeof method == 'string') {
            return $.fn.eventCalendar.methods[method](this, param);
        }
        /*生成一个新的 eventCalendar 对象，第一个参数为 options*/
        else {
            var options = method || {};
            return this.each(function () {
                /*取回在 eventCalendar 上存放的选项*/
                var data = $(this).data('eventCalendar');
                if (data) {
                    $.extend(data.options, options);
                } else {
                    $(this).data('eventCalendar', {options: $.extend({}, $.fn.eventCalendar.defaults, options)});
                    /*初始化对象*/
                    _init(this);
                }
                /*初始化事件*/
                _initEvent(this);
                /*加载自定义的事件*/
                _reload(this);
            });
        }
    };

    /**
     * 定义calendar上可以被重写的方法
     */
    $.fn.eventCalendar.methods = {
        options: function (jq, param) {
            return _options(jq[0], param);
        },
        reload: function (jq, param) {
            return jq.each(function () {
                _reload(this, param);
            });
        }
    };

    /*默认值*/
    $.fn.eventCalendar.defaults = {
        width: 180,
        height: 180,
        fit: false,
        border: true,
        method: 'GET',
        queryParams: {},
        weeks: ['日', '一', '二', '三', '四', '五', '六'],
        months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        year: new Date().getFullYear(),
        month: new Date().getMonth() + 1,
        current: new Date(),
        onSelect: function (date) {
        },
        onMonthChange: function (year, month) {
        },
        onLoadSuccess: function (data) {
        },
        /*事件状态*/
        status: ['OPEN', 'CLOSED'],
        /*事件状态对应的样式名*/
        statusCls: ['calendar-day-event-open', 'calendar-day-event-closed'],
        /*是否显示悬浮穿提示*/
        showTips: true
    };

})(jQuery);