<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>授权管理</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="授权管理" subTitle="授权菜单、功能及数据权限" col="12" collapsible="true">
            <bc:table id="roleTable" idField="roleCode" url="${base}/sys/role">
                <bc:toolbar id="grantMenu" text="授权菜单" type="button" handler="authorizeMenuToRole"/>
                <bc:column field="roleCode" title="角色编码" width="100"/>
                <bc:column field="roleName" title="角色名称" width="100"/>
                <bc:column field="roleDesc" title="角色描述" width="100"/>
                <bc:column field="roleStdCode" title="角色类型" width="100" formatter="custom:sys/roleStd/queryAll"/>
                <bc:column field="status" title="状态" width="100" formatter="fixedDict:RoleStatus"/>
            </bc:table>
            <bc:table id="roleGroupTable" idField="roleGroupCode" url="${base}/sys/roleGroup">
                <bc:toolbar id="grantMenu" text="授权菜单" type="button" handler="authorizeMenuToRoleGp"/>
                <bc:column field="roleGroupCode" title="角色组编码" width="100"/>
                <bc:column field="roleGroupName" title="角色组名称" width="100"/>
                <bc:column field="hierarchy" title="角色组层级" width="100" formatter="fixedDict:InsUnitHierarchy"/>
                <bc:column field="roleGroupDesc" title="角色组描述" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    /* 为角色授权菜单 */
    function authorizeMenuToRole() {
        var rows = $('#roleTable').bootstrapTable('getSelections');
        window.location = '${base}/sys/authorization/authorizeMenu?code='
            + rows[0].roleCode + '&name=' + rows[0].roleName + '&type=ROLE';
    }

    /* 为角色组授权菜单 */
    function authorizeMenuToRoleGp() {
        var rows = $('#roleGroupTable').bootstrapTable('getSelections');
        window.location = '${base}/sys/authorization/authorizeMenu?code='
            + rows[0].roleGroupCode + '&name=' + rows[0].roleGroupName + '&type=ROLEGROUP';
    }
</script>
</body>
</html>