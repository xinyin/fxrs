<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据表的自定义查询</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="metaQueryBox" title="" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="元数据ID" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.tableId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="查询ID" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.queryId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="查询名称" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.queryName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="查询类型" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.queryType}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="查询内容" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.script}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="状态" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.status}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="机构编码" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.unitCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="机构名称" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.unitName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="部门编码" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.deptCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="部门名称" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.deptName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="用户编码" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.staffCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="用户名称" col="2" formControl="true"/>
                    <bc:label text="${metaQuery.staffName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/dataProvider/sync';
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