/**
 * String 的原型处理，增加 trim 方法
 */
String.prototype.trim = function () {
    var t = new String(this);
    t = t.replace(/(^\s*)|(\s*$)/g, "");
    return t.replace(/(^\u3000*)|(\u3000*$)/g, "");
};

/**
 * Number 的原型方法，去掉末尾的 0
 * @author Michael
 */
Number.prototype.rtrim = function () {
    var _fn = new String(this);
    if (_fn.indexOf('.') < 0) return _fn;

    if (/(\-?\d+)(.?)(\d*)/gi.test(_fn)) {
        var _fv = RegExp.$1;
        var _p = RegExp.$2;
        var _bv = RegExp.$3;
        if (_bv != '') {
            _fv += _p;
            if (/(0*)([1-9]+)/gi.test(_bv)) {
                _fv += _bv;
            } else if (/([1-9]+)(0*)/gi.test(_bv)) {
                _fv += _bv.substring(0, _bv.indexOf('0'));
            } else if (/(0+)([1-9]+)(0+)/gi.test(_bv)) {
                _fv += RegExp.$1 + RegExp.$2;
            }
        }
        return _fv;
    }
    return _fn;
};

/*为只读输入框，屏蔽退格键*/
$(window).load(function () {
    var _o = $(':input[readonly="readonly"]');
    for (var i = 0; i < _o.length; i++) {
        $(_o[i]).keydown('norespond8', function (e) {
            if (e.keyCode == 8 && $.browser.msie) {
                window.event.returnValue = false;
                return false;
            }
        });
        try {
            var _t = $(_o[i]).combobox('textbox');
            $(_t).keydown('norespond8', function (e) {
                if (e.keyCode == 8 && $.browser.msie) {
                    window.event.returnValue = false;
                    return false;
                }
            });
        } catch (e) {
        }
    }
});


/**
 * 按键处理
 */
