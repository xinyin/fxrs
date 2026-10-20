/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';
    var CHECK_DATA = {};

    var JTable = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        if (this.options) {
            $.extend(this.options, {'id': this.$el.attr('id')});
            $.extend(this.options, {'title': this.$el.attr('title')});
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
    JTable.DEFAULTS = $.extend({}, $.fn.bootstrapTable.defaults, {
        /*覆盖 bootstrapTable 的默认属性*/
        queryParamsType: '',
        pagination: true,
        sidePagination: 'server',
        pageNumber: 1,
        paginationHAlign: 'left',
        paginationDetailHAlign: 'right',
        pageSize: 10,
        pageList: [1, 2, 5, 10, 25, 50, 100,200],
        showRefresh: true,
        showColumns: true,
        clickToSelect: true,
        /*jTable 的新增属性*/
        queryForm: undefined,
        queryFormQueryButton: 'btnQuery',
        queryFormResetButton: 'btnReset',
        /*controller 的根路径，用于CRUD默认操作生成url*/
        url: undefined,
        queryUrl: 'query',
        addUrl: 'add',
        editUrl: 'edit',
        viewUrl: 'view',
        removeUrl: 'remove',
        /*扩展查询参数，用于主从关联，父对象可以是 jTree Or jTable*/
        extQueryParams: {},
        /*扩展查询参数，用于导出获取参数*/
        exportQueryParams: {},
        /*子表对象*/
        children: undefined,
        /*父对象相关属性*/
        parentId: undefined,
        parentType: 'table',
        parentField: undefined,
        childField: undefined,
        childFieldType: 'S',
        /*默认选中第一条*/
        selectFirst: true,
        /*导出参数选择窗口的ID*/
        exportWinID: 'exportWinID'
    });

    /**
     * 列默认属性，从 BootstrapTable 的默认属性扩展而来
     */
    JTable.COLUMN_DEFAULTS = $.extend({}, $.fn.bootstrapTable.columnDefaults, {
        halign: 'center'
    });

    JTable.EVENTS = [
        'onAll', 'onClickCell', 'onDblClickCell', 'onClickRow', 'onDblClickRow',
        'onSort', 'onCheck', 'onUncheck', 'onCheckAll', 'onUncheckAll', 'onCheckSome',
        'onUncheckSome', 'onLoadSuccess', 'onLoadError', 'onColumnSwitch', 'onPageChange',
        'onSearch', 'onToggle', 'onPreBody', 'onPostBody', 'onPostHeader', 'onExpandRow',
        'onCollapseRow', 'onRefreshOptions', 'onResetView', 'onRefresh'
    ];

    /**
     * 初始化
     */
    JTable.prototype.init = function () {

        this.initParent();
        this.initQueryForm();
        this.initBootstrapTable();
    };

    /**
     * 初始化父对象
     */
    JTable.prototype.initParent = function () {

        var $this = this, $opt = $this.options;
        /*设置父对象*/
        if ($opt.parentId && $opt.parentField) {
            //child 格式: {id : 'schoolTable', idField: 'code', idFieldType: 'S', field: 'code', operator: 'EQ'}
            var child = {
                id: $this.$el.attr('id'),
                idField: $opt.childField || $opt.idField,
                idFieldType: $opt.childFieldType,
                field: $opt.parentField,
                operator: 'EQ'
            }

            if ($opt.parentType === 'tree') {
                setTimeout(function () {
                    $('#' + $opt.parentId).jTree('addChild', child);
                }, 500);
            } else {
                $('#' + $opt.parentId).jTable('addChild', child);
            }

            /*设置一个假的值，让子表不显示数据*/
            var params = {};
            params['__EQ' + $opt.idFieldType + '_' + $opt.idField] = 'NotAssigned';
            this.setExtQueryParams(params);
        }

    };

    /**
     * 初始化相关联的查询表单
     */
    JTable.prototype.initQueryForm = function () {

        var $this = this, $options = $this.options;
        /*定义了queryForm，处理查询和重置按钮事件*/
        if ($options.queryForm) {
            /*查询按钮事件*/
            if ($options.queryFormQueryButton) {
                $('#' + $options.queryFormQueryButton).on('click', function (e) {
                    $this.$el.bootstrapTable('refresh');
                });
            }

            /*重置按钮事件*/
            if ($options.queryFormResetButton) {
                $('#' + $options.queryFormResetButton).on('click', function (e) {
                    /*清空表单*/
                    /*$(':input', '#' + $options.queryForm)
                    .not(':button, :submit, :reset')
                    .val('')
                    .removeAttr('checked')
                    .removeAttr('selected');
                    */
                    // 迭代input清空
                    $(':input', '#' + $options.queryForm).each(function () {
                        var type = this.type;
                        var tag = this.tagName.toLowerCase();
                        if (type == 'text' || type == 'password' || tag == 'textarea')
                            this.value = "";
                        else if (type == 'checkbox' || type == 'radio')
                            this.checked = false;
                        else if (tag == 'select')
                            this.selectedIndex = -1;
                    });

                    /*返回第一页*/
                    $this.$el.bootstrapTable('selectPage', 1);
                    /*重新查询*/
                    $this.$el.bootstrapTable('refresh');

                });
            }
        }

    };


    /*重设事件处理函数*/
    JTable.prototype.resetEventHandlers = function () {
        var $this = this, $opt = $this.options, $options = $.extend(true, {}, $opt);
        $.each(JTable.EVENTS, function () {
            var $old = undefined;
            if (typeof $opt[this] === 'string') {
                $old = calculateFunc($opt[this]);
            }
            if (this == 'onCheck') {
                $options[this] = function (row) {
                    //判断是否是多选，只有多选才需要记住翻页的数据
                    if ($opt.multiple) {
                        if (!isCheckExsit(row, $opt.idField, $opt.id)) {
                            //先判断是否是数组
                            if ($.isArray(CHECK_DATA['\'' + $opt.id + '\''])) {
                                CHECK_DATA['\'' + $opt.id + '\''].push(row);
                            } else {
                                CHECK_DATA['\'' + $opt.id + '\''] = [row];
                            }

                        }
                    } else {
                        CHECK_DATA['\'' + $opt.id + '\''] = [row];
                    }

                    /*设置主子关联*/
                    if ($.isArray($opt.children)) {
                        $.each($opt.children, function () {
                            var queryParams = {};
                            queryParams['__' + this.operator + this.idFieldType + '_' + this.idField] = row[this.field];
                            $('#' + this.id).jTable('setExtQueryParams', queryParams);
                            $('#' + this.id).jTable('refresh');
                        });
                    }
                    if ($old) {
                        return $old.apply($this, arguments);
                    }
                    return true;
                }
            } else if (this == 'onLoadSuccess') {
                $options[this] = function (data) {
                    if ($options.selectFirst && data.total > 0) {
                        $this.$el.bootstrapTable('check', 0);
                        $('table thead th .th-inner').each(function () {
                            $(this).attr('title', $(this).text() || "--");
                        });
                        //显示title， 用作提示
                        $('#' + $options.id + ' tr td').each(function () {
                            $(this).attr("title", $(this).text());
                            $(this).css("cursor", 'pointer');
                        });
                    }
                    if ($old) $old.apply($this, arguments);
                }
            } else if (this == 'onCheckAll') {
                $options[this] = function (rows) {
                    //判断是否是多选，只有多选才需要记住翻页的数据
                    if ($opt.multiple) {
                        $.each(rows, function (i, row) {
                            if (!isCheckExsit(row, $opt.idField, $opt.id)) {
                                //先判断是否是数组
                                if ($.isArray(CHECK_DATA['\'' + $opt.id + '\''])) {
                                    CHECK_DATA['\'' + $opt.id + '\''].push(row);
                                } else {
                                    CHECK_DATA['\'' + $opt.id + '\''] = [row];
                                }
                            }
                        });
                    }
                }
            } else if (this == 'onUncheck') {
                $options[this] = function (row) {
                    //判断是否是多选，只有多选才需要记住翻页的数据
                    if ($opt.multiple) {
                        CHECK_DATA['\'' + $opt.id + '\''] = $.grep(CHECK_DATA['\'' + $opt.id + '\''], function (value) {
                            return value['' + $opt.idField + ''] != row['' + $opt.idField + ''];
                        });
                    }
                }
            } else if (this == 'onUncheckAll') {
                $options[this] = function (rows) {
                    //判断是否是多选，只有多选才需要记住翻页的数据
                    if ($opt.multiple) {
                        $.each(rows, function (i, row) {
                            CHECK_DATA['\'' + $opt.id + '\''] = $.grep(CHECK_DATA['\'' + $opt.id + '\''], function (value) {
                                return value['' + $opt.idField + ''] != row['' + $opt.idField + ''];
                            });
                        });
                    }
                }
            } else {
                $options[this] = $old;
            }
        });
        return $options;
    };

    //比较数组中是否已存在当前选中的值
    var isCheckExsit = function (row, idField, tId) {
        var flag = false;

        $.each(CHECK_DATA['\'' + tId + '\''], function (index, obj) {
            if (row['' + idField + ''] == obj['' + idField + '']) {
                flag = true;
            }
        });
        return flag;

    };

    /**
     * 获取已选中的所有行数据
     */
    JTable.prototype.getCheckData = function () {
        var $this = this, $options = $this.options;
        return CHECK_DATA['\'' + $options.id + '\''] ? CHECK_DATA['\'' + $options.id + '\''] : [];
    };

    /**
     * 清空已选中的所有行数据
     */
    JTable.prototype.clearCheckData = function () {
        var $this = this, $options = $this.options;
        CHECK_DATA['\'' + $options.id + '\''] = [];
    };

    /**
     * 初始化已选中的数据
     */
    JTable.prototype.initCheckData = function (rows) {
        var $this = this, $options = $this.options;
        CHECK_DATA['\'' + $options.id + '\''] = rows.concat();
    };

    /**
     * 初始化BootstrapTable
     */
    JTable.prototype.initBootstrapTable = function () {
        var $this = this, $opt = $this.options, $options = this.resetEventHandlers();

        /*扩展表格参数，设置查询条件*/
        $options = $.extend({}, $options, {
            queryParams: function (params) {
                var queryFormParams = {};
                if ($options.queryForm && $options.queryFormQueryButton) {
                    var data = $('#' + $options.queryForm).serializeAll();
                    $.each(data, function (i, v) {
                        //如果是checkbox 属于多选，需要单独处理
                        var input = $('input[name="' + v.name + '"]');
                        if (input.attr('type') == 'checkbox' || input.attr('type') == 'select') {
                            if (queryFormParams[v.name] == undefined) {
                                queryFormParams[v.name] = v.value;
                            } else {
                                queryFormParams[v.name] = queryFormParams[v.name] + ',' + v.value;
                            }
                        } else {
                            queryFormParams[v.name] = v.value;
                        }
                    });

                    $options.exportQueryParams = jQuery.extend({}, queryFormParams);
                }

                /*查询参数，合并table的参数和查询表单的参数*/
                var queryParams = $.extend({
                    pageSize: params.pageSize,
                    pageNumber: params.pageNumber,
                    sortOrder: params.sortOrder,
                    sortName: params.sortName,
                    searchText: params.searchText
                }, queryFormParams);

                /*合并扩展查询参数*/
                $.extend(queryParams, $this.options.extQueryParams);
                return queryParams;
            },
            url: $.helper.curl($options.url, $options.queryUrl),
            baseUrl: $options.url
        });

        /*初始化工具栏按钮*/
        var $toolbarOptions = $options.toolbarOptions;
        if ($toolbarOptions) {
            /*原始函数初始化完数据后，修改列的formatter*/
            $.each($toolbarOptions, function (i, p) {
                var $baseUrl = $options.baseUrl;
                //自定义事件
                $('#' + p.btnId).on('click', function (e) {
                    if (p.id === 'add') {
                        /*获取上下联动父类参数*/
                        var param = '';
                        if ($opt.parentId && $opt.parentField) {
                            var $parentType = $opt.parentType;
                            var $parentId = $opt.parentId;
                            var $parentField = $opt.parentField;
                            var $childField = $opt.childField;
                            /*table 返回 参数=值*/
                            if ($parentType === 'table') {
                                var rows = $('#' + $parentId).bootstrapTable('getSelections');
                                var value = (rows.length > 0) ? (rows[0][$parentField]) : '';
                                param = '?' + $childField + '=' + value;
                            } else {
                                //tree暂时未实现
                            }
                        }

                        //弹出页面
                        var $url = $.helper.curl($baseUrl, $options.addUrl);
                        window.location = $url + param;
                        //弹出框
                        /*$.messager.modal({
                            winId: 'winSchoolEdit',
                            width: 800,
                            height: 500,
                            url: $url,
                            buttons: [
                                {
                                    text : '关闭',
                                    icon : 'times',
                                    type : 'default',
                                    value : false
                                }
                            ],
                            onClose: function(){
                                $this.refresh();
                            }
                        });*/
                    }
                    /*简单导出*/
                    else if (p.id === 'exportSimple') {
                        var _table = $('#' + $opt.id).bootstrapTable('getOptions')
                        var total = _table.totalRows;
                        //列表为空，则不导出数据
                        if (total > 0) {
                            var exportWinID = $options.exportWinID.substring(0, 1) == '#' ? $options.exportWinID.substring(1) : $options.exportWinID;
                            var initialized = $('#ifrExport').length > 0;
                            if (!initialized) {
                                var winhtml = '<iframe id="ifrExport" src="about:blank" frameborder="0" scrolling="auto" style="display:none;z-index: 1;width:0px;height:0px; overflow: visible;"/>';
                                $(winhtml).appendTo($(document.body));
                            }

                            //获取下载地址，并获取下载参数
                            var url = $baseUrl + '/export';
                            /*获得列名与变量值*/
                            var columnTitles = [];
                            var columnFields = [];
                            var columns = _table.columns;
                            $.each(columns[0], function (i, column) {
                                if (column.field == 'state' || column.field == '__operator') return true;
                                columnTitles.push(column.title);
                                columnFields.push(column.field);
                            })

                            //打开下载选择页面
                            $.messager.modal({
                                winId: exportWinID,
                                width: 450,
                                height: 240,
                                title: '简单导出',
                                url: base + '/sys/xlsie/simple',
                                args: {
                                    callback: function (url) {
                                        $('#ifrExport').attr('src', url);
                                    },
                                    id: exportWinID,
                                    title: _table.title,
                                    pageNumber: _table.pageNumber,
                                    pageSize: _table.pageSize,
                                    total: total,
                                    url: url,
                                    queryParams: $options.exportQueryParams,
                                    columnTitles: $.helper.base64Encode(columnTitles.join(',')),
                                    columnFields: columnFields.join(',')
                                },
                                onClose: function () {

                                }
                            });
                        } else {
                            $.messager.toastr('错误', '导出数据为空。', 'error');
                        }

                    }
                    /*自定义操作，必须定义handler*/
                    else if (typeof p.handler === 'function') {
                        p.handler.apply(this, arguments);
                    } else if (typeof p.handler === 'string') {
                        var func = calculateFunc(p.handler);
                        if (func) func.apply(this, arguments);
                    }
                });
            });
        }

        /*在 initTable 后取得column的配置，对 fixedDict 的 formatter 做特殊处理*/
        var $initTable = $.fn.bootstrapTable.Constructor.prototype.initTable;
        $.fn.bootstrapTable.Constructor.prototype.initTable = function () {
            /*先调用原始函数*/
            $initTable.apply(this, Array.prototype.slice.apply(arguments));
            var $this = this;
            /*原始函数初始化完数据后，修改列的formatter*/
            $.each(this.columns, function (i, column) {
                var $formatter = column.formatter;
                /*重新设置formatter为代理过的函数*/
                if (typeof $formatter === 'string') {
                    /*fixedDict*/
                    if (/^fixedDict\:(\w+)/g.test($formatter)) {
                        var $type = RegExp.$1;
                        $.cache.cache('fixedDict', $type);
                        column.formatter = function (value, row, index) {
                            return ['<div class="text-center">', $.cache.getLabel($type, value), '</div>'].join('');
                        };
                    }
                    /*dict*/
                    if (/^dict\:(\w+)/g.test($formatter)) {
                        var $type = RegExp.$1;
                        $.cache.cache('dict', $type);
                        column.formatter = function (value, row, index) {
                            return ['<div class="text-center">', $.cache.getLabel($type, value), '</div>'].join('');
                        };
                    }
                    /*custom*/
                    else if (/^custom\:(.+)/g.test($formatter)) {
                        var $type = RegExp.$1;
                        $.cache.cache('custom', $type);
                        column.formatter = function (value, row, index) {
                            return ['<div class="text-center">', $.cache.getLabel($type, value), '</div>'].join('');
                        };
                    } else if (/^date\:(.+)/g.test($formatter)) {
                        var $pattern = RegExp.$1;
                        $pattern = $pattern == 'date' ? $.date.DATE_FORMAT_4 : ($pattern == 'datetime' ? $.date.DATE_FORMAT_1 : $pattern);
                        column.formatter = function (value, row, index) {
                            if (typeof value === 'number') {
                                var date = new Date(value);
                                return ['<div class="text-center">', $.date.format(date, $pattern), '</div>'].join('');
                            } else if (typeof value === 'string') {
                                return value;
                            } else if (typeof value === 'object' && (value instanceof Date)) {
                                return ['<div class="text-center">', $.date.format(value, $pattern), '</div>'].join('');
                            }

                            return "";
                        }
                    }
                }

                /*
                 * field === __operator， 表示这是一个操作列
                 * 根据 column.operators 的定义，初始化operator列的显示和事件处理
                 */
                if (column.field === '__operator') {
                    var $opers = column.operators;

                    /*自定义事件对象*/
                    var $propName = $this.$el.attr('id') + "_operatorEvent";
                    window[$propName] = {};

                    /*设置列格式*/
                    column.formatter = function (value, row, index) {
                        var $html = [];
                        var $events = window[$propName];
                        var $idField = $this.options.idField;
                        var $baseUrl = $this.options.baseUrl;

                        $html.push('<div class="text-center">');
                        $.each($opers, function (i, p) {
                            /*判断是否可见*/
                            var isVisible = true;
                            if (typeof p.visible === 'string') {
                                var func = calculateFunc(p.visible);
                                if (func)
                                    isVisible = func.apply(this, [row]);
                            }

                            if (isVisible) {
                                $html.push('<a class="' + p.id + '" href="javascript:void(0)" title="' + p.title + '">');
                                $html.push('<i class="fa fa-' + p.icon + '"></i>');
                                $html.push('</a>&nbsp;');

                                /*处理事件*/
                                var $eventName = 'click .' + p.id;

                                $events[$eventName] = function (e, value, row, index) {
                                    /*编辑操作默认处理程序*/
                                    if (p.id === 'edit') {
                                        var $url = $.helper.curl($baseUrl, $this.options.editUrl, row[$idField]);
                                        window.location = $url;
                                        /*$.messager.modal({
                                            winId: 'winSchoolEdit',
                                            width: 800,
                                            height: 500,
                                            url: $url,
                                            buttons: [
                                                {
                                                    text : '关闭',
                                                    icon : 'times',
                                                    type : 'default',
                                                    value : false
                                                }
                                            ],
                                            onClose: function(){
                                                $this.refresh();
                                            }
                                        });*/
                                    }
                                    /*查看操作默认处理程序*/
                                    else if (p.id === 'view') {
                                        var $url = $.helper.curl($baseUrl, $this.options.viewUrl, row[$idField]);
                                        window.location = $url;
                                        /*$.messager.modal({
                                            winId: 'winSchoolView',
                                            width: 800,
                                            height: 400,
                                            url: $url,
                                            buttons: [
                                                {
                                                    text : '关闭',
                                                    icon : 'times',
                                                    type : 'default',
                                                    value : false
                                                }
                                            ]
                                        });*/
                                    }
                                    /*移除操作默认处理程序*/
                                    else if (p.id === 'remove') {
                                        $.messager.confirm('删除', '确定要删除吗？', function (r) {

                                            if (r) {
                                                var $url = $.helper.curl($baseUrl, $this.options.removeUrl, row[$idField]);
                                                $.mask.post($url, function (data) {
                                                    if (data.success) {
                                                        $this.refresh();
                                                    } else {
                                                        $.messager.alert('错误', data.msg, 'error');
                                                    }
                                                });
                                            }

                                        });
                                    }
                                    /*自定义操作，必须定义handler*/
                                    else if (typeof p.handler === 'function') {
                                        p.handler.apply(this, arguments);
                                    } else if (typeof p.handler === 'string') {
                                        var func = calculateFunc(p.handler);
                                        if (func) func.apply(this, arguments);
                                    }
                                };
                            }


                        });
                        $html.push('</div>');
                        return $html.join('');
                    }

                    /*事件处理程序*/
                    column.events = $propName;

                }
            });
        };
        var $detailFormatter = $options.detailFormatter || function (index, row) {
            var html = [];
            $.each(row, function (key, value) {
                html.push('<p><b>' + key + ':</b> ' + value + '</p>');
            });
            return html.join('');
        };

        /*初始化 detailFormatter*/
        $.extend($options, {
            detailFormatter: $detailFormatter
        });

        var $responseHandler = function (res) {

            var $initCheckData = $options.initCheckData;

            if ($initCheckData) {
                CHECK_DATA['\'' + $options.id + '\''] = $initCheckData;
                $options.initCheckData = '';
            }

            //判断当前数组是否存在数据
            var checkDatas = CHECK_DATA['\'' + $options.id + '\''];
            if (checkDatas && checkDatas.length > 0) {
                $.each(res.rows, function (i, val) {
                    $.each(checkDatas, function (j, item) {
                        if (val['' + $options.idField + ''] == item['' + $options.idField + '']) {
                            val.state = true;
                        } else {
                            val.state = false;
                        }
                    });
                });
            }

            return res;
        };

        /*初始化 responseHandler*/
        $.extend($options, {
            responseHandler: $responseHandler
        });

        /*初始化 bootstrapTable */
        $this.$el.bootstrapTable($options);
    };

    /**
     * 设置扩展查询参数，该参数是除默认参数和查询表单外的其他查询参数
     * 主要用于主子表的关联条件查询
     */
    JTable.prototype.setExtQueryParams = function (params) {
        /*扩展选项，增加queryparams*/
        this.options.extQueryParams = params;
    };

    /**
     * 增加子对象，目前只支持 jTable
     * @parma child 格式: {id : 'schoolTable', idField: 'code', idFieldType: 'S', field: 'code', operator: 'EQ'}
     */
    JTable.prototype.addChild = function (child) {
        if (!this.options.children) {
            this.options.children = [];
        }
        this.options.children.push(child);
    };

    /**
     * 可调用的方法，默认和BootstrapTable 的方法相同。
     */
    var allowedMethods = $.fn.bootstrapTable.methods.slice(0);
    allowedMethods.push("setExtQueryParams");
    allowedMethods.push("addChild");
    allowedMethods.push("getCheckData");
    allowedMethods.push("initCheckData");
    allowedMethods.push("clearCheckData");

    $.fn.jTable = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jTable'),
                bdata = $this.data('bootstrap.table'),
                options = $.extend({}, JTable.DEFAULTS, $this.data(),
                    typeof option === 'object' && option);

            if (typeof option === 'string') {
                if ($.inArray(option, allowedMethods) < 0) {
                    throw new Error("Unknown method: " + option);
                }

                if (!data || !bdata) {
                    return;
                }

                /*方法直接调用 bootstrapTable的函数 */
                if (bdata[option]) {
                    value = bdata[option].apply(bdata, args);
                }
                /*jTable自定义的函数*/
                else {
                    value = data[option].apply(data, args);
                }

                if (option === 'destroy') {
                    $this.removeData('bootstrap.jTable');
                    $this.removeData('bootstrap.table');
                }
            }

            if (!data) {
                $this.data('bootstrap.jTable', (data = new JTable(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jTable.Constructor = JTable;
    $.fn.jTable.defaults = JTable.DEFAULTS;
    $.fn.jTable.columnDefaults = JTable.COLUMN_DEFAULTS;
    /*扩展 bootstrapTable 的默认列属性*/
    $.extend($.fn.bootstrapTable.columnDefaults, JTable.COLUMN_DEFAULTS);
    $.fn.jTable.methods = allowedMethods;
    $.fn.jTable.utils = $.extend({}, $.fn.bootstrapTable.utils, {});

    $(function () {
        $('[data-toggle="jTable"]').jTable();
    });

}(jQuery);