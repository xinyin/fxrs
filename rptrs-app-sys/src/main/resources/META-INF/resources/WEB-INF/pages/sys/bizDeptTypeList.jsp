<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>部门业务种类关系表</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title="部门业务种类关系表" subTitle="请输入部门业务种类关系表查询信息" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="4">
                    <bc:label text="主键" col="4" formControl="true"/>
                    <bf:text name="__LIKES_deptTypeId" col="8" placeholder="主键"/>
                </bc:col>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="bizDeptTypeTable" idField="deptTypeId" url="${base}/sys/bizDeptType">
            <bc:toolbar id="add" text="新增" type="button"/>
            <bc:column field="deptTypeId" title="主键" width="100"/>
            <bc:column field="deptCode" title="机构编码" width="100"/>
            <bc:column field="deptName" title="部门名称" width="100"/>
            <bc:column field="bizTypeCode" title="业务种类编码" width="100"/>
            <bc:column field="bizTypeName" title="业务种类名称" width="100"/>
            <bc:column field="tenementId" title="租户" width="100"/>
            <bc:column field="bizDeptCode" title="所属部门编码" width="100"/>
            <bc:column field="bizDeptName" title="所属部门名称" width="100"/>
            <bc:operator id="edit" icon="edit" title="编辑"/>
            <bc:operator id="remove" icon="trash" title="移除"/>
            <bc:operator id="view" icon="file-o" title="查看"/>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>