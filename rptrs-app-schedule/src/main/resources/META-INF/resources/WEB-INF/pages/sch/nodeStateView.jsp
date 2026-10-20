<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>集群节点状态表</title>
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
					<bc:label text="节点编码" col="2" formControl="true"/>
					<bc:label text="${nodeState.code}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="节点名称" col="2" formControl="true"/>
					<bc:label text="${nodeState.name}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="集群编码" col="2" formControl="true"/>
					<bc:label text="${nodeState.clusterCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="集群名称" col="2" formControl="true"/>
					<bc:label text="${nodeState.clusterName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="IP地址" col="2" formControl="true"/>
					<bc:label text="${nodeState.ipAddr}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="端口" col="2" formControl="true"/>
					<bc:label text="${nodeState.port}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="应用上下文" col="2" formControl="true"/>
					<bc:label text="${nodeState.webContext}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="状态:             ONLINE - 在线             CONNECTING - 连接中             OFFLINE - 断开" col="2" formControl="true"/>
					<bc:label text="${nodeState.status}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="运行任务数" col="2" formControl="true"/>
					<bc:label text="${nodeState.jobCount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="内存总数" col="2" formControl="true"/>
					<bc:label text="${nodeState.totalMemory}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="内存占用" col="2" formControl="true"/>
					<bc:label text="${nodeState.usedMemory}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="CPU占用" col="2" formControl="true"/>
					<bc:label text="${nodeState.cpuUsage}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="锁定:Y锁定/N未锁定" col="2" formControl="true"/>
					<bc:label text="${nodeState.lock}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="实例ID" col="2" formControl="true"/>
					<bc:label text="${nodeState.lockInstanceId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="锁定时间" col="2" formControl="true"/>
					<bc:label text="${nodeState.lockTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="超时时间" col="2" formControl="true"/>
					<bc:label text="${nodeState.lockExpireTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="租户" col="2" formControl="true"/>
					<bc:label text="${nodeState.tenantId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="">
							<be:event>
								window.location = '${base}/sch/nodeState/list';
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