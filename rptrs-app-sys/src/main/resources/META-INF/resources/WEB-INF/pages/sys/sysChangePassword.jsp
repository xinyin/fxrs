<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>修改密码</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="changePWDBox" title="修改密码" col="12" collapsible="false">
            <bf:form id="changePWDForm" name="changePWDFormNM" method="post" validation="true">
                <bf:formGroup cls="m-b">
                    <bc:label text="密码" col="2" formControl="true"/>
                    <bf:text name="password" type="password" placeholder="密码" col="4">
                        <bv:notEmpty/>
                        <bv:regexp regexp="^[0-9a-zA-Z_#]{6,16}$" message="密码必须是6-16位的字母数字或者符号"/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup cls="m-b">
                    <bc:label text="密码确认" col="2" formControl="true"/>
                    <bf:text name="passwordConfirm" type="password" placeholder="密码确认" col="4">
                        <bv:notEmpty/>
                        <bv:identical field="password" message="两次密码不一致"/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-4">
                        <bc:button id="btnSave" type="primary" text="保存" icon="save">
                            <be:event>
                                $('#changePWDForm').data('formValidation').validate();
                                var isValid = $('#changePWDForm').data('formValidation').isValid();
                                if(isValid)
                                {
                                var formData = $('#changePWDForm').serializeAll();
                                $.mask.post('${base}/sys/user/changePWD'
                                , formData
                                , function(data){
                                if(data.success)
                                {
                                $.messager.alert('提示', '修改成功。', 'info', function(){
                                });
                                }
                                else
                                {
                                $.messager.alert('错误', '修改失败。', 'error');
                                }
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
</body>
</html>