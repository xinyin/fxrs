<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>集群节点</title>

</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title="节点管理" subTitle="请输入节点管理查询信息" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="4">
                    <bc:label text="节点编码" col="4" formControl="true"/>
                    <bf:text name="__LIKES_code" col="8" placeholder="节点编码"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="节点名称" col="4" formControl="true"/>
                    <bf:text name="__LIKES_name" col="8" placeholder="节点名称"/>
                </bc:col>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="nodeTable" idField="code" url="${base}/sch/node" detailView="false">
            <bc:toolbar id="add" text="新增" type="button"/>
            <bc:toolbar id="enable" text="启用" type="button" handler="enabledHandler"/>
            <bc:toolbar id="disable" text="停用" type="button" handler="disableHandler"/>
            <bc:column field="clusterCode" title="集群编码" width="100"/>
            <bc:column field="code" title="节点编码" width="100"/>
            <bc:column field="name" title="节点名称" width="100"/>
            <bc:column field="ipAddr" title="IP地址" width="100"/>
            <bc:column field="port" title="端口" width="100"/>
            <bc:column field="webContext" title="应用上下文" width="100"/>
            <bc:column field="status" title="状态" width="100" formatter="fixedDict:Status"/>
            <bc:column field="remark" title="备注" width="100"/>
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
            $('#nodeTable_tb_enable').attr('disabled', false);
            $('#nodeTable_tb_disable').attr('disabled', true);
        } else if ((row['status'] == 'ENABLED')) {
            $('#nodeTable_tb_enable').attr('disabled', true);
            $('#nodeTable_tb_disable').attr('disabled', false);
        } else {
            $('#nodeTable_tb_enable').attr('disabled', false);
            $('#nodeTable_tb_disable').attr('disabled', true);
        }
    }

    //启用
    function enabledHandler() {
        var tables = $('#nodeTable').jTable('getCheckData');
        if (tables.length > 0) {
            $.messager.confirm('提示', '确定要启动该记录吗？', function (r) {
                if (r) {
                    $.mask.post('${base}/sch/node/enable/' + tables[0].code, function (data) {
                        if (data.success) {
                            $('#nodeTable').jTable('refresh');
                            $.messager.alert('提示', data.msg, 'info')
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                }
            });
        } else {
            $.messager.toastr('提示', '请先选择需要启动的记录。', 'error');
        }
    }

    //停用
    function disableHandler() {
        var tables = $('#nodeTable').jTable('getCheckData');
        if (tables.length > 0) {
            $.messager.confirm('提示', '确定要停用该记录吗？', function (r) {
                if (r) {
                    $.mask.post('${base}/sch/node/disable/' + tables[0].code, function (data) {
                        if (data.success) {
                            $('#nodeTable').jTable('refresh');
                            $.messager.alert('提示', data.msg, 'info')
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                }
            });

        } else {
            $.messager.toastr('提示', '请先选择需要停用的记录。', 'error');
        }
    }

    //编辑先验证
    function editVisible(row) {
        if (row['status'] == 'ENABLED') {
            return false;
        }
        return true;
    }

    //删除时先验证
    function removeVisible(row) {
        if (row['status'] == 'ENABLED') {
            return false;
        }
        return true;
    }
</script>
</body>
</html>