/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';


    var JDatetimepicker = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
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
    JDatetimepicker.DEFAULTS = {
        format: null,
        name: null
    };

    JDatetimepicker.EVENTS = [];

    /**
     * 初始化
     */
    JDatetimepicker.prototype.init = function () {

        var $this = this, $opt = this.options;
        /*初始化datetimepicker参数*/
        var dp = {
            language: 'zh-CN',
            autoclose: true,
            weekStart: 1,
            minView: 2,
            maxView: 4,
            todayHighlight: true,
            todayBtn: true,
            pickerPosition: 'bottom-right'
        };
        if ($opt['format']) {
            dp['format'] = $opt['format'];
        }
        console.info(dp);
        $(this.$el[0]).datetimepicker(dp).on('changeDate', function (e) {
            //$(this.$el[0]).parents(['form']).formValidation('updateStatus', $opt['name'], 'NOT_VALIDATED').formValidation('validateField', $opt['name']);
        });
    };


    /**
     * 可调用的方法
     */
    var allowedMethods = [];

    $.fn.jDatetimepicker = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jDatetimepicker'),
                options = $.extend({}, JDatetimepicker.DEFAULTS, $this.data(),
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
                    $this.removeData('bootstrap.jDatetimepicker');
                }
            }

            if (!data) {
                $this.data('bootstrap.jDatetimepicker', (data = new JDatetimepicker(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jDatetimepicker.Constructor = JDatetimepicker;
    $.fn.jDatetimepicker.defaults = JDatetimepicker.DEFAULTS;
    $.fn.jDatetimepicker.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jDatetimepicker"]').jDatetimepicker();
    });

}(jQuery);