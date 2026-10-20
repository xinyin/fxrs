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
	<bc:ibox id="relOrgSendBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="form1" name="form1" method="post" validation="true">
			<bf:formGroup>
				<bc:col col="12">
					<bc:label text="新增处理机构" col="2" formControl="true"/>
					<bf:unitLookup id="unitCode" name="unitCode"
								   col="8"
								   value="${unitCode}" text="${unitName}"
								   propText="unitName" propValue="unitCode"
								   callback="result"  onSelect="onSelect">
						<bv:notEmpty/>
					</bf:unitLookup>
				</bc:col>
			</bf:formGroup>
			<bf:formGroup>
				<bc:col col="12">
					<bc:label text="下发原因" col="2" formControl="true"/>
					<bf:text id="memo" name="memo" col="10" multiple="true"><bv:notEmpty/></bf:text>
				</bc:col>
			</bf:formGroup>
			<bf:formGroup>
				<div class="col-xs-8  col-xs-offset-4">
					<bc:button type="primary"  text="确认下发" icon="save">


						<be:event>
							$('#form1').data('formValidation').validate();
							var isValid = $('#form1').data('formValidation').isValid();
							if(isValid)
							{

							var url = '${base}/bus/traNoCredit/taskSend';
							var objData = {};

							for (var entry of $('#form1').serializeArray()){
							objData[entry['name']] = entry['value'];
							}
							objData.sendOrgId =  $.messager.args('traNoCreditSend').orgId;

							$.mask.post(url, objData, function(data){
							if(data.success)
							{
								$.messager.alert('提示', data.msg, 'info', function(){
								$.messager.hide('traNoCreditSend');
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
							$.messager.hide('traNoCreditSend');
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
</script>
</body>
</html>
