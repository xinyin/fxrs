<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>RelSuspect</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="relSuspectBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${relSuspect.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/relSuspect/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联关系pid" col="4" formControl="true"/>
						<bf:text id="toPid" name="toPid" col="8" value="${relSuspect.toPid}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="源主体pid" col="4" formControl="true"/>
						<bf:text id="fromPid" name="fromPid" col="8" value="${relSuspect.fromPid}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关系类型" col="4" formControl="true"/>
						<bf:text id="relCategory" name="relCategory" col="8" value="${relSuspect.relCategory}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="源主体类型  PSN|个人 ORG|机构" col="4" formControl="true"/>
						<bf:text id="fromType" name="fromType" col="8" value="${relSuspect.fromType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联关系主体类型   PSN|个人 ORG|机构" col="4" formControl="true"/>
						<bf:text id="toType" name="toType" col="8" value="${relSuspect.toType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="源主体显示名称" col="4" formControl="true"/>
						<bf:text id="fromMemo" name="fromMemo" col="8" value="${relSuspect.fromMemo}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联关系主体显示名称" col="4" formControl="true"/>
						<bf:text id="toMemo" name="toMemo" col="8" value="${relSuspect.toMemo}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="亲属关系" col="4" formControl="true"/>
						<bf:text id="relation" name="relation" col="8" value="${relSuspect.relation}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="持股比例" col="4" formControl="true"/>
						<bf:text id="shareholdingRatio" name="shareholdingRatio" col="8" value="${relSuspect.shareholdingRatio}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否关联关系主体股东    0-否 1-是" col="4" formControl="true"/>
						<bf:text id="toPidShareholders" name="toPidShareholders" col="8" value="${relSuspect.toPidShareholders}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否显示" col="4" formControl="true"/>
						<bf:text id="isShow" name="isShow" col="8" value="${relSuspect.isShow}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关系层级" col="4" formControl="true"/>
						<bf:text id="level" name="level" col="8" value="${relSuspect.level}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="解除原因" col="4" formControl="true"/>
						<bf:text id="liftReason" name="liftReason" col="8" value="${relSuspect.liftReason}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-4">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/relSuspect/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/relSuspect/list';
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
								window.location = '${base}/bus/relSuspect/list';
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
