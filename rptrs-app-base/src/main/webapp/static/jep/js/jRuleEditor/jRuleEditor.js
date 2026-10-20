/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';


    var jRuleEditor = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        if (this.options) {
            $.extend(this.options, {'id': this.$el.attr('id')});
        }
        this.container = document.querySelector('#' + options.id);
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
    jRuleEditor.DEFAULTS = {
        multiple: null
    };

    jRuleEditor.EVENTS = [
        'addConditions'
    ];

    /**
     * 存放当前编辑器编辑的数据
     * 如果 : when
     * []
     * 那么 : then
     * []
     *      : end
     */
    jRuleEditor.EDITORDATA = {
        when: 'when',
        data0: [],
        then: 'then',
        data1: [],
        end: 'end'
    };

    jRuleEditor.OPERATES = {
        "==": "等于",
        ">": "大于",
        ">=": "大于等于",
        "<": "小于",
        "<=": "小于等于",
        "!=": "不等于",
        "memberOf": "属于",
        "not memberOf": "不属于"
    }; //操作符
    jRuleEditor.LOGICS = {
        "": "空",
        "and": "并且",
        "or": "或者",
        "and (": "并且(",
        "or (": "或者(",
        ") and": ") 并且",
        ") or": ") 或者",
        "(": "(",
        ")": ")"
    };//逻辑操作


    /**
     * 初始化
     */
    jRuleEditor.prototype.init = function () {
        var $this = this, $opt = this.options;
        console.info($opt.oldValue);
        console.log($opt.ruleCode);
        var html = [];
        html.push('<div style="overflow:auto;background-color:#eeeeee;height:300px">');
        html.push(this.getHtml());
        html.push('</div>');
        $('#' + $opt.id).html(html.join(''));

        this.EventHandlers();
    };

    /**
     * 获取html 文本
     */
    jRuleEditor.prototype.getHtml = function () {
        var $this = this, $opt = this.options;
        var script = (null == $opt.script || '' == $opt.script) ? jRuleEditor.EDITORDATA : $opt.script;
        console.info(script);
        var html = [];
        html.push('<div style="margin:10px;"><h2>如果：</h2></div>\n');
        html.push('<div id="lhs">');
        html.push('</div>');
        html.push('<div id="lhsMore">');
        html.push('<span class="add-condition">增加一个条件</span>');
        html.push('</div>');
        html.push('<div style="margin:10px;"><h2>那么：</h2></div>');
        html.push('<div id="rhs">');
        html.push('</div>');
        html.push('<div id="rhsMore">');
        html.push('<span class="obj" onclick="addActions(this)">插入新动作</span>');
        html.push('</div>');
        return html.join('');
    };

    //处理操作函数
    jRuleEditor.prototype.EventHandlers = function () {
        var container = this.container;
        var _this = this;
        console.info(container);
        //操作条件
        var addCondition = container.querySelector('.add-condition');
        addCondition.onclick = function () {
            var data0 = jRuleEditor.EDITORDATA.data0;
            var _this0 = this;
            var html = '<div id=\"row_' + data0.length + parseInt(1) + '\" rowIndex=\"' + data0.length + 1 + '\">';
            html += '<a class=\"logic\" rowIndex=\"' + (parseInt(data0.length) + parseInt(1)) + '\">请选择</a>';
            html += "</div>";
            var data = [];
            data0.push(data);
            $("#lhs").append(html);

            //弹出选择框操作
            var logic = container.querySelector('.logic');
            logic.onclick = function () {
                var _this1 = this;
                var data0 = jRuleEditor.EDITORDATA.data0;
                var logics = jRuleEditor.LOGICS;
                var rowInex = $(this).attr("rowIndex");
                var data = data0[rowInex - 1];
                var html = '<div style="width:100px;">';
                for (var k in logics) {
                    html += '<a href="javaScript: void(0);" colIndex="' + (parseInt(data.length) + parseInt(1)) + '" code="' + k + '"  data-el-id="logic" class="item">' + logics[k] + '</a>';
                }
                html += '</div>';
                _this.showPoper(this, html);

                //对选择逻辑运算符操作
                var items = container.querySelectorAll('.item');
                $.each(items, function (i, item) {
                    var data0 = jRuleEditor.EDITORDATA.data0;

                    //具体选择
                    $(item).on('click', function () {
                        var _this2 = this;
                        var rowInex = $(_this1).attr("rowIndex");
                        var colIndex = $(_this2).attr("colIndex");
                        var obj = data0[rowInex - 1][colIndex - 1];
                        console.info(obj);
                        var popid = '#' + $(_this2).attr('data-el-id');
                        if ($(_this2).is('input')) {
                            var val = $(_this2).val();
                            $(_this1).text(val);
                        } else {
                            var val = $(_this2).text();
                            $(_this1).text(val)
                        }
                        $(_this1).popover("hide");
                        obj = {
                            name: $(_this2).text(),
                            code: $(_this2).attr('code')
                        }
                        data0[rowInex - 1][colIndex - 1] = obj;
                    });
                });
            };
        };
    }

    //显示弹出框
    jRuleEditor.prototype.showPoper = function (el, html) {
        $(el).popover({
            trigger: 'manual',
            placement: 'right',
            html: 'true',
            content: html,
            animation: false
        }).on("mouseleave", function () {
            var _this = this;
            setTimeout(function () {
                if (!$(".popover:hover").length) {
                    $(_this).popover("hide")
                }
            }, 100);
        }).popover("show");
    }

    /**
     * 获取编辑器编辑后的脚本
     */
    jRuleEditor.prototype.getSoureCode = function () {
        var $this = this, $opt = this.options;
        var script = (null == $opt.script || '' == $opt.script) ? jRuleEditor.EDITORDATA : $opt.script;

        var file = [];
        file.push('rule "' + $opt.code + '"');
        file.push('\n' + script.when);
        file.push('\n' + '123');
        file.push('\n' + script.then);
        file.push('\n' + '345');
        file.push('\n' + script.end);

        return file;
    };

    /**
     * 可调用的方法
     */
    var allowedMethods = [];
    allowedMethods.push("getSoureCode");

    $.fn.jRuleEditor = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jRuleEditor'),
                options = $.extend({}, jRuleEditor.DEFAULTS, $this.data(),
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
                    $this.removeData('bootstrap.jRuleEditor');
                }
            }

            if (!data) {
                $this.data('bootstrap.jRuleEditor', (data = new jRuleEditor(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jRuleEditor.Constructor = jRuleEditor;
    $.fn.jRuleEditor.defaults = jRuleEditor.DEFAULTS;
    $.fn.jRuleEditor.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jRuleEditor"]').jRuleEditor();
    });

}(jQuery);