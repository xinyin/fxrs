<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>机构上报关系表</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title="上报管理" subTitle="" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="4">
                    <bc:label text="上报部门" col="4" formControl="true"/>
                    <bf:text name="__LIKES_deptName" col="8" placeholder="上报部门"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务类型" col="4" formControl="true"/>
                    <bf:text name="__LIKES_bizName" col="8" placeholder="上报业务类型"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="管理部门" col="4" formControl="true"/>
                    <bf:text name="__LIKES_bizName" col="8" placeholder="管理部门"/>
                </bc:col>
            </bc:row>
            <bc:row>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="bizDeptReportTable" idField="id" url="${base}/sys/bizDeptReport">
            <bc:toolbar id="add" text="新增" type="button"/>
            <bc:column field="deptName" title="部门名称" width="100"/>
            <bc:column field="bizName" title="部门业务种类名称" width="100"/>
            <bc:column field="managerDeptName" title="管理部门名称" width="100"/>
            <bc:column field="remark" title="备注" width="200"/>
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