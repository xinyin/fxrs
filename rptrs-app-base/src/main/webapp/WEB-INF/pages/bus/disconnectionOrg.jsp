<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>自然人新增或编辑</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="toPid" name="toPid" value="${toPid}"/>
				<%--<bf:formGroup type="success" hr="dashed" hrp="bottom">--%>
					<%--<bc:col col="6">--%>
						<%--<bc:label text="要解除的自然人" col="4" formControl="true"/>--%>
						<%--<bf:lookup url="${base}/bus/relPsn/queryRelPsn/${pid}" id="pid"--%>
								   <%--name="pid" winId="winlm" propText="projectName"--%>
								   <%--propValue="pid" col="8" onSelect="check" width="800px" height="400px">--%>
						<%--</bf:lookup>--%>
						<%--<bf:text id="blackReason" name="blackReason" col="8" value="${relPsn.blackReason}" placeholder="此项为必填项">--%>
							<%--<bv:notEmpty message="请输入解除关联关系原因"/>--%>
						<%--</bf:text>--%>
					<%--</bc:col>--%>
				<%--</bf:formGroup>--%>
				<bf:formGroup type="success" hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="解除关联关系原因" col="4" formControl="true"/>
						<bf:text id="liftReason" name="liftReason" col="8" placeholder="此项为必填项">
							<bv:notEmpty message="请输入解除关联关系原因"/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success" hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="审批部门" col="4" formControl="true"/>
                        <bf:select id="identifyType" name="identifyType" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-department">
                        </bf:select>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="审批人" col="4" formControl="true"/>
                        <bf:select id="sex" name="sex" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-approver">
                        </bf:select>
                    </bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="success" outline="true" text="提交审批" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/relOrg/disconnection', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												<%--var opt = $("#opt").val();--%>
												window.location = '${base}/bus/relOrg/list';
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
						<bc:button type="primary" text="取消" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/relOrg/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
		</bf:form>
	</bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">

</script>
</body>
</html>
