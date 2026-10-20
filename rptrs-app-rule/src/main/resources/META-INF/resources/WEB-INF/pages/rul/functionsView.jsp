<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>自定义函数库</title>
</head>
<body class="gray-bg" onload="prettyPrint();">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="functionsBox" title="自定义函数库" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="编码" col="4" formControl="true"/>
						<bc:label text="${functions.code}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="名称" col="4" formControl="true"/>
						<bc:label text="${functions.name}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="内容" col="2" formControl="true"/>
					<bf:text id="content" name="content" col="10" value="${functions.content}" multiple="true"></bf:text>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="描述" col="2" formControl="true"/>
					<bc:label text="${functions.remark}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
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
<link href="${res}/css/plugins/codemirror/codemirror.css" rel="stylesheet" />
<script src="${res}/js/plugins/codemirror/codemirror.js"></script>
<script src="${res}/js/plugins/codemirror/mode/clike/clike.js"></script>
<be:processEvent/>
<script type="text/javascript">
$(document).ready(function(){ 
	//初始化内容脚本
	//初始化编辑器
	var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
	   	lineNumbers: true,
        matchBrackets: true,
        mode: "text/x-java",
        readOnly : true
      });
});
</script>
</body>
</html>