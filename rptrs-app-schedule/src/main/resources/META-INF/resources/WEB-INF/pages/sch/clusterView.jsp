<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>集群管理</title>
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
				<bf:formGroup >
					<bc:label text="服务器编码" col="2" formControl="true"/>
					<bc:label text="${cluster.code}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup >
					<bc:label text="服务器名称" col="2" formControl="true"/>
					<bc:label text="${cluster.name}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup >
					<bc:label text="备注" col="2" formControl="true"/>
					<bc:label text="${cluster.remark}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button  text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/sch/cluster/list';
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