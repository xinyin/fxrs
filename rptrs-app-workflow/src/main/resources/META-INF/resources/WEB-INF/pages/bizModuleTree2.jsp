<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<link rel="stylesheet" type="text/css" href="/jep-app/static/workflow/css/jFlowDiagram/jFlowDiagram.css">
<title>业务模块表管理</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox id="iboxbizModule" title="业务模块表配置" col="12" collapsible="false">
			<bc:col col="2">
				
			</bc:col>
			<bc:col col="10">
				<div id="diagram" style="overflow:auto;" data-on-node-select='onNodeSelect' data-toggle="jFlowDiagram" data-code="testImpl"></div>
			</bc:col>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<script src="/jep-app/static/workflow/js/jFlowDiagram/jFlowDiagram.js" type="text/javascript"></script>
<be:processEvent/>
<script type="text/javascript">
function onNodeSelect(node){
	$.messager.alert(node.key);
}
</script>
</body>
</html>