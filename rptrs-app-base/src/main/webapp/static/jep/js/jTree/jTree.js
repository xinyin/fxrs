/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';

    var _EVENT_NAME_POPUP_BODY = 'mousedown.jtree.popup.menu';
    var _EVENT_NAME_FORM_SAVE = 'click.jtree.form.save';
    var _EVENT_NAME_POPUP_MENU = 'click.jtree.popup.menu';
    var _NODE_ID_NEW = '-1';
    var _NODE_ID_VIRTURE = '_ROOT_';

    var JTree = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        this.$tree = null;
        this.$treeId = null;

        this.$popup = {
            enable: false,
            menu: null,
            el: null
        };

        this.$form = {
            enable: false,
            el: null,
            nameFormatter: null
        };
        this.init();
    };

    /**
     * 计算函数名是否为一个函数
     */
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
     * 计算函数的返回值
     */
    var calculateObjectValue = function (self, name, args, defaultValue) {
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
        if (typeof func === 'object') {
            return func;
        }
        if (typeof func === 'function') {
            return func.apply(self, args);
        }
        if (!func && typeof name === 'string' && sprintf.apply(this, [name].concat(args))) {
            return sprintf.apply(this, [name].concat(args));
        }
        return defaultValue;
    };


    /**
     * 默认属性，从 BootstrapTable 的默认属性扩展而来
     */
    JTree.DEFAULTS = {
        // base url，用于查询、删除和重命名
        url: undefined,
        // 查询的url，查询时用 url/queryUrl
        queryUrl: 'treeNode',
        // 移除的url， 移除时用 url/removeurl
        removeUrl: 'removeNode',
        // 重命名url， 重命名时用 url/renameurl
        renameUrl: 'renameNode',
        // 新增节点的 Url
        addUrl: 'addNode',
        // 更新节点的 Url
        updateUrl: 'updateNode',
        // 是否可以选中，目前默认是复选框，暂不支持单选
        check: false,
        //请求时传递到后台的动态参数，默认传递 id 和 name
        autoParam: 'id,name',
        // 请求时传递到后台的静态参数
        otherParam: undefined,
        // 是否显示右键菜单
        popupMenuEnable: false,
        /*
         * 右键菜单定义，格式如下：
         * [
         *   {
         *     id: 'add',
         *     icon: 'times',
         *     text: '增加',
         *     type: 'success|info|warning|danger'
         *     handler: 'onPopupAdd'
         *   }
         * ]
         */
        popupMenu: undefined,
        /*默认按钮*/
        popupMenuDefault: 'add,addChild,remove',
        /*提醒方式，默认为 toastr */
        alertType: 'toastr',
        /*是否开启关联编辑*/
        formEnable: false,
        /*关联的编辑表单*/
        form: undefined,
        /*关联的编辑表单的保存按钮*/
        formBtn: 'btnSave',
        /*保存id的字段名*/
        formIdField: 'id',
        /*保存表单中菜单名字*/
        formNameField: 'name',
        /*保存pid的字段名*/
        formPidField: 'parentId',
        /*保存pname的字段名*/
        formPnameField: 'parentName',
        /*更新时不可编辑字段*/
        formUneditableField: [],

        /*子表对象*/
        children: undefined,

        /*编辑选项*/
        edit: false,
        editRemove: false,
        editRename: false,
        lazy: true

    };

    /**
     * zTree 的默认设置
     */
    JTree.setting = {
        check: {
            //设置自动关联勾选时是否触发 beforeCheck / onCheck 事件回调函数
            autoCheckTrigger: false,
            /*
             * 勾选 checkbox 对于父子节点的关联关系
             * Y 属性定义 checkbox 被勾选后的情况；
             * N 属性定义 checkbox 取消勾选后的情况；
             * "p" 表示操作会影响父级节点；
             * "s" 表示操作会影响子级节点。
             */
            chkboxType: {'Y': 'ps', 'N': 'ps'},
            //勾选框类型(checkbox 或 radio）
            chkStyle: 'checkbox',
            /*
             * radio 的分组范围。[setting.check.enable = true 且 setting.check.chkStyle = "radio" 时生效]
             * radioType = "level" 时，在每一级节点范围内当做一个分组。
             * radioType = "all" 时，在整棵树范围内当做一个分组。
             */
            radioType: 'level',
            //当父节点设置 nocheck = true 时，设置子节点是否自动继承 nocheck = true
            nocheckInherit: false,
            //当父节点设置 chkDisabled = true 时，设置子节点是否自动继承 chkDisabled = true
            chkDisabledInherit: false
        },
        edit: {
            // 设置 zTree 是否处于编辑状态
            enable: false,
            //节点编辑名称 input 初次显示时,设置 txt 内容是否为全选状态
            editNameSelectAll: true,
            //删除按钮的 Title 辅助信息
            removeTitle: '移除',
            //编辑名称按钮的 Title 辅助信息
            renameTitle: '重命名',
            /*
             * 设置是否显示删除按钮
             * 当点击某节点的删除按钮时：
             * 1、首先触发 setting.callback.beforeRemove 回调函数，用户可判定是否进行删除操作。
             * 2、如果未设置 beforeRemove 或 beforeRemove 返回 true，则删除节点并触发 setting.callback.onRemove 回调函数。
             */
            showRemoveBtn: true,
            /*
             * 设置是否显示编辑名称按钮
             * 当点击某节点的编辑名称按钮时：
             * 1、进入节点编辑名称状态。
             * 2、编辑名称完毕（Input 失去焦点 或 按下 Enter 键），会触发 setting.callback.beforeRename 回调函数，用户可根据自己的规则判定是否允许修改名称。
             * 3、如果 beforeRename 返回 false，则继续保持编辑名称状态，直到名称符合规则位置 （按下 ESC 键可取消编辑名称状态，恢复原名称）。
             * 4、如果未设置 beforeRename 或 beforeRename 返回 true，则结束节点编辑名称状态，更新节点名称，并触发 setting.callback.onRename 回调函数。
             */
            showRenameBtn: true,
        },
        data: {
            keep: {
                /* zTree 的节点叶子节点属性锁，是否始终保持 isParent = false
                 * 如果设置为 true，则所有 isParent = false 的节点，都无法添加子节点*/
                leaf: false,
                //zTree 的节点父节点属性锁，是否始终保持 isParent = true
                //如果设置为 true，则所有 isParent = true 的节点，即使该节点的子节点被全部删除或移走，依旧保持父节点状态
                parent: false
            },
            key: {
                //zTree 节点数据中保存子节点数据的属性名称
                children: 'children',
                //zTree 节点数据中保存 check 状态的属性名称
                checked: 'checked',
                //zTree 节点数据保存节点名称的属性名称
                name: 'name',
                /*zTree 节点数据保存节点提示信息的属性名称。[setting.view.showTitle = true 时生效]
                如果设置为 "" ，则自动与 setting.data.key.name 保持一致，避免用户反复设置*/
                title: 'title',
                //zTree 节点数据保存节点链接的目标 URL 的属性名称
                //特殊用途：当后台数据只能生成 url 属性，又不想实现点击节点跳转的功能时，可以直接修改此属性为其他不存在的属性名称
                url: 'url'
            },
            simpleData: {
                /*
                 * 确定 zTree 初始化时的节点数据、异步加载时的节点数据、或 addNodes 方法中输入的 newNodes 数据是否采用简单数据模式 (Array)
                 */
                enable: true,
                //节点数据中保存唯一标识的属性名称
                idKey: 'id',
                //节点数据中保存其父节点唯一标识的属性名称
                pIdKey: 'pid',
                //用于修正根节点父节点数据，即 pIdKey 指定的属性
                rootId: null
            }
        },
        view: {
            //点击节点时，按下 Ctrl 或 Cmd 键是否允许取消选择操作
            autoCancelSelected: false,
            //双击节点时，是否自动展开父节点的标识
            dblClickExpand: true,
            //zTree 节点展开、折叠时的动画速度，设置方法同 JQuery 动画效果中 speed 参数, "slow", "normal", or "fast"
            expandSpeed: 'fast',
            //设置 name 属性是否支持 HTML 脚本
            nameIsHTML: false,
            //设置是否允许同时选中多个节点
            selectedMulti: true,
            //设置 zTree 是否显示节点的图标
            showIcon: true,
            //设置 zTree 是否显示节点之间的连线
            showLine: true,
            //设置 zTree 是否显示节点的 title 提示信息(即节点 DOM 的 title 属性)
            showTitle: true,
            //设置 zTree 是否允许可以选择 zTree DOM 内的文本
            txtSelectedEnable: false
        },
        async: {
            //设置 zTree 是否开启异步加载模式
            enable: false,
            //Ajax 的 http 请求模式
            type: 'post',
            //Ajax 获取的数据类型
            dataType: 'text',
            /*
             * json 格式请求数据，controller 层需使用 @RequestBody 来接收参数
             * Ajax 提交参数的数据类型。[setting.async.enable = true 时生效]
             * 默认值："application/x-www-form-urlencoded"
             */
            contentType: 'application/x-www-form-urlencoded'
        }
    };

    /**
     * 初始化
     */
    JTree.prototype.init = function () {
        this.initZTree();

    };


    /**
     * 初始化相关联的查询表单
     */
    JTree.prototype.initZTree = function () {
        var $opt = this.options, $this = this;
        this.$treeId = '_jTree_' + this.$el.attr('id');

        /*数据节点*/
        var $zNodes = undefined;

        /*默认按钮定义*/
        var $defaultPopupMenu = {
            'add': {
                id: 'add',
                icon: 'plus-square',
                text: '增加同级',
                target: 'node',
                handler: function (event, treeId, treeNode) {
                    var $nodes = $this.addNodes(treeNode.getParentNode(), {id: _NODE_ID_NEW, name: '新节点'}, false);
                    $this.selectNode($nodes[0], false, false);

                    /*如果有虚拟根节点，移除*/
                    if (treeNode.id === _NODE_ID_VIRTURE) {
                        $this.removeNode(treeNode, false);
                    }

                    if ($this.$form.enable) {
                        $this.loadFormData($nodes[0]);
                    } else {
                        $this.editName($nodes[0]);
                    }
                }
            },
            'addChild': {
                id: 'addChild',
                icon: 'plus-square-o',
                text: '增加下级',
                target: 'node',
                handler: function (event, treeId, treeNode) {
                    $this.expandNode(treeNode, true, false, true, true);
                    setTimeout(function () {
                        var $nodes = $this.addNodes(treeNode, {id: _NODE_ID_NEW, name: '新节点'}, false);
                        $this.selectNode($nodes[0], false, false);
                        if ($this.$form.enable) {
                            $this.loadFormData($nodes[0]);
                        } else {
                            $this.editName($nodes[0]);
                        }

                        treeNode.isParent = true;
                        $this.updateNode(treeNode, true);
                    }, 200);
                }
            },
            'remove': {
                id: 'remove',
                icon: 'window-close',
                text: '移除',
                target: 'node',
                handler: function (event, treeId, treeNode) {
                    if (!treeNode) return;
                    $this.removeNode(treeNode, true);
                    if ($this.$form.enable) {
                        $this.clearFormData();
                        $this.checkFormField();
                    }
                }
            },
            'expand': {
                id: 'expand',
                icon: 'expand',
                text: '全部展开',
                handler: function (event, treeId, treeNode) {
                    $this.expandAll(true);
                }
            },
            'collapse': {
                id: 'collapse',
                icon: 'compress',
                text: '全部收起',
                handler: function (event, treeId, treeNode) {
                    $this.expandAll(false);
                }
            }
        };

        /*
         * 右键菜单的处理
         * 定义了 data-popup-menu, 将自定义菜单加入到默认菜单的后面
         * 如果要取消默认菜单，需设置 data-popup-menu-default 属性
         * 默认菜单有5个，分别为 'add,addChild,remove,expand,collapse'
         */
        if ($opt.popupMenuEnable) {
            this.$popup.enable = true;

            // 定义了 data-popup-menu
            if ($opt.popupMenu) {

                if (!$.isArray($opt.popupMenu)) this.$popup.menu = undefined;
                else this.$popup.menu = $opt.popupMenu;
            }

            if (!this.$popup.menu) this.$popup.menu = [];

            /*增加默认按钮*/
            if ($opt.popupMenuDefault && $opt.popupMenuDefault !== 'none') {
                var $popupMenuDefault = $opt.popupMenuDefault;
                $popupMenuDefault = $popupMenuDefault.split(',');
                for (var i = $popupMenuDefault.length - 1; i >= 0; i--) {
                    if ($defaultPopupMenu[$popupMenuDefault[i]]) {
                        this.$popup.menu.unshift($defaultPopupMenu[$popupMenuDefault[i]]);
                    }
                }
            }
            if (this.$popup.menu.length == 0) {
                this.$popup.enable = false;
                this.$popup.menu = undefined;
            }
        }

        /*设置是否选中*/
        var $setting = $.extend(true, {}, JTree.setting, {
            check: {
                enable: $opt.check ? $opt.check : false
            },
            edit: {
                enable: $opt.edit ? true : false,
                showRenameBtn: function (treeId, treeNode) {
                    if (treeNode.id === _NODE_ID_VIRTURE) {
                        return false;
                    }
                    if (typeof $opt.editRename === 'string') {
                        var func = calculateFunc($opt.editRename);
                        if (func) return func.apply($this, arguments);
                    } else {
                        return $opt.editRename;
                    }
                },
                showRemoveBtn: function (treeId, treeNode) {
                    if (treeNode.id === _NODE_ID_VIRTURE) {
                        return false;
                    }
                    if (typeof $opt.editRemove === 'string') {
                        var func = calculateFunc($opt.editRemove);
                        if (func) return func.apply($this, arguments);
                    } else {
                        return $opt.editRemove;
                    }
                }
            }
        });

        /*加载远程数据*/
        if ($opt.url) {
            /*判断opt.lazy如果是true 用下面，  如果是false， 用同步的ajax取回数据（数据解析下再用），调用calculateObjectValue*/
            if ($opt.lazy) {
                $.extend(true, $setting, {
                    async: {
                        enable: true,
                        url: $.helper.curl($opt.url, $opt.queryUrl),
                        type: $opt.method ? $opt.method : 'post',
                        /*
                         * 异步加载时需要自动提交父节点属性的参数
                         * 1、将需要作为参数提交的属性名称，制作成 Array 即可，例如：["id", "name"]
                         * 2、可以设置提交时的参数名称，例如 server 只接受 zId : ["id=zId"]
                         */
                        autoParam: $opt.autoParam ? $opt.autoParam.split(',') : [],
                        //Ajax 请求提交的静态参数键值对
                        otherParam: jQuery.isArray($opt.otherParam) == true ? $opt.otherParam : $opt.otherParam ? $opt.otherParam.split(',') : []
                    }
                });
            } else {
                /*如果设置的是eager加载，则默认otherParam为JSON对象数据*/
                /*直接去后台通过ajax请求数据*/
                $.ajax({
                    async: false,//设置为同步请求
                    type: $opt.method ? $opt.method : 'post',
                    url: $.helper.curl($opt.url, $opt.queryUrl),
                    data: $opt.otherParam,
                    success: function (data) {
                        /*如果有数据，则加载数据*/
                        if (data.length > 0) {
                            $zNodes = calculateObjectValue(this, data);
                        }
                        /*如果没有数据，则加载虚拟节点*/
                        else {
                            $zNodes = {id: _NODE_ID_VIRTURE, name: '虚拟根节点'};
                        }
                    }
                });
            }

        }
        /*加载本地数据，简单数据*/
        else if ($opt.nodeData) {
            $zNodes = calculateObjectValue(this, $opt.nodeData)
        }

        /*关联表单处理*/
        if ($opt.edit && $opt.formEnable && $opt.formId && $('#' + $opt.formId).length == 1) {
            this.$form.enable = true;
            this.$form.el = $('#' + $opt.formId);
            /*解析节点名称格式化函数*/
            if (typeof $opt.formNameFormatter === 'string') {
                this.$form.nameFormatter = calculateFunc($opt.formNameFormatter);
            }

            /*处理不可编辑的表单*/
            if (typeof $opt.formUneditableField === 'string') {
                $opt.formUneditableField = $opt.formUneditableField.split(',');
            } else if (!$.isArray($opt.formUneditableField)) {
                $opt.formUneditableField = [];
            }

            /*增加保存按钮事件*/
            var $formBtn = $opt.formBtn || 'btnSave';
            $('#' + $formBtn).off(_EVENT_NAME_FORM_SAVE).on(_EVENT_NAME_FORM_SAVE, function (e) {
                var $form = $this.$form.el;
                if ($form.data('formValidation')) {
                    $form.data('formValidation').validate();
                    if (!$form.data('formValidation').isValid()) return;
                }
                var formData = $this.$form.el.serializeAll();
                $.ajax({
                    url: $.helper.curl($opt.url, $opt.updateUrl),
                    type: 'post',
                    data: formData,
                    success: function (data) {
                        if (data.success) {
                            $this.alert('提示', data.msg, 'info', function () {
                                var $node = null;
                                /*保存时如果只有虚拟根节点，移除虚拟根节点*/
                                var treeNode = $this.getNodes();
                                if (treeNode[0].id === _NODE_ID_VIRTURE) {
                                    $this.removeNode(treeNode[0], false);
                                }

                                treeNode = $this.getSelectedNodes();
                                /*根据字段映射，将表单字段更新回树节点属性*/
                                if (treeNode[0]) {
                                    $node = treeNode[0];
                                } else {
                                    $node = $this.addNodes(undefined, {id: _NODE_ID_NEW, name: '新节点'}, false)[0];
                                }

                                $.each(formData, function () {
                                    $node[this.name] = this.value;
                                });
                                /*将数组转化为对象*/
                                var $formData = {};
                                for (var p in formData) {
                                    $formData[formData[p].name] = formData[p].value;
                                }

                                if (typeof $this.$form.nameFormatter === 'function') {
                                    $node.name = $this.$form.nameFormatter.apply($this, [$formData, data]);
                                } else if ($opt.formNameField && $formData[$opt.formNameField]) {
                                    $node.name = $formData[$opt.formNameField];
                                }
                                /*更新nodeid*/
                                $node.id = data.id;
                                $this.updateNode($node, true, false, true, true);
                                /*检查字段状态*/
                                this.checkFormField($node);
                                $this.selectNode($node, false, false);

                            });
                        }
                    },
                    error: function (xhr, status, error) {
                        $this.alert('提示', '系统错误', 'error');
                    }
                });

            });

        }

        /*
         * 设置回调函数的扩展
         */
        $.extend(true, $setting, {
            callback: {

                /*
                 * 用于捕获单击节点之前的事件回调函数，并且根据返回值确定是否允许单击操作
                 */
                beforeClick: function (treeId, treeNode, clickFlag) {
                    /*
                     * 调用用户设置的单击
                     */
                    if (typeof $opt.beforeClick === 'string') {
                        var func = calculateFunc($opt.beforeClick);
                        if (func) func.apply($this, arguments);
                    }
                },
                /*
                 * 用于捕获父节点折叠之前的事件回调函数，并且根据返回值确定是否允许折叠操作
                 */
                beforeCollapse: function (treeId, treeNode) {
                    /*
                     * 调用用户设置的折叠
                     */
                    if (typeof $opt.beforeCollapse === 'string') {
                        var func = calculateFunc($opt.beforeCollapse);
                        if (func) func.apply($this, arguments);
                    }
                },
                /*
                 * 处理拖拽前的控制，如果节点的 drag 属性为 false，则不允许拖拽
                 */
                beforeDrag: function (treeId, treeNodes) {
                    for (var i = 0, l = treeNodes.length; i < l; i++) {
                        if (treeNodes[i].drag === false) {
                            return false;
                        }
                    }
                    if (typeof $opt.beforeDrag === 'string') {
                        var func = calculateFunc($opt.beforeDrag);
                        if (func) return func.apply($this, arguments);
                    }
                    return true;
                },
                /*
                 * 处理拖放前的控制，如果节点的drop为true，则允许拖放
                 */
                beforeDrop: function (treeId, treeNodes, targetNode, moveType, isCopy) {
                    if (targetNode && targetNode.drop === false) {
                        return false;
                    }
                    if (typeof $opt.beforeDrop === 'string') {
                        var func = calculateFunc($opt.beforeDrop);
                        if (func) return func.apply($this, arguments);
                    }
                    return true;
                },
                /*
                 * 删除节点前，先删除服务器数据，服务器删除失败则不允许前端删除。
                 */
                beforeRemove: function (treeId, treeNode) {
                    /*虚拟根节点不允许删除*/
                    if (treeNode.id === _NODE_ID_VIRTURE) {
                        alert('虚拟节点无法删除');
                        return false;
                    }
                    if (typeof $opt.beforeRemove === 'string') {
                        var func = calculateFunc($opt.beforeRemove);
                        if (func) return func.apply($this, arguments);
                    } else {
                        var $succ = false;
                        /*远程删除数据*/
                        if ($opt.url) {
                            $.ajax({
                                url: $.helper.curl($opt.url, $opt.removeUrl, treeNode.id),
                                type: 'post',
                                async: false,
                                success: function (data) {
                                    $succ = data.success;
                                    if (!data.success) {
                                        $this.alert('提示', data.msg, 'info', function () {
                                            $this.expandNode(treeNode, true, false, true, true);
                                        });
                                    }
                                },
                                error: function (xhr, status, error) {
                                    $this.alert('提示', '系统错误', 'error');
                                }
                            });
                        }
                        return $succ;
                    }
                },
                /*
                 * 重命名节点前的处理，发送请求到服务端进行重命名，成功则重命名
                 */
                beforeRename: function (treeId, treeNode, newName, isCancel) {
                    /*虚拟根节点不允许删除*/
                    if (treeNode.id === _NODE_ID_VIRTURE) {
                        return false;
                    }
                    if (isCancel) return true;
                    var $succ = false;

                    if (typeof $opt.beforeRename === 'string') {
                        var func = calculateFunc($opt.beforeRename);
                        if (func) $succ = func.apply($this, arguments);
                    } else {
                        /*远程重命名节点*/
                        if ($opt.url) {
                            /*判断id == _NODE_ID_NEW，为新增，否则为重命名*/
                            if (treeNode.id == _NODE_ID_NEW) {
                                $.ajax({
                                    url: $.helper.curl($opt.url, $opt.addUrl),
                                    data: {'parentId': treeNode.pid, 'name': newName},
                                    type: 'post',
                                    async: false,
                                    success: function (data) {
                                        $succ = data.success;
                                        if (!data.success) {
                                            $this.alert('提示', data.msg, 'info');
                                        } else {
                                            treeNode.id = data.id;
                                            $this.updateNode(treeNode, true);
                                        }
                                    },
                                    error: function (xhr, status, error) {
                                        $succ = false;
                                        $this.alert('提示', '系统错误', 'error');
                                    }
                                });
                            } else {
                                $.ajax({
                                    url: $.helper.curl($opt.url, $opt.renameUrl, treeNode.id),
                                    data: {'newName': newName},
                                    type: 'post',
                                    async: false,
                                    success: function (data) {
                                        $succ = data.success;
                                        if (!data.success) {
                                            $this.alert('提示', data.msg, 'info');
                                        }
                                    },
                                    error: function (xhr, status, error) {
                                        $succ = false;
                                        $this.alert('提示', '系统错误', 'error');
                                    }
                                });
                            }
                        }
                    }

                    if (!$succ) {
                        $this.cancelEditName(treeNode.name);
                    }
                    return $succ;
                },
                /*
                 * 设置 onExpand 函数，远程加载数据后，如果没有子节点，设置当前节点的 isParent 为 false
                 */
                onExpand: function (event, treeId, treeNode) {
                    if (!treeNode.children || treeNode.children.length == 0) {
                        treeNode.isParent = false;
                        $this.updateNode(treeNode, true);
                    }

                    /*
                     * 调用用户设置的展开函数
                     */
                    if (typeof $opt.onExpand === 'string') {
                        var func = calculateFunc($opt.onExpand);
                        if (func) func.apply($this, arguments);
                    }
                },
                /*
                 * 调用用户定义的 onRemove 事件
                 */
                onRemove: function (event, treeId, treeNode) {
                    if (typeof $opt.onRemove === 'string') {
                        var func = calculateFunc($opt.onRemove);
                        if (func) func.apply($this, arguments);
                    }

                    var nodes = $this.getNodes();
                    /*删除节点时，如果删除的是最后一个节点，增加虚拟节点*/
                    if (!nodes || nodes.length == 0) {
                        $this.addNodes(undefined, {id: _NODE_ID_VIRTURE, name: '虚拟根节点'}, false);
                    }
                },
                /*
                 * 调用用户定义的 onRemove 事件
                 */
                onRename: function (event, treeId, treeNode) {
                    /*更新关联表单*/
                    if ($this.$form.enable) {
                        $this.loadFormData(treeNode);
                    }
                    if (typeof $opt.onRename === 'string') {
                        var func = calculateFunc($opt.onRename);
                        if (func) func.apply($this, arguments);
                    }
                },
                /*
                 * 调用用户定义的 onRemove 事件
                 */
                onClick: function (event, treeId, treeNode, clickFlag) {
                    /*更新关联表单*/
                    if ($this.$form.enable) {
                        $this.loadFormData(treeNode);
                    }

                    /*加载子表数据*/
                    if ($.isArray($opt.children)) {
                        $.each($opt.children, function () {
                            var queryParams = {};
                            queryParams['__' + this.operator + this.idFieldType + '_' + this.idField] = treeNode[this.field];
                            $('#' + this.id).jTable('setExtQueryParams', queryParams);
                            $('#' + this.id).jTable('refresh');
                        });
                    }

                    if (typeof $opt.onClick === 'string') {
                        var func = calculateFunc($opt.onClick);
                        if (func) func.apply($this, arguments);
                    }
                },
                /*
                 * 右键单击，处理弹出菜单
                 */
                onRightClick: function (event, treeId, treeNode) {
                    /*
                     * 定义了右键菜单，设置右键菜单
                     */
                    if ($opt.edit && $this.$popup.enable) {
                        $this.$popup.el.hide();
                        $.each($this.$popup.menu, function () {
                            var $menu = $('a[menuid=' + this.id + ']', $this.$popup.el);
                            if (!treeNode && this.target === 'node') {
                                $menu.addClass('disabled');
                                $menu.off(_EVENT_NAME_POPUP_MENU);
                            } else {
                                if (treeNode) {
                                    if (treeNode.id === _NODE_ID_VIRTURE) {
                                        if ((this.id === 'add' || this.target !== 'node')) {
                                            var $handler = typeof this.handler === 'function' ? this.handler : calculateFunc(this.handler);
                                            $menu.removeClass('disabled');
                                            $menu.off(_EVENT_NAME_POPUP_MENU).on(_EVENT_NAME_POPUP_MENU, function (e) {
                                                if ($handler) $handler.apply($this, [e, $this.$treeId, treeNode]);
                                                $this.hidePopupMenu();
                                            });
                                        } else {
                                            $menu.addClass('disabled');
                                            $menu.off(_EVENT_NAME_POPUP_MENU);
                                        }
                                    } else {
                                        var $handler = typeof this.handler === 'function' ? this.handler : calculateFunc(this.handler);
                                        $menu.removeClass('disabled');
                                        $menu.off(_EVENT_NAME_POPUP_MENU).on(_EVENT_NAME_POPUP_MENU, function (e) {
                                            if ($handler) $handler.apply($this, [e, $this.$treeId, treeNode]);
                                            $this.hidePopupMenu();
                                        });
                                    }
                                } else {
                                    var $handler = typeof this.handler === 'function' ? this.handler : calculateFunc(this.handler);
                                    $menu.removeClass('disabled');
                                    $menu.off(_EVENT_NAME_POPUP_MENU).on(_EVENT_NAME_POPUP_MENU, function (e) {
                                        if ($handler) $handler.apply($this, [e, $this.$treeId, treeNode]);
                                        $this.hidePopupMenu();
                                    });
                                }
                            }
                        });
                        $this.$popup.el.show();
                        $this.$popup.el.css({
                            position: "absolute",
                            width: "150px",
                            top: (event.clientY - $this.$el.offset().top + 20) + "px",
                            left: (event.clientX - $this.$el.offset().left + 20) + "px",
                            visibility: "visible"
                        });

                        /*
                         * 增加文档的鼠标单击事件，关闭弹出菜单
                         */
                        $(document.body).off(_EVENT_NAME_POPUP_BODY).on(_EVENT_NAME_POPUP_BODY, function (e) {
                            if (!$(e.target).hasClass('popupMenuItem')) {
                                $this.hidePopupMenu();
                            }

                        });
                    }
                },
                /*
                 * 异步加载后的事件
                 */
                onAsyncSuccess: function (event, treeId, treeNode, data) {
                    /*代表加载的是根节点的数据*/
                    if (treeNode === undefined) {
                        var nodes = $this.getNodes();
                        /*加载根数据时，没有加载到数据，说明当前没有数据*/
                        if (!nodes || nodes.length == 0) {
                            /*当前处在编辑模式下，增加虚拟根节点*/
                            if ($opt.edit) {
                                var $nodes = $this.addNodes(treeNode, {id: _NODE_ID_VIRTURE, name: '虚拟根节点'}, false);
                            }

                        }
                    }
                },
                onMouseDown: function (event, treeId, treeNode) {
                    if (typeof $opt.onMouseDown === 'string') {
                        var func = calculateFunc($opt.onMouseDown);
                        if (func) func.apply($this, arguments);
                    }
                }

            }
        });

        /*增加zTree的dom节点*/
        this.$el.addClass('jtree');
        var $root = $('<ul id="' + this.$treeId + '" class="ztree"></ul>').appendTo(this.$el);
        /*初始化zTree*/
        $.fn.zTree.init($('#' + this.$treeId), $setting, $zNodes);
        /*保存 zTreeObj*/
        this.$tree = $.fn.zTree.getZTreeObj(this.$treeId);

        /*
         * 初始化弹出菜单
         */
        if (this.$popup.enable) {
            this.$popup.el = $('<div class="popupMenu"></div>').appendTo(this.$el);
            $.each(this.$popup.menu, function () {
                var $html = ['<a href="#" '];
                $html.push(' menuid="');
                $html.push(this.id);
                $html.push('" class="popupMenuItem list-group-item ');
                if (this.type && /success|info|warning|danger/g.test(this.type)) {
                    $html.push('list-group-item-');
                    $html.push(this.type);
                }
                $html.push('">');
                if (this.icon) {
                    $html.push('<li class="fa fa-');
                    $html.push(this.icon);
                    $html.push('"></li> ');
                }
                $html.push(this.text);
                $html.push('</a>');
                $($html.join('')).appendTo($this.$popup.el);
            });
        }

    };

    /**
     * 重新加载数据
     */
    JTree.prototype.reload = function (options) {
        $.extend(this.options, options);
        this.init();
    }

    /**
     * 隐藏右键菜单
     */
    JTree.prototype.alert = function (title, msg, type, callback) {
        if (typeof type === 'function') {
            callback = type;
            type = 'info';
        }
        var $type = (type && /info|warning|error/g.test(type)) ? type : 'info';
        if (this.options.alertType === 'toastr' && top.toastr) {
            $.messager.toastr(title, msg, $type);
            if (typeof callback === 'function') callback.apply(this);
        } else {
            $.messager.alert(title, msg, $type, callback);
        }
    }

    /**
     * 隐藏右键菜单
     */
    JTree.prototype.hidePopupMenu = function () {
        if (this.$popup.enable) {
            this.$popup.el.hide();
            $(document.body).off(_EVENT_NAME_POPUP_BODY);
        }
    }

    /**
     * 返回zTree的处理对象
     */
    JTree.prototype.getTree = function () {
        return this.$tree;
    }

    /**
     * 加载表单数据
     */
    JTree.prototype.loadFormData = function (data) {
        var $opt = this.options;
        this.clearFormData();
        if (typeof $opt.onDefaultData === 'string') {
            var func = calculateFunc($opt.onDefaultData);
            if (func)
                func.apply(this, [data]);
        } else if (typeof $opt.onDefaultData === 'function') {
            $opt.onDefaultData.apply(this, [data]);
        }

        var parent = data.getParentNode();
        if (parent) {
            data[$opt.formPidField] = parent.id;
            data[$opt.formPnameField] = parent.name;
        }
        var $form = this.$form.el;
        for (var name in data) {
            var val = data[name];
            var rr = this.loadCheckData(name, val);
            if (!rr.length) {
                $('input[name="' + name + '"]', this.$form.el).val(val);
                $('textarea[name="' + name + '"]', this.$form.el).val(val);
                $('select[name="' + name + '"]', this.$form.el).val(val).trigger('change');
            }
            if ($form.data('formValidation')) {
                $form.data('formValidation').revalidateField(name);
            }
        }
        this.checkFormField(data);
    }

    JTree.prototype.checkFormField = function (data) {
        var $this = this, $opt = this.options;
        var $disabled = data && !(data.id == _NODE_ID_NEW || data.id == _NODE_ID_VIRTURE);

        if ($opt.formUneditableField) {
            $.each($opt.formUneditableField, function () {

                if ($disabled) {
                    $('input[name="' + this + '"]', $this.$form.el).attr('disabled', 'disabled');
                    $('textarea[name="' + this + '"]', $this.$form.el).attr('disabled', 'disabled');
                    $('select[name="' + this + '"]', $this.$form.el).attr('disabled', 'disabled');
                } else {
                    $('input[name="' + this + '"]', $this.$form.el).removeAttr('disabled');
                    $('textarea[name="' + this + '"]', $this.$form.el).removeAttr('disabled');
                    $('select[name="' + this + '"]', $this.$form.el).removeAttr('disabled');
                }
            });
        }
    }

    /**
     * 清空表单数据
     */
    JTree.prototype.clearFormData = function () {
        var rr = $('input[type=radio], input[type=checkbox]', this.$form.el);
        $.fn.prop ? rr.prop("checked", false) : rr.attr("checked", false);
        $('input[type!=radio][type!=checkbox]', this.$form.el).val('');
        $('textarea', this.$form.el).val('');
        $('select', this.$form.el).val('').trigger('change');
    }


    /**
     * 加载check/radio数据
     */
    JTree.prototype.loadCheckData = function (name, val) {
        var rr = $('input[name="' + name + '"][type=radio], input[name="' + name + '"][type=checkbox]', this.$form.el);
        $.fn.prop ? rr.prop("checked", false) : rr.attr("checked", false);
        rr.each(function () {
            var f = $(this);
            if (f.val() == String(val)) {
                $.fn.prop ? f.prop("checked", true) : f.attr("checked", true);
            }
        });
        return rr;
    }

    /**
     * 获取 zTree 的全部节点数据
     * 请通过 zTree 对象执行此方法。
     *
     * 返回值Array(JSON)
     * 全部节点数据
     * 1、Array 仅仅是根节点的集合（默认情况子节点都处于 children 属性下）；
     * 2、如需遍历全部节点需要利用递归，或利用 transformToArray 方法 将数据变成简单的 Array 集合
     * 3、对于异步加载模式下，尚未加载的子节点是无法通过此方法获取的。
     */
    JTree.prototype.getNodes = function () {
        return this.getTree().getNodes();
    }

    /**
     * 选中指定节点
     *
     * treeNode        JSON
     * 需要被选中的节点数据
     * 请务必保证此节点数据对象 是 zTree 内部的数据对象
     *
     * addFlag        Boolean
     * addFlag = true 表示追加选中，会出现多点同时被选中的情况
     * addFlag = false （默认）表示单独选中，原先被选中的节点会被取消选中状态
     * setting.view.selectedMulti = false 时，此参数无效，始终进行单独选中
     *
     * isSilent        Boolean
     * isSilent = true 选中节点时，不会让节点自动滚到到可视区域内
     * isSilent = false （默认）表示选中节点时，会让节点自动滚到到可视区域内
     * (v3.5.23+)
     *
     * 返回值无
     * 目前无任何返回值
     */
    JTree.prototype.selectNode = function (treeNode, addFlag, isSilent) {
        return this.getTree().selectNode(treeNode, addFlag, isSilent);
    }

    /**
     * 获取 zTree 当前被选中的节点数据集合
     * 请通过 zTree 对象执行此方法。
     *
     * 返回值Array(JSON)
     * 当前被选中的节点数据集合
     */
    JTree.prototype.getSelectedNodes = function () {
        return this.getTree().getSelectedNodes();
    }

    /**
     * 设置某节点进入编辑名称状态。
     * 1、如果需要用 js 取消编辑名称状态，请使用 cancelEditName(newName) 方法。
     * 2、可利用此方法让当前正编辑的节点 input 输入框获取焦点。
     * 3、请通过 zTree 对象执行此方法。
     *
     * treeNode        JSON
     * 指定进入编辑名称状态的节点 JSON 数据
     * 请务必保证此节点数据对象 是 zTree 内部的数据对象
     */
    JTree.prototype.editName = function (treeNode) {
        return this.getTree().editName(treeNode);
    }

    /**
     * 添加节点。
     * parentNode        JSON
     * 指定的父节点，如果增加根节点，请设置 parentNode 为 null 即可。
     * 请务必保证此节点数据对象 是 zTree 内部的数据对象
     *
     * [index]            Number
     * 新节点插入的位置（从 0 开始）
     * index = -1 时，插入到最后
     * 此参数可忽略
     * v3.5.19+
     *
     * newNodes            JSON / Array(JSON)
     * 需要增加的节点数据 JSON 对象集合，数据只需要满足 zTree 的节点数据必需的属性即可，详细请参考“treeNode 节点数据详解”
     * 1、v3.x 支持单独添加一个节点，即如果只新增一个节点，不用必须包在数组中
     * 2、使用简单数据模式，请参考 setting.data.simpleData 内的属性说明
     *
     * isSilent            Boolean
     * 设定增加节点后是否自动展开父节点。
     * isSilent = true 时，不展开父节点，其他值或缺省状态都自动展开。
     *
     * 返回值Array(JSON)
     * 返回值是 zTree 最终添加的节点数据集合
     * 如果 newNodes 是单个节点数据 JSON，返回值也是将其包在 Array 内
     * 请务必记住：返回值中的数据对象 是 newNodes 被 clone 后的，所以绝对不相等！
     */
    JTree.prototype.addNodes = function (parentNode, index, newNodes, isSilent) {
        return this.getTree().addNodes(parentNode, index, newNodes, isSilent);
    }

    /**
     * 删除节点。
     * treeNode        JSON
     * 需要被删除的节点数据
     * 请务必保证此节点数据对象 是 zTree 内部的数据对象
     *
     * callbackFlag        Boolean
     * callbackFlag = true 表示执行此方法时触发 beforeRemove & onRemove 事件回调函数
     * callbackFlag = false 表示执行此方法时不触发事件回调函数
     * 省略此参数，等同于 false
     */
    JTree.prototype.removeNode = function (treeNode, callbackFlag) {
        var $this = this;
        if (treeNode.id === _NODE_ID_VIRTURE) {
            $this.getTree().removeNode(treeNode, callbackFlag);
        } else {
            if ($.messager.confirm('提示', '确定要删除节点[' + treeNode.name + ']吗?', function (r) {
                $this.getTree().removeNode(treeNode, callbackFlag);
            })) ;
        }

    }

    /**
     * 展开 / 折叠 全部节点
     * 此方法不会触发 beforeExpand / onExpand 和 beforeCollapse / onCollapse 事件回调函数。
     * 请通过 zTree 对象执行此方法。
     *
     * expandFlag    Boolean
     * expandFlag = true 表示 展开 全部节点
     * expandFlag = false 表示 折叠 全部节点
     * 返回值Boolean
     * 返回值表示最终实际操作情况
     * true 表示 展开 全部节点
     * false 表示 折叠 全部节点
     * null 表示 不存在任何父节点
     */
    JTree.prototype.expandAll = function (expandFlag) {
        return this.getTree().expandAll(expandFlag);
    }

    /**
     * 更新某节点数据，主要用于该节点显示属性的更新。
     * 1、可针对 name、target、 url、icon、 iconSkin、checked、nocheck 等这几个用于显示效果的参数进行更新，其他用于 zTreeNodes 的参数请不要随意更新，对于展开节点，还请调用 expandNode方法，因此请勿随意修改 open 属性。
     * 2、用此方法修改 checked 勾选状态不会触发 beforeCheck / onCheck 事件回调函数。
     * 请通过 zTree 对象执行此方法。
     *
     * treeNode        JSON
     * 指定需要更新的节点 JSON 数据
     * 请务必保证此节点数据对象 是 zTree 内部的数据对象
     *
     * checkTypeFlag        Boolean
     * checkTypeFlag = true 表示按照 setting.check.chkboxType 属性进行父子节点的勾选联动操作
     * checkTypeFlag = false 表示只修改此节点勾选状态，无任何勾选联动操作
     * 当 setting.check.enable = true 且 setting.check.chkStyle = "checkbox" 时才有效
     * 不影响父子节点中 treeNode.nochecked = true 的节点。
     */
    JTree.prototype.updateNode = function (treeNode, checkTypeFlag) {
        return this.getTree().updateNode(treeNode, checkTypeFlag);
    }

    /**
     * 取消节点的编辑名称状态，可以恢复原名称，也可以强行赋给新的名称。
     * 请通过 zTree 对象执行此方法。
     *
     * newNameString
     * 重新给定的新名称。
     * 如果省略此参数，则恢复原名称。
     */
    JTree.prototype.cancelEditName = function (newName) {
        return this.getTree().cancelEditName(newName);
    }

    /**
     * treeNode                JSON
     * 需要 展开 / 折叠 的节点数据
     * 请务必保证此节点数据对象 是 zTree 内部的数据对象
     *
     * expandFlag            Boolean
     * expandFlag = true 表示 展开 节点
     * expandFlag = false 表示 折叠 节点
     * 省略此参数，则根据对此节点的展开状态进行 toggle 切换
     *
     * sonSign                Boolean
     * sonSign = true 表示 全部子孙节点 进行与 expandFlag 相同的操作
     * sonSign = false 表示 只影响此节点，对于其 子孙节点无任何影响
     * sonSign = false 且 treeNode.open = expandFlag 时，不会触发回调函数，直接返回
     * 省略此参数，等同于 false
     *
     * focus                Boolean
     * focus = true 表示 展开 / 折叠 操作后，通过设置焦点保证此焦点进入可视区域内
     * focus = false 表示 展开 / 折叠 操作后，不设置任何焦点
     * 省略此参数，等同于 true
     *
     * callbackFlag            Boolean
     * callbackFlag = true 表示执行此方法时触发 beforeExpand / onExpand 或 beforeCollapse / onCollapse 事件回调函数
     * callbackFlag = false 表示执行此方法时不触发事件回调函数
     * 省略此参数，等同于 false
     *
     * 返回值                Boolean
     * 返回值表示最终实际操作情况
     * true 表示 展开 节点
     * false 表示 折叠 节点
     * null 表示 不是父节点
     */
    JTree.prototype.expandNode = function (treeNode, expandFlag, sonSign, focus, callbackFlag) {
        return this.getTree().expandNode(treeNode, expandFlag, sonSign, focus, callbackFlag);
    }


    /**
     * 增加子对象，目前只支持 jTable
     * @parma child 格式: {id : 'schoolTable', idField: 'code', idFieldType: 'S', field: 'code', operator: 'EQ'}
     */
    JTree.prototype.addChild = function (child) {
        if (!this.options.children) {
            this.options.children = [];
        }
        this.options.children.push(child);
    };

    /**
     * 获取输入框被勾选 或 未勾选的节点集合。[setting.check.enable = true 时有效]
     * checkedBoolean
     * checked = true 表示获取 被勾选 的节点集合
     * checked = false 表示获取 未勾选 的节点集合
     * 省略此参数，等同于 true。
     *
     * 对于 treeNode.nochecked = true 的节点不进行获取。
     * 返回值Array(JSON)
     * 返回全部符合要求的节点集合 Array
     */
    JTree.prototype.getCheckedNodes = function (checked) {
        return this.getTree().getCheckedNodes(checked);
    };

    /**
     * 获取输入框被勾选 或 未勾选的节点集合中所有的叶子节点。[setting.check.enable = true 时有效]
     * checkedBoolean
     * checked = true 表示获取 被勾选 的节点集合
     * checked = false 表示获取 未勾选 的节点集合
     * 省略此参数，等同于 true。
     *
     * 对于 treeNode.nochecked = true 的节点不进行获取。
     * 返回值Array(JSON)
     * 返回全部符合要求的节点集合的叶子节点 Array
     */

    JTree.prototype.getCheckedChildNodes = function (checked) {
        var treeNode = this.getTree().getCheckedNodes(checked);
        var childNodes = [];
        $.each(treeNode, function (i, val) {
            if (!val['children']) {
                childNodes.push(val);
            }
            ;
        });
        return childNodes;
    }
    /**
     * 可调用的方法
     */
    var allowedMethods = ['editName', 'addNodes', 'updateNode', 'expandAll'
        , 'expandNode', 'cancelEditName', 'removeNode', 'getSelectedNodes'
        , 'addChild', 'getNodes', 'getCheckedNodes', 'reload', 'getCheckedChildNodes'];


    $.fn.jTree = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jTree'),
                options = $.extend({}, JTree.DEFAULTS, $this.data(),
                    typeof option === 'object' && option);

            if (typeof option === 'string') {
                if ($.inArray(option, allowedMethods) < 0) {
                    throw new Error("Unknown method: " + option);
                }


                if (!data) {
                    return;
                }
                /*方法直接调用 bootstrapTable 的方法 */
                value = data[option].apply(data, args);

                if (option === 'destroy') {
                    $this.removeData('bootstrap.jTree');
                }
            }

            if (!data) {
                $this.data('bootstrap.jTree', (data = new JTree(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jTree.Constructor = JTree;
    $.fn.jTree.defaults = JTree.DEFAULTS;
    $.fn.jTree.methods = allowedMethods;
    $.fn.jTree.utils = {};

    $(function () {
        $('[data-toggle="jTree"]').jTree();
    });

}(jQuery);