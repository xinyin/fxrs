<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>描述元数据表之间的关联</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="metaRelationBox" title="描述元数据表之间的关联" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="表关系ID" col="2" formControl="true"/>
                    <bc:label text="${metaRelation.relationId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="主表ID" col="2" formControl="true"/>
                    <bc:label text="${metaRelation.masterTableId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="主表模式" col="2" formControl="true"/>
                    <bc:label text="${metaRelation.masterTableSchema}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="主表编码" col="2" formControl="true"/>
                    <bc:label text="${metaRelation.masterTableCode}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="主表列编码" col="2" formControl="true"/>
                    <bc:label text="${metaRelation.masterColumnCode}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="joinTableId" col="2" formControl="true"/>
                    <bc:label text="${metaRelation.joinTableId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="关联表模式" col="2" formControl="true"/>
                    <bc:label text="${metaRelation.joinTableSchema}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="关联表编码" col="2" formControl="true"/>
                    <bc:label text="${metaRelation.joinTableCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="关联表列编码" col="2" formControl="true"/>
                    <bc:label text="${metaRelation.joinColumnCode}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="关联关系" col="2" formControl="true"/>
                    <bc:label text="${metaRelation.joinDetails}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/metaRelation/list';
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