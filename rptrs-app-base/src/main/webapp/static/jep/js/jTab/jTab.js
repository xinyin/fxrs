/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';


    var jTab = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        if (this.options) {
            $.extend(this.options, {'id': this.$el.attr('id')});
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
    jTab.DEFAULTS = {
        multiple: null
    };

    jTab.EVENTS = [];

    /**
     * 初始化
     */
    jTab.prototype.init = function () {
        var $this = this, $opt = $this.options;
        var $a = $('#' + $opt.id + ' ul ').find('a');
        //获取绑定事件
        var $old = undefined;
        if (typeof $this.options.showBsTab === 'string') {
            $old = calculateFunc($this.options.showBsTab);
        }
        var func = calculateFunc($old);
        //获取绑定事件
        var $old1 = undefined;
        if (typeof $this.options.shownBsTab === 'string') {
            $old1 = calculateFunc($this.options.shownBsTab);
        }
        var func1 = calculateFunc($old1);
        $.each($a, function (i, o) {
            //初始化绑定点击事件
            $(o).on('click', function (e) {
                e.preventDefault();
                /*判断是否是懒加载，如果是就对未加载的数据进行加载*/
                if ($opt['lazy']) {
                    var tabId = $(this).attr('name');
                    var href = $opt['hrefs'][tabId];
                    if (href) {
                        $('#' + tabId + 'Iframe').attr('src', href);
                        delete $opt['hrefs'][tabId];
                    }
                }
                $(this).tab('show');
            });
            //初始化绑定点击之前事件
            $(o).on('show.bs.tab', function (e) {
                if (func)
                    func.apply(this, [e]);
            });
            //初始化绑定点击之后事件
            $(o).on('shown.bs.tab', function (e) {
                if (func1)
                    func1.apply(this, [e]);
            });
            $.helper.autoResizeIframe(o.name + 'Iframe');
        });

        //绑定关闭事件
        $('.tab-close').on('click', function (ev) {
            var ev = window.event || ev;
            ev.stopPropagation();
            //先判断当前要关闭的tab选项卡有没有active类，再判断当前选项卡是否最后一个，如果是则给前一个选项卡以及内容添加active，否则给下一个添加active类
            var gParent = $(this).parent().parent(),
                parent = $(this).parent();
            var count = $('#' + $opt.id).children('ul').children('li').length - 1;
            if (gParent.hasClass('active')) {
                if (gParent.index() == count) {
                    gParent.prev().addClass('active');
                    $(parent.attr('href')).prev().addClass('active');
                } else {
                    gParent.next().addClass('active');
                    $(parent.attr('href')).next().addClass('active');
                }
            }
            gParent.remove();
            $(parent.attr('href')).remove();
        });
    };

    /**
     * 设置根据id选中tab
     */
    jTab.prototype.selectTab = function (id) {
        var $this = this, $opt = $this.options;
        //设置选中
        var tab = $('#' + $opt.id + ' a[href="#' + id + '"]');
        $(tab).tab('show');
    };

    /**
     * 获取选中tab
     */
    jTab.prototype.getTabTitle = function () {
        var $this = this, $opt = $this.options;
        var array = $('#' + $opt.id + ' ul .tab-list');
        var $li = '';
        $.each(array, function (i, v) {
            if ($(v).hasClass('active')) {
                $li = v;
                return false;
            }
        });
        return $li;
    };

    /**
     * 获取tab打开数量
     */
    jTab.prototype.getTabSize = function () {
        var $this = this, $opt = $this.options;
        var array = $('#' + $opt.id + ' ul .tab-list');
        return array.length + 1;
    };

    /**
     * 获取选中tab
     */
    jTab.prototype.getTab = function () {
        var $this = this, $opt = $this.options;
        var array = $('#' + $opt.id + ' ul .tab-list');
        var $v = null;
        $.each(array, function (i, v) {
            if ($(v).hasClass('active')) {
                $v = {
                    index: i,
                    title: $(v).text()
                };
                return false;
            }
        });
        return $v;
    };

    /**
     * 修改tab 子集中的内容
     */
    jTab.prototype.updateTab = function (tab) {
        var $this = this, $opt = $this.options;
        var $li = this.getTabTitle();
        var tabId = '';
        var tabsId = $this.options.id;

        //设置标题
        if (tab.title != null && tab.title != '') {
            var $t = $($li).children('a');
            tabId = $($t).attr('href');
            var liLength = $($t).children('i').length;
            if (liLength == 0) {
                $($t).html(tab.title);
            } else {
                $($t).html($($t).children('i'));
                $($t).children('i').eq(liLength - 1).before(tab.title);
                //绑定关闭事件
                $('.tab-close').on('click', function (ev) {
                    var ev = window.event || ev;
                    ev.stopPropagation();
                    //先判断当前要关闭的tab选项卡有没有active类，再判断当前选项卡是否最后一个，如果是则给前一个选项卡以及内容添加active，否则给下一个添加active类
                    var gParent = $(this).parent().parent(),
                        parent = $(this).parent();
                    var count = $('#' + tabsId).children('ul').children('li').length - 1;
                    if (gParent.hasClass('active')) {
                        if (gParent.index() == count) {
                            gParent.prev().addClass('active');
                            $(parent.attr('href')).prev().addClass('active');
                        } else {
                            gParent.next().addClass('active');
                            $(parent.attr('href')).next().addClass('active');
                        }
                    }
                    gParent.remove();
                    $(parent.attr('href')).remove();
                });
            }

        }

        //设置修改的值
        if (tab.content != null && tab.content != null) {
            var html = '<div class="panel-body">' + tab.content + '</div>';
            $(tabId).html(html);
        }
    }

    /**
     * 修改tab 子集中的内容
     */
    jTab.prototype.updateHrefTab = function (id, href) {
        var $this = this, $opt = $this.options;

        //设置修改的值
        var html = '<div class="panel-body"><iframe id="' + id + 'Iframe" frameborder="0" style="margin:0;padding:0;width:100%;" scrolling="yes" src="' + href + '" ></iframe></div>';
        $('#' + id).html(html);
    }

    /**
     * 关闭一个tab
     */
    jTab.prototype.colseTab = function (name) {
        var $this = this, $opt = $this.options;
        var tab = $('a[name="' + name + '"]');
        var parent = $(tab).parent();
        var count = $('#' + $opt.id).children('ul').children('li').length - 1;
        if (parent.hasClass('active')) {
            if (parent.index() == count) {
                parent.prev().addClass('active');
                $(tab.attr('href')).prev().addClass('active');
            } else {
                parent.next().addClass('active');
                $(tab.attr('href')).next().addClass('active');
            }
        }
        parent.remove();
        $(tab.attr('href')).remove();
    }

    /**
     * 新增一个tab页， href 优先
     */
    jTab.prototype.addTab = function (tab) {
        var $this = this, $opt = $this.options;
        var tabs = $('#' + $opt.id);
        //获取当前传入id是否已被使用
        var exist = tabs.find('[id="' + tab.id + '"]');
        if (exist.length == 0) {
            $('.active').removeClass('active');
            var ul = $(tabs).find('.nav-tabs');
            var title = '<li class="tab-list active"><a name="' + tab.id + '" href="#' + tab.id + '">';
            if (null != tab.icon && '' != tab.icon) {
                title += '<i class="fa fa-' + tab.icon + '"></i>';
            }
            if (null != tab.title && '' != tab.title) {
                title += tab.title;
            }
            //是否显示关闭按钮
            if (tab.colse) {
                title += '<i class="fa fa-remove tab-close" style="vertical-align: super;cursor:pointer "></i>';
            }
            title += '</a></li>';
            $(ul).append(title);
            //初始化tab 内容
            var div = $(tabs).find('.tab-content');
            var html = $(div).html();
            html += '<div id="' + tab.id + '" class="tab-pane active">\n';
            html += '<div class="wrapper wrapper-content">\n';
            //如果href不为空，以href优先
            if (null != tab.href && '' != tab.href) {
                html += '<iframe id="' + tab.id + 'Iframe" frameborder="0" style="margin: 0px; padding: 0px; width: 100%; ';
                if (null != tab.height && tab.height != '') html += 'height: ' + tab.height + 'px;';
                else html += 'height: 500px;';
                html += '" scrolling="yes" src="' + tab.href + '"></iframe>\n';
            } else {
                html += tab.content;
            }

            html += '</div>\n</div>\n';
            $(div).html(html);

            //初始化绑定点击事件
            $($('a[name="' + tab.id + '"]')).on('click', function (e) {
                e.preventDefault();
                $(this).tab('show');
            });

            //绑定关闭事件
            $('.tab-close').on('click', function (ev) {
                var ev = window.event || ev;
                ev.stopPropagation();
                //先判断当前要关闭的tab选项卡有没有active类，再判断当前选项卡是否最后一个，如果是则给前一个选项卡以及内容添加active，否则给下一个添加active类
                var gParent = $(this).parent().parent(),
                    parent = $(this).parent();
                var count = $(tabs).children('ul').children('li').length - 1;
                if (gParent.hasClass('active')) {
                    if (gParent.index() == count) {
                        gParent.prev().addClass('active');
                        $(parent.attr('href')).prev().addClass('active');
                    } else {
                        gParent.next().addClass('active');
                        $(parent.attr('href')).next().addClass('active');
                    }
                }
                gParent.remove();
                $(parent.attr('href')).remove();
            });
        } else {
            //设置选中
            var existTab = $('#' + $opt.id + ' a[href="#' + tab.id + '"]');
            $(existTab).tab('show');
        }


    }

    /**
     * 可调用的方法
     */
    var allowedMethods = [];
    allowedMethods.push("addTab");
    allowedMethods.push("selectTab");
    allowedMethods.push("colseTab");
    allowedMethods.push("updateTab");
    allowedMethods.push("updateHrefTab");
    allowedMethods.push("getTab");
    allowedMethods.push("getTabTitle");
    allowedMethods.push("getTabSize");


    $.fn.jTab = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jTab'),
                options = $.extend({}, jTab.DEFAULTS, $this.data(),
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
                    $this.removeData('bootstrap.jTab');
                }
            }

            if (!data) {
                $this.data('bootstrap.jTab', (data = new jTab(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jTab.Constructor = jTab;
    $.fn.jTab.defaults = jTab.DEFAULTS;
    $.fn.jTab.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jTab"]').jTab();
    });

}(jQuery);