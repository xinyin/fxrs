<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>选择汇总字段</title>
    <style type="text/css">
        #iboxColumns {
            margin-bottom: 0px;
        }

        .ibox-content {
            padding-bottom: 0px;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxColumns" title="" col="12" collapsible="false">
            <bf:form id="tableForm" method="post" validation="false">
                <bf:formGroup>
                    <div class="col-xs-9" id="showTableFg"></div>
                    <bc:col col="3">
                        <bc:button id="btnSave" type="primary" text="确定" click="btnSave()"/>
                        <bc:button id="btnBack" text="关闭">
                            <be:event>
                                $.messager.hide('wselectGroupColumns');
                            </be:event>
                        </bc:button>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:col col="5">
                        <div class="col-xs-4 pull-right" style="padding-right: 0px;">
                            <input id="searchTextL" name="searchTextL" placeholder="搜索" class="form-control">
                        </div>
                        <bc:buttonGroup>
                            <bc:button tooltip="全部右移" icon="forward" cls="moveall" click="moveAllToRight()"/>
                            <bc:button tooltip="右移" icon="chevron-right" click="moveToRight()"/>
                        </bc:buttonGroup>
                        <div style="overflow-y: auto; overflow-x: hidden; max-height: 400px;">
                            <bc:table id="leftColumns" idField="" sidePagination="client" url="" showRefresh="false"
                                      showColumns="false"
                                      pagination="false">
                                <bc:column field="tableCode" title="表名" width="100" formatter="formatTableName"/>
                                <bc:column field="columnCode" title="列名" width="100" formatter="isKeyFormat"/>
                                <bc:column field="columnName" title="中文名" width="100"/>
                            </bc:table>
                        </div>
                    </bc:col>
                    <bc:col col="7">
                        <div class="col-xs-4 pull-right" style="padding-right: 0px;">
                            <input id="searchTextR" name="searchTextR" placeholder="搜索" class="form-control">
                        </div>
                        <bc:buttonGroup>
                            <bc:button tooltip="移除" icon="chevron-left" click="moveToLeft()"/>
                            <bc:button tooltip="全部移除" icon="backward" click="moveAllToLeft()"/>
                        </bc:buttonGroup>
                        <div style="overflow-y: auto; overflow-x: auto; max-height: 400px;">
                            <table id="rightColumns"/>
                        </div>
                    </bc:col>
                </bf:formGroup>
            </bf:form>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    var cols = []; //全部的列
    var lCols = []; //左边表格的列
    var rCols = [];  //右边表格的列
    var tables = ''; //选择的表
    var groupCount = 0; //选择分组汇总字段
    var selected;

    $(document).ready(function () {
        var args = $.messager.args('wselectGroupColumns');
        tables = args.tables;
        groupCount = args.groupCount;
        selected = args.selected;

        /*初始化页面数据和布局 */
        init();
    });

    function init() {
        $('.fixed-table-toolbar').hide();
        $('.queryFg').attr('style', 'margin-bottom: 0px;');
        $('.paddingRight').parent('div').attr('style', 'padding-right: 0px;');
        $('.marginRight').parent('div').attr('style', 'margin-right: 15px; padding-right: 0px;');

        /* 组装所有表用于显示 */
        var tablesShow = '';
        $.each(tables, function (index, table) {
            var tableName = '';
            if (table.alias == 'T1') {
                tableId = table.id;
                tableName = '（主表）' + table.name + '（' + table.code + '）';
            } else {
                tableName = '（关联表-' + table.alias + '）' + table.name + '（' + table.code + '）';
            }
            tablesShow += '<div class="row" style="padding-left: 15px;">';
            tablesShow += '<div class="col-xs-1" style="width: 40px;"><div class="checkbox checkbox-default checkbox-inline">';
            tablesShow += '<input type="checkbox" name="tables" class="form-control" value="' + table.alias + '" onclick="checkTable(this)" checked="checked">';
            tablesShow += '<label></label></div></div>';
            tablesShow += '<label class="control-label" style="text-align:left">' + tableName + '</label>';
            tablesShow += '</div>';
        });
        $('#showTableFg').html(tablesShow);

        /* 后台取出对应的所有列 */
        var tableJson = JSON.stringify(tables);
        $.ajax({
            type: 'GET',
            url: '${base}/sys/metaColumn/getAllColumns',
            data: {tables: tableJson},
            dataType: 'json',
            success: function (data) {
                cols = cols.concat(data);
                if ((!selected) || selected.length == 0) //当没有选中有列，则左边为全部列
                {
                    lCols = cols.slice(0);
                } else {
                    /*右边数据*/
                    $.each(selected, function (index, row) {
                        var rowCode = row.expr;
                        $.each(cols, function (index, cols) {
                            var colsCode = cols.alias + '.' + cols.columnCode;
                            if (rowCode == colsCode) {
                                var tableCode = formatTableName('', cols);
                                var obj = {
                                    columnId: cols.columnId,
                                    columnCode: cols.columnCode,
                                    columnName: cols.columnName,
                                    expr: row.expr,
                                    alias: cols.alias,
                                    dataType: cols.dataType,
                                    tableCode: tableCode,
                                    dc: row.DC,
                                    sum: row.SUM,
                                    ds: row.DS,
                                    max: row.MAX,
                                    min: row.MIN,
                                    avg: row.AVG
                                };
                                rCols.push(obj);
                            }
                        });
                    });
                    /*左边数据*/
                    lCols = arr_dive(cols, rCols);
                }
                $('#leftColumns').bootstrapTable('append', lCols);
                $('#rightColumns').bootstrapTable('append', rCols);
            }
        });

        $('#rightColumns').bootstrapTable({
            idField: "columnCode",
            pagination: false,
            search: false,
            showRefresh: false,
            showColumns: false,
            clickToSelect: true,
            singleSelect: true,
            sidePagination: 'client',
            columns: [[
                {radio: true, rowspan: 2, width: 100},
                {field: "tableCode", title: "表名", rowspan: 2, halign: "center", align: "center", width: 100},
                {field: "columnCode", title: "列名", rowspan: 2, halign: "center", align: "center", width: 100},
                {field: "columnName", title: "中文名", rowspan: 2, halign: "center", align: "center", width: 100},
                {title: "汇总方式", colspan: 6}
            ], [
                {
                    field: 'dc', title: '唯一值计数', width: 250, align: 'center',
                    formatter: function (value, row, index) {
                        return formatColumnType(value, row.columnCode + 'DC', index);
                    }
                },
                {
                    field: 'sum', title: '求和', width: 120, align: 'center',
                    formatter: function (value, row, index) {
                        return formatColumnType(value, row.columnCode + 'SUM', index);
                    }
                },
                {
                    field: 'ds', title: '唯一值求和', width: 250, align: 'center',
                    formatter: function (value, row, index) {
                        return formatColumnType(value, row.columnCode + 'DS', index);
                    }
                },
                {
                    field: 'max', title: '最大', width: 120, align: 'center',
                    formatter: function (value, row, index) {
                        return formatColumnType(value, row.columnCode + 'MAX', index);
                    }
                },
                {
                    field: 'min', title: '最小', width: 120, align: 'center',
                    formatter: function (value, row, index) {
                        return formatColumnType(value, row.columnCode + 'MIN', index);
                    }
                },
                {
                    field: 'avg', title: '平均值', width: 150, align: 'center',
                    formatter: function (value, row, index) {
                        return formatColumnType(value, row.columnCode + 'AVG', index);
                    }
                }
            ]]
        });

        /* 自动搜索 */
        $('#searchTextL').bind('input propertychange', function () {
            setTimeout(doLSearch, 500);
        });
        $('#searchTextR').bind('input propertychange', function () {
            setTimeout(doRSearch, 500);
        });
    }

    /* 显示排序*/
    function formatColumnType(value, columnCode, index) {

        /*根据只判断当前字段排序方式*/
        var checkbox = '';
        if (value == 'Y') {
            checkbox += '<input type="checkbox" name="' + columnCode + '" value="Y" checked="checked">';
        } else {
            checkbox += '<input type="checkbox" name="' + columnCode + '" value="Y">';
        }
        return checkbox;
    }

    /* 选择表 */
    function checkTable(el) {
        /* 先把原来选择的表取消选中 */
        var chk_value = [];

        $('input[name="tables"]:checked').each(function () {
            chk_value.push($(this).val());
        });
        doSearchByTableCode('leftColumns', lCols, chk_value);
    }

    /* 查询 */
    function doSearchByTableCode(tableId, initialData, value) {
        $('#' + tableId).jTable('clearCheckData');
        /* 输入的条件不为空，进行过滤 */
        if (value.length > 0) {
            /* 获取原数组长度 */
            var lengths = initialData.length;

            /* 新建数组用于筛选后显示 */
            var metaDNewata = new Array();
            $.each(value, function (index, val) {
                /* 筛选符合条件的数据  */
                for (var i = 0; i < lengths; i++) {
                    var tableCode = initialData[i].alias;

                    /* 输入的值包含在tableCode 或者 columnName 把此对象push到数组metaDNewata */
                    if (tableCode == val) {
                        metaDNewata.push(initialData[i]);
                    }
                }
            });

            /* 把metaDNewata load 到 datagrid 中*/
            $('#' + tableId).bootstrapTable('load', metaDNewata);
        } else {
            /*没有输入任何值，直接返回原始数组*/
            $('#' + tableId).bootstrapTable('load', initialData);
        }
    }

    /* 左页面查询 */
    function doLSearch(value) {
        var value = $('#searchTextL').val();
        doSearchData('leftColumns', lCols, ['columnName', 'columnCode'], value);
    }

    /* 右页面查询 */
    function doRSearch(value) {
        var value = $('#searchTextR').val();
        doSearchData('rightColumns', rCols, ['columnName', 'columnCode'], value);
    }

    /* 查询 */
    function doSearchData(tableId, initialData, properties, value) {
        $('#' + tableId).jTable('clearCheckData');
        /* 获取搜索输入的值 */
        value = value.trim();
        /* 输入的条件不为空，进行过滤 */
        if (value) {
            value = value.toUpperCase();
            /* 新建数组用于筛选后显示 */
            var metaDNewata = new Array();
            /* 遍历数据 */
            $.each(initialData, function (index, data) {
                var flag = false;
                /* 遍历数据，对每个数据进行比对 */
                $.each(properties, function (i, key) {
                    var val = data[key];
                    if (val) {
                        if (val.indexOf(value) > -1) {
                            flag = true;
                            return false;
                        }
                    }
                });
                if (flag) {
                    metaDNewata.push(data);
                }
                flag = false;
            });
            /* 把metaDNewata load 到 表格 中*/
            $('#' + tableId).bootstrapTable('load', metaDNewata);
        } else {
            /*没有输入任何值，直接返回原始数组*/
            $('#' + tableId).bootstrapTable('load', initialData.slice(0));
        }
    }

    /* 格式化表名 */
    function formatTableName(value, row) {
        if (row.alias == 'T1') {
            return '主表';
        }
        return '关联表-' + row.alias;
    }

    /* 列主键格式化，是否显示主键图标 */
    function isKeyFormat(value, row, index) {
        if (row.isKey == 'Y') {
            return '<i class="fa fa-key" style="padding-right: 3px;"></i>' + row.columnCode;
        }
        return row.columnCode;
    }

    /* 全部右移*/
    function moveAllToRight() {
        /* 判断左边是否还有数据，没有就不作操作 */
        if (lCols.length > 0) {
            var rows = $('#leftColumns').bootstrapTable('getData');
            /*循环处理右边数据*/
            $.each(rows, function (index, row) {
                var tableCode = formatTableName('', row);
                moveData = {
                    columnId: row.columnId,
                    columnCode: row.columnCode,
                    columnName: row.columnName,
                    dataType: row.dataType,
                    alias: row.alias,
                    tableCode: tableCode
                };
                rCols.push(moveData);
            });
            $('#rightColumns').bootstrapTable('load', rCols);

            /*清空左边数据*/
            lCols = arr_dive(lCols, rows);
            $('#leftColumns').bootstrapTable('removeAll');
        } else {
            $.messager.toastr('系统提示', '左边没有可以移动的列。', 'warning');
        }
    }

    /* 全部左移 */
    function moveAllToLeft() {
        if (rCols.length > 0) {
            var rows = $('#rightColumns').bootstrapTable('getData');

            /*清空右边的列*/
            rCols = arr_dive(rCols, rows);
            $('#rightColumns').bootstrapTable('removeAll');

            /*显示左边的列*/
            var array = arr_dive(cols, rCols);
            lCols = copyArray(array);
            $('#leftColumns').bootstrapTable('load', lCols);
        } else {
            $.messager.toastr('系统提示', '右边没有可以移动的列。', 'warning');
        }
    }


    /* 逐个左移 */
    function moveToLeft() {
        var moveData = $('#rightColumns').bootstrapTable('getSelections');

        /* 判断是否选择数据 */
        if (moveData.length > 0) {
            moveData = moveData[0];
            /*移动到左边*/
            lCols = lCols.concat(moveData);
            $('#leftColumns').bootstrapTable('append', moveData);

            /*更新右边*/
            $('#rightColumns').bootstrapTable('remove', {field: 'columnId', values: [moveData['columnId']]});
            rCols = arr_dive(rCols, [moveData]);
        } else {
            $.messager.toastr('系统提示', '请先选择需要移动的列。', 'warning');
        }
    }

    /* 逐个右移 */
    function moveToRight() {
        var moveData = $('#leftColumns').bootstrapTable('getSelections');
        /* 判断是否选择数据 */
        if (moveData.length > 0) {
            moveData = moveData[0];
            var tableCode = formatTableName('', moveData);
            if (isExistCol(moveData.columnCode)) {
                $.messager.toastr('系统提示', '已选择的列中已经存在该列名。', 'warning');
                return;
            }
            /*重新构建对象*/
            moveNewData = {
                columnId: moveData.columnId,
                columnCode: moveData.columnCode,
                columnName: moveData.columnName,
                dataType: moveData.dataType,
                alias: moveData.alias,
                tableCode: tableCode
            };

            /*添加到右边*/
            rCols = rCols.concat(moveNewData);
            $('#rightColumns').bootstrapTable('append', [moveNewData]);

            /*移除左边的列*/
            lCols = arr_dive(lCols, [moveData]);
            $('#leftColumns').bootstrapTable('load', lCols);
        } else {
            $.messager.toastr('系统提示', '请先选择需要移动的列。', 'warning');
        }
    }

    /* 保存 */
    function btnSave() {
        rCols = $('#rightColumns').bootstrapTable('getData');
        if (rCols.length > groupCount) {
            $.messager.toastr('系统提示', '汇总字段只能选择' + groupCount + '个。', 'warning');
            return;
        }

        /*过滤掉没有勾选的，并组装数组*/
        var newGroups = checkGroups(rCols);

        $.messager.args('wselectGroupColumns').callback(newGroups);
        $.messager.hide('wselectGroupColumns');
    }

    /* ==================方法工具================== */

    /* 比较两个数组，去掉第一个数组中的第二个数组中存在的对象 */
    function arr_dive(aArr, bArr) { //第一个数组减去第二个数组
        if (bArr.length == 0) {
            return aArr;
        }
        var result = [];
        $.each(aArr, function (aIndex, a) {
            var isHave = false;
            $.each(bArr, function (bIndex, b) {
                if (a.expr == b.expr) {
                    isHave = true;
//         		bArr[bIndex] = a;
                }
            });
            if (!isHave) {
                result.push(a);
            }
        });
        return result;
    }

    /* 判断列名是否已经存在 ，存在返回true，不存在返回false*/
    function isExistCol(value) {
        var isExist = false;
        /* 遍历整个已选择的数组，查看是否已经存在该列名 */
        $.each(rCols, function (index, col) {
            if (col.columnCode == value) {
                return isExist = true;
            }
        });
        return isExist;
    }

    /*根据checkbox名字获取值，返回value*/
    function checkbox(checkName) {

        var value = $('input[name="' + checkName + '"]:checked ').val();
        if (value) {
            return value;
        } else {
            return 'N';
        }
    }

    /*过滤掉选择了，但是没有勾选的空数组*/
    function checkGroups(selectCols) {
        var newGroups = new Array();
        $.each(selectCols, function (index, row) {
            if (checkbox(row.columnCode + 'DC') == 'Y' || checkbox(row.columnCode + 'SUM') == 'Y'
                || checkbox(row.columnCode + 'DS') == 'Y' || checkbox(row.columnCode + 'MAX') == 'Y'
                || checkbox(row.columnCode + 'MIN') == 'Y' || checkbox(row.columnCode + 'AVG') == 'Y') {
                var obj = {
                    code: row.columnCode,
                    name: (row.columnName) ? row.columnName : '',
                    expr: row.alias + '.' + row.columnCode,
                    DC: checkbox(row.columnCode + 'DC'),
                    SUM: checkbox(row.columnCode + 'SUM'),
                    DS: checkbox(row.columnCode + 'DS'),
                    MAX: checkbox(row.columnCode + 'MAX'),
                    MIN: checkbox(row.columnCode + 'MIN'),
                    AVG: checkbox(row.columnCode + 'AVG')
                };

                newGroups.push(obj);
            }
        });
        return newGroups;
    }
</script>
</body>
</html>