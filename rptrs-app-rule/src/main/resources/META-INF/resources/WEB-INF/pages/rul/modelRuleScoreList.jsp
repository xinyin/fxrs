<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="../include/common.jsp"%>
<br:css pack="list"/>
<title>模型规则评分表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="模型规则评分表" subTitle="请输入模型规则评分表查询信息" col="12" collapsible="true">
			<bc:col col="3">
				<bc:table id="rulesTable" idField="id" url="${base}/rul/pkgRuleMap" queryUrl="queryRuleList?pkgId=${pkgId }" height="500" pagination="false">
					<bc:column field="name" title="规则名称" width="100"/>
				</bc:table>
			</bc:col>
			<bc:col col="9">
				<bc:table id="modelRuleScoreTable" idField="id" url="${base}/rul/modelRuleScore" queryUrl="query?__EQS_modelId=${modelId}" height="500"
				childField="ruleId" childFieldType="S" parentField="id" parentId="rulesTable" parentType="table" pagination="false">
					<bc:toolbar id="add1" text="新增" type="button" handler="addHandler"/>
					<bc:toolbar id="return" text="返回" type="button" handler="returnHandler"/>
					<bc:column field="ruleName" title="规则名称" width="100"/>
					<bc:column field="minValue" title="最小阈值" width="100"/>
					<bc:column field="maxValue" title="最大阈值" width="100"/>
					<bc:column field="score" title="分值" width="100"/>
					<bc:column field="remark" title="备注" width="100"/>
					<bc:operator id="edit1" icon="edit" title="编辑" handler="editHandler"/>
					<bc:operator id="remove" icon="trash" title="移除"/>
				</bc:table>
			</bc:col>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
var modelId = '${modelId}';

//新增分值项
function addHandler(){
	var rows =  $('#rulesTable').jTable('getCheckData');
	if(rows.length > 0)
	{
		$.messager.modal({
			winId: 'winScoreAdd',
			width: 700,
			height: 350,
			title: '分值段设置',
			url: '${base}/rul/modelRuleScore/add?ruleId=' + rows[0].id + '&modelId=' +modelId,
			onClose: function(){
				$("#modelRuleScoreTable").bootstrapTable('refresh');
			}
		});
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要操作的记录。', 'error');
	}
}

//编辑
function editHandler(row){
	var rows =  $('#modelRuleScoreTable').jTable('getCheckData');
	$.messager.modal({
		winId: 'winScoreAdd',
		width: 700,
		height: 350,
		title: '分值段设置',
		url: '${base}/rul/modelRuleScore/edit/' + rows[0].id,
		onClose: function(){
			$("#modelRuleScoreTable").bootstrapTable('refresh');
		}
	});
}

//返回评分模型界面
function returnHandler(){
	window.location = '${base}/rul/scoreModel/list';
}
</script>
</body>
</html>