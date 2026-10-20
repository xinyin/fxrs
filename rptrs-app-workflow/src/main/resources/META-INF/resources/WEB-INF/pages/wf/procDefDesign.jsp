<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>业务流程设计</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
	
		<bc:ibox id="iboxbizModule" title="流程图" col="12" collapsible="true">
			<bc:col col="10" colOffset="1">
				<div id="diagram" style="overflow:auto;" data-on-node-select='onNodeSelect' 
					 data-toggle="jFlowDiagram" data-code="${procDef.procTemplateCode}"></div>
			</bc:col>
			<bc:col col="4">
				<bc:button type="primary"  text="返回">
					<be:event>
						window.location = '${base}/workflow/procDef/list';
					</be:event>
				</bc:button>
			</bc:col>
			<bc:col col="11">
				<bc:tabs id="tabContent">
					<bc:tab id="tab1" title="流程基本信息" visible="true">
						<bc:ibox id="ibox1" title="流程基本信息" col="12" >
							<bf:form id="procTemplateForm" method="post" validation="false">
								<bf:formGroup>
									<bc:col col="4">
										<bc:label text="业务编码" col="4" formControl="true"/>
										<bc:label text="${procDef.code}" col="6" formControl="true" style="text-align:left"/>
									</bc:col>
									<bc:col col="4">
										<bc:label text="模板编码" col="4" formControl="true"/>
										<bc:label text="${procDef.moduleCode}" col="6" formControl="true" style="text-align:left"/>
									</bc:col>
									<bc:col col="4">
										<bc:label text="表单编码" col="4" formControl="true"/>
										<bc:label text="${procDef.formCode}" col="6" formControl="true" style="text-align:left"/>
									</bc:col>
								</bf:formGroup>
								<bf:formGroup>
									<bc:col col="4">
										<bc:label text="业务名称" col="4" formControl="true"/>
										<bc:label text="${procDef.name}" col="6" formControl="true" style="text-align:left"/>
									</bc:col>
									<bc:col col="4">
										<bc:label text="模板名称" col="4" formControl="true"/>
										<bc:label text="${procDef.moduleName}" col="6" formControl="true" style="text-align:left"/>
									</bc:col>
									<bc:col col="4">
										<bc:label text="表单名称" col="4" formControl="true"/>
										<bc:label text="${procDef.formName}" col="6" formControl="true" style="text-align:left"/>
									</bc:col>
								</bf:formGroup>
							</bf:form>
						</bc:ibox>
					</bc:tab>
					<bc:tab id="tab2" title="节点授权">
						<bc:ibox id="ibox2" title="节点授权" col="12" >
							<bf:form id="procAuthForm" method="post" validation="true">
								<bf:formGroup>
									<bc:col col="6">
										<bc:label text="节点ID" col="4" formControl="true"/>
										<bc:label text="" col="6" formControl="true" cls="nodeKeyNode" style="text-align:left"/>
									</bc:col>
									<bc:col col="6">
										<bc:label text="节点名称" col="4" formControl="true"/>
										<bc:label text="" col="6" formControl="true" cls="nodeName" style="text-align:left"/>
									</bc:col>
								</bf:formGroup>
								<bf:formGroup>
									<bc:col col="1" cls="candidateCheck">
										<bf:checkGroup name="">
											<bf:check id="candidateCheck" change="candidateCheckChange()"/>
										</bf:checkGroup>
									</bc:col>
									<bc:col col="1">
										<bc:label text="候选人" formControl="true"/>
									</bc:col>
									<bc:col col="2">
										<bf:select id="userScope" name="userScope" dataSource="fixedDict"
											url="UserScope" value="">
											<bv:notEmpty/>
											<be:event type="select">userScopeChange(e.target.value);</be:event>
										</bf:select>
									</bc:col>
									<bc:col col="4" cls="authUserForm">
										<bf:select id="authUserForm" name="authUser" value=""
											dataSource="" url="">
											<bv:notEmpty/>
										</bf:select>
									</bc:col>
									<bc:col col="4" cls="authUserOther">
										<bf:lookup id="authUserOther" name="authUser"
											url="${base}/sys/staff/dialog?mult=true"
											value="${procAuth.authUser}" text="${procAuth.authUserName}"
											propValue="staffCode" propText="staffName" 
											callback="result" onSelect="onSelectUser" winId="jStaffSelect"
											title="选择候选人" width="800" height="400">
											<bv:notEmpty/>
										</bf:lookup>
									</bc:col>
								</bf:formGroup>
								<bf:formGroup>
									<bc:col col="1" cls="roleCheck">
										<bf:checkGroup name="">
											<bf:check id="roleCheck" change="roleCheckChange()"/>
										</bf:checkGroup>
									</bc:col>
									<bc:col col="1">
										<bc:label text="选择岗位" formControl="true"/>
									</bc:col>
									<bc:col col="2">
										<bf:select id="roleScope" name="roleScope" dataSource="custom"
											url="workflow/procDef/selectUnitScop" value="${procAuth.roleScope}">
											<bv:notEmpty/>
											<be:event type="select">roleScopeChange(e.target.value);</be:event>
										</bf:select>
									</bc:col>
									<bc:col col="1" cls="authRoleForm">
										<bf:select id="authRoleForm" name="authRoleProp" 
											dataSource="" url="" value="${procAuth.authRoleProp}">
											<bv:notEmpty/>
										</bf:select>
									</bc:col>
									<bc:col col="4" cls="authRoleOther">
										<bf:lookup id="authRoleOther" name="authRole" url="${base}/workflow/procDef/authRoleDlg"
											value="${procAuth.authRole}" text="${procAuth.authRoleName}"
											propValue="authRole" propText="authRoleName" 
											callback="result" onSelect="onSelectRole" winId="authRoleSelect"
											title="选择岗位" height="330">
											<bv:notEmpty/>
										</bf:lookup>
									</bc:col>
								</bf:formGroup>
								<bf:formGroup>
									<bc:col col="1" cls="bizAuthCheck">
										<bf:checkGroup name="">
											<bf:check id="bizAuthCheck" change="bizAuthCheckChange()"/>
										</bf:checkGroup>
									</bc:col>
									<bc:col col="1">
										<bc:label text="用户自定义授权" formControl="true"/>
									</bc:col>
									<bc:col col="8">
										<bf:select id="bizAuthCode" name="bizAuthCode" dataSource="custom" headEmpty="true"
											url="workflow/procDef/selectBizAuthHandler" value="${procAuth.bizAuthCode}" col="12">
											<bv:notEmpty/>
											<%-- <be:event type="select">bizAuthCodeChange(e.target.value);</be:event> --%>
										</bf:select>
									</bc:col>
								</bf:formGroup>
								<bf:formGroup>
									<bc:col col="6" colOffset="1">
										<bc:button id="btnProcAuthSave" type="primary"  text="保存" click="btnProcAuthSave();"></bc:button>
									</bc:col>
								</bf:formGroup>
							</bf:form>
						</bc:ibox>
					</bc:tab>
					<bc:tab id="tab3" title="表单配置">
						<bc:ibox id="ibox3" title="表单配置" col="12" >
							<bf:form id="formCfgForm" validation="true">
								<bc:row cls="m-b">
									<bf:formGroup>
										<bc:label text="节点名称：" col="1" formControl="true"/>
										<bf:select id="nodeKeySelect" name="nodeKeySelect" col="4" dataSource="custom"
											url="workflow/procDef/selectNodeKey?code=${procDef.procTemplateCode}" value="">
											<be:event type="select">nodeKeyChange(e.target.value);</be:event>
										</bf:select>
									</bf:formGroup>
								</bc:row>
							</bf:form>
							<bc:table id="formCfgTable" idField="id" url="${base}/workflow/procDef" queryUrl="/getNodeProcFormCfg" 
								multipleVisible="false" pagination="false">
								<bc:toolbar id="formCfgSave" text="保存" type="button"  handler="btnFormCfgSave"/>
								<bc:column field="name" title="字段名称" width="100" formatter="fmtCgfName"/>
								<bc:column field="visible" title="是否可见" width="100" formatter="fmtVisible"/>
								<bc:column field="writable" title="是否可编辑" width="100"  formatter="fmtWritable"/>
								<bc:column field="required" title="是否必填" width="100" formatter="fmtRequired"/>
							</bc:table>
						</bc:ibox>
					</bc:tab>
					<bc:tab id="tab4" title="变量映射">
						<bc:ibox id="ibox4" title="变量映射" col="12" >
							<bf:form id="queryForm" validation="true">
								<bc:row cls="m-b">
									<bf:formGroup>
										<bc:col col="12">
											<bc:label text="线条名称：" col="1" formControl="true"/>
											<bc:label text="" col="6" formControl="true" cls="nodeKeyFlow" style="text-align:left"/>
										</bc:col>
									</bf:formGroup>
								</bc:row>
							</bf:form>
							<bc:table id="varMapTable" idField="id" url="${base}/workflow/procDef" 
								queryUrl="selectProcVarMap?code=${procDef.code}&version=${procDef.procVersion}" 
								 multipleVisible="false" pagination="false">
								<bc:toolbar id="varMapSave" text="保存" type="button"  handler="btnVarMapSave"/>
								<bc:column field="nodeKey" title="线条名称" width="100"/>
								<bc:column field="varCode" title="变量名" width="100"/>
								<bc:column field="propCode" title="表单属性" width="100" formatter="fmtPropCode"/>
							</bc:table>
						</bc:ibox>
					</bc:tab>
				</bc:tabs>
			</bc:col>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<script src="${base}/static/workflow/js/jFlowDiagram/jFlowDiagram.js" type="text/javascript"></script>
