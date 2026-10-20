<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>业务模块管理</title>
<x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox id="iboxbizModule" title="业务模块管理" col="12" collapsible="false">
			<bc:col col="3">
				<bc:tree id="bizModuleTree" url="${base}/workflow/bizModule" edit="true"
					formId="bizModuleForm" autoParam="id=parentId" lazy="false"
					formUneditableField="code" formNameField="bizModuleName">
				</bc:tree>
			</bc:col>
			<bc:col col="9">
				<bf:form id="bizModuleForm" method="post" validation="true">
					<bf:formGroup>
						<bc:col col="6">
							<bc:label text="父编码" col="4" formControl="true"/>
							<bf:text name="parentId" col="6" disabled="true"/>
						</bc:col>
						<bc:col col="6">
							<bc:label text="父名称" col="2" formControl="true"/>
							<bf:text name="parentName" col="6" disabled="true"/>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup>
						<bc:col col="6">
							<bc:label text="编码" col="4" formControl="true"/>
							<bf:text id="code" name="code" col="6">
								<bv:notEmpty/>
								<bv:regexp regexp="^[a-zA-Z]{1}[a-zA-Z\d_]*$" message="请输入正确的编码"/>
								<bv:remote url="${base}/workflow/bizModule/check" name="code" message="该编码已被占用"/>
							</bf:text>
						</bc:col>
						<bc:col col="6">
							<bc:label text="名称" col="2" formControl="true"/>
							<bf:text id="name" name="name" col="6">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup>
						<bc:label text="备注" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="8" multiple="true"/>
					</bf:formGroup>
					<bf:formGroup>
						<div align="center">
							<bc:button id="btnSave" type="primary"  text="保存" icon="save"/>
						</div>
					</bf:formGroup>
				</bf:form>
			</bc:col>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
$(document).ready(function(){
	$('#bizModuleTree').jTree('expandAll', true);
});
</script>
</body>
</html>