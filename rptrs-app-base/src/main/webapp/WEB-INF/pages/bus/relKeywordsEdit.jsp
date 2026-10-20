<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>疑似关联方规则关键字</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="relKeywordsBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<%--<bc:col col="6">--%>
						<%--<bc:label text="自增长主键" col="4" formControl="true"/>--%>
						<%--<bf:text id="pid" name="pid" col="8" value="${relKeywords.pid}"  disabled="${opt == 'edit'}">--%>
							<%--<bv:jepCode/>--%>
							<%--<bv:notEmpty/>--%>
							<%--<bv:remote url="${base}/bus/relKeywords/check" name="pid" message="该编码已被占用"/>--%>
						<%--</bf:text>--%>
					<%--</bc:col>--%>
					<bc:col col="6">
						<bc:label text="编号" col="4" formControl="true"/>
						<bf:text id="keyNo" name="keyNo" col="8" value="${relKeywords.keyNo}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="企业名称中关键字" col="4" formControl="true"/>
						<bf:text id="keyOrgName" name="keyOrgName" col="8" value="${relKeywords.keyOrgName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>

				<%--</bf:formGroup>--%>
				<bf:formGroup>
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/relKeywords/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/relRule/list';
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
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/relRule/list';
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
	
});
</script>
</body>
</html>
