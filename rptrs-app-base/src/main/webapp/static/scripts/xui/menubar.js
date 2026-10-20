/**
 * menubar 的封装
 * 用于在界面显示一个带选择按钮的输入框，从指定的url读取数据
 * 外部依赖：jquery.js, jquery-easyui.js, common.js, windlg.js
 */
;(function ($) {


    /**
     * 初始化 menubar
     */
    function _init(target) {
        var opts = _options(target);
        //console.info(opts);
        var _id = $(target).attr('id');
        /*url 为必选项*/
        if (!opts.url || !opts.codeField || !opts.textField) return;
        var menuData = $.ajax({url: opts.url, type: 'GET', async: false, cache: false}).responseText;
        var menuInfo = $.parseJSON(menuData);
        /*插入根DIV*/
        var rootDiv = '<div class="menubar easyui-layout" border="false" style="width: auto; background-color: #D2E0F2; height: 26px; overflow: hidden;"></div>';
        $(target).append(rootDiv);
        /*找到根div，对菜单进行组织*/
        var _menubar = $(target).children('.menubar');
        var _menuWrapDiv = '<div class="' + _id + '_wrapMenu" border="false" region="center" style="background-color: #D2E0F2; height: 26px; overflow: hidden;"></div>';
        $(_menuWrapDiv).appendTo(_menubar);
        var _menuWrap = $(_menubar).children('.' + _id + '_wrapMenu');
        var _newMenuDiv = '<div  border="false" class="' + _id + '_newMenu" region="center"  style="padding: 3px; background-color: #D2E0F2;"></div>';
        $(_newMenuDiv).appendTo(_menuWrap);
        var _newMenu = $(_menuWrap).children('.' + _id + '_newMenu');
        /*插入菜单目录*/
        generateMenu(_newMenu, menuInfo, opts.codeField, opts.textField, opts.menubarWidth, opts);
        /*添加当一级菜单过多时的翻页按钮*/
        var tbar = '<div region="east" border="false">'
            + '<span class="menubar-rightbar">&nbsp;</span>'
            + '<span class="menubar-leftbar">&nbsp;</span>'
            + '</div>';
        $(tbar).prependTo(_menubar);
        var leftbar = $(_menubar).children().children('.menubar-leftbar');
        var rightbar = $(_menubar).children().children('.menubar-rightbar');
        /*绑定向右翻页事件*/
        $(rightbar).click(function () {
            if ($(rightbar).hasClass('menubar-rightbar')) {
                var menuHeight = $(_newMenu).height();
                var scroll = $(_menuWrap).scrollTop();
                var newScroll = scroll + opts.pageLength;
                if (!(menuHeight < newScroll)) {
                    $(_menuWrap).scrollTop(newScroll);
                    if (newScroll > 0) {
                        $(leftbar).attr('class', 'menubar-leftbar');
                    }
                    if (menuHeight - opts.pageLength == newScroll) {
                        $(rightbar).attr('class', 'menubar-disable');
                    }
                }
            }

        });
        /*绑定向左翻页事件*/
        $(leftbar).click(function () {
            if ($(leftbar).hasClass('menubar-leftbar')) {
                var menuHeight = $(_newMenu).height();
                var scroll = $(_menuWrap).scrollTop();
                var newScroll = scroll - opts.pageLength;
                if (scroll > 0) {
                    $(_menuWrap).scrollTop(newScroll);
                    if (newScroll < menuHeight - opts.pageLength) {
                        $(rightbar).attr('class', 'menubar-rightbar');
                    }
                    if (newScroll == 0) {
                        $(leftbar).attr('class', 'menubar-disable');
                    }
                }
            }
        });
        /*设置窗口大小改变事件*/
        $(_menuWrap).resize(function () {
            /*获得原窗口的大小*/
            var oldMenuHeight = opts.menuHeight;
            /*获得新窗口的大小*/
            opts.menuHeight = $(_newMenu).height();
            var newMenuHeight = opts.menuHeight;
            /*获得现在scroll的位置*/
            var scroll = $(_menuWrap).scrollTop();
            var newScroll = 0;
            /*只有当窗口的高度改变的时候，目录的行数才会改变，才需要调整scroll的高度*/
            if (oldMenuHeight != newMenuHeight && scroll != 0) {
                var oldRowSize = Math.ceil(oldMenuHeight / opts.pageLength);
                var newRowSize = Math.ceil(newMenuHeight / opts.pageLength);
                /*计算新的scroll的位置*/
                newScroll = Math.floor((newRowSize / oldRowSize) * (scroll / opts.pageLength)) * opts.pageLength;
                /*移动到新的位置*/
                $(_menuWrap).scrollTop(newScroll);
            }
            /*判断是否需要显示翻页按钮*/
            if (newScroll > 0 && ((newMenuHeight - newScroll) > opts.pageLength)) {
                $(leftbar).attr('class', 'menubar-leftbar');
                $(rightbar).attr('class', 'menubar-rightbar');
            } else if (newScroll == 0 && ((newMenuHeight - newScroll) > opts.pageLength)) {
                $(leftbar).attr('class', 'menubar-disable');
                $(rightbar).attr('class', 'menubar-rightbar');
            } else if (newScroll > 0 && (!((newMenuHeight - newScroll) > opts.pageLength))) {
                $(leftbar).attr('class', 'menubar-leftbar');
                $(rightbar).attr('class', 'menubar-disable');
            } else {
                $(leftbar).attr('class', 'menubar-disable');
                $(rightbar).attr('class', 'menubar-disable');
            }

        });
        $.parser.parse($('.menubar'));

        /*根据菜单的多少判断是否需要显示按钮*/
        if ($(_newMenu).height() > opts.pageLength) {
            $(leftbar).attr('class', 'menubar-disable');
        } else {
            $(leftbar).attr('class', 'menubar-disable');
            $(rightbar).attr('class', 'menubar-disable');
        }
        /*初始化窗口的大小*/
        opts.menuWidth = $(_menuWrap).width();
        opts.menuHeight = $(_newMenu).height();
        opts.menuScroll = $(_menuWrap).scrollTop();
        /* 删除一级菜单中的下拉小三角 */
        $('#' + _id + ' .m-btn-downarrow').remove();
    }


    /*  生成目录菜单 */
    function generateMenu(target, record, codeField, textField, menubarWidth, opts) {
        var _id = $(target).attr('id');
        if (_id === undefined) {
            _id = $(target).attr('class');
        }
        var tItemMenu = '';
        $.each(record, function (i, v) {

            /* 判断是否是第一层 */
            if (v.parent == null) {
                var tParentId = v[codeField];
                tMenu = '<a href="#"  class="easyui-menubutton"  data-options="menu:\'#' + _id + '_' + tParentId + '\'">' + v[textField] + '</a>';
                $(tMenu).appendTo(target);
                /* 判断是否存在子元素 */
                if (v.children != null && v.children.length > 0) {
                    tItemMenu = '<div id= "' + _id + '_' + tParentId + '"  style="width:' + menubarWidth + 'px;"></div>';
                    $(target).after(tItemMenu);
                    var _item = $('#' + _id + '_' + tParentId);
                    generateMenu(_item, v.children, codeField, textField, menubarWidth, opts);
                }
            } else {
                /* 判断是否存在子元素 */
                if (v.children != null && v.children.length > 0) {
                    /*因为目录编码中存在‘.’,设置成class的时候会出现错误，所以先把‘.’替换为‘_’*/
                    var code = v[codeField];
                    var clazz = '';
                    if (code) {
                        clazz = code.replace(/\./g, '_');
                    }
                    tItemMenu = '<div style="width:' + menubarWidth + 'px;" class="' + clazz + '" ><span>' + v[textField] + '</span><div></div></div>';
                    $(target).append(tItemMenu);
                    var _item = $(target).children('.' + clazz).children('div');
                    generateMenu(_item, v.children, codeField, textField, menubarWidth, opts);

                    /*获得当前菜单，绑定点击事件*/
                    var _itemClick = $(target).children('.' + clazz).children('span');
                    /*绑定点击事件*/
                    $(_itemClick).click(function () {
                        /*触发 onMenuClick 事件*/
                        if (opts.onMenuClick) {
                            opts.onMenuClick(v);
                        }

                    });
                } else {
                    var code = v[codeField];
                    var clazz = '';
                    if (code) {
                        clazz = code.replace(/\./g, '_');
                    }
                    tItemMenu = '<div style="width:' + menubarWidth + 'px;" class="' + clazz + '">' + v[textField] + '</div>';
                    $(target).append(tItemMenu);
                    var _item = $(target).children('.' + clazz);
                    /*绑定点击事件*/
                    $(_item).click(function () {
                        /*触发 onMenuClick 事件*/
                        if (opts.onMenuClick) {
                            opts.onMenuClick(v);
                        }

                    });
                }
            }
        });
    }


    /**
     * 用于在某个对象上存取 option
     */
    function _options(target, opts) {
        /*未给出第二个参数，取回option*/
        if (opts == undefined) {
            var data = $.data(target, 'menubar');
            if (data) {
                return data.options;
            }
            return null;
        }
    }


    /**
     * 销毁一个menubar对象
     */
    function _destroy(target) {
        /*清空数据*/
        $.removeData(target, 'menubar');
    }

    /**
     * menubar 定义
     */
    $.fn.menubar = function (options) {
        /*如果第一个参数为 string 类型，则为调用方法，其他的参数作为方法参数*/
        if (typeof options == 'string') {
            /*将参数中的第一个参数去掉，剩下的作为参数传入方法调用*/
            var args = Array.prototype.slice.call(arguments, 1);
            /*返回值*/
            var res = undefined;
            this.each(function () {
                /*判断传入的方法是否有定义，如果有，在当前的jQuery对象上进行调用*/
                if ($.isFunction($.fn.menubar.methods[options])) {
                    var r = $.fn.menubar.methods[options].apply(this, args);
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
        /*初始化 menubar*/
        options = options || {};
        return this.each(function () {
            /*取回在当前对象上上存放的选项*/
            var data = $.data(this, 'menubar');
            /*
             * 该对象上保存过数据，说明该对象存在，先销毁，再重建。
             */
            if (data) {
                _destroy(this);
            }
            /*
             * 用 defaults 和新的 option 合并，建立新的 menubar
             */
            var innerOpts = $.extend({}, $.fn.menubar.defaults, options);
            /*将数据保存回当前对象*/
            $.data(this, 'menubar', {options: innerOpts});

            /*初始化menubar*/
            _init(this);
        });
    };

    /**
     * menubar 默认参数定义
     */
    $.fn.menubar.defaults = {

        /*按钮图标*/
        iconCls: 'icon-edit',
        /*选择框的url*/
        url: '',
        /*显示的菜单名称对应的Id*/
        codeField: 'menuCode',
        /*显示的菜单名称对应的字段*/
        textField: 'menuName',
        /*用于设置当一级菜单过多，进行分页显示*/
        pageLength: 26,
        page: 0,
        menuLength: 0,
        menubarWidth: 150,
        /*选择数据之后，可以进一步处理*/
        onMenuClick: function (menu) {
        },
        /*用于记录窗口大小改变前的窗口的高度*/
        menuHeigth: 0
    };


    /**
     * menubar 的方法定义
     */
    $.fn.menubar.methods = {
        options: function () {
            return _options(this);
        },
        /*销毁 menubar*/
        destroy: function () {
            _destroy(this);
        }
    };

})(jQuery);

/*用于定位捕获div 的resize事件*/
(function ($, h, c) {
    var a = $([]), e = $.resize = $.extend($.resize, {}), i, k = "setTimeout", j = "resize", d = j
        + "-special-event", b = "delay", f = "throttleWindow";
    e[b] = 250;
    e[f] = true;
    $.event.special[j] = {
        setup: function () {
            if (!e[f] && this[k]) {
                return false
            }
            var l = $(this);
            a = a.add(l);
            $.data(this, d, {
                w: l.width(),
                h: l.height()
            });
            if (a.length === 1) {
                g()
            }
        },
        teardown: function () {
            if (!e[f] && this[k]) {
                return false
            }
            var l = $(this);
            a = a.not(l);
            l.removeData(d);
            if (!a.length) {
                clearTimeout(i)
            }
        },
        add: function (l) {
            if (!e[f] && this[k]) {
                return false
            }
            var n;

            function m(s, o, p) {
                var q = $(this), r = $.data(this, d);
                r.w = o !== c ? o : q.width();
                r.h = p !== c ? p : q.height();
                n.apply(this, arguments)
            }

            if ($.isFunction(l)) {
                n = l;
                return m
            } else {
                n = l.handler;
                l.handler = m
            }
        }
    };

    function g() {
        i = h[k](function () {
            a.each(function () {
                var n = $(this), m = n.width(), l = n.height(), o = $.data(
                    this, d);
                if (m !== o.w || l !== o.h) {
                    n.trigger(j, [o.w = m, o.h = l])
                }
            });
            g()
        }, e[b])
    }
})(jQuery, this);