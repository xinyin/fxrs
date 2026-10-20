<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>Sublog</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="sublogBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="pid" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${sublog.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/sub/sublog/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="batchDecimal" col="4" formControl="true"/>
						<bf:text id="batchDecimal" name="batchDecimal" col="8" value="${sublog.batchDecimal}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="fileName" col="4" formControl="true"/>
						<bf:text id="fileName" name="fileName" col="8" value="${sublog.fileName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="subType" col="4" formControl="true"/>
						<bf:text id="subType" name="subType" col="8" value="${sublog.subType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="subYear" col="4" formControl="true"/>
						<bf:text id="subYear" name="subYear" col="8" value="${sublog.subYear}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="subQuarter" col="4" formControl="true"/>
						<bf:text id="subQuarter" name="subQuarter" col="8" value="${sublog.subQuarter}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="maxPercentInNetAssets" col="4" formControl="true"/>
						<bf:datebox id="maxPercentInNetAssets" name="maxPercentInNetAssets" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="downAmt" col="4" formControl="true"/>
						<bf:text id="downAmt" name="downAmt" col="8" value="${sublog.downAmt}">
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
									$.mask.post('${base}/sub/sublog/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/sub/sublog/list';
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
$(document).ready(function(){
	
});
</script>
</body>
</html>
