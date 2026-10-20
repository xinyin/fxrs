<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>报送管理表</title>
	<style>
		.disnone{
			display: none;
		}
		.disblock{
			display: block;
		}
		.btns{
			display: inline-block;
			margin: 4% 0;
		}
	</style>
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
					<bc:col col="6">
						<bc:label text="生成报送时间" col="5" formControl="true"></bc:label>
						<bf:datebox name="beginDate" id="beginDate" col="6" format="date">
						</bf:datebox>
					</bc:col>
					<bc:col col="2">
						<bf:datebox id="endDate" name="endDate" col="12" format="date">
						</bf:datebox>
					</bc:col>
				</bf:formGroup>

				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-5">
						<bc:button type="primary" text="统计" icon="arrow-circle-left">
							<be:event>
                                <%--document.getElementById("result").value="";--%>
                                $('#editForm').data('formValidation').validate();
                                var isValid = $('#editForm').data('formValidation').isValid();
                                if(isValid)
                                {

                                var formData = $('#editForm').serializeAll();
                                $.mask.post('${base}/bus/traDocSubmit/census', formData, function(data){
                                if(data.success)
                                {
								console.log(data)
								<%--显示表格--%>
								$('.disnone').addClass('disblock').removeClass('disnone');
								var html = "";
								for( var i = 0; i < data.traSubmitVo.length; i++ ) {
								html += "<tr align= 'center' >";
								html +=     "<td > <input type='checkbox' /></td>"
								html +=     "<td >" + data.traSubmitVo[i].name + "</td>"
								html +=     "<td >" + data.traSubmitVo[i].manageOrg + "</td>"
								html +=     "<td >" + data.traSubmitVo[i].identifyType + "</td>"
								html +=     "<td >" + data.traSubmitVo[i].identNumber + "</td>"
								html +=     "<td >" + data.traSubmitVo[i].businessType  + "</td>"
								html +=     "<td >" + data.traSubmitVo[i].relatedTranType + "</td>"
								html +=     "<td >" + data.traSubmitVo[i].tranAmount + "</td>"
								html +=     "<td > <input type='button' value='详情' class='btns'> </td>"
								html += "</tr>";
								}
								$("#tbody").html(html);
                                <%--$.messager.alert('提示', data.msg, 'info', function(){--%>
                                <%--&lt;%&ndash;window.location = '${base}/bus/docSubmit/list';&ndash;%&gt;--%>
                                <%--});--%>
                                }
                                else
                                {
                                <%--document.getElementById("result").value=data.check;--%>
                                <%--document.getElementById("result").innerHTML=data.check;--%>
								document.getElementById("result").value=data.check;
                                $.messager.alert('错误', data.msg, 'error');
                                }
                                });
                                }
							</be:event>
						</bc:button>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh">
							<be:event>
								document.getElementById("beginDate").value="";
								document.getElementById("endDate").value="";
							</be:event>
						</bc:button>
					</div>

				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<div class="disnone">
						<table id="tab" width="80%" align="center" cellspacing="0" border="1" bordercolor="#000000">
							<thead align= 'center' >
							<th style="text-align: center" ><input type="checkbox" /></th>
							<th  style="text-align: center">关联方名称</th>
							<th  style="text-align: center">机构名称</th>
							<th  style="text-align: center">证件类型</th>
							<th  style="text-align: center">证件号码</th>
							<th  style="text-align: center">业务类型</th>
							<th  style="text-align: center">交易类型</th>
							<th  style="text-align: center">交易金额/元</th>
							<th  style="text-align: center">操作</th>
							</thead>
							<tbody id="tbody">
							</tbody>
						</table>
						<br>
						<br>

					</div>

				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-5">
						<bc:button type="primary" text="数据校验" icon="arrow-circle-left">
							<be:event>
								<%--document.getElementById("result").value="";--%>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
								var formData = $('#editForm').serializeAll();
								$.mask.post('${base}/bus/traDocSubmit/checkSubmit/46/null', formData, function(data){
								if(data.success)
								{
								console.log(data)
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
                    <div  id="result" value="校验结果..." style="width: 500px;height: 150px;border: 1px solid;margin: 0px auto;">
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
									$.mask.post('${base}/bus/traDocSubmit/makeSubmitFile/46/null', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/traDocSubmit/list';
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
	
});
</script>
</body>
</html>