<be:processEvent/>
<script type="text/javascript">
var procAuth;
var procFormCfgId;

$(document).ready(function(){
	
	/* 加载时，禁用节点授权 */
	$('#btnProcAuthSave').attr('disabled', true);
	$('#candidateCheck').attr('disabled', true);
	$('#roleCheck').attr('disabled', true);
	$('#bizAuthCheck').attr('disabled', true);
	disabledCandidate();
	disabledRole();
	disabledBizAuth();
	
	/* 表单配置 */
		$('#nodeKeySelect').val('').trigger('change');
	$('#formCfgTable_tb_formCfgSave').attr('disabled', true);
	
	/* 如果是业务表单，则不需要配置表单配置 */
	if('${procDef.formType}' == 'CUSTOM'){
		$('#tab3').hide();
		$('#tabContent .tab-list').eq(2).hide();
	}
});

/* 流程图 节点事件 */
function onNodeSelect(node){
	
	if(node.type == 'USERTASK')
	{
		$('.nodeKeyNode').text(node.key);
		$('.nodeKeyFlow').text('');
		$('.nodeName').html(node.name);
		getNodeProcFormCfg(node);
		
		/*初始化 【表单配置】的值 */
		$('#nodeKeySelect').val(node.key).trigger('change');
		initFormCfgTable(node.key);
		$('#formCfgTable_tb_formCfgSave').removeAttr('disabled');
	}
	else
	{
		if(node.type == 'FLOW'){
			$('.nodeKeyNode').text('');
			$('.nodeKeyFlow').text(node.key);
			$('.nodeName').html('');
		}
		else
		{
			$('.nodeKeyNode').text(node.key);
			$('.nodeKeyFlow').text('');
			$('.nodeName').html(node.name);
		}
		
		/* 节点授权，禁用 */
		$('#btnProcAuthSave').attr('disabled', true);
		$('#candidateCheck').attr('disabled', true);
		$('#roleCheck').attr('disabled', true);
		$('#bizAuthCheck').attr('disabled', true);
		disabledCandidate();
		disabledBizAuth();
		disabledRole();
		
		/* 表单配置 */
		$('#nodeKeySelect').val('').trigger('change');
		$("#formCfgTable").bootstrapTable('removeAll');
		$('#formCfgTable_tb_formCfgSave').attr('disabled', true);
	}
}

