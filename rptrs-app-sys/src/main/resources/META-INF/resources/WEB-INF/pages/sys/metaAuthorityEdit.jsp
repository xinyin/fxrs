<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据授权，包括操作权限与数据权限。</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="metaAuthorityBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="授权ID" col="4" formControl="true"/>
                    <bf:text id="authId" name="authId" col="8" value="${metaAuthority.authId}"
                             disabled="${opt == 'edit'}">
                        <bv:jepCode/>
                        <bv:notEmpty/>
                        <bv:remote url="${base}/sys/metaAuthority/check" name="authId" message="该编码已被占用"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="角色编码" col="4" formControl="true"/>
                    <bf:text id="roleCode" name="roleCode" col="8" value="${metaAuthority.roleCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="OPER - 操作权限             DAK - 数据权限             " col="4" formControl="true"/>
                    <bf:text id="authType" name="authType" col="8" value="${metaAuthority.authType}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="授权对象类型             M - 数据集市             T - 元数据             C - 元数据列" col="4"
                              formControl="true"/>
                    <bf:text id="authTargetType" name="authTargetType" col="8" value="${metaAuthority.authTargetType}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="授权对象" col="4" formControl="true"/>
                    <bf:text id="authTarget" name="authTarget" col="8" value="${metaAuthority.authTarget}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label
                            text="授权内容：             操作权限： Y - 允许，N - 拒绝             数据权限： DAK_ORG, DAK_ORG_ALL, DAK_DEPT, DAK_DEPT_ALL, DAK_FORBIDDEN"
                            col="4" formControl="true"/>
                    <bf:text id="authData" name="authData" col="8" value="${metaAuthority.authData}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/metaAuthority/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/metaAuthority/list';
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
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/sys/metaAuthority/list';
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
