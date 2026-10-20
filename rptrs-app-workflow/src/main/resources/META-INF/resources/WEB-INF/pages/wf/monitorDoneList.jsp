<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>流程实例表</title>
<x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="角色类型" subTitle="请输入角色类型查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bf:hidden name="__INS_status"  value="40,60" />
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="业务流程名称" col="5" formControl="true"></bc:label>
						<bf:text name="__LIKES_procName" col="7" placeholder="业务流程名称"></bf:text>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="模块名称" col="5" formControl="true"></bc:label>
						<bf:text name="__LIKES_moduleName" col="7" placeholder="模块名称"></bf:text>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="procInstTable" idField="id" url="${base}/workflow/process" queryUrl="queryHistory" >
				<bc:column field="procCode" title="业务流程编码" width="100"/>
				<bc:column field="procName" title="业务流程名称" width="100"/>
				<bc:column field="moduleName" title="模块名称" width="100"/>
				<bc:column field="templateName" title="模板名称" width="100"/>
				<bc:column field="formName" title="表单名称" width="100"/>
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
/* 选中模板，展示模板内容 */
function onClickRow(row){
	if(row['status'] == '50'){
		$('#procInstTable_tb_suspend').attr('disabled', true);
		$('#procInstTable_tb_activate').attr('disabled', false);
		$('#procInstTable_tb_before').attr('disabled', true);
		$('#procInstTable_tb_after').attr('disabled', true);
		$('#procInstTable_tb_transfer').attr('disabled', true);
		$('#procInstTable_tb_moveTo').attr('disabled', true);
	}
	else 
	{
		$('#procInstTable_tb_suspend').attr('disabled', false);
		$('#procInstTable_tb_activate').attr('disabled', true);
		$('#procInstTable_tb_before').attr('disabled', false);
		$('#procInstTable_tb_after').attr('disabled', false);
		$('#procInstTable_tb_transfer').attr('disabled', false);
		$('#procInstTable_tb_moveTo').attr('disabled', false);
	}
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