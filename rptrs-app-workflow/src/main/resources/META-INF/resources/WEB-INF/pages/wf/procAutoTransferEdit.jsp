<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>流程自动转办定义</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="procAutoTransferBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="id" name="id" value="${procAutoTransfer.id}"/>
				<bf:hidden id="staffName" name="staffName" value="${procAutoTransfer.staffName}"/>
				<bf:hidden id="transferStaffName" name="transferStaffName" value="${procAutoTransfer.transferStaffName}"/>
				<bf:formGroup>
					<bc:col col="8">
						<bc:label text="委托人" col="4" formControl="true"/>
						<bf:staffLookup id="staffCode" name="staffCode" col="8"
										value="${procAutoTransfer.staffCode}" text="${procAutoTransfer.staffName}" propText="staffName" propValue="staffCode"
										callback="result"  onSelect="staffChange" mult="false" placeholder="委托人">
							<bv:notEmpty/>
							<bv:callback callback="isTransfered" />
						</bf:staffLookup>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="8">
						<bc:label text="被委托人" col="4" formControl="true"/>
						<bf:staffLookup id="transferStaffCode" name="transferStaffCode" col="8"
							value="${procAutoTransfer.transferStaffCode}" text="${procAutoTransfer.transferStaffName}" propText="staffName" propValue="staffCode"
							callback="result"  onSelect="staffChange" mult="false" placeholder="被委托人">
							<bv:notEmpty/>
							<bv:callback callback="isTransfered" />
						</bf:staffLookup>
					</bc:col>
				</bf:formGroup>
				
				<bf:formGroup>
					<bc:col col="8">
						<bc:label text="开始时间" col="4" formControl="true"/>
						<bf:datebox id="startTime" name="startTime" col="8" format="date" value="${procAutoTransfer.startTimeString}">
							<bv:callback callback="isTransfered" />
							<bv:notEmpty/>
						</bf:datebox>
					</bc:col>
				</bf:formGroup>
				
				<bf:formGroup>
					<bc:col col="8">
						<bc:label text="结束时间" col="4" formControl="true"/>
						<bf:datebox id="endTime" name="endTime" col="8" format="date" value="${procAutoTransfer.endTimeString}">
							<bv:callback callback="isTransfered" />
							<bv:notEmpty/>
						</bf:datebox>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="8">
						<bc:label text="联系方式" col="4" formControl="true"/>
						<bf:datebox id="mobile" name="mobile" col="8" disabled="true"  value="${procAutoTransfer.mobile}">
							<bv:callback callback="isTransfered" />
							<bv:notEmpty/>
						</bf:datebox>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="8">
						<bc:label text="备注" col="4" formControl="true"/>
						<bf:text id="remark" name="remark" col="8"  multiple="true" rows="5" >${procAutoTransfer.remark}</bf:text>
					</bc:col>
				</bf:formGroup>
				
				<bf:formGroup>
					<div align="center">
						<bc:button type="primary" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/wf/procAutoTransfer/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/wf/procAutoTransfer/list';
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
						<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/wf/procAutoTransfer/list';
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
	$('#startTime').on('changeDate', function(ev){
		$('#editForm').data('formValidation').revalidateField('endTime');
		$('#editForm').data('formValidation').revalidateField('transferStaffCodeText');
	});
	$('#endTime').on('changeDate', function(ev){
		$('#editForm').data('formValidation').revalidateField('startTime');
		$('#editForm').data('formValidation').revalidateField('transferStaffCodeText');
	});
});
function staffChange(data){
	$('#transferStaffName').val(data[0]['staffName']);
	$('#mobile').val(data[0]['mobile']);
	$('#editForm').data('formValidation').revalidateField('transferStaffCodeText');
}

function isTransfered(value, validator, $field) {
	var staffCode = $('#transferStaffCode').jStaffLookup('getValue');
	var startTime = $('#startTime [name="startTime"]').val();
	var endTime = $('#endTime [name="endTime"]').val();
	var id = $('#id').val();
	
	var flag = true;
	
	if(startTime && endTime && !staffCode)
	{
		var start= new Date(startTime.replace("-", "/").replace("-", "/")); 
		var end = new Date(endTime.replace("-", "/").replace("-", "/"));  
		if(start > end)
		{
			return {valid : false, message: '结束时间不能晚于开始时间。'};
		}
		else
		{
			return {valid : true, message: '结束时间不能晚于开始时间。'};
		}
	}
	else if(staffCode && startTime && endTime){
		var start= new Date(startTime.replace("-", "/").replace("-", "/")); 
		var end = new Date(endTime.replace("-", "/").replace("-", "/"));  
		if(start > end)
		{
			return {valid : false, message: '结束时间不能晚于开始时间。'};
		}
		else
		{
			var transfer = {'id' : id, 'staffCode' : staffCode, 'startTime' : startTime, 'endTime' : endTime};
		 	$.ajax({  
				url: "${base}/wf/procAutoTransfer/isTransfered",  
			  	data: transfer, 
			  	async: false,
			  	type: 'post',
			  	success: function(data) { 
				  	flag = data.valid;
			  	}
			 });
	 	}
	}
	return {valid : flag, message: '您在该时间段内已经存在转办用户，请检查'};
}
</script>
</body>
</html>
