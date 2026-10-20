/******插件初始化完成后，需要初始化脚本*********/
function initCron(id, data) {
    //初始化数字选择框
    var ids = new Array();
    ids.push({id: 'second1', name: 'second', type: '-', max: 58, min: 1});
    ids.push({id: 'second2', name: 'second', type: '-', max: 59, min: 2});
    ids.push({id: 'second3', name: 'second', type: '/', max: 59, min: 0});
    ids.push({id: 'second4', name: 'second', type: '/', max: 59, min: 1});
    ids.push({id: 'min1', name: 'min', type: '-', max: 58, min: 1});
    ids.push({id: 'min2', name: 'min', type: '-', max: 59, min: 2});
    ids.push({id: 'min3', name: 'min', type: '/', max: 59, min: 0});
    ids.push({id: 'min4', name: 'min', type: '/', max: 59, min: 1});
    ids.push({id: 'hour1', name: 'hour', type: '-', max: 23, min: 0});
    ids.push({id: 'hour2', name: 'hour', type: '-', max: 23, min: 2});
    ids.push({id: 'hour3', name: 'hour', type: '/', max: 23, min: 0});
    ids.push({id: 'hour4', name: 'hour', type: '/', max: 23, min: 1});
    ids.push({id: 'day1', name: 'day', type: '-', max: 31, min: 1});
    ids.push({id: 'day2', name: 'day', type: '-', max: 31, min: 2});
    ids.push({id: 'day3', name: 'day', type: '/', max: 31, min: 1});
    ids.push({id: 'day4', name: 'day', type: '/', max: 31, min: 1});
    ids.push({id: 'day5', name: 'day', type: 'W', max: 31, min: 1});
    ids.push({id: 'month1', name: 'month', type: '-', max: 12, min: 1});
    ids.push({id: 'month2', name: 'month', type: '-', max: 12, min: 2});
    ids.push({id: 'month3', name: 'month', type: '/', max: 12, min: 1});
    ids.push({id: 'month4', name: 'month', type: '/', max: 12, min: 1});
    ids.push({id: 'week1', name: 'week', type: '/', max: 7, min: 1});
    ids.push({id: 'week2', name: 'week', type: '/', max: 7, min: 2});
    ids.push({id: 'week3', name: 'week', type: '#', max: 4, min: 1});
    ids.push({id: 'week4', name: 'week', type: '#', max: 7, min: 1});
    ids.push({id: 'week5', name: 'week', type: 'L', max: 7, min: 1});
    ids.push({id: 'year1', name: 'year', type: '-', max: 3000, min: 2017});
    ids.push({id: 'year2', name: 'year', type: '-', max: 3000, min: 2018});
    initNumber(ids);

    var vals = $("input[name^='v_']");
    var cron = $('#' + id + 'Id');
    vals.change(function () {
        var item = [];
        vals.each(function () {
            item.push(this.value);
        });
        //修复表达式错误BUG，如果后一项不为* 那么前一项肯定不为为*，要不然就成了每秒执行了
        //获取当前选中tab
        var currentIndex = 0;
        $('.nav-tabs>li').each(function (i, item) {
            if ($(item).hasClass('active')) {
                currentIndex = i;
                return false;
            }

        });
        //当前选中项之前的如果为*，则都设置成0
        for (var i = currentIndex; i >= 1; i--) {
            if (item[i] != '*' && item[i - 1] == '*') {
                item[i - 1] = '0';
            }
        }
        //当前选中项之后的如果不为*则都设置成*
        if (item[currentIndex] == '*') {
            for (var i = currentIndex + 1; i < item.length; i++) {
                if (i == 5) {
                    item[i] = '?';
                } else {
                    item[i] = '*';
                }
            }
        }
        cron.val(item.join(' ')).change();
    });

    cron.change(function () {
        cronChange(id);
    });


    var secondList = $('.secondList').find('input');
    $('#sencond_appoint').click(function () {
        if (this.checked) {
            if ($(secondList).filter(':checked').length == 0) {
                $(secondList.eq(0)).prop('checked', true);
            }
            secondList.eq(0).change();
        }
    });
    secondList.change(function () {
        var sencond_appoint = $('#sencond_appoint').prop('checked');
        if (sencond_appoint) {
            var vals = [];
            secondList.each(function () {
                if (this.checked) {
                    vals.push(this.value);
                }
            });
            var val = '?';
            if (vals.length > 0 && vals.length < 59) {
                val = vals.join(',');
            } else if (vals.length == 59) {
                val = '*';
            }
            var item = $('input[name=v_second]');
            item.val(val);
            item.change();
        }
    });

    var minList = $('.minList').find('input');
    $('#min_appoint').click(function () {
        if (this.checked) {
            if ($(minList).filter(':checked').length == 0) {
                $(minList.eq(0)).prop('checked', true);
            }
            minList.eq(0).change();
        }
    });
    minList.change(function () {
        var min_appoint = $('#min_appoint').prop('checked');
        if (min_appoint) {
            var vals = [];
            minList.each(function () {
                if (this.checked) {
                    vals.push(this.value);
                }
            });
            var val = '?';
            if (vals.length > 0 && vals.length < 59) {
                val = vals.join(",");
            } else if (vals.length == 59) {
                val = '*';
            }
            var item = $('input[name=v_min]');
            item.val(val);
            item.change();
        }
    });

    var hourList = $('.hourList').find('input');
    $('#hour_appoint').click(function () {
        if (this.checked) {
            if ($(hourList).filter(':checked').length == 0) {
                $(hourList.eq(0)).prop('checked', true);
            }
            hourList.eq(0).change();
        }
    });

    hourList.change(function () {
        var hour_appoint = $('#hour_appoint').prop('checked');
        if (hour_appoint) {
            var vals = [];
            hourList.each(function () {
                if (this.checked) {
                    vals.push(this.value);
                }
            });
            var val = '?';
            if (vals.length > 0 && vals.length < 24) {
                val = vals.join(',');
            } else if (vals.length == 24) {
                val = '*';
            }
            var item = $('input[name=v_hour]');
            item.val(val);
            item.change();
        }
    });

    var dayList = $('.dayList').find('input');
    $('#day_appoint').click(function () {
        if (this.checked) {
            if ($(dayList).filter(':checked').length == 0) {
                $(dayList.eq(0)).prop('checked', true);
            }
            dayList.eq(0).change();
        }
    });
    dayList.change(function () {
        var day_appoint = $('#day_appoint').prop('checked');
        if (day_appoint) {
            var vals = [];
            dayList.each(function () {
                if (this.checked) {
                    vals.push(this.value);
                }
            });
            var val = '?';
            if (vals.length > 0 && vals.length < 31) {
                val = vals.join(',');
            } else if (vals.length == 31) {
                val = '*';
            }
            var item = $('input[name=v_day]');
            item.val(val);
            item.change();
        }
    });

    var monthList = $('.monthList').find('input');
    $('#month_appoint').click(function () {
        if (this.checked) {
            if ($(monthList).filter(':checked').length == 0) {
                $(monthList.eq(0)).prop('checked', true);
            }
            monthList.eq(0).change();
        }
    });
    monthList.change(function () {
        var month_appoint = $('#month_appoint').prop('checked');
        if (month_appoint) {
            var vals = [];
            monthList.each(function () {
                if (this.checked) {
                    vals.push(this.value);
                }
            });
            var val = '?';
            if (vals.length > 0 && vals.length < 12) val = vals.join(',');
            else if (vals.length == 12) val = '*';
            var item = $("input[name=v_month]");
            item.val(val);
            item.change();
        }
    });

    var weekList = $('.weekList').find('input');
    $('#week_appoint').click(function () {
        if (this.checked) {
            if ($(weekList).filter(':checked').length == 0) {
                $(weekList.eq(0)).prop('checked', true);
            }
            weekList.eq(0).change();
        }
    });
    weekList.change(function () {
        var week_appoint = $('#week_appoint').prop('checked');
        if (week_appoint) {
            var vals = [];
            weekList.each(function () {
                if (this.checked) {
                    vals.push(this.value);
                }
            });
            var val = '?';
            if (vals.length > 0 && vals.length < 7) val = vals.join(",");
            else if (vals.length == 7) val = '*';
            var item = $('input[name=v_week]');
            item.val(val);
            item.change();
        }
    });
    //如果传入出初始数据， 需要反显
    if (data) {
        var regs = data.split(' ');
        $('input[name=v_second]').val(regs[0]);
        $('input[name=v_min]').val(regs[1]);
        $('input[name=v_hour]').val(regs[2]);
        $('input[name=v_day]').val(regs[3]);
        $('input[name=v_month]').val(regs[4]);
        $('input[name=v_week]').val(regs[5]);
        initObj(regs[0], 'second');
        initObj(regs[1], 'min');
        initObj(regs[2], 'hour');
        initDay(regs[3]);
        initMonth(regs[4]);
        initWeek(regs[5]);
        if (regs.length > 6) {
            $('input[name=v_year]').val(regs[6]);
            initYear(regs[6]);
        }
    }
}

