<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>集群节点状态表</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="父菜单编码" col="4" formControl="true"/>
						<bf:text name="parentId" col="8" disabled="true"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="父菜单名称" col="4" formControl="true"/>
						<bf:text name="parentName" col="8" disabled="true"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="节点编码" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="8" value="${nodeState.code}">
								<bv:remote url="${base}/sch/nodeState/check" name="code" message="节点编码"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="节点名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${nodeState.name}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="集群编码" col="4" formControl="true"/>
						<bf:text id="clusterCode" name="clusterCode" col="8" value="${nodeState.clusterCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="集群名称" col="4" formControl="true"/>
						<bf:text id="clusterName" name="clusterName" col="8" value="${nodeState.clusterName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="IP地址" col="4" formControl="true"/>
						<bf:text id="ipAddr" name="ipAddr" col="8" value="${nodeState.ipAddr}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="端口" col="4" formControl="true"/>
						<bf:text id="port" name="port" col="8" value="${nodeState.port}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="应用上下文" col="4" formControl="true"/>
						<bf:text id="webContext" name="webContext" col="8" value="${nodeState.webContext}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="状态:             ONLINE - 在线             CONNECTING - 连接中             OFFLINE - 断开" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${nodeState.status}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="运行任务数" col="4" formControl="true"/>
						<bf:text id="jobCount" name="jobCount" col="8" value="${nodeState.jobCount}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="内存总数" col="4" formControl="true"/>
						<bf:text id="totalMemory" name="totalMemory" col="8" value="${nodeState.totalMemory}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="内存占用" col="4" formControl="true"/>
						<bf:text id="usedMemory" name="usedMemory" col="8" value="${nodeState.usedMemory}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="CPU占用" col="4" formControl="true"/>
						<bf:text id="cpuUsage" name="cpuUsage" col="8" value="${nodeState.cpuUsage}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="锁定:Y锁定/N未锁定" col="4" formControl="true"/>
						<bf:text id="lock" name="lock" col="8" value="${nodeState.lock}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="实例ID" col="4" formControl="true"/>
						<bf:text id="lockInstanceId" name="lockInstanceId" col="8" value="${nodeState.lockInstanceId}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="锁定时间" col="4" formControl="true"/>
						<bf:datebox id="lockTime" name="lockTime" col="8" format="date"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="超时时间" col="4" formControl="true"/>
						<bf:datebox id="lockExpireTime" name="lockExpireTime" col="8" format="date"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="租户" col="4" formControl="true"/>
						<bf:text id="tenantId" name="tenantId" col="8" value="${nodeState.tenantId}">
						</bf:text>
					</bc:col>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="保存" icon="save">
							<be:event>
								$('#form1').data('formValidation').validate();
								var isValid = $('#form1').data('formValidation').isValid();
								if(isValid)
								{
									var data = $('#form1').serializeAll();
									$.mask.post('${base}/sch/nodeState/save', data, function(data){
										if(data.success)
										{
											$.messager.alert('提示', '保存成功。', 'info', function(){
												window.location = '${base}/sch/nodeState/list';
											});
										}
										else
										{
											$.messager.alert('错误', data.msg, 'error');
										}
									});
								}
							</be:event>
						</bc:button>
						<bc:button  text="返回">
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
