<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>表单事件表</title>
<script type="text/javascript">
	$(window).load(function() {
		jQuery.ajaxSetup({
			cache : false
		});
	});
</script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="true">
				<bf:formGroup>
					<bc:label text="事件类型" col="2" formControl="true"/>
					<bc:label text="${formEvent.type}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="事件名称" col="2" formControl="true"/>
					<bc:label text="${formEvent.name}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="属性编码" col="2" formControl="true"/>
					<bc:label text="${formEvent.propCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="脚本" col="2" formControl="true"/>
					<bc:label text="${formEvent.script}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="备注" col="2" formControl="true"/>
					<bc:label text="${formEvent.remark}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button  text="返回" icon="">
							<be:event>
								if('${type }' == 'view') window.location = '${base}/workflow/formProp/list?formCode=${formEvent.formCode}';
								else window.location = '${base}/workflow/formProp/designView?formCode=${formEvent.formCode}';
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