/*初始化数字选择框*/
function initNumber(ids) {
    $.each(ids, function (i, v) {
        $('.' + v.id + ' .btn:first-of-type').on('click', function () {
            var val = parseInt($('.' + v.id + ' input').val(), 10) + 1;
            if (val >= v.min && val <= v.max) {
                $('.' + v.id + ' input').val(val);
                var $radio = $(this).parent().parent().parent().parent().find('input[type=radio]');
                $($radio).prop('checked', true);
                var ns = $(this).parent().parent().parent().parent().find('.numberspinner');
                var start = $(ns.eq(0)).val();
                var end = $(ns.eq(1)).val();
                var item = $("input[name=v_" + v.name + "]");
                if (v.type == 'W') item.val(start + v.type);
                else if (v.type == 'L') item.val(start + v.type);
                else item.val(start + v.type + end);
                item.change();
            }
        });

        $('.' + v.id + ' .btn:last-of-type').on('click', function () {
            var val = parseInt($('.' + v.id + ' input').val(), 10) - 1;
            if (val >= v.min && val <= v.max) {
                $('.' + v.id + ' input').val(val);
                var $radio = $(this).parent().parent().parent().parent().find('input[type=radio]');
                $($radio).prop('checked', true);
                var ns = $(this).parent().parent().parent().parent().find('.numberspinner');
                var start = $(ns.eq(0)).val();
                var end = $(ns.eq(1)).val();
                var item = $("input[name=v_" + v.name + "]");
                if (v.type == 'W') item.val(start + v.type);
                else if (v.type == 'L') item.val(start + v.type);
                else item.val(start + v.type + end);
                item.change();
            }
        });

        $('.' + v.id + ' input').on('blur', function () {
            var val = v.min;
            var ival = parseInt($('.' + v.id + ' input').val());
            if (!isNaN(ival)) {
                if (ival < v.min) $('.' + v.id + ' input').val(v.min);
                if (ival > v.max) $('.' + v.id + ' input').val(v.max);
                var $radio = $(this).parent().parent().parent().find('input[type=radio]');
                $($radio).prop('checked', true);
                var ns = $(this).parent().parent().parent().find('.numberspinner');
                var start = $(ns.eq(0)).val();
                var end = $(ns.eq(1)).val();
                var item = $("input[name=v_" + v.name + "]");
                if (v.type == 'W') item.val(start + v.type);
                else if (v.type == 'L') item.val(start + v.type);
                else item.val(start + v.type + end);
                item.change();
            } else {
                $('.' + v.id + ' input').val(val);
                var $radio = $(this).parent().parent().parent().find('input[type=radio]');
                $($radio).prop('checked', true);
                var ns = $(this).parent().parent().parent().find(".numberspinner");
                var start = $(ns.eq(0)).val();
                var end = $(ns.eq(1)).val();
                var item = $("input[name=v_" + v.name + "]");
                if (v.type == 'W') item.val(start + v.type);
                else if (v.type == 'L') item.val(start + v.type);
                else item.val(start + v.type + end);
                item.change();
            }
        });
    });
}

