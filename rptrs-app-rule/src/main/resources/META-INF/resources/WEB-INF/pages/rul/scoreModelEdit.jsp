<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>评分模型</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="scoreModelBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="id" name="id" value="${scoreModel.id}"/>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="评分编码" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="8" value="${scoreModel.code}" disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/rul/scoreModel/check" name="code" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="评分名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${scoreModel.name}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="规则集" col="4" formControl="true"/>
						<bf:select id="rulPkgId" name="rulPkgId" col="8"  dataSource="custom" url="rul/packages/queryList" value="${scoreModel.rulPkgId}">
							<bv:notEmpty message="规则集不能为空"/>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="评分算法" col="4" formControl="true"/>
						<bf:select id="algorithm" name="algorithm" col="8" value="${scoreModel.algorithm}" dataSource="fixedDict" url="ScoreAlgorithm">
							<be:event type="select">algorithmClick();</be:event>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="functionIdDiv">
					<bc:col col="6">
						<bc:label text="自定义算法" col="4" formControl="true"/>
						<bf:select id="functionId" name="functionId" col="8" dataSource="custom" url="rul/functions/queryList" value="${scoreModel.functionId}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="备注" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="10" value="${scoreModel.remark}" multiple="true"></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/rul/scoreModel/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/rul/scoreModel/list';
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
						<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/rul/scoreModel/list';
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
var algorithm = '${scoreModel.algorithm}';
$(document).ready(function(){
	
	//判断当前模式是否为自定义模式
	if(opt == 'edit')
	{
		if(algorithm == 'CUSTOM') $('#functionIdDiv').show();
		else $('#functionIdDiv').hide();
	}
	else
	{
		$('#functionIdDiv').hide();
	}
});

//判断当前选择类型
function algorithmClick(){
	var val = $('#algorithm').val();
	if(val == 'CUSTOM') $('#functionIdDiv').show();
	else $('#functionIdDiv').hide();
}
</script>
</body>
</html>
