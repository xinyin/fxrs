<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联关系自然人表</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="relPsnBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增长主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${relPsn.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/relPsn/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="登记编码" col="4" formControl="true"/>
						<bf:text id="regCode" name="regCode" col="8" value="${relPsn.regCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="源主体银行机构" col="4" formControl="true"/>
						<bf:text id="orignalOrg" name="orignalOrg" col="8" value="${relPsn.orignalOrg}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="源主体银行机构名称" col="4" formControl="true"/>
						<bf:text id="orignalOrgName" name="orignalOrgName" col="8" value="${relPsn.orignalOrgName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="姓名" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${relPsn.name}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="性別" col="4" formControl="true"/>
						<bf:text id="sex" name="sex" col="8" value="${relPsn.sex}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="国别/地区" col="4" formControl="true"/>
						<bf:text id="nationality" name="nationality" col="8" value="${relPsn.nationality}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="境内外标识" col="4" formControl="true"/>
						<bf:text id="domesticOrForeign" name="domesticOrForeign" col="8" value="${relPsn.domesticOrForeign}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="民族" col="4" formControl="true"/>
						<bf:text id="race" name="race" col="8" value="${relPsn.race}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:text id="identifyType" name="identifyType" col="8" value="${relPsn.identifyType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identifyNumber" name="identifyNumber" col="8" value="${relPsn.identifyNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="出生日期" col="4" formControl="true"/>
						<bf:text id="birthdate" name="birthdate" col="8" value="${relPsn.birthdate}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="移动电话" col="4" formControl="true"/>
						<bf:text id="mobile" name="mobile" col="8" value="${relPsn.mobile}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="邮箱" col="4" formControl="true"/>
						<bf:text id="email" name="email" col="8" value="${relPsn.email}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="职位" col="4" formControl="true"/>
						<bf:text id="dutyCode" name="dutyCode" col="8" value="${relPsn.dutyCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="操作状态    未提交/已提交/未审批" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${relPsn.status}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="操作状态说明" col="4" formControl="true"/>
						<bf:text id="statusComment" name="statusComment" col="8" value="${relPsn.statusComment}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="婚姻状况" col="4" formControl="true"/>
						<bf:text id="maritalStatus" name="maritalStatus" col="8" value="${relPsn.maritalStatus}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否有效   0|无效1|有效" col="4" formControl="true"/>
						<bf:text id="validStatus" name="validStatus" col="8" value="${relPsn.validStatus}">
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
						<bc:label text="机构名称(默认是总行)" col="4" formControl="true"/>
						<bf:text id="mgerOrg" name="mgerOrg" col="8" value="${relPsn.mgerOrg}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上一级审批部门" col="4" formControl="true"/>
						<bf:text id="upApprovalDepartment" name="upApprovalDepartment" col="8" value="${relPsn.upApprovalDepartment}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="下一级审批部门" col="4" formControl="true"/>
						<bf:text id="nextApprovalDepartment" name="nextApprovalDepartment" col="8" value="${relPsn.nextApprovalDepartment}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上一级审批人" col="4" formControl="true"/>
						<bf:text id="upApprovalPeople" name="upApprovalPeople" col="8" value="${relPsn.upApprovalPeople}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="下一级审批人" col="4" formControl="true"/>
						<bf:text id="nextApprovalPeople" name="nextApprovalPeople" col="8" value="${relPsn.nextApprovalPeople}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="银保监口径  0|否 1|是" col="4" formControl="true"/>
						<bf:text id="regulatorYbj" name="regulatorYbj" col="8" value="${relPsn.regulatorYbj}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证监会口径（0|否1|是）" col="4" formControl="true"/>
						<bf:text id="regulatorZjh" name="regulatorZjh" col="8" value="${relPsn.regulatorZjh}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="财政部口径（0|否1|是）" col="4" formControl="true"/>
						<bf:text id="regulatorCzb" name="regulatorCzb" col="8" value="${relPsn.regulatorCzb}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="其他口径1（0|否1|是）" col="4" formControl="true"/>
						<bf:text id="regulatorOth1" name="regulatorOth1" col="8" value="${relPsn.regulatorOth1}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="其他口径2（0|否1|是）" col="4" formControl="true"/>
						<bf:text id="regulatorOth2" name="regulatorOth2" col="8" value="${relPsn.regulatorOth2}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否黑名单（0|否1|是）" col="4" formControl="true"/>
						<bf:text id="isBlack" name="isBlack" col="8" value="${relPsn.isBlack}">
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
									$.mask.post('${base}/bus/relPsn/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/relPsn/list';
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
								window.location = '${base}/bus/relPsn/list';
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
