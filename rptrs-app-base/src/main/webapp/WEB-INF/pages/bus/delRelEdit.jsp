<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>DelRel</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="delRelBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="4">
						<bc:label text="pid" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${delRel.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/delRel/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="姓名" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${delRel.name}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="4">
						<bc:label text="境内外标识" col="4" formControl="true"/>
						<bf:text id="domesticOrForeign" name="domesticOrForeign" col="8" value="${delRel.domesticOrForeign}">
						</bf:text>
					</bc:col>
<%--					<bc:col col="6">--%>
<%--						<bc:label text="机构名称" col="4" formControl="true"/>--%>
<%--						<bf:text id="mgerOrg" name="mgerOrg" col="8" value="${delRel.mgerOrg}">--%>
<%--						</bf:text>--%>
<%--					</bc:col>--%>
					<bf:formGroup col="4">
						<bc:label text="机构名称" col="4" formControl="true"/>
						<bf:select name="__LIKES_dispatchDep" col="8" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS_DispatchDep">
							<bf:option text="ALL" value=""></bf:option>
						</bf:select>
					</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
<%--					<bc:col col="6">--%>
<%--						<bc:label text="证件类型" col="4" formControl="true"/>--%>
<%--						<bf:text id="identifyType" name="identifyType" col="8" value="${delRel.identifyType}">--%>
<%--						</bf:text>--%>
<%--					</bc:col>--%>
					<bf:formGroup col="4">
						<bc:label text="证件类型" col="4" formControl="true"/>

						<bf:select name="__LIKES_identifyType" col="8" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-IdentifyType">
							<bf:option text="ALL" value="%"></bf:option>
							<be:event type="select">
								var input = $("#__LIKES_identifyType");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>

					</bf:formGroup>
					<bc:col col="4">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identifyNumber" name="identifyNumber" col="8" value="${delRel.identifyNumber}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
<%--				<bf:formGroup hr="dashed" hrp="bottom">--%>
<%--					<bc:col col="6">--%>
<%--						<bc:label text="解除口径" col="4" formControl="true"/>--%>
<%--						<bf:text id="regulator" name="regulator" col="8" value="${delRel.regulator}">--%>
<%--						</bf:text>--%>
<%--					</bc:col>--%>
<%--				</bf:formGroup>--%>
				<bf:formGroup col="4">
					<bc:label text="解除口径" col="4" formControl="true"/>
					<bf:select name="__LIKES_dispatchDep" col="8" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS_DispatchDep">
						<bf:option text="ALL" value=""></bf:option>
					</bf:select>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/delRel/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/delRel/list';
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
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/delRel/list';
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
