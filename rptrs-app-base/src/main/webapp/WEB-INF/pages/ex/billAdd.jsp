<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>Bill</title>
<x:jsLib/>
<style type="text/css">
input{
		border:0px;
		outline:none;
		width:100%;
}
.hotel-error{
	color:red;
}

</style>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="arrayAno" id="arrayAno" value=""></bf:hidden>
				<bf:formGroup type="success" hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="单据标题(必填)" col="4" formControl="true"/>
						<bf:text id="billTitle" name="billTitle" col="8" value="" placeholder="此项为必填项">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="单据概要信息(必填)" col="4" formControl="true"/>
						<bf:text id="billDesc" name="billDesc" col="8" value="" placeholder="此项为必填项">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success" hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="单据日期(必填)" col="4" formControl="true"/>
						<bf:datebox id="billDate" name="billDate" col="8" format="date" value="">
						</bf:datebox>
					</bc:col>
					<bc:col col="6">
						<bc:label text="是否预提" col="4" formControl="true"/>
						<bf:select id="isCD" name="isCD" col="8"  value="" >
							<bf:option text="是" value="是"/>
							<bf:option text="否" value="否"/>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success" hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="项目编号" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8">
						</bf:text>
						<%-- <bf:select id="pid" name="pid" col="8" dataSource="custom" url="pm/project/queryAll">
							<be:event type="select">
								queryEntity();
							</be:event>
						</bf:select> --%>
					</bc:col>
					<bc:col col="6">
						<bc:label text="项目实体" col="4" formControl="true"/>
						<bf:select id="entityId" name="entityId" col="8" dataSource="custom" value="">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success" hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="机会号" col="4" formControl="true"/>
						<bf:text id="oppId" name="oppId" col="8" value="">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="成本中心代码(必填)" col="4" formControl="true"/>
						<bf:text id="ccc" name="ccc" col="8" value="${ccc}" placeholder="此项为必填项">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup type="success" hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否增值税专票" col="4" formControl="true"/>
						<bf:select id="isSV" name="isSV" col="8" dataSource="custom" url="sys/dictItem/getDictItems/SYS-IsNo" value="" >
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="费用总金额" col="4" formControl="true"/>
						<bf:text id="billAmt" name="billAmt" col="8">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				
			</bf:form>
			<div class="new_bj"></div>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<!-- <bc:button type="success" outline="true" text="保存" icon="save" id="save1">
							<be:event>
								saveBill();
							</be:event>
						</bc:button> -->
						<bc:button type="info" outline="true" text="保存并提交" id="saveAndCommit">
							<be:event>
								saveAndStart();
							</be:event>
						</bc:button>
						<%-- <bc:button type="info" outline="true" text="返回">
							<be:event>
								window.location = '${base}/ex/bill/list';
							</be:event>
						</bc:button> --%>
					</div>
				</bf:formGroup>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">


function saveAndCommitBill(){
	var num=0;
		$('#form1').data('formValidation').validate();
		var isValid = $('#form1').data('formValidation').isValid();
		if(isValid)
		{
			var data = $('#form1').serializeAll();
			var sdata = parseRuleJson(data);
			console.log(JSON.stringify(sdata));
			$.ajax({
		        type: 'post',
		        url: '${base}/common/rule/ruleById/6C7761FF4302C35AE050A8C0F4011798',
		        dataType: 'json', //表示返回值的数据类型
		        contentType: 'application/json;charset=UTF-8', //内容类型
		        traditional: true, //使json格式的字符串不会被转码
		        data: JSON.stringify(sdata),
		        success: function(res){
		        	if(res.success){
		        		$.messager.alert('成功', res.msg, 'success');
		        	}else{
		        		$.messager.alert('错误', res.msg, 'error');
		        	}
		        }
		    });
		}
}

function parseRuleJson(data){
	var json={
			ccc:$("#ccc").val(),
			billAmt:$("#billAmt").val(),
			isCD:$("#isCD").val(),
			billDate:$("input[name='billDate']").val(),
			billDesc:$("#billDesc").val(),
			billTitle:$("#billTitle").val()
	};
	return json;
}

function saveAndStart(){
	var data = $('#form1').serializeAll();
	var objData = parseRuleJson(data);
    var myDate = new Date();
    objData.id =  myDate.getTime();
	data = JSON.stringify(objData);
	console.log(data);
	var formData = $.helper.base64Encode(data);
	var obj = {
			procDefCode: 'YJZR_ManpowerApplyProcess001', 
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

</script>
</body>
</html>
