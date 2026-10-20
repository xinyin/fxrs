<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>报送管理表</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="docSubmitBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="3">
						<bc:label text="报送机构" col="4" formControl="true"/>
                        <bf:select id="submitOrg" name="submitOrg" col="8"  dataSource="custom" url="/sys/dictItem/getDictItems/SUBMIT-org">
                        </bf:select>
					</bc:col>
					<bc:col col="3">
						<bc:label text="年度" col="4" formControl="true"/>
						<bf:text id="year" name="year" col="8"  value="${docSubmit.year}">
						</bf:text>
					</bc:col>
					<bc:col col="3">
						<bc:label text="季度" col="4" formControl="true"/>
                        <bf:select id="quarter" name="quarter" col="8"   value="${docSubmit.quarter}"  dataSource="custom" url="/sys/dictItem/getDictItems/SUBMIT-quarter">
                        </bf:select>
					</bc:col>
				</bf:formGroup>

				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-5">
						<bc:button type="primary" text="统计并校验" icon="arrow-circle-left">
							<be:event>
                                document.getElementById("result").value="";
								document.getElementById("result").style="text-align:left;width: 500px;background:#F2F2F2 ;min-height:150px;border: 1px solid;margin: 0px auto;padding: 1%;line-height:25px;";
                                $('#editForm').data('formValidation').validate();
                                var isValid = $('#editForm').data('formValidation').isValid();
                                if(isValid)
                                {
                                var formData = $('#editForm').serializeAll();
                                $.mask.post('${base}/bus/quarterDocSubmit/checkSubmit', formData, function(data){
                                if(data.success)
                                {
                                $.messager.alert('提示', data.msg, 'info', function(){
                                <%--window.location = '${base}/bus/docSubmit/list';--%>
                                });
                                }
                                else
                                {
                                <%--document.getElementById("result").value=data.check;--%>
                                <%--document.getElementById("result").innerHTML=data.check;--%>
								document.getElementById("result").innerHTML=data.check;
                                $.messager.alert('错误', data.msg, 'error');
                                }
                                });
                                }
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>

				<bf:formGroup hr="dashed" hrp="bottom">
                    <div  id="result"  style="width: 500px;background:#F2F2F2 ;min-height:150px;border: 1px solid;margin: 0px auto;text-align: center">
                    </div>
					<%--<input type="text" id="result" value="校验结果..." style="width: 500px;height: 150px;border: 1px solid;margin-left: 300px">--%>
					<%--</input>--%>
				</bf:formGroup>

				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-5">
						<bc:button type="primary" text="生成报送文件" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/quarterDocSubmit/makeSubmitFile', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/quarterDocSubmit/list';
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
	document.getElementById("result").innerHTML="校验通过or校验失败明细";
	document.getElementById("result").style="width: 500px;background:#F2F2F2 ;min-height:150px;border: 1px solid;margin: 0px auto;text-align: center;line-height:150px";
});
</script>
</body>
</html>
