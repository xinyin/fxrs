<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>机构代管关系表</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title="代管管理" subTitle="" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row cls="m-b">
                <bc:col col="4">
                    <bc:label text="部门" col="4" formControl="true"/>
                    <bf:text name="__LIKES_deptName" col="8" placeholder="部门"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="标准角色" col="4" formControl="true"/>
                    <bf:text name="__LIKES_stdRoleName" col="8" placeholder="标准角色"/>
                </bc:col>
            </bc:row>
            <bc:row cls="m-b">
                <bc:col col="4">
                    <bc:label text="代管部门" col="4" formControl="true"/>
                    <bf:text name="__LIKES_proxyDeptName" col="8" placeholder="代管部门"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="代管标准角色" col="4" formControl="true"/>
                    <bf:text name="__LIKES_proxyStdRoleName" col="8" placeholder="代管标准角色"/>
                </bc:col>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="bizDeptProxyTable" idField="id" url="${base}/sys/bizDeptProxy">
            <bc:toolbar id="add" text="新增" type="button"/>
            <bc:column field="deptName" title="部门" width="100"/>
            <bc:column field="stdRoleName" title="标准角色" width="100"/>
            <bc:column field="proxyDeptName" title="代管部门" width="100"/>
            <bc:column field="proxyStdRoleName" title="代管标准角色" width="100"/>
            <bc:column field="remark" title="备注" width="200"/>
            <bc:operator id="edit" icon="edit" title="编辑"/>
            <bc:operator id="remove" icon="trash" title="移除"/>
            <bc:operator id="view" icon="file-o" title="查看"/>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

</script>
</body>
</html>