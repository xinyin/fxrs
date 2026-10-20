<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>Dod</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="行外制度" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
                <bf:hidden name="systemType" id="systemType" value="I"></bf:hidden>
                <bc:row cls="m-b">
					<bf:formGroup col="3">
						<bc:label text="制度名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_sysName" col="6" placeholder="制度名称"/>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="文号" col="5" formControl="true"/>
						<bf:text name="__LIKES_articleNumber" col="6" placeholder="文号"/>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="颁布单位" col="5" formControl="true"/>
                        <bf:select name="__LIKES_dispatchDep" col="6" dataSource="custom"  url="sys/dictItem/getDictItems/BUS_DispatchDep">
                            <bf:option text="ALL" value="%"></bf:option>
                        </bf:select>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="时效性" col="5" formControl="true"/>
                        <bf:select name="__LIKES_isVal" col="6" dataSource="custom"  url="sys/dictItem/getDictItems/BUS_IsValid">
                            <bf:option text="ALL" value="%"></bf:option>
                        </bf:select>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<bf:formGroup col="3" type="success">
						<bc:label text="生效时间" col="3" formControl="true"></bc:label>
						<bf:datebox name="__LIKES_beginDate" id="__LIKES_endDate" col="8" format="date">
						</bf:datebox>
					</bf:formGroup>
					<bf:formGroup col="3" type="success">
						<bc:label text="失效时间" col="3" formControl="true"></bc:label>
						<bf:datebox id="__LIKES_endDate" name="__LIKES_endDate" col="8" format="date">
						</bf:datebox>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="docTable" idField="pid" url="${base}/bus/docForOutside">
				<bc:toolbar id="uploadFile" text="上传附件" type="button"/>
				<bc:column field="sysName" title="文件名" width="100"/>
				<bc:column field="articleNumber" title="文件类型" width="100"/>
				<bc:column field="dispatchDep" title="文件大小" width="100"/>
				<bc:column field="isVal" title="上传机构" width="100"/>
				<bc:column field="effectiveTime" title="上传部门" width="100"/>
				<bc:column field="expiryTime" title="上传时间" width="100"/>
				<bc:column field="expiryTime" title="操作" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>