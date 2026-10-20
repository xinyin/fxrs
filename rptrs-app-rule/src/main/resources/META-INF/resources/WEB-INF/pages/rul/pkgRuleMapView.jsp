<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>规则集和规则映射</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="pkgRuleMapBox" title="规则集和规则映射" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="主键" col="2" formControl="true"/>
					<bc:label text="${pkgRuleMap.id}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="主键" col="2" formControl="true"/>
					<bc:label text="${pkgRuleMap.ruleId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="规则集ID" col="2" formControl="true"/>
					<bc:label text="${pkgRuleMap.pkgId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="规则ID" col="2" formControl="true"/>
					<bc:label text="${pkgRuleMap.ruleId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/rul/pkgRuleMap/list';
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