<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>布局的基本信息</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="dashLayoutBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:label text="布局编码" col="4" formControl="true"/>
                <bf:text id="code" name="code" col="4" value="${dashLayout.code}" disabled="${opt == 'edit'}">
                    <bv:jepCode/>
                    <bv:remote url="${base}/sys/dashLayout/check" name="code" message="该编码已被占用"/>
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="布局名称" col="4" formControl="true"/>
                <bf:text id="name" name="name" col="4" value="${dashLayout.name}">
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="优先级" col="4" formControl="true"/>
                <bf:text id="priority" name="priority" col="4" value="${dashLayout.priority}">
                    <bv:digits/>
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="布局设计模式" col="4" formControl="true"/>
                <bf:checkGroup name="designType" value="${dashLayout.designType}">
                    <bf:radio url="DashLayoutDesignType" dataSource="fixedDict" checked="true"/>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="布局描述" col="4" formControl="true"/>
                <bf:text id="desc" name="desc" col="4" value="${dashLayout.desc}" multiple="true"></bf:text>
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
                            $.mask.post('${base}/sys/dashLayout/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/dashLayout/list';
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
                            window.location = '${base}/sys/dashLayout/list';
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
