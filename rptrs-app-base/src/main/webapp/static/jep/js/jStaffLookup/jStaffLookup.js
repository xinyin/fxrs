/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';
    var _SELECT_TYPE_ALL_UNIT = 'allUnit'; //所有的机构
    var _SELECT_TYPE_HEAD_DEPT = 'headDept'; //总行所有的部门
    var _SELECT_TYPE_LS_UNIT = 'lsUnit'; //总行部门+所有机构
    var _SELECT_TYPE_OWN_UNIT = 'ownUnit'; //本机构
    var _SELECT_TYPE_OWN_DEPT = 'ownDept'; //本部门
    var _SELECT_TYPE_OWN_BIZ = 'ownBiz'; //本条线


    var JStaffLookup = function (el, options) {
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
    JStaffLookup.DEFAULTS = {
        /*字段的名称*/
        name: undefined,
        /*打开页面的 url*/
        url: '/sys/staff/dialog',
        /*打开页面的标题*/
        title: '选择人员',
        /*窗口id*/
        winId: 'jStaffSelect',
        /*窗口宽度*/
        width: 1350,
        /*窗口高度*/
        height: 570,
        /*回调函数名，将通过windowArgs传递*/
        callback: 'result',
        /*打开页面返回对象的text属性，用于显示*/
        propText: 'text',
        /*打开页面返回对象的value属性，用于表单提交*/
        propValue: 'value',
        /*连接多个文本或值的连接符*/
        seperator: ',',
        /*选择后的默认执行方法*/
        onSelect: 'onSelect',
        /*默认为单选*/
        mult: false,
        /*默认为真*/
        lazy: true,
        /*默认包含参数*/
        includes: [],
        /*默认不包含参数*/
        excludes: [],
        /*标准角色编码*/
        roleCodes: undefined,
        /*选择树类型*/
        selectType: undefined,
        /*选择的机构*/
        unitCodes: undefined,
        /*是否禁用*/
        disabled: false

    };

    JStaffLookup.EVENTS = [];

    /**
     * 创建回调方法
     */
    JStaffLookup.prototype.createCallback = function () {

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
    JStaffLookup.prototype.getData = function () {
        return this.data ? this.data : null;
    }

    /**
     * 返回文本
     */
    JStaffLookup.prototype.getText = function () {
        return this.text ? this.text : null;
    }

    /**
     * 返回值
     */
    JStaffLookup.prototype.getValue = function () {
        return this.value ? this.value : null;
    }


    /**
     * 打开选择窗口
     */
    JStaffLookup.prototype.open = function () {
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

        /*根据选择机构树类型，设置包含和排除参数*/
        if ($opt.selectType) {
            /*查询所有的机构*/
            if ($opt.selectType == _SELECT_TYPE_ALL_UNIT) {
                if (jQuery.isArray($opt.includes)) {
                    /*组装一个includes条件*/
                    var include = {
                        kind: 'UNIT'
                    };
                    /*存入includes条件中*/
                    $opt.includes.push(include);
                } else {
                    /*不是数组就说明还没有存入包含条件，直接存入即可*/
                    $opt.includes = [{
                        kind: 'UNIT'
                    }];
                }
                /*查询总行的部门*/
            } else if ($opt.selectType == _SELECT_TYPE_HEAD_DEPT) {
                if (jQuery.isArray($opt.includes)) {
                    /*组装一个includes条件*/
                    var include = {
                        kind: 'DEPT',
                        hierarchy: 'HEAD'
                    };
                    /*存入includes条件中*/
                    $opt.includes.push(include);
                } else {
                    /*不是数组就说明还没有存入包含条件，直接存入即可*/
                    $opt.includes = [{
                        kind: 'DEPT',
                        hierarchy: 'HEAD'
                    }];
                }
                /*查询总行的部门*/
            } else if ($opt.selectType == _SELECT_TYPE_LS_UNIT) {
                if (jQuery.isArray($opt.includes)) {

                    /*组装一个includes条件存入includes条件中*/
                    $opt.includes.push({
                        kind: 'DEPT',
                        hierarchy: 'HEAD'
                    });
                    $opt.includes.push({
                        kind: 'UNIT'
                    });
                } else {
                    /*不是数组就说明还没有存入包含条件，直接存入即可*/
                    $opt.includes = [{
                        kind: 'DEPT',
                        hierarchy: 'HEAD'
                    }, {
                        kind: 'UNIT'
                    }];
                }
                /*查询本机构*/
            } else if ($opt.selectType == _SELECT_TYPE_OWN_UNIT) {
                if (jQuery.isArray($opt.includes)) {

                    /*组装一个includes条件存入includes条件中*/
                    $opt.includes.push({
                        unitScope: 'UNIT'
                    });
                } else {
                    /*不是数组就说明还没有存入包含条件，直接存入即可*/
                    $opt.includes = [{
                        unitScope: 'UNIT'
                    }];
                }
                /*查询本部门*/
            } else if ($opt.selectType == _SELECT_TYPE_OWN_DEPT) {
                if (jQuery.isArray($opt.includes)) {

                    /*组装一个includes条件存入includes条件中*/
                    $opt.includes.push({
                        unitScope: 'DEPT'
                    });
                } else {
                    /*不是数组就说明还没有存入包含条件，直接存入即可*/
                    $opt.includes = [{
                        unitScope: 'DEPT'
                    }];
                }
                /*查询本机构*/
            } else if ($opt.selectType == _SELECT_TYPE_OWN_BIZ) {
                if (jQuery.isArray($opt.includes)) {

                    /*组装一个includes条件存入includes条件中*/
                    $opt.includes.push({
                        unitScope: 'BIZ'
                    });
                } else {
                    /*不是数组就说明还没有存入包含条件，直接存入即可*/
                    $opt.includes = [{
                        unitScope: 'BIZ'
                    }];
                }
            }
        }
        /*根据选择机构代码，设置包含和排除参数*/
        if ($opt.unitCodes) {
            /*如果是显示多个机构，就循环取出来，组装成包含参数*/
            var unitCodes = $opt.unitCodes.split(',');
            $.each(unitCodes, function (i, v) {
                /*先判断是否是一个数组*/
                if (jQuery.isArray($opt.includes)) {
                    /*组装一个includes条件*/
                    var include = {
                        unitCode: v,
                        isRoot: false,
                        hasChildren: true
                    };
                    /*存入includes条件中*/
                    $opt.includes.push(include);
                } else {
                    /*不是数组就说明还没有存入包含条件，直接存入即可*/
                    $opt.includes = [{
                        unitCode: v,
                        isRoot: false,
                        hasChildren: true
                    }];
                }
            });
        }


        /*传入包含参数*/
        $args['includes'] = $opt.includes;
        /*传入排除参数*/
        $args['excludes'] = $opt.excludes;
        /*设置是否多选*/
        $args['mult'] = $opt.mult;
        /*设置是否懒加载*/
        $args['lazy'] = $opt.lazy;
        /*设置是否懒加载*/
        $args['roleCodes'] = $opt.roleCodes;

        /*设置是否多选*/
        var mult;
        if ($args['mult']) {
            mult = '?mult=true'
        } else {
            mult = '?mult=false'
        }

        $.messager.modal({
            url: $.helper.curl(base, $opt.url + mult),
            closable: true,
            width: $opt.width,
            height: $opt.height,
            title: $opt.title,
            winId: $opt.winId,
            args: $args
        });
    }


    /**
     * 禁用
     */
    JStaffLookup.prototype.disable = function () {
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
        $tf.off('mouseup.jStaffLookup');
        $btn.off('click.jStaffLookup');
    }

    /**
     * 启用
     */
    JStaffLookup.prototype.enable = function () {
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
        $tf.off('mouseup.jStaffLookup').on('mouseup.jStaffLookup', function (e) {
            $this.open();
        });
        $btn.off('click.jStaffLookup').on('click.jStaffLookup', function (e) {
            $this.open();
        });
    }

    /**
     * 设置值
     */
    JStaffLookup.prototype.setData = function (data) {
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
     * 设置值
     */
    JStaffLookup.prototype.cleanData = function () {
        var $tf = this.$el.find(".lookup-text");
        var $vf = this.$el.find(".lookup-value");

        $tf.val('');
        $vf.val('');
    }

    /**
     * 初始化
     */
    JStaffLookup.prototype.init = function () {

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
            $tf.off('mouseup.jStaffLookup').on('mouseup.jStaffLookup', function (e) {
                $this.open();
            });
        }

        /*为按钮增加事件处理*/
        var $btn = this.$el.find('.lookup-btn');
        if (!this.disabled) {
            $btn.off('click.jStaffLookup').on('click.jStaffLookup', function (e) {
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
    var allowedMethods = ['open', 'disable', 'enable', 'getData', 'getText', 'getValue', 'cleanData'];

    $.fn.jStaffLookup = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jStaffLookup'),
                options = $.extend({}, JStaffLookup.DEFAULTS, $this.data(),
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
                    $this.removeData('bootstrap.jStaffLookup');
                }
            }

            if (!data) {
                $this.data('bootstrap.jStaffLookup', (data = new JStaffLookup(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jStaffLookup.Constructor = JStaffLookup;
    $.fn.jStaffLookup.defaults = JStaffLookup.DEFAULTS;
    $.fn.jStaffLookup.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jStaffLookup"]').jStaffLookup();
    });

}(jQuery);