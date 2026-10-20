/**
 * report 的封装
 * 用于在界面显示一个带选择按钮的输入框，从指定的url读取数据
 * 外部依赖：jquery.js, jquery-easyui.js, common.js, windlg.js
 */
;(function ($) {

    /**
     * 用于在某个对象上存取 option
     */
    function _options(target, opts) {
        /*未给出第二个参数，取回option*/
        if (opts == undefined) {
            var data = $.data(target, 'report');
            if (data) {
                return data.options;
            }
            return null;
        }
    }

    /**
     * 初始化 report
     */
    function _init(target) {
        var opts = _options(target);

        /*增加报表容器样式*/
        $(target).addClass('report');
        /*新增iframe，用于显示报表*/
        $('<iframe frameborder="no" src=""></iframe>').appendTo(target);

        var fullUrl = contextPath + '/frameset?__report=' + opts.url + '.' + opts.suffix;
        opts.fullUrl = fullUrl;

        /*关联查询表单*/
        if (opts.queryForm && opts.queryForm.id && opts.queryForm.query) {
            var query = '#' + opts.queryForm.query;
            $(query).unbind('click').click('report', function () {
                _query(target);
            });
        }

        /*关联重置按钮*/
        if (opts.queryForm && opts.queryForm.id && opts.queryForm.reset) {
            var reset = '#' + (opts.queryForm.reset == undefined ? 'btnReset' : opts.queryForm.reset);
            $(reset).unbind('click').click(function (event) {
                $('#' + opts.queryForm.id).form('clear');
            });
        }

        /*判断是否自动加载，自动加载是没有参数的情况*/
        if (opts.autoLoad) {
            $(target).children('iframe').attr('src', opts.fullUrl);
        }
    }

    function _query(target) {
        var opts = _options(target);
        if (opts.queryForm) {
            /*去的查询表单对象*/
            var queryForm = opts.queryForm;
            if (queryForm.id) {
                var queryFormId = '#' + queryForm.id;
                /*增加校验表单功能*/
                var r = $(queryFormId).form('validate');
                if (!r) {
                    return;
                }

                /*将查询字段序列化为 JSON 对象*/
                var fields = $(queryFormId).serializeArray();
//			var failed = false;
                var params = opts.params ? opts.params : {};
                var queryParams = {};

                $.each(fields, function (idx, field) {
                    var values = [];
                    if (queryParams[field.name] !== undefined) {
                        values = queryParams[field.name];
                    }
                    values.push(field.value);
                    queryParams[field.name] = values;
                });

                $.each(queryParams, function (name, value) {
                    var vv = value.join(',');
                    if (opts.prepare && $.isFunction(opts.prepare)) {
                        v = opts.prepare(name, vv);
                    }
                    params[name] = v;
                });

//			if(failed)
//			{
//				$.messager.alert('提示', '请输入查询条件', 'info');
//				return;
//			}
//			else
//			{
                var parr = [];
                $.each(params, function (name, value) {
                    parr.push(name + '=' + value);
                });

                var url = opts.fullUrl + '&' + parr.join('&');
                if (opts.beforeQuery && $.isFunction(opts.beforeQuery)) {
                    var r = opts.beforeQuery(url, params);
                    if (r) {
                        $(target).children('iframe').attr('src', url);
                    }
                } else {
                    $(target).children('iframe').attr('src', url);
                }
//			}
            }
        }
    }


    /**
     * 销毁一个report对象
     */
    function _destroy(target) {
        /*清空数据*/
        $.removeData(target, 'report');
        $(target).empty();
    }

    /**
     * report 定义
     */
    $.fn.report = function (options) {
        /*如果第一个参数为 string 类型，则为调用方法，其他的参数作为方法参数*/
        if (typeof options == 'string') {
            /*将参数中的第一个参数去掉，剩下的作为参数传入方法调用*/
            var args = Array.prototype.slice.call(arguments, 1);
            /*返回值*/
            var res = undefined;
            this.each(function () {
                /*判断传入的方法是否有定义，如果有，在当前的jQuery对象上进行调用*/
                if ($.isFunction($.fn.report.methods[options])) {
                    var r = $.fn.report.methods[options].apply(this, args);
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

        /*初始化 report*/
        options = options || {};
        return this.each(function () {
            /*取回在当前对象上上存放的选项*/
            var data = $.data(this, 'report');
            /*
             * 该对象上保存过数据，说明该对象存在，先销毁，再重建。
             */
            if (data) {
                _destroy(this);
            }
            /*
             * 用 defaults 和新的 option 合并，建立新的 report
             */
            var innerOpts = $.extend({}, $.fn.report.defaults, options);
            /*将数据保存回当前对象*/
            $.data(this, 'report', {options: innerOpts});

            /*初始化report*/
            _init(this);
        });
    };

    /**
     * report 默认参数定义
     */
    $.fn.report.defaults = {

        suffix: 'rptdesign',

        /*报表文件的相对路径*/
        url: '',

        /*是否自动加载*/
        autoLoad: false,

        queryForm: {
            id: 'queryForm',
            query: 'btnQuery',
            reset: 'btnReset'
        },

        /*在请求之前，对参数做最后的处理*/
        prepare: function (name, value) {
            return value;
        },

        /*在查询之前，最后一次机会可以取消对报表的查询, url 中已经包含了参数*/
        beforeQuery: function (url, params) {
            return true;
        }

    };


    /**
     * report 的方法定义
     */
    $.fn.report.methods = {
        options: function () {
            return _options(this);
        },
        /*销毁 report*/
        destroy: function () {
            _destroy(this);
        },
        enable: function () {
            _enable(this);
        },
        disable: function () {
            _disable(this);
        },
        setValue: function (data) {
            _setValue(this, data);
        },
        getValue: function () {
            return _getValue(this);
        },
        clear: function () {
            _clear(this);
        }
    };

})(jQuery);