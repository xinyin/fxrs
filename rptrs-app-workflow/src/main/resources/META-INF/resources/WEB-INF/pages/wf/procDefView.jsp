<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>业务流程信息查看</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="procDefBox" title="业务流程信息" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup >
					<bc:col col="4">
						<bc:label text="流程名称" col="4" formControl="true"/>
						<bc:label text="${procDef.name}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="4">
						<bc:label text="模板名称" col="4" formControl="true"/>
						<bc:label text="${procDef.moduleName}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="4">
						<bc:label text="模板名称" col="4" formControl="true"/>
						<bc:label text="${procDef.procTemplateName}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="4">
						<bc:label text="版本号" col="4" formControl="true"/>
						<bc:label text="${procDef.procVersion}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="4">
						<bc:label text="版本状态" col="4" formControl="true"/>
						<bc:label text="${procDef.procVersionStatus}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="4">
						<bc:label text="状态" col="4" formControl="true"/>
						<bc:label text="${procDef.status}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="4">
						<bc:label text="备注" col="4" formControl="true"/>
						<bc:label text="${procDef.remark}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				
				<bf:formGroup>
					<div align="center">
						<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/workflow/procDef/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
				</bf:form>
			</bc:ibox>
		</div>
	</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>