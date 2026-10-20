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
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bc:table id="taskInstTable" idField="id" url="${base}/workflow/approve"  multiple="false">
				<bc:toolbar id="viewBiz" text="查看业务数据" type="button" handler="viewBizHandler"/>
				<bc:toolbar id="process" text="处理" type="button"  handler="processHandler"/>
<%--				<bc:toolbar id="before" text="前加签" type="button"  handler="beforeHandler"/>--%>
				<bc:toolbar id="after" text="加签" type="button"  handler="afterHandler"/>
<%--				<bc:toolbar id="transfer" text="转办" type="button"  handler="transferHandler"/>--%>
				<bc:column field="procCode" title="业务流程编码" width="100" visible="false" />
				<bc:column field="taskId" title="任务ID" width="100" visible="false" />
				<bc:column field="procName" title="流程名称" width="100"/>
				<bc:column field="relType" title="关联方类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
				<bc:column field="relName" title="关联方名称" width="100"/>
				<bc:column field="relNumber" title="证件号码" width="100"/>
				<bc:column field="transType" title="交易类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
				<bc:column field="businessType" title="业务类型" width="100"/>
				<bc:column field="transactionAmount" title="合同金额（万元）" width="100"/>
				<%--				<bc:column field="formName" title="表单名称" width="100"/>--%>
				<bc:column field="starterDeptName" title="发起部门" width="100"/>
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
		window.location = '${base}/workflow/process/view?procInstId=' + tables[0].id + '&option=approve/todoList'; 
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要查看的数据。');
	}
}

/* 处理 */
function processHandler(){
	var tables =  $('#taskInstTable').jTable('getCheckData');
	if(tables.length == 1)
	{
		window.location = '${base}/workflow/approve/process?procInstId=' + tables[0].id + '&taskId=' + tables[0].taskId; 
	}
	else
	{
		$.messager.toastr('提示', '请选择一条需要处理的数据。');
	} 
}


// 打开批量审批窗口
function batchProcessHandler() {
	let rows =  $('#taskInstTable').jTable('getCheckData');
	if (!rows || rows.length <= 0) {
		$.messager.toastr('提示', '请选择一条数据。');
		return
	}
	$('#batchApproval').click()
}

// 批量处理
function batchApproval() {
	const rows =  $('#taskInstTable').jTable('getCheckData');
	const memo = $('#memo').val()
	if ('' === memo) {
		$.messager.toastr('提示', '请输入意见。');
		return
	}
	const procInstIds = rows.map(row => row.id)
	const taskIds = rows.map(row => row.taskId)
	const params = {
		procInstIds,
		taskIds,
		memo
	}
	$.mask.show();
	$.ajax({
		url: '${base}/batchComplete',
		method: 'POST',
		contentType : "application/json",
		data : JSON.stringify(params),
		dataType: 'JSON',
		traditional: true,
		success(data) {
			$.mask.remove();
			if(data.success){
				$.messager.alert('提示', '提交成功。', 'info', function () {
					window.location = '${base}/workflow/approve/todoList';
				});
			}
		}
	})
}


/* 处理 */
function beforeHandler(){
	var tables =  $('#taskInstTable').jTable('getCheckData');
	if(tables.length == 1)
	{
		$.messager.modal({
			winId: 'winProcInstInsertTaskEdit',
			width: 700,
			height: 420,
			title: '前加签',
			url: '${base}/workflow/approve/insertTaskPage?flag=before&procInstId=' + tables[0].id + '&taskId=' + tables[0].taskId,
			args: {procInstId: tables[0].id, opt: 'todo'},
			onClose: function(){
				$('#taskInstTable').jTable('clearCheckData');
				$('#taskInstTable').jTable('refresh');
			}
		});
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要前加签的数据。');
	} 
}

/* 处理 */
function afterHandler(){
	var tables =  $('#taskInstTable').jTable('getCheckData');
	if(tables.length == 1)
	{
		$.messager.modal({
			winId: 'winProcInstInsertTaskEdit',
			width: 700,
			height: 420,
			title: '后加签',
			url: '${base}/workflow/approve/insertTaskPage?flag=after&procInstId=' + tables[0].id + '&taskId=' + tables[0].taskId,
			args: {procInstId: tables[0].id, opt: 'todo'},
			onClose: function(){
				$('#taskInstTable').jTable('clearCheckData');
				$('#taskInstTable').jTable('refresh');
			}
		});
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要后加签的数据。');
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
	
}
</script>
</body>
</html>