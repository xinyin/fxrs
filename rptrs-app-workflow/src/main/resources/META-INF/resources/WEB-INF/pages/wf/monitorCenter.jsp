<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>流程实例表</title>
<x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:tabs id="tabMonitorCenter" lazy="true">
			<bc:tab id="tabDone" title="完成列表" visible="true" href="${base }/workflow/monitor/doneList">
			</bc:tab>
			<bc:tab id="tabNotify" title="待接收数据流程" href="${base }/workflow/monitor/notifyDataErrorList" >
			</bc:tab>
			<bc:tab id="tabLongTime" title="长时间无处理流程" href="${base }/workflow/monitor/longTimeList" >
			</bc:tab>
			<bc:tab id="tabNoCandidate" title="无处理人流程" href="${base }/workflow/monitor/noCandidateList" >
			</bc:tab>
		</bc:tabs>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>