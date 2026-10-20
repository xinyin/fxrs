/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';


    var jSelect2 = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        if (this.options) {
            $.extend(this.options, {'id': this.$el.attr('id')});
            $.extend(this.options, {'url': this.$el.attr('url')});
            $.extend(this.options, {'value': this.$el.attr('value')});
            $.extend(this.options, {'headEmpty': this.$el.attr('headEmpty')});
        }
        this.init();
    };

    var calculateFunc = function (name) {
        var func = name;

        if (typeof name === 'string') {
            // support obj.func1.func2
            var names = name.split('.');

            if (names.length > 1) {
                func = window;
                $.each(names, function (i, f) {
                    func = func[f];
                });
            } else {
                func = window[name];
            }
        }

        if (typeof func === 'function') return func;
        return undefined;
    }

    /**
     * 默认属性，从 BootstrapTable 的默认属性扩展而来
     */
    jSelect2.DEFAULTS = {
        multiple: null
    };

    jSelect2.EVENTS = [];

    /**
     * 初始化
     */
    jSelect2.prototype.init = function () {

        var $this = this, $opt = this.options;
        /*初始化select2参数*/
        var option = {
            language: "zh-CN",
            theme: 'bootstrap',
            allowClear: false,
            placeholder: '请选择',
            minimumResultsForSearch: Infinity,
            fvNotemptyMessage: $opt.fvNotemptyMessage,
            fvNotempty: $opt.fvNotempty,
            escapeMarkup: function (markup) {
                return markup;
            }
        };
        $(this.$el[0]).select2(option).trigger('change');
    };

    /**
     * 重新加载select2
     */
    jSelect2.prototype.load = function (params) {
        var $this = this, $opt = $this.options;
        //获取参数
        var param = '';
        $.each(params, function (key, val) {
            param += key + '=' + val + '&';
        });
        //获取请求路径
        var url = $opt.url;
        url = url.indexOf('?') > -1 ? url + '&' + param : url + '?' + param;
        //重新加载数据
        $.ajax({
            url: url, dataType: 'json', delay: 200, success: function (data) {
                var array = new Array();
                $.each(data, function (i, val) {
                    var obj = {
                        id: val.value,
                        text: val.name
                    };
                    array[i] = obj;
                });
                $('#' + $opt.id).empty();
                $('#' + $opt.id).select2({data: array, width: "100%"}).trigger('change');
                if (params.value != null && params.value != '')
                    $('#' + $opt.id).val(params.value).trigger('change');
            }
        });
    };

    /**
     * 可调用的方法
     */
    var allowedMethods = [];
    allowedMethods.push("load");

    $.fn.jSelect2 = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jSelect2'),
                options = $.extend({}, jSelect2.DEFAULTS, $this.data(),
                    typeof option === 'object' && option);

            if (typeof option === 'string') {
                if ($.inArray(option, allowedMethods) < 0) {
                    throw new Error("Unknown method: " + option);
                }

                if (!data) {
                    return;
                }

                value = data[option].apply(data, args);
                if (option === 'destroy') {
                    $this.removeData('bootstrap.jSelect2');
                }
            }

            if (!data) {
                $this.data('bootstrap.jSelect2', (data = new jSelect2(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jSelect2.Constructor = jSelect2;
    $.fn.jSelect2.defaults = jSelect2.DEFAULTS;
    $.fn.jSelect2.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jSelect2"]').jSelect2();
    });

}(jQuery);