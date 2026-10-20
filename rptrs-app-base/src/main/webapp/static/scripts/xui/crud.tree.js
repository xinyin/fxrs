/**
 * CRUD tree 的封装，基于tree实现
 * 提供基本的增删改查的功能
 * 以 '_' 开头的方法均为内部方法，外部无法调用
 * 外部依赖：jquery.js, jquery-easyui.js, common.js, windlg.js
 */
;(function ($) {

    /* 全局定义开始 */

    /*默认的按钮文本*/
    var defaultMenuText = {
        add: '增加同级',
        addchild: '增加下级',
        remove: '删除'
    };

    /*默认的按钮ID*/
    var defaultMenuId = {
        add: 'btn_add',
        addchild: 'btn_add_child',
        remove: 'btn_remove'
    };

    /*默认的按钮图标*/
    var defaultMenuCls = {
        add: 'icon-add',
        addchild: 'icon-add',
        remove: 'icon-remove'
    };


    /**
     * 用于在某个对象上获取 option
     */
    function _options(target) {
        /*取回option*/
        var data = $.data(target, 'crudTree');
        if (data) {
            return data.options;
        }
        return null;
    }

    /**
     * 用于从option中提取消息文本
     * 如果option中没有定义该消息，则从 $.fn.crudTree.messages 中提取
     * @param target  要提取消息的 crudTree 对象
     * @param key       消息的键值
     * @returns             返回的消息文本
     */
    function _message(target, key) {
        var option = _options(target);
        if (option[key] == undefined) {
            return $.fn.crudTree.messages[key];
        }
        return option[key];
    }


    /**
     * 对参数进行预处理
     * 主要是对事件的处理，加入自己的事件
     */
    function _prepareOption(target, opts) {
        /* 处理 menu */
        _prepareMenuOption(target, opts);
        /* 处理事件 */
        _prepareEventHandler(target, opts);
        return opts;
    };

    /**
     * 定义默认的弹出窗口回调函数
     * 定义默认的按钮点击处理函数
     * 处理 toolbar
     */
    function _prepareMenuOption(target, opts) {
        /*消息标题*/
        var msgTitle = _message(target, 'msgTitle');
        /*
         * 判断当前树的编辑状态
         * 必须提供 id, saveButton, loadurl, saveurl, removeurl
         * 如果未提供上述属性或属性设置无效，则当前的树还是不可编辑状态
         * 处于不可编辑状态的树可以点击节点，可以增加自定义的事件
         * 但是无法关联表单进行数据处理，右键菜单不可用
         */
        if (opts.editable) {
            if (opts.editForm == undefined) opts.editable = false;
            else if (opts.editForm.id == undefined || opts.editForm.id == null || $.trim(opts.editForm.id) == '') opts.editable = false;
            else if (opts.editForm.loadurl == undefined || opts.editForm.loadurl == null || $.trim(opts.editForm.loadurl) == '') opts.editable = false;
            else if (opts.editForm.saveurl == undefined || opts.editForm.saveurl == null || $.trim(opts.editForm.saveurl) == '') opts.editable = false;
            else if (opts.editForm.existedurl == undefined || opts.editForm.existedurl == null || $.trim(opts.editForm.existedurl) == '') opts.editable = false;
            else if (opts.editForm.removeurl == undefined || opts.editForm.removeurl == null || $.trim(opts.editForm.removeurl) == '') opts.editable = false;
            else if (opts.editForm.keyField == undefined || opts.editForm.keyField == null || $.trim(opts.editForm.keyField) == '') opts.editable = false;
            else {
                opts.editForm.id = opts.editForm.id.substring(0, 1) == '#' ? opts.editForm.id : '#' + opts.editForm.id;
                if ($(opts.editForm.id).length == 0) opts.editable = false;

                opts.editForm.saveButton = opts.editForm.saveButton.substring(0, 1) == '#' ? opts.editForm.saveButton : '#' + opts.editForm.saveButton;
                if ($(opts.editForm.saveButton).length == 0) opts.editable = false;
            }
        }

        /*表单验证通过，处理url*/
        if (opts.editable) {
            opts.editForm.loadurl = _resolveActionPath(target, opts.editForm.loadurl);
            opts.editForm.saveurl = _resolveActionPath(target, opts.editForm.saveurl);
            opts.editForm.existedurl = _resolveActionPath(target, opts.editForm.existedurl);
            opts.editForm.removeurl = _resolveActionPath(target, opts.editForm.removeurl);

        }

        /*处理url*/
        if (!opts.url)
            opts.url = _resolveActionPath(target, opts.url);

        /*
         * 此处定义按钮的默认处理函数。
         */
        opts.defaultMenuHandler = {
            /*
             * 增加同级按钮的默认处理函数
             */
            add: function () {
                /*隐藏右键菜单*/
                $(opts.contextMenuId).menu('hide');
                var node = $(target).tree('getSelected');
                var pNode = $(target).tree('getParent', node.target);

                var newNode = $(target).tree('append', {
                    parent: pNode ? pNode.target : null,
                    data: [{
                        text: $.message.format(_message(target, 'defaultNewText'), opts.objectAlias)
                    }]
                });
                /*标识下次加载数据时要延迟200毫秒加载*/
                opts.loadChildDelay = 200;
                /*设置新增节点选中状态*/
                if (pNode == null) {
                    var roots = $(target).tree('getRoots');
                    $(target).tree('select', roots[roots.length - 1].target);
                } else {
                    var children = $(target).tree('getChildren', pNode.target);
                    $(target).tree('select', children[children.length - 1].target);
                }
                if (opts.onAfterAddData && $.isFunction(opts.onAfterAddData)) {
                    opts.onAfterAddData.call(target, pNode, newNode);
                }
            },
            /*
             * 增加下级按钮的默认处理函数
             */
            addchild: function () {
                /*隐藏右键菜单*/
                $(opts.contextMenuId).menu('hide');

                var node = $(target).tree('getSelected');
                /*当前节点未保存，不能添加子节点*/
                if (node.id == undefined || node.id == null) {
                    $(opts.contextMenuId).menu('hide');
                    var msg = $.message.format(_message(target, 'parentNotSaved'), opts.objectAlias);
                    msg = _resolvePlaceholder(msg, node);
                    $.messager.alert(msgTitle, msg, 'info');
                    return;
                }
                /*添加子节点*/
                var newNode = $(target).tree('append', {
                    parent: (node ? node.target : null),
                    data: [{
                        text: $.message.format(_message(target, 'defaultNewChildText'), opts.objectAlias)
                    }]
                });
                /*设置新增节点未选中状态*/
                var children = $(target).tree('getChildren', node.target);
                $(target).tree('select', children[children.length - 1].target);
                if (opts.onAfterAddData && $.isFunction(opts.onAfterAddData)) {
                    opts.onAfterAddData.call(target, node, newNode);
                }
            },
            /*
             * 删除按钮的默认处理函数
             * 删除操作默认不打开窗口处理，将当前所有选中的记录ID合并成一个数组，发送回指定的URL
             * 并获得服务器端的响应，服务器的响应结果应该为一个对象
             * 包含 success (true | false) 属性 和 msg 属性
             * 该方法默认会有一个提示信息：‘确认要删除选中的 *** 吗？’
             * 可以通过 confirmBeforeRemove 属性来打开或关闭该提示
             * 可以通过 confirmOnRemove 属性来设置提示的消息
             */
            remove: function () {
                /*隐藏右键菜单*/
                $(opts.contextMenuId).menu('hide');
                /*
                 * 在删除数据之前，调用 onBeforeRemoveData 事件
                 * 如果该事件返回 false，则不继续删除数据
                 */
                if (opts.onBeforeRemoveData != undefined && $.isFunction(opts.onBeforeRemoveData)) {
                    var result = opts.onBeforeRemoveData.call(target, node);
                    if (!result) return;
                }

                var node = $(target).tree('getSelected');
                /*只有一个节点并且尚未保存，不允许删除*/
                if ($(target).tree('getRoots').length == 1 && $(target).tree('getChildren', $(target).tree('getRoot').target) == 0 && (node.id == undefined || node.id == null)) {
                    return;
                }

                /*取回子节点，判断是否递归删除*/
                var children = $(target).tree('getChildren', node.target);
                /*有子节点，但不允许递归删除，提示错误信息，返回*/
                if (children.length > 0 && !opts.recursiveRemove) {
                    var msg = $.message.format(_message(target, 'recursiveNotAllowed'), opts.objectAlias);
                    msg = _resolvePlaceholder(msg, node);
                    $.messager.alert(msgTitle, msg, 'info');
                    return;
                }
                /*允许递归删除，包含子节点，必须提示*/
                if (children.length > 0) {
                    var msg = $.message.format(_message(target, 'confirmOnRecursive'), opts.objectAlias);
                    msg = _resolvePlaceholder(msg, node);
                    $.messager.confirm(msgTitle, msg, function (b) {
                        if (b) {
                            _performRemove();
                        }
                    });
                }
                /*不包含子节点，设置了删除前提示*/
                else if (opts.confirmBeforeRemove) {
                    var msg = $.message.format(_message(target, 'confirmOnRemove'), opts.objectAlias);
                    msg = _resolvePlaceholder(msg, node);
                    $.messager.confirm(msgTitle, msg, function (b) {
                        if (b) {
                            _performRemove();
                        }
                    });
                }
                /*不提示，直接删除*/
                else {
                    _performRemove();
                }

                /**
                 * 为了内部重复调用而编写
                 */
                function _performRemove() {
                    var deleted = false;
                    /*虚拟节点，直接删除*/
                    if (node.id == undefined || node.id == null) {
                        deleted = true;
                    } else {
                        /*取回移除的 url ，用 keyField 来替换掉 {0}, 用 id 的值来替换掉 {id}*/
                        var url = $.message.format(opts.editForm.removeurl, opts.editForm.keyField);
                        url = _resolvePlaceholder(url, node);
                        var method = opts.editForm.method == undefined ? 'POST' : opts.editForm.method;

                        $.ajax({
                            async: false,
                            type: method,
                            url: url,
                            success: function (data, textStatus, jqXHR) {
//							$.messager.alert(msgTitle, data.msg, 'info');
                                if (data.success) {
                                    deleted = true;
                                } else {
                                    var msg = $.message.format(_message(target, 'removeDataFailed'), opts.objectAlias);
                                    msg = _resolvePlaceholder(msg, node);
                                    $.messager.alert(msgTitle, msg, 'info');
                                }
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                var msg = $.message.format(_message(target, 'removeDataFailed'), opts.objectAlias);
                                msg = _resolvePlaceholder(msg, node);
                                $.messager.alert(msgTitle, msg + '[' + textStatus + ']', 'info');
                                deleted = false;
                            }
                        });
                    }

                    /*删除本地节点*/
                    if (deleted) {
                        var pNode = $(target).tree('getParent', node.target);
                        _removeLocalNodeRecursive(target, node);
                        if (pNode != null) {
                            $(target).tree('select', pNode.target);
                        } else {
                            var roots = $(target).tree('getRoots');
                            if (roots.length > 0) {
                                $(target).tree('select', roots[0].target);
                            } else {
                                _appendRoot(target);
                            }
                        }
                    }
                };

                /**
                 * 递归删除本地节点
                 */
                function _removeLocalNodeRecursive(target, node) {
                    var children = $(target).tree('getChildren', node.target);
                    if (children.length > 0) {
                        for (var i = 0; i < children.length; i++) {
                            var childNode = children[i];
                            arguments.callee(target, childNode);
                        }
                    }
                    $(target).tree('remove', node.target);
                }
            }
        };

        var innerMenus = [];
        /*自定义了 menus， 否则不显示任何按钮*/
        if (opts.menus != undefined && opts.menus != null && $.isArray(opts.menus)) {
            /*预定义的menus*/
            var menuDef = opts.menuDef;
            /*要显示的按钮*/
            var menus = opts.menus;
            /*按钮文字*/
            var menuText = opts.menuText;
            /*按钮图标*/
            var menuCls = opts.menuCls;
            /*按钮ID*/
            var menuId = opts.menuId;
            /*按钮处理函数*/
            var menuHandler = opts.menuHandler;
            var defaultMenuHandler = opts.defaultMenuHandler;

            /*生成新的 menu*/
            for (var i = 0; i < menus.length; i++) {
                var menuName = menus[i];
                if (menuName == '-') {
                    innerMenus.push(menuName);
                    continue;
                }

                /*取回按钮的ID, 文字，图标，处理函数等*/
                var text = menuText != undefined
                    ? (menuText[menuName] != undefined ? menuText[menuName]
                        : defaultMenuText[menuName]) : defaultMenuText[menuName];
                if (text == undefined) {
                    text = menuDef[menuName].text;
                    if (text == undefined) continue;
                }

                var id = menuId != undefined
                    ? (menuId[menuName] != undefined ? menuId[menuName]
                        : defaultMenuId[menuName]) : defaultMenuId[menuName];
                if (id == undefined) {
                    id = menuDef[menuName].id;
                    if (id == undefined) continue;
                }


                var cls = menuCls != undefined
                    ? (menuCls[menuName] != undefined ? menuCls[menuName]
                        : defaultMenuCls[menuName]) : defaultMenuCls[menuName];
                if (cls == undefined) {
                    cls = menuDef[menuName].iconCls;
                }

                var handler = menuHandler != undefined
                    ? (menuHandler[menuName] != undefined ? menuHandler[menuName]
                        : defaultMenuHandler[menuName]) : defaultMenuHandler[menuName];
                if (handler == undefined) {
                    handler = menuDef[menuName].handler;
                }

                /*按钮没有定义处理函数，跳过*/
                if (!$.isFunction(handler)) continue;
                var menu = {
                    id: id,
                    name: menuName,
                    text: text,
                    iconCls: cls,
                    handler: handler
                };
                innerMenus.push(menu);
            }
            /*设置 innerMenu 属性*/
            opts.innerMenus = innerMenus;
        }

        /* 生成新的 menu ，表单设置无效时，不生成菜单*/
        if (innerMenus.length > 0 && opts.editable) {
            var contextMenuId = $(target).attr('id') + '_menu';

            /*循环生成菜单的HTML*/
            var html = '<div id="' + contextMenuId + '" class="easyui-menu" style="width:120px">';
            for (var i = 0; i < innerMenus.length; i++) {
                var menu = innerMenus[i];
                if (typeof menu == 'string') {
                    html += '<div class="menu-sep"></div>';
                } else {
                    html += '<div id="' + menu.id + '" iconCls="' + menu.iconCls + '">' + menu.text + '</div>';
                }
            }
            html += '</div>';
            /*将菜单的HTML追加到 BODY */
            $(html).appendTo($(document.body));
            /*初始化 menu*/
            contextMenuId = '#' + contextMenuId;
            opts.contextMenuId = contextMenuId;
            $(contextMenuId).menu();
            /*为每个menu增加点击事件，并设置菜单的状态*/
            for (var i = 0; i < innerMenus.length; i++) {
                var menu = innerMenus[i];
                if (typeof menu == 'string') continue;
                $('#' + menu.id).unbind('click').click(menu.handler);
            }
            /*覆盖掉默认的onContextMenu, 因此在 curdTree 中定义 onContextMenu 是没有任何作用的*/
            opts.onContextMenu = function (e, node) {
                e.preventDefault();
                $(target).tree('select', node.target);
                $(target).tree('expand', node.target);
                if (node != null) {
                    $(contextMenuId).menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                }
            };
        }
    }

    /**
     * 处理事件
     * 增加默认事件
     * 拦截 tree 的事件
     */
    function _prepareEventHandler(target, opts) {
        /*设置默认的事件*/
        if (opts.onBeforeSaveData == undefined) {
            /*设置保存数据之前的默认事件，进行表单校验*/
            opts.onBeforeSaveData = function (node, data) {
                var opts = _options(target);
                var formId = opts.editForm.id;
                var result = $(formId).form('validate');
                if (!result) {
                    var msgTitle = _message(target, 'msgTitle');
                    var msg = $.message.format(_message(target, 'validDataFailed'), opts.objectAlias);
                    msg = _resolvePlaceholder(msg, node);
                    $.messager.alert(msgTitle, msg, 'info');
                    return false;
                }
                return true;
            };
        }
        /* 重新设置 onLoadSuccess*/
        _resetOnLoadSuccessHandler(target, opts);
        /* 重新设置 onclick */
        //_resetOnClickHandler(target, opts);
        /* 重新设置 onSelect*/
        _resetOnSelectHandler(target, opts);
    }

    /**
     * 重新设置加载成功事件
     */
    function _resetOnLoadSuccessHandler(target, opts) {
        /*
         * 拦截 onLoadSuccess 事件
         */
        opts.userOnLoadSuccess = opts.onLoadSuccess;
        var userOnLoadSuccessHandler = opts.userOnLoadSuccess;
        var formId = opts.editForm.id;

        opts.onLoadSuccess = function (node, data) {
            /*获得根节点，如果没有根节点，增加虚拟节点*/
            var root = $(target).tree('getRoot');

            /*如果设置了编辑模式，将表单设置为不可编辑状态，并且为表单的保存按钮增加事件*/
            if (opts.editable) {
                if (root == null) {
                    _appendRoot(target);
                    root = $(target).tree('getRoot');
                }

//			_setFormEditable(target, false);
                $(opts.editForm.saveButton).unbind('click').click(function (event) {
                    event.preventDefault();
                    /*增加主键值验证，只在当前节点的ID为空时验证*/
                    var node = $(target).tree('getSelected');
                    if (node != null && (node.id == undefined || node.id == null)) {
                        var existed = _existedFormData(target);
                        if (existed) {
                            var value = '';
                            var keyField = $(formId).find('input[name="' + opts.editForm.keyField + '"]');
                            if (keyField.length > 0) {
                                value = $(keyField[0]).val();
                            }
                            if (value == '') value = '主键值';
                            var msgTitle = _message(target, 'msgTitle');
                            $.messager.alert(msgTitle, $.message.format(_message(target, 'existedDataFailed'), value), 'info');
                        } else {
                            /*保存表单数据*/
                            _saveFormData(target);
                        }
                    } else {
                        /*保存表单数据*/
                        _saveFormData(target);
                    }
                });
            }
            /*选中根节点*/
            if (root != null) {
                $(target).tree('select', root.target);
            }

            /*调用用户自己的 onLoadSuccess 事件*/
            if (userOnLoadSuccessHandler != undefined) userOnLoadSuccessHandler.call(target, node, data);
        };
    }


    /**
     * 重新设置 onClick 事件
     */
    function _resetOnClickHandler(target, opts) {
        /*
         * 拦截 onClick 事件
         */
        opts.userOnClick = opts.onClick;
        var userOnClickHandler = opts.userOnClick;
        /*
         * 增加点击节点时的默认行为，将节点的信息加载到表单，
         * 如果没有定义表单，或者没有定义表单的id, saveButton属性，则略过
         */
        opts.onClick = function (node) {
            /* 编辑模式 */
            if (opts.editable) {
                _loadFormData(target, node);
            }
            if (userOnClickHandler != undefined) userOnClickHandler.call(target, node);
        };
    }

    /**
     * 重新设置 onSelect 事件
     */
    function _resetOnSelectHandler(target, opts) {
        /*
         * 拦截 onSelect 事件
         */
        opts.userOnSelect = opts.onSelect;
        var userOnSelectHandler = opts.userOnSelect;
        /*
         * 增加点击节点时的默认行为，将节点的信息加载到表单，
         * 如果没有定义表单，或者没有定义表单的id, saveButton属性，则略过
         */
        opts.onSelect = function (node) {
            /* 编辑模式 */
            if (opts.editable) {
                _loadFormData(target, node);
            }
            /*刷新子对象*/
            _reloadChildren(target, node);
            /*调用用户自定义的 onSelect 事件*/
            if (userOnSelectHandler != undefined) userOnSelectHandler.call(target, node);
        };
    }

    /**
     * 刷新子对象的数据，目前只支持 datagrid
     */
    function _reloadChildren(target, node) {
        var opts = _options(target);
        /* 定义了子对象*/
        if (opts.children != undefined && opts.children != null && opts.children.length > 0) {
            /*
             * 遍历所有的子对象，将 parentField 中定义的字段的值作为参数，
             * 放到子对象的 queryParams 中，并刷新子对象
             */
            for (var i = 0; i < opts.children.length; i++) {
                var child = opts.children[i];
                if (child.id == undefined || child.parentField == undefined || child.childField == undefined) continue;
                var childId = child.id.substring(0, 1) == '#' ? child.id : '#' + child.id;

                /*子对象的类型暂不考虑*/
                var childType = child.type == undefined ? 'datagrid' : child.type;

                /*取回树的当前节点的对应字段，如果没有对应字段，在 attributes 中查找，找不到则赋值为空*/
                var parentField = node[child.parentField];
                if (parentField == undefined) {
                    if (node.attributes != undefined && node.attributes != null) {
                        parentField = node.attributes[child.parentField];
                    }
                }
                if (parentField == undefined) parentField = null;

                if (childType == 'datagrid') {
                    /*取得子表的option，如果没有定义，则跳过该表格*/
                    var childOpts = $(childId).datagrid('options');
                    if (childOpts == undefined) continue;
                    /*将参数传入到子表格，并刷新数据*/
                    childOpts.queryParams['_EQS_' + child.childField] = parentField;
                    /*
                     * 此处设置根据延迟时间，判断加载子 datagrid 的延迟
                     * 由于 tree 在增加节点后会选中父节点，导致 datagrid 重加载
                     * 在增加同级节点时有可能因为 datagrid 的异步加载机制而导致
                     * 显示了父节点的内容，因此设置一个延迟时间的机制
                     * 在网络状况良好的情况下，200ms足以加载数据
                     */
                    var delay = opts.loadChildDelay == undefined ? 500 : opts.loadChildDelay;
                    window._loadChildData = function () {
                        $(childId).datagrid('reset');
                    };
                    setTimeout('_loadChildData();', delay);
                    opts.loadChildDelay = 0;
                }

//			/*树对象作为子对象出现的情形，暂不考虑*/
//			if(childType == 'tree')
//			{
//			}
            }
        }
    }

    /**
     * 初始化方法，用 tree 来完成树的绘制
     */
    function _init(target) {
        var opts = _options(target);
        $(target).tree(opts);

        /*树对象作为子对象出现的情形还需考虑才能实现*/
//	/*定义了父对象，为父对象增加一个子对象的定义*/
//	if(opts.parent != undefined && opts.parent != null)
//	{
//		var parent = opts.parent;
//		if(parent.id != undefined && parent.parentField != undefined && parent.childField != undefined)
//		{
//			var parentId = parent.id.substring(0, 1) == '#' ? parent.id : '#' + parent.id;
//			if(parent.type == undefined) parent.type = 'datagrid';
//			var childId = $(target).attr('id');
//			var child = {id:childId, parentField:parent.parentField, childField:parent.childField, type: parent.type};
//			/*父对象的类型为树*/
//			if(parent.type == 'tree')
//			{
//				$(parentId).crudTree('addChild', child);
//			}
//			else
//			{
//				$(parentId).crudDatagrid('addChild', child);
//			}
//		}
//	}

    }

    /**
     * 为树增加一个子对象
     * 目前支持 datagrid 和 tree
     */
    function _addChild(target, child) {
        var opts = _options(target);
        var children = opts.children;
        if (children == undefined) children = [];
        children.push(child);
        opts.children = children;
        _resetOnSelectHandler(target, opts);
        var root = $(target).tree('getRoot');
        if (root != null) {
            $(target).tree('reload', root.target);
        }
    }


    /**
     * 处理 url 中的占位符
     * 用于替换在 url 中出现的占位符
     * 使用当前树节点作为待替换的对象
     * @param url          要处理的url
     * @param record     使用的节点对象
     * @returns                 返回替换后的url
     */
    function _resolvePlaceholder(url, node) {
        if (url == undefined || url == null) return null;
        var newUrl = url;
        newUrl = $.message.resolvePlaceholder(newUrl, node);
        if (node.attributes != undefined) {
            newUrl = $.message.resolvePlaceholder(newUrl, node.attributes);
        }
        return newUrl;
    }

    /**
     * 用actionPath的定义处理相对URL，返回新的url
     */
    function _resolveActionPath(target, originUrl) {
        var opts = _options(target);
        var newUrl = originUrl;
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
            url.push(newUrl);
            newUrl = url.join('/');
        }

        newUrl = _resolveUrl(newUrl);
        return newUrl;
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
        var urlLocation = location.href.toLowerCase();
        if (urlLocation.lastIndexOf('/') != -1) {
            urlLocation = urlLocation.substring(0, urlLocation.lastIndexOf('/') + 1);
            return urlLocation + url;
        }
    }

    /**
     * 新增虚拟根节点
     */
    function _appendRoot(target) {
        var opts = _options(target);
        var defaultRootText = $.message.format(_message(target, 'defaultRootText'), opts.objectAlias);
        $(target).tree('append', {
            parent: null,
            data: [{
                text: defaultRootText
            }]
        });
    }

    /**
     * 加载表单数据
     */
    function _loadFormData(target, node) {
        var opts = _options(target);
        /* 非编辑模式，不做任何操作 */
        if (!opts.editable) return;

        /* 清空表单，设置表单可编辑 */
//	_setFormEditable(target, true);
        $(opts.editForm.id).form('clear');
        _setFormReadOnly(target, node, false);

        /*有ID的实体数据，加载数据*/
        if (node.id != undefined && node.id != null) {
            /*
             * 在数据加载之前，调用 onBeforeLoadData 事件
             * 如果该事件返回 false，则不继续加载数据
             */
            if (opts.onBeforeLoadData != undefined && $.isFunction(opts.onBeforeLoadData)) {
                var result = opts.onBeforeLoadData.call(target, node);
                if (!result) return;
            }

            var formId = opts.editForm.id;
            /* 处理数据加载，如果没有定义 editForm.loadurl 则不进行处理 */
            if (opts.editForm.loadurl == undefined || opts.editForm.loadurl == null || $.trim(opts.editForm.loadurl) == '') return;
            var url = _resolvePlaceholder(opts.editForm.loadurl, node);
            var method = opts.editForm.method == undefined ? 'POST' : opts.editForm.method;
            $.ajax({
                async: false,
                type: method,
                url: url,
                success: function (data) {
                    /*
                     * 在加载数据成功之后，装入数据之前，
                     * 获得当前节点的父节点，根据 editForm 的设置，
                     * 为表单字段赋值
                     */
                    var pNode = $(target).tree('getParent', node.target);
                    if (pNode != null) {
                        if (opts.editForm.parentKeyField != undefined) {
                            var parentKeyField = $(formId).find('input[name="' + opts.editForm.parentKeyField + '"]');
                            if (parentKeyField.length > 0) {
                                $(parentKeyField[0]).val(pNode.id);
                            }
                        }

                        if (opts.editForm.parentTextField != undefined) {
                            var parentTextField = $(formId).find('input[name="' + opts.editForm.parentTextField + '"],textarea[name="' + opts.editForm.parentTextField + '"]');
                            if (parentTextField.length > 0) {
                                $(parentTextField[0]).val(pNode.text);
                            }
                        }
                    }

                    try {
                        $(formId).form('load', data);
                    } catch (e) {
                    }

                    /*加载数据之后，如果定义了 keyFieldEditable = false，设置 keyField 字段为只读*/
                    if (!opts.editForm.keyFieldEditable) {
                        var keyField = $(formId).find('input[name="' + opts.editForm.keyField + '"]');
                        if (keyField.length > 0) {
                            $(keyField[0]).attr('readOnly', 'readOnly');
                        }
                    }

                    /*在数据加载之后，调用 onLoadDataSuccess 事件*/
                    if (opts.onLoadDataSuccess != undefined && $.isFunction(opts.onLoadDataSuccess)) {
                        opts.onLoadDataSuccess.call(target, pNode, node, data, $(formId));
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    var msgTitle = _message(target, 'msgTitle');
                    var msg = $.message.format(_message(target, 'loadDataFailed'), opts.objectAlias);
                    msg = _resolvePlaceholder(msg, node);
                    $.messager.alert(msgTitle, msg + '[' + textStatus + ']', 'info');
                }
            });
        } else {
            var formId = opts.editForm.id;
            var pNode = $(target).tree('getParent', node.target);
            if (pNode != null) {
                if (opts.editForm.parentKeyField != undefined) {
                    var parentKeyField = $(formId).find('input[name="' + opts.editForm.parentKeyField + '"]');
                    if (parentKeyField.length > 0) {
                        $(parentKeyField[0]).val(pNode.id);
                    }
                }

                if (opts.editForm.parentTextField != undefined) {
                    var parentTextField = $(formId).find('input[name="' + opts.editForm.parentTextField + '"]');
                    if (parentTextField.length > 0) {
                        $(parentTextField[0]).val(pNode.text);
                    }
                }
            }

            if (opts.editForm.textField != undefined) {
                var textField = $(opts.editForm.id).find('input[name="' + opts.editForm.textField + '"]');
                if (textField.length > 0) {
                    $(textField[0]).val(node.text);
                }
            }

            /*加载默认值*/
            if (opts.defaultValues) {
                for (var p in opts.defaultValues) {
                    var expr = ':input[name="' + p + '"]';
                    var field = $(opts.editForm.id).find(expr);
                    if (field.length > 0) {
                        try {
                            field.val(opts.defaultValues[p]);
                        } catch (e) {
                        }
                        /*尝试为 combobox 复制，可能出现异常*/
                        try {
                            $('#' + p).combobox('setValue', opts.defaultValues[p]);
                        } catch (e) {
                        }
                    }
                }
            }
        }
    }

    /**
     * 验证主键值是否存在
     */
    function _existedFormData(target) {
        var opts = _options(target);
        /* 非编辑模式，不做任何操作 */
        if (!opts.editable) return false;
        /* 处理数据加载，如果没有定义 editForm.loadurl 则不进行处理 */
        if (opts.editForm.existedurl == undefined || opts.editForm.existedurl == null || $.trim(opts.editForm.existedurl) == '') return false;

        var existed = false;
        var method = opts.editForm.method == undefined ? 'POST' : opts.editForm.method;
        var formId = opts.editForm.id;
        var data = $(formId).serializeArray();
        $.ajax({
            async: false,
            type: method,
            url: opts.editForm.existedurl,
            data: data,
            success: function (data) {
                existed = data;
            }
        });
        return existed;
    }


    /**
     * 保存表单数据
     * 在保存数据的前后，为用户留出事件调用
     */
    function _saveFormData(target) {
        var msgTitle = _message(target, 'msgTitle');
        var opts = _options(target);
        var formId = opts.editForm.id;
        var data = $(formId).serializeArray();
        /*获得当前的树节点*/
        var node = $(target).tree('getSelected');
        /*在数据保存之前，调用 onBeforeSaveData 事件，如该事件返回false，则不再继续进行*/
        if (opts.onBeforeSaveData != undefined && $.isFunction(opts.onBeforeSaveData)) {
            var result = opts.onBeforeSaveData.call(target, node, data);
            if (!result) return;
        }
        /* 处理数据保存，如果没有定义 editForm.saveurl 则不进行处理 */
        if (opts.editForm.saveurl == undefined || opts.editForm.saveurl == null || $.trim(opts.editForm.saveurl) == '') return;
        var url = _resolvePlaceholder(opts.editForm.saveurl, node);
        var method = opts.editForm.method == undefined ? 'POST' : opts.editForm.method;
        /*POST方法调用数据保存*/
        $.ajax({
            async: false,
            type: method,
            url: url,
            data: data,
            success: function (data) {
                if (data != undefined && data.object != undefined) {
                    $.messager.alert(msgTitle, data.msg, 'info', function () {
                        if (data.success) {
                            /*重新加载表单数据，否则再次保存会因为乐观锁而失败*/
                            var obj = data.object;
                            if (obj != undefined) {
                                $(formId).form('load', obj);
                                /*
                                 * 重新加载完数据后，更新树节点
                                 * 根据 editForm 的相关属性更新节点
                                 * 如果没有设置相关属性，使用 id 和 text 属性尝试
                                 * 如果没有，则不更新
                                 */
                                var idUpdate = false;
                                var textUpdate = false;
                                /*更新ID*/
                                if (opts.editForm.keyField != undefined) {
                                    var keyField = $(formId).find('input[name="' + opts.editForm.keyField + '"]');
                                    if (keyField.length > 0) {
                                        node.id = $(keyField[0]).val();
                                        idUpdate = true;
                                    }
                                }
                                if (!idUpdate) {
                                    if (obj.id != undefined) node.id = obj.id;
                                }
                                /*更新text*/
                                if (opts.editForm.textField != undefined) {
                                    var textField = $(formId).find('input[name="' + opts.editForm.textField + '"]');
                                    if (textField.length > 0) {
                                        node.text = $(textField[0]).val();
                                        textUpdate = true;
                                    }
                                }
                                if (!textUpdate) {
                                    if (obj.text != undefined) node.text = obj.text;
                                }

                                if (idUpdate || textUpdate) $(target).tree('update', node);
                            }

                            /*加载数据之后，如果定义了 keyFieldEditable = false，设置 keyField 字段为只读*/
                            if (!opts.editForm.keyFieldEditable) {
                                var keyField = $(formId).find('input[name="' + opts.editForm.keyField + '"]');
                                if (keyField.length > 0) {
                                    $(keyField[0]).attr('readOnly', 'readOnly');
                                }
                            }

                            /*调用保存之后的事件*/
                            if (opts.onSaveDataSuccess != undefined && $.isFunction(opts.onSaveDataSuccess)) {
                                /*获得当前节点的父节点*/
                                var pNode = $(target).tree('getParent', node.target);
                                opts.onSaveDataSuccess.call(target, pNode, node, data, $(formId));
                            }
                        }
                    });
                } else {
                    var msg = $.message.format(_message(target, 'saveDataFailed'), opts.objectAlias);
                    msg = _resolvePlaceholder(msg, node);
                    $.messager.alert(msgTitle, msg, 'info');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                var msg = $.message.format(_message(target, 'saveDataFailed'), opts.objectAlias);
                msg = _resolvePlaceholder(msg, node);
                $.messager.alert(msgTitle, msg + '[' + textStatus + ']', 'info');
            }
        });
    }

    /**
     * 工具方法，设置表单是否可编辑
     * 设置表单所有字段的可用状态
     */
    function _setFormEditable(target, editable) {
        var opts = _options(target);
        var formId = opts.editForm.id;
        var saveButton = opts.editForm.saveButton;
        var fields = $(formId + ' :input');
        /*设置字段*/
        fields.each(function (index, element) {
            if ($(this).attr('name') != undefined) {
                /*设置为可编辑*/
                if (editable) {
                    $(this).removeAttr('disabled');
                } else $(this).attr('disabled', 'disabled');
            }
        });
        /*设置保存按钮*/
        if (editable) {
            $(saveButton).removeAttr('disabled');
        } else {
            $(saveButton).attr('disabled', 'disabled');
        }
    }

    /**
     * 工具方法，设置表单字段的只读属性
     */
    function _setFormReadOnly(target, node, readonly) {
        var opts = _options(target);
        var formId = opts.editForm.id;
        var fields = $(formId + ' :input');
        /*设置字段*/
        fields.each(function (index, element) {
            if ($(this).attr('name') != undefined) {
                /*设置为只读*/
                if (readonly) {
                    $(this).attr('readOnly', 'readOnly');
                } else {
                    /*为 dialog.textbox 增加的判断，如果字段强制要求只读，则不去掉只读属性*/
                    if (!$(this).attr('forceReadOnly')) {
                        $(this).removeAttr('readOnly');
                    }
                }

                /*设置为父节点的ID和text字段，不允许编辑*/
                if ($(this).attr('name') == opts.editForm.parentKeyField || $(this).attr('name') == opts.editForm.parentTextField) {
                    $(this).attr('readOnly', 'readOnly');
                }

                /*设置ID字段在编辑时不可用*/
//			if($(this).attr('name') == opts.editForm.keyField && (node.id != undefined && node.id != null))
//			{
//				$(this).attr('readOnly', 'readOnly');
//			}
            }
        });
    }

    /**
     * 销毁一个crudTree对象
     */
    function _destroy(target) {
        /*清空数据*/
        $.removeData(target, 'crudTree');
    }


    /**
     * crudTree 定义
     */
    $.fn.crudTree = function (options) {
        /*如果第一个参数为 string 类型，则为调用方法，其他的参数作为方法参数*/
        if (typeof options == 'string') {
            /*将参数中的第一个参数去掉，剩下的作为参数传入方法调用*/
            var args = Array.prototype.slice.call(arguments, 1);
            /*判断是否被 crudTree 调用*/
            var isCrudMethod = false;
            /*返回值*/
            var res = undefined;
            this.each(function () {
                /*判断传入的方法是否有定义，如果有，在当前的jQuery对象上进行调用*/
                if ($.isFunction($.fn.crudTree.methods[options])) {
                    isCrudMethod = true;
                    var r = $.fn.crudTree.methods[options].apply(this, args);
                    if (res === undefined) {
                        res = r;
                    }
                }
            });

            if (res !== undefined) {
                return res;
            }

            /*没有在 crudTree 上定义的方法，尝试在 tree 上进行调用*/
            if ($.isFunction($.fn.tree.methods[options]) && !isCrudMethod) {
                args.unshift(this);
                var r = $.fn.tree.methods[options].apply(this, args);
                if (r !== undefined) {
                    return r;
                }
            }
            return this;
        }

        /*初始化 crudTree*/
        options = options || {};
        return this.each(function () {
            /*取回在当前对象上上存放的选项*/
            var data = $.data(this, 'crudTree');
            /*
             * 该对象上保存过数据，说明该表格存在，先销毁，再重建。
             */
            if (data) {
                _destroy(this);
            }
            /*将editForm属性单独合并出来*/
            var editForm = $.extend({}, $.fn.crudTree.defaults.editForm, options.editForm);
            /*
             * 用 defaults 和新的 option 合并，建立新的 tree
             */
            var innerOpts = $.extend({}, $.fn.crudTree.defaults, options);
            innerOpts.editForm = editForm;
            /*将数据保存回当前对象*/
            $.data(this, 'crudTree', {options: innerOpts});
            /*
             * 对参数进行预处理，用于将一些事件拦截下来，
             * 做自己的处理，同时生成 toolbar。
             */
            _prepareOption(this, innerOpts);
            /*将数据保存回当前对象*/
            $.data(this, 'crudTree', {options: innerOpts});
            /*初始化数据表格*/
            _init(this);
        });
    };

    /**
     * crudTree 默认参数定义
     */
    $.fn.crudTree.defaults = {
        /*加载数据方法*/
        method: 'get',
        /*加载数据URL*/
        url: 'lazy.json',
        /*controll 路径*/
        actionPath: null,
        /*是否允许编辑，默认允许*/
        editable: true,
        /*是否允许递归删除*/
        recursiveRemove: false,
        /*菜单定义*/
        menuDef: {},
        /*默认的菜单*/
        menus: ['add', 'addchild', '-', 'remove'],
        /*菜单之间是否有分隔线*/
        menuSplit: false,
        /*默认的菜单文本*/
        menuText: defaultMenuText,
        /*默认的菜单ID*/
        menuId: defaultMenuId,
        /*对象的别名*/
        objectAlias: '节点',
        /*删除记录时是否显示提示*/
        confirmBeforeRemove: true,
        /*
         * id 属性用来定义和tree关联的表单 id, 默认为'editForm'
         * saveButton 属性用来设置表单的保存按钮的ID，默认为'btnSave'
         * crudTree 会为 saveButton 增加处理事件，无需自定义
         * method 用来定义和服务端交互数据的提交方法，默认为 post
         * loadurl, saveurl, existedurl, removeurl
         * 为与服务端交互的地址，默认分别为：
         * edit/{id}.json, save, existed, delete?{0}={id}
         * loadurl 中的 {id}代表当前节点的 id
         * removeurl 中的{0}会被 keyField设置的值替换
         * removeurl 中的{id}代表当前节点的id
         * 注1：loadurl请求的方法必须返回 Map<String, Object> 类型，
         * 其中必须有名为 "object" 的对象，代表保存后的对象，用来将
         * ID的值带回来更新树节点
         * 注2: id, loadurl, saveurl, existedurl, removeurl 必须设置，
         * 如果不设置，表单无效，不能进行增删改查的操作。
         * 注3: 用于在url中进行替换的标识符可以是 node节点的属性名，
         * 例如 id， text等，也可以是node.attributes 中定义的属性名
         */
        editForm: {
            /*表单ID*/
            id: 'editForm',
            /*与后台交互的方法*/
            method: 'POST',
            /*加载数据的 url*/
            loadurl: 'edit/{id}.json',
            /*保存的 url*/
            saveurl: 'save',
            /*验证主键的 url*/
            existedurl: 'existed',
            /*删除的url*/
            removeurl: 'delete?{0}={id}',
            /*保存按钮的ID*/
            saveButton: 'btnSave',
            /*设置在编辑时是否可以编辑主键值字段*/
            keyFieldEditable: false
        }
    };

    /**
     * crudTree 的消息定义
     */
    $.fn.crudTree.messages = {
        /*提示的标题*/
        msgTitle: '系统提示',
        /*默认的根节点的名称*/
        defaultRootText: '新{0}，请保存',
        /*新增的同级节点的名称*/
        defaultNewText: '同级{0}',
        /*新增的下级节点的名称*/
        defaultNewChildText: '下级{0}',
        /*删除记录时显示的提示信息*/
        confirmOnRemove: '确定要删除“{text}”吗？',
        /*递归删除记录时显示的提示信息*/
        confirmOnRecursive: '确定要删除“{text}”及其子节点吗？',
        /*创建子节点时，父节点还未保存*/
        parentNotSaved: '请先保存“{text}”。',
        /*不允许递归删除的提示*/
        recursiveNotAllowed: '“{text}”包含子节点，不允许删除。',
        /*数据验证失败*/
        validDataFailed: '数据验证失败。',
        /*加载数据失败*/
        loadDataFailed: '加载“{text}”失败。 ',
        /*删除数据失败*/
        removeDataFailed: '删除“{text}”失败。',
        /*数据保存失败*/
        saveDataFailed: '保存“{text}”失败。',
        /*验证主键值失败,这里的{0}会被替换为输入的keyField的值*/
        existedDataFailed: '“{0}”已存在。'
    };

    /**
     * crudTree 的方法定义
     */
    $.fn.crudTree.methods = {
        options: function () {
            return _options(this);
        },
        addChild: function (child) {
            _addChild(this, child);
        },
        /*销毁 crudTree*/
        destroy: function () {
            _destroy(this);
        }
    };

})(jQuery);