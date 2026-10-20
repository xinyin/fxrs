<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>GdTraZy</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="GdTraZy" subTitle="请输入GdTraZy查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<%--<bf:formGroup col="4">
						<bc:label text="主键" col="5" formControl="true"/>
						<bf:text name="__LIKES_pid" col="7" placeholder="主键"/>
					</bf:formGroup>--%>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bf:formGroup col="4">
							<bc:label text="选择审批人：" col="5" formControl="true"/>
						</bf:formGroup>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bf:formGroup col="4">
							<bc:label text="部门" col="5" formControl="true"/>
							<bf:select name="__LIKES_unpledgedComment" col="6" dataSource="custom"  >
								<bf:option text="法律合规部" value="%"></bf:option>
								<be:event type="select">
									var input = $("#__LIKES_unpledgedComment");
									input.focus();
									input.blur();
								</be:event>
							</bf:select>
						</bf:formGroup>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bf:formGroup col="4">
							<bc:label text="审批人" col="5" formControl="true"/>
							<bf:select name="__LIKES_unpledgedComment" col="6" dataSource="custom"  >
								<bf:option text="壹壹" value="%"></bf:option>
								<bf:option text="贰贰" value="%"></bf:option>
								<bf:option text="叁叁" value="%"></bf:option>
								<bf:option text="斯斯" value="%"></bf:option>
								<bf:option text="小五" value="%"></bf:option>
								<be:event type="select">
									var input = $("#__LIKES_unpledgedComment");
									input.focus();
									input.blur();
								</be:event>
							</bf:select>
						</bf:formGroup>
					</bf:formGroup>

					<%--<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="3" colOffset="1">
							<bc:button id="btnQuery" type="danger" text="确认" icon="search"/>
							<bc:button id="btnReset" type="primary" text="取消" icon="refresh"/>
						</bc:col>
					</bf:formGroup>--%>

					<bf:formGroup>
						<div class="col-sm-4 col-sm-offset-2">
							<bc:button type="danger" text="保存" icon="save">
								<be:event>
									$('#queryForm').data('formValidation').validate();
									var isValid = $('#queryForm').data('formValidation').isValid();
									if(isValid)
									{
									var formData = $('#queryForm').serializeAll();
									$.mask.post('${base}/bus/gdTraZy/save', formData, function(data){
									if(data.success)
									{
									$.messager.alert('提示', data.msg, 'info', function(){
									<%--window.location = '${base}/bus/gdTraZy/list';--%>
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
									window.location = '${base}/bus/gdTraSp/list';
								</be:event>
							</bc:button>
						</div>
					</bf:formGroup>

				</bc:row>

			</bf:form>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script type="text/javascript">

    function toSP(){
        /*alert("aaa");*/
        window.location = '${base}/bus/gdTraSp/edit2';
    }

</script>


</body>
</html>