/* 获取节点对应的表单属性 */
function getNodeProcFormCfg(node){
	$.ajax({
		type: 'GET',
		url: '${base}/workflow/procDef/getNodeProcFormCfg',
		data: {code: '${procDef.code}', version: '${procDef.procVersion}', nodeKey: node.key},
		dataType: 'json',
		success: function(data){
			
			procFormCfgId = data.procFormCfg.id;
			setProcAuth(node, data.procFormCfg.formConfig);
		}
	});
}

/* 设置【节点授权】的值 */
function setProcAuth(node, formConfig){
	
	if(node.head == 'N')
	{
		/* 节点授权，启用 */
		$('#btnProcAuthSave').removeAttr('disabled');
		$('#candidateCheck').prop('checked', 'checked');
		$('#roleCheck').prop('checked', 'checked');
		$('#bizAuthCheck').prop('checked', 'checked');
		enableCandidate();
		enableRole();
		enableBizAuth();
		
		$('#authUserOther').next().css('display', 'none');
		$('#authRoleOther').next().css('display', 'none');
		
		
		/* 初始化【节点授权】中下拉框的值 */
		var config = $.parseJSON(formConfig);
		if(config)
		{
			var arrUser = new Array();
			var arrRole = new Array();
			var userIndex = 0;
			var roleIndex = 0;
			$.each(config, function(i, val){
				debugger;
//				if(val.inputType == 'TEXT' && val.propType == 'STRING')
				if(val.inputType == 'STAFF')
				{
					var obj = {
			 				id: val.code,
			 				text: val.name
			 		};
					arrUser[userIndex] = obj;
					userIndex++;
				}
				else if(val.inputType == 'UNIT')
				{
					var obj = {
			 				id: val.code,
			 				text: val.name
			 		};
					arrRole[roleIndex] = obj;
					roleIndex++
				}
			});
			
			$("#authUserForm").empty();
			$("#authUserForm").select2({data: arrUser, width: '100%', minimumResultsForSearch: Infinity});
			$("#authRoleForm").empty();
			$("#authRoleForm").select2({data: arrRole, minimumResultsForSearch: Infinity});
		}
	
		$.ajax({
			type: 'GET',
			url: '${base}/workflow/procDef/getNodeProcAuth',
			data: {code: '${procDef.code}', version: '${procDef.procVersion}', nodeKey: node.key},
			dataType: 'json',
			success: function(data){
				
				procAuth = data.procAuth;
				if(procAuth.userScope == null && procAuth.roleScope != null)
				{
					disabledCandidate();
					$('#roleScope').val(procAuth.roleScope).trigger('change');
					$('#authRoleForm').val(procAuth.authRoleProp).trigger('change');
					$('#authRoleOther').jLookup('setData', {authRole:procAuth.authRole,authRoleName:procAuth.authRoleName});
					roleScopeChange(procAuth.roleScope);
					
				}
				else if(procAuth.userScope != null && procAuth.roleScope == null)
				{
					disabledRole();
					$('#userScope').val(procAuth.userScope).trigger('change');
					$('#authUserForm').val(procAuth.authUser).select2();
					$('#authUserOther').jLookup('setData', {staffCode:procAuth.authUser,staffName:procAuth.authUserName});
					userScopeChange(procAuth.userScope);
				}
				else if(procAuth.userScope != null && procAuth.roleScope != null)
				{
					$('#userScope').val(procAuth.userScope).trigger('change');
					userScopeChange(procAuth.userScope);
					if(procAuth.userScope == 'FORM'){
						$('#authUserForm').val(procAuth.authUser).trigger('change');
					}else{
						$('#authUserOther').jLookup('setData', {staffCode:procAuth.authUser,staffName:procAuth.authUserName});
					}
					
					$('#roleScope').val(procAuth.roleScope).trigger('change');
					$('#authRoleForm').val(procAuth.authRoleProp).trigger('change');
					$('#authRoleOther').jLookup('setData', {authRole:procAuth.authRole,authRoleName:procAuth.authRoleName});
					roleScopeChange(procAuth.roleScope);
				}
				else if(procAuth.userScope == null && procAuth.roleScope == null && procAuth.bizAuthCode)
				{
					disabledRole();
					disabledCandidate();
				}
				else
				{
					$('.authUserForm').css('display', 'none');
					$('.authUserOther').css('display', 'inline');
					$('#authUserOther').jLookup('setData', {});
					$('#userScope option:first').prop("selected", 'selected').trigger('change');
					
					$('.authRoleForm').css('display', 'none');
					$('.authRoleOther').css('display', 'inline');
					$('#authRoleOther').jLookup('setData',{});
					$('#roleScope option:first').prop("selected", 'selected').trigger('change');
				}
				debugger;
				if(procAuth.bizAuthCode){
					$('#bizAuthCode').val(procAuth.bizAuthCode).trigger('change');
				}
				else if(procAuth.userScope == null && procAuth.roleScope == null && (!procAuth.bizAuthCode))
				{
					 enableBizAuth();
					 $('#bizAuthCode option:first').prop("selected", 'selected').trigger('change');
				}
				else
				{
					disabledBizAuth();
				}
			}
		});
	}
	else
	{
		/* 节点授权，禁用 */
		$('#btnProcAuthSave').attr('disabled', true);
		$('#candidateCheck').attr('disabled', true);
		$('#roleCheck').attr('disabled', true);
		$('#bizAuthCheck').attr('disabled', true);
		disabledCandidate();
		disabledBizAuth();
		disabledRole();
	}
}

