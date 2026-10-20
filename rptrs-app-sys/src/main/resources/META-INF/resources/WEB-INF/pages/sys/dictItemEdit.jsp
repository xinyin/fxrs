<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>字典项编辑</title>
    <x:jsLib/>
</head>
<body class="white-bg">
<div class="row">
    <bc:ibox id="iboxDictItem" title="" col="12" collapsible="true">
        <bf:form id="formDictItem" method="post" validation="true">
            <bf:formGroup>
                <bc:label text="字典类别编码" col="4" formControl="true"/>
                <bf:text id="dictCateCode" name="dictCateCode" col="4" value="${dictItem.dictCateCode}" disabled="true">
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="字典编码" col="4" formControl="true"/>
                <bf:text id="dictCode" name="dictCode" col="4" value="${dictItem.dictCode}" disabled="true">
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bf:hidden name="itemId" value="${dictItem.itemId}"></bf:hidden>
                <bc:label text="字典项名称" col="4" formControl="true"/>
                <bf:text id="itemLabel" name="itemLabel" col="4" value="${dictItem.itemLabel}">
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="字典项值" col="4" formControl="true"/>
                <bf:text id="itemValue" name="itemValue" col="4" value="${dictItem.itemValue}">
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="字典项描述" col="4" formControl="true"/>
                <bf:text id="itemDesc" name="itemDesc" col="4" value="${dictItem.itemDesc}" multiple="true"
                         rows="3"></bf:text>
            </bf:formGroup>
            <div class="col-sm-8 col-sm-offset-4">
                <bc:button type="primary" text="保存" icon="save">
                    <be:event>
                        $('#formDictItem').data('formValidation').validate();
                        var isValid = $('#formDictItem').data('formValidation').isValid();
                        if(isValid)
                        {
                        var formData = $('#formDictItem').serializeAll();
                        $.mask.post('${base}/sys/dictItem/save', formData, function(data){
                        if(data.success)
                        {
                        $.messager.alert('提示', data.msg, 'info', function(){
                        $.messager.hide('winDictItemEdit');
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
                <bc:button outline="true" text="关闭">
                    <be:event>
                        $.messager.hide('winDictItemEdit');
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
