<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>调度任务实例</title>
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
					<bc:label text="${jobInst.id}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="任务编码" col="2" formControl="true"/>
					<bc:label text="${jobInst.jobCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="任务名称" col="2" formControl="true"/>
					<bc:label text="${jobInst.jobName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="任务类型" col="2" formControl="true"/>
					<bc:label text="${jobInst.groupCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="任务参数:JSON字符串。" col="2" formControl="true"/>
					<bc:label text="${jobInst.param}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="节点编码" col="2" formControl="true"/>
					<bc:label text="${jobInst.nodeCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="集群编码" col="2" formControl="true"/>
					<bc:label text="${jobInst.clusterCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="开始时间" col="2" formControl="true"/>
					<bc:label text="${jobInst.startTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="结束时间" col="2" formControl="true"/>
					<bc:label text="${jobInst.endTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="执行时间" col="2" formControl="true"/>
					<bc:label text="${jobInst.execTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="状态:             NEW - 新建             NOTIFYING - 通知中             EXECUTING  执行中             DONE - 执行完成             ERROR - 执行失败" col="2" formControl="true"/>
					<bc:label text="${jobInst.status}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="触发器" col="2" formControl="true"/>
					<bc:label text="${jobInst.triggerKey}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="上次触发器" col="2" formControl="true"/>
					<bc:label text="${jobInst.lastTriggerKey}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="计划触发时间" col="2" formControl="true"/>
					<bc:label text="${jobInst.scheduledFireTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="实例ID" col="2" formControl="true"/>
					<bc:label text="${jobInst.serverInstanceId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="">
							<be:event>
								window.location = '${base}/sch/jobInst/list';
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