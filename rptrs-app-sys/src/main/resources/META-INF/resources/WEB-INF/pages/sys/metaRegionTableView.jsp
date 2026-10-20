<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据区域关系表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="metaRegionTableBox" title="元数据区域关系表" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="关系ID" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.regionTableId}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="区域ID" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.regionId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="元数据ID" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.tableId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="模式" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.schema}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="元数据编码" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.tableCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="M - 模型结果表             S - 步骤结果表             B - 业务数据表" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.tableType}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="对象类型：             T - 表             V  -  视图" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.objectType}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="对象来源：             S - 系统同步             C - 用户创建             M - 模型创建" col="2"
                              formControl="true"/>
                    <bc:label text="${metaRegionTable.objectSource}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="机构编码" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.unitCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="机构名称" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.unitName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="部门编码" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.deptCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="部门名称" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.deptName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="用户编码" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.staffCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="用户名称" col="2" formControl="true"/>
                    <bc:label text="${metaRegionTable.staffName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/metaRegionTable/list';
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