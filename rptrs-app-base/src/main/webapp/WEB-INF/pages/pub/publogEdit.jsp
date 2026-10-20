<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>Publog</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="publogBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="pid" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${publog.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/pub/publog/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="pubFileName" col="4" formControl="true"/>
						<bf:text id="pubFileName" name="pubFileName" col="8" value="${publog.pubFileName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="pubSubject" col="4" formControl="true"/>
						<bf:text id="pubSubject" name="pubSubject" col="8" value="${publog.pubSubject}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="pubYear" col="4" formControl="true"/>
						<bf:text id="pubYear" name="pubYear" col="8" value="${publog.pubYear}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="pubQuarter" col="4" formControl="true"/>
						<bf:text id="pubQuarter" name="pubQuarter" col="8" value="${publog.pubQuarter}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="maxPercentInNetAssets" col="4" formControl="true"/>
						<bf:datebox id="maxPercentInNetAssets" name="maxPercentInNetAssets" col="8"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="下载次数" col="4" formControl="true"/>
						<bf:text id="downAmt" name="downAmt" col="8" value="${publog.downAmt}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="状态" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${publog.status}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="状态说明" col="4" formControl="true"/>
						<bf:text id="statusComment" name="statusComment" col="8" value="${publog.statusComment}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="下载状态" col="4" formControl="true"/>
						<bf:text id="downStatus" name="downStatus" col="8" value="${publog.downStatus}">
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
									$.mask.post('${base}/pub/publog/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/pub/publog/list';
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
								window.location = '${base}/pub/publog/list';
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
