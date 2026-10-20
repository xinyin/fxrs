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
		<bc:ibox title="待审批列表信息" subTitle="" col="12" collapsible="true">
			<bc:table id="taskInstTable" idField="id" url="${base}/workflow/approve" queryUrl="queryNoCandidate" >
				<bc:toolbar id="viewBiz" text="查看业务数据" type="button" handler="viewBizHandler"/>
				<bc:toolbar id="transfer" text="指定处理人" type="button" handler="transferHandler"/>
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
			<bc:tabs id="tabContent">
				<bc:tab id="tab1" title="审批记录" visible="true">
					<div style="overflow:auto;">
						<div id="logContent"></div> 
					</div>
				</bc:tab>
				<bc:tab id="tab2" title="流程模板">
					<div style="overflow:auto;">
						<img id="img" alt="流程模板"/> 
					</div>
				</bc:tab>
				<bc:tab id="tab3" title="下步处理人">
					<div style="overflow:auto;">
						<div id="candidates"></div>
					</div>
				</bc:tab>
			</bc:tabs>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

/* 查看业务数据 */
function viewBizHandler(){
	var tables =  $('#taskInstTable').jTable('getCheckData');
	if(tables.length == 1)
	{
		window.location = '${base}/workflow/process/view?procInstId=' + tables[0].id + '&option=monitor/noCandidateList'; 
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要查看的数据。');
	}
}

/* 处理 */
function transferHandler(){
	var tables =  $('#taskInstTable').jTable('getCheckData');
	if(tables.length == 1)
	{
		$.messager.modal({
			winId: 'winProcInstTransferEdit',
			width: 700,
			height: 420,
			title: '转办流程',
			url: '${base}/workflow/approve/transferPage?procInstId=' + tables[0].id + '&taskId=' + tables[0].taskId,
			args: {procInstId: tables[0].id, opt: 'todo'},
			onClose: function(){
				$('#taskInstTable').jTable('clearCheckData');
				$('#taskInstTable').jTable('refresh');
			}
		});
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要转办的流程。');
	} 
}

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
	
	var candidateUrl = '${base}/workflow/monitor/candidates/' + row['id'] + '/' + row['taskId'];
	$.mask.post(candidateUrl,{}, function(data){
		if(data.success){
			$('#candidates').html(data.candidates);
		}
	});
	
}
</script>
</body>
</html>