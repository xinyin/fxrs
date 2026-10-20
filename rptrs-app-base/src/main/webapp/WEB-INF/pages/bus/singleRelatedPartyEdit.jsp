<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>单笔关联交易对手方</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="singleRelatedPartyBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="物理主键，自增" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${singleRelatedParty.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/singleRelatedParty/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易id" col="4" formControl="true"/>
						<bf:text id="transId" name="transId" col="8" value="${singleRelatedParty.transId}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/singleRelatedParty/check" name="transId" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="报送记录版本" col="4" formControl="true"/>
						<bf:text id="reportVersion" name="reportVersion" col="8" value="${singleRelatedParty.reportVersion}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联方类别（1-机构，2-人）" col="4" formControl="true"/>
						<bf:text id="counterpartyType" name="counterpartyType" col="8" value="${singleRelatedParty.counterpartyType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联方pid（当交易对手为机构时，对应法人信息表的pid；为个人时，对应人员信息表的pid）" col="4" formControl="true"/>
						<bf:text id="counterparty" name="counterparty" col="8" value="${singleRelatedParty.counterparty}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易对手名称" col="4" formControl="true"/>
						<bf:text id="counterpartyName" name="counterpartyName" col="8" value="${singleRelatedParty.counterpartyName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:text id="identifyType" name="identifyType" col="8" value="${singleRelatedParty.identifyType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="境内外" col="4" formControl="true"/>
						<bf:text id="domesticOrForeign" name="domesticOrForeign" col="8" value="${singleRelatedParty.domesticOrForeign}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identifyNumber" name="identifyNumber" col="8" value="${singleRelatedParty.identifyNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="统一社会信用代码" col="4" formControl="true"/>
						<bf:text id="uniscCode" name="uniscCode" col="8" value="${singleRelatedParty.uniscCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易本年度累计金额" col="4" formControl="true"/>
						<bf:text id="transCountYear" name="transCountYear" col="8" value="${singleRelatedParty.transCountYear}">
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
									$.mask.post('${base}/bus/singleRelatedParty/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/singleRelatedParty/list';
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
								window.location = '${base}/bus/singleRelatedParty/list';
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
