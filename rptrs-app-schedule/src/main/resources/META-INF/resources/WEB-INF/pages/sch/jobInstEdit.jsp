<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>调度任务实例</title>

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
						<bc:label text="ID" col="4" formControl="true"/>
						<bf:text id="id" name="id" col="8" value="${jobInst.id}">
								<bv:remote url="${base}/sch/jobInst/check" name="id" message="ID"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务编码" col="4" formControl="true"/>
						<bf:text id="jobCode" name="jobCode" col="8" value="${jobInst.jobCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="任务名称" col="4" formControl="true"/>
						<bf:text id="jobName" name="jobName" col="8" value="${jobInst.jobName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务类型" col="4" formControl="true"/>
						<bf:text id="groupCode" name="groupCode" col="8" value="${jobInst.groupCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="任务参数:JSON字符串。" col="4" formControl="true"/>
						<bf:text id="param" name="param" col="8" value="${jobInst.param}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="节点编码" col="4" formControl="true"/>
						<bf:text id="nodeCode" name="nodeCode" col="8" value="${jobInst.nodeCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="集群编码" col="4" formControl="true"/>
						<bf:text id="clusterCode" name="clusterCode" col="8" value="${jobInst.clusterCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="开始时间" col="4" formControl="true"/>
						<bf:datebox id="startTime" name="startTime" col="8" foramt="date"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="结束时间" col="4" formControl="true"/>
						<bf:datebox id="endTime" name="endTime" col="8" foramt="date"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="执行时间" col="4" formControl="true"/>
						<bf:text id="execTime" name="execTime" col="8" value="${jobInst.execTime}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="状态:             NEW - 新建             NOTIFYING - 通知中             EXECUTING  执行中             DONE - 执行完成             ERROR - 执行失败" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${jobInst.status}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="触发器" col="4" formControl="true"/>
						<bf:text id="triggerKey" name="triggerKey" col="8" value="${jobInst.triggerKey}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="上次触发器" col="4" formControl="true"/>
						<bf:text id="lastTriggerKey" name="lastTriggerKey" col="8" value="${jobInst.lastTriggerKey}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="计划触发时间" col="4" formControl="true"/>
						<bf:text id="scheduledFireTime" name="scheduledFireTime" col="8" value="${jobInst.scheduledFireTime}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="实例ID" col="4" formControl="true"/>
						<bf:text id="serverInstanceId" name="serverInstanceId" col="8" value="${jobInst.serverInstanceId}">
						</bf:text>
					</bc:col>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary"  text="保存" icon="save">
							<be:event>
								$('#form1').data('formValidation').validate();
								var isValid = $('#form1').data('formValidation').isValid();
								if(isValid)
								{
									var data = $('#form1').serializeAll();
									$.mask.post('${base}/sch/jobInst/save', data, function(data){
										if(data.success)
										{
											$.messager.alert('提示', '保存成功。', 'info', function(){
												window.location = '${base}/sch/jobInst/list';
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
