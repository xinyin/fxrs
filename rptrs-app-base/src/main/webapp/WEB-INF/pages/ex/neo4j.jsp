<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 加载systemInfo配置文件 -->
<fmt:setBundle basename="jep" var="sysInfo" />
<!-- 读取配置值AppName，并赋值给变量appName -->
<fmt:message key="neo4jUrl" var="neo4jUrl" bundle="${sysInfo}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<title>图数据库</title>
<x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div id="chart">
        <iframe id="content" name="content" frameborder="0" style="width:100%;height:780px" scrolling="no"
                src="${neo4jUrl}/index.html?tenantId=${tenantId}"></iframe>
    </div>
</div>
</body>
</html>