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
.tis{
}
.is{
}
</style>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
			<bf:hidden name="flag" id="flag" value="${flag}"></bf:hidden>
			<bf:hidden name="isBUM" id="isBUM" value="${isBUM}"></bf:hidden>
			<bf:hidden name="toDUM" id="toDUM" value="${toDUM}"></bf:hidden>
			<bf:hidden name="toBUM" id="toBUM" value="${toBUM}"></bf:hidden>
			<bf:hidden name="toHR" id="toHR" value="${toHR}"></bf:hidden>
				<div class="ys_tlist">
					<table class="table table-hover" width="95%">
						<tbody class="dateboxs">
							<tr>
								<td>
									<div>
										<bc:label text="请假开始时间：" col="4" formControl="true"/>
										<bf:datebox id="startDate" name="startDate" col="7" format="date" value="${startDate }">
											<bv:notEmpty/>
										</bf:datebox>
									</div>
									<span class="hotel-error"></span>
								</td>
								<td>
									<div>
										<bc:label text="请假结束时间：" col="4" formControl="true"/>
										<bf:datebox id="endDate" name="endDate" col="7" format="date" value="${endDate }">
											<bv:notEmpty/>
										</bf:datebox>
									</div>
									<span class="hotel-error"></span>
								</td>
								<td><bc:label text="时长（小时）："  formControl="true"/><input id="hours" name= "hours" class="is" value='${hours }' style="width: 50px;height:34px;border-radius: 6px"/><br/><span class="hotel-error"></span></td>
							</tr>
							<tr>
								<td><bc:label text="移动电话：" col="4" formControl="true"/><input id= "mobile" name= "mobile" class="is" value='${mobile }' style="width: 200px;height:34px;border-radius: 6px"/><br/><span class="hotel-error"></span></td>
							</tr>
							<tr>
								<td ><bc:label text="请假事由：" col="4" formControl="true" />
									<bf:text id="remark" name="remark" value="${remark }" multiple="true" col="8" rows="4"/>
									<%-- <textarea id="remark" class="is" name="remark" rows="3" style="width: 300px;height:150px;border-radius: 6px;text-align: left;">
										${remark }
									</textarea><br/>--%><span class="hotel-error"></span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
			</bf:form>
			<div class="new_bj"></div>
				<bf:formGroup type="success" hr="dashed" hrp="bottom">
					<div class="mail-body">
						<bc:upload id="upload" entityType="Attendance" entityId="${uninId}" uploadEnable="true" removeEnable="true"/>
						<td>&nbsp;</td>
					</div>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="success" outline="true" text="提交" id="saveAndCommit">
							<be:event>
								saveAndCommitBill();
							</be:event>
						</bc:button>
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

function associate(billUnionId){
	$('#upload').jUpload('associate',billUnionId, function(result){
		if(result.count > 0){
			$.messager.alert('提示', "保存成功", 'info', function(){
				window.location = '${base}/ex/bill/mplist';
			});
		}
	});
}

function associateCommit(billUnionId){
	$('#upload').jUpload('associate',billUnionId, function(result){                          
	});
}

$("#startDate").datetimepicker({
	format:"yyyy-mm-dd hh:ii:00",
	language:"zh-CN",
	minuteStep:30,
	autoclose:2,
	todayBtn:"linked",
	minView: "hour"
});

$("#endDate").datetimepicker({
	format:"yyyy-mm-dd hh:ii:00",
	language:"zh-CN",
	minuteStep:30,
	autoclose:2,
	todayBtn:"linked",
	minView: "hour"
});


/* function getFormData(){
	$('#form1').data('formValidation').validate();
	var isValid = $('#form1').data('formValidation').isValid();
	if(isValid)
	{
		var objData = {};
	    
	    for (var entry of $('#form1').serializeArray()){
	        objData[entry['name']] = entry['value'];
	    }
		return objData;
	}
	else
	{
		return false;
	}
} */
$(document).ready(function(){
	var flag='${flag}';
	if(flag==1){
		$("#saveAndCommit").hide();
		$("#cheak").hide();
		$("#Vacation").hide();
	}
	$("#Breastfeedingbt").hide();
	/* $("#saveAndCommit").attr("disabled","disabled");
	$("#save1").attr("disabled","disabled"); */
});


