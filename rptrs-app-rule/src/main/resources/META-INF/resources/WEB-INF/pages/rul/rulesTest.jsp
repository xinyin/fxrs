<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="../include/common.jsp"%>
<br:css pack="form"/>
<title>规则测试</title>
</head>
<body class="white-bg">
<div class="row">
	<bc:ibox id="inOutObj" title="" subTitle="" col="12" collapsible="false">
		<bf:form id="editForm" method="post" validation="true">
			<div id="columnDiv">
			</div>
			<bf:formGroup>
				<bc:col col="6" colOffset="7">
					<bc:button id="btnSave" type="primary" text="测试" icon="wpexplorer" click="btnSave();"/>
					<bc:button id="btnReset" type="default" text="清空" icon="refresh" click="restClick();"/>
					<bc:button id="btnClose" type="default" text="关闭" icon="close" click="close();"/>
				</bc:col>
			</bf:formGroup>
		</bf:form>
	</bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function(){
        $.ajax({
            type: "get",
            url: "${base}/rul/ruleObjectMap/findObjectsByRuleId?ruleId=${ruleId}",
            cache:false,
            async:false,
            dataType: "json",
            success: function(data){
                var html = '';
                $.each(data.rows, function(i, v){
                    if(i%2 == 1)
                    {
                        html += '<div class="form-group ">';
                    }
                    html += '<div class="col-xs-6">';
                    html += '<label  class="control-label col-xs-3">' + v.name + '</label>';
                    html += '<div class=" col-xs-9">';
                    html += '<input type="text" id="' + v.code + '" name="' + v.code + '" data-fv-notempty="true" class="form-control">';
                    html += '</div></div>';
                    if(i%2 == 1)
                    {
                        html += '</div>';
                        html += '<div class="hr-line-dashed"></div>';
                    }
                    $('#columnDiv').html(html);
                });
            }
        });
    });

    //测试
    function btnSave(){

        var formData = $('#editForm').serializeAll();
        var obj = {};
        $.each(formData, function(i, v){
            obj['' + v.name + ''] = v.value;
        });

        $.mask.post('${base}/rul/rules/test', {ruleId: '${ruleId}', requestData: JSON.stringify(obj)}, function(data){
            if(data.success)
            {
                $.messager.alert('成功', "返回结果：" + JSON.stringify(data), 'info');
            }
            else
            {
                $.messager.alert('错误', "测试失败", 'error');
            }
        });

    }

    //清空
    function restClick(){
        $('#editForm')[0].reset();
    }

    function close(){
        $.messager.hide('winObjsAdd');
	}
</script>
</body>
</html>
