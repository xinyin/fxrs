<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<link rel="stylesheet" type="text/css" href="${base}/static/workflow/css/jFlowForm/jFlowForm.css">
<title>流程提交</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox id="iboxform" title="" col="12" collapsible="false">
			<bf:form id="workflowform" method="post" validation="true">
				<iframe style="width: 100%; border: 0;"  id="myFrame" name="myFrame" src="${clientAdd}/workflow/client/bizEdit?token=${token}" col="12" ></iframe>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
							<bc:button type="primary" id="button"  text="保存" icon="save">
								<be:event>save();</be:event>
							</bc:button>
							<bc:button type="primary" id="button"  text="保存并提交" icon="save">
								<be:event>saveAndStart();</be:event>
							</bc:button>
							<bc:button  text="返回">
							<be:event>
								window.location = '${base}/workflow/process/draftList?passToken=${passToken}';
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
var procDefCode = '${procDefCode}';
var oldData = '${oldData}';
var procInstId = '${procInstId}';
var status = '${status}';

//表单保存
function save(){
    var myFrame=document.getElementById('myFrame');
    var data = {type : 'workflow', opt : 'save'};
	myFrame.contentWindow.postMessage(data,'${clientAdd}/workflow/client/bizEdit');
}

function saveAndStart(){
    var myFrame=document.getElementById('myFrame');
    var data = {type : 'workflow', opt : 'saveAndStart'};
	myFrame.contentWindow.postMessage(data,'${clientAdd}/workflow/client/bizEdit');
}


window.addEventListener('message',function(e){
	/* 判断是不是监听的工作流相关数据 */
	if('workflow' == e.data.type){
		if(!e.data.formData){
			$.messager.alert('提示', '业务数据不能为空，请检查。', 'info');
			return;
		}
	    var formData = $.helper.base64Encode(e.data.formData);
		if(e.data.opt == 'save'){
			$.mask.post('${base}/workflow/process/save', 
				{
					procDefCode: procDefCode, 
					oldData: oldData, 
					newData: formData,
					procInstId: procInstId,
					status : status,
					passToken: '${passToken}'
				},
				function(data){
					if(data.success)
					{
						$.messager.alert('提示', '保存成功。', 'info', function(){
							window.location = '${base}/workflow/process/draftList?passToken=${passToken}';
						});
					}
					else
					{
						$.messager.alert('错误', data.msg, 'error');
					}
				}
			);
		}
		else
		{
			
			var obj = {
					procDefCode: procDefCode, 
					oldData: oldData, 
					newData: formData,
					procInstId: procInstId,
					passToken: '${passToken}'
			}
			$.mask.post('${base}/workflow/process/saveAndStart', obj, function(data){
				if(data.success)
				{
					$.messager.alert('提示', '保存并提交成功。', 'info', function(){
						window.location = '${base}/workflow/process/runingList?passToken=${passToken}';
					});
				}
				else
				{
					$.messager.alert('错误', data.msg, 'error');
				}
			});
		}
	}
	else if('workflowFrameH' == e.data.type){
		var myFrame=document.getElementById('myFrame');
		myFrame.style.height = e.data.height + 'px'; 
	}
		
},false);
</script>
</body>
</html>