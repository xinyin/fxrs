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
				<iframe style="width: 100%; border: 0;"  id="myFrame" name="myFrame" src="${clientAdd}/workflow/client/bizEdit?token=${token}" col="12" ></iframe>
				<bf:formGroup>
					<div align="center">
							<bc:button  text="返回" icon="arrow-circle-left">
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
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
window.addEventListener('message',function(e){
	/* 判断是不是监听的工作流相关数据 */
	/**获取列表对象*/
	if('workflowFrameH' == e.data.type){
		var myFrame=document.getElementById('myFrame');
		console.log(e.data.height >500)
		if(e.data.height >500) {
			myFrame.style.height = e.data.height + 'px';
		}else {
			myFrame.style.height = 500 + 'px';
		}
	}

},false);
//iframe自适应高度
$(function (){
	/*var ifm= document.getElementById("myFrame");
	ifm.height=document.documentElement.clientHeight;*/
})
</script>
</body>
</html>