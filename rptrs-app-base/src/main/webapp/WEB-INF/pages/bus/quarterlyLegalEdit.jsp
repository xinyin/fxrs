<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>季报保险公司与单一法人主体资金运用关联关系统计表</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="quarterlyLegalBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${quarterlyLegal.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/quarterlyLegal/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联交易季报主键" col="4" formControl="true"/>
						<bf:text id="reportId" name="reportId" col="8" value="${quarterlyLegal.reportId}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="法人主键" col="4" formControl="true"/>
						<bf:text id="corpId" name="corpId" col="8" value="${quarterlyLegal.corpId}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="法人主体名称" col="4" formControl="true"/>
						<bf:text id="corpName" name="corpName" col="8" value="${quarterlyLegal.corpName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="法人统一社会信用代码" col="4" formControl="true"/>
						<bf:text id="corpUniscCode" name="corpUniscCode" col="8" value="${quarterlyLegal.corpUniscCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="法人境内外" col="4" formControl="true"/>
						<bf:text id="corpRegisteAddress" name="corpRegisteAddress" col="8" value="${quarterlyLegal.corpRegisteAddress}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="法人投资金额" col="4" formControl="true"/>
						<bf:text id="corpInvestFund" name="corpInvestFund" col="8" value="${quarterlyLegal.corpInvestFund}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="法人主体总资产" col="4" formControl="true"/>
						<bf:text id="corpTotalFund" name="corpTotalFund" col="8" value="${quarterlyLegal.corpTotalFund}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="法人投资金额占保险公司上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="corpInvestYtotalPct" name="corpInvestYtotalPct" col="8" value="${quarterlyLegal.corpInvestYtotalPct}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="法人主体总资产占保险公司上年末总资产百分比" col="4" formControl="true"/>
						<bf:text id="corpAssetsYtotalPct" name="corpAssetsYtotalPct" col="8" value="${quarterlyLegal.corpAssetsYtotalPct}">
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
									$.mask.post('${base}/bus/quarterlyLegal/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/quarterlyLegal/list';
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
								window.location = '${base}/bus/quarterlyLegal/list';
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
