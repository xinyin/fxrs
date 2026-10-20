<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>RelOrg</title>
	<style>
		.hr-line-dashed {
			border-top: 1px dashed #e7eaec;
			color: #ffffff;
			background-color: #ffffff;
			height: 1px;
			margin: 7px 0 !important;
		}
		.form-group{
			margin: 0 0 0 0 !important;
		}
	</style>
</head>
<body class="gray-bg" style="background:white ">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed"  >
					<bc:col col="5">
						<bc:label text="内部人名称" col="4" formControl="true"/>
						<bf:text id="orgName" name="orgName" col="8" value="${relOrg.orgName}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:select id="identifyType" name="identifyType" col="8" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrg.identifyType}" disabled="true">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed"  >
					<bc:col col="5">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="uniscCode" name="uniscCode" col="8" value="${relOrg.uniscCode}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="法定代表人" col="4" formControl="true"/>
						<bf:text id="legalPerson" name="legalPerson" col="8" value="${relOrg.legalPerson}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
			<bf:formGroup hr="dashed">
				<bc:col col="5">
					<bc:label text="证件类型" col="4" formControl="true"/>
					<bf:select id="legalIdentifyType" name="legalIdentifyType" col="8" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrg.legalIdentifyType}" disabled="true">
					</bf:select>
				</bc:col>
				<bc:col col="5">
					<bc:label text="证件号码" col="4" formControl="true"/>
					<bf:text id="legalIdentifyNumber" name="legalIdentifyNumber" col="8" value="${relOrg.legalIdentifyNumber}" disabled="true">
					</bf:text>
				</bc:col>
			</bf:formGroup>
				<bf:formGroup hr="dashed" >

					<bc:col col="5">
						<bc:label text="机构名称" col="4" formControl="true"/>
						<bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relOrg.mgerOrg}" disabled="true">
						</bf:select>
					</bc:col>

					<bc:col col="5">
						<bc:label text="是否生效" col="4" formControl="true"/>
						<bf:text id="validStatus" name="validStatus" col="8" value="${relOrg.validStatus}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>

				<%--<bf:formGroup hr="dashed"  >
					<bc:col col="5">
						<bc:label text="企业类型" col="4" formControl="true"/>
						<bf:select id="orgType" name="orgType" col="8" dataSource="custom"
								   url="sys/dictItem/getDictItems/SUB-BusinessType" value="${relOrg.orgType}" disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="5">
						<bc:label text="营业范围" col="4" formControl="true"/>
						<bf:text id="businessScope" name="businessScope" col="8" value="${relOrg.businessScope}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed"  >
					<bc:col col="5">
						<bc:label text="机构状态" col="4" formControl="true"/>
						<bf:select id="status" name="status" col="8" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-Status" value="${relOrg.status}" disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="5">
						<bc:label text="注册资本(万元/人民币)" col="4" formControl="true"/>
						<bf:text id="regCapital" name="regCapital" col="8" value="${relOrg.regCapital}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed"  >--%>
					<%--<bc:col col="5">--%>
						<%--<bc:label text="黑名单标识" col="4" formControl="true"/>--%>
						<%--<bf:select id="isBlack" name="isBlack" col="8" dataSource="custom"--%>
								   <%--url="sys/dictItem/getDictItems/BUS-isNo" value="${relOrg.isBlack}" disabled="true">--%>
						<%--</bf:select>--%>
					<%--</bc:col>--%>
					<%--<bc:col col="5">--%>
						<%--<bc:label text="加入黑名单原因" col="4" formControl="true"/>--%>
						<%--<bf:text id="blackReason" name="blackReason" col="8" value="${relOrg.blackReason}" disabled="true">--%>
						<%--</bf:text>--%>
					<%--</bc:col>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed"  >
					<bc:col col="5">
						<bc:label text="注册资本币种代码" col="4" formControl="true"/>
						<bf:text id="regCapitalCurrency" name="regCapitalCurrency" col="8"
								 value="${relOrg.regCapitalCurrency}" disabled="true">
						</bf:text>
					</bc:col>

				</bf:formGroup>--%>

				<bf:formGroup hr="dashed"  >
					<bc:col col="5">
						<bc:label text="预设额度币种" col="4" formControl="true"/>
						<bf:text id="presetLimitCurrency" name="presetLimitCurrency" col="8"
								 value="${relOrg.presetLimitCurrency}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="预设额度" col="4" formControl="true"/>
						<bf:text id="presetLimit" name="presetLimit" col="8" value="${relOrg.presetLimit}" disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>

				<bf:formGroup hr="dashed"  >
					<bc:col col="5">
						<bc:label text="可用额度" col="4" formControl="true"/>
						<bf:text id="availableLimit" name="availableLimit" col="8"
								 value="${relOrg.availableLimit}" disabled="true">
						</bf:text>
					</bc:col>

				</bf:formGroup>



				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-5">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/quota/list';
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
</body>
</html>