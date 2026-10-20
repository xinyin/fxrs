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
			<div>
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
						id="hiddenBtn"
						style="display: none">
					文件上传按钮
				</button>
				<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
					 aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<div class="boxse">
									<div class="form-group has-success has-feedback">
										<div class="col-xs-12">
											<label class="control-label col-xs-4">
												<font color="red">*</font>
												报送日期
											</label>
											<div class="input-group date col-xs-4" id="submissionDate">
												<input type="text" class="form-control " name="submissionDate" data-fv-notempty="true"  data-fv-field="submissionDate">
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-body">
								<div style="text-align: center" id="loading"></div>
								<form method="post" enctype="multipart/form-data" id="uploadForm"
									  style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
									<div class="form-group" style="text-align: center;vertical-align: middle;">
										<label for="file"></label>
										<img src="${base}/static/img/sendfile.png"/>
										<a href="javascript:;" class="file">
											<input type="file" class="form-control" id="file" name="file" multiple
												   style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
										</a>
									</div>

								</form>
								<div class="form-group"
									 style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
									<button type="button" class=" btn btn-danger"><i class="fa fa-save"></i> 确定</button>
									<button type="button" class=" btn btn-primary"><i class="fa fa-arrow-circle-left"></i> 返回</button>
									<button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
									<button type="submit" class="btn btn-primary" id="upload">上传</button>
								</div>
							</div>
						</div>
					</div>
				</div>
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
							$.mask.post('${base}/sub/sublog/saveSubmission/' + pid, formData, function(data){
							if(data.success)
							{
							$.messager.confirm('提示', data.msg, function(r){
							if(r)
							{
							window.location = '${base}/sub/sublog/list';
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
							window.location = '${base}/sub/sublog/list';
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

		$(document).ready(function () {
			$("#submissionDate").datetimepicker("setStartDate",new Date());
			$(".required").each(function () {
				var $this = $(this);
				$(this).html("<font color='red'>✶</font>" + $this.html());
			});
			$("#hiddenBtn").click();
		});


		function getAllIdSelections() {
			return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
				return row.pid//返回数据行中的id值
			});
		}

	</script>

</body>
</html>