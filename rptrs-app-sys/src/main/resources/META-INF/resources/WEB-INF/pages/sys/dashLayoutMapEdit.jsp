<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>首页仪表盘布局映射表，可映射到角色、角色组等，现在只使用映射到角色。</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="dashLayoutMapBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="角色组编码" col="4" formControl="true"/>
                    <bf:text id="roleGroupCode" name="roleGroupCode" value="${dashLayoutRoleGroupMap.roleGroupCode}"
                             col="8" disabled="true">
                        <bv:jepCode/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="角色组名称" col="4" formControl="true"/>
                    <bf:text id="roleGroupName" value="${dashLayoutRoleGroupMap.roleGroupName}" name="roleGroupName"
                             col="8" disabled="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="布局" col="4" formControl="true"/>
                    <bf:select name="layoutCode" value="${dashLayoutRoleGroupMap.layoutCode}" id="layout" col="8"
                               dataSource="custom" url="sys/dashLayout/getAll">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-4">
                    <bc:button type="primary" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/dashLayoutMap/roleGroupSave', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('系统提示',data.msg, 'info', function(result){
                            if(result){
                            $.messager.hide('roleGroupEditWin');
                            }
                            })
                            }
                            else
                            {
                            $.messager.toastr('错误', data.msg, 'error');
                            }
                            });
                            }
                        </be:event>
                    </bc:button>
                    <bc:button text="关闭" icon="arrow-circle-left">
                        <be:event>
                            $.messager.hide('roleGroupEditWin');
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
    $(document).ready(function () {
        $('#layout').val('${dashLayoutRoleGroupMap.layoutCode}').trigger('change');
    });
</script>
</body>
</html>
