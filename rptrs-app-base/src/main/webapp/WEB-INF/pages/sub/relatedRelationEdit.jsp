<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>RelatedRelation</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="relatedRelationBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="pid" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${relatedRelation.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/sub/relatedRelation/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上级关联方名称" col="4" formControl="true"/>
						<bf:text id="superEntityName" name="superEntityName" col="8" value="${relatedRelation.superEntityName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上级关联方类型" col="4" formControl="true"/>
						<bf:text id="superEntityType" name="superEntityType" col="8" value="${relatedRelation.superEntityType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上级关联方统一社会信用代码/证件号码" col="4" formControl="true"/>
						<bf:text id="superEntityUniscCode" name="superEntityUniscCode" col="8" value="${relatedRelation.superEntityUniscCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联方名称" col="4" formControl="true"/>
						<bf:text id="entityName" name="entityName" col="8" value="${relatedRelation.entityName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联方类型" col="4" formControl="true"/>
						<bf:text id="entityType" name="entityType" col="8" value="${relatedRelation.entityType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联方统一社会信用代码/证件号码" col="4" formControl="true"/>
						<bf:text id="entityUniscUode" name="entityUniscUode" col="8" value="${relatedRelation.entityUniscUode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="与前一层的关联关系类型" col="4" formControl="true"/>
						<bf:text id="relationType" name="relationType" col="8" value="${relatedRelation.relationType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="统计口径" col="4" formControl="true"/>
						<bf:text id="regulator" name="regulator" col="8" value="${relatedRelation.regulator}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否持股" col="4" formControl="true"/>
						<bf:text id="isShareHolding" name="isShareHolding" col="8" value="${relatedRelation.isShareHolding}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="持股比例" col="4" formControl="true"/>
						<bf:text id="holdingPercent" name="holdingPercent" col="8" value="${relatedRelation.holdingPercent}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联关系说明" col="4" formControl="true"/>
						<bf:text id="relationInfo" name="relationInfo" col="8" value="${relatedRelation.relationInfo}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="该关系是否仍然有效" col="4" formControl="true"/>
						<bf:text id="validStatus" name="validStatus" col="8" value="${relatedRelation.validStatus}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="生效日期" col="4" formControl="true"/>
						<bf:datebox id="activeTime" name="activeTime" col="8"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="失效日期" col="4" formControl="true"/>
						<bf:datebox id="invalidTime" name="invalidTime" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="前置流程状态" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${relatedRelation.status}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="前置流程状态说明" col="4" formControl="true"/>
						<bf:text id="statusComment" name="statusComment" col="8" value="${relatedRelation.statusComment}">
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
									$.mask.post('${base}/sub/relatedRelation/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/sub/relatedRelation/list';
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
								window.location = '${base}/sub/relatedRelation/list';
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
