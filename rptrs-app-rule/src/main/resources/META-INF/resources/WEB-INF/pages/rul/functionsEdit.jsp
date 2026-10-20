<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<style type="text/css">
#content{
	font-size: 14px;
}
</style>
<title>自定义函数库</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="functionsBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="id" name="id" value="${functions.id}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="编码" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="8" value="${functions.code}" disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/rul/functions/check" name="code" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${functions.name}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="12">
						<bc:label text="内容" col="2" formControl="true"/>
						<bf:text id="content" name="content" col="10" value="${functions.content}" multiple="true"></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="12">
						<bc:label text="描述" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="10" value="${functions.remark}" multiple="true"></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									var contentValue = window.editor.getValue();
									if(contentValue)
									{
										$.each(formData,function(k,v){
											debugger
											if(v && v.name == 'content'){
												formData.splice(k,1);
											}
										});
										formData.push({name:'content',value:contentValue});
									}
									console.debug(formData);
									$.mask.post('${base}/rul/functions/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/rul/functions/list';
											});
										}
										else
										{
											$.messager.alert('错误', data.msg, 'error');
										}
									});
								}
							</be:event>
						</bc:button>
						<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/rul/functions/list';
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
<link href="${res}/css/plugins/codemirror/codemirror.css" rel="stylesheet" />
<script src="${res}/js/plugins/codemirror/codemirror.js"></script>
<script src="${res}/js/plugins/codemirror/mode/clike/clike.js"></script>
<script type="text/javascript">
var opt = '${opt}';
$(document).ready(function(){ 
	//初始化内容脚本
	initContent();
});

//初始化内容脚本
function initContent(){
	
	if(opt != 'edit') $('#content').text('function void functionName() {\n\n\n}');
	
	//初始化编辑器
	window.editor = CodeMirror.fromTextArea(document.getElementById("content"), {
	   	lineNumbers: true,
        matchBrackets: true,
        mode: "text/x-java",
        tabsize: 20
	});
}
</script>
</body>
</html>
