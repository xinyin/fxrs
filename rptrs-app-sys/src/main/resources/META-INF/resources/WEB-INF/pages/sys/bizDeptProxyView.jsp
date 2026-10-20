<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>机构代管关系表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="bizDeptProxyBox" title="" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="部门" col="4" formControl="true"/>
                        <bc:label text="${bizDeptProxy.deptName}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="标准角色" col="4" formControl="true"/>
                        <bc:label text="${bizDeptProxy.stdRoleName}" col="8" formControl="true"
                                  style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="代管部门" col="4" formControl="true"/>
                        <bc:label text="${bizDeptProxy.proxyDeptName}" col="8" formControl="true"
                                  style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="代管标准角色" col="4" formControl="true"/>
                        <bc:label text="${bizDeptProxy.proxyStdRoleName}" col="8" formControl="true"
                                  style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:label text="备注" col="2" formControl="true"/>
                    <bc:label text="${bizDeptProxy.remark}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div align="center">
                        <bc:button text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/bizDeptProxy/list';
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