<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="../include/common.jsp"%>
<br:css pack="form"/>
<title>规则内容编辑</title>
</head>
<body class="white-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox id="inOutObj" title="传入对象" subTitle="" col="12" collapsible="false">
			<bc:buttonGroup cls="right">
				<bc:button id="addOjb" text="添加">
					<be:event>
						$.messager.modal({
						winId: 'winObjsAdd',
						width: 800,
						height: 450,
						title: '选择对象库',
						url: "${base}/rul/objects/select?ruleId=" + '${ruleId}',
						onClose: function(){
							$("#inOutObjTable").bootstrapTable('refresh');
						console.debug(parent.document);
							parent.document.getObjects();
						}
						});
					</be:event>
				</bc:button>
				<bc:button id="removeObj" text="删除">
					<be:event>
						var objData = $('#inOutObjTable').jTable('getSelections');
						if(!objData[0])
						{
						$.messager.toastr("提示","请选择对象！","info");
						return;
						}
						$.mask.post("${base}/rul/ruleObjectMap/remove/" + objData[0].id,function(data){
						if(data.success)
						{
						$.messager.toastr("提示","删除成功！","success");
						$("#inOutObjTable").bootstrapTable('refresh');
						}
						else{
						$.messager.toastr("提示","删除失败！","error");
						}
						});
					</be:event>
				</bc:button>
			</bc:buttonGroup>
			<bc:table id="inOutObjTable" idField="code" url="${base}/rul/ruleObjectMap" queryUrl="findObjectsByRuleId?ruleId=${ruleId}" showRefresh="false" showColumns="false"  multiple="true">
				<%--<bc:toolbar id="add" text="增加" type="button"/>--%>
				<bc:column field="code" title="英文名称" width="100"/>
				<bc:column field="id" title="主键" visible="false"/>
				<bc:column field="name" title="中文名称" width="100"/>
				<bc:column field="dataType" title="数据类型" width="100" formatter="fixedDict:RuleDataType"/>
				<bc:column field="group" title="分组" width="100"/>
				<bc:column field="remark" title="说明" width="100"/>
			</bc:table>
		</bc:ibox>
		<bc:ibox id="baseColumnBox" title="函数" subTitle="" col="12" collapsible="false">
			<%--<bc:buttonGroup cls="right">
				<bc:button id="addOjb" text="添加"></bc:button>
				<bc:button id="addOjb" text="删除"></bc:button>
			</bc:buttonGroup>--%>
			<bc:table id="dashLayoutTable" idField="code" url="${base}/rul/functions" showRefresh="false" showColumns="false">
				<bc:column field="code" title="编码" width="200"/>
				<bc:column field="name" title="名称" width="200"/>
				<bc:column field="remark" title="说明" width="500"/>
			</bc:table>
		</bc:ibox>
		<bc:ibox id="constantBox" title="常量" subTitle="" col="12" collapsible="false">
			<bc:table id="constantTable" idField="code" url="${base}/rul/function" showRefresh="false" showColumns="false">
				<bc:column field="code" title="英文名称" width="100"/>
				<bc:column field="name" title="中文名称" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>
