<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>单笔关联交易交易</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="singleRelatedTransactionBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${singleRelatedTransaction.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/singleRelatedTransaction/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易id" col="4" formControl="true"/>
						<bf:text id="transId" name="transId" col="8" value="${singleRelatedTransaction.transId}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/singleRelatedTransaction/check" name="transId" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="报送记录版本" col="4" formControl="true"/>
						<bf:text id="reportVersion" name="reportVersion" col="8" value="${singleRelatedTransaction.reportVersion}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="主体类型（1-保险，2-银行）" col="4" formControl="true"/>
						<bf:text id="originalType" name="originalType" col="8" value="${singleRelatedTransaction.originalType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="机构类型" col="4" formControl="true"/>
						<bf:text id="originalClass" name="originalClass" col="8" value="${singleRelatedTransaction.originalClass}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构类型名称" col="4" formControl="true"/>
						<bf:text id="originalClassName" name="originalClassName" col="8" value="${singleRelatedTransaction.originalClassName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="源主体机构登记编码" col="4" formControl="true"/>
						<bf:text id="originalRegCode" name="originalRegCode" col="8" value="${singleRelatedTransaction.originalRegCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="源主体机构登记名称" col="4" formControl="true"/>
						<bf:text id="originalRegName" name="originalRegName" col="8" value="${singleRelatedTransaction.originalRegName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易类型" col="4" formControl="true"/>
						<bf:text id="transType" name="transType" col="8" value="${singleRelatedTransaction.transType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易标的" col="4" formControl="true"/>
						<bf:text id="transObject" name="transObject" col="8" value="${singleRelatedTransaction.transObject}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易标的情况说明" col="4" formControl="true"/>
						<bf:text id="transObjectDesc" name="transObjectDesc" col="8" value="${singleRelatedTransaction.transObjectDesc}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易提交时间" col="4" formControl="true"/>
						<bf:datebox id="signTime" name="signTime" col="8"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易批准日期" col="4" formControl="true"/>
						<bf:datebox id="approvalTime" name="approvalTime" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="该笔交易所有关联方名称" col="4" formControl="true"/>
						<bf:text id="counterpartys" name="counterpartys" col="8" value="${singleRelatedTransaction.counterpartys}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易评价" col="4" formControl="true"/>
						<bf:text id="transComment" name="transComment" col="8" value="${singleRelatedTransaction.transComment}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易金额" col="4" formControl="true"/>
						<bf:text id="transAmount" name="transAmount" col="8" value="${singleRelatedTransaction.transAmount}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易价格" col="4" formControl="true"/>
						<bf:text id="transPrice" name="transPrice" col="8" value="${singleRelatedTransaction.transPrice}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易本年度累计金额" col="4" formControl="true"/>
						<bf:text id="transCountYear" name="transCountYear" col="8" value="${singleRelatedTransaction.transCountYear}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易目的" col="4" formControl="true"/>
						<bf:text id="transPurpose" name="transPurpose" col="8" value="${singleRelatedTransaction.transPurpose}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="定价策略" col="4" formControl="true"/>
						<bf:text id="pricingStrategy" name="pricingStrategy" col="8" value="${singleRelatedTransaction.pricingStrategy}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="对财务状况及经营成果的影响" col="4" formControl="true"/>
						<bf:text id="impactFinancialOperating" name="impactFinancialOperating" col="8" value="${singleRelatedTransaction.impactFinancialOperating}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上一年末总资产（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="lastYearAmount" name="lastYearAmount" col="8" value="${singleRelatedTransaction.lastYearAmount}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="保险：上一年末净资产（万元/人民币），银行：上季末资本净额（万元/人民币）" col="4" formControl="true"/>
						<bf:text id="lastYearNetAssets" name="lastYearNetAssets" col="8" value="${singleRelatedTransaction.lastYearNetAssets}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="其他说明事项" col="4" formControl="true"/>
						<bf:text id="otherMatters" name="otherMatters" col="8" value="${singleRelatedTransaction.otherMatters}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易收益与损失" col="4" formControl="true"/>
						<bf:text id="incomeLoss" name="incomeLoss" col="8" value="${singleRelatedTransaction.incomeLoss}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易方在交易中所占权益的性质与比重" col="4" formControl="true"/>
						<bf:text id="natureProportion" name="natureProportion" col="8" value="${singleRelatedTransaction.natureProportion}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="最近报送时间" col="4" formControl="true"/>
						<bf:datebox id="operateTime" name="operateTime" col="8"/>
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
									$.mask.post('${base}/bus/singleRelatedTransaction/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/singleRelatedTransaction/list';
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
								window.location = '${base}/bus/singleRelatedTransaction/list';
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