/**
 * 每周期
 */
function everyTime(dom) {
    var item = $("input[name=v_" + dom.name + "]");
    item.val("*");
    item.change();
}

/**
 * 周期
 */
function cycle(dom) {
    var name = dom.name;
    var ns = $(dom).parent().parent().parent().find(".numberspinner");
    var start = $(ns.eq(0)).val();
    var end = $(ns.eq(1)).val();
    var item = $("input[name=v_" + name + "]");
    item.val(start + "-" + end);
    item.change();
}

/**
 * 从开始
 */
function startOn(dom) {
    var name = dom.name;
    var ns = $(dom).parent().parent().parent().find('.numberspinner');
    var start = $(ns.eq(0)).val();
    var end = $(ns.eq(1)).val();
    var item = $("input[name=v_" + name + "]");
    item.val(start + "/" + end);
    item.change();
}

/**
 * 不指定
 */
function unAppoint(dom) {
    var name = dom.name;
    var val = '?';
    if (name == 'year')
        val = '';
    var item = $("input[name=v_" + name + "]");
    item.val(val);
    item.change();
}

//本月的最后一天
function lastDay(dom) {
    var item = $("input[name=v_" + dom.name + "]");
    item.val('L');
    item.change();
}

//每月最近的那个工作日
function workDay(dom) {
    var name = dom.name;
    var ns = $(dom).parent().parent().parent().find('.numberspinner');
    var start = $(ns.eq(0)).val();
    var item = $("input[name=v_" + name + "]");
    item.val(start + "W");
    item.change();
}

