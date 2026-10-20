<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>选择排序</title>
    <style type="text/css">
        #iboxTable {
            margin-bottom: 0px;
        }

        .ibox-content {
            padding-bottom: 0px;
        }
    </style>
</head>
<body class="gray-bg" style="overflow: hidden">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxTable" title="" col="12" collapsible="false">
            <bf:form id="queryForm" validation="true">
                <bf:formGroup>
                    <bc:col col="6">
                        <div class="col-xs-5 pull-right" style="padding-right: 0px;">
                            <input id="searchTextL" name="searchTextL" placeholder="搜索" class="form-control">
                        </div>
                        <bc:buttonGroup>
                            <bc:button tooltip="全部右移" icon="forward" cls="moveall" click="moveAllToRight()"/>
                            <bc:button tooltip="右移" icon="chevron-right" click="moveToRight()"/>
                        </bc:buttonGroup>
                        <div style="overflow-y: auto; overflow-x: hidden; max-height: 400px;">
                            <bc:table id="leftColumns" idField="" sidePagination="client" url="" showRefresh="false"
                                      showColumns="false" pagination="false">
                                <bc:column field="code" title="列名" width="100"/>
                                <bc:column field="name" title="中文名" width="100"/>
                            </bc:table>
                        </div>
                    </bc:col>
                    <bc:col col="6">
                        <div class="col-xs-5 pull-right" style="padding-right: 0px;">
                            <input id="searchTextR" name="searchTextR" placeholder="搜索" class="form-control">
                        </div>
                        <bc:buttonGroup>
                            <bc:button tooltip="移除" icon="chevron-left" click="moveToLeft()"/>
                            <bc:button tooltip="全部移除" icon="backward" click="moveAllToLeft()"/>
                        </bc:buttonGroup>
                        <div style="overflow-y: auto; overflow-x: hidden; max-height: 400px;">
                            <bc:table id="rightColumns" idField="" sidePagination="client" url="" showRefresh="false"
                                      showColumns="false"
                                      pagination="false">
                                <bc:column field="code" title="列名" width="100"/>
                                <bc:column field="name" title="中文名" width="100"/>
                                <bc:column field="order" title="排序规则" width="100" formatter="formatColumnType"/>
                            </bc:table>
                        </div>
                    </bc:col>
                </bf:formGroup>
            </bf:form>
            <div class="row" align="center">
                <bc:button id="btnSave" type="primary" text="确定" click="btnSave()"/>
                <bc:button id="btnBack" text="关闭">
                    <be:event>
                        $.messager.hide('wSelectOrderColumns');
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
    var rCols = []; //右边表格的列
    var countOrder = 0; //最多选择多少个字段
    var selectedRow; // 排序规则选中列

    $(document).ready(function () {
        /* 设置参数 */
        var args = $.messager.args('wSelectOrderColumns');
        var selected = args.selected;
        countOrder = args.countOrder;
        cols = copyArray(args.orders);

        /* 当没有选中有列，则左边为全部列 */
        if ((!selected) || selected.length == 0) {
            lCols = copyArray(args.orders);
        } else {
            /*右边数据*/
            rCols = copyArray(selected);
            /*左边数据*/
            lCols = arr_dive_(cols, rCols);
        }

        /* 设置样式 */
        $('.bs-bars').attr('style', 'margin-top: 0px;');
        $('#iboxTable').find('div .panel-body').attr('style', 'padding-top: 0px;');

        /*加载页面数据*/
        $('#leftColumns').bootstrapTable('append', lCols);
        $('#rightColumns').bootstrapTable('append', rCols);

        /* 自动搜索 */
        $('#searchTextL').bind('input propertychange', function () {
            setTimeout(doLSearch, 500);
        });
        $('#searchTextR').bind('input propertychange', function () {
            setTimeout(doRSearch, 500);
        });
    });

    /* 显示排序*/
    function formatColumnType(value, row, index) {
        var html = '<div class="text-center">';
        if (value == 'DESC') {
            html += '<a class="order-down" onClick="clickOrder(' + index + ')" title="降序"><i class="fa fa-long-arrow-down"></i></a>';
            html += '<a class="order-up" onClick="clickOrder(' + index + ')" title="升序" style="display: none;"><i class="fa fa-long-arrow-up"></i></a>';
        } else {
            html += '<a class="order-down" onClick="clickOrder(' + index + ')" title="降序" style="display: none;"><i class="fa fa-long-arrow-down"></i></a>';
            html += '<a class="order-up" onClick="clickOrder(' + index + ')" title="升序"><i class="fa fa-long-arrow-up"></i></a>';
        }
        html += '</div>';
        return html;
    }

    function clickOrder(index) {

        var row = rCols[index];
        var ups = $('#rightColumns').find('.order-up');
        var downs = $('#rightColumns').find('.order-down');

        if (row.order == 'DESC') {
            $(ups[index]).show();
            $(downs[index]).hide();
            row.order = 'ASC';
        } else {
            $(ups[index]).hide();
            $(downs[index]).show();
            row.order = 'DESC';
        }
        $('#rightColumns').bootstrapTable('updateRow', {index: index, row: row});
        $('#rightColumns').bootstrapTable('check', index);
    }

    /* 左页面查询 */
    function doLSearch(value) {
        var value = $('#searchTextL').val();
        doSearchData('leftColumns', lCols, ['code', 'name'], value);
    }

    /* 右页面查询 */
    function doRSearch(value) {
        var value = $('#searchTextR').val();
        doSearchData('rightColumns', rCols, ['code', 'name'], value);
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


    /* 全部右移*/
    function moveAllToRight() {
        /* 判断左边是否还有数据，没有就不作操作 */
        if (lCols.length > 0) {
            var rows = $('#leftColumns').bootstrapTable('getData');
            /*循环处理右边数据*/
            $.each(rows, function (index, row) {
                moveData = {
                    code: row.code,
                    name: row.name,
                    order: 'ASC'
                };
                rCols.push(moveData);
            });
            $('#rightColumns').bootstrapTable('load', rCols);

            /*清空左边数据*/
            lCols = arr_dive_(lCols, rows);
            $('#leftColumns').bootstrapTable('removeAll');
        } else {
            $.messager.toastr('系统提示', '左边没有可以移动的列。', 'info');
        }
    }

    /* 全部左移 */
    function moveAllToLeft() {

        if (rCols.length > 0) {
            var rows = $('#rightColumns').bootstrapTable('getData');

            /*清空右边的列*/
            rCols = arr_dive_(rCols, rows);
            $('#rightColumns').bootstrapTable('removeAll');

            /*显示左边的列*/
            var array = arr_dive_(cols, rCols);
            lCols = copyArray(array);
            $('#leftColumns').bootstrapTable('load', lCols);
        } else {
            $.messager.toastr('系统提示', '右边没有可以移动的列。', 'info');
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
            $('#rightColumns').bootstrapTable('remove', {field: 'code', values: [moveData['code']]});
            rCols = arr_dive_(rCols, [moveData]);
        } else {
            $.messager.toastr('系统提示', '请先选择需要移动的列。', 'info');
        }
    }

    /* 逐个右移 */
    function moveToRight() {
        var moveData = $('#leftColumns').bootstrapTable('getSelections');
        /* 判断是否选择数据 */
        if (moveData.length > 0) {
            moveData = moveData[0];
            /*重新构建对象*/
            var moveNewData = {
                code: moveData.code,
                name: moveData.name,
                order: 'ASC'
            };
            /*添加到右边*/
            rCols = rCols.concat(moveNewData);
            $('#rightColumns').bootstrapTable('append', moveNewData);

            /*移除左边的列*/
            $('#leftColumns').bootstrapTable('remove', {field: 'code', values: [moveData['code']]});
            lCols = arr_dive_(lCols, [moveData]);
        } else {
            $.messager.toastr('系统提示', '请先选择需要移动的列。', 'info');
        }
    }


    /* 确定 */
    function btnSave() {
        rCols = $('#rightColumns').bootstrapTable('getData');

// 	if(rCols.length < 1)
// 	{
// 		$.messager.toastr('系统提示', '请先选排序字段。', 'info');
// 		return;
// 	}

        if (rCols.length > countOrder) {
            $.messager.toastr('系统提示', '排序字段数量不能超过' + countOrder + '个。', 'info');
            return;
        }
        $.messager.args('wSelectOrderColumns').callback(rCols);
        $.messager.hide('wSelectOrderColumns');
    }

    /* ==================方法工具================== */
    /* 数组深度拷贝 */
    function copyArray(arr) {
        var newArr = [];
        $.each(arr, function (index, val) {
            newArr.push(val);
        });
        return newArr;
    }

    /* 比较两个数组，去掉第一个数组中的第二个数组中存在的对象 */
    function arr_dive_(aArr, bArr) {   //第一个数组减去第二个数组
        if (bArr.length == 0) {
            return aArr;
        }
        var result = [];
        $.each(aArr, function (aIndex, a) {
            var isHave = false;
            $.each(bArr, function (bIndex, b) {
                if (a.code == b.code) {
                    isHave = true;
                }
            });
            if (!isHave) {
                result.push(a);
            }
        });
        return result;
    }
</script>
</body>
</html>