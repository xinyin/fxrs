<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>表单属性表</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="属性编码" col="4" formControl="true"/>
						<bc:label text="${formProp.propCode}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="属性名称" col="4" formControl="true"/>
						<bc:label text="${formProp.propName}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="是否是主键" col="4" formControl="true"/>
						<bc:label text="fixedDict:YesNo:${formProp.primaryKey}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="备注" col="2" formControl="true"/>
						<bc:label text="${formProp.remark}" col="10" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button  text="返回" icon="arrow-circle-left">
							<be:event>
								if('${type}' == 'design'){
									window.location = '${base}/workflow/formProp/designView?formCode=${formCode}';
								}
								else
								{
									window.location = '${base}/workflow/formProp/list?formCode=${formCode}';
								}
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