function disabledCandidate(){
	$('#candidateCheck').prop('checked', false);
	$('.authUserForm').css('display', 'none');
	$('.authUserOther').css('display', 'inline');
	$('#authUserOther').jLookup('setData', {});
	$('#userScope option:first').prop("selected", 'selected').trigger('change');
	
	$('#userScope').attr('disabled', true);
	$('#authUserForm').attr('disabled', true);
	$('#authUserOther').jLookup('disable');
}

function enableCandidate(){
	$('#candidateCheck').removeAttr('disabled');
	$('#userScope').removeAttr('disabled');
	$('#authUserForm').removeAttr('disabled');
	$('#authUserOther').jLookup('enable');
}

function disabledBizAuth(){
	$('#bizAuthCode option:first').prop("selected", 'selected').trigger('change');
	$('#bizAuthCheck').prop('checked', false);
	$('#procAuthForm').data('formValidation').resetField('bizAuthCode');
	$('#bizAuthCode').attr('disabled', true);
}

function enableBizAuth(){
	$('#bizAuthCheck').removeAttr('disabled');
	$('#bizAuthCode').removeAttr('disabled');
}

function disabledRole(){
	$('#roleCheck').prop('checked', false);
	$('.authRoleForm').css('display', 'none');
	$('.authRoleOther').css('display', 'inline');
	$('#authRoleOther').jLookup('setData',{});
	$('#roleScope option:first').prop("selected", 'selected').trigger('change');
	
	$('#roleScope').attr('disabled', true);
	$('#authRoleForm').attr('disabled', true);
	$('#authRoleOther').jLookup('disable');
}

