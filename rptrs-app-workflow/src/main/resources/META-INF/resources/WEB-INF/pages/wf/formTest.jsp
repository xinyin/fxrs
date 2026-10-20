<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<link rel="stylesheet" type="text/css" href="${base}/static/workflow/css/jFlowForm/jFlowForm.css">
<title>业务模块管理</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox id="iboxform" title="预览" col="12" collapsible="false">
			<bf:form id="workflowform" method="post" validation="true">
				${content}
				<bc:button id="_submit_" text="提交" click="onSubmit();"/>
			</bf:form>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jFlowForm/jFlowForm.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jFlowForm" data-form-def-code="${formDefCode}" data-form-data='${formData}'
	 data-form-id="workflowform"></div>
<script type="text/javascript">
function onSubmit(event)
{
	/*调用用户定义的表单提交事件*/
	$('#flowForm').jFlowForm('onFormSubmit');
	
	var valid = $('#workflowform').data('formValidation').validate();
	console.log(valid);
	if(!valid) return;
	
	/*获取表单数据*/
	var data = $('#flowForm').jFlowForm('getFormData');
	console.log(data);
	console.log($.helper.base64Decode(data.oldData));
	console.log($.helper.base64Decode(data.formData));
	console.log($.helper.base64Decode(data.uploadSessions));
}
</script>
</body>
</html>