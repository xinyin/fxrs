<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>表单事件表</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="form1" name="form2" method="post" validation="true">
			<bf:hidden name="opt" id="opt"></bf:hidden>
			<bf:hidden name="id" id="id" value="${formEvent.id}"></bf:hidden>
			<bf:hidden name="formCode" id="formCode" value="${formCode }"></bf:hidden>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="事件类型" col="4" formControl="true"/>
						<bf:select id="type" name="type" col="8" value="${formEvent.type}" dataSource="fixedDict" url="PropFormEventType" disabled="${opt == 'edit'}">
							<bv:notEmpty/>
							<be:event type="select">typeChange(e.target.value);</be:event>
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="事件" col="4" formControl="true"/>
						<bf:select id="name" name="name" col="8" value="${formEvent.type}">
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup id="propCodeDiv">
					<bc:col col="6">
						<bc:label text="属性名称" col="4" formControl="true"/>
						<bf:select name="propCode" col="8" dataSource="custom" url="workflow/formProp/queryFormProp?formCode=${formCode}" value="${formEvent.propCode}" >
							<bv:notEmpty/>
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="脚本" col="2" formControl="true"/>
						<bf:text id="script" name="script" col="10" multiple="true" value="${formEvent.script}"><bv:notEmpty/></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<bc:col col="12">
						<bc:label text="备注" col="2" formControl="true"/>
						<bf:text id="remark" name="remark" col="10" multiple="true" value="${formEvent.remark}"></bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary"  text="保存" icon="save">
							<be:event>
								$('#form1').data('formValidation').validate();
								var isValid = $('#form1').data('formValidation').isValid();
								if(isValid)
								{
									var data = $('#form1').serializeAll();
									$.mask.post('${base}/workflow/formEvent/save', data, function(data){
										if(data.success)
										{
											$.messager.alert('提示', '保存成功。', 'info', function(){
												window.location = '${base}/workflow/formProp/list?formCode=${formCode}';
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
								window.location = '${base}/workflow/formProp/list?formCode=${formCode}';
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
var opt = '${opt}';
var optEdit = true;
var editConfig = '${formProp.inputConfig}';
/* 用于存放属性事件 */
var propEventNames = [];
/* 表单事件 */
var formEventNames = [];
var opt = '${opt}';
$(document).ready(function(){
	
	//初始化时隐藏行分割线， 因为无法隐藏
	$('.hr-line-dashed').hide();
	$('#propCodeDiv').hide();
	
	//编辑加载
	if(opt == 'edit')
	{
		var type = '${type}';
		 typeChange(type);
	}
	// 获得所有的事件名称的地址  
    var url = "${base}/fixedDict/PropFormEvent";  
    // 向后台请求获取数据  
    $.getJSON(url,function (data) {  
        if (data) {  
            // 遍历事件名称
          $.each(data,function(i,val){  
        	  var obj = {
  	 				id: val.value,
  	 				text: val.name
  		 		};
        	  if(val.value == 'submit')
        	  {
        		  formEventNames.push(obj);
        	  }
        	  else
        	  {
        		  propEventNames.push(obj);
        	  }
          }); 
            
          if('${formEvent.type}' == 'PROP'){
        	  $('#name').select2({data: propEventNames, width: '100%'});
          }
          else
          {
        	  $('#name').select2({data: formEventNames, width: '100%'});
          }
        }  
    }); 
})

//事件类型选择
function typeChange(v){
	$('#propCodeDiv').show();
	//表单隐藏属性选择
	if(v == 'FORM')
	{
		$('#propCodeDiv').hide();
		$('#name').empty();
		$('#name').select2({data: formEventNames, width: '100%'});
	}
	else
	{
		$('#name').empty();
		$('#name').select2({data: propEventNames, width: '100%'});
	}
}
</script>

</body>
</html>
