<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>任务类型集群表</title>

</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="父菜单编码" col="4" formControl="true"/>
						<bf:text name="parentId" col="8" disabled="true"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="父菜单名称" col="4" formControl="true"/>
						<bf:text name="parentName" col="8" disabled="true"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="ID" col="4" formControl="true"/>
						<bf:text id="id" name="id" col="8" value="${groupCluster.id}">
								<bv:remote url="${base}/sch/groupCluster/check" name="id" message="ID"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="类别编码" col="4" formControl="true"/>
						<bf:text id="groupCode" name="groupCode" col="8" value="${groupCluster.groupCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup >
					<bc:col col="6">
						<bc:label text="服务器编码" col="4" formControl="true"/>
						<bf:text id="clusterCode" name="clusterCode" col="8" value="${groupCluster.clusterCode}">
						</bf:text>
					</bc:col>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary"  text="保存" icon="save">
							<be:event>
								$('#form1').data('formValidation').validate();
								var isValid = $('#form1').data('formValidation').isValid();
								if(isValid)
								{
									var data = $('#form1').serializeAll();
									$.mask.post('${base}/sch/groupCluster/save', data, function(data){
										if(data.success)
										{
											$.messager.alert('提示', '保存成功。', 'info', function(){
												window.location = '${base}/sch/groupCluster/list';
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
						<bc:button  text="返回">
							<be:event>
								window.location = '${base}/sch/groupCluster/list';
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
</body>
</html>
