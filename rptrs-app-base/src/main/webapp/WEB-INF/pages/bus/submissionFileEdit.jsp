<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>报送文件</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="submissionFileBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${submissionFile.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/submissionFile/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="报送文件类型" col="4" formControl="true"/>
						<bf:text id="fileType" name="fileType" col="8" value="${submissionFile.fileType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="文件路径" col="4" formControl="true"/>
						<bf:text id="fileUrl" name="fileUrl" col="8" value="${submissionFile.fileUrl}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="文件名称" col="4" formControl="true"/>
						<bf:text id="fileName" name="fileName" col="8" value="${submissionFile.fileName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="显示状态" col="4" formControl="true"/>
						<bf:text id="showStatus" name="showStatus" col="8" value="${submissionFile.showStatus}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="审批状态" col="4" formControl="true"/>
						<bf:text id="approveStatus" name="approveStatus" col="8" value="${submissionFile.approveStatus}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="报送状态" col="4" formControl="true"/>
						<bf:text id="submissionStatus" name="submissionStatus" col="8" value="${submissionFile.submissionStatus}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="时间属性             " col="4" formControl="true"/>
						<bf:text id="timeAttribute" name="timeAttribute" col="8" value="${submissionFile.timeAttribute}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="报送批次号             " col="4" formControl="true"/>
						<bf:text id="repBatchNo" name="repBatchNo" col="8" value="${submissionFile.repBatchNo}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="发起报送时间             " col="4" formControl="true"/>
						<bf:datebox id="repTime" name="repTime" col="8"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="报送人             " col="4" formControl="true"/>
						<bf:text id="repPerson" name="repPerson" col="8" value="${submissionFile.repPerson}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="复核人             " col="4" formControl="true"/>
						<bf:text id="reviewer" name="reviewer" col="8" value="${submissionFile.reviewer}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="报送信息反馈             " col="4" formControl="true"/>
						<bf:text id="repInforFeedback" name="repInforFeedback" col="8" value="${submissionFile.repInforFeedback}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="法人机构" col="4" formControl="true"/>
						<bf:text id="corporateBody" name="corporateBody" col="8" value="${submissionFile.corporateBody}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="报送机构" col="4" formControl="true"/>
						<bf:text id="repOrg" name="repOrg" col="8" value="${submissionFile.repOrg}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="生成文件类型(1、关联方信息2、重大关联交易3、季报4、年报5、月报6、G157、行内制度)" col="4" formControl="true"/>
						<bf:text id="buildFileType" name="buildFileType" col="8" value="${submissionFile.buildFileType}">
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
									$.mask.post('${base}/bus/submissionFile/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/submissionFile/list';
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
								window.location = '${base}/bus/submissionFile/list';
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
