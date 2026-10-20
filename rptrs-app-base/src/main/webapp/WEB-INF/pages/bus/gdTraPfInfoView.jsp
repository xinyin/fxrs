<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>GdTraPf</title>
    <style>
        .form-group .col-xs-4 {
            margin-top: 0.5%;
        }
    </style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="gdTraPfBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="pid" name="pid" value="${gdTraPf.pid}"/>
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="派发方案名称" col="5" formControl="true"/>
                    <bf:text id="name" name="name" col="7" value="${gdTraPf.name}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="派发年份" col="5" formControl="true"/>
                    <bf:text id="year" name="year" col="7" value="${gdTraPf.year}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="股利分配比例" col="5" formControl="true"/>
                    <bf:text id="allocationProportion" name="allocationProportion" col="7"
                             value="${gdTraPf.allocationProportion}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="现金分红" col="5" formControl="true"/>
                    <bf:text id="cashBonus" name="cashBonus" col="7" value="${gdTraPf.cashBonus}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="派送红股" col="5" formControl="true"/>
                    <bf:text id="bonusIssue" name="bonusIssue" col="7" value="${gdTraPf.bonusIssue}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
        </bf:form>
        <div class="row">
            <bc:ibox title="股东分配调整" subTitle="" col="12" collapsible="true">
                <bf:form id="queryForm" validation="true">
                    <bc:row cls="m-b">
                        <bf:formGroup col="4">
                            <bc:label text="股东账号" col="5" formControl="true"/>
                            <bf:text name="__LIKES_shareholdCode" col="7" placeholder="股东账号"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="股东名称" col="5" formControl="true"/>
                            <bf:text name="__LIKES_name" col="7" placeholder="股东名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="境内/境外" col="5" formControl="true"/>
                            <bf:select id="domesticOrForeign" name="__LIKES_domesticOrForeign" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-DomesticOrForeign">
                                <bf:option text="ALL" value=""></bf:option>
                                <be:event type="select">
                                    var input = $("#__LIKES_domesticOrForeign");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <%--<bc:col col="3" colOffset="1">--%>
                            <%--<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>--%>
                            <%--<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>--%>
                        <%--</bc:col>--%>

                    </bc:row>
                    <bc:row cls="m-b">
                        <bc:col col="12" colOffset="5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
                        </bc:col>
                    </bc:row>
                </bf:form>
                <bc:table id="gdPfInfoTable" idField="pid" url="${base}/bus/gdPfInfo"
                          queryUrl="queryGdPfInfo?pfPid=${gdTraPf.pid}">
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="pfPid" title="方案主键" width="100" visible="false"/>
                    <bc:column field="gdPid" title="股东主键" width="100" visible="false"/>
                    <bc:column field="shareholdCode" title="股东账号" width="100"/>
                    <bc:column field="name" title="股东名称" width="100"/>
                    <bc:column field="gdType" title="股东类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelatedPartiesType"/>
                    <bc:column field="domesticOrForeign" title="境内/境外" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="identifyNumber" title="证件号码" width="100"/>
                    <bc:column field="gdDj" title="账户冻结" width="100"/>
                    <bc:column field="tradeAccount" title="交易账户账号" width="100"/>
                    <bc:column field="holeShareAmount" title="持股数量" width="100"/>
                    <bc:column field="shareholdingProportion" title="持股占比" width="100"/>
                    <bc:column field="frozenShareAmount" title="已冻结股数" width="100"/>
                    <bc:column field="pledgedShareAmount" title="已质押股数" width="100"/>
                    <bc:column field="cashBonus" title="奖金分红" width="100"/>
                    <bc:column field="bonusIssue" title="派送红利" width="100"/>
<%--                    <bc:operator id="save" icon="eye" title="保存" handler="saveHandler"/>--%>
                    <%--                            <bc:operator id="edit" icon="edit" title="编辑"/>--%>
                    <%--                            <bc:operator id="remove" icon="trash" title="移除"/>--%>
                </bc:table>
            </bc:ibox>
        </div>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {

    });
</script>

<script type="text/javascript">
    $("#approvalTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    $("#applicaSubTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });


    $("#tranDate").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    $("#validityContract").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    function saveHandler() {
        var pid = getIdSelections();
        $.messager.alert(pid);
    }

    function getIdSelections() {
        return $.map($('#gdPfInfoTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
</script>

</body>
</html>
