<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>任务类型</title>

</head>
<body class="gray-bg">
	<div class="row animated fadeInRight">
		<bc:ibox title="任务类型" subTitle="请输入任务类型查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row>
					<bc:col col="4">
						<bc:label text="类别编码" col="4" formControl="true"/>
						<bf:text name="__LIKES_code" col="8" placeholder="类别编码"/>
					</bc:col>
					<bc:col col="4">
						<bc:label text="类别名称" col="4" formControl="true"/>
						<bf:text name="__LIKES_name" col="8" placeholder="类别名称"/>
					</bc:col>
					<bc:col col="4">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="groupTable" idField="code" url="${base}/sch/jobGroup">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="code" title="类别编码" width="100"/>
				<bc:column field="name" title="类别名称" width="100"/>
				<bc:column field="remark" title="描述" width="150"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
				<bc:operator id="view" icon="file-o" title="查看"/>
				<be:event type="onClickRow">onClickRow</be:event>
				<be:event type="onCheck">onClickRow</be:event>
			</bc:table>
		</bc:ibox>
		<bc:ibox title="关联集群" col="12" collapsible="true">
			<bc:table id="clusterTable" idField="code" url="${base}/sch/cluster"
				multiple="true" pagination="false" selectFirst="false">
				<bc:toolbar id="save" text="保存" type="button" handler="saveHandler"/>
				<bc:column field="code" title="集群编码" width="100"/>
				<bc:column field="name" title="集群名称" width="100"/>
				<bc:column field="remark" title="备注" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
//点击处理返回关联数据
function onClickRow(row){
	$.ajax({
		type: 'GET',
		url: '${base}/sch/jobGroupCluster/selectAll?groupCode=' + row.code,
		dataType: 'json',
		success: function(data){
			checkRow(data);
		}
	});
}

// 设置已选中的集群
function checkRow(data){
	//清楚已选中的数组的数据
	$('#clusterTable').jTable('clearCheckData');
	var rows =  $('#clusterTable').jTable('getData');
	//清楚已选中的数据
	$.each(rows, function(j, r){
		r.state = false;
	});
	//重设选中状态
	var selectRows = [];
	$.each(data, function(i, v){
		$.each(rows, function(j, r){
			if(v.clusterCode == r.code)
			{
				r.state = true;
				selectRows.push(r);
			}
		});
	});
	
	$('#clusterTable').jTable('initCheckData', selectRows);
	//重新渲染数据
	$('#clusterTable').jTable('load', rows);
}

//关联服务集群
function saveHandler(){
	var groupRows =  $('#groupTable').jTable('getCheckData');
	var rows =  $('#clusterTable').jTable('getCheckData');
	if(groupRows.length > 0 && rows.length > 0){
		//获取集群编码
		var clusterCode = '';
		$.each(rows, function(i, v){
			clusterCode += v.code + ',';
		});
		
		$.mask.post('${base}/sch/jobGroupCluster/saveAndCluster?groupCode=' + groupRows[0].code + '&clusterCode=' + clusterCode, function(data){
			if(data.success)
			{
				$.messager.toastr('提示', data.msg, 'info')
			}
			else
			{
				$.messager.toastr('错误', data.msg, 'error');
			}
		});
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要关联的记录。', 'error');
	}
}

</script>
</body>
</html>