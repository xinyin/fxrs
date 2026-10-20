<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>单笔关联交易对手方</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="单笔关联交易对手方" subTitle="请输入单笔关联交易对手方查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="物理主键，自增" col="5" formControl="true"/>
						<bf:text name="__LIKES_pid" col="7" placeholder="物理主键，自增"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="singleRelatedPartyTable" idField="pid" url="${base}/bus/singleRelatedParty">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="物理主键，自增" width="100"/>
				<bc:column field="transId" title="交易id" width="100"/>
				<bc:column field="reportVersion" title="报送记录版本" width="100"/>
				<bc:column field="counterpartyType" title="关联方类别（1-机构，2-人）" width="100"/>
				<bc:column field="counterparty" title="关联方pid（当交易对手为机构时，对应法人信息表的pid；为个人时，对应人员信息表的pid）" width="100"/>
				<bc:column field="counterpartyName" title="交易对手名称" width="100"/>
				<bc:column field="identifyType" title="证件类型" width="100"/>
				<bc:column field="domesticOrForeign" title="境内外" width="100"/>
				<bc:column field="identifyNumber" title="证件号码" width="100"/>
				<bc:column field="uniscCode" title="统一社会信用代码" width="100"/>
				<bc:column field="transCountYear" title="交易本年度累计金额" width="100"/>
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