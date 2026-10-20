<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>TraAsset</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="traAssetBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/traAsset/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易编号" col="4" formControl="true"/>
						<bf:text id="transNum" name="transNum" col="8" value="${transNum}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联方名称" col="4" formControl="true"/>
						<bf:text id="relatedName" name="relatedName" col="8" value="${relatedName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构名称" col="4" formControl="true"/>
						<bf:text id="manageOrg" name="manageOrg" col="8" value="${manageOrg}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:text id="identifyType" name="identifyType" col="8" value="${identifyType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identNumber" name="identNumber" col="8" value="${identNumber}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易类型" col="4" formControl="true"/>
						<bf:text id="tranType" name="tranType" col="8" value="${tranType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="审批时间" col="4" formControl="true"/>
						<bf:datebox id="approvalTime" name="approvalTime" col="8"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="合同有效期" col="4" formControl="true"/>
						<bf:text id="validityContract" name="validityContract" value="${validityContract}" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="状态" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${status}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易标的" col="4" formControl="true"/>
						<bf:text id="tranTarget" name="tranTarget" col="8" value="${tranTarget}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易金额" col="4" formControl="true"/>
						<bf:text id="tranAmount" name="tranAmount" col="8" value="${tranAmount}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易日期" col="4" formControl="true"/>
						<bf:text id="tranDate" name="tranDate" value="${tranDate}" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易情况" col="4" formControl="true"/>
						<bf:text id="tranInfo" name="tranInfo" col="8" value="${tranInfo}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="业务类型" col="4" formControl="true"/>
						<bf:text id="businessType" name="businessType" col="8" value="${businessType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="资产评估价" col="4" formControl="true"/>
						<bf:text id="appraisalAssets" name="appraisalAssets" col="8" value="${appraisalAssets}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否删除" col="4" formControl="true"/>
						<bf:text id="isDel" name="isDel" col="8" value="${isDel}">
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
