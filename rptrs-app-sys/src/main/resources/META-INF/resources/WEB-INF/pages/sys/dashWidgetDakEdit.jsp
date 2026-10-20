<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>角色的组件数据权限对应表。</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="dashWidgetDakBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="权限ID" col="4" formControl="true"/>
                    <bf:text id="dakId" name="dakId" col="8" value="${dashWidgetDak.dakId}" disabled="${opt == 'edit'}">
                        <bv:jepCode/>
                        <bv:remote url="${base}/sys/dashWidgetDak/check" name="dakId" message="该编码已被占用"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="权限编码" col="4" formControl="true"/>
                    <bf:text id="dakCode" name="dakCode" col="8" value="${dashWidgetDak.dakCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="组件编码" col="4" formControl="true"/>
                    <bf:text id="widgetCode" name="widgetCode" col="8" value="${dashWidgetDak.widgetCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="目前只支持角色（ROLEGROUP/ROLE）" col="4" formControl="true"/>
                    <bf:text id="dakMapType" name="dakMapType" col="8" value="${dashWidgetDak.dakMapType}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="关联角色或角色组编码" col="4" formControl="true"/>
                    <bf:text id="dakUsingCode" name="dakUsingCode" col="8" value="${dashWidgetDak.dakUsingCode}">
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
                            $.mask.post('${base}/sys/dashWidgetDak/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/dashWidgetDak/list';
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
                            window.location = '${base}/sys/dashWidgetDak/list';
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
