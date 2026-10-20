<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>调度任务管理</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row animated fadeInRight">
		<bc:ibox title="任务调度" subTitle="请输入任务调度查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row>
					<bc:col col="4">
						<bc:label text="任务编码" col="4" formControl="true"/>
						<bf:text name="__LIKES_code" col="8" placeholder="任务编码"/>
					</bc:col>
					<bc:col col="4">
						<bc:label text="任务名称" col="4" formControl="true"/>
						<bf:text name="__LIKES_name" col="8" placeholder="任务名称"/>
					</bc:col>
					<bc:col col="4">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="jobEntityTable" idField="code" url="${base}/sch/jobEntity">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="start" text="启动" type="button" handler="startHandler"/>
				<bc:toolbar id="pause" text="暂停" type="button" handler="pauseHandler"/>
				<bc:toolbar id="stop" text="停止" type="button" handler="stopHandler"/>
				<bc:column field="code" title="任务编码" width="100"/>
				<bc:column field="name" title="任务名称" width="100"/>
				<bc:column field="calendarCode" title="日历" width="100"/>
				<bc:column field="groupCode" title="任务类型" width="100"/>
				<bc:column field="startDateString" title="开始日期" width="100"/>
				<bc:column field="priority" title="优先级" width="50"/>
				<bc:column field="status" title="状态" width="50" formatter="fixedDict:JobStatusDict"/>
				<bc:operator id="edit" icon="edit" title="编辑" visible="editVisible"/>
				<bc:operator id="remove" icon="trash" title="移除" visible="removeVisible"/>
				<bc:operator id="view" icon="file-o" title="查看"/>
				<be:event type="onClickRow">onClickRow</be:event>
				<be:event type="onCheck">onClickRow</be:event>
			</bc:table>
		</bc:ibox>
	</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
function onClickRow(row){
	if(row['status'] == 'NEW'){
		$('#jobEntityTable_tb_start').attr('disabled', false);
		$('#jobEntityTable_tb_pause').attr('disabled', true);
		$('#jobEntityTable_tb_stop').attr('disabled', true);
	}
	else if(row['status'] == 'EXECUTING')
	{
		$('#jobEntityTable_tb_start').attr('disabled', true);
		$('#jobEntityTable_tb_pause').attr('disabled', false);
		$('#jobEntityTable_tb_stop').attr('disabled', false);
	}
	else if(row['status'] == 'COMPLETE')
	{
		$('#jobEntityTable_tb_start').attr('disabled', true);
		$('#jobEntityTable_tb_pause').attr('disabled', true);
		$('#jobEntityTable_tb_stop').attr('disabled', true);
	} 
	else if(row['status'] == 'PAUSED')
	{
		$('#jobEntityTable_tb_start').attr('disabled', false);
		$('#jobEntityTable_tb_pause').attr('disabled', true);
		$('#jobEntityTable_tb_stop').attr('disabled', true);
	}
	else
	{
		$('#jobEntityTable_tb_start').attr('disabled', true);
		$('#jobEntityTable_tb_pause').attr('disabled', true);
		$('#jobEntityTable_tb_stop').attr('disabled', true);
	} 
}

//编辑先验证
function editVisible(row){
		if(row['status'] == 'NEW'||row['status'] == 'PAUSED')
		{
			return true;
		}
	return false;
}

//删除时先验证
function removeVisible(row){
	if(row['status'] == 'NEW' || row['status'] == 'STOPPED')
	{
		return true;
	}
return false;
}

//启动任务
function startHandler(){
	var rows =  $('#jobEntityTable').jTable('getCheckData');
	if(rows.length > 0){
		$.messager.confirm('提示', '确定要启动该任务吗？', function(r){
			if(r)
			{
				$.mask.post('${base}/sch/jobEntity/start/' + rows[0].code, function(data){
					if(data.success)
					{
						$('#jobEntityTable').jTable('refresh');
						$.messager.alert('提示', data.msg, 'info')
					}
					else
					{
						$.messager.alert('错误', data.msg, 'error');
					}
				});
			}
		});
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要启动的记录。', 'error');
	}
}

//暂停任务
function pauseHandler(){
	var rows =  $('#jobEntityTable').jTable('getCheckData');
	if(rows.length > 0){
		$.messager.confirm('提示', '确定要暂停该任务吗？', function(r){
			if(r)
			{
				$.mask.post('${base}/sch/jobEntity/pause/' + rows[0].code, function(data){
					if(data.success)
					{
						$('#jobEntityTable').jTable('refresh');
						$.messager.alert('提示', data.msg, 'info')
					}
					else
					{
						$.messager.alert('错误', data.msg, 'error');
					}
				});
			}
		});
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要启动的记录。', 'error');
	}
}

//停止任务
function stopHandler(){
	var rows =  $('#jobEntityTable').jTable('getCheckData');
	if(rows.length > 0){
		$.messager.confirm('提示', '确定要停止该任务吗？', function(r){
			if(r)
			{
				$.mask.post('${base}/sch/jobEntity/stop/' + rows[0].code, function(data){
					if(data.success)
					{
						$('#jobEntityTable').jTable('refresh');
						$.messager.alert('提示', data.msg, 'info')
					}
					else
					{
						$.messager.alert('错误', data.msg, 'error');
					}
				});
			}
		});
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要启动的记录。', 'error');
	}
}
</script>
</body>
</html>