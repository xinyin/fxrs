<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>仪表盘数据提供源</title>
</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title="仪表盘数据提供源" subTitle="" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="4">
                    <bc:label text="查询名称" col="4" formControl="true"/>
                    <bf:text name="__LIKES_queryName" col="8" placeholder="查询名称"/>
                </bc:col>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="dataProviderTable" idField="queryId" url="${base}/sys/dataProvider">
            <bc:toolbar id="addDataSource" text="同步数据源" type="button" handler="add"/>
            <bc:column field="queryName" title="查询名称" width="100"/>
            <bc:column field="unitName" title="机构名称" width="100"/>
            <bc:column field="deptName" title="部门名称" width="100"/>
            <bc:column field="staffName" title="用户名称" width="100"/>
            <bc:operator id="view" icon="search" title="查看"/>
            <bc:operator id="remove" icon="trash" title="移除"/>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    /* 新增 */
    function add() {
        $.messager.modal({
            winId: 'addDataSource',
            width: 800,
            height: 400,
            title: '同步数据源',
            url: base + '/sys/dataProvider/sync',
            onClose: function () {
                $('#dataProviderTable').bootstrapTable('refresh');
            }
        });
    }
</script>
</body>
</html>