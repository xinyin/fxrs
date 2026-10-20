<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>组件基本信息</title>
</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title="组件基本信息" subTitle="请输入查询信息" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="4">
                    <bc:label text="组件编码" col="4" formControl="true"/>
                    <bf:text name="__LIKES_code" col="8" placeholder="编码">
                        <bv:jepCode/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="组件名称" col="4" formControl="true"/>
                    <bf:text name="__LIKES_name" col="8" placeholder="名称"/>
                </bc:col>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="dashWidgetTable" idField="code" url="${base}/sys/dashWidget">
            <bc:toolbar id="add" text="新增" type="button"/>
            <bc:toolbar id="dataSource" text="绑定数据源" type="button" handler="dataProvider"/>
            <bc:toolbar id="design" text="设计" type="button" handler="design"/>
            <bc:toolbar id="enable" text="启用" type="button" handler="enable"/>
            <bc:toolbar id="disable" text="停用" type="button" handler="disable"/>
            <bc:column field="code" title="组件编码" width="100"/>
            <bc:column field="name" title="组件名称" width="100"/>
            <bc:column field="type" title="组件类型" width="100" formatter="fixedDict:DashWidgetType"/>
            <bc:column field="status" title="状态" width="100" formatter="fixedDict:Status"/>
            <bc:column field="desc" title="组件描述" width="100"/>
            <bc:operator id="edit" icon="edit" title="编辑" visible="editVisible"/>
            <bc:operator id="remove" icon="trash" title="移除" visible="removeVisible"/>
            <bc:operator id="view" icon="file-o" title="查看"/>
            <be:event type="onClickRow">onClickRow</be:event>
            <be:event type="onCheck">onClickRow</be:event>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function onClickRow(row) {
        if (row['status'] == 'DRAFT') {
            $('#dashWidgetTable_tb_enable').attr('disabled', false);
            $('#dashWidgetTable_tb_dataSource').attr('disabled', false);
            $('#dashWidgetTable_tb_design').attr('disabled', false);
            $('#dashWidgetTable_tb_disable').attr('disabled', true);
        } else if (row['status'] == 'ENABLED') {
            if (row['type'] == 'CUSTOM') {
                $('#dashWidgetTable_tb_design').attr('disabled', true);
            } else {
                $('#dashWidgetTable_tb_design').attr('disabled', false);
            }
            $('#dashWidgetTable_tb_disable').attr('disabled', false);
            $('#dashWidgetTable_tb_dataSource').attr('disabled', true);
            $('#dashWidgetTable_tb_enable').attr('disabled', true);
        } else {
            $('#dashWidgetTable_tb_enable').attr('disabled', false);
            $('#dashWidgetTable_tb_dataSource').attr('disabled', true);
            $('#dashWidgetTable_tb_design').attr('disabled', true);
            $('#dashWidgetTable_tb_disable').attr('disabled', true);
        }
    }

    function editVisible(row) {
        if (row['status'] == 'DRAFT') {
            return true;
        }
        return false;
    }

    function removeVisible(row) {
        if (row['status'] == 'DRAFT') {
            return true;
        }
        return false;
    }

    /* 绑定数据源 */
    function dataProvider() {
        var rows = $("#dashWidgetTable").jTable("getSelections");
        if (rows.length == 0) {
            $.messager.toastr('提示', '请选择一个组件', 'warning');
        }
        /* 组件编码 */
        var code = rows[0].code;
        window.location = '${base}/sys/dashWidgetProvider/bingDataProviderProgram?componentCode=' + code;
    }

    /* 设计 */
    function design() {
        var rows = $('#dashWidgetTable').jTable('getSelections');
        if (rows.length > 0) {
            $.mask.post('${base}/sys/dashWidgetProvider/verifyForDashWidgetProvider', {widgetCode: rows[0].code}, function (data) {
                if (data.success) {
                    window.location = '${base}/sys/dashWidget/design?code=' + rows[0].code;
                } else {
                    $.messager.toastr('提示', '请先绑定数据源', 'warning');
                }
            });
        } else {
            $.messager.toastr('提示', '请选择需要一个组件。');
        }

    }

    /* 启用 */
    function enable() {
        var rows = $('#dashWidgetTable').jTable('getSelections');
        if (rows.length > 0) {
            $.mask.post('${base}/sys/dashWidget/canEnable', {code: rows[0].code}, function (data) {
                if (data.success) {
                    $.mask.post('${base}/sys/dashWidget/enable', {code: rows[0].code}, function (data) {
                        if (data.success) {
                            $.messager.toastr('提示', data.msg, 'info');
                            $('#dashWidgetTable').jTable('refresh');
                        } else {
                            $.messager.toastr('错误', data.msg, 'error');
                        }
                    });
                } else {
                    $.messager.toastr('错误', '组件启动验证不通过，请重新设计组件。', 'error');
                }
            });
        } else {
            $.messager.toastr('提示', '请选择需要启用的组件。');
        }
    }

    /* 停用 */
    function disable() {
        var rows = $('#dashWidgetTable').jTable('getSelections');
        if (rows.length > 0) {
            $.mask.post('${base}/sys/dashWidget/disable', {code: rows[0].code}, function (data) {
                if (data.success) {
                    $.messager.toastr('提示', data.msg, 'info');
                    $('#dashWidgetTable').jTable('refresh');
                } else {
                    $.messager.toastr('错误', data.msg, 'error');
                }
            });
        } else {
            $.messager.toastr('提示', '请选择需要启用的组件。');
        }
    }
</script>
</body>
</html>