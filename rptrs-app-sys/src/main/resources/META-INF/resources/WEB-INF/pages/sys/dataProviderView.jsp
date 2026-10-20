<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>仪表盘数据提供源</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="dataProviderBox" title="仪表盘数据提供源" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="查询ID" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.queryId}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="元数据ID" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.tableId}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="查询名称" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.queryName}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="查询类型" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.queryType}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="状态" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.status}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="机构编码" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.unitCode}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="机构名称" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.unitName}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="部门编码" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.deptCode}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="部门名称" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.deptName}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="用户编码" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.staffCode}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="用户名称" col="4" formControl="true"/>
                        <bc:label text="${dataProvider.staffName}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <div align="center">
                        <bc:button text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/dataProvider/list';
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