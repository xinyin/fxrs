<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>待办任务通知配置</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="notifyTodoOperBox" title="待办任务通知配置" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden name="opt" id="opt"/>
            <bf:formGroup>
                <bc:label text="目标类型" col="2" formControl="true"/>
                <bf:text id="targetType" name="targetType" col="6" value="${notifyTodoOper.targetType}">
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="动作" col="2" formControl="true"/>
                <bf:text id="action" name="action" col="6" value="${notifyTodoOper.action}">
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="URL地址" col="2" formControl="true"/>
                <bf:text id="url" name="url" col="6" value="${notifyTodoOper.url}">
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="具体操作" col="2" formControl="true"/>
                <bf:text id="operate" name="operate" col="6" value="${notifyTodoOper.operate}">
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="操作文本" col="2" formControl="true"/>
                <bf:text id="text" name="text" col="6" value="${notifyTodoOper.text}">
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="primary" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/notifyTodoOper/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/notifyTodoOper/list';
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
                    <bc:button outline="true" text="返回">
                        <be:event>
                            window.location = '${base}/sys/notifyTodoOper/list';
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
</body>
</html>
