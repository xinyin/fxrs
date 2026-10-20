<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>Organ</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="organBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="pid" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${organ.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/sub/organ/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="姓名" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${organ.name}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="境内(外)标志" col="4" formControl="true"/>
						<bf:text id="domesticOrForeign" name="domesticOrForeign" col="8" value="${organ.domesticOrForeign}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identityNo" name="identityNo" col="8" value="${organ.identityNo}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="注册资本(万元/人民币)" col="4" formControl="true"/>
						<bf:text id="regCapital" name="regCapital" col="8" value="${organ.regCapital}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="注册资本币种代码" col="4" formControl="true"/>
						<bf:text id="regCapitalCurrency" name="regCapitalCurrency" col="8" value="${organ.regCapitalCurrency}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="营业范围" col="4" formControl="true"/>
						<bf:text id="businessScope" name="businessScope" col="8" value="${organ.businessScope}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构状态" col="4" formControl="true"/>
						<bf:text id="orgStatus" name="orgStatus" col="8" value="${organ.orgStatus}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="法定代表人" col="4" formControl="true"/>
						<bf:text id="legalPerson" name="legalPerson" col="8" value="${organ.legalPerson}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="法定代表人_证件类型" col="4" formControl="true"/>
						<bf:text id="legalIdentifyType" name="legalIdentifyType" col="8" value="${organ.legalIdentifyType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="社会统一信用代码" col="4" formControl="true"/>
						<bf:text id="legalIdentifyNumber" name="legalIdentifyNumber" col="8" value="${organ.legalIdentifyNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="注册地" col="4" formControl="true"/>
						<bf:text id="regAddress" name="regAddress" col="8" value="${organ.regAddress}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否有效" col="4" formControl="true"/>
						<bf:text id="validStatus" name="validStatus" col="8" value="${organ.validStatus}">
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
						<bc:label text="前置流程状态" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${organ.status}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="前置流程状态说明" col="4" formControl="true"/>
						<bf:text id="statusComment" name="statusComment" col="8" value="${organ.statusComment}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构名称" col="4" formControl="true"/>
						<bf:text id="mgerOrg" name="mgerOrg" col="8" value="${organ.mgerOrg}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="口径" col="4" formControl="true"/>
						<bf:text id="regulatorOth" name="regulatorOth" col="8" value="${organ.regulatorOth}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否黑名单" col="4" formControl="true"/>
						<bf:text id="isBlack" name="isBlack" col="8" value="${organ.isBlack}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="加入黑名单原因" col="4" formControl="true"/>
						<bf:text id="blackReason" name="blackReason" col="8" value="${organ.blackReason}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否为股东" col="4" formControl="true"/>
						<bf:text id="isShareholder" name="isShareholder" col="8" value="${organ.isShareholder}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="股东代码" col="4" formControl="true"/>
						<bf:text id="shareholdersCode" name="shareholdersCode" col="8" value="${organ.shareholdersCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否国有企业" col="4" formControl="true"/>
						<bf:text id="stateOwned" name="stateOwned" col="8" value="${organ.stateOwned}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="账户关联银行账户开户行" col="4" formControl="true"/>
						<bf:text id="openBank" name="openBank" col="8" value="${organ.openBank}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联银行户账号" col="4" formControl="true"/>
						<bf:text id="tradeAccount" name="tradeAccount" col="8" value="${organ.tradeAccount}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="持股数量" col="4" formControl="true"/>
						<bf:text id="holeShareAmount" name="holeShareAmount" col="8" value="${organ.holeShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="持股占比" col="4" formControl="true"/>
						<bf:text id="shareholdingProportion" name="shareholdingProportion" col="8" value="${organ.shareholdingProportion}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="入股时间" col="4" formControl="true"/>
						<bf:datebox id="inTime" name="inTime" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="入股价格" col="4" formControl="true"/>
						<bf:text id="inPrice" name="inPrice" col="8" value="${organ.inPrice}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="总冻结股数（股）" col="4" formControl="true"/>
						<bf:text id="frozenShare" name="frozenShare" col="8" value="${organ.frozenShare}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="总质押股数（股）" col="4" formControl="true"/>
						<bf:text id="pledgedShare" name="pledgedShare" col="8" value="${organ.pledgedShare}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联方类型" col="4" formControl="true"/>
						<bf:text id="relType" name="relType" col="8" value="${organ.relType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="数据来源" col="4" formControl="true"/>
						<bf:text id="dataSource" name="dataSource" col="8" value="${organ.dataSource}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否本行主要股东" col="4" formControl="true"/>
						<bf:text id="isMainGd" name="isMainGd" col="8" value="${organ.isMainGd}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="股权证编号" col="4" formControl="true"/>
						<bf:text id="capitalNumber" name="capitalNumber" col="8" value="${organ.capitalNumber}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否具有非持股表决权" col="4" formControl="true"/>
						<bf:text id="isNoShareVote" name="isNoShareVote" col="8" value="${organ.isNoShareVote}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="表决权占比" col="4" formControl="true"/>
						<bf:text id="voteProportion" name="voteProportion" col="8" value="${organ.voteProportion}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="限制表决权情况" col="4" formControl="true"/>
						<bf:text id="limitVoteSituation" name="limitVoteSituation" col="8" value="${organ.limitVoteSituation}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否内部人" col="4" formControl="true"/>
						<bf:text id="isInsider" name="isInsider" col="8" value="${organ.isInsider}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="预警级别" col="4" formControl="true"/>
						<bf:text id="warnLeve" name="warnLeve" col="8" value="${organ.warnLeve}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="预设额度币种" col="4" formControl="true"/>
						<bf:text id="presetLimitCurrency" name="presetLimitCurrency" col="8" value="${organ.presetLimitCurrency}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="预设额度" col="4" formControl="true"/>
						<bf:text id="presetLimit" name="presetLimit" col="8" value="${organ.presetLimit}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="可用额度" col="4" formControl="true"/>
						<bf:text id="availableLimit" name="availableLimit" col="8" value="${organ.availableLimit}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="集团证件号" col="4" formControl="true"/>
						<bf:text id="memGroup" name="memGroup" col="8" value="${organ.memGroup}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="集团名称" col="4" formControl="true"/>
						<bf:text id="groupName" name="groupName" col="8" value="${organ.groupName}">
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
									$.mask.post('${base}/sub/organ/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/sub/organ/list';
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
								window.location = '${base}/sub/organ/list';
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
