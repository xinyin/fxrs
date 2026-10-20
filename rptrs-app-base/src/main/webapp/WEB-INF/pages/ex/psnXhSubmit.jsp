<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="form"/>
	<title>自然人新增或编辑</title>
	<x:jsLib/>

	<style>
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
			<bf:hidden id="pid" name="pid" value="${relPsn.pid}"/>
			<bf:hidden id="status" name="status" value="${relPsn.status}"/>
			<bf:hidden id="relSerial" name="relSerial" value="${relPsn.relSerial}"/>
			<div class="boxse">
				<bf:formGroup type="success"   hrp="bottom">
					<bc:col col="5">
						<bc:label text="姓名" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${relPsn.name}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="性別" col="4" formControl="true"/>
<%--						<bf:select id="sex" name="sex" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Sex" value="${relPsn.sex}">--%>
<%--						</bf:select>--%>
						<bf:text id="sex" name="sex" col="8" value="${relPsn.sex}">
						</bf:text>
					</bc:col>

					<bc:col col="5">
						<bc:label text="证件类型" col="4" formControl="true"/>
<%--						<bf:select id="identifyType" name="identifyType" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsn.identifyType}">--%>
<%--						</bf:select>--%>
						<bf:text id="identifyType" name="identifyType" col="8" value="${relPsn.identifyType}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identifyNumber" name="identifyNumber" col="8" value="${relPsn.identifyNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="政治面貌" col="4" formControl="true"/>
<%--						<bf:select id="politicsStatus" name="politicsStatus" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS_politically" value="${relPsn.politicsStatus}">--%>
<%--						</bf:select>--%>
						<bf:text id="politicsStatus" name="politicsStatus" col="8" value="${relPsn.politicsStatus}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="学历" col="4" formControl="true"/>
<%--						<bf:select id="educationBackground" name="educationBackground" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Education" value="${relPsn.educationBackground}">--%>
<%--						</bf:select>--%>
						<bf:text id="educationBackground" name="educationBackground" col="8" value="${relPsn.educationBackground}">
						</bf:text>
					</bc:col>

					<bc:col col="5">
						<bc:label text="民族" col="4" formControl="true"/>
<%--						<bf:select id="race" name="race" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Race" value="${relPsn.race}">--%>
<%--						</bf:select>--%>
						<bf:text id="race" name="race" col="8" value="${relPsn.race}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="境内外标识" col="4" formControl="true"/>
<%--						<bf:select id="domesticOrForeign" name="domesticOrForeign"  col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" value="${relPsn.domesticOrForeign}">--%>
<%--						</bf:select>--%>
						<bf:text id="domesticOrForeign" name="domesticOrForeign" col="8" value="${relPsn.domesticOrForeign}">
						</bf:text>
					</bc:col>

					<bc:col col="5">
						<bc:label text="出生日期" col="4" formControl="true"/>
						<bf:datebox name="birthdate" id="birthdate" col="8" format="date" value="${relPsn.birthdate}">
						</bf:datebox>
					</bc:col>
					<bc:col col="5">
						<bc:label text="婚姻状况" col="4" formControl="true"/>
<%--						<bf:select id="maritalStatus" name="maritalStatus" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsn.maritalStatus}">--%>
<%--						</bf:select>--%>
						<bf:text id="maritalStatus" name="maritalStatus" col="8" value="${relPsn.maritalStatus}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="移动电话" col="4" formControl="true"/>
						<bf:text id="mobile" name="mobile" col="8" value="${relPsn.mobile}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="邮箱" col="4" formControl="true"/>
						<bf:text id="email" name="email" col="8" value="${relPsn.email}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="机构名称" col="4" formControl="true"/>
<%--						<bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom"--%>
<%--								   url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsn.mgerOrg}">--%>
<%--						</bf:select>--%>
						<bf:text id="mgerOrg" name="mgerOrg" col="8" value="${relPsn.mgerOrg}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="居住地址" col="4" formControl="true"/>
						<bf:text id="residentialAddress" name="residentialAddress" col="8" value="${relPsn.residentialAddress}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="工作单位" col="4" formControl="true"/>
						<bf:text id="workUnits" name="workUnits" col="8" value="${relPsn.workUnits}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="单位地址" col="4" formControl="true"/>
						<bf:text id="unitAddress" name="unitAddress" col="8" value="${relPsn.unitAddress}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="是否为股东" col="4" formControl="true"/>
						<bf:checkGroup name="isShareholder" col="8" value="${relPsn.isShareholder}">
							<bf:radio value="1" label="是" checked="true"/>
							<bf:radio value="0" label="否"/>
						</bf:checkGroup>
					</bc:col>
					<bc:col col="5">
						<bc:label text="股东代码（账户号）" col="4" formControl="true"/>
						<bf:text id="shareholdersCode" name="shareholdersCode" col="8" value="${relPsn.shareholdersCode}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="交易账户开户行" col="4" formControl="true"/>
						<bf:text id="openBank" name="openBank" col="8" value="${relPsn.openBank}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="交易账户账号" col="4" formControl="true"/>
						<bf:text id="tradeAccount" name="tradeAccount" col="8" value="${relPsn.tradeAccount}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="入股时间" col="4" formControl="true"/>
						<bf:text id="inTime" name="inTime" col="8" value="${relPsn.inTime}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="入股价格" col="4" formControl="true"/>
						<bf:text id="inPrice" name="inPrice" col="8" value="${relPsn.inPrice}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="持股数量（股）" col="4" formControl="true"/>
						<bf:text id="holeShareAmount" name="holeShareAmount" col="8" value="${relPsn.holeShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="持股占比" col="4" formControl="true"/>
						<bf:text id="shareholdingProportion" name="shareholdingProportion" col="8" value="${relPsn.shareholdingProportion}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="总冻结股数（股）" col="4" formControl="true"/>
						<bf:text id="frozenShareAmount" name="frozenShareAmount" col="8" value="${relPsn.frozenShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="总质押股数（股）" col="4" formControl="true"/>
						<bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="8" value="${relPsn.pledgedShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="销户原因" col="4" formControl="true"/>
						<bf:text id="reason" name="reason" col="8" multiple="true" value="${reason}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
			</div>
		</bf:form>
	</bc:ibox>
</div>
<br:js pack="form"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">

</script>
</body>
</html>
