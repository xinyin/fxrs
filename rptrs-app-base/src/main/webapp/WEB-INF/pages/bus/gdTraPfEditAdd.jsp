<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>GdTraPf</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="gdTraPfBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="pid" name="pid" value="${gdTraPf.pid}"/>
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<%--<bc:col col="6">
						<bc:label text="主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${gdTraPf.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/gdTraPf/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>--%>
					<bc:col col="6">
						<bc:label text="派发方案名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${gdTraPf.name}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="派发年份" col="4" formControl="true"/>
						<bf:text id="year" name="year" col="8" value="${gdTraPf.year}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="文件生成时间" col="4" formControl="true"/>
						<bf:datebox id="createTime" name="createTime" col="8"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="当前总股本" col="4" formControl="true"/>
						<bf:text id="totalShare" name="totalShare" col="8" value="${gdTraPf.totalShare}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="股利分配额" col="4" formControl="true"/>
						<bf:text id="allocationAmount" name="allocationAmount" col="8" value="${gdTraPf.allocationAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="股利分配比例" col="4" formControl="true"/>
						<bf:text id="allocationProportion" name="allocationProportion" col="8" value="${gdTraPf.allocationProportion}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">

					<bc:col col="6">
						<bc:label text="现金分红" col="4" formControl="true"/>
						<bf:text id="cashBonus" name="cashBonus" col="8" value="${gdTraPf.cashBonus}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="派送红股" col="4" formControl="true"/>
						<bf:text id="bonusIssue" name="bonusIssue" col="8" value="${gdTraPf.bonusIssue}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">

					<bc:col col="6">
						<bc:label text="审批状态" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${gdTraPf.status}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="路径" col="4" formControl="true"/>
						<bf:text id="route" name="route" col="8" value="${gdTraPf.route}">
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
									$.mask.post('${base}/bus/gdTraPf/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												<%--window.location = '${base}/bus/gdTraPf/list';--%>
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
								window.location = '${base}/bus/gdTraPf/list';
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
</script>

</body>
</html>
