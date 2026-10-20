/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';


    var JTouchSpin = function (el, options) {
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
    JTouchSpin.DEFAULTS = {
        max: null,
        min: null,
        precision: null,
        step: null
    };

    JTouchSpin.EVENTS = [];

    /**
     * 初始化
     */
    JTouchSpin.prototype.init = function () {

        var $this = this, $opt = this.options;
        /*初始化TouchSpin参数*/
        var ts = {
            buttondown_class: 'btn btn-info',
            buttonup_class: 'btn btn-info',
            verticalbuttons: false
        };
        if ($opt['max']) {
            ts['max'] = $opt['max'];
        }
        if ($opt['min']) {
            ts['min'] = $opt['min'];
        }
        if ($opt['step']) {
            ts['step'] = $opt['step'];
        }
        if ($opt['precision']) {
            ts['decimals'] = $opt['precision'];
        }
        $(this.$el[0]).TouchSpin(ts);
    };


    /**
     * 可调用的方法
     */
    var allowedMethods = [];

    $.fn.jTouchSpin = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jTouchSpin'),
                options = $.extend({}, JTouchSpin.DEFAULTS, $this.data(),
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
                    $this.removeData('bootstrap.jTouchSpin');
                }
            }

            if (!data) {
                $this.data('bootstrap.jTouchSpin', (data = new JTouchSpin(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jTouchSpin.Constructor = JTouchSpin;
    $.fn.jTouchSpin.defaults = JTouchSpin.DEFAULTS;
    $.fn.jTouchSpin.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jTouchSpin"]').jTouchSpin();
    });

}(jQuery);