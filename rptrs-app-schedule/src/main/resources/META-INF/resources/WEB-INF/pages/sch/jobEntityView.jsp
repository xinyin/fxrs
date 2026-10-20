<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>调度任务管理</title>
<style type="text/css">
.btnCluster{
	background: white;
}
.btnCluster.active{
	background: #e7eaec;
}
</style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="true">
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="任务编码" col="4" formControl="true"/>
						<bc:label text="${jobEntity.code}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务名称" col="4" formControl="true"/>
						<bc:label text="${jobEntity.name}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="日历" col="4" formControl="true"/>
						<bc:label text="${jobEntity.calendarCode}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务类型" col="4" formControl="true"/>
					<bc:label text="${jobEntity.groupCode}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="任务类型1" col="4" formControl="true"/>
					<bc:label text="fixedDict:JobType1Dict:${jobEntity.jobType1}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="任务类型2" col="4" formControl="true"/>
						<bc:label text="fixedDict:JobType2Dict:${jobEntity.jobType2}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="节点算法" col="4" formControl="true"/>
						<bc:label text="fixedDict:NodeStrategyDict:${jobEntity.nodeStrategy}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="超时时间" col="4" formControl="true"/>
						<bc:label text="${jobEntity.timeout}" col="1" formControl="true" style="text-align:left"/>
						<bc:label text="fixedDict:TimeoutUnitDict:${jobEntity.timeoutUnit}" col="2" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="开始日期" col="4" formControl="true"/>
						<bc:label text="${jobEntity.startDateString}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="结束日期" col="4" formControl="true"/>
					<bc:label text="${jobEntity.endDateString}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="超时处理" col="4" formControl="true"/>
						<bc:label text="fixedDict:JobInstructionDict:${jobEntity.timeoutInstruction}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="失败处理" col="4" formControl="true"/>
						<bc:label text="fixedDict:JobInstructionDict:${jobEntity.errorInstruction}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="优先级" col="4" formControl="true"/>
						<bc:label text="${jobEntity.priority}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="状态" col="4" formControl="true"/>
						<bc:label text="fixedDict:JobStatusDict:${jobEntity.status}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="任务表达式" col="4" formControl="true"/>
						<bc:label text="${jobEntity.cronExpression}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="范围类型" col="4" formControl="true"/>
						<bc:label text="fixedDict:NodeScopeTypeDict:${jobEntity.nodeScopeType}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<div class="col-sm-10 col-sm-offset-2"  id="nodeScopeDiv">
						
					</div>
				</bf:formGroup>
				<bf:formGroup >
					<bc:label text="任务参数" col="2" formControl="true"/>
					<bc:col col="10">
						<table id="optionTable"></table>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:label text="备注" col="2" formControl="true"/>
					<bc:label text="${jobEntity.remark}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/sch/jobEntity/list';
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
<script type="text/javascript">
var nodeScope = '${jobEntity.nodeScope}';
var nodeScopeType = '${jobEntity.nodeScopeType}';
var paramJosn = '${jobEntity.param }';
$(document).ready(function(){
	
	//初始化类型选择
	initNodeScopeType();
	/* 初始化参数表格*/
	initTable();
	
})

//节点范围
function initNodeScopeType(){
	if(nodeScopeType == 'CLUSTER')
	{
		$.ajax({
			type: 'GET',
			url: '${base}/sch/cluster/queryAll',
			dataType: 'json',
			success: function(data){
				//加载数据 包含
				$.each(data, function(i, v){
					var html = '<button class="btn btn-white btn-xs btnCluster';
					if(nodeScope.indexOf(v.value) > -1)
					{
						var html = ' active';
					}
					var html = '" id="' + v.value + 'Cluster" data-placement="top">' + v.name + '</button>&nbsp;';
					$('#nodeScopeDiv').append(html);
				});
			}
		});
	}
	else
	{
		$.ajax({
			type: 'GET',
			url: '${base}/sch/cluster/queryClusterAndNodeAll',
			dataType: 'json',
			success: function(data){
				//加载数据 包含
				$.each(data.clusters, function(i, v){
					var html = '<div class="ibox float-e-margins col-sm-3" style="border: 1px solid #E7EAEC;clear: inherit;margin-left: 5px;border-radius:5px;">';
					html += '<div class="ibox-title" style="height: 10px;padding: 5px 5px 5px 5px;min-height: 20px;"><h5><small>' + v.name + '</small></h5></div>';
					html += '<div class="ibox-content" style="padding: 5px 5px 5px 5px;">';
					//循环节点
					$.each(data.nodes, function(j, n){
						if(v.code == n.clusterCode)
						{
							html += '<button class="btn btn-white btn-xs btnCluster';
							if(nodeScope.indexOf(n.code) > -1)
							{
								html += ' active';
							}
							html += '" id="' + n.code + 'Node" data-placement="top">' + n.name + '</button>';
						}
					});
					html += '</div></div>';
					$('#nodeScopeDiv').append(html);
				});
			}
		});
	}
}

/* 初始化参数表格*/
function initTable(){
	//初始化表格
	$("#optionTable").bootstrapTable({
        toolbar: "#optionTable_toolbar",
        idField: "code",
        pagination: false,
        search: false,
        showRefresh: false,
        clickToSelect: true,
        columns: [
        	{ field: "code", title: "编码", align: "center", width: 100},
        	{field: "type", title: "类型", align: "center", width: 100, editable: { type: 'select',  disabled: true,  mode: "type", source: [{ value: "string", text: "字符串" }, { value: "number", text: "数字" }, {value:"date", text:"日期"}], title: '参数类型'}},
        	{ field: "value", title: "默认值", width: 100}]
    });
	
	//判断是否是编辑， 编辑加载数据
	if(paramJosn)
	{
		var rows = JSON.parse(paramJosn);
		$('#optionTable').bootstrapTable('load', rows);
	}
}
</script>
</body>
</html>