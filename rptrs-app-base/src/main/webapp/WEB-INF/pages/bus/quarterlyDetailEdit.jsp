<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联交易季报-关联交易明细表</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="quarterlyDetailBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${quarterlyDetail.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/quarterlyDetail/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联交易季报主键" col="4" formControl="true"/>
						<bf:text id="reportId" name="reportId" col="8" value="${quarterlyDetail.reportId}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易编码" col="4" formControl="true"/>
						<bf:text id="transNum" name="transNum" col="8" value="${quarterlyDetail.transNum}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易时间" col="4" formControl="true"/>
						<bf:datebox id="transTime" name="transTime" col="8"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易类型" col="4" formControl="true"/>
						<bf:text id="transType" name="transType" col="8" value="${quarterlyDetail.transType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="交易金额" col="4" formControl="true"/>
						<bf:text id="transFund" name="transFund" col="8" value="${quarterlyDetail.transFund}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="交易标的" col="4" formControl="true"/>
						<bf:text id="transTarget" name="transTarget" col="8" value="${quarterlyDetail.transTarget}">
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
									$.mask.post('${base}/bus/quarterlyDetail/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/quarterlyDetail/list';
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
								window.location = '${base}/bus/quarterlyDetail/list';
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
