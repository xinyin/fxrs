<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>租户表</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="tenantBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="id" name="id" value="${tenant.id}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="编码" col="4" formControl="true"/>
                    <bf:text id="code" name="code" col="8" value="${tenant.code}">
                        <bv:regexp regexp="^[a-zA-Z0-9][a-zA-Z0-9_]*$" message="只能以英文字母、数字开头，只能包含英文字母、数字、下划线"/>
                        <bv:notEmpty/>
                        <bv:remote url="${base}/sys/tenant/check" name="code" message="该编码已被占用"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="名称" col="4" formControl="true"/>
                    <bf:text id="name" name="name" col="8" value="${tenant.name}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="用户编码" col="4" formControl="true"/>
                    <bf:text id="staffCode" name="staffCode" col="8" value="${staff.staffCode}">
                        <bv:notEmpty/>
                        <bv:remote url="${base}/sys/staff/check" name="staffCode" message="用户编码已存在"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="用户名称" col="4" formControl="true"/>
                    <bf:text id="staffName" name="staffName" col="8" value="${staff.staffName}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <%-- <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="是否根租户" col="4" formControl="true"/>
                    <bf:checkGroup name="isRoot" value="${tenant.isRoot}" col="8">
                        <bf:radio dataSource="fixedDict" url="YesNo" checked="true"/>
                    </bf:checkGroup>
                </bc:col>
            </bf:formGroup> --%>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="12">
                    <bc:label text="备注" col="2" formControl="true"/>
                    <bf:text id="remark" name="remark" col="10" value="${tenant.remark}" multiple="true"></bf:text>
                </bc:col>
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
                            $.mask.post('${base}/sys/tenant/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/tenant/list';
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
                            window.location = '${base}/sys/tenant/list';
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
