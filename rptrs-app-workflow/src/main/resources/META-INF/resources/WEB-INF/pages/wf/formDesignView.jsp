<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>表单设计</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="表单信息" col="12" collapsible="true">
			<bc:row cls="m-b">
				<bf:formGroup col="6">
					<bc:label text="表单编码:" col="4" formControl="true"></bc:label>
					<bc:label text="${formDef.code }" col="8" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup col="6">
					<bc:label text="表单名称:" col="4" formControl="true"></bc:label>
					<bc:label text="${formDef.name }" col="8" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup col="12">
					<bc:button  text="返回">
							<be:event>
								window.location = '${base}/workflow/formDef/list';
							</be:event>
						</bc:button>
				</bf:formGroup>
			</bc:row>
		</bc:ibox>
		<bc:ibox id="box2" title="属性信息" col="12" collapsible="true">
			<bc:col col="12">
				<bc:table id="formPropTable" idField="id" url="${base}/workflow/formProp" queryUrl="/queryList?formCode=${formDef.code }" detailView="false" pagination="false">
					<bc:toolbar id="view" text="查看" type="button" handler="viewHandler"/>
					<bc:column field="propCode" title="属性编码" width="100"/>
					<bc:column field="propName" title="属性名称" width="100"/>
					<bc:column field="primaryKey" title="是否是主键" width="100" formatter="fixedDict:YesNo"/>
					<bc:column field="propType" title="数据类型" width="200" formatter="fixedDict:PropDataType"/>
					<bc:column field="inputType" title="输入方式" width="200" formatter="fixedDict:PropInputType"/>
					<bc:column field="propOrder" title="排序" width="200"/>
					<bc:column field="remark" title="备注" width="200"/>
				</bc:table>
			</bc:col>
		</bc:ibox>
			<bc:ibox id="box2" title="事件信息" col="12" collapsible="true">
			<bc:col col="12">
				<bc:table id="formEventTable" idField="id" url="${base}/workflow/formEvent" queryUrl="/queryList?formCode=${formDef.code }" detailView="false" pagination="false">
					<bc:toolbar id="view" text="查看" type="button" handler="viewEventHandler"/>
					<bc:column field="propCode" title="属性编码" width="100"/>
					<bc:column field="type" title="事件类型" width="100" formatter="fixedDict:PropFormEventType"/>
					<bc:column field="name" title="事件名称" width="100" formatter="fixedDict:PropFormEvent"/>
					<bc:column field="script" title="脚本" width="100"/>
					<bc:column field="remark" title="备注" width="200"/>
				</bc:table>
			</bc:col>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
var formCode = '${formDef.code}';
var status = '${formDef.status}';
$(document).ready(function(){
	
})

//查看属性
function viewHandler(){
	var tables =  $('#formPropTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		window.location = '${base}/workflow/formProp/view/' + tables[0].formCode + '/' + tables[0].propCode + '/design';
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要查看的记录。', 'error');
	}
}

//查看事件
function viewEventHandler(){
	var tables =  $('#formEventTable').jTable('getCheckData');
	if(tables.length > 0)
	{
		window.location = '${base}/workflow/formEvent/view/' + tables[0].id + '/design';
	}
	else
	{
		$.messager.toastr('提示', '请先选择需要查看的记录。', 'error');
	}
}
</script>
</body>
</html>