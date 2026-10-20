<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>业务流程复制</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="procDefBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden name="code" id="code"/>
				<bf:hidden name="version" id="version"/>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="流程编码" col="4" formControl="true"/>
						<bf:text id="newCode" name="newCode" col="6">
							<bv:notEmpty/>
							<bv:regexp regexp="^[a-zA-Z\d_]{1}[a-zA-Z\d_]*" message="请输入正确的编码"/>
							<bv:stringLength min="1" max="64" message="流程编码长度范围：%s - %s"/>
							<bv:remote url="${base}/workflow/procDef/check" name="newCode" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="流程名称" col="4" formControl="true"/>
						<bf:text id="newName" name="newName" col="6">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button type="primary"  text="确认" icon="save" click="btnSave()">
						</bc:button>
						<bc:button  text="关闭" icon="times">
							<be:event>
								$.messager.hide('winProcDefCopy');
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
function btnSave(){
	$('#editForm').data('formValidation').validate();
	var isValid = $('#editForm').data('formValidation').isValid();
	if(isValid)
	{
		var args = $.messager.args('winProcDefCopy');
		$('#code').val(args.code);
		$('#version').val(args.version);
		
		var formData = $('#editForm').serializeAll();
		console.log(formData);
		$.mask.post('${base}/workflow/procDef/copy', formData, function(data){
			if(data.success)
			{
				$.messager.alert('提示', data.msg, 'info', function(){
					$.messager.hide('winProcDefCopy');
				});
			}
			else
			{
				$.messager.alert('错误', data.msg, 'error');
			}
		});
	}
}
</script>
</body>
</html>
