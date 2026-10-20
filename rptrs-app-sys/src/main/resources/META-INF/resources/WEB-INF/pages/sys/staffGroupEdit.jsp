<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>编辑用户组</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox title="编辑用户组" subTitle="用户组信息" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden name="opt" id="opt"/>
            <bf:formGroup>
                <bc:label text="用户组编码" col="4" formControl="true"/>
                <bf:text id="staffGroupCode" name="staffGroupCode" col="4" value="${staffGroup.staffGroupCode}"
                         disabled="${opt == 'edit'}">
                    <bv:remote url="${base}/sys/staffGroup/check" name="staffGroupCode" message="用户组编码"/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="用户组名称" col="4" formControl="true"/>
                <bf:text id="staffGroupName" name="staffGroupName" col="4" value="${staffGroup.staffGroupName}">
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="用户组描述" col="4" formControl="true"/>
                <bf:text id="staffGroupDesc" name="staffGroupDesc" col="4" value="${staffGroup.staffGroupDesc}"
                         multiple="true"/>
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
                            $.mask.post('${base}/sys/staffGroup/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/staffGroup/list';
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
                            window.location = '${base}/sys/staffGroup/list';
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