function enableRole(){
	$('#roleCheck').removeAttr('disabled');
	$('#roleScope').removeAttr('disabled');
	$('#authRoleForm').removeAttr('disabled');
	$('#authRoleOther').jLookup('enable');
}

/* 勾选候选人 */
function candidateCheckChange(){
	if($('#candidateCheck').prop('checked'))
	{
		$('#userScope').removeAttr('disabled');
		$('#authUserForm').removeAttr('disabled');
		$('#authUserOther').jLookup('enable');
		$('#btnProcAuthSave').removeAttr('disabled');
	}
	else
	{
		if(!$('#roleCheck').prop('checked') && !$('#bizAuthCheck').prop('checked'))
		{
			$('#userScope').removeAttr('disabled');
			$('#authUserForm').removeAttr('disabled');
			$('#authUserOther').jLookup('enable');
			$('#btnProcAuthSave').removeAttr('disabled');
			$('#candidateCheck').prop('checked', true);
		}
		else
		{
			$('#procAuthForm').data('formValidation').resetField('authUser');
			$('#procAuthForm').data('formValidation').resetField('authUserText');
			$('#userScope').attr('disabled', true);
			$('#authUserForm').attr('disabled', true);
			$('#authUserOther').jLookup('disable');
		}
	}
}

/* 勾选岗位 */
function roleCheckChange(){
	if($('#roleCheck').prop('checked'))
	{
		$('#roleScope').removeAttr('disabled');
		$('#authRoleForm').removeAttr('disabled');
		$('#authRoleOther').jLookup('enable');
		$('#btnProcAuthSave').removeAttr('disabled');
	}
	else
	{
		if(!$('#candidateCheck').prop('checked') && !$('#bizAuthCheck').prop('checked'))
		{
			$('#roleScope').removeAttr('disabled');
			$('#authRoleForm').removeAttr('disabled');
			$('#authRoleOther').jLookup('enable');
			$('#btnProcAuthSave').removeAttr('disabled');
			$('#roleCheck').prop('checked',true);
		}
		else
		{
			$('#procAuthForm').data('formValidation').resetField('authRoleProp');
			$('#procAuthForm').data('formValidation').resetField('authRoleText');
			$('#roleScope').attr('disabled', true);
			$('#authRoleForm').attr('disabled', true);
			$('#authRoleOther').jLookup('disable');
		}
	}
}

