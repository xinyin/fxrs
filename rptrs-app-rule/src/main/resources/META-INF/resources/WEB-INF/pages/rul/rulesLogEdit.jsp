<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>规则引擎的调用日志</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="rulesLogBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="主键" col="4" formControl="true"/>
						<bf:text id="id" name="id" col="8" value="${rulesLog.id}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/rul/rulesLog/check" name="id" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="模型ID" col="4" formControl="true"/>
						<bf:text id="pkgId" name="pkgId" col="8" value="${rulesLog.pkgId}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="模型名称" col="4" formControl="true"/>
						<bf:text id="pkgModel" name="pkgModel" col="8" value="${rulesLog.pkgModel}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易时间" col="4" formControl="true"/>
						<bf:datebox id="startTime" name="startTime" col="8" value="${rulesLog.startTime}"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="执行耗时(MS)" col="4" formControl="true"/>
						<bf:text id="exeTime" name="exeTime" col="8" value="${rulesLog.exeTime}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="请求内容" col="4" formControl="true"/>
						<bf:text id="reqData" name="reqData" col="8" value="${rulesLog.reqData}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/rul/rulesLog/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/rul/rulesLog/list';
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
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/rul/rulesLog/list';
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
<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
</body>
</html>
