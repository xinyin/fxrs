<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>定义系统中的附件上传类型。</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="attachmentTypeBox" title="定义附件上传属性" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden name="opt" id="opt"/>
            <bf:formGroup>
                <bc:label text="上传类型编码" col="4" formControl="true"/>
                <bf:text id="attachTypeCode" name="attachTypeCode" col="4" value="${attachmentType.attachTypeCode}"
                         disabled="${opt == 'edit'}">
                    <bv:remote url="${base}/sys/attachmentType/check" name="attachTypeCode" message="上传类型编码"/>
                    <bv:regexp regexp="^[\w\s]+$" message="请输入正确的编码"/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="上传类型名称" col="4" formControl="true"/>
                <bf:text id="attachTypeName" name="attachTypeName" col="4" value="${attachmentType.attachTypeName}">
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="附件保存路径（系统配置项）" col="4" formControl="true"/>
                <bf:text id="attachPath" name="attachPath" col="4" placeholder="该路径为相对路径"
                         value="${attachmentType.attachPath}">
                    <bv:regexp regexp="^[a-zA-Z_]{1}[a-zA-Z\d_]*" message="请输入正确的路径"/>
                </bf:text>
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
                            $.mask.post('${base}/sys/attachmentType/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/attachmentType/list';
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
                            window.location = '${base}/sys/attachmentType/list';
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
