<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>调度任务实例</title>

</head>
<body class="gray-bg">
	<div class="row animated fadeInRight">
		<bc:ibox title="调度任务实例" subTitle="请输入调度任务实例查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row>
					<bc:col col="4">
						<bc:label text="任务名称" col="4" formControl="true"/>
						<bf:text name="__LIKES_jobName" col="8" placeholder="任务名称"/>
					</bc:col>
					<bc:col col="4">
						<bc:label text="任务状态" col="4" formControl="true"/>
						<bf:checkGroup name="__INS_status" col="8">
							<bf:check dataSource="fixedDict" url="JobInstStateDict" excludes="UNKNOLWN,NEW,ABANDON"/>
						</bf:checkGroup>
					</bc:col>
					<bc:col col="4">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="jobInstTable" idField="id" url="${base}/sch/jobInst" detailView="true" detailFormatter="jobInstDetail">
				<bc:toolbar id="redo" text="重做" type="button" handler="redoHandler"/>
				<bc:toolbar id="stop" text="停止" type="button" handler="stopHandler"/>
				<bc:column field="jobName" title="任务名称" width="100"/>
				<bc:column field="startTimeString" title="开始时间" width="100"/>
				<bc:column field="endTimeString" title="结束时间" width="100"/>
				<bc:column field="execTime" title="执行时间" width="100"/>
				<bc:column field="status" title="状态" width="100" formatter="fixedDict:JobInstStateDict"/>
			</bc:table>
		</bc:ibox>
	</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

//重做
function redoHandler(){
	var rows =  $('#jobInstTable').jTable('getCheckData');
	if(rows.length > 0){
		$.messager.confirm('提示', '确定要重做当前任务吗？', function(r){
			if(r)
			{
				$.mask.post('${base}/sch/jobInst/redo/' + rows[0].id, function(data){
					if(data.success)
					{
						$('#jobInstTable').jTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要重做的记录。', 'error');
	}
}

//停止
function stopHandler(){
	var rows =  $('#jobInstTable').jTable('getCheckData');
	if(rows.length > 0){
		$.messager.confirm('提示', '确定要停止当前任务吗？', function(r){
			if(r)
			{
				$.mask.post('${base}/sch/jobInst/stop/' + rows[0].id, function(data){
					if(data.success)
					{
						$('#jobInstTable').jTable('refresh');
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
		$.messager.toastr('提示', '请先选择需要停止的记录。', 'error');
	}
}

function jobInstDetail(index, row){
	var html = [];
	$.ajax({
		type: 'GET',
		url: '${base}/sch/jobLog/queryByinstId?instId=' + row.id,
		dataType: 'json',
		async: false,
		success: function(data){
		    $.each(data, function (key, v) {
		        html.push('<p><b>' + v.logTimeString + ':</b> ' + v.content + '</p>');
		    });
		}
	});
	
	return html.join('');
}
</script>
</body>
</html>