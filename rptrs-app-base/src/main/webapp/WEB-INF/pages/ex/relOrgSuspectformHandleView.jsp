<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="form"/>
	<title>RelOrgSuspect</title>
	<style>
		.form-group .col-xs-4{
			margin: 0.3% 0!important;
			padding-top:7px;
		}
		.boxse .form-group .col-xs-4{
			margin: 0.3% 0!important;
		}
		.boxse .has-success .form-control,.boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}
		.has-success .select2-dropdown, .has-success .select2-selection {
			border-color: #CCCCCC !important;
		}
		.gray-bg {
			background: white !important;
		}
	</style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
	<bc:ibox id="relOrgSuspectBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="pid" name="pid" value="${relOrgSuspect.pid}"/>
			<div class="boxse">
				<bf:formGroup>
					<bf:formGroup>
						<bc:col col="4">
							<bc:label cls="required" text="法人名称" col="5" formControl="true"/>
							<bf:text id="orgName" name="orgName" col="7" disabled="true" value="${relOrgSuspect.orgName}">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
						<bc:col col="4">
							<bc:label cls="required" text="证件类型" col="5" formControl="true"/>
							<bf:select id="identifyType" name="identifyType" disabled="true" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" value="${relOrgSuspect.identifyType}">
								<bf:option text="请选择" value=""/>
								<bv:notEmpty/>
							</bf:select>
						</bc:col>
						<bc:col col="4">
							<bc:label cls="required" text="证件号码" col="5" formControl="true"/>
							<bf:text id="uniscCode" name="uniscCode" disabled="true" col="7" value="${relOrgSuspect.uniscCode}">
								<bv:notEmpty/>
								<bv:stringLength min="8" max="18" trim="true" message="请输入正确的证件号码"/>
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup>
						<bc:col col="4">
							<bc:label cls="required" text="境内/境外" col="5" formControl="true"/>
							<bf:checkGroup name="domesticOrForeign" disabled="true" col="7" value="${relOrgSuspect.domesticOrForeign}">
								<bf:radio id="chk" value="1" label="境内" checked="true">
									<bv:notEmpty/>
								</bf:radio>
								<bf:radio id="chk1" value="2" label="境外" />
							</bf:checkGroup>
						</bc:col>
						<bc:col col="4">
							<bc:label cls="required" text="是否为本主体控股公司" col="5" formControl="true"/>
							<bf:checkGroup id="isSubsidiary" disabled="true" name="isSubsidiary" col="7" value="${relOrgSuspect.isSubsidiary}">
								<bf:radio id="chk" value="1" label="是" checked="true">
									<bv:notEmpty/>
								</bf:radio>
								<bf:radio id="chk1" value="0" label="否" />
							</bf:checkGroup>
						</bc:col>
						<bc:col col="4">
							<bc:label text="机构状态" col="5" formControl="true"/>
							<bf:select id="status" name="status" disabled="true" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/BUS-Status" value="${relOrgSuspect.status}">
								<bf:option text="请选择" value=""></bf:option>
							</bf:select>
						</bc:col>

					</bf:formGroup>
					<bf:formGroup>
						<bc:col col="4">
							<bc:label text="企业类型" col="5" formControl="true"/>
							<bf:select id="orgType" name="orgType" disabled="true" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/BUS-CompanyType" value="${relOrgSuspect.orgType}">
								<bf:option text="请选择" value=""></bf:option>
							</bf:select>
						</bc:col>
						<bc:col col="4">
							<bc:label text="注册地址" col="5" formControl="true"/>
							<bf:text id="regAddress" name="regAddress" disabled="true" col="7" value="${relOrgSuspect.regAddress}">
							</bf:text>
						</bc:col>
						<bc:col col="4">
							<bc:label text="注册资本(万元/人民币)" col="5" formControl="true"/>
							<bf:text id="regCapital" name="regCapital" disabled="true" col="7" value="${relOrgSuspect.regCapital}">
							</bf:text>
						</bc:col>
					</bf:formGroup>

					<bf:formGroup>
						<bc:col col="4">
							<bc:label text="法定代表人" col="5" formControl="true"/>
							<bf:text id="legalPerson" name="legalPerson" disabled="true" col="7" value="${relOrgSuspect.legalPerson}">
							</bf:text>
						</bc:col>
						<bc:col col="4">
							<bc:label text="法定代表人证件类型" col="5" formControl="true"/>
							<bf:select id="legalIdentifyType" name="legalIdentifyType" disabled="true" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrgSuspect.legalIdentifyType}">
								<bf:option text="请选择" value=""/>
							</bf:select>
						</bc:col>
						<bc:col col="4">
							<bc:label text="法定代表人证件号码" col="5" formControl="true"/>
							<bf:text id="legalIdentifyNumber" disabled="true" name="legalIdentifyNumber" col="7"
									 value="${relOrgSuspect.legalIdentifyNumber}">
								<bv:stringLength min="8" max="18" trim="true" message="请输入正确的证件号码"/>
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup>
						<bc:col col="4">
							<bc:label text="统计口径" col="5" formControl="true"/>
							<bf:text id="regulatorYbj" name="regulatorYbj" col="7"
									 value="银保监会" disabled="true">
							</bf:text>
						</bc:col>
						<bc:col col="4">
							<bc:label text="集团证件号" col="5" formControl="true"/>
							<bf:text id="memGroup" name="memGroup" disabled="true" col="7" value="${relOrgSuspect.memGroup}">
							</bf:text>
						</bc:col>
						<bc:col col="4">
							<div id="note" style="float: right;margin: 0.2% 0 !important;">
								<p style="color: red;margin: auto;text-align: center;font-weight: 700;line-height: 2rem;font-size: smaller">根据监管要求，若两个关联方或多个关联方属于同一集团时，需要合并计算。企业所属集团时，集团证件号必填，按照统一社会信用代码填写。</p>
							</div>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup>
						<bc:col col="12">
							<bc:label text="经营范围" col="1" formControl="true"/>
							<bf:text id="businessScope" disabled="true" name="businessScope" col="10" multiple="true" value="${relOrgSuspect.businessScope}"></bf:text>
						</bc:col>
					</bf:formGroup>
				</bf:formGroup>
			</div>
		</bf:form>
	</bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
</body>
</html>
