<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="form"/>
	<title>自然人新增或编辑</title>
	<x:jsLib/>
	<style>
		.form-group .col-xs-4{
			margin: 0.3% 0!important;
			padding-top:7px;
		}
		.boxse .form-group .col-xs-5{
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
<body class="gray-bg">
<div class="row">
	<bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="opt" name="opt" value="${opt}"/>
			<bf:hidden id="pid" name="pid" value="${relPsnSuspect.pid}"/>
			<div class="boxse">
				<bf:formGroup type="success">
					<bc:col col="4">
						<bc:label cls="required" text="姓名" col="5" formControl="true"/>
						<bf:text id="name" name="name" col="7" disabled="true" value="${relPsnSuspect.name}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label cls="required" text="证件类型" col="5" formControl="true"/>
						<bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-IdentifyType" disabled="true" value="${relPsnSuspect.identifyType}">
							<bf:option text="请选择" value=""></bf:option>
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label cls="required" text="证件号码" col="5" formControl="true" />
						<bf:text id="identifyNumber" name="identifyNumber" col="7" disabled="true" value="${relPsnSuspect.identifyNumber}">
							<%--                        click="addRelPsn()"--%>
							<bv:notEmpty/>
							<bv:stringLength min="8" max="18" trim="true"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="4">
						<bc:label cls="required" text="境内/境外" col="5" formControl="true"/>
						<bf:checkGroup name="domesticOrForeign" col="7" disabled="true" value="${relPsnSuspect.domesticOrForeign}">
							<bf:radio id="chk" value="1" label="境内" checked="true">
								<bv:notEmpty/>
							</bf:radio>
							<bf:radio id="chk1" value="2" label="境外" />
						</bf:checkGroup>
					</bc:col>
					<bc:col col="4">
						<bc:label text="性别" col="5" formControl="true"/>
						<bf:select id="sex" name="sex" col="7" dataSource="custom" disabled="true"
								   url="sys/dictItem/getDictItems/BUS-Sex" value="${relPsnSuspect.sex}">
							<bf:option text="请选择" value=""></bf:option>
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="出生日期" col="5" formControl="true"/>
						<bf:datebox name="birthdate" id="birthdate" col="7" disabled="true" format="date"
									value="${relPsnSuspect.birthdate}">
						</bf:datebox>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="4">
						<bc:label text="工作单位" col="5" formControl="true"/>
						<bf:text id="workUnits" name="workUnits" col="7" disabled="true" value="${relPsnSuspect.workUnits}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="单位地址" col="5" formControl="true"/>
						<bf:text id="unitAddress" name="unitAddress" col="7" disabled="true" value="${relPsnSuspect.unitAddress}">
							<bv:stringLength max="100" message="单位地址长度不能超过100"/>
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="移动电话" col="5" formControl="true"/>
						<bf:text id="mobile" name="mobile" col="7" disabled="true" value="${relPsnSuspect.mobile}">
							<bv:stringLength min="11" max="11" message="请输入11位数字" trim="true"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success">
					<bc:col col="4">
						<bc:label text="邮箱" col="5" formControl="true"/>
						<bf:text id="email" name="email" col="7" disabled="true" value="${relPsnSuspect.email}">
							<bv:stringLength max="32" message="邮箱长度不能超过32位"/>
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="统计口径" col="5" formControl="true"/>
						<bf:text id="regulatorYbj" name="regulatorYbj" col="7"
								 disabled='true' value="银保监会"/>
					</bc:col>
				</bf:formGroup>
			</div>
		</bf:form>
	</bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
</body>
</html>
