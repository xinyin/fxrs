<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>多部门角色管理</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title=""  col="12" collapsible="false">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="4">
                    <bc:label text="用户编码" col="4" formControl="true"/>
                    <bf:text name="__LIKES_staffCode_" col="8" placeholder="用户编码"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="所属部门" col="4" formControl="true"/>
                    <bf:text name="__LIKES_deptName_" col="8" placeholder="所属部门"/>
                </bc:col>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="approveMapTable" idField="pid" url="${base}/sys/approveMap">
            <bc:toolbar id="add" text="新增" type="button"/>
            <bc:column field="pid" title="编码" width="100" visible="false"/>
            <bc:column field="staffCode_" title="用户编码" width="100"/>
            <bc:column field="staffName_" title="用户名称" width="100"/>
            <bc:column field="deptCode_" title="部门编码" width="100"/>
            <bc:column field="deptName_" title="部门名称" width="100"/>
            <bc:column field="roleCode_" title="角色编码" width="100"/>
            <bc:column field="roleName_" title="角色名称" width="100"/>
            <bc:column field="roleStdCode_" title="标准角色编码" width="100"/>
            <bc:operator id="edit" icon="edit" title="编辑"/>
            <bc:operator id="remove" icon="trash" title="移除"/>
            <bc:operator id="view" icon="file-o" title="查看"/>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function getIdSelections() {
        return $.map($('#approveMapTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;//返回数据行中的id值
        });
    }
</script>
</body>
</html>