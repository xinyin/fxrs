<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <title>数据分析器</title>
</head>
<br:css pack="form"/>
<link rel="stylesheet" href="${res}/bootstrap-paginator/css/qunit-1.11.0.css">
<link rel="stylesheet" href="${res}/bootstrap-paginator/css/bootstrapv3.css">
<body>
<div class="ibox-content" style="padding: 0px; border-top-width: 0px;">
    <div class="panel-body">
        <button id="modelViewSwitch" class="btn btn-primary" onclick="replaceModel();"><span id="modelView">列模式</span>
        </button>
        <button onclick="query('init');" class="btn btn-primary">查询</button>
        <button type="button" id="set" class="btn btn-primary" onclick="set()">设置</button>
        <div id="queryChild" class="btn-group"></div>
        <button onclick="viewSql();" class="btn btn-primary">查看SQL</button>
        <button onclick="queryExport();" class="btn btn-primary">导出</button>
        <button onclick="btnSave();" id="saveId" class="btn btn-primary">保存</button>
        <button onclick="btnSaveAs();" class="btn btn-primary">另存为</button>
        <button onclick="btnSaveAsView();" class="btn btn-primary">另存为视图</button>
        <div style="margin-top: 5px; min-height: 390px;">
            <div id="tableColContent" style="display: none;"></div>
            <div id="tableRowContent">
                <table id="tableRow"></table>
            </div>
            <div style="margin-top: 3px;">
                <div class="fixed-table-pagination" style="">
                    <div class="pull-left pagination-detail">
                        <span class="pagination-info"></span><span
                            class="page-list">每页显示 <span class="btn-group dropup"><button
                            type="button" class="btn btn-default dropdown-toggle"
                            data-toggle="dropdown">
										<span class="page-size">10</span> <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li><a href="javascript:void(0)" onclick="setPageSize('1')">1</a></li>
										<li><a href="javascript:void(0)" onclick="setPageSize('2')">2</a></li>
										<li><a href="javascript:void(0)" onclick="setPageSize('5')">5</a></li>
										<li><a href="javascript:void(0)" onclick="setPageSize('10')">10</a></li>
										<li><a href="javascript:void(0)" onclick="setPageSize('25')">25</a></li>
										<li><a href="javascript:void(0)" onclick="setPageSize('50')">50</a></li>
									</ul></span> 条记录
							</span>
                    </div>
                    <div class="pull-right pagination">
                        <ul id='bp-pagination'></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="display: none;">
    <iframe name="exportIframe" src=""></iframe>
    <form id="exportForm" action="${base}/sys/dataAnalysis/queryExport" target="exportIframe" method="post">
        <input type="hidden" id="tableId" name="tableId">
        <input type="hidden" id="script" name="script">
    </form>
