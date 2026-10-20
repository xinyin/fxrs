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
		<bc:ibox id="relPsnSuspectBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增长主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${relPsnSuspect.pid}"  disabled="${opt == 'edit'}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="登记编码" col="4" formControl="true"/>
						<bf:text id="regCode" name="regCode" col="8" value="${relPsnSuspect.regCode}">
							<bv:remote url="${base}/bus/relPsnSuspect/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="源主体银行机构" col="4" formControl="true"/>
						<bf:text id="orignalOrg" name="orignalOrg" col="8" value="${relPsnSuspect.orignalOrg}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="源主体银行机构名称" col="4" formControl="true"/>
						<bf:text id="orignalOrgName" name="orignalOrgName" col="8" value="${relPsnSuspect.orignalOrgName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="姓名" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${relPsnSuspect.name}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="性別" col="4" formControl="true"/>
						<bf:select id="sex" name="sex" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Sex" value="${relPsnSuspect.sex}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="国别/地区" col="4" formControl="true"/>
						<bf:text id="nationality" name="nationality" col="8" value="${relPsnSuspect.nationality}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="境内外标识" col="4" formControl="true"/>
						<bf:select id="domesticOrForeign" name="domesticOrForeign" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" value="${relPsnSuspect.domesticOrForeign}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="民族" col="4" formControl="true"/>
						<bf:select id="race" name="race" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Race" value="${relPsnSuspect.race}">
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:select id="identifyType" name="identifyType" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsnSuspect.identifyType}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identifyNumber" name="identifyNumber" col="8" value="${relPsnSuspect.identifyNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="出生日期" col="4" formControl="true"/>
						<bf:text id="birthdate" name="birthdate" col="8" value="${relPsnSuspect.birthdate}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="移动电话" col="4" formControl="true"/>
						<bf:text id="mobile" name="mobile" col="8" value="${relPsnSuspect.mobile}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="邮箱" col="4" formControl="true"/>
						<bf:text id="email" name="email" col="8" value="${relPsnSuspect.email}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="职位" col="4" formControl="true"/>
						<bf:text id="dutyCode" name="dutyCode" col="8" value="${relPsnSuspect.dutyCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="审批状态    未提交/已提交/未审批" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${relPsnSuspect.status}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="审批状态说明" col="4" formControl="true"/>
						<bf:text id="statusComment" name="statusComment" col="8" value="${relPsnSuspect.statusComment}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="婚姻状况" col="4" formControl="true"/>
						<bf:select id="maritalStatus" name="maritalStatus" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsnSuspect.maritalStatus}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否有效   0|无效1|有效" col="4" formControl="true"/>
						<bf:text id="validStatus" name="validStatus" col="8" value="${relPsnSuspect.validStatus}">
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
						<bf:text id="mgerOrg" name="mgerOrg" col="8" value="${relPsnSuspect.mgerOrg}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上一级审批部门" col="4" formControl="true"/>
						<bf:text id="upApprovalDepartment" name="upApprovalDepartment" col="8" value="${relPsnSuspect.upApprovalDepartment}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="下一级审批部门" col="4" formControl="true"/>
						<bf:text id="nextApprovalDepartment" name="nextApprovalDepartment" col="8" value="${relPsnSuspect.nextApprovalDepartment}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上一级审批人" col="4" formControl="true"/>
						<bf:text id="upApprovalPeople" name="upApprovalPeople" col="8" value="${relPsnSuspect.upApprovalPeople}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="下一级审批人" col="4" formControl="true"/>
						<bf:text id="nextApprovalPeople" name="nextApprovalPeople" col="8" value="${relPsnSuspect.nextApprovalPeople}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="银保监口径  0|否 1|是" col="4" formControl="true"/>
						<bf:text id="regulatorYbj" name="regulatorYbj" col="8" value="${relPsnSuspect.regulatorYbj}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证监会口径（0|否1|是）" col="4" formControl="true"/>
						<bf:text id="regulatorZjh" name="regulatorZjh" col="8" value="${relPsnSuspect.regulatorZjh}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="财政部口径（0|否1|是）" col="4" formControl="true"/>
						<bf:text id="regulatorCzb" name="regulatorCzb" col="8" value="${relPsnSuspect.regulatorCzb}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="其他口径1（0|否1|是）" col="4" formControl="true"/>
						<bf:text id="regulatorOth1" name="regulatorOth1" col="8" value="${relPsnSuspect.regulatorOth1}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="其他口径2（0|否1|是）" col="4" formControl="true"/>
						<bf:text id="regulatorOth2" name="regulatorOth2" col="8" value="${relPsnSuspect.regulatorOth2}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否黑名单（0|否1|是）" col="4" formControl="true"/>
						<bf:text id="isBlack" name="isBlack" col="8" value="${relPsnSuspect.isBlack}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-4">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/relPsnSuspect/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/relPsnSuspect/list';
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
								window.location = '${base}/bus/relPsnSuspect/list';
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
