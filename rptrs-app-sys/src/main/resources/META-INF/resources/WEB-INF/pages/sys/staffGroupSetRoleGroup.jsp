<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>用户设定角色组</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxRoleGroup" title="${staffGroup.staffGroupName}" subTitle=" 选定角色组" col="12" collapsible="true">
            <bc:table id="roleGroupTable" idField="roleGroupCode" multiple="true"
                      url="${base}/sys/roleGroup" selectFirst="false" initCheckData="${roleGroups}">
                <bc:toolbar id="finished" text="完成设置" type="button" handler="finishHandler"/>
                <bc:toolbar id="backward" text="返回" type="button" icon="arrow-circle-left" handler="backwardHandler"/>
                <bc:column field="roleGroupCode" title="角色组编码" width="100"/>
                <bc:column field="roleGroupName" title="角色组名称" width="100"/>
                <bc:column field="roleGroupDesc" title="角色组描述" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    /* 保存 */
    function finishHandler() {
        var data = $('#roleGroupTable').jTable('getCheckData');
        var obj = {
            jsonRoleGroupList: JSON.stringify(data),
            staffGroupCode: '${staffGroup.staffGroupCode}'
        }

        $.mask.post('${base}/sys/staffGroupRoleGroupMap/saveRoleGroup', obj, function (data) {
            if (data.success) {
                $.messager.alert('提示', '设置成功。', 'info', function () {
                    window.location = '${base}/sys/staffGroup/list';
                });
            } else {
                $.messager.alert('错误', data.msg, 'error');
            }
        });
    }

    /* 返回 */
    function backwardHandler() {
        window.location = '${base}/sys/staffGroup/list';
    }
</script>
</body>
</html>