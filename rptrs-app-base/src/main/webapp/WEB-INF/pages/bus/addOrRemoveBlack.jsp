<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>自然人新增或编辑</title>
<x:jsLib/>
	<style>
		.form-group div{
			margin-top: 0.5%;
		}
		.labels .control-label{
			display: inline-block;
			width: 13%;
			float: left;
		}
	</style>
</head>
<body class="gray-bg" style="background: white;">
	<div class="row">
		<bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="pid" name="pid" value="${relPsn.pid}"/>
				<bf:hidden id="isBlack" name="isBlack" value="${relPsn.isBlack}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="4">
						<bc:label text="姓名" col="5" formControl="true"/>
						<bf:text id="name" name="name" col="7" value="${relPsn.name}" disabled="true"/>
					</bc:col>
					<bc:col col="4">
						<bc:label text="性別" col="5" formControl="true"/>
						<bf:select id="sex" name="sex" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Sex" value="${relPsn.sex}"  disabled="true"/>
					</bc:col>
					<bc:col col="4">
						<bc:label text="证件类型" col="5" formControl="true"/>
						<bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsn.identifyType}" disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="证件号码" col="5" formControl="true"/>
						<bf:text id="identifyNumber" name="identifyNumber" col="7" value="${relPsn.identifyNumber}"  disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="黑名单标识" col="5" formControl="true"/>
						<bf:select id="isBlack" name="isBlack" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-isNo" value="${relPsn.isBlack}"  disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="境内外标识" col="5" formControl="true"/>
						<bf:select id="domesticOrForeign" name="domesticOrForeign"  col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" value="${relPsn.domesticOrForeign}"  disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="民族" col="5" formControl="true"/>
						<bf:select id="race" name="race" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Race" value="${relPsn.race}"  disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="机构名称" col="5" formControl="true"/>
						<bf:select id="mgerOrg" name="mgerOrg" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsn.mgerOrg}"  disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="出生日期" col="5" formControl="true"/>
						<bf:datebox name="birthdate" id="birthdate" col="7" format="date" value="${relPsn.birthdate}"  disabled="true">
						</bf:datebox>
					</bc:col>
					<bc:col col="4">
						<bc:label text="婚姻状况" col="5" formControl="true"/>
						<bf:select id="maritalStatus" name="maritalStatus" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsn.maritalStatus}"  disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="移动电话" col="5" formControl="true"/>
						<bf:text id="mobile" name="mobile" col="7" value="${relPsn.mobile}"  disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="邮箱" col="5" formControl="true"/>
						<bf:text id="email" name="email" col="7" value="${relPsn.email}"  disabled="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<%--<bf:formGroup type="success" hr="dashed" hrp="bottom">--%>
					<%--<bc:col col="6">--%>
						<%--<bc:label text="境内外标识" col="4" formControl="true"/>--%>
						<%--<bf:select id="domesticOrForeign" name="domesticOrForeign"  col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" value="${relPsn.domesticOrForeign}"  disabled="true">--%>
						<%--</bf:select>--%>
					<%--</bc:col>--%>
				<%--</bf:formGroup>--%>
				<bf:formGroup type="success"  hrp="bottom">
					<div class="labels">
						<bc:col col="12">
							<bc:label text="${addOrRemoveBlack}"  formControl="true"/>
							<bf:text id="blackReason" name="blackReason" col="10" multiple="true" placeholder="此项为必填项">
								<bv:notEmpty message="请输入加入/移出黑名单原因"/>
							</bf:text>
						</bc:col>
					</div>

				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-4">
						<bc:button type="success" outline="true" text="提交审批" icon="save">
							<be:event>
								<%--$('#editForm').data('formValidation').validate();--%>
								<%--var isValid = $('#editForm').data('formValidation').isValid();--%>
								<%--if(isValid)--%>
								<%--{--%>
									<%--var formData = $('#editForm').serializeAll();--%>
									<%--$.mask.post('${base}/bus/relPsn/addOrRemoveBlack', formData, function(data){--%>
										<%--if(data.success)--%>
										<%--{--%>
											<%--$.messager.alert('提示', data.msg, 'info', function(){--%>
												<%--&lt;%&ndash;var opt = $("#opt").val();&ndash;%&gt;--%>
												<%--window.location = '${base}/bus/relPsn/list';--%>
											<%--});--%>
										<%--}--%>
										<%--else--%>
										<%--{--%>
											<%--$.messager.alert('错误', data.msg, 'error');--%>
										<%--}--%>
									<%--});--%>
								<%--}--%>
								sub();
							</be:event>
						</bc:button>
						<bc:button type="primary" text="取消" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/relPsn/list';
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
	//提交审核
	<%--function sub(){--%>
		<%--$('#editForm').data('formValidation').validate();--%>
		<%--var isValid = $('#editForm').data('formValidation').isValid();--%>
		<%--if(isValid)--%>
		<%--{--%>
			<%--var objData = {};--%>

			<%--for (var entry of $('#editForm').serializeArray()){--%>
				<%--objData[entry['name']] = entry['value'];--%>
			<%--}--%>
			<%--var myDate = new Date();--%>
			<%--objData.id =  myDate.getTime();--%>
			<%--objData.type = "PSN";--%>
			<%--data = JSON.stringify(objData);--%>
			<%--var formData = $.helper.base64Encode(data);--%>
			<%--var obj = {--%>
				<%--procDefCode: 'YZJ_REL',--%>
				<%--oldData: '',--%>
				<%--newData: formData,--%>
				<%--uploadSessions: '',--%>
				<%--procInstId: ''--%>
			<%--}--%>
			<%--$.mask.post('${base}/workflow/process/saveAndStart', obj, function(data){--%>
				<%--if(data.success)--%>
				<%--{--%>
					<%--$.messager.alert('提示', '提交成功。', 'info', function(){--%>
						<%--window.location = '${base}/workflow/process/runingList';--%>
					<%--});--%>
				<%--}--%>
				<%--else--%>
				<%--{--%>
					<%--$.messager.alert('错误', data.msg, 'error');--%>
				<%--}--%>
			<%--});--%>
		<%--}--%>
	<%--}--%>
	function sub(){
		var pid = $("#pid").val();
		var blackReason = document.getElementById('blackReason').innerHTML;
		alert(pid);
		$.ajax({
			url:"${base}/bus/relPsn/subAudit/"+pid,
			type:"post",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success:function(data){
				console.log(data.relPsn)
				if(data !=null){
					//必须传入id 值
					var objData = data.relPsn;
					//alert(objData)
					var myDate = new Date();
					objData.id =  myDate.getTime();
					objData.type = "PSN";
					objData.blackReason = $("#blackReason").val().trim();
					data = JSON.stringify(objData);
					var formData = $.helper.base64Encode(data);
					// alert(objData)
					var obj = {
						procDefCode: 'YZJ_REL',
						oldData: '',
						newData: formData,
						uploadSessions: '',
						procInstId: ''
					}
					$.mask.post('${base}/workflow/process/saveAndStart', obj, function(data){
						if(data.success)
						{
							$.messager.alert('提示', '提交成功。', 'info', function(){
								window.location = '${base}/workflow/process/runingList';
							});
						}
						else
						{
							$.messager.alert('错误', data.msg, 'error');
						}
					});
				}
			}
		})
	}
</script>
</body>
</html>
