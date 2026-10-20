<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>模型规则评分表</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="modelRuleScoreBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="id" name="id" value="${modelRuleScore.id}"/>
				<bf:hidden id="ruleId" name="ruleId" value="${ruleId == null ? modelRuleScore.ruleId : ruleId}"/>
				<bf:hidden id="modelId" name="modelId" value="${modelId == null ? modelRuleScore.modelId : modelId}"/>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="最小阈值" col="4" formControl="true"/>
						<bf:text name="minValue" id="minValue"  col="6" value="${modelRuleScore.minValue == null ? 0 : modelRuleScore.minValue}" />
					</bc:col>
					<bc:col col="6">
						<bc:label text="最大阈值" col="4" formControl="true"/>
						<bf:text name="maxValue" id="maxValue" col="6" value="${modelRuleScore.maxValue == null ? 0 : modelRuleScore.maxValue}" />
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="分值" col="4" formControl="true"/>
						<bf:touchpin name="score" id="score"  max="9999999" step="1" decimals="2" btnType="primary" col="8" value="${modelRuleScore.score  == null ? 0 : modelRuleScore.score}" verticalButtons="false"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="备注" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="10" value="${modelRuleScore.remark}" multiple="true"></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2" style="text-align: center;">
						<bc:button type="primary" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/rul/modelRuleScore/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												$.messager.hide('winScoreAdd');
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
						<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
								$.messager.hide('winScoreAdd');
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
