<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>基础信息表</title>
	<style>
		.form-group {
			margin: 0.3% 0 !important;
		}

		.form-group .col-xs-12 {
			margin: 0.3% 0 !important;
		}

		.form-group .col-xs-12 label {
			display: inline-block;
			float: left;
			margin: 0 0 0 3%;
			padding: 0 2% 0 0;
		}

		.form-group .col-xs-12 label {
			display: inline-block;
			float: left;
			margin: 0 0 0 3%;
			padding: 0 2% 0 0;
			width: 10%;
		}

		.has-success .form-control, .boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}

		.gray-bg {
			background: white !important;
		}
		.form-group .input-group.date,
		.form-group .select2{
			width: 100% !important;
		}
		.boxse .form-group .col-xs-4 {
			margin: 0.3% 0 !important;
		}
		.boxse .has-success .form-control, .boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}
	</style>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="rootOrgInfoBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bc:row cls="m-b">
				<bf:formGroup hrp="bottom">
				<bf:formGroup col="6">
					<bc:label text="总股本（股）" col="4" formControl="true"/>
					<bf:text id="totalShareAmount" name="totalShareAmount" col="8" value="${rootOrgInfo.totalShareAmount}">
					</bf:text>
				</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label text="质押股数（股）" col="4" formControl="true"/>
						<bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="8" value="${rootOrgInfo.pledgedShareAmount}">
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>


					<bf:formGroup hrp="bottom">
					<bf:formGroup col="6">
						<bc:label text="资本净额（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="netCaptial" name="netCaptial" col="8" value="${rootOrgInfo.netCaptial}">
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label text="冻结股数（股）" col="4" formControl="true"/>
						<bf:text id="frozenShareAmount" name="frozenShareAmount" col="8" value="${rootOrgInfo.frozenShareAmount}">
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>

				<bf:formGroup hrp="bottom">
					<bf:formGroup col="6">
						<bc:label text="总资产（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="totalCapital" name="totalCapital" col="8" value="${rootOrgInfo.totalCapital}">
						</bf:text>
				</bf:formGroup>
				<%--	<bc:col col="6">
						<bc:label text="关联机构主键" col="4" formControl="true"/>
						<bf:text id="relOrgId" name="relOrgId" col="8" value="${rootOrgInfo.relOrgId}">
						</bf:text>
					</bc:col>--%>
				</bf:formGroup>


				</bc:row>



				<bf:formGroup>
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/rootOrgInfo/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/rootOrgInfo/list';
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
		x();
	});
	function x() {
		$('#pledgedShareAmount').attr('disabled','true');//证件类型
		$('#frozenShareAmount').attr('disabled','true');//证件类型
	}
</script>
</body>
</html>
