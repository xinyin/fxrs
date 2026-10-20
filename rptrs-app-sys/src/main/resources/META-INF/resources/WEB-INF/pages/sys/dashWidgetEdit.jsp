<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>组件表基本信息表</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="dashWidgetBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:label text="组件编码" col="4" formControl="true"/>
                <bf:text id="code" name="code" col="4" value="${dashWidget.code}" disabled="${opt == 'edit'}">
                    <bv:jepCode/>
                    <bv:remote url="${base}/sys/dashWidget/check" name="code" message="该编码已被占用"/>
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="组件名称" col="4" formControl="true"/>
                <bf:text id="name" name="name" col="4" value="${dashWidget.name}">
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="组件类型" col="4" formControl="true"/>
                <bf:select id="type" name="type" col="4" value="${dashWidget.type}"
                           dataSource="fixedDict" url="DashWidgetType">
                    <be:event type="select">onChange(e.target.value);</be:event>
                </bf:select>
            </bf:formGroup>
            <bf:formGroup id="implClassFormGrp" hidden="true">
                <bc:label text="组件实现类" col="4" formControl="true"/>
                <bf:text id="implClass" name="implClass" col="4"
                         value="${dashWidget.implClass}" placeholder="组件实现类只对自定义类型的组件有效">
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="描述" col="4" formControl="true"/>
                <bf:text id="desc" name="desc" col="4" value="${dashWidget.desc}" multiple="true" rows="3"></bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="备注" col="4" formControl="true"/>
                <bf:text id="remark" name="remark" col="4" value="${dashWidget.remark}" multiple="true"
                         rows="3"></bf:text>
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
                            $.mask.post('${base}/sys/dashWidget/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/dashWidget/list';
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
                            window.location = '${base}/sys/dashWidget/list';
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

    function onChange(v) {
        if (v == 'CUSTOM') {
            $('#implClassFormGrp').show();
            $('#implClassFormGrp').next('div').show();
        } else {
            $('#implClassFormGrp').hide();
            $('#implClassFormGrp').next('div').hide();
        }

    }
</script>
</body>
</html>
