<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>系统级别配置</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="appConfigBox" title="系统级别配置" col="12" collapsible="false">
            <bf:form id="appConfigForm" name="appConfigFormNM" method="post" validation="true">
                <bf:formGroup>
                    <bc:label text="是否允许本地登录" col="4" formControl="true"/>
                    <bf:checkGroup name="ALLOW_LOCAL_LOGIN" value="${ALLOW_LOCAL_LOGIN}" col="6">
                        <bf:radio dataSource="fixedDict" url="YesNo" checked="true"/>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="禁止上传的附件类型" col="4" formControl="true"/>
                    <bf:text name="UPLOADEXCTYPES" value="${UPLOADEXCTYPES}" col="4"
                             placeholder="如：pdf，exe等。使用逗号分隔，-1代表不限制">
                        <bv:notEmpty/>
                        <bv:regexp regexp="^(([a-zA-Z]*|[a-zA-Z]*[,][a-zA-Z])*|-1)$"
                                   message="请输入有效的后缀名，如：csv,txt…… 或-1"/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="允许上传的附件类型" col="4" formControl="true"/>
                    <bf:text name="UPLOADINCTYPES" value="${UPLOADINCTYPES}" col="4"
                             placeholder="如：pdf，exe等。使用逗号分隔，-1代表不限制">
                        <bv:notEmpty/>
                        <bv:regexp regexp="^(([a-zA-Z]*|[a-zA-Z]*[,][a-zA-Z])*|-1)$"
                                   message="请输入有效的后缀名，如：csv,txt…… 或-1"/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="附件上传路径" col="4" formControl="true"/>
                    <bf:text name="UPLOADPATH" value="${UPLOADPATH}" col="4" placeholder="如：D:/upload">
                        <bv:notEmpty/>
                        <bv:regexp regexp="^(([A-Za-z]{1}:\/[\w\/]*)?\w|(\/[\w\/]*)?\w)$"
                                   message="请输入有效的路径，如：D:/upload"/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="单个附件大小" col="4" formControl="true"/>
                    <bf:text name="UPLOADSIZE" value="${UPLOADSIZE}" col="4" placeholder="允许上传的最大值，如：50">
                        <bf:addon position="after">M</bf:addon>
                        <bv:notEmpty/>
                        <bv:digits/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <div align="center">
                        <bc:button id="btnSave" type="primary" text="保存" icon="save">
                            <be:event>
                                $('#appConfigForm').data('formValidation').validate();
                                var isValid = $('#appConfigForm').data('formValidation').isValid();
                                if(isValid)
                                {
                                var formData = $('#appConfigForm').serializeAll();
                                $.mask.post('${base}/sys/appConfig/save'
                                , {"configs": JSON.stringify(formData)}
                                , function(data){
                                if(data.success)
                                {
                                $.messager.alert('提示', '保存成功。', 'info', function(){
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