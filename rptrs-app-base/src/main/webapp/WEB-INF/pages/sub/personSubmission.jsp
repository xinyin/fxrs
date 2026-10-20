<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<x:jsLib/>
	<br:css pack="form"/>

	<title>报送日期</title>
	<x:jsLib/>

	<style>

	</style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
	<bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="fromPid" name="fromPid"/>
			<bf:hidden id="toPid" name="toPid"/>
			<bf:hidden id="pid" name="pid" value="${sublog.pid}"/>
			<div class="boxse">
				<bf:formGroup type="success">
					<bc:col col="8">
						<bc:label cls="required" text="报送日期" col="5" formControl="true"/>
						<bf:datebox name="submissionDate" id="submissionDate" col="7" format="date" value="${sublog.submissionDate}">
							<bv:notEmpty/>
						</bf:datebox>
					</bc:col>
				</bf:formGroup>
			</div>
			<bf:formGroup>
				<div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
					<bc:button type="danger" text="确定" icon="save">
						<be:event>
							var docInnerPids = getAllIdSelections();
							$('#editForm').data('formValidation').validate();
							var isValid = $('#editForm').data('formValidation').isValid();
							if(isValid)
							{
							var formData = $('#editForm').serializeAll();
							var docTableSelectItem = {
							'name':'docTableSelectItem',
							'value':docInnerPids
							};
							formData.push(docTableSelectItem);
							$.mask.post('${base}/sub/person/savePersonSubmission/' + pid, formData, function(data){
							if(data.success)
							{
							$.messager.confirm('提示', data.msg, function(r){
							if(r)
							{
							window.location = '${base}/sub/person/sublist';
							}
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
							window.location = '${base}/sub/person/sublist';
						</be:event>
					</bc:button>
				</div>
			</bf:formGroup>
		</bf:form>
	</bc:ibox>
</div>
</div>
<div>
	<br:js pack="form"/>
	<be:processEvent/>
	<script type="text/javascript">

		$(document).ready(function () {
			$("#submissionDate").datetimepicker("setStartDate",new Date());
			$(".required").each(function () {
				var $this = $(this);
				$(this).html("<font color='red'>✶</font>" + $this.html());
			});
		});


		function getAllIdSelections() {
			return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
				return row.pid//返回数据行中的id值
			});
		}

	</script>

</body>
</html>