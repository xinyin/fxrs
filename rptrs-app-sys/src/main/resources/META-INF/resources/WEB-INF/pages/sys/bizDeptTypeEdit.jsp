<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>部门业务种类关系表</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="bizDeptTypeBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden name="opt" id="opt"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="主键" col="4" formControl="true"/>
                    <bf:text id="deptTypeId" name="deptTypeId" col="8" value="${bizDeptType.deptTypeId}"
                             disabled="${opt == 'edit'}">
                        <bv:remote url="${base}/sys/bizDeptType/check" name="deptTypeId" message="主键"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="机构编码" col="4" formControl="true"/>
                    <bf:text id="deptCode" name="deptCode" col="8" value="${bizDeptType.deptCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="部门名称" col="4" formControl="true"/>
                    <bf:text id="deptName" name="deptName" col="8" value="${bizDeptType.deptName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="业务种类编码" col="4" formControl="true"/>
                    <bf:text id="bizTypeCode" name="bizTypeCode" col="8" value="${bizDeptType.bizTypeCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="业务种类名称" col="4" formControl="true"/>
                    <bf:text id="bizTypeName" name="bizTypeName" col="8" value="${bizDeptType.bizTypeName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="租户" col="4" formControl="true"/>
                    <bf:text id="tenementId" name="tenementId" col="8" value="${bizDeptType.tenementId}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="所属部门编码" col="4" formControl="true"/>
                    <bf:text id="bizDeptCode" name="bizDeptCode" col="8" value="${bizDeptType.bizDeptCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="所属部门名称" col="4" formControl="true"/>
                    <bf:text id="bizDeptName" name="bizDeptName" col="8" value="${bizDeptType.bizDeptName}">
                    </bf:text>
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
                            $.mask.post('${base}/sys/bizDeptType/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/bizDeptType/list';
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
                            window.location = '${base}/sys/bizDeptType/list';
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
</body>
</html>
