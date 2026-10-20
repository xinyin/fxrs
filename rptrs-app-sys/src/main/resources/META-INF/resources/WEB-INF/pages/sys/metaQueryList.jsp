<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>元数据表的自定义查询</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="元数据表的自定义查询" subTitle="请输入元数据表的自定义查询查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row>
                    <bf:formGroup col="4">
                        <bc:label text="查询ID" col="5" formControl="true"/>
                        <bf:text name="__LIKES_queryId" col="7" placeholder="查询ID"/>
                    </bf:formGroup>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="metaQueryTable" idField="queryId" url="${base}/sys/metaQuery">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:column field="tableId" title="元数据ID" width="100"/>
                <bc:column field="queryId" title="查询ID" width="100"/>
                <bc:column field="queryName" title="查询名称" width="100"/>
                <bc:column field="queryType" title="公共查询：PUBLIC             私有查询：PRIVATE" width="100"/>
                <bc:column field="script" title="查询内容" width="100"/>
                <bc:column field="status" title="状态：             10 - 草稿             30 - 已生效             50 - 已失效"
                           width="100"/>
                <bc:column field="unitCode" title="机构编码" width="100"/>
                <bc:column field="unitName" title="机构名称" width="100"/>
                <bc:column field="deptCode" title="部门编码" width="100"/>
                <bc:column field="deptName" title="部门名称" width="100"/>
                <bc:column field="staffCode" title="用户编码" width="100"/>
                <bc:column field="staffName" title="用户名称" width="100"/>
                <bc:operator id="view" icon="search" title="查看"/>
                <bc:operator id="edit" icon="edit" title="编辑"/>
                <bc:operator id="remove" icon="trash" title="移除"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>