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
<br:css pack="list"/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bc:table id="traBusinessTable" idField="pid" url="${base}/ins/traBusiness" queryUrl="transBalanceQuery/${name}">
				<bc:column field="pid" title="序号" width="100" visible="false"/>
				<bc:column field="relId" title="关联方ID" width="100" visible="false"/>
				<bc:column field="relType" title="交易对手类型" width="100"
						   formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
				<bc:column field="relName" title="交易对手名称" width="100" />
				<bc:column field="relNumber" title="关联方证件号码" width="100"/>
				<bc:column field="tradeOrg" title="交易机构" width="100"
						   formatter="custom:sys/unit/selectUnit"/>
				<bc:column field="tranType" title="交易类型" width="100"
						   formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
				<bc:column field="tradeAmount" title="交易金额（万元/人民币）" width="100"/>
				<bc:column field="majorTrade" title="是否重大关联交易" width="100"
						   formatter="fixedDict:IsStatusDict"/>
			</bc:table>
			<bc:button type="primary" text="返回" icon="arrow-circle-left">
				<be:event>
					window.location = '${neo4jUrl}';
				</be:event>
			</bc:button>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>