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
			<bf:hidden name="opt" id="opt"></bf:hidden>
			<bf:hidden name="id" id="id" value="${formProp.id}"></bf:hidden>
			<bf:hidden name="propOrder" id="propOrder" value="${formProp.propOrder}"></bf:hidden>
			<bf:hidden name="formCode" id="formCode" value="${formCode }"></bf:hidden>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="属性编码" col="4" formControl="true"/>
						<bf:text id="propCode" name="propCode" col="8" value="${formProp.propCode}" disabled="${opt == 'edit'}">
							<bv:remote url="${base}/workflow/formProp/check?formCode=${formCode}" name="propCode" message="属性编码重复"/>
							<bv:notEmpty/>
							<bv:regexp regexp="^[a-zA-Z][a-zA-Z0-9_]*$" message="只能以英文字母开头，只能包含英文字母、数字、下划线"/>
							<bv:stringLength max="64" message="属性编码不能大于64"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="属性名称" col="4" formControl="true"/>
						<bf:text id="propName" name="propName" col="8" value="${formProp.propName}">
						<bv:notEmpty/>
						<bv:stringLength max="64" message="属性名称不能大于64"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="是否是主键" col="4" formControl="true"/>
						<bf:select id="primaryKey" name="primaryKey" col="8" value="${formProp.primaryKey}" dataSource="fixedDict" url="YesNo">
						<bv:notEmpty message="是否是主键不能为空"/>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="备注" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="8"  multiple="true">${formProp.remark}</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary"  text="保存" icon="save" click="btnSave();">
						</bc:button>
						<bc:button  text="返回">
							<be:event>
								window.location = '${base}/workflow/formProp/list?formCode=${formCode}';
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
var opt = '${opt}';
var optEdit = true;

$(document).ready(function(){
	//禁用表单提交事件 //禁用表单提交事件 btnSave
	$("#form1").attr("onsubmit", "return false");
	$('#primaryKey').val('N').trigger('change');
});

//保存
function btnSave(){
	//TODO 行编辑 和 属性验证有冲突，  只要页面使用行编辑， 手动输入后 ， 或触发action 的事件 参考 122 行禁用onsubmit事件
	$('#form1').data('formValidation').validate();
	var isValid = $('#form1').data('formValidation').isValid();
	if(isValid)
	{
		
		var dataAll = $('#form1').serializeAll();
		$.mask.post('${base}/workflow/formProp/save', dataAll, function(data){
			if(data.success)
			{
				$.messager.alert('提示', '保存成功。', 'info', function(){
					window.location = '${base}/workflow/formProp/list?formCode=${formCode}';
				});
			}
			else
			{
				$.messager.alert('错误', data.msg, 'error');
			}
		});
	}
}
</script>
</body>
</html>
