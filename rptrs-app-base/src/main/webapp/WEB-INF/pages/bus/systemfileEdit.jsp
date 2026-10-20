<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>制度文件</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="systemfileBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${systemfile.pid}">
							<%--<bv:jepCode/>--%>
							<%--<bv:notEmpty/>--%>
							<%--<bv:remote url="${base}/bus/systemfile/check" name="pid" message="该编码已被占用"/>--%>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="制度类型" col="4" formControl="true"/>
						<bf:text id="systemType" name="systemType" col="8" value="${systemfile.systemType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="文件路径" col="4" formControl="true"/>
						<bf:text id="fileUrl" name="fileUrl" col="8" value="${systemfile.fileUrl}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="文件名称" col="4" formControl="true"/>
						<bf:text id="fileName" name="fileName" col="8" value="${systemfile.fileName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="文件类型" col="4" formControl="true"/>
						<bf:text id="fileType" name="fileType" col="8" value="${systemfile.fileType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="版本号" col="4" formControl="true"/>
						<bf:text id="fileVersion" name="fileVersion" col="8" value="${systemfile.fileVersion}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="显示状态" col="4" formControl="true"/>
						<bf:text id="showStatus" name="showStatus" col="8" value="${systemfile.showStatus}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="审批状态" col="4" formControl="true"/>
						<bf:text id="approveStatus" name="approveStatus" col="8" value="${systemfile.approveStatus}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/systemfile/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/systemfile/list';
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
								window.location = '${base}/bus/systemfile/list';
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
	function showit (){

	}
});
</script>
</body>
</html>
