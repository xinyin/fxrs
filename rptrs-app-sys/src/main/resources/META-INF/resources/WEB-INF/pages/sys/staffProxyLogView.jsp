<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>人员借调记录查看</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="staffProxyLogBox" title="人员借调详情" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="主键" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.id}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="用户编码" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.staffCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="用户名称" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.staffName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="借调人编码" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.staffSecondCode}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="借调人名称" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.staffSecondName}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="所属部门" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.deptCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="借调部门名称" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.deptName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="借调起始时间" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.startDate}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="预计借调结束时间" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.preEndDate}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="实际结束时间" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.endDate}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="状态" col="2" formControl="true"/>
                    <bc:label text="${staffProxyLog.status}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回">
                            <be:event>
                                window.location = '${base}/sys/staffProxyLog/list';
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