</div>
<be:processEvent/>
<br:js pack="form"/>
<script type="text/javascript" src="${res}/js/jquery.json-2.4.min.js"></script>
<script type="text/javascript" src="${res}/js/meta/MetaQuery.js"></script>
<script src="${res}/bootstrap-paginator/src/bootstrap-paginator.js"></script>
<script src="${res}/bootstrap-paginator/lib/qunit-1.11.0.js"></script>
<script type="text/javascript">
    var id = '${id}';
    var type = '${type}';
    var masterTableId = '${masterTableId}';
    var masterColumnCode = '${masterColumnCode}';
    var joinTableId = '${joinTableId}';
    var joinColumnCode = '${joinColumnCode}';
    var data = '${data}';
    var staffCode = '${curUser.staffCode}';
    /*关联表类型*/
    var relationType = '${relationType}';
    /*metaQuery对象*/
    var metaQuery = '';
    /*当前页码*/
    var pageNumber = 1;
    /*每页显示数*/
    var pageSize = '';
    /*结果对象*/
    var result = '';
    /*当前第几行*/
    var index = 0;
    var ieIndex = 0;
    /*新增名字*/
    var queryName = '';
    /*记录是第一次进入*/
    var onePage = -1;
    /*初始化关联表数据*/
    var joinTables = '';
    var masterTables = '';
    /*当前默认模式 L行模式*/
    var colModel = '${modelView}';
    /*记录结果*/
    var resultColumns = [];
    var resultData = [];
    /* 总条数 */
    var total = 0;

    var tId = '${tId}';

    $(function () {
        if (type == 'T') {
            //如果是点击的Table进行下面的查询
            /*获得初始化数据*/
            $.mask.post('${base}/sys/metaQuery/get', {'regionTableId': id}, function (data) {
                if (data.success) {
                    /*获取脚本*/
                    metaQuery = data.query;

                    result = data.result;
                    //resultData = result.result;
                    resultData = $.parseJSON(data.resultData);
                    /*初始化关联数据*/
                    joinTables = data.joinTable;
                    masterTables = data.masterTable;

                    /*获取分页数据*/
                    var obj = $.parseJSON(metaQuery.script);
                    total = obj.total;

                    pageSize = obj.limit;
                    pageNumber = obj.pageNo;
                    /*初始化显示查询数据*/
                    showResult(result, resultData);
                    pageMove(total);
                    /*初始化关联表数据*/
                    childSelect();
                }

            });
        } else if (type == 'Q' || type == 'P') {
            $.mask.post('${base}/sys/metaQuery/edit', {'queryId': id}, function (data) {
                if (data.success) {
                    /*获取脚本*/
                    metaQuery = data.metaQuery;

                    result = data.result;
                    //resultData = result.result;
                    resultData = $.parseJSON(data.resultData);

                    /*初始化关联数据*/
                    joinTables = data.joinTable;
                    masterTables = data.masterTable;

                    /*获取分页数据*/
                    var obj = $.parseJSON(metaQuery.script);
                    total = obj.total;
                    pageSize = obj.limit;
                    pageNumber = obj.pageNo;

                    /*初始化显示查询数据*/
                    showResult(result, resultData);
                    pageMove(total);
                    /*初始化关联表数据*/
                    childSelect();

                    /* 验证公共查询只能自己才能保存 */
                    if (metaQuery.queryType == 'PUBLIC') {
                        /* 验证是不是自己创建的 */
                        if (metaQuery.staffCode != staffCode) {
                            $('#saveId').attr('disabled', true);
                        }
                    }
                }

            });
        } else {

            //如果是点击的Table进行下面的查询
            /*获得初始化数据*/
            $.mask.post('${base}/sys/metaQuery/queryChild', {
                'masterTableId': masterTableId,
                'joinTableId': joinTableId,
                'data': data,
                'relationType': relationType,
                'masterColumnCode': masterColumnCode,
                'joinColumnCode': joinColumnCode
            }, function (data) {
                if (data.success) {
                    /*获取脚本*/
                    metaQuery = data.query;

                    result = data.result;
                    //resultData = result.result;
                    resultData = $.parseJSON(data.resultData);

                    /*初始化关联数据*/
                    joinTables = data.joinTable;
                    masterTables = data.masterTable;

                    /*获取分页数据*/
                    var obj = $.parseJSON(metaQuery.script);
                    total = obj.total;

                    /*初始化显示查询数据*/
                    showResult(result, resultData);
                    /*初始化关联表数据*/
                    childSelect();
                } else {
                    /*查询数据不存在关联字段*/
                    $.messager.alert('系统提示', data.msg, 'info', function () {
                        /*关闭当前打开的tabs*/
                        $(window.parent.colseTabs());
                    });
                }

            });
        }
    });


    /*读取关联表*/
    function childSelect() {

        var length = masterTables.length + joinTables.length;

        var menuHtml = '<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">关联表<span class="caret"></span></button> ';
        /* 计算当前子项一共的高度 */
        if ((length * 20) > 200) {
            menuHtml += '<ul class="dropdown-menu" style="height: 200px; overflow:scroll; overflow-x: hidden;"> ';
        } else {
            menuHtml += '<ul class="dropdown-menu"> ';
        }


        /*加载主表*/
        for (var i = 0; i < masterTables.length; i++) {


            var title = masterTables[i].masterTableCode;

            /* 判断关联是否存在中文名 */
            if (null != masterTables[i].masterTableName && masterTables[i].masterTableName != '') {
                title = masterTables[i].masterTableName;
            }

            /* 截取12个字符，以前过长显示不下 */
            var _title = title + '(' + masterTables[i].masterColumnCode + ')';
            //	_title = _title.substr(0, 15);

            menuHtml += '<li><a href="#" onclick="selectTableCallback(\'' + masterTables[i].regionTableId + '\', \'' + masterTables[i].masterColumnCode + '\', \'' + metaQuery.tableId + '\', \'' + masterTables[i].joinColumnCode + '\', \'' + masterTables[i].masterTableCode + '\', \'' + encodeURI(title) + '\', \'JOIN\');" title="' + title + '(' + masterTables[i].masterColumnCode + ')' + '"> (主)' + _title + '</a></li>';

            //menuHtml += '<div onclick="selectTableCallback(\'' + masterTables[i].regionTableId + '\', \'' + masterTables[i].masterColumnCode + '\', \'' + metaQuery.tableId + '\', \'' + masterTables[i].joinColumnCode + '\', \'' + masterTables[i].masterTableCode + '\', \'' + encodeURI(title) + '\', \'JOIN\');" title="' + title + '(' + masterTables[i].joinColumnCode + ')' + '"> (主)<span style="display:-moz-inline-box;display:inline-block;width:200px;">' + _title + '</span></div>';
        }

        if (joinTables.length > 0 && masterTables.length > 0) {
            menuHtml += '<li role="separator" class="divider"></li>';
        }

        /*循环加入菜单项*/
        for (var i = 0; i < joinTables.length; i++) {

            var title = joinTables[i].joinTableCode;

            /* 判断关联是否存在中文名 */
            if (null != joinTables[i].joinTableName && joinTables[i].joinTableName != '') {
                title = joinTables[i].joinTableName;
            }

            /* 截取12个字符，以前过长显示不下 */
            var _title = title + '(' + joinTables[i].joinColumnCode + ')';
            //_title = _title.substr(0, 15);

            menuHtml += '<li><a href="#" onclick="selectTableCallback(\'' + joinTables[i].regionTableId + '\', \'' + joinTables[i].masterColumnCode + '\', \'' + metaQuery.tableId + '\', \'' + joinTables[i].joinColumnCode + '\', \'' + joinTables[i].joinTableCode + '\', \'' + encodeURI(title) + '\', \'MASTER\');" title="' + title + '(' + joinTables[i].joinColumnCode + ')' + '"> (子)' + _title + '</a></li>';
            //menuHtml += '<div onclick="selectTableCallback(\'' + joinTables[i].regionTableId + '\', \'' + joinTables[i].masterColumnCode + '\', \'' + metaQuery.tableId + '\', \'' + joinTables[i].joinColumnCode + '\', \'' + joinTables[i].joinTableCode + '\', \'' + encodeURI(title) + '\', \'MASTER\');" title="' + title  + '(' + joinTables[i].masterColumnCode + ')' + '"> (子)<span style="display:-moz-inline-box;display:inline-block;width:200px;">' + _title + '</span></div>';
        }

        /*菜单项为空，显示无*/
        if (masterTables.length < 1 && joinTables.length < 1) {
            menuHtml += '<li><a href="#">无</a></li>';
            //$('#queryChild').height(20);
        }

        menuHtml += '</ul>';
        /*加载子表*/
        $('#queryChild').append(menuHtml);
    }

    /**
     * 显示查询结果信息
     */
    function showResult(result, resultData) {
        resultColumns = [];
        /*封装列信息*/
        var count = result.columns.length;
        for (var i = 0; i < count; i++) {
            var column = {
                field: result.columns[i].columnCode,
                title: result.columns[i].displayName,
                width: 150
            };
            resultColumns.push(column);
        }

        $('#tableRow').bootstrapTable('destroy');
        $('#tableRow').bootstrapTable({
            columns: resultColumns,
            pageNumber: pageNumber,
            pageSize: pageSize,
            data: resultData
        });
    }

    function pageMove(total) {
        var totalPages = 1;
        if (total > 0) {
            totalPages = Math.ceil(total / pageSize);
        }
        var pInfo = '没有找到匹配的记录';
        if (total > 0) {
            var s = (pageNumber - 1) * pageSize + 1;
            var e = (pageNumber) * pageSize;
            if (e > total) {
                e = total;
            }
            pInfo = '显示第' + s + '到第' + e + '条记录，总共' + total + '条记录';
        }

        $('.pagination-info').html(pInfo);
        var options = {
            bootstrapMajorVersion: 3,
            currentPage: pageNumber,
            numberOfPages: 5,
            totalPages: totalPages,
            //点击事件
            onPageClicked: function (event, originalEvent, type, page) {
                query(type, page);
            }
        }

        $('#bp-pagination').bootstrapPaginator(options);
    }

    /*列模式*/
    function replaceModel() {
        if (resultData.length < 1) {
            colModel = VIEW_TYPE_COLUMN; //如果没有数据，则只能是列模式
            $.messager.alert('系统提示', '未选择数据，无法切换列模式。', 'info');
            return;
        }
        reviewModel();
        if (colModel == VIEW_TYPE_GENERAL) {
            colModel = VIEW_TYPE_COLUMN;
            $('#tableColContent').show();
            $('#tableRowContent').hide();
            $('#modelView').html('行模式');
        } else {
            colModel = VIEW_TYPE_GENERAL;
            $('#tableColContent').hide();
            $('#tableRowContent').show();
            /*列模式，切换普通模式*/
            $('#modelView').html('列模式');
        }
    }

    /*列模式*/
    function reviewModel() {
        /*定义显示的title 和  数据*/
        var columns = [];
        var colData = [];

        /*列模式显示*/
        /*获取当前选中的数据*/
        var obj = resultData[index];

        /*封装列*/
        var array = [{columnCode: 'code', title: '列编码'}, {columnCode: 'name', title: '列名'}, {
            columnCode: 'value',
            title: '值'
        }, {columnCode: 'child', title: '操作'}];
        for (var i = 0; i < array.length; i++) {
            if (array[i].columnCode == 'code') {
                var column = {
                    field: array[i].columnCode,
                    title: array[i].title,
                    width: 200,
                    formatter: function (value, row, index) {
                        var columnCode = value;
                        if (masterTables.length > 0) {
                            $.each(masterTables, function (index, col) {
                                if (col.joinColumnCode == value) {
                                    var title = col.masterTableCode;

                                    /* 判断关联是否存在中文名 */
                                    if (null != col.masterTableName && col.masterTableName != '') {
                                        title = col.masterTableName;
                                    }

                                    columnCode = '<a href="javaScript:void(0);" style="color: #0000CC ;" onclick="selectTableCallback(\'' + col.regionTableId + '\', \'' + col.masterColumnCode + '\', \'' + metaQuery.tableId + '\', \'' + col.joinColumnCode + '\', \'' + col.masterTableCode + '\',\'' + encodeURI(title) + '\', \'JOIN\');">' + value + '</a>';

                                }
                            });
                        }


                        return columnCode;
                    }
                };
                columns.push(column);
            } else if (array[i].columnCode == 'child') {
                /*查询该字段是否关联子表*/
                var column = {
                    field: array[i].columnCode,
                    title: array[i].title,
                    align: 'center',
                    formatter: function (value, row, index) {
                        var length = 0;
                        var menuHtml = '';
                        if (joinTables.length > 0) {

                            $.each(joinTables, function (index, col) {
                                if (col.masterColumnCode == value) {
                                    /* 记录关联表数量 */
                                    length += 1;

                                    /* 获取关联表编码 */
                                    var title = col.masterTableCode;

                                    /* 判断关联是否存在中文名 */
                                    if (null != col.masterTableName && col.masterTableName != '') {
                                        title = col.masterTableName;
                                    }

                                    var _title = title + '(' + col.joinColumnCode + ')';
                                    //_title = _title.substr(0, 14);
                                    menuHtml += '<li><a href="#" onclick="selectTableCallback(\'' + col.regionTableId + '\', \'' + col.masterColumnCode + '\', \'' + metaQuery.tableId + '\', \'' + col.joinColumnCode + '\', \'' + col.joinTableCode + '\', \'' + encodeURI(title) + '\', \'MASTER\');" title="' + title + '(' + col.joinColumnCode + ')' + '">' + _title + '</a></li>';
                                }
                            });
                        }

                        if (menuHtml != '') {
                            var div = '<div class="btn-group" style="z-index:100">'
                                + '<button type="button" style="z-index:100" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
                                + '查询子表 <span class="caret"></span>'
                                + '</button>'
                                + '<ul class="dropdown-menu" style="z-index:999999;position: absolute; -webkit-transition: .5s ease-in-out;-moz-transition: .5s ease-in-out;-o-transition: .5s ease-in-out; margin-top: -800px;margin-left:650px; ">'
                                + menuHtml
                                + '</ul>'
                                + '</div>';
                            return $(div).html();
                        }

                        return '';
                    }
                };
                columns.push(column);
            } else {
                var column = {
                    field: array[i].columnCode,
                    title: array[i].title,
                    width: 200
                };
                columns.push(column);
            }

        }

        /*封装数据*/
        /*准备value数据*/
        var dt = [];
        $.each(obj, function (key, value) {
            dt.push(value);
        });

        var c = result.columns.length;
        for (var i = 0; i < c; i++) {
            var column = {
                code: result.columns[i].columnCode,
                name: result.columns[i].columnName,
                value: dt[i],
                child: result.columns[i].columnCode
            };
            colData.push(column);
        }


        /*显示页码*/
        var pageCount = resultData.length - 1;
        var pageTxt = '';

        if (index < 1) {
            pageTxt += '<div style="margin-top: 7px;display:inline-block;float:left"><img src="${res}/img/meta/first.png" align="absmiddle"  title="第一条" style="margin-left: 10px;color: #ccc;opacity: 0.5;filter: alpha(opacity=50);vertical-align:middle" />';
            pageTxt += '<img src="${res}/img/meta/prev.png" align="absmiddle"  title="上一条" style="color: #ccc;opacity: 0.5;filter: alpha(opacity=50);vertical-align:middle"  />';
        } else if (index > 0) {
            pageTxt += '<div style="margin-top: 7px;display:inline-block;float:left"><img src="${res}/img/meta/first.png" align="absmiddle"  title="第一条" style="cursor: pointer;margin-left: 10px;vertical-align:middle" onclick="clickModel(\'index\');" />';
            pageTxt += '<img src="${res}/img/meta/prev.png" align="absmiddle"  title="上一条" style="cursor: pointer;vertical-align:middle"  onclick="clickModel(\'on\');" />';
        }

        /*下一条*/
        if (index >= pageCount) {
            pageTxt += '<img src="${res}/img/meta/next.png" align="absmiddle"  title="下一条"  style="cursor: pointer;color: #ccc;opacity: 0.5;filter: alpha(opacity=50);cursor: default;vertical-align:middle" />';
            pageTxt += '<img src="${res}/img/meta/last.png" align="absmiddle"  title="最后一条" style="cursor: pointer;color: #ccc;opacity: 0.5;filter: alpha(opacity=50);cursor: default;vertical-align:middle"  /> ';
        } else if (index < pageCount) {
            pageTxt += '<img src="${res}/img/meta/next.png" align="absmiddle"  title="下一条"  style="cursor: pointer;vertical-align:middle" onclick="clickModel(\'next\');" />';
            pageTxt += '<img src="${res}/img/meta/last.png" align="absmiddle"  title="最后一条" style="cursor: pointer;vertical-align:middle"  onclick="clickModel(\'end\');" /> ';
        }

        pageTxt += '<span>当前： ' + (index + 1) + '/' + result.result.length + '</span>';

        pageTxt += '<div style="margin: 2 0 0 5;display:inline-block;float:right">&nbsp;&nbsp;&nbsp;&nbsp;<a id="queryAllColumn" onclick="queryAllColumn();" href="javascript:void(0)">查看全部列</a></div></div>';
        pageTxt += '<table id="tableCol"></table>'
        $('#tableColContent').html(pageTxt);


        var obj = $.parseJSON(metaQuery.script);

        /*获取tables*/
        var tables = obj.tables;
        if (tables.length != 1) {
            $("#queryAllColumn").css("disable", "true");
        }

        /*加载数据*/
        $('#tableCol').bootstrapTable('destroy');
        $('#tableCol').bootstrapTable({
            columns: columns,
            data: colData
        });
    }

    /*上一条下一条*/
    function clickModel(type) {

        /*第一条*/
        if (type == 'index') {
            index = 0;
        }/*上一条*/
        else if (type == 'on') {
            if (index > 0) {
                index = index - 1;
            }

        }/*下一条*/
        else if (type == 'next') {
            if (index < result.result.length - 1) {
                index = index + 1;
            }
        } else {   /*最后一条*/
            index = result.result.length - 1;
        }

        reviewModel();

    }


    /*查询*/
    function query(type, pageNumber) {
        if (type == 'init') {
            pageNumber = 1;
        }
        /*把json脚本转换成对象*/
        var obj = $.parseJSON(metaQuery.script);
        /*封装对形象返回*/
        var script = {
            tables: obj.tables,
            masterTable: obj.masterTable,
            joinTables: obj.joinTables,
            queryType: obj.queryType,
            columns: obj.columns,
            groupBy: obj.groupBy,
            COUNT: obj.COUNT,
            groups: obj.groups,
            orders: obj.orders,
            having: obj.having,
            where: obj.where,
            limit: pageSize,
            pageNo: pageNumber
        };

        $.mask.post('${base}/sys/metaQuery/queryResult', {'script': JSON.stringify(script)}, function (data) {
            if (data.success) {
                result = data.result;
                //resultData = result.result;
                resultData = $.parseJSON(data.resultData);
                metaQuery.script = data.query.script;
                index = 0;
                var obj = $.parseJSON(metaQuery.script);
                total = obj.total;
                /*初始化显示查询数据*/
                showResult(result, resultData);
                clickModel('index');
                /* 移动到第一页 */
                if (type == 'init') {
                    pageMove(total);
                }
            } else {
                $.messager.alert('系统提示', data.msg, 'info');
            }
        });

    }

    /*查看sql语句*/
    function viewSql() {
        var viewSql = result.sql;

        /*编码，传入后台乱码*/
        var sql = encodeURI(encodeURI(viewSql));
        /* 打开sql查看画面 */
        $.messager.modal({
            winId: 'viewSqlList',
            width: 900,
            height: 400,
            title: '查看SQL',
            url: '${base}/sys/dataAnalysis/viewSql?querySql=' + sql
        });
    }

    /* 设置 */
    function set() {
        $.messager.modal({
            winId: 'winDataAnalysisSet',
            width: 800,
            height: 400,
            title: '增加关联表',
            url: '${base}/sys/dataAnalysis/set',
            args: {callback: setColse, script: metaQuery.script},
        });
    }

    function setColse(data, type) {
        script = data;
        metaQuery.script = JSON.stringify(data);
        /*传回来的type为E，需要立即执行 */
        if (type == 'E') {
            query('init');
        }
    }

    /*保存*/
    function btnSave() {

        /*queryId为空先设置名称*/
        if (!metaQuery.queryId) {
            /*弹出界面填写查询名称*/
            saveType = 'SAVE';
            addQueryName();
        } else {
            /* 验证公共查询只能自己才能保存 */
            if (metaQuery.queryType == 'PUBLIC') {
                /* 验证是不是自己创建的 */
                if (metaQuery.staffCode != staffCode) {
                    $.messager.alert('系统提示', '只能保存自己创建的查询。', 'info');
                    return;
                }
            }

            /*queryId存在，直接保存*/
            data = {
                queryId: metaQuery.queryId,
                script: metaQuery.script
            };

            $.mask.post('${base}/sys/metaQuery/save', data, function (data) {
                $.messager.alert('系统提示', data.msg, 'info');
            });
        }

    }

    /*另存为*/
    function btnSaveAs() {
        /*弹出界面填写查询名称*/
        saveType = 'SAVEAS';
        addQueryName();
    }

    /*填写查询名称*/
    function addQueryName() {
        /* 打开保存查查询画面 */
        $.messager.modal({
            winId: 'wmetaQueryName',
            width: 540,
            height: 300,
            title: '查询名称',
            url: '${base}/sys/dataAnalysis/queryName',
            args: {tableId: metaQuery.tableId, script: metaQuery.script, callback: saveCallback},
            onClose: function () {
                /*刷新标题,刷新树*/
                if (_isClosed == 1) {
                    $(window.parent.updateTitle(_title, _queryId, _tableId, _saveType, _queryType));
                    _isClosed = 0;
                }

            }
        });
    }

    var _title, _queryId, _tableId, _saveType, _queryType, _isClosed = 0;

    /*保存回调函数, 更新保存的tab的title等数据*/
    function saveCallback(queryName, queryId, tableId, queryType) {
        _title = queryName;
        _queryId = queryId;
        _tableId = tableId;
        _saveType = saveType;
        _queryType = queryType;
        _isClosed = 1;
    }

    /*另存为视图*/
    function btnSaveAsView() {
        /* 打开另存为画面 */
        $.messager.modal({
            winId: 'wmetaQuerySaveView',
            width: 650,
            height: 450,
            title: '另存为视图',
            url: '${base}/sys/dataAnalysis/saveView',
            args: {script: metaQuery.script, callback: colseSaveView},
            onClose: function () {
                /*刷新标题,刷新树*/
                if (_isClosed == 1) {
                    $(window.parent.updateTableView(_regionId, _tableId, _tableCode));
                    _isClosed = 0;
                }

            }
        });
    }

    var _regionId, _tableCode;

    /*保存回调函数, 更新保存的tab的title等数据*/
    function colseSaveView(regionId, tableId, tableCode) {
        _regionId = regionId;
        _tableCode = tableCode;
        _tableId = tableId;
        _isClosed = 1;
    }

    /*查询导出*/
    function queryExport() {
        /*防止打开新页面，采用post提交，target="exportIframe" */
        /*设置参数*/
        $('#tableId').val(metaQuery.tableId);

        var expObj = $.parseJSON(metaQuery.script);
        /* expObj.limit = total;
        expObj.pageNo = 1; */

        $('#script').val(JSON.stringify(expObj));

        /*提交表单*/
        $("#exportForm").submit();
    }

    /*选择关联表回调函数*/
    function selectTableCallback(masterTableId, masterColumnCode, joinTableId, joinColumnCode, tableCode, tableName, type) {

        if (resultData.length < 1) {
            $.messager.alert('系统提示', '暂无可以查看数据。', 'info');
            return;
        }

        var rows = resultData[index];

        var row = {};

        /* 验证当前数据行中是够存在关联字段 */
        var columnCode = '';
        if (type == 'JOIN') {
            /* 获取关联字段 */
            columnCode = joinColumnCode;

            /* 验证当前数组中是否存在关联字段 */
            if (!rows[joinColumnCode]) {
                $.messager.alert('系统提示', columnCode + '列不包含在查询结果中，请添加后再查询。', 'info');
                return;
            }

            row[joinColumnCode] = rows[joinColumnCode];
        } else {
            /* 获取关联字段 */
            columnCode = masterColumnCode;

            /* 验证当前数组中是否存在关联字段 */
            if (!rows[masterColumnCode]) {
                $.messager.alert('系统提示', columnCode + '列不包含在查询结果中，请添加后再查询。', 'info');
                return;
            }

            row[masterColumnCode] = rows[masterColumnCode];
        }

        /* 不存在就提醒 */
        if (!isColumnCode(row, columnCode)) {
            $.messager.alert('系统提示', columnCode + '列不包含在查询结果中，请添加后再查询。', 'info');
            return;
        }

        $(window.parent.joinTable(masterTableId, masterColumnCode, joinTableId, joinColumnCode, encodeURI(encodeURI(JSON.stringify(row))), tableCode, tableName, type));
    }

    /* 验证当前数据行中是够存在关联字段 */
    function isColumnCode(rows, columnCode) {
        var isColumnCode = false;

        for (var p in rows) {
            if (p == columnCode) {
                isColumnCode = true;
                break;
            }
        }

        return isColumnCode;
    }


    /* 查看当前主表的全部列 */
    function queryAllColumn() {
        var obj = $.parseJSON(metaQuery.script);
        var columns = obj.columns;

        /* 把table对象转化为json字符串 */
        var tableJson = JSON.stringify(obj.tables);

        /* 后台取出对应的所有列 */
        $.mask.post('${base}/sys/metaColumn/getAllColumns', {tables: tableJson}, function (data) {
            $.each(data, function (index, col) {
                if (!isExistColumns(columns, col.expr)) {
                    var data = {
                        type: col.columnType,
                        code: col.columnCode,
                        name: (col.columnName) ? col.columnName : '',
                        expr: col.expr
                    };
                    columns.push(data);
                }
            });


            metaQuery.script = JSON.stringify(obj);

            /* 在当前也查询 */
            query('init');

        });
    }

    function setPageSize(size) {
        pageSize = size;
        $('.page-size').html(pageSize);
        query('init');
    }
</script>
</body>
</html>