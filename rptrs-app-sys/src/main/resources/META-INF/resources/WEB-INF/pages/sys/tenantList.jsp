<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>租户表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="租户表" subTitle="请输入租户表查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="租户编码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_code" col="7" placeholder="租户编码"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="租户名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_name" col="7" placeholder="租户名称"/>
                    </bf:formGroup>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" type="primary" text="查询" icon="search"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="tenantTable" idField="id" url="${base}/sys/tenant">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="start" text="启动" type="button" handler="startHandler"/>
                <bc:toolbar id="stop" text="停止" type="button" handler="stopHandler"/>
                <bc:column field="code" title="编码" width="100"/>
                <bc:column field="name" title="名称" width="100"/>
                <bc:column field="status" title="状态" width="50" formatter="fixedDict:Status"/>
                <bc:column field="remark" title="备注" width="150"/>
                <bc:operator id="edit" icon="edit" title="编辑" visible="editVisible"/>
                <bc:operator id="remove" icon="trash" title="移除" visible="removeVisible"/>
                <be:event type="onClickRow">onClickRow</be:event>
                <be:event type="onCheck">onClickRow</be:event>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function onClickRow(row) {
        if (row['status'] == 'DRAFT') {
            $('#tenantTable_tb_start').attr('disabled', false);
            $('#tenantTable_tb_stop').attr('disabled', true);
        } else if (row['status'] == 'ENABLED') {
            $('#tenantTable_tb_start').attr('disabled', true);
            $('#tenantTable_tb_stop').attr('disabled', false);
        } else {
            $('#tenantTable_tb_start').attr('disabled', false);
            $('#tenantTable_tb_stop').attr('disabled', true);
        }
    }

    //编辑先验证
    function editVisible(row) {
        if (row['status'] == 'DRAFT') {
            return true;
        }
        return false;
    }

    //删除时先验证
    function removeVisible(row) {
        if (row['status'] == 'DRAFT') {
            return true;
        }
        return false;
    }

    //启动任务
    function startHandler() {
        var rows = $('#tenantTable').jTable('getCheckData');
        if (rows.length > 0) {
            $.messager.confirm('提示', '确定要启动该租户吗？', function (r) {
                if (r) {
                    $.mask.post('${base}/sys/tenant/start/' + rows[0].id, function (data) {
                        if (data.success) {
                            $('#tenantTable').jTable('refresh');
                            $.messager.toastr('提示', data.msg, 'info')
                        } else {
                            $.messager.toastr('错误', data.msg, 'error');
                        }
                    });
                }
            });
        } else {
            $.messager.toastr('提示', '请先选择需要启动的记录。', 'error');
        }
    }

    //停止任务
    function stopHandler() {
        var rows = $('#tenantTable').jTable('getCheckData');
        if (rows.length > 0) {
            $.messager.confirm('提示', '确定要停止该租户吗？', function (r) {
                if (r) {
                    $.mask.post('${base}/sys/tenant/stop/' + rows[0].id, function (data) {
                        if (data.success) {
                            $('#tenantTable').jTable('refresh');
                            $.messager.toastr('提示', data.msg, 'info')
                        } else {
                            $.messager.toastr('错误', data.msg, 'error');
                        }
                    });
                }
            });
        } else {
            $.messager.toastr('提示', '请先选择需要启动的记录。', 'error');
        }
    }
</script>
</body>
</html>