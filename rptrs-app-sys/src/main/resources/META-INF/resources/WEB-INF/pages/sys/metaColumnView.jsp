<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>列元数据表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="metaColumnBox" title="列元数据表" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="元数据ID" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.columnId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="表元数据ID" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.tableId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="tableCode" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.tableCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="字段名" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.columnCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="中文名" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.columnName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="数据类型" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.dataType}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="字段长度" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.length}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="字段精度" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.scale}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="是否主键" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.isKey}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="是否敏感字段" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.isSensitive}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="是否外键" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.isForeignKey}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="字段顺序" col="2" formControl="true"/>
                    <bc:label text="${metaColumn.order}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="字段属性：             O - 机构字段             D - 日期字段             N - 普通字段" col="2"
                              formControl="true"/>
                    <bc:label text="${metaColumn.prop}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/metaColumn/list';
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