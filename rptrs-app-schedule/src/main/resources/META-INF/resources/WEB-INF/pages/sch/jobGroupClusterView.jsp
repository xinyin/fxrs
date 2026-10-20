<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>任务类型集群表</title>
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
					<bc:label text="ID" col="2" formControl="true"/>
					<bc:label text="${groupCluster.id}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="类别编码" col="2" formControl="true"/>
					<bc:label text="${groupCluster.groupCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="服务器编码" col="2" formControl="true"/>
					<bc:label text="${groupCluster.clusterCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="">
							<be:event>
								window.location = '${base}/sch/groupCluster/list';
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