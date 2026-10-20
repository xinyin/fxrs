<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>描述元数据的表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="metaTableBox" title="描述元数据的表" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="元数据ID" col="2" formControl="true"/>
                    <bc:label text="${metaTable.tableId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="表所属的用户" col="2" formControl="true"/>
                    <bc:label text="${metaTable.schema}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="表名" col="2" formControl="true"/>
                    <bc:label text="${metaTable.tableCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="表中文名" col="2" formControl="true"/>
                    <bc:label text="${metaTable.tableName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="M - 模型结果表             S - 步骤结果表             B - 业务数据表" col="2" formControl="true"/>
                    <bc:label text="${metaTable.tableType}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="对象类型：             T - 表             V  -  视图" col="2" formControl="true"/>
                    <bc:label text="${metaTable.objectType}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="对象来源：             S - 系统同步             C - 用户创建             M - 模型创建" col="2"
                              formControl="true"/>
                    <bc:label text="${metaTable.objectSource}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="10 - 草稿             30 - 已生效             50 - 已失效" col="2" formControl="true"/>
                    <bc:label text="${metaTable.status}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="备注" col="2" formControl="true"/>
                    <bc:label text="${metaTable.remark}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/metaTable/list';
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