/*自定义事件日历控件*/
;(function ($) {

    /**
     * 内部工具方法
     */
    var _tools = {

        /**
         * 返回改选择框的显示域
         */
        _getLabelField: function (_t) {
            var _f = $(_t).siblings('.dialogTextboxContainer').children('.dialogTextboxLabel');
            if (_f[0])
                return _f;

            return undefined;
        }

    };

    /**
     * 获取保存在选择输入框对象上的 options
     * param: _t 用于显示选择输入框控件的DOM对象
     */
    function _options(_t/*Object, DOM*/, _o/*Object, options*/) {
        /*从该对象上获取保存的数据*/
        var data = $(_t).data('dialogTextbox');
        var options;
        if (data) {
            options = data.options;
        } else {
            options = $.extend({}, $.fn.dialogTextbox.defaults);
            $(_t).data('dialogTextbox', {options: options});
        }

        /*没有第二个参数，获取options对象*/
        if (arguments.length == 1) {
            return options;
        }
        /*有第二个参数，保存 options 对象*/
        else {
            if (_o == undefined || _o == null) return options;
            $(_t).data('dialogTextbox', {options: $.extend(options, _o)});
        }
    }

    /**
     * 初始化插件的方法
     * param: _t 用于显示选择输入框控件的DOM对象
     */
    function _init(target/*Object*/) {
        var options = _options(target);

        /*获得原始输入框，ID及其父对象*/
        var _o = $(target);
        /*隐藏原始输入框*/
        _o.css({display: 'none'});


        var _p = _o.parent();
        _p.addClass('dialogTextbox');


        /*新建显示输入框和选择按钮*/
        var _c = $('<div class="dialogTextboxContainer"></div>').appendTo(_p);


        var _w = _o.width();
        var _h = _o.height();
        var _t = 2;
        var _l = (_h + 2) * -1;


        var _disInput = $('<input class="dialogTextboxLabel" type="text" style="width:' + _w + ';">').appendTo(_c);
        /*判断是否要设置为只读*/
        if (options.readOnly) {
            _disInput.attr('forceReadOnly', true);
            _disInput.attr("readOnly", true);
        }

        var _btn = $('<img class="dialogTextboxButton" style="width:' + _h + 'px;height:'
            + _h + 'px;top:' + _t + 'px;left: ' + _l + 'px;" src="' + contextPath + '/' + options.src + '" value="">')
            .appendTo(_c);


        var __tools = _tools;
        /*点击选择按钮触发事件*/
        _btn.click(function (event) {
            var _f = __tools._getLabelField(target);
            console.info(_f);
            return false;
        });

    }


    /**
     * 自定义的 dialogTextbox 对象
     *
     */
    $.fn.dialogTextbox = function (method, param) {
        /*调用  dialogTextbox 的方法, 调用形式: $('#input1').dialogTextbox('options', [参数])*/
        if (typeof method == 'string') {
            return $.fn.dialogTextbox.methods[method](this, param);
        }
        /*生成一个新的  dialogTextbox 对象，第一个参数为 options*/
        else {
            var options = method || {};
            return this.each(function () {
                /*取回在  dialogTextbox 上存放的选项*/
                var data = $(this).data('dialogTextbox');
                if (data) {
                    $.extend(data.options, options);
                } else {
                    $(this).data('dialogTextbox', {options: $.extend({}, $.fn.dialogTextbox.defaults, options)});
                    /*初始化对象*/
                    _init(this);
                }

            });
        }
    };

    /**
     * 定义dialogTextbox上可以被重写的方法
     */
    $.fn.dialogTextbox.methods = {
        options: function (jq, param) {
            return _options(jq[0], param);
        }

    };

    /*默认值*/
    $.fn.dialogTextbox.defaults = {
        src: 'resource/images/select.png',
        url: '',
        readOnly: true,
        labelField: 'text',
        valueField: 'value'
    };


})(jQuery);