<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>编辑角色类型</title>
</head>

<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="roleStdIbox" title="编辑角色类型" col="12" collapsible="true">
            <bf:form id="editForm" method="post" validation="true">
                <bf:hidden name="opt" id="opt"/>

                <bf:formGroup>
                    <bc:label text="角色类型编码" col="4" formControl="true"/>
                    <bf:text name="roleStdCode" value="${roleStd.roleStdCode}" col="4" disabled="${opt == 'edit'}">
                        <bv:notEmpty/>
                        <bv:regexp regexp="^[\w\s]+$" message="请输入正确的编码"/>
                        <bv:remote url="${base}/sys/roleStd/check" name="roleStdCode" message="该编码已被占用"/>
                        <bv:digits/>
                    </bf:text>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:label text="角色类型名称" col="4" formControl="true"/>
                    <bf:text name="roleStdName" value="${roleStd.roleStdName}" col="4">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:label text="角色类型描述" col="4" formControl="true"/>
                    <bf:text name="roleStdDesc" value="${roleStd.roleStdDesc}" col="4" multiple="true"></bf:text>
                </bf:formGroup>

                <bf:formGroup>
                    <div style="text-align: center;">
                        <bc:button type="primary" text="保存" icon="save">
                            <be:event>
                                $('#editForm').data('formValidation').validate();
                                var isValid = $('#editForm').data('formValidation').isValid();
                                if(isValid)
                                {
                                var data = $('#editForm').serializeAll();
                                $.mask.post('${base}/sys/roleStd/save', data, function(data){
                                if(data.success)
                                {
                                $.messager.alert('提示', data.msg, 'info', function(){
                                window.location = '${base}/sys/roleStd/list';
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
                                window.location = '${base}/sys/roleStd/list';
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