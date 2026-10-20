/*自定义RCSA日历控件*/
;(function ($) {

    /**
     * 获取保存在日历对象上的 options
     * param: target 用于显示日历控件的DOM对象
     */
    function _options(target) {
        /*从该对象上获取保存的数据*/
        var data = $(target).data('rcsaCalendar');
        var options;
        if (data) {
            options = data.options;
        } else {
            options = $.extend({}, $.fn.rcsaCalendar.defaults);
            $(target).data('rcsaCalendar', {options: options});
        }

        return options;
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
            $(arguments[1]).bind('click.rcsaCalendar', _monthChange);
        });

        /*为标题增加点击事件，在点击时，为选择月份的层对象增加点击事件*/
        $(target).find('.calendar-title').bind('click', function () {
            $.each($(target).find('.calendar-menu-month'), function () {
                $(arguments[1]).bind('click.rcsaCalendar', _monthChange);
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
                    $(arguments[1]).unbind('click.rcsaCalendar', this);
                });
            }
            /*加载事件*/
            _reload(target);
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
                _transferData(data);
                options.data = data;

                /*加载数据到日历对象*/
                _loadData(target, options.data);
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
        /*重新处理问卷的开始和结束日期的格式*/
        $.map(data, function (object, index) {
            try {
                var startDate = $.date.parse(object.start, $.date.DATE_FORMAT_2);
                object.startStr = $.date.format(startDate, _getShortDateFormat(startDate));

                var endDate = $.date.parse(object.end, $.date.DATE_FORMAT_2);
                object.endStr = $.date.format(endDate, _getShortDateFormat(endDate));

            } catch (ex) {
            }
        });
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
     * 加载自定义的事件，如果加载成功
     */
    function _loadData(target, data) {
        var opts = _options(target);
        /*声明消息事件提示窗口*/
        var eventBox = null;
        /*确认显示悬浮窗*/
        if (opts.showTips) {
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
        }


        for (var i = 0; i < days.length; i++) {
            var startCount = 0;
            var endCount = 0;

            var day = $(days[i]);
            for (var j = 0; j < data.length; j++) {
                var paper = data[j];
                if (paper.startStr == day.attr('abbr')) {
                    startCount++;
                } else if (paper.endStr == day.attr('abbr')) {
                    endCount++;
                }
            }

            /*增加开始样式*/
            if (startCount > 0) {
                $(day).addClass('calendar-day-rcsa-start');
            }

            /*增加结束样式*/
            if (endCount > 0) {
                $(day).addClass('calendar-day-rcsa-end');
            }


            if (opts.showTips) {
                day.hover(function (event) {
                    /*为消息盒子增加内容*/
                    var html = '';
                    var papers = [];

                    for (var j = 0; j < data.length; j++) {
                        var paper = data[j];
                        if (paper.startStr == $(this).attr('abbr') || paper.endStr == $(this).attr('abbr')) {
                            papers.push(paper);
                        }
                    }

                    if (papers.length > 0) {
                        for (var i = 0; i < papers.length; i++) {
                            var date = null;
                            var fontColor = "#000000";
                            var desc = " 即将开始";
                            if (papers[i].startStr == $(this).attr('abbr')) {
                                date = papers[i].start;
                                fontColor = "#339933";
                            } else {
                                date = papers[i].end;
                                fontColor = "#FF0000";
                                desc = " 即将结束";
                            }
                            var startDate = $.date.format($.date.parse(date, $.date.DATE_FORMAT_2), $.date.DATE_FORMAT_4);
                            html += '<div class="calendar-event" style="color: ' + fontColor + '">' + startDate + ' ' + _getShortString(papers[i].title) + desc + '</div>';
                            if (i >= 5) break;
                        }

                        $(html).appendTo(eventBox);
                        /*重新定位事件盒子*/
                        _relocateEventBox(eventBox, event);
                        /*设置事件盒子为显示*/
                        eventBox.css({display: 'block'});
                    }
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
                        if (eventBox.css('display') == 'block')
                            _relocateEventBox(eventBox, event);
                    }
                });
            }
        }
    }

    /**
     * 返回截断的字符串
     */
    function _getShortString(str) {
        if (str.length >= 30) return str.substring(0, 30) + "...";
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
            left = pageWidth - width - 10;
        }

        eventBox.css({left: left, top: top});
    }


    /**
     * 自定义的 rcsaCalendar 对象
     *
     */
    $.fn.rcsaCalendar = function (options) {

        /*如果第一个参数为 string 类型，则为调用方法，其他的参数作为方法参数*/
        if (typeof options == 'string') {
            /*将参数中的第一个参数去掉，剩下的作为参数传入方法调用*/
            var args = Array.prototype.slice.call(arguments, 1);
            /*判断是否被 rcsaCalendar 调用*/
            var isRCSAMethod = false;
            /*返回值*/
            var res = undefined;
            this.each(function () {
                /*判断传入的方法是否有定义，如果有，在当前的jQuery对象上进行调用*/
                if ($.isFunction($.fn.rcsaCalendar.methods[options])) {
                    isRCSAMethod = true;
                    var r = $.fn.rcsaCalendar.methods[options].apply(this, args);
                    if (res === undefined) {
                        res = r;
                    }
                }
            });

            if (res !== undefined) {
                return res;
            }

            /*没有在 rcsaCalendar 上定义的方法，尝试在 calendar 上进行调用*/
            if ($.isFunction($.fn.calendar.methods[options]) && !isRCSAMethod) {
                args.unshift(this);
                var r = $.fn.calendar.methods[options].apply(this, args);
                if (r !== undefined) {
                    return r;
                }
            }
            return this;
        }

        /*初始化 rcsaCalendar*/
        options = options || {};
        return this.each(function () {
            /*
             * 用 defaults 和新的 option 合并，建立新的 calendar
             */
            var innerOpts = $.extend({}, $.fn.rcsaCalendar.defaults, options);
            /*将数据保存回当前对象*/
            $.data(this, 'rcsaCalendar', {options: innerOpts});
            /*初始化 calendar*/
            _init(this);
            /*初始化事件*/
            _initEvent(this);
            /*加载数据*/
            _reload(this);
        });

    };

    /**
     * 定义calendar上可以被重写的方法
     */
    $.fn.rcsaCalendar.methods = {
        options: function () {
            return _options(this);
        },
        reload: function (param) {
            return _reload(this, param);
        }
    };

    /*默认值*/
    $.fn.rcsaCalendar.defaults = {
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
        /*是否显示悬浮穿提示*/
        showTips: true
    };

})(jQuery);