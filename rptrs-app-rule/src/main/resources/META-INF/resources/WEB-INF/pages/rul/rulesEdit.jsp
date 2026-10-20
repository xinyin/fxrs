<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="../include/common.jsp"%>
	<br:css pack="form"/>
	<title>规则表</title>
</head>
<body class="gray-bg">
<div class="row">
	<bc:ibox id="rulesBox" title="编辑信息" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="opt" name="opt" value="${opt}"/>
			<bf:hidden name="id" id="id" value="${rules.id}" />
			<bf:formGroup hr="dashed" hrp="bottom">
				<bc:col col="6">
					<bc:label text="规则编码" col="4" formControl="true"/>
					<bf:text id="code" name="code" col="8" value="${rules.code}"  disabled="${opt == 'edit'}">
						<bv:jepCode/>
						<bv:notEmpty/>
						<bv:remote url="${base}/rul/rules/check" name="id" message="该编码已被占用"/>
					</bf:text>
				</bc:col>
				<bc:col col="6">
					<bc:label text="规则名称" col="4" formControl="true"/>
					<bf:text id="name" name="name" col="8" value="${rules.name}">
					</bf:text>
				</bc:col>
			</bf:formGroup>
			<bf:formGroup hr="dashed" hrp="bottom">
				<bc:col col="6">
					<bc:label text="规则优先级" col="4" formControl="true"/>
					<bf:text id="priority" name="priority" col="8" value="${rules.priority}">
					</bf:text>
				</bc:col>
				<bc:col col="6">
					<bc:label text="备注" col="4" formControl="true"/>
					<bf:text id="remark" name="remark" col="8" value="${rules.remark}">
					</bf:text>
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
							$.mask.post('${base}/rul/rules/save', formData, function(data){
							if(data.success)
							{
							$.messager.alert('提示', data.msg, 'info', function(){
							window.location = '${base}/rul/rules/list';
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
					<bc:button type="default" text="返回" icon="arrow-circle-left">
						<be:event>
							window.location = '${base}/rul/rules/list';
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
    $(document).ready(function(){

    });
</script>
</body>
</html>
