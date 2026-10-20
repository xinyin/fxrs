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
<body class="gray-bg" style="background: white">
	<div class="row">
		<bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="pid" name="pid" value="${relOrg.pid}"/>
				<bf:hidden id="isBlack" name="isBlack" value="${relOrg.isBlack}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="4">
						<bc:label text="机构名称" col="5" formControl="true"/>
						<bf:text id="orgName" name="orgName" col="7" value="${relOrg.orgName}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="证件类型" col="5" formControl="true"/>
						<bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrg.identifyType}" disabled="true">
						</bf:select>
					</bc:col>

					<bc:col col="4">
						<bc:label text="证件号码" col="5" formControl="true"/>
						<bf:text id="uniscCode" name="uniscCode" col="7" value="${relOrg.uniscCode}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="注册地" col="5" formControl="true"/>
						<bf:text id="regAddress" name="regAddress" col="7" value="${relOrg.regAddress}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="境内/境外" col="5" formControl="true"/>
						<bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
								   value="${relOrg.domesticOrForeign}" disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="机构名称" col="5" formControl="true"/>
						<bf:select id="mgerOrg" name="mgerOrg" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relOrg.mgerOrg}" disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="企业类型" col="5" formControl="true"/>
						<bf:select id="orgType" name="orgType" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/SUB-BusinessType" value="${relOrg.orgType}" disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="营业范围" col="5" formControl="true"/>
						<bf:text id="businessScope" name="businessScope" col="7" value="${relOrg.businessScope}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="机构状态" col="5" formControl="true"/>
						<bf:select id="status" name="status" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-Status" value="${relOrg.status}" disabled="true">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="注册资本(万元/人民币)" col="5" formControl="true"/>
						<bf:text id="regCapital" name="regCapital" col="7" value="${relOrg.regCapital}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="注册资本币种代码" col="5" formControl="true"/>
						<bf:text id="regCapitalCurrency" name="regCapitalCurrency" col="7"
								 value="${relOrg.regCapitalCurrency}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="法定代表人" col="5" formControl="true"/>
						<bf:text id="legalPerson" name="legalPerson" col="7" value="${relOrg.legalPerson}" disabled="true">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="黑名单标识" col="5" formControl="true"/>
						<bf:select id="isBlack" name="isBlack" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-isNo" value="${relOrg.isBlack}" disabled="true">
						</bf:select>
					</bc:col>

				</bf:formGroup>
				<bf:formGroup type="success"  hrp="bottom">
					<div class="labels">
						<bc:col col="12">
							<bc:label text="${addOrRemoveBlack}"   formControl="true"/>
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
									<%--$.mask.post('${base}/bus/relOrg/addOrRemoveBlack', formData, function(data){--%>
										<%--if(data.success)--%>
										<%--{--%>
											<%--$.messager.alert('提示', data.msg, 'info', function(){--%>
												<%--&lt;%&ndash;var opt = $("#opt").val();&ndash;%&gt;--%>
												<%--window.location = '${base}/bus/relOrg/list';--%>
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
	//提交审核
	function sub(){
		var pid = $("#pid").val();
		$.messager.alert(pid);
		$.ajax({
			url:"${base}/bus/relOrg/subAudit/"+pid,
			type:"post",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success:function(data){
				console.log(data.relOrg)
				if(data !=null){
					//必须传入id 值
					var objData = data.relOrg;
					//alert(objData)
					var myDate = new Date();
					objData.id =  myDate.getTime();
					objData.type = "ORG";
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
