<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>设置查询语句</title>
    <style type="text/css">
        #setIbox {
            margin-bottom: 0px;
        }

        .ibox-content {
            padding-bottom: 0px;
        }

        .panel-body {
            padding-bottom: 0px;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row" style="overflow: hidden">
    <bc:ibox id="setIbox" title="" col="12" collapsible="true">
        <bf:form id="setForm" method="post" validation="true">
            <bf:formGroup id="masterTableId">
                <bc:label text="选择表" col="3" formControl="true"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="查询方式" col="3" formControl="true"/>
                <bf:checkGroup name="queryType" value="N" col="6">
                    <bf:radio id="queryTypen" label="普通查询" value="N" change="queryTypeChange(this)"/>
                    <bf:radio id="queryTypeg" label="汇总查询" value="G" change="queryTypeChange(this)"/>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup cls="queryTypeN">
                <bc:label text="选择字段" col="3" formControl="true"/>
                <bf:text id="selectColumns" name="selectColumns" multiple="true" col="6" disabled="true"
                         rows="3"><bv:notEmpty/></bf:text>
                <bc:button id="selectColumnsBtn" icon="search-plus" tooltip="选择字段" size="xs" click="checkColumns()"/>
            </bf:formGroup>
            <bf:formGroup cls="queryTypeG">
                <bc:label text="分组字段" col="3" formControl="true"/>
                <bf:text id="groupByColumns" name="groupByColumns" multiple="true" col="6" disabled="true"
                         rows="3"><bv:notEmpty/></bf:text>
                <bc:button id="groupByColumnsBtn" icon="search-plus" tooltip="分组字段" size="xs"
                           click="checkGroupByColumns()"/>
            </bf:formGroup>
            <bf:formGroup cls="queryTypeG">
                <bc:label text="计数" col="3" formControl="true"/>
                <bf:checkGroup name="groupCount" col="6" value="">
                    <bf:check value="G" id="groupCountCK" change="groupCountChange(this)"/>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup cls="queryTypeG">
                <bc:label text="汇总计算" col="3" formControl="true"/>
                <bf:text id="selectGroupColumns" name="selectGroupColumns" multiple="true" col="6" disabled="true"
                         rows="3"><bv:callback callback="groupValid"/></bf:text>
                <bc:button id="selectGroupColumnsBtn" icon="search-plus" tooltip="汇总计算" size="xs"
                           click="checkGroupColumns()"/>
            </bf:formGroup>
            <bf:formGroup cls="queryTypeG">
                <bc:label text="汇总筛选" col="3" formControl="true"/>
                <bf:text id="selectHaving" name="selectHaving" multiple="true" col="6" rows="3"><bv:notEmpty/></bf:text>
                <bc:button id="selectHavingBtn" icon="pencil" tooltip="汇总筛选" size="xs" click="checkHaving()"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="筛选条件" col="3" formControl="true"/>
                <bf:text id="condition" name="condition" multiple="true" col="6" rows="3"/>
                <bc:button id="conditionBtn" icon="pencil" tooltip="筛选条件" size="xs" click="conditionSelect()"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="排序字段" col="3" formControl="true"/>
                <bf:text id="selectOrder" name="selectOrder" multiple="true" col="6" disabled="true" rows="3"/>
                <bc:button id="selectOrderBtn" icon="search-plus" tooltip="排序字段" size="xs" click="checkOrder()"/>
            </bf:formGroup>
            <bf:formGroup>
                <div align="center">
                    <bc:button id="btnSave" text="保存" click="btnSave('B')"/>
                    <bc:button id="btnSaveAndExcute" type="primary" text="保存并执行" click="btnSave('E')"/>
                    <bc:button id="btnBack" text="关闭">
                        <be:event>
                            $.messager.hide('winDataAnalysisSet');
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    var tables = []; //所有选中的表
    var joinTableTypes = []; //关联表关系
    var tableCount = 6; //关联表数量最大值
    var joinAlias = 'T1'; //默认第一张表为T1

    var columns = []; //选择字段
    var groupBy = []; //选择分组字段
    var groups = []; //分组汇总字段
    var orders = []; //排序字段
    var limit = ''; //默认显示条数
    var count = 'Y'; //是否为【计数】方式。

    $(document).ready(function () {

        var args = $.messager.args('winDataAnalysisSet');
        var script = args.script; //当前设置的脚本
        var obj = $.parseJSON(script);

        init(); //初始化样式

        /* 初始化显示表 */
        tables = obj.tables; //获取tables
        showMasterTable();

        /*关联关系*/
        joinTableTypes = obj.joinTables;

        /*查询类型*/
        if (obj.queryType == 'G') {
            $('#queryTypeg').attr('checked', 'checked');
            queryTypeChange(obj.queryType);
        }

        /*选择的字段*/
        columns = obj.columns;
        showCoulumns();

        /*分组字段*/
        groupBy = obj.groupBy;
        showGroupByCoulumns();

        /*是否计数*/
        if (obj.COUNT == 'Y') {
            $('#groupCountCK').attr('checked', 'checked');
        }

        /*筛选条件*/
        $('#condition').val(obj.where);

        /*排序字段*/
        orders = obj.orders;
        showOrder();

        /*显示行数*/
        limit = obj.limit;
    });

    /* 初始化样式 */
    function init() {
        $('.queryTypeG').hide();
        $('.queryTypeG').next('div .hr-line-dashed').hide();
    }

    /*初始化显示表*/
    function showMasterTable() {
        $.each(tables, function (index, table) {
            var title = table.alias + '-' + table.name + '(' + table.code + ')';

            var tableHtml = '';
            /*主表不显示编辑和删除*/
            if (table.alias == 'T1') {
                tableHtml += '<label class="control-label col-xs-5" style="text-align:left;">' + title + '</label>';
                tableHtml += '<button type="button" id="add_table" onclick="addTable(\'' + table.alias + '\')" data-toggle="tooltip" data-placement="top" class="btn btn-xs btn-default" data-original-title="添加表"><i class="fa fa-plus"></i></button>';
            } else {
                tableHtml += '<div class="col-xs-5 col-xs-offset-3 ' + joinTable.alias + '"><label class="control-label" style="text-align:left; padding-left: 0px;">' + title + '</label></div>';
                tableHtml += '<div class="col-xs-offset-7 ' + joinTable.alias + '"><button type="button" id="add_table" onclick="addTable(\'' + joinTable.alias + '\')" data-toggle="tooltip" data-placement="top" class="btn btn-xs btn-default" data-original-title="添加表"><i class="fa fa-plus"></i></button>';
                tableHtml += '<button type="button" id="edit_table" onclick="editTable(\'' + joinTable.alias + '\')" data-toggle="tooltip" data-placement="top" class="btn btn-xs btn-default" data-original-title="修改表"><i class="fa fa-edit"></i></button>';
                tableHtml += '<button type="button" id="del_table" onclick="delTable(\'' + joinTable.alias + '\')" data-toggle="tooltip" data-placement="top" class="btn btn-xs btn-default" data-original-title="删除表"><i class="fa fa-times"></i></button></div>';

            }
            $("#masterTableId").append(tableHtml);
        });
    }

    /*清空页面已选择条件*/
    function clearForm() {
        columns = []; //清空选择的字段
        groupBy = []; //清空分组字段
        groups = []; //清空汇总计算
        orders = []; //清空排序
        $('#selectColumns').val('');
        $('#groupByColumns').val(''); //清空分组字段
        $('#selectGroupColumns').val(''); //清空汇总计算
        $('#selectHaving').val(''); //清空汇总筛选
        $('#condition').val(''); //清空筛选条件
        $('#selectOrder').val('');
    }

    /*添加关联表*/
    function addTable(alias) {
        /*判断添加的表数量是否大于规定数量*/
        if (tables.length >= tableCount) {
            $.messager.toastr('提示', '最多能选择' + tableCount + '张表。', 'info');
            return false;
        }

        var masterTable = getTable(alias); //获取主表信息

        /*关联表别名*/
        var number = Number(joinAlias.substr(1, joinAlias.length)) + Number(1);
        joinAlias = 'T' + number;

        $.messager.modal({
            winId: 'winDataAnalysisJoin',
            width: 800,
            height: 260,
            title: '增加关联表',
            url: '${base}/sys/metaQuery/join',
            args: {callback: joinColse, masterTable: masterTable, joinAlias: joinAlias, type: 'add'}
        });
    }

    function joinColse(joinTable, joinTableType) {

        /*把关联表加入到数组中*/
        tables.push(joinTable);
        /*把关联条件加入都数组中*/
        joinTableTypes.push(joinTableType);
        /*显示选择的表*/
        showTable(joinTable, 'add');
    }

    /*编辑关联表*/
    function editTable(alias) {
        /*获取关联方式*/
        var joinTableType = getJoinTableType(alias);

        /*获取主表、关联表*/
        var masterTable = getTable(joinTableType.masterTable);
        var joinTable = getTable(alias);

        $.messager.modal({
            winId: 'winDataAnalysisJoin',
            width: 800,
            height: 350,
            title: '编辑关联表',
            url: '${base}/sys/metaQuery/join',
            args: {
                callback: joinEditColse,
                masterTable: masterTable,
                joinTable: joinTable,
                joinTableType: joinTableType,
                type: 'edit'
            }
        });
    }

    function joinEditColse(joinTable, joinTableType) {

        /*把元数据表中的替换*/
        replaceTables(joinTable);
        /*把关联条件数组中替换*/
        replaceJoinTableTypes(joinTableType);
        /*显示选择的表*/
        showTable(joinTable, 'edit');
    }

    /*删除已选择的表*/
    function delTable(alias) {
        $.messager.confirm('系统提示', '是否确认删除该表并删除下面所有的表？', function (result) {
            if (result) {
                var aliasStr = getAlias(alias);

                /*移除要删除的表对象*/
                removeTables(alias);

                /*移除关联条件数组中的对象*/
                removejoinTableTypes(alias);

                /*删除页面显示div*/
                aliasStr = aliasStr.substr(0, aliasStr.length - 1);
                var array = aliasStr.split(',');
                for (var i = 0; i < array.length; i++) {
                    $('.' + array[i]).remove();
                }

                /*清空已选字段*/
                clearForm();
            }
        });
    }

    /*显示选择的表*/
    function showTable(joinTable, type) {
        var title = joinTable.alias + '-' + joinTable.name + '(' + joinTable.code + ')';

        var tableHtml = '<div class="col-xs-5 col-xs-offset-3 ' + joinTable.alias + '"><label class="control-label" style="text-align:left; padding-left: 0px;">' + title + '</label></div>';
        tableHtml += '<div class="col-xs-offset-7 ' + joinTable.alias + '"><button type="button" id="add_table" onclick="addTable(\'' + joinTable.alias + '\')" data-toggle="tooltip" data-placement="top" class="btn btn-xs btn-default" data-original-title="添加表"><i class="fa fa-plus"></i></button>';
        tableHtml += '<button type="button" id="edit_table" onclick="editTable(\'' + joinTable.alias + '\')" data-toggle="tooltip" data-placement="top" class="btn btn-xs btn-default" data-original-title="修改表"><i class="fa fa-edit"></i></button>';
        tableHtml += '<button type="button" id="del_table" onclick="delTable(\'' + joinTable.alias + '\')" data-toggle="tooltip" data-placement="top" class="btn btn-xs btn-default" data-original-title="删除表"><i class="fa fa-times"></i></button></div>';

        /*新增添加div，修改替换div内容*/
        if (type == 'add') {
            $("#masterTableId").append(tableHtml);
        } else {
            $('#' + joinTable.alias).html(tableHtml);
        }
    }

    /* 改变查询方式 */
    function queryTypeChange(el) {

        /* 清空排序字段值 */
        orders = [];
        $('#selectOrder').val('');

        var val = '';
        if (el != 'G' || el != 'N') val = $(el).val();
        else val = el;

        if (val == 'N') {
            $('.queryTypeG').hide();
            $('.queryTypeG').next('div .hr-line-dashed').hide();
            $('.queryTypeN').show();
            $('.queryTypeN').next('div .hr-line-dashed').show();
        } else if (val == 'G') {
            $('.queryTypeN').hide();
            $('.queryTypeN').next('div .hr-line-dashed').hide();
            $('.queryTypeG').show();
            $('.queryTypeG').next('div .hr-line-dashed').show();
        }
    }

    /*选择字段*/
    function checkColumns() {
        if (!tables) {
            $.messager.toastr('提示', '请先选择主表。', 'warning');
            return;
        }

        var selectColumns = formatColumns(columns);
        $.messager.modal({
            winId: 'wSelectColumns',
            width: 900,
            height: 475,
            title: '选择字段',
            url: '${base}/sys/metaQuery/selectColumn',
            args: {callback: colseColumns, tables: tables, selected: selectColumns, maxRows: -1}
        });
    }

    function colseColumns(cloumn) {
        $('#selectColumns').attr('disabled', false);
        $('#setForm').data('formValidation').revalidateField('selectColumns');
        $('#selectColumns').attr('disabled', true);
        /*加到数组中*/
        columns = cloumn;

        /*清空排序字段*/
        orders = [];
        $('#selectOrder').val('');

        /*页面显示*/
        showCoulumns();
    }

    /*显示选择字段*/
    function showCoulumns() {

        var columnHtml = '';
        $.each(columns, function (index, column) {
            columnHtml += column.code + ',';
        });

        columnHtml = columnHtml.substr(0, columnHtml.length - 1);
        $('#selectColumns').val(columnHtml);
    }

    /*分组字段*/
    function checkGroupByColumns() {
        if (!tables) {
            $.messager.toastr('系统提示', '请先选择主表。', 'warning');
            return;
        }

        var selectColumns = formatColumns(groupBy);
        $.messager.modal({
            winId: 'wSelectColumns',
            width: 900,
            height: 475,
            title: '选择分组字段',
            url: '${base}/sys/metaQuery/selectColumn',
            args: {callback: colseGroupByColumns, tables: tables, selected: selectColumns, maxRows: 6}
        });
    }

    /*分组字段回调函数*/
    function colseGroupByColumns(cloumn) {
        $('#groupByColumns').attr('disabled', false);
        $('#setForm').data('formValidation').resetField('groupByColumns');
        $('#groupByColumns').attr('disabled', true);

        /*加到数组中*/
        groupBy = cloumn;

        /*清空排序字段*/
        orders = [];
        $('#selectOrder').val('');

        /*页面显示*/
        showGroupByCoulumns();
    }

    /*显示分组字段*/
    function showGroupByCoulumns() {

        var columnHtml = '';
        $.each(groupBy, function (index, column) {
            columnHtml += column.code + ',';
        });

        columnHtml = columnHtml.substr(0, columnHtml.length - 1);
        $('#groupByColumns').val(columnHtml);
    }

    /* 计数 */
    function groupCountChange(el) {
        $('#selectGroupColumns').attr('disabled', false);
        $('#setForm').data('formValidation').revalidateField('selectGroupColumns');
        $('#selectGroupColumns').attr('disabled', true);
    }

    /*汇总计算*/
    function checkGroupColumns() {
        /* 判断是否已经选择表 */
        if (!tables) {
            $.messager.toastr('系统提示', '请先选择主表。', 'warning');
            return;
        }

        $.messager.modal({
            winId: 'wselectGroupColumns',
            width: 900,
            height: 475,
            title: '选择汇总字段',
            url: '${base}/sys/metaQuery/groupColumns',
            args: {callback: colseGroupsCallBack, groupCount: 6, tables: tables, selected: groups, options: ''}
        });
    }

    /*汇总计算回调函数*/
    function colseGroupsCallBack(group) {
        $('#selectGroupColumns').attr('disabled', false);
        $('#setForm').data('formValidation').resetField('selectGroupColumns');
        $('#selectGroupColumns').attr('disabled', true);
        groups = group;

        /*清空排序字段*/
        orders = [];
        $('#selectOrder').val('');

        /*显示分组汇总到页面 */
        showGroups();
    }

    /* 设置选中分组字段名称显示在界面上 */
    function showGroups() {
        var colNames = '';
        /* 循环选择的列，把这些列的名称都取出来，组成以'，'分割的字符串 */
        $.each(groups, function (index, col) {

            if (col.DC == 'Y')
                colNames += 'DC(' + col.code + '),';
            if (col.SUM == 'Y')
                colNames += 'SUM(' + col.code + '),';
            if (col.DS == 'Y')
                colNames += 'DS(' + col.code + '),';
            if (col.MAX == 'Y')
                colNames += 'MAX(' + col.code + '),';
            if (col.MIN == 'Y')
                colNames += 'MIN(' + col.code + '),';
            if (col.AVG == 'Y')
                colNames += 'AVG(' + col.code + '),';

        });
        colNames = colNames.substr(0, colNames.length - 1);
        $('#selectGroupColumns').val(colNames);
    }

    /*选择条件*/
    function conditionSelect() {
        if (!tables) {
            $.messager.toastr('系统提示', '请先选择主表。', 'warning');
            return;
        }

        /*获取筛选条件*/
        var expr = {expr: $('#condition').val()};
        var data = {
            tables: tables,
            expr: expr,
            type: 'E',
            options: ''
        };
        $.messager.modal({
            winId: 'wSelectCondition',
            width: 800,
            height: 450,
            title: '选择条件',
            url: '${base}/sys/metaQuery/condition',
            args: {callback: colseCondition, data: data}
        });
    }

    /*显示选择条件*/
    function colseCondition(data) {
        $('#condition').val(data.expr);
    }

    /* 选择排序字段 */
    function checkOrder() {
        if (!tables) {
            $.messager.toastr('系统提示', '请先选择主表。', 'warning');
            return;
        }

        /*组装排序字段*/
        var height = 500;
        var queryType = $('input[name="queryType"]:checked ').val();
        if (queryType == 'G') height = 360
        var orderColumns = setOrders(queryType);
        if (orderColumns.length < 1) {
            $.messager.toastr('系统提示', '请先选择字段。', 'info');
            return;
        }

        $.messager.modal({
            winId: 'wSelectOrderColumns',
            width: 800,
            height: height,
            title: '选择排序',
            url: '${base}/sys/metaQuery/order',
            args: {callback: colseOrder, countOrder: 6, orders: orderColumns, selected: orders, options: ''}
        });
    }

    /*选择排序回调方法*/
    function colseOrder(order) {
        orders = order;
        /*显示排序*/
        showOrder();
    }

    /* 设置选中排序字段名称显示在界面上 */
    function showOrder() {
        var colNames = '';
        /* 循环选择的列，把这些列的名称都取出来，组成以'，'分割的字符串 */
        $.each(orders, function (index, col) {
            /* 判断是否是第一次进入，如果是前面就不加‘,’ */
            if (colNames == '') {
                colNames = col.code;
            } else {
                colNames = colNames + ', ' + col.code;
            }
        });
        $('#selectOrder').val(colNames);
    }

    /*汇总筛选*/
    function checkHaving() {
        if (!tables) {
            $.messager.toastr('系统提示', '请先选择主表。', 'warning');
            return;
        }

        /*获取汇总筛选*/
        var having = {expr: $('#selectHaving').val()};
        var data = {
            tables: tables,
            expr: having,
            type: 'F'
        };
        $.messager.modal({
            winId: 'wSelectCondition',
            width: 800,
            height: 450,
            title: '选择汇总筛选',
            url: '${base}/sys/metaQuery/condition',
            args: {callback: colseHaving, data: data}
        });
    }

    /*筛选条件回调方法*/
    function colseHaving(data) {
        $('#selectHaving').val(data.expr);
        $('#setForm').data('formValidation').revalidateField('selectHaving');
    }

    /* 验证【计数】和【汇总计算】的值，至少选择一种 */
    function groupValid() {

        count = 'Y';
        var queryType = $('input[name="queryType"]:checked').val();
        if (queryType == 'G') //验证汇总查询
        {
            /*汇总计数和汇总计算*/
            var groupCountCK = $('#groupCountCK').prop("checked");
            if (!groupCountCK) {
                count = 'N';
                var selectGroupColumns = $('#selectGroupColumns').val();
                if (!selectGroupColumns) {
                    return {valid: false, message: '汇总查询模式下【计数】和【汇总计算】必须选择一项。'};
                }
            }
        }
        return {valid: true};
    }

    /*保存*/
    function btnSave(type) {
        var queryType = $('input[name="queryType"]:checked ').val();
        if (queryType == 'N') //验证普通查询
        {
            $('#selectColumns').attr('disabled', false);
        } else //验证汇总查询
        {
            $('#selectGroupColumns').attr('disabled', false);
            $('#groupByColumns').attr('disabled', false);
        }

        $('#setForm').data('formValidation').validate();
        var isValid = $('#setForm').data('formValidation').isValid();
        $('#groupByColumns').attr('disabled', true);
        $('#selectColumns').attr('disabled', true);
        $('#selectGroupColumns').attr('disabled', true);
        if (isValid) {
            var selectHaving = $('#selectHaving').val();
            var condition = $('#condition').val();

            /*封装对形象返回*/
            var metaQuery = {
                tables: tables,
                masterTable: "T1",
                joinTables: joinTableTypes,
                queryType: queryType,
                columns: columns,
                groupBy: groupBy,
                COUNT: count,
                groups: groups,
                orders: orders,
                having: (selectHaving) ? selectHaving : '',
                where: (condition) ? condition : '',
                limit: limit
            };

            $.messager.args('winDataAnalysisSet').callback(metaQuery, type);
            $.messager.hide('winDataAnalysisSet');
        }
    }

    /* ==================方法工具================== */
    /*根据表别名获取表对象*/
    function getTable(alias) {
        var obj = null;
        $.each(tables, function (i, v) {
            if (v.alias == alias) {
                obj = v;
            }
        });
        return obj;
    }

    /*根据表别名获取joinTableTypes数组中的对象*/
    function getJoinTableType(alias) {
        var obj = null;
        $.each(joinTableTypes, function (i, v) {
            if (v.joinTable == alias) {
                obj = v;
            }
        });
        return obj;
    }

    /*替换tables中的对象*/
    function replaceTables(joinTable) {
        $.each(tables, function (i, v) {
            if (v.alias == joinTable.alias) {
                tables.splice(i, 1, joinTable);
                return false;
            }
        });
    }

    /*替换joinTableTypes中对象*/
    function replaceJoinTableTypes(joinTableType) {
        $.each(joinTableTypes, function (i, v) {
            if (v.joinTable == joinTableType.joinTable) {
                joinTableTypes.splice(i, 1, joinTableType);
                return false;
            }
        });
    }

    /*获取数组中大于传入的表别名的所有别名*/
    function getAlias(alias) {
        var aliasStr = '';
        $.each(tables, function (index, table) {
            var tab_number = Number(table.alias.substr(1, table.alias.length));
            var number = Number(alias.substr(1, alias.length));
            if (tab_number >= number) {
                aliasStr += table.alias + ',';
            }
        });
        return aliasStr;
    }

    /*根据表别名在tables数组中找出对象并移除*/
    function removeTables(alias) {
        $.each(tables, function (index, table) {
            if (table.alias == alias) {
                tables.splice(index, (tables.length - index));
                return false;
            }
        });
    }

    /*根据表别名在joinTableTypes数组中找出对象并移除*/
    function removejoinTableTypes(alias) {
        $.each(joinTableTypes, function (i, v) {
            if (v.joinTable == alias) {
                joinTableTypes.splice(i, (joinTableTypes.length - i));
                return false;
            }
        });
    }

    /*格式化数组字段和数据库对应*/
    function formatColumns(cols) {
        var newCols = [];
        $.each(cols, function (index, col) {
            var data = {
                columnCode: col.code,
                columnName: col.name,
                columnType: col.type,
                expr: col.expr

            };
            newCols.push(data);
        });

        return newCols;
    }

    /*组装排序字段*/
    function setOrders(type) {
        var array = [];
        if (type == 'N') {
            $.each(columns, function (index, col) {
                var data = {
                    code: col.code,
                    name: col.name
                };
                array.push(data);
            });
        } else {
            /*分组字段*/
            $.each(groupBy, function (index, col) {
                var data = {
                    code: col.code,
                    name: col.name
                };
                array.push(data);
            });

            /*汇总计算*/
            $.each(groups, function (index, col) {
                if (col.DC == 'Y') {
                    var obj = {
                        code: col.code + '_DC_',
                        name: ((col.name) ? col.name : col.code) + '唯一值计数'
                    };
                    array.push(obj);
                }
                if (col.SUM == 'Y') {
                    var obj = createObj(col.code + '_SUM_', ((col.name) ? col.name : col.code) + '求和');
                    array.push(obj);
                }
                if (col.DS == 'Y') {
                    var obj = createObj(col.code + '_DS_', ((col.name) ? col.name : col.code) + '唯一值求和');
                    array.push(obj);
                }
                if (col.MAX == 'Y') {
                    var obj = createObj(col.code + '_MAX_', ((col.name) ? col.name : col.code) + '最大值');
                    array.push(obj);
                }
                if (col.MIN == 'Y') {
                    var obj = createObj(col.code + '_MIN_', ((col.name) ? col.name : col.code) + '最小值');
                    array.push(obj);
                }
                if (col.AVG == 'Y') {
                    var obj = createObj(col.code + '_AVG_', ((col.name) ? col.name : col.code) + '平均数');
                    array.push(obj);
                }
            });
        }
        return array;
    }

    /*创建结果表对象*/
    function createObj(code, name) {
        var obj = {
            code: code,
            name: (name) ? name : ''
        };
        return obj;
    }
</script>
</body>
</html>