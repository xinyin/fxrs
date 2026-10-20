<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>GdTraZy</title>
	<style>
		.form-group .col-xs-5 {
			margin-top: 0.5%;
		}
	</style>
</head>
<body class="gray-bg" style="background: white">
	<div class="row">
		<bc:ibox id="gdTraZyBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="pid" name="pid" value="${gdTraZy.pid}"/>
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<%--<bc:col col="6">
						<bc:label text="主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${gdTraZy.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/gdTraZy/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>--%>
					<bc:col col="5">
						<bc:label text="股东代码" col="4" formControl="true"/>
						<bf:text id="shareholdersCode" name="shareholdersCode" col="7" value="${gdTraZy.shareholdersCode}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="股东名称" col="4" formControl="true"/>
						<bf:text id="shareholdersName" name="shareholdersName" col="7" value="${gdTraZy.shareholdersName}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="股东类型" col="4" formControl="true"/>
						<bf:text id="shareholdersType" name="shareholdersType" col="7" value="${gdTraZy.shareholdersType}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="是否国有企业" col="4" formControl="true"/>
						<bf:text id="stateOwned" name="stateOwned" col="7" value="${gdTraZy.stateOwned}">
						</bf:text>
					</bc:col>
					<bc:col col="5">
						<bc:label text="境内境外" col="4" formControl="true"/>
						<bf:select name="domesticOrForeign" col="7" dataSource="custom"  >
							<bf:option text="境内" value="%"></bf:option>
							<bf:option text="境外" value="%"></bf:option>
							<be:event type="select">
								var input = $("#domesticOrForeign");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>
					</bc:col>
					<bc:col col="5">
						<bc:label text="状态" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="7" value="${gdTraZy.status}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/gdTraZy/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/gdTraZy/list';
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
								window.location = '${base}/bus/gdTraZy/list';
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

	<script type="text/javascript">
        $("#approvalTime").datetimepicker({
            format:"yyyy-mm-dd hh:ii:00",
            language:"zh-CN",
            minuteStep:30,
            autoclose:2,
            todayBtn:"linked",
            minView: "hour"
        });

        $("#applicaSubTime").datetimepicker({
            format:"yyyy-mm-dd hh:ii:00",
            language:"zh-CN",
            minuteStep:30,
            autoclose:2,
            todayBtn:"linked",
            minView: "hour"
        });


        $("#tranDate").datetimepicker({
            format:"yyyy-mm-dd hh:ii:00",
            language:"zh-CN",
            minuteStep:30,
            autoclose:2,
            todayBtn:"linked",
            minView: "hour"
        });

        $("#validityContract").datetimepicker({
            format:"yyyy-mm-dd hh:ii:00",
            language:"zh-CN",
            minuteStep:30,
            autoclose:2,
            todayBtn:"linked",
            minView: "hour"
        });

	</script>
</body>
</html>
