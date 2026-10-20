<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<link rel="stylesheet" type="text/css" href="${base}/static/workflow/css/jFlowForm/jFlowForm.css">
<title>表单预览</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox id="iboxform" title="预览" col="12" collapsible="false">
			<bf:form id="workflowform" method="post" validation="true">
				${content}
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
							<bc:button type="primary" id="button"  text="保存" icon="save">
								<be:event>save();</be:event>
							</bc:button>
							<bc:button  text="返回">
							<be:event>
								window.location = '${base}/workflow/formDef/list';
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
<div id="flowForm" data-toggle="jFlowForm" data-form-def-code="${formCode}" data-form-id="workflowform"></div>
<script type="text/javascript">
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
		var formData = $.helper.base64Decode(data.formData);
		$.messager.modal({
			winId: 'workflowformDataView',
			width: 650,
			height: 200,
			title: '当前表单的数据',
			content: formData,
			buttons: [{
					text : '关闭',
					icon : 'times',
					type : 'default',
					value : false
			}]
		});
	}
}
</script>
</body>
</html>