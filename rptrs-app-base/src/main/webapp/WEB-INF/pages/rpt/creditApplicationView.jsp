
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <!-- 加载systemInfo配置文件 -->
    <fmt:setBundle basename="jep" var="sysInfo" />
    <!-- 读取配置值AppName，并赋值给变量appName -->
    <fmt:message key="reportUrl" var="reportUrl" bundle="${sysInfo}" />
    <br:css pack="list"/>
    <title>授信-用信情况明细报表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <%--<div class="row">--%>
        <%--<bc:ibox  col="12" title=""  collapsible="true">--%>
        <%--<bf:form id="queryForm" validation="true">--%>
        <%--<bc:row cls="m-b">--%>

    <%--</div>--%>
    <%--<bc:col col="3" colOffset="1">--%>
        <%--<bc:button id="btnReset" type="primary" text="返回" icon="refresh" click="btnReast()">--%>
        <%--</bc:button>--%>
    <%--</bc:col>--%>
    <%--</bc:row>--%>
    <%--</bf:form>--%>
    <%--</bc:ibox>--%>
    <iframe id="content" name="content" frameborder="0" style="width:100%;height: 500px" scrolling="yes" src="${reportUrl}report_creditApplication.rptdesign&startTime=${startTime}&endTime=${endTime}&manage_org=${manage_org}"></iframe>
</div>
<br:js pack="list"/>
<be:processEvent/>

<%--<script src="../../static/js/jquery.dialog.js"></script>--%>
<%--<script type="text/javascript">--%>
    <%--//返回--%>
    <%--function btnReast() {--%>

        <%--window.location = '${base}/report/design/creditApplication';--%>
    <%--}--%>
<%--</script>--%>
</body>
</html>
