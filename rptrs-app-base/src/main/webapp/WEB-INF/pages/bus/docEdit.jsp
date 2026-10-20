<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>Doc</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="docBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${doc.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/doc/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="制度名称" col="4" formControl="true"/>
						<bf:text id="sysName" name="sysName" col="8" value="${doc.sysName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="文号" col="4" formControl="true"/>
						<bf:text id="articleNumber" name="articleNumber" col="8" value="${doc.articleNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="发文部门" col="4" formControl="true"/>
						<bf:text id="dispatchDep" name="dispatchDep" col="8" value="${doc.dispatchDep}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上传机构" col="4" formControl="true"/>
						<bf:text id="uploadOrg" name="uploadOrg" col="8" value="${doc.uploadOrg}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上传部门" col="4" formControl="true"/>
						<bf:text id="uploadDep" name="uploadDep" col="8" value="${doc.uploadDep}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="生效时间" col="4" formControl="true"/>
						<bf:datebox id="effectiveTime" name="effectiveTime" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="失效时间" col="4" formControl="true"/>
						<bf:datebox id="expiryTime" name="expiryTime" col="8"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="颁布机构" col="4" formControl="true"/>
						<bf:text id="issueOrg" name="issueOrg" col="8" value="${doc.issueOrg}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否有效" col="4" formControl="true"/>
						<bf:text id="isVal" name="isVal" col="8" value="${doc.isVal}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="制度类型" col="4" formControl="true"/>
						<bf:text id="systemType" name="systemType" col="8" value="${doc.systemType}">
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
									$.mask.post('${base}/bus/doc/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/doc/list';
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
								window.location = '${base}/bus/doc/list';
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