function saveAndCommitBill(){
	var num=0;
	$(".is").each(function(){
		var thisval=$(this).val();
		var thisid=this.id;
		if(thisval == "" || thisval == null || thisval == undefined){ 
			num++;
			if(thisid.indexOf("Date")>=0){
				$(this).parent().parent().next('span').html('该项不能为空!');
			}else{
				$(this).next().next('span').html('该项不能为空!');
			}
		}
	});	
	$(".tis").each(function(){
		var a=$(this).find("option:selected").val();
		if(a=='N/A'){
			num++;
			$(this).next('span').html('该项不能为空!');
		}
	});
	if(num==0){
		//countAmt();
		$('#form1').data('formValidation').validate();
		var isValid = $('#form1').data('formValidation').isValid();
		if(isValid)
		{
			var data = $('#form1').serializeAll();
			/* var sdata = parseRuleJson(data);
			$.ajax({
		        type: 'post',
		      	//url: '${base}/common/rule/ruleById/fa020b16-5e71-11e8-8f55-fa163ea4799c',//248
		        url: '${base}/common/rule/ruleById/b3bdcafa-54ca-11e8-ae5a-8cec4b488362',//246
		        //url: '${base}/common/rule/ruleById/8880465d-5e6a-11e8-801c-8cec4b4b2170',
		        dataType: 'json', //表示返回值的数据类型
		        contentType: 'application/json;charset=UTF-8', //内容类型
		        traditional: true, //使json格式的字符串不会被转码
		        data: JSON.stringify(sdata),
		        success: function(res){
		        	if(res.success){  */
			        	$.mask.post('${base}/pub/staff/saveAdj', data, function(data){
							$("#toHR").attr("value",data.toHR);
							$("#toDUM").attr("value",data.toDUM);
							$("#toBUM").attr("value",data.toBUM);
							$("#isBUM").attr("value",data.isBUM);
							if(data.success)
							{
								associateCommit(data.UnionId);
								saveAndStart();
							}
							else
							{
								$.messager.alert('错误', data.msg, 'error');
							}
						});
		        	/*  }else{
		        		$.messager.alert('错误', res.msg, 'error');
		        	}
		        }
		    });  */
		}
	}
}

function parseRuleJson(data){
	var json={
			budgetAmt:$("#billAmt").val(),
			flag:$("#flag").val()
	};
	return json;
} 

function saveAndStart(){
	$('#form1').data('formValidation').validate();
	var isValid = $('#form1').data('formValidation').isValid();
	if(isValid)
	{
		var objData = {};
	    
	    for (var entry of $('#form1').serializeArray()){
	        objData[entry['name']] = entry['value'];
	    }
	    var myDate = new Date();
	    objData.id =  myDate.getTime();
		data = JSON.stringify(objData);
		var formData = $.helper.base64Encode(data);
		var obj = {
				procDefCode: 'YJZR_LeaveApplyProcess001', 
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

$("#hours").click(function(){
	changeVacation();
});

function changeVacation(){
	var startDate = $("[name='startDate']").val();
	var endDate = $("[name='endDate']").val();
	var adjType = $("#adjType").val();
	if(adjType=='Breastfeeding'){
		$("#Breastfeedingbt").show();
		$("#Vacation").hide();
	}
	var frontDate='${frontDate }';
	if(startDate!=''){
	$.ajax({  

        type:"POST", 

        url:"${base}/pub/staff/changeVacation/"+startDate+"/"+endDate+"/" + adjType+"/"+frontDate, 

        dataType:"html", 

        success:function(result){  
      	 var ajaxobj=eval("("+result+")");
      	 if(adjType=='Marriage'){
      		$("[name='endDate']").val(ajaxobj.endDate);
      		$("[name='endDate']").attr("disabled",ajaxobj.disabled);
      	 }else{
      		$("[name='endDate']").attr("disabled",false); 
      	 }
   	     $("#Vacation").show();
   	     if(ajaxobj.hour!=0){
   	     	$("#hours").attr("disabled",ajaxobj.disabled);
	   	    $("#hours").val(ajaxobj.hour);
   	     }else{
   	    	$("#hours").attr("disabled",false);
   	     }
       }
 
	});
	}else{
		$.messager.toastr('提示', "请填写开始日期！", 'success');
	}
}

</script>
</body>
</html>