function bizAuthCheckChange(){
	if($('#bizAuthCheck').prop('checked'))
	{
		$('#bizAuthCode').removeAttr('disabled');
	}
	else
	{
		if(!$('#roleCheck').prop('checked') && !$('#candidateCheck').prop('checked'))
		{
			$('#bizAuthCode').removeAttr('disabled');
			$('#bizAuthCheck').prop('checked', true);
		}
		else
		{
			$('#bizAuthCode').attr('disabled', true);
		}
		
	}
}


/* 候选人范围改变 */
function userScopeChange(val){
	if(val == 'FORM')
	{
		$('.authUserForm').css('display', 'inline');
		$('.authUserOther').css('display', 'none');
	}
	else
	{
		$('.authUserForm').css('display', 'none');
		$('.authUserOther').css('display', 'inline');
	}
}

/* 候选人范围改变 */
function roleScopeChange(val){
	if(val == 'FORM')
	{
		$('.authRoleOther').css('margin-left', '15px');
		$('.authRoleForm').css('display', 'inline');
	}
	else
	{
		$('.authRoleOther').css('margin-left', '0');
		$('.authRoleForm').css('display', 'none');
		$('.authRoleOther').css('display', 'inline');
	}
}

function onSelectRole(){
	$('#procAuthForm').data('formValidation').revalidateField('authRoleText');
}
function onSelectUser(){
	$('#procAuthForm').data('formValidation').revalidateField('authUserText');
}

