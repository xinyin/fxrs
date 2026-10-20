<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>TraCredit</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="traCreditBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/traCredit/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易编号" col="4" formControl="true"/>
						<bf:text id="transNum" name="transNum" col="8" value="${transNum}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联方名称" col="4" formControl="true"/>
						<bf:text id="relatedName" name="relatedName" col="8" value="${relatedName}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构名称" col="4" formControl="true"/>
						<bf:text id="manageOrg" name="manageOrg" col="8" value="${manageOrg}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:text id="identifyType" name="identifyType" col="8" value="${identifyType}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identNumber" name="identNumber" col="8" value="${identNumber}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易类型" col="4" formControl="true"/>
						<bf:text id="tranType" name="tranType" col="8" value="${tranType}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="审批时间" col="4" formControl="true"/>
						<bf:datebox id="approvalTime" name="approvalTime" col="8" disabled="true"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="合同有效期" col="4" formControl="true"/>
						<bf:datebox id="validityContract" name="validityContract" col="8" disabled="true"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="状态" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${status}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="授信金额" col="4" formControl="true"/>
						<bf:text id="creditAmount" name="creditAmount" col="8" value="${creditAmount}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="授信余额" col="4" formControl="true"/>
						<bf:text id="validityBalance" name="validityBalance" col="8" value="${validityBalance}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="授信起始日期" col="4" formControl="true"/>
						<bf:datebox id="creditStartDate" name="creditStartDate" col="8" disabled="true"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="授信到期日" col="4" formControl="true"/>
						<bf:datebox id="creditEndDate" name="creditEndDate" col="8" disabled="true"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="担保方式" col="4" formControl="true"/>
						<bf:text id="guaraMode" name="guaraMode" col="8" value="${guaraMode}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="担保品情况" col="4" formControl="true"/>
						<bf:text id="collateral" name="collateral" col="8" value="${collateral}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="保证人名称" col="4" formControl="true"/>
						<bf:text id="guarantor" name="guarantor" col="8" value="${guarantor}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="五级分类" col="4" formControl="true"/>
						<bf:text id="fiveLevel" name="fiveLevel" col="8" value="${fiveLevel}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="保证金金额" col="4" formControl="true"/>
						<bf:text id="securitDeposit" name="securitDeposit" col="8" value="${securitDeposit}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="保证金比例" col="4" formControl="true"/>
						<bf:text id="marginRatio" name="marginRatio" col="8" value="${marginRatio}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="业务类型" col="4" formControl="true"/>
						<bf:text id="businessType" name="businessType" col="8" value="${businessType}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否删除" col="4" formControl="true"/>
						<bf:text id="isDel" name="isDel" col="8" value="${isDel}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
			</bf:form>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
	<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
	<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
</body>
</html>
