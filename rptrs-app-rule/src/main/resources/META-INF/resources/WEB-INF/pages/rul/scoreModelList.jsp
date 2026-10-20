<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>交易分值表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="交易分值表" subTitle="请输入交易分值表查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="评分编码" col="5" formControl="true"/>
						<bf:text name="__LIKES_code" col="7" placeholder="评分编码"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="评分名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_name" col="7" placeholder="评分名称"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="primary" text="查询" icon="search"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="scoreModelTable" idField="id" url="${base}/rul/scoreModel">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="set" text="评分项设置" type="button" handler="setHandler"/>
				<bc:column field="code" title="评分编码" width="100"/>
				<bc:column field="name" title="评分名称" width="100"/>
				<bc:column field="rulPkgName" title="规则集名称" width="100"/>
				<bc:column field="algorithm" title="评分算法" width="100"/>
				<bc:column field="remark" title="备注" width="100"/>
				<bc:operator id="view" icon="search" title="查看"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
//评分项设置
function setHandler(){
	var rows =  $('#scoreModelTable').jTable('getCheckData');
	if(rows.length > 0)
	{
		window.location = '${base}/rul/modelRuleScore/list?pkgId=' + rows[0].rulPkgId + '&modelId=' + rows[0].id;
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要操作的记录。', 'error');
	}
}
</script>
</body>
</html>