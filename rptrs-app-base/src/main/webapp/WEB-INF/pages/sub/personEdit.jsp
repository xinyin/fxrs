<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>Person</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="personBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="pid" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${person.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/sub/person/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="源主体银行机构" col="4" formControl="true"/>
						<bf:text id="orignalOrg" name="orignalOrg" col="8" value="${person.orignalOrg}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="源主体银行机构名称" col="4" formControl="true"/>
						<bf:text id="orignalOrgName" name="orignalOrgName" col="8" value="${person.orignalOrgName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="姓名" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${person.name}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="性别" col="4" formControl="true"/>
						<bf:text id="sex" name="sex" col="8" value="${person.sex}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="国别/地区" col="4" formControl="true"/>
						<bf:text id="nationality" name="nationality" col="8" value="${person.nationality}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="民族" col="4" formControl="true"/>
						<bf:text id="race" name="race" col="8" value="${person.race}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="境内(外)标志" col="4" formControl="true"/>
						<bf:text id="domesticOrForeign" name="domesticOrForeign" col="8" value="${person.domesticOrForeign}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:text id="identityType" name="identityType" col="8" value="${person.identityType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identityNo" name="identityNo" col="8" value="${person.identityNo}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="出生日期" col="4" formControl="true"/>
						<bf:datebox id="birthdate" name="birthdate" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="移动电话" col="4" formControl="true"/>
						<bf:text id="mobile" name="mobile" col="8" value="${person.mobile}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="邮箱" col="4" formControl="true"/>
						<bf:text id="email" name="email" col="8" value="${person.email}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="职位" col="4" formControl="true"/>
						<bf:text id="dutyCode" name="dutyCode" col="8" value="${person.dutyCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="前置流程状态0-已抽取 1-校验不通过 2-待统计 3-待报送 4-已报送" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${person.status}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="前置流程状态说明" col="4" formControl="true"/>
						<bf:text id="statusComment" name="statusComment" col="8" value="${person.statusComment}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="婚姻状况" col="4" formControl="true"/>
						<bf:text id="maritalStatus" name="maritalStatus" col="8" value="${person.maritalStatus}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="机构名称" col="4" formControl="true"/>
						<bf:text id="mgerOrg" name="mgerOrg" col="8" value="${person.mgerOrg}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="口径" col="4" formControl="true"/>
						<bf:text id="regulator" name="regulator" col="8" value="${person.regulator}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否黑名单" col="4" formControl="true"/>
						<bf:text id="isBlack" name="isBlack" col="8" value="${person.isBlack}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="加入黑名单原因" col="4" formControl="true"/>
						<bf:text id="blackReason" name="blackReason" col="8" value="${person.blackReason}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否为股东" col="4" formControl="true"/>
						<bf:text id="isShareholder" name="isShareholder" col="8" value="${person.isShareholder}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="股东代码（账户号）" col="4" formControl="true"/>
						<bf:text id="shareholdersCode" name="shareholdersCode" col="8" value="${person.shareholdersCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易账户开户行" col="4" formControl="true"/>
						<bf:text id="openBank" name="openBank" col="8" value="${person.openBank}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易账户账号" col="4" formControl="true"/>
						<bf:text id="tradeAccount" name="tradeAccount" col="8" value="${person.tradeAccount}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="入股时间" col="4" formControl="true"/>
						<bf:datebox id="inTime" name="inTime" col="8"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="入股价格" col="4" formControl="true"/>
						<bf:text id="inPrice" name="inPrice" col="8" value="${person.inPrice}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="持股数量（股）" col="4" formControl="true"/>
						<bf:text id="holeShareAmount" name="holeShareAmount" col="8" value="${person.holeShareAmount}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="持股占比" col="4" formControl="true"/>
						<bf:text id="shareholdingProportion" name="shareholdingProportion" col="8" value="${person.shareholdingProportion}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="总冻结股数（股）" col="4" formControl="true"/>
						<bf:text id="frozenShareAmount" name="frozenShareAmount" col="8" value="${person.frozenShareAmount}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="总质押股数（股）" col="4" formControl="true"/>
						<bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="8" value="${person.pledgedShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联方类型" col="4" formControl="true"/>
						<bf:text id="relType" name="relType" col="8" value="${person.relType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="政治面貌" col="4" formControl="true"/>
						<bf:text id="politicsStatus" name="politicsStatus" col="8" value="${person.politicsStatus}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="学历" col="4" formControl="true"/>
						<bf:text id="educationBackground" name="educationBackground" col="8" value="${person.educationBackground}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="居住地址" col="4" formControl="true"/>
						<bf:text id="residentialAddress" name="residentialAddress" col="8" value="${person.residentialAddress}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="工作单位" col="4" formControl="true"/>
						<bf:text id="workUnits" name="workUnits" col="8" value="${person.workUnits}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="单位地址" col="4" formControl="true"/>
						<bf:text id="unitAddress" name="unitAddress" col="8" value="${person.unitAddress}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="数据来源" col="4" formControl="true"/>
						<bf:text id="dataSource" name="dataSource" col="8" value="${person.dataSource}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否本行主要股东" col="4" formControl="true"/>
						<bf:text id="isMainGd" name="isMainGd" col="8" value="${person.isMainGd}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="股权证编号" col="4" formControl="true"/>
						<bf:text id="capitalNumber" name="capitalNumber" col="8" value="${person.capitalNumber}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否具有非持股表决权" col="4" formControl="true"/>
						<bf:text id="isNoShareVote" name="isNoShareVote" col="8" value="${person.isNoShareVote}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="表决权占比" col="4" formControl="true"/>
						<bf:text id="voteProportion" name="voteProportion" col="8" value="${person.voteProportion}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="限制表决权情况" col="4" formControl="true"/>
						<bf:text id="limitVoteSituation" name="limitVoteSituation" col="8" value="${person.limitVoteSituation}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易余额" col="4" formControl="true"/>
						<bf:text id="tradeBalance" name="tradeBalance" col="8" value="${person.tradeBalance}">
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
									$.mask.post('${base}/sub/person/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/sub/person/list';
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
								window.location = '${base}/sub/person/list';
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
