<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>通知默认配置</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="notifyConfigBox" title="基础配置" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden name="id" value="${notifyConfig.id}"/>
            <bf:formGroup>
                <bc:label text="短信重发次数" col="4" formControl="true"/>
                <bf:text name="smsRetryTimes" value="${notifyConfig.smsRetryTimes}" col="4">
                    <bv:digits/>
                    <bv:notEmpty/>
                    <bv:stringLength min="1" max="1" message="短信重发次数范围：1~3"/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="邮件重发次数" col="4" formControl="true"/>
                <bf:text name="emailRetryTimes" value="${notifyConfig.emailRetryTimes}" col="4">
                    <bv:digits/>
                    <bv:notEmpty/>
                    <bv:stringLength min="1" max="1" message="邮件重发次数范围：1~3"/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="收件箱保留时长" col="4" formControl="true"/>
                <bf:checkGroup id="inboxRetentionTime" name="inboxRetentionTime" col="4"
                               value="${notifyConfig.inboxRetentionTime}">
                    <bf:radio url="NotifyConfigTime" dataSource="fixedDict">
                        <bv:notEmpty/>
                    </bf:radio>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="垃圾箱保留时长" col="4" formControl="true"/>
                <bf:checkGroup id="trashRetentionTime" name="trashRetentionTime" col="4"
                               value="${notifyConfig.trashRetentionTime}">
                    <bf:radio url="NotifyConfigTime" dataSource="fixedDict">
                        <bv:notEmpty/>
                    </bf:radio>
                </bf:checkGroup>
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
                            $.mask.post('${base}/sys/notifyConfig/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/notify/announceList';
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
                            window.location = '${base}/sys/notify/announceList';
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
