<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>用户组包含的用户</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="用户组列表" subTitle="请输入用户组查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="用户组编码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_staffGroupCode" col="7" placeholder="用户组编码">
                            <bv:jepCode/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="用户组名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_staffGroupName" col="7" placeholder="用户组名称"/>
                    </bf:formGroup>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="staffGroupTable" idField="staffGroupCode" url="${base}/sys/staffGroup">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="setStaff" text="添加用户" type="button" handler="setStaffHandler"/>
                <bc:toolbar id="setRole" text="关联角色" type="button" handler="setRoleHandler"/>
                <bc:toolbar id="setRoleGroup" text="关联角色组" type="button" handler="setRoleGroupHandler"/>
                <bc:column field="staffGroupCode" title="用户组编码" width="100"/>
                <bc:column field="staffGroupName" title="用户组名称" width="100"/>
                <bc:column field="staffGroupDesc" title="用户组描述" width="100"/>
                <bc:operator id="edit" icon="edit" title="编辑"/>
                <bc:operator id="remove" icon="trash" title="移除"/>
                <bc:operator id="view" icon="file-o" title="查看"/>
            </bc:table>
        </bc:ibox>
        <bc:ibox title="包含的用户" col="12" collapsible="true">
            <bc:table id="staffTable" idField="staffCode" url="${base}/sys/staffGroupMap" queryUrl="queryStaff"
                      childField="staffGroupCode" childFieldType="S" showColumns="false" showRefresh="false"
                      parentField="staffGroupCode" parentId="staffGroupTable" parentType="table">
                <bc:column field="staffCode" title="用户编码" width="100"/>
                <bc:column field="staffName" title="用户名称" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function setStaffHandler() {
        var row = $('#staffGroupTable').bootstrapTable('getSelections');
        if (row.length != 0) {
            window.location = '${base}/sys/staffGroupMap/setStaffMap?staffGroupCode=' + row[0].staffGroupCode;
        } else {
            $.messager.toastr('提示', '请选择一个用户组');
        }
    }

    /* 关联角色 */
    function setRoleHandler() {
        var row = $('#staffGroupTable').bootstrapTable('getSelections');
        if (row.length != 0) {
            window.location = '${base}/sys/staffGroupRoleMap/setRoleMap?staffGroupCode=' + row[0].staffGroupCode;
        } else {
            $.messager.toastr('提示', '请选择一个用户组');
        }
    }

    /* 关联角色组 */
    function setRoleGroupHandler() {
        var row = $('#staffGroupTable').bootstrapTable('getSelections');
        if (row.length != 0) {
            window.location = '${base}/sys/staffGroupRoleGroupMap/setRoleGroupMap?staffGroupCode=' + row[0].staffGroupCode;
        } else {
            $.messager.toastr('提示', '请选择一个用户组');
        }
    }

</script>
</body>
</html>