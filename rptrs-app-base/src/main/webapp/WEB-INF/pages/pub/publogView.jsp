<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>Publog</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="publogBox" title="Publog" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="pid" col="2" formControl="true"/>
                    <bc:label text="${publog.pid}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="文件名称" col="2" formControl="true"/>
                    <bc:label text="${publog.pubFileName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="披露部门" col="2" formControl="true"/>
                    <bc:label text="${publog.pubSubject}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="pubYear" col="2" formControl="true"/>
                    <bc:label text="${publog.pubYear}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <%--<bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="pubQuarter" col="2" formControl="true"/>
                    <bc:label text="${publog.pubQuarter}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>--%>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="maxPercentInNetAssets" col="2" formControl="true"/>
                    <bc:label text="${publog.maxPercentInNetAssets}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="下载次数" col="2" formControl="true"/>
                    <bc:label text="${publog.downAmt}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="状态" col="2" formControl="true"/>
                    <bc:label text="${publog.status}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="状态说明" col="2" formControl="true"/>
                    <bc:label text="${publog.statusComment}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="下载状态" col="2" formControl="true"/>
                    <bc:label text="${publog.downStatus}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/pub/publog/list';
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