<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>角色组管理</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="roleGroupBox" title="编辑角色组" subTitle="请输入角色组信息" col="12" collapsible="true">
            <bf:form id="editForm" method="post" validation="true">
                <bf:hidden name="opt" id="opt"/>
                <bf:formGroup>
                    <bc:label text="机构层级" col="4" formControl="true"/>
                    <bf:select id="hierarchy" name="hierarchy" col="4" value="${roleGroup.hierarchy}"
                               dataSource="fixedDict" url="InsUnit" placeholder="请选择">
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色组编码" col="4" formControl="true"/>
                    <bf:text name="roleGroupCode" col="4" value="${roleGroup.roleGroupCode}"
                             disabled="${opt == 'edit'}">
                        <bv:notEmpty/>
                        <bv:regexp regexp="^[\w\s]+$" message="请输入正确的编码"/>
                        <bv:remote url="${base}/sys/roleGroup/check" name="roleGroupCode" message="该编码已被占用"/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色组名称" col="4" formControl="true"/>
                    <bf:text name="roleGroupName" col="4" value="${roleGroup.roleGroupName}">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色组描述" col="4" formControl="true"/>
                    <bf:text name="roleGroupDesc" multiple="true" col="4" value="${roleGroup.roleGroupDesc}"/>
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
                                $.mask.post('${base}/sys/roleGroup/save', formData, function(data){
                                if(data.success)
                                {
                                $.messager.alert('提示', data.msg, 'info', function(){
                                window.location = '${base}/sys/roleGroup/list';
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
                                window.location = '${base}/sys/roleGroup/list';
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

    });
</script>
</body>
</html>