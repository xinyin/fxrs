<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>GdTraZr</title>
    <style>
        .center div {
            float: none;
            margin: 1% 0;
        }

        .lefts, .rights {
            border: 1px solid #cccccc;
            width:40%;
        }

        .lefts p, .rights p {
            width: 19%;
            margin: 3% auto;
            padding: 2%;
            background: #0d8ddb;
            color: white;
            border-radius: 6px;
            text-align: center;
        }

        .lefts div, .rights div {
            float: none;
            margin: 2% 0;
            display: flex;

        }

        .center {
            margin: 10% 0 0 0;
            width: 20%;
        }

        .center label {
            text-align: center !important;
        }

        .box {
            display: flex;
            justify-content: space-around;
            width: 80%;
            margin: 0 auto;
        }

        .file {
            position: absolute;
            top: 100px;
            left: 44%;
            display: inline-block;
            background: #D0EEFF;
            border: 1px solid #99D3F5;
            border-radius: 4px;
            padding: 4px 12px;
            overflow: hidden;
            color: #1E88C7;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
        }

        .file input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
        }

        .file:hover {
            background: #AADFFD;
            border-color: #78C3F3;
            color: #004974;
            text-decoration: none;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="gdTraZrBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${gdTraZr.pid}"/>
            <bf:hidden id="traSerial" name="traSerial" value="${gdTraZr.traSerial}"/>
            <div class="box">
                <div class="lefts">
                    <p>被继承人</p>
                    <bc:col col="12">
                        <bc:label text="被继承人股东代码" col="6" formControl="true"/>
                        <bf:text id="fromShareholdersCode" name="fromShareholdersCode" col="8"
                                 value="${gdTraZr.fromShareholdersCode}" change="getFromShareholder()">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="被继承人名称" col="6" formControl="true"/>
                        <bf:text id="fromShareholdersName" name="fromShareholdersName" col="8"
                                 value="${gdTraZr.fromShareholdersName}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="被继承人变更前持股数量（股）" col="6" formControl="true"/>
                        <bf:text id="beforeFromAmount" name="beforeFromAmount" col="8"
                                 value="${gdTraZr.beforeFromAmount}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="被继承人变更前持股占比（%）" col="6" formControl="true"/>
                        <bf:text id="beforeFromProportion" name="beforeFromProportion" col="8"
                                 value="${gdTraZr.beforeFromProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
<%--                    <bc:col col="12">--%>
<%--                        <bc:label text="被继承人可用股数(股)" col="6" formControl="true"/>--%>
<%--                        <bf:text id="useFromShareAmount" name="useFromShareAmount" col="8"--%>
<%--                                 value="${useFromShareAmount}" disabled="true">--%>
<%--                        </bf:text>--%>
<%--                    </bc:col>--%>
                    <bc:col col="12">
                        <bc:label text="被继承人变更后持股数量（股）" col="6" formControl="true"/>
                        <bf:text id="afterFromAmount" name="afterFromAmount" col="8" value="${gdTraZr.afterFromAmount}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="被继承人变更后持股数占比（%)" col="6" formControl="true"/>
                        <bf:text id="afterFromProportion" name="afterFromProportion" col="8"
                                 value="${gdTraZr.afterFromProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
                </div>
                <div class="center">
                    <bc:col col="12">
                        <bc:label text="继承股数（股）" col="12" formControl="true"/>
                        <bf:text id="operationAmount" name="operationAmount" col="12" value="${gdTraZr.operationAmount}"
                                 keyup="getToFromAmount()">
                        </bf:text>
                    </bc:col>
                </div>
                <div class="rights">
                    <p>继承人</p>
                    <bc:col col="12">
                        <bc:label text="继承人股东代码" col="6" formControl="true"/>
                        <bf:text id="toShareholdersCode" name="toShareholdersCode" col="8"
                                 value="${gdTraZr.toShareholdersCode}" change="getToShareholder()">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="继承人股东名称" col="6" formControl="true"/>
                        <bf:text id="toShareholdersName" name="toShareholdersName" col="8"
                                 value="${gdTraZr.toShareholdersName}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="继承人现持股数量（股）" col="6" formControl="true"/>
                        <bf:text id="beforeToAmount" name="beforeToAmount" col="8" value="${gdTraZr.beforeToAmount}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="继承人现持股占比（%）" col="6" formControl="true"/>
                        <bf:text id="beforeToProportion" name="beforeToProportion" col="8"
                                 value="${gdTraZr.beforeToProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
<%--                    <bc:col col="12">--%>
<%--                        <bc:label text="继承人可用股数(股)" col="6" formControl="true"/>--%>
<%--                        <bf:text id="useToShareAmount" name="useToShareAmount" col="8"--%>
<%--                                 value="${useToShareAmount}" disabled="true">--%>
<%--                        </bf:text>--%>
<%--                    </bc:col>--%>
                    <bc:col col="12">
                        <bc:label text="继承人变更后持股数量（股）" col="6" formControl="true"/>
                        <bf:text id="afterToAmount" name="afterToAmount" col="8"
                                 value="${gdTraZr.afterToAmount}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="变更后继承人持股数占比（%）" col="6" formControl="true"/>
                        <bf:text id="afterToProportion" name="afterToProportion" col="8"
                                 value="${gdTraZr.afterToProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
                </div>
            </div>
        </bf:form>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {

    });
</script>
</body>
</html>
