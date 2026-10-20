<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>流程终止</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="procInstTerminateBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form1" method="post" validation="true">
				<bf:hidden name="procInstId" id="procInstId" value="${procInstId}"/>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="终止原因" col="2" formControl="true"/>
						<bf:text id="memo" name="memo" col="10" multiple="true"><bv:notEmpty/></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-xs-8  col-xs-offset-4">
						<bc:button type="primary"  text="保存" icon="save">
							<be:event>
								$('#form1').data('formValidation').validate();
								var isValid = $('#form1').data('formValidation').isValid();
								if(isValid)
								{
									var data = $('#form1').serializeAll();
									$.mask.post('${base}/workflow/approve/terminate', data, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												$.messager.hide('winProcInstTerminateEdit');
											});
										}
										else
										{
											$.messager.alert('错误', data.msg, 'error');
										}
									});
								}
							</be:event>
						</bc:button>
						<bc:button  text="关闭" icon="times">
							<be:event>
								$.messager.hide('winProcInstTerminateEdit');
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
<script type="text/javascript">

$(document).ready(function(){
	var args = $.messager.args('winProcInstTerminateEdit');
	$('#procInstId').val(args.procInstId);
})


</script>

</body>
</html>
