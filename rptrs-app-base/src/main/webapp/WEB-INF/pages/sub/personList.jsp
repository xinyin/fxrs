<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>Person</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="Person" subTitle="请输入Person查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="pid" col="5" formControl="true"/>
						<bf:text name="__LIKES_pid" col="7" placeholder="pid"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="personTable" idField="pid" url="${base}/sub/person">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="pid" width="100"/>
				<bc:column field="orignalOrg" title="源主体银行机构" width="100"/>
				<bc:column field="orignalOrgName" title="源主体银行机构名称" width="100"/>
				<bc:column field="name" title="姓名" width="100"/>
				<bc:column field="sex" title="性别" width="100"/>
				<bc:column field="nationality" title="国别/地区" width="100"/>
				<bc:column field="race" title="民族" width="100"/>
				<bc:column field="domesticOrForeign" title="境内(外)标志" width="100"/>
				<bc:column field="identityType" title="证件类型" width="100"/>
				<bc:column field="identityNo" title="证件号码" width="100"/>
				<bc:column field="birthdate" title="出生日期" width="100"/>
				<bc:column field="mobile" title="移动电话" width="100"/>
				<bc:column field="email" title="邮箱" width="100"/>
				<bc:column field="dutyCode" title="职位" width="100"/>
				<bc:column field="status" title="前置流程状态0-已抽取 1-校验不通过 2-待统计 3-待报送 4-已报送" width="100"/>
				<bc:column field="statusComment" title="前置流程状态说明" width="100"/>
				<bc:column field="maritalStatus" title="婚姻状况" width="100"/>
				<bc:column field="mgerOrg" title="机构名称" width="100"/>
				<bc:column field="regulator" title="口径" width="100"/>
				<bc:column field="isBlack" title="是否黑名单" width="100"/>
				<bc:column field="blackReason" title="加入黑名单原因" width="100"/>
				<bc:column field="isShareholder" title="是否为股东" width="100"/>
				<bc:column field="shareholdersCode" title="股东代码（账户号）" width="100"/>
				<bc:column field="openBank" title="交易账户开户行" width="100"/>
				<bc:column field="tradeAccount" title="交易账户账号" width="100"/>
				<bc:column field="inTime" title="入股时间" width="100"/>
				<bc:column field="inPrice" title="入股价格" width="100"/>
				<bc:column field="holeShareAmount" title="持股数量（股）" width="100"/>
				<bc:column field="shareholdingProportion" title="持股占比" width="100"/>
				<bc:column field="frozenShareAmount" title="总冻结股数（股）" width="100"/>
				<bc:column field="pledgedShareAmount" title="总质押股数（股）" width="100"/>
				<bc:column field="relType" title="关联方类型" width="100"/>
				<bc:column field="politicsStatus" title="政治面貌" width="100"/>
				<bc:column field="educationBackground" title="学历" width="100"/>
				<bc:column field="residentialAddress" title="居住地址" width="100"/>
				<bc:column field="workUnits" title="工作单位" width="100"/>
				<bc:column field="unitAddress" title="单位地址" width="100"/>
				<bc:column field="dataSource" title="数据来源" width="100"/>
				<bc:column field="isMainGd" title="是否本行主要股东" width="100"/>
				<bc:column field="capitalNumber" title="股权证编号" width="100"/>
				<bc:column field="isNoShareVote" title="是否具有非持股表决权" width="100"/>
				<bc:column field="voteProportion" title="表决权占比" width="100"/>
				<bc:column field="limitVoteSituation" title="限制表决权情况" width="100"/>
				<bc:column field="tradeBalance" title="交易余额" width="100"/>
				<bc:operator id="view" icon="search" title="查看"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>