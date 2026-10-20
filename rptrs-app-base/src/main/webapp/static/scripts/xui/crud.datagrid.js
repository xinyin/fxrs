/**
 * CRUD datagrid 的封装，基于datagrid实现
 * 提供基本的增删改查的功能
 * 以 '_' 开头的方法均为内部方法，外部无法调用
 * 外部依赖：jquery.js, jquery-easyui.js, common.js, windlg.js
 */
;(function ($) {

    /* 全局定义开始 */

    /*默认的按钮文本*/
    var defaultButtonText = {
        add: '新建',
        edit: '编辑',
        remove: '删除',
        view: '查看',
        'export': '导出'
    };

    /*默认的按钮ID*/
    var defaultButtonID = {
        add: 'btn_add',
        edit: 'btn_edit',
        remove: 'btn_remove',
        view: 'btn_view',
        'export': 'btn_export'
    };

    /*默认的按钮图标*/
    var defaultButtonCls = {
        add: 'icon-add',
        edit: 'icon-edit',
        remove: 'icon-remove',
        view: 'icon-view',
        'export': 'icon-export'
    };

    var defaultButtonUrl = {
        add: 'edit',
        edit: 'edit',
        remove: 'delete',
        view: 'edit',
        'export': 'export'
    };

    /* 全局定义结束 */

    /**
     * 用于在某个对象上存取 option
     */
    function _options(target, opts) {
        /*未给出第二个参数，取回option*/
        if (opts == undefined) {
            var data = $.data(target, 'crudDatagrid');
            if (data) {
                return data.options;
            }
            return null;
        }
    }

    /**
     * 用于从option中提取消息文本
     * 如果option中没有定义该消息，则从 $.fn.crudDatagrid.messages 中提取
     * @param target  要提取消息的 crudDatagrid 对象
     * @param key       消息的键值
     * @returns             返回的消息文本
     */
    function _message(target, key) {
        var option = _options(target);
        if (option[key] == undefined) {
            return $.fn.crudDatagrid.messages[key];
        }
        return option[key];
    }

    /**
     * 处理参数中的占位符
     * 用于替换在 url 及 args 中出现的占位符
     * 在编辑，查看，和删除操作时进行替换
     * 使用当前记录作为待替换的对象
     * @param args          要处理的参数
     * @param record     使用的记录对象
     * @returns                 返回替换后的参数
     */
    function _resolvePlaceholder(args, record) {
        if (args == undefined || args == null) return null;
        var newArgs = {};
        for (var property in args) {
            if (typeof args[property] == 'string') {
                newArgs[property] = $.message.resolvePlaceholder(args[property], record);
            } else {
                newArgs[property] = args[property];
            }
        }
        return newArgs;
    }

    /**
     * 处理URL
     * 如果URL不是以 / 开头，认为是相对于当前页面的路径
     * 会根据 location.href 的属性重新设置url，并返回
     */
    function _resolveUrl(url) {
        if (url == undefined || url == null) return url;
        if (/^http\:\/\//g.test(url)) return url;
        if (url.substring(0, 1) == '/') return url;
        var urlLocation = location.href;
        if (urlLocation.lastIndexOf('/') != -1) {
            urlLocation = urlLocation.substring(0, urlLocation.lastIndexOf('/') + 1);
            return urlLocation + url;
        }
    }

    /**
     * 对参数进行预处理
     * 主要是对事件的处理，加入自己的事件
     */
    function _prepareOption(target, opts) {
        /*处理标题，默认用 objectAlias 替换 {0}*/
        opts.title = $.message.format(opts.title, opts.objectAlias);
        /* 处理 toolbar */
        _prepareToolbarOption(target, opts);
        /* 处理事件 */
        _prepareEventHandler(target, opts);
        return opts;
    };

    /**
     * 定义默认的弹出窗口回调函数
     * 定义默认的按钮点击处理函数
     * 处理 toolbar
     */
    function _prepareToolbarOption(target, opts) {
        /*
         * 此处定义按钮的默认回调函数。
         * add, edit, remove默认回调函数都是进行重查询操作。
         * view, export 默认无任何操作。
         */
        opts.defaultWindowCallback = {
            add: function () {
                _query(target);
            },
            edit: function () {
                _query(target);
            },
            view: function () {
            }
        };

        /*
         * 此处定义按钮的默认处理函数。
         */
        opts.defaultButtonHandler = {
            add: function () {
                _add(target);
            },
            edit: function () {
                _edit(target);
            },
            view: function () {
                _view(target);
            },
            remove: function () {
                _remove(target);
            },
            'export': function () {
                _export(target);
            }
        };

        /* 先取回自定义的工具栏*/
        var toolbar = opts.toolbar;
        /* 没有自定义 toolbar， 新定义一个*/
        if (toolbar == undefined) toolbar = [];
        /*内部的数组，最终加入到toolbar中*/
        var innerToolbar = [];

        /*自定义了 buttons， 否则不显示任何按钮*/
        if (opts.buttons != undefined && opts.buttons != null && $.isArray(opts.buttons)) {
            /*预定义的buttons*/
            var buttonDef = opts.buttonDef;
            if (buttonDef == undefined) buttonDef = {};

            /*要显示的按钮*/
            var buttons = opts.buttons;
            /*按钮文字*/
            var buttonText = opts.buttonText;
            /*按钮图标*/
            var buttonCls = opts.buttonCls;
            /*按钮ID*/
            var buttonID = opts.buttonID;
            /*按钮路径*/
            var buttonUrl = opts.buttonUrl;
            /*按钮处理函数*/
            var buttonHandler = opts.buttonHandler;
            var defaultButtonHandler = opts.defaultButtonHandler;

            /*生成新的 toolbar*/
            for (var i = 0; i < buttons.length; i++) {
                if (buttons[i] == '-') {
                    innerToolbar.push(buttons[i]);
                    continue;
                }
                var buttonName = buttons[i];

                /*取回按钮的ID, 文字，图标，处理函数等*/
                var text = buttonText != undefined
                    ? (buttonText[buttonName] != undefined ? buttonText[buttonName]
                        : defaultButtonText[buttonName]) : defaultButtonText[buttonName];
                if (text == undefined) {
                    text = buttonDef[buttonName] != undefined ? buttonDef[buttonName].text : undefined;
                    if (text == undefined) continue;
                }

                var id = buttonID != undefined
                    ? (buttonID[buttonName] != undefined ? buttonID[buttonName]
                        : defaultButtonID[buttonName]) : defaultButtonID[buttonName];
                if (id == undefined) {
                    id = buttonDef[buttonName].id;
                }


                var cls = buttonCls != undefined
                    ? (buttonCls[buttonName] != undefined ? buttonCls[buttonName]
                        : defaultButtonCls[buttonName]) : defaultButtonCls[buttonName];
                if (cls == undefined) {
                    cls = buttonDef[buttonName].iconCls;
                }

                var handler = buttonHandler != undefined
                    ? (buttonHandler[buttonName] != undefined ? buttonHandler[buttonName]
                        : defaultButtonHandler[buttonName]) : defaultButtonHandler[buttonName];
                if (handler == undefined) {
                    handler = buttonDef[buttonName].handler;
                }

                /*按钮没有定义处理函数，跳过*/
                if (!$.isFunction(handler)) continue;
                var button = {
                    id: id,
                    text: '<span style="font-size:14px;font-weight:bold;font-decoration:none">' + text + '</span>',
                    iconCls: cls,
                    handler: handler
                };
                innerToolbar.push(button);

                /*对于没有定义的buttonUrl，用 defaultButtonUrl 替代*/
                if (buttonUrl == undefined) buttonUrl = {};
                if (buttonUrl[buttonName] == undefined || buttonUrl[buttonName] == null) {
                    /*是默认按钮，如果是自定义的 button，不需要考虑url*/
                    if (defaultButtonUrl[buttonName] != undefined) {
                        /*
                         * 如果没有指定，优先根据 actionPath 的路径生成
                         * 生成策略 contextPath + actionPath + defaultPath
                         * 如果 actionPath 没有指定，采用默认路径
                         */
                        var actionPath = opts.actionPath;
                        if (actionPath != undefined && actionPath != null && $.trim(actionPath) != '') {
                            var url = [];
                            var action = actionPath;
                            url.push(contextPath);

                            action = actionPath.substring(0, 1) == '/' ? actionPath.substring(1) : actionPath;
                            action = actionPath.substring(actionPath.length - 1, actionPath.length) == '/' ? actionPath.substring(0, actionPath.length - 1) : actionPath;
                            url.push(action);
                            url.push(defaultButtonUrl[buttonName]);
                            buttonUrl[buttonName] = url.join('/');
                        } else {
                            buttonUrl[buttonName] = defaultButtonUrl[buttonName];
                        }
                    }
                }
                //alert('定义:' +$(target).attr('id') + ':' + buttonName + ':' + buttonUrl[buttonName]);
                /*将 url 处理为本地url*/
                buttonUrl[buttonName] = _resolveUrl(buttonUrl[buttonName]);
            }
            opts.buttonUrl = buttonUrl;

            /*按钮窗口定义*/
            var windowDef = opts.windowDef;
            if (windowDef == undefined) windowDef = {};
            /*如果title没有定义，则采用默认*/
            for (var i = 0; i < buttons.length; i++) {
                var key = buttons[i];
                if (windowDef[key] == undefined) {
                    windowDef[key] = {};
                }
                /*未定义窗口ID，如果定义了 windowID，用 windowID 生成标题，否则生成默认*/
                if (windowDef[key].id == undefined) {
                    windowDef[key].id = opts.windowID;
                }

                /*未定义标题，如果定义了 objectAlias，用 objectAlias 生成标题，否则生成默认*/
                if (windowDef[key].title == undefined) {
                    windowDef[key].title = defaultButtonText[key] + opts.objectAlias;
                }

                /*未定义宽度，如果定义了 windowWidth，用 windowWidth 生成宽度，否则生成默认*/
                if (windowDef[key].width == undefined) {
                    if (opts.windowSize != undefined && opts.windowSize.width != undefined)
                        windowDef[key].width = opts.windowSize.width;
                }

                /*未定义高度，如果定义了 windowHeight，用 windowHeight 生成宽度，否则生成默认*/
                if (windowDef[key].height == undefined) {
                    if (opts.windowSize != undefined && opts.windowSize.height != undefined)
                        windowDef[key].height = opts.windowSize.height;
                }

                /*未定义回调函数，使用默认回调函数*/
                if (windowDef[key].callback == undefined) {
                    windowDef[key].callback = opts.defaultWindowCallback[key];
                }
            }
            /*重新赋值*/
            opts.windowDef = windowDef;
        }
        opts.toolbar = innerToolbar.length > 0 ? innerToolbar : null;
    }

    /**
     * 处理新增记录的方法
     */
    function _add(target) {
        var opts = _options(target);
        var def = opts.windowDef.add;
        var url = opts.buttonUrl.add;

        /*窗口的参数*/
        var args = {};

        /*获得父窗口的键值*/
        var parentKey = _getParentKey();
        if (parentKey) {
            args[opts.parent.childField] = parentKey;
        }

        /*合并参数*/
        args = $.extend({}, args, {op: 'add', callback: def.callback});
        args = $.extend({}, args, def.args);

        /*调用添加之前的事件*/
        if (opts.onBeforeAdd && $.isFunction(opts.onBeforeAdd)) {
            var result = opts.onBeforeAdd.call(target, args);
            if (!result) return;
        }

        /*
         * 设置选项
         */
        var openArgs = {
            inFrame: true,
            windowArgs: args
        };
        /*新增关闭窗口事件*/
        if (opts.onClose) {
            openArgs = $.extend({}, openArgs, {onClose: opts.onClose});
        }


        /*窗口大小*/
        if (def.width && def.height) {
            openArgs = $.extend({}, openArgs, {
                size: '',
                width: def.width,
                height: def.height
            });
        }

        openWindow(def.id, def.title, url, openArgs);

        /**
         * 获得父对象的键值
         */
        function _getParentKey() {
            /*判断是否关联了父对象，从父对象中取出对应字段的值，放到界面参数中*/
            if (opts.parent != undefined && opts.parent.id != undefined && opts.parent.childField != undefined) {
                var type = opts.parent.type;
                if (type == 'datagrid') {
                    var parentOpts = $('#' + opts.parent.id).crudDatagrid('options');
                    if (parentOpts == undefined) return null;

                    var parentField = parent.parentField;
                    if (parentField == undefined) {
                        parentField = parentOpts.keyField;
                    }
                    if (parentField == undefined) return null;

                    var row = $('#' + opts.parent.id).datagrid('getSelected');
                    if (row == undefined || row == null) return null;
                    var keyValue = row[parentField];
                    return keyValue;
                } else {
                    var parentOpts = $('#' + opts.parent.id).crudTree('options');
                    if (parentOpts == undefined) return null;
                    var node = $('#' + opts.parent.id).tree('getSelected');
                    if (node == undefined || node == null) return null;
                    if (node.id != undefined && node.id != null) {
                        return node.id;
                    }
                }
            }
            return null;
        };
    }

    /**
     *  编辑按钮的默认处理函数
     * 该操作取得当前选中的记录，打开指定的url
     * 将当前选中的行对象作为参数传递给打开的窗口
     */
    function _edit(target) {
        var opts = _options(target);
        /*取得当前选中行*/
        var row = $(target).datagrid('getSelected');
        if (row != null) {
            var def = opts.windowDef.edit;
            var url = $.message.resolvePlaceholder(opts.buttonUrl.edit, row);
            /*窗口的参数*/
            var args = {};

            /*合并参数*/
            var formHref = url + '/{' + opts.keyField + '}.json';
            formHref = $.message.resolvePlaceholder(formHref, row);
            args = $.extend({}, args, {op: 'edit', record: row, callback: def.callback, formHref: formHref});
            args = $.extend({}, args, def.args);
            args = _resolvePlaceholder(args, row);

            /*调用添加之前的事件*/
            if (opts.onBeforeEdit && $.isFunction(opts.onBeforeEdit)) {
                var result = opts.onBeforeEdit.call(target, row, args);
                if (!result) return;
            }

            /*
             * 设置选项
             */
            var openArgs = {
                inFrame: true,
                windowArgs: args
            };
            /*新增关闭窗口事件*/
            if (opts.onClose) {
                openArgs = $.extend({}, openArgs, {onClose: opts.onClose});
            }


            /*窗口大小*/
            if (def.width && def.height) {
                openArgs = $.extend({}, openArgs, {
                    size: '',
                    width: def.width,
                    height: def.height
                });
            }

            openWindow(def.id, def.title, url, openArgs);
        } else {
            var msgTitle = _message(target, 'msgTitle');
            var noneSelectOnEdit = $.message.format(_message(target, 'noneSelectOnEdit'), opts.objectAlias);
            $.messager.alert(msgTitle, noneSelectOnEdit, 'info');
        }
    }

    /**
     * 查看按钮的默认处理函数
     * 该操作取得当前选中的记录，打开指定的url
     * 将当前选中的行对象作为参数传递给打开的窗口
     */
    function _view(target) {
        var opts = _options(target);
        /*取得当前选中行*/
        var row = $(target).datagrid('getSelected');
        if (row != null) {
            var def = opts.windowDef.view;
            var url = $.message.resolvePlaceholder(opts.buttonUrl.view, row);
            /*窗口的参数*/
            var args = {};

            /*合并参数*/
            var formHref = url + '/{' + opts.keyField + '}.json';
            formHref = $.message.resolvePlaceholder(formHref, row);
            args = $.extend({}, args, {
                op: 'view',
                record: row,
                callback: def.callback,
                readOnly: true,
                formHref: formHref
            });
            args = $.extend({}, args, def.args);
            args = _resolvePlaceholder(args, row);

            /*调用添加之前的事件*/
            if (opts.onBeforeView && $.isFunction(opts.onBeforeView)) {
                var result = opts.onBeforeView.call(target, row, args);
                if (!result) return;
            }

            /*
             * 设置选项
             */
            var openArgs = {
                inFrame: true,
                windowArgs: args
            };
            /*新增关闭窗口事件*/
            if (opts.onClose) {
                openArgs = $.extend({}, openArgs, {onClose: opts.onClose});
            }


            /*窗口大小*/
            if (def.width && def.height) {
                openArgs = $.extend({}, openArgs, {
                    size: '',
                    width: def.width,
                    height: def.height
                });
            }

            openWindow(def.id, def.title, url, openArgs);
        } else {
            var msgTitle = _message(target, 'msgTitle');
            var noneSelectOnView = $.message.format(_message(target, 'noneSelectOnView'), opts.objectAlias);
            $.messager.alert(msgTitle, noneSelectOnView, 'info');
        }
    }

    /**
     * 删除按钮的默认处理函数
     * 删除操作默认不打开窗口处理，将当前所有选中的记录ID合并成一个数组，发送回指定的URL
     * 并获得服务器端的响应，服务器的响应结果应该为一个对象
     * 包含 success (true | false) 属性 和 msg 属性
     * 该方法默认会有一个提示信息：‘确认要删除选中的 *** 吗？’
     * 可以通过 confirmBeforeRemove 属性来打开或关闭该提示
     * 可以通过 confirmOnRemove 属性来设置提示的消息
     */
    function _remove(target) {
        var opts = _options(target);
        /*取回消息标题*/
        var msgTitle = _message(target, 'msgTitle');
        /*取得当前选中行*/
        var row = $(target).datagrid('getSelected');
        if (row != null) {
            /*判断是否要提示*/
            if (opts.confirmBeforeRemove) {
                /*获得提示信息，并格式化消息*/
                var confirmOnRemove = $.message.format(_message(target, 'confirmOnRemove'), opts.objectAlias);
                /*替换占位符*/
                confirmOnRemove = $.message.resolvePlaceholder(confirmOnRemove, row);
                $.messager.confirm(msgTitle, confirmOnRemove, function (approve) {
                    if (approve) _performRemove();
                });
            } else {
                _performRemove();
            }
        } else {
            var noneSelectOnRemove = $.message.format(_message(target, 'noneSelectOnRemove'), opts.objectAlias);
            $.messager.alert(msgTitle, noneSelectOnRemove, 'info');
        }

        /*闭包函数，外部无法调用，为内部多次调用而设计*/
        function _performRemove() {
//		/*定义传递到后台的参数*/
//		var args = {};

            /*增加了 onBeforeRemove 的事件*/
            if (opts.onBeforeRemove != undefined && $.isFunction(opts.onBeforeRemove)) {
                var approve = opts.onBeforeRemove.call(target, row);
                if (!approve) return;
            }

//		var def = opts.windowDef.remove;
            var url = $.message.resolvePlaceholder(opts.buttonUrl.remove, row);
            $.mask.post(url, row, function (data) {
                if (data != undefined) {
                    /*修改删除成功后的操作顺序，先刷新，后提示*/
                    var msgType = data.success ? 'info' : 'warning';
                    _query(target);
                    $.messager.alert(msgTitle, data.msg, msgType);
                }
            });
        };
    }

    /**
     * 导出按钮的默认处理函数
     * 导出按钮默认弹出选项对话框，并提供确定和取消按钮
     * 用户选定参数后，将参数传回该函数，调用url去后台处理导出操作
     */
    function _export(target) {
        var opts = _options(target);
        /*判断是否已经初始化过参数DIV，如果没有则进行初始化*/
        var exportWinID = opts.exportWinID.substring(0, 1) == '#' ? opts.exportWinID.substring(1) : opts.exportWinID;
        var initialized = $('#' + exportWinID).length > 0;
        if (!initialized) {
            /*下载窗口*/
            var winhtml = '<iframe id="ifrExport" src="about:blank" frameborder="0" scrolling="auto" style="display:none;z-index: 1;width:0px;height:0px; overflow: visible;"/>';
            $(winhtml).appendTo($(document.body));

            /*选项窗口*/
            var html = '<div id="' + exportWinID + '">';
            html += '<table style="width: 100%;height: 1%;" cellpadding="0" cellspacing="0" class="kTable">';
            html += '<input type="hidden"/><tr>';
            html += '<td class="kTableLabel" style="width: 30%">导出类型</td><td class="kTableValue">';
            html += '<select id="exportType" name="exportType" style="width: 198px;" class="easyui-combobox" panelHeight="auto"  editable="false"/>';
            html += '</td></tr><tr><td class="kTableLabel">导出范围</td><td class="kTableValue">';
            html += '<select id="exportRange" name="exportRange" style="width: 198px;" class="easyui-combobox" panelHeight="auto"  editable="false" />';
            html += '</td></tr></table><div style="margin:0 auto;text-align:center;padding-top:5px;">';
            html += '<a href="javascript:void(0)" id="export_ok" class="easyui-linkbutton" iconCls="icon-excel">导出</a>';
            html += '<a href="javascript:void(0)" id="export_cancel"  class="easyui-linkbutton" iconCls="icon-back">关闭</a>';
            html += '</div></div>';
            $(html).appendTo($(document.body));
            /*设置下拉列表*/
            $('#exportType').combobox({
                valueField: 'value',
                textField: 'name',
                data: $.fixedDict.get('ExportType')
            });
            $('#exportRange').combobox({
                valueField: 'value',
                textField: 'name',
                data: $.fixedDict.get('ExportRange')
            });
        }

        /*设置按钮*/
        $('#export_ok').linkbutton().unbind('click').click(function (event) {
            _exportGridData(target);
        });
        $('#export_cancel').linkbutton().unbind('click').click(function (event) {
            $('#' + exportWinID).window('close');
        });

        $('#' + exportWinID).window({
            width: 360,
            height: 130,
            title: '导出选项',
            collapsible: false,
            minimizable: false,
            maximizable: false,
            //closable: false,
            modal: true
        });
    }


    /**
     * 导出数据
     */
    function _exportGridData(target) {
        var opts = $(target).datagrid('options');
        var exportWinID = opts.exportWinID.substring(0, 1) == '#' ? opts.exportWinID.substring(1) : opts.exportWinID;
        $('#' + exportWinID).window('close');

        /* 获得选择的导出类型和导出范围 */
        var exportType = $('#exportType').combobox('getValue');
        var exportRange = $('#exportRange').combobox('getValue');
        if (exportType == undefined || exportType == null || $.trim(exportType) == '') {
            $.messager.alert('系统提示', '请选择导出类型', 'info');
            return;
        }
        /*目前只处理导出 Excel 的情形*/
        else if (exportType == 'PDF') {
            return;
        }

        if (exportRange == undefined || exportRange == null || $.trim(exportRange) == '') {
            $.messager.alert('系统提示', '请选择导出范围', 'info');
            return;
        }

        /*取回分页参数*/
        var pageOpts = $(target).datagrid('getPager').data("pagination").options;
        var current = pageOpts.pageNumber;
        var total = pageOpts.total;
        var pagesize = pageOpts.pageSize;
        var params = [];

        params.push('exportType=' + exportType);
        if (exportRange == 'ALL') {
            params.push('page=1');
            params.push('rows=' + total);
        } else {
            params.push('page=' + current);
            params.push('rows=' + pagesize);
        }

        /* 将查询参数增加到路径中 */
        if (opts.queryParams != undefined && opts.queryParams != null) {
            for (var k in opts.queryParams) {
                params.push(k + '=' + opts.queryParams[k]);
            }
        }

        /*获得列名与变量值*/
        var columnTitles = [];
        var columnFields = [];
        var columns = $(target).datagrid("getColumnFields");
        for (var k in columns) {
            //根据列的filed参数，获得一个完整的列
            var column = $(target).datagrid("getColumnOption", columns[k]);
            columnTitles.push(column.title);
            columnFields.push(columns[k]);
        }
        /*2012-04-20 修改，增加了对字段名的编码*/
        params.push('columnTitles=' + $.encoder.encode(columnTitles.join(',')));
        params.push('columnFields=' + columnFields.join(','));

        var url = opts.buttonUrl['export'];
        url += '?' + params.join('&');
        $('#ifrExport').attr('src', url);
    }

    /**
     * 处理事件，拦截 datagrid 的事件
     */
    function _prepareEventHandler(target, opts) {
        /*重新设置 onDblClickRow*/
        _resetOnDblClickRowHandler(target, opts);
        /* 重新设置 onLoadSuccess*/
        _resetOnLoadSuccessHandler(target, opts);
        /* 重新设置子 datagrid*/
        _resetOnSelectHandler(target, opts);
    }

    /**
     * 重新设置加载成功事件
     */
    function _resetOnDblClickRowHandler(target, opts) {
        /*
         * 拦截 onDblClickRow 事件，在定义了 view 按钮的前提下，
         * 进行和 view 按钮相同的事件，用户定义的 onDblClickRow 将被取消
         * 如果用户没有定义 view 按钮，则保留用户定义的 onDblClickRow.
         */
        var buttons = opts.buttons;
        if (buttons != null && buttons != undefined) {
            var hasView = false;
            for (var i = 0; i < buttons.length; i++) {
                if (buttons[i] == 'view') {
                    hasView = true;
                    break;
                }
            }
            /*定义了view按钮*/
            if (hasView) {
                try {
                    /*如果用户自己定义了 onDblClickRow 事件，则忽略默认设置*/
                    if (!opts.onDblClickRow || !$.isFunction(opts.onDblClickRow)) {
                        opts.onDblClickRow = function () {
                            _view(target);
                        };
                    }
                } catch (e) {
                }
            }
        }
    }

    /**
     * 重新设置加载成功事件
     */
    function _resetOnLoadSuccessHandler(target, opts) {
        /*
         * 拦截 onLoadSuccess 事件，默认有数据的情形下，选中第一条记录
         * 先保存用户定义的事件回调函数，在之前加入选中第一条记录的逻辑
         * 用户自定义的事件不会被覆盖
         */
        opts.userOnLoadSuccess = opts.onLoadSuccess;
        var userOnLoadSuccessHandler = opts.userOnLoadSuccess;
        opts.onLoadSuccess = function (data) {
            /*增加提示字段的判断*/
            if (opts.tooltip) {
                var tips = opts.tooltip.split(',');
                var delay = 500;
                if (opts.tooltipDelay) delay = parseInt(opts.tooltipDelay);
                $(target).datagrid('doCellTip', {delay: delay, fields: tips});
            }

            if (data != undefined && data != null && data.total > 0) {
                /*2012-04-01增加，是否选中首行的选项*/
                if (opts.selectFirst) {
                    $(this).datagrid('selectRow', 0);
                }
            } else {
                var body = $(target).data().datagrid.dc.body2;
                body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;">没有数据</td></tr>');
            }
            if (userOnLoadSuccessHandler != undefined) userOnLoadSuccessHandler.call(target, data);
        };
    }


    /**
     * 重新设置选择事件，在子datagrid 定义后，可以调用
     */
    function _resetOnSelectHandler(target, opts) {
        /*
         * 拦截 onSelect 事件，判断当前的参数中是否配置了子数据表格
         * 如果有子数据表格，则调用子数据表格的 reload
         * 用户自定义的事件不会被覆盖，将在之后调用
         */
        opts.userOnSelect = opts.onSelect;
        var userOnSelectHandler = opts.userOnSelect;
        opts.onSelect = function (rowIndex, rowData) {
            /*刷新子对象数据*/
            _reloadChildren(target, rowData);

            if (userOnSelectHandler != undefined) userOnSelectHandler.call(target, rowIndex, rowData);
        };
    }

    /**
     * 刷新子对象数据，目前只支持 datagrid
     */
    function _reloadChildren(target, rowData) {
        var opts = _options(target);
        /* 定义了子对象*/
        if (opts.children != undefined && opts.children != null && opts.children.length > 0) {
            /*
             * 遍历所有的子对象，将 parentField 中定义的字段的值作为参数，
             * 放到子对象的 queryParams 中，并刷新子对象
             */
            for (var i = 0; i < opts.children.length; i++) {
                var child = opts.children[i];
                if (child.id == undefined || child.childField == undefined) continue;
                var childId = child.id.substring(0, 1) == '#' ? child.id : '#' + child.id;

                var childType = child.type == undefined ? 'datagrid' : child.type;
                /*取回父表格的当前记录的字段*/
                var parentField = rowData[child.parentField];
                if (parentField == undefined) {
                    if (opts.keyField != undefined) parentField = rowData[opts.keyField];
                    else continue;
                }

                if (childType == 'datagrid') {
                    /*取得子表的option，如果没有定义，则跳过该表格*/
                    var childOpts = $(childId).datagrid('options');
                    if (childOpts == undefined) continue;
                    /*将参数传入到子表格，并刷新数据*/
                    childOpts.queryParams['_EQS_' + child.childField] = parentField;//gyzhang@20140424@ChengDu修改：父子关系为相等而不是like
                    $(childId).datagrid('reset');
                }

//			/*树控件作为子对象的情形，暂不考虑*/
//			if(childType == 'tree')
//			{
//			}
            }
        }
    }

    /**
     * 为表格增加一个子对象
     * 目前支持 datagrid 和 tree
     */
    function _addChild(target, child) {
        var opts = _options(target);
        var children = opts.children;
        if (children == undefined) children = [];
        children.push(child);
        opts.children = children;
        _resetOnSelectHandler(target, opts);
        $(target).datagrid('reload');
    }

    /**
     * 初始化方法，用 datagrid 来完成数据表格的绘制
     */
    function _init(target) {
        var opts = _options(target);
        $(target).datagrid(opts);

        /*关联了查询表单，为表单的按钮增加点击事件*/
        if (opts.queryForm != undefined && opts.queryForm.id != undefined) {
            var queryForm = opts.queryForm;
            /*增加查询按钮的点击事件*/
            var query = '#' + (queryForm.query == undefined ? 'btnQuery' : queryForm.query);
            $(query).unbind('click').click(function (event) {
                _query(target, 'query');
            });

            /*增加重置按钮的点击事件*/
            var reset = '#' + (queryForm.reset == undefined ? 'btnReset' : queryForm.reset);
            $(reset).unbind('click').click(function (event) {
                if (opts.advQueryForm) {
                    $('#' + opts.advQueryForm.id).form('clear');
                }
                $('#' + queryForm.id).form('clear');
                /*给combox赋值 请选择*/
                $('.easyui-combobox').combobox('setText', '[请选择...]');

                /*新增在清空查询后的回调函数, modify by mamin 20140422*/
                if (opts.onQueryReset) {
                    try {
                        opts.onQueryReset.call(target);
                    } catch (e) {
                    }
                }
                _query(target, 'query');
            });

            /*高级查询按钮的点击事件*/
            var adv = '#' + (!queryForm.adv ? 'btnAdv' : queryForm.adv);
            $(adv).unbind('click').click(function (event) {
                var advWin = '#' + (!queryForm.advWin ? 'wAdvQuery' : queryForm.advWin);
                $(advWin).window('open');
            });

        }

        /*关联了高级查询，为高级表单的按钮增加点击事件*/
        if (opts.advQueryForm != undefined && opts.advQueryForm.id != undefined) {
            var advQueryForm = opts.advQueryForm;
            /*增加查询按钮的点击事件*/
            var advQuery = '#' + (!advQueryForm.query ? 'btnAdvQuery' : advQueryForm.query);
            $(advQuery).unbind('click').click(function (event) {
                var advWin = '#' + (!advQueryForm.advWin ? 'wAdvQuery' : advQueryForm.advWin);
                $(advWin).window('close');
                _advQuery(target, 'query');
            });

            /*增加重置按钮的点击事件*/
            var advReset = '#' + (!advQueryForm.reset ? 'btnAdvReset' : advQueryForm.reset);
            $(advReset).unbind('click').click(function (event) {
                if (opts.queryForm) {
                    $('#' + opts.queryForm.id).form('clear');
                }
                $('#' + advQueryForm.id).form('clear');

                /*新增在清空查询后的回调函数, modify by mamin 20140422*/
                if (opts.onQueryReset) {
                    try {
                        opts.onQueryReset.call(target);
                    } catch (e) {
                    }
                }

                var advWin = '#' + (!advQueryForm.advWin ? 'wAdvQuery' : advQueryForm.advWin);
                $(advWin).window('close');
                _advQuery(target, 'query');
            });
        }


        /*定义了父对象，为父对象增加一个子对象的定义*/
        if (opts.parent != undefined && opts.parent != null) {
            var parent = opts.parent;
            if (parent.id != undefined && parent.childField != undefined && parent.childField != null) // && parent.parentField != undefined && parent.childField != undefined)
            {
                var parentId = parent.id.substring(0, 1) == '#' ? parent.id : '#' + parent.id;
                if (parent.type == undefined) parent.type = 'datagrid';
                var childId = $(target).attr('id');
                var child = {
                    id: childId,
                    parentField: parent.parentField,
                    childField: parent.childField,
                    type: 'datagrid'
                };
                /*父对象的类型为树*/
                if (parent.type == 'tree') {
                    $(parentId).crudTree('addChild', child);
                } else {
                    $(parentId).crudDatagrid('addChild', child);
                }
            }
        }
    }

    /**
     * 重新查询对象
     * 如果定义了 queryForm 对象，将查询条件加入到 datagrid 的 queryParams 里
     */
    function _query(target) {
        var option = _options(target);
        if (option.queryForm != undefined) {
            /*去的查询表单对象*/
            var queryForm = option.queryForm;
            if (queryForm.id != undefined) {
                var queryFormId = '#' + queryForm.id;
                /*先去的预定义的查询条件*/
                var params = $(target).datagrid('options').queryParams;
                /*将查询字段序列化为 JSON 对象*/
                var fields = $(queryFormId).serializeArray();
                var queryParams = {};
                $.each(fields, function (idx, field) {
                    var values = [];
                    if (queryParams[field.name] !== undefined) {
                        values = queryParams[field.name];
                    }
                    values.push(field.value);
                    queryParams[field.name] = values;
                });

                $.each(queryParams, function (idx, value) {
                    params[idx] = value.join(',');
                });

                //TODO 此处还有问题，当combobox为复选时，重置按钮不起作用，因为复选的combobox在调用
                //TODO form.clear 函数后，不会传递空值，所以无法清除datagrid中的查询条件。
            }
        }

        var _args = arguments;
        if (_args.length > 1) {
            if (_args[1] == 'query') {
                $(target).datagrid('reset');
            }
        } else {
            /*发送查询请求*/
            $(target).datagrid('reload');
        }
    }


    /**
     * 重新查询对象
     * 如果定义了 queryForm 对象，将查询条件加入到 datagrid 的 queryParams 里
     */
    function _advQuery(target) {
        var option = _options(target);
        if (option.advQueryForm) {
            /*去的查询表单对象*/
            var advQueryForm = option.advQueryForm;
            if (advQueryForm.id) {
                var advQueryFormId = '#' + advQueryForm.id;
                /*先去的预定义的查询条件*/
                var params = $(target).datagrid('options').queryParams;
                /*将查询字段序列化为 JSON 对象*/
                var fields = $(advQueryFormId).serializeArray();
                $.each(fields, function (idx, field) {
                    params[field.name] = field.value;
                });
            }
        }

        var _args = arguments;
        if (_args.length > 1) {
            if (_args[1] == 'query') {
                $(target).datagrid('reset');
            }
        } else {
            /*发送查询请求*/
            $(target).datagrid('reload');
        }
    }


    /**
     * 销毁一个crudDatagrid对象
     */
    function _destroy(target) {
        /*清空数据*/
        $.removeData(target, 'crudDatagrid');
    }

    /**
     * crudDatagrid 定义
     */
    $.fn.crudDatagrid = function (options) {
        /*如果第一个参数为 string 类型，则为调用方法，其他的参数作为方法参数*/
        if (typeof options == 'string') {
            /*将参数中的第一个参数去掉，剩下的作为参数传入方法调用*/
            var args = Array.prototype.slice.call(arguments, 1);
            /*判断是否被 crudDatagrid 调用*/
            var isCrudMethod = false;
            /*返回值*/
            var res = undefined;
            this.each(function () {
                /*判断传入的方法是否有定义，如果有，在当前的jQuery对象上进行调用*/
                if ($.isFunction($.fn.crudDatagrid.methods[options])) {
                    isCrudMethod = true;
                    var r = $.fn.crudDatagrid.methods[options].apply(this, args);
                    if (res === undefined) {
                        res = r;
                    }
                }
            });

            if (res !== undefined) {
                return res;
            }

            /*没有在 crudDatagrid 上定义的方法，尝试在 datagrid 上进行调用*/
            if ($.isFunction($.fn.datagrid.methods[options]) && !isCrudMethod) {
                args.unshift(this);
                var r = $.fn.datagrid.methods[options].apply(this, args);
                if (r !== undefined) {
                    return r;
                }
            }
            return this;
        }

        /*初始化 crudDatagrid*/
        options = options || {};
        return this.each(function () {
            /*取回在当前对象上上存放的选项*/
            var data = $.data(this, 'crudDatagrid');
            /*声明一个当前的 jQuery 对象，为了defaultButtonHandler 中进行调用*/
//		var target = this;
            /*
             * 该对象上保存过数据，说明该表格存在，先销毁，再重建。
             */
            if (data) {
                _destroy(this);
            }
            /*
             * 用 defaults 和新的 option 合并，建立新的 datagrid
             */
            var innerOpts = $.extend({}, $.fn.crudDatagrid.defaults, options);
            /*将数据保存回当前对象*/
            $.data(this, 'crudDatagrid', {options: innerOpts});
            /*
             * 对参数进行预处理，用于将一些事件拦截下来，
             * 做自己的处理，同时生成 toolbar。
             */
            _prepareOption(this, innerOpts);
            /*初始化数据表格*/
            _init(this);
        });
    };

    /**
     * crudDatagrid 默认参数定义
     */
    $.fn.crudDatagrid.defaults = {
        /*默认标题*/
        title: '{0}列表',
        /*默认方法 post*/
        method: 'POST',
        /*默认提取数据的URL*/
        url: 'query.json',
        /*默认高度*/
        //height: 300,
        /*默认显示分页*/
        pagination: true,
        /*默认不是自适应*/
        fit: true,
        /*默认不显示标题*/
        noheader: true,
        /*默认分页大小 10*/
        pageSize: 10,
        /*分页选项*/
        pageList: [10, 15, 20, 30, 50],
        /*默认不显示行号*/
        rownumbers: false,
        /*默认为单行选择*/
        singleSelect: true,
        /*默认选中复选框时不选中行*/
        selectOnCheck: false,
        /*默认选中行时不选中复选框*/
        checkOnSelect: false,
        /*默认显示行号*/
        rownumbers: true,
        /*默认的按钮*/
        buttons: ['add', 'edit', 'view', 'remove'], //, 'export'],
        /*新增，编辑，查看的窗口名字*/
        windowID: 'wEdit',
        /*对象的别名*/
        objectAlias: '记录',
        /* controller 的 mapping */
        actionPath: null,
        /*主键值字段*/
        keyField: 'id',
        /*删除记录时是否显示提示*/
        confirmBeforeRemove: true,
        /*新建窗口之前的回调函数*/
        onBeforeAdd: function (args) {
            return true;
        },
        /*编辑窗口之前的回调函数*/
        onBeforeEdit: function (row, args) {
            return true;
        },
        /*浏览窗口之前的回调函数*/
        onBeforeView: function (row, args) {
            return true;
        },
        /*删除之前的回调函数*/
        onBeforeRemove: function (row) {
            return true;
        },
        /*清空查询表单时调用*/
        onQueryReset: function () {
        },
        /*导出参数选择窗口的ID*/
        exportWinID: 'exportWinID',
        /*默认选中第一行*/
        selectFirst: true,
        striped: true
    };

    /**
     * crudDatagrid 的消息定义
     */
    $.fn.crudDatagrid.messages = {
        /*提示的标题*/
        msgTitle: '系统提示',
        /*编辑记录-没有选中记录时的提示*/
        noneSelectOnEdit: '请选择要编辑的“{0}”。',
        /*查看记录-没有选中记录时的提示*/
        noneSelectOnView: '请选择要查看的“{0}”。',
        /*删除记录-没有选中记录时的提示*/
        noneSelectOnRemove: '请选择要删除的“{0}”。',
        /*删除记录时显示的提示信息*/
        confirmOnRemove: '确定要删除选择的“{0}”吗？'
    };

    /**
     * crudDatagrid 的方法定义
     */
    $.fn.crudDatagrid.methods = {
        options: function () {
            return _options(this);
        },
        /*增加一个子对象，对象的类型暂时只能为 datagrid*/
        addChild: function (crud) {
            _addChild(this, crud);
        },
        /*销毁 crudDatagrid*/
        destroy: function () {
            _destroy(this);
        }
    };

})(jQuery);