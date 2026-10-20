<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>任务类型</title>
<script type="text/javascript">
	$(window).load(function() {
		jQuery.ajaxSetup({
			cache : false
		});
	});
</script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="true">
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="类别编码" col="4" formControl="true"/>
						<bc:label text="${group.code}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="类别名称" col="4" formControl="true"/>
						<bc:label text="${group.name}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="参数" col="2" formControl="true"/>
					<bc:col col="10">
						<table id="optionTable"></table>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="描述" col="2" formControl="true"/>
					<bc:label text="${group.remark}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/sch/jobGroup/list';
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
var paramJosn = '${group.param }';
$(document).ready(function(){
	/* 初始化参数表格*/
	init();
})

/* 初始化参数表格*/
function init(){
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