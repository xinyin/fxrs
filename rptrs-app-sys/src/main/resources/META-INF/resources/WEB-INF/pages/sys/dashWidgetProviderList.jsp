<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>组件所包含的数据提供程序，可能有多个。</title>
</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title="数据提供程序" subTitle="请输入组件所包含的数据提供程序，可能有多个。查询信息" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="4">
                    <bc:label text="提供程序ID" col="5" formControl="true"/>
                    <bf:text name="__LIKES_provierId" col="7" placeholder="提供程序ID"/>
                </bc:col>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="dashWidgetProviderTable" idField="provierId" url="${base}/sys/dashWidgetProvider">
            <bc:toolbar id="add" text="新增" type="button"/>
            <bc:column field="provierId" title="提供程序ID" width="100"/>
            <bc:column field="providerCode" title="数据提供程序编码" width="100"/>
            <bc:column field="providerParams" title="数据提供程序参数，JSON 格式，不同的数据提供程序有不同格式。" width="100"/>
            <bc:column field="widgetCode" title="组件编码" width="100"/>
            <bc:column field="beenUsed" title="是否被引用，Y/N。一旦被引用，该数据提供程序将不能被移除。" width="100"/>
            <bc:column field="tenantId" title="租户" width="100"/>
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