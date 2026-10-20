<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>组件详细配置信息。</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="form1" name="form2" method="post" validation="true">
            <bf:hidden name="opt" id="opt"></bf:hidden>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="组件编码" col="4" formControl="true"/>
                    <bf:text id="widgetCode" name="widgetCode" col="8" value="${dashWidgetConfig.widgetCode}">
                        <bv:remote url="${base}/sys/dashWidgetConfig/check" name="widgetCode" message="组件编码"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="组件详细配置，JSON 格式" col="4" formControl="true"/>
                    <bf:text id="config" name="config" col="8" value="${dashWidgetConfig.config}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="租户" col="4" formControl="true"/>
                    <bf:text id="tenantId" name="tenantId" col="8" value="${dashWidgetConfig.tenantId}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="primary" text="保存" icon="save">
                        <be:event>
                            $('#form1').data('formValidation').validate();
                            var isValid = $('#form1').data('formValidation').isValid();
                            if(isValid)
                            {
                            var data = $('#form1').serializeAll();
                            $.mask.post('${base}/sys/dashWidgetConfig/save', data, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', '保存成功。', 'info', function(){
                            window.location = '${base}/sys/dashWidgetConfig/list';
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
                    <bc:button outline="true" text="返回">
                        <be:event>
                            window.location = '${base}/sys/dashWidgetConfig/list';
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
    $(window).load(function () {
        jQuery.ajaxSetup({
            cache: false
        });
    });
</script>
</body>
</html>
