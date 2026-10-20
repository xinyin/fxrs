/**
 * lookup 的封装
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
            var data = $.data(target, 'lookup');
            if (data) {
                return data.options;
            }
            return null;
        }
    }

    /**
     * 初始化 lookup
     */
    function _init(target) {
        var opts = _options(target);

        var _parent = $(target).parent();
        var _id = $(target).attr('id');
        /*valueField, textField, valueRef, textRef 为必选项*/
        if (!opts.valueField || !opts.textField || !opts.valueRef || !opts.textRef) return;

        /*隐藏原有的输入框，建立新的用于显示的输入和隐藏域*/
        $(target).css('display', 'none');
        //var _lookup = $('<span class="lookup" for="' + _id + '"></span>').appendTo(_parent);
        $(target).after('<span class="lookup" for="' + _id + '"></span>');
        var _lookup = $(_parent).children('.lookup');

        /*创建隐藏域*/
        $('<input id="' + opts.valueField + '" name="' + opts.valueField + '" class="lookup-valuefield" type="hidden"/>').appendTo(_lookup);
//	var _textField = $('<input id="' + opts.textField + '" name="' + opts.textField + '" class="lookup-textfield"/>').appendTo(_lookup);

        /*创建panel及text*/
        var _panel = $('<span class="lookup-panel"></span>').appendTo(_lookup);
        var _textSpan = $('<span class="lookup-input"></span>').appendTo(_panel);
        var _text = $('<input class="lookup-text" id="' + opts.textField + '" name="' + opts.textField + '"/>').appendTo(_textSpan);
        /*设置文本框为只读，不响应退格键*/
        _text.attr('readOnly', true);
        _text.keydown('norespond8', function (e) {
            if (e.keyCode == 8 && $.browser.msie) {
                window.event.returnValue = false;
                return false;
            }
        });
        /*单击文本框时，弹出选择框*/
        if (opts.fireOnClickInput) {
            _text.click('lookup', function (e) {
                _showLookupWin(target);
            });
        }
        /*textField 值改变时同时改变显示框的值*/
//	_textField.bind('input', function(e){
//		_text.val(_textField.val());
//	});


        /*加载定义在原输入框上的样式*/
        _text.validatebox(opts);

        _lookup.outerWidth($(target).outerWidth());
        _text.height(18);
        if (opts.required) {
            _panel.outerWidth($(target).outerWidth() - 5);
            _text.outerWidth($(target).outerWidth() - 30);
            $('<span style="color:red;display:inline-block;width:3px;height:18px;vertical-align:top;padding-top:5px;">*</span>').appendTo(_lookup);
        } else {
            _panel.outerWidth($(target).outerWidth());
            _text.outerWidth($(target).outerWidth() - 25);
        }

        var _icon = $('<span class="' + opts.iconCls + ' lookup-btn"></span>').appendTo(_panel);
        /*点击图标时，弹出选择框*/
        _icon.click('lookup', function (e) {
            _showLookupWin(target);
        });

        if (opts.disabled) {
            _disable(target);
        }
    }


    /**
     * 显示弹出窗口
     */
    function _showLookupWin(target) {
        var opts = _options(target);
        var _args = {};
        _args = $.extend({}, _args, opts.windowArgs);

        /*调用事件函数，进一步处理参数*/
        if (opts.onWindowOpen && $.isFunction(opts.onWindowOpen)) {
            var _rtn = opts.onWindowOpen.call(target, _args);
            if (!_rtn) return;
        }
        /*为参数增加回调函数*/
        if (opts.callbackProp) {
            var _co = {};
            _co[opts.callbackProp] = _createCallback(target);
            _args = $.extend({}, _args, _co);
        }
        /*增加回显数据参数*/
        if (opts.showValues) {
            var _id = $(target).attr('id');
            var _lookup = $(target).parent().children('[for="' + _id + '"]');
            var _value = _lookup.children('.lookup-valuefield').val();
            if (_value) {
                var _vo = {values: _value};
                _args = $.extend({}, _args, _vo);
            }
        }

        if (_args) {
            openWindow(opts.windowId, opts.title, opts.url, {
                inFrame: true,
                size: '',
                width: opts.windowWidth,
                height: opts.windowHeight,
                windowArgs: _args
            });
        }
    }

    function _createCallback(target) {
        var opts = _options(target);

        function _formatText(value, data) {
            return opts.formatText ? opts.formatText(value, data) : value;
        };

        function _formatValue(value, data) {
            return opts.formatValue ? opts.formatValue(value, data) : value;
        };

        return function (data) {
            if (!data) return;
            /*如果返回为数组，则需要遍历数组的每个元素，取出其 textRef 和 valueRef*/
            var _textArr = [];
            var _valueArr = [];
            if ($.isArray(data)) {
                $.each(data, function (i, n) {
                    _textArr.push(_formatText(n[opts.textRef], n));
                    _valueArr.push(_formatValue(n[opts.valueRef], n));
                });
            } else {
                _textArr.push(_formatText(data[opts.textRef], data));
                _valueArr.push(_formatValue(data[opts.valueRef], data));
            }

            /*用指定连接符连接值*/
            var _text = _textArr.join(opts.separator);
            var _value = _valueArr.join(opts.separator);


            /*将值赋值给 textField 和  valueField*/
            var _id = $(target).attr('id');
            var _lookup = $(target).parent().children('[for="' + _id + '"]');
            _lookup.children('.lookup-textfield').val(_text);
            _lookup.children('.lookup-valuefield').val(_value);
            _lookup.children('.lookup-panel').children('.lookup-input').children('.lookup-text').val(_text);

            /*触发 onSelect 事件*/
            if (opts.onSelect) {
                opts.onSelect.call(target, data);
            }
        };
    }

    /**
     * 启用
     */
    function _enable(target) {
        var opts = _options(target);

        var _id = $(target).attr('id');
        var _lookup = $(target).parent().children('[for="' + _id + '"]');
        _lookup.children('.lookup-panel').removeClass('lookup-panel-disabled');
        _lookup.children('.lookup-panel').children('.lookup-input').children('.lookup-text').removeAttr('disabled');
        _lookup.children('.lookup-panel').children('.lookup-input').children('.lookup-text').removeClass('lookup-input-disabled');
        /*单击文本框时，弹出选择框*/
        if (opts.fireOnClickInput) {
            _lookup.children('.lookup-panel').children('.lookup-input').children('.lookup-text').click('lookup', function (e) {
                _showLookupWin(target);
            });
        }

        _lookup.children('.lookup-panel').children('.lookup-btn').click('lookup', function (e) {
            _showLookupWin(target);
        });
        _lookup.children('.lookup-panel').children('.lookup-btn').removeClass('lookup-btn-disabled');
    }

    /**
     * 禁用lookup
     * 禁用并不会禁用到隐藏域
     */
    function _disable(target) {
        var _id = $(target).attr('id');
        var _lookup = $(target).parent().children('[for="' + _id + '"]');
        _lookup.children('.lookup-panel').addClass('lookup-panel-disabled');

        _lookup.children('.lookup-panel').children('.lookup-input').children('.lookup-text').attr('disabled', 'true');
        _lookup.children('.lookup-panel').children('.lookup-input').children('.lookup-text').unbind('click');
        _lookup.children('.lookup-panel').children('.lookup-input').children('.lookup-text').addClass('lookup-input-disabled');

        _lookup.children('.lookup-panel').children('.lookup-btn').unbind('click');
        _lookup.children('.lookup-panel').children('.lookup-btn').addClass('lookup-btn-disabled');
    }

    /*获得值，返回一个对象，对象的属性根据textField 和 valueField定*/
    function _getValue(target) {
        var opts = _options(target);
        var _id = $(target).attr('id');
        var _lookup = $(target).parent().children('[for="' + _id + '"]');
        var _text = _lookup.children('.lookup-panel').children('.lookup-input').children('.lookup-text').val();
        var _value = _lookup.children('.lookup-valuefield').val();
        var _o = {};
        _o[opts.textField] = _text;
        _o[opts.valueField] = _value;
        return _o;
    }

    /**
     * 设置值
     */
    function _setValue(target, data) {
        if (!data) return;
        var opts = _options(target);
        var _id = $(target).attr('id');
        var _lookup = $(target).parent().children('[for="' + _id + '"]');
        var _text = data[opts.textField];
        var _value = data[opts.valueField];
        _lookup.children('.lookup-panel').children('.lookup-input').children('.lookup-text').val(_text);
        _lookup.children('.lookup-valuefield').val(_value);
    }

    /**
     * 清空数据
     */
    function _clear(target) {
        var opts = _options(target);
        var _id = $(target).attr('id');
        var _lookup = $(target).parent().children('[for="' + _id + '"]');
        _lookup.children('.lookup-panel').children('.lookup-input').children('.lookup-text').val('');
        _lookup.children('.lookup-valuefield').val('');
    }


    /**
     * 销毁一个lookup对象
     */
    function _destroy(target) {
        /*清空数据*/
        $.removeData(target, 'lookup');
    }

    /**
     * lookup 定义
     */
    $.fn.lookup = function (options) {
        /*如果第一个参数为 string 类型，则为调用方法，其他的参数作为方法参数*/
        if (typeof options == 'string') {
            /*将参数中的第一个参数去掉，剩下的作为参数传入方法调用*/
            var args = Array.prototype.slice.call(arguments, 1);
            /*返回值*/
            var res = undefined;
            this.each(function () {
                /*判断传入的方法是否有定义，如果有，在当前的jQuery对象上进行调用*/
                if ($.isFunction($.fn.lookup.methods[options])) {
                    var r = $.fn.lookup.methods[options].apply(this, args);
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

        /*初始化 lookup*/
        options = options || {};
        return this.each(function () {
            /*取回在当前对象上上存放的选项*/
            var data = $.data(this, 'lookup');
            /*
             * 该对象上保存过数据，说明该对象存在，先销毁，再重建。
             */
            if (data) {
                _destroy(this);
            }
            /*
             * 用 defaults 和新的 option 合并，建立新的 lookup
             */
            var innerOpts = $.extend({}, $.fn.lookup.defaults, options);
            /*将数据保存回当前对象*/
            $.data(this, 'lookup', {options: innerOpts});

            /*初始化lookup*/
            _init(this);
        });
    };

    /**
     * lookup 默认参数定义
     */
    $.fn.lookup.defaults = {
        /*是否禁用*/
        disabled: false,
        /*打开的窗口名称*/
        windowId: 'wLookupWindow',
        /*打开的页面标题*/
        title: '选择',
        /*按钮图标*/
        iconCls: 'icon-lookup',
        /*选择框的url*/
        url: '',
        /*窗口参数列表*/
        windowArgs: {},
        /*窗口的宽度及高度*/
        windowWidth: 600,
        windowHeight: 500,
        /*
         * 选择框通常会返回一个对象，对象中的不同字段对应value或text。
         * textRef 和 valueRef 对应打开窗口返回对象的属性，
         * textField 和 valueField 对应保存返回值的域，用于表单提交。
         */
        textRef: null,
        valueRef: null,
        textField: null,
        valueField: null,
        /*返回数据若是多条的分隔符*/
        separator: ',',

        /*以下4个属性对应于校验*/
        required: false,
        missingMessage: '请选择',
        validType: null,
        invalidMessage: null,

        /*单击时是否弹出框*/
        fireOnClickInput: true,
        /*打开窗口的回调函数名, 默认为 result */
        callbackProp: 'result',
        /*是否传递回显数据，默认为是*/
        showValues: true,

        /*事件处理*/
        /*打开窗口之前*/
        onWindowOpen: function (windowArgs) {
            return true;
        },
        /*选择数据之后，可以进一步处理*/
        onSelect: function (data) {
        },
        /*格式化返回的数据*/
        formatText: function (value, data) {
            return value;
        },
        formatValue: function (value, data) {
            return value;
        }
    };


    /**
     * lookup 的方法定义
     */
    $.fn.lookup.methods = {
        options: function () {
            return _options(this);
        },
        /*销毁 lookup*/
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