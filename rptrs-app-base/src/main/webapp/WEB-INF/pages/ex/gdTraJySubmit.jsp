<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <%--<title>GdTraZy</title>--%>
    <style>
        .form-group {
            margin-bottom: 8px !important;
        }

        .hr-line-dashed {
            border-top: 1px dashed #e7eaec;
            color: #ffffff;
            background-color: #ffffff;
            height: 1px;
            margin: 7px 0 !important;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="editForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup hr="dashed" hrp="bottom">
                        <bf:formGroup col="4">
                            <bc:label text="股东代码" col="5" formControl="true"/>
                            <bf:text name="shareholdersCode" col="7" value="${gdTraZy.shareholdersCode}"
                                     placeholder="股东代码"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="股东名称" col="5" formControl="true"/>
                            <bf:text name="shareholdersName" col="7" value="${gdTraZy.shareholdersName}"
                                     placeholder="股东名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="股东类型" col="5" formControl="true"/>
                            <bf:select id="shareholdersType" name="shareholdersType" col="7" value="${gdTraZy.shareholdersType}"
                                       dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-RelatedPartiesType" disabled="true">
                                <bf:option text="ALL" value=""></bf:option>
                                <be:event type="select">
                                    var input = $("#__LIKES_shareholdersType");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="总持有股数" col="5" formControl="true"/>
                            <bf:text name="holeShareAmount" col="7" value="${gdTraZy.holeShareAmount}"
                                     placeholder="总持有股数"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="总质押股数" col="5" formControl="true"/>
                            <bf:text name="pledgedShareAmount" col="7" value="${gdTraZy.pledgedShareAmount}"
                                     placeholder="总质押股数"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="质押合同号" col="5" formControl="true"/>
                            <bf:text name="pledgedContract" col="7" value="${gdTraZy.pledgedContract}"
                                     placeholder="质押合同号"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="质押股数" col="5" formControl="true"/>
                            <bf:text name="currPledgedAmount" col="7" value="${gdTraZy.currPledgedAmount}"
                                     placeholder="质押股数">
                            </bf:text>
                        </bf:formGroup>

                        <bf:formGroup col="4">
                            <bc:label text="解押股数" col="5" formControl="true"/>
                            <bf:text name="operationAmount" col="7" placeholder="解押股数" value="${gdTraZy.operationAmount}"/>
                        </bf:formGroup>

                        <bf:formGroup col="4">
                            <bc:label text="解押时间" col="5" formControl="true"/>
                            <bf:datebox id="unpledgedTime" name="unpledgedTime" col="7" format="date"
                                        value="${gdTraZy.unpledgedTime}"/>
                        </bf:formGroup>

                        <bf:formGroup col="4">
                            <bc:label text="解押说明" col="5" formControl="true"/>
                            <bf:text name="unpledgedComment" col="7" placeholder="解押说明" value="${gdTraZy.unpledgedComment}"/>
                        </bf:formGroup>
                    </bf:formGroup>
                </bc:row>
            </bf:form>
        </bc:ibox>
    </div>
</div>

<br:js pack="list"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script src="../../static/js/jquery.dialog.js"></script>

<script type="text/javascript">

</script>
</body>
</html>