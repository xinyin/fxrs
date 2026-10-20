<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>角色组管理</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="roleGroupBox" title="角色组列表" subTitle="请输入角色组的查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row>
                    <bf:formGroup col="4">
                        <bc:label text="角色组编码" col="5" formControl="true"></bc:label>
                        <bf:text name="__LIKES_roleGroupCode" col="7" placeholder="角色组编码">
                            <bv:jepCode/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="角色组名称" col="5" formControl="true"></bc:label>
                        <bf:text name="__LIKES_roleGroupName" col="7" placeholder="角色组名称"></bf:text>
                    </bf:formGroup>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="roleGroupTable" idField="roleGroupCode" url="${base}/sys/roleGroup">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="setRole" text="设置包含的角色" type="button" handler="setRoleHandler"/>
                <bc:column field="roleGroupCode" title="角色组编码" width="100"/>
                <bc:column field="roleGroupName" title="角色组名称" width="100"/>
                <bc:column field="hierarchy" title="角色组层级" width="100" formatter="fixedDict:InsUnitHierarchy"/>
                <bc:column field="roleGroupDesc" title="角色组描述" width="100"/>
                <bc:operator id="edit" icon="edit" title="编辑"/>
                <bc:operator id="remove" icon="trash" title="移除"/>
                <bc:operator id="view" icon="file-o" title="查看"/>
            </bc:table>
        </bc:ibox>

        <bc:ibox id="roleBox" title="角色组包含的角色" col="12" collapsible="true">
            <bc:table id="roleMapTable" idField="roleGroupCode" url="${base}/sys/role" queryUrl="queryByRoleGroupCode"
                      childField="roleGroupCode" childFieldType="S" multipleVisible="false" showColumns="false"
                      showRefresh="false"
                      parentType="table" parentId="roleGroupTable" parentField="roleGroupCode">
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
    function setRoleHandler() {
        var row = $('#roleGroupTable').bootstrapTable('getSelections');
        window.location = '${base}/sys/roleGroupMap/setRoleMap?roleGroupCode=' + row[0].roleGroupCode + '&hierarchy=' + row[0].hierarchy;
    }

</script>
</body>
</html>