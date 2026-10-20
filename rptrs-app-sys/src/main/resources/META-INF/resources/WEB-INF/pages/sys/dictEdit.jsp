<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>编辑字典</title>
</head>
<body class="white-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxDict" title="" col="12" collapsible="true">
            <bf:form id="dictForm" method="post" validation="true">
                <bf:formGroup>
                    <bc:label text="所属分类编码" col="4" formControl="true"/>
                    <bf:text name="dictCateCode" value="${dict.dictCateCode}" col="4" disabled="true"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="字典编码" col="4" formControl="true"/>
                    <bf:text name="dictCode" value="${dict.dictCode}" col="4" disabled="${opt == 'edit'}">
                        <bv:notEmpty/>
                        <bv:remote url="${base}/sys/dict/check" name="dictCode" message="该编码已被占用"/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="字典名称" col="4" formControl="true"/>
                    <bf:text name="dictName" value="${dict.dictName}" col="4">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="字典描述" col="4" formControl="true"/>
                    <bf:text name="dictDesc" value="${dict.dictDesc}" multiple="true" col="4" rows="3"/>
                </bf:formGroup>
                <div class="col-sm-8 col-sm-offset-4">
                    <bc:button type="primary" text="保存" icon="save">
                        <be:event>
                            $('#dictForm').data('formValidation').validate();
                            var isValid = $('#dictForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#dictForm').serializeAll();
                            $.mask.post('${base}/sys/dict/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            $.messager.hide('winDictEdit');
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
                    <bc:button text="关闭" icon="times">
                        <be:event>
                            $.messager.hide('winDictEdit');
                        </be:event>
                    </bc:button>
                </div>
            </bf:form>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>