<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>机构关联解除名单</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">

				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="关联方名称" col="5" formControl="true"/>
						<bf:text name="" col="7" placeholder="关联方名称"/>
					</bf:formGroup>

					<bf:formGroup col="4" >
						<bc:label text="社会统一信用代码" col="5" formControl="true"/>
						<bf:text name="" col="7" placeholder="社会统一信用代码"/>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<bf:formGroup col="4" type="success">
						<bc:label text="解除日期" col="5" formControl="true"></bc:label>
						<bf:datebox name="beginDate" id="beginDate" col="7" format="date">
						</bf:datebox>
					</bf:formGroup>
					<bf:formGroup col="4" type="success">
						<bc:label text="" col="5" formControl="true"></bc:label>
						<bf:datebox id="endDate" name="endDate" col="7" format="date">
						</bf:datebox>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<bf:formGroup>
						<bc:col col="12" colOffset="5">
							<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
							<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
						</bc:col>
					</bf:formGroup>

				</bc:row>
			</bf:form>
			<bc:table id="legalPersonRemoveTable" idField="pid" url="${base}/bus/legalPersonRemove">
				<bc:column field="pid" title="自增主键" width="100" visible="false"/>
				<bc:column field="regCode" title="登记编码" width="100" visible="false"/>
				<bc:column field="orignalOrg" title="源主体银行" width="100" visible="false"/>
				<bc:column field="orignalOrgName" title="源主体银行名称" width="100" visible="false"/>
				<bc:column field="orgName" title="企业名称" width="100"/>
				<bc:column field="orgType" title="企业类型" width="100" visible="false"/>
				<bc:column field="domesticOrForeign" title="境内/境外(1-境内，2-境外)" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
				<bc:column field="uniscCode" title="社会统一信用代码" width="100"/>
				<bc:column field="regCapital" title="注册资本(万元/人民币)" width="100" visible="false"/>
				<bc:column field="regCapitalCurrency" title="注册资本币种代码" width="100" visible="false"/>
				<bc:column field="businessScope" title="营业范围" width="100" visible="false"/>
				<bc:column field="status" title="机构状态（1-正常，2-停业）" width="100" visible="false" formatter="custom:sys/dictItem/getDictItems/BUS-Status"/>
				<bc:column field="legalPerson" title="法定代表人" width="100" visible="false"/>
				<bc:column field="regAddress" title="注册地" width="100" visible="false"/>
				<bc:column field="reportVersion" title="报送版本号" width="100" visible="false"/>
				<bc:column field="validStatus" title="是否有效 0-无效，1-有效" width="100" visible="false" formatter="custom:sys/dictItem/getDictItems/BUS-ValidStatus"/>
				<bc:column field="isReported" title="是否被报送过（0-历史上从未报送过，1-已被报送过）" width="100" visible="false"/>
				<bc:column field="identifyType" title="证件类型" width="100" visible="false"/>
				<bc:column field="identifyNumber" title="证件代码" width="100" visible="false"/>
				<bc:column field="removeDate" title="关联解除时间" width="100"/>
				<bc:column field="removeCause" title="关联解除原因" width="100"/>
				<bc:column field="commitName" title="提交人" width="100"/>
				<bc:column field="commitDutyCode" title="提交人职位" width="100" visible="false"/>
				<bc:column field="checkName" title="复核人" width="100"/>
				<bc:column field="checkDutyCode" title="复核人职位" width="100" visible="false"/>
				<bc:column field="activeTime" title="生效时间" width="100" visible="false"/>
				<bc:column field="invalidTime" title="失效时间" width="100" visible="false"/>
				<bc:column field="bodyType" title="机构类型" width="100" visible="false"/>
				<bc:operator id="view" icon="search" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>