<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>GdTraDj</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="gdTraDjBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${gdTraDj.pid}"/>
            <bf:hidden id="domesticOrForeign" name="domesticOrForeign" value="${gdTraDj.domesticOrForeign}"/>
            <div>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="股东代码" col="4" formControl="true"/>
                        <bf:text id="shareholdersCode" name="shareholdersCode" col="8"
                                 value="${gdTraDj.shareholdersCode}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股东名称" col="4" formControl="true"/>
                        <bf:text id="shareholdersName" name="shareholdersName" col="8"
                                 value="${gdTraDj.shareholdersName}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股东类型" col="4" formControl="true"/>
                        <bf:select id="shareholdersType" name="shareholdersType" col="8" value="${gdTraDj.shareholdersType}"
                                   dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelatedPartiesType" disabled="true">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_shareholdersType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="总持有股数" col="4" formControl="true"/>
                        <bf:text id="holeShareAmount" name="holeShareAmount" col="8" value="${gdTraDj.holeShareAmount}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="总冻结股数" col="4" formControl="true"/>
                        <bf:text id="frozenShareAmount" name="frozenShareAmount" col="8"
                                 value="${gdTraDj.frozenShareAmount}" disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:col col="4">
                        <bc:label text="冻结流水号" col="4" formControl="true"/>
                        <bf:text id="frozenSerial" name="frozenSerial" col="8" value="${gdTraDj.frozenSerial}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="冻结股数" col="4" formControl="true"/>
                        <bf:text id="currFrozenAmount" name="currFrozenAmount" col="8" value="${gdTraDj.currFrozenAmount}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
            </div>
            <div>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="解冻股数" col="4" formControl="true"/>
                        <bf:text id="operationAmount" name="operationAmount" col="8" value="${gdTraDj.operationAmount}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="解冻时间" col="4" formControl="true"/>
                        <bf:datebox id="unfrozenTime" name="unfrozenTime" col="8" format="date"
                                    value="${gdTraDj.unfrozenTime}"/>
                    </bc:col>
                </bf:formGroup>
            </div>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="12">
                    <bc:label text="解冻说明" col="4" formControl="true"/>
                    <bf:text id="unfrozenReason" name="unfrozenReason" col="8" value="${gdTraDj.unfrozenReason}"
                             multiple="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
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
