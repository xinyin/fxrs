<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>节点授权选择岗位</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bc:table id="roleStdTable" idField="roleStdCode" selectFirst="false"
				url="${base}/sys/roleStd" showRefresh="false" multiple="true">
				<bc:toolbar id="confirm" text="选择" type="button" handler="confirm"/>
				<bc:toolbar id="back" text="返回" type="button" handler="back"/>
				<bc:column field="roleStdCode" title="角色类型编码" width="100"/>
				<bc:column field="roleStdName" title="角色类型名称" width="100"/>
				<bc:column field="roleStdDesc" title="角色类型描述" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
function confirm(){
	var row = $('#roleStdTable').bootstrapTable('getSelections');
	
	var rows =  $('#roleStdTable').jTable('getCheckData');
	if(rows.length == 0)
	{
		$.messager.toastr('提示', '请选择岗位。', 'warning');
		return;
	}
	var codeList = new Array();
	var nameList = new Array();
	$.each(rows, function(i, val){
		codeList.push(val.roleStdCode);
		nameList.push(val.roleStdName);
	});
	
	$.messager.args('authRoleSelect').result([{
		'authRole': codeList,
		'authRoleName': nameList
	}]);
	$.messager.hide('authRoleSelect');
}

function back(){
	$.messager.hide('authRoleSelect');
}
</script>
</body>
</html>