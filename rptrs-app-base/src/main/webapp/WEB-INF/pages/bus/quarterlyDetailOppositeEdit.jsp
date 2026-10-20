<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联交易季报-关联交易明细表-交易对手情况</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="quarterlyDetailOppositeBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${quarterlyDetailOpposite.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/quarterlyDetailOpposite/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易编码（与季报关联交易明细表关联）" col="4" formControl="true"/>
						<bf:text id="transNum" name="transNum" col="8" value="${quarterlyDetailOpposite.transNum}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联方类型" col="4" formControl="true"/>
						<bf:text id="relatedType" name="relatedType" col="8" value="${quarterlyDetailOpposite.relatedType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联方主键（与关联方档案关联）" col="4" formControl="true"/>
						<bf:text id="relatedId" name="relatedId" col="8" value="${quarterlyDetailOpposite.relatedId}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联方名称" col="4" formControl="true"/>
						<bf:text id="relatedName" name="relatedName" col="8" value="${quarterlyDetailOpposite.relatedName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联方证件类型编码" col="4" formControl="true"/>
						<bf:text id="relatedCredentialsType" name="relatedCredentialsType" col="8" value="${quarterlyDetailOpposite.relatedCredentialsType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联方证件类型名称" col="4" formControl="true"/>
						<bf:text id="relatedCredentialsTypeName" name="relatedCredentialsTypeName" col="8" value="${quarterlyDetailOpposite.relatedCredentialsTypeName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联方证件号码" col="4" formControl="true"/>
						<bf:text id="relatedCredentialsNum" name="relatedCredentialsNum" col="8" value="${quarterlyDetailOpposite.relatedCredentialsNum}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="统一社会信用代码" col="4" formControl="true"/>
						<bf:text id="relatedUniscCode" name="relatedUniscCode" col="8" value="${quarterlyDetailOpposite.relatedUniscCode}">
						</bf:text>
					</bc:col>
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
									$.mask.post('${base}/bus/quarterlyDetailOpposite/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/quarterlyDetailOpposite/list';
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
								window.location = '${base}/bus/quarterlyDetailOpposite/list';
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
