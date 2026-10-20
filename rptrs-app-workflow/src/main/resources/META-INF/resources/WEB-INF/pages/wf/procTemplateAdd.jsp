<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>新增流程模板</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="flowBox" title="模板信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" name="editForm" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
				<bf:formGroup>
					<bc:col col="8">
						<bc:label text="模板编码" col="6" formControl="true"/>
						<bf:text id="code" name="code" col="6">
							<bv:notEmpty/>
							<bv:regexp regexp="^[a-zA-Z][a-zA-Z0-9]*$" message="只能以英文字母开头，只能包含英文字母、数字、下划线"/>
							<bv:stringLength max="64" message="模板编码不能大于64"/>
							<bv:remote url="${base}/workflow/procTemplate/check" name="code" message="模板编码已存在"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="8">
						<bc:label text="模板名称" col="6" formControl="true"/>
						<bf:text id="name" name="name" col="6">
							<bv:notEmpty/>
							<bv:stringLength max="64" message="模板名称不能大于64"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="8">
						<bc:label text="备注" col="6" formControl="true"/>
						<bf:text id="remark" name="remark" col="6" rows="3" multiple="true"><bv:stringLength max="512" message="模板备注不能大于512"/></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button type="primary" text="确定" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/workflow/procTemplate/save', formData, function(data){
										if(data.success)
										{
											window.location = '${base}/workflow/procTemplate/list';
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
								window.location = '${base}/workflow/procTemplate/list';
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
