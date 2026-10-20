<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="../include/common.jsp"%>
<br:css pack="list"/>
<title>规则选择表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="12">
						<bf:text name="__LIKES_name" col="7" placeholder="规则名称"/>
						<bc:button id="btnQuery" type="primary" text="查询" icon="search"/>
					</bf:formGroup>
				</bc:row>
			</bf:form>
			<bc:buttonGroup cls="right">
				<bc:button text="确定" outline="false" type="primary">
					<be:event>
						<!-- 获取选择的数据 -->
						var rules = $('#rulesTable').jTable('getCheckData');
						var ids = "";
						$.each(rules,function(k,v){
						ids += "," + v.id;
						})

						if(rules.length > 0 ){
						$.mask.post("${base}/rul/pkgRuleMap/batchInsert",{packagesId:'${packagesId}',rules:ids},function(data){
						if(data.success){
						$.messager.toastr('提示', '保存成功。');
						$.messager.hide('winObjsAdd');
						}else{
						$.messager.toastr('提示', '保存失败。',"error");
						}
						});
						}else{
						$.messager.toastr('提示', '请选择对象。');
						return;
						}
					</be:event>
				</bc:button>
				<bc:button text="取消" outline="true">
					<be:event>
						$.messager.hide('winObjsAdd');
					</be:event>
				</bc:button>
			</bc:buttonGroup>
			<bc:table id="rulesTable" idField="id" url="${base}/rul/rules" multiple="true" showColumns="false" showRefresh="false">
				<bc:column field="id" title="主键" width="100" visible="false"/>
				<bc:column field="code" title="规则编码" width="100"/>
				<bc:column field="name" title="规则名称" width="100"/>
				<bc:column field="priority" title="规则优先级" width="100"/>
				<bc:column field="remark" title="备注" width="100"/>
				<bc:operator id="view" icon="search" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>