jQuery.keyboard = {

    norespond8: function () {
        var _args = arguments;
        if (!_args || _args.length <= 0) return;
        var _id = [];
        for (var i = 0; i < _args.length; i++) {
            _id.push(_args[i].test(/\#/) ? _args[i] : '#' + _args[i]);
        }
        var _ids = _id.join(',');

        var _os = $(_ids);
        if (!_os || _os.length <= 0) return;
        _os.keydown('norespond8', function (e) {
            if (e.keyCode == 8 && $.browser.msie) {
                window.event.returnValue = false;
                return false;
            }
        });
    }
};


/**
 * 遮罩层的命名空间
 */
jQuery.mask = {

    /**
     * 带 mask 功能的 datagrid
     * @param url
     * @param data
     * @param fn
     */
    post: function (url, data, fn) {
        var _data, _fn;
        if (data) {
            if ($.isFunction(data)) {
                _fn = data;
            } else {
                _data = data;
                if (fn && $.isFunction(fn)) {
                    _fn = fn;
                }
            }
        }

        $.mask.show();
        $.post(url, _data, function (data) {
            $.mask.remove();
            if (_fn) _fn.call(this, data);
        });
    },

    /**
     * 显示一个遮罩层在当前页面上,调用代码：$.mask.show();
     * @param msg 要显示的消息，如果为空，则默认为：正在处理,请稍后...
     */
    show: function (msg) {
        if (msg == undefined || msg == '' || msg == null) msg = '正在处理,请稍候...';
        var topwin = $.mask.getTopPage();
        var topdoc = topwin.document;

        var width = topdoc.body.clientWidth;
        var height = topdoc.body.clientHeight;
        var div = $('<div class="datagrid-mask"></div>').css({
            display: 'block',
            width: width,
            height: height
        }).appendTo($(topdoc.body));
        var msgDiv = $('<div class="datagrid-mask-msg">' + msg + '</div>').css({display: 'block'}).appendTo($(topdoc.body));
        var msgWidth = msgDiv.width();
        var msgHeight = msgDiv.height();
        var left = (width - msgWidth) / 2;
        var top = (height - msgHeight) / 2;
        div.css({'z-index': '1000000'});
        msgDiv.css({left: left, top: top, 'z-index': '1000001'});
    },

    getTopPage: function (current) {
        var win;
        if (!current) {
            win = top;
        } else {
            win = current.top;
        }

        if (!win.isIndex) return arguments.callee(win);

        return win;
    },

    /**
     * 移除页面上的遮罩层,调用代码: $.mask.remove();
     */
    remove: function () {
        var topwin = $.mask.getTopPage();
        var topdoc = topwin.document;
        $(topdoc.body).find("div.datagrid-mask").remove();
        $(topdoc.body).find("div.datagrid-mask-msg").remove();
    }
};

/**
 * 数学运算命名空间
 */
jQuery.math = {

    /**
     * 取两数之间的小值
     * @param a
     * @param b
     */
    min: function (a, b) {
        return a > b ? b : a;
    },
    /**
     * 取两数之间的大值
     * @param a
     * @param b
     */
    max: function (a, b) {
        return a > b ? a : b;
    },
    /**
     * 生成一个随机数
     * @param min
     * @param max
     */
    random: function (min, max) {
        var low = 1;
        var high = 100;
        if (min == undefined && max == undefined) {

        } else if (max == undefined) {
            high = min;
        } else {
            low = min;
            high = max;
        }
        if (low >= high) {
            var tmp = low;
            low = high;
            high = tmp;
        }
        return parseInt((high - low) * Math.random() + low);
    }
};

/**
 * 增加格式化函数
 */
jQuery.format = {

    /**
     * 将一个数字格式化为千分位。
     */
    commafy: function (num) {
        num = num + '';
        var re = /(\-?\d+)(\d{3})/;
        while (re.test(num)) {
            num = num.replace(re, "$1,$2");
        }
        return num;
    },
    /**
     * 将一个数字格式化为千分位，四舍五入。
     */
    roundCommafy: function (num, n) {
        var a = parseFloat(num + '');
        if (isNaN(a)) return num;

        var b = Math.pow(10, n);
        a = Math.round(a * b) / b;
        return $.format.commafy(a);
    }
};


/**
 * 日期命名空间
 */
jQuery.date = {
    DATE_FORMAT_1: 'yyyy-MM-dd HH:mm:ss',
    DATE_FORMAT_2: 'yyyy-MM-dd HH:mm',
    DATE_FORMAT_3: 'HH:mm',
    DATE_FORMAT_4: 'yyyy-MM-dd',
    DATE_FORMAT_5: 'h:mmtt',
    DATE_FORMAT_6: 'yyyyMMdd',
    /**
     * 将指定日期根据格式，格式化为字符串
     * param date 日期
     * param format 日期格式
     */
    format: function (date, format) {
        var o = {
            "M+": date.getMonth() + 1, //month
            "d+": date.getDate(), //day
            "h+": date.getHours() >= 12 ? date.getHours() - 12 : date.getHours(), //hour
            "H+": date.getHours(),
            "m+": date.getMinutes(), //minute
            "s+": date.getSeconds(), //second
            "q+": Math.floor((date.getMonth() + 3) / 3), //quarter
            "S": date.getMilliseconds(),
            "tt": date.getHours() < 12 ? 'am' : 'pm',
            "t": date.getHours() < 12 ? 'a' : 'p',
            "TT": date.getHours() < 12 ? 'AM' : 'PM',
            "T": date.getHours() < 12 ? 'A' : 'P'
        };

        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")", "g").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    },
    /**
     * 将指定字符串，按照一个格式，转换为日期对象
     * param dateStr 日期字符串
     * param format 格式
     * return 如果格式错误，或者与字符串不匹配，返回 undefined
     */
    parse: function (dateStr, format) {
        var dateformat = format;
        if (dateformat == undefined) {
            dateformat = 'YYYY-MM-dd hh:mm:ss';
        }

        /*第一步，先解析格式，识别特殊字符并加上括号*/
        if (/(d+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1.toUpperCase() + ')');
        }
        if (/(y+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1.toUpperCase() + ')');
        }
        if (/(M+)/g.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1 + ')');
        }
        if (/(h+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1.toUpperCase() + ')');
        }
        if (/(m+)/g.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1 + ')');
        }
        if (/(s+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1 + ')');
        }

        /*第二步，解析修改后的格式，识别位置*/
        var position = {
            year: -1,
            month: -1,
            date: -1,
            hour: -1,
            minute: -1,
            second: -1
        };

        var reg = new RegExp('(\(\\w+\))', 'g');
        var result = dateformat.match(reg);
        for (var i = 0; i < result.length; i++) {
            switch (result[i]) {
                case 'YYYY':
                    position.year = i + 1;
                    break;
                case 'MM':
                    position.month = i + 1;
                    break;
                case 'DD':
                    position.date = i + 1;
                    break;
                case 'HH':
                    position.hour = i + 1;
                    break;
                case 'mm':
                    position.minute = i + 1;
                    break;
                case 'ss':
                    position.second = i + 1;
                    break;
            }
        }

        /*第三步，生成最终的正则表达式*/
        if (/(d+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }
        if (/(y+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }
        if (/(M+)/g.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }
        if (/(h+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }
        if (/(m+)/g.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }
        if (/(s+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }

        if (new RegExp(dateformat).test(dateStr)) {
            var year = eval('RegExp.$' + position.year);
            var month = eval('RegExp.$' + position.month);
            var day = eval('RegExp.$' + position.date);
            var hour = eval('RegExp.$' + position.hour);
            var minute = eval('RegExp.$' + position.minute);
            var second = eval('RegExp.$' + position.second);

            var date = new Date();
            var value = null;
            if (position.year != -1) {
                value = parseInt(year.replace(/^0+/, ''));
                date.setFullYear(value);
            }

            if (position.month != -1) {
                value = parseInt(month.replace(/^0/, ''));
                date.setMonth(value - 1);
            }

            if (position.date != -1) {
                value = parseInt(day.replace(/^0/, ''));
                date.setDate(value);
            }

            if (position.hour != -1) {
                value = parseInt(hour.replace(/^0/, ''));
                date.setHours(value);
            }

            if (position.minute != -1) {
                value = parseInt(minute.replace(/^0/, ''));
                date.setMinutes(value);
            }

            if (position.second != -1) {
                value = parseInt(second.replace(/^0/, ''));
                date.setSeconds(value);
            }
            return date;
        }
    },
    /*增加日期*/
    dateAdd: function (date, interval, offset) {
        var number = 0;
        switch (interval) {
            case 's':
                number = 1000;
                break;
            case 'n':
                number = 1000 * 60;
                break;
            case 'h':
                number = 1000 * 60 * 60;
                break;
            case 'd':
                number = 1000 * 3600 * 24;
                break;
            case 'm':
                number = 1000 * 3600 * 24 * 30;
                break;
            case 'y':
                number = 1000 * 3600 * 24 * 365;
                break;
            default:
                number = 0;
        }
        var time = date.getTime();
        time += number * offset;
        return new Date(time);
    }

};

/**
 * 排序法命名空间
 */
jQuery.sort = {

    /**
     * 默认的升序比较接口，提供基本类型比较大小结果
     * @param o1
     * @param o2
     * @returns 小于0表示 o1 < o2, 等于0表示 o1 = o2, 大于0表示 o1 > o2
     */
    ascComparator: function (o1, o2) {
        if (typeof o1 == 'string' && typeof o2 == 'string') {
            if (o1 < o2) return -1;
            if (o1 > o2) return 1;
            return 0;
        } else if (typeof o1 == 'number' && typeof o2 == 'number') {
            if (o1 < o2) return -1;
            if (o1 > o2) return 1;
            return 0;
//			return o1 - o2;
        }
        return 0;
    },
    /**
     * 默认的降序比较接口，提供基本类型比较大小结果
     * @param o1
     * @param o2
     * @returns 小于0表示 o1 < o2, 等于0表示 o1 = o2, 大于0表示 o1 > o2
     */
    descComparator: function (o1, o2) {
        if (typeof o1 == 'string' && typeof o2 == 'string') {
            if (o1 > o2) return -1;
            if (o1 < o2) return 1;
            return 0;
        } else if (typeof o1 == 'number' && typeof o2 == 'number') {
            return o2 - o1;
        }
        return 0;
    },
    /**
     * 冒泡算法的实现
     * @param array 数组
     * @param comparator 比较接口
     */
    bubbleSort: function (array, comparator) {
        if (comparator == undefined) comparator = $.sort.ascComparator;
        var comp;
        for (var i = 0; i < array.length - 1; i++) {
            for (var j = array.length - 1; j > 0; j--) {
                comp = comparator(array[j - 1], array[j]);
                if (comp > 0) {
                    var temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }
        }
    },
    /**
     * 插入排序法，性能略好于冒泡排序
     * @param array
     * @param comparator
     */
    insertSort: function (array, comparator) {
        if (comparator == undefined) comparator = $.sort.ascComparator;
        var temp, comp;
        for (var i = 1; i < array.length; i++) {
            var temp = array[i];
            for (var j = i; j > 0; j--) {
                comp = comparator(array[j - 1], temp);
                if (comp > 0) {
                    array[j] = array[j - 1];
                    continue;
                }
                break;
            }
            array[j] = temp;
        }
    },
    /**
     * 选择排序
     * @param array
     * @param comparator
     */
    selectSort: function (array, comparator) {
        if (comparator == undefined) comparator = $.sort.ascComparator;
        var min, temp, comp;
        for (var i = 0; i < array.length; i++) {
            min = i;
            for (var j = i + 1; j < array.length; j++) {
                comp = comparator(array[min], array[j]);
                if (comp > 0)
                    min = j;
            }
            if (min != i) {
                temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            }
        }
    }
};

/**
 * 字典命名空间
 */
jQuery.dict = {

    /**
     * baseurl 属性：访问服务器取得字典数据的 url, 如果有变化，可以在脚本开始时用 : $.dict.baseurl = 'myUrl' 来重新设置。
     */
    baseurl: contextPath + '/dictItem/getDictItems',
    /**
     * 字典中的数据集合，默认没有任何数据
     */
    values: {},
    /**
     * 初始化一个字典
     * @param dictName 字典的名称
     */
    init: function (dictCode) {
        var baseurl = $.dict.baseurl;
        if (baseurl.substring(baseurl.length - 1, baseurl.length) != '/') baseurl = baseurl + '/';
        $.ajax({
            async: false,
            type: "POST",
            url: baseurl + dictCode,
            dataType: "json",
            success: function (data) {
                $.dict.set(dictCode, data);
            }
        });
    },
    /**
     * 获得一个字典数据，类型为数组, 数组中的对象属性为 name 和 value.
     * @param dictName 字典名称
     * @returns 对象数组
     */
    get: function (dictCode) {
        if ($.dict.values[dictCode] == undefined) {
            $.dict.init(dictCode);
        }
        return $.dict.values[dictCode];
    },
    /**
     * 设置一个字典数据，类型为数据
     * @param dictName 字典名称
     * @param data  数据
     */
    set: function (dictCode, data) {
        if (data == undefined || data == null) {
            $.dict.values[dictCode] = {};
        } else {
            $.dict.values[dictCode] = data;
        }
    },
    /**
     * 从字典中获得和指定值对应的 label
     * @param dictName  字典名称
     * @param value       值
     * @returns              字典的label
     */
    getLabel: function (dictCode, value) {
        var data = $.dict.get(dictCode);
        for (var i = 0; i < data.length; i++) {
            if (value == data[i].value) {
                return data[i].name;
            }
        }
    }
};

/**
 * 数据字典的命名空间
 */
jQuery.fixedDict = {
    /**
     * baseurl 属性：访问服务器取得字典数据的 url, 如果有变化，可以在脚本开始时用 : $.fixedDict.baseurl = 'myUrl' 来重新设置。
     */
    baseurl: contextPath + '/fixedDict/',
    /**
     * 字典中的数据集合，默认没有任何数据
     */
    values: {},
    /**
     * 初始化一个字典
     * @param dictName 字典的名称
     */
    init: function (dictName) {
        var baseurl = $.fixedDict.baseurl;
        if (baseurl.substring(baseurl.length - 1, baseurl.length) != '/') baseurl = baseurl + '/';
        $.ajax({
            async: false,
            type: "POST",
            url: baseurl + dictName,
            dataType: "json",
            success: function (data) {
                $.fixedDict.set(dictName, data);
            }
        });
    },
    /**
     * 获得一个字典数据，类型为数组, 数组中的对象属性为 name 和 value.
     * @param dictName 字典名称
     * @returns 对象数组
     */
    get: function (dictName) {
        if ($.fixedDict.values[dictName] == undefined) {
            $.fixedDict.init(dictName);
        }
        return $.fixedDict.values[dictName];
    },
    /**
     * 设置一个字典数据，类型为数据
     * @param dictName 字典名称
     * @param data  数据
     */
    set: function (dictName, data) {
        if (data == undefined || data == null) {
            $.fixedDict.values[dictName] = {};
        } else {
            $.fixedDict.values[dictName] = data;
        }
    },
    /**
     * 从字典中获得和指定值对应的 label
     * @param dictName  字典名称
     * @param value       值
     * @returns              字典的label
     */
    getLabel: function (dictName, value) {
        var data = $.fixedDict.get(dictName);
        for (var i = 0; i < data.length; i++) {
            if (value == data[i].value) {
                return data[i].name;
            }
        }
    }
};


/**
 * 页面数据缓存，在 dict 和 fixedDict 的基础上扩展，
 * 支持自定义的数据来源, 支持前端过滤
 * 可以取代 dict 和 fixedDict
 */
jQuery.cache = {

    /*存储数据的对象*/
    _values: {},

    /*运算符*/
    _opers: {
        'EQ': '==',
        'NE': '!=',
        'GT': '>',
        'LT': '<',
        'GE': '>=',
        'LE': '<='
    },

    /**
     * 缓存数据，请求是同步的。
     * @param type         数据类型， dict/fixedDict/custom
     * @param url          对于 dict， 为字典名称，对于 fixedDict 为类名，对于 custom， 为url
     * @param code         缓存中的键值，如果不提供，默认等于 url
     */
    cache: function (type, url, code) {
        if (!type || !url) return;

        var _url = null;
        var _c = code ? code : url;
        if (this._values[_c]) {
            return;
        }

        if (type === 'dict') {
            _url = contextPath + '/dictItem/getDictItems/' + url;
        } else if (type === 'fixedDict') {
            _url = contextPath + '/fixedDict/' + url;
        } else {
            _url = url;
        }

        if (_url == null) return;

        var _s = this;
        $.ajax({
            async: false,
            type: "POST",
            url: _url,
            dataType: "json",
            success: function (data) {
                _s._values[_c] = data;
            }
        });
    },

    /**
     * 从缓存中获取数据,dict/fixedDict的数据均为 {name:xxx, value:xxx} 格式, custom 的为对象原有格式
     * @param code 缓存名称
     * @param value 需要过滤的字段
     * @param includes 需要包括的值
     * @param excludes 需要排除的值
     */
    getFilteredCache: function (code, valueField, includes, excludes) {
        if (!valueField) return this.getCache(code);

        var _fs = [];
        if (excludes && excludes != '' && excludes != 'null') {
            var _exc = excludes.split(',');
            if (_exc && _exc.length > 0) {
                for (var i = 0; i < _exc.length; i++) {
                    var _eo = {
                        name: 'NE_' + valueField,
                        value: _exc[i]
                    };
                    _fs.push(_eo);
                }
            }
        }

        var _fv = this.getCache(code, _fs);
        var _nfv = [];

        if (includes && includes != '' && includes != 'null') {
            var _inc = includes.split(',');
            for (var i = 0; i < _fv.length; i++) {
                var _v = _fv[i];
                if (_v[valueField] === '') {
                    _nfv.push(_v);
                    continue;
                }

                for (var j = 0; j < _inc.length; j++) {
                    if (_v[valueField] === _inc[j]) _nfv.push(_v);
                }
            }
        } else {
            _nfv = _fv;
        }

        return _nfv;
    },

    /**
     * 从缓存中获取数据，dict/fixedDict的数据均为 {name:xxx, value:xxx} 格式, custom 的为对象原有格式
     * @param code       缓存的名称
     * @param filters    数组，需过滤数据的条件。格式：[{name:'EQ_controlType', value:'xxx'}]
     */
    getCache: function (code, filters) {
        if (!code) return;

        var _vs = this._values[code];

        if (!filters) return _vs;

        if (!_vs || !_vs.length) return;

        var _fs = [];
        for (var i = 0; i < filters.length; i++) {
            var _f = filters[i];
            var _n = _f.name;
            var _v = _f.value;
            if (!/^(\w{2})\_(\w+)$/g.test(_n)) continue;
            var _p = _n.split('_');
            var _o = this._opers[_p[0]];
            var _pn = _p[1];
            _fs.push(_pn + ' ' + _o + ' \'' + _v + '\'');
        }

        var _rvs = [];
        for (var i = 0; i < _vs.length; i++) {
            var _o = _vs[i];
            var _val = true;
            for (var j = 0; j < _fs.length; j++) {
                try {
                    _val = Boolean(eval('with(_o){' + _fs[j] + '}'));
                    if (!_val) break;
                } catch (e) {
                }
            }
            if (!_val) continue;
            _rvs.push(_o);
        }
        return _rvs;
    },

    /**
     * 从缓存中获取指定的缓存数据，用来为 datagrid 提供 formatter 函数
     * @param code          缓存代码
     * @param value         值
     * @param nameField     名称字段， dict/fixedDict 无需提供
     * @param valueField    值字段， dict/fixedDict 无需提供
     */
    getLabel: function (code, value, nameField, valueField) {
        if (!code || !value) return;
        var _nf = nameField ? nameField : 'name';
        var _vf = valueField ? valueField : 'value';
        var _vs = this._values[code];

        if (!_vs || !_vs.length) return;
        for (var i = 0; i < _vs.length; i++) {
            if (value == _vs[i][_vf]) return _vs[i][_nf];
        }
    }
};


/**
 * 消息格式化的命名空间
 */
jQuery.message = {
    /**
     * 格式化一个消息文本
     * 可以传递多个附加参数给该函数
     * format 函数将根据参数的顺序对 msg 中的占位符进行格式化
     * msg 中的占位符格式 ： {0}, {1} ....
     * 除第一个参数外，还可以传递多个参数，按参数的顺序进行替换
     * 如果参数列表的第二个参数为数组，则忽略其他参数，
     * 用数组中的元素按顺序替换
     * @param msg
     */
    format: function (msg) {
        if (msg == undefined) return;
        if (typeof msg != 'string') return;

        /*获得除msg 之外的其他参数*/
        var args = Array.prototype.slice.call(arguments, 1);
        if (args == undefined || args == null || args.length == 0) return msg;
        if ($.isArray(args[0])) {
            var arr = args.pop();
            args = [];
            args = args.concat(arr);
        }

        var formatMsg = msg;
        /*定义正则表达式*/
        var reg = /(\{\s*\d+\s*})/g;
        var result = msg.match(reg);
        /*如果能匹配字符串*/
        if (result != null && $.isArray(result) && result.length > 0) {
            var nums = [];
            for (var i = 0; i < result.length; i++) {
                var value = result[i];
                value = value.replace(/\s+/g, '');
                value = value.replace(/\{|\}/g, '');
                value = parseInt(value);
                nums.push(value);
            }
            /*排序*/
            $.sort.selectSort(nums);
            for (var i = 0; i < nums.length; i++) {
                if (args[nums[i]] == undefined || args[nums[i]] == null) continue;
                reg = new RegExp('\\{\\s*' + nums[i] + '\\s*\\}');
                formatMsg = formatMsg.replace(reg, args[nums[i]]);
            }
        }
        return formatMsg;
    },
    /**
     * 处理文本中的占位符
     * 返回处理后的字符串，原串不进行处理
     * 占位符的表示方法为：{place} = record.place, {holder} = record.holder
     * 占位符中只允许出现大小写英文字符，数字，下划线和 $
     * 占位符中，大括号与属性名之间的空格将被忽略
     * 同样的占位符出现多次，都将被替换
     * 使用record对象中的同名属性替换字符串中的占位符，并返回
     * record的属性只支持 string， number 和 array
     * 如果是array类型，则将array中的所有元素用 , 分隔连接成一个字符串，替换掉相应的占位符。
     * @param str             要处理的字符串
     * @param record      进行替换的对象
     * @returns                  替换后的字符串
     */
    resolvePlaceholder: function (str, record) {
        /*要求第一个参数必须为 string, 第二个参数必须提供*/
        if (typeof str != 'string') return;
        if (record == undefined || record == null) return str;
        /*新字符串用于替换*/
        var newStr = str;
        /*如果包含占位符，进行处理*/
        if (hasPlaceholder(str)) {
            var reg = /(\{\s*[\w\$]+\s*\})/g;
            var result = str.match(reg);
            if (result != undefined && result != null) {
                for (var i = 0; i < result.length; i++) {
                    var pattern = result[i];
                    var property = pattern.replace(/\{|\}/g, '').trim();
                    if (record[property] == undefined || record[property] == null) continue;
                    var value = resolveProperty(record[property]);
                    if (value == null) continue;
                    newStr = newStr.replace(pattern, value);
                }
            }
        }
        return newStr;

        /*闭包函数，判断字符串中是否包含占位符*/
        function hasPlaceholder(str) {
            var reg = /\{\s*[\w\$]+\s*\}/g;
            return reg.test(str);
        };

        /*将参数转换为字符串*/
        function resolveProperty(value) {
            if (typeof value == 'string') return value;
            if (typeof value == 'number') return String(value);
            if ($.isArray(value)) {
                return value.toString();
            }
            return null;
        };
    }
};


/**
 * 事件命名空间
 * 2012/4/8 添加
 */
jQuery.events = {
    /*禁止右键菜单*/
    disableContextMenu: function () {
        $(document.body).unbind('.risk').bind('contextmenu.risk', function (e) {
            e.preventDefault();
        });
    }

};


/**
 * 编码解码
 */
jQuery.encoder = {
    encode: function (value) {
        return encodeURIComponent(value);
    },
    encodeEx: function (value) {
        var uri = encodeURIComponent(value);
        return uri.replace(/\%/g, '|');
    }
};

jQuery.decoder = {
    decode: function (value) {
        return decodeURIComponent(value);
    },
    decodeEx: function (value) {
        var uri = value.replace(/\|/g, '%');
        return decodeURIComponent(uri);
    }
};

/**
 * 导入命名空间
 */
jQuery.importData = {
    /*导入*/
    doImport: function (code, url, callback, params) {
        var args = {};
        args.templateCode = code;
        args.url = url;
        //if(template != undefined) args.template = template;
        if (callback != undefined) args.callback = callback;
        if (params != undefined) args.params = params;

        var title = '导入数据';
        $.ajax({
            async: false,
            type: 'POST',
            url: contextPath + '/bas/import/getParam',
            data: {code: code},
            success: function (data) {
                title = '导入' + data.title;
            }
        });
        openWindow('wImport', title, contextPath + '/bas/import/import', {
            inFrame: true,
            size: '',
            width: 600,
            height: 400,
            windowArgs: args
        });
    },
    addTemplate: function (template) {
        /*转换成 json 串*/
        var data = $.toJSON(template);
        var success = false;
        $.ajax({
            async: false,
            type: 'POST',
            url: contextPath + '/bas/import/addTemplate',
            data: {data: data},
            success: function (data) {
                success = data.success;
            }
        });
        return success;
    }
};


/**
 * 工具方法合集
 */
jQuery.tools = {

    /**
     * 用于将datagrid中的多行的某个字段连接成一个字符串
     * 通常用于向后台传递多条记录的id时非常有用。
     */
    join: function (rows, field) {
        if (!rows || !field) return '';
        var _r = [];
        for (var i = 0; i < rows.length; i++) {
            _r.push(rows[i][field]);
        }
        return _r.join(',');
    },

    /**
     * 启用某个按钮
     */
    enablebutton: function (id) {
        var btn = $(/^\#(.+)/g.test(id) ? id : '#' + id);
        if (btn && btn.length > 0) {
            try {
                btn.linkbutton('enable');
            } catch (e) {
            }
            try {
                btn.attr('disabled', false);
            } catch (e) {
            }
        }
    },

    disablebutton: function (id) {
        var btn = $(/^\#(.+)/g.test(id) ? id : '#' + id);
        if (btn && btn.length > 0) {
            try {
                btn.linkbutton('disable');
            } catch (e) {
            }
            try {
                btn.attr('disabled', true);
            } catch (e) {
            }
        }
    }

};

//增加图片阅览器 start  2014/03/28
jQuery.imageViewer = {

    show: function (options) {
        var topPage = $.imageViewer.getTopPage();
        topPage.showImageViewer(options);
    },

    getTopPage: function (current) {
        var win;
        if (!current) {
            win = top;
        } else {
            win = current.top;
        }

        if (!win.isIndex) return arguments.callee(win);

        return win;
    }

};
//增加图片阅览器 end  2014/03/28

