<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>表单属性表</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<div style="height: 450px; width: 100%">
			${content}
			</div>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>
