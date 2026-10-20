<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>关联交易季报-关联交易明细表-交易对手情况</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="关联交易季报-关联交易明细表-交易对手情况" subTitle="请输入关联交易季报-关联交易明细表-交易对手情况查询信息" col="12" collapsible="true">
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
			<bc:table id="quarterlyDetailOppositeTable" idField="pid" url="${base}/bus/quarterlyDetailOpposite">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="自增主键" width="100"/>
				<bc:column field="transNum" title="交易编码（与季报关联交易明细表关联）" width="100"/>
				<bc:column field="relatedType" title="关联方类型" width="100"/>
				<bc:column field="relatedId" title="关联方主键（与关联方档案关联）" width="100"/>
				<bc:column field="relatedName" title="关联方名称" width="100"/>
				<bc:column field="relatedCredentialsType" title="关联方证件类型编码" width="100"/>
				<bc:column field="relatedCredentialsTypeName" title="关联方证件类型名称" width="100"/>
				<bc:column field="relatedCredentialsNum" title="关联方证件号码" width="100"/>
				<bc:column field="relatedUniscCode" title="统一社会信用代码" width="100"/>
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