<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>业务流程管理</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="procDefBox" title="编辑业务流程信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden name="opt" id="opt" value="${opt}"/>
				<bf:hidden name="id" id="id" value="${procDef.id}"/>
				<bf:hidden name="procVersion" value="${procVersion}"/>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="流程编码" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="6" value="${procDef.code}" disabled="${opt == 'edit'}">
							<bv:notEmpty/>
							<bv:jepCode/>
							<bv:stringLength min="1" max="64" message="流程编码长度范围：%s - %s"/>
							<bv:remote url="${base}/workflow/procDef/check" name="code" message="该编码已被占用"/>
						</bf:text>
						 <div class="progress" id="progressBar" style="margin: 5px 0 0 0; display: none;">
				            <div class="progress-bar progress-bar-success progress-bar-striped active" style="width: 100%"></div>
				        </div>
					</bc:col>
					<bc:col col="6">
						<bc:label text="流程名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="6" value="${procDef.name}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="模板名称" col="4" formControl="true"/>
						<bf:select id="procTemplateCode" name="procTemplateCode" dataSource="custom" col="6"
								   url="workflow/procTemplate/selectAll" value="${procDef.procTemplateCode}">
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="表单名称" col="4" formControl="true"/>
						<bf:select id="formCode" name="formCode" dataSource="custom" col="6"
								   url="workflow/formDef/selectAll" value="${procDef.formCode}">
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="模块名称" col="4" formControl="true"/>
						<bf:lookup id="moduleCode" name="moduleCode" col="6" 
							url="${base}/workflow/bizModule/dlg?code=${procDef.moduleCode}"
							value="${procDef.moduleCode}" text="${procDef.moduleName}"
							propValue="moduleCode" propText="moduleName" 
							callback="result" onSelect="onSelect" winId="bizModuleSelect"
							title="选择业务模块" height="300">
							<bv:notEmpty/>
						</bf:lookup>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否跳过同一审批人" col="4" formControl="true"/>
						<bf:checkGroup name="skipSameApprover" id="skipSameApprover" value="${procDef.skipSameApprover}" col="8">
							<bf:check value="Y"/>
						</bf:checkGroup>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="客户端地址" col="4" formControl="true"/>
						<bf:text id="clientUrl" name="clientUrl" col="6" value="${procDef.clientUrl}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="客户端端口" col="4" formControl="true"/>
						<bf:text id="clientPort" name="clientPort" col="6" value="${procDef.clientPort}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="客户端上下文" col="4" formControl="true"/>
						<bf:text id="clientContext" name="clientContext" col="6" value="${procDef.clientContext}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="备注" col="2" formControl="true"/>
					<bf:text id="remark" name="remark" col="8" value="${procDef.remark}" multiple="true"></bf:text>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button type="primary"  text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/workflow/procDef/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/workflow/procDef/list';
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
						<bc:button  text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/workflow/procDef/list';
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
function onSelect(){
	$('#editForm').data('formValidation').revalidateField('moduleCodeText');
}

$(document).ready(function(){
	
	var code = $('#editForm').data('formValidation').getFieldElements('code');
	code.on('status.field.fv', function(e, data) {
        
        if (data.field === 'username' && data.validator === 'remote') {
            (data.status === 'VALIDATING') ? $('#progressBar').show() : $('#progressBar').hide();     // Otherwise, hide it
        }
    });
	
	console.log(code);
});
</script>
</body>
</html>
