<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据表空间</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="metaTablespaceBox" title="元数据表空间" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="表空间ID" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.tablespaceId}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="表空间编码" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.tablespaceCode}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="表空间名称" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.tablespaceName}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="表空间描述" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.tablespaceDesc}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="数据文件" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.dataFileLoc}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="初始大小(M)" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.initSize}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="页面大小(K)" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.pageSize}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="是否扩展: Y/N" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.autoExtend}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="扩展大小(M)" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.extendSize}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="最大容量(M)" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.maxSize}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="表空间状态:             10 - 草稿             30 - 启用             50 - 停用" col="2"
                              formControl="true"/>
                    <bc:label text="${metaTablespace.status}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="备注" col="2" formControl="true"/>
                    <bc:label text="${metaTablespace.remark}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/metaTablespace/list';
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