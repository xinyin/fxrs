<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>表单定义表</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
				<bf:hidden name="opt" id="opt"></bf:hidden>
				<bf:hidden name="status" id="status" value="${formDef.status}"></bf:hidden>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="表单编码" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="8" value="${formDef.code}" disabled="${opt == 'edit'}">
							<bv:notEmpty/>
							<bv:jepCode/>
							<bv:remote url="${base}/workflow/formDef/check" name="code" message="表单编码"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="表单名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${formDef.name}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="表单类型" col="4" formControl="true"/>
						<bf:select id="formType" name="formType" dataSource="fixedDict" col="8"
								   url="PropFormType" value="${formDef.formType}">
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="备注${procDef.formType}" col="2" formControl="true"/>
					<bf:text id="remark" name="remark" col="8" value="${formDef.remark}" multiple="true" rows="3" ></bf:text>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button type="primary"  text="保存" icon="save">
							<be:event>
								$('#form1').data('formValidation').validate();
								var isValid = $('#form1').data('formValidation').isValid();
								if(isValid)
								{
									var data = $('#form1').serializeAll();
									$.mask.post('${base}/workflow/formDef/save', data, function(data){
										if(data.success)
										{
											$.messager.alert('提示', '保存成功。', 'info', function(){
												window.location = '${base}/workflow/formDef/list';
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
								window.location = '${base}/workflow/formDef/list';
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

</script>
</body>
</html>
