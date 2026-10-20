<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>用户定岗</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxRole" title="${staff.staffName}" subTitle="选定角色" col="12" collapsible="true">
            <bf:form id="queryForm">
                <bf:hidden name="__EQS_hierarchy" value="${hierarchy}"/>
            </bf:form>
            <bc:table id="roleTable" idField="roleCode" multiple="true" url="${base}/sys/role"
                      selectFirst="false" initCheckData="${roleList}">
                <bc:toolbar id="finished" text="完成设置" type="button" handler="finishHandler"/>
                <bc:toolbar id="backward" text="返回" type="button" icon="arrow-circle-left" handler="backwardHandler"/>
                <bc:column field="roleCode" title="角色编码" width="100"/>
                <bc:column field="roleName" title="角色名称" width="100"/>
                <bc:column field="roleDesc" title="角色描述" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    /* 保存 */
    function finishHandler() {
        var data = $('#roleTable').jTable('getCheckData');
        var obj = {
            jsonRoleList: JSON.stringify(data),
            unitCode: '${unitCode}',
            staffCode: '${staff.staffCode}'
        }

        $.mask.post('${base}/sys/duty/saveRoleStaff', obj, function (data) {
            if (data.success) {
                $.messager.alert('提示', '设置成功。', 'info', function () {
                    window.location = '${base}/sys/staff/list';
                });
            } else {
                $.messager.alert('错误', data.msg, 'error');
            }
        });
    }

    /* 返回 */
    function backwardHandler() {
        window.location = '${base}/sys/staff/list';
    }
</script>
</body>
</html>