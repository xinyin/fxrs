<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>对象库</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="objectsBox" title="对象库编辑" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="id" name="id" value="${objects.id}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="对象名称英文" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="8" value="${objects.code}" disabled="${opt == 'edit'}">
							<bv:notEmpty message="对象名称英文不能为空"/>
							<bv:remote url="${base}/rul/objects/check" name="code" message="属性编码重复"/>
							<bv:regexp regexp="^[a-zA-Z][a-zA-Z0-9_]*$" message="只能以英文字母开头，只能包含英文字母、数字、下划线"/>
							<bv:stringLength max="64" message="属性编码不能大于64"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="对象名称中文" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${objects.name}">
							<bv:notEmpty message="对象名称中文不能为空"/>
							<bv:stringLength max="64" message="属性编码不能大于64"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="数据类型" col="4" formControl="true"/>
						<bf:select id="dataType" name="dataType" col="8" value="${objects.dataType}" dataSource="fixedDict" url="RuleDataType">
							<bv:notEmpty message="数据类型不能为空"/>
						</bf:select>
					</bc:col>
					<%--<bc:col col="6">
						<bc:label text="对象类型" col="4" formControl="true"/>
						<bf:select id="objType" name="objType" col="8" value="${objects.objType}" dataSource="fixedDict" url="TableType">
							<bv:notEmpty message="对象类型不能为空"/>
						</bf:select>
					</bc:col>--%>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="传递方向" col="4" formControl="true"/>
						<bf:select id="inOut" name="inOut" col="8" value="${objects.inOut}" dataSource="fixedDict" url="InOut">
							<bv:notEmpty message="传递方向不能为空"/>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="分组" col="4" formControl="true"/>
						<bf:text id="group" name="group" col="8" value="${objects.group}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="12">
						<bc:label text="描述" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="10"  multiple="true">${objects.remark}</bf:text>
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
									$.mask.post('${base}/rul/objects/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/rul/objects/list';
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
								window.location = '${base}/rul/objects/list';
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
