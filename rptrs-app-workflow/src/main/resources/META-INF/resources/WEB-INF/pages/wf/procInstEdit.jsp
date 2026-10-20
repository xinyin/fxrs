<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<link rel="stylesheet" type="text/css" href="${base}/static/workflow/css/jFlowForm/jFlowForm.css">
<title>流程提交</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox id="iboxform" title="" col="12" collapsible="false">
			<bf:form id="workflowform" method="post" validation="true">
				${content}
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
							<bc:button type="primary" id="button"  text="保存" icon="save">
								<be:event>save();</be:event>
							</bc:button>
							<bc:button type="primary" id="button"  text="保存并提交" icon="save">
								<be:event>saveAndStart();</be:event>
							</bc:button>
							<bc:button  text="返回">
							<be:event>
								window.location = '${base}/workflow/process/draftList?passToken=${passToken}';
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
<script src="${base}/static/workflow/js/jFlowForm/jFlowForm.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jFlowForm" data-proc-inst-id="${procInstId}"  data-proc-def-code="${procDefCode}" data-form-id="workflowform" data-form-data='${oldData}'></div>
<script type="text/javascript">
var procDefCode = '${procDefCode}';
var oldData = '${oldData}';
var procInstId = '${procInstId}';
var status = '${status}';

$(document).ready(function(){
	
});

//表单提交 , 先执行验证表单事件
function save(){
	$('#workflowform').data('formValidation').validate();
	var isValid = $('#workflowform').data('formValidation').isValid();
	if(isValid)
	{
		/*调用用户定义的表单提交事件*/
		$('#flowForm').jFlowForm('onFormSubmit');
		/*获取表单数据*/
		var data = $('#flowForm').jFlowForm('getFormData');
		var formData = data.formData;
		
		
		$.mask.post('${base}/workflow/process/save', 
			{
				procDefCode: procDefCode, 
				oldData: oldData, 
				newData: formData,
				uploadSessions: data.uploadSessions,
				procInstId: procInstId,
				status : status,
				passToken: '${passToken}'
			},
			function(data){
				if(data.success)
				{
					console.info(data.procInstId);
					$.messager.alert('提示', '保存成功。', 'info', function(){
						window.location = '${base}/workflow/process/draftList?passToken=${passToken}';
					});
				}
				else
				{
					$.messager.alert('错误', data.msg, 'error');
				}
			}
		);
	}
}

//表单提交 , 先执行验证表单事件
function saveAndStart(){
	$('#workflowform').data('formValidation').validate();
	var isValid = $('#workflowform').data('formValidation').isValid();
	if(isValid)
	{
		/*调用用户定义的表单提交事件*/
		$('#flowForm').jFlowForm('onFormSubmit');
		
		/*获取表单数据*/
		var data = $('#flowForm').jFlowForm('getFormData');
		var formData = data.formData;
		var obj = {
				procDefCode: procDefCode, 
				oldData: oldData, 
				newData: formData,
				uploadSessions: data.uploadSessions,
				procInstId: procInstId,
				passToken: '${passToken}'
		}
		$.mask.post('${base}/workflow/process/saveAndStart', obj, function(data){
			if(data.success)
			{
				$.messager.alert('提示', '保存并提交成功。', 'info', function(){
					window.location = '${base}/workflow/process/runingList?passToken=${passToken}';
				});
			}
			else
			{
				$.messager.alert('错误', data.msg, 'error');
			}
		});
		
	}
}
</script>
</body>
</html>