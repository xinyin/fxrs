<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>流程模板</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
				
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="请选择流程图资源(*.bpmn)&nbsp;" col="4" formControl="true"/>
						<div class="col-sm-6">  
						    <input type="file" class="form-control" id="uploadFile" name="uploadFile"  
						           style="display: none;" onchange="change();"></input>
						    <input type="text" class="form-control" id="uploadFile_tmp" name="uploadFile_tmp"  
						           readonly="readonly" onclick="uploadFile.click(); "><bv:notEmpty/></input>
						</div>  
						<div class="col-sm-2">  
						    <button type="button" class="btn btn-primary" id="select_file"  
						            onclick="uploadFile.click();">浏览  
						    </button>  
						</div> 
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="备注" col="4" formControl="true"/>
						<bf:text id="remark" name="remark" col="8" rows="3" multiple="true">${procTemplate.remark}</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button type="primary"  text="保存" icon="save">
							<be:event>
								/*组装数据*/
								if(!$('#uploadFile').val()){
									$.messager.alert('提示',"请选择要发布的模板","warning");
									return;
								}
								else
								{
									//显示遮罩层
									$.mask.show();
									var formData = new FormData();
									formData.append('uploadFile', $('#uploadFile')[0].files[0]);
									formData.append('remark', $('#remark').val());
									/*发送请求*/
									$.ajax({
									  url: "${base}/workflow/procTemplate/deploy",
									  type: "POST",
									  data: formData,
									  contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
									  processData: false, //必须false才会自动加上正确的Content-Type
									  success: function (data) {
						  				//移除遮罩层
										$.mask.remove();
									  	if(data.success)
										{
									  		$.messager.alert('提示', data.msg, 'info', function(){
									  				
													window.location = '${base}/workflow/procTemplate/list';
												});
										}
										else
										{
											$.messager.alert('错误', data.msg, 'error');
										}
									  },  
								     error : function(data) {  
								     	//移除遮罩层
										$.mask.remove();
								     	$.messager.alert('错误', data.msg, 'error');
								     }
									 });
								}
							</be:event>
						</bc:button>
						<bc:button  text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/workflow/procTemplate/list';
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
function change(){  
   var fileName  = document.getElementById("uploadFile_tmp").value=document.getElementById("uploadFile").value;
 
   if(!(fileName.toLowerCase().endsWith('.bpmn') || fileName.toLowerCase().endsWith('.bpmn20.xml'))){ 
	   $.messager.alert('提示', '文件类型必须为bpmn或者bpmn20.xml。', 'info');
	   $('#uploadFile_tmp').val("");  
	   $('#uploadFile').val("");  
   } 
}  

</script>
</body>
</html>
