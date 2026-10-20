<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>备案基础信息表</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="rootOrgInfoBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${rootOrgInfo.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/fil/rootOrgInfo/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="备案编号" col="4" formControl="true"/>
						<bf:text id="filNum" name="filNum" col="8" value="${rootOrgInfo.filNum}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联机构主键" col="4" formControl="true"/>
						<bf:text id="relOrgId" name="relOrgId" col="8" value="${rootOrgInfo.relOrgId}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="总股数" col="4" formControl="true"/>
						<bf:text id="totalShareAmount" name="totalShareAmount" col="8" value="${rootOrgInfo.totalShareAmount}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="资本净额" col="4" formControl="true"/>
						<bf:text id="netCaptial" name="netCaptial" col="8" value="${rootOrgInfo.netCaptial}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="总资产" col="4" formControl="true"/>
						<bf:text id="totalCapital" name="totalCapital" col="8" value="${rootOrgInfo.totalCapital}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="冻结股数(股)" col="4" formControl="true"/>
						<bf:text id="frozenShareAmount" name="frozenShareAmount" col="8" value="${rootOrgInfo.frozenShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="质押股数(股)" col="4" formControl="true"/>
						<bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="8" value="${rootOrgInfo.pledgedShareAmount}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上季末总资产" col="4" formControl="true"/>
						<bf:text id="endLastQuarter" name="endLastQuarter" col="8" value="${rootOrgInfo.endLastQuarter}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上年末总资产" col="4" formControl="true"/>
						<bf:text id="endLastYear" name="endLastYear" col="8" value="${rootOrgInfo.endLastYear}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上年末净资产" col="4" formControl="true"/>
						<bf:text id="netCaptialLastYear" name="netCaptialLastYear" col="8" value="${rootOrgInfo.netCaptialLastYear}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上季末净资产" col="4" formControl="true"/>
						<bf:text id="netCaptialLastQuarter" name="netCaptialLastQuarter" col="8" value="${rootOrgInfo.netCaptialLastQuarter}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="季度综合偿付能力充足率" col="4" formControl="true"/>
						<bf:text id="quarterCompreSolvencyRate" name="quarterCompreSolvencyRate" col="8" value="${rootOrgInfo.quarterCompreSolvencyRate}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="年度" col="4" formControl="true"/>
						<bf:text id="years" name="years" col="8" value="${rootOrgInfo.years}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="季度" col="4" formControl="true"/>
						<bf:text id="quarter" name="quarter" col="8" value="${rootOrgInfo.quarter}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="审批状态" col="4" formControl="true"/>
						<bf:text id="approvalStatus" name="approvalStatus" col="8" value="${rootOrgInfo.approvalStatus}">
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
									$.mask.post('${base}/fil/rootOrgInfo/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/fil/rootOrgInfo/list';
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
								window.location = '${base}/fil/rootOrgInfo/list';
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
