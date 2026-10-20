<%--
  Created by IntelliJ IDEA.
  User: xin
  Date: 2021/7/7
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="form"/>
    <title>RiskRatio</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="riskRatioBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="上传时间" col="4" formControl="true"/>
                    <bf:datebox id="createTimes" name="createTimes" col="8"/>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="预警比例(%)" col="4" formControl="true"/>
                    <bf:text id="warningValue" name="warningValue" col="8" value="${riskRatio.warningValue}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="预警提示语" col="4" formControl="true"/>
                    <bf:text id="markedWords" name="markedWords" col="8" value="${riskRatio.markedWords}">
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
                            $.mask.post('${base}/bus/riskRatio/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/riskRatio/list';
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
                            window.location = '${base}/bus/riskRatio/list';
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
    $(document).ready(function(){

    });
</script>
</body>
</html>
