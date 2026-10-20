<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>角色管理</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="roleBox" title="角色列表" subTitle="请输入角色的查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row>
                    <bf:formGroup col="4">
                        <bc:label text="角色编码" col="4" formControl="true"></bc:label>
                        <bf:text name="__LIKES_roleCode" col="8" placeholder="角色编码">
                            <bv:jepCode/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="角色名称" col="4" formControl="true"></bc:label>
                        <bf:text name="__LIKES_roleName" col="8" placeholder="角色名称"></bf:text>
                    </bf:formGroup>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="roleTable" idField="roleCode" url="${base}/sys/role">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:column field="roleCode" title="角色编码" width="100"/>
                <bc:column field="roleName" title="角色名称" width="100"/>
                <bc:column field="roleDesc" title="角色描述" width="100"/>
                <bc:column field="roleStdCode" title="角色类型" width="100" formatter="custom:sys/roleStd/queryAll"/>
                <bc:column field="status" title="状态" width="100" formatter="fixedDict:RoleStatus"/>
                <bc:operator id="edit" icon="edit" title="编辑"/>
                <bc:operator id="remove" icon="trash" title="移除"/>
                <bc:operator id="view" icon="newspaper-o" title="查看"/>
            </bc:table>
        </bc:ibox>

        <bc:ibox id="userBox" title="分配了该角色的人员列表" col="12" collapsible="true">
            <bc:table id="staffTable" idField="staffCode" url="${base}/sys/role" queryUrl="queryRoleStaff"
                      childField="roleCode" childFieldType="S" parentField="roleCode" parentId="roleTable"
                      parentType="table"
                      showColumns="false" showRefresh="false">
                <bc:column field="unitName" title="部门" width="100"/>
                <bc:column field="staffCode" title="用户编码" width="100"/>
                <bc:column field="staffName" title="用户名称" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>