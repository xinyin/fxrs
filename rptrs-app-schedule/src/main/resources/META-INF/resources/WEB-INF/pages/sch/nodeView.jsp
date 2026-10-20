<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>集群节点</title>
<script type="text/javascript">
	$(window).load(function() {
		jQuery.ajaxSetup({
			cache : false
		});
	});
</script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="true">
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="节点编码" col="6" formControl="true"/>
						<bc:label text="${node.code}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="节点名称" col="6" formControl="true"/>
					<bc:label text="${node.name}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="集群名称" col="6" formControl="true"/>
						<bc:label text="${node.clusterCode}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="IP地址" col="6" formControl="true"/>
						<bc:label text="${node.ipAddr}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="端口" col="6" formControl="true"/>
						<bc:label text="${node.port}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="应用上下文" col="6" formControl="true"/>
						<bc:label text="${node.webContext}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="状态" col="6" formControl="true"/>
					<bc:label text="fixedDict:Status:${node.status}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="备注" col="6" formControl="true"/>
						<bc:label text="${node.remark}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/sch/node/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
				</bf:form>
			</bc:ibox>
		</div>
	</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>