//第几周的星期几
function weekOfDay(dom) {
    var name = dom.name;
    var ns = $(dom).parent().parent().parent().find('.numberspinner');
    var start = $(ns.eq(0)).val();
    var end = $(ns.eq(1)).val();
    var item = $("input[name=v_" + name + "]");
    item.val(start + '#' + end);
    item.change();
}

//本月最后一个星期
function lastWeek(dom) {
    var item = $("input[name=v_" + dom.name + "]");
    var ns = $(dom).parent().parent().parent().find('.numberspinner');
    var start = $(ns.eq(0)).val();
    item.val(start + 'L');
    item.change();
}


function cronChange(id) {
    var txt = $('#' + id + 'Id').val();
    if (txt) {
        var regs = txt.split(' ');
        $('input[name=v_second]').val(regs[0]);
        $('input[name=v_min]').val(regs[1]);
        $('input[name=v_hour]').val(regs[2]);
        $('input[name=v_day]').val(regs[3]);
        $('input[name=v_month]').val(regs[4]);
        $('input[name=v_week]').val(regs[5]);

        initObj(regs[0], 'second');
        initObj(regs[1], 'min');
        initObj(regs[2], 'hour');
        initDay(regs[3]);
        initMonth(regs[4]);
        initWeek(regs[5]);
        if (regs.length > 6) {
            $('input[name=v_year]').val(regs[6]);
            initYear(regs[6]);
        }
    }
}

function initObj(strVal, strid) {
    var ary = null;
    var objRadio = $("input[name='" + strid + "'");
    if (strVal == '*') {
        objRadio.eq(0).prop('checked', 'checked');
    } else if (strVal.split('-').length > 1) {
        ary = strVal.split('-');
        objRadio.eq(1).prop('checked', 'checked');
        $('#' + strid + 'Start_0').val(ary[0]);
        $('#' + strid + 'End_0').val(ary[1]);
    } else if (strVal.split('/').length > 1) {
        ary = strVal.split('/');
        objRadio.eq(2).prop('checked', 'checked');
        $('#' + strid + 'Start_1').val(ary[0]);
        $('#' + strid + 'End_1').val(ary[1]);
    } else {
        objRadio.eq(3).prop('checked', 'checked');
        if (strVal != '?') {
            ary = strVal.split(',');
            for (var i = 0; i < ary.length; i++) {
                $('.' + strid + 'List input[value="' + ary[i] + '"]').prop('checked', 'checked');
            }
        }
    }
}

