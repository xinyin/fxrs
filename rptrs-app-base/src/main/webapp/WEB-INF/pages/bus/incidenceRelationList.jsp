<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>关联关系表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="关联关系表" subTitle="请输入关联关系表查询信息" col="12" collapsible="true">
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
			<bc:table id="incidenceRelationTable" idField="pid" url="${base}/bus/incidenceRelation">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:column field="pid" title="自增主键" width="100"/>
				<bc:column field="entityPid" title="关联主体pid" width="100"/>
				<bc:column field="entityRegCode" title="关联主体登记编码" width="100"/>
				<bc:column field="entityType" title="关联主体是类别（1-法人，2-自然人）" width="100"/>
				<bc:column field="entityName" title="关联主体名称" width="100"/>
				<bc:column field="superEntityPid" title="上层关联主体pid" width="100"/>
				<bc:column field="superEntityRegCode" title="上层关联主体登记编码" width="100"/>
				<bc:column field="superEntityType" title="上层关联主体是类别（1-法人，2-自然人）" width="100"/>
				<bc:column field="superEntityName" title="上层关联主体名称" width="100"/>
				<bc:column field="relationId" title="主体与上层主体关联关系id" width="100"/>
				<bc:column field="relationDesc" title="主体与上层主体关联关描述" width="100"/>
				<bc:column field="relationInfo" title="关联关系说明" width="100"/>
				<bc:column field="isShareHolding" title="是否持有或控制上一级关联方股权(0-否，1-是)" width="100"/>
				<bc:column field="holdingPercent" title="持股占比（默认为0）" width="100"/>
				<bc:column field="orignalRegcode" title="银行保险机构编码" width="100"/>
				<bc:column field="orignalOrgName" title="源主体（银行/保险机构）名称" width="100"/>
				<bc:column field="relationOrignal" title="关联主体与银行保险机构关联关系" width="100"/>
				<bc:column field="reportVersion" title="报送版本号" width="100"/>
				<bc:column field="parentLevelPid" title="上一层pid" width="100"/>
				<bc:column field="level" title="关联关系层级（1为根节点(银行/保险)，该表中只有以2开始的子节点）" width="100"/>
				<bc:column field="validStatus" title="是否有效 0-无效，1-有效" width="100"/>
				<bc:column field="isReported" title="是否被报送过（0-历史上从未报送过，1-已被报送过）" width="100"/>
				<bc:column field="activeTime" title="生效时间" width="100"/>
				<bc:column field="invalidTime" title="失效时间" width="100"/>
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