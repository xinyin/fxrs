<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>机构黑名单</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="legalPersonBlackBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${legalPersonBlack.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/legalPersonBlack/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="登记编码" col="4" formControl="true"/>
						<bf:text id="regCode" name="regCode" col="8" value="${legalPersonBlack.regCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="源主体银行" col="4" formControl="true"/>
						<bf:text id="orignalOrg" name="orignalOrg" col="8" value="${legalPersonBlack.orignalOrg}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="源主体银行名称" col="4" formControl="true"/>
						<bf:text id="orignalOrgName" name="orignalOrgName" col="8" value="${legalPersonBlack.orignalOrgName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="企业名称" col="4" formControl="true"/>
						<bf:text id="orgName" name="orgName" col="8" value="${legalPersonBlack.orgName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="企业类型" col="4" formControl="true"/>
						<bf:text id="orgType" name="orgType" col="8" value="${legalPersonBlack.orgType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="境内/境外(1-境内，2-境外)" col="4" formControl="true"/>
						<bf:text id="domesticOrForeign" name="domesticOrForeign" col="8" value="${legalPersonBlack.domesticOrForeign}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="社会统一信用代码" col="4" formControl="true"/>
						<bf:text id="uniscCode" name="uniscCode" col="8" value="${legalPersonBlack.uniscCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="注册资本(万元/人民币)" col="4" formControl="true"/>
						<bf:text id="regCapital" name="regCapital" col="8" value="${legalPersonBlack.regCapital}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="注册资本币种代码" col="4" formControl="true"/>
						<bf:text id="regCapitalCurrency" name="regCapitalCurrency" col="8" value="${legalPersonBlack.regCapitalCurrency}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="营业范围" col="4" formControl="true"/>
						<bf:text id="businessScope" name="businessScope" col="8" value="${legalPersonBlack.businessScope}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构状态（1-正常，2-停业）" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${legalPersonBlack.status}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="法定代表人" col="4" formControl="true"/>
						<bf:text id="legalPerson" name="legalPerson" col="8" value="${legalPersonBlack.legalPerson}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="注册地" col="4" formControl="true"/>
						<bf:text id="regAddress" name="regAddress" col="8" value="${legalPersonBlack.regAddress}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="报送版本号" col="4" formControl="true"/>
						<bf:text id="reportVersion" name="reportVersion" col="8" value="${legalPersonBlack.reportVersion}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否有效 0-无效，1-有效" col="4" formControl="true"/>
						<bf:text id="validStatus" name="validStatus" col="8" value="${legalPersonBlack.validStatus}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否被报送过（0-历史上从未报送过，1-已被报送过）" col="4" formControl="true"/>
						<bf:text id="isReported" name="isReported" col="8" value="${legalPersonBlack.isReported}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:text id="identifyType" name="identifyType" col="8" value="${legalPersonBlack.identifyType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件代码" col="4" formControl="true"/>
						<bf:text id="identifyNumber" name="identifyNumber" col="8" value="${legalPersonBlack.identifyNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="计入黑名单时间" col="4" formControl="true"/>
						<bf:datebox id="blackDate" name="blackDate" col="8"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="计入黑名单原因" col="4" formControl="true"/>
						<bf:text id="blackCause" name="blackCause" col="8" value="${legalPersonBlack.blackCause}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="提交人" col="4" formControl="true"/>
						<bf:text id="commitName" name="commitName" col="8" value="${legalPersonBlack.commitName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="提交人职位" col="4" formControl="true"/>
						<bf:text id="commitDutyCode" name="commitDutyCode" col="8" value="${legalPersonBlack.commitDutyCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="复核人" col="4" formControl="true"/>
						<bf:text id="checkName" name="checkName" col="8" value="${legalPersonBlack.checkName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="复核人职位" col="4" formControl="true"/>
						<bf:text id="checkDutyCode" name="checkDutyCode" col="8" value="${legalPersonBlack.checkDutyCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="生效时间" col="4" formControl="true"/>
						<bf:datebox id="activeTime" name="activeTime" col="8"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="失效时间" col="4" formControl="true"/>
						<bf:datebox id="invalidTime" name="invalidTime" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构类型" col="4" formControl="true"/>
						<bf:text id="bodyType" name="bodyType" col="8" value="${legalPersonBlack.bodyType}">
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
									$.mask.post('${base}/bus/legalPersonBlack/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/legalPersonBlack/list';
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
								window.location = '${base}/bus/legalPersonBlack/list';
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
