<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>排除时间段设置</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="code" value="${code }"></bf:hidden>
				<bf:formGroup type="success">
					<bf:select name="startHour" id="startHour" col="2">
							<bf:option text="00" value="00"/>
							<bf:option text="01" value="01"/>
							<bf:option text="02" value="02"/>
							<bf:option text="03" value="03"/>
							<bf:option text="04" value="04"/>
							<bf:option text="05" value="05"/>
							<bf:option text="06" value="06"/>
							<bf:option text="07" value="07"/>
							<bf:option text="08" value="08"/>
							<bf:option text="09" value="09"/>
							<bf:option text="10" value="10"/>
							<bf:option text="11" value="11"/>
							<bf:option text="12" value="12"/>
							<bf:option text="13" value="13"/>
							<bf:option text="14" value="14"/>
							<bf:option text="15" value="15"/>
							<bf:option text="16" value="16"/>
							<bf:option text="17" value="17"/>
							<bf:option text="18" value="18"/>
							<bf:option text="19" value="19"/>
							<bf:option text="20" value="20"/>
							<bf:option text="21" value="21"/>
							<bf:option text="22" value="22"/>
							<bf:option text="23" value="23"/>
					</bf:select>
					<bc:label text="点" col="1" formControl="true" style="width: 5px;"/>
					<bf:select name="startMinute" id="startMinute" col="2">
						<bf:option text="00" value="00"/>
						<bf:option text="01" value="01"/>
						<bf:option text="02" value="02"/>
						<bf:option text="03" value="03"/>
						<bf:option text="04" value="04"/>
						<bf:option text="05" value="05"/>
						<bf:option text="06" value="06"/>
						<bf:option text="07" value="07"/>
						<bf:option text="08" value="08"/>
						<bf:option text="09" value="09"/>
						<bf:option text="10" value="10"/>
						<bf:option text="11" value="11"/>
						<bf:option text="12" value="12"/>
						<bf:option text="13" value="13"/>
						<bf:option text="14" value="14"/>
						<bf:option text="15" value="15"/>
						<bf:option text="16" value="16"/>
						<bf:option text="17" value="17"/>
						<bf:option text="18" value="18"/>
						<bf:option text="19" value="19"/>
						<bf:option text="20" value="20"/>
						<bf:option text="21" value="21"/>
						<bf:option text="22" value="22"/>
						<bf:option text="23" value="23"/>
						<bf:option text="24" value="24"/>
						<bf:option text="24" value="24"/>
						<bf:option text="25" value="25"/>
						<bf:option text="26" value="26"/>
						<bf:option text="27" value="27"/>
						<bf:option text="28" value="28"/>
						<bf:option text="29" value="29"/>
						<bf:option text="30" value="30"/>
						<bf:option text="31" value="31"/>
						<bf:option text="32" value="32"/>
						<bf:option text="33" value="33"/>
						<bf:option text="34" value="34"/>
						<bf:option text="35" value="35"/>
						<bf:option text="36" value="36"/>
						<bf:option text="37" value="37"/>
						<bf:option text="38" value="38"/>
						<bf:option text="39" value="39"/>
						<bf:option text="40" value="40"/>
						<bf:option text="41" value="41"/>
						<bf:option text="42" value="42"/>
						<bf:option text="43" value="43"/>
						<bf:option text="44" value="44"/>
						<bf:option text="45" value="45"/>
						<bf:option text="46" value="46"/>
						<bf:option text="47" value="47"/>
						<bf:option text="48" value="48"/>
						<bf:option text="49" value="49"/>
						<bf:option text="50" value="50"/>
						<bf:option text="51" value="51"/>
						<bf:option text="52" value="52"/>
						<bf:option text="53" value="53"/>
						<bf:option text="54" value="54"/>
						<bf:option text="55" value="55"/>
						<bf:option text="56" value="56"/>
						<bf:option text="57" value="57"/>
						<bf:option text="58" value="58"/>
						<bf:option text="59" value="59"/>
					</bf:select>
					<bc:label text="分" col="1" formControl="true" style="width: 5px;"/>
					<bc:label text="-" col="1" formControl="true" style="width: 5px;"/>
					<bf:select name="endHour" id="endHour" col="2">
							<bf:option text="00" value="00"/>
							<bf:option text="01" value="01"/>
							<bf:option text="02" value="02"/>
							<bf:option text="03" value="03"/>
							<bf:option text="04" value="04"/>
							<bf:option text="05" value="05"/>
							<bf:option text="06" value="06"/>
							<bf:option text="07" value="07"/>
							<bf:option text="08" value="08"/>
							<bf:option text="09" value="09"/>
							<bf:option text="10" value="10"/>
							<bf:option text="11" value="11"/>
							<bf:option text="12" value="12"/>
							<bf:option text="13" value="13"/>
							<bf:option text="14" value="14"/>
							<bf:option text="15" value="15"/>
							<bf:option text="16" value="16"/>
							<bf:option text="17" value="17"/>
							<bf:option text="18" value="18"/>
							<bf:option text="19" value="19"/>
							<bf:option text="20" value="20"/>
							<bf:option text="21" value="21"/>
							<bf:option text="22" value="22"/>
							<bf:option text="23" value="23"/>
					</bf:select>
					<bc:label text="点" col="1" formControl="true" style="width: 5px;"/>
					<bf:select name="endMinute" id="endMinute" col="2">
						<bf:option text="00" value="00"/>
						<bf:option text="01" value="01"/>
						<bf:option text="02" value="02"/>
						<bf:option text="03" value="03"/>
						<bf:option text="04" value="04"/>
						<bf:option text="05" value="05"/>
						<bf:option text="06" value="06"/>
						<bf:option text="07" value="07"/>
						<bf:option text="08" value="08"/>
						<bf:option text="09" value="09"/>
						<bf:option text="10" value="10"/>
						<bf:option text="11" value="11"/>
						<bf:option text="12" value="12"/>
						<bf:option text="13" value="13"/>
						<bf:option text="14" value="14"/>
						<bf:option text="15" value="15"/>
						<bf:option text="16" value="16"/>
						<bf:option text="17" value="17"/>
						<bf:option text="18" value="18"/>
						<bf:option text="19" value="19"/>
						<bf:option text="20" value="20"/>
						<bf:option text="21" value="21"/>
						<bf:option text="22" value="22"/>
						<bf:option text="23" value="23"/>
						<bf:option text="24" value="24"/>
						<bf:option text="25" value="25"/>
						<bf:option text="26" value="26"/>
						<bf:option text="27" value="27"/>
						<bf:option text="28" value="28"/>
						<bf:option text="29" value="29"/>
						<bf:option text="30" value="30"/>
						<bf:option text="31" value="31"/>
						<bf:option text="32" value="32"/>
						<bf:option text="33" value="33"/>
						<bf:option text="34" value="34"/>
						<bf:option text="35" value="35"/>
						<bf:option text="36" value="36"/>
						<bf:option text="37" value="37"/>
						<bf:option text="38" value="38"/>
						<bf:option text="39" value="39"/>
						<bf:option text="40" value="40"/>
						<bf:option text="41" value="41"/>
						<bf:option text="42" value="42"/>
						<bf:option text="43" value="43"/>
						<bf:option text="44" value="44"/>
						<bf:option text="45" value="45"/>
						<bf:option text="46" value="46"/>
						<bf:option text="47" value="47"/>
						<bf:option text="48" value="48"/>
						<bf:option text="49" value="49"/>
						<bf:option text="50" value="50"/>
						<bf:option text="51" value="51"/>
						<bf:option text="52" value="52"/>
						<bf:option text="53" value="53"/>
						<bf:option text="54" value="54"/>
						<bf:option text="55" value="55"/>
						<bf:option text="56" value="56"/>
						<bf:option text="57" value="57"/>
						<bf:option text="58" value="58"/>
						<bf:option text="59" value="59"/>
					</bf:select>
					<bc:label text="分" col="1" formControl="true" style="width: 5px;"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary"  text="保存" icon="save">
							<be:event>
								$('#form1').data('formValidation').validate();
								var isValid = $('#form1').data('formValidation').isValid();
								if(isValid)
								{
									var startHour = $('#startHour').val();
									var startMinute = $('#startMinute').val();
									var endHour = $('#endHour').val();
									var endMinute = $('#endMinute').val();
									var start = startHour + startMinute;
									var end = endHour + endMinute;
									if(Number(end) <= Number(start))
									{
										$.messager.toastr('错误', '结束时间必须大于开始时间。', 'error');
										return;
									}
									
									//返回列表界面
									var time = {
										start: start,
										end:end
									};
																	
									var data = $('#form1').serializeAll();
									$.mask.post('${base}/sch/calendarDetail/time/set', data, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												$.messager.hide('winAddTime');
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
								$.messager.hide('winAddTime');
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

})

</script>
</body>
</html>
