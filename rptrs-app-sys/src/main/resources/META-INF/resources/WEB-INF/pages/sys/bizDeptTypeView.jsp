<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>部门业务种类关系表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="bizDeptTypeBox" title="" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="主键" col="2" formControl="true"/>
                    <bc:label text="${bizDeptType.deptTypeId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="机构编码" col="2" formControl="true"/>
                    <bc:label text="${bizDeptType.deptCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="部门名称" col="2" formControl="true"/>
                    <bc:label text="${bizDeptType.deptName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="业务种类编码" col="2" formControl="true"/>
                    <bc:label text="${bizDeptType.bizTypeCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="业务种类名称" col="2" formControl="true"/>
                    <bc:label text="${bizDeptType.bizTypeName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="租户" col="2" formControl="true"/>
                    <bc:label text="${bizDeptType.tenementId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="所属部门编码" col="2" formControl="true"/>
                    <bc:label text="${bizDeptType.bizDeptCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="所属部门名称" col="2" formControl="true"/>
                    <bc:label text="${bizDeptType.bizDeptName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="">
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