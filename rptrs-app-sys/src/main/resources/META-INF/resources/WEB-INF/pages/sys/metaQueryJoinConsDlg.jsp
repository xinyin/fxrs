<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>选择关联表</title>
    <style type="text/css">
        #iboxTable {
            margin-bottom: 0px;
        }

        .ibox-content {
            padding-bottom: 0px;
            height: 610px;
        }

        .panel-body {
            padding-bottom: 0px;
        }
    </style>
</head>
<body class="gray-bg" style="overflow: hidden">
<div class="row">
    <bc:ibox id="iboxTable" title="" col="12" collapsible="false">
        <div class="row">
            <bc:col col="6">
                <bf:form id="queryForm" validation="true">
                    <bf:formGroup cls="queryFg">
                        <bf:text id="codeMaster" name="" col="4" placeholder="列名" cls="paddingRight"/>
                        <bf:text id="nameMaster" name="" col="4" placeholder="中文名" cls="marginRight"/>
                        <bc:button type="primary" icon="search" tooltip="查询" click="queryMaster()"/>
                        <bc:button icon="refresh" tooltip="重置" click="resetMaster()"/>
                    </bf:formGroup>
                </bf:form>
                <div style="overflow-y: auto; overflow-x: hidden; height: 260px;">
                    <bc:table id="masterTable" idField="columnId" url="${base}/sys/metaColumn"
                              showRefresh="false" showColumns="false" pagination="true">
                        <bc:column field="columnCode" title="列名" width="100" formatter="isKeyFormat"/>
                        <bc:column field="columnName" title="中文名" width="100"/>
                        <bc:column field="dataType" title="类型" width="100"/>
                    </bc:table>
                </div>
            </bc:col>
            <bc:col col="6">
                <bf:form id="joinForm" name="joinForm" validation="true">
                    <bf:formGroup cls="queryFg">
                        <bf:text id="codeJoin" name="" col="4" placeholder="列名" cls="paddingRight"/>
                        <bf:text id="nameJoin" name="" col="4" placeholder="中文名" cls="marginRight"/>
                        <bc:button id="searchBtn" type="primary" icon="search" tooltip="查询" click="queryJoin()"/>
                        <bc:button id="resetBtn" icon="refresh" tooltip="重置" click="resetJoin()"/>
                    </bf:formGroup>
                </bf:form>
                <div style="overflow-y: auto; overflow-x: hidden; height: 260px;">
                    <bc:table id="joinTable" idField="columnId" url="${base}/sys/metaColumn"
                              showRefresh="false" showColumns="false" pagination="true" selectFirst="false">
                        <bc:column field="columnCode" title="列名" width="100" formatter="isKeyFormat"/>
                        <bc:column field="columnName" title="中文名" width="100"/>
                        <bc:column field="dataType" title="类型" width="100"/>
                        <bc:operator id="add" icon="plus" title="添加" handler="addColumn"/>
                        <bc:operator id="del" icon="minus" title="移除" handler="delColumn"/>
                        <be:event type="onLoadSuccess">onLoadSuccess</be:event>
                    </bc:table>
                </div>
            </bc:col>
        </div>
        <div class="hr-line-dashed"></div>
        <div class="row">
            <div style="overflow-y: auto; overflow-x: hidden; max-height: 200px;">
                <bc:table id="resultTable" idField="" url="" showColumns="false" showRefresh="false"
                          multipleVisible="false" pagination="false">
                    <bc:column field="mColumnCode" title="列名（主表）" width="100"/>
                    <bc:column field="mColumnName" title="中文名（主表）" width="100"/>
                    <bc:column field="mDataType" title="类型（主表）" width="100"/>
                    <bc:column field="jColumnCode" title="列名（关联表）" width="100"/>
                    <bc:column field="jColumnName" title="中文名（关联表）" width="100"/>
                    <bc:column field="jDataType" title="类型（关联表）" width="100"/>
                    <bc:operator id="removeHandler" icon="minus" title="移除" handler="removeHandler"/>
                </bc:table>
            </div>
        </div>
        <div class="row" align="center" style="padding-top: 15px;">
            <bc:button id="btnSave" type="primary" text="确定" click="btnSave()"/>
            <bc:button id="btnBack" text="关闭">
                <be:event>
                    $.messager.hide('winMetaQueryJoinCons');
                </be:event>
            </bc:button>
        </div>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    var joinCons = []; //全部的列
    var masterTable = ''; //选择的主表
    var joinTable = ''; //选择的关联表
    var joinDisCons = []; //编辑时，已选择的列

    $(document).ready(function () {
        /* 设置参数 */
        var args = $.messager.args('winMetaQueryJoinCons');
        masterTable = args.masterTable;
        joinTable = args.joinTable;
        if (args.joinCons) joinCons = args.joinCons;
        console.log(args);

        /* 查询主表、关联表列数据 */
        queryMaster();
        queryJoin();

        /* 初始化选中数据 */
        init();
    });

    function init() {
        /* 设置样式 */
        $('.bs-bars').attr('style', 'margin-top: 0px;');
        $('.queryFg').attr('style', 'margin-bottom: 0px;');
        $('#iboxTable').find('div .panel-body').attr('style', 'padding-top: 0px;');
        $('.paddingRight').parent('div').attr('style', 'padding-right: 0px;');
        $('.marginRight').parent('div').attr('style', 'margin-right: 36px; padding-right: 0px;');

        /* 设置关联条件表的回显数据 */
        if (joinCons.length > 0) {
            var mCols = '', jCols = ''; //主表、关联表所有列
            var url = '${base}/sys/metaColumn/getAllColumns';

            /* 取出主表所有列 */
            $.ajax({
                type: 'GET',
                url: url,
                data: {tables: JSON.stringify(masterTable)},
                dataType: 'json',
                success: function (data) {
                    mCols = data;

                    /* 取出关联表所有列 */
                    $.ajax({
                        type: 'GET',
                        url: url,
                        data: {tables: JSON.stringify(joinTable)},
                        dataType: 'json',
                        success: function (result) {
                            jCols = result;
                            loadData(mCols, jCols); //设置回显数据
                        }
                    });
                }
            });
        }
    }

    function loadData(mCols, jCols) {
        $.each(joinCons, function (i, v) {
            var masterRow = '', joinRow = ''; //主表、关联表选中行

            /* 先查询主表的回显数据 */
            $.each(mCols, function (j, m) {
                if (m.columnCode == v.masterColumn) {
                    masterRow = m;
                    return false;
                }
            });

            /* 查询关联表的回显数据 */
            $.each(jCols, function (k, n) {
                if (n.columnCode == v.joinColumn) {
                    joinRow = n;
                    return false;
                }
            });

            /* 组装显示关联数据，用于显示数据 */
            var joinDisCon = {
                'mColumnCode': v.masterColumn,
                'mColumnName': masterRow.columnName,
                'mDataType': masterRow.dataType,
                'jColumnCode': v.joinColumn,
                'jColumnName': joinRow.columnName,
                'jDataType': joinRow.dataType
            };
            joinDisCons.push(joinDisCon);
        });

// 		var valueCol = {field: "mColumnCode", title: "值1", align: "center", width: 100};
// 		var valueCol1 = {field: "mColumnName", title: "值2", align: "center", width: 100};
// 		var valueCol2 = {field: "jColumnCode", title: "值3", align: "center", width: 100};

// 		var columns = [];
// 		columns.push(valueCol);
// 		columns.push(valueCol1);
// 		columns.push(valueCol2);
// 	$('#resultTable').bootstrapTable({
// 		   idField: "mColumnCode",
// 	        pagination: false,
// 	        search: false,
// 	        showRefresh: false,
// 	        clickToSelect: true,
// 	        columns: columns,

// 	});
// 	$('#resultTable').bootstrapTable('load', joinDisCons);

        $('#resultTable').bootstrapTable('append', joinDisCons);
    }

    /* 显示或隐藏添加、移除图标 */
    function onLoadSuccess() {
        var adds = $('#joinTable').find('.add');
        var dels = $('#joinTable').find('.del');
        $(adds).show();
        $(dels).hide();

        var rows = $('#joinTable').bootstrapTable('getData');
        $.each(joinCons, function (i, v) {
            $.each(rows, function (j, r) {
                if (v.joinColumn == r.columnCode) {
                    $(adds[j]).hide();
                    $(dels[j]).show();
                    return false;
                }
            });
        });
    }

    /* 主表查询  */
    function queryMaster() {
        var data = {
            __EQS_tableId: masterTable[0].id,
            __LIKES_columnCode: $('#codeMaster').val(),
            __LIKES_columnName: $('#nameMaster').val()
        }
        $('#masterTable').jTable('clearCheckData');
        $('#masterTable').jTable('setExtQueryParams', data);
        $('#masterTable').jTable('refresh');
    }

    /* 主表重置  */
    function resetMaster() {
        $('#codeMaster').val('');
        $('#nameMaster').val('');
        queryMaster();
    }

    /* 关联表查询  */
    function queryJoin() {
        var dataJoinTable = {
            __EQS_tableId: joinTable[0].id,
            __LIKES_columnCode: $('#codeJoin').val(),
            __LIKES_columnName: $('#nameJoin').val()
        }
        $('#joinTable').jTable('clearCheckData');
        $('#joinTable').jTable('setExtQueryParams', dataJoinTable);
        $('#joinTable').jTable('refresh');
    }

    /* 关联表重置  */
    function resetJoin() {
        $('#codeJoin').val('');
        $('#nameJoin').val('');
        queryJoin();
    }

    /* 是否显示主键图标 */
    function isKeyFormat(value, row, index) {
        if (row.isKey == 'Y') {
            return '<i class="fa fa-key" style="padding-right: 3px;"></i>' + row.columnCode;
        }
        return row.columnCode;
    }

    /* 添加关联字段 */
    function addColumn(e, value, row, index) {
        var masterRow = $('#masterTable').jTable('getCheckData');

        /* 组装关联条件，用于保存数据 */
        var joinCon = {
            masterColumn: masterRow[0].columnCode,
            joinColumn: row.columnCode,
            operator: "="
        };
        joinCons.push(joinCon);

        /* 组装显示关联数据，用于显示数据 */
        var joinDisCon = {
            mColumnCode: masterRow[0].columnCode,
            mColumnName: masterRow[0].columnName,
            mDataType: masterRow[0].dataType,
            jColumnCode: row.columnCode,
            jColumnName: row.columnName,
            jDataType: row.dataType
        };
        joinDisCons.push(joinDisCon);

        var tmp = [];
        tmp.push(joinDisCon);
        $('#resultTable').bootstrapTable('append', tmp);

        var adds = $('#joinTable').find('.add');
        $(adds[index]).hide();
        var dels = $('#joinTable').find('.del');
        $(dels[index]).show();
    }

    /* 删除关联条件 */
    function delColumn(e, value, row, index) {
        var masterRow = $('#masterTable').jTable('getCheckData');

        /* 组装关联条件，用于删除数据 */
        var joinCon = {
            masterColumn: masterRow[0].columnCode,
            joinColumn: row.columnCode,
            operator: "="
        };
        joinCons.splice(getIndexByData(joinCons, ['masterColumn', 'joinColumn'], joinCon), 1);

        /* 组装显示关联数据，用于删除显示数据 */
        var joinDisCon = {
            'mColumnCode': masterRow[0].columnCode,
            'mColumnName': masterRow[0].columnName,
            'mDataType': masterRow[0].dataType,
            'jColumnCode': row.columnCode,
            'jColumnName': row.columnName,
            'jDataType': row.dataType
        };
        joinDisCons.splice(getIndexByData(joinDisCons, ['mColumnCode', 'jColumnCode'], joinDisCon), 1);

        /* 移除选中行 */
        var tmp = [];
        tmp.push(joinDisCon.jColumnCode);
        $('#resultTable').bootstrapTable('remove', {field: 'jColumnCode', values: tmp});

        var adds = $('#joinTable').find('.add');
        $(adds[index]).show();
        var dels = $('#joinTable').find('.del');
        $(dels[index]).hide();
    }

    /* 移除关联字段 */
    function removeHandler(e, value, row, index) {

        /* 组装关联条件，用于删除数据 */
        var joinCon = {
            masterColumn: row.mColumnCode,
            joinColumn: row.jColumnCode,
            operator: "="
        };
        joinCons.splice(getIndexByData(joinCons, ['masterColumn', 'joinColumn'], joinCon), 1);

        /* 删除选中的移除数据 */
        joinDisCons.splice(getIndexByData(joinDisCons, ['mColumnCode', 'jColumnCode'], row), 1);
        $('#resultTable').bootstrapTable('remove', {field: 'jColumnCode', values: [row.jColumnCode]});

        /* 显示或隐藏对应数据的图标 */
        var j = 0;
        var rows = $('#joinTable').bootstrapTable('getData');
        $.each(rows, function (i, v) {
            if (v.columnCode == row.jColumnCode) {
                j = i;
                return false;
            }
        });

        var adds = $('#joinTable').find('.add');
        $(adds[j]).show();
        var dels = $('#joinTable').find('.del');
        $(dels[j]).hide();
    }

    /* 查询对象在数组中的位置 ， array为待查询的数组，properties为数组中对象是属性数组，可以是多个属性, row为具体对象*/
    function getIndexByData(array, properties, row) {
        var index = -1;
        /* 循环整个数组。判断是否存在当前对象 */
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

    /* 确定 */
    function btnSave() {
        /* 当没有选择关联条件的时候给出提示 */
        if (joinCons.length < 1) {
            $.messager.toastr('提示', '请选择关联条件', 'warning');
        } else {
            $.messager.args('winMetaQueryJoinCons').callback(joinCons);
            $.messager.hide('winMetaQueryJoinCons');
        }
    }
</script>
</body>
</html>