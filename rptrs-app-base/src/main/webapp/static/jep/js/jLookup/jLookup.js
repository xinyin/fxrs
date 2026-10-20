/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';

    var JLookup = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        this.$name = this.$el.attr('name');
        /*移除div上的name属性，因为这个属性会影响到 formValidation 的校验*/
        this.$el.removeAttr('name');
        this.disabled = false;
        this.data = undefined;
        this.text = undefined;
        this.value = undefined;
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
    JLookup.DEFAULTS = {
        /*字段的名称*/
        name: undefined,
        /*打开页面的 url*/
        url: undefined,
        /*打开页面的标题*/
        title: '标题',
        /*窗口id*/
        winId: '',
        /*窗口宽度*/
        width: 600,
        /*窗口高度*/
        height: 400,
        /*回调函数名，将通过windowArgs传递*/
        callback: 'result',
        /*打开页面返回对象的text属性，用于显示*/
        propText: 'text',
        /*打开页面返回对象的value属性，用于表单提交*/
        propValue: 'value',
        /*连接多个文本或值的连接符*/
        seperator: ',',
        /*是否禁用*/
        disabled: false

    };

    JLookup.EVENTS = [];

    /**
     * 重新加载数据
     */
    JLookup.prototype.reload = function (options) {
        $.extend(this.options, options);
        this.init();
    }

    /**
     * 创建回调方法
     */
    JLookup.prototype.createCallback = function () {

        var $this = this, $opt = $this.options;

        return function (data) {

            $this.setData(data);

            /*触发onSelect事件*/
            var $onSelect = calculateFunc($opt.onSelect);
            if ($onSelect) {
                $onSelect.apply($this, [data]);
            }
        };

    }

    /**
     * 返回选择的数据
     */
    JLookup.prototype.getData = function () {
        return this.data ? this.data : null;
    }

    /**
     * 返回文本
     */
    JLookup.prototype.getText = function () {
        return this.text ? this.text : null;
    }

    /**
     * 返回值
     */
    JLookup.prototype.getValue = function () {
        return this.value ? this.value : null;
    }


    /**
     * 打开选择窗口
     */
    JLookup.prototype.open = function () {
        if (this.disabled) return;
        var $this = this, $opt = this.options;

        /*调用用户定义的 beforeOpen 函数，确定是否打开对话框*/
        if (typeof $opt.beforeOpen === 'string') {
            var $beforeOpen = calculateFunc($opt.beforeOpen);
            if ($beforeOpen.apply(this, [this.data]) === false) return;
        }

        var $args = {};
        var $callback = $opt.callback || 'result';
        $args[$callback] = this.createCallback();
        $.messager.modal({
            url: $.helper.curl($opt.url),
            closable: true,
            width: $opt.width ? $opt.width : 600,
            height: $opt.height ? $opt.height : 300,
            title: $opt.title,
            winId: $opt.winId,
            args: $args
        });
    }

    /**
     * 禁用
     */
    JLookup.prototype.disable = function () {
        var $this = this, $opt = this.options;

        this.disabled = true;
        var $tf = this.$el.find(".lookup-text");
        var $vf = this.$el.find(".lookup-value");
        var $btn = this.$el.find('.lookup-btn');
        $tf.attr('disabled', 'disabled');
        $vf.attr('disabled', 'disabled');
        $btn.attr('disabled', 'disabled');
        $tf.addClass('lookup-text-disabled');
        $btn.addClass('lookup-btn-disabled');
        $btn.removeClass('btn-outline');
        $tf.off('mouseup.jLookup');
        $btn.off('click.jLookup');
    }

    /**
     * 启用
     */
    JLookup.prototype.enable = function () {
        var $this = this, $opt = this.options;

        this.disabled = false;
        var $tf = this.$el.find(".lookup-text");
        var $vf = this.$el.find(".lookup-value");
        var $btn = this.$el.find('.lookup-btn');
        $tf.removeAttr('disabled');
        $vf.removeAttr('disabled');
        $btn.removeAttr('disabled');
        $tf.removeClass('lookup-text-disabled');
        $btn.removeClass('lookup-btn-disabled');
        $btn.addClass('btn-outline');
        $tf.off('mouseup.jLookup').on('mouseup.jLookup', function (e) {
            $this.open();
        });
        $btn.off('click.jLookup').on('click.jLookup', function (e) {
            $this.open();
        });
    }

    /**
     * 设置值
     */
    JLookup.prototype.setData = function (data) {
        if (!data) return;
        var $this = this, $opt = this.options;

        if ($.isArray(data)) {
            this.data = data;
        } else {
            this.data = [data];
        }

        function _formatText(text, data) {
            var formatter = calculateFunc($opt.textFormatter);
            return formatter ? formatter(text, data) : text;
        };

        function _formatValue(value, data) {
            var formatter = calculateFunc($opt.valueFormatter);
            return formatter ? formatter(value, data) : value;
        };


        var $texts = [];
        var $values = [];

        if ($.isArray(this.data)) {
            $.each(this.data, function () {
                $texts.push(_formatText(this[$opt.propText], this));
                $values.push(_formatValue(this[$opt.propValue], this));
            });
        }

        this.text = $texts.join($opt.seperator);
        this.value = $values.join($opt.seperator);

        var $tf = this.$el.find(".lookup-text");
        var $vf = this.$el.find(".lookup-value");

        $tf.val(this.text);
        $vf.val(this.value);
    }

    /**
     * 初始化
     */
    JLookup.prototype.init = function () {

        var $this = this, $opt = this.options, html = [];
        this.disabled = $opt.disabled || false;

        html.push('<div class="input-group">');
        html.push('<input type="text" class="form-control lookup-text ');
        if (this.disabled) {
            html.push(' lookup-text-disabled" disabled="disabled" ');
        } else {
            html.push('" ');
        }
        if (typeof $opt.placeholder === 'string') {
            html.push(' placeholder="' + $opt.placeholder + '" ');
        }
        html.push(' name="' + this.$name + 'Text">');
        html.push('<input type="hidden" class="lookup-value" ');
        if (this.disabled) {
            html.push(' disabled="disabled" ');
        }
        html.push(' name="' + this.$name + '" >');
        html.push('<span class="input-group-btn">');
        html.push('<button type="button" class=" btn lookup-btn ');

        var $fg = this.$el.parent('div.form-group'), $type = 'default';
        if ($fg.length > 0) {
            if ($fg.hasClass('has-success')) {
                $type = 'primary';
            } else if ($fg.hasClass('has-warning')) {
                $type = 'warning';
            } else if ($fg.hasClass('has-error')) {
                $type = 'danger';
            }
        }

        if (/primary|warning|danger|default/g.test($type)) {
            html.push('btn-' + $type);
        }
        if (this.disabled) {
            html.push(' lookup-btn-disabled" disabled="disabled" ');
        } else {
            html.push(' btn-outline" ');
        }
        html.push('>');
        html.push('<i class="fa fa-search"></i></button>');
        html.push('</span>');
        html.push('</div>');
        if (typeof $opt.helpText === 'string') {
            html.push('<span class="help-block m-b-none">' + $opt.helpText + '</span>');
        }
        this.$el.html(html.join(''));


        /*为显示文本框增加校验属性和增加事件处理*/
        var $tf = this.$el.find(".lookup-text");
        $.each($opt, function (name, value) {
            if (name.startsWith('fv')) {
                $tf.attr('data-' + name.camel2minus(), value);
            }
        });

        if (!this.disabled) {
            $tf.off('mouseup.jLookup').on('mouseup.jLookup', function (e) {
                $this.open();
            });
        }

        /*为按钮增加事件处理*/
        var $btn = this.$el.find('.lookup-btn');
        if (!this.disabled) {
            $btn.off('click.jLookup').on('click.jLookup', function (e) {
                $this.open();
            });
        }

        /*设置默认值*/
        if ($opt.value) {
            this.setData($opt.value);
        }
    };


    /**
     * 可调用的方法
     */
    var allowedMethods = ['open', 'disable', 'enable', 'getData', 'getText', 'getValue', 'setData', 'reload'];

    $.fn.jLookup = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jLookup'),
                options = $.extend({}, JLookup.DEFAULTS, $this.data(),
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
                    $this.removeData('bootstrap.jLookup');
                }
            }

            if (!data) {
                $this.data('bootstrap.jLookup', (data = new JLookup(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jLookup.Constructor = JLookup;
    $.fn.jLookup.defaults = JLookup.DEFAULTS;
    $.fn.jLookup.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jLookup"]').jLookup();
    });

}(jQuery);