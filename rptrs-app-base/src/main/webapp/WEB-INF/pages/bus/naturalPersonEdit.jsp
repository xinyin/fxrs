<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>自然人</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="naturalPersonBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增长主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${naturalPerson.pid}"  disabled="${opt == 'edit'}">
							<%--<bv:jepCode/>--%>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/naturalPerson/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="登记编码" col="4" formControl="true"/>
						<bf:text id="regCode" name="regCode" col="8" value="${naturalPerson.regCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="源主体银行或保险机构" col="4" formControl="true"/>
						<bf:text id="orignalOrg" name="orignalOrg" col="8" value="${naturalPerson.orignalOrg}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="源主体银行或保险机构名称" col="4" formControl="true"/>
						<bf:text id="orignalOrgName" name="orignalOrgName" col="8" value="${naturalPerson.orignalOrgName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="姓名" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${naturalPerson.name}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="性別" col="4" formControl="true"/>
						<bf:text id="sex" name="sex" col="8" value="${naturalPerson.sex}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="国别/地区" col="4" formControl="true"/>
						<bf:text id="nationality" name="nationality" col="8" value="${naturalPerson.nationality}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="境内/境外(1-境内，2-境外)" col="4" formControl="true"/>
						<bf:text id="domesticOrForeign" name="domesticOrForeign" col="8" value="${naturalPerson.domesticOrForeign}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="民族" col="4" formControl="true"/>
						<bf:text id="race" name="race" col="8" value="${naturalPerson.race}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:text id="identifyType" name="identifyType" col="8" value="${naturalPerson.identifyType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identifyNumber" name="identifyNumber" col="8" value="${naturalPerson.identifyNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="出生日期" col="4" formControl="true"/>
						<bf:text id="birthdate" name="birthdate" col="8" value="${naturalPerson.birthdate}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="移动电话" col="4" formControl="true"/>
						<bf:text id="mobile" name="mobile" col="8" value="${naturalPerson.mobile}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="邮箱" col="4" formControl="true"/>
						<bf:text id="email" name="email" col="8" value="${naturalPerson.email}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="职务编码" col="4" formControl="true"/>
                        <bf:text id="reportVersion" name="reportVersion" col="8" value="${naturalPerson.dutyCode}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="状态" col="4" formControl="true"/>
                        <bf:datebox id="status" name="invalidTime" col="8"/>
                    </bc:col>
                </bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="报送版本号" col="4" formControl="true"/>
						<bf:text id="reportVersion" name="reportVersion" col="8" value="${naturalPerson.reportVersion}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否有效 0-无效，1-有效" col="4" formControl="true"/>
						<bf:text id="validStatus" name="validStatus" col="8" value="${naturalPerson.validStatus}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否被报送过（0-历史上从未报送过，1-已被报送过）" col="4" formControl="true"/>
						<bf:text id="isReported" name="isReported" col="8" value="${naturalPerson.isReported}">
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
									$.mask.post('${base}/bus/naturalPerson/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/naturalPerson/list';
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
								window.location = '${base}/bus/naturalPerson/list';
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
