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
		<bc:ibox id="iboxform" title="" col="12" collapsible="false">
			<bf:form id="workflowform" method="post" validation="true">
				${content}
				<bf:formGroup>
					<div align="center">
							<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
<%--								window.location = '${base}/workflow/${option}';--%>
								window.history.back(-1);
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
<script type="text/javascript">
function viewDetails(instId, propCode){
	$.messager.modal({
		url : "${base}/workflow/formProp/viewDetails?instId=" + instId + "&propCode=" + propCode,
		closable : true,
		width : 800,
		height : 500,
		title : '数据变动内容',
		winId: 'w_viewDetails',
		args: {instId : instId, propCode : propCode}
	});
}
//iframe自适应高度
$(function (){
	var ifm= document.getElementById("myFrame");
	ifm.height=document.documentElement.clientHeight;
})
</script>
<div id="flowForm" data-toggle="jFlowForm" data-proc-inst-id="${procInstId}" data-form-id="workflowform"></div>
</body>
</html>