function initDay(strVal) {
    var ary = null;
    var objRadio = $('input[name="day"');
    if (strVal == '*') objRadio.eq(0).prop('checked', 'checked');
    else if (strVal == '?') objRadio.eq(1).prop('checked', 'checked');
    else if (strVal.split('-').length > 1) {
        ary = strVal.split('-');
        objRadio.eq(2).prop('checked', 'checked');
        $('#dayStart_0').val(ary[0]);
        $('#dayEnd_0').val(ary[1]);
    } else if (strVal.split('/').length > 1) {
        ary = strVal.split('/');
        objRadio.eq(3).prop('checked', 'checked');
        $('#dayStart_1').val(ary[0]);
        $('#dayEnd_1').val(ary[1]);
    } else if (strVal.split('W').length > 1) {
        ary = strVal.split('W');
        objRadio.eq(4).prop('checked', 'checked');
        $('#dayStart_2').val(ary[0]);
    } else if
    (strVal == 'L') {
        objRadio.eq(5).prop('checked', 'checked');
    } else {
        objRadio.eq(6).prop('checked', 'checked');
        ary = strVal.split(',');
        for (var i = 0; i < ary.length; i++) {
            $('.dayList input[value="' + ary[i] + '"]').prop('checked', 'checked');
        }
    }
}

function initMonth(strVal) {
    var ary = null;
    var objRadio = $('input[name="month"');
    if (strVal == '*') {
        objRadio.eq(0).prop('checked', 'checked');
    } else if (strVal == '?') {
        objRadio.eq(1).prop('checked', 'checked');
    } else if (strVal.split('-').length > 1) {
        ary = strVal.split('-');
        objRadio.eq(2).prop('checked', 'checked');
        $('#monthStart_0').val(ary[0]);
        $('#monthEnd_0').val(ary[1]);
    } else if (strVal.split('/').length > 1) {
        ary = strVal.split('/');
        objRadio.eq(3).prop('checked', 'checked');
        $('#monthStart_1').val(ary[0]);
        $('#monthEnd_1').val(ary[1]);
    } else {
        objRadio.eq(4).prop('checked', 'checked');
        ary = strVal.split(',');
        for (var i = 0; i < ary.length; i++) {
            $('.monthList input[value="' + ary[i] + '"]').prop('checked', 'checked');
        }
    }
}

function initWeek(strVal) {
    var ary = null;
    var objRadio = $('input[name="week"');
    if (strVal == '*') {
        objRadio.eq(0).prop('checked', 'checked');
    } else if (strVal == '?') {
        objRadio.eq(1).prop('checked', 'checked');
    } else if (strVal.split('/').length > 1) {
        ary = strVal.split('/');
        objRadio.eq(2).prop('checked', 'checked');
        $('#weekStart_0').val(ary[0]);
        $('#weekEnd_0').val(ary[1]);
    } else if (strVal.split('-').length > 1) {
        ary = strVal.split('-');
        objRadio.eq(3).prop('checked', 'checked');
        $('#weekStart_1').val(ary[0]);
        $('#weekEnd_1').val(ary[1]);
    } else if (strVal.split('L').length > 1) {
        ary = strVal.split('L');
        objRadio.eq(4).prop('checked', 'checked');
        $('#weekStart_2').val(ary[0]);
    } else {
        objRadio.eq(5).prop('checked', 'checked');
        ary = strVal.split(',');
        for (var i = 0; i < ary.length; i++) {
            $('.weekList input[value="' + ary[i] + '"]').prop('checked', 'checked');
        }
    }
}

function initYear(strVal) {
    var ary = null;
    var objRadio = $('input[name="year"');
    if (strVal == '*') {
        objRadio.eq(1).prop('checked', 'checked');
    } else if (strVal.split('-').length > 1) {
        ary = strVal.split('-');
        objRadio.eq(2).prop('checked', 'checked');
        $('#yearStart_0').val(ary[0]);
        $('#yearEnd_0').val(ary[1]);
    }
}
