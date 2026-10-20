<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>报送文件</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="报送文件" subTitle="请输入报送文件查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
					<bc:row cls="m-b">
						<bf:formGroup col="3">
						<bc:label text="发起报送时间" col="5" formControl="true"/>
						<bf:text name="__LIKES_repTime" col="7" placeholder="发起报送时间"/>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="报送文件类型" col="5" formControl="true"/>
						<bf:text name="__LIKES_fileType" col="7" placeholder="报送文件类型"/>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="报送状态" col="5" formControl="true"/>
						<bf:text name="__LIKES_submissionStatus" col="7" placeholder="报送状态"/>
					</bf:formGroup>
					<bc:col col="" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="submissionFileTable" idField="pid" url="${base}/bus/submissionFile">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="add" text="下载文件" type="button"/>
				<bc:toolbar id="add" text="提交审核" type="button"/>
				<bc:toolbar id="add" text="报送" type="button"/>
				<bc:column field="pid" title="序号" width="100"/>
				<bc:column field="fileName" title="文件名称" width="100"/>
				<bc:column field="fileType" title="报送文件类型" width="100"/>
				<bc:column field="timeAttribute" title="时间属性             " width="100"/>
				<bc:column field="repBatchNo" title="报送批次号             " width="100"/>
				<bc:column field="repTime" title="发起报送时间             " width="100"/>
				<bc:column field="submissionStatus" title="报送状态" width="100"/>
				<bc:column field="repPerson" title="报送人             " width="100"/>
				<bc:column field="reviewer" title="复核人             " width="100"/>
				<bc:column field="repInforFeedback" title="报送信息反馈             " width="100"/>
				<%--<bc:column field="fileUrl" title="文件路径" width="100"/>
				<bc:column field="showStatus" title="显示状态" width="100"/>
				<bc:column field="approveStatus" title="操作状态" width="100"/>
				<bc:column field="corporateBody" title="法人机构" width="100"/>
				<bc:column field="repOrg" title="报送机构" width="100"/>
				<bc:column field="buildFileType" title="生成文件类型" width="100"/>--%>
				<bc:operator id="view" icon="search" title="查看"/>
			<%--	<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>--%>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>