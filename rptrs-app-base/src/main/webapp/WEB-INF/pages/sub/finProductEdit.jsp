<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>FinProduct</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="finProductBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="pid" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${finProduct.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/sub/finProduct/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="产品全称" col="4" formControl="true"/>
						<bf:text id="productName" name="productName" col="8" value="${finProduct.productName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="产品代码" col="4" formControl="true"/>
						<bf:text id="productRegisterNo" name="productRegisterNo" col="8" value="${finProduct.productRegisterNo}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="产品分类" col="4" formControl="true"/>
						<bf:text id="productType" name="productType" col="8" value="${finProduct.productType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="产品批准/注册/备案号" col="4" formControl="true"/>
						<bf:text id="productIssueNo" name="productIssueNo" col="8" value="${finProduct.productIssueNo}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="产品登记机构" col="4" formControl="true"/>
						<bf:text id="registerOrg" name="registerOrg" col="8" value="${finProduct.registerOrg}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="产品管理人全称" col="4" formControl="true"/>
						<bf:text id="productOwner" name="productOwner" col="8" value="${finProduct.productOwner}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="管理人统一社会信用代码" col="4" formControl="true"/>
						<bf:text id="productOwnerUniscCode" name="productOwnerUniscCode" col="8" value="${finProduct.productOwnerUniscCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="产品托管人全称" col="4" formControl="true"/>
						<bf:text id="productCustodian" name="productCustodian" col="8" value="${finProduct.productCustodian}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="托管人统一社会信用代码" col="4" formControl="true"/>
						<bf:text id="productCustodianUniscCode" name="productCustodianUniscCode" col="8" value="${finProduct.productCustodianUniscCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="前置流程状态" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${finProduct.status}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="前置流程状态说明" col="4" formControl="true"/>
						<bf:text id="statusComment" name="statusComment" col="8" value="${finProduct.statusComment}">
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
									$.mask.post('${base}/sub/finProduct/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/sub/finProduct/list';
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
								window.location = '${base}/sub/finProduct/list';
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
