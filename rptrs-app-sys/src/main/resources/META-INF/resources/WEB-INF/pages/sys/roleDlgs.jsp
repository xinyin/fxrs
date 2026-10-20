<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>选择人员</title>
<style type="text/css">
	td {
		text-align:center;
	}
</style>
</head>
<body class="gray-bg">
<!-- 整体布局 -->
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bf:form id="queryForm" validation="true">
		<bc:row cls="m-b"></bc:row>
				<bc:row cls="m-b">
					<bf:formGroup col="4" type="success">	
						<bc:label text="员工代码" col="5" formControl="true"></bc:label>
						<bf:text name="__LIKES_roleCode" col="7" placeholder="员工代码"></bf:text>
					</bf:formGroup>
					<bf:formGroup col="4" type="success">
						<bc:label text="员工姓名" col="5" formControl="true"></bc:label>
						<bf:text name="__LIKES_roleName" col="7" placeholder="员工姓名"></bf:text>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询"/>
						<bc:button id="btnReset" type="primary" text="重置"/>
					</bc:col>
				</bc:row>
			</bf:form>
		<div class="col-sm-12">
			<bc:table id="staffTable" idField="eid" url="${base}/sys/role"  queryUrl="queryStaff">
				<bc:toolbar id="setStaff" text="选择" type="button" outline="true" handler="selectStaff"/>
				<bc:column field="roleCode" title="角色编码" width="50"/>
				<bc:column field="roleName" title="角色名称" width="60"/>
				<bc:column field="roleStdCode" title="角色类型" width="50" formatter="custom:sys/roleStd/queryAll"/>
				<%--<bc:column field="depId" title="部门" width="100" formatter="custom:pub/department/queryAll"></bc:column>--%>
			</bc:table>
		</div>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

function selectStaff(){
		var selectedStaffs =  $('#staffTable').bootstrapTable('getSelections');
		if(selectedStaffs.length == 0){
			$.messager.alert('提示', '请选择人员。', 'info');
			return;
		}
		else
		{
			$.messager.args('winmainSales').result([{
				'roleCode': selectedStaffs[0].roleCode,
				'roleName': selectedStaffs[0].roleName,
				'roleStdCode': selectedStaffs[0].roleStdCode,
				'roleS': selectedStaffs[0].roleCode+','+selectedStaffs[0].roleName+','+selectedStaffs[0].roleStdCode
			}]);
		}
		
	$.messager.hide('winmainSales');
}


</script>
</body>
</html>