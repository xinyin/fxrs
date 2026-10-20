<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>新增流程</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="flowBox" title="流程信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" name="editForm" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="键" col="4" formControl="true"/>
						<bf:text id="key" name="key" col="8">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="描述" col="4" formControl="true"/>
						<bf:text id="description" name="description" col="8">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button outline="false" text="确定" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/workflow/model/create.json', formData, function(data){
										if(data.success)
										{
											window.location = data.url;
										}
										else
										{
											$.messager.alert('错误', data.msg, 'error');
										}
									});
								}
							</be:event>
						</bc:button>
						<bc:button type="primary"  text="返回" icon="times">
							<be:event>
								window.location = '${base}/workflow/model/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
					</bf:form>
		</bc:ibox>
	</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>
