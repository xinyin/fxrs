<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>业务流程启动测试</title>
<x:jsLib/>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="procDefBox" title="业务流程启动测试" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden name="opt" id="opt" value="${opt}"/>
				<bf:formGroup>
					<bc:label text="业务流程名称" col="2" formControl="true"/>
					<bf:select id="procDefCode" name="procDefCode" dataSource="custom" col="8"
							   url="workflow/procDef/selectCurrent" >
						<bv:notEmpty/>
					</bf:select>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="修改前数据" col="2" formControl="true"/>
					<bf:text id="oldData" name="oldData" col="8" multiple="true"></bf:text>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary"  text="提交" icon="save">
							<be:event>
								var oldData = $('#oldData').val();
								var procDefCode = $('#procDefCode').val();
								if(oldData){
									if(isJSON(oldData)){
									  	window.location = '${base}/workflow/process/edit?procDefCode=' + procDefCode + '&oldData=' + $.helper.base64Encode(oldData);
									}
									else
									{
										$.messager.alert('提示', "修改前数据的格式不是正确的json格式", 'info');
									    return;
									}
								}
								else
								{
									window.location = '${base}/workflow/process/edit?procDefCode=' + procDefCode ;	
								}
								
							</be:event>
						</bc:button>
					</div>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary"  text="客户端确认" icon="save">
							<be:event>
								var procDefCode = $('#procDefCode').val();
								if(procDefCode){
									$.mask.post('${base}/workflow/process/cleanNotifyByTest', {procDefCode: procDefCode}, function(data){
										$.messager.alert('提示', data.msg, 'info');
									});
								}
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
function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(str.indexOf('{')>-1){
                return true;
            }else{
                return false;
            }

        } catch(e) {
           
            return false;
        }
    }
    return false;
}
</script>
</body>
</html>
