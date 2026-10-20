<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>内部交易额度预警规则</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="warnConfigBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="pid" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${warnConfig.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/warnConfig/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="预警指标" col="4" formControl="true"/>
						<bf:text id="warningIndex" name="warningIndex" col="8" value="${warnConfig.warningIndex}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="一级预警阈值" col="4" formControl="true"/>
						<bf:text id="1lwarnThreshold" name="1lwarnThreshold" col="8" value="${warnConfig.1lwarnThreshold}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="二级预警阈值" col="4" formControl="true"/>
						<bf:text id="2lwarnThreshold" name="2lwarnThreshold" col="8" value="${warnConfig.2lwarnThreshold}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="三级预警阈值" col="4" formControl="true"/>
						<bf:text id="3lwarnThreshold" name="3lwarnThreshold" col="8" value="${warnConfig.3lwarnThreshold}">
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
									$.mask.post('${base}/bus/warnConfig/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/warnConfig/list';
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
								window.location = '${base}/bus/warnConfig/list';
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