/* 保存节点授权 */
function btnProcAuthSave(){
	
	var authUser = '';
	var authUserName = '';
	var authRole = '';
	var authRoleName = '';
	var authRoleProp = '';
	$('#procAuthForm').data('formValidation').resetForm();
	$('#procAuthForm').data('formValidation').validate();
	var isValid = $('#procAuthForm').data('formValidation').isValid();
	if(isValid)
	{
		if($('#candidateCheck').prop('checked')) //候选人勾选时保存
		{
			/* 候选人验证 */
			var userScopeVal = $('#userScope').val();
			var authUserFormVal = $('#authUserForm').val();
			var authUserOtherVal = $('#authUserOther').jLookup('getValue');
			
			if(userScopeVal == 'FORM')
			{
				authUser = authUserFormVal;
			}
			else
			{
				authUser = authUserOtherVal;
				authUserName = $('#authUserOther').jLookup('getText');
			}
		}
		
		if($('#roleCheck').prop('checked')) //岗位勾选时保存
		{
			/* 岗位验证 */
			var roleScopeVal = $('#roleScope').val();
			var authRoleFormVal = $('#authRoleForm').val();
			var authRoleOtherVal = $('#authRoleOther').jLookup('getValue');
			
			authRoleProp = authRoleFormVal
			authRole = authRoleOtherVal;
			authRoleName = $('#authRoleOther').jLookup('getText');
		}
		
		if($('#bizAuthCheck').prop('checked')) //用户自定义授权勾选时保存
		{
			/* 岗位验证 */
			var bizAuthCode = $('#bizAuthCode').val();
			
		}
		
		var obj = {
				id: procAuth.id,
				procId: procAuth.procId,
				procCode: procAuth.procCode,
				templateCode: procAuth.templateCode,
				nodeKey: procAuth.nodeKey,
				userScope: userScopeVal,
				authUser: authUser,
				authUserName: authUserName,
				roleScope: roleScopeVal,
				authRoleProp: authRoleProp,
				authRole: authRole,
				authRoleName: authRoleName,
				bizAuthCode: bizAuthCode
		}
		
		$.mask.post('${base}/workflow/procDef/updateNodeProcAuth', obj, function(data){
			if(data.success)
			{
				$.messager.alert('提示', '保存成功。', 'info');
			}
			else
			{
				$.messager.alert('错误', data.msg, 'error');
			}
		});
	}
}

function nodeKeyChange(val){
	$('#formCfgTable_tb_formCfgSave').removeAttr('disabled');
	initFormCfgTable(val);
}

/* 初始化表单属性 */
function initFormCfgTable(key){
	
	$("#formCfgTable").bootstrapTable('refresh', {
		url:'${base}/workflow/procDef/getNodeProcFormCfg',
		query: {code: '${procDef.code}', version: '${procDef.procVersion}', nodeKey: key}
	});
}
function fmtId(value, row, index){
	return 
}

function fmtCgfName(value, row, index){
	return row.name +'(' + row.code + ')';
}

