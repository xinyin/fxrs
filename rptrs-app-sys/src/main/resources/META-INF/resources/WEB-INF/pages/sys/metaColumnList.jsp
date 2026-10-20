<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>列元数据表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="列元数据表" subTitle="请输入列元数据表查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bf:hidden name="__EQS_tableId" value="${tableId}"></bf:hidden>
                <bc:row>
                    <bf:formGroup col="4">
                        <bc:label text="列名" col="5" formControl="true"/>
                        <bf:text name="__LIKES_columnCode" col="7" placeholder="列名"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="列中文名" col="5" formControl="true"/>
                        <bf:text name="__LIKES_columnName" col="7" placeholder="列中文名"/>
                    </bf:formGroup>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:col col="12">
                <div id="optionTable_toolbar">
                    <button id="optionTable_tb_syn" type="button" onclick="synClick()" class=" btn btn-primary">同步
                    </button>
                    <button id="optionTable_tb_save" type="button" onclick="saveClick()" class=" btn btn-primary">保存
                    </button>
                    <button id="optionTable_tb_back" type="button" onclick="getBack()" class=" btn btn-primary">返回
                    </button>
                </div>
                <table id="optionTable">
                </table>
            </bc:col>
            <%-- <bc:table id="metaColumnTable" idField="columnId" url="${base}/sys/metaColumn">
                <bc:toolbar id="getBack" text="返回" type="button" handler="getBack"/>
                <bc:column field="columnCode" title="列名" width="100"/>
                <bc:column field="columnName" title="列中文名" width="100"/>
                <bc:column field="prop" title="字段属性" width="100" formatter="fixedDict:ColumnAttribute"/>
                <bc:column field="isForeignKey" title="外键" width="100" formatter="fixedDict:YesNo"/>
                <bc:column field="dataType" title="数据类型" width="100" />
                <bc:column field="isKey" title="主键" width="100" formatter="fixedDict:YesNo"/>
                <bc:column field="length" title="字段长度" width="100"/>
                <bc:column field="scale" title="字段精度" width="100"/>
                <bc:column field="order" title="字段顺序" width="100"/>
            </bc:table> --%>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    function getBack() {
        window.location = '${base}/sys/metaTable/list';
    }

    var columnAttribute = [], yesNo = [];

    $(document).ready(function () {
        /* 初始化参数表格*/
        $.ajax({
            url: '${base}/sys/metaColumn/queryMetaColumnDto?tableId=${tableId}',
            dataType: 'json',
            delay: 200,
            success: function (data) {
                initTable(data);
            }
        });
        $.cache.cache('fixedDict', 'ColumnAttribute');
        $.cache.cache('fixedDict', 'YesNo');

        $.each($.cache.getCache('ColumnAttribute'), function (i, v) {
            columnAttribute.push({'value': v['value'], 'text': v.name});
        });

        $.each($.cache.getCache('YesNo'), function (i, v) {
            yesNo.push({'value': v['value'], 'text': v.name});
        });
    })

    /* 初始化参数表格*/
    function initTable(rows) {
        //初始化表格
        $("#optionTable").bootstrapTable({
            toolbar: "#optionTable_toolbar",
            idField: "propCode",
            pagination: false,
            search: false,
            showRefresh: false,
            clickToSelect: true,
            columns: [{field: "check", checkbox: true},
                {field: "columnCode", title: "列名", align: "center", width: 100},
                {
                    field: "columnName",
                    title: "列中文名",
                    align: "center",
                    width: 100,
                    editable: {type: 'text', mode: "inline", title: '中文名'}
                },
                {
                    field: "prop",
                    title: "字段属性",
                    align: "center",
                    width: 100,
                    editable: {type: 'select', mode: "inline", title: '字段属性', source: columnAttribute}
                },
                {
                    field: "isForeignKey",
                    title: "外键",
                    align: "center",
                    width: 100,
                    editable: {type: 'select', mode: "inline", title: '是否外键', source: yesNo}
                },
                {field: "masterTableDisplayName", title: "关联表", align: "center", width: 100},
                {field: "masterColumnCode", title: "关联字段", align: "center", width: 100},
                {field: "dataType", title: "数据类型", align: "center", width: 100},
                {field: "isKey", title: "主键", align: "center", width: 100},
                {field: "length", title: "字段长度", align: "center", width: 100},
                {field: "scale", title: "字段精度", align: "center", width: 100},
                {field: "order", title: "字段顺序", align: "center", width: 100}],
            onEditableSave: function (field, row, oldValue, $el) {
                updateRow(field, row, oldValue);
            }
        });

        $('#optionTable').bootstrapTable('load', rows);
    }

    function updateRow(field, row, oldValue) {
        if ('isForeignKey' == field) {
            if (oldValue == 'N' && row['isForeignKey'] == 'Y') {
                /* 打开另存为画面 */
                $.messager.modal({
                    winId: 'wChooseMastertable',
                    width: 900,
                    height: 520,
                    title: '选择关联表',
                    url: '${base}/sys/metaColumn/chooseMastertable?tableId=${tableId}',
                    args: {script: '', callback: setMastertable},
                    onClose: function () {
                        /*刷新标题,刷新树*/
                        if (_isClosed == 1) {
                            var rows = $('#optionTable').bootstrapTable('getData');
                            updateForeignKey(rows, row);
                            _isClosed = 0;
                        }
                    }
                });
            } else if (oldValue == 'Y' && row['isForeignKey'] == 'N') {
                var rows = $('#optionTable').bootstrapTable('getData');
                _masterTableId = null;
                _masterTableCode = null;
                _masterColumnCode = null;
                _masterTableDisplayName = null;
                updateForeignKey(rows, row);
            }
        } else if ('prop' == field) {
            var rows = $('#optionTable').bootstrapTable('getData');
            updateProp(rows, row);
        }
    }

    function updateProp(rows, row) {
        $.each(rows, function (i, v) {
            if (v['columnCode'] != row['columnCode'] && v['prop'] == 'O') {
                v['prop'] = 'N';
                return;
            }
        });
        $('#optionTable').bootstrapTable('load', rows);
    }


    function updateForeignKey(rows, row) {
        $.each(rows, function (i, v) {
            if (v['columnCode'] == row['columnCode']) {
                v['masterTableId'] = _masterTableId;
                v['masterTableCode'] = _masterTableCode;
                v['masterColumnCode'] = _masterColumnCode;
                v['masterTableDisplayName'] = _masterTableDisplayName;
                return;
            }
        });

        $('#optionTable').bootstrapTable('load', rows);
    }


    var _isClosed = 0, _masterTableId, _masterTableCode, _masterColumnCode, _masterTableDisplayName;

    function setMastertable(masterTableId, masterTableCode, masterColumnCode, masterTableDisplayName) {
        debugger;
        _masterTableId = masterTableId;
        _masterTableCode = masterTableCode;
        _masterColumnCode = masterColumnCode;
        _masterTableDisplayName = masterTableDisplayName;
        _isClosed = 1;
    }

    function saveClick() {
        $.messager.confirm('提示', '确定要保存修改的列吗？', function (result) {
            if (result) {
                var rows = $('#optionTable').bootstrapTable('getData');
                var metaColumnData = JSON.stringify(rows);
                data = {mcList: metaColumnData, tableId: '${tableId}'};
                /* 提交到后台更新列数据信息 */
                $.mask.post('${base}/sys/metaColumn/updateMetaColumn', data, function (data) {
                    if (data.success) {
                        $.messager.alert('系统提示', data.msg, 'info');
                    } else {
                        $.messager.alert('系统提示', data.msg, 'info');
                    }
                });
            }
        });
    }

    function synClick() {
        $.messager.confirm('提示', '确定要同步元数据列信息吗？', function (result) {
            if (result) {
                params = {
                    tableId: '${tableId}'
                };
                $.mask.post('${base}/sys/metaColumn/update', params, function (data) {
                    if (data.success) {
                        $.messager.alert('系统提示', data.msg, 'info');
                    } else {
                        $.messager.alert('系统提示', data.msg, 'warning');
                    }
                });
            }
        });
    }

</script>
</body>
</html>