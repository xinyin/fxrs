<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联关系表</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="incidenceRelationBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${incidenceRelation.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/incidenceRelation/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联主体pid" col="4" formControl="true"/>
						<bf:text id="entityPid" name="entityPid" col="8" value="${incidenceRelation.entityPid}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联主体登记编码" col="4" formControl="true"/>
						<bf:text id="entityRegCode" name="entityRegCode" col="8" value="${incidenceRelation.entityRegCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联主体是类别（1-法人，2-自然人）" col="4" formControl="true"/>
						<bf:text id="entityType" name="entityType" col="8" value="${incidenceRelation.entityType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联主体名称" col="4" formControl="true"/>
						<bf:text id="entityName" name="entityName" col="8" value="${incidenceRelation.entityName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上层关联主体pid" col="4" formControl="true"/>
						<bf:text id="superEntityPid" name="superEntityPid" col="8" value="${incidenceRelation.superEntityPid}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上层关联主体登记编码" col="4" formControl="true"/>
						<bf:text id="superEntityRegCode" name="superEntityRegCode" col="8" value="${incidenceRelation.superEntityRegCode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="上层关联主体是类别（1-法人，2-自然人）" col="4" formControl="true"/>
						<bf:text id="superEntityType" name="superEntityType" col="8" value="${incidenceRelation.superEntityType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上层关联主体名称" col="4" formControl="true"/>
						<bf:text id="superEntityName" name="superEntityName" col="8" value="${incidenceRelation.superEntityName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="主体与上层主体关联关系id" col="4" formControl="true"/>
						<bf:text id="relationId" name="relationId" col="8" value="${incidenceRelation.relationId}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="主体与上层主体关联关描述" col="4" formControl="true"/>
						<bf:text id="relationDesc" name="relationDesc" col="8" value="${incidenceRelation.relationDesc}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联关系说明" col="4" formControl="true"/>
						<bf:text id="relationInfo" name="relationInfo" col="8" value="${incidenceRelation.relationInfo}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否持有或控制上一级关联方股权(0-否，1-是)" col="4" formControl="true"/>
						<bf:text id="isShareHolding" name="isShareHolding" col="8" value="${incidenceRelation.isShareHolding}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="持股占比（默认为0）" col="4" formControl="true"/>
						<bf:text id="holdingPercent" name="holdingPercent" col="8" value="${incidenceRelation.holdingPercent}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="银行保险机构编码" col="4" formControl="true"/>
						<bf:text id="orignalRegcode" name="orignalRegcode" col="8" value="${incidenceRelation.orignalRegcode}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="源主体（银行/保险机构）名称" col="4" formControl="true"/>
						<bf:text id="orignalOrgName" name="orignalOrgName" col="8" value="${incidenceRelation.orignalOrgName}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关联主体与银行保险机构关联关系" col="4" formControl="true"/>
						<bf:text id="relationOrignal" name="relationOrignal" col="8" value="${incidenceRelation.relationOrignal}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="报送版本号" col="4" formControl="true"/>
						<bf:text id="reportVersion" name="reportVersion" col="8" value="${incidenceRelation.reportVersion}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="上一层pid" col="4" formControl="true"/>
						<bf:text id="parentLevelPid" name="parentLevelPid" col="8" value="${incidenceRelation.parentLevelPid}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="关联关系层级（1为根节点(银行/保险)，该表中只有以2开始的子节点）" col="4" formControl="true"/>
						<bf:text id="level" name="level" col="8" value="${incidenceRelation.level}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否有效 0-无效，1-有效" col="4" formControl="true"/>
						<bf:text id="validStatus" name="validStatus" col="8" value="${incidenceRelation.validStatus}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否被报送过（0-历史上从未报送过，1-已被报送过）" col="4" formControl="true"/>
						<bf:text id="isReported" name="isReported" col="8" value="${incidenceRelation.isReported}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="生效时间" col="4" formControl="true"/>
						<bf:datebox id="activeTime" name="activeTime" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="失效时间" col="4" formControl="true"/>
						<bf:datebox id="invalidTime" name="invalidTime" col="8"/>
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
									$.mask.post('${base}/bus/incidenceRelation/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/incidenceRelation/list';
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
								window.location = '${base}/bus/incidenceRelation/list';
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
