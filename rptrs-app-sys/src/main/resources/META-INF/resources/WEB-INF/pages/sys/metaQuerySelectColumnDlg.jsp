<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>选择字段</title>
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
                <bf:formGroup id="showTableFg"/>
                <bf:formGroup>
                    <bc:col col="6">
                        <div class="col-xs-4 pull-right" style="padding-right: 0px;">
                            <input id="searchTextL" name="searchTextL" placeholder="搜索" class="form-control">
                        </div>
                        <bc:buttonGroup>
                            <bc:button tooltip="全部右移" icon="forward" cls="moveall" click="moveAllToRight()"/>
                            <bc:button tooltip="右移" icon="chevron-right" click="moveToRight()"/>
                        </bc:buttonGroup>
                        <div style="overflow-y: auto; overflow-x: hidden; max-height: 400px;">
                            <bc:table id="leftColumns" idField="columnId" sidePagination="client" url=""
                                      showRefresh="false" showColumns="false"
                                      pagination="false">
                                <bc:column field="alias" title="表名" width="100" formatter="tableNameFormat"/>
                                <bc:column field="columnCode" title="列名" width="100" formatter="isKeyFormat"/>
                                <bc:column field="columnName" title="中文名" width="100"/>
                                <bc:column field="dataType" title="类型" width="100"/>
                            </bc:table>
                        </div>
                    </bc:col>
                    <bc:col col="6">
                        <div class="col-xs-4 pull-right" style="padding-right: 0px;">
                            <input id="searchTextR" name="searchTextR" placeholder="搜索" class="form-control">
                        </div>
                        <bc:buttonGroup>
                            <bc:button tooltip="移除" icon="chevron-left" click="moveToLeft()"/>
                            <bc:button tooltip="全部移除" icon="backward" click="moveAllToLeft()"/>
                        </bc:buttonGroup>
                        <bc:button id="customRowBtn" text="添加自定义列" click="editCustomRow()"/>
                        <div style="overflow-y: auto; overflow-x: hidden; max-height: 400px;">
                            <bc:table id="rightColumns" idField="columnId" sidePagination="client" url=""
                                      showRefresh="false" showColumns="false"
                                      pagination="false">
                                <bc:column field="alias" title="表名" width="100" formatter="tableNameFormat"/>
                                <bc:column field="columnCode" title="列名" width="100" formatter="isKeyFormat"/>
                                <bc:column field="columnName" title="中文名" width="100"/>
                                <bc:column field="dataType" title="类型" width="100"/>
                                <bc:column field="columnType" title="操作" width="100" formatter="formatColumnType"/>
                            </bc:table>
                        </div>
                    </bc:col>
                </bf:formGroup>
            </bf:form>
            <div class="row" align="center" style="padding-top: 15px;">
                <bc:button id="btnSave" type="primary" text="确定" click="btnSave()"/>
                <bc:button id="btnBack" text="关闭">
                    <be:event>
                        $.messager.hide('wSelectColumns');
                    </be:event>
                </bc:button>
            </div>
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
    var selected = ''; //编辑时，已选择的列
    var maxRows = -1; //右边选择的列最多只能选择的数量
    var editCustom = ''; //是否可以增加自定义列
    var tableId = ''; //主表Id
    var relationColumns = []; //主表关联字段
    var columnMax = 20; //警告最大字段数
    var selectedRow; // 操作列中，选择的行数据

    /*定义列类型*/
    var COLUMN_TYPE_CUSTOM = 'C', COLUMN_TYPE_ORIGINAL = 'O';

    /*定义公式类型*/
    var EXPR_TYPE_BASE = 'B';

    $(document).ready(function () {
        var args = $.messager.args('wSelectColumns');
        tables = args.tables;
        selected = args.selected;
        editCustom = args.editCustom;

        /* 传入参数是否存在选择记录数量限制 */
        if (args.maxRows) {
            maxRows = args.maxRows;
        }

        /* 当不能编辑自定义列时，editCustom为'N'。 */
        if (editCustom == 'N') {
            $('#customRowBtn').hide();
        }

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
                    rCols = rCols.concat(selected);
                    lCols = arr_dive(cols, rCols);
                }

                $('#leftColumns').bootstrapTable('load', lCols.slice(0));
                $('#rightColumns').bootstrapTable('load', rCols.slice(0));
            }
        });

        /* 后台取出所有主表关联字段 */
        $.ajax({
            type: 'GET',
            url: '${base}/sys/metaColumn/getRelationColumns',
            data: {tableId: tableId},
            dataType: 'json',
            success: function (data) {
                relationColumns = data;
            }
        });

        /* 当表的个数少于两个的时候不显示表名 */
        if (tables.length < 2) {
            $('#leftColumns').bootstrapTable('hideColumn', 'alias');
            $('#rightColumns').bootstrapTable('hideColumn', 'alias');
        }
        /* 当没有自定义列的时候，不显示操作列 */
        if (editCustom == 'N') {
            $('#rightColumns').bootstrapTable('hideColumn', 'columnType');
        }

        /* 自动搜索 */
        $('#searchTextL').bind('input propertychange', function () {
            setTimeout(doLSearch, 500);
        });
        $('#searchTextR').bind('input propertychange', function () {
            setTimeout(doRSearch, 500);
        });
    }

    /* 格式化是否为自定义列 */
    function formatColumnType(value, row, index) {

        selectedRow = row;
        if (value == COLUMN_TYPE_CUSTOM) {
            var html = '<div class="text-center">';
            html += '<a class="editBtn" onClick="editHandler(' + index + ')" title="编辑"><i class="fa fa-edit"></i></a>&nbsp;';
            html += '<a class="removeBtn" onClick="removeHandler(' + index + ')" title="移除"><i class="fa fa-trash"></i></a>';
            html += '</div>';
            return html;
        }
    }

    /* 删除行 */
    function removeHandler(index) {
        $.messager.confirm('提示', '是否确定删除该自定义列吗？', function (result) {
            if (result) {
                $('#rightColumns').bootstrapTable('check', index);
                rCols = arr_dive(rCols, [selectedRow]);
                $('#rightColumns').bootstrapTable('removeAll');
                $('#rightColumns').bootstrapTable('append', rCols);
            }
        });
    }

    /* 编辑自定义列 */
    function editHandler(index) {
        $('#rightColumns').bootstrapTable('check', index);
        var row = $('#rightColumns').jTable('getCheckData');
        editCustomRow('edit', selectedRow, index);
    }

    /* 添加自定义列 */
    function editCustomRow(opt, customRow, rowIndex) {
        var mainIndex = -1;
        if (customRow) {
            mainIndex = getIndexByData(rCols, ['columnCode'], customRow);
        }
        var data = {
            tables: tables,
            type: EXPR_TYPE_BASE,
            customRow: customRow,
            opt: opt,
            rowIndex: rowIndex,
            mainIndex: mainIndex,
            rCols: rCols,
            options: ''
        };

        $.messager.modal({
            winId: 'wEditCustomRow',
            width: 800,
            height: 220,
            title: '自定义字段',
            url: '${base}/sys/metaQuery/customColumn',
            args: {callback: cusRowCallBack, data: data}
        });
    }

    /* 自定义列的的回调函数 */
    function cusRowCallBack(data) {
        /* 组装自定义列对象 */
        var cusRow = {
            columnType: COLUMN_TYPE_CUSTOM,
            columnCode: data.columnCode,
            columnName: data.columnName,
            expr: data.expr
        };
        /* 如果是编辑状态，就把原来的数据修改 */
        if (data.opt == 'edit') {
            var rowIndex = data.rowIndex;
            rCols[data.mainIndex] = cusRow;
            /* 获得当前显示的所有数据 */
            var remainData = $('#rightColumns').bootstrapTable('getData');
            remainData[rowIndex] = cusRow;
            /* 刷新编辑列 */
            $('#rightColumns').bootstrapTable('updateRow', {index: rowIndex, row: cusRow});
        } else {
            rCols.push(cusRow);
            $('#rightColumns').bootstrapTable('append', cusRow);
        }
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
            $('#' + tableId).bootstrapTable('removeAll');
            $('#' + tableId).bootstrapTable('append', metaDNewata);
        } else {
            /*没有输入任何值，直接返回原始数组*/
            $('#' + tableId).bootstrapTable('removeAll');
            $('#' + tableId).bootstrapTable('append', initialData);
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
// 		$('#' + tableId).bootstrapTable('load', metaDNewata);
            $('#' + tableId).bootstrapTable('removeAll');
            $('#' + tableId).bootstrapTable('append', metaDNewata);
        } else {
            /*没有输入任何值，直接返回原始数组*/
            $('#' + tableId).bootstrapTable('removeAll');
            $('#' + tableId).bootstrapTable('append', initialData.slice(0));
        }
    }

    /* 格式化表名 */
    function tableNameFormat(value, row, index) {
        if (value == 'T1') {
            return '主表';
        } else if (value) {
            return '关联表-' + value
        }
        return '';
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
        var rows = $('#leftColumns').bootstrapTable('getData');
        /* 判断左边是否还有数据，没有就不作操作 */
        if (rows.length > 0) {
            /* 判断当前列数是否大于30列 */
            var length = lCols.length + rows.length;
            if (getOs() != 'Firefox' && getOs() != 'Chrome' && length > columnMax) {
                $.messager.confirm('系统提示', '显示列数过多，可能导致页面响应时间过长，甚至无法显示，是否继续？', function (result) {
                    if (result) {

                        lCols = arr_dive(lCols, rows);
                        var repeat = [];

                        /* 遍历整个数组，判断右边是否存在相同的字段，如果存在就只能通过自定义列增加了 */
                        $.each(rows, function (index, value) {
                            if (isExistCol(value.columnCode)) {
                                repeat.push(value);
                            } else {
                                rCols.push(value);
                            }
                        });
                        rows = repeat;

                        $('#rightColumns').bootstrapTable('load', rCols.slice(0));
                        $('#leftColumns').bootstrapTable('load', rows);
                        if (rows.length > 0) {
                            lCols = lCols.concat(rows);
                            $.messager.toastr('系统提示', '已存在相同的列名，如需增加相同列名，请新增自定义列。', 'info');
                        }
                    }
                });
            } else {
                lCols = arr_dive(lCols, rows);

                var repeat = [];
                /* 遍历整个数组，判断右边是否存在相同的字段，如果存在就只能通过自定义列增加了 */
                $.each(rows, function (index, value) {
                    if (isExistCol(value.columnCode)) {
                        repeat.push(value);
                    } else {
                        rCols.push(value);
                    }
                });
                rows = repeat;

                $('#rightColumns').bootstrapTable('removeAll');
                $('#leftColumns').bootstrapTable('removeAll');
                $('#rightColumns').bootstrapTable('append', rCols.slice(0));
                $('#leftColumns').bootstrapTable('append', rows);
                if (rows.length > 0) {
                    lCols = lCols.concat(rows);
                    $.messager.alert('系统提示', '已存在相同的列名，如需增加相同列名，请新增自定义列。', 'info');
                }
            }
        }
    }

    /* 全部左移 */
    function moveAllToLeft() {
        var selectedRows = $('#rightColumns').bootstrapTable('getData');
        /* 自定义列*/
        var cRows = [];
        /* 原始列 */
        var oRows = [];
        /* 只能移动原始列,这里取出原始列 */
        for (var k = 0; k < selectedRows.length; k++) {
            var row = selectedRows[k];

            if (isExistRelationColumns(row.expr)) {
                cRows.push(row);
            } else {
                if (COLUMN_TYPE_CUSTOM == row.columnType) {
                    cRows.push(row);
                } else {
                    oRows.push(row);
                }
            }
        }
        rCols = arr_dive(rCols, oRows);
        lCols = lCols.concat(oRows);

        $('#rightColumns').bootstrapTable('removeAll');
        $('#leftColumns').bootstrapTable('removeAll');
        $('#rightColumns').bootstrapTable('append', cRows);
        $('#leftColumns').bootstrapTable('append', lCols.slice(0));
    }


    /* 逐个左移 */
    function moveToLeft() {
        var moveData = $('#rightColumns').bootstrapTable('getSelections');
        var remainData = $('#rightColumns').bootstrapTable('getData');

        /* 判断是否选择数据 */
        if (moveData.length > 0) {
            moveData = moveData[0];
            if (isExistRelationColumns(moveData.expr)) {
                $.messager.alert('系统提示', '关联字段不能被移除。', 'info');
                return;
            }

            /* 判断是否为自定义列，如果是就不能移动 */
            if (moveData.columnType == COLUMN_TYPE_CUSTOM) {
                $.messager.alert('系统提示', '不能移动自定义字段。', 'info');
                return;
            }

            rCols = arr_dive(rCols, [moveData]);
            lCols.push(moveData);

            $('#leftColumns').bootstrapTable('append', moveData);
            $('#rightColumns').bootstrapTable('load', rCols);
        }
    }

    /* 逐个右移 */
    function moveToRight() {
        var moveData = $('#leftColumns').bootstrapTable('getSelections');
        var remainData = $('#leftColumns').bootstrapTable('getData');
        /* 判断是否选择数据 */
        if (moveData.length > 0) {
            moveData = moveData[0];
            /* 判断左边是否已经存在相同的列名了，如果存在就只能通过自定义列来给需要添加的列增加一个别名 */
            if (isExistCol(moveData.columnCode)) {
                $.messager.alert('系统提示', '已存在相同的列名，如需增加相同列名，请新增自定义列。', 'info');
                return;
            }
            var rightCols = $('#rightColumns').bootstrapTable('getData');

            /* 判断当前列数是否大于列 */
            if (getOs() != 'Firefox' && getOs() != 'Chrome' && rightCols.length > columnMax) {
                $.messager.confirm('系统提示', '显示列数过多，可能导致页面响应时间过长，甚至无法显示,是否继续？', function (result) {
                    if (result) {
                        $('#rightColumns').bootstrapTable('append', [moveData]);
                        $('#leftColumns').bootstrapTable('remove', {field: 'columnId', values: [moveData['columnId']]});

                        lCols = arr_dive(lCols, [moveData]);
                        rCols.push(moveData);
                    }
                });
            } else {
                $('#rightColumns').bootstrapTable('append', moveData);
                $('#leftColumns').bootstrapTable('remove', {field: 'columnId', values: [moveData['columnId']]});

                lCols = arr_dive(lCols, [moveData]);
                rCols.push(moveData);
            }
        }
    }

    /* 保存 */
    function btnSave() {

        /* 当没有选择列的时候给出提示 */
        if (rCols.length < 1) {
            $.messager.alert('系统提示', '请先选择列。', 'info');
            return;
        }
        if (maxRows != -1) {

            /* 当选择列的数量超出限制时候给出提示 */
            if (rCols.length > maxRows) {
                var message = '选择的列数量不能超过' + maxRows + '条, 请检查。'
                $.messager.toastr('系统提示', message, 'info');
                return;
            }
        }

        $.messager.args('wSelectColumns').callback(formatShortColumns(rCols));
        $.messager.hide('wSelectColumns');
    }

    /* ==================方法工具================== */

    /* 查询对象在数组中的位置 ， array为待查询的数组，properties为数组中对象是属性数组，可以是多个属性, row为具体对象*/
    function getIndexByData(array, properties, row) {
        var index = -1;
        /* 循环整个数组。判断是否存在是否存在有这个对象 */
        $.each(array, function (ind, value) {
            var flag = true;
            /* 遍历整个属性数组，要每个都相等才能为真 */
            $.each(properties, function (i, property) {
                /* 只要存在一个不等就立即跳出循环 */
                if (value[property] != row[property]) {
                    flag = false;
                    return false;
                }
            });
            /* 当为真时，表示存在这样的数据，给出该对象的编号 */
            if (flag) {
                index = ind;
            }
        });
        return index;
    }

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
                    bArr[bIndex] = a;
                }
            });
            if (!isHave) {
                result.push(a);
            }
        });
        return result;
    }

    /*获取当前浏览器类型*/
    function getOs() {
        var osObject = '';

        var explorer = navigator.userAgent;
        /*ie*/
        if (explorer.indexOf("MSIE") >= 0) {
            osObject = 'ie';
        }
        /*firefox*/
        else if (explorer.indexOf("Firefox") >= 0) {
            osObject = 'Firefox';
        }
        /*Chrome*/
        else if (explorer.indexOf("Chrome") >= 0) {
            osObject = 'Chrome';
        }
        /*Opera*/
        else if (explorer.indexOf("Opera") >= 0) {
            osObject = 'Opera';
        }
        /*Safari*/
        else if (explorer.indexOf("Safari") >= 0) {
            osObject = 'Safari';
        }
        /*Netscape*/
        else if (explorer.indexOf("Netscape") >= 0) {
            osObject = 'Netscape';
        }

        return osObject;
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

    /*验证该字段是否是关联字段*/
    function isExistRelationColumns(columnCode) {
        var isColumn = false;
        var alias = 'T1';
        $.each(relationColumns, function (index, table) {
            var expr = alias + '.' + table.columnCode;
            if (expr == columnCode) {
                return isColumn = true;
            }
        });

        return isColumn;
    }

    /*格式化数组字段简写*/
    function formatShortColumns(cols) {
        var newCols = [];
        $.each(cols, function (index, col) {
            var data = {
                type: col.columnType,
                code: col.columnCode,
                name: (col.columnName) ? col.columnName : '',
                expr: col.expr
            };
            newCols.push(data);
        });
        return newCols;
    }
</script>
</body>
</html>