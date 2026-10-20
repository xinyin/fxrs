<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="../include/common.jsp"%>
<br:css pack="form"/>
<title>规则集</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="packagesBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden name="id" value="${packages.id}" />
				<bf:formGroup hr="dashed" hrp="bottom">
					<%--<bc:col col="6">
						<bc:label text="主键" col="4" formControl="true"/>
						<bf:text id="id" name="id" col="8" value="${packages.id}"  disabled="${opt == 'edit'}">

						</bf:text>
					</bc:col>--%>
					<bc:col col="6">
						<bc:label text="英文名称" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="8" value="${packages.code}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<c:if test="${packages.id != null}">
								<bv:remote url="${base}/rul/packages/check" name="code" message="该编码已被占用"/>
							</c:if>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="中文名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${packages.name}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否评分" col="4" formControl="true"/>
						<bf:select name="type" id="type" col="8" value="${packages.type}" dataSource="fixedDict" url="YesNo">
							<bv:notEmpty message="是否评分不能为空"/>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="评分模型" col="4" formControl="true"/>
						<bf:select id="scoreModel" name="scoreModel" col="8"  dataSource="custom" url="rul/scoreModel/queryList?pkgId=${packages.id}" value="${packages.scoreModel}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="备注" col="4" formControl="true"/>
						<bf:text id="remark" name="remark" col="8"  multiple="true" value="${packages.remark}">
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
									$.mask.post('${base}/rul/packages/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/rul/packages/list';
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
								window.location = '${base}/rul/packages/list';
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
