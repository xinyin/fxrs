<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>季报保险公司与单一法人主体资金运用关联关系统计表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="季报保险公司与单一法人主体资金运用关联关系统计表" subTitle="请输入季报保险公司与单一法人主体资金运用关联关系统计表查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="自增主键" col="5" formControl="true"/>
						<bf:text name="__LIKES_pid" col="7" placeholder="自增主键"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="quarterlyLegalTable" idField="pid" url="${base}/bus/quarterlyLegal">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="自增主键" width="100"/>
				<bc:column field="reportId" title="关联交易季报主键" width="100"/>
				<bc:column field="corpId" title="法人主键" width="100"/>
				<bc:column field="corpName" title="法人主体名称" width="100"/>
				<bc:column field="corpUniscCode" title="法人统一社会信用代码" width="100"/>
				<bc:column field="corpRegisteAddress" title="法人境内外" width="100"/>
				<bc:column field="corpInvestFund" title="法人投资金额" width="100"/>
				<bc:column field="corpTotalFund" title="法人主体总资产" width="100"/>
				<bc:column field="corpInvestYtotalPct" title="法人投资金额占保险公司上年末总资产百分比" width="100"/>
				<bc:column field="corpAssetsYtotalPct" title="法人主体总资产占保险公司上年末总资产百分比" width="100"/>
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