<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="../include/common.jsp"%>
	<br:css pack="list"/>
	<title>选择对象</title>
</head>
<body class="gray-bg">
<!-- 整体布局 -->
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox id="iboxTradeRuleSelectColumnDlg" title="" col="12" collapsible="false">
			<bc:ibox title="" subTitle="" col="12" collapsible="true">
				<bf:form id="queryForm" validation="true">
					<bc:row cls="m-b">
						<bf:formGroup col="12">
							<%--<bc:label text="名称" col="5" formControl="true"/>--%>
							<bf:text name="__LIKES_code" col="4" placeholder="编码"/>
							<bf:text name="__LIKES_name" col="4" placeholder="名称"/>
							<bc:button id="btnQuery"  text="查询" icon="search" type="primary"/>
						</bf:formGroup>

							<%--<bc:button id="btnReset" text="重置" icon="refresh"/>--%>
					</bc:row>
				</bf:form>
				<bc:buttonGroup cls="right">
					<bc:button text="选择" outline="false" type="primary">
						<be:event>
							<!-- 获取选择的数据 -->
							var objData = $('#objectsTable').jTable('getCheckData');
							var ruleId = '${ruleId}';

							if(objData.length > 0 ){
							var code = '';
							$.each(objData, function(i,v){
							code += ',' + v['id'];
							});

							$.mask.post("${base}/rul/ruleObjectMap/batchInsert",{ruleId:ruleId,objIds:code},function(data){
							if(data.success){
							$.messager.toastr('提示', '保存成功。');
							$.messager.hide('winObjsAdd');
							}else{
							$.messager.toastr('提示', '保存失败。',"error");
							}
							});
							}
							else
							{
							$.messager.toastr('提示', '请选择对象。');
							return
							}
						</be:event>
					</bc:button>
					<bc:button text="取消" outline="true">
						<be:event>
							$.messager.hide('winObjsAdd');
						</be:event>
					</bc:button>
				</bc:buttonGroup>
				<bc:table id="objectsTable" idField="id" url="${base}/rul/objects" multiple="true" showRefresh="false" showColumns="false" pageSize="5">
					<bc:column field="code" title="英文名称" width="100"/>
					<bc:column field="name" title="中文名称" width="100"/>
					<bc:column field="dataType" title="数据类型" width="100" formatter="fixedDict:RuleDataType"/>
					<%--<bc:column field="objType" title="对象类型" width="100" formatter="fixedDict:TableType"/>--%>
					<bc:column field="inOut" title="传递方向" width="100" formatter="fixedDict:InOut"/>
					<bc:column field="group" title="分组" width="100"/>
					<bc:column field="remark" title="描述" width="100"/>
				</bc:table>
			</bc:ibox>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>