function fmtVisible(value, row, index, e){
	if(row.visible == 'Y'){
		checked = 'checked="checked"';
	}else{
		checked = '';
	}
	
	if(row.head == 'Y') //首任务节点
	{
		if(row.primaryKey == 'Y')//主键
		{
			return '<div style="width:100%; hight: 100%; text-align:center"><input type="checkbox" ' + checked + ' id="visible' + index + '" value="' + value + '" disabled="disabled"/></div>';
		}
	}
	return '<div style="width:100%; hight: 100%; text-align:center"><input type="checkbox" ' + checked + ' id="visible' + index + '" name="visible" value="' + row.head + '" onClick="checkboxClick(id,name,value)"/></div>';;
}
function fmtWritable(value, row, index){
	if(row.writable == 'Y'){
		checked = 'checked="checked"';
	}else{
		checked = '';
	}
	
	if(row.primaryKey == 'Y')//主键
	{
		return '<div style="width:100%; hight: 100%; text-align:center"><input type="checkbox" ' + checked + ' id="writable' + index + '" value="' + value + '" disabled="disabled"/></div>';
	}
	return '<div style="width:100%; hight: 100%; text-align:center"><input type="checkbox" ' + checked + ' id="writable' + index + '" name="writable" value="' + row.head + '" onClick="checkboxClick(id,name,value)"/></div>';
	
}
function fmtRequired(value, row, index){
	if(row.required == 'Y'){
		checked = 'checked="checked"';
	}else{
		checked = '';
	}
	
	if(row.primaryKey != 'Y')//主键
	{
		return '<div style="width:100%; hight: 100%; text-align:center"><input type="checkbox" ' + checked + ' id="required' + index + '" name="required" value="' + row.head + '" onClick="checkboxClick(id,name,value)"/></div>';
	}
	return '<div style="width:100%; hight: 100%; text-align:center"><input type="checkbox" ' + checked + ' id="required' + index + '" value="' + value + '" disabled="disabled"/></div>';;
	
}
/* 表单配置验证 */
function checkboxClick(id,name,value){
	
	var index = id.substring(id.length - 1);
	if(name == 'visible') //是否可见
	{
		if(!$('#' + id).prop('checked')) //选中时
		{ 
			$('#writable' + index).prop('checked', false);
			$('#required' + index).prop('checked', false);
		}
	}
	else if(name == 'writable') //是否可编辑
	{
		if($('#' + id).prop('checked')) //选中时
		{ 
			$('#visible' + index).prop('checked', 'checked');
		}
		else
		{
			$('#required' + index).prop('checked', false);
		}
	}
	else  //是否必填
	{
		if($('#' + id).prop('checked')) //选中时
		{ 
			$('#visible' + index).prop('checked', 'checked');
			$('#writable' + index).prop('checked', 'checked');
		}
	}
}

/* 保存表单属性配置 */
function btnFormCfgSave(){
	
	var data = $("#formCfgTable").bootstrapTable('getData');
	
	$.each(data, function(i, v){
		if($('#visible' + i).prop('checked'))
		{
			v.visible = "Y";
		}
		else
		{
			v.visible = "N";
		}
		
		if($('#writable' + i).prop('checked'))
		{
			v.writable = "Y";
		}
		else
		{
			v.writable = "N";
		}
		
		if($('#required' + i).prop('checked'))
		{
			v.required = "Y";
		}
		else
		{
			v.required = "N";
		}
	});
	
	var obj = {
			id: procFormCfgId,
			formConfig: JSON.stringify(data)
	}
	
	$.mask.post('${base}/workflow/procDef/updateProcFormCfg', obj, function(data){
		if(data.success)
		{
			$.messager.alert('提示', '保存成功。', 'info');
		}
		else
		{
			$.messager.alert('错误', data.msg, 'error');
		}
	});
}

/* 变量映射 */
function fmtPropCode(value, row, index){
	
	$.ajax({
		type: 'GET',
		url: '${base}/workflow/formProp/queryFormProp',
		data: {formCode: '${procDef.formCode}'},
		dataType: 'json',
		success: function(data){
			$.each(data, function(i,v){
				if(value == v.value){
					$('#propCode' + index).append('<option value="' + v.value + '" selected="selected">' + v.name + '</option>');
				}else{
					$('#propCode' + index).append('<option value="' + v.value + '">' + v.name + '</option>');
				}
			});
		}
	});
	
	return '<div style="width:100%; hight: 100%; text-align:center"><select id="propCode' + index + '" name="propCode' + index + '" value="' + row.propCode + '" data-fv-notempty="true" data-fv-field="authUser" style="width:100px; height:25px;"></select></div>';
}

/* 保存变量映射 */
function btnVarMapSave(){
	var data = $("#varMapTable").bootstrapTable('getData');
	
	$.each(data, function(i, v){
		var val = $('#propCode' + i).val();
		v.propCode = val;
	});
	var obj = {
			varMapJson: JSON.stringify(data)
	}
	$.mask.post('${base}/workflow/procDef/updateProcVarMap',obj , function(data){
		if(data.success)
		{
			$.messager.alert('提示', '保存成功。', 'info');
		}
		else
		{
			$.messager.alert('错误', data.msg, 'error');
		}
	});
}
</script>
</body>
</html>
