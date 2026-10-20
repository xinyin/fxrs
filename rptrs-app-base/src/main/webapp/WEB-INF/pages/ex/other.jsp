<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>其他关联交易类</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="otherTypeBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<input type="hidden" id="pid" name="pid" value="${pid}">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联方名称" col="4" formControl="true"/>
						<bf:text id="relatedName" name="relatedName" col="8" value="${relatedName}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构名称" col="4" formControl="true"/>
						<bf:text id="houseManageOrg" name="houseManageOrg" col="8" value="${houseManageOrg}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identNumber" name="identNumber" col="8" value="${identNumber}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:text id="identlfyType" name="identlfyType"  col="8" value="${identlfyType}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="审批时间 " col="4" formControl="true"/>
						<bf:text id="approvalTime" name="approvalTime" value="${approvalTime}" col="8"  disabled="true"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="合同有效期" col="4" formControl="true"/>
						<bf:text id="validityContract" name="validityContract" value="${validityContract}" col="8" disabled="true" />
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<%--<bc:col col="6">
						<bc:label text="状态" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${status}">
						</bf:text>
					</bc:col>--%>
					<bc:col col="6">
						<bc:label text="交易金额" col="4" formControl="true"/>
						<bf:text id="tranAmount" name="tranAmount" col="8" value="${tranAmount}" disabled="true" >
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="统一口径" col="4" formControl="true"/>
						<bf:text id="tranAmount" name="tranAmount" col="8" value="${tranAmount}" disabled="true" >
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="审批完成时间" col="4" formControl="true"/>
						<bf:text id="tranOmpTime" name="tranOmpTime" value=" ${tranOmpTime}" col="8" disabled="true" />
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易类型" col="4" formControl="true"/>
						<%--<bf:text id="tranType" name="tranType" col="8" value="${tranType}">
						</bf:text>--%>
						<bf:text  id="tranType" name="tranType"   col="8" value="${tranType}" disabled="true" >
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<%--<bc:col col="6">
						<bc:label text="交易标的" col="4" formControl="true"/>
						</bf:text>&ndash;%&gt;
						<textarea name="tranTarget" id="tranTarget" style="margin-left: 290px" rows="5" cols="100" value="${tranTarget}">
						</textarea>
					</bc:col>--%>
					<bc:col col="8" >
						<bc:label text="交易标的" col="4" formControl="true" />
						<bf:text id="tranTarget" name="tranTarget" col="8"
								 multiple="true" value="${tranTarget}" disabled="true" >
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="8" >
						<bc:label text="定价政策" col="4" formControl="true" />
						<bf:text id="pricingStrategy" name="pricingStrategy" col="8"
								 multiple="true" value="${pricingStrategy}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom" >
					<bc:col col="8">
						<bc:label text="其他交易信息" col="4" formControl="true" />
						<bf:text id="tranInfo" name="tranInfo" col="8"
								 multiple="true" value="${tranInfo}" disabled="true" >
						</bf:text>
					</bc:col>
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
	<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
	<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
</body>
</html>
