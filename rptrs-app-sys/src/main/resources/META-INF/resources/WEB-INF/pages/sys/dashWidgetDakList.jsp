<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>组件授权</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="角色授权" subTitle="请输入相关查询信息" col="12" collapsible="true">
            <bf:hidden name="dakId" id="dakId"/>
            <bc:col col="4">
                <bc:table id="roleTable" idField="roleCode" url="${base}/sys/role">
                    <bc:column field="roleCode" title="角色编码" width="100"/>
                    <bc:column field="roleName" title="角色名称" width="100"/>
                    <be:event type="onClickRow">onClickRole</be:event>
                    <be:event type="onCheck">onClickRole</be:event>
                </bc:table>
            </bc:col>
            <bc:col col="4">
                <bc:table id="dashWidgetTable" idField="code" url="${base}/sys/dashWidget"
                          queryUrl="query?__LIKES_status=ENABLED" selectFirst="false">
                    <bc:column field="code" title="组件编码" width="100"/>
                    <bc:column field="name" title="组件名称" width="100"/>
                    <be:event type="onClickRow">onClickWidget</be:event>
                    <be:event type="onCheck">onClickWidget</be:event>
                </bc:table>
            </bc:col>
            <bc:col col="4">
                <bc:table id="permitSet" idField="value" url="${base}/sys/dashWidgetDak" queryUrl="permitTypes">
                    <bc:toolbar id="widgetPermit" text="确定" type="button" handler="widgetPermitHandler"/>
                    <bc:column field="value" title="数据权限代码" width="100"/>
                    <bc:column field="name" title="数据权限名称" width="100"/>
                </bc:table>
            </bc:col>
        </bc:ibox>

        <bc:ibox title="角色组授权" subTitle="请输入相关查询信息" col="12" collapsible="true">
            <bf:hidden name="dakIdGp" id="dakIdGp"/>
            <bc:col col="4">
                <bc:table id="roleGpTable" idField="roleGroupCode" url="${base}/sys/roleGroup">
                    <bc:column field="roleGroupCode" title="角色组编码" width="100"/>
                    <bc:column field="roleGroupName" title="角色组名称" width="100"/>
                    <be:event type="onClickRow">onClickRoleGp</be:event>
                    <be:event type="onCheck">onClickRoleGp</be:event>
                </bc:table>
            </bc:col>
            <bc:col col="4">
                <bc:table id="dashWidgetTableGp" idField="code" url="${base}/sys/dashWidget"
                          queryUrl="query?__LIKES_status=ENABLED" selectFirst="false">
                    <bc:column field="code" title="组件编码" width="100"/>
                    <bc:column field="name" title="组件名称" width="100"/>
                    <be:event type="onClickRow">onClickWidgetGp</be:event>
                    <be:event type="onCheck">onClickWidgetGp</be:event>
                </bc:table>
            </bc:col>
            <bc:col col="4">
                <bc:table id="permitSetGp" idField="value" url="${base}/sys/dashWidgetDak" queryUrl="permitTypes">
                    <bc:toolbar id="widgetPermitGp" text="确定" type="button" icon="" handler="widgetPermitHandlerGp"/>
                    <bc:column field="value" title="数据权限代码" width="100"/>
                    <bc:column field="name" title="数据权限名称" width="100"/>
                </bc:table>
            </bc:col>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    /* 选中角色记录 */
    function onClickRole(row) {
        var obj = {
            dakUsingCode: row.roleCode,
            dakMapType: 'ROLE'
        };
        $.mask.post('${base}/sys/dashWidgetDak/getWidgetData', obj, function (data) {
            $('#dashWidgetTable').jTable('initCheckData', data);
            $('#dashWidgetTable').bootstrapTable('refresh');
            onClickWidget(data[0]);
        });
    }

    /* 选中组件记录 */
    function onClickWidget(row) {
        var code;
        if (row) code = row.code;

        var selectedRole = $('#roleTable').jTable('getCheckData');
        var obj = {
            dakUsingCode: selectedRole[0].roleCode,
            widgetCode: code,
            dakMapType: 'ROLE'
        };
        $.mask.post('${base}/sys/dashWidgetDak/getPermitData', obj, function (data) {
            $('#permitSet').jTable('initCheckData', data.permitType);
            $('#permitSet').bootstrapTable('refresh');
            if (data.dakId)
                $("#dakId").val(data.dakId);
        });
    }

    /* 保存角色授权数据 */
    function widgetPermitHandler() {
        var selectedRole = $('#roleTable').jTable('getCheckData');
        var selectedWidget = $('#dashWidgetTable').jTable('getCheckData');
        var selectedDak = $('#permitSet').jTable('getCheckData');
        btnSave(selectedDak[0].value, selectedWidget[0].code, selectedRole[0].roleCode, 'ROLE');
    }

    /* 保存 */
    function btnSave(dakCode, widgetCode, dakUsingCode, dakMapType) {
        var obj = {
            dakId: $('#dakId').val(),
            dakCode: dakCode,
            widgetCode: widgetCode,
            dakUsingCode: dakUsingCode,
            dakMapType: dakMapType
        };
        $.mask.post('${base}/sys/dashWidgetDak/save', obj, function (data) {
            if (data.success) {
                $.messager.alert('提示', data.msg, 'info');
            } else {
                $.messager.alert('错误', data.msg, 'error');
            }
        });
    }

    /* 角色组授权 */

    /* 选中角色记录 */
    function onClickRoleGp(row) {
        var obj = {
            dakUsingCode: row.roleGroupCode,
            dakMapType: 'ROLEGROUP'
        };
        $.mask.post('${base}/sys/dashWidgetDak/getWidgetData', obj, function (data) {
            $('#dashWidgetTableGp').jTable('initCheckData', data);
            $('#dashWidgetTableGp').bootstrapTable('refresh');
            onClickWidgetGp(data[0]);
        });
    }

    /* 选中组件记录 */
    function onClickWidgetGp(row) {
        var code = '';
        if (row) code = row.code;

        var selectedRoleGp = $('#roleGpTable').jTable('getCheckData');
        var obj = {
            dakUsingCode: selectedRoleGp[0].roleGroupCode,
            widgetCode: code,
            dakMapType: 'ROLEGROUP'
        };
        $.mask.post('${base}/sys/dashWidgetDak/getPermitData', obj, function (data) {
            $('#permitSetGp').jTable('initCheckData', data.permitType);
            $('#permitSetGp').bootstrapTable('refresh');
            if (data.dakId)
                $("#dakIdGp").val(data.dakId);
        });
    }

    /* 保存角色组授权数据 */
    function widgetPermitHandlerGp() {
        var selectedRoleGp = $('#roleGpTable').jTable('getCheckData');
        var selectedWidget = $('#dashWidgetTableGp').jTable('getCheckData');
        var selectedDak = $('#permitSetGp').jTable('getCheckData');
        btnSave(selectedDak[0].value, selectedWidget[0].code, selectedRoleGp[0].roleGroupCode, 'ROLEGROUP');
    }
</script>
</body>
</html>