<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>集群节点</title>

</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
			<bf:hidden name="status" id="status" value="${node.status}"></bf:hidden>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="节点编码" col="4" formControl="true"/>
						<bf:text id="code" name="code" col="8" value="${node.code}" disabled="${opt == 'edit'}">
							<bv:remote url="${base}/sch/node/check" name="code" message="节点编码"/>
							<bv:notEmpty/>
							<bv:regexp regexp="^[a-zA-Z][a-zA-Z0-9_]*$" message="只能以英文字母开头，只能包含英文字母、数字、下划线"/>
							<bv:stringLength max="64" message="节点编码不能大于64"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="节点名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${node.name}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="集群编码" col="4" formControl="true"/>
						<bf:select id="clusterCode" name="clusterCode" col="8" value="${node.clusterCode}" dataSource="custom" url="/sch/cluster/queryAll"  disabled="${opt == 'edit'}">
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="IP地址" col="4" formControl="true"/>
						<bf:text id="ipAddr" name="ipAddr" col="8" value="${node.ipAddr}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="端口" col="4" formControl="true"/>
						<bf:text id="port" name="port" col="8" value="${node.port}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="应用上下文" col="4" formControl="true"/>
						<bf:text id="webContext" name="webContext" col="8" value="${node.webContext}">
							<bv:notEmpty/>
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="备注" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="10" multiple="true" value="${node.remark}"></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button type="primary"  text="保存" icon="save">
							<be:event>
								$('#form1').data('formValidation').validate();
								var isValid = $('#form1').data('formValidation').isValid();
								if(isValid)
								{
									var data = $('#form1').serializeAll();
									$.mask.post('${base}/sch/node/save', data, function(data){
										if(data.success)
										{
											$.messager.alert('提示', '保存成功。', 'info', function(){
												window.location = '${base}/sch/node/list';
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
						<bc:button  text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/sch/node/list';
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
