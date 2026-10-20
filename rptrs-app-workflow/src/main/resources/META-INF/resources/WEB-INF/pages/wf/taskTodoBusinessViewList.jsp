<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>流程实例列表</title>
<x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="业务信息" subTitle="" col="12" collapsible="true">
			<iframe style="width: 100%; border: 0;"  id="myFrame" name="myFrame" src="${clientAdd}/workflow/client/bizEdit?token=${token}" col="12" ></iframe>
		</bc:ibox>
		<bc:ibox title="待审批列表信息" subTitle="" col="12" collapsible="true">
			<bc:table id="taskInstTable" idField="id" url="${base}/workflow/shortcut" queryUrl="viewData?procInstId=${procInstId}">
				<bc:column field="procCode" title="业务流程编码" width="100"/>
				<bc:column field="procName" title="业务流程名称" width="100"/>
				<bc:column field="moduleName" title="模块名称" width="100"/>
				<bc:column field="taskName" title="任务名称" width="100"/>
				<bc:column field="starterName" title="发起人" width="100"/>
				<bc:column field="startTimeString" title="开始时间" width="100"/>
				<bc:column field="status" title="状态" width="100" formatter="fixedDict:ProcInstStatus"/>
				<be:event type="onClickRow">onClickRow</be:event>
				<be:event type="onCheck">onClickRow</be:event>
			</bc:table>
		</bc:ibox>
		<bc:ibox title="流程模板" col="12" collapsible="true" >
			<div style="overflow:auto;">
				<img id="img" alt="流程模板"/> 
			</div>
		</bc:ibox>
		<bc:ibox title="审批记录" col="12" collapsible="true" >
			<div style="overflow:auto;">
				<div id="logContent"/> 
			</div>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
/* 选中模板，展示模板内容 */
function onClickRow(row){
	/*图片路径*/
	var imgUrl = '${base}/workflow/diagram/exedFlowImage/' + row['id'];
	/*加载流程图片  */
	document.getElementById("img").src=imgUrl;
	
	var logUrl = '${base}/workflow/procLog/hisLog/' + row['id'];
	$.mask.post(logUrl,{}, function(data){
		if(data.success){
			$('#logContent').html(data.logContent);
		}
	});
	
}

window.addEventListener('message',function(e){
	/* 判断是不是监听的工作流相关数据 */
	if('workflowFrameH' == e.data.type){
		var myFrame=document.getElementById('myFrame');
		myFrame.style.height = e.data.height + 'px'; 
	}
		
},false);
</script>
</body>
</html>